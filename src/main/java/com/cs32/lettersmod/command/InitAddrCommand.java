package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.saveddata.Address;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

import java.io.IOException;


/**
 * Adds a command allowing the player to change the address of this world.
 * They must input the old address of the world as an argument to confirm.
 * The mail server will update the address table to map this world's (hidden) true_key to a new randomly generated
 * address, which is then sent back to the user and printed, as well as saved in this world's global address field.
 */
public class InitAddrCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    LiteralArgumentBuilder<CommandSource> initaddrCommand
        = Commands.literal("initaddr")
        .requires((commandSource) -> commandSource.hasPermissionLevel(1))
        .executes(commandContext -> {
          ServerWorld world = commandContext.getSource().getWorld();
          if (!world.isRemote()) {
            SavedDataClass saver = SavedDataClass.forWorld(world);
            System.out.println("saver.data: " + saver.data);
            if (!saver.data.contains("address")) {
              try {
                // set up apiclient, send a post request to server with old addr
                ApiClient poster = new ApiClient("http://localhost:4567/initaddr");
                JsonObject reqBody = new JsonObject();
                JsonObject reqResult = poster.postFromJson(reqBody);

                // get back the new addr and print it out
                String newAddr = reqResult.get("newaddr").getAsString();
                CommandUtils.sendMessage(commandContext, newAddr + " being set as worldAddress");

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
            } else {
              // if a mailbox has already been created for this world
              CommandUtils.sendMessage(commandContext,
                  "worldAddress has already been set");
            }
          }
          return 0;
        });

    dispatcher.register(initaddrCommand);
  }

}


