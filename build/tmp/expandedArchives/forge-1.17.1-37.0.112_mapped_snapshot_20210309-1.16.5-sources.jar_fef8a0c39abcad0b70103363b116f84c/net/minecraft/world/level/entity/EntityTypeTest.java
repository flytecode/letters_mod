package net.minecraft.world.level.entity;

import javax.annotation.Nullable;

public interface EntityTypeTest<B, T extends B> {
   static <B, T extends B> EntityTypeTest<B, T> m_156916_(final Class<T> p_156917_) {
      return new EntityTypeTest<B, T>() {
         @Nullable
         public T m_141992_(B p_156924_) {
            return (T)(p_156917_.isInstance(p_156924_) ? p_156924_ : null);
         }

         public Class<? extends B> m_142225_() {
            return p_156917_;
         }
      };
   }

   @Nullable
   T m_141992_(B p_156918_);

   Class<? extends B> m_142225_();
}