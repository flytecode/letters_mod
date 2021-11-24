package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public interface LightEventListener {
   void m_142202_(BlockPos p_164454_);

   void m_142519_(BlockPos p_164455_, int p_164456_);

   boolean m_142182_();

   int m_142528_(int p_164449_, boolean p_164450_, boolean p_164451_);

   default void m_75834_(BlockPos p_75835_, boolean p_75836_) {
      this.m_6191_(SectionPos.m_123199_(p_75835_), p_75836_);
   }

   void m_6191_(SectionPos p_75837_, boolean p_75838_);

   void m_141940_(ChunkPos p_164452_, boolean p_164453_);
}