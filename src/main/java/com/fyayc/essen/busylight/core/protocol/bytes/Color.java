package com.fyayc.essen.busylight.core.protocol.bytes;

public class Color extends StepByte {

  private Color() {
    super("light");
  }

  public static Color ofPixel(int pixelValue) {
    if (pixelValue < 0 || pixelValue > 255) {
      throw new IllegalArgumentException("expecting times between 0 and 255");
    }
    Color colorByte = new Color();
    colorByte.bitStore().setBits(0, pixel2Intensity(pixelValue), BIT_LENGTH);
    return colorByte;
  }

  public static Color ofIntensity(int intensity) {
    if (intensity < 0 || intensity > 100) {
      throw new IllegalArgumentException("expecting times between 0 and 255");
    }
    Color colorByte = new Color();
    colorByte.bitStore().setBits(0, intensity, BIT_LENGTH);
    return colorByte;
  }

  private static int pixel2Intensity(int color) {
    return (color * 100 / 255);
  }
}
