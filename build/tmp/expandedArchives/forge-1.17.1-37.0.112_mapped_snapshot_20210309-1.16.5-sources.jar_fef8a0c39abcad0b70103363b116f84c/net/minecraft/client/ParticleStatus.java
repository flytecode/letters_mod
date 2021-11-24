package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum ParticleStatus {
   ALL(0, "options.particles.all"),
   DECREASED(1, "options.particles.decreased"),
   MINIMAL(2, "options.particles.minimal");

   private static final ParticleStatus[] f_92185_ = Arrays.stream(values()).sorted(Comparator.comparingInt(ParticleStatus::m_92198_)).toArray((p_92200_) -> {
      return new ParticleStatus[p_92200_];
   });
   private final int f_92186_;
   private final String f_92187_;

   private ParticleStatus(int p_92193_, String p_92194_) {
      this.f_92186_ = p_92193_;
      this.f_92187_ = p_92194_;
   }

   public String m_92195_() {
      return this.f_92187_;
   }

   public int m_92198_() {
      return this.f_92186_;
   }

   public static ParticleStatus m_92196_(int p_92197_) {
      return f_92185_[Mth.m_14100_(p_92197_, f_92185_.length)];
   }
}