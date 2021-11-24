package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundChatPacket implements Packet<ServerGamePacketListener> {
   private static final int f_179543_ = 256;
   private final String f_133827_;

   public ServerboundChatPacket(String p_133830_) {
      if (p_133830_.length() > 256) {
         p_133830_ = p_133830_.substring(0, 256);
      }

      this.f_133827_ = p_133830_;
   }

   public ServerboundChatPacket(FriendlyByteBuf p_179545_) {
      this.f_133827_ = p_179545_.m_130136_(256);
   }

   public void m_5779_(FriendlyByteBuf p_133839_) {
      p_133839_.m_130070_(this.f_133827_);
   }

   public void m_5797_(ServerGamePacketListener p_133836_) {
      p_133836_.m_7388_(this);
   }

   public String m_133837_() {
      return this.f_133827_;
   }
}