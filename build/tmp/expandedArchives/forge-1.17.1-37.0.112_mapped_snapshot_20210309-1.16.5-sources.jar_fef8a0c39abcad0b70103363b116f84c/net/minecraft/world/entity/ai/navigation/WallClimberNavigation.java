package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class WallClimberNavigation extends GroundPathNavigation {
   private BlockPos f_26578_;

   public WallClimberNavigation(Mob p_26580_, Level p_26581_) {
      super(p_26580_, p_26581_);
   }

   public Path m_7864_(BlockPos p_26589_, int p_26590_) {
      this.f_26578_ = p_26589_;
      return super.m_7864_(p_26589_, p_26590_);
   }

   public Path m_6570_(Entity p_26586_, int p_26587_) {
      this.f_26578_ = p_26586_.m_142538_();
      return super.m_6570_(p_26586_, p_26587_);
   }

   public boolean m_5624_(Entity p_26583_, double p_26584_) {
      Path path = this.m_6570_(p_26583_, 0);
      if (path != null) {
         return this.m_26536_(path, p_26584_);
      } else {
         this.f_26578_ = p_26583_.m_142538_();
         this.f_26497_ = p_26584_;
         return true;
      }
   }

   public void m_7638_() {
      if (!this.m_26571_()) {
         super.m_7638_();
      } else {
         if (this.f_26578_ != null) {
            // FORGE: Fix MC-94054
            if (!this.f_26578_.m_123306_(this.f_26494_.m_20182_(), Math.max((double)this.f_26494_.m_20205_(), 1.0D)) && (!(this.f_26494_.m_20186_() > (double)this.f_26578_.m_123342_()) || !(new BlockPos((double)this.f_26578_.m_123341_(), this.f_26494_.m_20186_(), (double)this.f_26578_.m_123343_())).m_123306_(this.f_26494_.m_20182_(), Math.max((double)this.f_26494_.m_20205_(), 1.0D)))) {
               this.f_26494_.m_21566_().m_6849_((double)this.f_26578_.m_123341_(), (double)this.f_26578_.m_123342_(), (double)this.f_26578_.m_123343_(), this.f_26497_);
            } else {
               this.f_26578_ = null;
            }
         }

      }
   }
}
