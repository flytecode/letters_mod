package net.minecraft.client.resources.sounds;

import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSoundInstance implements SoundInstance {
   protected Sound f_119570_;
   protected final SoundSource f_119571_;
   protected final ResourceLocation f_119572_;
   protected float f_119573_ = 1.0F;
   protected float f_119574_ = 1.0F;
   protected double f_119575_;
   protected double f_119576_;
   protected double f_119577_;
   protected boolean f_119578_;
   protected int f_119579_;
   protected SoundInstance.Attenuation f_119580_ = SoundInstance.Attenuation.LINEAR;
   protected boolean f_119582_;

   protected AbstractSoundInstance(SoundEvent p_119584_, SoundSource p_119585_) {
      this(p_119584_.m_11660_(), p_119585_);
   }

   protected AbstractSoundInstance(ResourceLocation p_119587_, SoundSource p_119588_) {
      this.f_119572_ = p_119587_;
      this.f_119571_ = p_119588_;
   }

   public ResourceLocation m_7904_() {
      return this.f_119572_;
   }

   public WeighedSoundEvents m_6775_(SoundManager p_119591_) {
      WeighedSoundEvents weighedsoundevents = p_119591_.m_120384_(this.f_119572_);
      if (weighedsoundevents == null) {
         this.f_119570_ = SoundManager.f_120344_;
      } else {
         this.f_119570_ = weighedsoundevents.m_6776_();
      }

      return weighedsoundevents;
   }

   public Sound m_5891_() {
      return this.f_119570_;
   }

   public SoundSource m_8070_() {
      return this.f_119571_;
   }

   public boolean m_7775_() {
      return this.f_119578_;
   }

   public int m_7766_() {
      return this.f_119579_;
   }

   public float m_7769_() {
      return this.f_119573_ * this.f_119570_.m_119791_();
   }

   public float m_7783_() {
      return this.f_119574_ * this.f_119570_.m_119792_();
   }

   public double m_7772_() {
      return this.f_119575_;
   }

   public double m_7780_() {
      return this.f_119576_;
   }

   public double m_7778_() {
      return this.f_119577_;
   }

   public SoundInstance.Attenuation m_7438_() {
      return this.f_119580_;
   }

   public boolean m_7796_() {
      return this.f_119582_;
   }

   public String toString() {
      return "SoundInstance[" + this.f_119572_ + "]";
   }
}