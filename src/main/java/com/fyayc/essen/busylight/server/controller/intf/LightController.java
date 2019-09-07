package com.fyayc.essen.busylight.server.controller.intf;

import com.fyayc.essen.busylight.server.controller.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface LightController {

  @GetMapping(value = "/light/{hex}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
  Response light(
      @Parameter(
          description = "Hex code of the color to set, preceding with '0x'",
          required = true,
          in = ParameterIn.PATH,
          examples = {
              @ExampleObject(value = "0xff0000", name = "Red"),
          })
      @PathVariable
      @NotEmpty(message = "Color can not be empty")
          String hex,
      @Parameter(
          description = "Sets the type of light",
          required = false,
          in = ParameterIn.QUERY,
          examples = {
              @ExampleObject(value = "none", name = "Normal light", summary = "Only light"),
              @ExampleObject(
                  value = "blink",
                  name = "Blinking light with the desired color",
                  summary = "Blink light"),
              @ExampleObject(
                  value = "pulse",
                  name = "Pulsating light with the desired color",
                  summary = "Pulse light"),
          })
      @NotEmpty
      @RequestParam(required = false, defaultValue = "none")
          String type);
}
