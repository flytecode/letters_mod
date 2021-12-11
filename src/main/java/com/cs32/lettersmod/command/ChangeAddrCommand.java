package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;

import java.io.IOException;

/**
 * Adds a command allowing the player to change the address of this world.
 * They must input the old address of the world as an argument to confirm.
 * The mail server will update the address table to map this world's (hidden) true_key to a new randomly generated
 * address, which is then sent back to the user and printed, as well as saved in this world's global address field.
 */
public class ChangeAddrCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> changeaddrCommand
        = Commands.literal("changeaddr")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .then(Commands.argument("oldaddr",
                MessageArgument.message())  // see also StringArgumentType; .word() or .string() or .greedystring()
            .executes(commandContext -> {
              // parse the user argument for the old/current address
              ITextComponent iTextComponent = MessageArgument.getMessage(commandContext, "oldaddr");
              String oldAddress = iTextComponent.getString();

              // code that loads up value for current address and makes sure it is equal to inputted oldaddr
              ServerWorld world = commandContext.getSource().getWorld();
              if (!world.isRemote()) {
                // get data for world
                SavedDataClass saver = SavedDataClass.forWorld(world);

                // call change addr functionality
                String resultString = MailCourier.changeAddr(saver, oldAddress);
                CommandUtils.sendMessage(commandContext, resultString);
              }
              return 0;
            })
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter this world's old address."));  // blank: didn't match a literal or the custommessage argument

    dispatcher.register(changeaddrCommand);
  }
}


