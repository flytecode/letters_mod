package net.minecraft.server.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;

public class BanPlayerCommands {
   private static final SimpleCommandExceptionType f_136556_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.ban.failed"));

   public static void m_136558_(CommandDispatcher<CommandSourceStack> p_136559_) {
      p_136559_.register(Commands.m_82127_("ban").requires((p_136563_) -> {
         return p_136563_.m_6761_(3);
      }).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).executes((p_136569_) -> {
         return m_136564_(p_136569_.getSource(), GameProfileArgument.m_94590_(p_136569_, "targets"), (Component)null);
      }).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes((p_136561_) -> {
         return m_136564_(p_136561_.getSource(), GameProfileArgument.m_94590_(p_136561_, "targets"), MessageArgument.m_96835_(p_136561_, "reason"));
      }))));
   }

   private static int m_136564_(CommandSourceStack p_136565_, Collection<GameProfile> p_136566_, @Nullable Component p_136567_) throws CommandSyntaxException {
      UserBanList userbanlist = p_136565_.m_81377_().m_6846_().m_11295_();
      int i = 0;

      for(GameProfile gameprofile : p_136566_) {
         if (!userbanlist.m_11406_(gameprofile)) {
            UserBanListEntry userbanlistentry = new UserBanListEntry(gameprofile, (Date)null, p_136565_.m_81368_(), (Date)null, p_136567_ == null ? null : p_136567_.getString());
            userbanlist.m_11381_(userbanlistentry);
            ++i;
            p_136565_.m_81354_(new TranslatableComponent("commands.ban.success", ComponentUtils.m_130727_(gameprofile), userbanlistentry.m_10962_()), true);
            ServerPlayer serverplayer = p_136565_.m_81377_().m_6846_().m_11259_(gameprofile.getId());
            if (serverplayer != null) {
               serverplayer.f_8906_.m_9942_(new TranslatableComponent("multiplayer.disconnect.banned"));
            }
         }
      }

      if (i == 0) {
         throw f_136556_.create();
      } else {
         return i;
      }
   }
}