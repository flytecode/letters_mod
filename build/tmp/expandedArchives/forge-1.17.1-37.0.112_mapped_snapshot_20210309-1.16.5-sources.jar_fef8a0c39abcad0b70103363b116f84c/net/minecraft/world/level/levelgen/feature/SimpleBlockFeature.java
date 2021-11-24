package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class SimpleBlockFeature extends Feature<SimpleBlockConfiguration> {
   public SimpleBlockFeature(Codec<SimpleBlockConfiguration> p_66808_) {
      super(p_66808_);
   }

   public boolean m_142674_(FeaturePlaceContext<SimpleBlockConfiguration> p_160341_) {
      SimpleBlockConfiguration simpleblockconfiguration = p_160341_.m_159778_();
      WorldGenLevel worldgenlevel = p_160341_.m_159774_();
      BlockPos blockpos = p_160341_.m_159777_();
      if ((simpleblockconfiguration.f_68070_.isEmpty() || simpleblockconfiguration.f_68070_.contains(worldgenlevel.m_8055_(blockpos.m_7495_()))) && (simpleblockconfiguration.f_68071_.isEmpty() || simpleblockconfiguration.f_68071_.contains(worldgenlevel.m_8055_(blockpos))) && (simpleblockconfiguration.f_68072_.isEmpty() || simpleblockconfiguration.f_68072_.contains(worldgenlevel.m_8055_(blockpos.m_7494_())))) {
         BlockState blockstate = simpleblockconfiguration.f_68069_.m_7112_(p_160341_.m_159776_(), blockpos);
         if (blockstate.m_60710_(worldgenlevel, blockpos)) {
            if (blockstate.m_60734_() instanceof DoublePlantBlock) {
               if (!worldgenlevel.m_46859_(blockpos.m_7494_())) {
                  return false;
               }

               DoublePlantBlock.m_153173_(worldgenlevel, blockstate, blockpos, 2);
            } else {
               worldgenlevel.m_7731_(blockpos, blockstate, 2);
            }

            return true;
         }
      }

      return false;
   }
}