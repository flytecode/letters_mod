package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ClientboundAddMobPacket implements Packet<ClientGamePacketListener> {
   private final int f_131531_;
   private final UUID f_131532_;
   private final int f_131533_;
   private final double f_131534_;
   private final double f_131535_;
   private final double f_131536_;
   private final int f_131537_;
   private final int f_131538_;
   private final int f_131539_;
   private final byte f_131540_;
   private final byte f_131541_;
   private final byte f_131542_;

   public ClientboundAddMobPacket(LivingEntity p_131545_) {
      this.f_131531_ = p_131545_.m_142049_();
      this.f_131532_ = p_131545_.m_142081_();
      this.f_131533_ = Registry.f_122826_.m_7447_(p_131545_.m_6095_());
      this.f_131534_ = p_131545_.m_20185_();
      this.f_131535_ = p_131545_.m_20186_();
      this.f_131536_ = p_131545_.m_20189_();
      this.f_131540_ = (byte)((int)(p_131545_.m_146908_() * 256.0F / 360.0F));
      this.f_131541_ = (byte)((int)(p_131545_.m_146909_() * 256.0F / 360.0F));
      this.f_131542_ = (byte)((int)(p_131545_.f_20885_ * 256.0F / 360.0F));
      double d0 = 3.9D;
      Vec3 vec3 = p_131545_.m_20184_();
      double d1 = Mth.m_14008_(vec3.f_82479_, -3.9D, 3.9D);
      double d2 = Mth.m_14008_(vec3.f_82480_, -3.9D, 3.9D);
      double d3 = Mth.m_14008_(vec3.f_82481_, -3.9D, 3.9D);
      this.f_131537_ = (int)(d1 * 8000.0D);
      this.f_131538_ = (int)(d2 * 8000.0D);
      this.f_131539_ = (int)(d3 * 8000.0D);
   }

   public ClientboundAddMobPacket(FriendlyByteBuf p_178566_) {
      this.f_131531_ = p_178566_.m_130242_();
      this.f_131532_ = p_178566_.m_130259_();
      this.f_131533_ = p_178566_.m_130242_();
      this.f_131534_ = p_178566_.readDouble();
      this.f_131535_ = p_178566_.readDouble();
      this.f_131536_ = p_178566_.readDouble();
      this.f_131540_ = p_178566_.readByte();
      this.f_131541_ = p_178566_.readByte();
      this.f_131542_ = p_178566_.readByte();
      this.f_131537_ = p_178566_.readShort();
      this.f_131538_ = p_178566_.readShort();
      this.f_131539_ = p_178566_.readShort();
   }

   public void m_5779_(FriendlyByteBuf p_131554_) {
      p_131554_.m_130130_(this.f_131531_);
      p_131554_.m_130077_(this.f_131532_);
      p_131554_.m_130130_(this.f_131533_);
      p_131554_.writeDouble(this.f_131534_);
      p_131554_.writeDouble(this.f_131535_);
      p_131554_.writeDouble(this.f_131536_);
      p_131554_.writeByte(this.f_131540_);
      p_131554_.writeByte(this.f_131541_);
      p_131554_.writeByte(this.f_131542_);
      p_131554_.writeShort(this.f_131537_);
      p_131554_.writeShort(this.f_131538_);
      p_131554_.writeShort(this.f_131539_);
   }

   public void m_5797_(ClientGamePacketListener p_131551_) {
      p_131551_.m_6965_(this);
   }

   public int m_131552_() {
      return this.f_131531_;
   }

   public UUID m_131555_() {
      return this.f_131532_;
   }

   public int m_131556_() {
      return this.f_131533_;
   }

   public double m_131557_() {
      return this.f_131534_;
   }

   public double m_131558_() {
      return this.f_131535_;
   }

   public double m_131559_() {
      return this.f_131536_;
   }

   public int m_131560_() {
      return this.f_131537_;
   }

   public int m_131561_() {
      return this.f_131538_;
   }

   public int m_131562_() {
      return this.f_131539_;
   }

   public byte m_131563_() {
      return this.f_131540_;
   }

   public byte m_131564_() {
      return this.f_131541_;
   }

   public byte m_131565_() {
      return this.f_131542_;
   }
}