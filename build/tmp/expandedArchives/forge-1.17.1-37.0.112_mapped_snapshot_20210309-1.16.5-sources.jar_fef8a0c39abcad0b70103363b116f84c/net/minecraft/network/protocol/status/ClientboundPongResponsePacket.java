package net.minecraft.network.protocol.status;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPongResponsePacket implements Packet<ClientStatusPacketListener> {
   private final long f_134873_;

   public ClientboundPongResponsePacket(long p_134876_) {
      this.f_134873_ = p_134876_;
   }

   public ClientboundPongResponsePacket(FriendlyByteBuf p_179831_) {
      this.f_134873_ = p_179831_.readLong();
   }

   public void m_5779_(FriendlyByteBuf p_134884_) {
      p_134884_.writeLong(this.f_134873_);
   }

   public void m_5797_(ClientStatusPacketListener p_134882_) {
      p_134882_.m_7017_(this);
   }

   public long m_179832_() {
      return this.f_134873_;
   }
}