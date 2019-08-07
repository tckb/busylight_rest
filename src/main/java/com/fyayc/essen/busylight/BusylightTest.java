package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StatusSpec;

public class BusylightTest {
  public static void main(String[] args) throws InterruptedException {
    BusylightKeepAliveThread busylight = new BusylightKeepAliveThread(5_000);
    busylight.start();
    Driver.tryAndAcquire().send(StatusSpec.DO_NOT_DISTURB);
    Thread.sleep(60_000);
  }
}
