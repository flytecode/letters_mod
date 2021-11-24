package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;

public class DebugMobSpawningCommand {
   public static void m_180110_(CommandDispatcher<CommandSourceStack> p_180111_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("debugmobspawning").requires((p_180113_) -> {
         return p_180113_.m_6761_(2);
      });

      for(MobCategory mobcategory : MobCategory.values()) {
         literalargumentbuilder.then(Commands.m_82127_(mobcategory.m_21607_()).then(Commands.m_82129_("at", BlockPosArgument.m_118239_()).executes((p_180109_) -> {
            return m_180114_(p_180109_.getSource(), mobcategory, BlockPosArgument.m_118242_(p_180109_, "at"));
         })));
      }

      p_180111_.register(literalargumentbuilder);
   }

   private static int m_180114_(CommandSourceStack p_180115_, MobCategory p_180116_, BlockPos p_180117_) {
      NaturalSpawner.m_151612_(p_180116_, p_180115_.m_81372_(), p_180117_);
      return 1;
   }
}