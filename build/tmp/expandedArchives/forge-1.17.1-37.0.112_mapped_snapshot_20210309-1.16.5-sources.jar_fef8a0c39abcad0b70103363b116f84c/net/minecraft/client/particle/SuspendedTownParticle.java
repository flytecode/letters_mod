package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SuspendedTownParticle extends TextureSheetParticle {
   SuspendedTownParticle(ClientLevel p_108104_, double p_108105_, double p_108106_, double p_108107_, double p_108108_, double p_108109_, double p_108110_) {
      super(p_108104_, p_108105_, p_108106_, p_108107_, p_108108_, p_108109_, p_108110_);
      float f = this.f_107223_.nextFloat() * 0.1F + 0.2F;
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.m_107250_(0.02F, 0.02F);
      this.f_107663_ *= this.f_107223_.nextFloat() * 0.6F + 0.5F;
      this.f_107215_ *= (double)0.02F;
      this.f_107216_ *= (double)0.02F;
      this.f_107217_ *= (double)0.02F;
      this.f_107225_ = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_6257_(double p_108122_, double p_108123_, double p_108124_) {
      this.m_107259_(this.m_107277_().m_82386_(p_108122_, p_108123_, p_108124_));
      this.m_107275_();
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
      } else {
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107215_ *= 0.99D;
         this.f_107216_ *= 0.99D;
         this.f_107217_ *= 0.99D;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ComposterFillProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108126_;

      public ComposterFillProvider(SpriteSet p_108128_) {
         this.f_108126_ = p_108128_;
      }

      public Particle m_6966_(SimpleParticleType p_108139_, ClientLevel p_108140_, double p_108141_, double p_108142_, double p_108143_, double p_108144_, double p_108145_, double p_108146_) {
         SuspendedTownParticle suspendedtownparticle = new SuspendedTownParticle(p_108140_, p_108141_, p_108142_, p_108143_, p_108144_, p_108145_, p_108146_);
         suspendedtownparticle.m_108335_(this.f_108126_);
         suspendedtownparticle.m_107253_(1.0F, 1.0F, 1.0F);
         suspendedtownparticle.m_107257_(3 + p_108140_.m_5822_().nextInt(5));
         return suspendedtownparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class DolphinSpeedProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108147_;

      public DolphinSpeedProvider(SpriteSet p_108149_) {
         this.f_108147_ = p_108149_;
      }

      public Particle m_6966_(SimpleParticleType p_108160_, ClientLevel p_108161_, double p_108162_, double p_108163_, double p_108164_, double p_108165_, double p_108166_, double p_108167_) {
         SuspendedTownParticle suspendedtownparticle = new SuspendedTownParticle(p_108161_, p_108162_, p_108163_, p_108164_, p_108165_, p_108166_, p_108167_);
         suspendedtownparticle.m_107253_(0.3F, 0.5F, 1.0F);
         suspendedtownparticle.m_108335_(this.f_108147_);
         suspendedtownparticle.m_107271_(1.0F - p_108161_.f_46441_.nextFloat() * 0.7F);
         suspendedtownparticle.m_107257_(suspendedtownparticle.m_107273_() / 2);
         return suspendedtownparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class HappyVillagerProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108168_;

      public HappyVillagerProvider(SpriteSet p_108170_) {
         this.f_108168_ = p_108170_;
      }

      public Particle m_6966_(SimpleParticleType p_108181_, ClientLevel p_108182_, double p_108183_, double p_108184_, double p_108185_, double p_108186_, double p_108187_, double p_108188_) {
         SuspendedTownParticle suspendedtownparticle = new SuspendedTownParticle(p_108182_, p_108183_, p_108184_, p_108185_, p_108186_, p_108187_, p_108188_);
         suspendedtownparticle.m_108335_(this.f_108168_);
         suspendedtownparticle.m_107253_(1.0F, 1.0F, 1.0F);
         return suspendedtownparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108189_;

      public Provider(SpriteSet p_108191_) {
         this.f_108189_ = p_108191_;
      }

      public Particle m_6966_(SimpleParticleType p_108202_, ClientLevel p_108203_, double p_108204_, double p_108205_, double p_108206_, double p_108207_, double p_108208_, double p_108209_) {
         SuspendedTownParticle suspendedtownparticle = new SuspendedTownParticle(p_108203_, p_108204_, p_108205_, p_108206_, p_108207_, p_108208_, p_108209_);
         suspendedtownparticle.m_108335_(this.f_108189_);
         return suspendedtownparticle;
      }
   }
}