package net.minecraft.world.level.storage;

import net.minecraft.CrashReportCategory;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelHeightAccessor;

public interface LevelData {
   int m_6789_();

   int m_6527_();

   int m_6526_();

   float m_6790_();

   long m_6793_();

   long m_6792_();

   boolean m_6534_();

   boolean m_6533_();

   void m_5565_(boolean p_78171_);

   boolean m_5466_();

   GameRules m_5470_();

   Difficulty m_5472_();

   boolean m_5474_();

   default void m_142471_(CrashReportCategory p_164873_, LevelHeightAccessor p_164874_) {
      p_164873_.m_128165_("Level spawn location", () -> {
         return CrashReportCategory.m_178942_(p_164874_, this.m_6789_(), this.m_6527_(), this.m_6526_());
      });
      p_164873_.m_128165_("Level time", () -> {
         return String.format("%d game time, %d day time", this.m_6793_(), this.m_6792_());
      });
   }
}