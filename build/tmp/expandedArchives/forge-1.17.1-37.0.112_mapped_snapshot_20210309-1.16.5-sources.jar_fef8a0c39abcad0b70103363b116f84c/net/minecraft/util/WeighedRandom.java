package net.minecraft.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeighedRandom {
   static final Logger f_145028_ = LogManager.getLogger();

   public static int m_14470_(List<? extends WeighedRandom.WeighedRandomItem> p_14471_) {
      long i = 0L;

      for(WeighedRandom.WeighedRandomItem weighedrandom$weighedrandomitem : p_14471_) {
         i += (long)weighedrandom$weighedrandomitem.f_14482_;
      }

      if (i > 2147483647L) {
         throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
      } else {
         return (int)i;
      }
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> m_145037_(Random p_145038_, List<T> p_145039_, int p_145040_) {
      if (p_145040_ < 0) {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException("Negative total weight in getRandomItem"));
      } else if (p_145040_ == 0) {
         return Optional.empty();
      } else {
         int i = p_145038_.nextInt(p_145040_);
         return m_145031_(p_145039_, i);
      }
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> m_145031_(List<T> p_145032_, int p_145033_) {
      for(T t : p_145032_) {
         p_145033_ -= t.f_14482_;
         if (p_145033_ < 0) {
            return Optional.of(t);
         }
      }

      return Optional.empty();
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> m_145034_(Random p_145035_, List<T> p_145036_) {
      return m_145037_(p_145035_, p_145036_, m_14470_(p_145036_));
   }

   public static class WeighedRandomItem {
      public final int f_14482_;

      public WeighedRandomItem(int p_14484_) {
         if (p_14484_ < 0) {
            throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException("Weight should be >= 0"));
         } else {
            if (p_14484_ == 0 && SharedConstants.f_136183_) {
               WeighedRandom.f_145028_.warn("Found 0 weight, make sure this is intentional!");
            }

            this.f_14482_ = p_14484_;
         }
      }
   }
}