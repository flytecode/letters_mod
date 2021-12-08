package com.cs32.lettersmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Register our commands when the server starts up.
 * Don't forget to register this class on the MinecraftForge.EVENT_BUS.
 */
public class RegisterCommandEvent {
  @SubscribeEvent
  public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
    GetMailCommand.register(commandDispatcher);
    ChangeAddrCommand.register(commandDispatcher);
    InitAddrCommand.register(commandDispatcher);
    SendCommand.register(commandDispatcher);
  }
}
