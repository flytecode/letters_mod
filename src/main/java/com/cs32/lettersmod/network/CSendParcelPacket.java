package com.cs32.lettersmod.network;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CSendParcelPacket implements IPacket<LettersPacketHandler> {
  private String address;
  private String parcelString;

  public CSendParcelPacket() {
  }

  public CSendParcelPacket(String address, String parcelString) {
    this.address = address;
    this.parcelString = parcelString;
  }

  /**
   * Reads the raw packet data from the data stream.
   */
  public void readPacketData(PacketBuffer buf) throws IOException {
    this.address = buf.readString(32767);
    this.parcelString = buf.readString(32767);
  }

  /**
   * Writes the raw packet data to the data stream.
   */
  public void writePacketData(PacketBuffer buf) throws IOException {
    buf.writeString(this.address);
    buf.writeString(this.parcelString);
  }

  /**
   * Passes this Packet on to the NetHandler for processing.
   */
  public void processPacket(LettersPacketHandler handler) {
    handler.processSendParcel(this);
  }

  public String getAddress() {
    return this.address;
  }
  public String getParcelString() { return this.parcelString;}
}