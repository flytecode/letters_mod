package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class BadlandsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   protected static final int f_163819_ = 15;
   private static final BlockState f_74702_ = Blocks.f_50287_.m_49966_();
   private static final BlockState f_74703_ = Blocks.f_50288_.m_49966_();
   private static final BlockState f_74704_ = Blocks.f_50352_.m_49966_();
   private static final BlockState f_74705_ = Blocks.f_50291_.m_49966_();
   private static final BlockState f_74706_ = Blocks.f_50299_.m_49966_();
   private static final BlockState f_74707_ = Blocks.f_50301_.m_49966_();
   private static final BlockState f_74708_ = Blocks.f_50295_.m_49966_();
   protected BlockState[] f_74709_;
   protected long f_74710_;
   protected PerlinSimplexNoise f_74711_;
   protected PerlinSimplexNoise f_74712_;
   protected PerlinSimplexNoise f_74713_;

   public BadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74716_) {
      super(p_74716_);
   }

   public void m_142263_(Random p_163821_, ChunkAccess p_163822_, Biome p_163823_, int p_163824_, int p_163825_, int p_163826_, double p_163827_, BlockState p_163828_, BlockState p_163829_, int p_163830_, int p_163831_, long p_163832_, SurfaceBuilderBaseConfiguration p_163833_) {
      int i = p_163824_ & 15;
      int j = p_163825_ & 15;
      BlockState blockstate = f_74702_;
      SurfaceBuilderConfiguration surfacebuilderconfiguration = p_163823_.m_47536_().m_47824_();
      BlockState blockstate1 = surfacebuilderconfiguration.m_6744_();
      BlockState blockstate2 = surfacebuilderconfiguration.m_6743_();
      BlockState blockstate3 = blockstate1;
      int k = (int)(p_163827_ / 3.0D + 3.0D + p_163821_.nextDouble() * 0.25D);
      boolean flag = Math.cos(p_163827_ / 3.0D * Math.PI) > 0.0D;
      int l = -1;
      boolean flag1 = false;
      int i1 = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j1 = p_163826_; j1 >= p_163831_; --j1) {
         if (i1 < 15) {
            blockpos$mutableblockpos.m_122178_(i, j1, j);
            BlockState blockstate4 = p_163822_.m_8055_(blockpos$mutableblockpos);
            if (blockstate4.m_60795_()) {
               l = -1;
            } else if (blockstate4.m_60713_(p_163828_.m_60734_())) {
               if (l == -1) {
                  flag1 = false;
                  if (k <= 0) {
                     blockstate = Blocks.f_50016_.m_49966_();
                     blockstate3 = p_163828_;
                  } else if (j1 >= p_163830_ - 4 && j1 <= p_163830_ + 1) {
                     blockstate = f_74702_;
                     blockstate3 = blockstate1;
                  }

                  if (j1 < p_163830_ && (blockstate == null || blockstate.m_60795_())) {
                     blockstate = p_163829_;
                  }

                  l = k + Math.max(0, j1 - p_163830_);
                  if (j1 >= p_163830_ - 1) {
                     if (j1 > p_163830_ + 3 + k) {
                        BlockState blockstate5;
                        if (j1 >= 64 && j1 <= 127) {
                           if (flag) {
                              blockstate5 = f_74704_;
                           } else {
                              blockstate5 = this.m_74717_(p_163824_, j1, p_163825_);
                           }
                        } else {
                           blockstate5 = f_74703_;
                        }

                        p_163822_.m_6978_(blockpos$mutableblockpos, blockstate5, false);
                     } else {
                        p_163822_.m_6978_(blockpos$mutableblockpos, blockstate2, false);
                        flag1 = true;
                     }
                  } else {
                     p_163822_.m_6978_(blockpos$mutableblockpos, blockstate3, false);
                     if (blockstate3.m_60713_(Blocks.f_50287_) || blockstate3.m_60713_(Blocks.f_50288_) || blockstate3.m_60713_(Blocks.f_50289_) || blockstate3.m_60713_(Blocks.f_50290_) || blockstate3.m_60713_(Blocks.f_50291_) || blockstate3.m_60713_(Blocks.f_50292_) || blockstate3.m_60713_(Blocks.f_50293_) || blockstate3.m_60713_(Blocks.f_50294_) || blockstate3.m_60713_(Blocks.f_50295_) || blockstate3.m_60713_(Blocks.f_50296_) || blockstate3.m_60713_(Blocks.f_50297_) || blockstate3.m_60713_(Blocks.f_50298_) || blockstate3.m_60713_(Blocks.f_50299_) || blockstate3.m_60713_(Blocks.f_50300_) || blockstate3.m_60713_(Blocks.f_50301_) || blockstate3.m_60713_(Blocks.f_50302_)) {
                        p_163822_.m_6978_(blockpos$mutableblockpos, f_74703_, false);
                     }
                  }
               } else if (l > 0) {
                  --l;
                  if (flag1) {
                     p_163822_.m_6978_(blockpos$mutableblockpos, f_74703_, false);
                  } else {
                     p_163822_.m_6978_(blockpos$mutableblockpos, this.m_74717_(p_163824_, j1, p_163825_), false);
                  }
               }

               ++i1;
            }
         }
      }

   }

   public void m_6190_(long p_74722_) {
      if (this.f_74710_ != p_74722_ || this.f_74709_ == null) {
         this.m_74749_(p_74722_);
      }

      if (this.f_74710_ != p_74722_ || this.f_74711_ == null || this.f_74712_ == null) {
         WorldgenRandom worldgenrandom = new WorldgenRandom(p_74722_);
         this.f_74711_ = new PerlinSimplexNoise(worldgenrandom, IntStream.rangeClosed(-3, 0));
         this.f_74712_ = new PerlinSimplexNoise(worldgenrandom, ImmutableList.of(0));
      }

      this.f_74710_ = p_74722_;
   }

   protected void m_74749_(long p_74750_) {
      this.f_74709_ = new BlockState[64];
      Arrays.fill(this.f_74709_, f_74704_);
      WorldgenRandom worldgenrandom = new WorldgenRandom(p_74750_);
      this.f_74713_ = new PerlinSimplexNoise(worldgenrandom, ImmutableList.of(0));

      for(int l1 = 0; l1 < 64; ++l1) {
         l1 += worldgenrandom.nextInt(5) + 1;
         if (l1 < 64) {
            this.f_74709_[l1] = f_74703_;
         }
      }

      int i2 = worldgenrandom.nextInt(4) + 2;

      for(int i = 0; i < i2; ++i) {
         int j = worldgenrandom.nextInt(3) + 1;
         int k = worldgenrandom.nextInt(64);

         for(int l = 0; k + l < 64 && l < j; ++l) {
            this.f_74709_[k + l] = f_74705_;
         }
      }

      int j2 = worldgenrandom.nextInt(4) + 2;

      for(int k2 = 0; k2 < j2; ++k2) {
         int i3 = worldgenrandom.nextInt(3) + 2;
         int l3 = worldgenrandom.nextInt(64);

         for(int i1 = 0; l3 + i1 < 64 && i1 < i3; ++i1) {
            this.f_74709_[l3 + i1] = f_74706_;
         }
      }

      int l2 = worldgenrandom.nextInt(4) + 2;

      for(int j3 = 0; j3 < l2; ++j3) {
         int i4 = worldgenrandom.nextInt(3) + 1;
         int k4 = worldgenrandom.nextInt(64);

         for(int j1 = 0; k4 + j1 < 64 && j1 < i4; ++j1) {
            this.f_74709_[k4 + j1] = f_74707_;
         }
      }

      int k3 = worldgenrandom.nextInt(3) + 3;
      int j4 = 0;

      for(int l4 = 0; l4 < k3; ++l4) {
         int i5 = 1;
         j4 += worldgenrandom.nextInt(16) + 4;

         for(int k1 = 0; j4 + k1 < 64 && k1 < 1; ++k1) {
            this.f_74709_[j4 + k1] = f_74702_;
            if (j4 + k1 > 1 && worldgenrandom.nextBoolean()) {
               this.f_74709_[j4 + k1 - 1] = f_74708_;
            }

            if (j4 + k1 < 63 && worldgenrandom.nextBoolean()) {
               this.f_74709_[j4 + k1 + 1] = f_74708_;
            }
         }
      }

   }

   protected BlockState m_74717_(int p_74718_, int p_74719_, int p_74720_) {
      int i = (int)Math.round(this.f_74713_.m_75449_((double)p_74718_ / 512.0D, (double)p_74720_ / 512.0D, false) * 2.0D);
      return this.f_74709_[(p_74719_ + i + 64) % 64];
   }
}