package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class RandomPatchFeature extends Feature<RandomPatchConfiguration> {
   public RandomPatchFeature(Codec<RandomPatchConfiguration> p_66605_) {
      super(p_66605_);
   }

   public boolean m_142674_(FeaturePlaceContext<RandomPatchConfiguration> p_160210_) {
      RandomPatchConfiguration randompatchconfiguration = p_160210_.m_159778_();
      Random random = p_160210_.m_159776_();
      BlockPos blockpos = p_160210_.m_159777_();
      WorldGenLevel worldgenlevel = p_160210_.m_159774_();
      BlockState blockstate = randompatchconfiguration.f_67903_.m_7112_(random, blockpos);
      BlockPos blockpos1;
      if (randompatchconfiguration.f_67912_) {
         blockpos1 = worldgenlevel.m_5452_(Heightmap.Types.WORLD_SURFACE_WG, blockpos);
      } else {
         blockpos1 = blockpos;
      }

      int i = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j = 0; j < randompatchconfiguration.f_67907_; ++j) {
         blockpos$mutableblockpos.m_122154_(blockpos1, random.nextInt(randompatchconfiguration.f_67908_ + 1) - random.nextInt(randompatchconfiguration.f_67908_ + 1), random.nextInt(randompatchconfiguration.f_67909_ + 1) - random.nextInt(randompatchconfiguration.f_67909_ + 1), random.nextInt(randompatchconfiguration.f_67910_ + 1) - random.nextInt(randompatchconfiguration.f_67910_ + 1));
         BlockPos blockpos2 = blockpos$mutableblockpos.m_7495_();
         BlockState blockstate1 = worldgenlevel.m_8055_(blockpos2);
         if ((worldgenlevel.m_46859_(blockpos$mutableblockpos) || randompatchconfiguration.f_67911_ && worldgenlevel.m_8055_(blockpos$mutableblockpos).m_60767_().m_76336_()) && blockstate.m_60710_(worldgenlevel, blockpos$mutableblockpos) && (randompatchconfiguration.f_67905_.isEmpty() || randompatchconfiguration.f_67905_.contains(blockstate1.m_60734_())) && !randompatchconfiguration.f_67906_.contains(blockstate1) && (!randompatchconfiguration.f_67913_ || worldgenlevel.m_6425_(blockpos2.m_142125_()).m_76153_(FluidTags.f_13131_) || worldgenlevel.m_6425_(blockpos2.m_142126_()).m_76153_(FluidTags.f_13131_) || worldgenlevel.m_6425_(blockpos2.m_142127_()).m_76153_(FluidTags.f_13131_) || worldgenlevel.m_6425_(blockpos2.m_142128_()).m_76153_(FluidTags.f_13131_))) {
            randompatchconfiguration.f_67904_.m_7275_(worldgenlevel, blockpos$mutableblockpos, blockstate, random);
            ++i;
         }
      }

      return i > 0;
   }
}