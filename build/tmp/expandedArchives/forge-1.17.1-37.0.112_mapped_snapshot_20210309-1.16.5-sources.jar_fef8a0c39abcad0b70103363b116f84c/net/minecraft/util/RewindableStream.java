package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RewindableStream<T> {
   final List<T> f_14215_ = Lists.newArrayList();
   final Spliterator<T> f_14216_;

   public RewindableStream(Stream<T> p_14218_) {
      this.f_14216_ = p_14218_.spliterator();
   }

   public Stream<T> m_14219_() {
      return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, 0) {
         private int f_14225_;

         public boolean tryAdvance(Consumer<? super T> p_14231_) {
            while(true) {
               if (this.f_14225_ >= RewindableStream.this.f_14215_.size()) {
                  if (RewindableStream.this.f_14216_.tryAdvance(RewindableStream.this.f_14215_::add)) {
                     continue;
                  }

                  return false;
               }

               p_14231_.accept(RewindableStream.this.f_14215_.get(this.f_14225_++));
               return true;
            }
         }
      }, false);
   }
}