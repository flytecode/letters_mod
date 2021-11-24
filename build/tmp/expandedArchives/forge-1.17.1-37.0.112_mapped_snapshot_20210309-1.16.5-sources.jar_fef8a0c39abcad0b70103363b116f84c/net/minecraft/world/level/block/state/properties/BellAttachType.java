package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum BellAttachType implements StringRepresentable {
   FLOOR("floor"),
   CEILING("ceiling"),
   SINGLE_WALL("single_wall"),
   DOUBLE_WALL("double_wall");

   private final String f_61349_;

   private BellAttachType(String p_61355_) {
      this.f_61349_ = p_61355_;
   }

   public String m_7912_() {
      return this.f_61349_;
   }
}