package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class NetherVines {
   private static final double f_153987_ = 0.826D;
   public static final double f_153986_ = 0.1D;

   public static boolean m_54963_(BlockState p_54964_) {
      return p_54964_.m_60795_();
   }

   public static int m_54965_(Random p_54966_) {
      double d0 = 1.0D;

      int i;
      for(i = 0; p_54966_.nextDouble() < d0; ++i) {
         d0 *= 0.826D;
      }

      return i;
   }
}