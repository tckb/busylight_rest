package com.fyayc.essen.busylight.core.protocol.bytes;

public class Time extends StepByte {
  public static final double MAX_DURATION = 25.5;
  public static final double MIN_DURATION = 0;

  private Time() {
    super("light");
  }

  public static Time forDuration(double duration) {
    if (duration < 0 || duration > MAX_DURATION) {
      throw new UnsupportedOperationException("expecting times between 0 and 255");
    }
    Time durationByte = new Time();
    durationByte.bitStore().setBits(0, (int) (duration * 10), BIT_LENGTH);
    return durationByte;
  }
}
