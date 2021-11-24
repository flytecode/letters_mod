package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class ClientboundAddEntityPacket implements Packet<ClientGamePacketListener> {
   public static final double f_178559_ = 8000.0D;
   private final int f_131456_;
   private final UUID f_131457_;
   private final double f_131458_;
   private final double f_131459_;
   private final double f_131460_;
   private final int f_131461_;
   private final int f_131462_;
   private final int f_131463_;
   private final int f_131464_;
   private final int f_131465_;
   private final EntityType<?> f_131466_;
   private final int f_131467_;
   public static final double f_178560_ = 3.9D;

   public ClientboundAddEntityPacket(int p_131470_, UUID p_131471_, double p_131472_, double p_131473_, double p_131474_, float p_131475_, float p_131476_, EntityType<?> p_131477_, int p_131478_, Vec3 p_131479_) {
      this.f_131456_ = p_131470_;
      this.f_131457_ = p_131471_;
      this.f_131458_ = p_131472_;
      this.f_131459_ = p_131473_;
      this.f_131460_ = p_131474_;
      this.f_131464_ = Mth.m_14143_(p_131475_ * 256.0F / 360.0F);
      this.f_131465_ = Mth.m_14143_(p_131476_ * 256.0F / 360.0F);
      this.f_131466_ = p_131477_;
      this.f_131467_ = p_131478_;
      this.f_131461_ = (int)(Mth.m_14008_(p_131479_.f_82479_, -3.9D, 3.9D) * 8000.0D);
      this.f_131462_ = (int)(Mth.m_14008_(p_131479_.f_82480_, -3.9D, 3.9D) * 8000.0D);
      this.f_131463_ = (int)(Mth.m_14008_(p_131479_.f_82481_, -3.9D, 3.9D) * 8000.0D);
   }

   public ClientboundAddEntityPacket(Entity p_131481_) {
      this(p_131481_, 0);
   }

   public ClientboundAddEntityPacket(Entity p_131483_, int p_131484_) {
      this(p_131483_.m_142049_(), p_131483_.m_142081_(), p_131483_.m_20185_(), p_131483_.m_20186_(), p_131483_.m_20189_(), p_131483_.m_146909_(), p_131483_.m_146908_(), p_131483_.m_6095_(), p_131484_, p_131483_.m_20184_());
   }

   public ClientboundAddEntityPacket(Entity p_131486_, EntityType<?> p_131487_, int p_131488_, BlockPos p_131489_) {
      this(p_131486_.m_142049_(), p_131486_.m_142081_(), (double)p_131489_.m_123341_(), (double)p_131489_.m_123342_(), (double)p_131489_.m_123343_(), p_131486_.m_146909_(), p_131486_.m_146908_(), p_131487_, p_131488_, p_131486_.m_20184_());
   }

   public ClientboundAddEntityPacket(FriendlyByteBuf p_178562_) {
      this.f_131456_ = p_178562_.m_130242_();
      this.f_131457_ = p_178562_.m_130259_();
      this.f_131466_ = Registry.f_122826_.m_7942_(p_178562_.m_130242_());
      this.f_131458_ = p_178562_.readDouble();
      this.f_131459_ = p_178562_.readDouble();
      this.f_131460_ = p_178562_.readDouble();
      this.f_131464_ = p_178562_.readByte();
      this.f_131465_ = p_178562_.readByte();
      this.f_131467_ = p_178562_.readInt();
      this.f_131461_ = p_178562_.readShort();
      this.f_131462_ = p_178562_.readShort();
      this.f_131463_ = p_178562_.readShort();
   }

   public void m_5779_(FriendlyByteBuf p_131498_) {
      p_131498_.m_130130_(this.f_131456_);
      p_131498_.m_130077_(this.f_131457_);
      p_131498_.m_130130_(Registry.f_122826_.m_7447_(this.f_131466_));
      p_131498_.writeDouble(this.f_131458_);
      p_131498_.writeDouble(this.f_131459_);
      p_131498_.writeDouble(this.f_131460_);
      p_131498_.writeByte(this.f_131464_);
      p_131498_.writeByte(this.f_131465_);
      p_131498_.writeInt(this.f_131467_);
      p_131498_.writeShort(this.f_131461_);
      p_131498_.writeShort(this.f_131462_);
      p_131498_.writeShort(this.f_131463_);
   }

   public void m_5797_(ClientGamePacketListener p_131495_) {
      p_131495_.m_6771_(this);
   }

   public int m_131496_() {
      return this.f_131456_;
   }

   public UUID m_131499_() {
      return this.f_131457_;
   }

   public double m_131500_() {
      return this.f_131458_;
   }

   public double m_131501_() {
      return this.f_131459_;
   }

   public double m_131502_() {
      return this.f_131460_;
   }

   public double m_131503_() {
      return (double)this.f_131461_ / 8000.0D;
   }

   public double m_131504_() {
      return (double)this.f_131462_ / 8000.0D;
   }

   public double m_131505_() {
      return (double)this.f_131463_ / 8000.0D;
   }

   public int m_131506_() {
      return this.f_131464_;
   }

   public int m_131507_() {
      return this.f_131465_;
   }

   public EntityType<?> m_131508_() {
      return this.f_131466_;
   }

   public int m_131509_() {
      return this.f_131467_;
   }
}