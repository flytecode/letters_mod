package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadedLevelLightEngine extends LevelLightEngine implements AutoCloseable {
   private static final Logger f_9296_ = LogManager.getLogger();
   private final ProcessorMailbox<Runnable> f_9297_;
   private final ObjectList<Pair<ThreadedLevelLightEngine.TaskType, Runnable>> f_9298_ = new ObjectArrayList<>();
   private final ChunkMap f_9299_;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_9300_;
   private volatile int f_9301_ = 5;
   private final AtomicBoolean f_9302_ = new AtomicBoolean();

   public ThreadedLevelLightEngine(LightChunkGetter p_9305_, ChunkMap p_9306_, boolean p_9307_, ProcessorMailbox<Runnable> p_9308_, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> p_9309_) {
      super(p_9305_, true, p_9307_);
      this.f_9299_ = p_9306_;
      this.f_9300_ = p_9309_;
      this.f_9297_ = p_9308_;
   }

   public void close() {
   }

   public int m_142528_(int p_9324_, boolean p_9325_, boolean p_9326_) {
      throw (UnsupportedOperationException)Util.m_137570_(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   public void m_142519_(BlockPos p_9359_, int p_9360_) {
      throw (UnsupportedOperationException)Util.m_137570_(new UnsupportedOperationException("Ran automatically on a different thread!"));
   }

   public void m_142202_(BlockPos p_9357_) {
      BlockPos blockpos = p_9357_.m_7949_();
      this.m_9312_(SectionPos.m_123171_(p_9357_.m_123341_()), SectionPos.m_123171_(p_9357_.m_123343_()), ThreadedLevelLightEngine.TaskType.POST_UPDATE, Util.m_137474_(() -> {
         super.m_142202_(blockpos);
      }, () -> {
         return "checkBlock " + blockpos;
      }));
   }

   protected void m_9330_(ChunkPos p_9331_) {
      this.m_9317_(p_9331_.f_45578_, p_9331_.f_45579_, () -> {
         return 0;
      }, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         super.m_6462_(p_9331_, false);
         super.m_141940_(p_9331_, false);

         for(int i = this.m_164447_(); i < this.m_164448_(); ++i) {
            super.m_5687_(LightLayer.BLOCK, SectionPos.m_123196_(p_9331_, i), (DataLayer)null, true);
            super.m_5687_(LightLayer.SKY, SectionPos.m_123196_(p_9331_, i), (DataLayer)null, true);
         }

         for(int j = this.f_164445_.m_151560_(); j < this.f_164445_.m_151561_(); ++j) {
            super.m_6191_(SectionPos.m_123196_(p_9331_, j), true);
         }

      }, () -> {
         return "updateChunkStatus " + p_9331_ + " true";
      }));
   }

   public void m_6191_(SectionPos p_9364_, boolean p_9365_) {
      this.m_9317_(p_9364_.m_123170_(), p_9364_.m_123222_(), () -> {
         return 0;
      }, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         super.m_6191_(p_9364_, p_9365_);
      }, () -> {
         return "updateSectionStatus " + p_9364_ + " " + p_9365_;
      }));
   }

   public void m_141940_(ChunkPos p_9336_, boolean p_9337_) {
      this.m_9312_(p_9336_.f_45578_, p_9336_.f_45579_, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         super.m_141940_(p_9336_, p_9337_);
      }, () -> {
         return "enableLight " + p_9336_ + " " + p_9337_;
      }));
   }

   public void m_5687_(LightLayer p_9339_, SectionPos p_9340_, @Nullable DataLayer p_9341_, boolean p_9342_) {
      this.m_9317_(p_9340_.m_123170_(), p_9340_.m_123222_(), () -> {
         return 0;
      }, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         super.m_5687_(p_9339_, p_9340_, p_9341_, p_9342_);
      }, () -> {
         return "queueData " + p_9340_;
      }));
   }

   private void m_9312_(int p_9313_, int p_9314_, ThreadedLevelLightEngine.TaskType p_9315_, Runnable p_9316_) {
      this.m_9317_(p_9313_, p_9314_, this.f_9299_.m_140371_(ChunkPos.m_45589_(p_9313_, p_9314_)), p_9315_, p_9316_);
   }

   private void m_9317_(int p_9318_, int p_9319_, IntSupplier p_9320_, ThreadedLevelLightEngine.TaskType p_9321_, Runnable p_9322_) {
      this.f_9300_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(() -> {
         this.f_9298_.add(Pair.of(p_9321_, p_9322_));
         if (this.f_9298_.size() >= this.f_9301_) {
            this.m_9366_();
         }

      }, ChunkPos.m_45589_(p_9318_, p_9319_), p_9320_));
   }

   public void m_6462_(ChunkPos p_9370_, boolean p_9371_) {
      this.m_9317_(p_9370_.f_45578_, p_9370_.f_45579_, () -> {
         return 0;
      }, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         super.m_6462_(p_9370_, p_9371_);
      }, () -> {
         return "retainData " + p_9370_;
      }));
   }

   public CompletableFuture<ChunkAccess> m_9353_(ChunkAccess p_9354_, boolean p_9355_) {
      ChunkPos chunkpos = p_9354_.m_7697_();
      p_9354_.m_8094_(false);
      this.m_9312_(chunkpos.f_45578_, chunkpos.f_45579_, ThreadedLevelLightEngine.TaskType.PRE_UPDATE, Util.m_137474_(() -> {
         LevelChunkSection[] alevelchunksection = p_9354_.m_7103_();

         for(int i = 0; i < p_9354_.m_151559_(); ++i) {
            LevelChunkSection levelchunksection = alevelchunksection[i];
            if (!LevelChunkSection.m_63000_(levelchunksection)) {
               int j = this.f_164445_.m_151568_(i);
               super.m_6191_(SectionPos.m_123196_(chunkpos, j), false);
            }
         }

         super.m_141940_(chunkpos, true);
         if (!p_9355_) {
            p_9354_.m_6267_().forEach((p_143477_) -> {
               super.m_142519_(p_143477_, p_9354_.m_7146_(p_143477_));
            });
         }

      }, () -> {
         return "lightChunk " + chunkpos + " " + p_9355_;
      }));
      return CompletableFuture.supplyAsync(() -> {
         p_9354_.m_8094_(true);
         super.m_6462_(chunkpos, false);
         this.f_9299_.m_140375_(chunkpos);
         return p_9354_;
      }, (p_9334_) -> {
         this.m_9312_(chunkpos.f_45578_, chunkpos.f_45579_, ThreadedLevelLightEngine.TaskType.POST_UPDATE, p_9334_);
      });
   }

   public void m_9409_() {
      if ((!this.f_9298_.isEmpty() || super.m_142182_()) && this.f_9302_.compareAndSet(false, true)) {
         this.f_9297_.m_6937_(() -> {
            this.m_9366_();
            this.f_9302_.set(false);
         });
      }

   }

   private void m_9366_() {
      int i = Math.min(this.f_9298_.size(), this.f_9301_);
      ObjectListIterator<Pair<ThreadedLevelLightEngine.TaskType, Runnable>> objectlistiterator = this.f_9298_.iterator();

      int j;
      for(j = 0; objectlistiterator.hasNext() && j < i; ++j) {
         Pair<ThreadedLevelLightEngine.TaskType, Runnable> pair = objectlistiterator.next();
         if (pair.getFirst() == ThreadedLevelLightEngine.TaskType.PRE_UPDATE) {
            pair.getSecond().run();
         }
      }

      objectlistiterator.back(j);
      super.m_142528_(Integer.MAX_VALUE, true, true);

      for(int k = 0; objectlistiterator.hasNext() && k < i; ++k) {
         Pair<ThreadedLevelLightEngine.TaskType, Runnable> pair1 = objectlistiterator.next();
         if (pair1.getFirst() == ThreadedLevelLightEngine.TaskType.POST_UPDATE) {
            pair1.getSecond().run();
         }

         objectlistiterator.remove();
      }

   }

   public void m_9310_(int p_9311_) {
      this.f_9301_ = p_9311_;
   }

   static enum TaskType {
      PRE_UPDATE,
      POST_UPDATE;
   }
}