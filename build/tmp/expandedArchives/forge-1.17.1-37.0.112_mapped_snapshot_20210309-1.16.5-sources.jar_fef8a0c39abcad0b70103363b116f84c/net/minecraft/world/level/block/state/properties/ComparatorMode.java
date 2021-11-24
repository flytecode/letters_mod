package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum ComparatorMode implements StringRepresentable {
   COMPARE("compare"),
   SUBTRACT("subtract");

   private final String f_61528_;

   private ComparatorMode(String p_61534_) {
      this.f_61528_ = p_61534_;
   }

   public String toString() {
      return this.f_61528_;
   }

   public String m_7912_() {
      return this.f_61528_;
   }
}