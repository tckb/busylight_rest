package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.protocol.ProtocolV2Spec;
import com.fyayc.essen.busylight.core.protocol.ProtocolStep;
import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.Repeat;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;

public class BusylightTest {
  public static void main(String[] args) {
    ProtocolV2Spec protocol =
        ProtocolV2Spec.builder()
            .addStep(
                ProtocolStep.builder()
                    .color( Color.EMPTY, Color.EMPTY,Color.ofIntensity(20))
                    .light(Time.forDuration(0.5), Time.forDuration(0.5))
                    .repeatStep(Repeat.ofTimes(3))
                    .tone(com.fyayc.essen.busylight.core.protocol.bytes.Tone.TURN_OFF_TONE)
                    .command(Command.nextStep(1))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .color(Color.ofIntensity(80), Color.EMPTY, Color.EMPTY)
                    .light(Time.forDuration(1.5), Time.forDuration(0.5))
                    .repeatStep(Repeat.ofTimes(5))
                    .tone(com.fyayc.essen.busylight.core.protocol.bytes.Tone.noSettings())
                    .command(Command.nextStep(2))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .color(Color.EMPTY, Color.EMPTY,Color.ofIntensity(100))
                    .light(Time.forDuration(10), Time.forDuration(0))
                    .repeatStep(Repeat.ofTimes(1))
                    .tone(Tone.forSettings(13, 7))
                    .command(Command.nextStep(0))
                    .build())
            .build();
    System.out.println("Sending out : "+protocol.toHexString());
    try (BlDriver driver = BlDriver.tryAndAcquire()) {
      driver.sendBuffer(protocol.toBytes());
    }
  }

}










