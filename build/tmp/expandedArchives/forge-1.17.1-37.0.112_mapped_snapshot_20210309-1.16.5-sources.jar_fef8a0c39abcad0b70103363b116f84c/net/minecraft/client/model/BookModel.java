package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookModel extends Model {
   private static final String f_170469_ = "left_pages";
   private static final String f_170470_ = "right_pages";
   private static final String f_170471_ = "flip_page1";
   private static final String f_170472_ = "flip_page2";
   private final ModelPart f_170473_;
   private final ModelPart f_102283_;
   private final ModelPart f_102284_;
   private final ModelPart f_102285_;
   private final ModelPart f_102286_;
   private final ModelPart f_102287_;
   private final ModelPart f_102288_;

   public BookModel(ModelPart p_170475_) {
      super(RenderType::m_110446_);
      this.f_170473_ = p_170475_;
      this.f_102283_ = p_170475_.m_171324_("left_lid");
      this.f_102284_ = p_170475_.m_171324_("right_lid");
      this.f_102285_ = p_170475_.m_171324_("left_pages");
      this.f_102286_ = p_170475_.m_171324_("right_pages");
      this.f_102287_ = p_170475_.m_171324_("flip_page1");
      this.f_102288_ = p_170475_.m_171324_("flip_page2");
   }

   public static LayerDefinition m_170476_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("left_lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.m_171419_(0.0F, 0.0F, -1.0F));
      partdefinition.m_171599_("right_lid", CubeListBuilder.m_171558_().m_171514_(16, 0).m_171481_(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.m_171419_(0.0F, 0.0F, 1.0F));
      partdefinition.m_171599_("seam", CubeListBuilder.m_171558_().m_171514_(12, 0).m_171481_(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F), PartPose.m_171430_(0.0F, ((float)Math.PI / 2F), 0.0F));
      partdefinition.m_171599_("left_pages", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171481_(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F), PartPose.f_171404_);
      partdefinition.m_171599_("right_pages", CubeListBuilder.m_171558_().m_171514_(12, 10).m_171481_(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F), PartPose.f_171404_);
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(24, 10).m_171481_(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      partdefinition.m_171599_("flip_page1", cubelistbuilder, PartPose.f_171404_);
      partdefinition.m_171599_("flip_page2", cubelistbuilder, PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_7695_(PoseStack p_102298_, VertexConsumer p_102299_, int p_102300_, int p_102301_, float p_102302_, float p_102303_, float p_102304_, float p_102305_) {
      this.m_102316_(p_102298_, p_102299_, p_102300_, p_102301_, p_102302_, p_102303_, p_102304_, p_102305_);
   }

   public void m_102316_(PoseStack p_102317_, VertexConsumer p_102318_, int p_102319_, int p_102320_, float p_102321_, float p_102322_, float p_102323_, float p_102324_) {
      this.f_170473_.m_104306_(p_102317_, p_102318_, p_102319_, p_102320_, p_102321_, p_102322_, p_102323_, p_102324_);
   }

   public void m_102292_(float p_102293_, float p_102294_, float p_102295_, float p_102296_) {
      float f = (Mth.m_14031_(p_102293_ * 0.02F) * 0.1F + 1.25F) * p_102296_;
      this.f_102283_.f_104204_ = (float)Math.PI + f;
      this.f_102284_.f_104204_ = -f;
      this.f_102285_.f_104204_ = f;
      this.f_102286_.f_104204_ = -f;
      this.f_102287_.f_104204_ = f - f * 2.0F * p_102294_;
      this.f_102288_.f_104204_ = f - f * 2.0F * p_102295_;
      this.f_102285_.f_104200_ = Mth.m_14031_(f);
      this.f_102286_.f_104200_ = Mth.m_14031_(f);
      this.f_102287_.f_104200_ = Mth.m_14031_(f);
      this.f_102288_.f_104200_ = Mth.m_14031_(f);
   }
}