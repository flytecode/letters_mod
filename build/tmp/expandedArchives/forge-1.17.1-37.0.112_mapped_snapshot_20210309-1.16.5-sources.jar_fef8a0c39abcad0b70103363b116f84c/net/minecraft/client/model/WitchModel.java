package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchModel<T extends Entity> extends VillagerModel<T> {
   private boolean f_104062_;

   public WitchModel(ModelPart p_171055_) {
      super(p_171055_);
   }

   public static LayerDefinition m_171056_() {
      MeshDefinition meshdefinition = VillagerModel.m_171052_();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.f_171404_);
      PartDefinition partdefinition2 = partdefinition1.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(0, 64).m_171481_(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F), PartPose.m_171419_(-5.0F, -10.03125F, -5.0F));
      PartDefinition partdefinition3 = partdefinition2.m_171599_("hat2", CubeListBuilder.m_171558_().m_171514_(0, 76).m_171481_(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F), PartPose.m_171423_(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F));
      PartDefinition partdefinition4 = partdefinition3.m_171599_("hat3", CubeListBuilder.m_171558_().m_171514_(0, 87).m_171481_(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F), PartPose.m_171423_(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F));
      partdefinition4.m_171599_("hat4", CubeListBuilder.m_171558_().m_171514_(0, 95).m_171488_(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.m_171423_(1.75F, -2.0F, 2.0F, -0.20943952F, 0.0F, 0.10471976F));
      PartDefinition partdefinition5 = partdefinition1.m_171597_("nose");
      partdefinition5.m_171599_("mole", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.m_171419_(0.0F, -2.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 128);
   }

   public void m_6973_(T p_104067_, float p_104068_, float p_104069_, float p_104070_, float p_104071_, float p_104072_) {
      super.m_6973_(p_104067_, p_104068_, p_104069_, p_104070_, p_104071_, p_104072_);
      this.f_104044_.m_104227_(0.0F, -2.0F, 0.0F);
      float f = 0.01F * (float)(p_104067_.m_142049_() % 10);
      this.f_104044_.f_104203_ = Mth.m_14031_((float)p_104067_.f_19797_ * f) * 4.5F * ((float)Math.PI / 180F);
      this.f_104044_.f_104204_ = 0.0F;
      this.f_104044_.f_104205_ = Mth.m_14089_((float)p_104067_.f_19797_ * f) * 2.5F * ((float)Math.PI / 180F);
      if (this.f_104062_) {
         this.f_104044_.m_104227_(0.0F, 1.0F, -1.5F);
         this.f_104044_.f_104203_ = -0.9F;
      }

   }

   public ModelPart m_104073_() {
      return this.f_104044_;
   }

   public void m_104074_(boolean p_104075_) {
      this.f_104062_ = p_104075_;
   }
}