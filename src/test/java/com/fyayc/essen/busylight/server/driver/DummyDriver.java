package com.fyayc.essen.busylight.server.driver;

import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;

public class DummyDriver implements BusylightDriverSpec {

  @Override
  public void sendToDriver(ProtocolSpec protocolSpec) {
    // consume it to blackhole
  }

  @Override
  public void close() {
    // consume it to blackhole
  }
}
