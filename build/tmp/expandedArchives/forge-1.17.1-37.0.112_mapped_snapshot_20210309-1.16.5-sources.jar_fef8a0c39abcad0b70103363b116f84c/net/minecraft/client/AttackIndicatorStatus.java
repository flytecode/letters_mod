package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum AttackIndicatorStatus {
   OFF(0, "options.off"),
   CROSSHAIR(1, "options.attack.crosshair"),
   HOTBAR(2, "options.attack.hotbar");

   private static final AttackIndicatorStatus[] f_90498_ = Arrays.stream(values()).sorted(Comparator.comparingInt(AttackIndicatorStatus::m_90508_)).toArray((p_90513_) -> {
      return new AttackIndicatorStatus[p_90513_];
   });
   private final int f_90499_;
   private final String f_90500_;

   private AttackIndicatorStatus(int p_90506_, String p_90507_) {
      this.f_90499_ = p_90506_;
      this.f_90500_ = p_90507_;
   }

   public int m_90508_() {
      return this.f_90499_;
   }

   public String m_90511_() {
      return this.f_90500_;
   }

   public static AttackIndicatorStatus m_90509_(int p_90510_) {
      return f_90498_[Mth.m_14100_(p_90510_, f_90498_.length)];
   }
}