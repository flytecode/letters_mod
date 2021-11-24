package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.InactiveProfiler;

public class SimpleReloadInstance<S> implements ReloadInstance {
   private static final int f_143937_ = 2;
   private static final int f_143938_ = 2;
   private static final int f_143939_ = 1;
   protected final ResourceManager f_10798_;
   protected final CompletableFuture<Unit> f_10799_ = new CompletableFuture<>();
   protected final CompletableFuture<List<S>> f_10800_;
   final Set<PreparableReloadListener> f_10801_;
   private final int f_10802_;
   private int f_10803_;
   private int f_10804_;
   private final AtomicInteger f_10805_ = new AtomicInteger();
   private final AtomicInteger f_10806_ = new AtomicInteger();

   public static SimpleReloadInstance<Void> m_10815_(ResourceManager p_10816_, List<PreparableReloadListener> p_10817_, Executor p_10818_, Executor p_10819_, CompletableFuture<Unit> p_10820_) {
      return new SimpleReloadInstance<>(p_10818_, p_10819_, p_10816_, p_10817_, (p_10829_, p_10830_, p_10831_, p_10832_, p_10833_) -> {
         return p_10831_.m_5540_(p_10829_, p_10830_, InactiveProfiler.f_18554_, InactiveProfiler.f_18554_, p_10818_, p_10833_);
      }, p_10820_);
   }

   protected SimpleReloadInstance(Executor p_10808_, final Executor p_10809_, ResourceManager p_10810_, List<PreparableReloadListener> p_10811_, SimpleReloadInstance.StateFactory<S> p_10812_, CompletableFuture<Unit> p_10813_) {
      this.f_10798_ = p_10810_;
      this.f_10802_ = p_10811_.size();
      this.f_10805_.incrementAndGet();
      p_10813_.thenRun(this.f_10806_::incrementAndGet);
      List<CompletableFuture<S>> list = Lists.newArrayList();
      CompletableFuture<?> completablefuture = p_10813_;
      this.f_10801_ = Sets.newHashSet(p_10811_);

      for(final PreparableReloadListener preparablereloadlistener : p_10811_) {
         final CompletableFuture<?> completablefuture1 = completablefuture;
         CompletableFuture<S> completablefuture2 = p_10812_.m_10863_(new PreparableReloadListener.PreparationBarrier() {
            public <T> CompletableFuture<T> m_6769_(T p_10858_) {
               p_10809_.execute(() -> {
                  SimpleReloadInstance.this.f_10801_.remove(preparablereloadlistener);
                  if (SimpleReloadInstance.this.f_10801_.isEmpty()) {
                     SimpleReloadInstance.this.f_10799_.complete(Unit.INSTANCE);
                  }

               });
               return SimpleReloadInstance.this.f_10799_.thenCombine(completablefuture1, (p_10861_, p_10862_) -> {
                  return p_10858_;
               });
            }
         }, p_10810_, preparablereloadlistener, (p_10842_) -> {
            this.f_10805_.incrementAndGet();
            p_10808_.execute(() -> {
               p_10842_.run();
               this.f_10806_.incrementAndGet();
            });
         }, (p_10836_) -> {
            ++this.f_10803_;
            p_10809_.execute(() -> {
               p_10836_.run();
               ++this.f_10804_;
            });
         });
         list.add(completablefuture2);
         completablefuture = completablefuture2;
      }

      this.f_10800_ = Util.m_143840_(list);
   }

   public CompletableFuture<Unit> m_7237_() {
      return this.f_10800_.thenApply((p_10826_) -> {
         return Unit.INSTANCE;
      });
   }

   public float m_7750_() {
      int i = this.f_10802_ - this.f_10801_.size();
      float f = (float)(this.f_10806_.get() * 2 + this.f_10804_ * 2 + i * 1);
      float f1 = (float)(this.f_10805_.get() * 2 + this.f_10803_ * 2 + this.f_10802_ * 1);
      return f / f1;
   }

   public boolean m_7746_() {
      return this.f_10800_.isDone();
   }

   public void m_7748_() {
      if (this.f_10800_.isCompletedExceptionally()) {
         this.f_10800_.join();
      }

   }

   protected interface StateFactory<S> {
      CompletableFuture<S> m_10863_(PreparableReloadListener.PreparationBarrier p_10864_, ResourceManager p_10865_, PreparableReloadListener p_10866_, Executor p_10867_, Executor p_10868_);
   }
}