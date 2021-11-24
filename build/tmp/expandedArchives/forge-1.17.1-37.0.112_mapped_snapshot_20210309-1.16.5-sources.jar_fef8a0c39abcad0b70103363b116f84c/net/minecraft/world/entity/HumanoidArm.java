package net.minecraft.world.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum HumanoidArm {
   LEFT(new TranslatableComponent("options.mainHand.left")),
   RIGHT(new TranslatableComponent("options.mainHand.right"));

   private final Component f_20821_;

   private HumanoidArm(Component p_20827_) {
      this.f_20821_ = p_20827_;
   }

   public HumanoidArm m_20828_() {
      return this == LEFT ? RIGHT : LEFT;
   }

   public String toString() {
      return this.f_20821_.getString();
   }

   public Component m_20829_() {
      return this.f_20821_;
   }
}