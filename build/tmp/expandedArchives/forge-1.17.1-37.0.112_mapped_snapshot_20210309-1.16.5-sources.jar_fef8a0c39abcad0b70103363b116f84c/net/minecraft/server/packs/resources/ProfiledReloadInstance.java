package net.minecraft.server.packs.resources;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.Util;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfiledReloadInstance extends SimpleReloadInstance<ProfiledReloadInstance.State> {
   private static final Logger f_10645_ = LogManager.getLogger();
   private final Stopwatch f_10646_ = Stopwatch.createUnstarted();

   public ProfiledReloadInstance(ResourceManager p_10649_, List<PreparableReloadListener> p_10650_, Executor p_10651_, Executor p_10652_, CompletableFuture<Unit> p_10653_) {
      super(p_10651_, p_10652_, p_10649_, p_10650_, (p_10668_, p_10669_, p_10670_, p_10671_, p_10672_) -> {
         AtomicLong atomiclong = new AtomicLong();
         AtomicLong atomiclong1 = new AtomicLong();
         ActiveProfiler activeprofiler = new ActiveProfiler(Util.f_137440_, () -> {
            return 0;
         }, false);
         ActiveProfiler activeprofiler1 = new ActiveProfiler(Util.f_137440_, () -> {
            return 0;
         }, false);
         CompletableFuture<Void> completablefuture = p_10670_.m_5540_(p_10668_, p_10669_, activeprofiler, activeprofiler1, (p_143927_) -> {
            p_10671_.execute(() -> {
               long i = Util.m_137569_();
               p_143927_.run();
               atomiclong.addAndGet(Util.m_137569_() - i);
            });
         }, (p_143920_) -> {
            p_10672_.execute(() -> {
               long i = Util.m_137569_();
               p_143920_.run();
               atomiclong1.addAndGet(Util.m_137569_() - i);
            });
         });
         return completablefuture.thenApplyAsync((p_143913_) -> {
            return new ProfiledReloadInstance.State(p_10670_.m_7812_(), activeprofiler.m_5948_(), activeprofiler1.m_5948_(), atomiclong, atomiclong1);
         }, p_10652_);
      }, p_10653_);
      this.f_10646_.start();
      this.f_10800_.thenAcceptAsync(this::m_10664_, p_10652_);
   }

   private void m_10664_(List<ProfiledReloadInstance.State> p_10665_) {
      this.f_10646_.stop();
      int i = 0;
      f_10645_.info("Resource reload finished after {} ms", (long)this.f_10646_.elapsed(TimeUnit.MILLISECONDS));

      for(ProfiledReloadInstance.State profiledreloadinstance$state : p_10665_) {
         ProfileResults profileresults = profiledreloadinstance$state.f_10687_;
         ProfileResults profileresults1 = profiledreloadinstance$state.f_10688_;
         int j = (int)((double)profiledreloadinstance$state.f_10689_.get() / 1000000.0D);
         int k = (int)((double)profiledreloadinstance$state.f_10690_.get() / 1000000.0D);
         int l = j + k;
         String s = profiledreloadinstance$state.f_10686_;
         f_10645_.info("{} took approximately {} ms ({} ms preparing, {} ms applying)", s, l, j, k);
         i += k;
      }

      f_10645_.info("Total blocking time: {} ms", (int)i);
   }

   public static class State {
      final String f_10686_;
      final ProfileResults f_10687_;
      final ProfileResults f_10688_;
      final AtomicLong f_10689_;
      final AtomicLong f_10690_;

      State(String p_10692_, ProfileResults p_10693_, ProfileResults p_10694_, AtomicLong p_10695_, AtomicLong p_10696_) {
         this.f_10686_ = p_10692_;
         this.f_10687_ = p_10693_;
         this.f_10688_ = p_10694_;
         this.f_10689_ = p_10695_;
         this.f_10690_ = p_10696_;
      }
   }
}