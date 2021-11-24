package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoiSection {
   private static final Logger f_27260_ = LogManager.getLogger();
   private final Short2ObjectMap<PoiRecord> f_27261_ = new Short2ObjectOpenHashMap<>();
   private final Map<PoiType, Set<PoiRecord>> f_27262_ = Maps.newHashMap();
   private final Runnable f_27263_;
   private boolean f_27264_;

   public static Codec<PoiSection> m_27295_(Runnable p_27296_) {
      return RecordCodecBuilder.<PoiSection>create((p_27299_) -> {
         return p_27299_.group(RecordCodecBuilder.point(p_27296_), Codec.BOOL.optionalFieldOf("Valid", Boolean.valueOf(false)).forGetter((p_148681_) -> {
            return p_148681_.f_27264_;
         }), PoiRecord.m_27242_(p_27296_).listOf().fieldOf("Records").forGetter((p_148675_) -> {
            return ImmutableList.copyOf(p_148675_.f_27261_.values());
         })).apply(p_27299_, PoiSection::new);
      }).orElseGet(Util.m_137489_("Failed to read POI section: ", f_27260_::error), () -> {
         return new PoiSection(p_27296_, false, ImmutableList.of());
      });
   }

   public PoiSection(Runnable p_27267_) {
      this(p_27267_, true, ImmutableList.of());
   }

   private PoiSection(Runnable p_27269_, boolean p_27270_, List<PoiRecord> p_27271_) {
      this.f_27263_ = p_27269_;
      this.f_27264_ = p_27270_;
      p_27271_.forEach(this::m_27273_);
   }

   public Stream<PoiRecord> m_27304_(Predicate<PoiType> p_27305_, PoiManager.Occupancy p_27306_) {
      return this.f_27262_.entrySet().stream().filter((p_27309_) -> {
         return p_27305_.test(p_27309_.getKey());
      }).flatMap((p_27301_) -> {
         return p_27301_.getValue().stream();
      }).filter(p_27306_.m_27221_());
   }

   public void m_27281_(BlockPos p_27282_, PoiType p_27283_) {
      if (this.m_27273_(new PoiRecord(p_27282_, p_27283_, this.f_27263_))) {
         f_27260_.debug("Added POI of type {} @ {}", () -> {
            return p_27283_;
         }, () -> {
            return p_27282_;
         });
         this.f_27263_.run();
      }

   }

   private boolean m_27273_(PoiRecord p_27274_) {
      BlockPos blockpos = p_27274_.m_27257_();
      PoiType poitype = p_27274_.m_27258_();
      short short1 = SectionPos.m_123218_(blockpos);
      PoiRecord poirecord = this.f_27261_.get(short1);
      if (poirecord != null) {
         if (poitype.equals(poirecord.m_27258_())) {
            return false;
         }

         Util.m_143785_("POI data mismatch: already registered at " + blockpos);
      }

      this.f_27261_.put(short1, p_27274_);
      this.f_27262_.computeIfAbsent(poitype, (p_27278_) -> {
         return Sets.newHashSet();
      }).add(p_27274_);
      return true;
   }

   public void m_27279_(BlockPos p_27280_) {
      PoiRecord poirecord = this.f_27261_.remove(SectionPos.m_123218_(p_27280_));
      if (poirecord == null) {
         f_27260_.error("POI data mismatch: never registered at {}", (Object)p_27280_);
      } else {
         this.f_27262_.get(poirecord.m_27258_()).remove(poirecord);
         f_27260_.debug("Removed POI of type {} @ {}", poirecord::m_27258_, poirecord::m_27257_);
         this.f_27263_.run();
      }
   }

   @Deprecated
   @VisibleForDebug
   public int m_148682_(BlockPos p_148683_) {
      return this.m_148684_(p_148683_).map(PoiRecord::m_148667_).orElse(0);
   }

   public boolean m_27317_(BlockPos p_27318_) {
      PoiRecord poirecord = this.f_27261_.get(SectionPos.m_123218_(p_27318_));
      if (poirecord == null) {
         throw (IllegalStateException)Util.m_137570_(new IllegalStateException("POI never registered at " + p_27318_));
      } else {
         boolean flag = poirecord.m_27250_();
         this.f_27263_.run();
         return flag;
      }
   }

   public boolean m_27288_(BlockPos p_27289_, Predicate<PoiType> p_27290_) {
      return this.m_27319_(p_27289_).filter(p_27290_).isPresent();
   }

   public Optional<PoiType> m_27319_(BlockPos p_27320_) {
      return this.m_148684_(p_27320_).map(PoiRecord::m_27258_);
   }

   private Optional<PoiRecord> m_148684_(BlockPos p_148685_) {
      return Optional.ofNullable(this.f_27261_.get(SectionPos.m_123218_(p_148685_)));
   }

   public void m_27302_(Consumer<BiConsumer<BlockPos, PoiType>> p_27303_) {
      if (!this.f_27264_) {
         Short2ObjectMap<PoiRecord> short2objectmap = new Short2ObjectOpenHashMap<>(this.f_27261_);
         this.m_27310_();
         p_27303_.accept((p_27293_, p_27294_) -> {
            short short1 = SectionPos.m_123218_(p_27293_);
            PoiRecord poirecord = short2objectmap.computeIfAbsent(short1, (p_148679_) -> {
               return new PoiRecord(p_27293_, p_27294_, this.f_27263_);
            });
            this.m_27273_(poirecord);
         });
         this.f_27264_ = true;
         this.f_27263_.run();
      }

   }

   private void m_27310_() {
      this.f_27261_.clear();
      this.f_27262_.clear();
   }

   boolean m_27272_() {
      return this.f_27264_;
   }
}