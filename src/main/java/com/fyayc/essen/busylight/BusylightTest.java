package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.protocol.ProtocolStep;
import com.fyayc.essen.busylight.core.protocol.ProtocolV2Spec;
import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.Repeat;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    try (BlDriver driver = BlDriver.tryAndAcquire()) {
      for (int i = 0; i < 16; i++) {
        ProtocolV2Spec protocol =
            ProtocolV2Spec.builder()
                .addStep(
                    ProtocolStep.builder()
                        .color(Color.EMPTY, Color.EMPTY, Color.EMPTY)
                        .light(Time.forDuration(0), Time.forDuration(0))
                        .repeatStep(Repeat.DO_NOT_REPEAT)
                        .tone(Tone.TURN_OFF_TONE)
                        .command(Command.nextStep(1))
                        .build())
                .addStep(
                    ProtocolStep.builder()
                        .color(Color.ofPixel(i+50), Color.ofIntensity(i+10), Color.ofIntensity(i))
                        .light(Time.forDuration(3), Time.forDuration(0))
                        .repeatStep(Repeat.DO_NOT_REPEAT)
                        .tone(Tone.forSettings(i, 7))
                        .command(Command.nextStep(0))
                        .build())
                .build();
        System.out.println("playing : " + i);
        driver.sendBuffer(protocol.toBytes());
        Thread.sleep(6000);
      }
    }
  }
}
