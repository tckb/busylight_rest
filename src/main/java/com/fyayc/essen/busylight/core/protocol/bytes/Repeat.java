package com.fyayc.essen.busylight.core.protocol.bytes;

public class Repeat extends StepByte {

  private Repeat() {
    super("repeat");
  }

  public static Repeat ofTimes(int times) {
    if(times<1 || times>255){
      throw new UnsupportedOperationException("expecting times between 1 and 255");
    }
    Repeat repeatByte = new Repeat();
    repeatByte.bitStore().setBits(0, times, BIT_LENGTH);
    return repeatByte;
  }
}
