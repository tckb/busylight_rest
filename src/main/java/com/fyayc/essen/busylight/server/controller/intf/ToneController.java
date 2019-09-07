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

public interface ToneController {

  @GetMapping(value = "/tone/{toneName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Operation(
      summary = "Plays the specific tone on the busylight device",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "successfully set the tone",
              content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "500", description = "Server-side issue")
      })
  Response tone(
      @Parameter(
          description = "Tone name",
          required = true,
          in = ParameterIn.PATH,
          examples = {
              @ExampleObject(value = "OPENOFFICE", name = "OPENOFFICE", summary = "OPENOFFICE"),
              @ExampleObject(value = "QUIET", name = "QUIET", summary = "QUIET"),
              @ExampleObject(value = "FUNKY", name = "FUNKY", summary = "FUNKY"),
              @ExampleObject(
                  value = "FAIRY_TALE",
                  name = "FAIRY TALE",
                  summary = "FA" + "IRY TALE"),
              @ExampleObject(
                  value = "KUANDO_TRAIN",
                  name = "KUANDO_TRAIN",
                  summary = "KUANDO TRAIN"),
              @ExampleObject(
                  value = "TELEPHONIC_NORDIC",
                  name = "TELEPHONIC_NORDIC",
                  summary = "TELEPHONIC NORDIC"),
              @ExampleObject(
                  value = "TELEPHONIC_ORIGINAL",
                  name = "TELEPHONIC_ORIGINAL",
                  summary = "TELEPHONIC ORIGINAL"),
              @ExampleObject(
                  value = "TELEPHONIC_PICKMEUP",
                  name = "TELEPHONIC_PICKMEUP",
                  summary = "TELEPHONIC PICKMEUP"),
              @ExampleObject(value = "IM_1", name = "IM_1", summary = "Instant Message 1"),
              @ExampleObject(value = "IM_2", name = "IM_2", summary = "Instant Message 2"),
              @ExampleObject(value = "IM_3", name = "IM_3", summary = "Instant Message 3"),
          })
      @PathVariable
      @NotEmpty(message = "Color can not be empty")
          String toneName,
      @Parameter(
          description = "Sets the volume of the tone",
          required = false,
          in = ParameterIn.QUERY,
          examples = {
              @ExampleObject(value = "1", name = "Lowest volume", summary = "Minimum Volume"),
              @ExampleObject(value = "7", name = "Loudest volume", summary = "Maximum Volume")
          })
      @NotEmpty
      @RequestParam(required = false, defaultValue = "4")
          Integer volume,
      @Parameter(
          description = "Number of seconds to play the tone, default = 5 seconds",
          required = false,
          in = ParameterIn.QUERY,
          examples = {
              @ExampleObject(value = "-1", name = "negative duration", summary = "Non Stop"),
              @ExampleObject(value = "5", name = "5 seconds", summary = "Default duration"),
          })
      @NotEmpty
      @RequestParam(required = false, defaultValue = "5")
          Integer duration);
}
