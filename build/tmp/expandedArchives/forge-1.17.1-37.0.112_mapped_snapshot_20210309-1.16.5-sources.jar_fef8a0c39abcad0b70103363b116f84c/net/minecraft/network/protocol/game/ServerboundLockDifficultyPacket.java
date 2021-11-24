package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundLockDifficultyPacket implements Packet<ServerGamePacketListener> {
   private final boolean f_134105_;

   public ServerboundLockDifficultyPacket(boolean p_134108_) {
      this.f_134105_ = p_134108_;
   }

   public void m_5797_(ServerGamePacketListener p_134114_) {
      p_134114_.m_7728_(this);
   }

   public ServerboundLockDifficultyPacket(FriendlyByteBuf p_179673_) {
      this.f_134105_ = p_179673_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_134117_) {
      p_134117_.writeBoolean(this.f_134105_);
   }

   public boolean m_134115_() {
      return this.f_134105_;
   }
}