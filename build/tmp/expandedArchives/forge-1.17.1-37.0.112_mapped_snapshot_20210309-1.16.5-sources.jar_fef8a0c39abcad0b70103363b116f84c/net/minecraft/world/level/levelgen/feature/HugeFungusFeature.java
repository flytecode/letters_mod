package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.material.Material;

public class HugeFungusFeature extends Feature<HugeFungusConfiguration> {
   private static final float f_159876_ = 0.06F;

   public HugeFungusFeature(Codec<HugeFungusConfiguration> p_65922_) {
      super(p_65922_);
   }

   public boolean m_142674_(FeaturePlaceContext<HugeFungusConfiguration> p_159878_) {
      WorldGenLevel worldgenlevel = p_159878_.m_159774_();
      BlockPos blockpos = p_159878_.m_159777_();
      Random random = p_159878_.m_159776_();
      ChunkGenerator chunkgenerator = p_159878_.m_159775_();
      HugeFungusConfiguration hugefungusconfiguration = p_159878_.m_159778_();
      Block block = hugefungusconfiguration.f_65897_.m_60734_();
      BlockPos blockpos1 = null;
      BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_7495_());
      if (blockstate.m_60713_(block)) {
         blockpos1 = blockpos;
      }

      if (blockpos1 == null) {
         return false;
      } else {
         int i = Mth.m_14072_(random, 4, 13);
         if (random.nextInt(12) == 0) {
            i *= 2;
         }

         if (!hugefungusconfiguration.f_65901_) {
            int j = chunkgenerator.m_6331_();
            if (blockpos1.m_123342_() + i + 1 >= j) {
               return false;
            }
         }

         boolean flag = !hugefungusconfiguration.f_65901_ && random.nextFloat() < 0.06F;
         worldgenlevel.m_7731_(blockpos, Blocks.f_50016_.m_49966_(), 4);
         this.m_65935_(worldgenlevel, random, hugefungusconfiguration, blockpos1, i, flag);
         this.m_65967_(worldgenlevel, random, hugefungusconfiguration, blockpos1, i, flag);
         return true;
      }
   }

   private static boolean m_65923_(LevelAccessor p_65924_, BlockPos p_65925_, boolean p_65926_) {
      return p_65924_.m_7433_(p_65925_, (p_65966_) -> {
         Material material = p_65966_.m_60767_();
         return p_65966_.m_60767_().m_76336_() || p_65926_ && material == Material.f_76300_;
      });
   }

   private void m_65935_(LevelAccessor p_65936_, Random p_65937_, HugeFungusConfiguration p_65938_, BlockPos p_65939_, int p_65940_, boolean p_65941_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockState blockstate = p_65938_.f_65898_;
      int i = p_65941_ ? 1 : 0;

      for(int j = -i; j <= i; ++j) {
         for(int k = -i; k <= i; ++k) {
            boolean flag = p_65941_ && Mth.m_14040_(j) == i && Mth.m_14040_(k) == i;

            for(int l = 0; l < p_65940_; ++l) {
               blockpos$mutableblockpos.m_122154_(p_65939_, j, l, k);
               if (m_65923_(p_65936_, blockpos$mutableblockpos, true)) {
                  if (p_65938_.f_65901_) {
                     if (!p_65936_.m_8055_(blockpos$mutableblockpos.m_7495_()).m_60795_()) {
                        p_65936_.m_46961_(blockpos$mutableblockpos, true);
                     }

                     p_65936_.m_7731_(blockpos$mutableblockpos, blockstate, 3);
                  } else if (flag) {
                     if (p_65937_.nextFloat() < 0.1F) {
                        this.m_5974_(p_65936_, blockpos$mutableblockpos, blockstate);
                     }
                  } else {
                     this.m_5974_(p_65936_, blockpos$mutableblockpos, blockstate);
                  }
               }
            }
         }
      }

   }

   private void m_65967_(LevelAccessor p_65968_, Random p_65969_, HugeFungusConfiguration p_65970_, BlockPos p_65971_, int p_65972_, boolean p_65973_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      boolean flag = p_65970_.f_65899_.m_60713_(Blocks.f_50451_);
      int i = Math.min(p_65969_.nextInt(1 + p_65972_ / 3) + 5, p_65972_);
      int j = p_65972_ - i;

      for(int k = j; k <= p_65972_; ++k) {
         int l = k < p_65972_ - p_65969_.nextInt(3) ? 2 : 1;
         if (i > 8 && k < j + 4) {
            l = 3;
         }

         if (p_65973_) {
            ++l;
         }

         for(int i1 = -l; i1 <= l; ++i1) {
            for(int j1 = -l; j1 <= l; ++j1) {
               boolean flag1 = i1 == -l || i1 == l;
               boolean flag2 = j1 == -l || j1 == l;
               boolean flag3 = !flag1 && !flag2 && k != p_65972_;
               boolean flag4 = flag1 && flag2;
               boolean flag5 = k < j + 3;
               blockpos$mutableblockpos.m_122154_(p_65971_, i1, k, j1);
               if (m_65923_(p_65968_, blockpos$mutableblockpos, false)) {
                  if (p_65970_.f_65901_ && !p_65968_.m_8055_(blockpos$mutableblockpos.m_7495_()).m_60795_()) {
                     p_65968_.m_46961_(blockpos$mutableblockpos, true);
                  }

                  if (flag5) {
                     if (!flag3) {
                        this.m_65942_(p_65968_, p_65969_, blockpos$mutableblockpos, p_65970_.f_65899_, flag);
                     }
                  } else if (flag3) {
                     this.m_65927_(p_65968_, p_65969_, p_65970_, blockpos$mutableblockpos, 0.1F, 0.2F, flag ? 0.1F : 0.0F);
                  } else if (flag4) {
                     this.m_65927_(p_65968_, p_65969_, p_65970_, blockpos$mutableblockpos, 0.01F, 0.7F, flag ? 0.083F : 0.0F);
                  } else {
                     this.m_65927_(p_65968_, p_65969_, p_65970_, blockpos$mutableblockpos, 5.0E-4F, 0.98F, flag ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }

   }

   private void m_65927_(LevelAccessor p_65928_, Random p_65929_, HugeFungusConfiguration p_65930_, BlockPos.MutableBlockPos p_65931_, float p_65932_, float p_65933_, float p_65934_) {
      if (p_65929_.nextFloat() < p_65932_) {
         this.m_5974_(p_65928_, p_65931_, p_65930_.f_65900_);
      } else if (p_65929_.nextFloat() < p_65933_) {
         this.m_5974_(p_65928_, p_65931_, p_65930_.f_65899_);
         if (p_65929_.nextFloat() < p_65934_) {
            m_65960_(p_65931_, p_65928_, p_65929_);
         }
      }

   }

   private void m_65942_(LevelAccessor p_65943_, Random p_65944_, BlockPos p_65945_, BlockState p_65946_, boolean p_65947_) {
      if (p_65943_.m_8055_(p_65945_.m_7495_()).m_60713_(p_65946_.m_60734_())) {
         this.m_5974_(p_65943_, p_65945_, p_65946_);
      } else if ((double)p_65944_.nextFloat() < 0.15D) {
         this.m_5974_(p_65943_, p_65945_, p_65946_);
         if (p_65947_ && p_65944_.nextInt(11) == 0) {
            m_65960_(p_65945_, p_65943_, p_65944_);
         }
      }

   }

   private static void m_65960_(BlockPos p_65961_, LevelAccessor p_65962_, Random p_65963_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_65961_.m_122032_().m_122173_(Direction.DOWN);
      if (p_65962_.m_46859_(blockpos$mutableblockpos)) {
         int i = Mth.m_14072_(p_65963_, 1, 5);
         if (p_65963_.nextInt(7) == 0) {
            i *= 2;
         }

         int j = 23;
         int k = 25;
         WeepingVinesFeature.m_67376_(p_65962_, p_65963_, blockpos$mutableblockpos, i, 23, 25);
      }
   }
}