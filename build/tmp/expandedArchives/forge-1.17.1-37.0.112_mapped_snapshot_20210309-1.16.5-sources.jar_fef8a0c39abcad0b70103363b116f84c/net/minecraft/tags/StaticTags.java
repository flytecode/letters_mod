package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class StaticTags {
   private static final java.util.Map<ResourceKey<?>, StaticTagHelper<?>> HELPER_MAP = new java.util.HashMap<>(); // Forge: Minecraft separates this for no reason, lets make it a map again!
   private static final Set<ResourceKey<?>> f_144340_ = HELPER_MAP.keySet();
   private static final java.util.Collection<StaticTagHelper<?>> f_13264_ = HELPER_MAP.values();

   public static <T> StaticTagHelper<T> m_144351_(ResourceKey<? extends Registry<T>> p_144352_, String p_144353_) {
      if (f_144340_.contains(p_144352_)) {
         throw new IllegalStateException("Duplicate entry for static tag collection: " + p_144352_);
      } else {
         StaticTagHelper<T> statictaghelper = new StaticTagHelper<>(p_144352_, p_144353_);
         HELPER_MAP.put(p_144352_, statictaghelper);
         return statictaghelper;
      }
   }

   public static void m_13269_(TagContainer p_13270_) {
      f_13264_.forEach((p_13273_) -> {
         p_13273_.m_13242_(p_13270_);
      });
   }

   public static void m_13266_() {
      f_13264_.forEach(StaticTagHelper::m_13232_);
   }

   public static Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> m_13283_(TagContainer p_13284_) {
      Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> multimap = HashMultimap.create();
      f_13264_.forEach((p_144348_) -> {
         multimap.putAll(p_144348_.m_144338_(), p_144348_.m_13247_(p_13284_));
      });
      return multimap;
   }

   public static void m_13282_() {
      m_144356_();
   }

   private static Set<StaticTagHelper<?>> m_144355_() {
      return ImmutableSet.of(BlockTags.f_13053_, ItemTags.f_13163_, FluidTags.f_13130_, EntityTypeTags.f_13119_, GameEventTags.f_144301_);
   }

   private static void m_144356_() {
      if (true) { m_144355_(); return; } //FORGE this check is unnecessary as the 2 collections are patched back into a map above. Still need to 'clinit' them though.
      Set<ResourceKey<?>> set = m_144355_().stream().map(StaticTagHelper::m_144338_).collect(Collectors.toSet());
      if (!Sets.difference(f_144340_, set).isEmpty()) {
         throw new IllegalStateException("Missing helper registrations");
      }
   }

   @javax.annotation.Nullable
   public static StaticTagHelper<?> get(ResourceLocation rl) {
      return HELPER_MAP.get(ResourceKey.m_135788_(rl));
   }

   public static void m_144349_(Consumer<StaticTagHelper<?>> p_144350_) {
      f_13264_.forEach(p_144350_);
   }

   public static TagContainer m_144354_() {
      TagContainer.Builder tagcontainer$builder = new TagContainer.Builder();
      m_144356_();
      f_13264_.forEach((p_144344_) -> {
         p_144344_.m_144336_(tagcontainer$builder);
      });
      return tagcontainer$builder.m_144485_();
   }
}
