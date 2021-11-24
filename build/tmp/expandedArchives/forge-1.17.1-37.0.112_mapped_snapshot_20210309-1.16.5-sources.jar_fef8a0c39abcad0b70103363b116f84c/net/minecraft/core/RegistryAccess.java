package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RegistryAccess {
   private static final Logger f_123047_ = LogManager.getLogger();
   static final Map<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> f_123048_ = Util.m_137537_(() -> {
      Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> builder = ImmutableMap.builder();
      m_123057_(builder, Registry.f_122818_, DimensionType.f_63843_, DimensionType.f_63843_);
      m_123057_(builder, Registry.f_122885_, Biome.f_47429_, Biome.f_47430_);
      m_123053_(builder, Registry.f_122879_, ConfiguredSurfaceBuilder.f_74762_);
      m_123053_(builder, Registry.f_122880_, ConfiguredWorldCarver.f_64846_);
      m_123053_(builder, Registry.f_122881_, ConfiguredFeature.f_65373_);
      m_123053_(builder, Registry.f_122882_, ConfiguredStructureFeature.f_65400_);
      m_123053_(builder, Registry.f_122883_, StructureProcessorType.f_74467_);
      m_123053_(builder, Registry.f_122884_, StructureTemplatePool.f_69245_);
      m_123053_(builder, Registry.f_122878_, NoiseGeneratorSettings.f_64430_);
      return builder.build();
   });
   private static final RegistryAccess.RegistryHolder f_123049_ = Util.m_137537_(() -> {
      RegistryAccess.RegistryHolder registryaccess$registryholder = new RegistryAccess.RegistryHolder();
      DimensionType.m_63926_(registryaccess$registryholder);
      f_123048_.keySet().stream().filter((p_175518_) -> {
         return !p_175518_.equals(Registry.f_122818_);
      }).forEach((p_175511_) -> {
         m_123078_(registryaccess$registryholder, p_175511_);
      });
      return registryaccess$registryholder;
   });

   public abstract <E> Optional<WritableRegistry<E>> m_142664_(ResourceKey<? extends Registry<? extends E>> p_175507_);

   public <E> WritableRegistry<E> m_175512_(ResourceKey<? extends Registry<? extends E>> p_175513_) {
      return this.m_142664_(p_175513_).orElseThrow(() -> {
         return new IllegalStateException("Missing registry: " + p_175513_);
      });
   }

   public <E> Optional<? extends Registry<E>> m_6632_(ResourceKey<? extends Registry<? extends E>> p_123085_) {
      Optional<? extends Registry<E>> optional = this.m_142664_(p_123085_);
      return optional.isPresent() ? optional : (Optional<? extends Registry<E>>)Registry.f_122897_.m_6612_(p_123085_.m_135782_());
   }

   public <E> Registry<E> m_175515_(ResourceKey<? extends Registry<? extends E>> p_175516_) {
      return this.m_6632_(p_175516_).orElseThrow(() -> {
         return new IllegalStateException("Missing registry: " + p_175516_);
      });
   }

   private static <E> void m_123053_(Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> p_123054_, ResourceKey<? extends Registry<E>> p_123055_, Codec<E> p_123056_) {
      p_123054_.put(p_123055_, new RegistryAccess.RegistryData<>(p_123055_, p_123056_, (Codec<E>)null));
   }

   private static <E> void m_123057_(Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> p_123058_, ResourceKey<? extends Registry<E>> p_123059_, Codec<E> p_123060_, Codec<E> p_123061_) {
      p_123058_.put(p_123059_, new RegistryAccess.RegistryData<>(p_123059_, p_123060_, p_123061_));
   }

   public static RegistryAccess.RegistryHolder m_123086_() {
      RegistryAccess.RegistryHolder registryaccess$registryholder = new RegistryAccess.RegistryHolder();
      RegistryReadOps.ResourceAccess.MemoryMap registryreadops$resourceaccess$memorymap = new RegistryReadOps.ResourceAccess.MemoryMap();

      for(RegistryAccess.RegistryData<?> registrydata : f_123048_.values()) {
         m_123071_(registryaccess$registryholder, registryreadops$resourceaccess$memorymap, registrydata);
      }

      RegistryReadOps.m_179870_(JsonOps.INSTANCE, registryreadops$resourceaccess$memorymap, registryaccess$registryholder);
      return registryaccess$registryholder;
   }

   private static <E> void m_123071_(RegistryAccess.RegistryHolder p_123072_, RegistryReadOps.ResourceAccess.MemoryMap p_123073_, RegistryAccess.RegistryData<E> p_123074_) {
      ResourceKey<? extends Registry<E>> resourcekey = p_123074_.m_123108_();
      boolean flag = !resourcekey.equals(Registry.f_122878_) && !resourcekey.equals(Registry.f_122818_);
      Registry<E> registry = f_123049_.m_175515_(resourcekey);
      if (!resourcekey.equals(Registry.f_122818_))
         registry = ((Registry<Registry<E>>)BuiltinRegistries.f_123858_).m_6246_((ResourceKey<Registry<E>>)resourcekey);
      WritableRegistry<E> writableregistry = p_123072_.m_175512_(resourcekey);

      for(Entry<ResourceKey<E>, E> entry : registry.m_6579_()) {
         ResourceKey<E> resourcekey1 = entry.getKey();
         E e = entry.getValue();
         if (flag) {
            p_123073_.m_135745_(f_123049_, resourcekey1, p_123074_.m_123109_(), registry.m_7447_(e), e, registry.m_6228_(e));
         } else {
            writableregistry.m_5748_(registry.m_7447_(e), resourcekey1, e, registry.m_6228_(e));
         }
      }

   }

   private static <R extends Registry<?>> void m_123078_(RegistryAccess.RegistryHolder p_123079_, ResourceKey<R> p_123080_) {
      Registry<R> registry = (Registry<R>)BuiltinRegistries.f_123858_;
      Registry<?> registry1 = registry.m_123013_(p_123080_);
      m_123068_(p_123079_, registry1);
   }

   private static <E> void m_123068_(RegistryAccess.RegistryHolder p_123069_, Registry<E> p_123070_) {
      WritableRegistry<E> writableregistry = p_123069_.m_175512_(p_123070_.m_123023_());

      for(Entry<ResourceKey<E>, E> entry : p_123070_.m_6579_()) {
         E e = entry.getValue();
         writableregistry.m_5748_(p_123070_.m_7447_(e), entry.getKey(), e, p_123070_.m_6228_(e));
      }

   }

   public static void m_175500_(RegistryAccess p_175501_, RegistryReadOps<?> p_175502_) {
      for(RegistryAccess.RegistryData<?> registrydata : f_123048_.values()) {
         m_175503_(p_175502_, p_175501_, registrydata);
      }

   }

   private static <E> void m_175503_(RegistryReadOps<?> p_175504_, RegistryAccess p_175505_, RegistryAccess.RegistryData<E> p_175506_) {
      ResourceKey<? extends Registry<E>> resourcekey = p_175506_.m_123108_();
      MappedRegistry<E> mappedregistry = (MappedRegistry)p_175505_.<E>m_175512_(resourcekey);
      DataResult<MappedRegistry<E>> dataresult = p_175504_.m_135662_(mappedregistry, p_175506_.m_123108_(), p_175506_.m_123109_());
      dataresult.error().ifPresent((p_175499_) -> {
         throw new JsonParseException("Error loading registry data: " + p_175499_.message());
      });
   }

   static final class RegistryData<E> {
      private final ResourceKey<? extends Registry<E>> f_123101_;
      private final Codec<E> f_123102_;
      @Nullable
      private final Codec<E> f_123103_;

      public RegistryData(ResourceKey<? extends Registry<E>> p_123105_, Codec<E> p_123106_, @Nullable Codec<E> p_123107_) {
         this.f_123101_ = p_123105_;
         this.f_123102_ = p_123106_;
         this.f_123103_ = p_123107_;
      }

      public ResourceKey<? extends Registry<E>> m_123108_() {
         return this.f_123101_;
      }

      public Codec<E> m_123109_() {
         return this.f_123102_;
      }

      @Nullable
      public Codec<E> m_123110_() {
         return this.f_123103_;
      }

      public boolean m_123111_() {
         return this.f_123103_ != null;
      }
   }

   public static final class RegistryHolder extends RegistryAccess {
      public static final Codec<RegistryAccess.RegistryHolder> f_123112_ = m_123139_();
      private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> f_123113_;

      private static <E> Codec<RegistryAccess.RegistryHolder> m_123139_() {
         Codec<ResourceKey<? extends Registry<E>>> codec = ResourceLocation.f_135803_.xmap(ResourceKey::m_135788_, ResourceKey::m_135782_);
         Codec<MappedRegistry<E>> codec1 = codec.partialDispatch("type", (p_123134_) -> {
            return DataResult.success(p_123134_.m_123023_());
         }, (p_123145_) -> {
            return m_123137_(p_123145_).map((p_175531_) -> {
               return MappedRegistry.m_122715_(p_123145_, Lifecycle.experimental(), p_175531_);
            });
         });
         UnboundedMapCodec<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> unboundedmapcodec = Codec.unboundedMap(codec, codec1);
         return m_123118_(unboundedmapcodec);
      }

      private static <K extends ResourceKey<? extends Registry<?>>, V extends MappedRegistry<?>> Codec<RegistryAccess.RegistryHolder> m_123118_(UnboundedMapCodec<K, V> p_123119_) {
         return p_123119_.xmap(RegistryAccess.RegistryHolder::new, (p_123136_) -> {
            return ((Map<K, V>)p_123136_.f_123113_).entrySet().stream().filter((p_175526_) -> {
               return RegistryAccess.f_123048_.get(p_175526_.getKey()).m_123111_();
            }).collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue));
         });
      }

      private static <E> DataResult<? extends Codec<E>> m_123137_(ResourceKey<? extends Registry<E>> p_123138_) {
         return Optional.ofNullable(RegistryAccess.f_123048_.get(p_123138_)).map((p_123123_) -> {
            return (Codec<E>)p_123123_.m_123110_();
         }).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown or not serializable registry: " + p_123138_);
         });
      }

      public RegistryHolder() {
         this(RegistryAccess.f_123048_.keySet().stream().collect(Collectors.toMap(Function.identity(), RegistryAccess.RegistryHolder::m_123140_)));
      }

      private RegistryHolder(Map<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> p_123117_) {
         this.f_123113_ = p_123117_;
      }

      private static <E> MappedRegistry<?> m_123140_(ResourceKey<? extends Registry<?>> p_123141_) {
         return new MappedRegistry(p_123141_, Lifecycle.stable());
      }

      public <E> Optional<WritableRegistry<E>> m_142664_(ResourceKey<? extends Registry<? extends E>> p_175528_) {
         return Optional.ofNullable(this.f_123113_.get(p_175528_)).map((p_175524_) -> {
            return (WritableRegistry<E>)p_175524_;
         });
      }
   }
}
