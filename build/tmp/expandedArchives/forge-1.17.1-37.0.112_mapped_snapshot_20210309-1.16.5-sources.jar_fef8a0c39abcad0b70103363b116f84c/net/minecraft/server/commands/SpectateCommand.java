package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;

public class SpectateCommand {
   private static final SimpleCommandExceptionType f_138674_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.spectate.self"));
   private static final DynamicCommandExceptionType f_138675_ = new DynamicCommandExceptionType((p_138688_) -> {
      return new TranslatableComponent("commands.spectate.not_spectator", p_138688_);
   });

   public static void m_138677_(CommandDispatcher<CommandSourceStack> p_138678_) {
      p_138678_.register(Commands.m_82127_("spectate").requires((p_138682_) -> {
         return p_138682_.m_6761_(2);
      }).executes((p_138692_) -> {
         return m_138683_(p_138692_.getSource(), (Entity)null, p_138692_.getSource().m_81375_());
      }).then(Commands.m_82129_("target", EntityArgument.m_91449_()).executes((p_138690_) -> {
         return m_138683_(p_138690_.getSource(), EntityArgument.m_91452_(p_138690_, "target"), p_138690_.getSource().m_81375_());
      }).then(Commands.m_82129_("player", EntityArgument.m_91466_()).executes((p_138680_) -> {
         return m_138683_(p_138680_.getSource(), EntityArgument.m_91452_(p_138680_, "target"), EntityArgument.m_91474_(p_138680_, "player"));
      }))));
   }

   private static int m_138683_(CommandSourceStack p_138684_, @Nullable Entity p_138685_, ServerPlayer p_138686_) throws CommandSyntaxException {
      if (p_138686_ == p_138685_) {
         throw f_138674_.create();
      } else if (p_138686_.f_8941_.m_9290_() != GameType.SPECTATOR) {
         throw f_138675_.create(p_138686_.m_5446_());
      } else {
         p_138686_.m_9213_(p_138685_);
         if (p_138685_ != null) {
            p_138684_.m_81354_(new TranslatableComponent("commands.spectate.success.started", p_138685_.m_5446_()), false);
         } else {
            p_138684_.m_81354_(new TranslatableComponent("commands.spectate.success.stopped"), false);
         }

         return 1;
      }
   }
}