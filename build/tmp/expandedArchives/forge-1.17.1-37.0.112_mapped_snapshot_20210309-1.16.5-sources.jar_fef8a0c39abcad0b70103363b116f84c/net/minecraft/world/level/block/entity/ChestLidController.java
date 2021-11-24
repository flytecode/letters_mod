package net.minecraft.world.level.block.entity;

import net.minecraft.util.Mth;

public class ChestLidController {
   private boolean f_155370_;
   private float f_155371_;
   private float f_155372_;

   public void m_155374_() {
      this.f_155372_ = this.f_155371_;
      float f = 0.1F;
      if (!this.f_155370_ && this.f_155371_ > 0.0F) {
         this.f_155371_ = Math.max(this.f_155371_ - 0.1F, 0.0F);
      } else if (this.f_155370_ && this.f_155371_ < 1.0F) {
         this.f_155371_ = Math.min(this.f_155371_ + 0.1F, 1.0F);
      }

   }

   public float m_155375_(float p_155376_) {
      return Mth.m_14179_(p_155376_, this.f_155372_, this.f_155371_);
   }

   public void m_155377_(boolean p_155378_) {
      this.f_155370_ = p_155378_;
   }
}