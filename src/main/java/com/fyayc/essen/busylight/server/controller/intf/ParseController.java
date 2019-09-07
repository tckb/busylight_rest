package com.fyayc.essen.busylight.server.controller.intf;

import com.fyayc.essen.busylight.server.controller.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ParseController {

  @GetMapping(value = "/parse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Operation(
      summary = "Sets the color of the busylight device",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "successfully adjusted the color of the device",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "500", description = "Server-side issue")
      })
  Response parse(
      @NotEmpty @RequestParam(required = false, defaultValue = "set status to free") /**/
          String text);
}
