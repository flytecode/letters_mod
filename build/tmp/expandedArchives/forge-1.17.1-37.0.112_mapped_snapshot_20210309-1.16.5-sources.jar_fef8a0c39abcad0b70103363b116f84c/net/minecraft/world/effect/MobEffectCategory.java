package net.minecraft.world.effect;

import net.minecraft.ChatFormatting;

public enum MobEffectCategory {
   BENEFICIAL(ChatFormatting.BLUE),
   HARMFUL(ChatFormatting.RED),
   NEUTRAL(ChatFormatting.BLUE);

   private final ChatFormatting f_19490_;

   private MobEffectCategory(ChatFormatting p_19496_) {
      this.f_19490_ = p_19496_;
   }

   public ChatFormatting m_19497_() {
      return this.f_19490_;
   }
}