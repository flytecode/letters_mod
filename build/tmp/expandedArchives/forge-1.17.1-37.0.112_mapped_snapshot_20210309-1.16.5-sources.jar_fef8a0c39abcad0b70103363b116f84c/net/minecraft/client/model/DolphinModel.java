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
public class DolphinModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170528_;
   private final ModelPart f_102469_;
   private final ModelPart f_102470_;
   private final ModelPart f_102471_;

   public DolphinModel(ModelPart p_170530_) {
      this.f_170528_ = p_170530_;
      this.f_102469_ = p_170530_.m_171324_("body");
      this.f_102470_ = this.f_102469_.m_171324_("tail");
      this.f_102471_ = this.f_102470_.m_171324_("tail_fin");
   }

   public static LayerDefinition m_170531_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = 18.0F;
      float f1 = -8.0F;
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171481_(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F), PartPose.m_171419_(0.0F, 22.0F, -5.0F));
      partdefinition1.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(51, 0).m_171481_(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F), PartPose.m_171430_(((float)Math.PI / 3F), 0.0F, 0.0F));
      partdefinition1.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(48, 20).m_171480_().m_171481_(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F), PartPose.m_171423_(2.0F, -2.0F, 4.0F, ((float)Math.PI / 3F), 0.0F, 2.0943952F));
      partdefinition1.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(48, 20).m_171481_(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F), PartPose.m_171423_(-2.0F, -2.0F, 4.0F, ((float)Math.PI / 3F), 0.0F, -2.0943952F));
      PartDefinition partdefinition2 = partdefinition1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F), PartPose.m_171423_(0.0F, -2.5F, 11.0F, -0.10471976F, 0.0F, 0.0F));
      partdefinition2.m_171599_("tail_fin", CubeListBuilder.m_171558_().m_171514_(19, 20).m_171481_(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F), PartPose.m_171419_(0.0F, 0.0F, 9.0F));
      PartDefinition partdefinition3 = partdefinition1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F), PartPose.m_171419_(0.0F, -4.0F, -3.0F));
      partdefinition3.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_170528_;
   }

   public void m_6973_(T p_102475_, float p_102476_, float p_102477_, float p_102478_, float p_102479_, float p_102480_) {
      this.f_102469_.f_104203_ = p_102480_ * ((float)Math.PI / 180F);
      this.f_102469_.f_104204_ = p_102479_ * ((float)Math.PI / 180F);
      if (p_102475_.m_20184_().m_165925_() > 1.0E-7D) {
         this.f_102469_.f_104203_ += -0.05F - 0.05F * Mth.m_14089_(p_102478_ * 0.3F);
         this.f_102470_.f_104203_ = -0.1F * Mth.m_14089_(p_102478_ * 0.3F);
         this.f_102471_.f_104203_ = -0.2F * Mth.m_14089_(p_102478_ * 0.3F);
      }

   }
}