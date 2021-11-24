package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.TranslatableComponent;

public class WorldCoordinate {
   private static final char f_175084_ = '~';
   public static final SimpleCommandExceptionType f_120858_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.missing.double"));
   public static final SimpleCommandExceptionType f_120859_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.pos.missing.int"));
   private final boolean f_120860_;
   private final double f_120861_;

   public WorldCoordinate(boolean p_120864_, double p_120865_) {
      this.f_120860_ = p_120864_;
      this.f_120861_ = p_120865_;
   }

   public double m_120867_(double p_120868_) {
      return this.f_120860_ ? this.f_120861_ + p_120868_ : this.f_120861_;
   }

   public static WorldCoordinate m_120871_(StringReader p_120872_, boolean p_120873_) throws CommandSyntaxException {
      if (p_120872_.canRead() && p_120872_.peek() == '^') {
         throw Vec3Argument.f_120835_.createWithContext(p_120872_);
      } else if (!p_120872_.canRead()) {
         throw f_120858_.createWithContext(p_120872_);
      } else {
         boolean flag = m_120874_(p_120872_);
         int i = p_120872_.getCursor();
         double d0 = p_120872_.canRead() && p_120872_.peek() != ' ' ? p_120872_.readDouble() : 0.0D;
         String s = p_120872_.getString().substring(i, p_120872_.getCursor());
         if (flag && s.isEmpty()) {
            return new WorldCoordinate(true, 0.0D);
         } else {
            if (!s.contains(".") && !flag && p_120873_) {
               d0 += 0.5D;
            }

            return new WorldCoordinate(flag, d0);
         }
      }
   }

   public static WorldCoordinate m_120869_(StringReader p_120870_) throws CommandSyntaxException {
      if (p_120870_.canRead() && p_120870_.peek() == '^') {
         throw Vec3Argument.f_120835_.createWithContext(p_120870_);
      } else if (!p_120870_.canRead()) {
         throw f_120859_.createWithContext(p_120870_);
      } else {
         boolean flag = m_120874_(p_120870_);
         double d0;
         if (p_120870_.canRead() && p_120870_.peek() != ' ') {
            d0 = flag ? p_120870_.readDouble() : (double)p_120870_.readInt();
         } else {
            d0 = 0.0D;
         }

         return new WorldCoordinate(flag, d0);
      }
   }

   public static boolean m_120874_(StringReader p_120875_) {
      boolean flag;
      if (p_120875_.peek() == '~') {
         flag = true;
         p_120875_.skip();
      } else {
         flag = false;
      }

      return flag;
   }

   public boolean equals(Object p_120877_) {
      if (this == p_120877_) {
         return true;
      } else if (!(p_120877_ instanceof WorldCoordinate)) {
         return false;
      } else {
         WorldCoordinate worldcoordinate = (WorldCoordinate)p_120877_;
         if (this.f_120860_ != worldcoordinate.f_120860_) {
            return false;
         } else {
            return Double.compare(worldcoordinate.f_120861_, this.f_120861_) == 0;
         }
      }
   }

   public int hashCode() {
      int i = this.f_120860_ ? 1 : 0;
      long j = Double.doubleToLongBits(this.f_120861_);
      return 31 * i + (int)(j ^ j >>> 32);
   }

   public boolean m_120866_() {
      return this.f_120860_;
   }
}