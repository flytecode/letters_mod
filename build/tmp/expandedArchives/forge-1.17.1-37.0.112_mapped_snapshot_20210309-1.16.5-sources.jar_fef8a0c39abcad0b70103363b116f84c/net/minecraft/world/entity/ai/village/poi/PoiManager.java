package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.SectionStorage;

public class PoiManager extends SectionStorage<PoiSection> {
   public static final int f_148565_ = 6;
   public static final int f_148566_ = 1;
   private final PoiManager.DistanceTracker f_27029_;
   private final LongSet f_27030_ = new LongOpenHashSet();

   public PoiManager(File p_148568_, DataFixer p_148569_, boolean p_148570_, LevelHeightAccessor p_148571_) {
      super(p_148568_, PoiSection::m_27295_, PoiSection::new, p_148569_, DataFixTypes.POI_CHUNK, p_148570_, p_148571_);
      this.f_27029_ = new PoiManager.DistanceTracker();
   }

   public void m_27085_(BlockPos p_27086_, PoiType p_27087_) {
      this.m_63827_(SectionPos.m_175568_(p_27086_)).m_27281_(p_27086_, p_27087_);
   }

   public void m_27079_(BlockPos p_27080_) {
      this.m_63823_(SectionPos.m_175568_(p_27080_)).ifPresent((p_148657_) -> {
         p_148657_.m_27279_(p_27080_);
      });
   }

   public long m_27121_(Predicate<PoiType> p_27122_, BlockPos p_27123_, int p_27124_, PoiManager.Occupancy p_27125_) {
      return this.m_27181_(p_27122_, p_27123_, p_27124_, p_27125_).count();
   }

   public boolean m_27044_(PoiType p_27045_, BlockPos p_27046_) {
      return this.m_27091_(p_27046_, p_27045_::equals);
   }

   public Stream<PoiRecord> m_27166_(Predicate<PoiType> p_27167_, BlockPos p_27168_, int p_27169_, PoiManager.Occupancy p_27170_) {
      int i = Math.floorDiv(p_27169_, 16) + 1;
      return ChunkPos.m_45596_(new ChunkPos(p_27168_), i).flatMap((p_148616_) -> {
         return this.m_27117_(p_27167_, p_148616_, p_27170_);
      }).filter((p_148635_) -> {
         BlockPos blockpos = p_148635_.m_27257_();
         return Math.abs(blockpos.m_123341_() - p_27168_.m_123341_()) <= p_27169_ && Math.abs(blockpos.m_123343_() - p_27168_.m_123343_()) <= p_27169_;
      });
   }

   public Stream<PoiRecord> m_27181_(Predicate<PoiType> p_27182_, BlockPos p_27183_, int p_27184_, PoiManager.Occupancy p_27185_) {
      int i = p_27184_ * p_27184_;
      return this.m_27166_(p_27182_, p_27183_, p_27184_, p_27185_).filter((p_148598_) -> {
         return p_148598_.m_27257_().m_123331_(p_27183_) <= (double)i;
      });
   }

   @VisibleForDebug
   public Stream<PoiRecord> m_27117_(Predicate<PoiType> p_27118_, ChunkPos p_27119_, PoiManager.Occupancy p_27120_) {
      return IntStream.range(this.f_156618_.m_151560_(), this.f_156618_.m_151561_()).boxed().map((p_148578_) -> {
         return this.m_63823_(SectionPos.m_123196_(p_27119_, p_148578_).m_123252_());
      }).filter(Optional::isPresent).flatMap((p_148620_) -> {
         return p_148620_.get().m_27304_(p_27118_, p_27120_);
      });
   }

   public Stream<BlockPos> m_27138_(Predicate<PoiType> p_27139_, Predicate<BlockPos> p_27140_, BlockPos p_27141_, int p_27142_, PoiManager.Occupancy p_27143_) {
      return this.m_27181_(p_27139_, p_27141_, p_27142_, p_27143_).map(PoiRecord::m_27257_).filter(p_27140_);
   }

   public Stream<BlockPos> m_27171_(Predicate<PoiType> p_27172_, Predicate<BlockPos> p_27173_, BlockPos p_27174_, int p_27175_, PoiManager.Occupancy p_27176_) {
      return this.m_27138_(p_27172_, p_27173_, p_27174_, p_27175_, p_27176_).sorted(Comparator.comparingDouble((p_148652_) -> {
         return p_148652_.m_123331_(p_27174_);
      }));
   }

