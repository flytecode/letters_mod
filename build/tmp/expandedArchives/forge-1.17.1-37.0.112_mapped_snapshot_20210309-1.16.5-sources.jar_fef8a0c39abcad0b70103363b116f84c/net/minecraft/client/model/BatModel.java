package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BatModel extends HierarchicalModel<Bat> {
   private final ModelPart f_170425_;
   private final ModelPart f_102184_;
   private final ModelPart f_102185_;
   private final ModelPart f_102186_;
   private final ModelPart f_102187_;
   private final ModelPart f_102188_;
   private final ModelPart f_102189_;

   public BatModel(ModelPart p_170427_) {
      this.f_170425_ = p_170427_;
      this.f_102184_ = p_170427_.m_171324_("head");
      this.f_102185_ = p_170427_.m_171324_("body");
      this.f_102186_ = this.f_102185_.m_171324_("right_wing");
      this.f_102188_ = this.f_102186_.m_171324_("right_wing_tip");
      this.f_102187_ = this.f_102185_.m_171324_("left_wing");
      this.f_102189_ = this.f_102187_.m_171324_("left_wing_tip");
   }

   public static LayerDefinition m_170428_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171480_().m_171481_(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), PartPose.f_171404_);
      PartDefinition partdefinition2 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F).m_171514_(0, 34).m_171481_(-5.0F, 16.0F, 0.0F, 10.0F, 6.0F, 1.0F), PartPose.f_171404_);
      PartDefinition partdefinition3 = partdefinition2.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(42, 0).m_171481_(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), PartPose.f_171404_);
      partdefinition3.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171514_(24, 16).m_171481_(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), PartPose.m_171419_(-12.0F, 1.0F, 1.5F));
      PartDefinition partdefinition4 = partdefinition2.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(42, 0).m_171480_().m_171481_(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), PartPose.f_171404_);
      partdefinition4.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171514_(24, 16).m_171480_().m_171481_(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), PartPose.m_171419_(12.0F, 1.0F, 1.5F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_170425_;
   }

   public void m_6973_(Bat p_102200_, float p_102201_, float p_102202_, float p_102203_, float p_102204_, float p_102205_) {
      if (p_102200_.m_27452_()) {
         this.f_102184_.f_104203_ = p_102205_ * ((float)Math.PI / 180F);
         this.f_102184_.f_104204_ = (float)Math.PI - p_102204_ * ((float)Math.PI / 180F);
         this.f_102184_.f_104205_ = (float)Math.PI;
         this.f_102184_.m_104227_(0.0F, -2.0F, 0.0F);
         this.f_102186_.m_104227_(-3.0F, 0.0F, 3.0F);
         this.f_102187_.m_104227_(3.0F, 0.0F, 3.0F);
         this.f_102185_.f_104203_ = (float)Math.PI;
         this.f_102186_.f_104203_ = -0.15707964F;
         this.f_102186_.f_104204_ = -1.2566371F;
         this.f_102188_.f_104204_ = -1.7278761F;
         this.f_102187_.f_104203_ = this.f_102186_.f_104203_;
         this.f_102187_.f_104204_ = -this.f_102186_.f_104204_;
         this.f_102189_.f_104204_ = -this.f_102188_.f_104204_;
      } else {
         this.f_102184_.f_104203_ = p_102205_ * ((float)Math.PI / 180F);
         this.f_102184_.f_104204_ = p_102204_ * ((float)Math.PI / 180F);
         this.f_102184_.f_104205_ = 0.0F;
         this.f_102184_.m_104227_(0.0F, 0.0F, 0.0F);
         this.f_102186_.m_104227_(0.0F, 0.0F, 0.0F);
         this.f_102187_.m_104227_(0.0F, 0.0F, 0.0F);
         this.f_102185_.f_104203_ = ((float)Math.PI / 4F) + Mth.m_14089_(p_102203_ * 0.1F) * 0.15F;
         this.f_102185_.f_104204_ = 0.0F;
         this.f_102186_.f_104204_ = Mth.m_14089_(p_102203_ * 74.48451F * ((float)Math.PI / 180F)) * (float)Math.PI * 0.25F;
         this.f_102187_.f_104204_ = -this.f_102186_.f_104204_;
         this.f_102188_.f_104204_ = this.f_102186_.f_104204_ * 0.5F;
         this.f_102189_.f_104204_ = -this.f_102186_.f_104204_ * 0.5F;
      }

   }
}