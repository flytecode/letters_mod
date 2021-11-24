package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class SetPlayerIdleTimeoutCommand {
   public static void m_138634_(CommandDispatcher<CommandSourceStack> p_138635_) {
      p_138635_.register(Commands.m_82127_("setidletimeout").requires((p_138639_) -> {
         return p_138639_.m_6761_(3);
      }).then(Commands.m_82129_("minutes", IntegerArgumentType.integer(0)).executes((p_138637_) -> {
         return m_138640_(p_138637_.getSource(), IntegerArgumentType.getInteger(p_138637_, "minutes"));
      })));
   }

   private static int m_138640_(CommandSourceStack p_138641_, int p_138642_) {
      p_138641_.m_81377_().m_7196_(p_138642_);
      p_138641_.m_81354_(new TranslatableComponent("commands.setidletimeout.success", p_138642_), true);
      return p_138642_;
   }
}