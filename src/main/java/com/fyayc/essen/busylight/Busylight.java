package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlConstants;
import com.fyayc.essen.busylight.core.BlConstants.Tone;
import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.BlDriverBuffer;
import java.io.Closeable;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Busylight implements Closeable {
  private final long HEARTBEAT_INTERVAL = 100;
  private final BlDriver driver;
  private Timer timer;
  private int[] currentBuffer;
  private HeartbeatWork heartbeatWork;
  private TimerTask heartBeatTask;
  private boolean stopHeartbeat = false;

  private Busylight() throws InterruptedException {
    driver = BlDriver.tryAndAcquire();
    timer = new Timer("Busylight Comm Timer");
    currentBuffer = BlDriverBuffer.emptyBuffer();
    heartbeatWork = () -> send(currentBuffer);
    heartBeatTask =
        new TimerTask() {
          @Override
          public void run() {
            if (!stopHeartbeat) {
              heartbeatWork.workWithDriver();
            }
          }
        };
    showInit();
    timer.scheduleAtFixedRate(heartBeatTask, 1000, HEARTBEAT_INTERVAL);
  }

  public static Busylight find() {
    return Helper.INSTANCE;
  }

  private void showInit() throws InterruptedException {
    sendShortPulse(255, 0, 0);
    Thread.sleep(1000);
    sendShortPulse(0, 255, 0);
    Thread.sleep(1000);
    sendShortPulse(0, 0, 255);
    Thread.sleep(1000);
  }

  private void sendShortPulse(int r, int g, int b) throws InterruptedException {
    send(BlDriverBuffer.color(r, g, b));
    Thread.sleep(10);
    send(BlDriverBuffer.emptyBuffer());
  }

  public void blink(int[] rgb, long freq, boolean shortPulse) {
    heartbeatWork =
        () -> {
          try {
            currentBuffer = BlDriverBuffer.color(rgb[0], rgb[1], rgb[2]);
            send(currentBuffer);
            if (shortPulse) {
              Thread.sleep(10);
            } else {
              Thread.sleep(100);
            }
            currentBuffer = BlDriverBuffer.emptyBuffer();
            send(currentBuffer);
            Thread.sleep(freq);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };
  }

  public void blinkWithTone(int[] rgb, long freq, Tone bgTone) {
    blinkWithTone(rgb, freq, bgTone, BlConstants.MAX_VOLUME / 2);
  }

  public void blinkWithTone(int[] rgb, long freq, Tone bgTone, int volume) {
    heartbeatWork =
        () -> {
          try {
            currentBuffer =
                BlDriverBuffer.empty()
                    .withColor(rgb[0], rgb[1], rgb[2])
                    .withTone(bgTone, volume)
                    .get();
            send(currentBuffer);
            Thread.sleep(2000);
            currentBuffer = BlDriverBuffer.emptyBuffer();
            send(currentBuffer);
            Thread.sleep(freq);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };
  }

  public void light(int[] rgb) {
    heartbeatWork = () -> send(BlDriverBuffer.color(rgb[0], rgb[1], rgb[2]));
  }

  public void off() {
    heartbeatWork = () -> send(BlDriverBuffer.emptyBuffer());
  }

  private void send(int[] bufferData) {
    if (driver.isOpen()) {
      driver.sendBuffer(bufferData);
    } else {
      System.err.println("already closed");
    }
  }

  @Override
  public void close() throws IOException {
    send(BlDriverBuffer.emptyBuffer());
    stopHeartbeat = true;
    heartBeatTask.cancel();
    timer.cancel();
    timer.purge();
    driver.close();
  }

  private interface HeartbeatWork {
    void workWithDriver();
  }

  private static class Helper {
    private static final Busylight INSTANCE;

    static {
      try {
        INSTANCE = new Busylight();
      } catch (InterruptedException e) {
        throw new ExceptionInInitializerError(e);
      }
    }
  }
}
