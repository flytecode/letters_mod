package net.minecraft.client.color.block;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockTintCache {
   private static final int f_168641_ = 256;
   private final ThreadLocal<BlockTintCache.LatestCacheInfo> f_92650_ = ThreadLocal.withInitial(BlockTintCache.LatestCacheInfo::new);
   private final Long2ObjectLinkedOpenHashMap<int[]> f_92651_ = new Long2ObjectLinkedOpenHashMap<>(256, 0.25F);
   private final ReentrantReadWriteLock f_92652_ = new ReentrantReadWriteLock();

   public int m_92658_(BlockPos p_92659_, IntSupplier p_92660_) {
      int i = SectionPos.m_123171_(p_92659_.m_123341_());
      int j = SectionPos.m_123171_(p_92659_.m_123343_());
      BlockTintCache.LatestCacheInfo blocktintcache$latestcacheinfo = this.f_92650_.get();
      if (blocktintcache$latestcacheinfo.f_92665_ != i || blocktintcache$latestcacheinfo.f_92666_ != j) {
         blocktintcache$latestcacheinfo.f_92665_ = i;
         blocktintcache$latestcacheinfo.f_92666_ = j;
         blocktintcache$latestcacheinfo.f_92667_ = this.m_92662_(i, j);
      }

      int k = p_92659_.m_123341_() & 15;
      int l = p_92659_.m_123343_() & 15;
      int i1 = l << 4 | k;
      int j1 = blocktintcache$latestcacheinfo.f_92667_[i1];
      if (j1 != -1) {
         return j1;
      } else {
         int k1 = p_92660_.getAsInt();
         blocktintcache$latestcacheinfo.f_92667_[i1] = k1;
         return k1;
      }
   }

   public void m_92655_(int p_92656_, int p_92657_) {
      try {
         this.f_92652_.writeLock().lock();

         for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
               long k = ChunkPos.m_45589_(p_92656_ + i, p_92657_ + j);
               this.f_92651_.remove(k);
            }
         }
      } finally {
         this.f_92652_.writeLock().unlock();
      }

   }

   public void m_92654_() {
      try {
         this.f_92652_.writeLock().lock();
         this.f_92651_.clear();
      } finally {
         this.f_92652_.writeLock().unlock();
      }

   }

   private int[] m_92662_(int p_92663_, int p_92664_) {
      long i = ChunkPos.m_45589_(p_92663_, p_92664_);
      this.f_92652_.readLock().lock();

      int[] aint;
      try {
         aint = this.f_92651_.get(i);
      } finally {
         this.f_92652_.readLock().unlock();
      }

      if (aint != null) {
         return aint;
      } else {
         int[] aint1 = new int[256];
         Arrays.fill(aint1, -1);

         try {
            this.f_92652_.writeLock().lock();
            if (this.f_92651_.size() >= 256) {
               this.f_92651_.removeFirst();
            }

            this.f_92651_.put(i, aint1);
         } finally {
            this.f_92652_.writeLock().unlock();
         }

         return aint1;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class LatestCacheInfo {
      public int f_92665_ = Integer.MIN_VALUE;
      public int f_92666_ = Integer.MIN_VALUE;
      public int[] f_92667_;

      private LatestCacheInfo() {
      }
   }
}