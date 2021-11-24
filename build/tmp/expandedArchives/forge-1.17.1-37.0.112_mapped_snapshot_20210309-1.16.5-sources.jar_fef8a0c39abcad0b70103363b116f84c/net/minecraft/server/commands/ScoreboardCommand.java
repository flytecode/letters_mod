package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.commands.arguments.OperationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ScoreboardCommand {
   private static final SimpleCommandExceptionType f_138460_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.scoreboard.objectives.add.duplicate"));
   private static final SimpleCommandExceptionType f_138461_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.scoreboard.objectives.display.alreadyEmpty"));
   private static final SimpleCommandExceptionType f_138462_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.scoreboard.objectives.display.alreadySet"));
   private static final SimpleCommandExceptionType f_138463_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.scoreboard.players.enable.failed"));
   private static final SimpleCommandExceptionType f_138464_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.scoreboard.players.enable.invalid"));
   private static final Dynamic2CommandExceptionType f_138465_ = new Dynamic2CommandExceptionType((p_138534_, p_138535_) -> {
      return new TranslatableComponent("commands.scoreboard.players.get.null", p_138534_, p_138535_);
   });

   public static void m_138468_(CommandDispatcher<CommandSourceStack> p_138469_) {
      p_138469_.register(Commands.m_82127_("scoreboard").requires((p_138552_) -> {
         return p_138552_.m_6761_(2);
      }).then(Commands.m_82127_("objectives").then(Commands.m_82127_("list").executes((p_138585_) -> {
         return m_138538_(p_138585_.getSource());
      })).then(Commands.m_82127_("add").then(Commands.m_82129_("objective", StringArgumentType.word()).then(Commands.m_82129_("criteria", ObjectiveCriteriaArgument.m_102555_()).executes((p_138583_) -> {
         return m_138502_(p_138583_.getSource(), StringArgumentType.getString(p_138583_, "objective"), ObjectiveCriteriaArgument.m_102565_(p_138583_, "criteria"), new TextComponent(StringArgumentType.getString(p_138583_, "objective")));
      }).then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes((p_138581_) -> {
         return m_138502_(p_138581_.getSource(), StringArgumentType.getString(p_138581_, "objective"), ObjectiveCriteriaArgument.m_102565_(p_138581_, "criteria"), ComponentArgument.m_87117_(p_138581_, "displayName"));
      }))))).then(Commands.m_82127_("modify").then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82127_("displayname").then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes((p_138579_) -> {
         return m_138491_(p_138579_.getSource(), ObjectiveArgument.m_101960_(p_138579_, "objective"), ComponentArgument.m_87117_(p_138579_, "displayName"));
      }))).then(m_138467_()))).then(Commands.m_82127_("remove").then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes((p_138577_) -> {
         return m_138484_(p_138577_.getSource(), ObjectiveArgument.m_101960_(p_138577_, "objective"));
      }))).then(Commands.m_82127_("setdisplay").then(Commands.m_82129_("slot", ScoreboardSlotArgument.m_109196_()).executes((p_138575_) -> {
         return m_138477_(p_138575_.getSource(), ScoreboardSlotArgument.m_109199_(p_138575_, "slot"));
      }).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes((p_138573_) -> {
         return m_138480_(p_138573_.getSource(), ScoreboardSlotArgument.m_109199_(p_138573_, "slot"), ObjectiveArgument.m_101960_(p_138573_, "objective"));
      }))))).then(Commands.m_82127_("players").then(Commands.m_82127_("list").executes((p_138571_) -> {
         return m_138475_(p_138571_.getSource());
      }).then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).executes((p_138569_) -> {
         return m_138495_(p_138569_.getSource(), ScoreHolderArgument.m_108223_(p_138569_, "target"));
      }))).then(Commands.m_82127_("set").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer()).executes((p_138567_) -> {
         return m_138518_(p_138567_.getSource(), ScoreHolderArgument.m_108246_(p_138567_, "targets"), ObjectiveArgument.m_101965_(p_138567_, "objective"), IntegerArgumentType.getInteger(p_138567_, "score"));
      }))))).then(Commands.m_82127_("get").then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes((p_138565_) -> {
         return m_138498_(p_138565_.getSource(), ScoreHolderArgument.m_108223_(p_138565_, "target"), ObjectiveArgument.m_101960_(p_138565_, "objective"));
      })))).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer(0)).executes((p_138563_) -> {
         return m_138544_(p_138563_.getSource(), ScoreHolderArgument.m_108246_(p_138563_, "targets"), ObjectiveArgument.m_101965_(p_138563_, "objective"), IntegerArgumentType.getInteger(p_138563_, "score"));
      }))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("score", IntegerArgumentType.integer(0)).executes((p_138561_) -> {
         return m_138553_(p_138561_.getSource(), ScoreHolderArgument.m_108246_(p_138561_, "targets"), ObjectiveArgument.m_101965_(p_138561_, "objective"), IntegerArgumentType.getInteger(p_138561_, "score"));
      }))))).then(Commands.m_82127_("reset").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes((p_138559_) -> {
         return m_138507_(p_138559_.getSource(), ScoreHolderArgument.m_108246_(p_138559_, "targets"));
      }).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes((p_138550_) -> {
         return m_138540_(p_138550_.getSource(), ScoreHolderArgument.m_108246_(p_138550_, "targets"), ObjectiveArgument.m_101960_(p_138550_, "objective"));
      })))).then(Commands.m_82127_("enable").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).suggests((p_138473_, p_138474_) -> {
         return m_138510_(p_138473_.getSource(), ScoreHolderArgument.m_108246_(p_138473_, "targets"), p_138474_);
      }).executes((p_138537_) -> {
         return m_138514_(p_138537_.getSource(), ScoreHolderArgument.m_108246_(p_138537_, "targets"), ObjectiveArgument.m_101960_(p_138537_, "objective"));
      })))).then(Commands.m_82127_("operation").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("targetObjective", ObjectiveArgument.m_101957_()).then(Commands.m_82129_("operation", OperationArgument.m_103269_()).then(Commands.m_82129_("source", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()).executes((p_138471_) -> {
         return m_138523_(p_138471_.getSource(), ScoreHolderArgument.m_108246_(p_138471_, "targets"), ObjectiveArgument.m_101965_(p_138471_, "targetObjective"), OperationArgument.m_103275_(p_138471_, "operation"), ScoreHolderArgument.m_108246_(p_138471_, "source"), ObjectiveArgument.m_101960_(p_138471_, "sourceObjective"));
      })))))))));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> m_138467_() {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("rendertype");

      for(ObjectiveCriteria.RenderType objectivecriteria$rendertype : ObjectiveCriteria.RenderType.values()) {
         literalargumentbuilder.then(Commands.m_82127_(objectivecriteria$rendertype.m_83633_()).executes((p_138532_) -> {
            return m_138487_(p_138532_.getSource(), ObjectiveArgument.m_101960_(p_138532_, "objective"), objectivecriteria$rendertype);
         }));
      }

      return literalargumentbuilder;
   }

   private static CompletableFuture<Suggestions> m_138510_(CommandSourceStack p_138511_, Collection<String> p_138512_, SuggestionsBuilder p_138513_) {
      List<String> list = Lists.newArrayList();
      Scoreboard scoreboard = p_138511_.m_81377_().m_129896_();

      for(Objective objective : scoreboard.m_83466_()) {
         if (objective.m_83321_() == ObjectiveCriteria.f_83589_) {
            boolean flag = false;

            for(String s : p_138512_) {
               if (!scoreboard.m_83461_(s, objective) || scoreboard.m_83471_(s, objective).m_83407_()) {
                  flag = true;
                  break;
               }
            }

            if (flag) {
               list.add(objective.m_83320_());
            }
         }
      }

      return SharedSuggestionProvider.m_82970_(list, p_138513_);
   }

   private static int m_138498_(CommandSourceStack p_138499_, String p_138500_, Objective p_138501_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138499_.m_81377_().m_129896_();
      if (!scoreboard.m_83461_(p_138500_, p_138501_)) {
         throw f_138465_.create(p_138501_.m_83320_(), p_138500_);
      } else {
         Score score = scoreboard.m_83471_(p_138500_, p_138501_);
         p_138499_.m_81354_(new TranslatableComponent("commands.scoreboard.players.get.success", p_138500_, score.m_83400_(), p_138501_.m_83323_()), false);
         return score.m_83400_();
      }
   }

   private static int m_138523_(CommandSourceStack p_138524_, Collection<String> p_138525_, Objective p_138526_, OperationArgument.Operation p_138527_, Collection<String> p_138528_, Objective p_138529_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138524_.m_81377_().m_129896_();
      int i = 0;

      for(String s : p_138525_) {
         Score score = scoreboard.m_83471_(s, p_138526_);

         for(String s1 : p_138528_) {
            Score score1 = scoreboard.m_83471_(s1, p_138529_);
            p_138527_.m_6407_(score, score1);
         }

         i += score.m_83400_();
      }

      if (p_138525_.size() == 1) {
         p_138524_.m_81354_(new TranslatableComponent("commands.scoreboard.players.operation.success.single", p_138526_.m_83323_(), p_138525_.iterator().next(), i), true);
      } else {
         p_138524_.m_81354_(new TranslatableComponent("commands.scoreboard.players.operation.success.multiple", p_138526_.m_83323_(), p_138525_.size()), true);
      }

      return i;
   }

   private static int m_138514_(CommandSourceStack p_138515_, Collection<String> p_138516_, Objective p_138517_) throws CommandSyntaxException {
      if (p_138517_.m_83321_() != ObjectiveCriteria.f_83589_) {
         throw f_138464_.create();
      } else {
         Scoreboard scoreboard = p_138515_.m_81377_().m_129896_();
         int i = 0;

         for(String s : p_138516_) {
            Score score = scoreboard.m_83471_(s, p_138517_);
            if (score.m_83407_()) {
               score.m_83398_(false);
               ++i;
            }
         }

         if (i == 0) {
            throw f_138463_.create();
         } else {
            if (p_138516_.size() == 1) {
               p_138515_.m_81354_(new TranslatableComponent("commands.scoreboard.players.enable.success.single", p_138517_.m_83323_(), p_138516_.iterator().next()), true);
            } else {
               p_138515_.m_81354_(new TranslatableComponent("commands.scoreboard.players.enable.success.multiple", p_138517_.m_83323_(), p_138516_.size()), true);
            }

            return i;
         }
      }
   }

   private static int m_138507_(CommandSourceStack p_138508_, Collection<String> p_138509_) {
      Scoreboard scoreboard = p_138508_.m_81377_().m_129896_();

      for(String s : p_138509_) {
         scoreboard.m_83479_(s, (Objective)null);
      }

      if (p_138509_.size() == 1) {
         p_138508_.m_81354_(new TranslatableComponent("commands.scoreboard.players.reset.all.single", p_138509_.iterator().next()), true);
      } else {
         p_138508_.m_81354_(new TranslatableComponent("commands.scoreboard.players.reset.all.multiple", p_138509_.size()), true);
      }

      return p_138509_.size();
   }

   private static int m_138540_(CommandSourceStack p_138541_, Collection<String> p_138542_, Objective p_138543_) {
      Scoreboard scoreboard = p_138541_.m_81377_().m_129896_();

      for(String s : p_138542_) {
         scoreboard.m_83479_(s, p_138543_);
      }

      if (p_138542_.size() == 1) {
         p_138541_.m_81354_(new TranslatableComponent("commands.scoreboard.players.reset.specific.single", p_138543_.m_83323_(), p_138542_.iterator().next()), true);
      } else {
         p_138541_.m_81354_(new TranslatableComponent("commands.scoreboard.players.reset.specific.multiple", p_138543_.m_83323_(), p_138542_.size()), true);
      }

      return p_138542_.size();
   }

   private static int m_138518_(CommandSourceStack p_138519_, Collection<String> p_138520_, Objective p_138521_, int p_138522_) {
      Scoreboard scoreboard = p_138519_.m_81377_().m_129896_();

      for(String s : p_138520_) {
         Score score = scoreboard.m_83471_(s, p_138521_);
         score.m_83402_(p_138522_);
      }

      if (p_138520_.size() == 1) {
         p_138519_.m_81354_(new TranslatableComponent("commands.scoreboard.players.set.success.single", p_138521_.m_83323_(), p_138520_.iterator().next(), p_138522_), true);
      } else {
         p_138519_.m_81354_(new TranslatableComponent("commands.scoreboard.players.set.success.multiple", p_138521_.m_83323_(), p_138520_.size(), p_138522_), true);
      }

      return p_138522_ * p_138520_.size();
   }

   private static int m_138544_(CommandSourceStack p_138545_, Collection<String> p_138546_, Objective p_138547_, int p_138548_) {
      Scoreboard scoreboard = p_138545_.m_81377_().m_129896_();
      int i = 0;

      for(String s : p_138546_) {
         Score score = scoreboard.m_83471_(s, p_138547_);
         score.m_83402_(score.m_83400_() + p_138548_);
         i += score.m_83400_();
      }

      if (p_138546_.size() == 1) {
         p_138545_.m_81354_(new TranslatableComponent("commands.scoreboard.players.add.success.single", p_138548_, p_138547_.m_83323_(), p_138546_.iterator().next(), i), true);
      } else {
         p_138545_.m_81354_(new TranslatableComponent("commands.scoreboard.players.add.success.multiple", p_138548_, p_138547_.m_83323_(), p_138546_.size()), true);
      }

      return i;
   }

   private static int m_138553_(CommandSourceStack p_138554_, Collection<String> p_138555_, Objective p_138556_, int p_138557_) {
      Scoreboard scoreboard = p_138554_.m_81377_().m_129896_();
      int i = 0;

      for(String s : p_138555_) {
         Score score = scoreboard.m_83471_(s, p_138556_);
         score.m_83402_(score.m_83400_() - p_138557_);
         i += score.m_83400_();
      }

      if (p_138555_.size() == 1) {
         p_138554_.m_81354_(new TranslatableComponent("commands.scoreboard.players.remove.success.single", p_138557_, p_138556_.m_83323_(), p_138555_.iterator().next(), i), true);
      } else {
         p_138554_.m_81354_(new TranslatableComponent("commands.scoreboard.players.remove.success.multiple", p_138557_, p_138556_.m_83323_(), p_138555_.size()), true);
      }

      return i;
   }

   private static int m_138475_(CommandSourceStack p_138476_) {
      Collection<String> collection = p_138476_.m_81377_().m_129896_().m_83482_();
      if (collection.isEmpty()) {
         p_138476_.m_81354_(new TranslatableComponent("commands.scoreboard.players.list.empty"), false);
      } else {
         p_138476_.m_81354_(new TranslatableComponent("commands.scoreboard.players.list.success", collection.size(), ComponentUtils.m_130743_(collection)), false);
      }

      return collection.size();
   }

   private static int m_138495_(CommandSourceStack p_138496_, String p_138497_) {
      Map<Objective, Score> map = p_138496_.m_81377_().m_129896_().m_83483_(p_138497_);
      if (map.isEmpty()) {
         p_138496_.m_81354_(new TranslatableComponent("commands.scoreboard.players.list.entity.empty", p_138497_), false);
      } else {
         p_138496_.m_81354_(new TranslatableComponent("commands.scoreboard.players.list.entity.success", p_138497_, map.size()), false);

         for(Entry<Objective, Score> entry : map.entrySet()) {
            p_138496_.m_81354_(new TranslatableComponent("commands.scoreboard.players.list.entity.entry", entry.getKey().m_83323_(), entry.getValue().m_83400_()), false);
         }
      }

      return map.size();
   }

   private static int m_138477_(CommandSourceStack p_138478_, int p_138479_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138478_.m_81377_().m_129896_();
      if (scoreboard.m_83416_(p_138479_) == null) {
         throw f_138461_.create();
      } else {
         scoreboard.m_7136_(p_138479_, (Objective)null);
         p_138478_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.display.cleared", Scoreboard.m_83494_()[p_138479_]), true);
         return 0;
      }
   }

   private static int m_138480_(CommandSourceStack p_138481_, int p_138482_, Objective p_138483_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138481_.m_81377_().m_129896_();
      if (scoreboard.m_83416_(p_138482_) == p_138483_) {
         throw f_138462_.create();
      } else {
         scoreboard.m_7136_(p_138482_, p_138483_);
         p_138481_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.display.set", Scoreboard.m_83494_()[p_138482_], p_138483_.m_83322_()), true);
         return 0;
      }
   }

   private static int m_138491_(CommandSourceStack p_138492_, Objective p_138493_, Component p_138494_) {
      if (!p_138493_.m_83322_().equals(p_138494_)) {
         p_138493_.m_83316_(p_138494_);
         p_138492_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.modify.displayname", p_138493_.m_83320_(), p_138493_.m_83323_()), true);
      }

      return 0;
   }

   private static int m_138487_(CommandSourceStack p_138488_, Objective p_138489_, ObjectiveCriteria.RenderType p_138490_) {
      if (p_138489_.m_83324_() != p_138490_) {
         p_138489_.m_83314_(p_138490_);
         p_138488_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.modify.rendertype", p_138489_.m_83323_()), true);
      }

      return 0;
   }

   private static int m_138484_(CommandSourceStack p_138485_, Objective p_138486_) {
      Scoreboard scoreboard = p_138485_.m_81377_().m_129896_();
      scoreboard.m_83502_(p_138486_);
      p_138485_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.remove.success", p_138486_.m_83323_()), true);
      return scoreboard.m_83466_().size();
   }

   private static int m_138502_(CommandSourceStack p_138503_, String p_138504_, ObjectiveCriteria p_138505_, Component p_138506_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138503_.m_81377_().m_129896_();
      if (scoreboard.m_83477_(p_138504_) != null) {
         throw f_138460_.create();
      } else if (p_138504_.length() > 16) {
         throw ObjectiveArgument.f_101951_.create(16);
      } else {
         scoreboard.m_83436_(p_138504_, p_138505_, p_138506_, p_138505_.m_83622_());
         Objective objective = scoreboard.m_83477_(p_138504_);
         p_138503_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.add.success", objective.m_83323_()), true);
         return scoreboard.m_83466_().size();
      }
   }

   private static int m_138538_(CommandSourceStack p_138539_) {
      Collection<Objective> collection = p_138539_.m_81377_().m_129896_().m_83466_();
      if (collection.isEmpty()) {
         p_138539_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.list.empty"), false);
      } else {
         p_138539_.m_81354_(new TranslatableComponent("commands.scoreboard.objectives.list.success", collection.size(), ComponentUtils.m_178440_(collection, Objective::m_83323_)), false);
      }

      return collection.size();
   }
}