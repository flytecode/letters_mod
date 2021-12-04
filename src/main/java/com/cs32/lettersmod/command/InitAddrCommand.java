package com.cs32.lettersmod.command;

import com.cs32.lettersmod.ApiClient;
import com.cs32.lettersmod.saveddata.Address;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import static net.minecraft.util.math.MathHelper.clamp;

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
          // TODO is this client code? If so is this wrong, trying to access the server? this could cause weird bugs possibly.
          ServerWorld world = commandContext.getSource().getWorld();
          if (!world.isRemote()) {
            // set up apiclient, send a post request to server with old addr
            ApiClient poster = new ApiClient("http://localhost:4567/initaddr");
            JsonObject reqBody = new JsonObject();
            JsonObject reqResult = poster.postFromJson(reqBody);

            // get back the new addr and print it out
            String newAddr = reqResult.get("newaddr").getAsString();
            CommandUtils.sendMessage(commandContext, newAddr + " being set as worldAddress");

            // create new class to hold address and write to nbt, save it
            Address newAddressClass = new Address(newAddr);
            CompoundNBT newAddrNBT = new CompoundNBT();
            newAddressClass.writeToNBT(newAddrNBT);

            // save the nbt data in world data
            SavedDataClass addressSaver = new SavedDataClass("worldAddress");
            SavedDataClass saver = addressSaver.forWorld(world);
            saver.data = newAddrNBT;
            saver.markDirty();
            return 1;
          }
          return 0;
        });

    dispatcher.register(initaddrCommand);
  }

}


