package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public interface Nameable {
   Component m_7755_();

   default boolean m_8077_() {
      return this.m_7770_() != null;
   }

   default Component m_5446_() {
      return this.m_7755_();
   }

   @Nullable
   default Component m_7770_() {
      return null;
   }
}