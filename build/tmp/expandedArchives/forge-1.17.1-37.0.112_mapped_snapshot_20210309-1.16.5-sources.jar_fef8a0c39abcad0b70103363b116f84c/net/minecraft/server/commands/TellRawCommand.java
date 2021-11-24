package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;

public class TellRawCommand {
   public static void m_139063_(CommandDispatcher<CommandSourceStack> p_139064_) {
      p_139064_.register(Commands.m_82127_("tellraw").requires((p_139068_) -> {
         return p_139068_.m_6761_(2);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("message", ComponentArgument.m_87114_()).executes((p_139066_) -> {
         int i = 0;

         for(ServerPlayer serverplayer : EntityArgument.m_91477_(p_139066_, "targets")) {
            serverplayer.m_6352_(ComponentUtils.m_130731_(p_139066_.getSource(), ComponentArgument.m_87117_(p_139066_, "message"), serverplayer, 0), Util.f_137441_);
            ++i;
         }

         return i;
      }))));
   }
}