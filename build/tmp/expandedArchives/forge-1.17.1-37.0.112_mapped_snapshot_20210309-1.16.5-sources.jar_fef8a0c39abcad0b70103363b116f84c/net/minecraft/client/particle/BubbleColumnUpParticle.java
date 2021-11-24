package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BubbleColumnUpParticle extends TextureSheetParticle {
   BubbleColumnUpParticle(ClientLevel p_105733_, double p_105734_, double p_105735_, double p_105736_, double p_105737_, double p_105738_, double p_105739_) {
      super(p_105733_, p_105734_, p_105735_, p_105736_);
      this.f_107226_ = -0.125F;
      this.f_172258_ = 0.85F;
      this.m_107250_(0.02F, 0.02F);
      this.f_107663_ *= this.f_107223_.nextFloat() * 0.6F + 0.2F;
      this.f_107215_ = p_105737_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.f_107216_ = p_105738_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.f_107217_ = p_105739_ * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
      this.f_107225_ = (int)(40.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void m_5989_() {
      super.m_5989_();
      if (!this.f_107220_ && !this.f_107208_.m_6425_(new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_76153_(FluidTags.f_13131_)) {
         this.m_107274_();
      }

   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_105751_;

      public Provider(SpriteSet p_105753_) {
         this.f_105751_ = p_105753_;
      }

      public Particle m_6966_(SimpleParticleType p_105764_, ClientLevel p_105765_, double p_105766_, double p_105767_, double p_105768_, double p_105769_, double p_105770_, double p_105771_) {
         BubbleColumnUpParticle bubblecolumnupparticle = new BubbleColumnUpParticle(p_105765_, p_105766_, p_105767_, p_105768_, p_105769_, p_105770_, p_105771_);
         bubblecolumnupparticle.m_108335_(this.f_105751_);
         return bubblecolumnupparticle;
      }
   }
}