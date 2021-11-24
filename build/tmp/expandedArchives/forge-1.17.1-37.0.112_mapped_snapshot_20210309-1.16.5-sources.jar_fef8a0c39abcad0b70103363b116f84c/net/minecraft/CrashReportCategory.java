package net.minecraft;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CrashReportCategory {
   private final String f_128137_;
   private final List<CrashReportCategory.Entry> f_128138_ = Lists.newArrayList();
   private StackTraceElement[] f_128139_ = new StackTraceElement[0];

   public CrashReportCategory(String p_178936_) {
      this.f_128137_ = p_178936_;
   }

   public static String m_178937_(LevelHeightAccessor p_178938_, double p_178939_, double p_178940_, double p_178941_) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", p_178939_, p_178940_, p_178941_, m_178947_(p_178938_, new BlockPos(p_178939_, p_178940_, p_178941_)));
   }

   public static String m_178947_(LevelHeightAccessor p_178948_, BlockPos p_178949_) {
      return m_178942_(p_178948_, p_178949_.m_123341_(), p_178949_.m_123342_(), p_178949_.m_123343_());
   }

   public static String m_178942_(LevelHeightAccessor p_178943_, int p_178944_, int p_178945_, int p_178946_) {
      StringBuilder stringbuilder = new StringBuilder();

      try {
         stringbuilder.append(String.format("World: (%d,%d,%d)", p_178944_, p_178945_, p_178946_));
      } catch (Throwable throwable2) {
         stringbuilder.append("(Error finding world loc)");
      }

      stringbuilder.append(", ");

      try {
         int i = SectionPos.m_123171_(p_178944_);
         int j = SectionPos.m_123171_(p_178945_);
         int k = SectionPos.m_123171_(p_178946_);
         int l = p_178944_ & 15;
         int i1 = p_178945_ & 15;
         int j1 = p_178946_ & 15;
         int k1 = SectionPos.m_123223_(i);
         int l1 = p_178943_.m_141937_();
         int i2 = SectionPos.m_123223_(k);
         int j2 = SectionPos.m_123223_(i + 1) - 1;
         int k2 = p_178943_.m_151558_() - 1;
         int l2 = SectionPos.m_123223_(k + 1) - 1;
         stringbuilder.append(String.format("Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)", l, i1, j1, i, j, k, k1, l1, i2, j2, k2, l2));
      } catch (Throwable throwable1) {
         stringbuilder.append("(Error finding chunk loc)");
      }

      stringbuilder.append(", ");

      try {
         int i3 = p_178944_ >> 9;
         int j3 = p_178946_ >> 9;
         int k3 = i3 << 5;
         int l3 = j3 << 5;
         int i4 = (i3 + 1 << 5) - 1;
         int j4 = (j3 + 1 << 5) - 1;
         int k4 = i3 << 9;
         int l4 = p_178943_.m_141937_();
         int i5 = j3 << 9;
         int j5 = (i3 + 1 << 9) - 1;
         int k5 = p_178943_.m_151558_() - 1;
         int l5 = (j3 + 1 << 9) - 1;
         stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)", i3, j3, k3, l3, i4, j4, k4, l4, i5, j5, k5, l5));
      } catch (Throwable throwable) {
         stringbuilder.append("(Error finding world loc)");
      }

      return stringbuilder.toString();
   }

   public CrashReportCategory m_128165_(String p_128166_, CrashReportDetail<String> p_128167_) {
      try {
         this.m_128159_(p_128166_, p_128167_.call());
      } catch (Throwable throwable) {
         this.m_128162_(p_128166_, throwable);
      }

      return this;
   }

   public CrashReportCategory m_128159_(String p_128160_, Object p_128161_) {
      this.f_128138_.add(new CrashReportCategory.Entry(p_128160_, p_128161_));
      return this;
   }

   public void m_128162_(String p_128163_, Throwable p_128164_) {
      this.m_128159_(p_128163_, p_128164_);
   }

   public int m_128148_(int p_128149_) {
      StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
      if (astacktraceelement.length <= 0) {
         return 0;
      } else {
         int len = astacktraceelement.length - 3 - p_128149_;
         if (len <= 0) len = astacktraceelement.length;
         this.f_128139_ = new StackTraceElement[len];
         System.arraycopy(astacktraceelement, astacktraceelement.length - len, this.f_128139_, 0, this.f_128139_.length);
         return this.f_128139_.length;
      }
   }

   public boolean m_128156_(StackTraceElement p_128157_, StackTraceElement p_128158_) {
      if (this.f_128139_.length != 0 && p_128157_ != null) {
         StackTraceElement stacktraceelement = this.f_128139_[0];
         if (stacktraceelement.isNativeMethod() == p_128157_.isNativeMethod() && stacktraceelement.getClassName().equals(p_128157_.getClassName()) && stacktraceelement.getFileName().equals(p_128157_.getFileName()) && stacktraceelement.getMethodName().equals(p_128157_.getMethodName())) {
            if (p_128158_ != null != this.f_128139_.length > 1) {
               return false;
            } else if (p_128158_ != null && !this.f_128139_[1].equals(p_128158_)) {
               return false;
            } else {
               this.f_128139_[0] = p_128157_;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void m_128174_(int p_128175_) {
      StackTraceElement[] astacktraceelement = new StackTraceElement[this.f_128139_.length - p_128175_];
      System.arraycopy(this.f_128139_, 0, astacktraceelement, 0, astacktraceelement.length);
      this.f_128139_ = astacktraceelement;
   }

   public void m_128168_(StringBuilder p_128169_) {
      p_128169_.append("-- ").append(this.f_128137_).append(" --\n");
      p_128169_.append("Details:");

      for(CrashReportCategory.Entry crashreportcategory$entry : this.f_128138_) {
         p_128169_.append("\n\t");
         p_128169_.append(crashreportcategory$entry.m_128183_());
         p_128169_.append(": ");
         p_128169_.append(crashreportcategory$entry.m_128184_());
      }

      if (this.f_128139_ != null && this.f_128139_.length > 0) {
         p_128169_.append("\nStacktrace:");
         p_128169_.append(net.minecraftforge.fmllegacy.CrashReportExtender.generateEnhancedStackTrace(this.f_128139_));
      }

   }

   public StackTraceElement[] m_128143_() {
      return this.f_128139_;
   }

   public void applyStackTrace(Throwable t) {
      this.f_128139_ = t.getStackTrace();
   }

   public static void m_178950_(CrashReportCategory p_178951_, LevelHeightAccessor p_178952_, BlockPos p_178953_, @Nullable BlockState p_178954_) {
      if (p_178954_ != null) {
         p_178951_.m_128165_("Block", p_178954_::toString);
      }

      p_178951_.m_128165_("Block location", () -> {
         return m_178947_(p_178952_, p_178953_);
      });
   }

   static class Entry {
      private final String f_128178_;
      private final String f_128179_;

      public Entry(String p_128181_, @Nullable Object p_128182_) {
         this.f_128178_ = p_128181_;
         if (p_128182_ == null) {
            this.f_128179_ = "~~NULL~~";
         } else if (p_128182_ instanceof Throwable) {
            Throwable throwable = (Throwable)p_128182_;
            this.f_128179_ = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
         } else {
            this.f_128179_ = p_128182_.toString();
         }

      }

      public String m_128183_() {
         return this.f_128178_;
      }

      public String m_128184_() {
         return this.f_128179_;
      }
   }
}
