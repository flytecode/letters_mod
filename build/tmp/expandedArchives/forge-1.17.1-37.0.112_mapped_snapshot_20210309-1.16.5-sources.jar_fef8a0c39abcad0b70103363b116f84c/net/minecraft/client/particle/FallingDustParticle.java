package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingDustParticle extends TextureSheetParticle {
   private final float f_106607_;
   private final SpriteSet f_106608_;

   FallingDustParticle(ClientLevel p_106610_, double p_106611_, double p_106612_, double p_106613_, float p_106614_, float p_106615_, float p_106616_, SpriteSet p_106617_) {
      super(p_106610_, p_106611_, p_106612_, p_106613_);
      this.f_106608_ = p_106617_;
      this.f_107227_ = p_106614_;
      this.f_107228_ = p_106615_;
      this.f_107229_ = p_106616_;
      float f = 0.9F;
      this.f_107663_ *= 0.67499995F;
      int i = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
      this.f_107225_ = (int)Math.max((float)i * 0.9F, 1.0F);
      this.m_108339_(p_106617_);
      this.f_106607_ = ((float)Math.random() - 0.5F) * 0.1F;
      this.f_107231_ = (float)Math.random() * ((float)Math.PI * 2F);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_106631_) {
      return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_106631_) / (float)this.f_107225_ * 32.0F, 0.0F, 1.0F);
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_108339_(this.f_106608_);
         this.f_107204_ = this.f_107231_;
         this.f_107231_ += (float)Math.PI * this.f_106607_ * 2.0F;
         if (this.f_107218_) {
            this.f_107204_ = this.f_107231_ = 0.0F;
         }

         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107216_ -= (double)0.003F;
         this.f_107216_ = Math.max(this.f_107216_, (double)-0.14F);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<BlockParticleOption> {
      private final SpriteSet f_106632_;

      public Provider(SpriteSet p_106634_) {
         this.f_106632_ = p_106634_;
      }

      @Nullable
      public Particle m_6966_(BlockParticleOption p_106636_, ClientLevel p_106637_, double p_106638_, double p_106639_, double p_106640_, double p_106641_, double p_106642_, double p_106643_) {
         BlockState blockstate = p_106636_.m_123642_();
         if (!blockstate.m_60795_() && blockstate.m_60799_() == RenderShape.INVISIBLE) {
            return null;
         } else {
            BlockPos blockpos = new BlockPos(p_106638_, p_106639_, p_106640_);
            int i = Minecraft.m_91087_().m_91298_().m_92582_(blockstate, p_106637_, blockpos);
            if (blockstate.m_60734_() instanceof FallingBlock) {
               i = ((FallingBlock)blockstate.m_60734_()).m_6248_(blockstate, p_106637_, blockpos);
            }

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            return new FallingDustParticle(p_106637_, p_106638_, p_106639_, p_106640_, f, f1, f2, this.f_106632_);
         }
      }
   }
}