package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class DefaultSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   public DefaultSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74788_) {
      super(p_74788_);
   }

   public void m_142263_(Random p_163891_, ChunkAccess p_163892_, Biome p_163893_, int p_163894_, int p_163895_, int p_163896_, double p_163897_, BlockState p_163898_, BlockState p_163899_, int p_163900_, int p_163901_, long p_163902_, SurfaceBuilderBaseConfiguration p_163903_) {
      this.m_163918_(p_163891_, p_163892_, p_163893_, p_163894_, p_163895_, p_163896_, p_163897_, p_163898_, p_163899_, p_163903_.m_6743_(), p_163903_.m_6744_(), p_163903_.m_142434_(), p_163900_, p_163901_);
   }

   protected void m_163918_(Random p_163919_, ChunkAccess p_163920_, Biome p_163921_, int p_163922_, int p_163923_, int p_163924_, double p_163925_, BlockState p_163926_, BlockState p_163927_, BlockState p_163928_, BlockState p_163929_, BlockState p_163930_, int p_163931_, int p_163932_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = (int)(p_163925_ / 3.0D + 3.0D + p_163919_.nextDouble() * 0.25D);
      if (i == 0) {
         boolean flag = false;

         for(int j = p_163924_; j >= p_163932_; --j) {
            blockpos$mutableblockpos.m_122178_(p_163922_, j, p_163923_);
            BlockState blockstate = p_163920_.m_8055_(blockpos$mutableblockpos);
            if (blockstate.m_60795_()) {
               flag = false;
            } else if (blockstate.m_60713_(p_163926_.m_60734_())) {
               if (!flag) {
                  BlockState blockstate1;
                  if (j >= p_163931_) {
                     blockstate1 = Blocks.f_50016_.m_49966_();
                  } else if (j == p_163931_ - 1) {
                     blockstate1 = p_163921_.m_47505_(blockpos$mutableblockpos) < 0.15F ? Blocks.f_50126_.m_49966_() : p_163927_;
                  } else if (j >= p_163931_ - (7 + i)) {
                     blockstate1 = p_163926_;
                  } else {
                     blockstate1 = p_163930_;
                  }

                  p_163920_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
               }

               flag = true;
            }
         }
      } else {
         BlockState blockstate3 = p_163929_;
         int k = -1;

         for(int l = p_163924_; l >= p_163932_; --l) {
            blockpos$mutableblockpos.m_122178_(p_163922_, l, p_163923_);
            BlockState blockstate4 = p_163920_.m_8055_(blockpos$mutableblockpos);
            if (blockstate4.m_60795_()) {
               k = -1;
            } else if (blockstate4.m_60713_(p_163926_.m_60734_())) {
               if (k == -1) {
                  k = i;
                  BlockState blockstate2;
                  if (l >= p_163931_ + 2) {
                     blockstate2 = p_163928_;
                  } else if (l >= p_163931_ - 1) {
                     blockstate3 = p_163929_;
                     blockstate2 = p_163928_;
                  } else if (l >= p_163931_ - 4) {
                     blockstate3 = p_163929_;
                     blockstate2 = p_163929_;
                  } else if (l >= p_163931_ - (7 + i)) {
                     blockstate2 = blockstate3;
                  } else {
                     blockstate3 = p_163926_;
                     blockstate2 = p_163930_;
                  }

                  p_163920_.m_6978_(blockpos$mutableblockpos, blockstate2, false);
               } else if (k > 0) {
                  --k;
                  p_163920_.m_6978_(blockpos$mutableblockpos, blockstate3, false);
                  if (k == 0 && blockstate3.m_60713_(Blocks.f_49992_) && i > 1) {
                     k = p_163919_.nextInt(4) + Math.max(0, l - p_163931_);
                     blockstate3 = blockstate3.m_60713_(Blocks.f_49993_) ? Blocks.f_50394_.m_49966_() : Blocks.f_50062_.m_49966_();
                  }
               }
            }
         }
      }

   }
}