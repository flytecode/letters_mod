package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

public class DragonHoverPhase extends AbstractDragonPhaseInstance {
   private Vec3 f_31244_;

   public DragonHoverPhase(EnderDragon p_31246_) {
      super(p_31246_);
   }

   public void m_6989_() {
      if (this.f_31244_ == null) {
         this.f_31244_ = this.f_31176_.m_20182_();
      }

   }

   public boolean m_7080_() {
      return true;
   }

   public void m_7083_() {
      this.f_31244_ = null;
   }

   public float m_7072_() {
      return 1.0F;
   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31244_;
   }

   public EnderDragonPhase<DragonHoverPhase> m_7309_() {
      return EnderDragonPhase.f_31387_;
   }
}