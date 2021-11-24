package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaParticle extends TextureSheetParticle {
   LavaParticle(ClientLevel p_107074_, double p_107075_, double p_107076_, double p_107077_) {
      super(p_107074_, p_107075_, p_107076_, p_107077_, 0.0D, 0.0D, 0.0D);
      this.f_107226_ = 0.75F;
      this.f_172258_ = 0.999F;
      this.f_107215_ *= (double)0.8F;
      this.f_107216_ *= (double)0.8F;
      this.f_107217_ *= (double)0.8F;
      this.f_107216_ = (double)(this.f_107223_.nextFloat() * 0.4F + 0.05F);
      this.f_107663_ *= this.f_107223_.nextFloat() * 2.0F + 0.2F;
      this.f_107225_ = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public int m_6355_(float p_107086_) {
      int i = super.m_6355_(p_107086_);
      int j = 240;
      int k = i >> 16 & 255;
      return 240 | k << 16;
   }

   public float m_5902_(float p_107089_) {
      float f = ((float)this.f_107224_ + p_107089_) / (float)this.f_107225_;
      return this.f_107663_ * (1.0F - f * f);
   }

   public void m_5989_() {
      super.m_5989_();
      if (!this.f_107220_) {
         float f = (float)this.f_107224_ / (float)this.f_107225_;
         if (this.f_107223_.nextFloat() > f) {
            this.f_107208_.m_7106_(ParticleTypes.f_123762_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_107090_;

      public Provider(SpriteSet p_107092_) {
         this.f_107090_ = p_107092_;
      }

      public Particle m_6966_(SimpleParticleType p_107103_, ClientLevel p_107104_, double p_107105_, double p_107106_, double p_107107_, double p_107108_, double p_107109_, double p_107110_) {
         LavaParticle lavaparticle = new LavaParticle(p_107104_, p_107105_, p_107106_, p_107107_);
         lavaparticle.m_108335_(this.f_107090_);
         return lavaparticle;
      }
   }
}