package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.TwistingVinesFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;

public class NyliumBlock extends Block implements BonemealableBlock {
   public NyliumBlock(BlockBehaviour.Properties p_55057_) {
      super(p_55057_);
   }

   private static boolean m_55078_(BlockState p_55079_, LevelReader p_55080_, BlockPos p_55081_) {
      BlockPos blockpos = p_55081_.m_7494_();
      BlockState blockstate = p_55080_.m_8055_(blockpos);
      int i = LayerLightEngine.m_75667_(p_55080_, p_55079_, p_55081_, blockstate, blockpos, Direction.UP, blockstate.m_60739_(p_55080_, blockpos));
      return i < p_55080_.m_7469_();
   }

   public void m_7455_(BlockState p_55074_, ServerLevel p_55075_, BlockPos p_55076_, Random p_55077_) {
      if (!m_55078_(p_55074_, p_55075_, p_55076_)) {
         p_55075_.m_46597_(p_55076_, Blocks.f_50134_.m_49966_());
      }

   }

   public boolean m_7370_(BlockGetter p_55064_, BlockPos p_55065_, BlockState p_55066_, boolean p_55067_) {
      return p_55064_.m_8055_(p_55065_.m_7494_()).m_60795_();
   }

   public boolean m_5491_(Level p_55069_, Random p_55070_, BlockPos p_55071_, BlockState p_55072_) {
      return true;
   }

   public void m_7719_(ServerLevel p_55059_, Random p_55060_, BlockPos p_55061_, BlockState p_55062_) {
      BlockState blockstate = p_55059_.m_8055_(p_55061_);
      BlockPos blockpos = p_55061_.m_7494_();
      if (blockstate.m_60713_(Blocks.f_50699_)) {
         NetherForestVegetationFeature.m_66362_(p_55059_, p_55060_, blockpos, Features.Configs.f_127076_, 3, 1);
      } else if (blockstate.m_60713_(Blocks.f_50690_)) {
         NetherForestVegetationFeature.m_66362_(p_55059_, p_55060_, blockpos, Features.Configs.f_127077_, 3, 1);
         NetherForestVegetationFeature.m_66362_(p_55059_, p_55060_, blockpos, Features.Configs.f_127078_, 3, 1);
         if (p_55060_.nextInt(8) == 0) {
            TwistingVinesFeature.m_67306_(p_55059_, p_55060_, blockpos, 3, 1, 2);
         }
      }

   }
}