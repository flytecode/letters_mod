package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightTexture implements AutoCloseable {
   public static final int f_173040_ = 15728880;
   public static final int f_173041_ = 15728640;
   public static final int f_173042_ = 240;
   private final DynamicTexture f_109870_;
   private final NativeImage f_109871_;
   private final ResourceLocation f_109872_;
   private boolean f_109873_;
   private float f_109874_;
   private final GameRenderer f_109875_;
   private final Minecraft f_109876_;

   public LightTexture(GameRenderer p_109878_, Minecraft p_109879_) {
      this.f_109875_ = p_109878_;
      this.f_109876_ = p_109879_;
      this.f_109870_ = new DynamicTexture(16, 16, false);
      this.f_109872_ = this.f_109876_.m_91097_().m_118490_("light_map", this.f_109870_);
      this.f_109871_ = this.f_109870_.m_117991_();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            this.f_109871_.m_84988_(j, i, -1);
         }
      }

      this.f_109870_.m_117985_();
   }

   public void close() {
      this.f_109870_.close();
   }

   public void m_109880_() {
      this.f_109874_ = (float)((double)this.f_109874_ + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.1D);
      this.f_109874_ = (float)((double)this.f_109874_ * 0.9D);
      this.f_109873_ = true;
   }

   public void m_109891_() {
      RenderSystem.m_157453_(2, 0);
   }

   public void m_109896_() {
      RenderSystem.m_157456_(2, this.f_109872_);
      this.f_109876_.m_91097_().m_174784_(this.f_109872_);
      RenderSystem.m_69937_(3553, 10241, 9729);
      RenderSystem.m_69937_(3553, 10240, 9729);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void m_109881_(float p_109882_) {
      if (this.f_109873_) {
         this.f_109873_ = false;
         this.f_109876_.m_91307_().m_6180_("lightTex");
         ClientLevel clientlevel = this.f_109876_.f_91073_;
         if (clientlevel != null) {
            float f = clientlevel.m_104805_(1.0F);
            float f1;
            if (clientlevel.m_104819_() > 0) {
               f1 = 1.0F;
            } else {
               f1 = f * 0.95F + 0.05F;
            }

            float f3 = this.f_109876_.f_91074_.m_108639_();
            float f2;
            if (this.f_109876_.f_91074_.m_21023_(MobEffects.f_19611_)) {
               f2 = GameRenderer.m_109108_(this.f_109876_.f_91074_, p_109882_);
            } else if (f3 > 0.0F && this.f_109876_.f_91074_.m_21023_(MobEffects.f_19592_)) {
               f2 = f3;
            } else {
               f2 = 0.0F;
            }

            Vector3f vector3f = new Vector3f(f, f, 1.0F);
            vector3f.m_122255_(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float f4 = this.f_109874_ + 1.5F;
            Vector3f vector3f1 = new Vector3f();

            for(int i = 0; i < 16; ++i) {
               for(int j = 0; j < 16; ++j) {
                  float f5 = this.m_109888_(clientlevel, i) * f1;
                  float f6 = this.m_109888_(clientlevel, j) * f4;
                  float f7 = f6 * ((f6 * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float f8 = f6 * (f6 * f6 * 0.6F + 0.4F);
                  vector3f1.m_122245_(f6, f7, f8);
                  if (clientlevel.m_104583_().m_108884_()) {
                     vector3f1.m_122255_(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                  } else {
                     Vector3f vector3f2 = vector3f.m_122281_();
                     vector3f2.m_122261_(f5);
                     vector3f1.m_122253_(vector3f2);
                     vector3f1.m_122255_(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.f_109875_.m_109131_(p_109882_) > 0.0F) {
                        float f9 = this.f_109875_.m_109131_(p_109882_);
                        Vector3f vector3f3 = vector3f1.m_122281_();
                        vector3f3.m_122263_(0.7F, 0.6F, 0.6F);
                        vector3f1.m_122255_(vector3f3, f9);
                     }
                  }

                  vector3f1.m_122242_(0.0F, 1.0F);
                  if (f2 > 0.0F) {
                     float f10 = Math.max(vector3f1.m_122239_(), Math.max(vector3f1.m_122260_(), vector3f1.m_122269_()));
                     if (f10 < 1.0F) {
                        float f12 = 1.0F / f10;
                        Vector3f vector3f5 = vector3f1.m_122281_();
                        vector3f5.m_122261_(f12);
                        vector3f1.m_122255_(vector3f5, f2);
                     }
                  }

                  float f11 = (float)this.f_109876_.f_91066_.f_92071_;
                  Vector3f vector3f4 = vector3f1.m_122281_();
                  vector3f4.m_122258_(this::m_109892_);
                  vector3f1.m_122255_(vector3f4, f11);
                  vector3f1.m_122255_(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  vector3f1.m_122242_(0.0F, 1.0F);
                  vector3f1.m_122261_(255.0F);
                  int j1 = 255;
                  int k = (int)vector3f1.m_122239_();
                  int l = (int)vector3f1.m_122260_();
                  int i1 = (int)vector3f1.m_122269_();
                  this.f_109871_.m_84988_(j, i, -16777216 | i1 << 16 | l << 8 | k);
               }
            }

            this.f_109870_.m_117985_();
            this.f_109876_.m_91307_().m_7238_();
         }
      }
   }

   private float m_109892_(float p_109893_) {
      float f = 1.0F - p_109893_;
      return 1.0F - f * f * f * f;
   }

   private float m_109888_(Level p_109889_, int p_109890_) {
      return p_109889_.m_6042_().m_63902_(p_109890_);
   }

   public static int m_109885_(int p_109886_, int p_109887_) {
      return p_109886_ << 4 | p_109887_ << 20;
   }

   public static int m_109883_(int p_109884_) {
      return (p_109884_ & 0xFFFF) >> 4; // Forge: Fix fullbright quads showing dark artifacts. Reported as MC-169806
   }

   public static int m_109894_(int p_109895_) {
      return p_109895_ >> 20 & '\uffff';
   }
}
