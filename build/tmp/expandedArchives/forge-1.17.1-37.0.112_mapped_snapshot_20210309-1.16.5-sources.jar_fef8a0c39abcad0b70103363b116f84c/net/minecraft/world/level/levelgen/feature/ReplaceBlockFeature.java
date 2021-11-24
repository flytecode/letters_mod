package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;

public class ReplaceBlockFeature extends Feature<ReplaceBlockConfiguration> {
   public ReplaceBlockFeature(Codec<ReplaceBlockConfiguration> p_66651_) {
      super(p_66651_);
   }

   public boolean m_142674_(FeaturePlaceContext<ReplaceBlockConfiguration> p_160216_) {
      WorldGenLevel worldgenlevel = p_160216_.m_159774_();
      BlockPos blockpos = p_160216_.m_159777_();
      ReplaceBlockConfiguration replaceblockconfiguration = p_160216_.m_159778_();

      for(OreConfiguration.TargetBlockState oreconfiguration$targetblockstate : replaceblockconfiguration.f_161083_) {
         if (oreconfiguration$targetblockstate.f_161032_.m_7715_(worldgenlevel.m_8055_(blockpos), p_160216_.m_159776_())) {
            worldgenlevel.m_7731_(blockpos, oreconfiguration$targetblockstate.f_161033_, 2);
            break;
         }
      }

      return true;
   }
}