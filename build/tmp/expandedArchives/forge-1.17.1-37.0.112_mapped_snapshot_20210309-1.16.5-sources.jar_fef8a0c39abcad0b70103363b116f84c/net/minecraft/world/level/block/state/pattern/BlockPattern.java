package net.minecraft.world.level.block.state.pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;

public class BlockPattern {
   private final Predicate<BlockInWorld>[][][] f_61177_;
   private final int f_61178_;
   private final int f_61179_;
   private final int f_61180_;

   public BlockPattern(Predicate<BlockInWorld>[][][] p_61182_) {
      this.f_61177_ = p_61182_;
      this.f_61178_ = p_61182_.length;
      if (this.f_61178_ > 0) {
         this.f_61179_ = p_61182_[0].length;
         if (this.f_61179_ > 0) {
            this.f_61180_ = p_61182_[0][0].length;
         } else {
            this.f_61180_ = 0;
         }
      } else {
         this.f_61179_ = 0;
         this.f_61180_ = 0;
      }

   }

   public int m_61183_() {
      return this.f_61178_;
   }

   public int m_61202_() {
      return this.f_61179_;
   }

   public int m_61203_() {
      return this.f_61180_;
   }

   @VisibleForTesting
   public Predicate<BlockInWorld>[][][] m_155969_() {
      return this.f_61177_;
   }

   @Nullable
   @VisibleForTesting
   public BlockPattern.BlockPatternMatch m_155964_(LevelReader p_155965_, BlockPos p_155966_, Direction p_155967_, Direction p_155968_) {
      LoadingCache<BlockPos, BlockInWorld> loadingcache = m_61187_(p_155965_, false);
      return this.m_61197_(p_155966_, p_155967_, p_155968_, loadingcache);
   }

   @Nullable
   private BlockPattern.BlockPatternMatch m_61197_(BlockPos p_61198_, Direction p_61199_, Direction p_61200_, LoadingCache<BlockPos, BlockInWorld> p_61201_) {
      for(int i = 0; i < this.f_61180_; ++i) {
         for(int j = 0; j < this.f_61179_; ++j) {
            for(int k = 0; k < this.f_61178_; ++k) {
               if (!this.f_61177_[k][j][i].test(p_61201_.getUnchecked(m_61190_(p_61198_, p_61199_, p_61200_, i, j, k)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.BlockPatternMatch(p_61198_, p_61199_, p_61200_, p_61201_, this.f_61180_, this.f_61179_, this.f_61178_);
   }

   @Nullable
   public BlockPattern.BlockPatternMatch m_61184_(LevelReader p_61185_, BlockPos p_61186_) {
      LoadingCache<BlockPos, BlockInWorld> loadingcache = m_61187_(p_61185_, false);
      int i = Math.max(Math.max(this.f_61180_, this.f_61179_), this.f_61178_);

      for(BlockPos blockpos : BlockPos.m_121940_(p_61186_, p_61186_.m_142082_(i - 1, i - 1, i - 1))) {
         for(Direction direction : Direction.values()) {
            for(Direction direction1 : Direction.values()) {
               if (direction1 != direction && direction1 != direction.m_122424_()) {
                  BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.m_61197_(blockpos, direction, direction1, loadingcache);
                  if (blockpattern$blockpatternmatch != null) {
                     return blockpattern$blockpatternmatch;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockInWorld> m_61187_(LevelReader p_61188_, boolean p_61189_) {
      return CacheBuilder.newBuilder().build(new BlockPattern.BlockCacheLoader(p_61188_, p_61189_));
   }

   protected static BlockPos m_61190_(BlockPos p_61191_, Direction p_61192_, Direction p_61193_, int p_61194_, int p_61195_, int p_61196_) {
      if (p_61192_ != p_61193_ && p_61192_ != p_61193_.m_122424_()) {
         Vec3i vec3i = new Vec3i(p_61192_.m_122429_(), p_61192_.m_122430_(), p_61192_.m_122431_());
         Vec3i vec3i1 = new Vec3i(p_61193_.m_122429_(), p_61193_.m_122430_(), p_61193_.m_122431_());
         Vec3i vec3i2 = vec3i.m_7724_(vec3i1);
         return p_61191_.m_142082_(vec3i1.m_123341_() * -p_61195_ + vec3i2.m_123341_() * p_61194_ + vec3i.m_123341_() * p_61196_, vec3i1.m_123342_() * -p_61195_ + vec3i2.m_123342_() * p_61194_ + vec3i.m_123342_() * p_61196_, vec3i1.m_123343_() * -p_61195_ + vec3i2.m_123343_() * p_61194_ + vec3i.m_123343_() * p_61196_);
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class BlockCacheLoader extends CacheLoader<BlockPos, BlockInWorld> {
      private final LevelReader f_61204_;
      private final boolean f_61205_;

      public BlockCacheLoader(LevelReader p_61207_, boolean p_61208_) {
         this.f_61204_ = p_61207_;
         this.f_61205_ = p_61208_;
      }

      public BlockInWorld load(BlockPos p_61210_) {
         return new BlockInWorld(this.f_61204_, p_61210_, this.f_61205_);
      }
   }

   public static class BlockPatternMatch {
      private final BlockPos f_61213_;
      private final Direction f_61214_;
      private final Direction f_61215_;
      private final LoadingCache<BlockPos, BlockInWorld> f_61216_;
      private final int f_61217_;
      private final int f_61218_;
      private final int f_61219_;

      public BlockPatternMatch(BlockPos p_61221_, Direction p_61222_, Direction p_61223_, LoadingCache<BlockPos, BlockInWorld> p_61224_, int p_61225_, int p_61226_, int p_61227_) {
         this.f_61213_ = p_61221_;
         this.f_61214_ = p_61222_;
         this.f_61215_ = p_61223_;
         this.f_61216_ = p_61224_;
         this.f_61217_ = p_61225_;
         this.f_61218_ = p_61226_;
         this.f_61219_ = p_61227_;
      }

      public BlockPos m_61228_() {
         return this.f_61213_;
      }

      public Direction m_61233_() {
         return this.f_61214_;
      }

      public Direction m_61234_() {
         return this.f_61215_;
      }

      public int m_155970_() {
         return this.f_61217_;
      }

      public int m_155971_() {
         return this.f_61218_;
      }

      public int m_155972_() {
         return this.f_61219_;
      }

      public BlockInWorld m_61229_(int p_61230_, int p_61231_, int p_61232_) {
         return this.f_61216_.getUnchecked(BlockPattern.m_61190_(this.f_61213_, this.m_61233_(), this.m_61234_(), p_61230_, p_61231_, p_61232_));
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.f_61215_).add("forwards", this.f_61214_).add("frontTopLeft", this.f_61213_).toString();
      }
   }
}