package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public abstract class DoorInteractGoal extends Goal {
   protected Mob f_25189_;
   protected BlockPos f_25190_ = BlockPos.f_121853_;
   protected boolean f_25191_;
   private boolean f_25186_;
   private float f_25187_;
   private float f_25188_;

   public DoorInteractGoal(Mob p_25193_) {
      this.f_25189_ = p_25193_;
      if (!GoalUtils.m_26894_(p_25193_)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean m_25200_() {
      if (!this.f_25191_) {
         return false;
      } else {
         BlockState blockstate = this.f_25189_.f_19853_.m_8055_(this.f_25190_);
         if (!(blockstate.m_60734_() instanceof DoorBlock)) {
            this.f_25191_ = false;
            return false;
         } else {
            return blockstate.m_61143_(DoorBlock.f_52727_);
         }
      }
   }

   protected void m_25195_(boolean p_25196_) {
      if (this.f_25191_) {
         BlockState blockstate = this.f_25189_.f_19853_.m_8055_(this.f_25190_);
         if (blockstate.m_60734_() instanceof DoorBlock) {
            ((DoorBlock)blockstate.m_60734_()).m_153165_(this.f_25189_, this.f_25189_.f_19853_, blockstate, this.f_25190_, p_25196_);
         }
      }

   }

   public boolean m_8036_() {
      if (!GoalUtils.m_26894_(this.f_25189_)) {
         return false;
      } else if (!this.f_25189_.f_19862_) {
         return false;
      } else {
         GroundPathNavigation groundpathnavigation = (GroundPathNavigation)this.f_25189_.m_21573_();
         Path path = groundpathnavigation.m_26570_();
         if (path != null && !path.m_77392_() && groundpathnavigation.m_26492_()) {
            for(int i = 0; i < Math.min(path.m_77399_() + 2, path.m_77398_()); ++i) {
               Node node = path.m_77375_(i);
               this.f_25190_ = new BlockPos(node.f_77271_, node.f_77272_ + 1, node.f_77273_);
               if (!(this.f_25189_.m_20275_((double)this.f_25190_.m_123341_(), this.f_25189_.m_20186_(), (double)this.f_25190_.m_123343_()) > 2.25D)) {
                  this.f_25191_ = DoorBlock.m_52745_(this.f_25189_.f_19853_, this.f_25190_);
                  if (this.f_25191_) {
                     return true;
                  }
               }
            }

            this.f_25190_ = this.f_25189_.m_142538_().m_7494_();
            this.f_25191_ = DoorBlock.m_52745_(this.f_25189_.f_19853_, this.f_25190_);
            return this.f_25191_;
         } else {
            return false;
         }
      }
   }

   public boolean m_8045_() {
      return !this.f_25186_;
   }

   public void m_8056_() {
      this.f_25186_ = false;
      this.f_25187_ = (float)((double)this.f_25190_.m_123341_() + 0.5D - this.f_25189_.m_20185_());
      this.f_25188_ = (float)((double)this.f_25190_.m_123343_() + 0.5D - this.f_25189_.m_20189_());
   }

   public void m_8037_() {
      float f = (float)((double)this.f_25190_.m_123341_() + 0.5D - this.f_25189_.m_20185_());
      float f1 = (float)((double)this.f_25190_.m_123343_() + 0.5D - this.f_25189_.m_20189_());
      float f2 = this.f_25187_ * f + this.f_25188_ * f1;
      if (f2 < 0.0F) {
         this.f_25186_ = true;
      }

   }
}