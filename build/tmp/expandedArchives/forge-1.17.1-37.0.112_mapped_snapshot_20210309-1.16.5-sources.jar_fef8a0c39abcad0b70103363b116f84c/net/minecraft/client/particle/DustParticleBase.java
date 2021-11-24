package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustParticleBase<T extends DustParticleOptionsBase> extends TextureSheetParticle {
   private final SpriteSet f_172092_;

   protected DustParticleBase(ClientLevel p_172094_, double p_172095_, double p_172096_, double p_172097_, double p_172098_, double p_172099_, double p_172100_, T p_172101_, SpriteSet p_172102_) {
      super(p_172094_, p_172095_, p_172096_, p_172097_, p_172098_, p_172099_, p_172100_);
      this.f_172258_ = 0.96F;
      this.f_172259_ = true;
      this.f_172092_ = p_172102_;
      this.f_107215_ *= (double)0.1F;
      this.f_107216_ *= (double)0.1F;
      this.f_107217_ *= (double)0.1F;
      float f = this.f_107223_.nextFloat() * 0.4F + 0.6F;
      this.f_107227_ = this.m_172104_(p_172101_.m_175812_().m_122239_(), f);
      this.f_107228_ = this.m_172104_(p_172101_.m_175812_().m_122260_(), f);
      this.f_107229_ = this.m_172104_(p_172101_.m_175812_().m_122269_(), f);
      this.f_107663_ *= 0.75F * p_172101_.m_175813_();
      int i = (int)(8.0D / (this.f_107223_.nextDouble() * 0.8D + 0.2D));
      this.f_107225_ = (int)Math.max((float)i * p_172101_.m_175813_(), 1.0F);
      this.m_108339_(p_172102_);
   }

   protected float m_172104_(float p_172105_, float p_172106_) {
      return (this.f_107223_.nextFloat() * 0.2F + 0.8F) * p_172105_ * p_172106_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_172109_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_172109_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.f_172092_);
   }
}