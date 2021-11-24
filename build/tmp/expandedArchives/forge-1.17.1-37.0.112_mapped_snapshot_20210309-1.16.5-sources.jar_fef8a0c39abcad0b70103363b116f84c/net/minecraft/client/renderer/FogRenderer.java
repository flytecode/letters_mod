package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FogRenderer {
   private static final int f_172575_ = 192;
   public static final float f_172574_ = 5000.0F;
   private static float f_109010_;
   private static float f_109011_;
   private static float f_109012_;
   private static int f_109013_ = -1;
   private static int f_109014_ = -1;
   private static long f_109015_ = -1L;

   public static void m_109018_(Camera p_109019_, float p_109020_, ClientLevel p_109021_, int p_109022_, float p_109023_) {
      FogType fogtype = p_109019_.m_167685_();
      Entity entity = p_109019_.m_90592_();
      if (fogtype == FogType.WATER) {
         long i = Util.m_137550_();
         int j = p_109021_.m_46857_(new BlockPos(p_109019_.m_90583_())).m_47561_();
         if (f_109015_ < 0L) {
            f_109013_ = j;
            f_109014_ = j;
            f_109015_ = i;
         }

         int k = f_109013_ >> 16 & 255;
         int l = f_109013_ >> 8 & 255;
         int i1 = f_109013_ & 255;
         int j1 = f_109014_ >> 16 & 255;
         int k1 = f_109014_ >> 8 & 255;
         int l1 = f_109014_ & 255;
         float f = Mth.m_14036_((float)(i - f_109015_) / 5000.0F, 0.0F, 1.0F);
         float f1 = Mth.m_14179_(f, (float)j1, (float)k);
         float f2 = Mth.m_14179_(f, (float)k1, (float)l);
         float f3 = Mth.m_14179_(f, (float)l1, (float)i1);
         f_109010_ = f1 / 255.0F;
         f_109011_ = f2 / 255.0F;
         f_109012_ = f3 / 255.0F;
         if (f_109013_ != j) {
            f_109013_ = j;
            f_109014_ = Mth.m_14143_(f1) << 16 | Mth.m_14143_(f2) << 8 | Mth.m_14143_(f3);
            f_109015_ = i;
         }
      } else if (fogtype == FogType.LAVA) {
         f_109010_ = 0.6F;
         f_109011_ = 0.1F;
         f_109012_ = 0.0F;
         f_109015_ = -1L;
      } else if (fogtype == FogType.POWDER_SNOW) {
         f_109010_ = 0.623F;
         f_109011_ = 0.734F;
         f_109012_ = 0.785F;
         f_109015_ = -1L;
         RenderSystem.m_69424_(f_109010_, f_109011_, f_109012_, 0.0F);
      } else {
         float f4 = 0.25F + 0.75F * (float)p_109022_ / 32.0F;
         f4 = 1.0F - (float)Math.pow((double)f4, 0.25D);
         Vec3 vec3 = p_109021_.m_171660_(p_109019_.m_90583_(), p_109020_);
         float f5 = (float)vec3.f_82479_;
         float f7 = (float)vec3.f_82480_;
         float f9 = (float)vec3.f_82481_;
         float f10 = Mth.m_14036_(Mth.m_14089_(p_109021_.m_46942_(p_109020_) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeManager biomemanager = p_109021_.m_7062_();
         Vec3 vec31 = p_109019_.m_90583_().m_82492_(2.0D, 2.0D, 2.0D).m_82490_(0.25D);
         Vec3 vec32 = CubicSampler.m_130038_(vec31, (p_109033_, p_109034_, p_109035_) -> {
            return p_109021_.m_104583_().m_5927_(Vec3.m_82501_(biomemanager.m_47873_(p_109033_, p_109034_, p_109035_).m_47539_()), f10);
         });
         f_109010_ = (float)vec32.m_7096_();
         f_109011_ = (float)vec32.m_7098_();
         f_109012_ = (float)vec32.m_7094_();
         if (p_109022_ >= 4) {
            float f11 = Mth.m_14031_(p_109021_.m_46490_(p_109020_)) > 0.0F ? -1.0F : 1.0F;
            Vector3f vector3f = new Vector3f(f11, 0.0F, 0.0F);
            float f15 = p_109019_.m_90596_().m_122276_(vector3f);
            if (f15 < 0.0F) {
               f15 = 0.0F;
            }

            if (f15 > 0.0F) {
               float[] afloat = p_109021_.m_104583_().m_7518_(p_109021_.m_46942_(p_109020_), p_109020_);
               if (afloat != null) {
                  f15 = f15 * afloat[3];
                  f_109010_ = f_109010_ * (1.0F - f15) + afloat[0] * f15;
                  f_109011_ = f_109011_ * (1.0F - f15) + afloat[1] * f15;
                  f_109012_ = f_109012_ * (1.0F - f15) + afloat[2] * f15;
               }
            }
         }

         f_109010_ += (f5 - f_109010_) * f4;
         f_109011_ += (f7 - f_109011_) * f4;
         f_109012_ += (f9 - f_109012_) * f4;
         float f12 = p_109021_.m_46722_(p_109020_);
         if (f12 > 0.0F) {
            float f13 = 1.0F - f12 * 0.5F;
            float f16 = 1.0F - f12 * 0.4F;
            f_109010_ *= f13;
            f_109011_ *= f13;
            f_109012_ *= f16;
         }

         float f14 = p_109021_.m_46661_(p_109020_);
         if (f14 > 0.0F) {
            float f17 = 1.0F - f14 * 0.5F;
            f_109010_ *= f17;
            f_109011_ *= f17;
            f_109012_ *= f17;
         }

         f_109015_ = -1L;
      }

      double d0 = (p_109019_.m_90583_().f_82480_ - (double)p_109021_.m_141937_()) * p_109021_.m_6106_().m_104876_();
      if (p_109019_.m_90592_() instanceof LivingEntity && ((LivingEntity)p_109019_.m_90592_()).m_21023_(MobEffects.f_19610_)) {
         int i2 = ((LivingEntity)p_109019_.m_90592_()).m_21124_(MobEffects.f_19610_).m_19557_();
         if (i2 < 20) {
            d0 *= (double)(1.0F - (float)i2 / 20.0F);
         } else {
            d0 = 0.0D;
         }
      }

      if (d0 < 1.0D && fogtype != FogType.LAVA) {
         if (d0 < 0.0D) {
            d0 = 0.0D;
         }

         d0 = d0 * d0;
         f_109010_ = (float)((double)f_109010_ * d0);
         f_109011_ = (float)((double)f_109011_ * d0);
         f_109012_ = (float)((double)f_109012_ * d0);
      }

      if (p_109023_ > 0.0F) {
         f_109010_ = f_109010_ * (1.0F - p_109023_) + f_109010_ * 0.7F * p_109023_;
         f_109011_ = f_109011_ * (1.0F - p_109023_) + f_109011_ * 0.6F * p_109023_;
         f_109012_ = f_109012_ * (1.0F - p_109023_) + f_109012_ * 0.6F * p_109023_;
      }

      float f6;
      if (fogtype == FogType.WATER) {
         if (entity instanceof LocalPlayer) {
            f6 = ((LocalPlayer)entity).m_108639_();
         } else {
            f6 = 1.0F;
         }
      } else if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19611_)) {
         f6 = GameRenderer.m_109108_((LivingEntity)entity, p_109020_);
      } else {
         f6 = 0.0F;
      }

      if (f_109010_ != 0.0F && f_109011_ != 0.0F && f_109012_ != 0.0F) {
         float f8 = Math.min(1.0F / f_109010_, Math.min(1.0F / f_109011_, 1.0F / f_109012_));
         // Forge: fix MC-4647 and MC-10480
         if (Float.isInfinite(f8)) f8 = Math.nextAfter(f8, 0.0);
         f_109010_ = f_109010_ * (1.0F - f6) + f_109010_ * f8 * f6;
         f_109011_ = f_109011_ * (1.0F - f6) + f_109011_ * f8 * f6;
         f_109012_ = f_109012_ * (1.0F - f6) + f_109012_ * f8 * f6;
      }

      net.minecraftforge.client.event.EntityViewRenderEvent.FogColors event = new net.minecraftforge.client.event.EntityViewRenderEvent.FogColors(p_109019_, p_109020_, f_109010_, f_109011_, f_109012_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);

      f_109010_ = event.getRed();
      f_109011_ = event.getGreen();
      f_109012_ = event.getBlue();

      RenderSystem.m_69424_(f_109010_, f_109011_, f_109012_, 0.0F);
   }

   public static void m_109017_() {
      RenderSystem.m_157445_(Float.MAX_VALUE);
   }
   @Deprecated // FORGE: Pass in partialTicks
   public static void m_109024_(Camera p_109025_, FogRenderer.FogMode p_109026_, float p_109027_, boolean p_109028_) {
      setupFog(p_109025_, p_109026_, p_109027_, p_109028_, 0);
   }

   public static void setupFog(Camera p_109025_, FogRenderer.FogMode p_109026_, float p_109027_, boolean p_109028_, float partialTicks) {
      FogType fogtype = p_109025_.m_167685_();
      Entity entity = p_109025_.m_90592_();
      // TODO
      float hook = net.minecraftforge.client.ForgeHooksClient.getFogDensity(p_109026_, p_109025_, partialTicks, 0.1F);
      if (hook >= 0) {
         RenderSystem.m_157445_(-8.0F);
         RenderSystem.m_157443_(hook * 0.5F);
      } else
      if (fogtype == FogType.WATER) {
         float f = 192.0F;
         if (entity instanceof LocalPlayer) {
            LocalPlayer localplayer = (LocalPlayer)entity;
            f *= Math.max(0.25F, localplayer.m_108639_());
            Biome biome = localplayer.f_19853_.m_46857_(localplayer.m_142538_());
            if (biome.m_47567_() == Biome.BiomeCategory.SWAMP) {
               f *= 0.85F;
            }
         }

         RenderSystem.m_157445_(-8.0F);
         RenderSystem.m_157443_(f * 0.5F);
      } else {
         float f2;
         float f3;
         if (fogtype == FogType.LAVA) {
            if (entity.m_5833_()) {
               f2 = -8.0F;
               f3 = p_109027_ * 0.5F;
            } else if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19607_)) {
               f2 = 0.0F;
               f3 = 3.0F;
            } else {
               f2 = 0.25F;
               f3 = 1.0F;
            }
         } else if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19610_)) {
            int i = ((LivingEntity)entity).m_21124_(MobEffects.f_19610_).m_19557_();
            float f1 = Mth.m_14179_(Math.min(1.0F, (float)i / 20.0F), p_109027_, 5.0F);
            if (p_109026_ == FogRenderer.FogMode.FOG_SKY) {
               f2 = 0.0F;
               f3 = f1 * 0.8F;
            } else {
               f2 = f1 * 0.25F;
               f3 = f1;
            }
         } else if (fogtype == FogType.POWDER_SNOW) {
            if (entity.m_5833_()) {
               f2 = -8.0F;
               f3 = p_109027_ * 0.5F;
            } else {
               f2 = 0.0F;
               f3 = 2.0F;
            }
         } else if (p_109028_) {
            f2 = p_109027_ * 0.05F;
            f3 = Math.min(p_109027_, 192.0F) * 0.5F;
         } else if (p_109026_ == FogRenderer.FogMode.FOG_SKY) {
            f2 = 0.0F;
            f3 = p_109027_;
         } else {
            f2 = p_109027_ * 0.75F;
            f3 = p_109027_;
         }

         RenderSystem.m_157445_(f2);
         RenderSystem.m_157443_(f3);
         net.minecraftforge.client.ForgeHooksClient.onFogRender(p_109026_, p_109025_, partialTicks, f3);
      }

   }

   public static void m_109036_() {
      RenderSystem.m_157434_(f_109010_, f_109011_, f_109012_);
   }

   @OnlyIn(Dist.CLIENT)
   public static enum FogMode {
      FOG_SKY,
      FOG_TERRAIN;
   }
}
