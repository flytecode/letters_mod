package net.minecraft.util.profiling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActiveProfiler implements ProfileCollector {
   private static final long f_18368_ = Duration.ofMillis(100L).toNanos();
   private static final Logger f_18369_ = LogManager.getLogger();
   private final List<String> f_18370_ = Lists.newArrayList();
   private final LongList f_18371_ = new LongArrayList();
   private final Map<String, ActiveProfiler.PathEntry> f_18372_ = Maps.newHashMap();
   private final IntSupplier f_18373_;
   private final LongSupplier f_18374_;
   private final long f_18375_;
   private final int f_18376_;
   private String f_18377_ = "";
   private boolean f_18378_;
   @Nullable
   private ActiveProfiler.PathEntry f_18379_;
   private final boolean f_18380_;
   private final Set<Pair<String, MetricCategory>> f_145926_ = new ObjectArraySet<>();

   public ActiveProfiler(LongSupplier p_18383_, IntSupplier p_18384_, boolean p_18385_) {
      this.f_18375_ = p_18383_.getAsLong();
      this.f_18374_ = p_18383_;
      this.f_18376_ = p_18384_.getAsInt();
      this.f_18373_ = p_18384_;
      this.f_18380_ = p_18385_;
   }

   public void m_7242_() {
      if (this.f_18378_) {
         f_18369_.error("Profiler tick already started - missing endTick()?");
      } else {
         this.f_18378_ = true;
         this.f_18377_ = "";
         this.f_18370_.clear();
         this.m_6180_("root");
      }
   }

   public void m_7241_() {
      if (!this.f_18378_) {
         f_18369_.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.m_7238_();
         this.f_18378_ = false;
         if (!this.f_18377_.isEmpty()) {
            f_18369_.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", () -> {
               return ProfileResults.m_18575_(this.f_18377_);
            });
         }

      }
   }

   public void m_6180_(String p_18390_) {
      if (!this.f_18378_) {
         f_18369_.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", (Object)p_18390_);
      } else {
         if (!this.f_18377_.isEmpty()) {
            this.f_18377_ = this.f_18377_ + "\u001e";
         }

         this.f_18377_ = this.f_18377_ + p_18390_;
         this.f_18370_.add(this.f_18377_);
         this.f_18371_.add(Util.m_137569_());
         this.f_18379_ = null;
      }
   }

   public void m_6521_(Supplier<String> p_18392_) {
      this.m_6180_(p_18392_.get());
   }

   public void m_142259_(MetricCategory p_145928_) {
      this.f_145926_.add(Pair.of(this.f_18377_, p_145928_));
   }

   public void m_7238_() {
      if (!this.f_18378_) {
         f_18369_.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
      } else if (this.f_18371_.isEmpty()) {
         f_18369_.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long i = Util.m_137569_();
         long j = this.f_18371_.removeLong(this.f_18371_.size() - 1);
         this.f_18370_.remove(this.f_18370_.size() - 1);
         long k = i - j;
         ActiveProfiler.PathEntry activeprofiler$pathentry = this.m_18406_();
         activeprofiler$pathentry.f_145934_ += k;
         ++activeprofiler$pathentry.f_18410_;
         activeprofiler$pathentry.f_145932_ = Math.max(activeprofiler$pathentry.f_145932_, k);
         activeprofiler$pathentry.f_145933_ = Math.min(activeprofiler$pathentry.f_145933_, k);
         if (this.f_18380_ && k > f_18368_) {
            f_18369_.warn("Something's taking too long! '{}' took aprox {} ms", () -> {
               return ProfileResults.m_18575_(this.f_18377_);
            }, () -> {
               return (double)k / 1000000.0D;
            });
         }

         this.f_18377_ = this.f_18370_.isEmpty() ? "" : this.f_18370_.get(this.f_18370_.size() - 1);
         this.f_18379_ = null;
      }
   }

   public void m_6182_(String p_18395_) {
      this.m_7238_();
      this.m_6180_(p_18395_);
   }

   public void m_6523_(Supplier<String> p_18397_) {
      this.m_7238_();
      this.m_6521_(p_18397_);
   }

   private ActiveProfiler.PathEntry m_18406_() {
      if (this.f_18379_ == null) {
         this.f_18379_ = this.f_18372_.computeIfAbsent(this.f_18377_, (p_18405_) -> {
            return new ActiveProfiler.PathEntry();
         });
      }

      return this.f_18379_;
   }

   public void m_6174_(String p_18400_) {
      this.m_18406_().f_18411_.addTo(p_18400_, 1L);
   }

   public void m_6525_(Supplier<String> p_18402_) {
      this.m_18406_().f_18411_.addTo(p_18402_.get(), 1L);
   }

   public ProfileResults m_5948_() {
      return new FilledProfileResults(this.f_18372_, this.f_18375_, this.f_18376_, this.f_18374_.getAsLong(), this.f_18373_.getAsInt());
   }

   @Nullable
   public ActiveProfiler.PathEntry m_142431_(String p_145930_) {
      return this.f_18372_.get(p_145930_);
   }

   public Set<Pair<String, MetricCategory>> m_142579_() {
      return this.f_145926_;
   }

   public static class PathEntry implements ProfilerPathEntry {
      long f_145932_ = Long.MIN_VALUE;
      long f_145933_ = Long.MAX_VALUE;
      long f_145934_;
      long f_18410_;
      final Object2LongOpenHashMap<String> f_18411_ = new Object2LongOpenHashMap<>();

      public long m_7235_() {
         return this.f_145934_;
      }

      public long m_142752_() {
         return this.f_145932_;
      }

      public long m_7234_() {
         return this.f_18410_;
      }

      public Object2LongMap<String> m_7446_() {
         return Object2LongMaps.unmodifiable(this.f_18411_);
      }
   }
}