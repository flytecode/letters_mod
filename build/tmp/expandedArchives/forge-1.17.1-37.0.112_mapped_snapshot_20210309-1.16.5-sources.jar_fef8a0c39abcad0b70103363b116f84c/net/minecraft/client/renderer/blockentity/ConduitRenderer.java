package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConduitRenderer implements BlockEntityRenderer<ConduitBlockEntity> {
   public static final Material f_112378_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/base"));
   public static final Material f_112379_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/cage"));
   public static final Material f_112380_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/wind"));
   public static final Material f_112381_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/wind_vertical"));
   public static final Material f_112382_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/open_eye"));
   public static final Material f_112383_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/closed_eye"));
   private final ModelPart f_112384_;
   private final ModelPart f_112385_;
   private final ModelPart f_112386_;
   private final ModelPart f_112387_;
   private final BlockEntityRenderDispatcher f_173611_;

   public ConduitRenderer(BlockEntityRendererProvider.Context p_173613_) {
      this.f_173611_ = p_173613_.m_173581_();
      this.f_112384_ = p_173613_.m_173582_(ModelLayers.f_171281_);
      this.f_112385_ = p_173613_.m_173582_(ModelLayers.f_171283_);
      this.f_112386_ = p_173613_.m_173582_(ModelLayers.f_171282_);
      this.f_112387_ = p_173613_.m_173582_(ModelLayers.f_171280_);
   }

   public static LayerDefinition m_173614_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("eye", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 16, 16);
   }

   public static LayerDefinition m_173615_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("wind", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public static LayerDefinition m_173616_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public static LayerDefinition m_173617_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 32, 16);
   }

   public void m_6922_(ConduitBlockEntity p_112399_, float p_112400_, PoseStack p_112401_, MultiBufferSource p_112402_, int p_112403_, int p_112404_) {
      float f = (float)p_112399_.f_59183_ + p_112400_;
      if (!p_112399_.m_59216_()) {
         float f5 = p_112399_.m_59197_(0.0F);
         VertexConsumer vertexconsumer1 = f_112378_.m_119194_(p_112402_, RenderType::m_110446_);
         p_112401_.m_85836_();
         p_112401_.m_85837_(0.5D, 0.5D, 0.5D);
         p_112401_.m_85845_(Vector3f.f_122225_.m_122240_(f5));
         this.f_112386_.m_104301_(p_112401_, vertexconsumer1, p_112403_, p_112404_);
         p_112401_.m_85849_();
      } else {
         float f1 = p_112399_.m_59197_(p_112400_) * (180F / (float)Math.PI);
         float f2 = Mth.m_14031_(f * 0.1F) / 2.0F + 0.5F;
         f2 = f2 * f2 + f2;
         p_112401_.m_85836_();
         p_112401_.m_85837_(0.5D, (double)(0.3F + f2 * 0.2F), 0.5D);
         Vector3f vector3f = new Vector3f(0.5F, 1.0F, 0.5F);
         vector3f.m_122278_();
         p_112401_.m_85845_(vector3f.m_122240_(f1));
         this.f_112387_.m_104301_(p_112401_, f_112379_.m_119194_(p_112402_, RenderType::m_110458_), p_112403_, p_112404_);
         p_112401_.m_85849_();
         int i = p_112399_.f_59183_ / 66 % 3;
         p_112401_.m_85836_();
         p_112401_.m_85837_(0.5D, 0.5D, 0.5D);
         if (i == 1) {
            p_112401_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         } else if (i == 2) {
            p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
         }

         VertexConsumer vertexconsumer = (i == 1 ? f_112381_ : f_112380_).m_119194_(p_112402_, RenderType::m_110458_);
         this.f_112385_.m_104301_(p_112401_, vertexconsumer, p_112403_, p_112404_);
         p_112401_.m_85849_();
         p_112401_.m_85836_();
         p_112401_.m_85837_(0.5D, 0.5D, 0.5D);
         p_112401_.m_85841_(0.875F, 0.875F, 0.875F);
         p_112401_.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
         p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         this.f_112385_.m_104301_(p_112401_, vertexconsumer, p_112403_, p_112404_);
         p_112401_.m_85849_();
         Camera camera = this.f_173611_.f_112249_;
         p_112401_.m_85836_();
         p_112401_.m_85837_(0.5D, (double)(0.3F + f2 * 0.2F), 0.5D);
         p_112401_.m_85841_(0.5F, 0.5F, 0.5F);
         float f3 = -camera.m_90590_();
         p_112401_.m_85845_(Vector3f.f_122225_.m_122240_(f3));
         p_112401_.m_85845_(Vector3f.f_122223_.m_122240_(camera.m_90589_()));
         p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         float f4 = 1.3333334F;
         p_112401_.m_85841_(1.3333334F, 1.3333334F, 1.3333334F);
         this.f_112384_.m_104301_(p_112401_, (p_112399_.m_59217_() ? f_112382_ : f_112383_).m_119194_(p_112402_, RenderType::m_110458_), p_112403_, p_112404_);
         p_112401_.m_85849_();
      }
   }
}