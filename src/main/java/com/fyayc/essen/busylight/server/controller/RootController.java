package com.fyayc.essen.busylight.server.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

  @GetMapping("/")
  @Hidden
  public String doRedirect() {
    return "redirect:/api/console";
  }
}
