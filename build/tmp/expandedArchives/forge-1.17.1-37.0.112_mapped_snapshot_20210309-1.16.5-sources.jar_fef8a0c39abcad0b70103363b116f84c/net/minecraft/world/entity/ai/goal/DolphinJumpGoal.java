package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class DolphinJumpGoal extends JumpGoal {
   private static final int[] f_25162_ = new int[]{0, 1, 4, 5, 6, 7};
   private final Dolphin f_25163_;
   private final int f_25164_;
   private boolean f_25165_;

   public DolphinJumpGoal(Dolphin p_25168_, int p_25169_) {
      this.f_25163_ = p_25168_;
      this.f_25164_ = p_25169_;
   }

   public boolean m_8036_() {
      if (this.f_25163_.m_21187_().nextInt(this.f_25164_) != 0) {
         return false;
      } else {
         Direction direction = this.f_25163_.m_6374_();
         int i = direction.m_122429_();
         int j = direction.m_122431_();
         BlockPos blockpos = this.f_25163_.m_142538_();

         for(int k : f_25162_) {
            if (!this.m_25172_(blockpos, i, j, k) || !this.m_25178_(blockpos, i, j, k)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean m_25172_(BlockPos p_25173_, int p_25174_, int p_25175_, int p_25176_) {
      BlockPos blockpos = p_25173_.m_142082_(p_25174_ * p_25176_, 0, p_25175_ * p_25176_);
      return this.f_25163_.f_19853_.m_6425_(blockpos).m_76153_(FluidTags.f_13131_) && !this.f_25163_.f_19853_.m_8055_(blockpos).m_60767_().m_76334_();
   }

   private boolean m_25178_(BlockPos p_25179_, int p_25180_, int p_25181_, int p_25182_) {
      return this.f_25163_.f_19853_.m_8055_(p_25179_.m_142082_(p_25180_ * p_25182_, 1, p_25181_ * p_25182_)).m_60795_() && this.f_25163_.f_19853_.m_8055_(p_25179_.m_142082_(p_25180_ * p_25182_, 2, p_25181_ * p_25182_)).m_60795_();
   }

   public boolean m_8045_() {
      double d0 = this.f_25163_.m_20184_().f_82480_;
      return (!(d0 * d0 < (double)0.03F) || this.f_25163_.m_146909_() == 0.0F || !(Math.abs(this.f_25163_.m_146909_()) < 10.0F) || !this.f_25163_.m_20069_()) && !this.f_25163_.m_20096_();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      Direction direction = this.f_25163_.m_6374_();
      this.f_25163_.m_20256_(this.f_25163_.m_20184_().m_82520_((double)direction.m_122429_() * 0.6D, 0.7D, (double)direction.m_122431_() * 0.6D));
      this.f_25163_.m_21573_().m_26573_();
   }

   public void m_8041_() {
      this.f_25163_.m_146926_(0.0F);
   }

   public void m_8037_() {
      boolean flag = this.f_25165_;
      if (!flag) {
         FluidState fluidstate = this.f_25163_.f_19853_.m_6425_(this.f_25163_.m_142538_());
         this.f_25165_ = fluidstate.m_76153_(FluidTags.f_13131_);
      }

      if (this.f_25165_ && !flag) {
         this.f_25163_.m_5496_(SoundEvents.f_11805_, 1.0F, 1.0F);
      }

      Vec3 vec3 = this.f_25163_.m_20184_();
      if (vec3.f_82480_ * vec3.f_82480_ < (double)0.03F && this.f_25163_.m_146909_() != 0.0F) {
         this.f_25163_.m_146926_(Mth.m_14201_(this.f_25163_.m_146909_(), 0.0F, 0.2F));
      } else if (vec3.m_82553_() > (double)1.0E-5F) {
         double d0 = vec3.m_165924_();
         double d1 = Math.atan2(-vec3.f_82480_, d0) * (double)(180F / (float)Math.PI);
         this.f_25163_.m_146926_((float)d1);
      }

   }
}