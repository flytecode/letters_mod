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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.PlayerList;

public class DeOpCommands {
   private static final SimpleCommandExceptionType f_136886_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.deop.failed"));

   public static void m_136888_(CommandDispatcher<CommandSourceStack> p_136889_) {
      p_136889_.register(Commands.m_82127_("deop").requires((p_136896_) -> {
         return p_136896_.m_6761_(3);
      }).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_136893_, p_136894_) -> {
         return SharedSuggestionProvider.m_82967_(p_136893_.getSource().m_81377_().m_6846_().m_11308_(), p_136894_);
      }).executes((p_136891_) -> {
         return m_136897_(p_136891_.getSource(), GameProfileArgument.m_94590_(p_136891_, "targets"));
      })));
   }

   private static int m_136897_(CommandSourceStack p_136898_, Collection<GameProfile> p_136899_) throws CommandSyntaxException {
      PlayerList playerlist = p_136898_.m_81377_().m_6846_();
      int i = 0;

      for(GameProfile gameprofile : p_136899_) {
         if (playerlist.m_11303_(gameprofile)) {
            playerlist.m_5750_(gameprofile);
            ++i;
            p_136898_.m_81354_(new TranslatableComponent("commands.deop.success", p_136899_.iterator().next().getName()), true);
         }
      }

      if (i == 0) {
         throw f_136886_.create();
      } else {
         p_136898_.m_81377_().m_129849_(p_136898_);
         return i;
      }
   }
}