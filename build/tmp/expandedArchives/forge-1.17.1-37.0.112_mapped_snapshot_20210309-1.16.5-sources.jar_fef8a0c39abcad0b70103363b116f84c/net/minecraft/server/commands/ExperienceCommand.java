package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ExperienceCommand {
   private static final SimpleCommandExceptionType f_137304_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.experience.set.points.invalid"));

   public static void m_137306_(CommandDispatcher<CommandSourceStack> p_137307_) {
      LiteralCommandNode<CommandSourceStack> literalcommandnode = p_137307_.register(Commands.m_82127_("experience").requires((p_137324_) -> {
         return p_137324_.m_6761_(2);
      }).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("amount", IntegerArgumentType.integer()).executes((p_137341_) -> {
         return m_137316_(p_137341_.getSource(), EntityArgument.m_91477_(p_137341_, "targets"), IntegerArgumentType.getInteger(p_137341_, "amount"), ExperienceCommand.Type.POINTS);
      }).then(Commands.m_82127_("points").executes((p_137339_) -> {
         return m_137316_(p_137339_.getSource(), EntityArgument.m_91477_(p_137339_, "targets"), IntegerArgumentType.getInteger(p_137339_, "amount"), ExperienceCommand.Type.POINTS);
      })).then(Commands.m_82127_("levels").executes((p_137337_) -> {
         return m_137316_(p_137337_.getSource(), EntityArgument.m_91477_(p_137337_, "targets"), IntegerArgumentType.getInteger(p_137337_, "amount"), ExperienceCommand.Type.LEVELS);
      }))))).then(Commands.m_82127_("set").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("amount", IntegerArgumentType.integer(0)).executes((p_137335_) -> {
         return m_137325_(p_137335_.getSource(), EntityArgument.m_91477_(p_137335_, "targets"), IntegerArgumentType.getInteger(p_137335_, "amount"), ExperienceCommand.Type.POINTS);
      }).then(Commands.m_82127_("points").executes((p_137333_) -> {
         return m_137325_(p_137333_.getSource(), EntityArgument.m_91477_(p_137333_, "targets"), IntegerArgumentType.getInteger(p_137333_, "amount"), ExperienceCommand.Type.POINTS);
      })).then(Commands.m_82127_("levels").executes((p_137331_) -> {
         return m_137325_(p_137331_.getSource(), EntityArgument.m_91477_(p_137331_, "targets"), IntegerArgumentType.getInteger(p_137331_, "amount"), ExperienceCommand.Type.LEVELS);
      }))))).then(Commands.m_82127_("query").then(Commands.m_82129_("targets", EntityArgument.m_91466_()).then(Commands.m_82127_("points").executes((p_137322_) -> {
         return m_137312_(p_137322_.getSource(), EntityArgument.m_91474_(p_137322_, "targets"), ExperienceCommand.Type.POINTS);
      })).then(Commands.m_82127_("levels").executes((p_137309_) -> {
         return m_137312_(p_137309_.getSource(), EntityArgument.m_91474_(p_137309_, "targets"), ExperienceCommand.Type.LEVELS);
      })))));
      p_137307_.register(Commands.m_82127_("xp").requires((p_137311_) -> {
         return p_137311_.m_6761_(2);
      }).redirect(literalcommandnode));
   }

   private static int m_137312_(CommandSourceStack p_137313_, ServerPlayer p_137314_, ExperienceCommand.Type p_137315_) {
      int i = p_137315_.f_137347_.applyAsInt(p_137314_);
      p_137313_.m_81354_(new TranslatableComponent("commands.experience.query." + p_137315_.f_137346_, p_137314_.m_5446_(), i), false);
      return i;
   }

   private static int m_137316_(CommandSourceStack p_137317_, Collection<? extends ServerPlayer> p_137318_, int p_137319_, ExperienceCommand.Type p_137320_) {
      for(ServerPlayer serverplayer : p_137318_) {
         p_137320_.f_137344_.accept(serverplayer, p_137319_);
      }

      if (p_137318_.size() == 1) {
         p_137317_.m_81354_(new TranslatableComponent("commands.experience.add." + p_137320_.f_137346_ + ".success.single", p_137319_, p_137318_.iterator().next().m_5446_()), true);
      } else {
         p_137317_.m_81354_(new TranslatableComponent("commands.experience.add." + p_137320_.f_137346_ + ".success.multiple", p_137319_, p_137318_.size()), true);
      }

      return p_137318_.size();
   }

   private static int m_137325_(CommandSourceStack p_137326_, Collection<? extends ServerPlayer> p_137327_, int p_137328_, ExperienceCommand.Type p_137329_) throws CommandSyntaxException {
      int i = 0;

      for(ServerPlayer serverplayer : p_137327_) {
         if (p_137329_.f_137345_.test(serverplayer, p_137328_)) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_137304_.create();
      } else {
         if (p_137327_.size() == 1) {
            p_137326_.m_81354_(new TranslatableComponent("commands.experience.set." + p_137329_.f_137346_ + ".success.single", p_137328_, p_137327_.iterator().next().m_5446_()), true);
         } else {
            p_137326_.m_81354_(new TranslatableComponent("commands.experience.set." + p_137329_.f_137346_ + ".success.multiple", p_137328_, p_137327_.size()), true);
         }

         return p_137327_.size();
      }
   }

   static enum Type {
      POINTS("points", Player::m_6756_, (p_137367_, p_137368_) -> {
         if (p_137368_ >= p_137367_.m_36323_()) {
            return false;
         } else {
            p_137367_.m_8985_(p_137368_);
            return true;
         }
      }, (p_137365_) -> {
         return Mth.m_14143_(p_137365_.f_36080_ * (float)p_137365_.m_36323_());
      }),
      LEVELS("levels", ServerPlayer::m_6749_, (p_137360_, p_137361_) -> {
         p_137360_.m_9174_(p_137361_);
         return true;
      }, (p_137358_) -> {
         return p_137358_.f_36078_;
      });

      public final BiConsumer<ServerPlayer, Integer> f_137344_;
      public final BiPredicate<ServerPlayer, Integer> f_137345_;
      public final String f_137346_;
      final ToIntFunction<ServerPlayer> f_137347_;

      private Type(String p_137353_, BiConsumer<ServerPlayer, Integer> p_137354_, BiPredicate<ServerPlayer, Integer> p_137355_, ToIntFunction<ServerPlayer> p_137356_) {
         this.f_137344_ = p_137354_;
         this.f_137346_ = p_137353_;
         this.f_137345_ = p_137355_;
         this.f_137347_ = p_137356_;
      }
   }
}