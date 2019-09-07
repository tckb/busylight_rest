package com.fyayc.essen.busylight.server.controller.models;

public class Response {
  private final String message;

  public Response(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
