package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DebugBuffer<T> {
   private final AtomicReferenceArray<T> f_144620_;
   private final AtomicInteger f_144621_;

   public DebugBuffer(int p_144623_) {
      this.f_144620_ = new AtomicReferenceArray<>(p_144623_);
      this.f_144621_ = new AtomicInteger(0);
   }

   public void m_144625_(T p_144626_) {
      int i = this.f_144620_.length();

      int j;
      int k;
      do {
         j = this.f_144621_.get();
         k = (j + 1) % i;
      } while(!this.f_144621_.compareAndSet(j, k));

      this.f_144620_.set(k, p_144626_);
   }

   public List<T> m_144624_() {
      int i = this.f_144621_.get();
      Builder<T> builder = ImmutableList.builder();

      for(int j = 0; j < this.f_144620_.length(); ++j) {
         int k = Math.floorMod(i - j, this.f_144620_.length());
         T t = this.f_144620_.get(k);
         if (t != null) {
            builder.add(t);
         }
      }

      return builder.build();
   }
}