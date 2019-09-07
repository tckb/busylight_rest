package com.fyayc.essen.busylight.server.controller.intf;

import com.fyayc.essen.busylight.server.controller.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface StatusController {

  @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Operation(
      summary = "Sets the current status",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Status successfully sent to device",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "500", description = "Server-side issue")
      })
  Response status(
      @Parameter(
          description = "Status to send",
          required = true,
          in = ParameterIn.PATH,
          examples = {
              @ExampleObject(value = "free", name = "Available"),
              @ExampleObject(value = "busy", name = "Busy"),
              @ExampleObject(value = "oncall", name = "Call In Progress"),
              @ExampleObject(value = "dnd", name = "Do Not disturb"),
              @ExampleObject(value = "off", name = "Turn off"),
              @ExampleObject(value = "away", name = "Away From Desk"),
              @ExampleObject(value = "notify", name = "Notification Alert")
          })
      @PathVariable
          String status);
}
