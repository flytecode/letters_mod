package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CritParticle extends TextureSheetParticle {
   CritParticle(ClientLevel p_105919_, double p_105920_, double p_105921_, double p_105922_, double p_105923_, double p_105924_, double p_105925_) {
      super(p_105919_, p_105920_, p_105921_, p_105922_, 0.0D, 0.0D, 0.0D);
      this.f_172258_ = 0.7F;
      this.f_107226_ = 0.5F;
      this.f_107215_ *= (double)0.1F;
      this.f_107216_ *= (double)0.1F;
      this.f_107217_ *= (double)0.1F;
      this.f_107215_ += p_105923_ * 0.4D;
      this.f_107216_ += p_105924_ * 0.4D;
      this.f_107217_ += p_105925_ * 0.4D;
      float f = (float)(Math.random() * (double)0.3F + (double)0.6F);
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.f_107663_ *= 0.75F;
      this.f_107225_ = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
      this.f_107219_ = false;
      this.m_5989_();
   }

   public float m_5902_(float p_105938_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_105938_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   public void m_5989_() {
      super.m_5989_();
      this.f_107228_ = (float)((double)this.f_107228_ * 0.96D);
      this.f_107229_ = (float)((double)this.f_107229_ * 0.9D);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class DamageIndicatorProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105939_;

      public DamageIndicatorProvider(SpriteSet p_105941_) {
         this.f_105939_ = p_105941_;
      }

      public Particle m_6966_(SimpleParticleType p_105952_, ClientLevel p_105953_, double p_105954_, double p_105955_, double p_105956_, double p_105957_, double p_105958_, double p_105959_) {
         CritParticle critparticle = new CritParticle(p_105953_, p_105954_, p_105955_, p_105956_, p_105957_, p_105958_ + 1.0D, p_105959_);
         critparticle.m_107257_(20);
         critparticle.m_108335_(this.f_105939_);
         return critparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class MagicProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105960_;

      public MagicProvider(SpriteSet p_105962_) {
         this.f_105960_ = p_105962_;
      }

      public Particle m_6966_(SimpleParticleType p_105973_, ClientLevel p_105974_, double p_105975_, double p_105976_, double p_105977_, double p_105978_, double p_105979_, double p_105980_) {
         CritParticle critparticle = new CritParticle(p_105974_, p_105975_, p_105976_, p_105977_, p_105978_, p_105979_, p_105980_);
         critparticle.f_107227_ *= 0.3F;
         critparticle.f_107228_ *= 0.8F;
         critparticle.m_108335_(this.f_105960_);
         return critparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105981_;

      public Provider(SpriteSet p_105983_) {
         this.f_105981_ = p_105983_;
      }

      public Particle m_6966_(SimpleParticleType p_105994_, ClientLevel p_105995_, double p_105996_, double p_105997_, double p_105998_, double p_105999_, double p_106000_, double p_106001_) {
         CritParticle critparticle = new CritParticle(p_105995_, p_105996_, p_105997_, p_105998_, p_105999_, p_106000_, p_106001_);
         critparticle.m_108335_(this.f_105981_);
         return critparticle;
      }
   }
}