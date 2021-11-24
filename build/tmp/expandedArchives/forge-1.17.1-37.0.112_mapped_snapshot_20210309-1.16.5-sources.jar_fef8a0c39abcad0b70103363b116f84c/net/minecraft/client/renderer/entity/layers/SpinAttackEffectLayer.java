package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinAttackEffectLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
   public static final ResourceLocation f_117509_ = new ResourceLocation("textures/entity/trident_riptide.png");
   public static final String f_174538_ = "box";
   private final ModelPart f_117510_;

   public SpinAttackEffectLayer(RenderLayerParent<T, PlayerModel<T>> p_174540_, EntityModelSet p_174541_) {
      super(p_174540_);
      ModelPart modelpart = p_174541_.m_171103_(ModelLayers.f_171169_);
      this.f_117510_ = modelpart.m_171324_("box");
   }

   public static LayerDefinition m_174542_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("box", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6494_(PoseStack p_117526_, MultiBufferSource p_117527_, int p_117528_, T p_117529_, float p_117530_, float p_117531_, float p_117532_, float p_117533_, float p_117534_, float p_117535_) {
      if (p_117529_.m_21209_()) {
         VertexConsumer vertexconsumer = p_117527_.m_6299_(RenderType.m_110458_(f_117509_));

         for(int i = 0; i < 3; ++i) {
            p_117526_.m_85836_();
            float f = p_117533_ * (float)(-(45 + i * 5));
            p_117526_.m_85845_(Vector3f.f_122225_.m_122240_(f));
            float f1 = 0.75F * (float)i;
            p_117526_.m_85841_(f1, f1, f1);
            p_117526_.m_85837_(0.0D, (double)(-0.2F + 0.6F * (float)i), 0.0D);
            this.f_117510_.m_104301_(p_117526_, vertexconsumer, p_117528_, OverlayTexture.f_118083_);
            p_117526_.m_85849_();
         }

      }
   }
}