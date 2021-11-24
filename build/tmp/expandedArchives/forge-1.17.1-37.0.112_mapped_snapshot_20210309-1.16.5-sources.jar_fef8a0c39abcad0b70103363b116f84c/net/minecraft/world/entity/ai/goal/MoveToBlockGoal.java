package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelReader;

public abstract class MoveToBlockGoal extends Goal {
   private static final int f_148128_ = 1200;
   private static final int f_148129_ = 1200;
   private static final int f_148130_ = 200;
   protected final PathfinderMob f_25598_;
   public final double f_25599_;
   protected int f_25600_;
   protected int f_25601_;
   private int f_25604_;
   protected BlockPos f_25602_ = BlockPos.f_121853_;
   private boolean f_25605_;
   private final int f_25606_;
   private final int f_25607_;
   protected int f_25603_;

   public MoveToBlockGoal(PathfinderMob p_25609_, double p_25610_, int p_25611_) {
      this(p_25609_, p_25610_, p_25611_, 1);
   }

   public MoveToBlockGoal(PathfinderMob p_25613_, double p_25614_, int p_25615_, int p_25616_) {
      this.f_25598_ = p_25613_;
      this.f_25599_ = p_25614_;
      this.f_25606_ = p_25615_;
      this.f_25603_ = 0;
      this.f_25607_ = p_25616_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
   }

   public boolean m_8036_() {
      if (this.f_25600_ > 0) {
         --this.f_25600_;
         return false;
      } else {
         this.f_25600_ = this.m_6099_(this.f_25598_);
         return this.m_25626_();
      }
   }

   protected int m_6099_(PathfinderMob p_25618_) {
      return 200 + p_25618_.m_21187_().nextInt(200);
   }

   public boolean m_8045_() {
      return this.f_25601_ >= -this.f_25604_ && this.f_25601_ <= 1200 && this.m_6465_(this.f_25598_.f_19853_, this.f_25602_);
   }

   public void m_8056_() {
      this.m_25624_();
      this.f_25601_ = 0;
      this.f_25604_ = this.f_25598_.m_21187_().nextInt(this.f_25598_.m_21187_().nextInt(1200) + 1200) + 1200;
   }

   protected void m_25624_() {
      this.f_25598_.m_21573_().m_26519_((double)((float)this.f_25602_.m_123341_()) + 0.5D, (double)(this.f_25602_.m_123342_() + 1), (double)((float)this.f_25602_.m_123343_()) + 0.5D, this.f_25599_);
   }

   public double m_8052_() {
      return 1.0D;
   }

   protected BlockPos m_6669_() {
      return this.f_25602_.m_7494_();
   }

   public void m_8037_() {
      BlockPos blockpos = this.m_6669_();
      if (!blockpos.m_123306_(this.f_25598_.m_20182_(), this.m_8052_())) {
         this.f_25605_ = false;
         ++this.f_25601_;
         if (this.m_8064_()) {
            this.f_25598_.m_21573_().m_26519_((double)((float)blockpos.m_123341_()) + 0.5D, (double)blockpos.m_123342_(), (double)((float)blockpos.m_123343_()) + 0.5D, this.f_25599_);
         }
      } else {
         this.f_25605_ = true;
         --this.f_25601_;
      }

   }

   public boolean m_8064_() {
      return this.f_25601_ % 40 == 0;
   }

   protected boolean m_25625_() {
      return this.f_25605_;
   }

   protected boolean m_25626_() {
      int i = this.f_25606_;
      int j = this.f_25607_;
      BlockPos blockpos = this.f_25598_.m_142538_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k = this.f_25603_; k <= j; k = k > 0 ? -k : 1 - k) {
         for(int l = 0; l < i; ++l) {
            for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
               for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                  blockpos$mutableblockpos.m_122154_(blockpos, i1, k - 1, j1);
                  if (this.f_25598_.m_21444_(blockpos$mutableblockpos) && this.m_6465_(this.f_25598_.f_19853_, blockpos$mutableblockpos)) {
                     this.f_25602_ = blockpos$mutableblockpos;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean m_6465_(LevelReader p_25619_, BlockPos p_25620_);
}