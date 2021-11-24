package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class Cavifier implements NoiseModifier {
   private final int f_158095_;
   private final NormalNoise f_158096_;
   private final NormalNoise f_158097_;
   private final NormalNoise f_158098_;
   private final NormalNoise f_158099_;
   private final NormalNoise f_158100_;
   private final NormalNoise f_158101_;
   private final NormalNoise f_158102_;
   private final NormalNoise f_158103_;
   private final NormalNoise f_158104_;
   private final NormalNoise f_158105_;
   private final NormalNoise f_158106_;
   private final NormalNoise f_158107_;
   private final NormalNoise f_158108_;
   private final NormalNoise f_158109_;
   private final NormalNoise f_158110_;
   private final NormalNoise f_158111_;
   private static final int f_158112_ = 128;
   private static final int f_158113_ = 170;

   public Cavifier(RandomSource p_158115_, int p_158116_) {
      this.f_158095_ = p_158116_;
      this.f_158097_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -7, 1.0D, 1.0D);
      this.f_158098_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158099_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158100_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -7, 1.0D);
      this.f_158101_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158102_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -11, 1.0D);
      this.f_158103_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -11, 1.0D);
      this.f_158104_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -7, 1.0D);
      this.f_158105_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -7, 1.0D);
      this.f_158106_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -11, 1.0D);
      this.f_158107_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158108_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -5, 1.0D);
      this.f_158109_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158110_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D, 1.0D, 1.0D);
      this.f_158096_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 1.0D);
      this.f_158111_ = NormalNoise.m_164354_(new SimpleRandomSource(p_158115_.nextLong()), -8, 0.5D, 1.0D, 2.0D, 1.0D, 2.0D, 1.0D, 0.0D, 2.0D, 0.0D);
   }

   public double m_142124_(double p_158151_, int p_158152_, int p_158153_, int p_158154_) {
      boolean flag = p_158151_ < 170.0D;
      double d0 = this.m_158146_(p_158154_, p_158152_, p_158153_);
      double d1 = this.m_158138_(p_158154_, p_158152_, p_158153_);
      if (flag) {
         return Math.min(p_158151_, (d1 + d0) * 128.0D * 5.0D);
      } else {
         double d2 = this.f_158111_.m_75380_((double)p_158154_, (double)p_158152_ / 1.5D, (double)p_158153_);
         double d3 = Mth.m_14008_(d2 + 0.25D, -1.0D, 1.0D);
         double d4 = (double)((float)(30 - p_158152_) / 8.0F);
         double d5 = d3 + Mth.m_14085_(0.5D, 0.0D, d4);
         double d6 = this.m_158134_(p_158154_, p_158152_, p_158153_);
         double d7 = this.m_158142_(p_158154_, p_158152_, p_158153_);
         double d8 = d5 + d6;
         double d9 = Math.min(d8, Math.min(d1, d7) + d0);
         double d10 = Math.max(d9, this.m_158124_(p_158154_, p_158152_, p_158153_));
         return 128.0D * Mth.m_14008_(d10, -1.0D, 1.0D);
      }
   }

   private double m_158119_(double p_158120_, int p_158121_, int p_158122_, int p_158123_) {
      double d0 = this.f_158110_.m_75380_((double)(p_158121_ * 2), (double)p_158122_, (double)(p_158123_ * 2));
      d0 = NoiseUtils.m_164334_(d0, 1.0D);
      int i = 0;
      double d1 = (double)(p_158122_ - 0) / 40.0D;
      d0 = d0 + Mth.m_14085_(0.5D, p_158120_, d1);
      double d2 = 3.0D;
      d0 = 4.0D * d0 + 3.0D;
      return Math.min(p_158120_, d0);
   }

   private double m_158124_(int p_158125_, int p_158126_, int p_158127_) {
      double d0 = 0.0D;
      double d1 = 2.0D;
      double d2 = NoiseUtils.m_164337_(this.f_158098_, (double)p_158125_, (double)p_158126_, (double)p_158127_, 0.0D, 2.0D);
      double d3 = 0.0D;
      double d4 = 1.1D;
      double d5 = NoiseUtils.m_164337_(this.f_158099_, (double)p_158125_, (double)p_158126_, (double)p_158127_, 0.0D, 1.1D);
      d5 = Math.pow(d5, 3.0D);
      double d6 = 25.0D;
      double d7 = 0.3D;
      double d8 = this.f_158097_.m_75380_((double)p_158125_ * 25.0D, (double)p_158126_ * 0.3D, (double)p_158127_ * 25.0D);
      d8 = d5 * (d8 * 2.0D - d2);
      return d8 > 0.03D ? d8 : Double.NEGATIVE_INFINITY;
   }

   private double m_158134_(int p_158135_, int p_158136_, int p_158137_) {
      double d0 = this.f_158096_.m_75380_((double)p_158135_, (double)(p_158136_ * 8), (double)p_158137_);
      return Mth.m_144952_(d0) * 4.0D;
   }

   private double m_158138_(int p_158139_, int p_158140_, int p_158141_) {
      double d0 = this.f_158106_.m_75380_((double)(p_158139_ * 2), (double)p_158140_, (double)(p_158141_ * 2));
      double d1 = Cavifier.QuantizedSpaghettiRarity.m_158158_(d0);
      double d2 = 0.065D;
      double d3 = 0.088D;
      double d4 = NoiseUtils.m_164337_(this.f_158107_, (double)p_158139_, (double)p_158140_, (double)p_158141_, 0.065D, 0.088D);
      double d5 = m_158128_(this.f_158104_, (double)p_158139_, (double)p_158140_, (double)p_158141_, d1);
      double d6 = Math.abs(d1 * d5) - d4;
      double d7 = m_158128_(this.f_158105_, (double)p_158139_, (double)p_158140_, (double)p_158141_, d1);
      double d8 = Math.abs(d1 * d7) - d4;
      return m_158117_(Math.max(d6, d8));
   }

   private double m_158142_(int p_158143_, int p_158144_, int p_158145_) {
      double d0 = this.f_158102_.m_75380_((double)(p_158143_ * 2), (double)p_158144_, (double)(p_158145_ * 2));
      double d1 = Cavifier.QuantizedSpaghettiRarity.m_158156_(d0);
      double d2 = 0.6D;
      double d3 = 1.3D;
      double d4 = NoiseUtils.m_164337_(this.f_158103_, (double)(p_158143_ * 2), (double)p_158144_, (double)(p_158145_ * 2), 0.6D, 1.3D);
      double d5 = m_158128_(this.f_158100_, (double)p_158143_, (double)p_158144_, (double)p_158145_, d1);
      double d6 = 0.083D;
      double d7 = Math.abs(d1 * d5) - 0.083D * d4;
      int i = this.f_158095_;
      int j = 8;
      double d8 = NoiseUtils.m_164337_(this.f_158101_, (double)p_158143_, 0.0D, (double)p_158145_, (double)i, 8.0D);
      double d9 = Math.abs(d8 - (double)p_158144_ / 8.0D) - 1.0D * d4;
      d9 = d9 * d9 * d9;
      return m_158117_(Math.max(d9, d7));
   }

   private double m_158146_(int p_158147_, int p_158148_, int p_158149_) {
      double d0 = NoiseUtils.m_164337_(this.f_158109_, (double)p_158147_, (double)p_158148_, (double)p_158149_, 0.0D, 0.1D);
      return (0.4D - Math.abs(this.f_158108_.m_75380_((double)p_158147_, (double)p_158148_, (double)p_158149_))) * d0;
   }

   private static double m_158117_(double p_158118_) {
      return Mth.m_14008_(p_158118_, -1.0D, 1.0D);
   }

   private static double m_158128_(NormalNoise p_158129_, double p_158130_, double p_158131_, double p_158132_, double p_158133_) {
      return p_158129_.m_75380_(p_158130_ / p_158133_, p_158131_ / p_158133_, p_158132_ / p_158133_);
   }

   static final class QuantizedSpaghettiRarity {
      private QuantizedSpaghettiRarity() {
      }

      static double m_158156_(double p_158157_) {
         if (p_158157_ < -0.75D) {
            return 0.5D;
         } else if (p_158157_ < -0.5D) {
            return 0.75D;
         } else if (p_158157_ < 0.5D) {
            return 1.0D;
         } else {
            return p_158157_ < 0.75D ? 2.0D : 3.0D;
         }
      }

      static double m_158158_(double p_158159_) {
         if (p_158159_ < -0.5D) {
            return 0.75D;
         } else if (p_158159_ < 0.0D) {
            return 1.0D;
         } else {
            return p_158159_ < 0.5D ? 1.5D : 2.0D;
         }
      }
   }
}