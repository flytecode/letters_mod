package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class ErodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState f_74829_ = Blocks.f_50287_.m_49966_();
   private static final BlockState f_74830_ = Blocks.f_50288_.m_49966_();
   private static final BlockState f_74831_ = Blocks.f_50352_.m_49966_();

   public ErodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_74834_) {
      super(p_74834_);
   }

   public void m_142263_(Random p_163934_, ChunkAccess p_163935_, Biome p_163936_, int p_163937_, int p_163938_, int p_163939_, double p_163940_, BlockState p_163941_, BlockState p_163942_, int p_163943_, int p_163944_, long p_163945_, SurfaceBuilderBaseConfiguration p_163946_) {
      double d0 = 0.0D;
      double d1 = Math.min(Math.abs(p_163940_), this.f_74711_.m_75449_((double)p_163937_ * 0.25D, (double)p_163938_ * 0.25D, false) * 15.0D);
      if (d1 > 0.0D) {
         double d2 = 0.001953125D;
         double d3 = Math.abs(this.f_74712_.m_75449_((double)p_163937_ * 0.001953125D, (double)p_163938_ * 0.001953125D, false));
         d0 = d1 * d1 * 2.5D;
         double d4 = Math.ceil(d3 * 50.0D) + 14.0D;
         if (d0 > d4) {
            d0 = d4;
         }

         d0 = d0 + 64.0D;
      }

      int i1 = p_163937_ & 15;
      int i = p_163938_ & 15;
      BlockState blockstate4 = f_74829_;
      SurfaceBuilderConfiguration surfacebuilderconfiguration = p_163936_.m_47536_().m_47824_();
      BlockState blockstate5 = surfacebuilderconfiguration.m_6744_();
      BlockState blockstate = surfacebuilderconfiguration.m_6743_();
      BlockState blockstate1 = blockstate5;
      int j = (int)(p_163940_ / 3.0D + 3.0D + p_163934_.nextDouble() * 0.25D);
      boolean flag = Math.cos(p_163940_ / 3.0D * Math.PI) > 0.0D;
      int k = -1;
      boolean flag1 = false;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l = Math.max(p_163939_, (int)d0 + 1); l >= p_163944_; --l) {
         blockpos$mutableblockpos.m_122178_(i1, l, i);
         if (p_163935_.m_8055_(blockpos$mutableblockpos).m_60795_() && l < (int)d0) {
            p_163935_.m_6978_(blockpos$mutableblockpos, p_163941_, false);
         }

         BlockState blockstate2 = p_163935_.m_8055_(blockpos$mutableblockpos);
         if (blockstate2.m_60795_()) {
            k = -1;
         } else if (blockstate2.m_60713_(p_163941_.m_60734_())) {
            if (k == -1) {
               flag1 = false;
               if (j <= 0) {
                  blockstate4 = Blocks.f_50016_.m_49966_();
                  blockstate1 = p_163941_;
               } else if (l >= p_163943_ - 4 && l <= p_163943_ + 1) {
                  blockstate4 = f_74829_;
                  blockstate1 = blockstate5;
               }

               if (l < p_163943_ && (blockstate4 == null || blockstate4.m_60795_())) {
                  blockstate4 = p_163942_;
               }

               k = j + Math.max(0, l - p_163943_);
               if (l >= p_163943_ - 1) {
                  if (l <= p_163943_ + 3 + j) {
                     p_163935_.m_6978_(blockpos$mutableblockpos, blockstate, false);
                     flag1 = true;
                  } else {
                     BlockState blockstate3;
                     if (l >= 64 && l <= 127) {
                        if (flag) {
                           blockstate3 = f_74831_;
                        } else {
                           blockstate3 = this.m_74717_(p_163937_, l, p_163938_);
                        }
                     } else {
                        blockstate3 = f_74830_;
                     }

                     p_163935_.m_6978_(blockpos$mutableblockpos, blockstate3, false);
                  }
               } else {
                  p_163935_.m_6978_(blockpos$mutableblockpos, blockstate1, false);
                  if (blockstate1.m_60713_(Blocks.f_50287_) || blockstate1.m_60713_(Blocks.f_50288_) || blockstate1.m_60713_(Blocks.f_50289_) || blockstate1.m_60713_(Blocks.f_50290_) || blockstate1.m_60713_(Blocks.f_50291_) || blockstate1.m_60713_(Blocks.f_50292_) || blockstate1.m_60713_(Blocks.f_50293_) || blockstate1.m_60713_(Blocks.f_50294_) || blockstate1.m_60713_(Blocks.f_50295_) || blockstate1.m_60713_(Blocks.f_50296_) || blockstate1.m_60713_(Blocks.f_50297_) || blockstate1.m_60713_(Blocks.f_50298_) || blockstate1.m_60713_(Blocks.f_50299_) || blockstate1.m_60713_(Blocks.f_50300_) || blockstate1.m_60713_(Blocks.f_50301_) || blockstate1.m_60713_(Blocks.f_50302_)) {
                     p_163935_.m_6978_(blockpos$mutableblockpos, f_74830_, false);
                  }
               }
            } else if (k > 0) {
               --k;
               if (flag1) {
                  p_163935_.m_6978_(blockpos$mutableblockpos, f_74830_, false);
               } else {
                  p_163935_.m_6978_(blockpos$mutableblockpos, this.m_74717_(p_163937_, l, p_163938_), false);
               }
            }
         }
      }

   }
}