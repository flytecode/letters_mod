package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CampfireSmokeParticle extends TextureSheetParticle {
   CampfireSmokeParticle(ClientLevel p_105856_, double p_105857_, double p_105858_, double p_105859_, double p_105860_, double p_105861_, double p_105862_, boolean p_105863_) {
      super(p_105856_, p_105857_, p_105858_, p_105859_);
      this.m_6569_(3.0F);
      this.m_107250_(0.25F, 0.25F);
      if (p_105863_) {
         this.f_107225_ = this.f_107223_.nextInt(50) + 280;
      } else {
         this.f_107225_ = this.f_107223_.nextInt(50) + 80;
      }

      this.f_107226_ = 3.0E-6F;
      this.f_107215_ = p_105860_;
      this.f_107216_ = p_105861_ + (double)(this.f_107223_.nextFloat() / 500.0F);
      this.f_107217_ = p_105862_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ < this.f_107225_ && !(this.f_107230_ <= 0.0F)) {
         this.f_107215_ += (double)(this.f_107223_.nextFloat() / 5000.0F * (float)(this.f_107223_.nextBoolean() ? 1 : -1));
         this.f_107217_ += (double)(this.f_107223_.nextFloat() / 5000.0F * (float)(this.f_107223_.nextBoolean() ? 1 : -1));
         this.f_107216_ -= (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         if (this.f_107224_ >= this.f_107225_ - 60 && this.f_107230_ > 0.01F) {
            this.f_107230_ -= 0.015F;
         }

      } else {
         this.m_107274_();
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class CosyProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105876_;

      public CosyProvider(SpriteSet p_105878_) {
         this.f_105876_ = p_105878_;
      }

      public Particle m_6966_(SimpleParticleType p_105889_, ClientLevel p_105890_, double p_105891_, double p_105892_, double p_105893_, double p_105894_, double p_105895_, double p_105896_) {
         CampfireSmokeParticle campfiresmokeparticle = new CampfireSmokeParticle(p_105890_, p_105891_, p_105892_, p_105893_, p_105894_, p_105895_, p_105896_, false);
         campfiresmokeparticle.m_107271_(0.9F);
         campfiresmokeparticle.m_108335_(this.f_105876_);
         return campfiresmokeparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SignalProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105897_;

      public SignalProvider(SpriteSet p_105899_) {
         this.f_105897_ = p_105899_;
      }

      public Particle m_6966_(SimpleParticleType p_105910_, ClientLevel p_105911_, double p_105912_, double p_105913_, double p_105914_, double p_105915_, double p_105916_, double p_105917_) {
         CampfireSmokeParticle campfiresmokeparticle = new CampfireSmokeParticle(p_105911_, p_105912_, p_105913_, p_105914_, p_105915_, p_105916_, p_105917_, true);
         campfiresmokeparticle.m_107271_(0.95F);
         campfiresmokeparticle.m_108335_(this.f_105897_);
         return campfiresmokeparticle;
      }
   }
}