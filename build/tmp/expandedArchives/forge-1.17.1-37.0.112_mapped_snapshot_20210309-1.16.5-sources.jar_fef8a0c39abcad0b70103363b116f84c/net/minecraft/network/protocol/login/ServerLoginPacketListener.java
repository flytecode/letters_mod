package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketListener;

public interface ServerLoginPacketListener extends PacketListener {
   void m_5990_(ServerboundHelloPacket p_134823_);

   void m_8072_(ServerboundKeyPacket p_134824_);

   void m_7223_(ServerboundCustomQueryPacket p_134822_);
}