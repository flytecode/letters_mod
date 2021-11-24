package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class ClientboundUpdateMobEffectPacket implements Packet<ClientGamePacketListener> {
   private static final int f_179462_ = 1;
   private static final int f_179463_ = 2;
   private static final int f_179464_ = 4;
   private final int f_133604_;
   private final byte f_133605_;
   private final byte f_133606_;
   private final int f_133607_;
   private final byte f_133608_;

   public ClientboundUpdateMobEffectPacket(int p_133611_, MobEffectInstance p_133612_) {
      this.f_133604_ = p_133611_;
      this.f_133605_ = (byte)(MobEffect.m_19459_(p_133612_.m_19544_()) & 255);
      this.f_133606_ = (byte)(p_133612_.m_19564_() & 255);
      if (p_133612_.m_19557_() > 32767) {
         this.f_133607_ = 32767;
      } else {
         this.f_133607_ = p_133612_.m_19557_();
      }

      byte b0 = 0;
      if (p_133612_.m_19571_()) {
         b0 = (byte)(b0 | 1);
      }

      if (p_133612_.m_19572_()) {
         b0 = (byte)(b0 | 2);
      }

      if (p_133612_.m_19575_()) {
         b0 = (byte)(b0 | 4);
      }

      this.f_133608_ = b0;
   }

   public ClientboundUpdateMobEffectPacket(FriendlyByteBuf p_179466_) {
      this.f_133604_ = p_179466_.m_130242_();
      this.f_133605_ = p_179466_.readByte();
      this.f_133606_ = p_179466_.readByte();
      this.f_133607_ = p_179466_.m_130242_();
      this.f_133608_ = p_179466_.readByte();
   }

   public void m_5779_(FriendlyByteBuf p_133621_) {
      p_133621_.m_130130_(this.f_133604_);
      p_133621_.writeByte(this.f_133605_);
      p_133621_.writeByte(this.f_133606_);
      p_133621_.m_130130_(this.f_133607_);
      p_133621_.writeByte(this.f_133608_);
   }

   public boolean m_133619_() {
      return this.f_133607_ == 32767;
   }

   public void m_5797_(ClientGamePacketListener p_133618_) {
      p_133618_.m_7915_(this);
   }

   public int m_133622_() {
      return this.f_133604_;
   }

   public byte m_133623_() {
      return this.f_133605_;
   }

   public byte m_133624_() {
      return this.f_133606_;
   }

   public int m_133625_() {
      return this.f_133607_;
   }

   public boolean m_133626_() {
      return (this.f_133608_ & 2) == 2;
   }

   public boolean m_133627_() {
      return (this.f_133608_ & 1) == 1;
   }

   public boolean m_133628_() {
      return (this.f_133608_ & 4) == 4;
   }
}