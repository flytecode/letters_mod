package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.Vec2;

public class WorldBorderCommand {
   private static final SimpleCommandExceptionType f_139237_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.center.failed"));
   private static final SimpleCommandExceptionType f_139238_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.set.failed.nochange"));
   private static final SimpleCommandExceptionType f_139239_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.set.failed.small"));
   private static final SimpleCommandExceptionType f_139240_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.set.failed.big", 5.9999968E7D));
   private static final SimpleCommandExceptionType f_139241_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.warning.time.failed"));
   private static final SimpleCommandExceptionType f_139242_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.warning.distance.failed"));
   private static final SimpleCommandExceptionType f_139243_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.damage.buffer.failed"));
   private static final SimpleCommandExceptionType f_139244_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.worldborder.damage.amount.failed"));

   public static void m_139246_(CommandDispatcher<CommandSourceStack> p_139247_) {
      p_139247_.register(Commands.m_82127_("worldborder").requires((p_139268_) -> {
         return p_139268_.m_6761_(2);
      }).then(Commands.m_82127_("add").then(Commands.m_82129_("distance", DoubleArgumentType.doubleArg(-5.9999968E7D, 5.9999968E7D)).executes((p_139290_) -> {
         return m_139252_(p_139290_.getSource(), p_139290_.getSource().m_81372_().m_6857_().m_61959_() + DoubleArgumentType.getDouble(p_139290_, "distance"), 0L);
      }).then(Commands.m_82129_("time", IntegerArgumentType.integer(0)).executes((p_139288_) -> {
         return m_139252_(p_139288_.getSource(), p_139288_.getSource().m_81372_().m_6857_().m_61959_() + DoubleArgumentType.getDouble(p_139288_, "distance"), p_139288_.getSource().m_81372_().m_6857_().m_61960_() + (long)IntegerArgumentType.getInteger(p_139288_, "time") * 1000L);
      })))).then(Commands.m_82127_("set").then(Commands.m_82129_("distance", DoubleArgumentType.doubleArg(-5.9999968E7D, 5.9999968E7D)).executes((p_139286_) -> {
         return m_139252_(p_139286_.getSource(), DoubleArgumentType.getDouble(p_139286_, "distance"), 0L);
      }).then(Commands.m_82129_("time", IntegerArgumentType.integer(0)).executes((p_139284_) -> {
         return m_139252_(p_139284_.getSource(), DoubleArgumentType.getDouble(p_139284_, "distance"), (long)IntegerArgumentType.getInteger(p_139284_, "time") * 1000L);
      })))).then(Commands.m_82127_("center").then(Commands.m_82129_("pos", Vec2Argument.m_120822_()).executes((p_139282_) -> {
         return m_139262_(p_139282_.getSource(), Vec2Argument.m_120825_(p_139282_, "pos"));
      }))).then(Commands.m_82127_("damage").then(Commands.m_82127_("amount").then(Commands.m_82129_("damagePerBlock", FloatArgumentType.floatArg(0.0F)).executes((p_139280_) -> {
         return m_139269_(p_139280_.getSource(), FloatArgumentType.getFloat(p_139280_, "damagePerBlock"));
      }))).then(Commands.m_82127_("buffer").then(Commands.m_82129_("distance", FloatArgumentType.floatArg(0.0F)).executes((p_139278_) -> {
         return m_139256_(p_139278_.getSource(), FloatArgumentType.getFloat(p_139278_, "distance"));
      })))).then(Commands.m_82127_("get").executes((p_139276_) -> {
         return m_139250_(p_139276_.getSource());
      })).then(Commands.m_82127_("warning").then(Commands.m_82127_("distance").then(Commands.m_82129_("distance", IntegerArgumentType.integer(0)).executes((p_139266_) -> {
         return m_139272_(p_139266_.getSource(), IntegerArgumentType.getInteger(p_139266_, "distance"));
      }))).then(Commands.m_82127_("time").then(Commands.m_82129_("time", IntegerArgumentType.integer(0)).executes((p_139249_) -> {
         return m_139259_(p_139249_.getSource(), IntegerArgumentType.getInteger(p_139249_, "time"));
      })))));
   }

