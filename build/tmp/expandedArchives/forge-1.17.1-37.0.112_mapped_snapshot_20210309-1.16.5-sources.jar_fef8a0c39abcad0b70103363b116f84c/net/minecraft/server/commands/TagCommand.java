package net.minecraft.server.commands;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;

public class TagCommand {
   private static final SimpleCommandExceptionType f_138833_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType f_138834_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.tag.remove.failed"));

   public static void m_138836_(CommandDispatcher<CommandSourceStack> p_138837_) {
      p_138837_.register(Commands.m_82127_("tag").requires((p_138844_) -> {
         return p_138844_.m_6761_(2);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82127_("add").then(Commands.m_82129_("name", StringArgumentType.word()).executes((p_138861_) -> {
         return m_138848_(p_138861_.getSource(), EntityArgument.m_91461_(p_138861_, "targets"), StringArgumentType.getString(p_138861_, "name"));
      }))).then(Commands.m_82127_("remove").then(Commands.m_82129_("name", StringArgumentType.word()).suggests((p_138841_, p_138842_) -> {
         return SharedSuggestionProvider.m_82970_(m_138852_(EntityArgument.m_91461_(p_138841_, "targets")), p_138842_);
      }).executes((p_138855_) -> {
         return m_138856_(p_138855_.getSource(), EntityArgument.m_91461_(p_138855_, "targets"), StringArgumentType.getString(p_138855_, "name"));
      }))).then(Commands.m_82127_("list").executes((p_138839_) -> {
         return m_138845_(p_138839_.getSource(), EntityArgument.m_91461_(p_138839_, "targets"));
      }))));
   }

   private static Collection<String> m_138852_(Collection<? extends Entity> p_138853_) {
      Set<String> set = Sets.newHashSet();

      for(Entity entity : p_138853_) {
         set.addAll(entity.m_19880_());
      }

      return set;
   }

   private static int m_138848_(CommandSourceStack p_138849_, Collection<? extends Entity> p_138850_, String p_138851_) throws CommandSyntaxException {
      int i = 0;

      for(Entity entity : p_138850_) {
         if (entity.m_20049_(p_138851_)) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_138833_.create();
      } else {
         if (p_138850_.size() == 1) {
            p_138849_.m_81354_(new TranslatableComponent("commands.tag.add.success.single", p_138851_, p_138850_.iterator().next().m_5446_()), true);
         } else {
            p_138849_.m_81354_(new TranslatableComponent("commands.tag.add.success.multiple", p_138851_, p_138850_.size()), true);
         }

         return i;
      }
   }

   private static int m_138856_(CommandSourceStack p_138857_, Collection<? extends Entity> p_138858_, String p_138859_) throws CommandSyntaxException {
      int i = 0;

      for(Entity entity : p_138858_) {
         if (entity.m_20137_(p_138859_)) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_138834_.create();
      } else {
         if (p_138858_.size() == 1) {
            p_138857_.m_81354_(new TranslatableComponent("commands.tag.remove.success.single", p_138859_, p_138858_.iterator().next().m_5446_()), true);
         } else {
            p_138857_.m_81354_(new TranslatableComponent("commands.tag.remove.success.multiple", p_138859_, p_138858_.size()), true);
         }

         return i;
      }
   }

   private static int m_138845_(CommandSourceStack p_138846_, Collection<? extends Entity> p_138847_) {
      Set<String> set = Sets.newHashSet();

      for(Entity entity : p_138847_) {
         set.addAll(entity.m_19880_());
      }

      if (p_138847_.size() == 1) {
         Entity entity1 = p_138847_.iterator().next();
         if (set.isEmpty()) {
            p_138846_.m_81354_(new TranslatableComponent("commands.tag.list.single.empty", entity1.m_5446_()), false);
         } else {
            p_138846_.m_81354_(new TranslatableComponent("commands.tag.list.single.success", entity1.m_5446_(), set.size(), ComponentUtils.m_130743_(set)), false);
         }
      } else if (set.isEmpty()) {
         p_138846_.m_81354_(new TranslatableComponent("commands.tag.list.multiple.empty", p_138847_.size()), false);
      } else {
         p_138846_.m_81354_(new TranslatableComponent("commands.tag.list.multiple.success", p_138847_.size(), set.size(), ComponentUtils.m_130743_(set)), false);
      }

      return set.size();
   }
}