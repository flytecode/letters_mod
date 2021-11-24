package net.minecraft.data;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuiltinRegistries {
   protected static final Logger f_123857_ = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> f_123867_ = Maps.newLinkedHashMap();
   private static final WritableRegistry<WritableRegistry<?>> f_123868_ = new MappedRegistry<>(ResourceKey.m_135788_(new ResourceLocation("root")), Lifecycle.experimental());
   public static final Registry<? extends Registry<?>> f_123858_ = f_123868_;
   public static final Registry<ConfiguredSurfaceBuilder<?>> f_123859_ = m_123893_(Registry.f_122879_, () -> {
      return SurfaceBuilders.f_127291_;
   });
   public static final Registry<ConfiguredWorldCarver<?>> f_123860_ = m_123893_(Registry.f_122880_, () -> {
      return Carvers.f_126848_;
   });
   public static final Registry<ConfiguredFeature<?, ?>> f_123861_ = m_123893_(Registry.f_122881_, () -> {
      return Features.f_126948_;
   });
   public static final Registry<ConfiguredStructureFeature<?, ?>> f_123862_ = m_123893_(Registry.f_122882_, () -> {
      return StructureFeatures.f_127240_;
   });
   public static final Registry<StructureProcessorList> f_123863_ = m_123893_(Registry.f_122883_, () -> {
      return ProcessorLists.f_127199_;
   });
   public static final Registry<StructureTemplatePool> f_123864_ = m_123893_(Registry.f_122884_, Pools::m_127189_);
   @Deprecated public static final Registry<Biome> f_123865_ = forge(Registry.f_122885_, () -> {
      return Biomes.f_127321_;
   });
   public static final Registry<NoiseGeneratorSettings> f_123866_ = m_123893_(Registry.f_122878_, NoiseGeneratorSettings::m_64488_);

   private static <T> Registry<T> m_123893_(ResourceKey<? extends Registry<T>> p_123894_, Supplier<T> p_123895_) {
      return m_123884_(p_123894_, Lifecycle.stable(), p_123895_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(ResourceKey<? extends Registry<T>> key, Supplier<T> def) {
      return m_123888_(key, net.minecraftforge.registries.GameData.getWrapper(key, Lifecycle.stable()), def, Lifecycle.stable());
   }

   private static <T> Registry<T> m_123884_(ResourceKey<? extends Registry<T>> p_123885_, Lifecycle p_123886_, Supplier<T> p_123887_) {
      return m_123888_(p_123885_, new MappedRegistry<>(p_123885_, p_123886_), p_123887_, p_123886_);
   }

   private static <T, R extends WritableRegistry<T>> R m_123888_(ResourceKey<? extends Registry<T>> p_123889_, R p_123890_, Supplier<T> p_123891_, Lifecycle p_123892_) {
      ResourceLocation resourcelocation = p_123889_.m_135782_();
      f_123867_.put(resourcelocation, p_123891_);
      WritableRegistry<R> writableregistry = (WritableRegistry<R>)f_123868_;
      return (R)writableregistry.m_7135_((ResourceKey)p_123889_, p_123890_, p_123892_);
   }

   public static <T> T m_123876_(Registry<? super T> p_123877_, String p_123878_, T p_123879_) {
      return m_123880_(p_123877_, new ResourceLocation(p_123878_), p_123879_);
   }

   public static <V, T extends V> T m_123880_(Registry<V> p_123881_, ResourceLocation p_123882_, T p_123883_) {
      return ((WritableRegistry<V>)p_123881_).m_7135_(ResourceKey.m_135785_(p_123881_.m_123023_(), p_123882_), p_123883_, Lifecycle.stable());
   }

   public static <V, T extends V> T m_123871_(Registry<V> p_123872_, int p_123873_, ResourceKey<V> p_123874_, T p_123875_) {
      return ((WritableRegistry<V>)p_123872_).m_5748_(p_123873_, p_123874_, p_123875_, Lifecycle.stable());
   }

   public static void m_123870_() {
   }

   static {
      f_123867_.forEach((p_123897_, p_123898_) -> {
         if (p_123898_.get() == null) {
            f_123857_.error("Unable to bootstrap registry '{}'", (Object)p_123897_);
         }

      });
      Registry.m_122969_(f_123868_);
   }
}
