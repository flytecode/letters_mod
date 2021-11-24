package net.minecraft.advancements;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum FrameType {
   TASK("task", 0, ChatFormatting.GREEN),
   CHALLENGE("challenge", 26, ChatFormatting.DARK_PURPLE),
   GOAL("goal", 52, ChatFormatting.GREEN);

   private final String f_15536_;
   private final int f_15537_;
   private final ChatFormatting f_15538_;
   private final Component f_15539_;

   private FrameType(String p_15545_, int p_15546_, ChatFormatting p_15547_) {
      this.f_15536_ = p_15545_;
      this.f_15537_ = p_15546_;
      this.f_15538_ = p_15547_;
      this.f_15539_ = new TranslatableComponent("advancements.toast." + p_15545_);
   }

   public String m_15548_() {
      return this.f_15536_;
   }

   public int m_15551_() {
      return this.f_15537_;
   }

   public static FrameType m_15549_(String p_15550_) {
      for(FrameType frametype : values()) {
         if (frametype.f_15536_.equals(p_15550_)) {
            return frametype;
         }
      }

      throw new IllegalArgumentException("Unknown frame type '" + p_15550_ + "'");
   }

   public ChatFormatting m_15552_() {
      return this.f_15538_;
   }

   public Component m_15553_() {
      return this.f_15539_;
   }
}