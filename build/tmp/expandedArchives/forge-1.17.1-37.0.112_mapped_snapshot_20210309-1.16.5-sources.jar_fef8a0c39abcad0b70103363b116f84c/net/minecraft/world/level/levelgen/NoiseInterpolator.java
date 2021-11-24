package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;

public class NoiseInterpolator {
   private double[][] f_158572_;
   private double[][] f_158573_;
   private final int f_158574_;
   private final int f_158575_;
   private final int f_158576_;
   private final NoiseInterpolator.NoiseColumnFiller f_158577_;
   private double f_158578_;
   private double f_158579_;
   private double f_158580_;
   private double f_158581_;
   private double f_158582_;
   private double f_158583_;
   private double f_158584_;
   private double f_158585_;
   private double f_158586_;
   private double f_158587_;
   private double f_158588_;
   private double f_158589_;
   private double f_158590_;
   private double f_158591_;
   private final int f_158592_;
   private final int f_158593_;

   public NoiseInterpolator(int p_158595_, int p_158596_, int p_158597_, ChunkPos p_158598_, int p_158599_, NoiseInterpolator.NoiseColumnFiller p_158600_) {
      this.f_158574_ = p_158596_;
      this.f_158575_ = p_158597_;
      this.f_158576_ = p_158599_;
      this.f_158577_ = p_158600_;
      this.f_158572_ = m_158615_(p_158596_, p_158597_);
      this.f_158573_ = m_158615_(p_158596_, p_158597_);
      this.f_158592_ = p_158598_.f_45578_ * p_158595_;
      this.f_158593_ = p_158598_.f_45579_ * p_158597_;
   }

   private static double[][] m_158615_(int p_158616_, int p_158617_) {
      int i = p_158617_ + 1;
      int j = p_158616_ + 1;
      double[][] adouble = new double[i][j];

      for(int k = 0; k < i; ++k) {
         adouble[k] = new double[j];
      }

      return adouble;
   }

   public void m_158601_() {
      this.m_158609_(this.f_158572_, this.f_158592_);
   }

   public void m_158604_(int p_158605_) {
      this.m_158609_(this.f_158573_, this.f_158592_ + p_158605_ + 1);
   }

   private void m_158609_(double[][] p_158610_, int p_158611_) {
      for(int i = 0; i < this.f_158575_ + 1; ++i) {
         int j = this.f_158593_ + i;
         this.f_158577_.m_158620_(p_158610_[i], p_158611_, j, this.f_158576_, this.f_158574_);
      }

   }

   public void m_158606_(int p_158607_, int p_158608_) {
      this.f_158578_ = this.f_158572_[p_158608_][p_158607_];
      this.f_158579_ = this.f_158572_[p_158608_ + 1][p_158607_];
      this.f_158580_ = this.f_158573_[p_158608_][p_158607_];
      this.f_158581_ = this.f_158573_[p_158608_ + 1][p_158607_];
      this.f_158582_ = this.f_158572_[p_158608_][p_158607_ + 1];
      this.f_158583_ = this.f_158572_[p_158608_ + 1][p_158607_ + 1];
      this.f_158584_ = this.f_158573_[p_158608_][p_158607_ + 1];
      this.f_158585_ = this.f_158573_[p_158608_ + 1][p_158607_ + 1];
   }

   public void m_158602_(double p_158603_) {
      this.f_158586_ = Mth.m_14139_(p_158603_, this.f_158578_, this.f_158582_);
      this.f_158587_ = Mth.m_14139_(p_158603_, this.f_158580_, this.f_158584_);
      this.f_158588_ = Mth.m_14139_(p_158603_, this.f_158579_, this.f_158583_);
      this.f_158589_ = Mth.m_14139_(p_158603_, this.f_158581_, this.f_158585_);
   }

   public void m_158613_(double p_158614_) {
      this.f_158590_ = Mth.m_14139_(p_158614_, this.f_158586_, this.f_158587_);
      this.f_158591_ = Mth.m_14139_(p_158614_, this.f_158588_, this.f_158589_);
   }

   public double m_158618_(double p_158619_) {
      return Mth.m_14139_(p_158619_, this.f_158590_, this.f_158591_);
   }

   public void m_158612_() {
      double[][] adouble = this.f_158572_;
      this.f_158572_ = this.f_158573_;
      this.f_158573_ = adouble;
   }

   @FunctionalInterface
   public interface NoiseColumnFiller {
      void m_158620_(double[] p_158621_, int p_158622_, int p_158623_, int p_158624_, int p_158625_);
   }
}