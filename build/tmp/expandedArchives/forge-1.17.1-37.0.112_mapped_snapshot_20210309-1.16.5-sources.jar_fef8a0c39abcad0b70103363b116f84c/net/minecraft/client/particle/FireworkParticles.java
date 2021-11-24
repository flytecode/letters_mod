package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireworkParticles {
   @OnlyIn(Dist.CLIENT)
   public static class FlashProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106655_;

      public FlashProvider(SpriteSet p_106657_) {
         this.f_106655_ = p_106657_;
      }

      public Particle m_6966_(SimpleParticleType p_106668_, ClientLevel p_106669_, double p_106670_, double p_106671_, double p_106672_, double p_106673_, double p_106674_, double p_106675_) {
         FireworkParticles.OverlayParticle fireworkparticles$overlayparticle = new FireworkParticles.OverlayParticle(p_106669_, p_106670_, p_106671_, p_106672_);
         fireworkparticles$overlayparticle.m_108335_(this.f_106655_);
         return fireworkparticles$overlayparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class OverlayParticle extends TextureSheetParticle {
      OverlayParticle(ClientLevel p_106677_, double p_106678_, double p_106679_, double p_106680_) {
         super(p_106677_, p_106678_, p_106679_, p_106680_);
         this.f_107225_ = 4;
      }

      public ParticleRenderType m_7556_() {
         return ParticleRenderType.f_107431_;
      }

      public void m_5744_(VertexConsumer p_106688_, Camera p_106689_, float p_106690_) {
         this.m_107271_(0.6F - ((float)this.f_107224_ + p_106690_ - 1.0F) * 0.25F * 0.5F);
         super.m_5744_(p_106688_, p_106689_, p_106690_);
      }

      public float m_5902_(float p_106693_) {
         return 7.1F * Mth.m_14031_(((float)this.f_107224_ + p_106693_ - 1.0F) * 0.25F * (float)Math.PI);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class SparkParticle extends SimpleAnimatedParticle {
      private boolean f_106700_;
      private boolean f_106694_;
      private final ParticleEngine f_106695_;
      private float f_106696_;
      private float f_106697_;
      private float f_106698_;
      private boolean f_106699_;

      SparkParticle(ClientLevel p_106702_, double p_106703_, double p_106704_, double p_106705_, double p_106706_, double p_106707_, double p_106708_, ParticleEngine p_106709_, SpriteSet p_106710_) {
         super(p_106702_, p_106703_, p_106704_, p_106705_, p_106710_, 0.1F);
         this.f_107215_ = p_106706_;
         this.f_107216_ = p_106707_;
         this.f_107217_ = p_106708_;
         this.f_106695_ = p_106709_;
         this.f_107663_ *= 0.75F;
         this.f_107225_ = 48 + this.f_107223_.nextInt(12);
         this.m_108339_(p_106710_);
      }

      public void m_106727_(boolean p_106728_) {
         this.f_106700_ = p_106728_;
      }

      public void m_106729_(boolean p_106730_) {
         this.f_106694_ = p_106730_;
      }

      public void m_5744_(VertexConsumer p_106724_, Camera p_106725_, float p_106726_) {
         if (!this.f_106694_ || this.f_107224_ < this.f_107225_ / 3 || (this.f_107224_ + this.f_107225_) / 3 % 2 == 0) {
            super.m_5744_(p_106724_, p_106725_, p_106726_);
         }

      }

      public void m_5989_() {
         super.m_5989_();
         if (this.f_106700_ && this.f_107224_ < this.f_107225_ / 2 && (this.f_107224_ + this.f_107225_) % 2 == 0) {
            FireworkParticles.SparkParticle fireworkparticles$sparkparticle = new FireworkParticles.SparkParticle(this.f_107208_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0D, 0.0D, 0.0D, this.f_106695_, this.f_107644_);
            fireworkparticles$sparkparticle.m_107271_(0.99F);
            fireworkparticles$sparkparticle.m_107253_(this.f_107227_, this.f_107228_, this.f_107229_);
            fireworkparticles$sparkparticle.f_107224_ = fireworkparticles$sparkparticle.f_107225_ / 2;
            if (this.f_106699_) {
               fireworkparticles$sparkparticle.f_106699_ = true;
               fireworkparticles$sparkparticle.f_106696_ = this.f_106696_;
               fireworkparticles$sparkparticle.f_106697_ = this.f_106697_;
               fireworkparticles$sparkparticle.f_106698_ = this.f_106698_;
            }

            fireworkparticles$sparkparticle.f_106694_ = this.f_106694_;
            this.f_106695_.m_107344_(fireworkparticles$sparkparticle);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SparkProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet f_106731_;

      public SparkProvider(SpriteSet p_106733_) {
         this.f_106731_ = p_106733_;
      }

      public Particle m_6966_(SimpleParticleType p_106744_, ClientLevel p_106745_, double p_106746_, double p_106747_, double p_106748_, double p_106749_, double p_106750_, double p_106751_) {
         FireworkParticles.SparkParticle fireworkparticles$sparkparticle = new FireworkParticles.SparkParticle(p_106745_, p_106746_, p_106747_, p_106748_, p_106749_, p_106750_, p_106751_, Minecraft.m_91087_().f_91061_, this.f_106731_);
         fireworkparticles$sparkparticle.m_107271_(0.99F);
         return fireworkparticles$sparkparticle;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Starter extends NoRenderParticle {
      private int f_106754_;
      private final ParticleEngine f_106755_;
      private ListTag f_106752_;
      private boolean f_106753_;

      public Starter(ClientLevel p_106757_, double p_106758_, double p_106759_, double p_106760_, double p_106761_, double p_106762_, double p_106763_, ParticleEngine p_106764_, @Nullable CompoundTag p_106765_) {
         super(p_106757_, p_106758_, p_106759_, p_106760_);
         this.f_107215_ = p_106761_;
         this.f_107216_ = p_106762_;
         this.f_107217_ = p_106763_;
         this.f_106755_ = p_106764_;
         this.f_107225_ = 8;
         if (p_106765_ != null) {
            this.f_106752_ = p_106765_.m_128437_("Explosions", 10);
            if (this.f_106752_.isEmpty()) {
               this.f_106752_ = null;
            } else {
               this.f_107225_ = this.f_106752_.size() * 2 - 1;

               for(int i = 0; i < this.f_106752_.size(); ++i) {
                  CompoundTag compoundtag = this.f_106752_.m_128728_(i);
                  if (compoundtag.m_128471_("Flicker")) {
                     this.f_106753_ = true;
                     this.f_107225_ += 15;
                     break;
                  }
               }
            }
         }

      }

      public void m_5989_() {
         if (this.f_106754_ == 0 && this.f_106752_ != null) {
            boolean flag = this.m_106798_();
            boolean flag1 = false;
            if (this.f_106752_.size() >= 3) {
               flag1 = true;
            } else {
               for(int i = 0; i < this.f_106752_.size(); ++i) {
                  CompoundTag compoundtag = this.f_106752_.m_128728_(i);
                  if (FireworkRocketItem.Shape.m_41237_(compoundtag.m_128445_("Type")) == FireworkRocketItem.Shape.LARGE_BALL) {
                     flag1 = true;
                     break;
                  }
               }
            }

            SoundEvent soundevent1;
            if (flag1) {
               soundevent1 = flag ? SoundEvents.f_11931_ : SoundEvents.f_11930_;
            } else {
               soundevent1 = flag ? SoundEvents.f_11929_ : SoundEvents.f_11928_;
            }

            this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, soundevent1, SoundSource.AMBIENT, 20.0F, 0.95F + this.f_107223_.nextFloat() * 0.1F, true);
         }

         if (this.f_106754_ % 2 == 0 && this.f_106752_ != null && this.f_106754_ / 2 < this.f_106752_.size()) {
            int k = this.f_106754_ / 2;
            CompoundTag compoundtag1 = this.f_106752_.m_128728_(k);
            FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.m_41237_(compoundtag1.m_128445_("Type"));
            boolean flag4 = compoundtag1.m_128471_("Trail");
            boolean flag2 = compoundtag1.m_128471_("Flicker");
            int[] aint = compoundtag1.m_128465_("Colors");
            int[] aint1 = compoundtag1.m_128465_("FadeColors");
            if (aint.length == 0) {
               aint = new int[]{DyeColor.BLACK.m_41070_()};
            }

            switch(fireworkrocketitem$shape) {
            case SMALL_BALL:
            default:
               this.m_106778_(0.25D, 2, aint, aint1, flag4, flag2);
               break;
            case LARGE_BALL:
               this.m_106778_(0.5D, 4, aint, aint1, flag4, flag2);
               break;
            case STAR:
               this.m_106785_(0.5D, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, aint, aint1, flag4, flag2, false);
               break;
            case CREEPER:
               this.m_106785_(0.5D, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, aint, aint1, flag4, flag2, true);
               break;
            case BURST:
               this.m_106793_(aint, aint1, flag4, flag2);
            }

            int j = aint[0];
            float f = (float)((j & 16711680) >> 16) / 255.0F;
            float f1 = (float)((j & '\uff00') >> 8) / 255.0F;
            float f2 = (float)((j & 255) >> 0) / 255.0F;
            Particle particle = this.f_106755_.m_107370_(ParticleTypes.f_123747_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0D, 0.0D, 0.0D);
            particle.m_107253_(f, f1, f2);
         }

         ++this.f_106754_;
         if (this.f_106754_ > this.f_107225_) {
            if (this.f_106753_) {
               boolean flag3 = this.m_106798_();
               SoundEvent soundevent = flag3 ? SoundEvents.f_11935_ : SoundEvents.f_11934_;
               this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, soundevent, SoundSource.AMBIENT, 20.0F, 0.9F + this.f_107223_.nextFloat() * 0.15F, true);
            }

            this.m_107274_();
         }

      }

      private boolean m_106798_() {
         Minecraft minecraft = Minecraft.m_91087_();
         return minecraft.f_91063_.m_109153_().m_90583_().m_82531_(this.f_107212_, this.f_107213_, this.f_107214_) >= 256.0D;
      }

      private void m_106767_(double p_106768_, double p_106769_, double p_106770_, double p_106771_, double p_106772_, double p_106773_, int[] p_106774_, int[] p_106775_, boolean p_106776_, boolean p_106777_) {
         FireworkParticles.SparkParticle fireworkparticles$sparkparticle = (FireworkParticles.SparkParticle)this.f_106755_.m_107370_(ParticleTypes.f_123815_, p_106768_, p_106769_, p_106770_, p_106771_, p_106772_, p_106773_);
         fireworkparticles$sparkparticle.m_106727_(p_106776_);
         fireworkparticles$sparkparticle.m_106729_(p_106777_);
         fireworkparticles$sparkparticle.m_107271_(0.99F);
         int i = this.f_107223_.nextInt(p_106774_.length);
         fireworkparticles$sparkparticle.m_107657_(p_106774_[i]);
         if (p_106775_.length > 0) {
            fireworkparticles$sparkparticle.m_107659_(Util.m_137542_(p_106775_, this.f_107223_));
         }

      }

      private void m_106778_(double p_106779_, int p_106780_, int[] p_106781_, int[] p_106782_, boolean p_106783_, boolean p_106784_) {
         double d0 = this.f_107212_;
         double d1 = this.f_107213_;
         double d2 = this.f_107214_;

         for(int i = -p_106780_; i <= p_106780_; ++i) {
            for(int j = -p_106780_; j <= p_106780_; ++j) {
               for(int k = -p_106780_; k <= p_106780_; ++k) {
                  double d3 = (double)j + (this.f_107223_.nextDouble() - this.f_107223_.nextDouble()) * 0.5D;
                  double d4 = (double)i + (this.f_107223_.nextDouble() - this.f_107223_.nextDouble()) * 0.5D;
                  double d5 = (double)k + (this.f_107223_.nextDouble() - this.f_107223_.nextDouble()) * 0.5D;
                  double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / p_106779_ + this.f_107223_.nextGaussian() * 0.05D;
                  this.m_106767_(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, p_106781_, p_106782_, p_106783_, p_106784_);
                  if (i != -p_106780_ && i != p_106780_ && j != -p_106780_ && j != p_106780_) {
                     k += p_106780_ * 2 - 1;
                  }
               }
            }
         }

      }

      private void m_106785_(double p_106786_, double[][] p_106787_, int[] p_106788_, int[] p_106789_, boolean p_106790_, boolean p_106791_, boolean p_106792_) {
         double d0 = p_106787_[0][0];
         double d1 = p_106787_[0][1];
         this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, d0 * p_106786_, d1 * p_106786_, 0.0D, p_106788_, p_106789_, p_106790_, p_106791_);
         float f = this.f_107223_.nextFloat() * (float)Math.PI;
         double d2 = p_106792_ ? 0.034D : 0.34D;

         for(int i = 0; i < 3; ++i) {
            double d3 = (double)f + (double)((float)i * (float)Math.PI) * d2;
            double d4 = d0;
            double d5 = d1;

            for(int j = 1; j < p_106787_.length; ++j) {
               double d6 = p_106787_[j][0];
               double d7 = p_106787_[j][1];

               for(double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D) {
                  double d9 = Mth.m_14139_(d8, d4, d6) * p_106786_;
                  double d10 = Mth.m_14139_(d8, d5, d7) * p_106786_;
                  double d11 = d9 * Math.sin(d3);
                  d9 = d9 * Math.cos(d3);

                  for(double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D) {
                     this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, d9 * d12, d10, d11 * d12, p_106788_, p_106789_, p_106790_, p_106791_);
                  }
               }

               d4 = d6;
               d5 = d7;
            }
         }

      }

      private void m_106793_(int[] p_106794_, int[] p_106795_, boolean p_106796_, boolean p_106797_) {
         double d0 = this.f_107223_.nextGaussian() * 0.05D;
         double d1 = this.f_107223_.nextGaussian() * 0.05D;

         for(int i = 0; i < 70; ++i) {
            double d2 = this.f_107215_ * 0.5D + this.f_107223_.nextGaussian() * 0.15D + d0;
            double d3 = this.f_107217_ * 0.5D + this.f_107223_.nextGaussian() * 0.15D + d1;
            double d4 = this.f_107216_ * 0.5D + this.f_107223_.nextDouble() * 0.5D;
            this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, d2, d4, d3, p_106794_, p_106795_, p_106796_, p_106797_);
         }

      }
   }
}