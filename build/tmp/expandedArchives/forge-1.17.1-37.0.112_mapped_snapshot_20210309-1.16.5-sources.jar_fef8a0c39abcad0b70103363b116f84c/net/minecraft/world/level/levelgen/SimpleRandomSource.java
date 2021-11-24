package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Pair;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.util.ThreadingDetector;

public class SimpleRandomSource implements RandomSource {
   private static final int f_158880_ = 48;
   private static final long f_158881_ = 281474976710655L;
   private static final long f_158882_ = 25214903917L;
   private static final long f_158883_ = 11L;
   private static final float f_158884_ = 5.9604645E-8F;
   private static final double f_158885_ = (double)1.110223E-16F;
   private final AtomicLong f_158886_ = new AtomicLong();
   private double f_158887_;
   private boolean f_158888_;

   public SimpleRandomSource(long p_158890_) {
      this.setSeed(p_158890_);
   }

   public void setSeed(long p_158902_) {
      if (!this.f_158886_.compareAndSet(this.f_158886_.get(), (p_158902_ ^ 25214903917L) & 281474976710655L)) {
         throw ThreadingDetector.m_145007_("SimpleRandomSource", (DebugBuffer<Pair<Thread, StackTraceElement[]>>)null);
      }
   }

   private int m_158891_(int p_158892_) {
      long i = this.f_158886_.get();
      long j = i * 25214903917L + 11L & 281474976710655L;
      if (!this.f_158886_.compareAndSet(i, j)) {
         throw ThreadingDetector.m_145007_("SimpleRandomSource", (DebugBuffer<Pair<Thread, StackTraceElement[]>>)null);
      } else {
         return (int)(j >> 48 - p_158892_);
      }
   }

   public int nextInt() {
      return this.m_158891_(32);
   }

   public int nextInt(int p_158899_) {
      if (p_158899_ <= 0) {
         throw new IllegalArgumentException("Bound must be positive");
      } else if ((p_158899_ & p_158899_ - 1) == 0) {
         return (int)((long)p_158899_ * (long)this.m_158891_(31) >> 31);
      } else {
         int i;
         int j;
         do {
            i = this.m_158891_(31);
            j = i % p_158899_;
         } while(i - j + (p_158899_ - 1) < 0);

         return j;
      }
   }

   public long nextLong() {
      int i = this.m_158891_(32);
      int j = this.m_158891_(32);
      long k = (long)i << 32;
      return k + (long)j;
   }

   public boolean nextBoolean() {
      return this.m_158891_(1) != 0;
   }

   public float nextFloat() {
      return (float)this.m_158891_(24) * 5.9604645E-8F;
   }

   public double nextDouble() {
      int i = this.m_158891_(26);
      int j = this.m_158891_(27);
      long k = ((long)i << 27) + (long)j;
      return (double)k * (double)1.110223E-16F;
   }

   public double nextGaussian() {
      if (this.f_158888_) {
         this.f_158888_ = false;
         return this.f_158887_;
      } else {
         while(true) {
            double d0 = 2.0D * this.nextDouble() - 1.0D;
            double d1 = 2.0D * this.nextDouble() - 1.0D;
            double d2 = Mth.m_144952_(d0) + Mth.m_144952_(d1);
            if (!(d2 >= 1.0D)) {
               if (d2 != 0.0D) {
                  double d3 = Math.sqrt(-2.0D * Math.log(d2) / d2);
                  this.f_158887_ = d1 * d3;
                  this.f_158888_ = true;
                  return d0 * d3;
               }
            }
         }
      }
   }
}