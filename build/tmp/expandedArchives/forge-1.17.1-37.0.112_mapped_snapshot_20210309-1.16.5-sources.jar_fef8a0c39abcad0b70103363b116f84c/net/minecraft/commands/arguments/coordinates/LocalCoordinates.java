package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LocalCoordinates implements Coordinates {
   public static final char f_174681_ = '^';
   private final double f_119898_;
   private final double f_119899_;
   private final double f_119900_;

   public LocalCoordinates(double p_119902_, double p_119903_, double p_119904_) {
      this.f_119898_ = p_119902_;
      this.f_119899_ = p_119903_;
      this.f_119900_ = p_119904_;
   }

   public Vec3 m_6955_(CommandSourceStack p_119912_) {
      Vec2 vec2 = p_119912_.m_81376_();
      Vec3 vec3 = p_119912_.m_81378_().m_90379_(p_119912_);
      float f = Mth.m_14089_((vec2.f_82471_ + 90.0F) * ((float)Math.PI / 180F));
      float f1 = Mth.m_14031_((vec2.f_82471_ + 90.0F) * ((float)Math.PI / 180F));
      float f2 = Mth.m_14089_(-vec2.f_82470_ * ((float)Math.PI / 180F));
      float f3 = Mth.m_14031_(-vec2.f_82470_ * ((float)Math.PI / 180F));
      float f4 = Mth.m_14089_((-vec2.f_82470_ + 90.0F) * ((float)Math.PI / 180F));
      float f5 = Mth.m_14031_((-vec2.f_82470_ + 90.0F) * ((float)Math.PI / 180F));
      Vec3 vec31 = new Vec3((double)(f * f2), (double)f3, (double)(f1 * f2));
      Vec3 vec32 = new Vec3((double)(f * f4), (double)f5, (double)(f1 * f4));
      Vec3 vec33 = vec31.m_82537_(vec32).m_82490_(-1.0D);
      double d0 = vec31.f_82479_ * this.f_119900_ + vec32.f_82479_ * this.f_119899_ + vec33.f_82479_ * this.f_119898_;
      double d1 = vec31.f_82480_ * this.f_119900_ + vec32.f_82480_ * this.f_119899_ + vec33.f_82480_ * this.f_119898_;
      double d2 = vec31.f_82481_ * this.f_119900_ + vec32.f_82481_ * this.f_119899_ + vec33.f_82481_ * this.f_119898_;
      return new Vec3(vec3.f_82479_ + d0, vec3.f_82480_ + d1, vec3.f_82481_ + d2);
   }

   public Vec2 m_6970_(CommandSourceStack p_119915_) {
      return Vec2.f_82462_;
   }

   public boolean m_6888_() {
      return true;
   }

   public boolean m_6892_() {
      return true;
   }

   public boolean m_6900_() {
      return true;
   }

   public static LocalCoordinates m_119906_(StringReader p_119907_) throws CommandSyntaxException {
      int i = p_119907_.getCursor();
      double d0 = m_119908_(p_119907_, i);
      if (p_119907_.canRead() && p_119907_.peek() == ' ') {
         p_119907_.skip();
         double d1 = m_119908_(p_119907_, i);
         if (p_119907_.canRead() && p_119907_.peek() == ' ') {
            p_119907_.skip();
            double d2 = m_119908_(p_119907_, i);
            return new LocalCoordinates(d0, d1, d2);
         } else {
            p_119907_.setCursor(i);
            throw Vec3Argument.f_120834_.createWithContext(p_119907_);
         }
      } else {
         p_119907_.setCursor(i);
         throw Vec3Argument.f_120834_.createWithContext(p_119907_);
      }
   }

   private static double m_119908_(StringReader p_119909_, int p_119910_) throws CommandSyntaxException {
      if (!p_119909_.canRead()) {
         throw WorldCoordinate.f_120858_.createWithContext(p_119909_);
      } else if (p_119909_.peek() != '^') {
         p_119909_.setCursor(p_119910_);
         throw Vec3Argument.f_120835_.createWithContext(p_119909_);
      } else {
         p_119909_.skip();
         return p_119909_.canRead() && p_119909_.peek() != ' ' ? p_119909_.readDouble() : 0.0D;
      }
   }

   public boolean equals(Object p_119918_) {
      if (this == p_119918_) {
         return true;
      } else if (!(p_119918_ instanceof LocalCoordinates)) {
         return false;
      } else {
         LocalCoordinates localcoordinates = (LocalCoordinates)p_119918_;
         return this.f_119898_ == localcoordinates.f_119898_ && this.f_119899_ == localcoordinates.f_119899_ && this.f_119900_ == localcoordinates.f_119900_;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_119898_, this.f_119899_, this.f_119900_);
   }
}