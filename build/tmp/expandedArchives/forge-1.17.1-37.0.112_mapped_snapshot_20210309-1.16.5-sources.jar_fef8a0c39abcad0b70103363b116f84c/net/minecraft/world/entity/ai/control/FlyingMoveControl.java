package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FlyingMoveControl extends MoveControl {
   private final int f_24890_;
   private final boolean f_24891_;

   public FlyingMoveControl(Mob p_24893_, int p_24894_, boolean p_24895_) {
      super(p_24893_);
      this.f_24890_ = p_24894_;
      this.f_24891_ = p_24895_;
   }

   public void m_8126_() {
      if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
         this.f_24981_ = MoveControl.Operation.WAIT;
         this.f_24974_.m_20242_(true);
         double d0 = this.f_24975_ - this.f_24974_.m_20185_();
         double d1 = this.f_24976_ - this.f_24974_.m_20186_();
         double d2 = this.f_24977_ - this.f_24974_.m_20189_();
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 < (double)2.5000003E-7F) {
            this.f_24974_.m_21567_(0.0F);
            this.f_24974_.m_21564_(0.0F);
            return;
         }

         float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
         this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), f, 90.0F));
         float f1;
         if (this.f_24974_.m_20096_()) {
            f1 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
         } else {
            f1 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22280_));
         }

         this.f_24974_.m_7910_(f1);
         double d4 = Math.sqrt(d0 * d0 + d2 * d2);
         if (Math.abs(d1) > (double)1.0E-5F || Math.abs(d4) > (double)1.0E-5F) {
            float f2 = (float)(-(Mth.m_14136_(d1, d4) * (double)(180F / (float)Math.PI)));
            this.f_24974_.m_146926_(this.m_24991_(this.f_24974_.m_146909_(), f2, (float)this.f_24890_));
            this.f_24974_.m_21567_(d1 > 0.0D ? f1 : -f1);
         }
      } else {
         if (!this.f_24891_) {
            this.f_24974_.m_20242_(false);
         }

         this.f_24974_.m_21567_(0.0F);
         this.f_24974_.m_21564_(0.0F);
      }

   }
}