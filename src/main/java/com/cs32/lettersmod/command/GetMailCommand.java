package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.nbt.CollectionNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.nbt.JsonToNBT;

import java.io.IOException;
import java.util.LinkedList;


/**
 * Adds a command allowing the player to change the address of this world.
 * They must input the old address of the world as an argument to confirm.
 * The mail server will update the address table to map this world's (hidden) true_key to a new randomly generated
 * address, which is then sent back to the user and printed, as well as saved in this world's global address field.
 */
public class GetMailCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> getmailCommand
        = Commands.literal("getmail")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .then(Commands.argument("mailboxSlots", MessageArgument.message())
                .executes(commandContext -> {
                  ServerWorld world = commandContext.getSource().getWorld();
                  if (!world.isRemote()) {
                    SavedDataClass saver = SavedDataClass.forWorld(world);

                    // parse the user argument for the number of mailbox slots that are free
                    ITextComponent iTextComponent =
                        MessageArgument.getMessage(commandContext, "mailboxSlots");
                    int mailboxSlots = Integer.parseInt(iTextComponent.getString());


                    if (mailboxSlots == 0) {
                      // there is no space left in local mailbox
                      CommandUtils.sendMessage(commandContext, "No space - empty your mailbox!");
                      return 0;
                    }

                    if (!saver.data.contains("address")) {
                      // if a mailbox has not yet been created for this world
                      CommandUtils.sendMessage(commandContext,
                          "worldAddress has not been set");
                      return 0;
                    } else {
                      // get the current address
                      String currentAddress = saver.data.getString("address");

                      try {
                        // set up apiclient, send a post request to server with old addr
                        ApiClient poster = new ApiClient("http://localhost:4567/getmail");
                        JsonObject reqBody = new JsonObject();
                        reqBody.addProperty("address", currentAddress);
                        reqBody.addProperty("maxNumParcels", mailboxSlots);
                        JsonObject reqResult = poster.postFromJson(reqBody);

                        // get back the returned parcels
                        JsonArray parcels = reqResult.getAsJsonArray("parcels");

                        // get the parcels currently in the mailbox
                        ListNBT parcelList = (ListNBT) saver.data.get("parcelList");
                        if (parcelList == null) {
                          parcelList = new ListNBT();
                        }

                        // go through and add returned parcels to list
                        for (JsonElement parcelJson : parcels) {
                          CompoundNBT parcel = JsonToNBT.getTagFromJson(parcelJson.getAsString());
                          parcelList.add(parcel);
                        }

                        CommandUtils.sendMessage(commandContext, parcels.size() + " parcels retrieved");
                        saver.data.put("parcelList", parcelList);
                        saver.markDirty();
                        return 1;
                      } catch (IOException e) {
                        CommandUtils.sendMessage(commandContext,
                            "Error connecting to server, please try again.");
                        e.printStackTrace();
                        return 0;
                      }
                    }
                  }
                  return 0;
                })
        )
        .executes(commandContext -> CommandUtils.sendMessage(commandContext,
            "Please enter the number of free mail slots to retrieve for."));

    dispatcher.register(getmailCommand);
  }

}


