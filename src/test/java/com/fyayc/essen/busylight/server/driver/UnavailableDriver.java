package com.fyayc.essen.busylight.server.driver;

import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;

public class UnavailableDriver implements BusylightDriverSpec {

  @Override
  public void sendToDriver(ProtocolSpec protocolSpec) {
    throw new UnsupportedOperationException("Busylight device is offline");
  }

  @Override
  public void close() {
  }
}
