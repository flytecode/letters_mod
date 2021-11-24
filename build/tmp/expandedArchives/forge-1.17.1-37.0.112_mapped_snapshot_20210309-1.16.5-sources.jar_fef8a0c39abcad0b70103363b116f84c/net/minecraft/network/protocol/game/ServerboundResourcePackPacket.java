package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundResourcePackPacket implements Packet<ServerGamePacketListener> {
   private final ServerboundResourcePackPacket.Action f_134406_;

   public ServerboundResourcePackPacket(ServerboundResourcePackPacket.Action p_134409_) {
      this.f_134406_ = p_134409_;
   }

   public ServerboundResourcePackPacket(FriendlyByteBuf p_179740_) {
      this.f_134406_ = p_179740_.m_130066_(ServerboundResourcePackPacket.Action.class);
   }

   public void m_5779_(FriendlyByteBuf p_134417_) {
      p_134417_.m_130068_(this.f_134406_);
   }

   public void m_5797_(ServerGamePacketListener p_134415_) {
      p_134415_.m_7529_(this);
   }

   public ServerboundResourcePackPacket.Action m_179741_() {
      return this.f_134406_;
   }

   public static enum Action {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED;
   }
}