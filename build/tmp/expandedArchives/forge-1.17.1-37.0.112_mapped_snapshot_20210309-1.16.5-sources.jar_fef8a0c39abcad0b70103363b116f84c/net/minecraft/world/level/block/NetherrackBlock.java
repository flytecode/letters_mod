package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class NetherrackBlock extends Block implements BonemealableBlock {
   public NetherrackBlock(BlockBehaviour.Properties p_54995_) {
      super(p_54995_);
   }

   public boolean m_7370_(BlockGetter p_55002_, BlockPos p_55003_, BlockState p_55004_, boolean p_55005_) {
      if (!p_55002_.m_8055_(p_55003_.m_7494_()).m_60631_(p_55002_, p_55003_)) {
         return false;
      } else {
         for(BlockPos blockpos : BlockPos.m_121940_(p_55003_.m_142082_(-1, -1, -1), p_55003_.m_142082_(1, 1, 1))) {
            if (p_55002_.m_8055_(blockpos).m_60620_(BlockTags.f_13077_)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean m_5491_(Level p_55007_, Random p_55008_, BlockPos p_55009_, BlockState p_55010_) {
      return true;
   }

   public void m_7719_(ServerLevel p_54997_, Random p_54998_, BlockPos p_54999_, BlockState p_55000_) {
      boolean flag = false;
      boolean flag1 = false;

      for(BlockPos blockpos : BlockPos.m_121940_(p_54999_.m_142082_(-1, -1, -1), p_54999_.m_142082_(1, 1, 1))) {
         BlockState blockstate = p_54997_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50690_)) {
            flag1 = true;
         }

         if (blockstate.m_60713_(Blocks.f_50699_)) {
            flag = true;
         }

         if (flag1 && flag) {
            break;
         }
      }

      if (flag1 && flag) {
         p_54997_.m_7731_(p_54999_, p_54998_.nextBoolean() ? Blocks.f_50690_.m_49966_() : Blocks.f_50699_.m_49966_(), 3);
      } else if (flag1) {
         p_54997_.m_7731_(p_54999_, Blocks.f_50690_.m_49966_(), 3);
      } else if (flag) {
         p_54997_.m_7731_(p_54999_, Blocks.f_50699_.m_49966_(), 3);
      }

   }
}