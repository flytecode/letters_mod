package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170968_ = "body0";
   private static final String f_170969_ = "body1";
   private static final String f_170970_ = "right_middle_front_leg";
   private static final String f_170971_ = "left_middle_front_leg";
   private static final String f_170972_ = "right_middle_hind_leg";
   private static final String f_170973_ = "left_middle_hind_leg";
   private final ModelPart f_170974_;
   private final ModelPart f_103852_;
   private final ModelPart f_170975_;
   private final ModelPart f_170976_;
   private final ModelPart f_170977_;
   private final ModelPart f_170978_;
   private final ModelPart f_170979_;
   private final ModelPart f_170980_;
   private final ModelPart f_170981_;
   private final ModelPart f_170982_;

   public SpiderModel(ModelPart p_170984_) {
      this.f_170974_ = p_170984_;
      this.f_103852_ = p_170984_.m_171324_("head");
      this.f_170975_ = p_170984_.m_171324_("right_hind_leg");
      this.f_170976_ = p_170984_.m_171324_("left_hind_leg");
      this.f_170977_ = p_170984_.m_171324_("right_middle_hind_leg");
      this.f_170978_ = p_170984_.m_171324_("left_middle_hind_leg");
      this.f_170979_ = p_170984_.m_171324_("right_middle_front_leg");
      this.f_170980_ = p_170984_.m_171324_("left_middle_front_leg");
      this.f_170981_ = p_170984_.m_171324_("right_front_leg");
      this.f_170982_ = p_170984_.m_171324_("left_front_leg");
   }

   public static LayerDefinition m_170985_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 15;
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 4).m_171481_(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), PartPose.m_171419_(0.0F, 15.0F, -3.0F));
      partdefinition.m_171599_("body0", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.m_171419_(0.0F, 15.0F, 0.0F));
      partdefinition.m_171599_("body1", CubeListBuilder.m_171558_().m_171514_(0, 12).m_171481_(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), PartPose.m_171419_(0.0F, 15.0F, 9.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(18, 0).m_171481_(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(18, 0).m_171481_(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 15.0F, 2.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder1, PartPose.m_171419_(4.0F, 15.0F, 2.0F));
      partdefinition.m_171599_("right_middle_hind_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 15.0F, 1.0F));
      partdefinition.m_171599_("left_middle_hind_leg", cubelistbuilder1, PartPose.m_171419_(4.0F, 15.0F, 1.0F));
      partdefinition.m_171599_("right_middle_front_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 15.0F, 0.0F));
      partdefinition.m_171599_("left_middle_front_leg", cubelistbuilder1, PartPose.m_171419_(4.0F, 15.0F, 0.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 15.0F, -1.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder1, PartPose.m_171419_(4.0F, 15.0F, -1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170974_;
   }

   public void m_6973_(T p_103866_, float p_103867_, float p_103868_, float p_103869_, float p_103870_, float p_103871_) {
      this.f_103852_.f_104204_ = p_103870_ * ((float)Math.PI / 180F);
      this.f_103852_.f_104203_ = p_103871_ * ((float)Math.PI / 180F);
      float f = ((float)Math.PI / 4F);
      this.f_170975_.f_104205_ = (-(float)Math.PI / 4F);
      this.f_170976_.f_104205_ = ((float)Math.PI / 4F);
      this.f_170977_.f_104205_ = -0.58119464F;
      this.f_170978_.f_104205_ = 0.58119464F;
      this.f_170979_.f_104205_ = -0.58119464F;
      this.f_170980_.f_104205_ = 0.58119464F;
      this.f_170981_.f_104205_ = (-(float)Math.PI / 4F);
      this.f_170982_.f_104205_ = ((float)Math.PI / 4F);
      float f1 = -0.0F;
      float f2 = ((float)Math.PI / 8F);
      this.f_170975_.f_104204_ = ((float)Math.PI / 4F);
      this.f_170976_.f_104204_ = (-(float)Math.PI / 4F);
      this.f_170977_.f_104204_ = ((float)Math.PI / 8F);
      this.f_170978_.f_104204_ = (-(float)Math.PI / 8F);
      this.f_170979_.f_104204_ = (-(float)Math.PI / 8F);
      this.f_170980_.f_104204_ = ((float)Math.PI / 8F);
      this.f_170981_.f_104204_ = (-(float)Math.PI / 4F);
      this.f_170982_.f_104204_ = ((float)Math.PI / 4F);
      float f3 = -(Mth.m_14089_(p_103867_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_103868_;
      float f4 = -(Mth.m_14089_(p_103867_ * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * p_103868_;
      float f5 = -(Mth.m_14089_(p_103867_ * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * p_103868_;
      float f6 = -(Mth.m_14089_(p_103867_ * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * p_103868_;
      float f7 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662F + 0.0F) * 0.4F) * p_103868_;
      float f8 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662F + (float)Math.PI) * 0.4F) * p_103868_;
      float f9 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * p_103868_;
      float f10 = Math.abs(Mth.m_14031_(p_103867_ * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * p_103868_;
      this.f_170975_.f_104204_ += f3;
      this.f_170976_.f_104204_ += -f3;
      this.f_170977_.f_104204_ += f4;
      this.f_170978_.f_104204_ += -f4;
      this.f_170979_.f_104204_ += f5;
      this.f_170980_.f_104204_ += -f5;
      this.f_170981_.f_104204_ += f6;
      this.f_170982_.f_104204_ += -f6;
      this.f_170975_.f_104205_ += f7;
      this.f_170976_.f_104205_ += -f7;
      this.f_170977_.f_104205_ += f8;
      this.f_170978_.f_104205_ += -f8;
      this.f_170979_.f_104205_ += f9;
      this.f_170980_.f_104205_ += -f9;
      this.f_170981_.f_104205_ += f10;
      this.f_170982_.f_104205_ += -f10;
   }
}