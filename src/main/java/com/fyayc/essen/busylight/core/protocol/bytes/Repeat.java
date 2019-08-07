package com.fyayc.essen.busylight.core.protocol.bytes;

public class Repeat extends StepByte {
  public static final Repeat DO_NOT_REPEAT = new Repeat(1);

  private Repeat(int times) {
    super("repeat", "00000000");
    bitStore().setBits(0, times, BIT_LENGTH);
  }

  public static Repeat ofTimes(int times) {
    if (times < 0 || times > 255) {
      throw new UnsupportedOperationException("expecting times between 1 and 255");
    }
    return new Repeat(times);
  }
}
