package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class SwimNodeEvaluator extends NodeEvaluator {
   private final boolean f_77455_;

   public SwimNodeEvaluator(boolean p_77457_) {
      this.f_77455_ = p_77457_;
   }

   public Node m_7171_() {
      return super.m_5676_(Mth.m_14107_(this.f_77313_.m_142469_().f_82288_), Mth.m_14107_(this.f_77313_.m_142469_().f_82289_ + 0.5D), Mth.m_14107_(this.f_77313_.m_142469_().f_82290_));
   }

   public Target m_7568_(double p_77459_, double p_77460_, double p_77461_) {
      return new Target(super.m_5676_(Mth.m_14107_(p_77459_ - (double)(this.f_77313_.m_20205_() / 2.0F)), Mth.m_14107_(p_77460_ + 0.5D), Mth.m_14107_(p_77461_ - (double)(this.f_77313_.m_20205_() / 2.0F))));
   }

   public int m_6065_(Node[] p_77483_, Node p_77484_) {
      int i = 0;

      for(Direction direction : Direction.values()) {
         Node node = this.m_77486_(p_77484_.f_77271_ + direction.m_122429_(), p_77484_.f_77272_ + direction.m_122430_(), p_77484_.f_77273_ + direction.m_122431_());
         if (node != null && !node.f_77279_) {
            p_77483_[i++] = node;
         }
      }

      return i;
   }

   public BlockPathTypes m_7209_(BlockGetter p_77472_, int p_77473_, int p_77474_, int p_77475_, Mob p_77476_, int p_77477_, int p_77478_, int p_77479_, boolean p_77480_, boolean p_77481_) {
      return this.m_8086_(p_77472_, p_77473_, p_77474_, p_77475_);
   }

   public BlockPathTypes m_8086_(BlockGetter p_77467_, int p_77468_, int p_77469_, int p_77470_) {
      BlockPos blockpos = new BlockPos(p_77468_, p_77469_, p_77470_);
      FluidState fluidstate = p_77467_.m_6425_(blockpos);
      BlockState blockstate = p_77467_.m_8055_(blockpos);
      if (fluidstate.m_76178_() && blockstate.m_60647_(p_77467_, blockpos.m_7495_(), PathComputationType.WATER) && blockstate.m_60795_()) {
         return BlockPathTypes.BREACH;
      } else {
         return fluidstate.m_76153_(FluidTags.f_13131_) && blockstate.m_60647_(p_77467_, blockpos, PathComputationType.WATER) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
      }
   }

   @Nullable
   private Node m_77486_(int p_77487_, int p_77488_, int p_77489_) {
      BlockPathTypes blockpathtypes = this.m_77490_(p_77487_, p_77488_, p_77489_);
      return (!this.f_77455_ || blockpathtypes != BlockPathTypes.BREACH) && blockpathtypes != BlockPathTypes.WATER ? null : this.m_5676_(p_77487_, p_77488_, p_77489_);
   }

   @Nullable
   protected Node m_5676_(int p_77463_, int p_77464_, int p_77465_) {
      Node node = null;
      BlockPathTypes blockpathtypes = this.m_8086_(this.f_77313_.f_19853_, p_77463_, p_77464_, p_77465_);
      float f = this.f_77313_.m_21439_(blockpathtypes);
      if (f >= 0.0F) {
         node = super.m_5676_(p_77463_, p_77464_, p_77465_);
         node.f_77282_ = blockpathtypes;
         node.f_77281_ = Math.max(node.f_77281_, f);
         if (this.f_77312_.m_6425_(new BlockPos(p_77463_, p_77464_, p_77465_)).m_76178_()) {
            node.f_77281_ += 8.0F;
         }
      }

      return blockpathtypes == BlockPathTypes.OPEN ? node : node;
   }

   private BlockPathTypes m_77490_(int p_77491_, int p_77492_, int p_77493_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = p_77491_; i < p_77491_ + this.f_77315_; ++i) {
         for(int j = p_77492_; j < p_77492_ + this.f_77316_; ++j) {
            for(int k = p_77493_; k < p_77493_ + this.f_77317_; ++k) {
               FluidState fluidstate = this.f_77312_.m_6425_(blockpos$mutableblockpos.m_122178_(i, j, k));
               BlockState blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos.m_122178_(i, j, k));
               if (fluidstate.m_76178_() && blockstate.m_60647_(this.f_77312_, blockpos$mutableblockpos.m_7495_(), PathComputationType.WATER) && blockstate.m_60795_()) {
                  return BlockPathTypes.BREACH;
               }

               if (!fluidstate.m_76153_(FluidTags.f_13131_)) {
                  return BlockPathTypes.BLOCKED;
               }
            }
         }
      }

      BlockState blockstate1 = this.f_77312_.m_8055_(blockpos$mutableblockpos);
      return blockstate1.m_60647_(this.f_77312_, blockpos$mutableblockpos, PathComputationType.WATER) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
   }
}