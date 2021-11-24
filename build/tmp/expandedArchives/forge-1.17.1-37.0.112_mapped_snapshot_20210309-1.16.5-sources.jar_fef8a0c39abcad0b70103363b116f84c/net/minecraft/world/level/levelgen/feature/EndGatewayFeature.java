package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;

public class EndGatewayFeature extends Feature<EndGatewayConfiguration> {
   public EndGatewayFeature(Codec<EndGatewayConfiguration> p_65682_) {
      super(p_65682_);
   }

   public boolean m_142674_(FeaturePlaceContext<EndGatewayConfiguration> p_159715_) {
      BlockPos blockpos = p_159715_.m_159777_();
      WorldGenLevel worldgenlevel = p_159715_.m_159774_();
      EndGatewayConfiguration endgatewayconfiguration = p_159715_.m_159778_();

      for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-1, -2, -1), blockpos.m_142082_(1, 2, 1))) {
         boolean flag = blockpos1.m_123341_() == blockpos.m_123341_();
         boolean flag1 = blockpos1.m_123342_() == blockpos.m_123342_();
         boolean flag2 = blockpos1.m_123343_() == blockpos.m_123343_();
         boolean flag3 = Math.abs(blockpos1.m_123342_() - blockpos.m_123342_()) == 2;
         if (flag && flag1 && flag2) {
            BlockPos blockpos2 = blockpos1.m_7949_();
            this.m_5974_(worldgenlevel, blockpos2, Blocks.f_50446_.m_49966_());
            endgatewayconfiguration.m_67656_().ifPresent((p_65699_) -> {
               BlockEntity blockentity = worldgenlevel.m_7702_(blockpos2);
               if (blockentity instanceof TheEndGatewayBlockEntity) {
                  TheEndGatewayBlockEntity theendgatewayblockentity = (TheEndGatewayBlockEntity)blockentity;
                  theendgatewayblockentity.m_59955_(p_65699_, endgatewayconfiguration.m_67657_());
                  blockentity.m_6596_();
               }

            });
         } else if (flag1) {
            this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50016_.m_49966_());
         } else if (flag3 && flag && flag2) {
            this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50752_.m_49966_());
         } else if ((flag || flag2) && !flag3) {
            this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50752_.m_49966_());
         } else {
            this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50016_.m_49966_());
         }
      }

      return true;
   }
}