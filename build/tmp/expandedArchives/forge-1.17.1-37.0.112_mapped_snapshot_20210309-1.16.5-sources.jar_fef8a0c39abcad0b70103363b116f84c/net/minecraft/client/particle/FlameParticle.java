package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlameParticle extends RisingParticle {
   FlameParticle(ClientLevel p_106800_, double p_106801_, double p_106802_, double p_106803_, double p_106804_, double p_106805_, double p_106806_) {
      super(p_106800_, p_106801_, p_106802_, p_106803_, p_106804_, p_106805_, p_106806_);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_6257_(double p_106817_, double p_106818_, double p_106819_) {
      this.m_107259_(this.m_107277_().m_82386_(p_106817_, p_106818_, p_106819_));
      this.m_107275_();
   }

   public float m_5902_(float p_106824_) {
      float f = ((float)this.f_107224_ + p_106824_) / (float)this.f_107225_;
      return this.f_107663_ * (1.0F - f * f * 0.5F);
   }

   public int m_6355_(float p_106821_) {
      float f = ((float)this.f_107224_ + p_106821_) / (float)this.f_107225_;
      f = Mth.m_14036_(f, 0.0F, 1.0F);
      int i = super.m_6355_(p_106821_);
      int j = i & 255;
      int k = i >> 16 & 255;
      j = j + (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106825_;

      public Provider(SpriteSet p_106827_) {
         this.f_106825_ = p_106827_;
      }

      public Particle m_6966_(SimpleParticleType p_106838_, ClientLevel p_106839_, double p_106840_, double p_106841_, double p_106842_, double p_106843_, double p_106844_, double p_106845_) {
         FlameParticle flameparticle = new FlameParticle(p_106839_, p_106840_, p_106841_, p_106842_, p_106843_, p_106844_, p_106845_);
         flameparticle.m_108335_(this.f_106825_);
         return flameparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SmallFlameProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_172111_;

      public SmallFlameProvider(SpriteSet p_172113_) {
         this.f_172111_ = p_172113_;
      }

      public Particle m_6966_(SimpleParticleType p_172124_, ClientLevel p_172125_, double p_172126_, double p_172127_, double p_172128_, double p_172129_, double p_172130_, double p_172131_) {
         FlameParticle flameparticle = new FlameParticle(p_172125_, p_172126_, p_172127_, p_172128_, p_172129_, p_172130_, p_172131_);
         flameparticle.m_108335_(this.f_172111_);
         flameparticle.m_6569_(0.5F);
         return flameparticle;
      }
   }
}