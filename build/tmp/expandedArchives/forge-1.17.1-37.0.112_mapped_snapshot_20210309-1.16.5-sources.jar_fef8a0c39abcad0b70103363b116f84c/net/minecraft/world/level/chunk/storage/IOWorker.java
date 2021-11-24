package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOWorker implements AutoCloseable {
   private static final Logger f_63515_ = LogManager.getLogger();
   private final AtomicBoolean f_63516_ = new AtomicBoolean();
   private final ProcessorMailbox<StrictQueue.IntRunnable> f_63517_;
   private final RegionFileStorage f_63518_;
   private final Map<ChunkPos, IOWorker.PendingStore> f_63519_ = Maps.newLinkedHashMap();

   protected IOWorker(File p_63522_, boolean p_63523_, String p_63524_) {
      this.f_63518_ = new RegionFileStorage(p_63522_, p_63523_);
      this.f_63517_ = new ProcessorMailbox<>(new StrictQueue.FixedPriorityQueue(IOWorker.Priority.values().length), Util.m_137579_(), "IOWorker-" + p_63524_);
   }

   public CompletableFuture<Void> m_63538_(ChunkPos p_63539_, @Nullable CompoundTag p_63540_) {
      return this.m_63545_(() -> {
         IOWorker.PendingStore ioworker$pendingstore = this.f_63519_.computeIfAbsent(p_63539_, (p_156584_) -> {
            return new IOWorker.PendingStore(p_63540_);
         });
         ioworker$pendingstore.f_63565_ = p_63540_;
         return Either.left(ioworker$pendingstore.f_63566_);
      }).thenCompose(Function.identity());
   }

   @Nullable
   public CompoundTag m_63533_(ChunkPos p_63534_) throws IOException {
      CompletableFuture<CompoundTag> completablefuture = this.m_156587_(p_63534_);

      try {
         return completablefuture.join();
      } catch (CompletionException completionexception) {
         if (completionexception.getCause() instanceof IOException) {
            throw (IOException)completionexception.getCause();
         } else {
            throw completionexception;
         }
      }
   }

   protected CompletableFuture<CompoundTag> m_156587_(ChunkPos p_156588_) {
      return this.m_63545_(() -> {
         IOWorker.PendingStore ioworker$pendingstore = this.f_63519_.get(p_156588_);
         if (ioworker$pendingstore != null) {
            return Either.left(ioworker$pendingstore.f_63565_);
         } else {
            try {
               CompoundTag compoundtag = this.f_63518_.m_63706_(p_156588_);
               return Either.left(compoundtag);
            } catch (Exception exception) {
               f_63515_.warn("Failed to read chunk {}", p_156588_, exception);
               return Either.right(exception);
            }
         }
      });
   }

   public CompletableFuture<Void> m_182498_(boolean p_182499_) {
      CompletableFuture<Void> completablefuture = this.m_63545_(() -> {
         return Either.left(CompletableFuture.allOf(this.f_63519_.values().stream().map((p_156581_) -> {
            return p_156581_.f_63566_;
         }).toArray((p_156576_) -> {
            return new CompletableFuture[p_156576_];
         })));
      }).thenCompose(Function.identity());
      return p_182499_ ? completablefuture.thenCompose((p_63544_) -> {
         return this.m_63545_(() -> {
            try {
               this.f_63518_.m_63705_();
               return Either.left((Void)null);
            } catch (Exception exception) {
               f_63515_.warn("Failed to synchronize chunks", (Throwable)exception);
               return Either.right(exception);
            }
         });
      }) : completablefuture.thenCompose((p_182494_) -> {
         return this.m_63545_(() -> {
            return Either.left((Void)null);
         });
      });
   }

   private <T> CompletableFuture<T> m_63545_(Supplier<Either<T, Exception>> p_63546_) {
      return this.f_63517_.m_18722_((p_63549_) -> {
         return new StrictQueue.IntRunnable(IOWorker.Priority.FOREGROUND.ordinal(), () -> {
            if (!this.f_63516_.get()) {
               p_63549_.m_6937_(p_63546_.get());
            }

            this.m_63561_();
         });
      });
   }

   private void m_63553_() {
      if (!this.f_63519_.isEmpty()) {
         Iterator<Entry<ChunkPos, IOWorker.PendingStore>> iterator = this.f_63519_.entrySet().iterator();
         Entry<ChunkPos, IOWorker.PendingStore> entry = iterator.next();
         iterator.remove();
         this.m_63535_(entry.getKey(), entry.getValue());
         this.m_63561_();
      }
   }

   private void m_63561_() {
      this.f_63517_.m_6937_(new StrictQueue.IntRunnable(IOWorker.Priority.BACKGROUND.ordinal(), this::m_63553_));
   }

   private void m_63535_(ChunkPos p_63536_, IOWorker.PendingStore p_63537_) {
      try {
         this.f_63518_.m_63708_(p_63536_, p_63537_.f_63565_);
         p_63537_.f_63566_.complete((Void)null);
      } catch (Exception exception) {
         f_63515_.error("Failed to store chunk {}", p_63536_, exception);
         p_63537_.f_63566_.completeExceptionally(exception);
      }

   }

   public void close() throws IOException {
      if (this.f_63516_.compareAndSet(false, true)) {
         this.f_63517_.m_18720_((p_63529_) -> {
            return new StrictQueue.IntRunnable(IOWorker.Priority.SHUTDOWN.ordinal(), () -> {
               p_63529_.m_6937_(Unit.INSTANCE);
            });
         }).join();
         this.f_63517_.close();

         try {
            this.f_63518_.close();
         } catch (Exception exception) {
            f_63515_.error("Failed to close storage", (Throwable)exception);
         }

      }
   }

   static class PendingStore {
      @Nullable
      CompoundTag f_63565_;
      final CompletableFuture<Void> f_63566_ = new CompletableFuture<>();

      public PendingStore(@Nullable CompoundTag p_63568_) {
         this.f_63565_ = p_63568_;
      }
   }

   static enum Priority {
      FOREGROUND,
      BACKGROUND,
      SHUTDOWN;
   }
}