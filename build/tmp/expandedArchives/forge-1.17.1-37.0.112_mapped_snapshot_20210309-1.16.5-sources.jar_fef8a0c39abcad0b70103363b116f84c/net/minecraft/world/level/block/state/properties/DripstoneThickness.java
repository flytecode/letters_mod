package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum DripstoneThickness implements StringRepresentable {
   TIP_MERGE("tip_merge"),
   TIP("tip"),
   FRUSTUM("frustum"),
   MIDDLE("middle"),
   BASE("base");

   private final String f_156012_;

   private DripstoneThickness(String p_156018_) {
      this.f_156012_ = p_156018_;
   }

   public String toString() {
      return this.f_156012_;
   }

   public String m_7912_() {
      return this.f_156012_;
   }
}