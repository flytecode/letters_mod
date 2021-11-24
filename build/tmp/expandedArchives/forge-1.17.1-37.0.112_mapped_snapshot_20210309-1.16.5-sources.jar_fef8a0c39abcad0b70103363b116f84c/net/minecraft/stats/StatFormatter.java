package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.Util;

public interface StatFormatter {
   DecimalFormat f_12872_ = Util.m_137469_(new DecimalFormat("########0.00"), (p_12881_) -> {
      p_12881_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   });
   StatFormatter f_12873_ = NumberFormat.getIntegerInstance(Locale.US)::format;
   StatFormatter f_12874_ = (p_12885_) -> {
      return f_12872_.format((double)p_12885_ * 0.1D);
   };
   StatFormatter f_12875_ = (p_12883_) -> {
      double d0 = (double)p_12883_ / 100.0D;
      double d1 = d0 / 1000.0D;
      if (d1 > 0.5D) {
         return f_12872_.format(d1) + " km";
      } else {
         return d0 > 0.5D ? f_12872_.format(d0) + " m" : p_12883_ + " cm";
      }
   };
   StatFormatter f_12876_ = (p_12879_) -> {
      double d0 = (double)p_12879_ / 20.0D;
      double d1 = d0 / 60.0D;
      double d2 = d1 / 60.0D;
      double d3 = d2 / 24.0D;
      double d4 = d3 / 365.0D;
      if (d4 > 0.5D) {
         return f_12872_.format(d4) + " y";
      } else if (d3 > 0.5D) {
         return f_12872_.format(d3) + " d";
      } else if (d2 > 0.5D) {
         return f_12872_.format(d2) + " h";
      } else {
         return d1 > 0.5D ? f_12872_.format(d1) + " m" : d0 + " s";
      }
   };

   String m_12886_(int p_12887_);
}