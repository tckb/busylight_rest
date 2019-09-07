package com.fyayc.essen.busylight.server.service;

public interface DriverService {

  void setStatus(String status);

  void setColor(String hex, String type);

  void tone(String tone, int volume, long toneDuration);
}
