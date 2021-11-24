package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowflakeParticle extends TextureSheetParticle {
   private final SpriteSet f_172290_;

   protected SnowflakeParticle(ClientLevel p_172292_, double p_172293_, double p_172294_, double p_172295_, double p_172296_, double p_172297_, double p_172298_, SpriteSet p_172299_) {
      super(p_172292_, p_172293_, p_172294_, p_172295_);
      this.f_107226_ = 0.225F;
      this.f_172258_ = 1.0F;
      this.f_172290_ = p_172299_;
      this.f_107215_ = p_172296_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.f_107216_ = p_172297_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.f_107217_ = p_172298_ + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.f_107663_ = 0.1F * (this.f_107223_.nextFloat() * this.f_107223_.nextFloat() * 1.0F + 1.0F);
      this.f_107225_ = (int)(16.0D / ((double)this.f_107223_.nextFloat() * 0.8D + 0.2D)) + 2;
      this.m_108339_(p_172299_);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.f_172290_);
      this.f_107215_ *= (double)0.95F;
      this.f_107216_ *= (double)0.9F;
      this.f_107217_ *= (double)0.95F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_172302_;

      public Provider(SpriteSet p_172304_) {
         this.f_172302_ = p_172304_;
      }

      public Particle m_6966_(SimpleParticleType p_172315_, ClientLevel p_172316_, double p_172317_, double p_172318_, double p_172319_, double p_172320_, double p_172321_, double p_172322_) {
         SnowflakeParticle snowflakeparticle = new SnowflakeParticle(p_172316_, p_172317_, p_172318_, p_172319_, p_172320_, p_172321_, p_172322_, this.f_172302_);
         snowflakeparticle.m_107253_(0.923F, 0.964F, 0.999F);
         return snowflakeparticle;
      }
   }
}