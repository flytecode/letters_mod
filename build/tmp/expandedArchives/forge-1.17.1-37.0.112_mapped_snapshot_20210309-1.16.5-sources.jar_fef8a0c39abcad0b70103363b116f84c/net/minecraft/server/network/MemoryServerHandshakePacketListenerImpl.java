package net.minecraft.server.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.server.MinecraftServer;

public class MemoryServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
   private final MinecraftServer f_9688_;
   private final Connection f_9689_;

   public MemoryServerHandshakePacketListenerImpl(MinecraftServer p_9691_, Connection p_9692_) {
      this.f_9688_ = p_9691_;
      this.f_9689_ = p_9692_;
   }

   public void m_7322_(ClientIntentionPacket p_9697_) {
      if (!net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerLogin(p_9697_, this.f_9689_)) return;
      this.f_9689_.m_129498_(p_9697_.m_134735_());
      this.f_9689_.m_129505_(new ServerLoginPacketListenerImpl(this.f_9688_, this.f_9689_));
   }

   public void m_7026_(Component p_9695_) {
   }

   public Connection m_6198_() {
      return this.f_9689_;
   }
}
