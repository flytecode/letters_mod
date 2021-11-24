package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

public abstract class SpreadingSnowyDirtBlock extends SnowyDirtBlock {
   protected SpreadingSnowyDirtBlock(BlockBehaviour.Properties p_56817_) {
      super(p_56817_);
   }

   private static boolean m_56823_(BlockState p_56824_, LevelReader p_56825_, BlockPos p_56826_) {
      BlockPos blockpos = p_56826_.m_7494_();
      BlockState blockstate = p_56825_.m_8055_(blockpos);
      if (blockstate.m_60713_(Blocks.f_50125_) && blockstate.m_61143_(SnowLayerBlock.f_56581_) == 1) {
         return true;
      } else if (blockstate.m_60819_().m_76186_() == 8) {
         return false;
      } else {
         int i = LayerLightEngine.m_75667_(p_56825_, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.m_60739_(p_56825_, blockpos));
         return i < p_56825_.m_7469_();
      }
   }

   private static boolean m_56827_(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
      BlockPos blockpos = p_56830_.m_7494_();
      return m_56823_(p_56828_, p_56829_, p_56830_) && !p_56829_.m_6425_(blockpos).m_76153_(FluidTags.f_13131_);
   }

   public void m_7455_(BlockState p_56819_, ServerLevel p_56820_, BlockPos p_56821_, Random p_56822_) {
      if (!m_56823_(p_56819_, p_56820_, p_56821_)) {
         if (!p_56820_.isAreaLoaded(p_56821_, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
         p_56820_.m_46597_(p_56821_, Blocks.f_50493_.m_49966_());
      } else {
         if (p_56820_.m_46803_(p_56821_.m_7494_()) >= 9) {
            BlockState blockstate = this.m_49966_();

            for(int i = 0; i < 4; ++i) {
               BlockPos blockpos = p_56821_.m_142082_(p_56822_.nextInt(3) - 1, p_56822_.nextInt(5) - 3, p_56822_.nextInt(3) - 1);
               if (p_56820_.m_8055_(blockpos).m_60713_(Blocks.f_50493_) && m_56827_(blockstate, p_56820_, blockpos)) {
                  p_56820_.m_46597_(blockpos, blockstate.m_61124_(f_56637_, Boolean.valueOf(p_56820_.m_8055_(blockpos.m_7494_()).m_60713_(Blocks.f_50125_))));
               }
            }
         }

      }
   }
}
