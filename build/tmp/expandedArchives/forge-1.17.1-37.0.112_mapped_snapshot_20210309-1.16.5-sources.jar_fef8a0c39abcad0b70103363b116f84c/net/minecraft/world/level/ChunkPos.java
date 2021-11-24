package net.minecraft.world.level;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class ChunkPos {
   public static final long f_45577_ = m_45589_(1875016, 1875016);
   private static final long f_151375_ = 32L;
   private static final long f_151376_ = 4294967295L;
   private static final int f_151377_ = 5;
   private static final int f_151378_ = 31;
   public final int f_45578_;
   public final int f_45579_;
   private static final int f_151379_ = 1664525;
   private static final int f_151380_ = 1013904223;
   private static final int f_151381_ = -559038737;

   public ChunkPos(int p_45582_, int p_45583_) {
      this.f_45578_ = p_45582_;
      this.f_45579_ = p_45583_;
   }

   public ChunkPos(BlockPos p_45587_) {
      this.f_45578_ = SectionPos.m_123171_(p_45587_.m_123341_());
      this.f_45579_ = SectionPos.m_123171_(p_45587_.m_123343_());
   }

   public ChunkPos(long p_45585_) {
      this.f_45578_ = (int)p_45585_;
      this.f_45579_ = (int)(p_45585_ >> 32);
   }

   public long m_45588_() {
      return m_45589_(this.f_45578_, this.f_45579_);
   }

   public static long m_45589_(int p_45590_, int p_45591_) {
      return (long)p_45590_ & 4294967295L | ((long)p_45591_ & 4294967295L) << 32;
   }

   public static long m_151388_(BlockPos p_151389_) {
      return m_45589_(SectionPos.m_123171_(p_151389_.m_123341_()), SectionPos.m_123171_(p_151389_.m_123343_()));
   }

   public static int m_45592_(long p_45593_) {
      return (int)(p_45593_ & 4294967295L);
   }

   public static int m_45602_(long p_45603_) {
      return (int)(p_45603_ >>> 32 & 4294967295L);
   }

   public int hashCode() {
      int i = 1664525 * this.f_45578_ + 1013904223;
      int j = 1664525 * (this.f_45579_ ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_45607_) {
      if (this == p_45607_) {
         return true;
      } else if (!(p_45607_ instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos chunkpos = (ChunkPos)p_45607_;
         return this.f_45578_ == chunkpos.f_45578_ && this.f_45579_ == chunkpos.f_45579_;
      }
   }

   public int m_151390_() {
      return this.m_151382_(8);
   }

   public int m_151393_() {
      return this.m_151391_(8);
   }

   public int m_45604_() {
      return SectionPos.m_123223_(this.f_45578_);
   }

   public int m_45605_() {
      return SectionPos.m_123223_(this.f_45579_);
   }

   public int m_45608_() {
      return this.m_151382_(15);
   }

   public int m_45609_() {
      return this.m_151391_(15);
   }

   public int m_45610_() {
      return this.f_45578_ >> 5;
   }

   public int m_45612_() {
      return this.f_45579_ >> 5;
   }

   public int m_45613_() {
      return this.f_45578_ & 31;
   }

   public int m_45614_() {
      return this.f_45579_ & 31;
   }

   public BlockPos m_151384_(int p_151385_, int p_151386_, int p_151387_) {
      return new BlockPos(this.m_151382_(p_151385_), p_151386_, this.m_151391_(p_151387_));
   }

   public int m_151382_(int p_151383_) {
      return SectionPos.m_175554_(this.f_45578_, p_151383_);
   }

   public int m_151391_(int p_151392_) {
      return SectionPos.m_175554_(this.f_45579_, p_151392_);
   }

   public BlockPos m_151394_(int p_151395_) {
      return new BlockPos(this.m_151390_(), p_151395_, this.m_151393_());
   }

   public String toString() {
      return "[" + this.f_45578_ + ", " + this.f_45579_ + "]";
   }

   public BlockPos m_45615_() {
      return new BlockPos(this.m_45604_(), 0, this.m_45605_());
   }

   public int m_45594_(ChunkPos p_45595_) {
      return Math.max(Math.abs(this.f_45578_ - p_45595_.f_45578_), Math.abs(this.f_45579_ - p_45595_.f_45579_));
   }

   public static Stream<ChunkPos> m_45596_(ChunkPos p_45597_, int p_45598_) {
      return m_45599_(new ChunkPos(p_45597_.f_45578_ - p_45598_, p_45597_.f_45579_ - p_45598_), new ChunkPos(p_45597_.f_45578_ + p_45598_, p_45597_.f_45579_ + p_45598_));
   }

   public static Stream<ChunkPos> m_45599_(final ChunkPos p_45600_, final ChunkPos p_45601_) {
      int i = Math.abs(p_45600_.f_45578_ - p_45601_.f_45578_) + 1;
      int j = Math.abs(p_45600_.f_45579_ - p_45601_.f_45579_) + 1;
      final int k = p_45600_.f_45578_ < p_45601_.f_45578_ ? 1 : -1;
      final int l = p_45600_.f_45579_ < p_45601_.f_45579_ ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkPos>((long)(i * j), 64) {
         @Nullable
         private ChunkPos f_45621_;

         public boolean tryAdvance(Consumer<? super ChunkPos> p_45630_) {
            if (this.f_45621_ == null) {
               this.f_45621_ = p_45600_;
            } else {
               int i1 = this.f_45621_.f_45578_;
               int j1 = this.f_45621_.f_45579_;
               if (i1 == p_45601_.f_45578_) {
                  if (j1 == p_45601_.f_45579_) {
                     return false;
                  }

                  this.f_45621_ = new ChunkPos(p_45600_.f_45578_, j1 + l);
               } else {
                  this.f_45621_ = new ChunkPos(i1 + k, j1);
               }
            }

            p_45630_.accept(this.f_45621_);
            return true;
         }
      }, false);
   }
}