package com.fyayc.essen.busylight.server.controller;

import com.fyayc.essen.busylight.server.controller.models.Response;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @Hidden
  public Response badRequest(IllegalArgumentException ex) {
    return new Response(ex.getMessage());
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @Hidden
  public Response otherException(Throwable ex) {
    return new Response(ex.getMessage());
  }
}
