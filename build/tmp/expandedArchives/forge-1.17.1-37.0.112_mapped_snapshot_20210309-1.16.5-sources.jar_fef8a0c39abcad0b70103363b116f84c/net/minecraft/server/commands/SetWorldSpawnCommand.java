package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;

public class SetWorldSpawnCommand {
   public static void m_138660_(CommandDispatcher<CommandSourceStack> p_138661_) {
      p_138661_.register(Commands.m_82127_("setworldspawn").requires((p_138665_) -> {
         return p_138665_.m_6761_(2);
      }).executes((p_138673_) -> {
         return m_138666_(p_138673_.getSource(), new BlockPos(p_138673_.getSource().m_81371_()), 0.0F);
      }).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes((p_138671_) -> {
         return m_138666_(p_138671_.getSource(), BlockPosArgument.m_174395_(p_138671_, "pos"), 0.0F);
      }).then(Commands.m_82129_("angle", AngleArgument.m_83807_()).executes((p_138663_) -> {
         return m_138666_(p_138663_.getSource(), BlockPosArgument.m_174395_(p_138663_, "pos"), AngleArgument.m_83810_(p_138663_, "angle"));
      }))));
   }

   private static int m_138666_(CommandSourceStack p_138667_, BlockPos p_138668_, float p_138669_) {
      p_138667_.m_81372_().m_8733_(p_138668_, p_138669_);
      p_138667_.m_81354_(new TranslatableComponent("commands.setworldspawn.success", p_138668_.m_123341_(), p_138668_.m_123342_(), p_138668_.m_123343_(), p_138669_), true);
      return 1;
   }
}