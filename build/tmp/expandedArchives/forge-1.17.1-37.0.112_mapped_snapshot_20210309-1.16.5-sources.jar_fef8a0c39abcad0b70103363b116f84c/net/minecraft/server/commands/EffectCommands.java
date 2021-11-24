package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EffectCommands {
   private static final SimpleCommandExceptionType f_136949_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.effect.give.failed"));
   private static final SimpleCommandExceptionType f_136950_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.effect.clear.everything.failed"));
   private static final SimpleCommandExceptionType f_136951_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.effect.clear.specific.failed"));

   public static void m_136953_(CommandDispatcher<CommandSourceStack> p_136954_) {
      p_136954_.register(Commands.m_82127_("effect").requires((p_136958_) -> {
         return p_136958_.m_6761_(2);
      }).then(Commands.m_82127_("clear").executes((p_136984_) -> {
         return m_136959_(p_136984_.getSource(), ImmutableList.of(p_136984_.getSource().m_81374_()));
      }).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes((p_136982_) -> {
         return m_136959_(p_136982_.getSource(), EntityArgument.m_91461_(p_136982_, "targets"));
      }).then(Commands.m_82129_("effect", MobEffectArgument.m_98426_()).executes((p_136980_) -> {
         return m_136962_(p_136980_.getSource(), EntityArgument.m_91461_(p_136980_, "targets"), MobEffectArgument.m_98429_(p_136980_, "effect"));
      })))).then(Commands.m_82127_("give").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("effect", MobEffectArgument.m_98426_()).executes((p_136978_) -> {
         return m_136966_(p_136978_.getSource(), EntityArgument.m_91461_(p_136978_, "targets"), MobEffectArgument.m_98429_(p_136978_, "effect"), (Integer)null, 0, true);
      }).then(Commands.m_82129_("seconds", IntegerArgumentType.integer(1, 1000000)).executes((p_136976_) -> {
         return m_136966_(p_136976_.getSource(), EntityArgument.m_91461_(p_136976_, "targets"), MobEffectArgument.m_98429_(p_136976_, "effect"), IntegerArgumentType.getInteger(p_136976_, "seconds"), 0, true);
      }).then(Commands.m_82129_("amplifier", IntegerArgumentType.integer(0, 255)).executes((p_136974_) -> {
         return m_136966_(p_136974_.getSource(), EntityArgument.m_91461_(p_136974_, "targets"), MobEffectArgument.m_98429_(p_136974_, "effect"), IntegerArgumentType.getInteger(p_136974_, "seconds"), IntegerArgumentType.getInteger(p_136974_, "amplifier"), true);
      }).then(Commands.m_82129_("hideParticles", BoolArgumentType.bool()).executes((p_136956_) -> {
         return m_136966_(p_136956_.getSource(), EntityArgument.m_91461_(p_136956_, "targets"), MobEffectArgument.m_98429_(p_136956_, "effect"), IntegerArgumentType.getInteger(p_136956_, "seconds"), IntegerArgumentType.getInteger(p_136956_, "amplifier"), !BoolArgumentType.getBool(p_136956_, "hideParticles"));
      }))))))));
   }

   private static int m_136966_(CommandSourceStack p_136967_, Collection<? extends Entity> p_136968_, MobEffect p_136969_, @Nullable Integer p_136970_, int p_136971_, boolean p_136972_) throws CommandSyntaxException {
      int i = 0;
      int j;
      if (p_136970_ != null) {
         if (p_136969_.m_8093_()) {
            j = p_136970_;
         } else {
            j = p_136970_ * 20;
         }
      } else if (p_136969_.m_8093_()) {
         j = 1;
      } else {
         j = 600;
      }

      for(Entity entity : p_136968_) {
         if (entity instanceof LivingEntity) {
            MobEffectInstance mobeffectinstance = new MobEffectInstance(p_136969_, j, p_136971_, false, p_136972_);
            if (((LivingEntity)entity).m_147207_(mobeffectinstance, p_136967_.m_81373_())) {
               ++i;
            }
         }
      }

      if (i == 0) {
         throw f_136949_.create();
      } else {
         if (p_136968_.size() == 1) {
            p_136967_.m_81354_(new TranslatableComponent("commands.effect.give.success.single", p_136969_.m_19482_(), p_136968_.iterator().next().m_5446_(), j / 20), true);
         } else {
            p_136967_.m_81354_(new TranslatableComponent("commands.effect.give.success.multiple", p_136969_.m_19482_(), p_136968_.size(), j / 20), true);
         }

         return i;
      }
   }

   private static int m_136959_(CommandSourceStack p_136960_, Collection<? extends Entity> p_136961_) throws CommandSyntaxException {
      int i = 0;

      for(Entity entity : p_136961_) {
         if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21219_()) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_136950_.create();
      } else {
         if (p_136961_.size() == 1) {
            p_136960_.m_81354_(new TranslatableComponent("commands.effect.clear.everything.success.single", p_136961_.iterator().next().m_5446_()), true);
         } else {
            p_136960_.m_81354_(new TranslatableComponent("commands.effect.clear.everything.success.multiple", p_136961_.size()), true);
         }

         return i;
      }
   }

   private static int m_136962_(CommandSourceStack p_136963_, Collection<? extends Entity> p_136964_, MobEffect p_136965_) throws CommandSyntaxException {
      int i = 0;

      for(Entity entity : p_136964_) {
         if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21195_(p_136965_)) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_136951_.create();
      } else {
         if (p_136964_.size() == 1) {
            p_136963_.m_81354_(new TranslatableComponent("commands.effect.clear.specific.success.single", p_136965_.m_19482_(), p_136964_.iterator().next().m_5446_()), true);
         } else {
            p_136963_.m_81354_(new TranslatableComponent("commands.effect.clear.specific.success.multiple", p_136965_.m_19482_(), p_136964_.size()), true);
         }

         return i;
      }
   }
}