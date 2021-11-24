package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DimensionSpecialEffects {
   private static final Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> f_108857_ = Util.m_137469_(new Object2ObjectArrayMap<>(), (p_108881_) -> {
      DimensionSpecialEffects.OverworldEffects dimensionspecialeffects$overworldeffects = new DimensionSpecialEffects.OverworldEffects();
      p_108881_.defaultReturnValue(dimensionspecialeffects$overworldeffects);
      p_108881_.put(DimensionType.f_63840_, dimensionspecialeffects$overworldeffects);
      p_108881_.put(DimensionType.f_63841_, new DimensionSpecialEffects.NetherEffects());
      p_108881_.put(DimensionType.f_63842_, new DimensionSpecialEffects.EndEffects());
   });
   private final float[] f_108858_ = new float[4];
   private final float f_108859_;
   private final boolean f_108860_;
   private final DimensionSpecialEffects.SkyType f_108861_;
   private final boolean f_108862_;
   private final boolean f_108863_;
   private net.minecraftforge.client.IWeatherRenderHandler weatherRenderHandler = null;
   private net.minecraftforge.client.IWeatherParticleRenderHandler weatherParticleRenderHandler = null;
   private net.minecraftforge.client.ISkyRenderHandler skyRenderHandler = null;
   private net.minecraftforge.client.ICloudRenderHandler cloudRenderHandler = null;

   public DimensionSpecialEffects(float p_108866_, boolean p_108867_, DimensionSpecialEffects.SkyType p_108868_, boolean p_108869_, boolean p_108870_) {
      this.f_108859_ = p_108866_;
      this.f_108860_ = p_108867_;
      this.f_108861_ = p_108868_;
      this.f_108862_ = p_108869_;
      this.f_108863_ = p_108870_;
   }

   public static DimensionSpecialEffects m_108876_(DimensionType p_108877_) {
      return f_108857_.get(p_108877_.m_63969_());
   }

   @Nullable
   public float[] m_7518_(float p_108872_, float p_108873_) {
      float f = 0.4F;
      float f1 = Mth.m_14089_(p_108872_ * ((float)Math.PI * 2F)) - 0.0F;
      float f2 = -0.0F;
      if (f1 >= -0.4F && f1 <= 0.4F) {
         float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
         float f4 = 1.0F - (1.0F - Mth.m_14031_(f3 * (float)Math.PI)) * 0.99F;
         f4 = f4 * f4;
         this.f_108858_[0] = f3 * 0.3F + 0.7F;
         this.f_108858_[1] = f3 * f3 * 0.7F + 0.2F;
         this.f_108858_[2] = f3 * f3 * 0.0F + 0.2F;
         this.f_108858_[3] = f4;
         return this.f_108858_;
      } else {
         return null;
      }
   }

   public float m_108871_() {
      return this.f_108859_;
   }

   public boolean m_108882_() {
      return this.f_108860_;
   }

   public abstract Vec3 m_5927_(Vec3 p_108878_, float p_108879_);

   public abstract boolean m_5781_(int p_108874_, int p_108875_);

   public DimensionSpecialEffects.SkyType m_108883_() {
      return this.f_108861_;
   }

   public boolean m_108884_() {
      return this.f_108862_;
   }

   public boolean m_108885_() {
      return this.f_108863_;
   }

   public void setWeatherRenderHandler(net.minecraftforge.client.IWeatherRenderHandler weatherRenderHandler) {
      this.weatherRenderHandler = weatherRenderHandler;
   }
   public void setWeatherParticleRenderHandler(net.minecraftforge.client.IWeatherParticleRenderHandler weatherParticleRenderHandler) {
      this.weatherParticleRenderHandler = weatherParticleRenderHandler;
   }
   public void setSkyRenderHandler(net.minecraftforge.client.ISkyRenderHandler skyRenderHandler) {
      this.skyRenderHandler = skyRenderHandler;
   }
   public void setCloudRenderHandler(net.minecraftforge.client.ICloudRenderHandler cloudRenderHandler) {
      this.cloudRenderHandler = cloudRenderHandler;
   }
   @Nullable
   public net.minecraftforge.client.ICloudRenderHandler getCloudRenderHandler() {
      return cloudRenderHandler;
   }
   @Nullable
   public net.minecraftforge.client.IWeatherRenderHandler getWeatherRenderHandler() {
      return weatherRenderHandler;
   }
   @Nullable
   public net.minecraftforge.client.IWeatherParticleRenderHandler getWeatherParticleRenderHandler() {
      return weatherParticleRenderHandler;
   }
   @Nullable
   public net.minecraftforge.client.ISkyRenderHandler getSkyRenderHandler() {
      return skyRenderHandler;
   }

   @OnlyIn(Dist.CLIENT)
   public static class EndEffects extends DimensionSpecialEffects {
      public EndEffects() {
         super(Float.NaN, false, DimensionSpecialEffects.SkyType.END, true, false);
      }

      public Vec3 m_5927_(Vec3 p_108894_, float p_108895_) {
         return p_108894_.m_82490_((double)0.15F);
      }

      public boolean m_5781_(int p_108891_, int p_108892_) {
         return false;
      }

      @Nullable
      public float[] m_7518_(float p_108888_, float p_108889_) {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class NetherEffects extends DimensionSpecialEffects {
      public NetherEffects() {
         super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, true);
      }

      public Vec3 m_5927_(Vec3 p_108901_, float p_108902_) {
         return p_108901_;
      }

      public boolean m_5781_(int p_108898_, int p_108899_) {
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class OverworldEffects extends DimensionSpecialEffects {
      public static final int f_172562_ = 128;

      public OverworldEffects() {
         super(128.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
      }

      public Vec3 m_5927_(Vec3 p_108908_, float p_108909_) {
         return p_108908_.m_82542_((double)(p_108909_ * 0.94F + 0.06F), (double)(p_108909_ * 0.94F + 0.06F), (double)(p_108909_ * 0.91F + 0.09F));
      }

      public boolean m_5781_(int p_108905_, int p_108906_) {
         return false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum SkyType {
      NONE,
      NORMAL,
      END;
   }
}
