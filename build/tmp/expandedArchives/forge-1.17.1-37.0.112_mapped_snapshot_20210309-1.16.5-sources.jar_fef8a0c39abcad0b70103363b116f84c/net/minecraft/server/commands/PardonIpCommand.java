package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.regex.Matcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.IpBanList;

public class PardonIpCommand {
   private static final SimpleCommandExceptionType f_138105_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType f_138106_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardonip.failed"));

   public static void m_138108_(CommandDispatcher<CommandSourceStack> p_138109_) {
      p_138109_.register(Commands.m_82127_("pardon-ip").requires((p_138116_) -> {
         return p_138116_.m_6761_(3);
      }).then(Commands.m_82129_("target", StringArgumentType.word()).suggests((p_138113_, p_138114_) -> {
         return SharedSuggestionProvider.m_82967_(p_138113_.getSource().m_81377_().m_6846_().m_11299_().m_5875_(), p_138114_);
      }).executes((p_138111_) -> {
         return m_138117_(p_138111_.getSource(), StringArgumentType.getString(p_138111_, "target"));
      })));
   }

   private static int m_138117_(CommandSourceStack p_138118_, String p_138119_) throws CommandSyntaxException {
      Matcher matcher = BanIpCommands.f_136523_.matcher(p_138119_);
      if (!matcher.matches()) {
         throw f_138105_.create();
      } else {
         IpBanList ipbanlist = p_138118_.m_81377_().m_6846_().m_11299_();
         if (!ipbanlist.m_11039_(p_138119_)) {
            throw f_138106_.create();
         } else {
            ipbanlist.m_11393_(p_138119_);
            p_138118_.m_81354_(new TranslatableComponent("commands.pardonip.success", p_138119_), true);
            return 1;
         }
      }
   }
}