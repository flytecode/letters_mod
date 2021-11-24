package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class PanicGoal extends Goal {
   protected final PathfinderMob f_25684_;
   protected final double f_25685_;
   protected double f_25686_;
   protected double f_25687_;
   protected double f_25688_;
   protected boolean f_25689_;

   public PanicGoal(PathfinderMob p_25691_, double p_25692_) {
      this.f_25684_ = p_25691_;
      this.f_25685_ = p_25692_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.f_25684_.m_142581_() == null && !this.f_25684_.m_6060_()) {
         return false;
      } else {
         if (this.f_25684_.m_6060_()) {
            BlockPos blockpos = this.m_25694_(this.f_25684_.f_19853_, this.f_25684_, 5, 4);
            if (blockpos != null) {
               this.f_25686_ = (double)blockpos.m_123341_();
               this.f_25687_ = (double)blockpos.m_123342_();
               this.f_25688_ = (double)blockpos.m_123343_();
               return true;
            }
         }

         return this.m_25702_();
      }
   }

   protected boolean m_25702_() {
      Vec3 vec3 = DefaultRandomPos.m_148403_(this.f_25684_, 5, 4);
      if (vec3 == null) {
         return false;
      } else {
         this.f_25686_ = vec3.f_82479_;
         this.f_25687_ = vec3.f_82480_;
         this.f_25688_ = vec3.f_82481_;
         return true;
      }
   }

   public boolean m_25703_() {
      return this.f_25689_;
   }

   public void m_8056_() {
      this.f_25684_.m_21573_().m_26519_(this.f_25686_, this.f_25687_, this.f_25688_, this.f_25685_);
      this.f_25689_ = true;
   }

   public void m_8041_() {
      this.f_25689_ = false;
   }

   public boolean m_8045_() {
      return !this.f_25684_.m_21573_().m_26571_();
   }

   @Nullable
   protected BlockPos m_25694_(BlockGetter p_25695_, Entity p_25696_, int p_25697_, int p_25698_) {
      BlockPos blockpos = p_25696_.m_142538_();
      int i = blockpos.m_123341_();
      int j = blockpos.m_123342_();
      int k = blockpos.m_123343_();
      float f = (float)(p_25697_ * p_25697_ * p_25698_ * 2);
      BlockPos blockpos1 = null;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l = i - p_25697_; l <= i + p_25697_; ++l) {
         for(int i1 = j - p_25698_; i1 <= j + p_25698_; ++i1) {
            for(int j1 = k - p_25697_; j1 <= k + p_25697_; ++j1) {
               blockpos$mutableblockpos.m_122178_(l, i1, j1);
               if (p_25695_.m_6425_(blockpos$mutableblockpos).m_76153_(FluidTags.f_13131_)) {
                  float f1 = (float)((l - i) * (l - i) + (i1 - j) * (i1 - j) + (j1 - k) * (j1 - k));
                  if (f1 < f) {
                     f = f1;
                     blockpos1 = new BlockPos(blockpos$mutableblockpos);
                  }
               }
            }
         }
      }

      return blockpos1;
   }
}