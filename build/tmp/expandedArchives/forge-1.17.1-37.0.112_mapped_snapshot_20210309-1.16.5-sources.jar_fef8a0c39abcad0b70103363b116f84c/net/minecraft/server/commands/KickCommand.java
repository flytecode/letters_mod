package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class KickCommand {
   public static void m_137795_(CommandDispatcher<CommandSourceStack> p_137796_) {
      p_137796_.register(Commands.m_82127_("kick").requires((p_137800_) -> {
         return p_137800_.m_6761_(3);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((p_137806_) -> {
         return m_137801_(p_137806_.getSource(), EntityArgument.m_91477_(p_137806_, "targets"), new TranslatableComponent("multiplayer.disconnect.kicked"));
      }).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes((p_137798_) -> {
         return m_137801_(p_137798_.getSource(), EntityArgument.m_91477_(p_137798_, "targets"), MessageArgument.m_96835_(p_137798_, "reason"));
      }))));
   }

   private static int m_137801_(CommandSourceStack p_137802_, Collection<ServerPlayer> p_137803_, Component p_137804_) {
      for(ServerPlayer serverplayer : p_137803_) {
         serverplayer.f_8906_.m_9942_(p_137804_);
         p_137802_.m_81354_(new TranslatableComponent("commands.kick.success", serverplayer.m_5446_(), p_137804_), true);
      }

      return p_137803_.size();
   }
}