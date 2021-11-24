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
public class PufferfishBigModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170831_;
   private final ModelPart f_170832_;
   private final ModelPart f_170833_;

   public PufferfishBigModel(ModelPart p_170835_) {
      this.f_170831_ = p_170835_;
      this.f_170832_ = p_170835_.m_171324_("left_blue_fin");
      this.f_170833_ = p_170835_.m_171324_("right_blue_fin");
   }

   public static LayerDefinition m_170836_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 22;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("right_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-2.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F), PartPose.m_171419_(-4.0F, 15.0F, -2.0F));
      partdefinition.m_171599_("left_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 3).m_171481_(0.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F), PartPose.m_171419_(4.0F, 15.0F, -2.0F));
      partdefinition.m_171599_("top_front_fin", CubeListBuilder.m_171558_().m_171514_(15, 17).m_171481_(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F), PartPose.m_171423_(0.0F, 14.0F, -4.0F, ((float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("top_middle_fin", CubeListBuilder.m_171558_().m_171514_(14, 16).m_171481_(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 1.0F), PartPose.m_171419_(0.0F, 14.0F, 0.0F));
      partdefinition.m_171599_("top_back_fin", CubeListBuilder.m_171558_().m_171514_(23, 18).m_171481_(-4.0F, -1.0F, 0.0F, 8.0F, 1.0F, 0.0F), PartPose.m_171423_(0.0F, 14.0F, 4.0F, (-(float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_front_fin", CubeListBuilder.m_171558_().m_171514_(5, 17).m_171481_(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F), PartPose.m_171423_(-4.0F, 22.0F, -4.0F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_front_fin", CubeListBuilder.m_171558_().m_171514_(1, 17).m_171481_(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F), PartPose.m_171423_(4.0F, 22.0F, -4.0F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("bottom_front_fin", CubeListBuilder.m_171558_().m_171514_(15, 20).m_171481_(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F), PartPose.m_171423_(0.0F, 22.0F, -4.0F, (-(float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("bottom_middle_fin", CubeListBuilder.m_171558_().m_171514_(15, 20).m_171481_(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("bottom_back_fin", CubeListBuilder.m_171558_().m_171514_(15, 20).m_171481_(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 0.0F), PartPose.m_171423_(0.0F, 22.0F, 4.0F, ((float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_back_fin", CubeListBuilder.m_171558_().m_171514_(9, 17).m_171481_(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F), PartPose.m_171423_(-4.0F, 22.0F, 4.0F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_back_fin", CubeListBuilder.m_171558_().m_171514_(9, 17).m_171481_(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 0.0F), PartPose.m_171423_(4.0F, 22.0F, 4.0F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170831_;
   }

   public void m_6973_(T p_103451_, float p_103452_, float p_103453_, float p_103454_, float p_103455_, float p_103456_) {
      this.f_170833_.f_104205_ = -0.2F + 0.4F * Mth.m_14031_(p_103454_ * 0.2F);
      this.f_170832_.f_104205_ = 0.2F - 0.4F * Mth.m_14031_(p_103454_ * 0.2F);
   }
}