package com.fyayc.essen.busylight.core.protocol;

import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.StepByte;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;

/** Constants that can be used to build the procotocol */
public final class SpecConstants {
  public static final short[] SUPPORTED_PRODUCT_IDS = new short[] {0x3BCA, 0x3BCB, 0x3BCC, 0x3BCD};
  public static final short SUPPORTED_VENDOR_ID = 0x27BB;

  public static ProtocolSpec lightSpec(Light light) {
    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(light)
                .lightDuration(Time.forDuration(10), Time.forDuration(0))
                .command(Command.nextStep(0))
                .build())
        .build();
  }

  public static ProtocolSpec blinkSpec(Light light) {
    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(light)
                .lightDuration(Time.forDuration(0.5), Time.forDuration(0.5))
                .command(Command.nextStep(0))
                .build())
        .build();
  }

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

  public enum Light {
    AWAY(Color.ofIntensity(89), Color.ofIntensity(100), Color.EMPTY),
    BUSY(Color.ofIntensity(100), Color.EMPTY, Color.EMPTY),
    FREE(Color.EMPTY, Color.ofIntensity(100), Color.EMPTY),
    DO_NOT_DISTURB(Color.ofIntensity(67), Color.ofIntensity(2), Color.ofIntensity(100)),
    OFF(Color.EMPTY, Color.EMPTY, Color.EMPTY),
    MAGENTA(
        Color.ofPixel(java.awt.Color.MAGENTA.getRed()),
        Color.ofPixel(java.awt.Color.MAGENTA.getGreen()),
        Color.ofPixel(java.awt.Color.MAGENTA.getBlue())),
    ORANGE(
        Color.ofPixel(java.awt.Color.ORANGE.getRed()),
        Color.ofPixel(java.awt.Color.ORANGE.getGreen()),
        Color.ofPixel(java.awt.Color.ORANGE.getBlue())),
    PINK(
        Color.ofPixel(java.awt.Color.PINK.getRed()),
        Color.ofPixel(java.awt.Color.PINK.getGreen()),
        Color.ofPixel(java.awt.Color.PINK.getBlue())),
    YELLOW(
        Color.ofPixel(java.awt.Color.YELLOW.getRed()),
        Color.ofPixel(java.awt.Color.YELLOW.getGreen()),
        Color.ofPixel(java.awt.Color.YELLOW.getBlue())),
    WHITE(
        Color.ofPixel(java.awt.Color.WHITE.getRed()),
        Color.ofPixel(java.awt.Color.WHITE.getGreen()),
        Color.ofPixel(java.awt.Color.WHITE.getBlue()));

    final StepByte[] rgbBytes;

    Light(StepByte... rgb) {
      rgbBytes = rgb;
    }
  }

  public enum Specs {
    BUSY_IN_CALL(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(14), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.2), Time.forDuration(0))
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
                    .light(Light.BUSY)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    DO_NOT_DISTURB(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Light.DO_NOT_DISTURB)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    AWAY(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Light.AWAY)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    FREE(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Light.FREE)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    OFF(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Light.OFF)
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
