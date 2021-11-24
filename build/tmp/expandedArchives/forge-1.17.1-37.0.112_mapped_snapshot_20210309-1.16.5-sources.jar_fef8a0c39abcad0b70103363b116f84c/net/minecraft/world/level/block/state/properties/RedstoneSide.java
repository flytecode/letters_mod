package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum RedstoneSide implements StringRepresentable {
   UP("up"),
   SIDE("side"),
   NONE("none");

   private final String f_61753_;

   private RedstoneSide(String p_61759_) {
      this.f_61753_ = p_61759_;
   }

   public String toString() {
      return this.m_7912_();
   }

   public String m_7912_() {
      return this.f_61753_;
   }

   public boolean m_61761_() {
      return this != NONE;
   }
}