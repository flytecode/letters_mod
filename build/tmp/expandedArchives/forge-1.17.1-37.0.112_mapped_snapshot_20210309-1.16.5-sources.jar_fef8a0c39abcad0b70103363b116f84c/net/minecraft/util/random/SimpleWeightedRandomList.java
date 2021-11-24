package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SimpleWeightedRandomList<E> extends WeightedRandomList<WeightedEntry.Wrapper<E>> {
   public static <E> Codec<SimpleWeightedRandomList<E>> m_146264_(Codec<E> p_146265_) {
      return WeightedEntry.Wrapper.<E>m_146305_(p_146265_).listOf().xmap(SimpleWeightedRandomList::new, WeightedRandomList::m_146338_);
   }

   SimpleWeightedRandomList(List<? extends WeightedEntry.Wrapper<E>> p_146262_) {
      super(p_146262_);
   }

   public static <E> SimpleWeightedRandomList.Builder<E> m_146263_() {
      return new SimpleWeightedRandomList.Builder<>();
   }

   public Optional<E> m_146266_(Random p_146267_) {
      return this.m_146335_(p_146267_).map(WeightedEntry.Wrapper::m_146310_);
   }

   public static class Builder<E> {
      private final ImmutableList.Builder<WeightedEntry.Wrapper<E>> f_146268_ = ImmutableList.builder();

      public SimpleWeightedRandomList.Builder<E> m_146271_(E p_146272_, int p_146273_) {
         this.f_146268_.add(WeightedEntry.m_146290_(p_146272_, p_146273_));
         return this;
      }

      public SimpleWeightedRandomList<E> m_146270_() {
         return new SimpleWeightedRandomList<>(this.f_146268_.build());
      }
   }
}