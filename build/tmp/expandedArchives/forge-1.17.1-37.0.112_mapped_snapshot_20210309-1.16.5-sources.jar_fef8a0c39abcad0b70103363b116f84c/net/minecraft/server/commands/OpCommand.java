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

public class OpCommand {
   private static final SimpleCommandExceptionType f_138072_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.op.failed"));

   public static void m_138079_(CommandDispatcher<CommandSourceStack> p_138080_) {
      p_138080_.register(Commands.m_82127_("op").requires((p_138087_) -> {
         return p_138087_.m_6761_(3);
      }).then(Commands.m_82129_("targets", GameProfileArgument.m_94584_()).suggests((p_138084_, p_138085_) -> {
         PlayerList playerlist = p_138084_.getSource().m_81377_().m_6846_();
         return SharedSuggestionProvider.m_82981_(playerlist.m_11314_().stream().filter((p_180428_) -> {
            return !playerlist.m_11303_(p_180428_.m_36316_());
         }).map((p_180425_) -> {
            return p_180425_.m_36316_().getName();
         }), p_138085_);
      }).executes((p_138082_) -> {
         return m_138088_(p_138082_.getSource(), GameProfileArgument.m_94590_(p_138082_, "targets"));
      })));
   }

   private static int m_138088_(CommandSourceStack p_138089_, Collection<GameProfile> p_138090_) throws CommandSyntaxException {
      PlayerList playerlist = p_138089_.m_81377_().m_6846_();
      int i = 0;

      for(GameProfile gameprofile : p_138090_) {
         if (!playerlist.m_11303_(gameprofile)) {
            playerlist.m_5749_(gameprofile);
            ++i;
            p_138089_.m_81354_(new TranslatableComponent("commands.op.success", p_138090_.iterator().next().getName()), true);
         }
      }

      if (i == 0) {
         throw f_138072_.create();
      } else {
         return i;
      }
   }
}