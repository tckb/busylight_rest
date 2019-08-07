package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.ProtocolConstants.Specs;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    try (Driver driver = Driver.tryAndAcquire()) {
      driver.send(Specs.FREE.protocol);
      // send keep alive signal
      while (true) {
        Thread.sleep(10_000);
        driver.send(Specs.KEEP_ALIVE.protocol);
      }
    }
  }
}