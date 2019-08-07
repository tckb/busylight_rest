package com.fyayc.essen.busylight.core.protocol.bytes;

import com.tomgibara.bits.BitStore;
import com.tomgibara.bits.Bits;

public class Command extends StepByte {
  public static final Command START_BOOT_LOADER = new Command("01000000", "boot_loader");
  public static final Command RESET_DEVICE = new Command("00100000", "reset");

  private Command(String bitString, String stepByteName) {
    super(stepByteName, bitString);
  }

  public static Command nextStep(int step) {
    Command nextStepByte = new Command("00010000", "next_step");
    if (step >= 0 && step < 8) {
      BitStore stepStore = Bits.store(8);
      stepStore.setBits(0, step, 3);
      nextStepByte.bitStore().or().withStore(stepStore);
    }
    return nextStepByte;
  }

  public static Command keepAliveSignal(int timeout) {
    Command keepAliveByte = new Command("10000000", "keep_live");
    if (timeout >= 0 && timeout <= 15) {
      keepAliveByte.bitStore().setBits(0, timeout, 4);
    }
    return keepAliveByte;
  }


}
