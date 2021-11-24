package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskPriorityQueueSorter implements ChunkHolder.LevelChangeListener, AutoCloseable {
   private static final Logger f_140549_ = LogManager.getLogger();
   private final Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>> f_140550_;
   private final Set<ProcessorHandle<?>> f_140551_;
   private final ProcessorMailbox<StrictQueue.IntRunnable> f_140552_;

   public ChunkTaskPriorityQueueSorter(List<ProcessorHandle<?>> p_140555_, Executor p_140556_, int p_140557_) {
      this.f_140550_ = p_140555_.stream().collect(Collectors.toMap(Function.identity(), (p_140561_) -> {
         return new ChunkTaskPriorityQueue<>(p_140561_.m_7326_() + "_queue", p_140557_);
      }));
      this.f_140551_ = Sets.newHashSet(p_140555_);
      this.f_140552_ = new ProcessorMailbox<>(new StrictQueue.FixedPriorityQueue(4), p_140556_, "sorter");
   }

   public static <T> ChunkTaskPriorityQueueSorter.Message<T> m_143181_(Function<ProcessorHandle<Unit>, T> p_143182_, long p_143183_, IntSupplier p_143184_) {
      return new ChunkTaskPriorityQueueSorter.Message<>(p_143182_, p_143183_, p_143184_);
   }

   public static ChunkTaskPriorityQueueSorter.Message<Runnable> m_140624_(Runnable p_140625_, long p_140626_, IntSupplier p_140627_) {
      return new ChunkTaskPriorityQueueSorter.Message<>((p_140634_) -> {
         return () -> {
            p_140625_.run();
            p_140634_.m_6937_(Unit.INSTANCE);
         };
      }, p_140626_, p_140627_);
   }

   public static ChunkTaskPriorityQueueSorter.Message<Runnable> m_140642_(ChunkHolder p_140643_, Runnable p_140644_) {
      return m_140624_(p_140644_, p_140643_.m_140092_().m_45588_(), p_140643_::m_140094_);
   }

   public static <T> ChunkTaskPriorityQueueSorter.Message<T> m_143156_(ChunkHolder p_143157_, Function<ProcessorHandle<Unit>, T> p_143158_) {
      return m_143181_(p_143158_, p_143157_.m_140092_().m_45588_(), p_143157_::m_140094_);
   }

   public static ChunkTaskPriorityQueueSorter.Release m_140628_(Runnable p_140629_, long p_140630_, boolean p_140631_) {
      return new ChunkTaskPriorityQueueSorter.Release(p_140629_, p_140630_, p_140631_);
   }

   public <T> ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<T>> m_140604_(ProcessorHandle<T> p_140605_, boolean p_140606_) {
      return this.f_140552_.<ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<T>>>m_18720_((p_140610_) -> {
         return new StrictQueue.IntRunnable(0, () -> {
            this.m_140652_(p_140605_);
            p_140610_.m_6937_(ProcessorHandle.m_18714_("chunk priority sorter around " + p_140605_.m_7326_(), (p_143176_) -> {
               this.m_140589_(p_140605_, ((Message<T>)p_143176_).f_140664_, ((Message<T>)p_143176_).f_140665_, ((Message<T>)p_143176_).f_140666_, p_140606_);
            }));
         });
      }).join();
   }

   public ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> m_140567_(ProcessorHandle<Runnable> p_140568_) {
      return this.f_140552_.<ProcessorHandle<ChunkTaskPriorityQueueSorter.Release>>m_18720_((p_140581_) -> {
         return new StrictQueue.IntRunnable(0, () -> {
            p_140581_.m_6937_(ProcessorHandle.m_18714_("chunk priority sorter around " + p_140568_.m_7326_(), (p_143165_) -> {
               this.m_140569_(p_140568_, ((Release)p_143165_).f_140683_, ((Release)p_143165_).f_140682_, ((Release)p_143165_).f_140684_);
            }));
         });
      }).join();
   }

   public void m_6250_(ChunkPos p_140616_, IntSupplier p_140617_, int p_140618_, IntConsumer p_140619_) {
      this.f_140552_.m_6937_(new StrictQueue.IntRunnable(0, () -> {
         int i = p_140617_.getAsInt();
         this.f_140550_.values().forEach((p_143155_) -> {
            p_143155_.m_140521_(i, p_140616_, p_140618_);
         });
         p_140619_.accept(p_140618_);
      }));
   }

   private <T> void m_140569_(ProcessorHandle<T> p_140570_, long p_140571_, Runnable p_140572_, boolean p_140573_) {
      this.f_140552_.m_6937_(new StrictQueue.IntRunnable(1, () -> {
         ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> chunktaskpriorityqueue = this.m_140652_(p_140570_);
         chunktaskpriorityqueue.m_140530_(p_140571_, p_140573_);
         if (this.f_140551_.remove(p_140570_)) {
            this.m_140645_(chunktaskpriorityqueue, p_140570_);
         }

         p_140572_.run();
      }));
   }

   private <T> void m_140589_(ProcessorHandle<T> p_140590_, Function<ProcessorHandle<Unit>, T> p_140591_, long p_140592_, IntSupplier p_140593_, boolean p_140594_) {
      this.f_140552_.m_6937_(new StrictQueue.IntRunnable(2, () -> {
         ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> chunktaskpriorityqueue = this.m_140652_(p_140590_);
         int i = p_140593_.getAsInt();
         chunktaskpriorityqueue.m_140535_(Optional.of(p_140591_), p_140592_, i);
         if (p_140594_) {
            chunktaskpriorityqueue.m_140535_(Optional.empty(), p_140592_, i);
         }

         if (this.f_140551_.remove(p_140590_)) {
            this.m_140645_(chunktaskpriorityqueue, p_140590_);
         }

      }));
   }

   private <T> void m_140645_(ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> p_140646_, ProcessorHandle<T> p_140647_) {
      this.f_140552_.m_6937_(new StrictQueue.IntRunnable(3, () -> {
         Stream<Either<Function<ProcessorHandle<Unit>, T>, Runnable>> stream = p_140646_.m_140518_();
         if (stream == null) {
            this.f_140551_.add(p_140647_);
         } else {
            Util.m_137567_(stream.map((p_143172_) -> {
               return p_143172_.map(p_140647_::m_18720_, (p_143180_) -> {
                  p_143180_.run();
                  return CompletableFuture.completedFuture(Unit.INSTANCE);
               });
            }).collect(Collectors.toList())).thenAccept((p_143162_) -> {
               this.m_140645_(p_140646_, p_140647_);
            });
         }

      }));
   }

   private <T> ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> m_140652_(ProcessorHandle<T> p_140653_) {
      ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>> chunktaskpriorityqueue = this.f_140550_.get(p_140653_);
      if (chunktaskpriorityqueue == null) {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException("No queue for: " + p_140653_));
      } else {
         return (ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>>)chunktaskpriorityqueue;
      }
   }

   @VisibleForTesting
   public String m_140558_() {
      return (String)this.f_140550_.entrySet().stream().map((p_140636_) -> {
         return p_140636_.getKey().m_7326_() + "=[" + (String)p_140636_.getValue().m_140539_().stream().map((p_143178_) -> {
            return p_143178_ + ":" + new ChunkPos(p_143178_);
         }).collect(Collectors.joining(",")) + "]";
      }).collect(Collectors.joining(",")) + ", s=" + this.f_140551_.size();
   }

   public void close() {
      this.f_140550_.keySet().forEach(ProcessorHandle::close);
   }

   public static final class Message<T> {
      final Function<ProcessorHandle<Unit>, T> f_140664_;
      final long f_140665_;
      final IntSupplier f_140666_;

      Message(Function<ProcessorHandle<Unit>, T> p_140668_, long p_140669_, IntSupplier p_140670_) {
         this.f_140664_ = p_140668_;
         this.f_140665_ = p_140669_;
         this.f_140666_ = p_140670_;
      }
   }

   public static final class Release {
      final Runnable f_140682_;
      final long f_140683_;
      final boolean f_140684_;

      Release(Runnable p_140686_, long p_140687_, boolean p_140688_) {
         this.f_140682_ = p_140686_;
         this.f_140683_ = p_140687_;
         this.f_140684_ = p_140688_;
      }
   }
}