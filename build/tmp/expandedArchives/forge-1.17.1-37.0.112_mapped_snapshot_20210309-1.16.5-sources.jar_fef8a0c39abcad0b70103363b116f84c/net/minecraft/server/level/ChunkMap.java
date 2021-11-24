package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkMap extends ChunkStorage implements ChunkHolder.PlayerProvider {
   private static final byte f_143034_ = -1;
   private static final byte f_143035_ = 0;
   private static final byte f_143036_ = 1;
   private static final Logger f_140128_ = LogManager.getLogger();
   private static final int f_143037_ = 200;
   private static final int f_143038_ = 3;
   public static final int f_143032_ = 33;
   public static final int f_140127_ = 33 + ChunkStatus.m_62421_();
   public static final int f_143033_ = 31;
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140129_ = new Long2ObjectLinkedOpenHashMap<>();
   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140130_ = this.f_140129_.clone();
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140131_ = new Long2ObjectLinkedOpenHashMap<>();
   private final LongSet f_140132_ = new LongOpenHashSet();
   final ServerLevel f_140133_;
   private final ThreadedLevelLightEngine f_140134_;
   private final BlockableEventLoop<Runnable> f_140135_;
   private final ChunkGenerator f_140136_;
   private final Supplier<DimensionDataStorage> f_140137_;
   private final PoiManager f_140138_;
   final LongSet f_140139_ = new LongOpenHashSet();
   private boolean f_140140_;
   private final ChunkTaskPriorityQueueSorter f_140141_;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140142_;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140143_;
   private final ChunkProgressListener f_140144_;
   private final ChunkStatusUpdateListener f_143031_;
   private final ChunkMap.DistanceManager f_140145_;
   private final AtomicInteger f_140146_ = new AtomicInteger();
   private final StructureManager f_140147_;
   private final String f_182284_;
   private final PlayerMap f_140149_ = new PlayerMap();
   private final Int2ObjectMap<ChunkMap.TrackedEntity> f_140150_ = new Int2ObjectOpenHashMap<>();
   private final Long2ByteMap f_140151_ = new Long2ByteOpenHashMap();
   private final Queue<Runnable> f_140125_ = Queues.newConcurrentLinkedQueue();
   int f_140126_;

   public ChunkMap(ServerLevel p_143040_, LevelStorageSource.LevelStorageAccess p_143041_, DataFixer p_143042_, StructureManager p_143043_, Executor p_143044_, BlockableEventLoop<Runnable> p_143045_, LightChunkGetter p_143046_, ChunkGenerator p_143047_, ChunkProgressListener p_143048_, ChunkStatusUpdateListener p_143049_, Supplier<DimensionDataStorage> p_143050_, int p_143051_, boolean p_143052_) {
      super(new File(p_143041_.m_78299_(p_143040_.m_46472_()), "region"), p_143042_, p_143052_);
      this.f_140147_ = p_143043_;
      File file1 = p_143041_.m_78299_(p_143040_.m_46472_());
      this.f_182284_ = file1.getName();
      this.f_140133_ = p_143040_;
      this.f_140136_ = p_143047_;
      this.f_140135_ = p_143045_;
      ProcessorMailbox<Runnable> processormailbox = ProcessorMailbox.m_18751_(p_143044_, "worldgen");
      ProcessorHandle<Runnable> processorhandle = ProcessorHandle.m_18714_("main", p_143045_::m_6937_);
      this.f_140144_ = p_143048_;
      this.f_143031_ = p_143049_;
      ProcessorMailbox<Runnable> processormailbox1 = ProcessorMailbox.m_18751_(p_143044_, "light");
      this.f_140141_ = new ChunkTaskPriorityQueueSorter(ImmutableList.of(processormailbox, processorhandle, processormailbox1), p_143044_, Integer.MAX_VALUE);
      this.f_140142_ = this.f_140141_.m_140604_(processormailbox, false);
      this.f_140143_ = this.f_140141_.m_140604_(processorhandle, false);
      this.f_140134_ = new ThreadedLevelLightEngine(p_143046_, this, this.f_140133_.m_6042_().m_63935_(), processormailbox1, this.f_140141_.m_140604_(processormailbox1, false));
      this.f_140145_ = new ChunkMap.DistanceManager(p_143044_, p_143045_);
      this.f_140137_ = p_143050_;
      this.f_140138_ = new PoiManager(new File(file1, "poi"), p_143042_, p_143052_, p_143040_);
      this.m_140167_(p_143051_);
   }

   private static double m_140226_(ChunkPos p_140227_, Entity p_140228_) {
      double d0 = (double)SectionPos.m_175554_(p_140227_.f_45578_, 8);
      double d1 = (double)SectionPos.m_175554_(p_140227_.f_45579_, 8);
      double d2 = d0 - p_140228_.m_20185_();
      double d3 = d1 - p_140228_.m_20189_();
      return d2 * d2 + d3 * d3;
   }

   private static int m_140338_(ChunkPos p_140339_, ServerPlayer p_140340_, boolean p_140341_) {
      int i;
      int j;
      if (p_140341_) {
         SectionPos sectionpos = p_140340_.m_8965_();
         i = sectionpos.m_123170_();
         j = sectionpos.m_123222_();
      } else {
         i = SectionPos.m_123171_(p_140340_.m_146903_());
         j = SectionPos.m_123171_(p_140340_.m_146907_());
      }

      return m_140206_(p_140339_, i, j);
   }

   private static int m_143119_(ChunkPos p_143120_, Entity p_143121_) {
      return m_140206_(p_143120_, SectionPos.m_123171_(p_143121_.m_146903_()), SectionPos.m_123171_(p_143121_.m_146907_()));
   }

   private static int m_140206_(ChunkPos p_140207_, int p_140208_, int p_140209_) {
      int i = p_140207_.f_45578_ - p_140208_;
      int j = p_140207_.f_45579_ - p_140209_;
      return Math.max(Math.abs(i), Math.abs(j));
   }

   protected ThreadedLevelLightEngine m_140166_() {
      return this.f_140134_;
   }

   @Nullable
   protected ChunkHolder m_140174_(long p_140175_) {
      return this.f_140129_.get(p_140175_);
   }

   @Nullable
   protected ChunkHolder m_140327_(long p_140328_) {
      return this.f_140130_.get(p_140328_);
   }

   protected IntSupplier m_140371_(long p_140372_) {
      return () -> {
         ChunkHolder chunkholder = this.m_140327_(p_140372_);
         return chunkholder == null ? ChunkTaskPriorityQueue.f_140508_ - 1 : Math.min(chunkholder.m_140094_(), ChunkTaskPriorityQueue.f_140508_ - 1);
      };
   }

   public String m_140204_(ChunkPos p_140205_) {
      ChunkHolder chunkholder = this.m_140327_(p_140205_.m_45588_());
      if (chunkholder == null) {
         return "null";
      } else {
         String s = chunkholder.m_140093_() + "\n";
         ChunkStatus chunkstatus = chunkholder.m_140088_();
         ChunkAccess chunkaccess = chunkholder.m_140089_();
         if (chunkstatus != null) {
            s = s + "St: \u00a7" + chunkstatus.m_62445_() + chunkstatus + "\u00a7r\n";
         }

         if (chunkaccess != null) {
            s = s + "Ch: \u00a7" + chunkaccess.m_6415_().m_62445_() + chunkaccess.m_6415_() + "\u00a7r\n";
         }

         ChunkHolder.FullChunkStatus chunkholder$fullchunkstatus = chunkholder.m_140091_();
         s = s + "\u00a7" + chunkholder$fullchunkstatus.ordinal() + chunkholder$fullchunkstatus;
         return s + "\u00a7r";
      }
   }

   private CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> m_140210_(ChunkPos p_140211_, int p_140212_, IntFunction<ChunkStatus> p_140213_) {
      List<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> list = Lists.newArrayList();
      int i = p_140211_.f_45578_;
      int j = p_140211_.f_45579_;

      for(int k = -p_140212_; k <= p_140212_; ++k) {
         for(int l = -p_140212_; l <= p_140212_; ++l) {
            int i1 = Math.max(Math.abs(l), Math.abs(k));
            final ChunkPos chunkpos = new ChunkPos(i + l, j + k);
            long j1 = chunkpos.m_45588_();
            ChunkHolder chunkholder = this.m_140174_(j1);
            if (chunkholder == null) {
               return CompletableFuture.completedFuture(Either.right(new ChunkHolder.ChunkLoadingFailure() {
                  public String toString() {
                     return "Unloaded " + chunkpos;
                  }
               }));
            }

            ChunkStatus chunkstatus = p_140213_.apply(i1);
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = chunkholder.m_140049_(chunkstatus, this);
            list.add(completablefuture);
         }
      }

      CompletableFuture<List<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> completablefuture1 = Util.m_137567_(list);
      return completablefuture1.thenApply((p_140173_) -> {
         List<ChunkAccess> list1 = Lists.newArrayList();
         int k1 = 0;

         for(final Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> either : p_140173_) {
            Optional<ChunkAccess> optional = either.left();
            if (!optional.isPresent()) {
               final int l1 = k1;
               return Either.right(new ChunkHolder.ChunkLoadingFailure() {
                  public String toString() {
                     return "Unloaded " + new ChunkPos(i + l1 % (p_140212_ * 2 + 1), j + l1 / (p_140212_ * 2 + 1)) + " " + either.right().get();
                  }
               });
            }

            list1.add(optional.get());
            ++k1;
         }

         return Either.left(list1);
      });
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143117_(ChunkPos p_143118_) {
      return this.m_140210_(p_143118_, 2, (p_143129_) -> {
         return ChunkStatus.f_62326_;
      }).thenApplyAsync((p_143137_) -> {
         return p_143137_.mapLeft((p_143139_) -> {
            return (LevelChunk)p_143139_.get(p_143139_.size() / 2);
         });
      }, this.f_140135_);
   }

   @Nullable
   ChunkHolder m_140176_(long p_140177_, int p_140178_, @Nullable ChunkHolder p_140179_, int p_140180_) {
      if (p_140180_ > f_140127_ && p_140178_ > f_140127_) {
         return p_140179_;
      } else {
         if (p_140179_ != null) {
            p_140179_.m_140027_(p_140178_);
         }

         if (p_140179_ != null) {
            if (p_140178_ > f_140127_) {
               this.f_140139_.add(p_140177_);
            } else {
               this.f_140139_.remove(p_140177_);
            }
         }

         if (p_140178_ <= f_140127_ && p_140179_ == null) {
            p_140179_ = this.f_140131_.remove(p_140177_);
            if (p_140179_ != null) {
               p_140179_.m_140027_(p_140178_);
            } else {
               p_140179_ = new ChunkHolder(new ChunkPos(p_140177_), p_140178_, this.f_140133_, this.f_140134_, this.f_140141_, this);
            }

            this.f_140129_.put(p_140177_, p_140179_);
            this.f_140140_ = true;
         }

         return p_140179_;
      }
   }

   public void close() throws IOException {
      try {
         this.f_140141_.close();
         this.f_140138_.close();
      } finally {
         super.close();
      }

   }

   protected void m_140318_(boolean p_140319_) {
      if (p_140319_) {
         List<ChunkHolder> list = this.f_140130_.values().stream().filter(ChunkHolder::m_140095_).peek(ChunkHolder::m_140096_).collect(Collectors.toList());
         MutableBoolean mutableboolean = new MutableBoolean();

         do {
            mutableboolean.setFalse();
            list.stream().map((p_140420_) -> {
               CompletableFuture<ChunkAccess> completablefuture;
               do {
                  completablefuture = p_140420_.m_140090_();
                  this.f_140135_.m_18701_(completablefuture::isDone);
               } while(completablefuture != p_140420_.m_140090_());

               return completablefuture.join();
            }).filter((p_140400_) -> {
               return p_140400_ instanceof ImposterProtoChunk || p_140400_ instanceof LevelChunk;
            }).filter(this::m_140258_).forEach((p_140284_) -> {
               mutableboolean.setTrue();
            });
         } while(mutableboolean.isTrue());

         this.m_140353_(() -> {
            return true;
         });
         this.m_63514_();
      } else {
         this.f_140130_.values().stream().filter(ChunkHolder::m_140095_).forEach((p_140412_) -> {
            ChunkAccess chunkaccess = p_140412_.m_140090_().getNow((ChunkAccess)null);
            if (chunkaccess instanceof ImposterProtoChunk || chunkaccess instanceof LevelChunk) {
               this.m_140258_(chunkaccess);
               p_140412_.m_140096_();
            }

         });
      }

   }

   protected void m_140280_(BooleanSupplier p_140281_) {
      ProfilerFiller profilerfiller = this.f_140133_.m_46473_();
      profilerfiller.m_6180_("poi");
      this.f_140138_.m_6202_(p_140281_);
      profilerfiller.m_6182_("chunk_unload");
      if (!this.f_140133_.m_7441_()) {
         this.m_140353_(p_140281_);
      }

      profilerfiller.m_7238_();
   }

   private void m_140353_(BooleanSupplier p_140354_) {
      LongIterator longiterator = this.f_140139_.iterator();

      for(int i = 0; longiterator.hasNext() && (p_140354_.getAsBoolean() || i < 200 || this.f_140139_.size() > 2000); longiterator.remove()) {
         long j = longiterator.nextLong();
         ChunkHolder chunkholder = this.f_140129_.remove(j);
         if (chunkholder != null) {
            this.f_140131_.put(j, chunkholder);
            this.f_140140_ = true;
            ++i;
            this.m_140181_(j, chunkholder);
         }
      }

      Runnable runnable;
      while((p_140354_.getAsBoolean() || this.f_140125_.size() > 2000) && (runnable = this.f_140125_.poll()) != null) {
         runnable.run();
      }

   }

   private void m_140181_(long p_140182_, ChunkHolder p_140183_) {
      CompletableFuture<ChunkAccess> completablefuture = p_140183_.m_140090_();
      completablefuture.thenAcceptAsync((p_140309_) -> {
         CompletableFuture<ChunkAccess> completablefuture1 = p_140183_.m_140090_();
         if (completablefuture1 != completablefuture) {
            this.m_140181_(p_140182_, p_140183_);
         } else {
            if (this.f_140131_.remove(p_140182_, p_140183_) && p_140309_ != null) {
               if (p_140309_ instanceof LevelChunk) {
                  ((LevelChunk)p_140309_).m_62913_(false);
                  net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Unload((LevelChunk)p_140309_));
               }

               this.m_140258_(p_140309_);
               if (this.f_140132_.remove(p_140182_) && p_140309_ instanceof LevelChunk) {
                  LevelChunk levelchunk = (LevelChunk)p_140309_;
                  this.f_140133_.m_8712_(levelchunk);
               }

               this.f_140134_.m_9330_(p_140309_.m_7697_());
               this.f_140134_.m_9409_();
               this.f_140144_.m_5511_(p_140309_.m_7697_(), (ChunkStatus)null);
            }

         }
      }, this.f_140125_::add).whenComplete((p_140303_, p_140304_) -> {
         if (p_140304_ != null) {
            f_140128_.error("Failed to save chunk {}", p_140183_.m_140092_(), p_140304_);
         }

      });
   }

   protected boolean m_140324_() {
      if (!this.f_140140_) {
         return false;
      } else {
         this.f_140130_ = this.f_140129_.clone();
         this.f_140140_ = false;
         return true;
      }
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140292_(ChunkHolder p_140293_, ChunkStatus p_140294_) {
      ChunkPos chunkpos = p_140293_.m_140092_();
      if (p_140294_ == ChunkStatus.f_62314_) {
         return this.m_140417_(chunkpos);
      } else {
         if (p_140294_ == ChunkStatus.f_62323_) {
            this.f_140145_.m_140792_(TicketType.f_9446_, chunkpos, 33 + ChunkStatus.m_62370_(ChunkStatus.f_62323_), chunkpos);
         }

         Optional<ChunkAccess> optional = p_140293_.m_140049_(p_140294_.m_62482_(), this).getNow(ChunkHolder.f_139995_).left();
         if (optional.isPresent() && optional.get().m_6415_().m_62427_(p_140294_)) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = p_140294_.m_62364_(this.f_140133_, this.f_140147_, this.f_140134_, (p_143132_) -> {
               return this.m_140383_(p_140293_);
            }, optional.get());
            this.f_140144_.m_5511_(chunkpos, p_140294_);
            return completablefuture;
         } else {
            return this.m_140360_(p_140293_, p_140294_);
         }
      }
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140417_(ChunkPos p_140418_) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            this.f_140133_.m_46473_().m_6174_("chunkLoad");
            CompoundTag compoundtag = this.m_140427_(p_140418_);
            if (compoundtag != null) {
               boolean flag = compoundtag.m_128425_("Level", 10) && compoundtag.m_128469_("Level").m_128425_("Status", 8);
               if (flag) {
                  ChunkAccess chunkaccess = ChunkSerializer.m_63457_(this.f_140133_, this.f_140147_, this.f_140138_, p_140418_, compoundtag);
                  this.m_140229_(p_140418_, chunkaccess.m_6415_().m_62494_());
                  return Either.left(chunkaccess);
               }

               f_140128_.error("Chunk file at {} is missing level data, skipping", (Object)p_140418_);
            }
         } catch (ReportedException reportedexception) {
            Throwable throwable = reportedexception.getCause();
            if (!(throwable instanceof IOException)) {
               this.m_140422_(p_140418_);
               throw reportedexception;
            }

            f_140128_.error("Couldn't load chunk {}", p_140418_, throwable);
         } catch (Exception exception) {
            f_140128_.error("Couldn't load chunk {}", p_140418_, exception);
         }

         this.m_140422_(p_140418_);
         return Either.left(new ProtoChunk(p_140418_, UpgradeData.f_63320_, this.f_140133_));
      }, this.f_140135_);
   }

   private void m_140422_(ChunkPos p_140423_) {
      this.f_140151_.put(p_140423_.m_45588_(), (byte)-1);
   }

   private byte m_140229_(ChunkPos p_140230_, ChunkStatus.ChunkType p_140231_) {
      return this.f_140151_.put(p_140230_.m_45588_(), (byte)(p_140231_ == ChunkStatus.ChunkType.PROTOCHUNK ? -1 : 1));
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140360_(ChunkHolder p_140361_, ChunkStatus p_140362_) {
      ChunkPos chunkpos = p_140361_.m_140092_();
      CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.m_140210_(chunkpos, p_140362_.m_62488_(), (p_140346_) -> {
         return this.m_140262_(p_140362_, p_140346_);
      });
      this.f_140133_.m_46473_().m_6525_(() -> {
         return "chunkGenerate " + p_140362_.m_62467_();
      });
      Executor executor = (p_143148_) -> {
         this.f_140142_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_140361_, p_143148_));
      };
      return completablefuture.thenComposeAsync((p_143083_) -> {
         return p_143083_.map((p_143089_) -> {
            try {
               CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture1 = p_140362_.m_156212_(executor, this.f_140133_, this.f_140136_, this.f_140147_, this.f_140134_, (p_143113_) -> {
                  return this.m_140383_(p_140361_);
               }, p_143089_);
               this.f_140144_.m_5511_(chunkpos, p_140362_);
               return completablefuture1;
            } catch (Exception exception) {
               exception.getStackTrace();
               CrashReport crashreport = CrashReport.m_127521_(exception, "Exception generating new chunk");
               CrashReportCategory crashreportcategory = crashreport.m_127514_("Chunk to be generated");
               crashreportcategory.m_128159_("Location", String.format("%d,%d", chunkpos.f_45578_, chunkpos.f_45579_));
               crashreportcategory.m_128159_("Position hash", ChunkPos.m_45589_(chunkpos.f_45578_, chunkpos.f_45579_));
               crashreportcategory.m_128159_("Generator", this.f_140136_);
               throw new ReportedException(crashreport);
            }
         }, (p_143074_) -> {
            this.m_140375_(chunkpos);
            return CompletableFuture.completedFuture(Either.right(p_143074_));
         });
      }, executor);
   }

   protected void m_140375_(ChunkPos p_140376_) {
      this.f_140135_.m_6937_(Util.m_137474_(() -> {
         this.f_140145_.m_140823_(TicketType.f_9446_, p_140376_, 33 + ChunkStatus.m_62370_(ChunkStatus.f_62323_), p_140376_);
      }, () -> {
         return "release light ticket " + p_140376_;
      }));
   }

   private ChunkStatus m_140262_(ChunkStatus p_140263_, int p_140264_) {
      ChunkStatus chunkstatus;
      if (p_140264_ == 0) {
         chunkstatus = p_140263_.m_62482_();
      } else {
         chunkstatus = ChunkStatus.m_156185_(ChunkStatus.m_62370_(p_140263_) + p_140264_);
      }

      return chunkstatus;
   }

   private static void m_143064_(ServerLevel p_143065_, List<CompoundTag> p_143066_) {
      if (!p_143066_.isEmpty()) {
         p_143065_.m_143327_(EntityType.m_147045_(p_143066_, p_143065_));
      }

   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140383_(ChunkHolder p_140384_) {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> completablefuture = p_140384_.m_140047_(ChunkStatus.f_62326_.m_62482_());
      return completablefuture.thenApplyAsync((p_143060_) -> {
         ChunkStatus chunkstatus = ChunkHolder.m_140074_(p_140384_.m_140093_());
         return !chunkstatus.m_62427_(ChunkStatus.f_62326_) ? ChunkHolder.f_139995_ : p_143060_.mapLeft((p_143057_) -> {
            ChunkPos chunkpos = p_140384_.m_140092_();
            ProtoChunk protochunk = (ProtoChunk)p_143057_;
            LevelChunk levelchunk;
            if (protochunk instanceof ImposterProtoChunk) {
               levelchunk = ((ImposterProtoChunk)protochunk).m_62768_();
            } else {
               levelchunk = new LevelChunk(this.f_140133_, protochunk, (p_143098_) -> {
                  m_143064_(this.f_140133_, protochunk.m_63293_());
               });
               p_140384_.m_140052_(new ImposterProtoChunk(levelchunk));
            }

            levelchunk.m_62879_(() -> {
               return ChunkHolder.m_140083_(p_140384_.m_140093_());
            });
            levelchunk.m_62952_();
            if (this.f_140132_.add(chunkpos.m_45588_())) {
               levelchunk.m_62913_(true);
               try {
               p_140384_.currentlyLoading = levelchunk; // Forge - bypass the future chain when getChunk is called, this prevents deadlocks.
               levelchunk.m_156369_();
               net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Load(levelchunk));
               } finally {
                   p_140384_.currentlyLoading = null; // Forge - Stop bypassing the future chain.
               }
            }

            return levelchunk;
         });
      }, (p_143144_) -> {
         this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(p_143144_, p_140384_.m_140092_().m_45588_(), p_140384_::m_140093_));
      });
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143053_(ChunkHolder p_143054_) {
      ChunkPos chunkpos = p_143054_.m_140092_();
      CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> completablefuture = this.m_140210_(chunkpos, 1, (p_143108_) -> {
         return ChunkStatus.f_62326_;
      });
      CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> completablefuture1 = completablefuture.thenApplyAsync((p_143125_) -> {
         return p_143125_.flatMap((p_143127_) -> {
            LevelChunk levelchunk = (LevelChunk)p_143127_.get(p_143127_.size() / 2);
            levelchunk.m_62812_();
            return Either.left(levelchunk);
         });
      }, (p_143135_) -> {
         this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143054_, p_143135_));
      });
      completablefuture1.thenAcceptAsync((p_143095_) -> {
         p_143095_.ifLeft((p_143092_) -> {
            this.f_140146_.getAndIncrement();
            Packet<?>[] packet = new Packet[2];
            this.m_5960_(chunkpos, false).forEach((p_143106_) -> {
               this.m_140195_(p_143106_, packet, p_143092_);
            });
         });
      }, (p_143116_) -> {
         this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143054_, p_143116_));
      });
      return completablefuture1;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143109_(ChunkHolder p_143110_) {
      return this.m_140210_(p_143110_.m_140092_(), 1, ChunkStatus::m_156185_).thenApplyAsync((p_143100_) -> {
         return p_143100_.mapLeft((p_143102_) -> {
            LevelChunk levelchunk = (LevelChunk)p_143102_.get(p_143102_.size() / 2);
            levelchunk.m_62813_();
            return levelchunk;
         });
      }, (p_143063_) -> {
         this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143110_, p_143063_));
      });
   }

   public int m_140368_() {
      return this.f_140146_.get();
   }

   private boolean m_140258_(ChunkAccess p_140259_) {
      this.f_140138_.m_63796_(p_140259_.m_7697_());
      if (!p_140259_.m_6344_()) {
         return false;
      } else {
         p_140259_.m_8092_(false);
         ChunkPos chunkpos = p_140259_.m_7697_();

         try {
            ChunkStatus chunkstatus = p_140259_.m_6415_();
            if (chunkstatus.m_62494_() != ChunkStatus.ChunkType.LEVELCHUNK) {
               if (this.m_140425_(chunkpos)) {
                  return false;
               }

               if (chunkstatus == ChunkStatus.f_62314_ && p_140259_.m_6633_().values().stream().noneMatch(StructureStart::m_73603_)) {
                  return false;
               }
            }

            this.f_140133_.m_46473_().m_6174_("chunkSave");
            CompoundTag compoundtag = ChunkSerializer.m_63454_(this.f_140133_, p_140259_);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkDataEvent.Save(p_140259_, p_140259_.getWorldForge() != null ? p_140259_.getWorldForge() : this.f_140133_, compoundtag));
            this.m_63502_(chunkpos, compoundtag);
            this.m_140229_(chunkpos, chunkstatus.m_62494_());
            return true;
         } catch (Exception exception) {
            f_140128_.error("Failed to save chunk {},{}", chunkpos.f_45578_, chunkpos.f_45579_, exception);
            return false;
         }
      }
   }

   private boolean m_140425_(ChunkPos p_140426_) {
      byte b0 = this.f_140151_.get(p_140426_.m_45588_());
      if (b0 != 0) {
         return b0 == 1;
      } else {
         CompoundTag compoundtag;
         try {
            compoundtag = this.m_140427_(p_140426_);
            if (compoundtag == null) {
               this.m_140422_(p_140426_);
               return false;
            }
         } catch (Exception exception) {
            f_140128_.error("Failed to read chunk {}", p_140426_, exception);
            this.m_140422_(p_140426_);
            return false;
         }

         ChunkStatus.ChunkType chunkstatus$chunktype = ChunkSerializer.m_63485_(compoundtag);
         return this.m_140229_(p_140426_, chunkstatus$chunktype) == 1;
      }
   }

   protected void m_140167_(int p_140168_) {
      int i = Mth.m_14045_(p_140168_ + 1, 3, 33);
      if (i != this.f_140126_) {
         int j = this.f_140126_;
         this.f_140126_ = i;
         this.f_140145_.m_140777_(this.f_140126_);

         for(ChunkHolder chunkholder : this.f_140129_.values()) {
            ChunkPos chunkpos = chunkholder.m_140092_();
            Packet<?>[] packet = new Packet[2];
            this.m_5960_(chunkpos, false).forEach((p_143071_) -> {
               int k = m_140338_(chunkpos, p_143071_, true);
               boolean flag = k <= j;
               boolean flag1 = k <= this.f_140126_;
               this.m_140186_(p_143071_, chunkpos, packet, flag, flag1);
            });
         }
      }

   }

   protected void m_140186_(ServerPlayer p_140187_, ChunkPos p_140188_, Packet<?>[] p_140189_, boolean p_140190_, boolean p_140191_) {
      if (p_140187_.f_19853_ == this.f_140133_) {
         net.minecraftforge.event.ForgeEventFactory.fireChunkWatch(p_140190_, p_140191_, p_140187_, p_140188_, this.f_140133_);
         if (p_140191_ && !p_140190_) {
            ChunkHolder chunkholder = this.m_140327_(p_140188_.m_45588_());
            if (chunkholder != null) {
               LevelChunk levelchunk = chunkholder.m_140085_();
               if (levelchunk != null) {
                  this.m_140195_(p_140187_, p_140189_, levelchunk);
               }

               DebugPackets.m_133676_(this.f_140133_, p_140188_);
            }
         }

         if (!p_140191_ && p_140190_) {
            p_140187_.m_9088_(p_140188_);
         }

      }
   }

   public int m_140394_() {
      return this.f_140130_.size();
   }

   protected net.minecraft.server.level.DistanceManager m_143145_() {
      return this.f_140145_;
   }

   protected Iterable<ChunkHolder> m_140416_() {
      return Iterables.unmodifiableIterable(this.f_140130_.values());
   }

   void m_140274_(Writer p_140275_) throws IOException {
      CsvOutput csvoutput = CsvOutput.m_13619_().m_13630_("x").m_13630_("z").m_13630_("level").m_13630_("in_memory").m_13630_("status").m_13630_("full_status").m_13630_("accessible_ready").m_13630_("ticking_ready").m_13630_("entity_ticking_ready").m_13630_("ticket").m_13630_("spawning").m_13630_("block_entity_count").m_13628_(p_140275_);

      for(Entry<ChunkHolder> entry : this.f_140130_.long2ObjectEntrySet()) {
         ChunkPos chunkpos = new ChunkPos(entry.getLongKey());
         ChunkHolder chunkholder = entry.getValue();
         Optional<ChunkAccess> optional = Optional.ofNullable(chunkholder.m_140089_());
         Optional<LevelChunk> optional1 = optional.flatMap((p_143123_) -> {
            return p_143123_ instanceof LevelChunk ? Optional.of((LevelChunk)p_143123_) : Optional.empty();
         });
         csvoutput.m_13624_(chunkpos.f_45578_, chunkpos.f_45579_, chunkholder.m_140093_(), optional.isPresent(), optional.map(ChunkAccess::m_6415_).orElse((ChunkStatus)null), optional1.map(LevelChunk::m_6708_).orElse((ChunkHolder.FullChunkStatus)null), m_140278_(chunkholder.m_140082_()), m_140278_(chunkholder.m_140026_()), m_140278_(chunkholder.m_140073_()), this.f_140145_.m_140838_(entry.getLongKey()), !this.m_140397_(chunkpos), optional1.map((p_140402_) -> {
            return p_140402_.m_62954_().size();
         }).orElse(0));
      }

   }

   private static String m_140278_(CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> p_140279_) {
      try {
         Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> either = p_140279_.getNow((Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>)null);
         return either != null ? either.map((p_140380_) -> {
            return "done";
         }, (p_140286_) -> {
            return "unloaded";
         }) : "not completed";
      } catch (CompletionException completionexception) {
         return "failed " + completionexception.getCause().getMessage();
      } catch (CancellationException cancellationexception) {
         return "cancelled";
      }
   }

   @Nullable
   private CompoundTag m_140427_(ChunkPos p_140428_) throws IOException {
      CompoundTag compoundtag = this.m_63512_(p_140428_);
      return compoundtag == null ? null : this.m_63507_(this.f_140133_.m_46472_(), this.f_140137_, compoundtag);
   }

   boolean m_140397_(ChunkPos p_140398_) {
      long i = p_140398_.m_45588_();
      return !this.f_140145_.m_140847_(i) ? true : this.f_140149_.m_8243_(i).noneMatch((p_140221_) -> {
         return !p_140221_.m_5833_() && m_140226_(p_140398_, p_140221_) < 16384.0D;
      });
   }

   private boolean m_140329_(ServerPlayer p_140330_) {
      return p_140330_.m_5833_() && !this.f_140133_.m_46469_().m_46207_(GameRules.f_46146_);
   }

   void m_140192_(ServerPlayer p_140193_, boolean p_140194_) {
      boolean flag = this.m_140329_(p_140193_);
      boolean flag1 = this.f_140149_.m_8260_(p_140193_);
      int i = SectionPos.m_123171_(p_140193_.m_146903_());
      int j = SectionPos.m_123171_(p_140193_.m_146907_());
      if (p_140194_) {
         this.f_140149_.m_8252_(ChunkPos.m_45589_(i, j), p_140193_, flag);
         this.m_140373_(p_140193_);
         if (!flag) {
            this.f_140145_.m_140802_(SectionPos.m_123194_(p_140193_), p_140193_);
         }
      } else {
         SectionPos sectionpos = p_140193_.m_8965_();
         this.f_140149_.m_8249_(sectionpos.m_123251_().m_45588_(), p_140193_);
         if (!flag1) {
            this.f_140145_.m_140828_(sectionpos, p_140193_);
         }
      }

      for(int l = i - this.f_140126_; l <= i + this.f_140126_; ++l) {
         for(int k = j - this.f_140126_; k <= j + this.f_140126_; ++k) {
            ChunkPos chunkpos = new ChunkPos(l, k);
            this.m_140186_(p_140193_, chunkpos, new Packet[2], !p_140194_, p_140194_);
         }
      }

   }

   private SectionPos m_140373_(ServerPlayer p_140374_) {
      SectionPos sectionpos = SectionPos.m_123194_(p_140374_);
      p_140374_.m_9119_(sectionpos);
      p_140374_.f_8906_.m_141995_(new ClientboundSetChunkCacheCenterPacket(sectionpos.m_123170_(), sectionpos.m_123222_()));
      return sectionpos;
   }

   public void m_140184_(ServerPlayer p_140185_) {
      for(ChunkMap.TrackedEntity chunkmap$trackedentity : this.f_140150_.values()) {
         if (chunkmap$trackedentity.f_140472_ == p_140185_) {
            chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
         } else {
            chunkmap$trackedentity.m_140497_(p_140185_);
         }
      }

      int l1 = SectionPos.m_123171_(p_140185_.m_146903_());
      int i2 = SectionPos.m_123171_(p_140185_.m_146907_());
      SectionPos sectionpos = p_140185_.m_8965_();
      SectionPos sectionpos1 = SectionPos.m_123194_(p_140185_);
      long i = sectionpos.m_123251_().m_45588_();
      long j = sectionpos1.m_123251_().m_45588_();
      boolean flag = this.f_140149_.m_8262_(p_140185_);
      boolean flag1 = this.m_140329_(p_140185_);
      boolean flag2 = sectionpos.m_123252_() != sectionpos1.m_123252_();
      if (flag2 || flag != flag1) {
         this.m_140373_(p_140185_);
         if (!flag) {
            this.f_140145_.m_140828_(sectionpos, p_140185_);
         }

         if (!flag1) {
            this.f_140145_.m_140802_(sectionpos1, p_140185_);
         }

         if (!flag && flag1) {
            this.f_140149_.m_8256_(p_140185_);
         }

         if (flag && !flag1) {
            this.f_140149_.m_8258_(p_140185_);
         }

         if (i != j) {
            this.f_140149_.m_8245_(i, j, p_140185_);
         }
      }

      int k = sectionpos.m_123170_();
      int l = sectionpos.m_123222_();
      if (Math.abs(k - l1) <= this.f_140126_ * 2 && Math.abs(l - i2) <= this.f_140126_ * 2) {
         int k2 = Math.min(l1, k) - this.f_140126_;
         int i3 = Math.min(i2, l) - this.f_140126_;
         int j3 = Math.max(l1, k) + this.f_140126_;
         int k3 = Math.max(i2, l) + this.f_140126_;

         for(int l3 = k2; l3 <= j3; ++l3) {
            for(int k1 = i3; k1 <= k3; ++k1) {
               ChunkPos chunkpos1 = new ChunkPos(l3, k1);
               boolean flag5 = m_140206_(chunkpos1, k, l) <= this.f_140126_;
               boolean flag6 = m_140206_(chunkpos1, l1, i2) <= this.f_140126_;
               this.m_140186_(p_140185_, chunkpos1, new Packet[2], flag5, flag6);
            }
         }
      } else {
         for(int i1 = k - this.f_140126_; i1 <= k + this.f_140126_; ++i1) {
            for(int j1 = l - this.f_140126_; j1 <= l + this.f_140126_; ++j1) {
               ChunkPos chunkpos = new ChunkPos(i1, j1);
               boolean flag3 = true;
               boolean flag4 = false;
               this.m_140186_(p_140185_, chunkpos, new Packet[2], true, false);
            }
         }

         for(int j2 = l1 - this.f_140126_; j2 <= l1 + this.f_140126_; ++j2) {
            for(int l2 = i2 - this.f_140126_; l2 <= i2 + this.f_140126_; ++l2) {
               ChunkPos chunkpos2 = new ChunkPos(j2, l2);
               boolean flag7 = false;
               boolean flag8 = true;
               this.m_140186_(p_140185_, chunkpos2, new Packet[2], false, true);
            }
         }
      }

   }

   public Stream<ServerPlayer> m_5960_(ChunkPos p_140252_, boolean p_140253_) {
      return this.f_140149_.m_8243_(p_140252_.m_45588_()).filter((p_140257_) -> {
         int i = m_140338_(p_140252_, p_140257_, true);
         if (i > this.f_140126_) {
            return false;
         } else {
            return !p_140253_ || i == this.f_140126_;
         }
      });
   }

   protected void m_140199_(Entity p_140200_) {
      if (!(p_140200_ instanceof net.minecraftforge.entity.PartEntity)) {
         EntityType<?> entitytype = p_140200_.m_6095_();
         int i = entitytype.m_20681_() * 16;
         if (i != 0) {
            int j = entitytype.m_20682_();
            if (this.f_140150_.containsKey(p_140200_.m_142049_())) {
               throw (IllegalStateException)Util.m_137570_(new IllegalStateException("Entity is already tracked!"));
            } else {
               ChunkMap.TrackedEntity chunkmap$trackedentity = new ChunkMap.TrackedEntity(p_140200_, i, j, entitytype.m_20683_());
               this.f_140150_.put(p_140200_.m_142049_(), chunkmap$trackedentity);
               chunkmap$trackedentity.m_140487_(this.f_140133_.m_6907_());
               if (p_140200_ instanceof ServerPlayer) {
                  ServerPlayer serverplayer = (ServerPlayer)p_140200_;
                  this.m_140192_(serverplayer, true);

                  for(ChunkMap.TrackedEntity chunkmap$trackedentity1 : this.f_140150_.values()) {
                     if (chunkmap$trackedentity1.f_140472_ != serverplayer) {
                        chunkmap$trackedentity1.m_140497_(serverplayer);
                     }
                  }
               }

            }
         }
      }
   }

   protected void m_140331_(Entity p_140332_) {
      if (p_140332_ instanceof ServerPlayer) {
         ServerPlayer serverplayer = (ServerPlayer)p_140332_;
         this.m_140192_(serverplayer, false);

         for(ChunkMap.TrackedEntity chunkmap$trackedentity : this.f_140150_.values()) {
            chunkmap$trackedentity.m_140485_(serverplayer);
         }
      }

      ChunkMap.TrackedEntity chunkmap$trackedentity1 = this.f_140150_.remove(p_140332_.m_142049_());
      if (chunkmap$trackedentity1 != null) {
         chunkmap$trackedentity1.m_140482_();
      }

   }

   protected void m_140421_() {
      List<ServerPlayer> list = Lists.newArrayList();
      List<ServerPlayer> list1 = this.f_140133_.m_6907_();

      for(ChunkMap.TrackedEntity chunkmap$trackedentity : this.f_140150_.values()) {
         SectionPos sectionpos = chunkmap$trackedentity.f_140474_;
         SectionPos sectionpos1 = SectionPos.m_123194_(chunkmap$trackedentity.f_140472_);
         if (!Objects.equals(sectionpos, sectionpos1)) {
            chunkmap$trackedentity.m_140487_(list1);
            Entity entity = chunkmap$trackedentity.f_140472_;
            if (entity instanceof ServerPlayer) {
               list.add((ServerPlayer)entity);
            }

            chunkmap$trackedentity.f_140474_ = sectionpos1;
         }

         chunkmap$trackedentity.f_140471_.m_8533_();
      }

      if (!list.isEmpty()) {
         for(ChunkMap.TrackedEntity chunkmap$trackedentity1 : this.f_140150_.values()) {
            chunkmap$trackedentity1.m_140487_(list);
         }
      }

   }

   public void m_140201_(Entity p_140202_, Packet<?> p_140203_) {
      ChunkMap.TrackedEntity chunkmap$trackedentity = this.f_140150_.get(p_140202_.m_142049_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140489_(p_140203_);
      }

   }

   protected void m_140333_(Entity p_140334_, Packet<?> p_140335_) {
      ChunkMap.TrackedEntity chunkmap$trackedentity = this.f_140150_.get(p_140334_.m_142049_());
      if (chunkmap$trackedentity != null) {
         chunkmap$trackedentity.m_140499_(p_140335_);
      }

   }

   private void m_140195_(ServerPlayer p_140196_, Packet<?>[] p_140197_, LevelChunk p_140198_) {
      if (p_140197_[0] == null) {
         p_140197_[0] = new ClientboundLevelChunkPacket(p_140198_);
         p_140197_[1] = new ClientboundLightUpdatePacket(p_140198_.m_7697_(), this.f_140134_, (BitSet)null, (BitSet)null, true);
      }

      p_140196_.m_9090_(p_140198_.m_7697_(), p_140197_[0], p_140197_[1]);
      DebugPackets.m_133676_(this.f_140133_, p_140198_.m_7697_());
      List<Entity> list = Lists.newArrayList();
      List<Entity> list1 = Lists.newArrayList();

      for(ChunkMap.TrackedEntity chunkmap$trackedentity : this.f_140150_.values()) {
         Entity entity = chunkmap$trackedentity.f_140472_;
         if (entity != p_140196_ && entity.m_146902_().equals(p_140198_.m_7697_())) {
            chunkmap$trackedentity.m_140497_(p_140196_);
            if (entity instanceof Mob && ((Mob)entity).m_21524_() != null) {
               list.add(entity);
            }

            if (!entity.m_20197_().isEmpty()) {
               list1.add(entity);
            }
         }
      }

      if (!list.isEmpty()) {
         for(Entity entity1 : list) {
            p_140196_.f_8906_.m_141995_(new ClientboundSetEntityLinkPacket(entity1, ((Mob)entity1).m_21524_()));
         }
      }

      if (!list1.isEmpty()) {
         for(Entity entity2 : list1) {
            p_140196_.f_8906_.m_141995_(new ClientboundSetPassengersPacket(entity2));
         }
      }

   }

   protected PoiManager m_140424_() {
      return this.f_140138_;
   }

   public String m_182285_() {
      return this.f_182284_;
   }

   public CompletableFuture<Void> m_140270_(LevelChunk p_140271_) {
      return this.f_140135_.m_18707_(() -> {
         p_140271_.m_62823_(this.f_140133_);
      });
   }

   void m_143075_(ChunkPos p_143076_, ChunkHolder.FullChunkStatus p_143077_) {
      this.f_143031_.m_156794_(p_143076_, p_143077_);
   }

   class DistanceManager extends net.minecraft.server.level.DistanceManager {
      protected DistanceManager(Executor p_140459_, Executor p_140460_) {
         super(p_140459_, p_140460_);
      }

      protected boolean m_7009_(long p_140462_) {
         return ChunkMap.this.f_140139_.contains(p_140462_);
      }

      @Nullable
      protected ChunkHolder m_7316_(long p_140469_) {
         return ChunkMap.this.m_140174_(p_140469_);
      }

      @Nullable
      protected ChunkHolder m_7288_(long p_140464_, int p_140465_, @Nullable ChunkHolder p_140466_, int p_140467_) {
         return ChunkMap.this.m_140176_(p_140464_, p_140465_, p_140466_, p_140467_);
      }
   }

   class TrackedEntity {
      final ServerEntity f_140471_;
      final Entity f_140472_;
      private final int f_140473_;
      SectionPos f_140474_;
      private final Set<ServerPlayerConnection> f_140475_ = Sets.newIdentityHashSet();

      public TrackedEntity(Entity p_140478_, int p_140479_, int p_140480_, boolean p_140481_) {
         this.f_140471_ = new ServerEntity(ChunkMap.this.f_140133_, p_140478_, p_140480_, p_140481_, this::m_140489_);
         this.f_140472_ = p_140478_;
         this.f_140473_ = p_140479_;
         this.f_140474_ = SectionPos.m_123194_(p_140478_);
      }

      public boolean equals(Object p_140506_) {
         if (p_140506_ instanceof ChunkMap.TrackedEntity) {
            return ((ChunkMap.TrackedEntity)p_140506_).f_140472_.m_142049_() == this.f_140472_.m_142049_();
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.f_140472_.m_142049_();
      }

      public void m_140489_(Packet<?> p_140490_) {
         for(ServerPlayerConnection serverplayerconnection : this.f_140475_) {
            serverplayerconnection.m_141995_(p_140490_);
         }

      }

      public void m_140499_(Packet<?> p_140500_) {
         this.m_140489_(p_140500_);
         if (this.f_140472_ instanceof ServerPlayer) {
            ((ServerPlayer)this.f_140472_).f_8906_.m_141995_(p_140500_);
         }

      }

      public void m_140482_() {
         for(ServerPlayerConnection serverplayerconnection : this.f_140475_) {
            this.f_140471_.m_8534_(serverplayerconnection.m_142253_());
         }

      }

      public void m_140485_(ServerPlayer p_140486_) {
         if (this.f_140475_.remove(p_140486_.f_8906_)) {
            this.f_140471_.m_8534_(p_140486_);
         }

      }

      public void m_140497_(ServerPlayer p_140498_) {
         if (p_140498_ != this.f_140472_) {
            Vec3 vec3 = p_140498_.m_20182_().m_82546_(this.f_140471_.m_8540_());
            int i = Math.min(this.m_140496_(), (ChunkMap.this.f_140126_ - 1) * 16);
            boolean flag = vec3.f_82479_ >= (double)(-i) && vec3.f_82479_ <= (double)i && vec3.f_82481_ >= (double)(-i) && vec3.f_82481_ <= (double)i && this.f_140472_.m_6459_(p_140498_);
            if (flag) {
               if (this.f_140475_.add(p_140498_.f_8906_)) {
                  this.f_140471_.m_8541_(p_140498_);
               }
            } else if (this.f_140475_.remove(p_140498_.f_8906_)) {
               this.f_140471_.m_8534_(p_140498_);
            }

         }
      }

      private int m_140483_(int p_140484_) {
         return ChunkMap.this.f_140133_.m_142572_().m_7186_(p_140484_);
      }

      private int m_140496_() {
         int i = this.f_140473_;

         for(Entity entity : this.f_140472_.m_146897_()) {
            int j = entity.m_6095_().m_20681_() * 16;
            if (j > i) {
               i = j;
            }
         }

         return this.m_140483_(i);
      }

      public void m_140487_(List<ServerPlayer> p_140488_) {
         for(ServerPlayer serverplayer : p_140488_) {
            this.m_140497_(serverplayer);
         }

      }
   }
}
