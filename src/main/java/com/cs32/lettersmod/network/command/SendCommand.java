package com.cs32.lettersmod.network.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.network.saveddata.SavedDataClass;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.server.ServerWorld;

import java.io.IOException;

/**
 * Adds a command allowing the player to change the address of this world.
 * They must input the old address of the world as an argument to confirm.
 * The mail server will update the address table to map this world's (hidden) true_key to a new randomly generated
 * address, which is then sent back to the user and printed, as well as saved in this world's global address field.
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
//              String recipient = MessageArgument.getMessage(commandContext, "recipient").getString();
                          String recipient = StringArgumentType.getString(commandContext, "recipient");
                          System.out.println("recipient: " + recipient);
                          String parcelString =
                              StringArgumentType.getString(commandContext, "parcelString");
                          System.out.println("parcelString: " + parcelString);


                          // code that loads up value for current address and makes sure it is equal to inputted oldaddr
                          ServerWorld world = commandContext.getSource().getWorld();
                          if (!world.isRemote()) {
                            SavedDataClass saver = SavedDataClass.forWorld(world);
                            if (saver.data.contains("address")) {
                              String currentAddress = saver.data.getString("address");

                              try {
                                // set up apiclient, send a post request to server with old addr
                                ApiClient poster = new ApiClient("http://localhost:4567/send");
                                JsonObject reqBody = new JsonObject();
                                reqBody.addProperty("sender", currentAddress);
                                reqBody.addProperty("recipient", parcelString);
                                reqBody.addProperty("parcelString", parcelString);
                                JsonObject reqResult = poster.postFromJson(reqBody);

                                // get back the new addr and print it out
                                String receivedParcel = reqResult.get("receivedParcel").getAsString();
                                CommandUtils.sendMessage(commandContext,
                                    receivedParcel + " was received by mailroom database");

                                // return 0 if receivedParcel was null, which means we don't want to delete within the tileentity
                                if (receivedParcel == null) {
                                  return 0;
                                } else {
                                  return 1;
                                }
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
                          return 0;
                        })
                )
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter a recipient and parcelString."));  // blank: didn't match a literal or the custommessage argument

    dispatcher.register(sendCommand);
  }
}


