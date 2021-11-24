package net.minecraft.world.level.chunk;

public class OldDataLayer {
   public final byte[] f_63050_;
   private final int f_63051_;
   private final int f_63052_;

   public OldDataLayer(byte[] p_63054_, int p_63055_) {
      this.f_63050_ = p_63054_;
      this.f_63051_ = p_63055_;
      this.f_63052_ = p_63055_ + 4;
   }

   public int m_63056_(int p_63057_, int p_63058_, int p_63059_) {
      int i = p_63057_ << this.f_63052_ | p_63059_ << this.f_63051_ | p_63058_;
      int j = i >> 1;
      int k = i & 1;
      return k == 0 ? this.f_63050_[j] & 15 : this.f_63050_[j] >> 4 & 15;
   }
}