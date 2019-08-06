package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlConstants.Tone;
import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.BlDriverBuffer;
import java.io.IOException;
import java.util.BitSet;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException, IOException {
    try (Busylight busylight = Busylight.find()) {
      busylight.light(new int[]{0, 26, 0});
      Thread.sleep(10000);
    }
  }
}










