package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundClientCommandPacket implements Packet<ServerGamePacketListener> {
   private final ServerboundClientCommandPacket.Action f_133840_;

   public ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action p_133843_) {
      this.f_133840_ = p_133843_;
   }

   public ServerboundClientCommandPacket(FriendlyByteBuf p_179547_) {
      this.f_133840_ = p_179547_.m_130066_(ServerboundClientCommandPacket.Action.class);
   }

   public void m_5779_(FriendlyByteBuf p_133852_) {
      p_133852_.m_130068_(this.f_133840_);
   }

   public void m_5797_(ServerGamePacketListener p_133849_) {
      p_133849_.m_6272_(this);
   }

   public ServerboundClientCommandPacket.Action m_133850_() {
      return this.f_133840_;
   }

   public static enum Action {
      PERFORM_RESPAWN,
      REQUEST_STATS;
   }
}