package com.fyayc.essen.busylight.core.protocol;

import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.StepByte;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;

/** Constants that can be used to build the procotocol */
public final class ProtocolConstants {
  public static final short[] SUPPORTED_PRODUCT_IDS = new short[] {0x3BCA, 0x3BCB, 0x3BCC, 0x3BCD};
  public static final short SUPPORTED_VENDOR_ID = 0x27BB;

  public enum Tones {
    OPENOFFICE(1),
    QUIET(2),
    FUNKY(3),
    FAIRY_TALE(4),
    KUANDO_TRAIN(5),
    TELEPHONIC_NORDIC(6),
    TELEPHONIC_ORIGINAL(7),
    TELEPHONIC_PICKMEUP(8),
    IM_1(9),
    IM_2(10),
    IM_3(13);

    public final int value;

    Tones(int value) {
      this.value = value;
    }
  }

  public enum LightStatus {
    AWAY(Color.ofIntensity(89), Color.ofIntensity(100), Color.EMPTY),
    BUSY(Color.ofIntensity(100), Color.EMPTY, Color.EMPTY),
    FREE(Color.EMPTY, Color.ofIntensity(100), Color.EMPTY),
    DO_NOT_DISTURB(Color.ofIntensity(67), Color.ofIntensity(2), Color.ofIntensity(100)),
    OFF(Color.EMPTY, Color.EMPTY, Color.EMPTY);
    final StepByte[] rgbBytes;

    LightStatus(StepByte... rgb) {
      rgbBytes = rgb;
    }
  }

  public enum Specs {
    BUSY_IN_CALL(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(14), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.3), Time.forDuration(0))
                    .command(Command.nextStep(1))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(42), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
                    .command(Command.nextStep(2))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(72), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
                    .command(Command.nextStep(3))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(100), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.8), Time.forDuration(0))
                    .command(Command.nextStep(4))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(72), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
                    .command(Command.nextStep(5))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(42), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
                    .command(Command.nextStep(6))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(20), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    BUSY(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(LightStatus.BUSY)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    DO_NOT_DISTURB(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(LightStatus.DO_NOT_DISTURB)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    AWAY(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(LightStatus.AWAY)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    FREE(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(LightStatus.FREE)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    OFF(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(LightStatus.OFF)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    KEEP_ALIVE(
        ProtocolSpec.builder()
            .addStep(ProtocolStep.builder().command(Command.keepAliveSignal(15)).build())
            .build());

    public final ProtocolSpec protocol;

    Specs(ProtocolSpec protocol) {
      this.protocol = protocol;
    }
  }
}
