package com.fyayc.essen.busylight.server.controller;

import com.fyayc.essen.busylight.server.controller.intf.StatusController;
import com.fyayc.essen.busylight.server.controller.models.Response;
import com.fyayc.essen.busylight.server.service.DriverService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusControllerImpl implements StatusController {

  private final DriverService driverService;

  public StatusControllerImpl(DriverService driverService) {
    this.driverService = driverService;
  }

  public Response status(String status) {
    driverService.setStatus(status);
    return new Response("Setting status as " + status);
  }
}
