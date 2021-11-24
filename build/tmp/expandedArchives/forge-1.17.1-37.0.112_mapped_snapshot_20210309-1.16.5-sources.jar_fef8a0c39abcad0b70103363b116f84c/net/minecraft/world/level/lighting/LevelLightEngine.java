package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;

public class LevelLightEngine implements LightEventListener {
   public static final int f_164443_ = 15;
   public static final int f_164444_ = 1;
   protected final LevelHeightAccessor f_164445_;
   @Nullable
   private final LayerLightEngine<?, ?> f_75802_;
   @Nullable
   private final LayerLightEngine<?, ?> f_75803_;

   public LevelLightEngine(LightChunkGetter p_75805_, boolean p_75806_, boolean p_75807_) {
      this.f_164445_ = p_75805_.m_7653_();
      this.f_75802_ = p_75806_ ? new BlockLightEngine(p_75805_) : null;
      this.f_75803_ = p_75807_ ? new SkyLightEngine(p_75805_) : null;
   }

   public void m_142202_(BlockPos p_75823_) {
      if (this.f_75802_ != null) {
         this.f_75802_.m_142202_(p_75823_);
      }

      if (this.f_75803_ != null) {
         this.f_75803_.m_142202_(p_75823_);
      }

   }

   public void m_142519_(BlockPos p_75824_, int p_75825_) {
      if (this.f_75802_ != null) {
         this.f_75802_.m_142519_(p_75824_, p_75825_);
      }

   }

   public boolean m_142182_() {
      if (this.f_75803_ != null && this.f_75803_.m_142182_()) {
         return true;
      } else {
         return this.f_75802_ != null && this.f_75802_.m_142182_();
      }
   }

   public int m_142528_(int p_75809_, boolean p_75810_, boolean p_75811_) {
      if (this.f_75802_ != null && this.f_75803_ != null) {
         int i = p_75809_ / 2;
         int j = this.f_75802_.m_142528_(i, p_75810_, p_75811_);
         int k = p_75809_ - i + j;
         int l = this.f_75803_.m_142528_(k, p_75810_, p_75811_);
         return j == 0 && l > 0 ? this.f_75802_.m_142528_(l, p_75810_, p_75811_) : l;
      } else if (this.f_75802_ != null) {
         return this.f_75802_.m_142528_(p_75809_, p_75810_, p_75811_);
      } else {
         return this.f_75803_ != null ? this.f_75803_.m_142528_(p_75809_, p_75810_, p_75811_) : p_75809_;
      }
   }

   public void m_6191_(SectionPos p_75827_, boolean p_75828_) {
      if (this.f_75802_ != null) {
         this.f_75802_.m_6191_(p_75827_, p_75828_);
      }

      if (this.f_75803_ != null) {
         this.f_75803_.m_6191_(p_75827_, p_75828_);
      }

   }

   public void m_141940_(ChunkPos p_75812_, boolean p_75813_) {
      if (this.f_75802_ != null) {
         this.f_75802_.m_141940_(p_75812_, p_75813_);
      }

      if (this.f_75803_ != null) {
         this.f_75803_.m_141940_(p_75812_, p_75813_);
      }

   }

   public LayerLightEventListener m_75814_(LightLayer p_75815_) {
      if (p_75815_ == LightLayer.BLOCK) {
         return (LayerLightEventListener)(this.f_75802_ == null ? LayerLightEventListener.DummyLightLayerEventListener.INSTANCE : this.f_75802_);
      } else {
         return (LayerLightEventListener)(this.f_75803_ == null ? LayerLightEventListener.DummyLightLayerEventListener.INSTANCE : this.f_75803_);
      }
   }

   public String m_75816_(LightLayer p_75817_, SectionPos p_75818_) {
      if (p_75817_ == LightLayer.BLOCK) {
         if (this.f_75802_ != null) {
            return this.f_75802_.m_6647_(p_75818_.m_123252_());
         }
      } else if (this.f_75803_ != null) {
         return this.f_75803_.m_6647_(p_75818_.m_123252_());
      }

      return "n/a";
   }

   public void m_5687_(LightLayer p_75819_, SectionPos p_75820_, @Nullable DataLayer p_75821_, boolean p_75822_) {
      if (p_75819_ == LightLayer.BLOCK) {
         if (this.f_75802_ != null) {
            this.f_75802_.m_75660_(p_75820_.m_123252_(), p_75821_, p_75822_);
         }
      } else if (this.f_75803_ != null) {
         this.f_75803_.m_75660_(p_75820_.m_123252_(), p_75821_, p_75822_);
      }

   }

   public void m_6462_(ChunkPos p_75829_, boolean p_75830_) {
      if (this.f_75802_ != null) {
         this.f_75802_.m_75699_(p_75829_, p_75830_);
      }

      if (this.f_75803_ != null) {
         this.f_75803_.m_75699_(p_75829_, p_75830_);
      }

   }

   public int m_75831_(BlockPos p_75832_, int p_75833_) {
      int i = this.f_75803_ == null ? 0 : this.f_75803_.m_7768_(p_75832_) - p_75833_;
      int j = this.f_75802_ == null ? 0 : this.f_75802_.m_7768_(p_75832_);
      return Math.max(j, i);
   }

   public int m_164446_() {
      return this.f_164445_.m_151559_() + 2;
   }

   public int m_164447_() {
      return this.f_164445_.m_151560_() - 1;
   }

   public int m_164448_() {
      return this.m_164447_() + this.m_164446_();
   }
}