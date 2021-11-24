package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.GiantZombieModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Giant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantMobRenderer extends MobRenderer<Giant, HumanoidModel<Giant>> {
   private static final ResourceLocation f_114760_ = new ResourceLocation("textures/entity/zombie/zombie.png");
   private final float f_114761_;

   public GiantMobRenderer(EntityRendererProvider.Context p_174131_, float p_174132_) {
      super(p_174131_, new GiantZombieModel(p_174131_.m_174023_(ModelLayers.f_171151_)), 0.5F * p_174132_);
      this.f_114761_ = p_174132_;
      this.m_115326_(new ItemInHandLayer<>(this));
      this.m_115326_(new HumanoidArmorLayer<>(this, new GiantZombieModel(p_174131_.m_174023_(ModelLayers.f_171152_)), new GiantZombieModel(p_174131_.m_174023_(ModelLayers.f_171153_))));
   }

   protected void m_7546_(Giant p_114775_, PoseStack p_114776_, float p_114777_) {
      p_114776_.m_85841_(this.f_114761_, this.f_114761_, this.f_114761_);
   }

   public ResourceLocation m_5478_(Giant p_114773_) {
      return f_114760_;
   }
}