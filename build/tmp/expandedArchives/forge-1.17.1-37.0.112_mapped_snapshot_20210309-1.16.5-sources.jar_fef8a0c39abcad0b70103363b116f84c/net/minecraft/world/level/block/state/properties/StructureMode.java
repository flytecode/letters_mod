package net.minecraft.world.level.block.state.properties;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.StringRepresentable;

public enum StructureMode implements StringRepresentable {
   SAVE("save"),
   LOAD("load"),
   CORNER("corner"),
   DATA("data");

   private final String f_61802_;
   private final Component f_61803_;

   private StructureMode(String p_61809_) {
      this.f_61802_ = p_61809_;
      this.f_61803_ = new TranslatableComponent("structure_block.mode_info." + p_61809_);
   }

   public String m_7912_() {
      return this.f_61802_;
   }

   public Component m_61811_() {
      return this.f_61803_;
   }
}