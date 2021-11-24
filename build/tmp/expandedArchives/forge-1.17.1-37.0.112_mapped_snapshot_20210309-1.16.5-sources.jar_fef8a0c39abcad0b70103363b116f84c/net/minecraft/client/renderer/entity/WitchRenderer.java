package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchRenderer extends MobRenderer<Witch, WitchModel<Witch>> {
   private static final ResourceLocation f_116378_ = new ResourceLocation("textures/entity/witch.png");

   public WitchRenderer(EntityRendererProvider.Context p_174443_) {
      super(p_174443_, new WitchModel<>(p_174443_.m_174023_(ModelLayers.f_171213_)), 0.5F);
      this.m_115326_(new WitchItemLayer<>(this));
   }

   public void m_7392_(Witch p_116412_, float p_116413_, float p_116414_, PoseStack p_116415_, MultiBufferSource p_116416_, int p_116417_) {
      this.f_115290_.m_104074_(!p_116412_.m_21205_().m_41619_());
      super.m_7392_(p_116412_, p_116413_, p_116414_, p_116415_, p_116416_, p_116417_);
   }

   public ResourceLocation m_5478_(Witch p_116410_) {
      return f_116378_;
   }

   protected void m_7546_(Witch p_116419_, PoseStack p_116420_, float p_116421_) {
      float f = 0.9375F;
      p_116420_.m_85841_(0.9375F, 0.9375F, 0.9375F);
   }
}