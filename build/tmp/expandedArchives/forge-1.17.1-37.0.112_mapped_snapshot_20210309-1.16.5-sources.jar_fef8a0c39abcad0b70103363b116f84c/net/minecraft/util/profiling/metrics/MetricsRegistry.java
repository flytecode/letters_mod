package net.minecraft.util.profiling.metrics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class MetricsRegistry {
   public static final MetricsRegistry f_146067_ = new MetricsRegistry();
   private final WeakHashMap<ProfilerMeasured, Void> f_146068_ = new WeakHashMap<>();

   private MetricsRegistry() {
   }

   public void m_146072_(ProfilerMeasured p_146073_) {
      this.f_146068_.put(p_146073_, (Void)null);
   }

   public List<MetricSampler> m_146071_() {
      Map<String, List<MetricSampler>> map = this.f_146068_.keySet().stream().flatMap((p_146079_) -> {
         return p_146079_.m_142754_().stream();
      }).collect(Collectors.groupingBy(MetricSampler::m_146020_));
      return m_146076_(map);
   }

   private static List<MetricSampler> m_146076_(Map<String, List<MetricSampler>> p_146077_) {
      return p_146077_.entrySet().stream().map((p_146075_) -> {
         String s = p_146075_.getKey();
         List<MetricSampler> list = p_146075_.getValue();
         return (MetricSampler)(list.size() > 1 ? new MetricsRegistry.AggregatedMetricSampler(s, list) : list.get(0));
      }).collect(Collectors.toList());
   }

   static class AggregatedMetricSampler extends MetricSampler {
      private final List<MetricSampler> f_146080_;

      AggregatedMetricSampler(String p_146082_, List<MetricSampler> p_146083_) {
         super(p_146082_, p_146083_.get(0).m_146021_(), () -> {
            return m_146094_(p_146083_);
         }, () -> {
            m_146092_(p_146083_);
         }, m_146087_(p_146083_));
         this.f_146080_ = p_146083_;
      }

      private static MetricSampler.ThresholdTest m_146087_(List<MetricSampler> p_146088_) {
         return (p_146091_) -> {
            return p_146088_.stream().anyMatch((p_146086_) -> {
               return p_146086_.f_145986_ != null ? p_146086_.f_145986_.m_142488_(p_146091_) : false;
            });
         };
      }

      private static void m_146092_(List<MetricSampler> p_146093_) {
         for(MetricSampler metricsampler : p_146093_) {
            metricsampler.m_146001_();
         }

      }

      private static double m_146094_(List<MetricSampler> p_146095_) {
         double d0 = 0.0D;

         for(MetricSampler metricsampler : p_146095_) {
            d0 += metricsampler.m_146019_().getAsDouble();
         }

         return d0 / (double)p_146095_.size();
      }

      public boolean equals(@Nullable Object p_146101_) {
         if (this == p_146101_) {
            return true;
         } else if (p_146101_ != null && this.getClass() == p_146101_.getClass()) {
            if (!super.equals(p_146101_)) {
               return false;
            } else {
               MetricsRegistry.AggregatedMetricSampler metricsregistry$aggregatedmetricsampler = (MetricsRegistry.AggregatedMetricSampler)p_146101_;
               return this.f_146080_.equals(metricsregistry$aggregatedmetricsampler.f_146080_);
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(super.hashCode(), this.f_146080_);
      }
   }
}