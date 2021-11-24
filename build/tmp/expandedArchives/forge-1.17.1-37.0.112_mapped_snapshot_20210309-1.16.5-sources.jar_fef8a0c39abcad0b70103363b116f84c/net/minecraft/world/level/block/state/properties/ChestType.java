package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum ChestType implements StringRepresentable {
   SINGLE("single", 0),
   LEFT("left", 2),
   RIGHT("right", 1);

   public static final ChestType[] f_61475_ = values();
   private final String f_61476_;
   private final int f_61477_;

   private ChestType(String p_61483_, int p_61484_) {
      this.f_61476_ = p_61483_;
      this.f_61477_ = p_61484_;
   }

   public String m_7912_() {
      return this.f_61476_;
   }

   public ChestType m_61486_() {
      return f_61475_[this.f_61477_];
   }
}