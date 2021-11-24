package com.mojang.math;

import java.util.Arrays;
import net.minecraft.Util;

public enum SymmetricGroup3 {
   P123(0, 1, 2),
   P213(1, 0, 2),
   P132(0, 2, 1),
   P231(1, 2, 0),
   P312(2, 0, 1),
   P321(2, 1, 0);

   private final int[] f_109168_;
   private final Matrix3f f_109169_;
   private static final int f_175574_ = 3;
   private static final SymmetricGroup3[][] f_109170_ = Util.m_137469_(new SymmetricGroup3[values().length][values().length], (p_109188_) -> {
      for(SymmetricGroup3 symmetricgroup3 : values()) {
         for(SymmetricGroup3 symmetricgroup31 : values()) {
            int[] aint = new int[3];

            for(int i = 0; i < 3; ++i) {
               aint[i] = symmetricgroup3.f_109168_[symmetricgroup31.f_109168_[i]];
            }

            SymmetricGroup3 symmetricgroup32 = Arrays.stream(values()).filter((p_175577_) -> {
               return Arrays.equals(p_175577_.f_109168_, aint);
            }).findFirst().get();
            p_109188_[symmetricgroup3.ordinal()][symmetricgroup31.ordinal()] = symmetricgroup32;
         }
      }

   });

   private SymmetricGroup3(int p_109176_, int p_109177_, int p_109178_) {
      this.f_109168_ = new int[]{p_109176_, p_109177_, p_109178_};
      this.f_109169_ = new Matrix3f();
      this.f_109169_.m_8165_(0, this.m_109180_(0), 1.0F);
      this.f_109169_.m_8165_(1, this.m_109180_(1), 1.0F);
      this.f_109169_.m_8165_(2, this.m_109180_(2), 1.0F);
   }

   public SymmetricGroup3 m_109182_(SymmetricGroup3 p_109183_) {
      return f_109170_[this.ordinal()][p_109183_.ordinal()];
   }

   public int m_109180_(int p_109181_) {
      return this.f_109168_[p_109181_];
   }

   public Matrix3f m_109179_() {
      return this.f_109169_;
   }
}