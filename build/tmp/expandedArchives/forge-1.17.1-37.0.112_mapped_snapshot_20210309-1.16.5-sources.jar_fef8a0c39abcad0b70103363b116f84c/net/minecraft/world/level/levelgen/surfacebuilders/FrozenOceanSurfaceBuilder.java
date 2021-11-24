package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.Material;

public class FrozenOceanSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   protected static final BlockState f_74864_ = Blocks.f_50354_.m_49966_();
   protected static final BlockState f_74865_ = Blocks.f_50127_.m_49966_();
   private static final BlockState f_74866_ = Blocks.f_50016_.m_49966_();
   private static final BlockState f_74867_ = Blocks.f_49994_.m_49966_();
   private static final BlockState f_74868_ = Blocks.f_50126_.m_49966_();
   private PerlinSimplexNoise f_74861_;
   private PerlinSimplexNoise f_74862_;
   private long f_74863_;

   public FrozenOceanSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74871_) {
      super(p_74871_);
   }

   public void m_142263_(Random p_163962_, ChunkAccess p_163963_, Biome p_163964_, int p_163965_, int p_163966_, int p_163967_, double p_163968_, BlockState p_163969_, BlockState p_163970_, int p_163971_, int p_163972_, long p_163973_, SurfaceBuilderBaseConfiguration p_163974_) {
      double d0 = 0.0D;
      double d1 = 0.0D;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      float f = p_163964_.m_47505_(blockpos$mutableblockpos.m_122178_(p_163965_, 63, p_163966_));
      double d2 = Math.min(Math.abs(p_163968_), this.f_74861_.m_75449_((double)p_163965_ * 0.1D, (double)p_163966_ * 0.1D, false) * 15.0D);
      if (d2 > 1.8D) {
         double d3 = 0.09765625D;
         double d4 = Math.abs(this.f_74862_.m_75449_((double)p_163965_ * 0.09765625D, (double)p_163966_ * 0.09765625D, false));
         d0 = d2 * d2 * 1.2D;
         double d5 = Math.ceil(d4 * 40.0D) + 14.0D;
         if (d0 > d5) {
            d0 = d5;
         }

         if (f > 0.1F) {
            d0 -= 2.0D;
         }

         if (d0 > 2.0D) {
            d1 = (double)p_163971_ - d0 - 7.0D;
            d0 = d0 + (double)p_163971_;
         } else {
            d0 = 0.0D;
         }
      }

      int l1 = p_163965_ & 15;
      int i = p_163966_ & 15;
      SurfaceBuilderConfiguration surfacebuilderconfiguration = p_163964_.m_47536_().m_47824_();
      BlockState blockstate = surfacebuilderconfiguration.m_6744_();
      BlockState blockstate4 = surfacebuilderconfiguration.m_6743_();
      BlockState blockstate1 = blockstate;
      BlockState blockstate2 = blockstate4;
      int j = (int)(p_163968_ / 3.0D + 3.0D + p_163962_.nextDouble() * 0.25D);
      int k = -1;
      int l = 0;
      int i1 = 2 + p_163962_.nextInt(4);
      int j1 = p_163971_ + 18 + p_163962_.nextInt(10);

      for(int k1 = Math.max(p_163967_, (int)d0 + 1); k1 >= p_163972_; --k1) {
         blockpos$mutableblockpos.m_122178_(l1, k1, i);
         if (p_163963_.m_8055_(blockpos$mutableblockpos).m_60795_() && k1 < (int)d0 && p_163962_.nextDouble() > 0.01D) {
            p_163963_.m_6978_(blockpos$mutableblockpos, f_74864_, false);
         } else if (p_163963_.m_8055_(blockpos$mutableblockpos).m_60767_() == Material.f_76305_ && k1 > (int)d1 && k1 < p_163971_ && d1 != 0.0D && p_163962_.nextDouble() > 0.15D) {
            p_163963_.m_6978_(blockpos$mutableblockpos, f_74864_, false);
         }

         BlockState blockstate3 = p_163963_.m_8055_(blockpos$mutableblockpos);
         if (blockstate3.m_60795_()) {
            k = -1;
         } else if (!blockstate3.m_60713_(p_163969_.m_60734_())) {
            if (blockstate3.m_60713_(Blocks.f_50354_) && l <= i1 && k1 > j1) {
               p_163963_.m_6978_(blockpos$mutableblockpos, f_74865_, false);
               ++l;
            }
         } else if (k == -1) {
            if (j <= 0) {
               blockstate2 = f_74866_;
               blockstate1 = p_163969_;
            } else if (k1 >= p_163971_ - 4 && k1 <= p_163971_ + 1) {
               blockstate2 = blockstate4;
               blockstate1 = blockstate;
            }

            if (k1 < p_163971_ && (blockstate2 == null || blockstate2.m_60795_())) {
               if (p_163964_.m_47505_(blockpos$mutableblockpos.m_122178_(p_163965_, k1, p_163966_)) < 0.15F) {
                  blockstate2 = f_74868_;
               } else {
                  blockstate2 = p_163970_;
               }
            }

            k = j;
            if (k1 >= p_163971_ - 1) {
               p_163963_.m_6978_(blockpos$mutableblockpos, blockstate2, false);
            } else if (k1 < p_163971_ - 7 - j) {
               blockstate2 = f_74866_;
               blockstate1 = p_163969_;
               p_163963_.m_6978_(blockpos$mutableblockpos, f_74867_, false);
            } else {
               p_163963_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
            }
         } else if (k > 0) {
            --k;
            p_163963_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
            if (k == 0 && blockstate1.m_60713_(Blocks.f_49992_) && j > 1) {
               k = p_163962_.nextInt(4) + Math.max(0, k1 - 63);
               blockstate1 = blockstate1.m_60713_(Blocks.f_49993_) ? Blocks.f_50394_.m_49966_() : Blocks.f_50062_.m_49966_();
            }
         }
      }

   }

   public void m_6190_(long p_74873_) {
      if (this.f_74863_ != p_74873_ || this.f_74861_ == null || this.f_74862_ == null) {
         WorldgenRandom worldgenrandom = new WorldgenRandom(p_74873_);
         this.f_74861_ = new PerlinSimplexNoise(worldgenrandom, IntStream.rangeClosed(-3, 0));
         this.f_74862_ = new PerlinSimplexNoise(worldgenrandom, ImmutableList.of(0));
      }

      this.f_74863_ = p_74873_;
   }
}