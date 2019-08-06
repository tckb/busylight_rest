package com.fyayc.essen.busylight.core;

public final class BlConstants {
  static final short[] SUPPORTED_PRODUCT_IDS = new short[] {0x3BCA, 0x3BCB, 0x3BCC, 0x3BCD};
  static final short SUPPORTED_VENDOR_ID = 0x27BB;

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
  }
}
