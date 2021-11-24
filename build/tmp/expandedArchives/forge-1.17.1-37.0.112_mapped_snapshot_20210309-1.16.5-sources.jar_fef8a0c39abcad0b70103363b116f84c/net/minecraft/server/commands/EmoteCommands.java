package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class EmoteCommands {
   public static void m_136985_(CommandDispatcher<CommandSourceStack> p_136986_) {
      p_136986_.register(Commands.m_82127_("me").then(Commands.m_82129_("action", StringArgumentType.greedyString()).executes((p_136988_) -> {
         String s = StringArgumentType.getString(p_136988_, "action");
         Entity entity = p_136988_.getSource().m_81373_();
         MinecraftServer minecraftserver = p_136988_.getSource().m_81377_();
         if (entity != null) {
            if (entity instanceof ServerPlayer) {
               ServerPlayer serverplayer = (ServerPlayer)entity;
               serverplayer.m_8967_().m_6770_(s).thenAcceptAsync((p_180146_) -> {
                  String s1 = p_180146_.m_143722_();
                  Component component = s1.isEmpty() ? null : m_136989_(p_136988_, s1);
                  Component component1 = m_136989_(p_136988_, p_180146_.m_143719_());
                  minecraftserver.m_6846_().m_143991_(component1, (p_180140_) -> {
                     return serverplayer.m_143421_(p_180140_) ? component : component1;
                  }, ChatType.CHAT, entity.m_142081_());
               }, minecraftserver);
               return 1;
            }

            minecraftserver.m_6846_().m_11264_(m_136989_(p_136988_, s), ChatType.CHAT, entity.m_142081_());
         } else {
            minecraftserver.m_6846_().m_11264_(m_136989_(p_136988_, s), ChatType.SYSTEM, Util.f_137441_);
         }

         return 1;
      })));
   }

   private static Component m_136989_(CommandContext<CommandSourceStack> p_136990_, String p_136991_) {
      return new TranslatableComponent("chat.type.emote", p_136990_.getSource().m_81357_(), p_136991_);
   }
}