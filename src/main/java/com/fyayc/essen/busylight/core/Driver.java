package com.fyayc.essen.busylight.core;

import com.fyayc.essen.busylight.core.protocol.ProtocolSpec;
import com.fyayc.essen.busylight.core.protocol.SpecConstants;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StandardSpecs;
import com.tomgibara.bits.Bits;
import java.io.Closeable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

/** the driver class for finding, connecting and communicating with the device */
public class Driver implements Closeable {
  protected static final Logger logger = LogManager.getLogger(Driver.class);
  protected static final int MAX_CONNECT_RETRIES = 5;
  private HidDevice physicalDevice;
  private KeepAliveThread keepAliveThread;
  private boolean isRetriable = true;

  private Driver() {
    logger.trace("Searching for compatible  devices");

    HidServices hidServices = HidManager.getHidServices();
    for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
      logger.trace(
          "Found {}: 0x{} / 0x{} ",
          hidDevice.getProduct(),
          Bits.toStore(hidDevice.getProductId()).toString(16),
          Bits.toStore(hidDevice.getVendorId()).toString(16));

      if (hidDevice.getVendorId() == SpecConstants.SUPPORTED_VENDOR_ID
          && isValidProductId(hidDevice.getProductId())) {
        physicalDevice = hidDevice;
        logger.info(
            "Found a compatible device {}: 0x{} / 0x{}",
            hidDevice.getProduct(),
            Bits.toStore(hidDevice.getProductId()).toString(16),
            Bits.toStore(hidDevice.getVendorId()).toString(16));
        break;
      }
    }
    hidServices.shutdown();
    if (physicalDevice == null) {
      throw new UnsupportedOperationException(
          "Unable to open the device, is the device connected?");
    }
    if (!tryAndOpen()) {
      throw new UnsupportedOperationException(
          "Unable to open the device, is it already opened by some other process?");
    }

    keepAliveThread = new KeepAliveThread(10_000);
    keepAliveThread.start();
  }

  public static Driver tryAndAcquire() {
    logger.info("Trying to connect to the device");
    return DriverHelper.INSTANCE;
  }

  private boolean isValidProductId(short productId) {
    for (short supportedProductId : SpecConstants.SUPPORTED_PRODUCT_IDS) {
      if (productId == supportedProductId) return true;
    }
    return false;
  }

  /**
   * sends the given byte data to the device
   *
   * @param buffer the buffer
   */
  public void sendRawBuffer(byte[] buffer) {
    if (tryAndOpen()) {
      physicalDevice.write(buffer, buffer.length, (byte) 0);
      logger.trace("Sent buffer data of {} bytes", buffer.length);
    }
  }

  private boolean tryAndOpen() {
    if (isOpen()) {
      logger.trace("device is already opened");
      return true;
    } else {
      logger.info("device connection lost, retrying to open");
      if (isRetriable) {
        try {
          int tryIx = 0;
          while (tryIx < MAX_CONNECT_RETRIES) {

            if (!physicalDevice.open()) {
              logger.trace("retry# {}", tryIx);
            } else {
              logger.info("Device is now opened!");
              return true;
            }
            tryIx++;
            Thread.sleep(2000);
          }
        } catch (InterruptedException e) {
          logger.error("Unable to sleep, interrupted", e);
        }
        logger.warn("Unable to open the device");
        return false;
      } else {
        logger.info("Can not retry, isRetriable = false");
        return false;
      }
    }
  }

  /**
   * sends the given protocol data to the device
   *
   * @param buffer the buffer
   */
  public void send(ProtocolSpec buffer) {
    logger.trace("Sending buffer: \n{} ", buffer.dumpHex());
    sendRawBuffer(buffer.toBytes());
  }

  /**
   * sends the given protocol data to the device
   *
   * @param buffer the buffer
   */
  public void send(StandardSpecs buffer) {
    logger.debug("Sending {}", buffer);
    send(buffer.protocol);
  }

  @Override
  public void close() {
    logger.info("Closing the device connection");
    keepAliveThread.interrupt = true;
    physicalDevice.close();
  }

  /**
   * checks if the underlying device is still open
   *
   * @return true - if its still open
   */
  public boolean isOpen() {
    return physicalDevice.isOpen();
  }

  private static class DriverHelper {
    private static final Driver INSTANCE = new Driver();
  }

  private class KeepAliveThread extends Thread {
    private final Logger logger = LogManager.getLogger(KeepAliveThread.class);
    private final long keepAliveFreq;
    private boolean interrupt = false;

    private KeepAliveThread(long frqeuency) {
      super("busylight-keepalive-thread");
      this.setDaemon(true);
      this.keepAliveFreq = frqeuency;
      logger.info("Starting the keep alive thread with freq {} mills", keepAliveFreq);
    }

    @Override
    public void run() {
      while (!interrupt) {
        try {
          send(StandardSpecs.KEEP_ALIVE);
          Thread.sleep(keepAliveFreq);
        } catch (InterruptedException e) {
          logger.error("Busylight keep-alive thread interrupted ", e);
        }
      }
    }
  }
}
