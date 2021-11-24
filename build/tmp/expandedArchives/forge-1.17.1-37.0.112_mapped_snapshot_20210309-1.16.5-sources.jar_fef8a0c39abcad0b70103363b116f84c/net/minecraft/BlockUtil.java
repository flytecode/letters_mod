package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {
   public static BlockUtil.FoundRectangle m_124334_(BlockPos p_124335_, Direction.Axis p_124336_, int p_124337_, Direction.Axis p_124338_, int p_124339_, Predicate<BlockPos> p_124340_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_124335_.m_122032_();
      Direction direction = Direction.m_122390_(Direction.AxisDirection.NEGATIVE, p_124336_);
      Direction direction1 = direction.m_122424_();
      Direction direction2 = Direction.m_122390_(Direction.AxisDirection.NEGATIVE, p_124338_);
      Direction direction3 = direction2.m_122424_();
      int i = m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_), direction, p_124337_);
      int j = m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_), direction1, p_124337_);
      int k = i;
      BlockUtil.IntBounds[] ablockutil$intbounds = new BlockUtil.IntBounds[i + 1 + j];
      ablockutil$intbounds[i] = new BlockUtil.IntBounds(m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_), direction2, p_124339_), m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_), direction3, p_124339_));
      int l = ablockutil$intbounds[i].f_124355_;

      for(int i1 = 1; i1 <= i; ++i1) {
         BlockUtil.IntBounds blockutil$intbounds = ablockutil$intbounds[k - (i1 - 1)];
         ablockutil$intbounds[k - i1] = new BlockUtil.IntBounds(m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_).m_122175_(direction, i1), direction2, blockutil$intbounds.f_124355_), m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_).m_122175_(direction, i1), direction3, blockutil$intbounds.f_124356_));
      }

      for(int l2 = 1; l2 <= j; ++l2) {
         BlockUtil.IntBounds blockutil$intbounds2 = ablockutil$intbounds[k + l2 - 1];
         ablockutil$intbounds[k + l2] = new BlockUtil.IntBounds(m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_).m_122175_(direction1, l2), direction2, blockutil$intbounds2.f_124355_), m_124341_(p_124340_, blockpos$mutableblockpos.m_122190_(p_124335_).m_122175_(direction1, l2), direction3, blockutil$intbounds2.f_124356_));
      }

      int i3 = 0;
      int j3 = 0;
      int j1 = 0;
      int k1 = 0;
      int[] aint = new int[ablockutil$intbounds.length];

      for(int l1 = l; l1 >= 0; --l1) {
         for(int i2 = 0; i2 < ablockutil$intbounds.length; ++i2) {
            BlockUtil.IntBounds blockutil$intbounds1 = ablockutil$intbounds[i2];
            int j2 = l - blockutil$intbounds1.f_124355_;
            int k2 = l + blockutil$intbounds1.f_124356_;
            aint[i2] = l1 >= j2 && l1 <= k2 ? k2 + 1 - l1 : 0;
         }

         Pair<BlockUtil.IntBounds, Integer> pair = m_124346_(aint);
         BlockUtil.IntBounds blockutil$intbounds3 = pair.getFirst();
         int k3 = 1 + blockutil$intbounds3.f_124356_ - blockutil$intbounds3.f_124355_;
         int l3 = pair.getSecond();
         if (k3 * l3 > j1 * k1) {
            i3 = blockutil$intbounds3.f_124355_;
            j3 = l1;
            j1 = k3;
            k1 = l3;
         }
      }

      return new BlockUtil.FoundRectangle(p_124335_.m_142629_(p_124336_, i3 - k).m_142629_(p_124338_, j3 - l), j1, k1);
   }

   private static int m_124341_(Predicate<BlockPos> p_124342_, BlockPos.MutableBlockPos p_124343_, Direction p_124344_, int p_124345_) {
      int i;
      for(i = 0; i < p_124345_ && p_124342_.test(p_124343_.m_122173_(p_124344_)); ++i) {
      }

      return i;
   }

   @VisibleForTesting
   static Pair<BlockUtil.IntBounds, Integer> m_124346_(int[] p_124347_) {
      int i = 0;
      int j = 0;
      int k = 0;
      IntStack intstack = new IntArrayList();
      intstack.push(0);

      for(int l = 1; l <= p_124347_.length; ++l) {
         int i1 = l == p_124347_.length ? 0 : p_124347_[l];

         while(!intstack.isEmpty()) {
            int j1 = p_124347_[intstack.topInt()];
            if (i1 >= j1) {
               intstack.push(l);
               break;
            }

            intstack.popInt();
            int k1 = intstack.isEmpty() ? 0 : intstack.topInt() + 1;
            if (j1 * (l - k1) > k * (j - i)) {
               j = l;
               i = k1;
               k = j1;
            }
         }

         if (intstack.isEmpty()) {
            intstack.push(l);
         }
      }

      return new Pair<>(new BlockUtil.IntBounds(i, j - 1), k);
   }

   public static Optional<BlockPos> m_177845_(BlockGetter p_177846_, BlockPos p_177847_, Block p_177848_, Direction p_177849_, Block p_177850_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_177847_.m_122032_();

      BlockState blockstate;
      do {
         blockpos$mutableblockpos.m_122173_(p_177849_);
         blockstate = p_177846_.m_8055_(blockpos$mutableblockpos);
      } while(blockstate.m_60713_(p_177848_));

      return blockstate.m_60713_(p_177850_) ? Optional.of(blockpos$mutableblockpos) : Optional.empty();
   }

   public static class FoundRectangle {
      public final BlockPos f_124348_;
      public final int f_124349_;
      public final int f_124350_;

      public FoundRectangle(BlockPos p_124352_, int p_124353_, int p_124354_) {
         this.f_124348_ = p_124352_;
         this.f_124349_ = p_124353_;
         this.f_124350_ = p_124354_;
      }
   }

   public static class IntBounds {
      public final int f_124355_;
      public final int f_124356_;

      public IntBounds(int p_124358_, int p_124359_) {
         this.f_124355_ = p_124358_;
         this.f_124356_ = p_124359_;
      }

      public String toString() {
         return "IntBounds{min=" + this.f_124355_ + ", max=" + this.f_124356_ + "}";
      }
   }
}