package com.fyayc.essen.busylight.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info =
        @Info(
            title = "BusyLight REST Api",
            version = "0.5",
            description = "A Rest interface for controlling the busylight device",
            contact = @Contact(name = "Chandra", email = "tckb@me.com")))
@SpringBootApplication
public class ServerApp {

  public static void main(String[] args) {
    SpringApplication.run(ServerApp.class, args);
  }
}
