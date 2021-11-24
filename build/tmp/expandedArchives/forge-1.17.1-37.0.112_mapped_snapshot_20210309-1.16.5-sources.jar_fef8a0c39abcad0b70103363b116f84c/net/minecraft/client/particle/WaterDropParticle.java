package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterDropParticle extends TextureSheetParticle {
   protected WaterDropParticle(ClientLevel p_108484_, double p_108485_, double p_108486_, double p_108487_) {
      super(p_108484_, p_108485_, p_108486_, p_108487_, 0.0D, 0.0D, 0.0D);
      this.f_107215_ *= (double)0.3F;
      this.f_107216_ = Math.random() * (double)0.2F + (double)0.1F;
      this.f_107217_ *= (double)0.3F;
      this.m_107250_(0.01F, 0.01F);
      this.f_107226_ = 0.06F;
      this.f_107225_ = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
      } else {
         this.f_107216_ -= (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107215_ *= (double)0.98F;
         this.f_107216_ *= (double)0.98F;
         this.f_107217_ *= (double)0.98F;
         if (this.f_107218_) {
            if (Math.random() < 0.5D) {
               this.m_107274_();
            }

            this.f_107215_ *= (double)0.7F;
            this.f_107217_ *= (double)0.7F;
         }

         BlockPos blockpos = new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_);
         double d0 = Math.max(this.f_107208_.m_8055_(blockpos).m_60812_(this.f_107208_, blockpos).m_83290_(Direction.Axis.Y, this.f_107212_ - (double)blockpos.m_123341_(), this.f_107214_ - (double)blockpos.m_123343_()), (double)this.f_107208_.m_6425_(blockpos).m_76155_(this.f_107208_, blockpos));
         if (d0 > 0.0D && this.f_107213_ < (double)blockpos.m_123342_() + d0) {
            this.m_107274_();
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_108490_;

      public Provider(SpriteSet p_108492_) {
         this.f_108490_ = p_108492_;
      }

      public Particle m_6966_(SimpleParticleType p_108503_, ClientLevel p_108504_, double p_108505_, double p_108506_, double p_108507_, double p_108508_, double p_108509_, double p_108510_) {
         WaterDropParticle waterdropparticle = new WaterDropParticle(p_108504_, p_108505_, p_108506_, p_108507_);
         waterdropparticle.m_108335_(this.f_108490_);
         return waterdropparticle;
      }
   }
}