package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronGolemModel<T extends IronGolem> extends HierarchicalModel<T> {
   private final ModelPart f_170691_;
   private final ModelPart f_102936_;
   private final ModelPart f_170692_;
   private final ModelPart f_170693_;
   private final ModelPart f_170694_;
   private final ModelPart f_170695_;

   public IronGolemModel(ModelPart p_170697_) {
      this.f_170691_ = p_170697_;
      this.f_102936_ = p_170697_.m_171324_("head");
      this.f_170692_ = p_170697_.m_171324_("right_arm");
      this.f_170693_ = p_170697_.m_171324_("left_arm");
      this.f_170694_ = p_170697_.m_171324_("right_leg");
      this.f_170695_ = p_170697_.m_171324_("left_leg");
   }

   public static LayerDefinition m_170698_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F).m_171514_(24, 0).m_171481_(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F), PartPose.m_171419_(0.0F, -7.0F, -2.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 40).m_171481_(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F).m_171514_(0, 70).m_171488_(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(60, 21).m_171481_(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(60, 58).m_171481_(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), PartPose.m_171419_(0.0F, -7.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(37, 0).m_171481_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), PartPose.m_171419_(-4.0F, 11.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(60, 0).m_171480_().m_171481_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), PartPose.m_171419_(5.0F, 11.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public ModelPart m_142109_() {
      return this.f_170691_;
   }

   public void m_6973_(T p_102962_, float p_102963_, float p_102964_, float p_102965_, float p_102966_, float p_102967_) {
      this.f_102936_.f_104204_ = p_102966_ * ((float)Math.PI / 180F);
      this.f_102936_.f_104203_ = p_102967_ * ((float)Math.PI / 180F);
      this.f_170694_.f_104203_ = -1.5F * Mth.m_14156_(p_102963_, 13.0F) * p_102964_;
      this.f_170695_.f_104203_ = 1.5F * Mth.m_14156_(p_102963_, 13.0F) * p_102964_;
      this.f_170694_.f_104204_ = 0.0F;
      this.f_170695_.f_104204_ = 0.0F;
   }

   public void m_6839_(T p_102957_, float p_102958_, float p_102959_, float p_102960_) {
      int i = p_102957_.m_28874_();
      if (i > 0) {
         this.f_170692_.f_104203_ = -2.0F + 1.5F * Mth.m_14156_((float)i - p_102960_, 10.0F);
         this.f_170693_.f_104203_ = -2.0F + 1.5F * Mth.m_14156_((float)i - p_102960_, 10.0F);
      } else {
         int j = p_102957_.m_28875_();
         if (j > 0) {
            this.f_170692_.f_104203_ = -0.8F + 0.025F * Mth.m_14156_((float)j, 70.0F);
            this.f_170693_.f_104203_ = 0.0F;
         } else {
            this.f_170692_.f_104203_ = (-0.2F + 1.5F * Mth.m_14156_(p_102958_, 13.0F)) * p_102959_;
            this.f_170693_.f_104203_ = (-0.2F - 1.5F * Mth.m_14156_(p_102958_, 13.0F)) * p_102959_;
         }
      }

   }

   public ModelPart m_102968_() {
      return this.f_170692_;
   }
}