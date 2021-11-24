package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermiteRenderer extends MobRenderer<Endermite, EndermiteModel<Endermite>> {
   private static final ResourceLocation f_114345_ = new ResourceLocation("textures/entity/endermite.png");

   public EndermiteRenderer(EntityRendererProvider.Context p_173994_) {
      super(p_173994_, new EndermiteModel<>(p_173994_.m_174023_(ModelLayers.f_171143_)), 0.3F);
   }

   protected float m_6441_(Endermite p_114352_) {
      return 180.0F;
   }

   public ResourceLocation m_5478_(Endermite p_114354_) {
      return f_114345_;
   }
}