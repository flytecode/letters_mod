package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ClientboundSetEntityMotionPacket implements Packet<ClientGamePacketListener> {
   private final int f_133176_;
   private final int f_133177_;
   private final int f_133178_;
   private final int f_133179_;

   public ClientboundSetEntityMotionPacket(Entity p_133185_) {
      this(p_133185_.m_142049_(), p_133185_.m_20184_());
   }

   public ClientboundSetEntityMotionPacket(int p_133182_, Vec3 p_133183_) {
      this.f_133176_ = p_133182_;
      double d0 = 3.9D;
      double d1 = Mth.m_14008_(p_133183_.f_82479_, -3.9D, 3.9D);
      double d2 = Mth.m_14008_(p_133183_.f_82480_, -3.9D, 3.9D);
      double d3 = Mth.m_14008_(p_133183_.f_82481_, -3.9D, 3.9D);
      this.f_133177_ = (int)(d1 * 8000.0D);
      this.f_133178_ = (int)(d2 * 8000.0D);
      this.f_133179_ = (int)(d3 * 8000.0D);
   }

   public ClientboundSetEntityMotionPacket(FriendlyByteBuf p_179294_) {
      this.f_133176_ = p_179294_.m_130242_();
      this.f_133177_ = p_179294_.readShort();
      this.f_133178_ = p_179294_.readShort();
      this.f_133179_ = p_179294_.readShort();
   }

   public void m_5779_(FriendlyByteBuf p_133194_) {
      p_133194_.m_130130_(this.f_133176_);
      p_133194_.writeShort(this.f_133177_);
      p_133194_.writeShort(this.f_133178_);
      p_133194_.writeShort(this.f_133179_);
   }

   public void m_5797_(ClientGamePacketListener p_133191_) {
      p_133191_.m_8048_(this);
   }

   public int m_133192_() {
      return this.f_133176_;
   }

   public int m_133195_() {
      return this.f_133177_;
   }

   public int m_133196_() {
      return this.f_133178_;
   }

   public int m_133197_() {
      return this.f_133179_;
   }
}