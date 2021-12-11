package com.cs32.lettersmod.command;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.server.ServerWorld;


/**
 * Adds a command allowing the player to send a parcel string to a recipient on the server.
 */
public class SendCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> sendCommand
        = Commands.literal("send")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .then(Commands.argument("recipient", StringArgumentType.word())
                .then(Commands.argument("parcelString", StringArgumentType.word())
                        .executes(commandContext -> {
                          // parse the user arguments
                          String recipient = StringArgumentType.getString(commandContext, "recipient");
                          String parcelString = StringArgumentType.getString(commandContext, "parcelString");

                          // code that loads up value for current address and makes sure it is equal to inputted oldaddr
                          ServerWorld world = commandContext.getSource().getWorld();
                          if (!world.isRemote()) {
                            // get world saved data
                            SavedDataClass saver = SavedDataClass.forWorld(world);

                            // call send functionality
                            String resultString = MailCourier.send(saver, recipient, parcelString);
                            CommandUtils.sendMessage(commandContext, resultString);
                          } else {
                            // if a mailbox has not been created for this world yet
                            CommandUtils.sendMessage(commandContext,
                                "worldAddress has not yet been set, please create a mailbox for this world.");
                          }
                          return 0;
                        })
                )
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter a recipient and parcelString."));  // blank: didn't match a literal or the custommessage argument

    dispatcher.register(sendCommand);
  }
}


