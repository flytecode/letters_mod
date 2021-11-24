package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class AngleArgument implements ArgumentType<AngleArgument.SingleAngle> {
   private static final Collection<String> f_83804_ = Arrays.asList("0", "~", "~-5");
   public static final SimpleCommandExceptionType f_83803_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.angle.incomplete"));
   public static final SimpleCommandExceptionType f_166217_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.angle.invalid"));

   public static AngleArgument m_83807_() {
      return new AngleArgument();
   }

   public static float m_83810_(CommandContext<CommandSourceStack> p_83811_, String p_83812_) {
      return p_83811_.getArgument(p_83812_, AngleArgument.SingleAngle.class).m_83825_(p_83811_.getSource());
   }

   public AngleArgument.SingleAngle parse(StringReader p_83809_) throws CommandSyntaxException {
      if (!p_83809_.canRead()) {
         throw f_83803_.createWithContext(p_83809_);
      } else {
         boolean flag = WorldCoordinate.m_120874_(p_83809_);
         float f = p_83809_.canRead() && p_83809_.peek() != ' ' ? p_83809_.readFloat() : 0.0F;
         if (!Float.isNaN(f) && !Float.isInfinite(f)) {
            return new AngleArgument.SingleAngle(f, flag);
         } else {
            throw f_166217_.createWithContext(p_83809_);
         }
      }
   }

   public Collection<String> getExamples() {
      return f_83804_;
   }

   public static final class SingleAngle {
      private final float f_83816_;
      private final boolean f_83817_;

      SingleAngle(float p_83819_, boolean p_83820_) {
         this.f_83816_ = p_83819_;
         this.f_83817_ = p_83820_;
      }

      public float m_83825_(CommandSourceStack p_83826_) {
         return Mth.m_14177_(this.f_83817_ ? this.f_83816_ + p_83826_.m_81376_().f_82471_ : this.f_83816_);
      }
   }
}