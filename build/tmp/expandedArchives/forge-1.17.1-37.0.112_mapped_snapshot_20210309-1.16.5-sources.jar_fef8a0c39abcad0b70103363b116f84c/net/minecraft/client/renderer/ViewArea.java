package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ViewArea {
   protected final LevelRenderer f_110838_;
   protected final Level f_110839_;
   protected int f_110840_;
   protected int f_110841_;
   protected int f_110842_;
   public ChunkRenderDispatcher.RenderChunk[] f_110843_;

   public ViewArea(ChunkRenderDispatcher p_110845_, Level p_110846_, int p_110847_, LevelRenderer p_110848_) {
      this.f_110838_ = p_110848_;
      this.f_110839_ = p_110846_;
      this.m_110853_(p_110847_);
      this.m_110864_(p_110845_);
   }

   protected void m_110864_(ChunkRenderDispatcher p_110865_) {
      int i = this.f_110841_ * this.f_110840_ * this.f_110842_;
      this.f_110843_ = new ChunkRenderDispatcher.RenderChunk[i];

      for(int j = 0; j < this.f_110841_; ++j) {
         for(int k = 0; k < this.f_110840_; ++k) {
            for(int l = 0; l < this.f_110842_; ++l) {
               int i1 = this.m_110855_(j, k, l);
               this.f_110843_[i1] = p_110865_.new RenderChunk(i1);
               this.f_110843_[i1].m_112801_(j * 16, k * 16, l * 16);
            }
         }
      }

   }

   public void m_110849_() {
      for(ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk : this.f_110843_) {
         chunkrenderdispatcher$renderchunk.m_112838_();
      }

   }

   private int m_110855_(int p_110856_, int p_110857_, int p_110858_) {
      return (p_110858_ * this.f_110840_ + p_110857_) * this.f_110841_ + p_110856_;
   }

   protected void m_110853_(int p_110854_) {
      int i = p_110854_ * 2 + 1;
      this.f_110841_ = i;
      this.f_110840_ = this.f_110839_.m_151559_();
      this.f_110842_ = i;
   }

   public void m_110850_(double p_110851_, double p_110852_) {
      int i = Mth.m_14107_(p_110851_);
      int j = Mth.m_14107_(p_110852_);

      for(int k = 0; k < this.f_110841_; ++k) {
         int l = this.f_110841_ * 16;
         int i1 = i - 8 - l / 2;
         int j1 = i1 + Math.floorMod(k * 16 - i1, l);

         for(int k1 = 0; k1 < this.f_110842_; ++k1) {
            int l1 = this.f_110842_ * 16;
            int i2 = j - 8 - l1 / 2;
            int j2 = i2 + Math.floorMod(k1 * 16 - i2, l1);

            for(int k2 = 0; k2 < this.f_110840_; ++k2) {
               int l2 = this.f_110839_.m_141937_() + k2 * 16;
               ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = this.f_110843_[this.m_110855_(k, k2, k1)];
               chunkrenderdispatcher$renderchunk.m_112801_(j1, l2, j2);
            }
         }
      }

   }

   public void m_110859_(int p_110860_, int p_110861_, int p_110862_, boolean p_110863_) {
      int i = Math.floorMod(p_110860_, this.f_110841_);
      int j = Math.floorMod(p_110861_ - this.f_110839_.m_151560_(), this.f_110840_);
      int k = Math.floorMod(p_110862_, this.f_110842_);
      ChunkRenderDispatcher.RenderChunk chunkrenderdispatcher$renderchunk = this.f_110843_[this.m_110855_(i, j, k)];
      chunkrenderdispatcher$renderchunk.m_112828_(p_110863_);
   }

   @Nullable
   protected ChunkRenderDispatcher.RenderChunk m_110866_(BlockPos p_110867_) {
      int i = Mth.m_14042_(p_110867_.m_123341_(), 16);
      int j = Mth.m_14042_(p_110867_.m_123342_() - this.f_110839_.m_141937_(), 16);
      int k = Mth.m_14042_(p_110867_.m_123343_(), 16);
      if (j >= 0 && j < this.f_110840_) {
         i = Mth.m_14100_(i, this.f_110841_);
         k = Mth.m_14100_(k, this.f_110842_);
         return this.f_110843_[this.m_110855_(i, j, k)];
      } else {
         return null;
      }
   }
}