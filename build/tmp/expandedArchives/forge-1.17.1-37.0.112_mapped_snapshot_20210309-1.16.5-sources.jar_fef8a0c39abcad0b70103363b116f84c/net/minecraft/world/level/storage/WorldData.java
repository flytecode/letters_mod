package net.minecraft.world.level.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public interface WorldData {
   int f_164978_ = 19133;
   int f_164979_ = 19132;

   DataPackConfig m_7513_();

   void m_6645_(DataPackConfig p_78634_);

   boolean m_6565_();

   Set<String> m_6161_();

   void m_7955_(String p_78638_, boolean p_78639_);

   default void m_5461_(CrashReportCategory p_78640_) {
      p_78640_.m_128165_("Known server brands", () -> {
         return String.join(", ", this.m_6161_());
      });
      p_78640_.m_128165_("Level was modded", () -> {
         return Boolean.toString(this.m_6565_());
      });
      p_78640_.m_128165_("Level storage version", () -> {
         int i = this.m_6517_();
         return String.format("0x%05X - %s", i, this.m_78646_(i));
      });
   }

   default String m_78646_(int p_78647_) {
      switch(p_78647_) {
      case 19132:
         return "McRegion";
      case 19133:
         return "Anvil";
      default:
         return "Unknown?";
      }
   }

   @Nullable
   CompoundTag m_6587_();

   void m_5917_(@Nullable CompoundTag p_78643_);

   ServerLevelData m_5996_();

   LevelSettings m_5926_();

   CompoundTag m_6626_(RegistryAccess p_78636_, @Nullable CompoundTag p_78637_);

   boolean m_5466_();

   int m_6517_();

   String m_5462_();

   GameType m_5464_();

   void m_5458_(GameType p_78635_);

   boolean m_5468_();

   Difficulty m_5472_();

   void m_6166_(Difficulty p_78633_);

   boolean m_5474_();

   void m_5560_(boolean p_78645_);

   GameRules m_5470_();

   CompoundTag m_6614_();

   CompoundTag m_6564_();

   void m_5915_(CompoundTag p_78641_);

   WorldGenSettings m_5961_();

   Lifecycle m_5754_();
}