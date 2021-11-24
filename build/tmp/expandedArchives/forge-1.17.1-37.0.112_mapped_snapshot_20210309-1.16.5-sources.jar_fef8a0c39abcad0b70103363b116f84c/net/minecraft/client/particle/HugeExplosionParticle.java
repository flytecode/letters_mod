package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HugeExplosionParticle extends TextureSheetParticle {
   private final SpriteSet f_106903_;

   HugeExplosionParticle(ClientLevel p_106905_, double p_106906_, double p_106907_, double p_106908_, double p_106909_, SpriteSet p_106910_) {
      super(p_106905_, p_106906_, p_106907_, p_106908_, 0.0D, 0.0D, 0.0D);
      this.f_107225_ = 6 + this.f_107223_.nextInt(4);
      float f = this.f_107223_.nextFloat() * 0.6F + 0.4F;
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.f_107663_ = 2.0F * (1.0F - (float)p_106909_ * 0.5F);
      this.f_106903_ = p_106910_;
      this.m_108339_(p_106910_);
   }

   public int m_6355_(float p_106921_) {
      return 15728880;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_108339_(this.f_106903_);
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107432_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106923_;

      public Provider(SpriteSet p_106925_) {
         this.f_106923_ = p_106925_;
      }

      public Particle m_6966_(SimpleParticleType p_106936_, ClientLevel p_106937_, double p_106938_, double p_106939_, double p_106940_, double p_106941_, double p_106942_, double p_106943_) {
         return new HugeExplosionParticle(p_106937_, p_106938_, p_106939_, p_106940_, p_106941_, this.f_106923_);
      }
   }
}