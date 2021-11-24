package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class StopCommand {
   public static void m_138785_(CommandDispatcher<CommandSourceStack> p_138786_) {
      p_138786_.register(Commands.m_82127_("stop").requires((p_138790_) -> {
         return p_138790_.m_6761_(4);
      }).executes((p_138788_) -> {
         p_138788_.getSource().m_81354_(new TranslatableComponent("commands.stop.stopping"), true);
         p_138788_.getSource().m_81377_().m_7570_(false);
         return 1;
      }));
   }
}