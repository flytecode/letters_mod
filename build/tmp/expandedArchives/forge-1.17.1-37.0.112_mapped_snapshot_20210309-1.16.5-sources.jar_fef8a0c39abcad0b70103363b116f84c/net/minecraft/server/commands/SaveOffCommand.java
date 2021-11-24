package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;

public class SaveOffCommand {
   private static final SimpleCommandExceptionType f_138282_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.alreadyOff"));

   public static void m_138284_(CommandDispatcher<CommandSourceStack> p_138285_) {
      p_138285_.register(Commands.m_82127_("save-off").requires((p_138289_) -> {
         return p_138289_.m_6761_(4);
      }).executes((p_138287_) -> {
         CommandSourceStack commandsourcestack = p_138287_.getSource();
         boolean flag = false;

         for(ServerLevel serverlevel : commandsourcestack.m_81377_().m_129785_()) {
            if (serverlevel != null && !serverlevel.f_8564_) {
               serverlevel.f_8564_ = true;
               flag = true;
            }
         }

         if (!flag) {
            throw f_138282_.create();
         } else {
            commandsourcestack.m_81354_(new TranslatableComponent("commands.save.disabled"), true);
            return 1;
         }
      }));
   }
}