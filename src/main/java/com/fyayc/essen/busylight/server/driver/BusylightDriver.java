package com.fyayc.essen.busylight.server.driver;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;

public class BusylightDriver implements BusylightDriverSpec {

  private Driver bldrive;

  public BusylightDriver(Driver driver) {
    bldrive = driver;
  }

  @Override
  public void sendToDriver(ProtocolSpec protocolSpec) {
    bldrive.send(protocolSpec);
  }

  @Override
  public void close() {
    bldrive.close();
  }
}
