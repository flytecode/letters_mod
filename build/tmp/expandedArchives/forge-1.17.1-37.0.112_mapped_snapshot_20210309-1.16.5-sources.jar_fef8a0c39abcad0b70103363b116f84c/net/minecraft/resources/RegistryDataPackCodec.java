package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;

public final class RegistryDataPackCodec<E> implements Codec<MappedRegistry<E>> {
   private final Codec<MappedRegistry<E>> f_135541_;
   private final ResourceKey<? extends Registry<E>> f_135542_;
   private final Codec<E> f_135543_;

   public static <E> RegistryDataPackCodec<E> m_135558_(ResourceKey<? extends Registry<E>> p_135559_, Lifecycle p_135560_, Codec<E> p_135561_) {
      return new RegistryDataPackCodec<>(p_135559_, p_135560_, p_135561_);
   }

   private RegistryDataPackCodec(ResourceKey<? extends Registry<E>> p_135545_, Lifecycle p_135546_, Codec<E> p_135547_) {
      this.f_135541_ = MappedRegistry.m_122756_(p_135545_, p_135546_, p_135547_);
      this.f_135542_ = p_135545_;
      this.f_135543_ = p_135547_;
   }

   public <T> DataResult<T> encode(MappedRegistry<E> p_135555_, DynamicOps<T> p_135556_, T p_135557_) {
      return this.f_135541_.encode(p_135555_, p_135556_, p_135557_);
   }

   public <T> DataResult<Pair<MappedRegistry<E>, T>> decode(DynamicOps<T> p_135563_, T p_135564_) {
      DataResult<Pair<MappedRegistry<E>, T>> dataresult = this.f_135541_.decode(p_135563_, p_135564_);
      return p_135563_ instanceof RegistryReadOps ? dataresult.flatMap((p_135553_) -> {
         return ((RegistryReadOps)p_135563_).m_135662_(p_135553_.getFirst(), this.f_135542_, this.f_135543_).map((p_179848_) -> {
            return Pair.of(p_179848_, (T)p_135553_.getSecond());
         });
      }) : dataresult;
   }

   public String toString() {
      return "RegistryDataPackCodec[" + this.f_135541_ + " " + this.f_135542_ + " " + this.f_135543_ + "]";
   }
}