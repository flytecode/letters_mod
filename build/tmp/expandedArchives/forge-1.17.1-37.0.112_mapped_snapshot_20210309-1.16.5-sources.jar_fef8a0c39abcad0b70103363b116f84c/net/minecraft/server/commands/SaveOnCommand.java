package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class SaveOnCommand {
   private static final SimpleCommandExceptionType f_138290_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.alreadyOn"));

   public static void m_138292_(CommandDispatcher<CommandSourceStack> p_138293_) {
      p_138293_.register(Commands.m_82127_("save-on").requires((p_138297_) -> {
         return p_138297_.m_6761_(4);
      }).executes((p_138295_) -> {
         CommandSourceStack commandsourcestack = p_138295_.getSource();
         boolean flag = false;

         for(ServerLevel serverlevel : commandsourcestack.m_81377_().m_129785_()) {
            if (serverlevel != null && serverlevel.f_8564_) {
               serverlevel.f_8564_ = false;
               flag = true;
            }
         }

         if (!flag) {
            throw f_138290_.create();
         } else {
            commandsourcestack.m_81354_(new TranslatableComponent("commands.save.enabled"), true);
            return 1;
         }
      }));
   }
}