package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepRenderer extends MobRenderer<Sheep, SheepModel<Sheep>> {
   private static final ResourceLocation f_115833_ = new ResourceLocation("textures/entity/sheep/sheep.png");

   public SheepRenderer(EntityRendererProvider.Context p_174366_) {
      super(p_174366_, new SheepModel<>(p_174366_.m_174023_(ModelLayers.f_171177_)), 0.7F);
      this.m_115326_(new SheepFurLayer(this, p_174366_.m_174027_()));
   }

   public ResourceLocation m_5478_(Sheep p_115840_) {
      return f_115833_;
   }
}