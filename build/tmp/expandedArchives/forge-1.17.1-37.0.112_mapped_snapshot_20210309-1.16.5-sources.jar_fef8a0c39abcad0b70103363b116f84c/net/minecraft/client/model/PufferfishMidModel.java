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
public class PufferfishMidModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170838_;
   private final ModelPart f_170839_;
   private final ModelPart f_170840_;

   public PufferfishMidModel(ModelPart p_170842_) {
      this.f_170838_ = p_170842_;
      this.f_170839_ = p_170842_.m_171324_("left_blue_fin");
      this.f_170840_ = p_170842_.m_171324_("right_blue_fin");
   }

   public static LayerDefinition m_170843_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 22;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(12, 22).m_171481_(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("right_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171419_(-2.5F, 17.0F, -1.5F));
      partdefinition.m_171599_("left_blue_fin", CubeListBuilder.m_171558_().m_171514_(24, 3).m_171481_(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171419_(2.5F, 17.0F, -1.5F));
      partdefinition.m_171599_("top_front_fin", CubeListBuilder.m_171558_().m_171514_(15, 16).m_171481_(-2.5F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F), PartPose.m_171423_(0.0F, 17.0F, -2.5F, ((float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("top_back_fin", CubeListBuilder.m_171558_().m_171514_(10, 16).m_171481_(-2.5F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F), PartPose.m_171423_(0.0F, 17.0F, 2.5F, (-(float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_front_fin", CubeListBuilder.m_171558_().m_171514_(8, 16).m_171481_(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.m_171423_(-2.5F, 22.0F, -2.5F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("right_back_fin", CubeListBuilder.m_171558_().m_171514_(8, 16).m_171481_(-1.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.m_171423_(-2.5F, 22.0F, 2.5F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_back_fin", CubeListBuilder.m_171558_().m_171514_(4, 16).m_171481_(0.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.m_171423_(2.5F, 22.0F, 2.5F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_front_fin", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(0.0F, -5.0F, 0.0F, 1.0F, 5.0F, 1.0F), PartPose.m_171423_(2.5F, 22.0F, -2.5F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("bottom_back_fin", CubeListBuilder.m_171558_().m_171514_(8, 22).m_171481_(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F), PartPose.m_171423_(0.5F, 22.0F, 2.5F, ((float)Math.PI / 4F), 0.0F, 0.0F));
      partdefinition.m_171599_("bottom_front_fin", CubeListBuilder.m_171558_().m_171514_(17, 21).m_171481_(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 1.0F), PartPose.m_171423_(0.0F, 22.0F, -2.5F, (-(float)Math.PI / 4F), 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170838_;
   }

   public void m_6973_(T p_103471_, float p_103472_, float p_103473_, float p_103474_, float p_103475_, float p_103476_) {
      this.f_170840_.f_104205_ = -0.2F + 0.4F * Mth.m_14031_(p_103474_ * 0.2F);
      this.f_170839_.f_104205_ = 0.2F - 0.4F * Mth.m_14031_(p_103474_ * 0.2F);
   }
}