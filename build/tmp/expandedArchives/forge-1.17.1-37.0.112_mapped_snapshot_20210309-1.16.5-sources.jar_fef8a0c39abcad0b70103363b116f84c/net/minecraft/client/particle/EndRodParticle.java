package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndRodParticle extends SimpleAnimatedParticle {
   EndRodParticle(ClientLevel p_106531_, double p_106532_, double p_106533_, double p_106534_, double p_106535_, double p_106536_, double p_106537_, SpriteSet p_106538_) {
      super(p_106531_, p_106532_, p_106533_, p_106534_, p_106538_, 0.0125F);
      this.f_107215_ = p_106535_;
      this.f_107216_ = p_106536_;
      this.f_107217_ = p_106537_;
      this.f_107663_ *= 0.75F;
      this.f_107225_ = 60 + this.f_107223_.nextInt(12);
      this.m_107659_(15916745);
      this.m_108339_(p_106538_);
   }

   public void m_6257_(double p_106550_, double p_106551_, double p_106552_) {
      this.m_107259_(this.m_107277_().m_82386_(p_106550_, p_106551_, p_106552_));
      this.m_107275_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106553_;

      public Provider(SpriteSet p_106555_) {
         this.f_106553_ = p_106555_;
      }

      public Particle m_6966_(SimpleParticleType p_106566_, ClientLevel p_106567_, double p_106568_, double p_106569_, double p_106570_, double p_106571_, double p_106572_, double p_106573_) {
         return new EndRodParticle(p_106567_, p_106568_, p_106569_, p_106570_, p_106571_, p_106572_, p_106573_, this.f_106553_);
      }
   }
}