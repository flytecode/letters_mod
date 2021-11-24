package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleAnimatedParticle extends TextureSheetParticle {
   protected final SpriteSet f_107644_;
   private float f_107640_;
   private float f_107641_;
   private float f_107642_;
   private boolean f_107643_;

   protected SimpleAnimatedParticle(ClientLevel p_107647_, double p_107648_, double p_107649_, double p_107650_, SpriteSet p_107651_, float p_107652_) {
      super(p_107647_, p_107648_, p_107649_, p_107650_);
      this.f_172258_ = 0.91F;
      this.f_107226_ = p_107652_;
      this.f_107644_ = p_107651_;
   }

   public void m_107657_(int p_107658_) {
      float f = (float)((p_107658_ & 16711680) >> 16) / 255.0F;
      float f1 = (float)((p_107658_ & '\uff00') >> 8) / 255.0F;
      float f2 = (float)((p_107658_ & 255) >> 0) / 255.0F;
      float f3 = 1.0F;
      this.m_107253_(f * 1.0F, f1 * 1.0F, f2 * 1.0F);
   }

   public void m_107659_(int p_107660_) {
      this.f_107640_ = (float)((p_107660_ & 16711680) >> 16) / 255.0F;
      this.f_107641_ = (float)((p_107660_ & '\uff00') >> 8) / 255.0F;
      this.f_107642_ = (float)((p_107660_ & 255) >> 0) / 255.0F;
      this.f_107643_ = true;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.f_107644_);
      if (this.f_107224_ > this.f_107225_ / 2) {
         this.m_107271_(1.0F - ((float)this.f_107224_ - (float)(this.f_107225_ / 2)) / (float)this.f_107225_);
         if (this.f_107643_) {
            this.f_107227_ += (this.f_107640_ - this.f_107227_) * 0.2F;
            this.f_107228_ += (this.f_107641_ - this.f_107228_) * 0.2F;
            this.f_107229_ += (this.f_107642_ - this.f_107229_) * 0.2F;
         }
      }

   }

   public int m_6355_(float p_107655_) {
      return 15728880;
   }
}