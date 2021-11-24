package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ClearInventoryCommands {
   private static final DynamicCommandExceptionType f_136694_ = new DynamicCommandExceptionType((p_136717_) -> {
      return new TranslatableComponent("clear.failed.single", p_136717_);
   });
   private static final DynamicCommandExceptionType f_136695_ = new DynamicCommandExceptionType((p_136711_) -> {
      return new TranslatableComponent("clear.failed.multiple", p_136711_);
   });

   public static void m_136699_(CommandDispatcher<CommandSourceStack> p_136700_) {
      p_136700_.register(Commands.m_82127_("clear").requires((p_136704_) -> {
         return p_136704_.m_6761_(2);
      }).executes((p_136721_) -> {
         return m_136705_(p_136721_.getSource(), Collections.singleton(p_136721_.getSource().m_81375_()), (p_180029_) -> {
            return true;
         }, -1);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((p_136719_) -> {
         return m_136705_(p_136719_.getSource(), EntityArgument.m_91477_(p_136719_, "targets"), (p_180027_) -> {
            return true;
         }, -1);
      }).then(Commands.m_82129_("item", ItemPredicateArgument.m_121037_()).executes((p_136715_) -> {
         return m_136705_(p_136715_.getSource(), EntityArgument.m_91477_(p_136715_, "targets"), ItemPredicateArgument.m_121040_(p_136715_, "item"), -1);
      }).then(Commands.m_82129_("maxCount", IntegerArgumentType.integer(0)).executes((p_136702_) -> {
         return m_136705_(p_136702_.getSource(), EntityArgument.m_91477_(p_136702_, "targets"), ItemPredicateArgument.m_121040_(p_136702_, "item"), IntegerArgumentType.getInteger(p_136702_, "maxCount"));
      })))));
   }

   private static int m_136705_(CommandSourceStack p_136706_, Collection<ServerPlayer> p_136707_, Predicate<ItemStack> p_136708_, int p_136709_) throws CommandSyntaxException {
      int i = 0;

      for(ServerPlayer serverplayer : p_136707_) {
         i += serverplayer.m_150109_().m_36022_(p_136708_, p_136709_, serverplayer.f_36095_.m_39730_());
         serverplayer.f_36096_.m_38946_();
         serverplayer.f_36095_.m_6199_(serverplayer.m_150109_());
      }

      if (i == 0) {
         if (p_136707_.size() == 1) {
            throw f_136694_.create(p_136707_.iterator().next().m_7755_());
         } else {
            throw f_136695_.create(p_136707_.size());
         }
      } else {
         if (p_136709_ == 0) {
            if (p_136707_.size() == 1) {
               p_136706_.m_81354_(new TranslatableComponent("commands.clear.test.single", i, p_136707_.iterator().next().m_5446_()), true);
            } else {
               p_136706_.m_81354_(new TranslatableComponent("commands.clear.test.multiple", i, p_136707_.size()), true);
            }
         } else if (p_136707_.size() == 1) {
            p_136706_.m_81354_(new TranslatableComponent("commands.clear.success.single", i, p_136707_.iterator().next().m_5446_()), true);
         } else {
            p_136706_.m_81354_(new TranslatableComponent("commands.clear.success.multiple", i, p_136707_.size()), true);
         }

         return i;
      }
   }
}