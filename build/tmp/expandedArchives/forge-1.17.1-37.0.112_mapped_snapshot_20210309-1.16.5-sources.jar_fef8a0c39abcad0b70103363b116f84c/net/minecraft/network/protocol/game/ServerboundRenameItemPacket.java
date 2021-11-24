package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundRenameItemPacket implements Packet<ServerGamePacketListener> {
   private final String f_134393_;

   public ServerboundRenameItemPacket(String p_134396_) {
      this.f_134393_ = p_134396_;
   }

   public ServerboundRenameItemPacket(FriendlyByteBuf p_179738_) {
      this.f_134393_ = p_179738_.m_130277_();
   }

   public void m_5779_(FriendlyByteBuf p_134405_) {
      p_134405_.m_130070_(this.f_134393_);
   }

   public void m_5797_(ServerGamePacketListener p_134402_) {
      p_134402_.m_5591_(this);
   }

   public String m_134403_() {
      return this.f_134393_;
   }
}