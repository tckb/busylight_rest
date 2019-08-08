package com.fyayc.essen.busylight.core.protocol.bytes;

import com.fyayc.essen.busylight.core.protocol.SpecConstants.Tones;

public class Tone extends StepByte {

  private static final int MAX_VOLUME = 7;
  public static final Tone TURN_OFF_TONE = Tone.forTone(0, 0);

  private Tone() {
    super("tone");
  }

  public static Tone noSettings() {
    Tone toneByte = new Tone();
    toneByte.bitStore().setBit(7, false);
    return toneByte;
  }

  public static Tone forTone(int toneId, int volume) {
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

  public static Tone forTone(Tones tone, int volume) {
    return forTone(tone.value, volume);
  }
}
