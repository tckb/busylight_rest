package com.fyayc.essen.busylight.core.protocol.bytes;

public class Tone extends StepByte {

  private static final int MAX_VOLUME = 7;
  public static final Tone TURN_OFF_TONE = Tone.forSettings(0, 0);

  private Tone() {
    super("tone");
  }

  public static Tone noSettings() {
    Tone toneByte = new Tone();
    toneByte.bitStore().setBit(7, false);
    return toneByte;
  }

  public static Tone forSettings(int toneId, int volume) {
    if (volume < 0 || volume > MAX_VOLUME) {
      throw new UnsupportedOperationException("expecting times between 0 and 7");
    }
    if (toneId < 0 || toneId > 15) {
      throw new UnsupportedOperationException("expecting times between 0 and 255");
    }
    Tone toneByte = new Tone();
    toneByte.bitStore().setBit(7, true);
    toneByte.bitStore().setBits(0, volume, 3);
    toneByte.bitStore().setBits(3, toneId, 4);
    return toneByte;
  }

  public static Tone forSettings(Tones tone, int volume) {
    return forSettings(tone.value, volume);
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

    private final int value;

    Tones(int value) {
      this.value = value;
    }
  }
}
