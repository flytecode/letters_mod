package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DustColorTransitionParticle extends DustParticleBase<DustColorTransitionOptions> {
   private final Vector3f f_172050_;
   private final Vector3f f_172051_;

   protected DustColorTransitionParticle(ClientLevel p_172053_, double p_172054_, double p_172055_, double p_172056_, double p_172057_, double p_172058_, double p_172059_, DustColorTransitionOptions p_172060_, SpriteSet p_172061_) {
      super(p_172053_, p_172054_, p_172055_, p_172056_, p_172057_, p_172058_, p_172059_, p_172060_, p_172061_);
      float f = this.f_107223_.nextFloat() * 0.4F + 0.6F;
      this.f_172050_ = this.m_172066_(p_172060_.m_175771_(), f);
      this.f_172051_ = this.m_172066_(p_172060_.m_175774_(), f);
   }

   private Vector3f m_172066_(Vector3f p_172067_, float p_172068_) {
      return new Vector3f(this.m_172104_(p_172067_.m_122239_(), p_172068_), this.m_172104_(p_172067_.m_122260_(), p_172068_), this.m_172104_(p_172067_.m_122269_(), p_172068_));
   }

   private void m_172069_(float p_172070_) {
      float f = ((float)this.f_107224_ + p_172070_) / ((float)this.f_107225_ + 1.0F);
      Vector3f vector3f = this.f_172050_.m_122281_();
      vector3f.m_122255_(this.f_172051_, f);
      this.f_107227_ = vector3f.m_122239_();
      this.f_107228_ = vector3f.m_122260_();
      this.f_107229_ = vector3f.m_122269_();
   }

   public void m_5744_(VertexConsumer p_172063_, Camera p_172064_, float p_172065_) {
      this.m_172069_(p_172065_);
      super.m_5744_(p_172063_, p_172064_, p_172065_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<DustColorTransitionOptions> {
      private final SpriteSet f_172071_;

      public Provider(SpriteSet p_172073_) {
         this.f_172071_ = p_172073_;
      }

      public Particle m_6966_(DustColorTransitionOptions p_172075_, ClientLevel p_172076_, double p_172077_, double p_172078_, double p_172079_, double p_172080_, double p_172081_, double p_172082_) {
         return new DustColorTransitionParticle(p_172076_, p_172077_, p_172078_, p_172079_, p_172080_, p_172081_, p_172082_, p_172075_, this.f_172071_);
      }
   }
}