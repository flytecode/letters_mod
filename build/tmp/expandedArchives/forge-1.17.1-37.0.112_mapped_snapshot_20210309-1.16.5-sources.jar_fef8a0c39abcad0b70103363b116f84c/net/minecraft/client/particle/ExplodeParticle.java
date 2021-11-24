package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExplodeParticle extends TextureSheetParticle {
   private final SpriteSet f_106574_;

   protected ExplodeParticle(ClientLevel p_106576_, double p_106577_, double p_106578_, double p_106579_, double p_106580_, double p_106581_, double p_106582_, SpriteSet p_106583_) {
      super(p_106576_, p_106577_, p_106578_, p_106579_);
      this.f_107226_ = -0.1F;
      this.f_172258_ = 0.9F;
      this.f_106574_ = p_106583_;
      this.f_107215_ = p_106580_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.f_107216_ = p_106581_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.f_107217_ = p_106582_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      float f = this.f_107223_.nextFloat() * 0.3F + 0.7F;
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.f_107663_ = 0.1F * (this.f_107223_.nextFloat() * this.f_107223_.nextFloat() * 6.0F + 1.0F);
      this.f_107225_ = (int)(16.0D / ((double)this.f_107223_.nextFloat() * 0.8D + 0.2D)) + 2;
      this.m_108339_(p_106583_);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.f_106574_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106586_;

      public Provider(SpriteSet p_106588_) {
         this.f_106586_ = p_106588_;
      }

      public Particle m_6966_(SimpleParticleType p_106599_, ClientLevel p_106600_, double p_106601_, double p_106602_, double p_106603_, double p_106604_, double p_106605_, double p_106606_) {
         return new ExplodeParticle(p_106600_, p_106601_, p_106602_, p_106603_, p_106604_, p_106605_, p_106606_, this.f_106586_);
      }
   }
}