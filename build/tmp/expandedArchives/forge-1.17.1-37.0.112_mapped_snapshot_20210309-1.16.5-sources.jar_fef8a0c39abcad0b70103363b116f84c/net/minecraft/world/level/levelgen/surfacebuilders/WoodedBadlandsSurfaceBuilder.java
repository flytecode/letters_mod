package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class WoodedBadlandsSurfaceBuilder extends BadlandsSurfaceBuilder {
   private static final BlockState f_75289_ = Blocks.f_50287_.m_49966_();
   private static final BlockState f_75290_ = Blocks.f_50288_.m_49966_();
   private static final BlockState f_75291_ = Blocks.f_50352_.m_49966_();

   public WoodedBadlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_75294_) {
      super(p_75294_);
   }

   public void m_142263_(Random p_164261_, ChunkAccess p_164262_, Biome p_164263_, int p_164264_, int p_164265_, int p_164266_, double p_164267_, BlockState p_164268_, BlockState p_164269_, int p_164270_, int p_164271_, long p_164272_, SurfaceBuilderBaseConfiguration p_164273_) {
      int i = p_164264_ & 15;
      int j = p_164265_ & 15;
      BlockState blockstate = f_75289_;
      SurfaceBuilderConfiguration surfacebuilderconfiguration = p_164263_.m_47536_().m_47824_();
      BlockState blockstate1 = surfacebuilderconfiguration.m_6744_();
      BlockState blockstate2 = surfacebuilderconfiguration.m_6743_();
      BlockState blockstate3 = blockstate1;
      int k = (int)(p_164267_ / 3.0D + 3.0D + p_164261_.nextDouble() * 0.25D);
      boolean flag = Math.cos(p_164267_ / 3.0D * Math.PI) > 0.0D;
      int l = -1;
      boolean flag1 = false;
      int i1 = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int j1 = p_164266_; j1 >= p_164271_; --j1) {
         if (i1 < 15) {
            blockpos$mutableblockpos.m_122178_(i, j1, j);
            BlockState blockstate4 = p_164262_.m_8055_(blockpos$mutableblockpos);
            if (blockstate4.m_60795_()) {
               l = -1;
            } else if (blockstate4.m_60713_(p_164268_.m_60734_())) {
               if (l == -1) {
                  flag1 = false;
                  if (k <= 0) {
                     blockstate = Blocks.f_50016_.m_49966_();
                     blockstate3 = p_164268_;
                  } else if (j1 >= p_164270_ - 4 && j1 <= p_164270_ + 1) {
                     blockstate = f_75289_;
                     blockstate3 = blockstate1;
                  }

                  if (j1 < p_164270_ && (blockstate == null || blockstate.m_60795_())) {
                     blockstate = p_164269_;
                  }

                  l = k + Math.max(0, j1 - p_164270_);
                  if (j1 >= p_164270_ - 1) {
                     if (j1 > 86 + k * 2) {
                        if (flag) {
                           p_164262_.m_6978_(blockpos$mutableblockpos, Blocks.f_50546_.m_49966_(), false);
                        } else {
                           p_164262_.m_6978_(blockpos$mutableblockpos, Blocks.f_50440_.m_49966_(), false);
                        }
                     } else if (j1 > p_164270_ + 3 + k) {
                        BlockState blockstate5;
                        if (j1 >= 64 && j1 <= 127) {
                           if (flag) {
                              blockstate5 = f_75291_;
                           } else {
                              blockstate5 = this.m_74717_(p_164264_, j1, p_164265_);
                           }
                        } else {
                           blockstate5 = f_75290_;
                        }

                        p_164262_.m_6978_(blockpos$mutableblockpos, blockstate5, false);
                     } else {
                        p_164262_.m_6978_(blockpos$mutableblockpos, blockstate2, false);
                        flag1 = true;
                     }
                  } else {
                     p_164262_.m_6978_(blockpos$mutableblockpos, blockstate3, false);
                     if (blockstate3 == f_75289_) {
                        p_164262_.m_6978_(blockpos$mutableblockpos, f_75290_, false);
                     }
                  }
               } else if (l > 0) {
                  --l;
                  if (flag1) {
                     p_164262_.m_6978_(blockpos$mutableblockpos, f_75290_, false);
                  } else {
                     p_164262_.m_6978_(blockpos$mutableblockpos, this.m_74717_(p_164264_, j1, p_164265_), false);
                  }
               }

               ++i1;
            }
         }
      }

   }
}