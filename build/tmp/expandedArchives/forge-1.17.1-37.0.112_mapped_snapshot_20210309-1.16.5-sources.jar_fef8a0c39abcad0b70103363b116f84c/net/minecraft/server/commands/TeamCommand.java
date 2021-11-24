package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class TeamCommand {
   private static final SimpleCommandExceptionType f_138862_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.add.duplicate"));
   private static final DynamicCommandExceptionType f_138863_ = new DynamicCommandExceptionType((p_138921_) -> {
      return new TranslatableComponent("commands.team.add.longName", p_138921_);
   });
   private static final SimpleCommandExceptionType f_138864_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.empty.unchanged"));
   private static final SimpleCommandExceptionType f_138865_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.name.unchanged"));
   private static final SimpleCommandExceptionType f_138866_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.color.unchanged"));
   private static final SimpleCommandExceptionType f_138867_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.friendlyfire.alreadyEnabled"));
   private static final SimpleCommandExceptionType f_138868_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.friendlyfire.alreadyDisabled"));
   private static final SimpleCommandExceptionType f_138869_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
   private static final SimpleCommandExceptionType f_138870_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
   private static final SimpleCommandExceptionType f_138871_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.nametagVisibility.unchanged"));
   private static final SimpleCommandExceptionType f_138872_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.deathMessageVisibility.unchanged"));
   private static final SimpleCommandExceptionType f_138873_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.team.option.collisionRule.unchanged"));

   public static void m_138877_(CommandDispatcher<CommandSourceStack> p_138878_) {
      p_138878_.register(Commands.m_82127_("team").requires((p_138925_) -> {
         return p_138925_.m_6761_(2);
      }).then(Commands.m_82127_("list").executes((p_138876_) -> {
         return m_138881_(p_138876_.getSource());
      }).then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes((p_138995_) -> {
         return m_138943_(p_138995_.getSource(), TeamArgument.m_112091_(p_138995_, "team"));
      }))).then(Commands.m_82127_("add").then(Commands.m_82129_("team", StringArgumentType.word()).executes((p_138993_) -> {
         return m_138910_(p_138993_.getSource(), StringArgumentType.getString(p_138993_, "team"));
      }).then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes((p_138991_) -> {
         return m_138913_(p_138991_.getSource(), StringArgumentType.getString(p_138991_, "team"), ComponentArgument.m_87117_(p_138991_, "displayName"));
      })))).then(Commands.m_82127_("remove").then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes((p_138989_) -> {
         return m_138926_(p_138989_.getSource(), TeamArgument.m_112091_(p_138989_, "team"));
      }))).then(Commands.m_82127_("empty").then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes((p_138987_) -> {
         return m_138883_(p_138987_.getSource(), TeamArgument.m_112091_(p_138987_, "team"));
      }))).then(Commands.m_82127_("join").then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes((p_138985_) -> {
         return m_138894_(p_138985_.getSource(), TeamArgument.m_112091_(p_138985_, "team"), Collections.singleton(p_138985_.getSource().m_81374_().m_6302_()));
      }).then(Commands.m_82129_("members", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes((p_138983_) -> {
         return m_138894_(p_138983_.getSource(), TeamArgument.m_112091_(p_138983_, "team"), ScoreHolderArgument.m_108246_(p_138983_, "members"));
      })))).then(Commands.m_82127_("leave").then(Commands.m_82129_("members", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes((p_138981_) -> {
         return m_138917_(p_138981_.getSource(), ScoreHolderArgument.m_108246_(p_138981_, "members"));
      }))).then(Commands.m_82127_("modify").then(Commands.m_82129_("team", TeamArgument.m_112088_()).then(Commands.m_82127_("displayName").then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes((p_138979_) -> {
         return m_138902_(p_138979_.getSource(), TeamArgument.m_112091_(p_138979_, "team"), ComponentArgument.m_87117_(p_138979_, "displayName"));
      }))).then(Commands.m_82127_("color").then(Commands.m_82129_("value", ColorArgument.m_85463_()).executes((p_138977_) -> {
         return m_138898_(p_138977_.getSource(), TeamArgument.m_112091_(p_138977_, "team"), ColorArgument.m_85466_(p_138977_, "value"));
      }))).then(Commands.m_82127_("friendlyFire").then(Commands.m_82129_("allowed", BoolArgumentType.bool()).executes((p_138975_) -> {
         return m_138937_(p_138975_.getSource(), TeamArgument.m_112091_(p_138975_, "team"), BoolArgumentType.getBool(p_138975_, "allowed"));
      }))).then(Commands.m_82127_("seeFriendlyInvisibles").then(Commands.m_82129_("allowed", BoolArgumentType.bool()).executes((p_138973_) -> {
         return m_138906_(p_138973_.getSource(), TeamArgument.m_112091_(p_138973_, "team"), BoolArgumentType.getBool(p_138973_, "allowed"));
      }))).then(Commands.m_82127_("nametagVisibility").then(Commands.m_82127_("never").executes((p_138971_) -> {
         return m_138890_(p_138971_.getSource(), TeamArgument.m_112091_(p_138971_, "team"), Team.Visibility.NEVER);
      })).then(Commands.m_82127_("hideForOtherTeams").executes((p_138969_) -> {
         return m_138890_(p_138969_.getSource(), TeamArgument.m_112091_(p_138969_, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS);
      })).then(Commands.m_82127_("hideForOwnTeam").executes((p_138967_) -> {
         return m_138890_(p_138967_.getSource(), TeamArgument.m_112091_(p_138967_, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM);
      })).then(Commands.m_82127_("always").executes((p_138965_) -> {
         return m_138890_(p_138965_.getSource(), TeamArgument.m_112091_(p_138965_, "team"), Team.Visibility.ALWAYS);
      }))).then(Commands.m_82127_("deathMessageVisibility").then(Commands.m_82127_("never").executes((p_138963_) -> {
         return m_138929_(p_138963_.getSource(), TeamArgument.m_112091_(p_138963_, "team"), Team.Visibility.NEVER);
      })).then(Commands.m_82127_("hideForOtherTeams").executes((p_138961_) -> {
         return m_138929_(p_138961_.getSource(), TeamArgument.m_112091_(p_138961_, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS);
      })).then(Commands.m_82127_("hideForOwnTeam").executes((p_138959_) -> {
         return m_138929_(p_138959_.getSource(), TeamArgument.m_112091_(p_138959_, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM);
      })).then(Commands.m_82127_("always").executes((p_138957_) -> {
         return m_138929_(p_138957_.getSource(), TeamArgument.m_112091_(p_138957_, "team"), Team.Visibility.ALWAYS);
      }))).then(Commands.m_82127_("collisionRule").then(Commands.m_82127_("never").executes((p_138955_) -> {
         return m_138886_(p_138955_.getSource(), TeamArgument.m_112091_(p_138955_, "team"), Team.CollisionRule.NEVER);
      })).then(Commands.m_82127_("pushOwnTeam").executes((p_138953_) -> {
         return m_138886_(p_138953_.getSource(), TeamArgument.m_112091_(p_138953_, "team"), Team.CollisionRule.PUSH_OWN_TEAM);
      })).then(Commands.m_82127_("pushOtherTeams").executes((p_138951_) -> {
         return m_138886_(p_138951_.getSource(), TeamArgument.m_112091_(p_138951_, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS);
      })).then(Commands.m_82127_("always").executes((p_138942_) -> {
         return m_138886_(p_138942_.getSource(), TeamArgument.m_112091_(p_138942_, "team"), Team.CollisionRule.ALWAYS);
      }))).then(Commands.m_82127_("prefix").then(Commands.m_82129_("prefix", ComponentArgument.m_87114_()).executes((p_138923_) -> {
         return m_138933_(p_138923_.getSource(), TeamArgument.m_112091_(p_138923_, "team"), ComponentArgument.m_87117_(p_138923_, "prefix"));
      }))).then(Commands.m_82127_("suffix").then(Commands.m_82129_("suffix", ComponentArgument.m_87114_()).executes((p_138880_) -> {
         return m_138946_(p_138880_.getSource(), TeamArgument.m_112091_(p_138880_, "team"), ComponentArgument.m_87117_(p_138880_, "suffix"));
      }))))));
   }

   private static int m_138917_(CommandSourceStack p_138918_, Collection<String> p_138919_) {
      Scoreboard scoreboard = p_138918_.m_81377_().m_129896_();

      for(String s : p_138919_) {
         scoreboard.m_83495_(s);
      }

      if (p_138919_.size() == 1) {
         p_138918_.m_81354_(new TranslatableComponent("commands.team.leave.success.single", p_138919_.iterator().next()), true);
      } else {
         p_138918_.m_81354_(new TranslatableComponent("commands.team.leave.success.multiple", p_138919_.size()), true);
      }

      return p_138919_.size();
   }

   private static int m_138894_(CommandSourceStack p_138895_, PlayerTeam p_138896_, Collection<String> p_138897_) {
      Scoreboard scoreboard = p_138895_.m_81377_().m_129896_();

      for(String s : p_138897_) {
         scoreboard.m_6546_(s, p_138896_);
      }

      if (p_138897_.size() == 1) {
         p_138895_.m_81354_(new TranslatableComponent("commands.team.join.success.single", p_138897_.iterator().next(), p_138896_.m_83367_()), true);
      } else {
         p_138895_.m_81354_(new TranslatableComponent("commands.team.join.success.multiple", p_138897_.size(), p_138896_.m_83367_()), true);
      }

      return p_138897_.size();
   }

   private static int m_138890_(CommandSourceStack p_138891_, PlayerTeam p_138892_, Team.Visibility p_138893_) throws CommandSyntaxException {
      if (p_138892_.m_7470_() == p_138893_) {
         throw f_138871_.create();
      } else {
         p_138892_.m_83346_(p_138893_);
         p_138891_.m_81354_(new TranslatableComponent("commands.team.option.nametagVisibility.success", p_138892_.m_83367_(), p_138893_.m_83581_()), true);
         return 0;
      }
   }

   private static int m_138929_(CommandSourceStack p_138930_, PlayerTeam p_138931_, Team.Visibility p_138932_) throws CommandSyntaxException {
      if (p_138931_.m_7468_() == p_138932_) {
         throw f_138872_.create();
      } else {
         p_138931_.m_83358_(p_138932_);
         p_138930_.m_81354_(new TranslatableComponent("commands.team.option.deathMessageVisibility.success", p_138931_.m_83367_(), p_138932_.m_83581_()), true);
         return 0;
      }
   }

   private static int m_138886_(CommandSourceStack p_138887_, PlayerTeam p_138888_, Team.CollisionRule p_138889_) throws CommandSyntaxException {
      if (p_138888_.m_7156_() == p_138889_) {
         throw f_138873_.create();
      } else {
         p_138888_.m_83344_(p_138889_);
         p_138887_.m_81354_(new TranslatableComponent("commands.team.option.collisionRule.success", p_138888_.m_83367_(), p_138889_.m_83557_()), true);
         return 0;
      }
   }

   private static int m_138906_(CommandSourceStack p_138907_, PlayerTeam p_138908_, boolean p_138909_) throws CommandSyntaxException {
      if (p_138908_.m_6259_() == p_138909_) {
         if (p_138909_) {
            throw f_138869_.create();
         } else {
            throw f_138870_.create();
         }
      } else {
         p_138908_.m_83362_(p_138909_);
         p_138907_.m_81354_(new TranslatableComponent("commands.team.option.seeFriendlyInvisibles." + (p_138909_ ? "enabled" : "disabled"), p_138908_.m_83367_()), true);
         return 0;
      }
   }

   private static int m_138937_(CommandSourceStack p_138938_, PlayerTeam p_138939_, boolean p_138940_) throws CommandSyntaxException {
      if (p_138939_.m_6260_() == p_138940_) {
         if (p_138940_) {
            throw f_138867_.create();
         } else {
            throw f_138868_.create();
         }
      } else {
         p_138939_.m_83355_(p_138940_);
         p_138938_.m_81354_(new TranslatableComponent("commands.team.option.friendlyfire." + (p_138940_ ? "enabled" : "disabled"), p_138939_.m_83367_()), true);
         return 0;
      }
   }

   private static int m_138902_(CommandSourceStack p_138903_, PlayerTeam p_138904_, Component p_138905_) throws CommandSyntaxException {
      if (p_138904_.m_83364_().equals(p_138905_)) {
         throw f_138865_.create();
      } else {
         p_138904_.m_83353_(p_138905_);
         p_138903_.m_81354_(new TranslatableComponent("commands.team.option.name.success", p_138904_.m_83367_()), true);
         return 0;
      }
   }

   private static int m_138898_(CommandSourceStack p_138899_, PlayerTeam p_138900_, ChatFormatting p_138901_) throws CommandSyntaxException {
      if (p_138900_.m_7414_() == p_138901_) {
         throw f_138866_.create();
      } else {
         p_138900_.m_83351_(p_138901_);
         p_138899_.m_81354_(new TranslatableComponent("commands.team.option.color.success", p_138900_.m_83367_(), p_138901_.m_126666_()), true);
         return 0;
      }
   }

   private static int m_138883_(CommandSourceStack p_138884_, PlayerTeam p_138885_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138884_.m_81377_().m_129896_();
      Collection<String> collection = Lists.newArrayList(p_138885_.m_6809_());
      if (collection.isEmpty()) {
         throw f_138864_.create();
      } else {
         for(String s : collection) {
            scoreboard.m_6519_(s, p_138885_);
         }

         p_138884_.m_81354_(new TranslatableComponent("commands.team.empty.success", collection.size(), p_138885_.m_83367_()), true);
         return collection.size();
      }
   }

   private static int m_138926_(CommandSourceStack p_138927_, PlayerTeam p_138928_) {
      Scoreboard scoreboard = p_138927_.m_81377_().m_129896_();
      scoreboard.m_83475_(p_138928_);
      p_138927_.m_81354_(new TranslatableComponent("commands.team.remove.success", p_138928_.m_83367_()), true);
      return scoreboard.m_83491_().size();
   }

   private static int m_138910_(CommandSourceStack p_138911_, String p_138912_) throws CommandSyntaxException {
      return m_138913_(p_138911_, p_138912_, new TextComponent(p_138912_));
   }

   private static int m_138913_(CommandSourceStack p_138914_, String p_138915_, Component p_138916_) throws CommandSyntaxException {
      Scoreboard scoreboard = p_138914_.m_81377_().m_129896_();
      if (scoreboard.m_83489_(p_138915_) != null) {
         throw f_138862_.create();
      } else if (p_138915_.length() > 16) {
         throw f_138863_.create(16);
      } else {
         PlayerTeam playerteam = scoreboard.m_83492_(p_138915_);
         playerteam.m_83353_(p_138916_);
         p_138914_.m_81354_(new TranslatableComponent("commands.team.add.success", playerteam.m_83367_()), true);
         return scoreboard.m_83491_().size();
      }
   }

   private static int m_138943_(CommandSourceStack p_138944_, PlayerTeam p_138945_) {
      Collection<String> collection = p_138945_.m_6809_();
      if (collection.isEmpty()) {
         p_138944_.m_81354_(new TranslatableComponent("commands.team.list.members.empty", p_138945_.m_83367_()), false);
      } else {
         p_138944_.m_81354_(new TranslatableComponent("commands.team.list.members.success", p_138945_.m_83367_(), collection.size(), ComponentUtils.m_130743_(collection)), false);
      }

      return collection.size();
   }

   private static int m_138881_(CommandSourceStack p_138882_) {
      Collection<PlayerTeam> collection = p_138882_.m_81377_().m_129896_().m_83491_();
      if (collection.isEmpty()) {
         p_138882_.m_81354_(new TranslatableComponent("commands.team.list.teams.empty"), false);
      } else {
         p_138882_.m_81354_(new TranslatableComponent("commands.team.list.teams.success", collection.size(), ComponentUtils.m_178440_(collection, PlayerTeam::m_83367_)), false);
      }

      return collection.size();
   }

   private static int m_138933_(CommandSourceStack p_138934_, PlayerTeam p_138935_, Component p_138936_) {
      p_138935_.m_83360_(p_138936_);
      p_138934_.m_81354_(new TranslatableComponent("commands.team.option.prefix.success", p_138936_), false);
      return 1;
   }

   private static int m_138946_(CommandSourceStack p_138947_, PlayerTeam p_138948_, Component p_138949_) {
      p_138948_.m_83365_(p_138949_);
      p_138947_.m_81354_(new TranslatableComponent("commands.team.option.suffix.success", p_138949_), false);
      return 1;
   }
}