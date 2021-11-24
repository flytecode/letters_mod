package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface ChangeOverTimeBlock<T extends Enum<T>> {
   int f_153035_ = 4;

   Optional<BlockState> m_142123_(BlockState p_153040_);

   float m_142377_();

   default void m_153041_(BlockState p_153042_, ServerLevel p_153043_, BlockPos p_153044_, Random p_153045_) {
      float f = 0.05688889F;
      if (p_153045_.nextFloat() < 0.05688889F) {
         this.m_153046_(p_153042_, p_153043_, p_153044_, p_153045_);
      }

   }

   T m_142297_();

   default void m_153046_(BlockState p_153047_, ServerLevel p_153048_, BlockPos p_153049_, Random p_153050_) {
      int i = this.m_142297_().ordinal();
      int j = 0;
      int k = 0;

      for(BlockPos blockpos : BlockPos.m_121925_(p_153049_, 4, 4, 4)) {
         int l = blockpos.m_123333_(p_153049_);
         if (l > 4) {
            break;
         }

         if (!blockpos.equals(p_153049_)) {
            BlockState blockstate = p_153048_.m_8055_(blockpos);
            Block block = blockstate.m_60734_();
            if (block instanceof ChangeOverTimeBlock) {
               Enum<?> oenum = ((ChangeOverTimeBlock)block).m_142297_();
               if (this.m_142297_().getClass() == oenum.getClass()) {
                  int i1 = oenum.ordinal();
                  if (i1 < i) {
                     return;
                  }

                  if (i1 > i) {
                     ++k;
                  } else {
                     ++j;
                  }
               }
            }
         }
      }

      float f = (float)(k + 1) / (float)(k + j + 1);
      float f1 = f * f * this.m_142377_();
      if (p_153050_.nextFloat() < f1) {
         this.m_142123_(p_153047_).ifPresent((p_153039_) -> {
            p_153048_.m_46597_(p_153049_, p_153039_);
         });
      }

   }
}