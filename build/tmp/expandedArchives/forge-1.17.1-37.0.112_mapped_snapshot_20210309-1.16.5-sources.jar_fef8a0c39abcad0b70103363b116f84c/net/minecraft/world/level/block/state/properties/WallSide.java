package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum WallSide implements StringRepresentable {
   NONE("none"),
   LOW("low"),
   TALL("tall");

   private final String f_61818_;

   private WallSide(String p_61824_) {
      this.f_61818_ = p_61824_;
   }

   public String toString() {
      return this.m_7912_();
   }

   public String m_7912_() {
      return this.f_61818_;
   }
}