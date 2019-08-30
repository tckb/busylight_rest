package com.fyayc.essen.busylight.core.protocol;

import com.fyayc.essen.busylight.core.protocol.bytes.Color;
import com.fyayc.essen.busylight.core.protocol.bytes.Command;
import com.fyayc.essen.busylight.core.protocol.bytes.Repeat;
import com.fyayc.essen.busylight.core.protocol.bytes.StepByte;
import com.fyayc.essen.busylight.core.protocol.bytes.Time;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;
import java.util.Arrays;

/**
 * Constants that can be used to build the protocol
 */
public final class SpecConstants {

  public static final short[] SUPPORTED_PRODUCT_IDS = new short[]{0x3BCA, 0x3BCB, 0x3BCC, 0x3BCD};
  public static final short SUPPORTED_VENDOR_ID = 0x27BB;
  private static double PULSE_GAP_TIME = 0.1;

  public static ProtocolSpec lightSpec(Light light) {
    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(light)
                .lightDuration(Time.forDuration(10), Time.forDuration(0))
                .tone(Tone.noSettings())
                .command(Command.nextStep(0))
                .build())
        .build();
  }

  public static ProtocolSpec lightSpec(java.awt.Color color) {
    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(
                    Color.ofPixel(color.getRed()),
                    Color.ofPixel(color.getGreen()),
                    Color.ofPixel(color.getBlue()))
                .lightDuration(Time.forDuration(10), Time.forDuration(0))
                .tone(Tone.noSettings())
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
                .tone(Tone.noSettings())
                .command(Command.nextStep(0))
                .build())
        .build();
  }

  public static ProtocolSpec blinkSpec(java.awt.Color color) {
    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(
                    Color.ofPixel(color.getRed()),
                    Color.ofPixel(color.getGreen()),
                    Color.ofPixel(color.getBlue()))
                .lightDuration(Time.forDuration(0.5), Time.forDuration(0.5))
                .tone(Tone.noSettings())
                .command(Command.nextStep(0))
                .build())
        .build();
  }

  public static ProtocolSpec toneSpec(Tone tone) {
    return ProtocolSpec.builder()
        .addStep(ProtocolStep.builder().tone(tone).command(Command.nextStep(0)).build())
        .build();
  }

  public static ProtocolSpec pulseSpec(Light light) {
    Color[][] interpolatedColors =
        interpolate(
            new int[]{0, 0, 0},
            new int[]{
                light.rgbBytes[0].toInt(), light.rgbBytes[1].toInt(), light.rgbBytes[2].toInt()
            },
            5);

    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[1][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                .command(Command.nextStep(1))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[2][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME * 2), Time.forDuration(0))
                .command(Command.nextStep(2))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[3][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME * 2), Time.forDuration(0))
                .command(Command.nextStep(3))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[4][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME * 4), Time.forDuration(0))
                .command(Command.nextStep(4))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[3][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME * 2), Time.forDuration(0))
                .command(Command.nextStep(5))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[2][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME * 2), Time.forDuration(0))
                .command(Command.nextStep(6))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[1][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                .command(Command.nextStep(0))
                .build())
        .build();
  }

  private static Color[][] interpolate(int[] fromColor, int[] toColor, int steps) {
    Color[][] colors = new Color[steps][3];
    double stepFactor = 1.0 / (steps - 1);
    for (int step = 0; step < steps; step++) {
      Color[] interpolatedColor = interpolate(fromColor, toColor, stepFactor * step);
      System.arraycopy(interpolatedColor, 0, colors[step], 0, interpolatedColor.length);
    }
    return colors;
  }

  private static Color[] interpolate(int[] fromColor, int[] toColor, double factor) {
    int[] resRgb = Arrays.copyOf(fromColor, fromColor.length);

    for (int i = 0; i < 3; i++) {
      resRgb[i] = Math.toIntExact(Math.round(resRgb[i] + factor * (toColor[i] - fromColor[i])));
    }
    return new Color[]{
        Color.ofPixel(resRgb[0]), Color.ofPixel(resRgb[1]), Color.ofPixel(resRgb[2])
    };
  }

  public static ProtocolSpec pulseSpec(java.awt.Color color) {
    return pulseSpec(
        color,
        new double[]{
            PULSE_GAP_TIME,
            PULSE_GAP_TIME * 2,
            PULSE_GAP_TIME * 2,
            PULSE_GAP_TIME * 4,
            PULSE_GAP_TIME * 2,
            PULSE_GAP_TIME * 2,
            PULSE_GAP_TIME
        });
  }

  public static ProtocolSpec pulseSpec(java.awt.Color color, double[] pulseDuration) {
    Color[][] interpolatedColors =
        interpolate(
            new int[]{0, 0, 0}, new int[]{color.getRed(), color.getGreen(), color.getBlue()}, 5);

    return ProtocolSpec.builder()
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[1][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(pulseDuration[0]), Time.forDuration(0))
                .command(Command.nextStep(1))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[2][0], interpolatedColors[2][1], interpolatedColors[2][2])
                .lightDuration(Time.forDuration(pulseDuration[1]), Time.forDuration(0))
                .command(Command.nextStep(2))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[3][0], interpolatedColors[3][1], interpolatedColors[3][2])
                .lightDuration(Time.forDuration(pulseDuration[2]), Time.forDuration(0))
                .command(Command.nextStep(3))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[4][0], interpolatedColors[4][1], interpolatedColors[4][2])
                .lightDuration(Time.forDuration(pulseDuration[3]), Time.forDuration(0))
                .command(Command.nextStep(4))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[3][0], interpolatedColors[3][1], interpolatedColors[3][2])
                .lightDuration(Time.forDuration(pulseDuration[4]), Time.forDuration(0))
                .command(Command.nextStep(5))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[2][0], interpolatedColors[2][1], interpolatedColors[2][2])
                .lightDuration(Time.forDuration(pulseDuration[5]), Time.forDuration(0))
                .command(Command.nextStep(6))
                .build())
        .addStep(
            ProtocolStep.builder()
                .light(interpolatedColors[1][0], interpolatedColors[1][1], interpolatedColors[1][2])
                .lightDuration(Time.forDuration(pulseDuration[6]), Time.forDuration(0))
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
        Color.ofPixel(java.awt.Color.WHITE.getBlue())),
    CUSTOM();
    final StepByte[] rgbBytes;

    Light(StepByte... rgb) {
      rgbBytes = rgb;
    }
  }

  public enum StandardSpecs {
    BUSY_IN_CALL(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(14), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                    .command(Command.nextStep(1))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(42), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                    .command(Command.nextStep(2))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(72), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                    .command(Command.nextStep(3))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(100), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME * 4), Time.forDuration(0))
                    .command(Command.nextStep(4))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(72), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                    .command(Command.nextStep(5))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(42), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
                    .command(Command.nextStep(6))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(20), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(PULSE_GAP_TIME), Time.forDuration(0))
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
    NOTIFY_IM(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Color.EMPTY, Color.EMPTY, Color.ofIntensity(80))
                    .lightDuration(Time.forDuration(0.5), Time.forDuration(0.5))
                    .tone(Tone.forTone(Tones.OPENOFFICE, 4))
                    .command(Command.nextStep(1))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .tone(Tone.forTone(Tones.IM_1, 7))
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    SOS(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(80), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.3), Time.forDuration(0.3))
                    .command(Command.nextStep(1))
                    .repeat(Repeat.ofTimes(3))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(80), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.6), Time.forDuration(0.6))
                    .command(Command.nextStep(2))
                    .repeat(Repeat.ofTimes(3))
                    .build())
            .addStep(
                ProtocolStep.builder()
                    .light(Color.ofIntensity(80), Color.EMPTY, Color.EMPTY)
                    .lightDuration(Time.forDuration(0.3), Time.forDuration(0.3))
                    .command(Command.nextStep(0))
                    .repeat(Repeat.ofTimes(0))
                    .build())
            .build()),
    OFF(
        ProtocolSpec.builder()
            .addStep(
                ProtocolStep.builder()
                    .light(Light.OFF)
                    .lightDuration(Time.forDuration(10), Time.forDuration(0))
                    .tone(Tone.TURN_OFF_TONE)
                    .command(Command.nextStep(0))
                    .build())
            .build()),
    KEEP_ALIVE(
        ProtocolSpec.builder()
            .addStep(ProtocolStep.builder().command(Command.keepAliveSignal(15)).build())
            .build());

    public final ProtocolSpec protocol;

    StandardSpecs(ProtocolSpec protocol) {
      this.protocol = protocol;
    }
  }
}
