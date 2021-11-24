package net.minecraft.util.profiling.metrics.profiling;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;

public class ProfilerSamplerAdapter {
   private final Set<String> f_146161_ = new ObjectOpenHashSet<>();

   public Set<MetricSampler> m_146163_(Supplier<ProfileCollector> p_146164_) {
      Set<MetricSampler> set = p_146164_.get().m_142579_().stream().filter((p_146176_) -> {
         return !this.f_146161_.contains(p_146176_.getLeft());
      }).map((p_146174_) -> {
         return m_146168_(p_146164_, p_146174_.getLeft(), p_146174_.getRight());
      }).collect(Collectors.toSet());

      for(MetricSampler metricsampler : set) {
         this.f_146161_.add(metricsampler.m_146020_());
      }

      return set;
   }

   private static MetricSampler m_146168_(Supplier<ProfileCollector> p_146169_, String p_146170_, MetricCategory p_146171_) {
      return MetricSampler.m_146009_(p_146170_, p_146171_, () -> {
         ActiveProfiler.PathEntry activeprofiler$pathentry = p_146169_.get().m_142431_(p_146170_);
         return activeprofiler$pathentry == null ? 0.0D : (double)activeprofiler$pathentry.m_142752_() / (double)TimeUtil.f_145017_;
      });
   }
}