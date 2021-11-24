package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

public class DebugPathCommand {
   private static final SimpleCommandExceptionType f_180118_ = new SimpleCommandExceptionType(new TextComponent("Source is not a mob"));
   private static final SimpleCommandExceptionType f_180119_ = new SimpleCommandExceptionType(new TextComponent("Path not found"));
   private static final SimpleCommandExceptionType f_180120_ = new SimpleCommandExceptionType(new TextComponent("Target not reached"));

   public static void m_180123_(CommandDispatcher<CommandSourceStack> p_180124_) {
      p_180124_.register(Commands.m_82127_("debugpath").requires((p_180128_) -> {
         return p_180128_.m_6761_(2);
      }).then(Commands.m_82129_("to", BlockPosArgument.m_118239_()).executes((p_180126_) -> {
         return m_180129_(p_180126_.getSource(), BlockPosArgument.m_118242_(p_180126_, "to"));
      })));
   }

   private static int m_180129_(CommandSourceStack p_180130_, BlockPos p_180131_) throws CommandSyntaxException {
      Entity entity = p_180130_.m_81373_();
      if (!(entity instanceof Mob)) {
         throw f_180118_.create();
      } else {
         Mob mob = (Mob)entity;
         PathNavigation pathnavigation = new GroundPathNavigation(mob, p_180130_.m_81372_());
         Path path = pathnavigation.m_7864_(p_180131_, 0);
         DebugPackets.m_133703_(p_180130_.m_81372_(), mob, path, pathnavigation.m_148228_());
         if (path == null) {
            throw f_180119_.create();
         } else if (!path.m_77403_()) {
            throw f_180120_.create();
         } else {
            p_180130_.m_81354_(new TextComponent("Made path"), true);
            return 1;
         }
      }
   }
}