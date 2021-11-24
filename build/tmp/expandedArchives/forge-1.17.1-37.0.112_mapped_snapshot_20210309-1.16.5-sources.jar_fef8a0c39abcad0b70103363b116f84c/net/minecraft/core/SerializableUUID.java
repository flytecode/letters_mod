package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.Util;

public final class SerializableUUID {
   public static final Codec<UUID> f_123272_ = Codec.INT_STREAM.comapFlatMap((p_123280_) -> {
      return Util.m_137539_(p_123280_, 4).map(SerializableUUID::m_123281_);
   }, (p_123284_) -> {
      return Arrays.stream(m_123277_(p_123284_));
   });

   private SerializableUUID() {
   }

   public static UUID m_123281_(int[] p_123282_) {
      return new UUID((long)p_123282_[0] << 32 | (long)p_123282_[1] & 4294967295L, (long)p_123282_[2] << 32 | (long)p_123282_[3] & 4294967295L);
   }

   public static int[] m_123277_(UUID p_123278_) {
      long i = p_123278_.getMostSignificantBits();
      long j = p_123278_.getLeastSignificantBits();
      return m_123274_(i, j);
   }

   private static int[] m_123274_(long p_123275_, long p_123276_) {
      return new int[]{(int)(p_123275_ >> 32), (int)p_123275_, (int)(p_123276_ >> 32), (int)p_123276_};
   }

   public static UUID m_175580_(Dynamic<?> p_175581_) {
      int[] aint = p_175581_.asIntStream().toArray();
      if (aint.length != 4) {
         throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + aint.length + ".");
      } else {
         return m_123281_(aint);
      }
   }
}