   public Optional<BlockPos> m_27186_(Predicate<PoiType> p_27187_, Predicate<BlockPos> p_27188_, BlockPos p_27189_, int p_27190_, PoiManager.Occupancy p_27191_) {
      return this.m_27138_(p_27187_, p_27188_, p_27189_, p_27190_, p_27191_).findFirst();
   }

   public Optional<BlockPos> m_27192_(Predicate<PoiType> p_27193_, BlockPos p_27194_, int p_27195_, PoiManager.Occupancy p_27196_) {
      return this.m_27181_(p_27193_, p_27194_, p_27195_, p_27196_).map(PoiRecord::m_27257_).min(Comparator.comparingDouble((p_148641_) -> {
         return p_148641_.m_123331_(p_27194_);
      }));
   }

   public Optional<BlockPos> m_148658_(Predicate<PoiType> p_148659_, Predicate<BlockPos> p_148660_, BlockPos p_148661_, int p_148662_, PoiManager.Occupancy p_148663_) {
      return this.m_27181_(p_148659_, p_148661_, p_148662_, p_148663_).map(PoiRecord::m_27257_).filter(p_148660_).min(Comparator.comparingDouble((p_148604_) -> {
         return p_148604_.m_123331_(p_148661_);
      }));
   }

   public Optional<BlockPos> m_27133_(Predicate<PoiType> p_27134_, Predicate<BlockPos> p_27135_, BlockPos p_27136_, int p_27137_) {
      return this.m_27181_(p_27134_, p_27136_, p_27137_, PoiManager.Occupancy.HAS_SPACE).filter((p_148646_) -> {
         return p_27135_.test(p_148646_.m_27257_());
      }).findFirst().map((p_148573_) -> {
         p_148573_.m_27247_();
         return p_148573_.m_27257_();
      });
   }

   public Optional<BlockPos> m_27126_(Predicate<PoiType> p_27127_, Predicate<BlockPos> p_27128_, PoiManager.Occupancy p_27129_, BlockPos p_27130_, int p_27131_, Random p_27132_) {
      List<PoiRecord> list = this.m_27181_(p_27127_, p_27130_, p_27131_, p_27129_).collect(Collectors.toList());
      Collections.shuffle(list, p_27132_);
      return list.stream().filter((p_148623_) -> {
         return p_27128_.test(p_148623_.m_27257_());
      }).findFirst().map(PoiRecord::m_27257_);
   }

   public boolean m_27154_(BlockPos p_27155_) {
      return this.m_63823_(SectionPos.m_175568_(p_27155_)).map((p_148649_) -> {
         return p_148649_.m_27317_(p_27155_);
      }).orElseThrow(() -> {
         return Util.m_137570_(new IllegalStateException("POI never registered at " + p_27155_));
      });
   }

   public boolean m_27091_(BlockPos p_27092_, Predicate<PoiType> p_27093_) {
      return this.m_63823_(SectionPos.m_175568_(p_27092_)).map((p_148608_) -> {
         return p_148608_.m_27288_(p_27092_, p_27093_);
      }).orElse(false);
   }

   public Optional<PoiType> m_27177_(BlockPos p_27178_) {
      return this.m_63823_(SectionPos.m_175568_(p_27178_)).flatMap((p_148638_) -> {
         return p_148638_.m_27319_(p_27178_);
      });
   }

   @Deprecated
   @VisibleForDebug
   public int m_148653_(BlockPos p_148654_) {
      return this.m_63823_(SectionPos.m_175568_(p_148654_)).map((p_148601_) -> {
         return p_148601_.m_148682_(p_148654_);
      }).orElse(0);
   }

   public int m_27098_(SectionPos p_27099_) {
      this.f_27029_.m_27203_();
      return this.f_27029_.m_6172_(p_27099_.m_123252_());
   }

   boolean m_27197_(long p_27198_) {
      Optional<PoiSection> optional = this.m_63818_(p_27198_);
      return optional == null ? false : optional.map((p_148575_) -> {
         return p_148575_.m_27304_(PoiType.f_27330_, PoiManager.Occupancy.IS_OCCUPIED).count() > 0L;
      }).orElse(false);
   }

