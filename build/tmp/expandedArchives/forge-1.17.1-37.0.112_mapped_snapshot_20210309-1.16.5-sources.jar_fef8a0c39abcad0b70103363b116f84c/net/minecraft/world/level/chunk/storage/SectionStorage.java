package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SectionStorage<R> implements AutoCloseable {
   private static final Logger f_63772_ = LogManager.getLogger();
   private static final String f_156617_ = "Sections";
   private final IOWorker f_63773_;
   private final Long2ObjectMap<Optional<R>> f_63774_ = new Long2ObjectOpenHashMap<>();
   private final LongLinkedOpenHashSet f_63775_ = new LongLinkedOpenHashSet();
   private final Function<Runnable, Codec<R>> f_63776_;
   private final Function<Runnable, R> f_63777_;
   private final DataFixer f_63778_;
   private final DataFixTypes f_63779_;
   protected final LevelHeightAccessor f_156618_;

   public SectionStorage(File p_156620_, Function<Runnable, Codec<R>> p_156621_, Function<Runnable, R> p_156622_, DataFixer p_156623_, DataFixTypes p_156624_, boolean p_156625_, LevelHeightAccessor p_156626_) {
      this.f_63776_ = p_156621_;
      this.f_63777_ = p_156622_;
      this.f_63778_ = p_156623_;
      this.f_63779_ = p_156624_;
      this.f_156618_ = p_156626_;
      this.f_63773_ = new IOWorker(p_156620_, p_156625_, p_156620_.getName());
   }

   protected void m_6202_(BooleanSupplier p_63812_) {
      while(!this.f_63775_.isEmpty() && p_63812_.getAsBoolean()) {
         ChunkPos chunkpos = SectionPos.m_123184_(this.f_63775_.firstLong()).m_123251_();
         this.m_63825_(chunkpos);
      }

   }

   @Nullable
   protected Optional<R> m_63818_(long p_63819_) {
      return this.f_63774_.get(p_63819_);
   }

   protected Optional<R> m_63823_(long p_63824_) {
      if (this.m_156630_(p_63824_)) {
         return Optional.empty();
      } else {
         Optional<R> optional = this.m_63818_(p_63824_);
         if (optional != null) {
            return optional;
         } else {
            this.m_63814_(SectionPos.m_123184_(p_63824_).m_123251_());
            optional = this.m_63818_(p_63824_);
            if (optional == null) {
               throw (IllegalStateException)Util.m_137570_(new IllegalStateException());
            } else {
               return optional;
            }
         }
      }
   }

   protected boolean m_156630_(long p_156631_) {
      int i = SectionPos.m_123223_(SectionPos.m_123225_(p_156631_));
      return this.f_156618_.m_151562_(i);
   }

   protected R m_63827_(long p_63828_) {
      if (this.m_156630_(p_63828_)) {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException("sectionPos out of bounds"));
      } else {
         Optional<R> optional = this.m_63823_(p_63828_);
         if (optional.isPresent()) {
            return optional.get();
         } else {
            R r = this.f_63777_.apply(() -> {
               this.m_5838_(p_63828_);
            });
            this.f_63774_.put(p_63828_, Optional.of(r));
            return r;
         }
      }
   }

   private void m_63814_(ChunkPos p_63815_) {
      this.m_63801_(p_63815_, NbtOps.f_128958_, this.m_63820_(p_63815_));
   }

   @Nullable
   private CompoundTag m_63820_(ChunkPos p_63821_) {
      try {
         return this.f_63773_.m_63533_(p_63821_);
      } catch (IOException ioexception) {
         f_63772_.error("Error reading chunk {} data from disk", p_63821_, ioexception);
         return null;
      }
   }

   private <T> void m_63801_(ChunkPos p_63802_, DynamicOps<T> p_63803_, @Nullable T p_63804_) {
      if (p_63804_ == null) {
         for(int i = this.f_156618_.m_151560_(); i < this.f_156618_.m_151561_(); ++i) {
            this.f_63774_.put(m_156627_(p_63802_, i), Optional.empty());
         }
      } else {
         Dynamic<T> dynamic1 = new Dynamic<>(p_63803_, p_63804_);
         int j = m_63805_(dynamic1);
         int k = SharedConstants.m_136187_().getWorldVersion();
         boolean flag = j != k;
         Dynamic<T> dynamic = this.f_63778_.update(this.f_63779_.m_14504_(), dynamic1, j, k);
         OptionalDynamic<T> optionaldynamic = dynamic.get("Sections");

         for(int l = this.f_156618_.m_151560_(); l < this.f_156618_.m_151561_(); ++l) {
            long i1 = m_156627_(p_63802_, l);
            Optional<R> optional = optionaldynamic.get(Integer.toString(l)).result().flatMap((p_63791_) -> {
               return this.f_63776_.apply(() -> {
                  this.m_5838_(i1);
               }).parse(p_63791_).resultOrPartial(f_63772_::error);
            });
            this.f_63774_.put(i1, optional);
            optional.ifPresent((p_63795_) -> {
               this.m_5839_(i1);
               if (flag) {
                  this.m_5838_(i1);
               }

            });
         }
      }

   }

   private void m_63825_(ChunkPos p_63826_) {
      Dynamic<Tag> dynamic = this.m_63798_(p_63826_, NbtOps.f_128958_);
      Tag tag = dynamic.getValue();
      if (tag instanceof CompoundTag) {
         this.f_63773_.m_63538_(p_63826_, (CompoundTag)tag);
      } else {
         f_63772_.error("Expected compound tag, got {}", (Object)tag);
      }

   }

   private <T> Dynamic<T> m_63798_(ChunkPos p_63799_, DynamicOps<T> p_63800_) {
      Map<T, T> map = Maps.newHashMap();

      for(int i = this.f_156618_.m_151560_(); i < this.f_156618_.m_151561_(); ++i) {
         long j = m_156627_(p_63799_, i);
         this.f_63775_.remove(j);
         Optional<R> optional = this.f_63774_.get(j);
         if (optional != null && optional.isPresent()) {
            DataResult<T> dataresult = this.f_63776_.apply(() -> {
               this.m_5838_(j);
            }).encodeStart(p_63800_, optional.get());
            String s = Integer.toString(i);
            dataresult.resultOrPartial(f_63772_::error).ifPresent((p_63811_) -> {
               map.put(p_63800_.createString(s), p_63811_);
            });
         }
      }

      return new Dynamic<>(p_63800_, p_63800_.createMap(ImmutableMap.of(p_63800_.createString("Sections"), p_63800_.createMap(map), p_63800_.createString("DataVersion"), p_63800_.createInt(SharedConstants.m_136187_().getWorldVersion()))));
   }

   private static long m_156627_(ChunkPos p_156628_, int p_156629_) {
      return SectionPos.m_123209_(p_156628_.f_45578_, p_156629_, p_156628_.f_45579_);
   }

   protected void m_5839_(long p_63813_) {
   }

   protected void m_5838_(long p_63788_) {
      Optional<R> optional = this.f_63774_.get(p_63788_);
      if (optional != null && optional.isPresent()) {
         this.f_63775_.add(p_63788_);
      } else {
         f_63772_.warn("No data for position: {}", (Object)SectionPos.m_123184_(p_63788_));
      }
   }

   private static int m_63805_(Dynamic<?> p_63806_) {
      return p_63806_.get("DataVersion").asInt(1945);
   }

   public void m_63796_(ChunkPos p_63797_) {
      if (!this.f_63775_.isEmpty()) {
         for(int i = this.f_156618_.m_151560_(); i < this.f_156618_.m_151561_(); ++i) {
            long j = m_156627_(p_63797_, i);
            if (this.f_63775_.contains(j)) {
               this.m_63825_(p_63797_);
               return;
            }
         }
      }

   }

   public void close() throws IOException {
      this.f_63773_.close();
   }
}