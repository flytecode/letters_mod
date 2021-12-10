package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
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
                SavedDataClass saver = SavedDataClass.forWorld(world);
                System.out.println("saver.data: " + saver.data);
                if (saver.data.contains("address")) {
                  String currentAddress = saver.data.getString("address");

                  System.out.println("currentAddress: " + currentAddress);

                  if (!currentAddress.equals(oldAddress)) {
                    // incorrect oldaddr, tell the user and don't do anything else
                    CommandUtils.sendMessage(commandContext,
                        "oldaddr does not match current worldAddress.");
                  } else {
                    try {
                      // set up apiclient, send a post request to server with old addr
                      ApiClient poster = new ApiClient("https://serene-bayou-00030.herokuapp.com/changeaddr"); // http://localhost:4567/changeaddr
                      JsonObject reqBody = new JsonObject();
                      reqBody.addProperty("oldaddr", oldAddress);
                      JsonObject reqResult = poster.postFromJson(reqBody);

                      // get back the new addr and print it out
                      String newAddr = reqResult.get("newaddr").getAsString();
                      CommandUtils.sendMessage(commandContext,
                          newAddr + " being set as worldAddress");

                      // save the nbt data in world data
                      saver.data.putString("address", newAddr);
                      saver.markDirty();
                      return 1;
                    } catch (IOException e) {
                        CommandUtils.sendMessage(commandContext,
                            "Error connecting to server, please try again.");
                        e.printStackTrace();
                        return 0;
                    }
                  }
                } else {
                  // if a mailbox has not been created for this world yet
                  CommandUtils.sendMessage(commandContext,
                      "worldAddress has not yet been set, please create a mailbox for this world.");
                }
              }
              return 0;
            })
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter this world's old address."));  // blank: didn't match a literal or the custommessage argument

    dispatcher.register(changeaddrCommand);
  }
}


