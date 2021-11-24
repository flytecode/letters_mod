package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WakeParticle extends TextureSheetParticle {
   private final SpriteSet f_108405_;

   WakeParticle(ClientLevel p_108407_, double p_108408_, double p_108409_, double p_108410_, double p_108411_, double p_108412_, double p_108413_, SpriteSet p_108414_) {
      super(p_108407_, p_108408_, p_108409_, p_108410_, 0.0D, 0.0D, 0.0D);
      this.f_108405_ = p_108414_;
      this.f_107215_ *= (double)0.3F;
      this.f_107216_ = Math.random() * (double)0.2F + (double)0.1F;
      this.f_107217_ *= (double)0.3F;
      this.m_107250_(0.01F, 0.01F);
      this.f_107225_ = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.m_108339_(p_108414_);
      this.f_107226_ = 0.0F;
      this.f_107215_ = p_108411_;
      this.f_107216_ = p_108412_;
      this.f_107217_ = p_108413_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      int i = 60 - this.f_107225_;
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
      } else {
         this.f_107216_ -= (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107215_ *= (double)0.98F;
         this.f_107216_ *= (double)0.98F;
         this.f_107217_ *= (double)0.98F;
         float f = (float)i * 0.001F;
         this.m_107250_(f, f);
         this.m_108337_(this.f_108405_.m_5819_(i % 4, 4));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108427_;

      public Provider(SpriteSet p_108429_) {
         this.f_108427_ = p_108429_;
      }

      public Particle m_6966_(SimpleParticleType p_108440_, ClientLevel p_108441_, double p_108442_, double p_108443_, double p_108444_, double p_108445_, double p_108446_, double p_108447_) {
         return new WakeParticle(p_108441_, p_108442_, p_108443_, p_108444_, p_108445_, p_108446_, p_108447_, this.f_108427_);
      }
   }
}