package com.fyayc.essen.busylight.core.protocol.bytes;

import com.google.common.base.MoreObjects;
import com.tomgibara.bits.BitStore;
import com.tomgibara.bits.Bits;

public class StepByte {
  public static final StepByte EMPTY = new StepByte("empty_byte", "00000000");
  public static final int BIT_LENGTH = 8;
  public static final int BYTE_LENGTH = 1;
  private final String stepByteName;
  private final BitStore internalByte;

  private StepByte(String stepByteName, BitStore bitstore) {
    this.internalByte = bitstore;
    this.stepByteName = stepByteName;
  }

  public StepByte(String stepByteName) {
    this.internalByte = Bits.store(BIT_LENGTH);
    this.stepByteName = stepByteName;
  }

  public StepByte(String stepByteName, String bitString) {
    this.stepByteName = stepByteName;
    this.internalByte = Bits.toStore(bitString);
  }

  public static StepByte ofUnsignedInt(String stepByteName, int i) {
    if (i < 0 || i > 255) {
      throw new IllegalArgumentException("expecting value between 0 and 255");
    }
    BitStore uByte = Bits.store(8);
    uByte.setBits(0, i, 8);
    return new StepByte(stepByteName, uByte);
  }

  protected BitStore bitStore() {
    return internalByte;
  }

  public byte getCurrentByte() {
    return internalByte.asNumber().byteValue();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", stepByteName)
        .add("bits", Bits.toString(internalByte))
        .add("hex", hex())
        .toString();
  }

  public String hex() {
    return internalByte.toString(16).toUpperCase();
  }

  public int toInt() {
    return Integer.parseUnsignedInt(Bits.toString(internalByte), 2);
  }
}
