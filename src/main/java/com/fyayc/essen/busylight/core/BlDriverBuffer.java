package com.fyayc.essen.busylight.core;

import static com.fyayc.essen.busylight.core.BlConstants.MAX_VOLUME;

import com.fyayc.essen.busylight.core.BlConstants.BufferPositions;
import com.fyayc.essen.busylight.core.BlConstants.Tone;

public final class BlDriverBuffer {

  public static Builder empty() {
    return new Builder(emptyBuffer());
  }

  public static int[] emptyBuffer() {
    return new int[] {
      16, 0, 0, 0, 0, 0, 0, 128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255,
      255, 255
    };
  }

  public static void color(int[] buffer, int r, int g, int b) {
    buffer[BufferPositions.RED.ix] = color2Intensity(r);
    buffer[BufferPositions.GREEN.ix] = color2Intensity(g);
    buffer[BufferPositions.BLUE.ix] = color2Intensity(b);
  }

  public static int[] color(int r, int g, int b) {
    int[] buffer = emptyBuffer();
    color(buffer, r, g, b);
    return buffer;
  }

  public static void tone(int[] buffer, Tone tone, int vol) {
    buffer[BufferPositions.TONE.ix] = tone.getValue(vol);
  }

  public static int[] tone(Tone tone, int vol) {
    int[] buffer = emptyBuffer();
    tone(buffer, tone, vol);
    return buffer;
  }

  public static int[] tone(Tone tone) {
    return tone(tone, MAX_VOLUME);
  }

  public static void off(int[] buffer) {
    buffer[BufferPositions.RED.ix] = 0;
    buffer[BufferPositions.GREEN.ix] = 0;
    buffer[BufferPositions.BLUE.ix] = 0;
    buffer[BufferPositions.TONE.ix] = Tone.NONE.getValue(0);
  }
  // convert scale from 0-255 => 0-100
  private static int color2Intensity(int color){
    return (color * 100 / 255);
  }

  public static class Builder {
    private int[] data;

    public Builder(int[] initBuffer) {
      this.data = initBuffer;
    }

    public Builder withColor(int r, int g, int b) {
      color(data, r, g, b);
      return this;
    }

    public Builder withTone(Tone tone, int volume) {
      tone(data, tone, volume);
      return this;
    }

    public Builder withTone(Tone t) {
      return withTone(t, MAX_VOLUME);
    }

    public int[] get() {
      return data;
    }
  }
}
