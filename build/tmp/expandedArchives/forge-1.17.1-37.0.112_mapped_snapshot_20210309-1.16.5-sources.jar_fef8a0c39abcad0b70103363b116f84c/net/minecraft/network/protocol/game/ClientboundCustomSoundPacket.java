package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class ClientboundCustomSoundPacket implements Packet<ClientGamePacketListener> {
   public static final float f_178837_ = 8.0F;
   private final ResourceLocation f_132046_;
   private final SoundSource f_132047_;
   private final int f_132048_;
   private final int f_132049_;
   private final int f_132050_;
   private final float f_132051_;
   private final float f_132052_;

   public ClientboundCustomSoundPacket(ResourceLocation p_132055_, SoundSource p_132056_, Vec3 p_132057_, float p_132058_, float p_132059_) {
      this.f_132046_ = p_132055_;
      this.f_132047_ = p_132056_;
      this.f_132048_ = (int)(p_132057_.f_82479_ * 8.0D);
      this.f_132049_ = (int)(p_132057_.f_82480_ * 8.0D);
      this.f_132050_ = (int)(p_132057_.f_82481_ * 8.0D);
      this.f_132051_ = p_132058_;
      this.f_132052_ = p_132059_;
   }

   public ClientboundCustomSoundPacket(FriendlyByteBuf p_178839_) {
      this.f_132046_ = p_178839_.m_130281_();
      this.f_132047_ = p_178839_.m_130066_(SoundSource.class);
      this.f_132048_ = p_178839_.readInt();
      this.f_132049_ = p_178839_.readInt();
      this.f_132050_ = p_178839_.readInt();
      this.f_132051_ = p_178839_.readFloat();
      this.f_132052_ = p_178839_.readFloat();
   }

   public void m_5779_(FriendlyByteBuf p_132068_) {
      p_132068_.m_130085_(this.f_132046_);
      p_132068_.m_130068_(this.f_132047_);
      p_132068_.writeInt(this.f_132048_);
      p_132068_.writeInt(this.f_132049_);
      p_132068_.writeInt(this.f_132050_);
      p_132068_.writeFloat(this.f_132051_);
      p_132068_.writeFloat(this.f_132052_);
   }

   public ResourceLocation m_132066_() {
      return this.f_132046_;
   }

   public SoundSource m_132069_() {
      return this.f_132047_;
   }

   public double m_132070_() {
      return (double)((float)this.f_132048_ / 8.0F);
   }

   public double m_132071_() {
      return (double)((float)this.f_132049_ / 8.0F);
   }

   public double m_132072_() {
      return (double)((float)this.f_132050_ / 8.0F);
   }

   public float m_132073_() {
      return this.f_132051_;
   }

   public float m_132074_() {
      return this.f_132052_;
   }

   public void m_5797_(ClientGamePacketListener p_132065_) {
      p_132065_.m_6490_(this);
   }
}