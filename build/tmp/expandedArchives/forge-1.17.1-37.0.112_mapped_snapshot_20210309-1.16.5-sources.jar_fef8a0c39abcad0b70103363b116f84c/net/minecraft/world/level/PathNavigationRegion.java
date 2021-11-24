package net.minecraft.world.level;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathNavigationRegion implements BlockGetter, CollisionGetter {
   protected final int f_47158_;
   protected final int f_47159_;
   protected final ChunkAccess[][] f_47160_;
   protected boolean f_47161_;
   protected final Level f_47162_;

   public PathNavigationRegion(Level p_47164_, BlockPos p_47165_, BlockPos p_47166_) {
      this.f_47162_ = p_47164_;
      this.f_47158_ = SectionPos.m_123171_(p_47165_.m_123341_());
      this.f_47159_ = SectionPos.m_123171_(p_47165_.m_123343_());
      int i = SectionPos.m_123171_(p_47166_.m_123341_());
      int j = SectionPos.m_123171_(p_47166_.m_123343_());
      this.f_47160_ = new ChunkAccess[i - this.f_47158_ + 1][j - this.f_47159_ + 1];
      ChunkSource chunksource = p_47164_.m_7726_();
      this.f_47161_ = true;

      for(int k = this.f_47158_; k <= i; ++k) {
         for(int l = this.f_47159_; l <= j; ++l) {
            this.f_47160_[k - this.f_47158_][l - this.f_47159_] = chunksource.m_7131_(k, l);
         }
      }

      for(int i1 = SectionPos.m_123171_(p_47165_.m_123341_()); i1 <= SectionPos.m_123171_(p_47166_.m_123341_()); ++i1) {
         for(int j1 = SectionPos.m_123171_(p_47165_.m_123343_()); j1 <= SectionPos.m_123171_(p_47166_.m_123343_()); ++j1) {
            ChunkAccess chunkaccess = this.f_47160_[i1 - this.f_47158_][j1 - this.f_47159_];
            if (chunkaccess != null && !chunkaccess.m_5566_(p_47165_.m_123342_(), p_47166_.m_123342_())) {
               this.f_47161_ = false;
               return;
            }
         }
      }

   }

   private ChunkAccess m_47185_(BlockPos p_47186_) {
      return this.m_47167_(SectionPos.m_123171_(p_47186_.m_123341_()), SectionPos.m_123171_(p_47186_.m_123343_()));
   }

   private ChunkAccess m_47167_(int p_47168_, int p_47169_) {
      int i = p_47168_ - this.f_47158_;
      int j = p_47169_ - this.f_47159_;
      if (i >= 0 && i < this.f_47160_.length && j >= 0 && j < this.f_47160_[i].length) {
         ChunkAccess chunkaccess = this.f_47160_[i][j];
         return (ChunkAccess)(chunkaccess != null ? chunkaccess : new EmptyLevelChunk(this.f_47162_, new ChunkPos(p_47168_, p_47169_)));
      } else {
         return new EmptyLevelChunk(this.f_47162_, new ChunkPos(p_47168_, p_47169_));
      }
   }

   public WorldBorder m_6857_() {
      return this.f_47162_.m_6857_();
   }

   public BlockGetter m_7925_(int p_47173_, int p_47174_) {
      return this.m_47167_(p_47173_, p_47174_);
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_47180_) {
      ChunkAccess chunkaccess = this.m_47185_(p_47180_);
      return chunkaccess.m_7702_(p_47180_);
   }

   public BlockState m_8055_(BlockPos p_47188_) {
      if (this.m_151570_(p_47188_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         ChunkAccess chunkaccess = this.m_47185_(p_47188_);
         return chunkaccess.m_8055_(p_47188_);
      }
   }

   public Stream<VoxelShape> m_5454_(@Nullable Entity p_47176_, AABB p_47177_, Predicate<Entity> p_47178_) {
      return Stream.empty();
   }

   public Stream<VoxelShape> m_7786_(@Nullable Entity p_47182_, AABB p_47183_, Predicate<Entity> p_47184_) {
      return this.m_45761_(p_47182_, p_47183_);
   }

   public FluidState m_6425_(BlockPos p_47171_) {
      if (this.m_151570_(p_47171_)) {
         return Fluids.f_76191_.m_76145_();
      } else {
         ChunkAccess chunkaccess = this.m_47185_(p_47171_);
         return chunkaccess.m_6425_(p_47171_);
      }
   }

   public int m_141937_() {
      return this.f_47162_.m_141937_();
   }

   public int m_141928_() {
      return this.f_47162_.m_141928_();
   }

   public ProfilerFiller m_151625_() {
      return this.f_47162_.m_46473_();
   }
}