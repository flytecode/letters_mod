package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class NetherSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private static final BlockState f_75067_ = Blocks.f_50627_.m_49966_();
   private static final BlockState f_75068_ = Blocks.f_49994_.m_49966_();
   private static final BlockState f_75069_ = Blocks.f_50135_.m_49966_();
   protected long f_75065_;
   protected PerlinNoise f_75066_;

   public NetherSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_75072_) {
      super(p_75072_);
   }

   public void m_142263_(Random p_164130_, ChunkAccess p_164131_, Biome p_164132_, int p_164133_, int p_164134_, int p_164135_, double p_164136_, BlockState p_164137_, BlockState p_164138_, int p_164139_, int p_164140_, long p_164141_, SurfaceBuilderBaseConfiguration p_164142_) {
      int i = p_164139_;
      int j = p_164133_ & 15;
      int k = p_164134_ & 15;
      double d0 = 0.03125D;
      boolean flag = this.f_75066_.m_75408_((double)p_164133_ * 0.03125D, (double)p_164134_ * 0.03125D, 0.0D) * 75.0D + p_164130_.nextDouble() > 0.0D;
      boolean flag1 = this.f_75066_.m_75408_((double)p_164133_ * 0.03125D, 109.0D, (double)p_164134_ * 0.03125D) * 75.0D + p_164130_.nextDouble() > 0.0D;
      int l = (int)(p_164136_ / 3.0D + 3.0D + p_164130_.nextDouble() * 0.25D);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i1 = -1;
      BlockState blockstate = p_164142_.m_6743_();
      BlockState blockstate1 = p_164142_.m_6744_();

      for(int j1 = 127; j1 >= p_164140_; --j1) {
         blockpos$mutableblockpos.m_122178_(j, j1, k);
         BlockState blockstate2 = p_164131_.m_8055_(blockpos$mutableblockpos);
         if (blockstate2.m_60795_()) {
            i1 = -1;
         } else if (blockstate2.m_60713_(p_164137_.m_60734_())) {
            if (i1 == -1) {
               boolean flag2 = false;
               if (l <= 0) {
                  flag2 = true;
                  blockstate1 = p_164142_.m_6744_();
               } else if (j1 >= i - 4 && j1 <= i + 1) {
                  blockstate = p_164142_.m_6743_();
                  blockstate1 = p_164142_.m_6744_();
                  if (flag1) {
                     blockstate = f_75068_;
                     blockstate1 = p_164142_.m_6744_();
                  }

                  if (flag) {
                     blockstate = f_75069_;
                     blockstate1 = f_75069_;
                  }
               }

               if (j1 < i && flag2) {
                  blockstate = p_164138_;
               }

               i1 = l;
               if (j1 >= i - 1) {
                  p_164131_.m_6978_(blockpos$mutableblockpos, blockstate, false);
               } else {
                  p_164131_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
               }
            } else if (i1 > 0) {
               --i1;
               p_164131_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
            }
         }
      }

   }

   public void m_6190_(long p_75074_) {
      if (this.f_75065_ != p_75074_ || this.f_75066_ == null) {
         this.f_75066_ = new PerlinNoise(new WorldgenRandom(p_75074_), IntStream.rangeClosed(-3, 0));
      }

      this.f_75065_ = p_75074_;
   }
}