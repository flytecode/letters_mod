package net.minecraft.world.entity.animal.horse;

import java.util.Arrays;
import java.util.Comparator;

public enum Markings {
   NONE(0),
   WHITE(1),
   WHITE_FIELD(2),
   WHITE_DOTS(3),
   BLACK_DOTS(4);

   private static final Markings[] f_30861_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Markings::m_30869_)).toArray((p_30873_) -> {
      return new Markings[p_30873_];
   });
   private final int f_30862_;

   private Markings(int p_30868_) {
      this.f_30862_ = p_30868_;
   }

   public int m_30869_() {
      return this.f_30862_;
   }

   public static Markings m_30870_(int p_30871_) {
      return f_30861_[p_30871_ % f_30861_.length];
   }
}