package net.minecraft.util.profiling.metrics.storage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricsPersister {
   public static final Path f_146209_ = Paths.get("debug/profiling");
   public static final String f_146210_ = "metrics";
   public static final String f_146211_ = "deviations";
   public static final String f_146212_ = "profiling.txt";
   private static final Logger f_146213_ = LogManager.getLogger();
   private final String f_146214_;

   public MetricsPersister(String p_146217_) {
      this.f_146214_ = p_146217_;
   }

   public Path m_146250_(Set<MetricSampler> p_146251_, Map<MetricSampler, List<RecordedDeviation>> p_146252_, ProfileResults p_146253_) {
      try {
         Files.createDirectories(f_146209_);
      } catch (IOException ioexception1) {
         throw new UncheckedIOException(ioexception1);
      }

      try {
         Path path = Files.createTempDirectory("minecraft-profiling");
         path.toFile().deleteOnExit();
         Files.createDirectories(f_146209_);
         Path path1 = path.resolve(this.f_146214_);
         Path path2 = path1.resolve("metrics");
         this.m_146247_(p_146251_, path2);
         if (!p_146252_.isEmpty()) {
            this.m_146244_(p_146252_, path1.resolve("deviations"));
         }

         this.m_146223_(p_146253_, path1);
         return path;
      } catch (IOException ioexception) {
         throw new UncheckedIOException(ioexception);
      }
   }

   private void m_146247_(Set<MetricSampler> p_146248_, Path p_146249_) {
      if (p_146248_.isEmpty()) {
         throw new IllegalArgumentException("Expected at least one sampler to persist");
      } else {
         Map<MetricCategory, List<MetricSampler>> map = p_146248_.stream().collect(Collectors.groupingBy(MetricSampler::m_146021_));
         map.forEach((p_146232_, p_146233_) -> {
            this.m_146226_(p_146232_, p_146233_, p_146249_);
         });
      }
   }

   private void m_146226_(MetricCategory p_146227_, List<MetricSampler> p_146228_, Path p_146229_) {
      Path path = p_146229_.resolve(Util.m_137483_(p_146227_.m_145981_(), ResourceLocation::m_135828_) + ".csv");
      Writer writer = null;

      try {
         Files.createDirectories(path.getParent());
         writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
         CsvOutput.Builder csvoutput$builder = CsvOutput.m_13619_();
         csvoutput$builder.m_13630_("@tick");

         for(MetricSampler metricsampler : p_146228_) {
            csvoutput$builder.m_13630_(metricsampler.m_146020_());
         }

         CsvOutput csvoutput = csvoutput$builder.m_13628_(writer);
         List<MetricSampler.SamplerResult> list = p_146228_.stream().map(MetricSampler::m_146024_).collect(Collectors.toList());
         int i = list.stream().mapToInt(MetricSampler.SamplerResult::m_146056_).summaryStatistics().getMin();
         int j = list.stream().mapToInt(MetricSampler.SamplerResult::m_146059_).summaryStatistics().getMax();

         for(int k = i; k <= j; ++k) {
            int l = k;
            Stream<String> stream = list.stream().map((p_146222_) -> {
               return String.valueOf(p_146222_.m_146057_(l));
            });
            Object[] aobject = Stream.concat(Stream.of(String.valueOf(k)), stream).toArray((p_146219_) -> {
               return new String[p_146219_];
            });
            csvoutput.m_13624_(aobject);
         }

         f_146213_.info("Flushed metrics to {}", (Object)path);
      } catch (Exception exception) {
         f_146213_.error("Could not save profiler results to {}", path, exception);
      } finally {
         IOUtils.closeQuietly(writer);
      }

   }

   private void m_146244_(Map<MetricSampler, List<RecordedDeviation>> p_146245_, Path p_146246_) {
      DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK).withZone(ZoneId.systemDefault());
      p_146245_.forEach((p_146242_, p_146243_) -> {
         p_146243_.forEach((p_146238_) -> {
            String s = datetimeformatter.format(p_146238_.f_146254_);
            Path path = p_146246_.resolve(Util.m_137483_(p_146242_.m_146020_(), ResourceLocation::m_135828_)).resolve(String.format(Locale.ROOT, "%d@%s.txt", p_146238_.f_146255_, s));
            p_146238_.f_146256_.m_142444_(path);
         });
      });
   }

   private void m_146223_(ProfileResults p_146224_, Path p_146225_) {
      p_146224_.m_142444_(p_146225_.resolve("profiling.txt"));
   }
}