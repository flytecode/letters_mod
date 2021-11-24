package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

public class GameModeCommand {
   public static final int f_180230_ = 2;

   public static void m_137729_(CommandDispatcher<CommandSourceStack> p_137730_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("gamemode").requires((p_137736_) -> {
         return p_137736_.m_6761_(2);
      });

      for(GameType gametype : GameType.values()) {
         literalargumentbuilder.then(Commands.m_82127_(gametype.m_46405_()).executes((p_137743_) -> {
            return m_137731_(p_137743_, Collections.singleton(p_137743_.getSource().m_81375_()), gametype);
         }).then(Commands.m_82129_("target", EntityArgument.m_91470_()).executes((p_137728_) -> {
            return m_137731_(p_137728_, EntityArgument.m_91477_(p_137728_, "target"), gametype);
         })));
      }

      p_137730_.register(literalargumentbuilder);
   }

   private static void m_137737_(CommandSourceStack p_137738_, ServerPlayer p_137739_, GameType p_137740_) {
      Component component = new TranslatableComponent("gameMode." + p_137740_.m_46405_());
      if (p_137738_.m_81373_() == p_137739_) {
         p_137738_.m_81354_(new TranslatableComponent("commands.gamemode.success.self", component), true);
      } else {
         if (p_137738_.m_81372_().m_46469_().m_46207_(GameRules.f_46144_)) {
            p_137739_.m_6352_(new TranslatableComponent("gameMode.changed", component), Util.f_137441_);
         }

         p_137738_.m_81354_(new TranslatableComponent("commands.gamemode.success.other", p_137739_.m_5446_(), component), true);
      }

   }

   private static int m_137731_(CommandContext<CommandSourceStack> p_137732_, Collection<ServerPlayer> p_137733_, GameType p_137734_) {
      int i = 0;

      for(ServerPlayer serverplayer : p_137733_) {
         if (serverplayer.m_143403_(p_137734_)) {
            m_137737_(p_137732_.getSource(), serverplayer, p_137734_);
            ++i;
         }
      }

      return i;
   }
}