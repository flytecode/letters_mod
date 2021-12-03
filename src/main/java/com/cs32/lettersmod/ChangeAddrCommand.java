package com.cs32.lettersmod;

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
import net.minecraft.nbt.INBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

import static net.minecraft.util.math.MathHelper.clamp;

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
                  Integer oldAddress = Integer.parseInt(iTextComponent.getString());

                  // code that loads up value for current address and makes sure it is equal to inputted oldaddr
                  // TODO is this client code? If so is this wrong, trying to access the server? this could cause weird bugs possibly.
                  ServerWorld world = commandContext.getSource().getWorld();
                  if (!world.isRemote()) {
                    LettersSavedData saver = LettersSavedData.forWorld(world);
                    if (saver.data.contains("world_address")) {
                      INBT currentAddress = saver.data.get("world_address");
                      //TODO WORK WITH INBT AND REFACTOR THIS CODE
                      if (!currentAddress.equals(oldAddress)) {
                        // incorrect oldaddr, tell the user and don't do anything else
                        sendMessage(commandContext, "oldaddr does not match current world_address.");
                      } else {
                        // set up apiclient, send a post request to server with old addr
                        ApiClient poster = new ApiClient("http://localhost:4567/changeaddr");
                        JsonObject reqBody = new JsonObject();
                        reqBody.addProperty("oldaddr", oldAddress);
                        JsonObject reqResult = poster.postFromJson(reqBody);

                        // get back the new addr and print it out
                        Integer newAddr = reqResult.get("newaddr").getAsInt();
                        sendMessage(commandContext,
                            Integer.toString(newAddr) + " being set as world_address");
                        // TODO save the new address in global save data
//                  LettersSavedData saver = LettersSavedData.forWorld((ServerWorld) event.getWorld());
//                  CompoundNBT myData = new CompoundNBT();
//                  myData.putInt("MyData", 69); //Put in whatever you want with myData.put
//                  saver.data = myData;
//                  saver.markDirty();
//                  LOGGER.debug("Put my data in!");

                        return 1;
                      }


                    } else {
                      // this shouldn't happen, world_address should be set
                      sendMessage(commandContext, "world_address was not set");
                    }


                  }
                })
        )
        .executes(commandContext -> sendMessage(commandContext,
            "Please enter this world's old address."));  // blank: didn't match a literal or the custommessage argument

    dispatcher.register(changeaddrCommand);
  }

  static boolean checkOldAddr(CommandContext<CommandSource> commandContext)
      throws CommandSyntaxException {
    //TODO function that checks old address provided by player to make sure we good to go
    return false;
  }

  static void resetAddr(CommandContext<CommandSource> commandContext)
      throws CommandSyntaxException {
    //TODO function that takes the new addr output from the request to server and saves it in global addr var
  }

  static int sendMessage(CommandContext<CommandSource> commandContext, String message)
      throws CommandSyntaxException {
    TranslationTextComponent finalText = new TranslationTextComponent("chat.type.announcement",
        commandContext.getSource().getDisplayName(), new StringTextComponent(message));

    Entity entity = commandContext.getSource().getEntity();
    if (entity != null) {
      commandContext.getSource().getServer().getPlayerList()
          .func_232641_a_(finalText, ChatType.CHAT, entity.getUniqueID());
      //func_232641_a_ is sendMessage()
    } else {
      commandContext.getSource().getServer().getPlayerList()
          .func_232641_a_(finalText, ChatType.SYSTEM, Util.DUMMY_UUID);
    }
    return 1;
  }


}


