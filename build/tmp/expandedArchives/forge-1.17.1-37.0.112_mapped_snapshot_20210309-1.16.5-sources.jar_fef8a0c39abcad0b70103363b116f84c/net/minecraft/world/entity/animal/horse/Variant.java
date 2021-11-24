package net.minecraft.world.entity.animal.horse;

import java.util.Arrays;
import java.util.Comparator;

public enum Variant {
   WHITE(0),
   CREAMY(1),
   CHESTNUT(2),
   BROWN(3),
   BLACK(4),
   GRAY(5),
   DARKBROWN(6);

   private static final Variant[] f_30977_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Variant::m_30985_)).toArray((p_30989_) -> {
      return new Variant[p_30989_];
   });
   private final int f_30978_;

   private Variant(int p_30984_) {
      this.f_30978_ = p_30984_;
   }

   public int m_30985_() {
      return this.f_30978_;
   }

   public static Variant m_30986_(int p_30987_) {
      return f_30977_[p_30987_ % f_30977_.length];
   }
}