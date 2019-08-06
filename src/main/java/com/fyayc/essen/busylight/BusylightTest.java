package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.protocol.ProtocolStep;
import com.fyayc.essen.busylight.core.protocol.ProtocolV2Spec;
import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.Repeat;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone.Tones;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    try (BlDriver driver = BlDriver.tryAndAcquire()) {
        ProtocolV2Spec protocol =
            ProtocolV2Spec.builder()
                .addStep(
                    ProtocolStep.builder()
                        .color(Color.EMPTY, Color.EMPTY, Color.EMPTY)
                        .light(Time.forDuration(0), Time.forDuration(0))
                        .tone(Tone.TURN_OFF_TONE)
                        .repeat(Repeat.DO_NOT_REPEAT)
                        .command(Command.nextStep(1))
                        .build())
                .addStep(
                    ProtocolStep.builder()
                        .color(Color.ofPixel(10), Color.ofIntensity(50), Color.ofIntensity(80))
                        .light(Time.forDuration(3), Time.forDuration(0))
                        .tone(Tone.forSettings(Tones.TELEPHONIC_PICKMEUP, 7))
                        .repeat(Repeat.DO_NOT_REPEAT)
                        .command(Command.nextStep(0))
                        .build())
                .build();
        driver.sendBuffer(protocol.toBytes());
        Thread.sleep(6000);
    }
  }
}
