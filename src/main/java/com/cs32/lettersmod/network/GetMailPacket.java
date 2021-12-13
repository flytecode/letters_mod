package com.cs32.lettersmod.network;

import java.util.function.Supplier;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class GetMailPacket {
  private int mailboxSlots;

  public GetMailPacket() {
  }

  public GetMailPacket(int mailboxSlots) {
    this.mailboxSlots = mailboxSlots;
  }

  public GetMailPacket(PacketBuffer buf) {
    this.mailboxSlots = buf.readInt();
  }

  public void toBytes(PacketBuffer buf) {
    buf.writeInt(mailboxSlots);
  }

  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {

      World world = ctx.get().getSender().world;
      if (!world.isRemote()) {
        SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) world);

        String res = MailCourier.getMail(saver, mailboxSlots);
        System.out.println("GetMailPacket: " + res);
      }

    });
    ctx.get().setPacketHandled(true);
  }
}