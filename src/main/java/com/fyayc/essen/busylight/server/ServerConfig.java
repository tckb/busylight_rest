package com.fyayc.essen.busylight.server;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.server.driver.BusylightDriver;
import com.fyayc.essen.busylight.server.driver.BusylightDriverSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

  @Bean
  public BusylightDriverSpec driver() {
    return new BusylightDriver(Driver.tryAndAcquire());
  }
}
