package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeashKnotRenderer extends EntityRenderer<LeashFenceKnotEntity> {
   private static final ResourceLocation f_115229_ = new ResourceLocation("textures/entity/lead_knot.png");
   private final LeashKnotModel<LeashFenceKnotEntity> f_115230_;

   public LeashKnotRenderer(EntityRendererProvider.Context p_174284_) {
      super(p_174284_);
      this.f_115230_ = new LeashKnotModel<>(p_174284_.m_174023_(ModelLayers.f_171193_));
   }

   public void m_7392_(LeashFenceKnotEntity p_115246_, float p_115247_, float p_115248_, PoseStack p_115249_, MultiBufferSource p_115250_, int p_115251_) {
      p_115249_.m_85836_();
      p_115249_.m_85841_(-1.0F, -1.0F, 1.0F);
      this.f_115230_.m_6973_(p_115246_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer vertexconsumer = p_115250_.m_6299_(this.f_115230_.m_103119_(f_115229_));
      this.f_115230_.m_7695_(p_115249_, vertexconsumer, p_115251_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_115249_.m_85849_();
      super.m_7392_(p_115246_, p_115247_, p_115248_, p_115249_, p_115250_, p_115251_);
   }

   public ResourceLocation m_5478_(LeashFenceKnotEntity p_115244_) {
      return f_115229_;
   }
}