package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class RangedAttackGoal extends Goal {
   private final Mob f_25757_;
   private final RangedAttackMob f_25758_;
   private LivingEntity f_25759_;
   private int f_25760_ = -1;
   private final double f_25761_;
   private int f_25762_;
   private final int f_25763_;
   private final int f_25764_;
   private final float f_25765_;
   private final float f_25766_;

   public RangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
      this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
   }

   public RangedAttackGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
      if (!(p_25773_ instanceof LivingEntity)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.f_25758_ = p_25773_;
         this.f_25757_ = (Mob)p_25773_;
         this.f_25761_ = p_25774_;
         this.f_25763_ = p_25775_;
         this.f_25764_ = p_25776_;
         this.f_25765_ = p_25777_;
         this.f_25766_ = p_25777_ * p_25777_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.f_25757_.m_5448_();
      if (livingentity != null && livingentity.m_6084_()) {
         this.f_25759_ = livingentity;
         return true;
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return this.m_8036_() || !this.f_25757_.m_21573_().m_26571_();
   }

   public void m_8041_() {
      this.f_25759_ = null;
      this.f_25762_ = 0;
      this.f_25760_ = -1;
   }

   public void m_8037_() {
      double d0 = this.f_25757_.m_20275_(this.f_25759_.m_20185_(), this.f_25759_.m_20186_(), this.f_25759_.m_20189_());
      boolean flag = this.f_25757_.m_21574_().m_148306_(this.f_25759_);
      if (flag) {
         ++this.f_25762_;
      } else {
         this.f_25762_ = 0;
      }

      if (!(d0 > (double)this.f_25766_) && this.f_25762_ >= 5) {
         this.f_25757_.m_21573_().m_26573_();
      } else {
         this.f_25757_.m_21573_().m_5624_(this.f_25759_, this.f_25761_);
      }

      this.f_25757_.m_21563_().m_24960_(this.f_25759_, 30.0F, 30.0F);
      if (--this.f_25760_ == 0) {
         if (!flag) {
            return;
         }

         float f = (float)Math.sqrt(d0) / this.f_25765_;
         float f1 = Mth.m_14036_(f, 0.1F, 1.0F);
         this.f_25758_.m_6504_(this.f_25759_, f1);
         this.f_25760_ = Mth.m_14143_(f * (float)(this.f_25764_ - this.f_25763_) + (float)this.f_25763_);
      } else if (this.f_25760_ < 0) {
         this.f_25760_ = Mth.m_14107_(Mth.m_14139_(Math.sqrt(d0) / (double)this.f_25765_, (double)this.f_25763_, (double)this.f_25764_));
      }

   }
}