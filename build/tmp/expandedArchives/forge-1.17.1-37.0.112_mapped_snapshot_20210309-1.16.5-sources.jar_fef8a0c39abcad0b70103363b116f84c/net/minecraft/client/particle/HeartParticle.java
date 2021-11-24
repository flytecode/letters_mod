package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeartParticle extends TextureSheetParticle {
   HeartParticle(ClientLevel p_106847_, double p_106848_, double p_106849_, double p_106850_) {
      super(p_106847_, p_106848_, p_106849_, p_106850_, 0.0D, 0.0D, 0.0D);
      this.f_172259_ = true;
      this.f_172258_ = 0.86F;
      this.f_107215_ *= (double)0.01F;
      this.f_107216_ *= (double)0.01F;
      this.f_107217_ *= (double)0.01F;
      this.f_107216_ += 0.1D;
      this.f_107663_ *= 1.5F;
      this.f_107225_ = 16;
      this.f_107219_ = false;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_106860_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_106860_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public static class AngryVillagerProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106861_;

      public AngryVillagerProvider(SpriteSet p_106863_) {
         this.f_106861_ = p_106863_;
      }

      public Particle m_6966_(SimpleParticleType p_106874_, ClientLevel p_106875_, double p_106876_, double p_106877_, double p_106878_, double p_106879_, double p_106880_, double p_106881_) {
         HeartParticle heartparticle = new HeartParticle(p_106875_, p_106876_, p_106877_ + 0.5D, p_106878_);
         heartparticle.m_108335_(this.f_106861_);
         heartparticle.m_107253_(1.0F, 1.0F, 1.0F);
         return heartparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106882_;

      public Provider(SpriteSet p_106884_) {
         this.f_106882_ = p_106884_;
      }

      public Particle m_6966_(SimpleParticleType p_106895_, ClientLevel p_106896_, double p_106897_, double p_106898_, double p_106899_, double p_106900_, double p_106901_, double p_106902_) {
         HeartParticle heartparticle = new HeartParticle(p_106896_, p_106897_, p_106898_, p_106899_);
         heartparticle.m_108335_(this.f_106882_);
         return heartparticle;
      }
   }
}