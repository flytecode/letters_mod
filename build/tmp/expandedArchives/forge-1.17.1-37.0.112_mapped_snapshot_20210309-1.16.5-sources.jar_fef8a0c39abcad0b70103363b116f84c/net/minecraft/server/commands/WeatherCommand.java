package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;

public class WeatherCommand {
   private static final int f_142787_ = 6000;

   public static void m_139166_(CommandDispatcher<CommandSourceStack> p_139167_) {
      p_139167_.register(Commands.m_82127_("weather").requires((p_139171_) -> {
         return p_139171_.m_6761_(2);
      }).then(Commands.m_82127_("clear").executes((p_139190_) -> {
         return m_139172_(p_139190_.getSource(), 6000);
      }).then(Commands.m_82129_("duration", IntegerArgumentType.integer(0, 1000000)).executes((p_139188_) -> {
         return m_139172_(p_139188_.getSource(), IntegerArgumentType.getInteger(p_139188_, "duration") * 20);
      }))).then(Commands.m_82127_("rain").executes((p_139186_) -> {
         return m_139177_(p_139186_.getSource(), 6000);
      }).then(Commands.m_82129_("duration", IntegerArgumentType.integer(0, 1000000)).executes((p_139181_) -> {
         return m_139177_(p_139181_.getSource(), IntegerArgumentType.getInteger(p_139181_, "duration") * 20);
      }))).then(Commands.m_82127_("thunder").executes((p_139176_) -> {
         return m_139182_(p_139176_.getSource(), 6000);
      }).then(Commands.m_82129_("duration", IntegerArgumentType.integer(0, 1000000)).executes((p_139169_) -> {
         return m_139182_(p_139169_.getSource(), IntegerArgumentType.getInteger(p_139169_, "duration") * 20);
      }))));
   }

   private static int m_139172_(CommandSourceStack p_139173_, int p_139174_) {
      p_139173_.m_81372_().m_8606_(p_139174_, 0, false, false);
      p_139173_.m_81354_(new TranslatableComponent("commands.weather.set.clear"), true);
      return p_139174_;
   }

   private static int m_139177_(CommandSourceStack p_139178_, int p_139179_) {
      p_139178_.m_81372_().m_8606_(0, p_139179_, true, false);
      p_139178_.m_81354_(new TranslatableComponent("commands.weather.set.rain"), true);
      return p_139179_;
   }

   private static int m_139182_(CommandSourceStack p_139183_, int p_139184_) {
      p_139183_.m_81372_().m_8606_(0, p_139184_, true, true);
      p_139183_.m_81354_(new TranslatableComponent("commands.weather.set.thunder"), true);
      return p_139184_;
   }
}