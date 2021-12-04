package com.cs32.lettersmod;

import com.cs32.lettersmod.command.ChangeAddrCommand;
import com.cs32.lettersmod.command.InitAddrCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.cs32.lettersmod.command.GetMailCommand;

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
  }
}
