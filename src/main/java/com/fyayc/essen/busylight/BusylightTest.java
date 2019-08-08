package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.SpecConstants;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.Light;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StandardSpecs;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.Tones;
import com.fyayc.essen.busylight.core.protocol.bytes.Tone;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    try (Driver driver = Driver.tryAndAcquire()) {
      System.out.println("****** PLAYING MUSIC *******");
      driver.send(SpecConstants.toneSpec(Tone.forTone(Tones.OPENOFFICE, 1)));

      System.out.println("****** PLAYING STANDARD SPECS *******");
      for (StandardSpecs spec : StandardSpecs.values()) {
        driver.send(spec);
        Thread.sleep(2000);
      }

      System.out.println("****** PLAYING LIGHT SPECS *******");

      for (Light light : Light.values()) {
        driver.send(SpecConstants.lightSpec(light));
        Thread.sleep(1000);
      }

      System.out.println("****** PLAYING BLINK SPECS *******");

      for (Light light : Light.values()) {
        driver.send(SpecConstants.blinkSpec(light));
        Thread.sleep(5000);
      }

      driver.send(StandardSpecs.OFF);
    }
  }
}
