package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BaseAshSmokeParticle extends TextureSheetParticle {
   private final SpriteSet f_105620_;

   protected BaseAshSmokeParticle(ClientLevel p_171904_, double p_171905_, double p_171906_, double p_171907_, float p_171908_, float p_171909_, float p_171910_, double p_171911_, double p_171912_, double p_171913_, float p_171914_, SpriteSet p_171915_, float p_171916_, int p_171917_, float p_171918_, boolean p_171919_) {
      super(p_171904_, p_171905_, p_171906_, p_171907_, 0.0D, 0.0D, 0.0D);
      this.f_172258_ = 0.96F;
      this.f_107226_ = p_171918_;
      this.f_172259_ = true;
      this.f_105620_ = p_171915_;
      this.f_107215_ *= (double)p_171908_;
      this.f_107216_ *= (double)p_171909_;
      this.f_107217_ *= (double)p_171910_;
      this.f_107215_ += p_171911_;
      this.f_107216_ += p_171912_;
      this.f_107217_ += p_171913_;
      float f = p_171904_.f_46441_.nextFloat() * p_171916_;
      this.f_107227_ = f;
      this.f_107228_ = f;
      this.f_107229_ = f;
      this.f_107663_ *= 0.75F * p_171914_;
      this.f_107225_ = (int)((double)p_171917_ / ((double)p_171904_.f_46441_.nextFloat() * 0.8D + 0.2D));
      this.f_107225_ = (int)((float)this.f_107225_ * p_171914_);
      this.f_107225_ = Math.max(this.f_107225_, 1);
      this.m_108339_(p_171915_);
      this.f_107219_ = p_171919_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_105642_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_105642_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.f_105620_);
   }
}