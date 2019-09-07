package com.fyayc.essen.busylight.server.driver;

import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StandardSpecs;

public interface BusylightDriverSpec {

  void sendToDriver(ProtocolSpec protocolSpec);

  default void sendToDriver(StandardSpecs standardSpecs) {
    sendToDriver(standardSpecs.protocol);
  }

  void close();
}
