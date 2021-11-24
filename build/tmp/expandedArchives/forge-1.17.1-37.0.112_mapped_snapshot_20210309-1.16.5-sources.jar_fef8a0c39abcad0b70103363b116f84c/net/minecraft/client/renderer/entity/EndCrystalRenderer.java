package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndCrystalRenderer extends EntityRenderer<EndCrystal> {
   private static final ResourceLocation f_114132_ = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
   private static final RenderType f_114133_ = RenderType.m_110458_(f_114132_);
   private static final float f_114134_ = (float)Math.sin((Math.PI / 4D));
   private static final String f_173967_ = "glass";
   private static final String f_173968_ = "base";
   private final ModelPart f_114135_;
   private final ModelPart f_114136_;
   private final ModelPart f_114137_;

   public EndCrystalRenderer(EntityRendererProvider.Context p_173970_) {
      super(p_173970_);
      this.f_114477_ = 0.5F;
      ModelPart modelpart = p_173970_.m_174023_(ModelLayers.f_171145_);
      this.f_114136_ = modelpart.m_171324_("glass");
      this.f_114135_ = modelpart.m_171324_("cube");
      this.f_114137_ = modelpart.m_171324_("base");
   }

   public static LayerDefinition m_173971_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("glass", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      partdefinition.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      partdefinition.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_7392_(EndCrystal p_114162_, float p_114163_, float p_114164_, PoseStack p_114165_, MultiBufferSource p_114166_, int p_114167_) {
      p_114165_.m_85836_();
      float f = m_114158_(p_114162_, p_114164_);
      float f1 = ((float)p_114162_.f_31032_ + p_114164_) * 3.0F;
      VertexConsumer vertexconsumer = p_114166_.m_6299_(f_114133_);
      p_114165_.m_85836_();
      p_114165_.m_85841_(2.0F, 2.0F, 2.0F);
      p_114165_.m_85837_(0.0D, -0.5D, 0.0D);
      int i = OverlayTexture.f_118083_;
      if (p_114162_.m_31065_()) {
         this.f_114137_.m_104301_(p_114165_, vertexconsumer, p_114167_, i);
      }

      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      p_114165_.m_85837_(0.0D, (double)(1.5F + f / 2.0F), 0.0D);
      p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0F, f_114134_), 60.0F, true));
      this.f_114136_.m_104301_(p_114165_, vertexconsumer, p_114167_, i);
      float f2 = 0.875F;
      p_114165_.m_85841_(0.875F, 0.875F, 0.875F);
      p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0F, f_114134_), 60.0F, true));
      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      this.f_114136_.m_104301_(p_114165_, vertexconsumer, p_114167_, i);
      p_114165_.m_85841_(0.875F, 0.875F, 0.875F);
      p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0F, f_114134_), 60.0F, true));
      p_114165_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
      this.f_114135_.m_104301_(p_114165_, vertexconsumer, p_114167_, i);
      p_114165_.m_85849_();
      p_114165_.m_85849_();
      BlockPos blockpos = p_114162_.m_31064_();
      if (blockpos != null) {
         float f3 = (float)blockpos.m_123341_() + 0.5F;
         float f4 = (float)blockpos.m_123342_() + 0.5F;
         float f5 = (float)blockpos.m_123343_() + 0.5F;
         float f6 = (float)((double)f3 - p_114162_.m_20185_());
         float f7 = (float)((double)f4 - p_114162_.m_20186_());
         float f8 = (float)((double)f5 - p_114162_.m_20189_());
         p_114165_.m_85837_((double)f6, (double)f7, (double)f8);
         EnderDragonRenderer.m_114187_(-f6, -f7 + f, -f8, p_114164_, p_114162_.f_31032_, p_114165_, p_114166_, p_114167_);
      }

      super.m_7392_(p_114162_, p_114163_, p_114164_, p_114165_, p_114166_, p_114167_);
   }

   public static float m_114158_(EndCrystal p_114159_, float p_114160_) {
      float f = (float)p_114159_.f_31032_ + p_114160_;
      float f1 = Mth.m_14031_(f * 0.2F) / 2.0F + 0.5F;
      f1 = (f1 * f1 + f1) * 0.4F;
      return f1 - 1.4F;
   }

   public ResourceLocation m_5478_(EndCrystal p_114157_) {
      return f_114132_;
   }

   public boolean m_5523_(EndCrystal p_114169_, Frustum p_114170_, double p_114171_, double p_114172_, double p_114173_) {
      return super.m_5523_(p_114169_, p_114170_, p_114171_, p_114172_, p_114173_) || p_114169_.m_31064_() != null;
   }
}