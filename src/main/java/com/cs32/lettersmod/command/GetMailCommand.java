package com.cs32.lettersmod.command;

import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;


/**
 * Adds a command allowing the player to get mail and store it in this world's saved data.
 */
public class GetMailCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> getmailCommand
        = Commands.literal("getmail")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .then(Commands.argument("mailboxSlots", MessageArgument.message())
            .executes(commandContext -> {
              ServerWorld world = commandContext.getSource().getWorld();
              if (!world.isRemote()) { // if we are on the server side
                // retrieve the saved data for this world
                SavedDataClass saver = SavedDataClass.forWorld(world);

                // parse the user argument for the number of mailbox slots that are free
                ITextComponent iTextComponent =
                    MessageArgument.getMessage(commandContext, "mailboxSlots");
                int mailboxSlots = Integer.parseInt(iTextComponent.getString());

                // call getMail method and send resultString to command line
                String resultString = MailCourier.getMail(saver, mailboxSlots);
                CommandUtils.sendMessage(commandContext, resultString);
              }
              return 0;
            })
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter the number of free mail slots to retrieve for."));

    dispatcher.register(getmailCommand);
  }

}


