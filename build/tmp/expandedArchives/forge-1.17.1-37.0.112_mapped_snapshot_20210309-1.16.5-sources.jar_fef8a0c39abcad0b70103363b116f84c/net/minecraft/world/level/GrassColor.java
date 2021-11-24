package net.minecraft.world.level;

public class GrassColor {
   private static int[] f_46413_ = new int[65536];

   public static void m_46418_(int[] p_46419_) {
      f_46413_ = p_46419_;
   }

   public static int m_46415_(double p_46416_, double p_46417_) {
      p_46417_ = p_46417_ * p_46416_;
      int i = (int)((1.0D - p_46416_) * 255.0D);
      int j = (int)((1.0D - p_46417_) * 255.0D);
      int k = j << 8 | i;
      return k >= f_46413_.length ? -65281 : f_46413_[k];
   }
}