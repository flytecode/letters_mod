package net.minecraft.world.level.pathfinder;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FlyNodeEvaluator extends WalkNodeEvaluator {
   private final Long2ObjectMap<BlockPathTypes> f_164687_ = new Long2ObjectOpenHashMap<>();

   public void m_6028_(PathNavigationRegion p_77261_, Mob p_77262_) {
      super.m_6028_(p_77261_, p_77262_);
      this.f_164687_.clear();
      this.f_77544_ = p_77262_.m_21439_(BlockPathTypes.WATER);
   }

   public void m_6802_() {
      this.f_77313_.m_21441_(BlockPathTypes.WATER, this.f_77544_);
      this.f_164687_.clear();
      super.m_6802_();
   }

   public Node m_7171_() {
      int i;
      if (this.m_77361_() && this.f_77313_.m_20069_()) {
         i = this.f_77313_.m_146904_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.f_77313_.m_20185_(), (double)i, this.f_77313_.m_20189_());

         for(BlockState blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos); blockstate.m_60713_(Blocks.f_49990_); blockstate = this.f_77312_.m_8055_(blockpos$mutableblockpos)) {
            ++i;
            blockpos$mutableblockpos.m_122169_(this.f_77313_.m_20185_(), (double)i, this.f_77313_.m_20189_());
         }
      } else {
         i = Mth.m_14107_(this.f_77313_.m_20186_() + 0.5D);
      }

      BlockPos blockpos1 = this.f_77313_.m_142538_();
      BlockPathTypes blockpathtypes1 = this.m_164693_(blockpos1.m_123341_(), i, blockpos1.m_123343_());
      if (this.f_77313_.m_21439_(blockpathtypes1) < 0.0F) {
         for(BlockPos blockpos : ImmutableSet.of(new BlockPos(this.f_77313_.m_142469_().f_82288_, (double)i, this.f_77313_.m_142469_().f_82290_), new BlockPos(this.f_77313_.m_142469_().f_82288_, (double)i, this.f_77313_.m_142469_().f_82293_), new BlockPos(this.f_77313_.m_142469_().f_82291_, (double)i, this.f_77313_.m_142469_().f_82290_), new BlockPos(this.f_77313_.m_142469_().f_82291_, (double)i, this.f_77313_.m_142469_().f_82293_))) {
            BlockPathTypes blockpathtypes = this.m_164693_(blockpos1.m_123341_(), i, blockpos1.m_123343_());
            if (this.f_77313_.m_21439_(blockpathtypes) >= 0.0F) {
               return super.m_5676_(blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_());
            }
         }
      }

      return super.m_5676_(blockpos1.m_123341_(), i, blockpos1.m_123343_());
   }

   public Target m_7568_(double p_77229_, double p_77230_, double p_77231_) {
      return new Target(super.m_5676_(Mth.m_14107_(p_77229_), Mth.m_14107_(p_77230_), Mth.m_14107_(p_77231_)));
   }

   public int m_6065_(Node[] p_77266_, Node p_77267_) {
      int i = 0;
      Node node = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node)) {
         p_77266_[i++] = node;
      }

      Node node1 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_);
      if (this.m_77269_(node1)) {
         p_77266_[i++] = node1;
      }

      Node node2 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_);
      if (this.m_77269_(node2)) {
         p_77266_[i++] = node2;
      }

      Node node3 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node3)) {
         p_77266_[i++] = node3;
      }

      Node node4 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_);
      if (this.m_77269_(node4)) {
         p_77266_[i++] = node4;
      }

      Node node5 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_);
      if (this.m_77269_(node5)) {
         p_77266_[i++] = node5;
      }

      Node node6 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node6) && this.m_77263_(node) && this.m_77263_(node4)) {
         p_77266_[i++] = node6;
      }

      Node node7 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_);
      if (this.m_77269_(node7) && this.m_77263_(node1) && this.m_77263_(node4)) {
         p_77266_[i++] = node7;
      }

      Node node8 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_);
      if (this.m_77269_(node8) && this.m_77263_(node2) && this.m_77263_(node4)) {
         p_77266_[i++] = node8;
      }

      Node node9 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node9) && this.m_77263_(node3) && this.m_77263_(node4)) {
         p_77266_[i++] = node9;
      }

      Node node10 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node10) && this.m_77263_(node) && this.m_77263_(node5)) {
         p_77266_[i++] = node10;
      }

      Node node11 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_);
      if (this.m_77269_(node11) && this.m_77263_(node1) && this.m_77263_(node5)) {
         p_77266_[i++] = node11;
      }

      Node node12 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_);
      if (this.m_77269_(node12) && this.m_77263_(node2) && this.m_77263_(node5)) {
         p_77266_[i++] = node12;
      }

      Node node13 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node13) && this.m_77263_(node3) && this.m_77263_(node5)) {
         p_77266_[i++] = node13;
      }

      Node node14 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node14) && this.m_77263_(node3) && this.m_77263_(node2)) {
         p_77266_[i++] = node14;
      }

      Node node15 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node15) && this.m_77263_(node) && this.m_77263_(node2)) {
         p_77266_[i++] = node15;
      }

      Node node16 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node16) && this.m_77263_(node3) && this.m_77263_(node1)) {
         p_77266_[i++] = node16;
      }

      Node node17 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node17) && this.m_77263_(node) && this.m_77263_(node1)) {
         p_77266_[i++] = node17;
      }

      Node node18 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node18) && this.m_77263_(node14) && this.m_77263_(node3) && this.m_77263_(node2) && this.m_77263_(node4) && this.m_77263_(node9) && this.m_77263_(node8)) {
         p_77266_[i++] = node18;
      }

      Node node19 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node19) && this.m_77263_(node15) && this.m_77263_(node) && this.m_77263_(node2) && this.m_77263_(node4) && this.m_77263_(node6) && this.m_77263_(node8)) {
         p_77266_[i++] = node19;
      }

      Node node20 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node20) && this.m_77263_(node16) && this.m_77263_(node3) && this.m_77263_(node1) && this.m_77263_(node4) && this.m_77263_(node9) && this.m_77263_(node7)) {
         p_77266_[i++] = node20;
      }

      Node node21 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node21) && this.m_77263_(node17) && this.m_77263_(node) && this.m_77263_(node1) && this.m_77263_(node4) && this.m_77263_(node6) && this.m_77263_(node7)) {
         p_77266_[i++] = node21;
      }

      Node node22 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node22) && this.m_77263_(node14) && this.m_77263_(node3) && this.m_77263_(node2) && this.m_77263_(node5) && this.m_77263_(node13) && this.m_77263_(node12)) {
         p_77266_[i++] = node22;
      }

      Node node23 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node23) && this.m_77263_(node15) && this.m_77263_(node) && this.m_77263_(node2) && this.m_77263_(node5) && this.m_77263_(node10) && this.m_77263_(node12)) {
         p_77266_[i++] = node23;
      }

      Node node24 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1);
      if (this.m_77269_(node24) && this.m_77263_(node16) && this.m_77263_(node3) && this.m_77263_(node1) && this.m_77263_(node5) && this.m_77263_(node13) && this.m_77263_(node11)) {
         p_77266_[i++] = node24;
      }

      Node node25 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1);
      if (this.m_77269_(node25) && this.m_77263_(node17) && this.m_77263_(node) && this.m_77263_(node1) && this.m_77263_(node5) && this.m_77263_(node10) && this.m_77263_(node11)) {
         p_77266_[i++] = node25;
      }

      return i;
   }

   private boolean m_77263_(@Nullable Node p_77264_) {
      return p_77264_ != null && p_77264_.f_77281_ >= 0.0F;
   }

   private boolean m_77269_(@Nullable Node p_77270_) {
      return p_77270_ != null && !p_77270_.f_77279_;
   }

   @Nullable
   protected Node m_5676_(int p_77233_, int p_77234_, int p_77235_) {
      Node node = null;
      BlockPathTypes blockpathtypes = this.m_164693_(p_77233_, p_77234_, p_77235_);
      float f = this.f_77313_.m_21439_(blockpathtypes);
      if (f >= 0.0F) {
         node = super.m_5676_(p_77233_, p_77234_, p_77235_);
         node.f_77282_ = blockpathtypes;
         node.f_77281_ = Math.max(node.f_77281_, f);
         if (blockpathtypes == BlockPathTypes.WALKABLE) {
            ++node.f_77281_;
         }
      }

      return node;
   }

   private BlockPathTypes m_164693_(int p_164694_, int p_164695_, int p_164696_) {
      return this.f_164687_.computeIfAbsent(BlockPos.m_121882_(p_164694_, p_164695_, p_164696_), (p_164692_) -> {
         return this.m_7209_(this.f_77312_, p_164694_, p_164695_, p_164696_, this.f_77313_, this.f_77315_, this.f_77316_, this.f_77317_, this.m_77360_(), this.m_77357_());
      });
   }

   public BlockPathTypes m_7209_(BlockGetter p_77250_, int p_77251_, int p_77252_, int p_77253_, Mob p_77254_, int p_77255_, int p_77256_, int p_77257_, boolean p_77258_, boolean p_77259_) {
      EnumSet<BlockPathTypes> enumset = EnumSet.noneOf(BlockPathTypes.class);
      BlockPathTypes blockpathtypes = BlockPathTypes.BLOCKED;
      BlockPos blockpos = p_77254_.m_142538_();
      blockpathtypes = super.m_77580_(p_77250_, p_77251_, p_77252_, p_77253_, p_77255_, p_77256_, p_77257_, p_77258_, p_77259_, enumset, blockpathtypes, blockpos);
      if (enumset.contains(BlockPathTypes.FENCE)) {
         return BlockPathTypes.FENCE;
      } else {
         BlockPathTypes blockpathtypes1 = BlockPathTypes.BLOCKED;

         for(BlockPathTypes blockpathtypes2 : enumset) {
            if (p_77254_.m_21439_(blockpathtypes2) < 0.0F) {
               return blockpathtypes2;
            }

            if (p_77254_.m_21439_(blockpathtypes2) >= p_77254_.m_21439_(blockpathtypes1)) {
               blockpathtypes1 = blockpathtypes2;
            }
         }

         return blockpathtypes == BlockPathTypes.OPEN && p_77254_.m_21439_(blockpathtypes1) == 0.0F ? BlockPathTypes.OPEN : blockpathtypes1;
      }
   }

   public BlockPathTypes m_8086_(BlockGetter p_77245_, int p_77246_, int p_77247_, int p_77248_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPathTypes blockpathtypes = m_77643_(p_77245_, blockpos$mutableblockpos.m_122178_(p_77246_, p_77247_, p_77248_));
      if (blockpathtypes == BlockPathTypes.OPEN && p_77247_ >= p_77245_.m_141937_() + 1) {
         BlockPathTypes blockpathtypes1 = m_77643_(p_77245_, blockpos$mutableblockpos.m_122178_(p_77246_, p_77247_ - 1, p_77248_));
         if (blockpathtypes1 != BlockPathTypes.DAMAGE_FIRE && blockpathtypes1 != BlockPathTypes.LAVA) {
            if (blockpathtypes1 == BlockPathTypes.DAMAGE_CACTUS) {
               blockpathtypes = BlockPathTypes.DAMAGE_CACTUS;
            } else if (blockpathtypes1 == BlockPathTypes.DAMAGE_OTHER) {
               blockpathtypes = BlockPathTypes.DAMAGE_OTHER;
            } else if (blockpathtypes1 == BlockPathTypes.COCOA) {
               blockpathtypes = BlockPathTypes.COCOA;
            } else if (blockpathtypes1 == BlockPathTypes.FENCE) {
               blockpathtypes = BlockPathTypes.FENCE;
            } else {
               blockpathtypes = blockpathtypes1 != BlockPathTypes.WALKABLE && blockpathtypes1 != BlockPathTypes.OPEN && blockpathtypes1 != BlockPathTypes.WATER ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
            }
         } else {
            blockpathtypes = BlockPathTypes.DAMAGE_FIRE;
         }
      }

      if (blockpathtypes == BlockPathTypes.WALKABLE || blockpathtypes == BlockPathTypes.OPEN) {
         blockpathtypes = m_77607_(p_77245_, blockpos$mutableblockpos.m_122178_(p_77246_, p_77247_, p_77248_), blockpathtypes);
      }

      return blockpathtypes;
   }
}