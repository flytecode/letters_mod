package net.minecraft.tags;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class SetTag<T> implements Tag<T> {
   private final ImmutableList<T> f_13211_;
   private final Set<T> f_13212_;
   @VisibleForTesting
   protected final Class<?> f_13210_;

   protected SetTag(Set<T> p_13214_, Class<?> p_13215_) {
      this.f_13210_ = p_13215_;
      this.f_13212_ = p_13214_;
      this.f_13211_ = ImmutableList.copyOf(p_13214_);
   }

   public static <T> SetTag<T> m_13216_() {
      return new SetTag<>(ImmutableSet.of(), Void.class);
   }

   public static <T> SetTag<T> m_13222_(Set<T> p_13223_) {
      return new SetTag<>(p_13223_, m_13225_(p_13223_));
   }

   public boolean m_8110_(T p_13221_) {
      return this.f_13210_.isInstance(p_13221_) && this.f_13212_.contains(p_13221_);
   }

   public List<T> m_6497_() {
      return this.f_13211_;
   }

   private static <T> Class<?> m_13225_(Set<T> p_13226_) {
      if (p_13226_.isEmpty()) {
         return Void.class;
      } else {
         Class<?> oclass = null;

         for(T t : p_13226_) {
            if (oclass == null) {
               oclass = t.getClass();
            } else {
               oclass = m_13217_(oclass, t.getClass());
            }
         }

         return oclass;
      }
   }

   private static Class<?> m_13217_(Class<?> p_13218_, Class<?> p_13219_) {
      while(!p_13218_.isAssignableFrom(p_13219_)) {
         p_13218_ = p_13218_.getSuperclass();
      }

      return p_13218_;
   }
}