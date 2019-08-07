package com.fyayc.essen.busylight;

import com.fyayc.essen.busylight.core.Driver;
import com.fyayc.essen.busylight.core.protocol.SpecConstants.StatusSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** the thread responsible for sending "Keep-Alive" protocol data to the device */
public class BusylightKeepAliveThread extends Thread {
  protected static final Logger logger = LogManager.getLogger(BusylightKeepAliveThread.class);
  private final long keepAliveFreq;
  private Driver busylightDriver = null;
  private boolean interruped = false;

  public BusylightKeepAliveThread(long frqeuency) {
    super("busylight-keepalive-thread");
    this.setDaemon(true);
    this.keepAliveFreq = frqeuency;
    logger.info("Starting the keep alive thread with freq {} mills", keepAliveFreq);
    busylightDriver = Driver.tryAndAcquire();
  }

  @Override
  public void run() {
    while (!interruped) {
      try {
        busylightDriver.send(StatusSpec.KEEP_ALIVE);
        Thread.sleep(keepAliveFreq);
      } catch (InterruptedException e) {
        logger.error("Busylight keep-alive thread interrupted ", e);
        interruped = true;
      }
    }
    terminate();
  }

  private void terminate() {
    logger.error("Terminating the thread");
    busylightDriver.close();
  }
}
