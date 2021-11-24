package net.minecraft.world.level.levelgen.feature;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class DripstoneUtils {
   protected static double m_159623_(double p_159624_, double p_159625_, double p_159626_, double p_159627_) {
      if (p_159624_ < p_159627_) {
         p_159624_ = p_159627_;
      }

      double d0 = 0.384D;
      double d1 = p_159624_ / p_159625_ * 0.384D;
      double d2 = 0.75D * Math.pow(d1, 1.3333333333333333D);
      double d3 = Math.pow(d1, 0.6666666666666666D);
      double d4 = 0.3333333333333333D * Math.log(d1);
      double d5 = p_159626_ * (d2 - d3 - d4);
      d5 = Math.max(d5, 0.0D);
      return d5 / 0.384D * p_159625_;
   }

   protected static boolean m_159639_(WorldGenLevel p_159640_, BlockPos p_159641_, int p_159642_) {
      if (m_159659_(p_159640_, p_159641_)) {
         return false;
      } else {
         float f = 6.0F;
         float f1 = 6.0F / (float)p_159642_;

         for(float f2 = 0.0F; f2 < ((float)Math.PI * 2F); f2 += f1) {
            int i = (int)(Mth.m_14089_(f2) * (float)p_159642_);
            int j = (int)(Mth.m_14031_(f2) * (float)p_159642_);
            if (m_159659_(p_159640_, p_159641_.m_142082_(i, 0, j))) {
               return false;
            }
         }

         return true;
      }
   }

   protected static boolean m_159628_(LevelAccessor p_159629_, BlockPos p_159630_) {
      return p_159629_.m_7433_(p_159630_, DripstoneUtils::m_159664_);
   }

   protected static boolean m_159659_(LevelAccessor p_159660_, BlockPos p_159661_) {
      return p_159660_.m_7433_(p_159661_, DripstoneUtils::m_159666_);
   }

   protected static void m_159651_(Direction p_159652_, int p_159653_, boolean p_159654_, Consumer<BlockState> p_159655_) {
      if (p_159653_ >= 3) {
         p_159655_.accept(m_159656_(p_159652_, DripstoneThickness.BASE));

         for(int i = 0; i < p_159653_ - 3; ++i) {
            p_159655_.accept(m_159656_(p_159652_, DripstoneThickness.MIDDLE));
         }
      }

      if (p_159653_ >= 2) {
         p_159655_.accept(m_159656_(p_159652_, DripstoneThickness.FRUSTUM));
      }

      if (p_159653_ >= 1) {
         p_159655_.accept(m_159656_(p_159652_, p_159654_ ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
      }

   }

   protected static void m_159643_(WorldGenLevel p_159644_, BlockPos p_159645_, Direction p_159646_, int p_159647_, boolean p_159648_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159645_.m_122032_();
      m_159651_(p_159646_, p_159647_, p_159648_, (p_159635_) -> {
         if (p_159635_.m_60713_(Blocks.f_152588_)) {
            p_159635_ = p_159635_.m_61124_(PointedDripstoneBlock.f_154011_, Boolean.valueOf(p_159644_.m_46801_(blockpos$mutableblockpos)));
         }

         p_159644_.m_7731_(blockpos$mutableblockpos, p_159635_, 2);
         blockpos$mutableblockpos.m_122173_(p_159646_);
      });
   }

   protected static boolean m_159636_(WorldGenLevel p_159637_, BlockPos p_159638_) {
      BlockState blockstate = p_159637_.m_8055_(p_159638_);
      if (blockstate.m_60620_(BlockTags.f_144273_)) {
         p_159637_.m_7731_(p_159638_, Blocks.f_152537_.m_49966_(), 2);
         return true;
      } else {
         return false;
      }
   }

   private static BlockState m_159656_(Direction p_159657_, DripstoneThickness p_159658_) {
      return Blocks.f_152588_.m_49966_().m_61124_(PointedDripstoneBlock.f_154009_, p_159657_).m_61124_(PointedDripstoneBlock.f_154010_, p_159658_);
   }

   public static boolean m_159649_(BlockState p_159650_) {
      return m_159662_(p_159650_) || p_159650_.m_60713_(Blocks.f_49991_);
   }

   public static boolean m_159662_(BlockState p_159663_) {
      return p_159663_.m_60713_(Blocks.f_152537_) || p_159663_.m_60620_(BlockTags.f_144273_);
   }

   public static boolean m_159664_(BlockState p_159665_) {
      return p_159665_.m_60795_() || p_159665_.m_60713_(Blocks.f_49990_);
   }

   public static boolean m_159666_(BlockState p_159667_) {
      return p_159667_.m_60795_() || p_159667_.m_60713_(Blocks.f_49990_) || p_159667_.m_60713_(Blocks.f_49991_);
   }
}