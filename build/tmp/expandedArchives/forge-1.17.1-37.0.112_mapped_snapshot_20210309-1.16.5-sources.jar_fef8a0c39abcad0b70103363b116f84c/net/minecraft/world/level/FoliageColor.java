package net.minecraft.world.level;

public class FoliageColor {
   private static int[] f_46104_ = new int[65536];

   public static void m_46110_(int[] p_46111_) {
      f_46104_ = p_46111_;
   }

   public static int m_46107_(double p_46108_, double p_46109_) {
      p_46109_ = p_46109_ * p_46108_;
      int i = (int)((1.0D - p_46108_) * 255.0D);
      int j = (int)((1.0D - p_46109_) * 255.0D);
      int k = j << 8 | i;
      return k >= f_46104_.length ? m_46113_() : f_46104_[k];
   }

   public static int m_46106_() {
      return 6396257;
   }

   public static int m_46112_() {
      return 8431445;
   }

   public static int m_46113_() {
      return 4764952;
   }
}