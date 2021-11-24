package net.minecraft.world.level.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndPodiumFeature extends Feature<NoneFeatureConfiguration> {
   public static final int f_159718_ = 4;
   public static final int f_159719_ = 4;
   public static final int f_159720_ = 1;
   public static final float f_159721_ = 0.5F;
   public static final BlockPos f_65714_ = BlockPos.f_121853_;
   private final boolean f_65715_;

   public EndPodiumFeature(boolean p_65718_) {
      super(NoneFeatureConfiguration.f_67815_);
      this.f_65715_ = p_65718_;
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159723_) {
      BlockPos blockpos = p_159723_.m_159777_();
      WorldGenLevel worldgenlevel = p_159723_.m_159774_();

      for(BlockPos blockpos1 : BlockPos.m_121940_(new BlockPos(blockpos.m_123341_() - 4, blockpos.m_123342_() - 1, blockpos.m_123343_() - 4), new BlockPos(blockpos.m_123341_() + 4, blockpos.m_123342_() + 32, blockpos.m_123343_() + 4))) {
         boolean flag = blockpos1.m_123314_(blockpos, 2.5D);
         if (flag || blockpos1.m_123314_(blockpos, 3.5D)) {
            if (blockpos1.m_123342_() < blockpos.m_123342_()) {
               if (flag) {
                  this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50752_.m_49966_());
               } else if (blockpos1.m_123342_() < blockpos.m_123342_()) {
                  this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50259_.m_49966_());
               }
            } else if (blockpos1.m_123342_() > blockpos.m_123342_()) {
               this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50016_.m_49966_());
            } else if (!flag) {
               this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50752_.m_49966_());
            } else if (this.f_65715_) {
               this.m_5974_(worldgenlevel, new BlockPos(blockpos1), Blocks.f_50257_.m_49966_());
            } else {
               this.m_5974_(worldgenlevel, new BlockPos(blockpos1), Blocks.f_50016_.m_49966_());
            }
         }
      }

      for(int i = 0; i < 4; ++i) {
         this.m_5974_(worldgenlevel, blockpos.m_6630_(i), Blocks.f_50752_.m_49966_());
      }

      BlockPos blockpos2 = blockpos.m_6630_(2);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         this.m_5974_(worldgenlevel, blockpos2.m_142300_(direction), Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, direction));
      }

      return true;
   }
}