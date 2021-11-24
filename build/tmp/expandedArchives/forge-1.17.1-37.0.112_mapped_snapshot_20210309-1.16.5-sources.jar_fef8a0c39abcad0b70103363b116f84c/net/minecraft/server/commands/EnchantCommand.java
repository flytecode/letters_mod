package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantCommand {
   private static final DynamicCommandExceptionType f_137002_ = new DynamicCommandExceptionType((p_137029_) -> {
      return new TranslatableComponent("commands.enchant.failed.entity", p_137029_);
   });
   private static final DynamicCommandExceptionType f_137003_ = new DynamicCommandExceptionType((p_137027_) -> {
      return new TranslatableComponent("commands.enchant.failed.itemless", p_137027_);
   });
   private static final DynamicCommandExceptionType f_137004_ = new DynamicCommandExceptionType((p_137020_) -> {
      return new TranslatableComponent("commands.enchant.failed.incompatible", p_137020_);
   });
   private static final Dynamic2CommandExceptionType f_137005_ = new Dynamic2CommandExceptionType((p_137022_, p_137023_) -> {
      return new TranslatableComponent("commands.enchant.failed.level", p_137022_, p_137023_);
   });
   private static final SimpleCommandExceptionType f_137006_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.enchant.failed"));

   public static void m_137008_(CommandDispatcher<CommandSourceStack> p_137009_) {
      p_137009_.register(Commands.m_82127_("enchant").requires((p_137013_) -> {
         return p_137013_.m_6761_(2);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("enchantment", ItemEnchantmentArgument.m_95260_()).executes((p_137025_) -> {
         return m_137014_(p_137025_.getSource(), EntityArgument.m_91461_(p_137025_, "targets"), ItemEnchantmentArgument.m_95263_(p_137025_, "enchantment"), 1);
      }).then(Commands.m_82129_("level", IntegerArgumentType.integer(0)).executes((p_137011_) -> {
         return m_137014_(p_137011_.getSource(), EntityArgument.m_91461_(p_137011_, "targets"), ItemEnchantmentArgument.m_95263_(p_137011_, "enchantment"), IntegerArgumentType.getInteger(p_137011_, "level"));
      })))));
   }

   private static int m_137014_(CommandSourceStack p_137015_, Collection<? extends Entity> p_137016_, Enchantment p_137017_, int p_137018_) throws CommandSyntaxException {
      if (p_137018_ > p_137017_.m_6586_()) {
         throw f_137005_.create(p_137018_, p_137017_.m_6586_());
      } else {
         int i = 0;

         for(Entity entity : p_137016_) {
            if (entity instanceof LivingEntity) {
               LivingEntity livingentity = (LivingEntity)entity;
               ItemStack itemstack = livingentity.m_21205_();
               if (!itemstack.m_41619_()) {
                  if (p_137017_.m_6081_(itemstack) && EnchantmentHelper.m_44859_(EnchantmentHelper.m_44831_(itemstack).keySet(), p_137017_)) {
                     itemstack.m_41663_(p_137017_, p_137018_);
                     ++i;
                  } else if (p_137016_.size() == 1) {
                     throw f_137004_.create(itemstack.m_41720_().m_7626_(itemstack).getString());
                  }
               } else if (p_137016_.size() == 1) {
                  throw f_137003_.create(livingentity.m_7755_().getString());
               }
            } else if (p_137016_.size() == 1) {
               throw f_137002_.create(entity.m_7755_().getString());
            }
         }

         if (i == 0) {
            throw f_137006_.create();
         } else {
            if (p_137016_.size() == 1) {
               p_137015_.m_81354_(new TranslatableComponent("commands.enchant.success.single", p_137017_.m_44700_(p_137018_), p_137016_.iterator().next().m_5446_()), true);
            } else {
               p_137015_.m_81354_(new TranslatableComponent("commands.enchant.success.multiple", p_137017_.m_44700_(p_137018_), p_137016_.size()), true);
            }

            return i;
         }
      }
   }
}