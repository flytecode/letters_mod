package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class SayCommand {
   public static void m_138409_(CommandDispatcher<CommandSourceStack> p_138410_) {
      p_138410_.register(Commands.m_82127_("say").requires((p_138414_) -> {
         return p_138414_.m_6761_(2);
      }).then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes((p_138412_) -> {
         Component component = MessageArgument.m_96835_(p_138412_, "message");
         Component component1 = new TranslatableComponent("chat.type.announcement", p_138412_.getSource().m_81357_(), component);
         Entity entity = p_138412_.getSource().m_81373_();
         if (entity != null) {
            p_138412_.getSource().m_81377_().m_6846_().m_11264_(component1, ChatType.CHAT, entity.m_142081_());
         } else {
            p_138412_.getSource().m_81377_().m_6846_().m_11264_(component1, ChatType.SYSTEM, Util.f_137441_);
         }

         return 1;
      })));
   }
}