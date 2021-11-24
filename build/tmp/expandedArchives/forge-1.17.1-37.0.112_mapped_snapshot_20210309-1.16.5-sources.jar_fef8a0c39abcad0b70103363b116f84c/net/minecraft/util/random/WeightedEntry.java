package net.minecraft.util.random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface WeightedEntry {
   Weight m_142631_();

   static <T> WeightedEntry.Wrapper<T> m_146290_(T p_146291_, int p_146292_) {
      return new WeightedEntry.Wrapper<>(p_146291_, Weight.m_146282_(p_146292_));
   }

   public static class IntrusiveBase implements WeightedEntry {
      private final Weight f_146293_;

      public IntrusiveBase(int p_146295_) {
         this.f_146293_ = Weight.m_146282_(p_146295_);
      }

      public IntrusiveBase(Weight p_146297_) {
         this.f_146293_ = p_146297_;
      }

      public Weight m_142631_() {
         return this.f_146293_;
      }
   }

   public static class Wrapper<T> implements WeightedEntry {
      private final T f_146299_;
      private final Weight f_146300_;

      Wrapper(T p_146302_, Weight p_146303_) {
         this.f_146299_ = p_146302_;
         this.f_146300_ = p_146303_;
      }

      public T m_146310_() {
         return this.f_146299_;
      }

      public Weight m_142631_() {
         return this.f_146300_;
      }

      public static <E> Codec<WeightedEntry.Wrapper<E>> m_146305_(Codec<E> p_146306_) {
         return RecordCodecBuilder.create((p_146309_) -> {
            return p_146309_.group(p_146306_.fieldOf("data").forGetter(WeightedEntry.Wrapper::m_146310_), Weight.f_146274_.fieldOf("weight").forGetter(WeightedEntry.Wrapper::m_142631_)).apply(p_146309_, WeightedEntry.Wrapper::new);
         });
      }
   }
}