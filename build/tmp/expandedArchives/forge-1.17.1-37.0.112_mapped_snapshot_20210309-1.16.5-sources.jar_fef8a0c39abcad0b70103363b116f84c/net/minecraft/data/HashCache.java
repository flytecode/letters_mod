package net.minecraft.data;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashCache {
   private static final Logger f_123926_ = LogManager.getLogger();
   private final Path f_123927_;
   private final Path f_123928_;
   private int f_123929_;
   private final Map<Path, String> f_123930_ = Maps.newHashMap();
   private final Map<Path, String> f_123931_ = Maps.newHashMap();
   private final Set<Path> f_123932_ = Sets.newHashSet();

   public HashCache(Path p_123935_, String p_123936_) throws IOException {
      this.f_123927_ = p_123935_;
      Path path = p_123935_.resolve(".cache");
      Files.createDirectories(path);
      this.f_123928_ = path.resolve(p_123936_);
      this.m_123951_().forEach((p_123959_) -> {
         this.f_123930_.put(p_123959_, "");
      });
      if (Files.isReadable(this.f_123928_)) {
         IOUtils.readLines(Files.newInputStream(this.f_123928_), Charsets.UTF_8).forEach((p_123950_) -> {
            int i = p_123950_.indexOf(32);
            this.f_123930_.put(p_123935_.resolve(p_123950_.substring(i + 1)), p_123950_.substring(0, i));
         });
      }

   }

   public void m_123937_() throws IOException {
      this.m_123945_();

      Writer writer;
      try {
         writer = Files.newBufferedWriter(this.f_123928_);
      } catch (IOException ioexception) {
         f_123926_.warn("Unable write cachefile {}: {}", this.f_123928_, ioexception.toString());
         return;
      }

      IOUtils.writeLines(this.f_123931_.entrySet().stream().map((p_123944_) -> {
         return (String)p_123944_.getValue() + ' ' + this.f_123927_.relativize(p_123944_.getKey()).toString().replace('\\', '/'); //Forge: Standardize file paths.
      }).sorted(java.util.Comparator.comparing(a -> a.split(" ")[1])).collect(Collectors.toList()), System.lineSeparator(), writer);
      writer.close();
      f_123926_.debug("Caching: cache hits: {}, created: {} removed: {}", this.f_123929_, this.f_123931_.size() - this.f_123929_, this.f_123930_.size());
   }

   @Nullable
   public String m_123938_(Path p_123939_) {
      return this.f_123930_.get(p_123939_);
   }

   public void m_123940_(Path p_123941_, String p_123942_) {
      this.f_123931_.put(p_123941_, p_123942_);
      if (Objects.equals(this.f_123930_.remove(p_123941_), p_123942_)) {
         ++this.f_123929_;
      }

   }

   public boolean m_123946_(Path p_123947_) {
      return this.f_123930_.containsKey(p_123947_);
   }

   public void m_123952_(Path p_123953_) {
      this.f_123932_.add(p_123953_);
   }

   private void m_123945_() throws IOException {
      this.m_123951_().forEach((p_123957_) -> {
         if (this.m_123946_(p_123957_) && !this.f_123932_.contains(p_123957_)) {
            try {
               Files.delete(p_123957_);
            } catch (IOException ioexception) {
               f_123926_.debug("Unable to delete: {} ({})", p_123957_, ioexception.toString());
            }
         }

      });
   }

   private Stream<Path> m_123951_() throws IOException {
      return Files.walk(this.f_123927_).filter((p_123955_) -> {
         return !Objects.equals(this.f_123928_, p_123955_) && !Files.isDirectory(p_123955_);
      });
   }
}
