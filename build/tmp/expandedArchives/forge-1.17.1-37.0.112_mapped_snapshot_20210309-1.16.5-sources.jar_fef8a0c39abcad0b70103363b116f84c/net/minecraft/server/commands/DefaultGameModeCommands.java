package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class DefaultGameModeCommands {
   public static void m_136926_(CommandDispatcher<CommandSourceStack> p_136927_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("defaultgamemode").requires((p_136929_) -> {
         return p_136929_.m_6761_(2);
      });

      for(GameType gametype : GameType.values()) {
         literalargumentbuilder.then(Commands.m_82127_(gametype.m_46405_()).executes((p_136925_) -> {
            return m_136930_(p_136925_.getSource(), gametype);
         }));
      }

      p_136927_.register(literalargumentbuilder);
   }

   private static int m_136930_(CommandSourceStack p_136931_, GameType p_136932_) {
      int i = 0;
      MinecraftServer minecraftserver = p_136931_.m_81377_();
      minecraftserver.m_7835_(p_136932_);
      GameType gametype = minecraftserver.m_142359_();
      if (gametype != null) {
         for(ServerPlayer serverplayer : minecraftserver.m_6846_().m_11314_()) {
            if (serverplayer.m_143403_(gametype)) {
               ++i;
            }
         }
      }

      p_136931_.m_81354_(new TranslatableComponent("commands.defaultgamemode.success", p_136932_.m_151499_()), true);
      return i;
   }
}