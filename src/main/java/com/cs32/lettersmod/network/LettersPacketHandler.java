package com.cs32.lettersmod.network;

import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


public class LettersPacketHandler {
  private static final String PROTOCOL_VERSION = "1";
  public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
      new ResourceLocation("lettersmod", "main"),
      () -> PROTOCOL_VERSION,
      PROTOCOL_VERSION::equals,
      PROTOCOL_VERSION::equals
  ).registerMessage();



//  public static void handle(MyMessage msg, Supplier<NetworkEvent.Context> ctx) {
//    ctx.get().enqueueWork(() -> {
//      // Work that needs to be thread-safe (most work)
//      ServerPlayerEntity sender = ctx.get().getSender(); // the client that sent this packet
//      // Do stuff
//    });
//    ctx.get().setPacketHandled(true);
//  }
}
