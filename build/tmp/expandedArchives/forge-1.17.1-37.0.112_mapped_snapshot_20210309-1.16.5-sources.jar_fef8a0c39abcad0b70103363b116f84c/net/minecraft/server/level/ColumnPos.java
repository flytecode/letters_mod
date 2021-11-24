package net.minecraft.server.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public class ColumnPos {
   private static final long f_143191_ = 32L;
   private static final long f_143192_ = 4294967295L;
   private static final int f_143193_ = 1664525;
   private static final int f_143194_ = 1013904223;
   private static final int f_143195_ = -559038737;
   public final int f_140723_;
   public final int f_140724_;

   public ColumnPos(int p_140726_, int p_140727_) {
      this.f_140723_ = p_140726_;
      this.f_140724_ = p_140727_;
   }

   public ColumnPos(BlockPos p_140729_) {
      this.f_140723_ = p_140729_.m_123341_();
      this.f_140724_ = p_140729_.m_123343_();
   }

   public ChunkPos m_143196_() {
      return new ChunkPos(SectionPos.m_123171_(this.f_140723_), SectionPos.m_123171_(this.f_140724_));
   }

   public long m_143200_() {
      return m_143197_(this.f_140723_, this.f_140724_);
   }

   public static long m_143197_(int p_143198_, int p_143199_) {
      return (long)p_143198_ & 4294967295L | ((long)p_143199_ & 4294967295L) << 32;
   }

   public String toString() {
      return "[" + this.f_140723_ + ", " + this.f_140724_ + "]";
   }

   public int hashCode() {
      int i = 1664525 * this.f_140723_ + 1013904223;
      int j = 1664525 * (this.f_140724_ ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_140731_) {
      if (this == p_140731_) {
         return true;
      } else if (!(p_140731_ instanceof ColumnPos)) {
         return false;
      } else {
         ColumnPos columnpos = (ColumnPos)p_140731_;
         return this.f_140723_ == columnpos.f_140723_ && this.f_140724_ == columnpos.f_140724_;
      }
   }
}