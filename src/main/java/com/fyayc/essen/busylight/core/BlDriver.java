package com.fyayc.essen.busylight.core;

import java.io.Closeable;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

public class BlDriver implements Closeable {
  private HidDevice physicalDevice;

  /** @return */
  public static BlDriver tryAndAcquire() {
    return DriverHelper.INSTANCE;
  }

  private BlDriver() {
    HidServices hidServices = HidManager.getHidServices();
    for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
      if (hidDevice.getProduct().contains(BlConstants.PRODUCT_STRING)) {
        physicalDevice = hidDevice;
      }
    }
    hidServices.shutdown();
    if (physicalDevice == null) {
      throw new UnsupportedOperationException(
          "Unable to open the device, is the device connected?");
    }
    if (!physicalDevice.open()) {
      throw new UnsupportedOperationException(
          "Unable to open the device, is it already opened by some other process?");
    }
  }

  public void sendBuffer(byte[] buffer) {
    physicalDevice.write(buffer, buffer.length, (byte) 0);
  }

  @Override
  public void close() {
    physicalDevice.close();
  }

  public boolean isOpen() {
    return physicalDevice.isOpen();
  }

  private static class DriverHelper {
    private static final BlDriver INSTANCE = new BlDriver();
  }
}
