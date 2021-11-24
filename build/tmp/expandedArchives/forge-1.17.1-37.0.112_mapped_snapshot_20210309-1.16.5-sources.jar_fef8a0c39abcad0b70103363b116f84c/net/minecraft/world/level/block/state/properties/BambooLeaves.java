package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum BambooLeaves implements StringRepresentable {
   NONE("none"),
   SMALL("small"),
   LARGE("large");

   private final String f_61319_;

   private BambooLeaves(String p_61325_) {
      this.f_61319_ = p_61325_;
   }

   public String toString() {
      return this.f_61319_;
   }

   public String m_7912_() {
      return this.f_61319_;
   }
}