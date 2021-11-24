package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketListener;

public interface ClientLoginPacketListener extends PacketListener {
   void m_7318_(ClientboundHelloPacket p_134742_);

   void m_7056_(ClientboundGameProfilePacket p_134741_);

   void m_5800_(ClientboundLoginDisconnectPacket p_134744_);

   void m_5693_(ClientboundLoginCompressionPacket p_134743_);

   void m_7254_(ClientboundCustomQueryPacket p_134740_);
}