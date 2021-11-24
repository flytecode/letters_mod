package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;

public class SaveAllCommand {
   private static final SimpleCommandExceptionType f_138269_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.save.failed"));

   public static void m_138271_(CommandDispatcher<CommandSourceStack> p_138272_) {
      p_138272_.register(Commands.m_82127_("save-all").requires((p_138276_) -> {
         return p_138276_.m_6761_(4);
      }).executes((p_138281_) -> {
         return m_138277_(p_138281_.getSource(), false);
      }).then(Commands.m_82127_("flush").executes((p_138274_) -> {
         return m_138277_(p_138274_.getSource(), true);
      })));
   }

   private static int m_138277_(CommandSourceStack p_138278_, boolean p_138279_) throws CommandSyntaxException {
      p_138278_.m_81354_(new TranslatableComponent("commands.save.saving"), false);
      MinecraftServer minecraftserver = p_138278_.m_81377_();
      minecraftserver.m_6846_().m_11302_();
      boolean flag = minecraftserver.m_129885_(true, p_138279_, true);
      if (!flag) {
         throw f_138269_.create();
      } else {
         p_138278_.m_81354_(new TranslatableComponent("commands.save.success"), true);
         return 1;
      }
   }
}