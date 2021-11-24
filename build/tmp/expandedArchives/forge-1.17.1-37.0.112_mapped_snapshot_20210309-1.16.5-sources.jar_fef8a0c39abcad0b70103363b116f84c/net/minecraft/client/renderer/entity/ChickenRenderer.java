package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenRenderer extends MobRenderer<Chicken, ChickenModel<Chicken>> {
   private static final ResourceLocation f_113988_ = new ResourceLocation("textures/entity/chicken.png");

   public ChickenRenderer(EntityRendererProvider.Context p_173952_) {
      super(p_173952_, new ChickenModel<>(p_173952_.m_174023_(ModelLayers.f_171277_)), 0.3F);
   }

   public ResourceLocation m_5478_(Chicken p_113998_) {
      return f_113988_;
   }

   protected float m_6930_(Chicken p_114000_, float p_114001_) {
      float f = Mth.m_14179_(p_114001_, p_114000_.f_28229_, p_114000_.f_28226_);
      float f1 = Mth.m_14179_(p_114001_, p_114000_.f_28228_, p_114000_.f_28227_);
      return (Mth.m_14031_(f) + 1.0F) * f1;
   }
}