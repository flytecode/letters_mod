package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class NetherForestVegetationFeature extends Feature<BlockPileConfiguration> {
   public NetherForestVegetationFeature(Codec<BlockPileConfiguration> p_66361_) {
      super(p_66361_);
   }

   public boolean m_142674_(FeaturePlaceContext<BlockPileConfiguration> p_160068_) {
      return m_66362_(p_160068_.m_159774_(), p_160068_.m_159776_(), p_160068_.m_159777_(), p_160068_.m_159778_(), 8, 4);
   }

   public static boolean m_66362_(LevelAccessor p_66363_, Random p_66364_, BlockPos p_66365_, BlockPileConfiguration p_66366_, int p_66367_, int p_66368_) {
      BlockState blockstate = p_66363_.m_8055_(p_66365_.m_7495_());
      if (!blockstate.m_60620_(BlockTags.f_13077_)) {
         return false;
      } else {
         int i = p_66365_.m_123342_();
         if (i >= p_66363_.m_141937_() + 1 && i + 1 < p_66363_.m_151558_()) {
            int j = 0;

            for(int k = 0; k < p_66367_ * p_66367_; ++k) {
               BlockPos blockpos = p_66365_.m_142082_(p_66364_.nextInt(p_66367_) - p_66364_.nextInt(p_66367_), p_66364_.nextInt(p_66368_) - p_66364_.nextInt(p_66368_), p_66364_.nextInt(p_66367_) - p_66364_.nextInt(p_66367_));
               BlockState blockstate1 = p_66366_.f_67540_.m_7112_(p_66364_, blockpos);
               if (p_66363_.m_46859_(blockpos) && blockpos.m_123342_() > p_66363_.m_141937_() && blockstate1.m_60710_(p_66363_, blockpos)) {
                  p_66363_.m_7731_(blockpos, blockstate1, 2);
                  ++j;
               }
            }

            return j > 0;
         } else {
            return false;
         }
      }
   }
}