package com.cs32.lettersmod.command;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.server.ServerWorld;


/**
 * Adds a command allowing the player to initialize the address of this world.
 */
public class InitAddrCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> initaddrCommand
        = Commands.literal("initaddr")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .executes(commandContext -> {
          ServerWorld world = commandContext.getSource().getWorld();
          if (!world.isRemote()) {
            // get worldsaveddata for this world
            SavedDataClass saver = SavedDataClass.forWorld(world);

            // call initAddr and print out result string
            String resultString = MailCourier.initAddr(saver);
            CommandUtils.sendMessage(commandContext, resultString);
          }
          return 0;
        });

    dispatcher.register(initaddrCommand);
  }

}


