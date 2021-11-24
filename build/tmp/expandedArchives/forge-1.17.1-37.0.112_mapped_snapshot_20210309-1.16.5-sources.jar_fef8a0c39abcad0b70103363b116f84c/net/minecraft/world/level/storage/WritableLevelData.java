package net.minecraft.world.level.storage;

import net.minecraft.core.BlockPos;

public interface WritableLevelData extends LevelData {
   void m_6395_(int p_78651_);

   void m_6397_(int p_78652_);

   void m_6400_(int p_78653_);

   void m_7113_(float p_78648_);

   default void m_7250_(BlockPos p_78649_, float p_78650_) {
      this.m_6395_(p_78649_.m_123341_());
      this.m_6397_(p_78649_.m_123342_());
      this.m_6400_(p_78649_.m_123343_());
      this.m_7113_(p_78650_);
   }
}