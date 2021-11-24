package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.GoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoatRenderer extends MobRenderer<Goat, GoatModel<Goat>> {
   private static final ResourceLocation f_174150_ = new ResourceLocation("textures/entity/goat/goat.png");

   public GoatRenderer(EntityRendererProvider.Context p_174153_) {
      super(p_174153_, new GoatModel<>(p_174153_.m_174023_(ModelLayers.f_171182_)), 0.7F);
   }

   public ResourceLocation m_5478_(Goat p_174157_) {
      return f_174150_;
   }
}