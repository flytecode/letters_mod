package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public interface LevelHeightAccessor {
   int m_141928_();

   int m_141937_();

   default int m_151558_() {
      return this.m_141937_() + this.m_141928_();
   }

   default int m_151559_() {
      return this.m_151561_() - this.m_151560_();
   }

   default int m_151560_() {
      return SectionPos.m_123171_(this.m_141937_());
   }

   default int m_151561_() {
      return SectionPos.m_123171_(this.m_151558_() - 1) + 1;
   }

   default boolean m_151570_(BlockPos p_151571_) {
      return this.m_151562_(p_151571_.m_123342_());
   }

   default boolean m_151562_(int p_151563_) {
      return p_151563_ < this.m_141937_() || p_151563_ >= this.m_151558_();
   }

   default int m_151564_(int p_151565_) {
      return this.m_151566_(SectionPos.m_123171_(p_151565_));
   }

   default int m_151566_(int p_151567_) {
      return p_151567_ - this.m_151560_();
   }

   default int m_151568_(int p_151569_) {
      return p_151569_ + this.m_151560_();
   }
}