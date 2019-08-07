package com.fyayc.essen.busylight.core;

import com.fyayc.essen.busylight.core.protocol.ProtocolConstants;
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
      if (hidDevice.getVendorId() == ProtocolConstants.SUPPORTED_VENDOR_ID
          && isValidProductId(hidDevice.getProductId())) {
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

  private boolean isValidProductId(short productId) {
    for (short supportedProductId : ProtocolConstants.SUPPORTED_PRODUCT_IDS) {
      if (productId == supportedProductId) return true;
    }
    return false;
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
