package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.Validate;

public class ClientboundSoundEntityPacket implements Packet<ClientGamePacketListener> {
   private final SoundEvent f_133408_;
   private final SoundSource f_133409_;
   private final int f_133410_;
   private final float f_133411_;
   private final float f_133412_;

   public ClientboundSoundEntityPacket(SoundEvent p_133415_, SoundSource p_133416_, Entity p_133417_, float p_133418_, float p_133419_) {
      Validate.notNull(p_133415_, "sound");
      this.f_133408_ = p_133415_;
      this.f_133409_ = p_133416_;
      this.f_133410_ = p_133417_.m_142049_();
      this.f_133411_ = p_133418_;
      this.f_133412_ = p_133419_;
   }

   public ClientboundSoundEntityPacket(FriendlyByteBuf p_179419_) {
      this.f_133408_ = Registry.f_122821_.m_7942_(p_179419_.m_130242_());
      this.f_133409_ = p_179419_.m_130066_(SoundSource.class);
      this.f_133410_ = p_179419_.m_130242_();
      this.f_133411_ = p_179419_.readFloat();
      this.f_133412_ = p_179419_.readFloat();
   }

   public void m_5779_(FriendlyByteBuf p_133428_) {
      p_133428_.m_130130_(Registry.f_122821_.m_7447_(this.f_133408_));
      p_133428_.m_130068_(this.f_133409_);
      p_133428_.m_130130_(this.f_133410_);
      p_133428_.writeFloat(this.f_133411_);
      p_133428_.writeFloat(this.f_133412_);
   }

   public SoundEvent m_133426_() {
      return this.f_133408_;
   }

   public SoundSource m_133429_() {
      return this.f_133409_;
   }

   public int m_133430_() {
      return this.f_133410_;
   }

   public float m_133431_() {
      return this.f_133411_;
   }

   public float m_133432_() {
      return this.f_133412_;
   }

   public void m_5797_(ClientGamePacketListener p_133425_) {
      p_133425_.m_5863_(this);
   }
}