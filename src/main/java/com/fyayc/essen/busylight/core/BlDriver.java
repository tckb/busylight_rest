package com.fyayc.essen.busylight.core;

import static com.fyayc.essen.busylight.core.BlConstants.BUFFER_LENGTH;

import com.fyayc.essen.busylight.core.BlConstants.BufferPositions;
import java.io.Closeable;
import java.util.stream.IntStream;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

public class BlDriver implements Closeable {
  private HidDevice physicalDevice;

  /**
   *
   * @return
   */
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
    if (!physicalDevice.open()) {
      throw new UnsupportedOperationException(
          "Unable to open the device, is it already opened by some other process?");
    }
  }

  public void sendBuffer(int[] buffer) {
    if (buffer.length != BUFFER_LENGTH) {
      throw new UnsupportedOperationException(
          "BlDriverBuffer length mismatch; expecting " + BUFFER_LENGTH);
    }
    int[] checksumBytes = calcChecksum(buffer);
    byte[] dataToWrite = getBytes(buffer, checksumBytes);
    physicalDevice.write(dataToWrite, dataToWrite.length, (byte) 0);
  }

  public void sendBuffer(byte[] buffer) {
    physicalDevice.write(buffer, 64, (byte) 0);
  }

  public static byte[] getBytes(int[] data, int[] checksum) {
    byte[] bytes = new byte[data.length + checksum.length];
    for (int i = 0; i < data.length; i++) {
      bytes[i] = (byte) data[i];
    }
    bytes[BufferPositions.CHECKSUM_MSB.ix] = (byte) checksum[0];
    bytes[BufferPositions.CHECKSUM_LSB.ix] = (byte) checksum[1];
    return bytes;
  }

  public int[] calcChecksum(int[] dataToWrite) {
    int dataSum = IntStream.of(dataToWrite).sum();
    // MSB and LSB of datasum
    return new int[] {((dataSum >> 8) & 0xffff), dataSum % 256};
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
