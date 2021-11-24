package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SmoothSwimmingMoveControl extends MoveControl {
   private final int f_148064_;
   private final int f_148065_;
   private final float f_148066_;
   private final float f_148067_;
   private final boolean f_148068_;

   public SmoothSwimmingMoveControl(Mob p_148070_, int p_148071_, int p_148072_, float p_148073_, float p_148074_, boolean p_148075_) {
      super(p_148070_);
      this.f_148064_ = p_148071_;
      this.f_148065_ = p_148072_;
      this.f_148066_ = p_148073_;
      this.f_148067_ = p_148074_;
      this.f_148068_ = p_148075_;
   }

   public void m_8126_() {
      if (this.f_148068_ && this.f_24974_.m_20069_()) {
         this.f_24974_.m_20256_(this.f_24974_.m_20184_().m_82520_(0.0D, 0.005D, 0.0D));
      }

      if (this.f_24981_ == MoveControl.Operation.MOVE_TO && !this.f_24974_.m_21573_().m_26571_()) {
         double d0 = this.f_24975_ - this.f_24974_.m_20185_();
         double d1 = this.f_24976_ - this.f_24974_.m_20186_();
         double d2 = this.f_24977_ - this.f_24974_.m_20189_();
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 < (double)2.5000003E-7F) {
            this.f_24974_.m_21564_(0.0F);
         } else {
            float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), f, (float)this.f_148065_));
            this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
            this.f_24974_.f_20885_ = this.f_24974_.m_146908_();
            float f1 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
            if (this.f_24974_.m_20069_()) {
               this.f_24974_.m_7910_(f1 * this.f_148066_);
               double d4 = Math.sqrt(d0 * d0 + d2 * d2);
               if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
                  float f2 = -((float)(Mth.m_14136_(d1, d4) * (double)(180F / (float)Math.PI)));
                  f2 = Mth.m_14036_(Mth.m_14177_(f2), (float)(-this.f_148064_), (float)this.f_148064_);
                  this.f_24974_.m_146926_(this.m_24991_(this.f_24974_.m_146909_(), f2, 5.0F));
               }

               float f4 = Mth.m_14089_(this.f_24974_.m_146909_() * ((float)Math.PI / 180F));
               float f3 = Mth.m_14031_(this.f_24974_.m_146909_() * ((float)Math.PI / 180F));
               this.f_24974_.f_20902_ = f4 * f1;
               this.f_24974_.f_20901_ = -f3 * f1;
            } else {
               this.f_24974_.m_7910_(f1 * this.f_148067_);
            }

         }
      } else {
         this.f_24974_.m_7910_(0.0F);
         this.f_24974_.m_21570_(0.0F);
         this.f_24974_.m_21567_(0.0F);
         this.f_24974_.m_21564_(0.0F);
      }
   }
}