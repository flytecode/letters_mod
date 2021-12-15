package com.cs32.lettersmod.network;

import java.util.function.Supplier;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class SendParcelPacket {
  private String address;
  private String parcelString;

  public SendParcelPacket() {
  }

  public SendParcelPacket(String address, String parcelString) {
    this.address = address;
    this.parcelString = parcelString;
  }

  public SendParcelPacket(PacketBuffer buf) {
    address = buf.readString(500); //TODO make this a macro
    parcelString = buf.readString(500);
  }

  public void toBytes(PacketBuffer buf) {
    buf.writeString(address);
    buf.writeString(parcelString);
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {

      World world = ctx.get().getSender().world;
      if (!world.isRemote()) {
        SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) world);

        String res = MailCourier.send(saver, address, parcelString);
        System.out.println("sendparcelpacket: " + res);
      }

    });
    ctx.get().setPacketHandled(true);
  }
}