package net.minecraft.network.protocol;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;

public interface Packet<T extends PacketListener> {
   void m_5779_(FriendlyByteBuf p_131343_);

   void m_5797_(T p_131342_);

   default boolean m_6588_() {
      return false;
   }
}