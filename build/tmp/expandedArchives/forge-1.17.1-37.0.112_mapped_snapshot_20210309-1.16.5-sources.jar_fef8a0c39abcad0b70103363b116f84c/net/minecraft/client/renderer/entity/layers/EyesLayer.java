package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EyesLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public EyesLayer(RenderLayerParent<T, M> p_116981_) {
      super(p_116981_);
   }

   public void m_6494_(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, T p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
      VertexConsumer vertexconsumer = p_116984_.m_6299_(this.m_5708_());
      this.m_117386_().m_7695_(p_116983_, vertexconsumer, 15728640, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public abstract RenderType m_5708_();
}