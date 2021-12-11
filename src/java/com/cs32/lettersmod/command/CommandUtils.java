package com.cs32.lettersmod.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandUtils {
  public static int sendMessage(CommandContext<CommandSource> commandContext, String message)
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
