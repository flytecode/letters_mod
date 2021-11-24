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
public class EvokerFangsModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170550_ = "base";
   private static final String f_170551_ = "upper_jaw";
   private static final String f_170552_ = "lower_jaw";
   private final ModelPart f_170553_;
   private final ModelPart f_102626_;
   private final ModelPart f_102627_;
   private final ModelPart f_102628_;

   public EvokerFangsModel(ModelPart p_170555_) {
      this.f_170553_ = p_170555_;
      this.f_102626_ = p_170555_.m_171324_("base");
      this.f_102627_ = p_170555_.m_171324_("upper_jaw");
      this.f_102628_ = p_170555_.m_171324_("lower_jaw");
   }

   public static LayerDefinition m_170556_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F), PartPose.m_171419_(-5.0F, 24.0F, -5.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
      partdefinition.m_171599_("upper_jaw", cubelistbuilder, PartPose.m_171419_(1.5F, 24.0F, -4.0F));
      partdefinition.m_171599_("lower_jaw", cubelistbuilder, PartPose.m_171423_(-1.5F, 24.0F, 4.0F, 0.0F, (float)Math.PI, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_102632_, float p_102633_, float p_102634_, float p_102635_, float p_102636_, float p_102637_) {
      float f = p_102633_ * 2.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      f = 1.0F - f * f * f;
      this.f_102627_.f_104205_ = (float)Math.PI - f * 0.35F * (float)Math.PI;
      this.f_102628_.f_104205_ = (float)Math.PI + f * 0.35F * (float)Math.PI;
      float f1 = (p_102633_ + Mth.m_14031_(p_102633_ * 2.7F)) * 0.6F * 12.0F;
      this.f_102627_.f_104201_ = 24.0F - f1;
      this.f_102628_.f_104201_ = this.f_102627_.f_104201_;
      this.f_102626_.f_104201_ = this.f_102627_.f_104201_;
   }

   public ModelPart m_142109_() {
      return this.f_170553_;
   }
}