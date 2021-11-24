package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public interface Aquifer {
   int f_157954_ = 9;
   int f_157955_ = 30;

   static Aquifer m_157959_(ChunkPos p_157960_, NormalNoise p_157961_, NormalNoise p_157962_, NormalNoise p_157963_, NoiseGeneratorSettings p_157964_, NoiseSampler p_157965_, int p_157966_, int p_157967_) {
      return new Aquifer.NoiseBasedAquifer(p_157960_, p_157961_, p_157962_, p_157963_, p_157964_, p_157965_, p_157966_, p_157967_);
   }

   static Aquifer m_157956_(final int p_157957_, final BlockState p_157958_) {
      return new Aquifer() {
         public BlockState m_142419_(BaseStoneSource p_157980_, int p_157981_, int p_157982_, int p_157983_, double p_157984_) {
            if (p_157984_ > 0.0D) {
               return p_157980_.m_142722_(p_157981_, p_157982_, p_157983_);
            } else {
               return p_157982_ >= p_157957_ ? Blocks.f_50016_.m_49966_() : p_157958_;
            }
         }

         public boolean m_142203_() {
            return false;
         }
      };
   }

   BlockState m_142419_(BaseStoneSource p_157968_, int p_157969_, int p_157970_, int p_157971_, double p_157972_);

   boolean m_142203_();

   public static class NoiseBasedAquifer implements Aquifer {
      private static final int f_157985_ = 10;
      private static final int f_157986_ = 9;
      private static final int f_157987_ = 10;
      private static final int f_157988_ = 6;
      private static final int f_157989_ = 3;
      private static final int f_157990_ = 6;
      private static final int f_157991_ = 16;
      private static final int f_157992_ = 12;
      private static final int f_157993_ = 16;
      protected final NormalNoise f_157994_;
      protected final NormalNoise f_157995_;
      protected final NormalNoise f_157996_;
      protected final NoiseGeneratorSettings f_157997_;
      protected final Aquifer.NoiseBasedAquifer.AquiferStatus[] f_157998_;
      protected final long[] f_157999_;
      protected boolean f_158000_;
      protected final NoiseSampler f_158001_;
      protected final int f_158002_;
      protected final int f_158003_;
      protected final int f_158004_;
      protected final int f_158005_;
      protected final int f_158006_;

      protected NoiseBasedAquifer(ChunkPos p_158008_, NormalNoise p_158009_, NormalNoise p_158010_, NormalNoise p_158011_, NoiseGeneratorSettings p_158012_, NoiseSampler p_158013_, int p_158014_, int p_158015_) {
         this.f_157994_ = p_158009_;
         this.f_157995_ = p_158010_;
         this.f_157996_ = p_158011_;
         this.f_157997_ = p_158012_;
         this.f_158001_ = p_158013_;
         this.f_158002_ = this.m_158039_(p_158008_.m_45604_()) - 1;
         int i = this.m_158039_(p_158008_.m_45608_()) + 1;
         this.f_158005_ = i - this.f_158002_ + 1;
         this.f_158003_ = this.m_158045_(p_158014_) - 1;
         int j = this.m_158045_(p_158014_ + p_158015_) + 1;
         int k = j - this.f_158003_ + 1;
         this.f_158004_ = this.m_158047_(p_158008_.m_45605_()) - 1;
         int l = this.m_158047_(p_158008_.m_45609_()) + 1;
         this.f_158006_ = l - this.f_158004_ + 1;
         int i1 = this.f_158005_ * k * this.f_158006_;
         this.f_157998_ = new Aquifer.NoiseBasedAquifer.AquiferStatus[i1];
         this.f_157999_ = new long[i1];
         Arrays.fill(this.f_157999_, Long.MAX_VALUE);
      }

      protected int m_158027_(int p_158028_, int p_158029_, int p_158030_) {
         int i = p_158028_ - this.f_158002_;
         int j = p_158029_ - this.f_158003_;
         int k = p_158030_ - this.f_158004_;
         return (j * this.f_158006_ + k) * this.f_158005_ + i;
      }

      public BlockState m_142419_(BaseStoneSource p_158034_, int p_158035_, int p_158036_, int p_158037_, double p_158038_) {
         if (p_158038_ <= 0.0D) {
            double d0;
            BlockState blockstate;
            boolean flag;
            if (this.m_158017_(p_158036_)) {
               blockstate = Blocks.f_49991_.m_49966_();
               d0 = 0.0D;
               flag = false;
            } else {
               int i = Math.floorDiv(p_158035_ - 5, 16);
               int j = Math.floorDiv(p_158036_ + 1, 12);
               int k = Math.floorDiv(p_158037_ - 5, 16);
               int l = Integer.MAX_VALUE;
               int i1 = Integer.MAX_VALUE;
               int j1 = Integer.MAX_VALUE;
               long k1 = 0L;
               long l1 = 0L;
               long i2 = 0L;

               for(int j2 = 0; j2 <= 1; ++j2) {
                  for(int k2 = -1; k2 <= 1; ++k2) {
                     for(int l2 = 0; l2 <= 1; ++l2) {
                        int i3 = i + j2;
                        int j3 = j + k2;
                        int k3 = k + l2;
                        int l3 = this.m_158027_(i3, j3, k3);
                        long j4 = this.f_157999_[l3];
                        long i4;
                        if (j4 != Long.MAX_VALUE) {
                           i4 = j4;
                        } else {
                           WorldgenRandom worldgenrandom = new WorldgenRandom(Mth.m_14130_(i3, j3 * 3, k3) + 1L);
                           i4 = BlockPos.m_121882_(i3 * 16 + worldgenrandom.nextInt(10), j3 * 12 + worldgenrandom.nextInt(9), k3 * 16 + worldgenrandom.nextInt(10));
                           this.f_157999_[l3] = i4;
                        }

                        int j5 = BlockPos.m_121983_(i4) - p_158035_;
                        int k4 = BlockPos.m_122008_(i4) - p_158036_;
                        int l4 = BlockPos.m_122015_(i4) - p_158037_;
                        int i5 = j5 * j5 + k4 * k4 + l4 * l4;
                        if (l >= i5) {
                           i2 = l1;
                           l1 = k1;
                           k1 = i4;
                           j1 = i1;
                           i1 = l;
                           l = i5;
                        } else if (i1 >= i5) {
                           i2 = l1;
                           l1 = i4;
                           j1 = i1;
                           i1 = i5;
                        } else if (j1 >= i5) {
                           i2 = i4;
                           j1 = i5;
                        }
                     }
                  }
               }

               Aquifer.NoiseBasedAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus = this.m_158031_(k1);
               Aquifer.NoiseBasedAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus1 = this.m_158031_(l1);
               Aquifer.NoiseBasedAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus2 = this.m_158031_(i2);
               double d6 = this.m_158024_(l, i1);
               double d7 = this.m_158024_(l, j1);
               double d8 = this.m_158024_(i1, j1);
               flag = d6 > 0.0D;
               if (aquifer$noisebasedaquifer$aquiferstatus.f_158049_ >= p_158036_ && aquifer$noisebasedaquifer$aquiferstatus.f_158050_.m_60713_(Blocks.f_49990_) && this.m_158017_(p_158036_ - 1)) {
                  d0 = 1.0D;
               } else if (d6 > -1.0D) {
                  double d9 = 1.0D + (this.f_157994_.m_75380_((double)p_158035_, (double)p_158036_, (double)p_158037_) + 0.05D) / 4.0D;
                  double d10 = this.m_158019_(p_158036_, d9, aquifer$noisebasedaquifer$aquiferstatus, aquifer$noisebasedaquifer$aquiferstatus1);
                  double d11 = this.m_158019_(p_158036_, d9, aquifer$noisebasedaquifer$aquiferstatus, aquifer$noisebasedaquifer$aquiferstatus2);
                  double d1 = this.m_158019_(p_158036_, d9, aquifer$noisebasedaquifer$aquiferstatus1, aquifer$noisebasedaquifer$aquiferstatus2);
                  double d2 = Math.max(0.0D, d6);
                  double d3 = Math.max(0.0D, d7);
                  double d4 = Math.max(0.0D, d8);
                  double d5 = 2.0D * d2 * Math.max(d10, Math.max(d11 * d3, d1 * d4));
                  d0 = Math.max(0.0D, d5);
               } else {
                  d0 = 0.0D;
               }

               blockstate = p_158036_ >= aquifer$noisebasedaquifer$aquiferstatus.f_158049_ ? Blocks.f_50016_.m_49966_() : aquifer$noisebasedaquifer$aquiferstatus.f_158050_;
            }

            if (p_158038_ + d0 <= 0.0D) {
               this.f_158000_ = flag;
               return blockstate;
            }
         }

         this.f_158000_ = false;
         return p_158034_.m_142722_(p_158035_, p_158036_, p_158037_);
      }

      public boolean m_142203_() {
         return this.f_158000_;
      }

      protected boolean m_158017_(int p_158018_) {
         return p_158018_ - this.f_157997_.m_64481_().m_158703_() <= 9;
      }

      protected double m_158024_(int p_158025_, int p_158026_) {
         double d0 = 25.0D;
         return 1.0D - (double)Math.abs(p_158026_ - p_158025_) / 25.0D;
      }

      protected double m_158019_(int p_158020_, double p_158021_, Aquifer.NoiseBasedAquifer.AquiferStatus p_158022_, Aquifer.NoiseBasedAquifer.AquiferStatus p_158023_) {
         if (p_158020_ <= p_158022_.f_158049_ && p_158020_ <= p_158023_.f_158049_ && p_158022_.f_158050_ != p_158023_.f_158050_) {
            return 1.0D;
         } else {
            int i = Math.abs(p_158022_.f_158049_ - p_158023_.f_158049_);
            double d0 = 0.5D * (double)(p_158022_.f_158049_ + p_158023_.f_158049_);
            double d1 = Math.abs(d0 - (double)p_158020_ - 0.5D);
            return 0.5D * (double)i * p_158021_ - d1;
         }
      }

      protected int m_158039_(int p_158040_) {
         return Math.floorDiv(p_158040_, 16);
      }

      protected int m_158045_(int p_158046_) {
         return Math.floorDiv(p_158046_, 12);
      }

      protected int m_158047_(int p_158048_) {
         return Math.floorDiv(p_158048_, 16);
      }

      protected Aquifer.NoiseBasedAquifer.AquiferStatus m_158031_(long p_158032_) {
         int i = BlockPos.m_121983_(p_158032_);
         int j = BlockPos.m_122008_(p_158032_);
         int k = BlockPos.m_122015_(p_158032_);
         int l = this.m_158039_(i);
         int i1 = this.m_158045_(j);
         int j1 = this.m_158047_(k);
         int k1 = this.m_158027_(l, i1, j1);
         Aquifer.NoiseBasedAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus = this.f_157998_[k1];
         if (aquifer$noisebasedaquifer$aquiferstatus != null) {
            return aquifer$noisebasedaquifer$aquiferstatus;
         } else {
            Aquifer.NoiseBasedAquifer.AquiferStatus aquifer$noisebasedaquifer$aquiferstatus1 = this.m_158041_(i, j, k);
            this.f_157998_[k1] = aquifer$noisebasedaquifer$aquiferstatus1;
            return aquifer$noisebasedaquifer$aquiferstatus1;
         }
      }

      protected Aquifer.NoiseBasedAquifer.AquiferStatus m_158041_(int p_158042_, int p_158043_, int p_158044_) {
         int i = this.f_157997_.m_64486_();
         if (p_158043_ > 30) {
            return new Aquifer.NoiseBasedAquifer.AquiferStatus(i, Blocks.f_49990_.m_49966_());
         } else {
            int j = 64;
            int k = -10;
            int l = 40;
            double d0 = this.f_157995_.m_75380_((double)Math.floorDiv(p_158042_, 64), (double)Math.floorDiv(p_158043_, 40) / 1.4D, (double)Math.floorDiv(p_158044_, 64)) * 30.0D + -10.0D;
            boolean flag = false;
            if (Math.abs(d0) > 8.0D) {
               d0 *= 4.0D;
            }

            int i1 = Math.floorDiv(p_158043_, 40) * 40 + 20;
            int j1 = i1 + Mth.m_14107_(d0);
            if (i1 == -20) {
               double d1 = this.f_157996_.m_75380_((double)Math.floorDiv(p_158042_, 64), (double)Math.floorDiv(p_158043_, 40) / 1.4D, (double)Math.floorDiv(p_158044_, 64));
               flag = Math.abs(d1) > (double)0.22F;
            }

            return new Aquifer.NoiseBasedAquifer.AquiferStatus(Math.min(56, j1), flag ? Blocks.f_49991_.m_49966_() : Blocks.f_49990_.m_49966_());
         }
      }

      public static final class AquiferStatus {
         public final int f_158049_;
         public final BlockState f_158050_;

         public AquiferStatus(int p_158052_, BlockState p_158053_) {
            this.f_158049_ = p_158052_;
            this.f_158050_ = p_158053_;
         }
      }
   }
}