package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.Bootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGenerator {
   private static final Logger f_123905_ = LogManager.getLogger();
   private final Collection<Path> f_123906_;
   private final Path f_123907_;
   private final List<DataProvider> f_123908_ = Lists.newArrayList();
   private final List<DataProvider> providerView = java.util.Collections.unmodifiableList(f_123908_);

   public DataGenerator(Path p_123911_, Collection<Path> p_123912_) {
      this.f_123907_ = p_123911_;
      this.f_123906_ = Lists.newArrayList(p_123912_);
   }

   public Collection<Path> m_123913_() {
      return this.f_123906_;
   }

   public Path m_123916_() {
      return this.f_123907_;
   }

   public void m_123917_() throws IOException {
      HashCache hashcache = new HashCache(this.f_123907_, "cache");
      hashcache.m_123952_(this.m_123916_().resolve("version.json"));
      Stopwatch stopwatch = Stopwatch.createStarted();
      Stopwatch stopwatch1 = Stopwatch.createUnstarted();

      for(DataProvider dataprovider : this.f_123908_) {
         f_123905_.info("Starting provider: {}", (Object)dataprovider.m_6055_());
         net.minecraftforge.fml.StartupMessageManager.addModMessage("Generating: " + dataprovider.m_6055_());
         stopwatch1.start();
         dataprovider.m_6865_(hashcache);
         stopwatch1.stop();
         f_123905_.info("{} finished after {} ms", dataprovider.m_6055_(), stopwatch1.elapsed(TimeUnit.MILLISECONDS));
         stopwatch1.reset();
      }

      f_123905_.info("All providers took: {} ms", (long)stopwatch.elapsed(TimeUnit.MILLISECONDS));
      hashcache.m_123937_();
   }

   public void m_123914_(DataProvider p_123915_) {
      this.f_123908_.add(p_123915_);
   }

   public List<DataProvider> getProviders() {
       return this.providerView;
   }

   public void addInput(Path value) {
      this.f_123906_.add(value);
   }

   static {
      Bootstrap.m_135870_();
   }
}
