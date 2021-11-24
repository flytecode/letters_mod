package net.minecraft.util.profiling;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilledProfileResults implements ProfileResults {
   private static final Logger f_18452_ = LogManager.getLogger();
   private static final ProfilerPathEntry f_18453_ = new ProfilerPathEntry() {
      public long m_7235_() {
         return 0L;
      }

      public long m_142752_() {
         return 0L;
      }

      public long m_7234_() {
         return 0L;
      }

      public Object2LongMap<String> m_7446_() {
         return Object2LongMaps.emptyMap();
      }
   };
   private static final Splitter f_18454_ = Splitter.on('\u001e');
   private static final Comparator<Entry<String, FilledProfileResults.CounterCollector>> f_18455_ = Entry.<String, FilledProfileResults.CounterCollector>comparingByValue(Comparator.comparingLong((p_18489_) -> {
      return p_18489_.f_18538_;
   })).reversed();
   private final Map<String, ? extends ProfilerPathEntry> f_18456_;
   private final long f_18457_;
   private final int f_18458_;
   private final long f_18459_;
   private final int f_18460_;
   private final int f_18461_;

   public FilledProfileResults(Map<String, ? extends ProfilerPathEntry> p_18464_, long p_18465_, int p_18466_, long p_18467_, int p_18468_) {
      this.f_18456_ = p_18464_;
      this.f_18457_ = p_18465_;
      this.f_18458_ = p_18466_;
      this.f_18459_ = p_18467_;
      this.f_18460_ = p_18468_;
      this.f_18461_ = p_18468_ - p_18466_;
   }

   private ProfilerPathEntry m_18525_(String p_18526_) {
      ProfilerPathEntry profilerpathentry = this.f_18456_.get(p_18526_);
      return profilerpathentry != null ? profilerpathentry : f_18453_;
   }

   public List<ResultField> m_6412_(String p_18493_) {
      String s = p_18493_;
      ProfilerPathEntry profilerpathentry = this.m_18525_("root");
      long i = profilerpathentry.m_7235_();
      ProfilerPathEntry profilerpathentry1 = this.m_18525_(p_18493_);
      long j = profilerpathentry1.m_7235_();
      long k = profilerpathentry1.m_7234_();
      List<ResultField> list = Lists.newArrayList();
      if (!p_18493_.isEmpty()) {
         p_18493_ = p_18493_ + "\u001e";
      }

      long l = 0L;

      for(String s1 : this.f_18456_.keySet()) {
         if (m_18494_(p_18493_, s1)) {
            l += this.m_18525_(s1).m_7235_();
         }
      }

      float f = (float)l;
      if (l < j) {
         l = j;
      }

      if (i < l) {
         i = l;
      }

      for(String s2 : this.f_18456_.keySet()) {
         if (m_18494_(p_18493_, s2)) {
            ProfilerPathEntry profilerpathentry2 = this.m_18525_(s2);
            long i1 = profilerpathentry2.m_7235_();
            double d0 = (double)i1 * 100.0D / (double)l;
            double d1 = (double)i1 * 100.0D / (double)i;
            String s3 = s2.substring(p_18493_.length());
            list.add(new ResultField(s3, d0, d1, profilerpathentry2.m_7234_()));
         }
      }

      if ((float)l > f) {
         list.add(new ResultField("unspecified", (double)((float)l - f) * 100.0D / (double)l, (double)((float)l - f) * 100.0D / (double)i, k));
      }

      Collections.sort(list);
      list.add(0, new ResultField(s, 100.0D, (double)l * 100.0D / (double)i, k));
      return list;
   }

   private static boolean m_18494_(String p_18495_, String p_18496_) {
      return p_18496_.length() > p_18495_.length() && p_18496_.startsWith(p_18495_) && p_18496_.indexOf(30, p_18495_.length() + 1) < 0;
   }

   private Map<String, FilledProfileResults.CounterCollector> m_18531_() {
      Map<String, FilledProfileResults.CounterCollector> map = Maps.newTreeMap();
      this.f_18456_.forEach((p_18512_, p_18513_) -> {
         Object2LongMap<String> object2longmap = p_18513_.m_7446_();
         if (!object2longmap.isEmpty()) {
            List<String> list = f_18454_.splitToList(p_18512_);
            object2longmap.forEach((p_145944_, p_145945_) -> {
               map.computeIfAbsent(p_145944_, (p_145947_) -> {
                  return new FilledProfileResults.CounterCollector();
               }).m_18547_(list.iterator(), p_145945_);
            });
         }

      });
      return map;
   }

   public long m_7229_() {
      return this.f_18457_;
   }

   public int m_7230_() {
      return this.f_18458_;
   }

   public long m_7236_() {
      return this.f_18459_;
   }

   public int m_7317_() {
      return this.f_18460_;
   }

   public boolean m_142444_(Path p_145940_) {
      Writer writer = null;

      boolean flag;
      try {
         Files.createDirectories(p_145940_.getParent());
         writer = Files.newBufferedWriter(p_145940_, StandardCharsets.UTF_8);
         writer.write(this.m_18485_(this.m_18577_(), this.m_7315_()));
         return true;
      } catch (Throwable throwable) {
         f_18452_.error("Could not save profiler results to {}", p_145940_, throwable);
         flag = false;
      } finally {
         IOUtils.closeQuietly(writer);
      }

      return flag;
   }

   protected String m_18485_(long p_18486_, int p_18487_) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Profiler Results ----\n");
      stringbuilder.append("// ");
      stringbuilder.append(m_18532_());
      stringbuilder.append("\n\n");
      stringbuilder.append("Version: ").append(SharedConstants.m_136187_().getId()).append('\n');
      stringbuilder.append("Time span: ").append(p_18486_ / 1000000L).append(" ms\n");
      stringbuilder.append("Tick span: ").append(p_18487_).append(" ticks\n");
      stringbuilder.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", (float)p_18487_ / ((float)p_18486_ / 1.0E9F))).append(" ticks per second. It should be ").append((int)20).append(" ticks per second\n\n");
      stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.m_18481_(0, "root", stringbuilder);
      stringbuilder.append("--- END PROFILE DUMP ---\n\n");
      Map<String, FilledProfileResults.CounterCollector> map = this.m_18531_();
      if (!map.isEmpty()) {
         stringbuilder.append("--- BEGIN COUNTER DUMP ---\n\n");
         this.m_18514_(map, stringbuilder, p_18487_);
         stringbuilder.append("--- END COUNTER DUMP ---\n\n");
      }

      return stringbuilder.toString();
   }

   public String m_142368_() {
      StringBuilder stringbuilder = new StringBuilder();
      this.m_18481_(0, "root", stringbuilder);
      return stringbuilder.toString();
   }

   private static StringBuilder m_18497_(StringBuilder p_18498_, int p_18499_) {
      p_18498_.append(String.format("[%02d] ", p_18499_));

      for(int i = 0; i < p_18499_; ++i) {
         p_18498_.append("|   ");
      }

      return p_18498_;
   }

   private void m_18481_(int p_18482_, String p_18483_, StringBuilder p_18484_) {
      List<ResultField> list = this.m_6412_(p_18483_);
      Object2LongMap<String> object2longmap = ObjectUtils.firstNonNull(this.f_18456_.get(p_18483_), f_18453_).m_7446_();
      object2longmap.forEach((p_18508_, p_18509_) -> {
         m_18497_(p_18484_, p_18482_).append('#').append(p_18508_).append(' ').append((Object)p_18509_).append('/').append(p_18509_ / (long)this.f_18461_).append('\n');
      });
      if (list.size() >= 3) {
         for(int i = 1; i < list.size(); ++i) {
            ResultField resultfield = list.get(i);
            m_18497_(p_18484_, p_18482_).append(resultfield.f_18610_).append('(').append(resultfield.f_18609_).append('/').append(String.format(Locale.ROOT, "%.0f", (float)resultfield.f_18609_ / (float)this.f_18461_)).append(')').append(" - ").append(String.format(Locale.ROOT, "%.2f", resultfield.f_18607_)).append("%/").append(String.format(Locale.ROOT, "%.2f", resultfield.f_18608_)).append("%\n");
            if (!"unspecified".equals(resultfield.f_18610_)) {
               try {
                  this.m_18481_(p_18482_ + 1, p_18483_ + "\u001e" + resultfield.f_18610_, p_18484_);
               } catch (Exception exception) {
                  p_18484_.append("[[ EXCEPTION ").append((Object)exception).append(" ]]");
               }
            }
         }

      }
   }

   private void m_18475_(int p_18476_, String p_18477_, FilledProfileResults.CounterCollector p_18478_, int p_18479_, StringBuilder p_18480_) {
      m_18497_(p_18480_, p_18476_).append(p_18477_).append(" total:").append(p_18478_.f_18537_).append('/').append(p_18478_.f_18538_).append(" average: ").append(p_18478_.f_18537_ / (long)p_18479_).append('/').append(p_18478_.f_18538_ / (long)p_18479_).append('\n');
      p_18478_.f_18539_.entrySet().stream().sorted(f_18455_).forEach((p_18474_) -> {
         this.m_18475_(p_18476_ + 1, p_18474_.getKey(), p_18474_.getValue(), p_18479_, p_18480_);
      });
   }

   private void m_18514_(Map<String, FilledProfileResults.CounterCollector> p_18515_, StringBuilder p_18516_, int p_18517_) {
      p_18515_.forEach((p_18503_, p_18504_) -> {
         p_18516_.append("-- Counter: ").append(p_18503_).append(" --\n");
         this.m_18475_(0, "root", p_18504_.f_18539_.get("root"), p_18517_, p_18516_);
         p_18516_.append("\n\n");
      });
   }

   private static String m_18532_() {
      String[] astring = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};

      try {
         return astring[(int)(Util.m_137569_() % (long)astring.length)];
      } catch (Throwable throwable) {
         return "Witty comment unavailable :(";
      }
   }

   public int m_7315_() {
      return this.f_18461_;
   }

   static class CounterCollector {
      long f_18537_;
      long f_18538_;
      final Map<String, FilledProfileResults.CounterCollector> f_18539_ = Maps.newHashMap();

      public void m_18547_(Iterator<String> p_18548_, long p_18549_) {
         this.f_18538_ += p_18549_;
         if (!p_18548_.hasNext()) {
            this.f_18537_ += p_18549_;
         } else {
            this.f_18539_.computeIfAbsent(p_18548_.next(), (p_18546_) -> {
               return new FilledProfileResults.CounterCollector();
            }).m_18547_(p_18548_, p_18549_);
         }

      }
   }
}