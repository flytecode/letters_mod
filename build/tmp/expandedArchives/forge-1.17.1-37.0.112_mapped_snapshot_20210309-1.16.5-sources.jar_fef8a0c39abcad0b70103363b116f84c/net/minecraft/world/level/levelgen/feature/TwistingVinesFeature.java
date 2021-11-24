package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TwistingVinesFeature extends Feature<NoneFeatureConfiguration> {
   public TwistingVinesFeature(Codec<NoneFeatureConfiguration> p_67292_) {
      super(p_67292_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160558_) {
      return m_67306_(p_160558_.m_159774_(), p_160558_.m_159776_(), p_160558_.m_159777_(), 8, 4, 8);
   }

   public static boolean m_67306_(LevelAccessor p_67307_, Random p_67308_, BlockPos p_67309_, int p_67310_, int p_67311_, int p_67312_) {
      if (m_67296_(p_67307_, p_67309_)) {
         return false;
      } else {
         m_67325_(p_67307_, p_67308_, p_67309_, p_67310_, p_67311_, p_67312_);
         return true;
      }
   }

   private static void m_67325_(LevelAccessor p_67326_, Random p_67327_, BlockPos p_67328_, int p_67329_, int p_67330_, int p_67331_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < p_67329_ * p_67329_; ++i) {
         blockpos$mutableblockpos.m_122190_(p_67328_).m_122184_(Mth.m_14072_(p_67327_, -p_67329_, p_67329_), Mth.m_14072_(p_67327_, -p_67330_, p_67330_), Mth.m_14072_(p_67327_, -p_67329_, p_67329_));
         if (m_67293_(p_67326_, blockpos$mutableblockpos) && !m_67296_(p_67326_, blockpos$mutableblockpos)) {
            int j = Mth.m_14072_(p_67327_, 1, p_67331_);
            if (p_67327_.nextInt(6) == 0) {
               j *= 2;
            }

            if (p_67327_.nextInt(5) == 0) {
               j = 1;
            }

            int k = 17;
            int l = 25;
            m_67299_(p_67326_, p_67327_, blockpos$mutableblockpos, j, 17, 25);
         }
      }

   }

   private static boolean m_67293_(LevelAccessor p_67294_, BlockPos.MutableBlockPos p_67295_) {
      do {
         p_67295_.m_122184_(0, -1, 0);
         if (p_67294_.m_151570_(p_67295_)) {
            return false;
         }
      } while(p_67294_.m_8055_(p_67295_).m_60795_());

      p_67295_.m_122184_(0, 1, 0);
      return true;
   }

   public static void m_67299_(LevelAccessor p_67300_, Random p_67301_, BlockPos.MutableBlockPos p_67302_, int p_67303_, int p_67304_, int p_67305_) {
      for(int i = 1; i <= p_67303_; ++i) {
         if (p_67300_.m_46859_(p_67302_)) {
            if (i == p_67303_ || !p_67300_.m_46859_(p_67302_.m_7494_())) {
               p_67300_.m_7731_(p_67302_, Blocks.f_50704_.m_49966_().m_61124_(GrowingPlantHeadBlock.f_53924_, Integer.valueOf(Mth.m_14072_(p_67301_, p_67304_, p_67305_))), 2);
               break;
            }

            p_67300_.m_7731_(p_67302_, Blocks.f_50653_.m_49966_(), 2);
         }

         p_67302_.m_122173_(Direction.UP);
      }

   }

   private static boolean m_67296_(LevelAccessor p_67297_, BlockPos p_67298_) {
      if (!p_67297_.m_46859_(p_67298_)) {
         return true;
      } else {
         BlockState blockstate = p_67297_.m_8055_(p_67298_.m_7495_());
         return !blockstate.m_60713_(Blocks.f_50134_) && !blockstate.m_60713_(Blocks.f_50690_) && !blockstate.m_60713_(Blocks.f_50692_);
      }
   }
}