package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlConstants.Tone;
import java.io.IOException;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException, IOException {
    try (Busylight busylight = Busylight.find()) {
      busylight.blink(new int[] {0, 140, 0}, 500, false);
      Thread.sleep(5000);
      busylight.light(new int[] {140, 0, 0});
      Thread.sleep(5000);
      busylight.blink(new int[] {0, 140, 140}, 1000, true);
      Thread.sleep(5000);
      busylight.blinkWithTone(new int[] {0, 140, 140}, 1000, Tone.FAIRY_TALE);
      Thread.sleep(10000);

    }
  }
}
