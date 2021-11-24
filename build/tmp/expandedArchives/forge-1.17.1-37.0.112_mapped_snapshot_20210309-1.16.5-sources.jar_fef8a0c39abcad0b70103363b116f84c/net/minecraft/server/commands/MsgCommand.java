package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class MsgCommand {
   public static void m_138060_(CommandDispatcher<CommandSourceStack> p_138061_) {
      LiteralCommandNode<CommandSourceStack> literalcommandnode = p_138061_.register(Commands.m_82127_("msg").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("message", MessageArgument.m_96832_()).executes((p_138063_) -> {
         return m_138064_(p_138063_.getSource(), EntityArgument.m_91477_(p_138063_, "targets"), MessageArgument.m_96835_(p_138063_, "message"));
      }))));
      p_138061_.register(Commands.m_82127_("tell").redirect(literalcommandnode));
      p_138061_.register(Commands.m_82127_("w").redirect(literalcommandnode));
   }

   private static int m_138064_(CommandSourceStack p_138065_, Collection<ServerPlayer> p_138066_, Component p_138067_) {
      UUID uuid = p_138065_.m_81373_() == null ? Util.f_137441_ : p_138065_.m_81373_().m_142081_();
      Entity entity = p_138065_.m_81373_();
      Consumer<Component> consumer;
      if (entity instanceof ServerPlayer) {
         ServerPlayer serverplayer = (ServerPlayer)entity;
         consumer = (p_138059_) -> {
            serverplayer.m_6352_((new TranslatableComponent("commands.message.display.outgoing", p_138059_, p_138067_)).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}), serverplayer.m_142081_());
         };
      } else {
         consumer = (p_138071_) -> {
            p_138065_.m_81354_((new TranslatableComponent("commands.message.display.outgoing", p_138071_, p_138067_)).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}), false);
         };
      }

      for(ServerPlayer serverplayer1 : p_138066_) {
         consumer.accept(serverplayer1.m_5446_());
         serverplayer1.m_6352_((new TranslatableComponent("commands.message.display.incoming", p_138065_.m_81357_(), p_138067_)).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}), uuid);
      }

      return p_138066_.size();
   }
}