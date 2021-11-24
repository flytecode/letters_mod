package net.minecraft.util.profiling;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public class InactiveProfiler implements ProfileCollector {
   public static final InactiveProfiler f_18554_ = new InactiveProfiler();

   private InactiveProfiler() {
   }

   public void m_7242_() {
   }

   public void m_7241_() {
   }

   public void m_6180_(String p_18559_) {
   }

   public void m_6521_(Supplier<String> p_18561_) {
   }

   public void m_142259_(MetricCategory p_145951_) {
   }

   public void m_7238_() {
   }

   public void m_6182_(String p_18564_) {
   }

   public void m_6523_(Supplier<String> p_18566_) {
   }

   public void m_6174_(String p_18569_) {
   }

   public void m_6525_(Supplier<String> p_18571_) {
   }

   public ProfileResults m_5948_() {
      return EmptyProfileResults.f_18441_;
   }

   @Nullable
   public ActiveProfiler.PathEntry m_142431_(String p_145953_) {
      return null;
   }

   public Set<Pair<String, MetricCategory>> m_142579_() {
      return ImmutableSet.of();
   }
}