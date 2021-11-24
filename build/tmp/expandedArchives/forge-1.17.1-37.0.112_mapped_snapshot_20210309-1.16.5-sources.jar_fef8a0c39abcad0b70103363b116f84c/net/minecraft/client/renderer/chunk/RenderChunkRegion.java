package net.minecraft.client.renderer.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderChunkRegion implements BlockAndTintGetter {
   protected final int f_112899_;
   protected final int f_112900_;
   protected final BlockPos f_112901_;
   protected final int f_112902_;
   protected final int f_112903_;
   protected final int f_112904_;
   protected final LevelChunk[][] f_112905_;
   protected final BlockState[] f_112906_;
   protected final Level f_112908_;

   @Nullable
   public static RenderChunkRegion m_112920_(Level p_112921_, BlockPos p_112922_, BlockPos p_112923_, int p_112924_) {
      int i = SectionPos.m_123171_(p_112922_.m_123341_() - p_112924_);
      int j = SectionPos.m_123171_(p_112922_.m_123343_() - p_112924_);
      int k = SectionPos.m_123171_(p_112923_.m_123341_() + p_112924_);
      int l = SectionPos.m_123171_(p_112923_.m_123343_() + p_112924_);
      LevelChunk[][] alevelchunk = new LevelChunk[k - i + 1][l - j + 1];

      for(int i1 = i; i1 <= k; ++i1) {
         for(int j1 = j; j1 <= l; ++j1) {
            alevelchunk[i1 - i][j1 - j] = p_112921_.m_6325_(i1, j1);
         }
      }

      if (m_112930_(p_112922_, p_112923_, i, j, alevelchunk)) {
         return null;
      } else {
         int k1 = 1;
         BlockPos blockpos1 = p_112922_.m_142082_(-1, -1, -1);
         BlockPos blockpos = p_112923_.m_142082_(1, 1, 1);
         return new RenderChunkRegion(p_112921_, i, j, alevelchunk, blockpos1, blockpos);
      }
   }

   public static boolean m_112930_(BlockPos p_112931_, BlockPos p_112932_, int p_112933_, int p_112934_, LevelChunk[][] p_112935_) {
      for(int i = SectionPos.m_123171_(p_112931_.m_123341_()); i <= SectionPos.m_123171_(p_112932_.m_123341_()); ++i) {
         for(int j = SectionPos.m_123171_(p_112931_.m_123343_()); j <= SectionPos.m_123171_(p_112932_.m_123343_()); ++j) {
            LevelChunk levelchunk = p_112935_[i - p_112933_][j - p_112934_];
            if (!levelchunk.m_5566_(p_112931_.m_123342_(), p_112932_.m_123342_())) {
               return false;
            }
         }
      }

      return true;
   }

   public RenderChunkRegion(Level p_112910_, int p_112911_, int p_112912_, LevelChunk[][] p_112913_, BlockPos p_112914_, BlockPos p_112915_) {
      this.f_112908_ = p_112910_;
      this.f_112899_ = p_112911_;
      this.f_112900_ = p_112912_;
      this.f_112905_ = p_112913_;
      this.f_112901_ = p_112914_;
      this.f_112902_ = p_112915_.m_123341_() - p_112914_.m_123341_() + 1;
      this.f_112903_ = p_112915_.m_123342_() - p_112914_.m_123342_() + 1;
      this.f_112904_ = p_112915_.m_123343_() - p_112914_.m_123343_() + 1;
      this.f_112906_ = new BlockState[this.f_112902_ * this.f_112903_ * this.f_112904_];

      for(BlockPos blockpos : BlockPos.m_121940_(p_112914_, p_112915_)) {
         int i = SectionPos.m_123171_(blockpos.m_123341_()) - p_112911_;
         int j = SectionPos.m_123171_(blockpos.m_123343_()) - p_112912_;
         LevelChunk levelchunk = p_112913_[i][j];
         int k = this.m_112925_(blockpos);
         this.f_112906_[k] = levelchunk.m_8055_(blockpos);
      }

   }

   protected final int m_112925_(BlockPos p_112926_) {
      return this.m_112916_(p_112926_.m_123341_(), p_112926_.m_123342_(), p_112926_.m_123343_());
   }

   protected int m_112916_(int p_112917_, int p_112918_, int p_112919_) {
      int i = p_112917_ - this.f_112901_.m_123341_();
      int j = p_112918_ - this.f_112901_.m_123342_();
      int k = p_112919_ - this.f_112901_.m_123343_();
      return k * this.f_112902_ * this.f_112903_ + j * this.f_112902_ + i;
   }

   public BlockState m_8055_(BlockPos p_112947_) {
      return this.f_112906_[this.m_112925_(p_112947_)];
   }

   public FluidState m_6425_(BlockPos p_112943_) {
      return this.f_112906_[this.m_112925_(p_112943_)].m_60819_();
   }

   public float m_7717_(Direction p_112940_, boolean p_112941_) {
      return this.f_112908_.m_7717_(p_112940_, p_112941_);
   }

   public LevelLightEngine m_5518_() {
      return this.f_112908_.m_5518_();
   }

   @Nullable
   public BlockEntity m_7702_(BlockPos p_112945_) {
      return this.m_112927_(p_112945_, LevelChunk.EntityCreationType.IMMEDIATE);
   }

   @Nullable
   public BlockEntity m_112927_(BlockPos p_112928_, LevelChunk.EntityCreationType p_112929_) {
      int i = SectionPos.m_123171_(p_112928_.m_123341_()) - this.f_112899_;
      int j = SectionPos.m_123171_(p_112928_.m_123343_()) - this.f_112900_;
      return this.f_112905_[i][j].m_5685_(p_112928_, p_112929_);
   }

   public int m_6171_(BlockPos p_112937_, ColorResolver p_112938_) {
      return this.f_112908_.m_6171_(p_112937_, p_112938_);
   }

   public int m_141937_() {
      return this.f_112908_.m_141937_();
   }

   public int m_141928_() {
      return this.f_112908_.m_141928_();
   }
}