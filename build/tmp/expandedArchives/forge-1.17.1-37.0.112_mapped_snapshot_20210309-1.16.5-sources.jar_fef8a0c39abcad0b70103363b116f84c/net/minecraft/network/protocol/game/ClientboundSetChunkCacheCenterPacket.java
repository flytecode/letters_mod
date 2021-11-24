package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetChunkCacheCenterPacket implements Packet<ClientGamePacketListener> {
   private final int f_133082_;
   private final int f_133083_;

   public ClientboundSetChunkCacheCenterPacket(int p_133086_, int p_133087_) {
      this.f_133082_ = p_133086_;
      this.f_133083_ = p_133087_;
   }

   public ClientboundSetChunkCacheCenterPacket(FriendlyByteBuf p_179282_) {
      this.f_133082_ = p_179282_.m_130242_();
      this.f_133083_ = p_179282_.m_130242_();
   }

   public void m_5779_(FriendlyByteBuf p_133096_) {
      p_133096_.m_130130_(this.f_133082_);
      p_133096_.m_130130_(this.f_133083_);
   }

   public void m_5797_(ClientGamePacketListener p_133093_) {
      p_133093_.m_8065_(this);
   }

   public int m_133094_() {
      return this.f_133082_;
   }

   public int m_133097_() {
      return this.f_133083_;
   }
}