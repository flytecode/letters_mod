package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeCommand {
   private static final SimpleCommandExceptionType f_138197_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.recipe.give.failed"));
   private static final SimpleCommandExceptionType f_138198_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.recipe.take.failed"));

   public static void m_138200_(CommandDispatcher<CommandSourceStack> p_138201_) {
      p_138201_.register(Commands.m_82127_("recipe").requires((p_138205_) -> {
         return p_138205_.m_6761_(2);
      }).then(Commands.m_82127_("give").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("recipe", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121642_).executes((p_138219_) -> {
         return m_138206_(p_138219_.getSource(), EntityArgument.m_91477_(p_138219_, "targets"), Collections.singleton(ResourceLocationArgument.m_106994_(p_138219_, "recipe")));
      })).then(Commands.m_82127_("*").executes((p_138217_) -> {
         return m_138206_(p_138217_.getSource(), EntityArgument.m_91477_(p_138217_, "targets"), p_138217_.getSource().m_81377_().m_129894_().m_44051_());
      })))).then(Commands.m_82127_("take").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("recipe", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121642_).executes((p_138211_) -> {
         return m_138212_(p_138211_.getSource(), EntityArgument.m_91477_(p_138211_, "targets"), Collections.singleton(ResourceLocationArgument.m_106994_(p_138211_, "recipe")));
      })).then(Commands.m_82127_("*").executes((p_138203_) -> {
         return m_138212_(p_138203_.getSource(), EntityArgument.m_91477_(p_138203_, "targets"), p_138203_.getSource().m_81377_().m_129894_().m_44051_());
      })))));
   }

   private static int m_138206_(CommandSourceStack p_138207_, Collection<ServerPlayer> p_138208_, Collection<Recipe<?>> p_138209_) throws CommandSyntaxException {
      int i = 0;

      for(ServerPlayer serverplayer : p_138208_) {
         i += serverplayer.m_7281_(p_138209_);
      }

      if (i == 0) {
         throw f_138197_.create();
      } else {
         if (p_138208_.size() == 1) {
            p_138207_.m_81354_(new TranslatableComponent("commands.recipe.give.success.single", p_138209_.size(), p_138208_.iterator().next().m_5446_()), true);
         } else {
            p_138207_.m_81354_(new TranslatableComponent("commands.recipe.give.success.multiple", p_138209_.size(), p_138208_.size()), true);
         }

         return i;
      }
   }

   private static int m_138212_(CommandSourceStack p_138213_, Collection<ServerPlayer> p_138214_, Collection<Recipe<?>> p_138215_) throws CommandSyntaxException {
      int i = 0;

      for(ServerPlayer serverplayer : p_138214_) {
         i += serverplayer.m_7279_(p_138215_);
      }

      if (i == 0) {
         throw f_138198_.create();
      } else {
         if (p_138214_.size() == 1) {
            p_138213_.m_81354_(new TranslatableComponent("commands.recipe.take.success.single", p_138215_.size(), p_138214_.iterator().next().m_5446_()), true);
         } else {
            p_138213_.m_81354_(new TranslatableComponent("commands.recipe.take.success.multiple", p_138215_.size(), p_138214_.size()), true);
         }

         return i;
      }
   }
}