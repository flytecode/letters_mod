package net.minecraft.world.level.storage;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.timers.TimerQueue;

public interface ServerLevelData extends WritableLevelData {
   String m_5462_();

   void m_5557_(boolean p_78623_);

   int m_6531_();

   void m_6399_(int p_78627_);

   void m_6398_(int p_78626_);

   int m_6558_();

   default void m_142471_(CrashReportCategory p_164976_, LevelHeightAccessor p_164977_) {
      WritableLevelData.super.m_142471_(p_164976_, p_164977_);
      p_164976_.m_128165_("Level name", this::m_5462_);
      p_164976_.m_128165_("Level game mode", () -> {
         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.m_5464_().m_46405_(), this.m_5464_().m_46392_(), this.m_5466_(), this.m_5468_());
      });
      p_164976_.m_128165_("Level weather", () -> {
         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", this.m_6531_(), this.m_6533_(), this.m_6558_(), this.m_6534_());
      });
   }

   int m_6537_();

   void m_6393_(int p_78616_);

   int m_6530_();

   void m_6391_(int p_78628_);

   int m_6528_();

   void m_6387_(int p_78629_);

   @Nullable
   UUID m_142403_();

   void m_8115_(UUID p_78620_);

   GameType m_5464_();

   void m_7831_(WorldBorder.Settings p_78619_);

   WorldBorder.Settings m_5813_();

   boolean m_6535_();

   void m_5555_(boolean p_78625_);

   boolean m_5468_();

   void m_5458_(GameType p_78618_);

   TimerQueue<MinecraftServer> m_7540_();

   void m_6253_(long p_78617_);

   void m_6247_(long p_78624_);
}