package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;

public class ChunkTaskPriorityQueue<T> {
   public static final int f_140508_ = ChunkMap.f_140127_ + 2;
   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> f_140509_ = IntStream.range(0, f_140508_).mapToObj((p_140520_) -> {
      return new Long2ObjectLinkedOpenHashMap<List<Optional<T>>>();
   }).collect(Collectors.toList());
   private volatile int f_140510_ = f_140508_;
   private final String f_140511_;
   private final LongSet f_140512_ = new LongOpenHashSet();
   private final int f_140513_;

   public ChunkTaskPriorityQueue(String p_140516_, int p_140517_) {
      this.f_140511_ = p_140516_;
      this.f_140513_ = p_140517_;
   }

   protected void m_140521_(int p_140522_, ChunkPos p_140523_, int p_140524_) {
      if (p_140522_ < f_140508_) {
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2objectlinkedopenhashmap = this.f_140509_.get(p_140522_);
         List<Optional<T>> list = long2objectlinkedopenhashmap.remove(p_140523_.m_45588_());
         if (p_140522_ == this.f_140510_) {
            while(this.f_140510_ < f_140508_ && this.f_140509_.get(this.f_140510_).isEmpty()) {
               ++this.f_140510_;
            }
         }

         if (list != null && !list.isEmpty()) {
            this.f_140509_.get(p_140524_).computeIfAbsent(p_140523_.m_45588_(), (p_140547_) -> {
               return Lists.newArrayList();
            }).addAll(list);
            this.f_140510_ = Math.min(this.f_140510_, p_140524_);
         }

      }
   }

   protected void m_140535_(Optional<T> p_140536_, long p_140537_, int p_140538_) {
      this.f_140509_.get(p_140538_).computeIfAbsent(p_140537_, (p_140545_) -> {
         return Lists.newArrayList();
      }).add(p_140536_);
      this.f_140510_ = Math.min(this.f_140510_, p_140538_);
   }

   protected void m_140530_(long p_140531_, boolean p_140532_) {
      for(Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2objectlinkedopenhashmap : this.f_140509_) {
         List<Optional<T>> list = long2objectlinkedopenhashmap.get(p_140531_);
         if (list != null) {
            if (p_140532_) {
               list.clear();
            } else {
               list.removeIf((p_140534_) -> {
                  return !p_140534_.isPresent();
               });
            }

            if (list.isEmpty()) {
               long2objectlinkedopenhashmap.remove(p_140531_);
            }
         }
      }

      while(this.f_140510_ < f_140508_ && this.f_140509_.get(this.f_140510_).isEmpty()) {
         ++this.f_140510_;
      }

      this.f_140512_.remove(p_140531_);
   }

   private Runnable m_140525_(long p_140526_) {
      return () -> {
         this.f_140512_.add(p_140526_);
      };
   }

   @Nullable
   public Stream<Either<T, Runnable>> m_140518_() {
      if (this.f_140512_.size() >= this.f_140513_) {
         return null;
      } else if (this.f_140510_ >= f_140508_) {
         return null;
      } else {
         int i = this.f_140510_;
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2objectlinkedopenhashmap = this.f_140509_.get(i);
         long j = long2objectlinkedopenhashmap.firstLongKey();

         List<Optional<T>> list;
         for(list = long2objectlinkedopenhashmap.removeFirst(); this.f_140510_ < f_140508_ && this.f_140509_.get(this.f_140510_).isEmpty(); ++this.f_140510_) {
         }

         return list.stream().map((p_140529_) -> {
            return p_140529_.<Either<T, Runnable>>map(Either::left).orElseGet(() -> {
               return Either.right(this.m_140525_(j));
            });
         });
      }
   }

   public String toString() {
      return this.f_140511_ + " " + this.f_140510_ + "...";
   }

   @VisibleForTesting
   LongSet m_140539_() {
      return new LongOpenHashSet(this.f_140512_);
   }
}