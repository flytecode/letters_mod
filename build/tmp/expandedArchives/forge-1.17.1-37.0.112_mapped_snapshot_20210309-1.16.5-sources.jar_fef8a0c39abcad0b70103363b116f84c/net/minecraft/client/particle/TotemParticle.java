package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TotemParticle extends SimpleAnimatedParticle {
   TotemParticle(ClientLevel p_108346_, double p_108347_, double p_108348_, double p_108349_, double p_108350_, double p_108351_, double p_108352_, SpriteSet p_108353_) {
      super(p_108346_, p_108347_, p_108348_, p_108349_, p_108353_, 1.25F);
      this.f_172258_ = 0.6F;
      this.f_107215_ = p_108350_;
      this.f_107216_ = p_108351_;
      this.f_107217_ = p_108352_;
      this.f_107663_ *= 0.75F;
      this.f_107225_ = 60 + this.f_107223_.nextInt(12);
      this.m_108339_(p_108353_);
      if (this.f_107223_.nextInt(4) == 0) {
         this.m_107253_(0.6F + this.f_107223_.nextFloat() * 0.2F, 0.6F + this.f_107223_.nextFloat() * 0.3F, this.f_107223_.nextFloat() * 0.2F);
      } else {
         this.m_107253_(0.1F + this.f_107223_.nextFloat() * 0.2F, 0.4F + this.f_107223_.nextFloat() * 0.3F, this.f_107223_.nextFloat() * 0.2F);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108364_;

      public Provider(SpriteSet p_108366_) {
         this.f_108364_ = p_108366_;
      }

      public Particle m_6966_(SimpleParticleType p_108377_, ClientLevel p_108378_, double p_108379_, double p_108380_, double p_108381_, double p_108382_, double p_108383_, double p_108384_) {
         return new TotemParticle(p_108378_, p_108379_, p_108380_, p_108381_, p_108382_, p_108383_, p_108384_, this.f_108364_);
      }
   }
}