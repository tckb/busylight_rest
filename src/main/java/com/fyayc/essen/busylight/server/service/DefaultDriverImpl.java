package com.fyayc.essen.busylight.server.service;

import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;
import com.fyayc.essen.busylight.core.protocol.SpecConstants;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StandardSpecs;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.Tones;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;
import com.fyayc.essen.busylight.server.driver.BusylightDriver;
import com.fyayc.essen.busylight.server.driver.BusylightDriverSpec;
import java.awt.Color;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class DefaultDriverImpl implements DriverService {

  private final BusylightDriverSpec blDriver;

  public DefaultDriverImpl(BusylightDriverSpec blDriver) {
    this.blDriver = blDriver;
  }

  @Override
  public void setStatus(String status) {
    StandardSpecs sSpec = null;
    switch (status) {
      case "busy":
        sSpec = StandardSpecs.BUSY;
        break;
      case "away":
        sSpec = StandardSpecs.AWAY;
        break;
      case "free":
      case "available":
        sSpec = StandardSpecs.FREE;
        break;
      case "dnd":
      case "do not disturb":
        sSpec = StandardSpecs.DO_NOT_DISTURB;
        break;
      case "oncall":
        sSpec = StandardSpecs.BUSY_IN_CALL;
        break;
      case "off":
        sSpec = StandardSpecs.OFF;
        break;
      case "notify":
        sSpec = StandardSpecs.NOTIFY_IM;
        break;
      case "sos":
        sSpec = StandardSpecs.SOS;
        break;
      default:
        sSpec = null;
    }
    if (sSpec == null) {
      throw new IllegalArgumentException(
          "Unknown status '"
              + status
              + "' ; Available Status: 'busy', 'away', 'free', 'dnd', 'oncall' and 'notify'");
    }
    sendToDriver(sSpec);
  }

  @Override
  public void setColor(String hex, String type) {
    Color color;
    try {
      color = Color.decode(hex);
    } catch (IllegalArgumentException exception) {
      throw new IllegalArgumentException("Unknown or illegal hex code: " + hex, exception);
    }
    switch (type) {
      case "none":
        sendToDriver(SpecConstants.lightSpec(color));
        break;
      case "blink":
        sendToDriver(SpecConstants.blinkSpec(color));
        break;
      case "pulse":
        sendToDriver(SpecConstants.pulseSpec(color));
        break;
      default:
        throw new IllegalArgumentException("Bad / Unknown type. Only 'none', 'blink', 'pulse'");
    }
  }

  @Override
  public void tone(String tone, int volume, long toneDuration) {
    sendToDriver(SpecConstants.toneSpec(Tone.forTone(Tones.valueOf(tone), volume)));
    if (toneDuration > 0) {
      try {
        Thread.sleep(toneDuration);
      } catch (InterruptedException ignored) {
      } finally {
        resetDevice();
      }
    }
  }

  @PostConstruct
  public void resetDevice() {
    if (blDriver instanceof BusylightDriver) {
      sendToDriver(StandardSpecs.OFF);
    }
  }

  @PreDestroy
  public void terminate() {
    resetDevice();
    if (blDriver != null) {
      blDriver.close();
    }
  }

  private void sendToDriver(ProtocolSpec spec) {
    if (blDriver != null) {
      blDriver.sendToDriver(spec);
    }
  }

  private void sendToDriver(StandardSpecs standardSpecs) {
    sendToDriver(standardSpecs.protocol);
  }
}
