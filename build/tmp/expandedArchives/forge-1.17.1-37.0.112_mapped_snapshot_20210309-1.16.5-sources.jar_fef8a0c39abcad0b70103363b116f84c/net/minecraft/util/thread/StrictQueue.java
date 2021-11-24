package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface StrictQueue<T, F> {
   @Nullable
   F m_6610_();

   boolean m_6944_(T p_18770_);

   boolean m_7263_();

   int m_142732_();

   public static final class FixedPriorityQueue implements StrictQueue<StrictQueue.IntRunnable, Runnable> {
      private final List<Queue<Runnable>> f_18771_;

      public FixedPriorityQueue(int p_18773_) {
         this.f_18771_ = IntStream.range(0, p_18773_).mapToObj((p_18776_) -> {
            return Queues.<Runnable>newConcurrentLinkedQueue();
         }).collect(Collectors.toList());
      }

      @Nullable
      public Runnable m_6610_() {
         for(Queue<Runnable> queue : this.f_18771_) {
            Runnable runnable = queue.poll();
            if (runnable != null) {
               return runnable;
            }
         }

         return null;
      }

      public boolean m_6944_(StrictQueue.IntRunnable p_18778_) {
         int i = p_18778_.m_18788_();
         this.f_18771_.get(i).add(p_18778_);
         return true;
      }

      public boolean m_7263_() {
         return this.f_18771_.stream().allMatch(Collection::isEmpty);
      }

      public int m_142732_() {
         int i = 0;

         for(Queue<Runnable> queue : this.f_18771_) {
            i += queue.size();
         }

         return i;
      }
   }

   public static final class IntRunnable implements Runnable {
      private final int f_18783_;
      private final Runnable f_18784_;

      public IntRunnable(int p_18786_, Runnable p_18787_) {
         this.f_18783_ = p_18786_;
         this.f_18784_ = p_18787_;
      }

      public void run() {
         this.f_18784_.run();
      }

      public int m_18788_() {
         return this.f_18783_;
      }
   }

   public static final class QueueStrictQueue<T> implements StrictQueue<T, T> {
      private final Queue<T> f_18790_;

      public QueueStrictQueue(Queue<T> p_18792_) {
         this.f_18790_ = p_18792_;
      }

      @Nullable
      public T m_6610_() {
         return this.f_18790_.poll();
      }

      public boolean m_6944_(T p_18795_) {
         return this.f_18790_.add(p_18795_);
      }

      public boolean m_7263_() {
         return this.f_18790_.isEmpty();
      }

      public int m_142732_() {
         return this.f_18790_.size();
      }
   }
}