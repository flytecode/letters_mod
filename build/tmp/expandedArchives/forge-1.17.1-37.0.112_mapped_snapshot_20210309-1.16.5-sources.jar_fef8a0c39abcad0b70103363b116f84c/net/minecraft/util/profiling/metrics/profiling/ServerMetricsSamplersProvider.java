package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class ServerMetricsSamplersProvider implements MetricsSamplerProvider {
   private static final Logger f_181117_ = LogManager.getLogger();
   private final Set<MetricSampler> f_146177_ = new ObjectOpenHashSet<>();
   private final ProfilerSamplerAdapter f_146178_ = new ProfilerSamplerAdapter();

   public ServerMetricsSamplersProvider(LongSupplier p_146180_, boolean p_146181_) {
      this.f_146177_.add(m_146188_(p_146180_));
      if (p_146181_) {
         this.f_146177_.addAll(m_146182_());
      }

   }

   public static Set<MetricSampler> m_146182_() {
      Builder<MetricSampler> builder = ImmutableSet.builder();

      try {
         ServerMetricsSamplersProvider.CpuStats servermetricssamplersprovider$cpustats = new ServerMetricsSamplersProvider.CpuStats();
         IntStream.range(0, servermetricssamplersprovider$cpustats.f_146200_).mapToObj((p_146185_) -> {
            return MetricSampler.m_146009_("cpu#" + p_146185_, MetricCategory.CPU, () -> {
               return servermetricssamplersprovider$cpustats.m_146207_(p_146185_);
            });
         }).forEach(builder::add);
      } catch (Throwable throwable) {
         f_181117_.warn("Failed to query cpu, no cpu stats will be recorded", throwable);
      }

      builder.add(MetricSampler.m_146009_("heap MiB", MetricCategory.JVM, () -> {
         return (double)((float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0F);
      }));
      builder.addAll(MetricsRegistry.f_146067_.m_146071_());
      return builder.build();
   }

   public Set<MetricSampler> m_142531_(Supplier<ProfileCollector> p_146191_) {
      this.f_146177_.addAll(this.f_146178_.m_146163_(p_146191_));
      return this.f_146177_;
   }

   public static MetricSampler m_146188_(final LongSupplier p_146189_) {
      Stopwatch stopwatch = Stopwatch.createUnstarted(new Ticker() {
         public long read() {
            return p_146189_.getAsLong();
         }
      });
      ToDoubleFunction<Stopwatch> todoublefunction = (p_146187_) -> {
         if (p_146187_.isRunning()) {
            p_146187_.stop();
         }

         long i = p_146187_.elapsed(TimeUnit.NANOSECONDS);
         p_146187_.reset();
         return (double)i;
      };
      MetricSampler.ValueIncreasedByPercentage metricsampler$valueincreasedbypercentage = new MetricSampler.ValueIncreasedByPercentage(2.0F);
      return MetricSampler.m_146013_("ticktime", MetricCategory.TICK_LOOP, todoublefunction, stopwatch).m_146042_(Stopwatch::start).m_146040_(metricsampler$valueincreasedbypercentage).m_146039_();
   }

   static class CpuStats {
      private final SystemInfo f_146201_ = new SystemInfo();
      private final CentralProcessor f_146202_ = this.f_146201_.getHardware().getProcessor();
      public final int f_146200_ = this.f_146202_.getLogicalProcessorCount();
      private long[][] f_146203_ = this.f_146202_.getProcessorCpuLoadTicks();
      private double[] f_146204_ = this.f_146202_.getProcessorCpuLoadBetweenTicks(this.f_146203_);
      private long f_146205_;

      public double m_146207_(int p_146208_) {
         long i = System.currentTimeMillis();
         if (this.f_146205_ == 0L || this.f_146205_ + 501L < i) {
            this.f_146204_ = this.f_146202_.getProcessorCpuLoadBetweenTicks(this.f_146203_);
            this.f_146203_ = this.f_146202_.getProcessorCpuLoadTicks();
            this.f_146205_ = i;
         }

         return this.f_146204_[p_146208_] * 100.0D;
      }
   }
}