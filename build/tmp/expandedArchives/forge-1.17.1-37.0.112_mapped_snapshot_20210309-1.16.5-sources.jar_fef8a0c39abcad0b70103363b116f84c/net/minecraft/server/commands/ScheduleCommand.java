package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.timers.FunctionCallback;
import net.minecraft.world.level.timers.FunctionTagCallback;
import net.minecraft.world.level.timers.TimerQueue;

public class ScheduleCommand {
   private static final SimpleCommandExceptionType f_138415_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType f_138416_ = new DynamicCommandExceptionType((p_138437_) -> {
      return new TranslatableComponent("commands.schedule.cleared.failure", p_138437_);
   });
   private static final SuggestionProvider<CommandSourceStack> f_138417_ = (p_138424_, p_138425_) -> {
      return SharedSuggestionProvider.m_82970_(p_138424_.getSource().m_81377_().m_129910_().m_5996_().m_7540_().m_82251_(), p_138425_);
   };

   public static void m_138419_(CommandDispatcher<CommandSourceStack> p_138420_) {
      p_138420_.register(Commands.m_82127_("schedule").requires((p_138427_) -> {
         return p_138427_.m_6761_(2);
      }).then(Commands.m_82127_("function").then(Commands.m_82129_("function", FunctionArgument.m_120907_()).suggests(FunctionCommand.f_137712_).then(Commands.m_82129_("time", TimeArgument.m_113037_()).executes((p_138459_) -> {
         return m_138428_(p_138459_.getSource(), FunctionArgument.m_120920_(p_138459_, "function"), IntegerArgumentType.getInteger(p_138459_, "time"), true);
      }).then(Commands.m_82127_("append").executes((p_138457_) -> {
         return m_138428_(p_138457_.getSource(), FunctionArgument.m_120920_(p_138457_, "function"), IntegerArgumentType.getInteger(p_138457_, "time"), false);
      })).then(Commands.m_82127_("replace").executes((p_138455_) -> {
         return m_138428_(p_138455_.getSource(), FunctionArgument.m_120920_(p_138455_, "function"), IntegerArgumentType.getInteger(p_138455_, "time"), true);
      }))))).then(Commands.m_82127_("clear").then(Commands.m_82129_("function", StringArgumentType.greedyString()).suggests(f_138417_).executes((p_138422_) -> {
         return m_138433_(p_138422_.getSource(), StringArgumentType.getString(p_138422_, "function"));
      }))));
   }

   private static int m_138428_(CommandSourceStack p_138429_, Pair<ResourceLocation, Either<CommandFunction, Tag<CommandFunction>>> p_138430_, int p_138431_, boolean p_138432_) throws CommandSyntaxException {
      if (p_138431_ == 0) {
         throw f_138415_.create();
      } else {
         long i = p_138429_.m_81372_().m_46467_() + (long)p_138431_;
         ResourceLocation resourcelocation = p_138430_.getFirst();
         TimerQueue<MinecraftServer> timerqueue = p_138429_.m_81377_().m_129910_().m_5996_().m_7540_();
         p_138430_.getSecond().ifLeft((p_138453_) -> {
            String s = resourcelocation.toString();
            if (p_138432_) {
               timerqueue.m_82259_(s);
            }

            timerqueue.m_82261_(s, i, new FunctionCallback(resourcelocation));
            p_138429_.m_81354_(new TranslatableComponent("commands.schedule.created.function", resourcelocation, p_138431_, i), true);
         }).ifRight((p_138445_) -> {
            String s = "#" + resourcelocation;
            if (p_138432_) {
               timerqueue.m_82259_(s);
            }

            timerqueue.m_82261_(s, i, new FunctionTagCallback(resourcelocation));
            p_138429_.m_81354_(new TranslatableComponent("commands.schedule.created.tag", resourcelocation, p_138431_, i), true);
         });
         return Math.floorMod(i, Integer.MAX_VALUE);
      }
   }

   private static int m_138433_(CommandSourceStack p_138434_, String p_138435_) throws CommandSyntaxException {
      int i = p_138434_.m_81377_().m_129910_().m_5996_().m_7540_().m_82259_(p_138435_);
      if (i == 0) {
         throw f_138416_.create(p_138435_);
      } else {
         p_138434_.m_81354_(new TranslatableComponent("commands.schedule.cleared.success", i, p_138435_), true);
         return i;
      }
   }
}