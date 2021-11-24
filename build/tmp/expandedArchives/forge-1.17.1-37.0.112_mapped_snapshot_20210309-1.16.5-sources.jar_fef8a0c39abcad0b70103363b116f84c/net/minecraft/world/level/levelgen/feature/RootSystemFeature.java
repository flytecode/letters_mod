package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;

public class RootSystemFeature extends Feature<RootSystemConfiguration> {
   public RootSystemFeature(Codec<RootSystemConfiguration> p_160218_) {
      super(p_160218_);
   }

   public boolean m_142674_(FeaturePlaceContext<RootSystemConfiguration> p_160257_) {
      WorldGenLevel worldgenlevel = p_160257_.m_159774_();
      BlockPos blockpos = p_160257_.m_159777_();
      if (!worldgenlevel.m_8055_(blockpos).m_60795_()) {
         return false;
      } else {
         Random random = p_160257_.m_159776_();
         BlockPos blockpos1 = p_160257_.m_159777_();
         RootSystemConfiguration rootsystemconfiguration = p_160257_.m_159778_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos1.m_122032_();
         if (this.m_160222_(worldgenlevel, p_160257_.m_159775_(), rootsystemconfiguration, random, blockpos$mutableblockpos, blockpos1)) {
            this.m_160246_(worldgenlevel, rootsystemconfiguration, random, blockpos1, blockpos$mutableblockpos);
         }

         return true;
      }
   }

   private boolean m_160235_(WorldGenLevel p_160236_, RootSystemConfiguration p_160237_, BlockPos p_160238_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_160238_.m_122032_();

      for(int i = 1; i <= p_160237_.f_161103_; ++i) {
         blockpos$mutableblockpos.m_122173_(Direction.UP);
         BlockState blockstate = p_160236_.m_8055_(blockpos$mutableblockpos);
         if (!m_160252_(blockstate, i, p_160237_.f_161113_)) {
            return false;
         }
      }

      return true;
   }

   private static boolean m_160252_(BlockState p_160253_, int p_160254_, int p_160255_) {
      return p_160253_.m_60795_() || p_160254_ <= p_160255_ && p_160253_.m_60819_().m_76153_(FluidTags.f_13131_);
   }

   private boolean m_160222_(WorldGenLevel p_160223_, ChunkGenerator p_160224_, RootSystemConfiguration p_160225_, Random p_160226_, BlockPos.MutableBlockPos p_160227_, BlockPos p_160228_) {
      int i = p_160228_.m_123341_();
      int j = p_160228_.m_123343_();

      for(int k = 0; k < p_160225_.f_161108_; ++k) {
         p_160227_.m_122173_(Direction.UP);
         if (TreeFeature.m_67272_(p_160223_, p_160227_)) {
            if (this.m_160235_(p_160223_, p_160225_, p_160227_)) {
               BlockPos blockpos = p_160227_.m_7495_();
               if (p_160223_.m_6425_(blockpos).m_76153_(FluidTags.f_13132_) || !p_160223_.m_8055_(blockpos).m_60767_().m_76333_()) {
                  return false;
               }

               if (this.m_160229_(p_160223_, p_160224_, p_160225_, p_160226_, p_160227_)) {
                  return true;
               }
            }
         } else {
            this.m_160239_(p_160223_, p_160225_, p_160226_, i, j, p_160227_);
         }
      }

      return false;
   }

   private boolean m_160229_(WorldGenLevel p_160230_, ChunkGenerator p_160231_, RootSystemConfiguration p_160232_, Random p_160233_, BlockPos p_160234_) {
      return p_160232_.f_161102_.get().m_65385_(p_160230_, p_160231_, p_160233_, p_160234_);
   }

   private void m_160239_(WorldGenLevel p_160240_, RootSystemConfiguration p_160241_, Random p_160242_, int p_160243_, int p_160244_, BlockPos.MutableBlockPos p_160245_) {
      int i = p_160241_.f_161104_;
      Tag<Block> tag = BlockTags.m_13115_().m_13404_(p_160241_.f_161105_);
      Predicate<BlockState> predicate = tag == null ? (p_160259_) -> {
         return true;
      } : (p_160221_) -> {
         return p_160221_.m_60620_(tag);
      };

      for(int j = 0; j < p_160241_.f_161107_; ++j) {
         p_160245_.m_122154_(p_160245_, p_160242_.nextInt(i) - p_160242_.nextInt(i), 0, p_160242_.nextInt(i) - p_160242_.nextInt(i));
         if (predicate.test(p_160240_.m_8055_(p_160245_))) {
            p_160240_.m_7731_(p_160245_, p_160241_.f_161106_.m_7112_(p_160242_, p_160245_), 2);
         }

         p_160245_.m_142451_(p_160243_);
         p_160245_.m_142443_(p_160244_);
      }

   }

   private void m_160246_(WorldGenLevel p_160247_, RootSystemConfiguration p_160248_, Random p_160249_, BlockPos p_160250_, BlockPos.MutableBlockPos p_160251_) {
      int i = p_160248_.f_161109_;
      int j = p_160248_.f_161110_;

      for(int k = 0; k < p_160248_.f_161112_; ++k) {
         p_160251_.m_122154_(p_160250_, p_160249_.nextInt(i) - p_160249_.nextInt(i), p_160249_.nextInt(j) - p_160249_.nextInt(j), p_160249_.nextInt(i) - p_160249_.nextInt(i));
         if (p_160247_.m_46859_(p_160251_)) {
            BlockState blockstate = p_160248_.f_161111_.m_7112_(p_160249_, p_160251_);
            if (blockstate.m_60710_(p_160247_, p_160251_) && p_160247_.m_8055_(p_160251_.m_7494_()).m_60783_(p_160247_, p_160251_, Direction.DOWN)) {
               p_160247_.m_7731_(p_160251_, blockstate, 2);
            }
         }
      }

   }
}