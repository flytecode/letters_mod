package net.minecraft.server.level;

import net.minecraft.core.BlockPos;

public class BlockDestructionProgress implements Comparable<BlockDestructionProgress> {
   private final int f_139974_;
   private final BlockPos f_139975_;
   private int f_139976_;
   private int f_139977_;

   public BlockDestructionProgress(int p_139979_, BlockPos p_139980_) {
      this.f_139974_ = p_139979_;
      this.f_139975_ = p_139980_;
   }

   public int m_142980_() {
      return this.f_139974_;
   }

   public BlockPos m_139985_() {
      return this.f_139975_;
   }

   public void m_139981_(int p_139982_) {
      if (p_139982_ > 10) {
         p_139982_ = 10;
      }

      this.f_139976_ = p_139982_;
   }

   public int m_139988_() {
      return this.f_139976_;
   }

   public void m_139986_(int p_139987_) {
      this.f_139977_ = p_139987_;
   }

   public int m_139991_() {
      return this.f_139977_;
   }

   public boolean equals(Object p_139993_) {
      if (this == p_139993_) {
         return true;
      } else if (p_139993_ != null && this.getClass() == p_139993_.getClass()) {
         BlockDestructionProgress blockdestructionprogress = (BlockDestructionProgress)p_139993_;
         return this.f_139974_ == blockdestructionprogress.f_139974_;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Integer.hashCode(this.f_139974_);
   }

   public int compareTo(BlockDestructionProgress p_139984_) {
      return this.f_139976_ != p_139984_.f_139976_ ? Integer.compare(this.f_139976_, p_139984_.f_139976_) : Integer.compare(this.f_139974_, p_139984_.f_139974_);
   }
}