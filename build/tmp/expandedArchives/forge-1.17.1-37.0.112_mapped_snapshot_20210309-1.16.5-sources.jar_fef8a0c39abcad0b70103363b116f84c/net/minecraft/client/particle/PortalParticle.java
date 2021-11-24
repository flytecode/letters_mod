package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PortalParticle extends TextureSheetParticle {
   private final double f_107548_;
   private final double f_107549_;
   private final double f_107547_;

   protected PortalParticle(ClientLevel p_107551_, double p_107552_, double p_107553_, double p_107554_, double p_107555_, double p_107556_, double p_107557_) {
      super(p_107551_, p_107552_, p_107553_, p_107554_);
      this.f_107215_ = p_107555_;
      this.f_107216_ = p_107556_;
      this.f_107217_ = p_107557_;
      this.f_107212_ = p_107552_;
      this.f_107213_ = p_107553_;
      this.f_107214_ = p_107554_;
      this.f_107548_ = this.f_107212_;
      this.f_107549_ = this.f_107213_;
      this.f_107547_ = this.f_107214_;
      this.f_107663_ = 0.1F * (this.f_107223_.nextFloat() * 0.2F + 0.5F);
      float f = this.f_107223_.nextFloat() * 0.6F + 0.4F;
      this.f_107227_ = f * 0.9F;
      this.f_107228_ = f * 0.3F;
      this.f_107229_ = f;
      this.f_107225_ = (int)(Math.random() * 10.0D) + 40;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_6257_(double p_107560_, double p_107561_, double p_107562_) {
      this.m_107259_(this.m_107277_().m_82386_(p_107560_, p_107561_, p_107562_));
      this.m_107275_();
   }

   public float m_5902_(float p_107567_) {
      float f = ((float)this.f_107224_ + p_107567_) / (float)this.f_107225_;
      f = 1.0F - f;
      f = f * f;
      f = 1.0F - f;
      return this.f_107663_ * f;
   }

   public int m_6355_(float p_107564_) {
      int i = super.m_6355_(p_107564_);
      float f = (float)this.f_107224_ / (float)this.f_107225_;
      f = f * f;
      f = f * f;
      int j = i & 255;
      int k = i >> 16 & 255;
      k = k + (int)(f * 15.0F * 16.0F);
      if (k > 240) {
         k = 240;
      }

      return j | k << 16;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         float f = (float)this.f_107224_ / (float)this.f_107225_;
         float f1 = -f + f * f * 2.0F;
         float f2 = 1.0F - f1;
         this.f_107212_ = this.f_107548_ + this.f_107215_ * (double)f2;
         this.f_107213_ = this.f_107549_ + this.f_107216_ * (double)f2 + (double)(1.0F - f);
         this.f_107214_ = this.f_107547_ + this.f_107217_ * (double)f2;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_107568_;

      public Provider(SpriteSet p_107570_) {
         this.f_107568_ = p_107570_;
      }

      public Particle m_6966_(SimpleParticleType p_107581_, ClientLevel p_107582_, double p_107583_, double p_107584_, double p_107585_, double p_107586_, double p_107587_, double p_107588_) {
         PortalParticle portalparticle = new PortalParticle(p_107582_, p_107583_, p_107584_, p_107585_, p_107586_, p_107587_, p_107588_);
         portalparticle.m_108335_(this.f_107568_);
         return portalparticle;
      }
   }
}