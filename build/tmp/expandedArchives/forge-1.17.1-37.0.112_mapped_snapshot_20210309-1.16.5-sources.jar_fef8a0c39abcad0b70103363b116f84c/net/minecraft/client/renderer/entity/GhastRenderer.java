package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhastRenderer extends MobRenderer<Ghast, GhastModel<Ghast>> {
   private static final ResourceLocation f_114743_ = new ResourceLocation("textures/entity/ghast/ghast.png");
   private static final ResourceLocation f_114744_ = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

   public GhastRenderer(EntityRendererProvider.Context p_174129_) {
      super(p_174129_, new GhastModel<>(p_174129_.m_174023_(ModelLayers.f_171150_)), 1.5F);
   }

   public ResourceLocation m_5478_(Ghast p_114755_) {
      return p_114755_.m_32756_() ? f_114744_ : f_114743_;
   }

   protected void m_7546_(Ghast p_114757_, PoseStack p_114758_, float p_114759_) {
      float f = 1.0F;
      float f1 = 4.5F;
      float f2 = 4.5F;
      p_114758_.m_85841_(4.5F, 4.5F, 4.5F);
   }
}