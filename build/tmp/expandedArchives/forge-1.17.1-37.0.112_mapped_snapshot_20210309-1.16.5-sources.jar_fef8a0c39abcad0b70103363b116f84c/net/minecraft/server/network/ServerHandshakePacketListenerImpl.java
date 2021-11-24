package net.minecraft.server.network;

import net.minecraft.SharedConstants;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.MinecraftServer;

public class ServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
   private static final Component f_9964_ = new TextComponent("Ignoring status request");
   private final MinecraftServer f_9965_;
   private final Connection f_9966_;

   public ServerHandshakePacketListenerImpl(MinecraftServer p_9969_, Connection p_9970_) {
      this.f_9965_ = p_9969_;
      this.f_9966_ = p_9970_;
   }

   public void m_7322_(ClientIntentionPacket p_9975_) {
      if (!net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerLogin(p_9975_, this.f_9966_)) return;
      switch(p_9975_.m_134735_()) {
      case LOGIN:
         this.f_9966_.m_129498_(ConnectionProtocol.LOGIN);
         if (p_9975_.m_134738_() != SharedConstants.m_136187_().getProtocolVersion()) {
            Component component;
            if (p_9975_.m_134738_() < 754) {
               component = new TranslatableComponent("multiplayer.disconnect.outdated_client", SharedConstants.m_136187_().getName());
            } else {
               component = new TranslatableComponent("multiplayer.disconnect.incompatible", SharedConstants.m_136187_().getName());
            }

            this.f_9966_.m_129512_(new ClientboundLoginDisconnectPacket(component));
            this.f_9966_.m_129507_(component);
         } else {
            this.f_9966_.m_129505_(new ServerLoginPacketListenerImpl(this.f_9965_, this.f_9966_));
         }
         break;
      case STATUS:
         if (this.f_9965_.m_6373_()) {
            this.f_9966_.m_129498_(ConnectionProtocol.STATUS);
            this.f_9966_.m_129505_(new ServerStatusPacketListenerImpl(this.f_9965_, this.f_9966_));
         } else {
            this.f_9966_.m_129507_(f_9964_);
         }
         break;
      default:
         throw new UnsupportedOperationException("Invalid intention " + p_9975_.m_134735_());
      }

   }

   public void m_7026_(Component p_9973_) {
   }

   public Connection m_6198_() {
      return this.f_9966_;
   }
}
