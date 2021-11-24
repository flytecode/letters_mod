package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class NetherForestSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private static final BlockState f_75032_ = Blocks.f_50627_.m_49966_();
   protected long f_75031_;
   private PerlinNoise f_75033_;

   public NetherForestSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_75036_) {
      super(p_75036_);
   }

   public void m_142263_(Random p_164102_, ChunkAccess p_164103_, Biome p_164104_, int p_164105_, int p_164106_, int p_164107_, double p_164108_, BlockState p_164109_, BlockState p_164110_, int p_164111_, int p_164112_, long p_164113_, SurfaceBuilderBaseConfiguration p_164114_) {
      int i = p_164111_;
      int j = p_164105_ & 15;
      int k = p_164106_ & 15;
      double d0 = this.f_75033_.m_75408_((double)p_164105_ * 0.1D, (double)p_164111_, (double)p_164106_ * 0.1D);
      boolean flag = d0 > 0.15D + p_164102_.nextDouble() * 0.35D;
      double d1 = this.f_75033_.m_75408_((double)p_164105_ * 0.1D, 109.0D, (double)p_164106_ * 0.1D);
      boolean flag1 = d1 > 0.25D + p_164102_.nextDouble() * 0.9D;
      int l = (int)(p_164108_ / 3.0D + 3.0D + p_164102_.nextDouble() * 0.25D);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i1 = -1;
      BlockState blockstate = p_164114_.m_6744_();

      for(int j1 = 127; j1 >= p_164112_; --j1) {
         blockpos$mutableblockpos.m_122178_(j, j1, k);
         BlockState blockstate1 = p_164114_.m_6743_();
         BlockState blockstate2 = p_164103_.m_8055_(blockpos$mutableblockpos);
         if (blockstate2.m_60795_()) {
            i1 = -1;
         } else if (blockstate2.m_60713_(p_164109_.m_60734_())) {
            if (i1 == -1) {
               boolean flag2 = false;
               if (l <= 0) {
                  flag2 = true;
                  blockstate = p_164114_.m_6744_();
               }

               if (flag) {
                  blockstate1 = p_164114_.m_6744_();
               } else if (flag1) {
                  blockstate1 = p_164114_.m_142434_();
               }

               if (j1 < i && flag2) {
                  blockstate1 = p_164110_;
               }

               i1 = l;
               if (j1 >= i - 1) {
                  p_164103_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
               } else {
                  p_164103_.m_6978_(blockpos$mutableblockpos, blockstate, false);
               }
            } else if (i1 > 0) {
               --i1;
               p_164103_.m_6978_(blockpos$mutableblockpos, blockstate, false);
            }
         }
      }

   }

   public void m_6190_(long p_75038_) {
      if (this.f_75031_ != p_75038_ || this.f_75033_ == null) {
         this.f_75033_ = new PerlinNoise(new WorldgenRandom(p_75038_), ImmutableList.of(0));
      }

      this.f_75031_ = p_75038_;
   }
}