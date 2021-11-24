package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class ClientboundTeleportEntityPacket implements Packet<ClientGamePacketListener> {
   private final int f_133529_;
   private final double f_133530_;
   private final double f_133531_;
   private final double f_133532_;
   private final byte f_133533_;
   private final byte f_133534_;
   private final boolean f_133535_;

   public ClientboundTeleportEntityPacket(Entity p_133538_) {
      this.f_133529_ = p_133538_.m_142049_();
      this.f_133530_ = p_133538_.m_20185_();
      this.f_133531_ = p_133538_.m_20186_();
      this.f_133532_ = p_133538_.m_20189_();
      this.f_133533_ = (byte)((int)(p_133538_.m_146908_() * 256.0F / 360.0F));
      this.f_133534_ = (byte)((int)(p_133538_.m_146909_() * 256.0F / 360.0F));
      this.f_133535_ = p_133538_.m_20096_();
   }

   public ClientboundTeleportEntityPacket(FriendlyByteBuf p_179437_) {
      this.f_133529_ = p_179437_.m_130242_();
      this.f_133530_ = p_179437_.readDouble();
      this.f_133531_ = p_179437_.readDouble();
      this.f_133532_ = p_179437_.readDouble();
      this.f_133533_ = p_179437_.readByte();
      this.f_133534_ = p_179437_.readByte();
      this.f_133535_ = p_179437_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_133547_) {
      p_133547_.m_130130_(this.f_133529_);
      p_133547_.writeDouble(this.f_133530_);
      p_133547_.writeDouble(this.f_133531_);
      p_133547_.writeDouble(this.f_133532_);
      p_133547_.writeByte(this.f_133533_);
      p_133547_.writeByte(this.f_133534_);
      p_133547_.writeBoolean(this.f_133535_);
   }

   public void m_5797_(ClientGamePacketListener p_133544_) {
      p_133544_.m_6435_(this);
   }

   public int m_133545_() {
      return this.f_133529_;
   }

   public double m_133548_() {
      return this.f_133530_;
   }

   public double m_133549_() {
      return this.f_133531_;
   }

   public double m_133550_() {
      return this.f_133532_;
   }

   public byte m_133551_() {
      return this.f_133533_;
   }

   public byte m_133552_() {
      return this.f_133534_;
   }

   public boolean m_133553_() {
      return this.f_133535_;
   }
}