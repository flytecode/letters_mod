package net.minecraft.util;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

@Deprecated
public class LazyLoadedValue<T> {
   private final Supplier<T> f_13967_;

   public LazyLoadedValue(Supplier<T> p_13970_) {
      this.f_13967_ = Suppliers.memoize(p_13970_::get);
   }

   public T m_13971_() {
      return this.f_13967_.get();
   }
}