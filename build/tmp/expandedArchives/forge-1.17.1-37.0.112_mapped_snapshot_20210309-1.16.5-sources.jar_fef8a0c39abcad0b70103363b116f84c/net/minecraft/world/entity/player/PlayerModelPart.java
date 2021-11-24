package net.minecraft.world.entity.player;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum PlayerModelPart {
   CAPE(0, "cape"),
   JACKET(1, "jacket"),
   LEFT_SLEEVE(2, "left_sleeve"),
   RIGHT_SLEEVE(3, "right_sleeve"),
   LEFT_PANTS_LEG(4, "left_pants_leg"),
   RIGHT_PANTS_LEG(5, "right_pants_leg"),
   HAT(6, "hat");

   private final int f_36434_;
   private final int f_36435_;
   private final String f_36436_;
   private final Component f_36437_;

   private PlayerModelPart(int p_36443_, String p_36444_) {
      this.f_36434_ = p_36443_;
      this.f_36435_ = 1 << p_36443_;
      this.f_36436_ = p_36444_;
      this.f_36437_ = new TranslatableComponent("options.modelPart." + p_36444_);
   }

   public int m_36445_() {
      return this.f_36435_;
   }

   public int m_150114_() {
      return this.f_36434_;
   }

   public String m_36446_() {
      return this.f_36436_;
   }

   public Component m_36447_() {
      return this.f_36437_;
   }
}