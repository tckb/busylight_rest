package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.BlDriver;
import com.fyayc.essen.busylight.core.protocol.ProtocolConstants.Specs;
import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    ProtocolSpec protocol;
    try (BlDriver driver = BlDriver.tryAndAcquire()) {
      protocol = Specs.DO_NOT_DISTURB.protocol;
      System.out.println(protocol.dumpHex());
      driver.sendBuffer(protocol.toBytes());

      // send keep alive signal
      while (true) {
        Thread.sleep(10_000);
        protocol = Specs.KEEP_ALIVE.protocol;
        System.out.println(protocol.dumpHex());
        driver.sendBuffer(protocol.toBytes());
      }
    }
  }
}
