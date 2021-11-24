package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;

public class ClientboundAddVibrationSignalPacket implements Packet<ClientGamePacketListener> {
   private final VibrationPath f_178571_;

   public ClientboundAddVibrationSignalPacket(VibrationPath p_178573_) {
      this.f_178571_ = p_178573_;
   }

   public ClientboundAddVibrationSignalPacket(FriendlyByteBuf p_178575_) {
      this.f_178571_ = VibrationPath.m_157943_(p_178575_);
   }

   public void m_5779_(FriendlyByteBuf p_178577_) {
      VibrationPath.m_157945_(p_178577_, this.f_178571_);
   }

   public void m_5797_(ClientGamePacketListener p_178581_) {
      p_178581_.m_142024_(this);
   }

   public VibrationPath m_178582_() {
      return this.f_178571_;
   }
}