package com.fyayc.essen.busylight.server.controller;

import com.fyayc.essen.busylight.server.controller.intf.ToneController;
import com.fyayc.essen.busylight.server.controller.models.Response;
import com.fyayc.essen.busylight.server.service.DriverService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToneControllerImpl implements ToneController {

  private final DriverService driverService;

  public ToneControllerImpl(DriverService driverService) {
    this.driverService = driverService;
  }

  public Response tone(String toneName, Integer volume, Integer duration) {
    if (duration < 0) {
      duration = -1;
    }
    driverService.tone(toneName.toUpperCase(), volume, duration * 1000);
    return new Response("updated");
  }
}
