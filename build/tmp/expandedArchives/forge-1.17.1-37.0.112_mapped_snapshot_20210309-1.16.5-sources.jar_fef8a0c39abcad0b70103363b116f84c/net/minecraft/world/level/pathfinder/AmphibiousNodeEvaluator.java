package net.minecraft.world.level.pathfinder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;

public class AmphibiousNodeEvaluator extends WalkNodeEvaluator {
   private final boolean f_164655_;
   private float f_164656_;
   private float f_164657_;

   public AmphibiousNodeEvaluator(boolean p_164659_) {
      this.f_164655_ = p_164659_;
   }

   public void m_6028_(PathNavigationRegion p_164671_, Mob p_164672_) {
      super.m_6028_(p_164671_, p_164672_);
      p_164672_.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_164656_ = p_164672_.m_21439_(BlockPathTypes.WALKABLE);
      p_164672_.m_21441_(BlockPathTypes.WALKABLE, 6.0F);
      this.f_164657_ = p_164672_.m_21439_(BlockPathTypes.WATER_BORDER);
      p_164672_.m_21441_(BlockPathTypes.WATER_BORDER, 4.0F);
   }

   public void m_6802_() {
      this.f_77313_.m_21441_(BlockPathTypes.WALKABLE, this.f_164656_);
      this.f_77313_.m_21441_(BlockPathTypes.WATER_BORDER, this.f_164657_);
      super.m_6802_();
   }

   public Node m_7171_() {
      return this.m_5676_(Mth.m_14107_(this.f_77313_.m_142469_().f_82288_), Mth.m_14107_(this.f_77313_.m_142469_().f_82289_ + 0.5D), Mth.m_14107_(this.f_77313_.m_142469_().f_82290_));
   }

   public Target m_7568_(double p_164662_, double p_164663_, double p_164664_) {
      return new Target(this.m_5676_(Mth.m_14107_(p_164662_), Mth.m_14107_(p_164663_ + 0.5D), Mth.m_14107_(p_164664_)));
   }

   public int m_6065_(Node[] p_164676_, Node p_164677_) {
      int i = super.m_6065_(p_164676_, p_164677_);
      BlockPathTypes blockpathtypes = this.m_77567_(this.f_77313_, p_164677_.f_77271_, p_164677_.f_77272_ + 1, p_164677_.f_77273_);
      BlockPathTypes blockpathtypes1 = this.m_77567_(this.f_77313_, p_164677_.f_77271_, p_164677_.f_77272_, p_164677_.f_77273_);
      int j;
      if (this.f_77313_.m_21439_(blockpathtypes) >= 0.0F && blockpathtypes1 != BlockPathTypes.STICKY_HONEY) {
         j = Mth.m_14143_(Math.max(1.0F, this.f_77313_.f_19793_));
      } else {
         j = 0;
      }

      double d0 = this.m_142213_(new BlockPos(p_164677_.f_77271_, p_164677_.f_77272_, p_164677_.f_77273_));
      Node node = this.m_164725_(p_164677_.f_77271_, p_164677_.f_77272_ + 1, p_164677_.f_77273_, Math.max(0, j - 1), d0, Direction.UP, blockpathtypes1);
      Node node1 = this.m_164725_(p_164677_.f_77271_, p_164677_.f_77272_ - 1, p_164677_.f_77273_, j, d0, Direction.DOWN, blockpathtypes1);
      if (this.m_77626_(node, p_164677_)) {
         p_164676_[i++] = node;
      }

      if (this.m_77626_(node1, p_164677_) && blockpathtypes1 != BlockPathTypes.TRAPDOOR) {
         p_164676_[i++] = node1;
      }

      for(int k = 0; k < i; ++k) {
         Node node2 = p_164676_[k];
         if (node2.f_77282_ == BlockPathTypes.WATER && this.f_164655_ && node2.f_77272_ < this.f_77313_.f_19853_.m_5736_() - 10) {
            ++node2.f_77281_;
         }
      }

      return i;
   }

   protected double m_142213_(BlockPos p_164674_) {
      return this.f_77313_.m_20069_() ? (double)p_164674_.m_123342_() + 0.5D : super.m_142213_(p_164674_);
   }

   protected boolean m_141974_() {
      return true;
   }

   public BlockPathTypes m_8086_(BlockGetter p_164666_, int p_164667_, int p_164668_, int p_164669_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPathTypes blockpathtypes = m_77643_(p_164666_, blockpos$mutableblockpos.m_122178_(p_164667_, p_164668_, p_164669_));
      if (blockpathtypes == BlockPathTypes.WATER) {
         for(Direction direction : Direction.values()) {
            BlockPathTypes blockpathtypes1 = m_77643_(p_164666_, blockpos$mutableblockpos.m_122178_(p_164667_, p_164668_, p_164669_).m_122173_(direction));
            if (blockpathtypes1 == BlockPathTypes.BLOCKED) {
               return BlockPathTypes.WATER_BORDER;
            }
         }

         return BlockPathTypes.WATER;
      } else {
         return m_77604_(p_164666_, blockpos$mutableblockpos);
      }
   }
}