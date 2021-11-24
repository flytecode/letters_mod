package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSetBeaconPacket implements Packet<ServerGamePacketListener> {
   private final int f_134472_;
   private final int f_134473_;

   public ServerboundSetBeaconPacket(int p_134476_, int p_134477_) {
      this.f_134472_ = p_134476_;
      this.f_134473_ = p_134477_;
   }

   public ServerboundSetBeaconPacket(FriendlyByteBuf p_179749_) {
      this.f_134472_ = p_179749_.m_130242_();
      this.f_134473_ = p_179749_.m_130242_();
   }

   public void m_5779_(FriendlyByteBuf p_134486_) {
      p_134486_.m_130130_(this.f_134472_);
      p_134486_.m_130130_(this.f_134473_);
   }

   public void m_5797_(ServerGamePacketListener p_134483_) {
      p_134483_.m_5712_(this);
   }

   public int m_134484_() {
      return this.f_134472_;
   }

   public int m_134487_() {
      return this.f_134473_;
   }
}