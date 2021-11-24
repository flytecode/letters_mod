package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

public interface LayerLightEventListener extends LightEventListener {
   @Nullable
   DataLayer m_8079_(SectionPos p_75709_);

   int m_7768_(BlockPos p_75710_);

   public static enum DummyLightLayerEventListener implements LayerLightEventListener {
      INSTANCE;

      @Nullable
      public DataLayer m_8079_(SectionPos p_75718_) {
         return null;
      }

      public int m_7768_(BlockPos p_75723_) {
         return 0;
      }

      public void m_142202_(BlockPos p_164434_) {
      }

      public void m_142519_(BlockPos p_164436_, int p_164437_) {
      }

      public boolean m_142182_() {
         return false;
      }

      public int m_142528_(int p_164427_, boolean p_164428_, boolean p_164429_) {
         return p_164427_;
      }

      public void m_6191_(SectionPos p_75720_, boolean p_75721_) {
      }

      public void m_141940_(ChunkPos p_164431_, boolean p_164432_) {
      }
   }
}