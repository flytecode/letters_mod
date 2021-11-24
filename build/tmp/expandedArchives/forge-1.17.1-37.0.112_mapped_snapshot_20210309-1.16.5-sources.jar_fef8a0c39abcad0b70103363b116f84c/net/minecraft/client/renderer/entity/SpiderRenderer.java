package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Spider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderRenderer<T extends Spider> extends MobRenderer<T, SpiderModel<T>> {
   private static final ResourceLocation f_116002_ = new ResourceLocation("textures/entity/spider/spider.png");

   public SpiderRenderer(EntityRendererProvider.Context p_174401_) {
      this(p_174401_, ModelLayers.f_171245_);
   }

   public SpiderRenderer(EntityRendererProvider.Context p_174403_, ModelLayerLocation p_174404_) {
      super(p_174403_, new SpiderModel<>(p_174403_.m_174023_(p_174404_)), 0.8F);
      this.m_115326_(new SpiderEyesLayer<>(this));
   }

   protected float m_6441_(T p_116011_) {
      return 180.0F;
   }

   public ResourceLocation m_5478_(T p_116009_) {
      return f_116002_;
   }
}