   private static int m_139256_(CommandSourceStack p_139257_, float p_139258_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139257_.m_81372_().m_6857_();
      if (worldborder.m_61964_() == (double)p_139258_) {
         throw f_139243_.create();
      } else {
         worldborder.m_61939_((double)p_139258_);
         p_139257_.m_81354_(new TranslatableComponent("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", p_139258_)), true);
         return (int)p_139258_;
      }
   }

   private static int m_139269_(CommandSourceStack p_139270_, float p_139271_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139270_.m_81372_().m_6857_();
      if (worldborder.m_61965_() == (double)p_139271_) {
         throw f_139244_.create();
      } else {
         worldborder.m_61947_((double)p_139271_);
         p_139270_.m_81354_(new TranslatableComponent("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", p_139271_)), true);
         return (int)p_139271_;
      }
   }

   private static int m_139259_(CommandSourceStack p_139260_, int p_139261_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139260_.m_81372_().m_6857_();
      if (worldborder.m_61967_() == p_139261_) {
         throw f_139241_.create();
      } else {
         worldborder.m_61944_(p_139261_);
         p_139260_.m_81354_(new TranslatableComponent("commands.worldborder.warning.time.success", p_139261_), true);
         return p_139261_;
      }
   }

   private static int m_139272_(CommandSourceStack p_139273_, int p_139274_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139273_.m_81372_().m_6857_();
      if (worldborder.m_61968_() == p_139274_) {
         throw f_139242_.create();
      } else {
         worldborder.m_61952_(p_139274_);
         p_139273_.m_81354_(new TranslatableComponent("commands.worldborder.warning.distance.success", p_139274_), true);
         return p_139274_;
      }
   }

   private static int m_139250_(CommandSourceStack p_139251_) {
      double d0 = p_139251_.m_81372_().m_6857_().m_61959_();
      p_139251_.m_81354_(new TranslatableComponent("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", d0)), false);
      return Mth.m_14107_(d0 + 0.5D);
   }

   private static int m_139262_(CommandSourceStack p_139263_, Vec2 p_139264_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139263_.m_81372_().m_6857_();
      if (worldborder.m_6347_() == (double)p_139264_.f_82470_ && worldborder.m_6345_() == (double)p_139264_.f_82471_) {
         throw f_139237_.create();
      } else {
         worldborder.m_61949_((double)p_139264_.f_82470_, (double)p_139264_.f_82471_);
         p_139263_.m_81354_(new TranslatableComponent("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", p_139264_.f_82470_), String.format("%.2f", p_139264_.f_82471_)), true);
         return 0;
      }
   }

   private static int m_139252_(CommandSourceStack p_139253_, double p_139254_, long p_139255_) throws CommandSyntaxException {
      WorldBorder worldborder = p_139253_.m_81372_().m_6857_();
      double d0 = worldborder.m_61959_();
      if (d0 == p_139254_) {
         throw f_139238_.create();
      } else if (p_139254_ < 1.0D) {
         throw f_139239_.create();
      } else if (p_139254_ > 5.9999968E7D) {
         throw f_139240_.create();
      } else {
         if (p_139255_ > 0L) {
            worldborder.m_61919_(d0, p_139254_, p_139255_);
            if (p_139254_ > d0) {
               p_139253_.m_81354_(new TranslatableComponent("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", p_139254_), Long.toString(p_139255_ / 1000L)), true);
            } else {
               p_139253_.m_81354_(new TranslatableComponent("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", p_139254_), Long.toString(p_139255_ / 1000L)), true);
            }
         } else {
            worldborder.m_61917_(p_139254_);
            p_139253_.m_81354_(new TranslatableComponent("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", p_139254_)), true);
         }

         return (int)(p_139254_ - d0);
      }
   }
}