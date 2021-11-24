package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrackingEmitter extends NoRenderParticle {
   private final Entity f_108387_;
   private int f_108388_;
   private final int f_108385_;
   private final ParticleOptions f_108386_;

   public TrackingEmitter(ClientLevel p_108390_, Entity p_108391_, ParticleOptions p_108392_) {
      this(p_108390_, p_108391_, p_108392_, 3);
   }

   public TrackingEmitter(ClientLevel p_108394_, Entity p_108395_, ParticleOptions p_108396_, int p_108397_) {
      this(p_108394_, p_108395_, p_108396_, p_108397_, p_108395_.m_20184_());
   }

   private TrackingEmitter(ClientLevel p_108399_, Entity p_108400_, ParticleOptions p_108401_, int p_108402_, Vec3 p_108403_) {
      super(p_108399_, p_108400_.m_20185_(), p_108400_.m_20227_(0.5D), p_108400_.m_20189_(), p_108403_.f_82479_, p_108403_.f_82480_, p_108403_.f_82481_);
      this.f_108387_ = p_108400_;
      this.f_108385_ = p_108402_;
      this.f_108386_ = p_108401_;
      this.m_5989_();
   }

   public void m_5989_() {
      for(int i = 0; i < 16; ++i) {
         double d0 = (double)(this.f_107223_.nextFloat() * 2.0F - 1.0F);
         double d1 = (double)(this.f_107223_.nextFloat() * 2.0F - 1.0F);
         double d2 = (double)(this.f_107223_.nextFloat() * 2.0F - 1.0F);
         if (!(d0 * d0 + d1 * d1 + d2 * d2 > 1.0D)) {
            double d3 = this.f_108387_.m_20165_(d0 / 4.0D);
            double d4 = this.f_108387_.m_20227_(0.5D + d1 / 4.0D);
            double d5 = this.f_108387_.m_20246_(d2 / 4.0D);
            this.f_107208_.m_6493_(this.f_108386_, false, d3, d4, d5, d0, d1 + 0.2D, d2);
         }
      }

      ++this.f_108388_;
      if (this.f_108388_ >= this.f_108385_) {
         this.m_107274_();
      }

   }
}