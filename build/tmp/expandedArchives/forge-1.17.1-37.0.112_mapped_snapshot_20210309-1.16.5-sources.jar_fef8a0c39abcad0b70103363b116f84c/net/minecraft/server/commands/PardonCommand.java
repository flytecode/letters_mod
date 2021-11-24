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
import net.minecraft.server.players.UserBanList;

public class PardonCommand {
   private static final SimpleCommandExceptionType f_138091_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.pardon.failed"));

   public static void m_138093_(CommandDispatcher<CommandSourceStack> p_138094_) {
      p_138094_.register(Commands.m_82127_("pardon").requires((p_138101_) -> {
         return p_138101_.m_6761_(3);
      }).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_138098_, p_138099_) -> {
         return SharedSuggestionProvider.m_82967_(p_138098_.getSource().m_81377_().m_6846_().m_11295_().m_5875_(), p_138099_);
      }).executes((p_138096_) -> {
         return m_138102_(p_138096_.getSource(), GameProfileArgument.m_94590_(p_138096_, "targets"));
      })));
   }

   private static int m_138102_(CommandSourceStack p_138103_, Collection<GameProfile> p_138104_) throws CommandSyntaxException {
      UserBanList userbanlist = p_138103_.m_81377_().m_6846_().m_11295_();
      int i = 0;

      for(GameProfile gameprofile : p_138104_) {
         if (userbanlist.m_11406_(gameprofile)) {
            userbanlist.m_11393_(gameprofile);
            ++i;
            p_138103_.m_81354_(new TranslatableComponent("commands.pardon.success", ComponentUtils.m_130727_(gameprofile)), true);
         }
      }

      if (i == 0) {
         throw f_138091_.create();
      } else {
         return i;
      }
   }
}