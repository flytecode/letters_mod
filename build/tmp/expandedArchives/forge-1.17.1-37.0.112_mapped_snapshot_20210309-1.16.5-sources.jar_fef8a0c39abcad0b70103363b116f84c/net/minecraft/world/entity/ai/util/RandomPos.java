package net.minecraft.world.entity.ai.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomPos {
   private static final int f_148535_ = 10;

   public static BlockPos m_148549_(Random p_148550_, int p_148551_, int p_148552_) {
      int i = p_148550_.nextInt(2 * p_148551_ + 1) - p_148551_;
      int j = p_148550_.nextInt(2 * p_148552_ + 1) - p_148552_;
      int k = p_148550_.nextInt(2 * p_148551_ + 1) - p_148551_;
      return new BlockPos(i, j, k);
   }

   @Nullable
   public static BlockPos m_148553_(Random p_148554_, int p_148555_, int p_148556_, int p_148557_, double p_148558_, double p_148559_, double p_148560_) {
      double d0 = Mth.m_14136_(p_148559_, p_148558_) - (double)((float)Math.PI / 2F);
      double d1 = d0 + (double)(2.0F * p_148554_.nextFloat() - 1.0F) * p_148560_;
      double d2 = Math.sqrt(p_148554_.nextDouble()) * (double)Mth.f_13994_ * (double)p_148555_;
      double d3 = -d2 * Math.sin(d1);
      double d4 = d2 * Math.cos(d1);
      if (!(Math.abs(d3) > (double)p_148555_) && !(Math.abs(d4) > (double)p_148555_)) {
         int i = p_148554_.nextInt(2 * p_148556_ + 1) - p_148556_ + p_148557_;
         return new BlockPos(d3, (double)i, d4);
      } else {
         return null;
      }
   }

   @VisibleForTesting
   public static BlockPos m_148545_(BlockPos p_148546_, int p_148547_, Predicate<BlockPos> p_148548_) {
      if (!p_148548_.test(p_148546_)) {
         return p_148546_;
      } else {
         BlockPos blockpos;
         for(blockpos = p_148546_.m_7494_(); blockpos.m_123342_() < p_148547_ && p_148548_.test(blockpos); blockpos = blockpos.m_7494_()) {
         }

         return blockpos;
      }
   }

   @VisibleForTesting
   public static BlockPos m_26947_(BlockPos p_26948_, int p_26949_, int p_26950_, Predicate<BlockPos> p_26951_) {
      if (p_26949_ < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + p_26949_ + ", expected >= 0");
      } else if (!p_26951_.test(p_26948_)) {
         return p_26948_;
      } else {
         BlockPos blockpos;
         for(blockpos = p_26948_.m_7494_(); blockpos.m_123342_() < p_26950_ && p_26951_.test(blockpos); blockpos = blockpos.m_7494_()) {
         }

         BlockPos blockpos1;
         BlockPos blockpos2;
         for(blockpos1 = blockpos; blockpos1.m_123342_() < p_26950_ && blockpos1.m_123342_() - blockpos.m_123342_() < p_26949_; blockpos1 = blockpos2) {
            blockpos2 = blockpos1.m_7494_();
            if (p_26951_.test(blockpos2)) {
               break;
            }
         }

         return blockpos1;
      }
   }

   @Nullable
   public static Vec3 m_148542_(PathfinderMob p_148543_, Supplier<BlockPos> p_148544_) {
      return m_148561_(p_148544_, p_148543_::m_21692_);
   }

   @Nullable
   public static Vec3 m_148561_(Supplier<BlockPos> p_148562_, ToDoubleFunction<BlockPos> p_148563_) {
      double d0 = Double.NEGATIVE_INFINITY;
      BlockPos blockpos = null;

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = p_148562_.get();
         if (blockpos1 != null) {
            double d1 = p_148563_.applyAsDouble(blockpos1);
            if (d1 > d0) {
               d0 = d1;
               blockpos = blockpos1;
            }
         }
      }

      return blockpos != null ? Vec3.m_82539_(blockpos) : null;
   }

   public static BlockPos m_148537_(PathfinderMob p_148538_, int p_148539_, Random p_148540_, BlockPos p_148541_) {
      int i = p_148541_.m_123341_();
      int j = p_148541_.m_123343_();
      if (p_148538_.m_21536_() && p_148539_ > 1) {
         BlockPos blockpos = p_148538_.m_21534_();
         if (p_148538_.m_20185_() > (double)blockpos.m_123341_()) {
            i -= p_148540_.nextInt(p_148539_ / 2);
         } else {
            i += p_148540_.nextInt(p_148539_ / 2);
         }

         if (p_148538_.m_20189_() > (double)blockpos.m_123343_()) {
            j -= p_148540_.nextInt(p_148539_ / 2);
         } else {
            j += p_148540_.nextInt(p_148539_ / 2);
         }
      }

      return new BlockPos((double)i + p_148538_.m_20185_(), (double)p_148541_.m_123342_() + p_148538_.m_20186_(), (double)j + p_148538_.m_20189_());
   }
}