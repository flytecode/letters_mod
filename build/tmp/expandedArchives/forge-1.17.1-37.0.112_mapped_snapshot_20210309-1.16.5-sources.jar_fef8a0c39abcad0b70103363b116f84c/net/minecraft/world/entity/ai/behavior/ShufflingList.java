package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ShufflingList<U> {
   protected final List<ShufflingList.WeightedEntry<U>> f_147917_;
   private final Random f_147918_ = new Random();

   public ShufflingList() {
      this.f_147917_ = Lists.newArrayList();
   }

   private ShufflingList(List<ShufflingList.WeightedEntry<U>> p_147921_) {
      this.f_147917_ = Lists.newArrayList(p_147921_);
   }

   public static <U> Codec<ShufflingList<U>> m_147927_(Codec<U> p_147928_) {
      return ShufflingList.WeightedEntry.<U>m_147943_(p_147928_).listOf().xmap(ShufflingList::new, (p_147926_) -> {
         return p_147926_.f_147917_;
      });
   }

   public ShufflingList<U> m_147929_(U p_147930_, int p_147931_) {
      this.f_147917_.add(new ShufflingList.WeightedEntry<>(p_147930_, p_147931_));
      return this;
   }

   public ShufflingList<U> m_147922_() {
      this.f_147917_.forEach((p_147924_) -> {
         p_147924_.m_147941_(this.f_147918_.nextFloat());
      });
      this.f_147917_.sort(Comparator.comparingDouble(ShufflingList.WeightedEntry::m_147946_));
      return this;
   }

   public Stream<U> m_147932_() {
      return this.f_147917_.stream().map(ShufflingList.WeightedEntry::m_147940_);
   }

   public String toString() {
      return "ShufflingList[" + this.f_147917_ + "]";
   }

   public static class WeightedEntry<T> {
      final T f_147934_;
      final int f_147935_;
      private double f_147936_;

      WeightedEntry(T p_147938_, int p_147939_) {
         this.f_147935_ = p_147939_;
         this.f_147934_ = p_147938_;
      }

      private double m_147946_() {
         return this.f_147936_;
      }

      void m_147941_(float p_147942_) {
         this.f_147936_ = -Math.pow((double)p_147942_, (double)(1.0F / (float)this.f_147935_));
      }

      public T m_147940_() {
         return this.f_147934_;
      }

      public int m_147945_() {
         return this.f_147935_;
      }

      public String toString() {
         return this.f_147935_ + ":" + this.f_147934_;
      }

      public static <E> Codec<ShufflingList.WeightedEntry<E>> m_147943_(final Codec<E> p_147944_) {
         return new Codec<ShufflingList.WeightedEntry<E>>() {
            public <T> DataResult<Pair<ShufflingList.WeightedEntry<E>, T>> decode(DynamicOps<T> p_147962_, T p_147963_) {
               Dynamic<T> dynamic = new Dynamic<>(p_147962_, p_147963_);
               return dynamic.get("data").flatMap(p_147944_::parse).map((p_147957_) -> {
                  return new ShufflingList.WeightedEntry<>(p_147957_, dynamic.get("weight").asInt(1));
               }).map((p_147960_) -> {
                  return Pair.of(p_147960_, p_147962_.empty());
               });
            }

            public <T> DataResult<T> encode(ShufflingList.WeightedEntry<E> p_147952_, DynamicOps<T> p_147953_, T p_147954_) {
               return p_147953_.mapBuilder().add("weight", p_147953_.createInt(p_147952_.f_147935_)).add("data", p_147944_.encodeStart(p_147953_, p_147952_.f_147934_)).build(p_147954_);
            }
         };
      }
   }
}