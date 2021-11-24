package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhiteAshParticle extends BaseAshSmokeParticle {
   private static final int f_172509_ = 12235202;

   protected WhiteAshParticle(ClientLevel p_108512_, double p_108513_, double p_108514_, double p_108515_, double p_108516_, double p_108517_, double p_108518_, float p_108519_, SpriteSet p_108520_) {
      super(p_108512_, p_108513_, p_108514_, p_108515_, 0.1F, -0.1F, 0.1F, p_108516_, p_108517_, p_108518_, p_108519_, p_108520_, 0.0F, 20, 0.0125F, false);
      this.f_107227_ = 0.7294118F;
      this.f_107228_ = 0.69411767F;
      this.f_107229_ = 0.7607843F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108521_;

      public Provider(SpriteSet p_108523_) {
         this.f_108521_ = p_108523_;
      }

      public Particle m_6966_(SimpleParticleType p_108534_, ClientLevel p_108535_, double p_108536_, double p_108537_, double p_108538_, double p_108539_, double p_108540_, double p_108541_) {
         Random random = p_108535_.f_46441_;
         double d0 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
         double d1 = (double)random.nextFloat() * -0.5D * (double)random.nextFloat() * 0.1D * 5.0D;
         double d2 = (double)random.nextFloat() * -1.9D * (double)random.nextFloat() * 0.1D;
         return new WhiteAshParticle(p_108535_, p_108536_, p_108537_, p_108538_, d0, d1, d2, 1.0F, this.f_108521_);
      }
   }
}