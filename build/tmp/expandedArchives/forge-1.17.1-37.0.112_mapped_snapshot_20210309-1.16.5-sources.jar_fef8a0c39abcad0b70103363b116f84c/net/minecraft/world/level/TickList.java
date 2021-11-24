package net.minecraft.world.level;

import net.minecraft.core.BlockPos;

public interface TickList<T> {
   boolean m_5916_(BlockPos p_47312_, T p_47313_);

   default void m_5945_(BlockPos p_47314_, T p_47315_, int p_47316_) {
      this.m_7663_(p_47314_, p_47315_, p_47316_, TickPriority.NORMAL);
   }

   void m_7663_(BlockPos p_47317_, T p_47318_, int p_47319_, TickPriority p_47320_);

   boolean m_5913_(BlockPos p_47321_, T p_47322_);

   int m_142536_();
}