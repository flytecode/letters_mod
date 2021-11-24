package net.minecraft.world.level.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.SharedConstants;

public class LevelVersion {
   private final int f_78378_;
   private final long f_78379_;
   private final String f_78380_;
   private final int f_78381_;
   private final boolean f_78382_;

   public LevelVersion(int p_78384_, long p_78385_, String p_78386_, int p_78387_, boolean p_78388_) {
      this.f_78378_ = p_78384_;
      this.f_78379_ = p_78385_;
      this.f_78380_ = p_78386_;
      this.f_78381_ = p_78387_;
      this.f_78382_ = p_78388_;
   }

   public static LevelVersion m_78390_(Dynamic<?> p_78391_) {
      int i = p_78391_.get("version").asInt(0);
      long j = p_78391_.get("LastPlayed").asLong(0L);
      OptionalDynamic<?> optionaldynamic = p_78391_.get("Version");
      return optionaldynamic.result().isPresent() ? new LevelVersion(i, j, optionaldynamic.get("Name").asString(SharedConstants.m_136187_().getName()), optionaldynamic.get("Id").asInt(SharedConstants.m_136187_().getWorldVersion()), optionaldynamic.get("Snapshot").asBoolean(!SharedConstants.m_136187_().isStable())) : new LevelVersion(i, j, "", 0, false);
   }

   public int m_78389_() {
      return this.f_78378_;
   }

   public long m_78392_() {
      return this.f_78379_;
   }

   public String m_78393_() {
      return this.f_78380_;
   }

   public int m_78394_() {
      return this.f_78381_;
   }

   public boolean m_78395_() {
      return this.f_78382_;
   }
}