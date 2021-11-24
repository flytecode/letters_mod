package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReversePortalParticle extends PortalParticle {
   ReversePortalParticle(ClientLevel p_107590_, double p_107591_, double p_107592_, double p_107593_, double p_107594_, double p_107595_, double p_107596_) {
      super(p_107590_, p_107591_, p_107592_, p_107593_, p_107594_, p_107595_, p_107596_);
      this.f_107663_ = (float)((double)this.f_107663_ * 1.5D);
      this.f_107225_ = (int)(Math.random() * 2.0D) + 60;
   }

   public float m_5902_(float p_107608_) {
      float f = 1.0F - ((float)this.f_107224_ + p_107608_) / ((float)this.f_107225_ * 1.5F);
      return this.f_107663_ * f;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         float f = (float)this.f_107224_ / (float)this.f_107225_;
         this.f_107212_ += this.f_107215_ * (double)f;
         this.f_107213_ += this.f_107216_ * (double)f;
         this.f_107214_ += this.f_107217_ * (double)f;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ReversePortalProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_107609_;

      public ReversePortalProvider(SpriteSet p_107611_) {
         this.f_107609_ = p_107611_;
      }

      public Particle m_6966_(SimpleParticleType p_107622_, ClientLevel p_107623_, double p_107624_, double p_107625_, double p_107626_, double p_107627_, double p_107628_, double p_107629_) {
         ReversePortalParticle reverseportalparticle = new ReversePortalParticle(p_107623_, p_107624_, p_107625_, p_107626_, p_107627_, p_107628_, p_107629_);
         reverseportalparticle.m_108335_(this.f_107609_);
         return reverseportalparticle;
      }
   }
}