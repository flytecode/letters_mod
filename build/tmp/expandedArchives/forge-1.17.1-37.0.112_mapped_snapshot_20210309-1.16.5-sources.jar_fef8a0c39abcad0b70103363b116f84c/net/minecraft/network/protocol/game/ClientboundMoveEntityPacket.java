package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ClientboundMoveEntityPacket implements Packet<ClientGamePacketListener> {
   private static final double f_178986_ = 4096.0D;
   protected final int f_132499_;
   protected final short f_132500_;
   protected final short f_132501_;
   protected final short f_132502_;
   protected final byte f_132503_;
   protected final byte f_132504_;
   protected final boolean f_132505_;
   protected final boolean f_132506_;
   protected final boolean f_132507_;

   public static long m_132511_(double p_132512_) {
      return Mth.m_14134_(p_132512_ * 4096.0D);
   }

   public static double m_132513_(long p_132514_) {
      return (double)p_132514_ / 4096.0D;
   }

   public Vec3 m_132521_(Vec3 p_132522_) {
      double d0 = this.f_132500_ == 0 ? p_132522_.f_82479_ : m_132513_(m_132511_(p_132522_.f_82479_) + (long)this.f_132500_);
      double d1 = this.f_132501_ == 0 ? p_132522_.f_82480_ : m_132513_(m_132511_(p_132522_.f_82480_) + (long)this.f_132501_);
      double d2 = this.f_132502_ == 0 ? p_132522_.f_82481_ : m_132513_(m_132511_(p_132522_.f_82481_) + (long)this.f_132502_);
      return new Vec3(d0, d1, d2);
   }

   public static Vec3 m_132515_(long p_132516_, long p_132517_, long p_132518_) {
      return (new Vec3((double)p_132516_, (double)p_132517_, (double)p_132518_)).m_82490_((double)2.4414062E-4F);
   }

   protected ClientboundMoveEntityPacket(int p_178988_, short p_178989_, short p_178990_, short p_178991_, byte p_178992_, byte p_178993_, boolean p_178994_, boolean p_178995_, boolean p_178996_) {
      this.f_132499_ = p_178988_;
      this.f_132500_ = p_178989_;
      this.f_132501_ = p_178990_;
      this.f_132502_ = p_178991_;
      this.f_132503_ = p_178992_;
      this.f_132504_ = p_178993_;
      this.f_132505_ = p_178994_;
      this.f_132506_ = p_178995_;
      this.f_132507_ = p_178996_;
   }

   public void m_5797_(ClientGamePacketListener p_132528_) {
      p_132528_.m_7865_(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   @Nullable
   public Entity m_132519_(Level p_132520_) {
      return p_132520_.m_6815_(this.f_132499_);
   }

   public short m_178997_() {
      return this.f_132500_;
   }

   public short m_178998_() {
      return this.f_132501_;
   }

   public short m_178999_() {
      return this.f_132502_;
   }

   public byte m_132531_() {
      return this.f_132503_;
   }

   public byte m_132532_() {
      return this.f_132504_;
   }

   public boolean m_132533_() {
      return this.f_132506_;
   }

   public boolean m_132534_() {
      return this.f_132507_;
   }

   public boolean m_132535_() {
      return this.f_132505_;
   }

   public static class Pos extends ClientboundMoveEntityPacket {
      public Pos(int p_132539_, short p_132540_, short p_132541_, short p_132542_, boolean p_132543_) {
         super(p_132539_, p_132540_, p_132541_, p_132542_, (byte)0, (byte)0, p_132543_, false, true);
      }

      public static ClientboundMoveEntityPacket.Pos m_179000_(FriendlyByteBuf p_179001_) {
         int i = p_179001_.m_130242_();
         short short1 = p_179001_.readShort();
         short short2 = p_179001_.readShort();
         short short3 = p_179001_.readShort();
         boolean flag = p_179001_.readBoolean();
         return new ClientboundMoveEntityPacket.Pos(i, short1, short2, short3, flag);
      }

      public void m_5779_(FriendlyByteBuf p_132549_) {
         p_132549_.m_130130_(this.f_132499_);
         p_132549_.writeShort(this.f_132500_);
         p_132549_.writeShort(this.f_132501_);
         p_132549_.writeShort(this.f_132502_);
         p_132549_.writeBoolean(this.f_132505_);
      }
   }

   public static class PosRot extends ClientboundMoveEntityPacket {
      public PosRot(int p_132552_, short p_132553_, short p_132554_, short p_132555_, byte p_132556_, byte p_132557_, boolean p_132558_) {
         super(p_132552_, p_132553_, p_132554_, p_132555_, p_132556_, p_132557_, p_132558_, true, true);
      }

      public static ClientboundMoveEntityPacket.PosRot m_179002_(FriendlyByteBuf p_179003_) {
         int i = p_179003_.m_130242_();
         short short1 = p_179003_.readShort();
         short short2 = p_179003_.readShort();
         short short3 = p_179003_.readShort();
         byte b0 = p_179003_.readByte();
         byte b1 = p_179003_.readByte();
         boolean flag = p_179003_.readBoolean();
         return new ClientboundMoveEntityPacket.PosRot(i, short1, short2, short3, b0, b1, flag);
      }

      public void m_5779_(FriendlyByteBuf p_132564_) {
         p_132564_.m_130130_(this.f_132499_);
         p_132564_.writeShort(this.f_132500_);
         p_132564_.writeShort(this.f_132501_);
         p_132564_.writeShort(this.f_132502_);
         p_132564_.writeByte(this.f_132503_);
         p_132564_.writeByte(this.f_132504_);
         p_132564_.writeBoolean(this.f_132505_);
      }
   }

   public static class Rot extends ClientboundMoveEntityPacket {
      public Rot(int p_132567_, byte p_132568_, byte p_132569_, boolean p_132570_) {
         super(p_132567_, (short)0, (short)0, (short)0, p_132568_, p_132569_, p_132570_, true, false);
      }

      public static ClientboundMoveEntityPacket.Rot m_179004_(FriendlyByteBuf p_179005_) {
         int i = p_179005_.m_130242_();
         byte b0 = p_179005_.readByte();
         byte b1 = p_179005_.readByte();
         boolean flag = p_179005_.readBoolean();
         return new ClientboundMoveEntityPacket.Rot(i, b0, b1, flag);
      }

      public void m_5779_(FriendlyByteBuf p_132576_) {
         p_132576_.m_130130_(this.f_132499_);
         p_132576_.writeByte(this.f_132503_);
         p_132576_.writeByte(this.f_132504_);
         p_132576_.writeBoolean(this.f_132505_);
      }
   }
}