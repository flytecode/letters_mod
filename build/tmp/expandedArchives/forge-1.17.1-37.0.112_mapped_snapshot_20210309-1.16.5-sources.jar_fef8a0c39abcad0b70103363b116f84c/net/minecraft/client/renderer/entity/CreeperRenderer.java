package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperRenderer extends MobRenderer<Creeper, CreeperModel<Creeper>> {
   private static final ResourceLocation f_114030_ = new ResourceLocation("textures/entity/creeper/creeper.png");

   public CreeperRenderer(EntityRendererProvider.Context p_173958_) {
      super(p_173958_, new CreeperModel<>(p_173958_.m_174023_(ModelLayers.f_171285_)), 0.5F);
      this.m_115326_(new CreeperPowerLayer(this, p_173958_.m_174027_()));
   }

   protected void m_7546_(Creeper p_114046_, PoseStack p_114047_, float p_114048_) {
      float f = p_114046_.m_32320_(p_114048_);
      float f1 = 1.0F + Mth.m_14031_(f * 100.0F) * f * 0.01F;
      f = Mth.m_14036_(f, 0.0F, 1.0F);
      f = f * f;
      f = f * f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      p_114047_.m_85841_(f2, f3, f2);
   }

   protected float m_6931_(Creeper p_114043_, float p_114044_) {
      float f = p_114043_.m_32320_(p_114044_);
      return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.m_14036_(f, 0.5F, 1.0F);
   }

   public ResourceLocation m_5478_(Creeper p_114041_) {
      return f_114030_;
   }
}