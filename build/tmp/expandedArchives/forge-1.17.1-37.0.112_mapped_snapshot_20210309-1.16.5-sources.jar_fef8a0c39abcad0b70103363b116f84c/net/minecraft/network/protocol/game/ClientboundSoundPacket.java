package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.apache.commons.lang3.Validate;

public class ClientboundSoundPacket implements Packet<ClientGamePacketListener> {
   public static final float f_179420_ = 8.0F;
   private final SoundEvent f_133433_;
   private final SoundSource f_133434_;
   private final int f_133435_;
   private final int f_133436_;
   private final int f_133437_;
   private final float f_133438_;
   private final float f_133439_;

   public ClientboundSoundPacket(SoundEvent p_133442_, SoundSource p_133443_, double p_133444_, double p_133445_, double p_133446_, float p_133447_, float p_133448_) {
      Validate.notNull(p_133442_, "sound");
      this.f_133433_ = p_133442_;
      this.f_133434_ = p_133443_;
      this.f_133435_ = (int)(p_133444_ * 8.0D);
      this.f_133436_ = (int)(p_133445_ * 8.0D);
      this.f_133437_ = (int)(p_133446_ * 8.0D);
      this.f_133438_ = p_133447_;
      this.f_133439_ = p_133448_;
   }

   public ClientboundSoundPacket(FriendlyByteBuf p_179422_) {
      this.f_133433_ = Registry.f_122821_.m_7942_(p_179422_.m_130242_());
      this.f_133434_ = p_179422_.m_130066_(SoundSource.class);
      this.f_133435_ = p_179422_.readInt();
      this.f_133436_ = p_179422_.readInt();
      this.f_133437_ = p_179422_.readInt();
      this.f_133438_ = p_179422_.readFloat();
      this.f_133439_ = p_179422_.readFloat();
   }

   public void m_5779_(FriendlyByteBuf p_133457_) {
      p_133457_.m_130130_(Registry.f_122821_.m_7447_(this.f_133433_));
      p_133457_.m_130068_(this.f_133434_);
      p_133457_.writeInt(this.f_133435_);
      p_133457_.writeInt(this.f_133436_);
      p_133457_.writeInt(this.f_133437_);
      p_133457_.writeFloat(this.f_133438_);
      p_133457_.writeFloat(this.f_133439_);
   }

   public SoundEvent m_133455_() {
      return this.f_133433_;
   }

   public SoundSource m_133458_() {
      return this.f_133434_;
   }

   public double m_133459_() {
      return (double)((float)this.f_133435_ / 8.0F);
   }

   public double m_133460_() {
      return (double)((float)this.f_133436_ / 8.0F);
   }

   public double m_133461_() {
      return (double)((float)this.f_133437_ / 8.0F);
   }

   public float m_133462_() {
      return this.f_133438_;
   }

   public float m_133463_() {
      return this.f_133439_;
   }

   public void m_5797_(ClientGamePacketListener p_133454_) {
      p_133454_.m_8068_(this);
   }
}