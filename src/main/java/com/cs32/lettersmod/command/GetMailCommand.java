package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.server.ServerWorld;


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
        .executes(commandContext -> {
          ServerWorld world = commandContext.getSource().getWorld();
          if (!world.isRemote()) {
            SavedDataClass addressSaver = new SavedDataClass("worldAddress");
            SavedDataClass saver = addressSaver.forWorld(world);

            // TODO check how much space is left in the local mailbox right now to use as param for getmail
            int mailboxSlots = 18;

            if (mailboxSlots == 0) {
              // there is no space left in local mailbox
              CommandUtils.sendMessage(commandContext, "Empty your mailbox!");
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

              // set up apiclient, send a post request to server with old addr
              ApiClient poster = new ApiClient("http://localhost:4567/getmail");
              JsonObject reqBody = new JsonObject();
              reqBody.addProperty("address", currentAddress);
              reqBody.addProperty("numParcels", mailboxSlots);
              JsonObject reqResult = poster.postFromJson(reqBody);


              // get back the returned parcels
              JsonArray parcels = reqResult.getAsJsonArray("parcels");

              // TODO add returned parcels to mailbox
              CommandUtils.sendMessage(commandContext, parcels.size() + " parcels retrieved");

              saver.data = asdf;
              saver.markDirty();
              return 1;
            }
          }
          return 0;
        });

    dispatcher.register(getmailCommand);
  }

}


