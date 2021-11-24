package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum NarratorStatus {
   OFF(0, "options.narrator.off"),
   ALL(1, "options.narrator.all"),
   CHAT(2, "options.narrator.chat"),
   SYSTEM(3, "options.narrator.system");

   private static final NarratorStatus[] f_91608_ = Arrays.stream(values()).sorted(Comparator.comparingInt(NarratorStatus::m_91618_)).toArray((p_91623_) -> {
      return new NarratorStatus[p_91623_];
   });
   private final int f_91609_;
   private final Component f_91610_;

   private NarratorStatus(int p_91616_, String p_91617_) {
      this.f_91609_ = p_91616_;
      this.f_91610_ = new TranslatableComponent(p_91617_);
   }

   public int m_91618_() {
      return this.f_91609_;
   }

   public Component m_91621_() {
      return this.f_91610_;
   }

   public static NarratorStatus m_91619_(int p_91620_) {
      return f_91608_[Mth.m_14100_(p_91620_, f_91608_.length)];
   }
}