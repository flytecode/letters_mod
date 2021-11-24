package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;

public class WhitelistCommand {
   private static final SimpleCommandExceptionType f_139191_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.whitelist.alreadyOn"));
   private static final SimpleCommandExceptionType f_139192_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.whitelist.alreadyOff"));
   private static final SimpleCommandExceptionType f_139193_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.whitelist.add.failed"));
   private static final SimpleCommandExceptionType f_139194_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.whitelist.remove.failed"));

   public static void m_139201_(CommandDispatcher<CommandSourceStack> p_139202_) {
      p_139202_.register(Commands.m_82127_("whitelist").requires((p_139234_) -> {
         return p_139234_.m_6761_(3);
      }).then(Commands.m_82127_("on").executes((p_139236_) -> {
         return m_139218_(p_139236_.getSource());
      })).then(Commands.m_82127_("off").executes((p_139232_) -> {
         return m_139225_(p_139232_.getSource());
      })).then(Commands.m_82127_("list").executes((p_139228_) -> {
         return m_139229_(p_139228_.getSource());
      })).then(Commands.m_82127_("add").then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_139216_, p_139217_) -> {
         PlayerList playerlist = p_139216_.getSource().m_81377_().m_6846_();
         return SharedSuggestionProvider.m_82981_(playerlist.m_11314_().stream().filter((p_142794_) -> {
            return !playerlist.m_11305_().m_11453_(p_142794_.m_36316_());
         }).map((p_142791_) -> {
            return p_142791_.m_36316_().getName();
         }), p_139217_);
      }).executes((p_139224_) -> {
         return m_139210_(p_139224_.getSource(), GameProfileArgument.m_94590_(p_139224_, "targets"));
      }))).then(Commands.m_82127_("remove").then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_139206_, p_139207_) -> {
         return SharedSuggestionProvider.m_82967_(p_139206_.getSource().m_81377_().m_6846_().m_11306_(), p_139207_);
      }).executes((p_139214_) -> {
         return m_139220_(p_139214_.getSource(), GameProfileArgument.m_94590_(p_139214_, "targets"));
      }))).then(Commands.m_82127_("reload").executes((p_139204_) -> {
         return m_139208_(p_139204_.getSource());
      })));
   }

   private static int m_139208_(CommandSourceStack p_139209_) {
      p_139209_.m_81377_().m_6846_().m_7542_();
      p_139209_.m_81354_(new TranslatableComponent("commands.whitelist.reloaded"), true);
      p_139209_.m_81377_().m_129849_(p_139209_);
      return 1;
   }

   private static int m_139210_(CommandSourceStack p_139211_, Collection<GameProfile> p_139212_) throws CommandSyntaxException {
      UserWhiteList userwhitelist = p_139211_.m_81377_().m_6846_().m_11305_();
      int i = 0;

      for(GameProfile gameprofile : p_139212_) {
         if (!userwhitelist.m_11453_(gameprofile)) {
            UserWhiteListEntry userwhitelistentry = new UserWhiteListEntry(gameprofile);
            userwhitelist.m_11381_(userwhitelistentry);
            p_139211_.m_81354_(new TranslatableComponent("commands.whitelist.add.success", ComponentUtils.m_130727_(gameprofile)), true);
            ++i;
         }
      }

      if (i == 0) {
         throw f_139193_.create();
      } else {
         return i;
      }
   }

   private static int m_139220_(CommandSourceStack p_139221_, Collection<GameProfile> p_139222_) throws CommandSyntaxException {
      UserWhiteList userwhitelist = p_139221_.m_81377_().m_6846_().m_11305_();
      int i = 0;

      for(GameProfile gameprofile : p_139222_) {
         if (userwhitelist.m_11453_(gameprofile)) {
            UserWhiteListEntry userwhitelistentry = new UserWhiteListEntry(gameprofile);
            userwhitelist.m_11386_(userwhitelistentry);
            p_139221_.m_81354_(new TranslatableComponent("commands.whitelist.remove.success", ComponentUtils.m_130727_(gameprofile)), true);
            ++i;
         }
      }

      if (i == 0) {
         throw f_139194_.create();
      } else {
         p_139221_.m_81377_().m_129849_(p_139221_);
         return i;
      }
   }

   private static int m_139218_(CommandSourceStack p_139219_) throws CommandSyntaxException {
      PlayerList playerlist = p_139219_.m_81377_().m_6846_();
      if (playerlist.m_11311_()) {
         throw f_139191_.create();
      } else {
         playerlist.m_6628_(true);
         p_139219_.m_81354_(new TranslatableComponent("commands.whitelist.enabled"), true);
         p_139219_.m_81377_().m_129849_(p_139219_);
         return 1;
      }
   }

   private static int m_139225_(CommandSourceStack p_139226_) throws CommandSyntaxException {
      PlayerList playerlist = p_139226_.m_81377_().m_6846_();
      if (!playerlist.m_11311_()) {
         throw f_139192_.create();
      } else {
         playerlist.m_6628_(false);
         p_139226_.m_81354_(new TranslatableComponent("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int m_139229_(CommandSourceStack p_139230_) {
      String[] astring = p_139230_.m_81377_().m_6846_().m_11306_();
      if (astring.length == 0) {
         p_139230_.m_81354_(new TranslatableComponent("commands.whitelist.none"), false);
      } else {
         p_139230_.m_81354_(new TranslatableComponent("commands.whitelist.list", astring.length, String.join(", ", astring)), false);
      }

      return astring.length;
   }
}