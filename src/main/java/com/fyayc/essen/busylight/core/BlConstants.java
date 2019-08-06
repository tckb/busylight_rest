package com.fyayc.essen.busylight.core;

public final class BlConstants {

  public static final int BUFFER_LENGTH = 62;
  public static final int MAX_VOLUME = 7;
  public static final int MIN_VOLUME = 0;
  static final String PRODUCT_STRING = "BUSYLIGHT";
  static final int PRODUCT_ID = 15309;
  static final int VENDOR_ID = 10171;

  enum BufferPositions {
    RED(2),
    GREEN(3),
    BLUE(4),
    TONE(7),
    CHECKSUM_MSB(62),
    CHECKSUM_LSB(63);

    final int ix;

    BufferPositions(int index) {
      ix = index;
    }
  }

  public enum Tone {
    OPENOFFICE(136),
    QUIET(144),
    FUNKY(152),
    FAIRY_TALE(160),
    KUANDO_TRAIN(168),
    TELEPHONIC_NORDIC(176),
    TELEPHONIC_ORIGINAL(184),
    TELEPHONIC_PICKMEUP(192),
    BUZZ(216),
    TEST(204),
    NONE(128);

    private final int value;

    Tone(int value) {
      this.value = value;
    }

    int getValue(int volume) {
      int actualVol = volume;
      if (volume < MIN_VOLUME) actualVol = MIN_VOLUME;
      if (volume > MAX_VOLUME) actualVol = MAX_VOLUME;

      return value + actualVol;
    }
  }
}
