package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomModel<T extends Phantom> extends HierarchicalModel<T> {
   private static final String f_170784_ = "tail_base";
   private static final String f_170785_ = "tail_tip";
   private final ModelPart f_170786_;
   private final ModelPart f_103315_;
   private final ModelPart f_103316_;
   private final ModelPart f_103317_;
   private final ModelPart f_103318_;
   private final ModelPart f_103319_;
   private final ModelPart f_103320_;

   public PhantomModel(ModelPart p_170788_) {
      this.f_170786_ = p_170788_;
      ModelPart modelpart = p_170788_.m_171324_("body");
      this.f_103319_ = modelpart.m_171324_("tail_base");
      this.f_103320_ = this.f_103319_.m_171324_("tail_tip");
      this.f_103315_ = modelpart.m_171324_("left_wing_base");
      this.f_103316_ = this.f_103315_.m_171324_("left_wing_tip");
      this.f_103317_ = modelpart.m_171324_("right_wing_base");
      this.f_103318_ = this.f_103317_.m_171324_("right_wing_tip");
   }

   public static LayerDefinition m_170789_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 8).m_171481_(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F), PartPose.m_171430_(-0.1F, 0.0F, 0.0F));
      PartDefinition partdefinition2 = partdefinition1.m_171599_("tail_base", CubeListBuilder.m_171558_().m_171514_(3, 20).m_171481_(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F), PartPose.m_171419_(0.0F, -2.0F, 1.0F));
      partdefinition2.m_171599_("tail_tip", CubeListBuilder.m_171558_().m_171514_(4, 29).m_171481_(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F), PartPose.m_171419_(0.0F, 0.5F, 6.0F));
      PartDefinition partdefinition3 = partdefinition1.m_171599_("left_wing_base", CubeListBuilder.m_171558_().m_171514_(23, 12).m_171481_(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F), PartPose.m_171423_(2.0F, -2.0F, -8.0F, 0.0F, 0.0F, 0.1F));
      partdefinition3.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171514_(16, 24).m_171481_(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F), PartPose.m_171423_(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F));
      PartDefinition partdefinition4 = partdefinition1.m_171599_("right_wing_base", CubeListBuilder.m_171558_().m_171514_(23, 12).m_171480_().m_171481_(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F), PartPose.m_171423_(-3.0F, -2.0F, -8.0F, 0.0F, 0.0F, -0.1F));
      partdefinition4.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171514_(16, 24).m_171480_().m_171481_(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F), PartPose.m_171423_(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1F));
      partdefinition1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F), PartPose.m_171423_(0.0F, 1.0F, -7.0F, 0.2F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_170786_;
   }

   public void m_6973_(T p_170791_, float p_170792_, float p_170793_, float p_170794_, float p_170795_, float p_170796_) {
      float f = ((float)p_170791_.m_149736_() + p_170794_) * 7.448451F * ((float)Math.PI / 180F);
      float f1 = 16.0F;
      this.f_103315_.f_104205_ = Mth.m_14089_(f) * 16.0F * ((float)Math.PI / 180F);
      this.f_103316_.f_104205_ = Mth.m_14089_(f) * 16.0F * ((float)Math.PI / 180F);
      this.f_103317_.f_104205_ = -this.f_103315_.f_104205_;
      this.f_103318_.f_104205_ = -this.f_103316_.f_104205_;
      this.f_103319_.f_104203_ = -(5.0F + Mth.m_14089_(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
      this.f_103320_.f_104203_ = -(5.0F + Mth.m_14089_(f * 2.0F) * 5.0F) * ((float)Math.PI / 180F);
   }
}