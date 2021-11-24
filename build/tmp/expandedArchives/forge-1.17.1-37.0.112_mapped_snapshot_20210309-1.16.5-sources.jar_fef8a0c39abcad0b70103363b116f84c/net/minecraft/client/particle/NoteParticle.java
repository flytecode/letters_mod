package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoteParticle extends TextureSheetParticle {
   NoteParticle(ClientLevel p_107167_, double p_107168_, double p_107169_, double p_107170_, double p_107171_) {
      super(p_107167_, p_107168_, p_107169_, p_107170_, 0.0D, 0.0D, 0.0D);
      this.f_172258_ = 0.66F;
      this.f_172259_ = true;
      this.f_107215_ *= (double)0.01F;
      this.f_107216_ *= (double)0.01F;
      this.f_107217_ *= (double)0.01F;
      this.f_107216_ += 0.2D;
      this.f_107227_ = Math.max(0.0F, Mth.m_14031_(((float)p_107171_ + 0.0F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
      this.f_107228_ = Math.max(0.0F, Mth.m_14031_(((float)p_107171_ + 0.33333334F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
      this.f_107229_ = Math.max(0.0F, Mth.m_14031_(((float)p_107171_ + 0.6666667F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
      this.f_107663_ *= 1.5F;
      this.f_107225_ = 6;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_107182_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_107182_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_107183_;

      public Provider(SpriteSet p_107185_) {
         this.f_107183_ = p_107185_;
      }

      public Particle m_6966_(SimpleParticleType p_107196_, ClientLevel p_107197_, double p_107198_, double p_107199_, double p_107200_, double p_107201_, double p_107202_, double p_107203_) {
         NoteParticle noteparticle = new NoteParticle(p_107197_, p_107198_, p_107199_, p_107200_, p_107201_);
         noteparticle.m_108335_(this.f_107183_);
         return noteparticle;
      }
   }
}