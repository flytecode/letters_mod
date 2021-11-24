package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.IpBanList;
import net.minecraft.server.players.IpBanListEntry;

public class BanIpCommands {
   public static final Pattern f_136523_ = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
   private static final SimpleCommandExceptionType f_136524_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.banip.invalid"));
   private static final SimpleCommandExceptionType f_136525_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.banip.failed"));

   public static void m_136527_(CommandDispatcher<CommandSourceStack> p_136528_) {
      p_136528_.register(Commands.m_82127_("ban-ip").requires((p_136532_) -> {
         return p_136532_.m_6761_(3);
      }).then(Commands.m_82129_("target", StringArgumentType.word()).executes((p_136538_) -> {
         return m_136533_(p_136538_.getSource(), StringArgumentType.getString(p_136538_, "target"), (Component)null);
      }).then(Commands.m_82129_("reason", MessageArgument.m_96832_()).executes((p_136530_) -> {
         return m_136533_(p_136530_.getSource(), StringArgumentType.getString(p_136530_, "target"), MessageArgument.m_96835_(p_136530_, "reason"));
      }))));
   }

   private static int m_136533_(CommandSourceStack p_136534_, String p_136535_, @Nullable Component p_136536_) throws CommandSyntaxException {
      Matcher matcher = f_136523_.matcher(p_136535_);
      if (matcher.matches()) {
         return m_136539_(p_136534_, p_136535_, p_136536_);
      } else {
         ServerPlayer serverplayer = p_136534_.m_81377_().m_6846_().m_11255_(p_136535_);
         if (serverplayer != null) {
            return m_136539_(p_136534_, serverplayer.m_9239_(), p_136536_);
         } else {
            throw f_136524_.create();
         }
      }
   }

   private static int m_136539_(CommandSourceStack p_136540_, String p_136541_, @Nullable Component p_136542_) throws CommandSyntaxException {
      IpBanList ipbanlist = p_136540_.m_81377_().m_6846_().m_11299_();
      if (ipbanlist.m_11039_(p_136541_)) {
         throw f_136525_.create();
      } else {
         List<ServerPlayer> list = p_136540_.m_81377_().m_6846_().m_11282_(p_136541_);
         IpBanListEntry ipbanlistentry = new IpBanListEntry(p_136541_, (Date)null, p_136540_.m_81368_(), (Date)null, p_136542_ == null ? null : p_136542_.getString());
         ipbanlist.m_11381_(ipbanlistentry);
         p_136540_.m_81354_(new TranslatableComponent("commands.banip.success", p_136541_, ipbanlistentry.m_10962_()), true);
         if (!list.isEmpty()) {
            p_136540_.m_81354_(new TranslatableComponent("commands.banip.info", list.size(), EntitySelector.m_175103_(list)), true);
         }

         for(ServerPlayer serverplayer : list) {
            serverplayer.f_8906_.m_9942_(new TranslatableComponent("multiplayer.disconnect.ip_banned"));
         }

         return list.size();
      }
   }
}