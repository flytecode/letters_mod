package net.minecraft.world.entity.ai.goal;

import java.util.List;
import net.minecraft.world.entity.animal.Animal;

public class FollowParentGoal extends Goal {
   public static final int f_148091_ = 8;
   public static final int f_148092_ = 4;
   public static final int f_148093_ = 3;
   private final Animal f_25314_;
   private Animal f_25315_;
   private final double f_25316_;
   private int f_25317_;

   public FollowParentGoal(Animal p_25319_, double p_25320_) {
      this.f_25314_ = p_25319_;
      this.f_25316_ = p_25320_;
   }

   public boolean m_8036_() {
      if (this.f_25314_.m_146764_() >= 0) {
         return false;
      } else {
         List<? extends Animal> list = this.f_25314_.f_19853_.m_45976_(this.f_25314_.getClass(), this.f_25314_.m_142469_().m_82377_(8.0D, 4.0D, 8.0D));
         Animal animal = null;
         double d0 = Double.MAX_VALUE;

         for(Animal animal1 : list) {
            if (animal1.m_146764_() >= 0) {
               double d1 = this.f_25314_.m_20280_(animal1);
               if (!(d1 > d0)) {
                  d0 = d1;
                  animal = animal1;
               }
            }
         }

         if (animal == null) {
            return false;
         } else if (d0 < 9.0D) {
            return false;
         } else {
            this.f_25315_ = animal;
            return true;
         }
      }
   }

   public boolean m_8045_() {
      if (this.f_25314_.m_146764_() >= 0) {
         return false;
      } else if (!this.f_25315_.m_6084_()) {
         return false;
      } else {
         double d0 = this.f_25314_.m_20280_(this.f_25315_);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   public void m_8056_() {
      this.f_25317_ = 0;
   }

   public void m_8041_() {
      this.f_25315_ = null;
   }

   public void m_8037_() {
      if (--this.f_25317_ <= 0) {
         this.f_25317_ = 10;
         this.f_25314_.m_21573_().m_5624_(this.f_25315_, this.f_25316_);
      }
   }
}