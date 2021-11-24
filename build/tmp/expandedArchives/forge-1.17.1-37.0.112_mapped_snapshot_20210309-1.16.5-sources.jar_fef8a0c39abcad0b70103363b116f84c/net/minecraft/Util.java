package net.minecraft;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
   private static final AtomicInteger f_137442_ = new AtomicInteger(1);
   private static final ExecutorService f_137443_ = m_137477_("Bootstrap");
   private static final ExecutorService f_137444_ = m_137477_("Main");
   private static final ExecutorService f_137445_ = m_137586_();
   public static LongSupplier f_137440_ = System::nanoTime;
   public static final UUID f_137441_ = new UUID(0L, 0L);
   public static final FileSystemProvider f_143778_ = FileSystemProvider.installedProviders().stream().filter((p_143794_) -> {
      return p_143794_.getScheme().equalsIgnoreCase("jar");
   }).findFirst().orElseThrow(() -> {
      return new IllegalStateException("No jar file system provider found");
   });
   static final Logger f_137446_ = LogManager.getLogger();

   public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> m_137448_() {
      return Collectors.toMap(Entry::getKey, Entry::getValue);
   }

   public static <T extends Comparable<T>> String m_137453_(Property<T> p_137454_, Object p_137455_) {
      return p_137454_.m_6940_((T)(p_137455_));
   }

   public static String m_137492_(String p_137493_, @Nullable ResourceLocation p_137494_) {
      return p_137494_ == null ? p_137493_ + ".unregistered_sadface" : p_137493_ + "." + p_137494_.m_135827_() + "." + p_137494_.m_135815_().replace('/', '.');
   }

   public static long m_137550_() {
      return m_137569_() / 1000000L;
   }

   public static long m_137569_() {
      return f_137440_.getAsLong();
   }

   public static long m_137574_() {
      return Instant.now().toEpochMilli();
   }

   private static ExecutorService m_137477_(String p_137478_) {
      int i = Mth.m_14045_(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
      ExecutorService executorservice;
      if (i <= 0) {
         executorservice = MoreExecutors.newDirectExecutorService();
      } else {
         executorservice = new ForkJoinPool(i, (p_143792_) -> {
            ForkJoinWorkerThread forkjoinworkerthread = new ForkJoinWorkerThread(p_143792_) {
               protected void onTermination(Throwable p_137590_) {
                  if (p_137590_ != null) {
                     Util.f_137446_.warn("{} died", this.getName(), p_137590_);
                  } else {
                     Util.f_137446_.debug("{} shutdown", (Object)this.getName());
                  }

                  super.onTermination(p_137590_);
               }
            };
            forkjoinworkerthread.setName("Worker-" + p_137478_ + "-" + f_137442_.getAndIncrement());
            return forkjoinworkerthread;
         }, Util::m_137495_, true);
      }

      return executorservice;
   }

   // We add this inner class to compensate for Mojang's missing inner class and allow us to obf subsequent anon inner classes correctly.
   @SuppressWarnings("unused") private static LongSupplier INNER_CLASS_SHIFT = new LongSupplier() { public long getAsLong() { return 0; } };

   public static Executor m_137577_() {
      return f_137443_;
   }

   public static Executor m_137578_() {
      return f_137444_;
   }

   public static Executor m_137579_() {
      return f_137445_;
   }

   public static void m_137580_() {
      m_137531_(f_137444_);
      m_137531_(f_137445_);
   }

   private static void m_137531_(ExecutorService p_137532_) {
      p_137532_.shutdown();

      boolean flag;
      try {
         flag = p_137532_.awaitTermination(3L, TimeUnit.SECONDS);
      } catch (InterruptedException interruptedexception) {
         flag = false;
      }

      if (!flag) {
         p_137532_.shutdownNow();
      }

   }

   private static ExecutorService m_137586_() {
      return Executors.newCachedThreadPool((p_143784_) -> {
         Thread thread = new Thread(p_143784_);
         thread.setName("IO-Worker-" + f_137442_.getAndIncrement());
         thread.setUncaughtExceptionHandler(Util::m_137495_);
         return thread;
      });
   }

   public static <T> CompletableFuture<T> m_137498_(Throwable p_137499_) {
      CompletableFuture<T> completablefuture = new CompletableFuture<>();
      completablefuture.completeExceptionally(p_137499_);
      return completablefuture;
   }

   public static void m_137559_(Throwable p_137560_) {
      throw p_137560_ instanceof RuntimeException ? (RuntimeException)p_137560_ : new RuntimeException(p_137560_);
   }

   private static void m_137495_(Thread p_137496_, Throwable p_137497_) {
      m_137570_(p_137497_);
      if (p_137497_ instanceof CompletionException) {
         p_137497_ = p_137497_.getCause();
      }

      if (p_137497_ instanceof ReportedException) {
         Bootstrap.m_135875_(((ReportedException)p_137497_).m_134761_().m_127526_());
         System.exit(-1);
      }

      f_137446_.error(String.format("Caught exception in thread %s", p_137496_), p_137497_);
   }

   @Nullable
   public static Type<?> m_137456_(TypeReference p_137457_, String p_137458_) {
      return !SharedConstants.f_136182_ ? null : m_137551_(p_137457_, p_137458_);
   }

   @Nullable
   private static Type<?> m_137551_(TypeReference p_137552_, String p_137553_) {
      Type<?> type = null;

      try {
         type = DataFixers.m_14512_().getSchema(DataFixUtils.makeKey(SharedConstants.m_136187_().getWorldVersion())).getChoiceType(p_137552_, p_137553_);
      } catch (IllegalArgumentException illegalargumentexception) {
         f_137446_.debug("No data fixer registered for {}", (Object)p_137553_);
         if (SharedConstants.f_136183_) {
            throw illegalargumentexception;
         }
      }

      return type;
   }

   public static Runnable m_143787_(String p_143788_, Runnable p_143789_) {
      return SharedConstants.f_136183_ ? () -> {
         Thread thread = Thread.currentThread();
         String s = thread.getName();
         thread.setName(p_143788_);

         try {
            p_143789_.run();
         } finally {
            thread.setName(s);
         }

      } : p_143789_;
   }

   public static Util.OS m_137581_() {
      String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
      if (s.contains("win")) {
         return Util.OS.WINDOWS;
      } else if (s.contains("mac")) {
         return Util.OS.OSX;
      } else if (s.contains("solaris")) {
         return Util.OS.SOLARIS;
      } else if (s.contains("sunos")) {
         return Util.OS.SOLARIS;
      } else if (s.contains("linux")) {
         return Util.OS.LINUX;
      } else {
         return s.contains("unix") ? Util.OS.LINUX : Util.OS.UNKNOWN;
      }
   }

   public static Stream<String> m_137582_() {
      RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
      return runtimemxbean.getInputArguments().stream().filter((p_143839_) -> {
         return p_143839_.startsWith("-X");
      });
   }

   public static <T> T m_137509_(List<T> p_137510_) {
      return p_137510_.get(p_137510_.size() - 1);
   }

   public static <T> T m_137466_(Iterable<T> p_137467_, @Nullable T p_137468_) {
      Iterator<T> iterator = p_137467_.iterator();
      T t = iterator.next();
      if (p_137468_ != null) {
         T t1 = t;

         while(t1 != p_137468_) {
            if (iterator.hasNext()) {
               t1 = iterator.next();
            }
         }

         if (iterator.hasNext()) {
            return iterator.next();
         }
      }

      return t;
   }

   public static <T> T m_137554_(Iterable<T> p_137555_, @Nullable T p_137556_) {
      Iterator<T> iterator = p_137555_.iterator();

      T t;
      T t1;
      for(t = null; iterator.hasNext(); t = t1) {
         t1 = iterator.next();
         if (t1 == p_137556_) {
            if (t == null) {
               t = (T)(iterator.hasNext() ? Iterators.getLast(iterator) : p_137556_);
            }
            break;
         }
      }

      return t;
   }

   public static <T> T m_137537_(Supplier<T> p_137538_) {
      return p_137538_.get();
   }

   public static <T> T m_137469_(T p_137470_, Consumer<T> p_137471_) {
      p_137471_.accept(p_137470_);
      return p_137470_;
   }

   public static <K> Strategy<K> m_137583_() {
      return (Strategy<K>) Util.IdentityStrategy.INSTANCE;
   }

   public static <V> CompletableFuture<List<V>> m_137567_(List<? extends CompletableFuture<? extends V>> p_137568_) {
      return p_137568_.stream().reduce(CompletableFuture.completedFuture(Lists.newArrayList()), (p_143836_, p_143837_) -> {
         return p_143837_.thenCombine(p_143836_, (p_143781_, p_143782_) -> {
            List<V> list = Lists.newArrayListWithCapacity(p_143782_.size() + 1);
            list.addAll(p_143782_);
            list.add(p_143781_);
            return list;
         });
      }, (p_143819_, p_143820_) -> {
         return p_143819_.thenCombine(p_143820_, (p_143802_, p_143803_) -> {
            List<V> list = Lists.newArrayListWithCapacity(p_143802_.size() + p_143803_.size());
            list.addAll(p_143802_);
            list.addAll(p_143803_);
            return list;
         });
      });
   }

   public static <V> CompletableFuture<List<V>> m_143840_(List<? extends CompletableFuture<? extends V>> p_143841_) {
      List<V> list = Lists.newArrayListWithCapacity(p_143841_.size());
      CompletableFuture<?>[] completablefuture = new CompletableFuture[p_143841_.size()];
      CompletableFuture<Void> completablefuture1 = new CompletableFuture<>();
      p_143841_.forEach((p_143811_) -> {
         int i = list.size();
         list.add((V)null);
         completablefuture[i] = p_143811_.whenComplete((p_143816_, p_143817_) -> {
            if (p_143817_ != null) {
               completablefuture1.completeExceptionally(p_143817_);
            } else {
               list.set(i, p_143816_);
            }

         });
      });
      return CompletableFuture.allOf(completablefuture).applyToEither(completablefuture1, (p_143800_) -> {
         return list;
      });
   }

   public static <T> Stream<T> m_137519_(Optional<? extends T> p_137520_) {
      return DataFixUtils.orElseGet(p_137520_.map(Stream::of), Stream::empty);
   }

   public static <T> Optional<T> m_137521_(Optional<T> p_137522_, Consumer<T> p_137523_, Runnable p_137524_) {
      if (p_137522_.isPresent()) {
         p_137523_.accept(p_137522_.get());
      } else {
         p_137524_.run();
      }

      return p_137522_;
   }

   public static Runnable m_137474_(Runnable p_137475_, Supplier<String> p_137476_) {
      return p_137475_;
   }

   public static void m_143785_(String p_143786_) {
      f_137446_.error(p_143786_);
      if (SharedConstants.f_136183_) {
         m_143842_();
      }

   }

   public static <T extends Throwable> T m_137570_(T p_137571_) {
      if (SharedConstants.f_136183_) {
         f_137446_.error("Trying to throw a fatal exception, pausing in IDE", p_137571_);
         m_143842_();
      }

      return p_137571_;
   }

   private static void m_143842_() {
      while(true) {
         try {
            Thread.sleep(1000L);
            f_137446_.error("paused");
         } catch (InterruptedException interruptedexception) {
            return;
         }
      }
   }

   public static String m_137575_(Throwable p_137576_) {
      if (p_137576_.getCause() != null) {
         return m_137575_(p_137576_.getCause());
      } else {
         return p_137576_.getMessage() != null ? p_137576_.getMessage() : p_137576_.toString();
      }
   }

   public static <T> T m_137545_(T[] p_137546_, Random p_137547_) {
      return p_137546_[p_137547_.nextInt(p_137546_.length)];
   }

   public static int m_137542_(int[] p_137543_, Random p_137544_) {
      return p_137543_[p_137544_.nextInt(p_137543_.length)];
   }

   public static <T> T m_143804_(List<T> p_143805_, Random p_143806_) {
      return p_143805_.get(p_143806_.nextInt(p_143805_.size()));
   }

   private static BooleanSupplier m_137502_(final Path p_137503_, final Path p_137504_) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.move(p_137503_, p_137504_);
               return true;
            } catch (IOException ioexception) {
               Util.f_137446_.error("Failed to rename", (Throwable)ioexception);
               return false;
            }
         }

         public String toString() {
            return "rename " + p_137503_ + " to " + p_137504_;
         }
      };
   }

   private static BooleanSupplier m_137500_(final Path p_137501_) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            try {
               Files.deleteIfExists(p_137501_);
               return true;
            } catch (IOException ioexception) {
               Util.f_137446_.warn("Failed to delete", (Throwable)ioexception);
               return false;
            }
         }

         public String toString() {
            return "delete old " + p_137501_;
         }
      };
   }

   private static BooleanSupplier m_137561_(final Path p_137562_) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return !Files.exists(p_137562_);
         }

         public String toString() {
            return "verify that " + p_137562_ + " is deleted";
         }
      };
   }

   private static BooleanSupplier m_137572_(final Path p_137573_) {
      return new BooleanSupplier() {
         public boolean getAsBoolean() {
            return Files.isRegularFile(p_137573_);
         }

         public String toString() {
            return "verify that " + p_137573_ + " is present";
         }
      };
   }

   private static boolean m_137548_(BooleanSupplier... p_137549_) {
      for(BooleanSupplier booleansupplier : p_137549_) {
         if (!booleansupplier.getAsBoolean()) {
            f_137446_.warn("Failed to execute {}", (Object)booleansupplier);
            return false;
         }
      }

      return true;
   }

   private static boolean m_137449_(int p_137450_, String p_137451_, BooleanSupplier... p_137452_) {
      for(int i = 0; i < p_137450_; ++i) {
         if (m_137548_(p_137452_)) {
            return true;
         }

         f_137446_.error("Failed to {}, retrying {}/{}", p_137451_, i, p_137450_);
      }

      f_137446_.error("Failed to {}, aborting, progress might be lost", (Object)p_137451_);
      return false;
   }

   public static void m_137462_(File p_137463_, File p_137464_, File p_137465_) {
      m_137505_(p_137463_.toPath(), p_137464_.toPath(), p_137465_.toPath());
   }

   public static void m_137505_(Path p_137506_, Path p_137507_, Path p_137508_) {
      int i = 10;
      if (!Files.exists(p_137506_) || m_137449_(10, "create backup " + p_137508_, m_137500_(p_137508_), m_137502_(p_137506_, p_137508_), m_137572_(p_137508_))) {
         if (m_137449_(10, "remove old " + p_137506_, m_137500_(p_137506_), m_137561_(p_137506_))) {
            if (!m_137449_(10, "replace " + p_137506_ + " with " + p_137507_, m_137502_(p_137507_, p_137506_), m_137572_(p_137506_))) {
               m_137449_(10, "restore " + p_137506_ + " from " + p_137508_, m_137502_(p_137508_, p_137506_), m_137572_(p_137506_));
            }

         }
      }
   }

   public static int m_137479_(String p_137480_, int p_137481_, int p_137482_) {
      int i = p_137480_.length();
      if (p_137482_ >= 0) {
         for(int j = 0; p_137481_ < i && j < p_137482_; ++j) {
            if (Character.isHighSurrogate(p_137480_.charAt(p_137481_++)) && p_137481_ < i && Character.isLowSurrogate(p_137480_.charAt(p_137481_))) {
               ++p_137481_;
            }
         }
      } else {
         for(int k = p_137482_; p_137481_ > 0 && k < 0; ++k) {
            --p_137481_;
            if (Character.isLowSurrogate(p_137480_.charAt(p_137481_)) && p_137481_ > 0 && Character.isHighSurrogate(p_137480_.charAt(p_137481_ - 1))) {
               --p_137481_;
            }
         }
      }

      return p_137481_;
   }

   public static Consumer<String> m_137489_(String p_137490_, Consumer<String> p_137491_) {
      return (p_143826_) -> {
         p_137491_.accept(p_137490_ + p_143826_);
      };
   }

   public static DataResult<int[]> m_137539_(IntStream p_137540_, int p_137541_) {
      int[] aint = p_137540_.limit((long)(p_137541_ + 1)).toArray();
      if (aint.length != p_137541_) {
         String s = "Input is not a list of " + p_137541_ + " ints";
         return aint.length >= p_137541_ ? DataResult.error(s, Arrays.copyOf(aint, p_137541_)) : DataResult.error(s);
      } else {
         return DataResult.success(aint);
      }
   }

   public static <T> DataResult<List<T>> m_143795_(List<T> p_143796_, int p_143797_) {
      if (p_143796_.size() != p_143797_) {
         String s = "Input is not a list of " + p_143797_ + " elements";
         return p_143796_.size() >= p_143797_ ? DataResult.error(s, p_143796_.subList(0, p_143797_)) : DataResult.error(s);
      } else {
         return DataResult.success(p_143796_);
      }
   }

   public static void m_137584_() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException interruptedexception) {
                  Util.f_137446_.warn("Timer hack thread interrupted, that really should not happen");
                  return;
               }
            }
         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_137446_));
      thread.start();
   }

   public static void m_137563_(Path p_137564_, Path p_137565_, Path p_137566_) throws IOException {
      Path path = p_137564_.relativize(p_137566_);
      Path path1 = p_137565_.resolve(path);
      Files.copy(p_137566_, path1);
   }

   public static String m_137483_(String p_137484_, CharPredicate p_137485_) {
      return p_137484_.toLowerCase(Locale.ROOT).chars().mapToObj((p_143831_) -> {
         return p_137485_.m_125854_((char)p_143831_) ? Character.toString((char)p_143831_) : "_";
      }).collect(Collectors.joining());
   }

   public static <T, R> Function<T, R> m_143827_(final Function<T, R> p_143828_) {
      return new Function<T, R>() {
         private final Map<T, R> f_143845_ = Maps.newHashMap();

         public R apply(T p_143849_) {
            return this.f_143845_.computeIfAbsent(p_143849_, p_143828_);
         }

         public String toString() {
            return "memoize/1[function=" + p_143828_ + ", size=" + this.f_143845_.size() + "]";
         }
      };
   }

   public static <T, U, R> BiFunction<T, U, R> m_143821_(final BiFunction<T, U, R> p_143822_) {
      return new BiFunction<T, U, R>() {
         private final Map<Pair<T, U>, R> f_143852_ = Maps.newHashMap();

         public R apply(T p_143859_, U p_143860_) {
            return this.f_143852_.computeIfAbsent(Pair.of(p_143859_, p_143860_), (p_143857_) -> {
               return p_143822_.apply(p_143857_.getFirst(), p_143857_.getSecond());
            });
         }

         public String toString() {
            return "memoize/2[function=" + p_143822_ + ", size=" + this.f_143852_.size() + "]";
         }
      };
   }

   static enum IdentityStrategy implements Strategy<Object> {
      INSTANCE;

      public int hashCode(Object p_137626_) {
         return System.identityHashCode(p_137626_);
      }

      public boolean equals(Object p_137623_, Object p_137624_) {
         return p_137623_ == p_137624_;
      }
   }

   public static enum OS {
      LINUX,
      SOLARIS,
      WINDOWS {
         protected String[] m_6868_(URL p_137662_) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", p_137662_.toString()};
         }
      },
      OSX {
         protected String[] m_6868_(URL p_137667_) {
            return new String[]{"open", p_137667_.toString()};
         }
      },
      UNKNOWN;

      public void m_137650_(URL p_137651_) {
         try {
            Process process = AccessController.doPrivileged((PrivilegedExceptionAction<Process>)(() -> {
               return Runtime.getRuntime().exec(this.m_6868_(p_137651_));
            }));

            for(String s : IOUtils.readLines(process.getErrorStream())) {
               Util.f_137446_.error(s);
            }

            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
         } catch (IOException | PrivilegedActionException privilegedactionexception) {
            Util.f_137446_.error("Couldn't open url '{}'", p_137651_, privilegedactionexception);
         }

      }

      public void m_137648_(URI p_137649_) {
         try {
            this.m_137650_(p_137649_.toURL());
         } catch (MalformedURLException malformedurlexception) {
            Util.f_137446_.error("Couldn't open uri '{}'", p_137649_, malformedurlexception);
         }

      }

      public void m_137644_(File p_137645_) {
         try {
            this.m_137650_(p_137645_.toURI().toURL());
         } catch (MalformedURLException malformedurlexception) {
            Util.f_137446_.error("Couldn't open file '{}'", p_137645_, malformedurlexception);
         }

      }

      protected String[] m_6868_(URL p_137652_) {
         String s = p_137652_.toString();
         if ("file".equals(p_137652_.getProtocol())) {
            s = s.replace("file:", "file://");
         }

         return new String[]{"xdg-open", s};
      }

      public void m_137646_(String p_137647_) {
         try {
            this.m_137650_((new URI(p_137647_)).toURL());
         } catch (MalformedURLException | IllegalArgumentException | URISyntaxException urisyntaxexception) {
            Util.f_137446_.error("Couldn't open uri '{}'", p_137647_, urisyntaxexception);
         }

      }
   }
}
