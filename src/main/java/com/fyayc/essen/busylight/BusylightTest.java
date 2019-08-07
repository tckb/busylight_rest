package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.SpecConstants;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.Light;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    try (Driver driver = Driver.tryAndAcquire()) {
      driver.send(SpecConstants.lightSpec(Light.BUSY));
      Thread.sleep(5_000);
      driver.send(SpecConstants.blinkSpec(Light.YELLOW));
    }
  }
}
