package com.cs32.lettersmod.network;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;

//TODO DELETE
public class LettersPacketHandler implements INetHandler {
  //this is the forge stuff
//  private static final String PROTOCOL_VERSION = "1";
//  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
//      new ResourceLocation("lettersmod", "main"),
//      () -> PROTOCOL_VERSION,
//      PROTOCOL_VERSION::equals,
//      PROTOCOL_VERSION::equals
//  );
//
//  public static void processSendParcel(MyMessage msg, Supplier<NetworkEvent.Context> ctx) {
//    ctx.get().enqueueWork(() -> {
//      // Work that needs to be thread-safe (most work)
//      ServerPlayerEntity sender = ctx.get().getSender(); // the client that sent this packet
//      // Do stuff
//    });
//    ctx.get().setPacketHandled(true);
//  }

  // this is me trying to copy vanilla stuff
  public void processSendParcel(SendParcelPacket p) {
    System.out.println("parcel: " + p);
  }

  @Override
  public void onDisconnect(ITextComponent reason) {
    System.out.println(reason);
  }

  @Override
  public NetworkManager getNetworkManager() {
    return null;
  }


}
