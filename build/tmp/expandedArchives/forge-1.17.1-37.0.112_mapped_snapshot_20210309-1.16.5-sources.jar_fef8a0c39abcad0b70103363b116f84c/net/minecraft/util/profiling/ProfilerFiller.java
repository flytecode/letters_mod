package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;

public interface ProfilerFiller {
   String f_145958_ = "root";

   void m_7242_();

   void m_7241_();

   void m_6180_(String p_18581_);

   void m_6521_(Supplier<String> p_18582_);

   void m_7238_();

   void m_6182_(String p_18583_);

   void m_6523_(Supplier<String> p_18584_);

   void m_142259_(MetricCategory p_145959_);

   void m_6174_(String p_18585_);

   void m_6525_(Supplier<String> p_18586_);

   static ProfilerFiller m_18578_(final ProfilerFiller p_18579_, final ProfilerFiller p_18580_) {
      if (p_18579_ == InactiveProfiler.f_18554_) {
         return p_18580_;
      } else {
         return p_18580_ == InactiveProfiler.f_18554_ ? p_18579_ : new ProfilerFiller() {
            public void m_7242_() {
               p_18579_.m_7242_();
               p_18580_.m_7242_();
            }

            public void m_7241_() {
               p_18579_.m_7241_();
               p_18580_.m_7241_();
            }

            public void m_6180_(String p_18594_) {
               p_18579_.m_6180_(p_18594_);
               p_18580_.m_6180_(p_18594_);
            }

            public void m_6521_(Supplier<String> p_18596_) {
               p_18579_.m_6521_(p_18596_);
               p_18580_.m_6521_(p_18596_);
            }

            public void m_142259_(MetricCategory p_145961_) {
               p_18579_.m_142259_(p_145961_);
               p_18580_.m_142259_(p_145961_);
            }

            public void m_7238_() {
               p_18579_.m_7238_();
               p_18580_.m_7238_();
            }

            public void m_6182_(String p_18599_) {
               p_18579_.m_6182_(p_18599_);
               p_18580_.m_6182_(p_18599_);
            }

            public void m_6523_(Supplier<String> p_18601_) {
               p_18579_.m_6523_(p_18601_);
               p_18580_.m_6523_(p_18601_);
            }

            public void m_6174_(String p_18604_) {
               p_18579_.m_6174_(p_18604_);
               p_18580_.m_6174_(p_18604_);
            }

            public void m_6525_(Supplier<String> p_18606_) {
               p_18579_.m_6525_(p_18606_);
               p_18580_.m_6525_(p_18606_);
            }
         };
      }
   }
}