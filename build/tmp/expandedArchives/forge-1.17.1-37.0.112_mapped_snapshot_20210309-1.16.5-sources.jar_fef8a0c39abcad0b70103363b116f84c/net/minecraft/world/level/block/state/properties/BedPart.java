package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum BedPart implements StringRepresentable {
   HEAD("head"),
   FOOT("foot");

   private final String f_61333_;

   private BedPart(String p_61339_) {
      this.f_61333_ = p_61339_;
   }

   public String toString() {
      return this.f_61333_;
   }

   public String m_7912_() {
      return this.f_61333_;
   }
}