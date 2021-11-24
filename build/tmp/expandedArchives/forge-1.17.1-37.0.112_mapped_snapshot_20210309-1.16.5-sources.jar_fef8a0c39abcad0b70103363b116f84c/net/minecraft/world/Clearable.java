package net.minecraft.world;

import javax.annotation.Nullable;

public interface Clearable {
   void m_6211_();

   static void m_18908_(@Nullable Object p_18909_) {
      if (p_18909_ instanceof Clearable) {
         ((Clearable)p_18909_).m_6211_();
      }

   }
}