package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AttackSweepParticle extends TextureSheetParticle {
   private final SpriteSet f_105544_;

   AttackSweepParticle(ClientLevel p_105546_, double p_105547_, double p_105548_, double p_105549_, double p_105550_, SpriteSet p_105551_) {
      super(p_105546_, p_105547_, p_105548_, p_105549_, 0.0D, 0.0D, 0.0D);
      this.f_105544_ = p_105551_;
      this.f_107225_ = 4;
      float f = this.f_107223_.nextFloat() * 0.6F + 0.4F;
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.f_107663_ = 1.0F - (float)p_105550_ * 0.5F;
      this.m_108339_(p_105551_);
   }

   public int m_6355_(float p_105562_) {
      return 15728880;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_108339_(this.f_105544_);
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107432_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105564_;

      public Provider(SpriteSet p_105566_) {
         this.f_105564_ = p_105566_;
      }

      public Particle m_6966_(SimpleParticleType p_105577_, ClientLevel p_105578_, double p_105579_, double p_105580_, double p_105581_, double p_105582_, double p_105583_, double p_105584_) {
         return new AttackSweepParticle(p_105578_, p_105579_, p_105580_, p_105581_, p_105582_, this.f_105564_);
      }
   }
}