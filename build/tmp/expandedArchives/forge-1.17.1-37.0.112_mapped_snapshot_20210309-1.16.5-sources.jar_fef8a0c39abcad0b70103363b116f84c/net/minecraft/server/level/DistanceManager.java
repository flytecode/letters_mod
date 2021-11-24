package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DistanceManager {
   static final Logger f_140758_ = LogManager.getLogger();
   private static final int f_143205_ = 2;
   static final int f_140759_ = 33 + ChunkStatus.m_62370_(ChunkStatus.f_62326_) - 2;
   private static final int f_143206_ = 4;
   final Long2ObjectMap<ObjectSet<ServerPlayer>> f_140760_ = new Long2ObjectOpenHashMap<>();
   final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> f_140761_ = new Long2ObjectOpenHashMap<>();
   private final DistanceManager.ChunkTicketTracker f_140762_ = new DistanceManager.ChunkTicketTracker();
   private final DistanceManager.FixedPlayerDistanceChunkTracker f_140763_ = new DistanceManager.FixedPlayerDistanceChunkTracker(8);
   private final DistanceManager.PlayerTicketTracker f_140764_ = new DistanceManager.PlayerTicketTracker(33);
   final Set<ChunkHolder> f_140765_ = Sets.newHashSet();
   final ChunkTaskPriorityQueueSorter f_140766_;
   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140767_;
   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> f_140768_;
   final LongSet f_140769_ = new LongOpenHashSet();
   final Executor f_140770_;
   private long f_140771_;

   private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> forcedTickets = new Long2ObjectOpenHashMap<>();

   protected DistanceManager(Executor p_140774_, Executor p_140775_) {
      ProcessorHandle<Runnable> processorhandle = ProcessorHandle.m_18714_("player ticket throttler", p_140775_::execute);
      ChunkTaskPriorityQueueSorter chunktaskpriorityqueuesorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(processorhandle), p_140774_, 4);
      this.f_140766_ = chunktaskpriorityqueuesorter;
      this.f_140767_ = chunktaskpriorityqueuesorter.m_140604_(processorhandle, true);
      this.f_140768_ = chunktaskpriorityqueuesorter.m_140567_(processorhandle);
      this.f_140770_ = p_140775_;
   }

   protected void m_140776_() {
      ++this.f_140771_;
      ObjectIterator<Entry<SortedArraySet<Ticket<?>>>> objectiterator = this.f_140761_.long2ObjectEntrySet().fastIterator();

      while(objectiterator.hasNext()) {
         Entry<SortedArraySet<Ticket<?>>> entry = objectiterator.next();
         if (entry.getValue().removeIf((p_140822_) -> {
            return p_140822_.m_9434_(this.f_140771_);
         })) {
            this.f_140762_.m_140715_(entry.getLongKey(), m_140797_(entry.getValue()), false);
         }

         if (entry.getValue().isEmpty()) {
            objectiterator.remove();
         }
      }

   }

   private static int m_140797_(SortedArraySet<Ticket<?>> p_140798_) {
      return !p_140798_.isEmpty() ? p_140798_.m_14262_().m_9433_() : ChunkMap.f_140127_ + 1;
   }

   protected abstract boolean m_7009_(long p_140779_);

   @Nullable
   protected abstract ChunkHolder m_7316_(long p_140817_);

   @Nullable
   protected abstract ChunkHolder m_7288_(long p_140780_, int p_140781_, @Nullable ChunkHolder p_140782_, int p_140783_);

   public boolean m_140805_(ChunkMap p_140806_) {
      this.f_140763_.m_6410_();
      this.f_140764_.m_6410_();
      int i = Integer.MAX_VALUE - this.f_140762_.m_140877_(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.f_140765_.isEmpty()) {
         this.f_140765_.forEach((p_140809_) -> {
            p_140809_.m_143003_(p_140806_, this.f_140770_);
         });
         this.f_140765_.clear();
         return true;
      } else {
         if (!this.f_140769_.isEmpty()) {
            LongIterator longiterator = this.f_140769_.iterator();

            while(longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.m_140857_(j).stream().anyMatch((p_140791_) -> {
                  return p_140791_.m_9428_() == TicketType.f_9444_;
               })) {
                  ChunkHolder chunkholder = p_140806_.m_140174_(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> completablefuture = chunkholder.m_140073_();
                  completablefuture.thenAccept((p_140789_) -> {
                     this.f_140770_.execute(() -> {
                        this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {
                        }, j, false));
                     });
                  });
               }
            }

            this.f_140769_.clear();
         }

         return flag;
      }
   }

   void m_140784_(long p_140785_, Ticket<?> p_140786_) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.m_140857_(p_140785_);
      int i = m_140797_(sortedarrayset);
      Ticket<?> ticket = sortedarrayset.m_14253_(p_140786_);
      ticket.m_9429_(this.f_140771_);
      if (p_140786_.m_9433_() < i) {
         this.f_140762_.m_140715_(p_140785_, p_140786_.m_9433_(), true);
      }

      if (p_140786_.isForceTicks()) {
          SortedArraySet<Ticket<?>> tickets = forcedTickets.computeIfAbsent(p_140785_, e -> SortedArraySet.m_14246_(4));
          tickets.m_14253_(ticket);
      }
   }

   void m_140818_(long p_140819_, Ticket<?> p_140820_) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.m_140857_(p_140819_);
      if (sortedarrayset.remove(p_140820_)) {
      }

      if (sortedarrayset.isEmpty()) {
         this.f_140761_.remove(p_140819_);
      }

      this.f_140762_.m_140715_(p_140819_, m_140797_(sortedarrayset), false);

      if (p_140820_.isForceTicks()) {
          SortedArraySet<Ticket<?>> tickets = forcedTickets.get(p_140819_);
          if (tickets != null) {
              tickets.remove(p_140820_);
          }
      }
   }

   public <T> void m_140792_(TicketType<T> p_140793_, ChunkPos p_140794_, int p_140795_, T p_140796_) {
      this.m_140784_(p_140794_.m_45588_(), new Ticket<>(p_140793_, p_140795_, p_140796_));
   }

   public <T> void m_140823_(TicketType<T> p_140824_, ChunkPos p_140825_, int p_140826_, T p_140827_) {
      Ticket<T> ticket = new Ticket<>(p_140824_, p_140826_, p_140827_);
      this.m_140818_(p_140825_.m_45588_(), ticket);
   }

   public <T> void m_140840_(TicketType<T> p_140841_, ChunkPos p_140842_, int p_140843_, T p_140844_) {
      this.m_140784_(p_140842_.m_45588_(), new Ticket<>(p_140841_, 33 - p_140843_, p_140844_));
   }

   public <T> void m_140849_(TicketType<T> p_140850_, ChunkPos p_140851_, int p_140852_, T p_140853_) {
      Ticket<T> ticket = new Ticket<>(p_140850_, 33 - p_140852_, p_140853_);
      this.m_140818_(p_140851_.m_45588_(), ticket);
   }

   public <T> void registerTicking(TicketType<T> type, ChunkPos pos, int distance, T value) {
      this.m_140784_(pos.m_45588_(), new Ticket<>(type, 33 - distance, value, true));
   }

   public <T> void releaseTicking(TicketType<T> type, ChunkPos pos, int distance, T value) {
      this.m_140818_(pos.m_45588_(), new Ticket<>(type, 33 - distance, value, true));
   }

   private SortedArraySet<Ticket<?>> m_140857_(long p_140858_) {
      return this.f_140761_.computeIfAbsent(p_140858_, (p_140867_) -> {
         return SortedArraySet.m_14246_(4);
      });
   }

   protected void m_140799_(ChunkPos p_140800_, boolean p_140801_) {
      Ticket<ChunkPos> ticket = new Ticket<>(TicketType.f_9445_, 31, p_140800_);
      if (p_140801_) {
         this.m_140784_(p_140800_.m_45588_(), ticket);
      } else {
         this.m_140818_(p_140800_.m_45588_(), ticket);
      }

   }

   public void m_140802_(SectionPos p_140803_, ServerPlayer p_140804_) {
      long i = p_140803_.m_123251_().m_45588_();
      this.f_140760_.computeIfAbsent(i, (p_140863_) -> {
         return new ObjectOpenHashSet();
      }).add(p_140804_);
      this.f_140763_.m_140715_(i, 0, true);
      this.f_140764_.m_140715_(i, 0, true);
   }

   public void m_140828_(SectionPos p_140829_, ServerPlayer p_140830_) {
      long i = p_140829_.m_123251_().m_45588_();
      ObjectSet<ServerPlayer> objectset = this.f_140760_.get(i);
      objectset.remove(p_140830_);
      if (objectset.isEmpty()) {
         this.f_140760_.remove(i);
         this.f_140763_.m_140715_(i, Integer.MAX_VALUE, false);
         this.f_140764_.m_140715_(i, Integer.MAX_VALUE, false);
      }

   }

   protected String m_140838_(long p_140839_) {
      SortedArraySet<Ticket<?>> sortedarrayset = this.f_140761_.get(p_140839_);
      String s;
      if (sortedarrayset != null && !sortedarrayset.isEmpty()) {
         s = sortedarrayset.m_14262_().toString();
      } else {
         s = "no_ticket";
      }

      return s;
   }

   protected void m_140777_(int p_140778_) {
      this.f_140764_.m_140912_(p_140778_);
   }

   public int m_140816_() {
      this.f_140763_.m_6410_();
      return this.f_140763_.f_140886_.size();
   }

   public boolean m_140847_(long p_140848_) {
      this.f_140763_.m_6410_();
      return this.f_140763_.f_140886_.containsKey(p_140848_);
   }

   public String m_140837_() {
      return this.f_140766_.m_140558_();
   }

   public boolean shouldForceTicks(long chunkPos) {
       SortedArraySet<Ticket<?>> tickets = forcedTickets.get(chunkPos);
       return tickets != null && !tickets.isEmpty();
   }

   private void m_143207_(String p_143208_) {
      try {
         FileOutputStream fileoutputstream = new FileOutputStream(new File(p_143208_));

         try {
            for(Entry<SortedArraySet<Ticket<?>>> entry : this.f_140761_.long2ObjectEntrySet()) {
               ChunkPos chunkpos = new ChunkPos(entry.getLongKey());

               for(Ticket<?> ticket : entry.getValue()) {
                  fileoutputstream.write((chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + ticket.m_9428_() + "\t" + ticket.m_9433_() + "\t\n").getBytes(StandardCharsets.UTF_8));
               }
            }
         } catch (Throwable throwable1) {
            try {
               fileoutputstream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         fileoutputstream.close();
      } catch (IOException ioexception) {
         f_140758_.error(ioexception);
      }

   }

   class ChunkTicketTracker extends ChunkTracker {
      public ChunkTicketTracker() {
         super(ChunkMap.f_140127_ + 2, 16, 256);
      }

      protected int m_7031_(long p_140883_) {
         SortedArraySet<Ticket<?>> sortedarrayset = DistanceManager.this.f_140761_.get(p_140883_);
         if (sortedarrayset == null) {
            return Integer.MAX_VALUE;
         } else {
            return sortedarrayset.isEmpty() ? Integer.MAX_VALUE : sortedarrayset.m_14262_().m_9433_();
         }
      }

      protected int m_6172_(long p_140885_) {
         if (!DistanceManager.this.m_7009_(p_140885_)) {
            ChunkHolder chunkholder = DistanceManager.this.m_7316_(p_140885_);
            if (chunkholder != null) {
               return chunkholder.m_140093_();
            }
         }

         return ChunkMap.f_140127_ + 1;
      }

      protected void m_7351_(long p_140880_, int p_140881_) {
         ChunkHolder chunkholder = DistanceManager.this.m_7316_(p_140880_);
         int i = chunkholder == null ? ChunkMap.f_140127_ + 1 : chunkholder.m_140093_();
         if (i != p_140881_) {
            chunkholder = DistanceManager.this.m_7288_(p_140880_, p_140881_, chunkholder, i);
            if (chunkholder != null) {
               DistanceManager.this.f_140765_.add(chunkholder);
            }

         }
      }

      public int m_140877_(int p_140878_) {
         return this.m_75588_(p_140878_);
      }
   }

   class FixedPlayerDistanceChunkTracker extends ChunkTracker {
      protected final Long2ByteMap f_140886_ = new Long2ByteOpenHashMap();
      protected final int f_140887_;

      protected FixedPlayerDistanceChunkTracker(int p_140891_) {
         super(p_140891_ + 2, 16, 256);
         this.f_140887_ = p_140891_;
         this.f_140886_.defaultReturnValue((byte)(p_140891_ + 2));
      }

      protected int m_6172_(long p_140901_) {
         return this.f_140886_.get(p_140901_);
      }

      protected void m_7351_(long p_140893_, int p_140894_) {
         byte b0;
         if (p_140894_ > this.f_140887_) {
            b0 = this.f_140886_.remove(p_140893_);
         } else {
            b0 = this.f_140886_.put(p_140893_, (byte)p_140894_);
         }

         this.m_8002_(p_140893_, b0, p_140894_);
      }

      protected void m_8002_(long p_140895_, int p_140896_, int p_140897_) {
      }

      protected int m_7031_(long p_140899_) {
         return this.m_140902_(p_140899_) ? 0 : Integer.MAX_VALUE;
      }

      private boolean m_140902_(long p_140903_) {
         ObjectSet<ServerPlayer> objectset = DistanceManager.this.f_140760_.get(p_140903_);
         return objectset != null && !objectset.isEmpty();
      }

      public void m_6410_() {
         this.m_75588_(Integer.MAX_VALUE);
      }

      private void m_143212_(String p_143213_) {
         try {
            FileOutputStream fileoutputstream = new FileOutputStream(new File(p_143213_));

            try {
               for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry : this.f_140886_.long2ByteEntrySet()) {
                  ChunkPos chunkpos = new ChunkPos(entry.getLongKey());
                  String s = Byte.toString(entry.getByteValue());
                  fileoutputstream.write((chunkpos.f_45578_ + "\t" + chunkpos.f_45579_ + "\t" + s + "\n").getBytes(StandardCharsets.UTF_8));
               }
            } catch (Throwable throwable1) {
               try {
                  fileoutputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            fileoutputstream.close();
         } catch (IOException ioexception) {
            DistanceManager.f_140758_.error(ioexception);
         }

      }
   }

   class PlayerTicketTracker extends DistanceManager.FixedPlayerDistanceChunkTracker {
      private int f_140905_;
      private final Long2IntMap f_140906_ = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet f_140907_ = new LongOpenHashSet();

      protected PlayerTicketTracker(int p_140910_) {
         super(p_140910_);
         this.f_140905_ = 0;
         this.f_140906_.defaultReturnValue(p_140910_ + 2);
      }

      protected void m_8002_(long p_140915_, int p_140916_, int p_140917_) {
         this.f_140907_.add(p_140915_);
      }

      public void m_140912_(int p_140913_) {
         for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry : this.f_140886_.long2ByteEntrySet()) {
            byte b0 = entry.getByteValue();
            long i = entry.getLongKey();
            this.m_140918_(i, b0, this.m_140932_(b0), b0 <= p_140913_ - 2);
         }

         this.f_140905_ = p_140913_;
      }

      private void m_140918_(long p_140919_, int p_140920_, boolean p_140921_, boolean p_140922_) {
         if (p_140921_ != p_140922_) {
            Ticket<?> ticket = new Ticket<>(TicketType.f_9444_, DistanceManager.f_140759_, new ChunkPos(p_140919_));
            if (p_140922_) {
               DistanceManager.this.f_140767_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(() -> {
                  DistanceManager.this.f_140770_.execute(() -> {
                     if (this.m_140932_(this.m_6172_(p_140919_))) {
                        DistanceManager.this.m_140784_(p_140919_, ticket);
                        DistanceManager.this.f_140769_.add(p_140919_);
                     } else {
                        DistanceManager.this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {
                        }, p_140919_, false));
                     }

                  });
               }, p_140919_, () -> {
                  return p_140920_;
               }));
            } else {
               DistanceManager.this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {
                  DistanceManager.this.f_140770_.execute(() -> {
                     DistanceManager.this.m_140818_(p_140919_, ticket);
                  });
               }, p_140919_, true));
            }
         }

      }

      public void m_6410_() {
         super.m_6410_();
         if (!this.f_140907_.isEmpty()) {
            LongIterator longiterator = this.f_140907_.iterator();

            while(longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.f_140906_.get(i);
               int k = this.m_6172_(i);
               if (j != k) {
                  DistanceManager.this.f_140766_.m_6250_(new ChunkPos(i), () -> {
                     return this.f_140906_.get(i);
                  }, k, (p_140928_) -> {
                     if (p_140928_ >= this.f_140906_.defaultReturnValue()) {
                        this.f_140906_.remove(i);
                     } else {
                        this.f_140906_.put(i, p_140928_);
                     }

                  });
                  this.m_140918_(i, k, this.m_140932_(j), this.m_140932_(k));
               }
            }

            this.f_140907_.clear();
         }

      }

      private boolean m_140932_(int p_140933_) {
         return p_140933_ <= this.f_140905_ - 2;
      }
   }
}
