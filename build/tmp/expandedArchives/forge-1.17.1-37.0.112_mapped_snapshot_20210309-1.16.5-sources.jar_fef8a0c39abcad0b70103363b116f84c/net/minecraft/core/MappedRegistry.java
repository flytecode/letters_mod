package net.minecraft.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.RegistryDataPackCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MappedRegistry<T> extends WritableRegistry<T> {
   protected static final Logger f_122670_ = LogManager.getLogger();
   private final ObjectList<T> f_122672_ = new ObjectArrayList<>(256);
   private final Object2IntMap<T> f_122673_ = new Object2IntOpenCustomHashMap<>(Util.m_137583_());
   private final BiMap<ResourceLocation, T> f_122674_;
   private final BiMap<ResourceKey<T>, T> f_122675_;
   private final Map<T, Lifecycle> f_122676_;
   private Lifecycle f_122677_;
   protected Object[] f_122671_;
   private int f_122678_;

   public MappedRegistry(ResourceKey<? extends Registry<T>> p_122681_, Lifecycle p_122682_) {
      super(p_122681_, p_122682_);
      this.f_122673_.defaultReturnValue(-1);
      this.f_122674_ = HashBiMap.create();
      this.f_122675_ = HashBiMap.create();
      this.f_122676_ = Maps.newIdentityHashMap();
      this.f_122677_ = p_122682_;
   }

   public static <T> MapCodec<MappedRegistry.RegistryEntry<T>> m_122727_(ResourceKey<? extends Registry<T>> p_122728_, MapCodec<T> p_122729_) {
      return RecordCodecBuilder.mapCodec((p_122733_) -> {
         return p_122733_.group(ResourceLocation.f_135803_.xmap(ResourceKey.m_135797_(p_122728_), ResourceKey::m_135782_).fieldOf("name").forGetter((p_175394_) -> {
            return p_175394_.f_122766_;
         }), Codec.INT.fieldOf("id").forGetter((p_175390_) -> {
            return p_175390_.f_122767_;
         }), p_122729_.forGetter((p_175383_) -> {
            return p_175383_.f_122768_;
         })).apply(p_122733_, MappedRegistry.RegistryEntry::new);
      });
   }

   public <V extends T> V m_5748_(int p_122686_, ResourceKey<T> p_122687_, V p_122688_, Lifecycle p_122689_) {
      return this.m_122690_(p_122686_, p_122687_, p_122688_, p_122689_, true);
   }

   private <V extends T> V m_122690_(int p_122691_, ResourceKey<T> p_122692_, V p_122693_, Lifecycle p_122694_, boolean p_122695_) {
      Validate.notNull(p_122692_);
      Validate.notNull((T)p_122693_);
      this.f_122672_.size(Math.max(this.f_122672_.size(), p_122691_ + 1));
      this.f_122672_.set(p_122691_, p_122693_);
      this.f_122673_.put((T)p_122693_, p_122691_);
      this.f_122671_ = null;
      if (p_122695_ && this.f_122675_.containsKey(p_122692_)) {
         f_122670_.debug("Adding duplicate key '{}' to registry", (Object)p_122692_);
      }

      if (this.f_122674_.containsValue(p_122693_)) {
         f_122670_.error("Adding duplicate value '{}' to registry", p_122693_);
      }

      this.f_122674_.put(p_122692_.m_135782_(), (T)p_122693_);
      this.f_122675_.put(p_122692_, (T)p_122693_);
      this.f_122676_.put((T)p_122693_, p_122694_);
      this.f_122677_ = this.f_122677_.add(p_122694_);
      if (this.f_122678_ <= p_122691_) {
         this.f_122678_ = p_122691_ + 1;
      }

      return p_122693_;
   }

   public <V extends T> V m_7135_(ResourceKey<T> p_122735_, V p_122736_, Lifecycle p_122737_) {
      return this.m_5748_(this.f_122678_, p_122735_, p_122736_, p_122737_);
   }

   public <V extends T> V m_7794_(OptionalInt p_122708_, ResourceKey<T> p_122709_, V p_122710_, Lifecycle p_122711_) {
      Validate.notNull(p_122709_);
      Validate.notNull((T)p_122710_);
      T t = this.f_122675_.get(p_122709_);
      int i;
      if (t == null) {
         i = p_122708_.isPresent() ? p_122708_.getAsInt() : this.f_122678_;
      } else {
         i = this.f_122673_.getInt(t);
         if (p_122708_.isPresent() && p_122708_.getAsInt() != i) {
            throw new IllegalStateException("ID mismatch");
         }

         this.f_122673_.removeInt(t);
         this.f_122676_.remove(t);
      }

      return this.m_122690_(i, p_122709_, p_122710_, p_122711_, false);
   }

   @Nullable
   public ResourceLocation m_7981_(T p_122746_) {
      return this.f_122674_.inverse().get(p_122746_);
   }

   public Optional<ResourceKey<T>> m_7854_(T p_122755_) {
      return Optional.ofNullable(this.f_122675_.inverse().get(p_122755_));
   }

   public int m_7447_(@Nullable T p_122706_) {
      return this.f_122673_.getInt(p_122706_);
   }

   @Nullable
   public T m_6246_(@Nullable ResourceKey<T> p_122714_) {
      return this.f_122675_.get(p_122714_);
   }

   @Nullable
   public T m_7942_(int p_122684_) {
      return (T)(p_122684_ >= 0 && p_122684_ < this.f_122672_.size() ? this.f_122672_.get(p_122684_) : null);
   }

   public Lifecycle m_6228_(T p_122764_) {
      return this.f_122676_.get(p_122764_);
   }

   public Lifecycle m_7837_() {
      return this.f_122677_;
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.f_122672_.iterator(), Objects::nonNull);
   }

   @Nullable
   public T m_7745_(@Nullable ResourceLocation p_122739_) {
      return this.f_122674_.get(p_122739_);
   }

   public Set<ResourceLocation> m_6566_() {
      return Collections.unmodifiableSet(this.f_122674_.keySet());
   }

   public Set<Entry<ResourceKey<T>, T>> m_6579_() {
      return Collections.unmodifiableMap(this.f_122675_).entrySet();
   }

   public boolean m_142427_() {
      return this.f_122674_.isEmpty();
   }

   @Nullable
   public T m_142697_(Random p_122712_) {
      if (this.f_122671_ == null) {
         Collection<?> collection = this.f_122674_.values();
         if (collection.isEmpty()) {
            return (T)null;
         }

         this.f_122671_ = collection.toArray(new Object[collection.size()]);
      }

      return Util.m_137545_((T[])this.f_122671_, p_122712_);
   }

   public boolean m_7804_(ResourceLocation p_122761_) {
      return this.f_122674_.containsKey(p_122761_);
   }

   public boolean m_142003_(ResourceKey<T> p_175392_) {
      return this.f_122675_.containsKey(p_175392_);
   }

   public static <T> Codec<MappedRegistry<T>> m_122715_(ResourceKey<? extends Registry<T>> p_122716_, Lifecycle p_122717_, Codec<T> p_122718_) {
      return m_122727_(p_122716_, p_122718_.fieldOf("element")).codec().listOf().xmap((p_122722_) -> {
         MappedRegistry<T> mappedregistry = new MappedRegistry<>(p_122716_, p_122717_);

         for(MappedRegistry.RegistryEntry<T> registryentry : p_122722_) {
            mappedregistry.m_5748_(registryentry.f_122767_, registryentry.f_122766_, registryentry.f_122768_, p_122717_);
         }

         return mappedregistry;
      }, (p_122744_) -> {
         Builder<MappedRegistry.RegistryEntry<T>> builder = ImmutableList.builder();

         for(T t : p_122744_) {
            builder.add(new MappedRegistry.RegistryEntry<>(p_122744_.m_7854_(t).get(), p_122744_.m_7447_(t), t));
         }

         return builder.build();
      });
   }

   public static <T> Codec<MappedRegistry<T>> m_122747_(ResourceKey<? extends Registry<T>> p_122748_, Lifecycle p_122749_, Codec<T> p_122750_) {
      return RegistryDataPackCodec.m_135558_(p_122748_, p_122749_, p_122750_);
   }

   public static <T> Codec<MappedRegistry<T>> m_122756_(ResourceKey<? extends Registry<T>> p_122757_, Lifecycle p_122758_, Codec<T> p_122759_) {
      // FORGE: Fix MC-197860
      return new net.minecraftforge.common.LenientUnboundedMapCodec<>(ResourceLocation.f_135803_.xmap(ResourceKey.m_135797_(p_122757_), ResourceKey::m_135782_), p_122759_).xmap((p_122726_) -> {
         MappedRegistry<T> mappedregistry = new MappedRegistry<>(p_122757_, p_122758_);
         p_122726_.forEach((p_175387_, p_175388_) -> {
            mappedregistry.m_7135_(p_175387_, p_175388_, p_122758_);
         });
         return mappedregistry;
      }, (p_122699_) -> {
         return ImmutableMap.copyOf(p_122699_.f_122675_);
      });
   }

   public static class RegistryEntry<T> {
      public final ResourceKey<T> f_122766_;
      public final int f_122767_;
      public final T f_122768_;

      public RegistryEntry(ResourceKey<T> p_122770_, int p_122771_, T p_122772_) {
         this.f_122766_ = p_122770_;
         this.f_122767_ = p_122771_;
         this.f_122768_ = p_122772_;
      }
   }
}
