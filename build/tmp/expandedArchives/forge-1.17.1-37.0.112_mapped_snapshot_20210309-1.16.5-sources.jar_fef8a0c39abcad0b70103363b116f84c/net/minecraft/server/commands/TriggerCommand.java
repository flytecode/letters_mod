package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class TriggerCommand {
   private static final SimpleCommandExceptionType f_139135_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.trigger.failed.unprimed"));
   private static final SimpleCommandExceptionType f_139136_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.trigger.failed.invalid"));

   public static void m_139141_(CommandDispatcher<CommandSourceStack> p_139142_) {
      p_139142_.register(Commands.m_82127_("trigger").then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).suggests((p_139146_, p_139147_) -> {
         return m_139148_(p_139146_.getSource(), p_139147_);
      }).executes((p_139165_) -> {
         return m_139151_(p_139165_.getSource(), m_139138_(p_139165_.getSource().m_81375_(), ObjectiveArgument.m_101960_(p_139165_, "objective")));
      }).then(Commands.m_82127_("add").then(Commands.m_82129_("value", IntegerArgumentType.integer()).executes((p_139159_) -> {
         return m_139154_(p_139159_.getSource(), m_139138_(p_139159_.getSource().m_81375_(), ObjectiveArgument.m_101960_(p_139159_, "objective")), IntegerArgumentType.getInteger(p_139159_, "value"));
      }))).then(Commands.m_82127_("set").then(Commands.m_82129_("value", IntegerArgumentType.integer()).executes((p_139144_) -> {
         return m_139160_(p_139144_.getSource(), m_139138_(p_139144_.getSource().m_81375_(), ObjectiveArgument.m_101960_(p_139144_, "objective")), IntegerArgumentType.getInteger(p_139144_, "value"));
      })))));
   }

   public static CompletableFuture<Suggestions> m_139148_(CommandSourceStack p_139149_, SuggestionsBuilder p_139150_) {
      Entity entity = p_139149_.m_81373_();
      List<String> list = Lists.newArrayList();
      if (entity != null) {
         Scoreboard scoreboard = p_139149_.m_81377_().m_129896_();
         String s = entity.m_6302_();

         for(Objective objective : scoreboard.m_83466_()) {
            if (objective.m_83321_() == ObjectiveCriteria.f_83589_ && scoreboard.m_83461_(s, objective)) {
               Score score = scoreboard.m_83471_(s, objective);
               if (!score.m_83407_()) {
                  list.add(objective.m_83320_());
               }
            }
         }
      }

      return SharedSuggestionProvider.m_82970_(list, p_139150_);
   }

   private static int m_139154_(CommandSourceStack p_139155_, Score p_139156_, int p_139157_) {
      p_139156_.m_83393_(p_139157_);
      p_139155_.m_81354_(new TranslatableComponent("commands.trigger.add.success", p_139156_.m_83404_().m_83323_(), p_139157_), true);
      return p_139156_.m_83400_();
   }

   private static int m_139160_(CommandSourceStack p_139161_, Score p_139162_, int p_139163_) {
      p_139162_.m_83402_(p_139163_);
      p_139161_.m_81354_(new TranslatableComponent("commands.trigger.set.success", p_139162_.m_83404_().m_83323_(), p_139163_), true);
      return p_139163_;
   }

   private static int m_139151_(CommandSourceStack p_139152_, Score p_139153_) {
      p_139153_.m_83393_(1);
      p_139152_.m_81354_(new TranslatableComponent("commands.trigger.simple.success", p_139153_.m_83404_().m_83323_()), true);
      return p_139153_.m_83400_();
   }

   private static Score m_139138_(ServerPlayer p_139139_, Objective p_139140_) throws CommandSyntaxException {
      if (p_139140_.m_83321_() != ObjectiveCriteria.f_83589_) {
         throw f_139136_.create();
      } else {
         Scoreboard scoreboard = p_139139_.m_36329_();
         String s = p_139139_.m_6302_();
         if (!scoreboard.m_83461_(s, p_139140_)) {
            throw f_139135_.create();
         } else {
            Score score = scoreboard.m_83471_(s, p_139140_);
            if (score.m_83407_()) {
               throw f_139135_.create();
            } else {
               score.m_83398_(true);
               return score;
            }
         }
      }
   }
}