package net.minecraft.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;

public class StatsComponent extends JComponent {
   private static final DecimalFormat f_139955_ = Util.m_137469_(new DecimalFormat("########0.000"), (p_139968_) -> {
      p_139968_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   });
   private final int[] f_139956_ = new int[256];
   private int f_139957_;
   private final String[] f_139958_ = new String[11];
   private final MinecraftServer f_139959_;
   private final Timer f_139960_;

   public StatsComponent(MinecraftServer p_139963_) {
      this.f_139959_ = p_139963_;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      this.f_139960_ = new Timer(500, (p_139966_) -> {
         this.m_139971_();
      });
      this.f_139960_.start();
      this.setBackground(Color.BLACK);
   }

   private void m_139971_() {
      long i = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      this.f_139958_[0] = "Memory use: " + i / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.f_139958_[1] = "Avg tick: " + f_139955_.format(this.m_139969_(this.f_139959_.f_129748_) * 1.0E-6D) + " ms";
      this.f_139956_[this.f_139957_++ & 255] = (int)(i * 100L / Runtime.getRuntime().maxMemory());
      this.repaint();
   }

   private double m_139969_(long[] p_139970_) {
      long i = 0L;

      for(long j : p_139970_) {
         i += j;
      }

      return (double)i / (double)p_139970_.length;
   }

   public void paint(Graphics p_139973_) {
      p_139973_.setColor(new Color(16777215));
      p_139973_.fillRect(0, 0, 456, 246);

      for(int i = 0; i < 256; ++i) {
         int j = this.f_139956_[i + this.f_139957_ & 255];
         p_139973_.setColor(new Color(j + 28 << 16));
         p_139973_.fillRect(i, 100 - j, 1, j);
      }

      p_139973_.setColor(Color.BLACK);

      for(int k = 0; k < this.f_139958_.length; ++k) {
         String s = this.f_139958_[k];
         if (s != null) {
            p_139973_.drawString(s, 32, 116 + k * 16);
         }
      }

   }

   public void m_139964_() {
      this.f_139960_.stop();
   }
}