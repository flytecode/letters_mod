package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class ScatteredOreFeature extends Feature<OreConfiguration> {
   private static final int f_160302_ = 7;

   ScatteredOreFeature(Codec<OreConfiguration> p_160304_) {
      super(p_160304_);
   }

   public boolean m_142674_(FeaturePlaceContext<OreConfiguration> p_160306_) {
      WorldGenLevel worldgenlevel = p_160306_.m_159774_();
      Random random = p_160306_.m_159776_();
      OreConfiguration oreconfiguration = p_160306_.m_159778_();
      BlockPos blockpos = p_160306_.m_159777_();
      int i = random.nextInt(oreconfiguration.f_67839_ + 1);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = 0; j < i; ++j) {
         this.m_160307_(blockpos$mutableblockpos, random, blockpos, Math.min(j, 7));
         BlockState blockstate = worldgenlevel.m_8055_(blockpos$mutableblockpos);

         for(OreConfiguration.TargetBlockState oreconfiguration$targetblockstate : oreconfiguration.f_161005_) {
            if (OreFeature.m_160169_(blockstate, worldgenlevel::m_8055_, random, oreconfiguration, oreconfiguration$targetblockstate, blockpos$mutableblockpos)) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos, oreconfiguration$targetblockstate.f_161033_, 2);
               break;
            }
         }
      }

      return true;
   }

   private void m_160307_(BlockPos.MutableBlockPos p_160308_, Random p_160309_, BlockPos p_160310_, int p_160311_) {
      int i = this.m_160312_(p_160309_, p_160311_);
      int j = this.m_160312_(p_160309_, p_160311_);
      int k = this.m_160312_(p_160309_, p_160311_);
      p_160308_.m_122154_(p_160310_, i, j, k);
   }

   private int m_160312_(Random p_160313_, int p_160314_) {
      return Math.round((p_160313_.nextFloat() - p_160313_.nextFloat()) * (float)p_160314_);
   }
}