   public void m_6202_(BooleanSupplier p_27105_) {
      super.m_6202_(p_27105_);
      this.f_27029_.m_27203_();
   }

   protected void m_5838_(long p_27036_) {
      super.m_5838_(p_27036_);
      this.f_27029_.m_8288_(p_27036_, this.f_27029_.m_7409_(p_27036_), false);
   }

   protected void m_5839_(long p_27145_) {
      this.f_27029_.m_8288_(p_27145_, this.f_27029_.m_7409_(p_27145_), false);
   }

   public void m_27047_(ChunkPos p_27048_, LevelChunkSection p_27049_) {
      SectionPos sectionpos = SectionPos.m_123196_(p_27048_, SectionPos.m_123171_(p_27049_.m_63017_()));
      Util.m_137521_(this.m_63823_(sectionpos.m_123252_()), (p_148588_) -> {
         p_148588_.m_27302_((p_148629_) -> {
            if (m_27060_(p_27049_)) {
               this.m_27069_(p_27049_, sectionpos, p_148629_);
            }

         });
      }, () -> {
         if (m_27060_(p_27049_)) {
            PoiSection poisection = this.m_63827_(sectionpos.m_123252_());
            this.m_27069_(p_27049_, sectionpos, poisection::m_27281_);
         }

      });
   }

   private static boolean m_27060_(LevelChunkSection p_27061_) {
      return p_27061_.m_63002_(PoiType.f_27352_::contains);
   }

   private void m_27069_(LevelChunkSection p_27070_, SectionPos p_27071_, BiConsumer<BlockPos, PoiType> p_27072_) {
      p_27071_.m_123253_().forEach((p_148592_) -> {
         BlockState blockstate = p_27070_.m_62982_(SectionPos.m_123207_(p_148592_.m_123341_()), SectionPos.m_123207_(p_148592_.m_123342_()), SectionPos.m_123207_(p_148592_.m_123343_()));
         PoiType.m_27390_(blockstate).ifPresent((p_148612_) -> {
            p_27072_.accept(p_148592_, p_148612_);
         });
      });
   }

   public void m_27056_(LevelReader p_27057_, BlockPos p_27058_, int p_27059_) {
      SectionPos.m_175557_(new ChunkPos(p_27058_), Math.floorDiv(p_27059_, 16), this.f_156618_.m_151560_(), this.f_156618_.m_151561_()).map((p_148643_) -> {
         return Pair.of(p_148643_, this.m_63823_(p_148643_.m_123252_()));
      }).filter((p_148631_) -> {
         return !p_148631_.getSecond().map(PoiSection::m_27272_).orElse(false);
      }).map((p_148594_) -> {
         return p_148594_.getFirst().m_123251_();
      }).filter((p_148625_) -> {
         return this.f_27030_.add(p_148625_.m_45588_());
      }).forEach((p_148581_) -> {
         p_27057_.m_46819_(p_148581_.f_45578_, p_148581_.f_45579_, ChunkStatus.f_62314_);
      });
   }

   final class DistanceTracker extends SectionTracker {
      private final Long2ByteMap f_27200_ = new Long2ByteOpenHashMap();

      protected DistanceTracker() {
         super(7, 16, 256);
         this.f_27200_.defaultReturnValue((byte)7);
      }

      protected int m_7409_(long p_27208_) {
         return PoiManager.this.m_27197_(p_27208_) ? 0 : 7;
      }

      protected int m_6172_(long p_27210_) {
         return this.f_27200_.get(p_27210_);
      }

      protected void m_7351_(long p_27205_, int p_27206_) {
         if (p_27206_ > 6) {
            this.f_27200_.remove(p_27205_);
         } else {
            this.f_27200_.put(p_27205_, (byte)p_27206_);
         }

      }

      public void m_27203_() {
         super.m_75588_(Integer.MAX_VALUE);
      }
   }

   public static enum Occupancy {
      HAS_SPACE(PoiRecord::m_27253_),
      IS_OCCUPIED(PoiRecord::m_27254_),
      ANY((p_27223_) -> {
         return true;
      });

      private final Predicate<? super PoiRecord> f_27214_;

      private Occupancy(Predicate<? super PoiRecord> p_27220_) {
         this.f_27214_ = p_27220_;
      }

      public Predicate<? super PoiRecord> m_27221_() {
         return this.f_27214_;
      }
   }
}