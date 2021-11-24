package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
   private static final ResourceLocation f_117404_ = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final SheepFurModel<Sheep> f_117405_;

   public SheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> p_174533_, EntityModelSet p_174534_) {
      super(p_174533_);
      this.f_117405_ = new SheepFurModel<>(p_174534_.m_171103_(ModelLayers.f_171178_));
   }

   public void m_6494_(PoseStack p_117421_, MultiBufferSource p_117422_, int p_117423_, Sheep p_117424_, float p_117425_, float p_117426_, float p_117427_, float p_117428_, float p_117429_, float p_117430_) {
      if (!p_117424_.m_29875_()) {
         if (p_117424_.m_20145_()) {
            Minecraft minecraft = Minecraft.m_91087_();
            boolean flag = minecraft.m_91314_(p_117424_);
            if (flag) {
               this.m_117386_().m_102624_(this.f_117405_);
               this.f_117405_.m_6839_(p_117424_, p_117425_, p_117426_, p_117427_);
               this.f_117405_.m_6973_(p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_);
               VertexConsumer vertexconsumer = p_117422_.m_6299_(RenderType.m_110491_(f_117404_));
               this.f_117405_.m_7695_(p_117421_, vertexconsumer, p_117423_, LivingEntityRenderer.m_115338_(p_117424_, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }

         } else {
            float f;
            float f1;
            float f2;
            if (p_117424_.m_8077_() && "jeb_".equals(p_117424_.m_7755_().m_6111_())) {
               int i1 = 25;
               int i = p_117424_.f_19797_ / 25 + p_117424_.m_142049_();
               int j = DyeColor.values().length;
               int k = i % j;
               int l = (i + 1) % j;
               float f3 = ((float)(p_117424_.f_19797_ % 25) + p_117427_) / 25.0F;
               float[] afloat1 = Sheep.m_29829_(DyeColor.m_41053_(k));
               float[] afloat2 = Sheep.m_29829_(DyeColor.m_41053_(l));
               f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
               f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
               f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
               float[] afloat = Sheep.m_29829_(p_117424_.m_29874_());
               f = afloat[0];
               f1 = afloat[1];
               f2 = afloat[2];
            }

            m_117359_(this.m_117386_(), this.f_117405_, f_117404_, p_117421_, p_117422_, p_117423_, p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_, p_117427_, f, f1, f2);
         }
      }
   }
}