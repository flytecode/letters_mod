package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum AttachFace implements StringRepresentable {
   FLOOR("floor"),
   WALL("wall"),
   CEILING("ceiling");

   private final String f_61305_;

   private AttachFace(String p_61311_) {
      this.f_61305_ = p_61311_;
   }

   public String m_7912_() {
      return this.f_61305_;
   }
}