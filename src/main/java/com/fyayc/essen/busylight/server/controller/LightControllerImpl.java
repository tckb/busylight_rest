package com.fyayc.essen.busylight.server.controller;

import com.fyayc.essen.busylight.server.controller.intf.LightController;
import com.fyayc.essen.busylight.server.controller.models.Response;
import com.fyayc.essen.busylight.server.service.DriverService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightControllerImpl implements LightController {

  private final DriverService driverService;

  public LightControllerImpl(DriverService driverService) {
    this.driverService = driverService;
  }

  public Response light(String hex, String type) {
    if (!hex.toUpperCase().startsWith("0X")) {
      throw new IllegalArgumentException(
          "Illegal hex format. Hex code must start with 0x[rr][gg][[bb]");
    }
    driverService.setColor(hex, type);
    return new Response("updated");
  }
}
