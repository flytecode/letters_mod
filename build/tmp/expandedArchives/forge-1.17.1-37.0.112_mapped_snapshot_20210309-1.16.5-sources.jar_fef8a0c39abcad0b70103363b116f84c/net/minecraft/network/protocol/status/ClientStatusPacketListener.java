package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketListener;

public interface ClientStatusPacketListener extends PacketListener {
   void m_6440_(ClientboundStatusResponsePacket p_134872_);

   void m_7017_(ClientboundPongResponsePacket p_134871_);
}