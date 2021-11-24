package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketListener;

public interface ServerStatusPacketListener extends PacketListener {
   void m_7883_(ServerboundPingRequestPacket p_134986_);

   void m_6733_(ServerboundStatusRequestPacket p_134987_);
}