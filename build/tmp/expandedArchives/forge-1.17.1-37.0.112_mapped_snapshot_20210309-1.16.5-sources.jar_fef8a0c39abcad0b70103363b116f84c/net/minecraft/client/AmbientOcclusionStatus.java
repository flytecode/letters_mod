package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum AmbientOcclusionStatus {
   OFF(0, "options.ao.off"),
   MIN(1, "options.ao.min"),
   MAX(2, "options.ao.max");

   private static final AmbientOcclusionStatus[] f_90476_ = Arrays.stream(values()).sorted(Comparator.comparingInt(AmbientOcclusionStatus::m_90486_)).toArray((p_90491_) -> {
      return new AmbientOcclusionStatus[p_90491_];
   });
   private final int f_90477_;
   private final String f_90478_;

   private AmbientOcclusionStatus(int p_90484_, String p_90485_) {
      this.f_90477_ = p_90484_;
      this.f_90478_ = p_90485_;
   }

   public int m_90486_() {
      return this.f_90477_;
   }

   public String m_90489_() {
      return this.f_90478_;
   }

   public static AmbientOcclusionStatus m_90487_(int p_90488_) {
      return f_90476_[Mth.m_14100_(p_90488_, f_90476_.length)];
   }
}