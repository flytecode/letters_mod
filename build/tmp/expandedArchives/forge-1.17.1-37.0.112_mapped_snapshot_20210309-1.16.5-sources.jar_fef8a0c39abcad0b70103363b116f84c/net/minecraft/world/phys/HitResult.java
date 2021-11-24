package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;

public abstract class HitResult {
   protected final Vec3 f_82445_;

   protected HitResult(Vec3 p_82447_) {
      this.f_82445_ = p_82447_;
   }

   public double m_82448_(Entity p_82449_) {
      double d0 = this.f_82445_.f_82479_ - p_82449_.m_20185_();
      double d1 = this.f_82445_.f_82480_ - p_82449_.m_20186_();
      double d2 = this.f_82445_.f_82481_ - p_82449_.m_20189_();
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public abstract HitResult.Type m_6662_();

   public Vec3 m_82450_() {
      return this.f_82445_;
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;
   }
}