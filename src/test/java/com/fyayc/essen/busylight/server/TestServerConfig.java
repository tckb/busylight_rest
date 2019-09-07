package com.fyayc.essen.busylight.server;

import com.fyayc.essen.busylight.server.driver.BusylightDriverSpec;
import com.fyayc.essen.busylight.server.driver.DummyDriver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestServerConfig {

  @Bean
  public BusylightDriverSpec driver() {
    return new DummyDriver();
  }
}
