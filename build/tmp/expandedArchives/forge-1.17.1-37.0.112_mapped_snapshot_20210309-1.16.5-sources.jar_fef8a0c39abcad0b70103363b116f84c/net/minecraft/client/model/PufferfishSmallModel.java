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
public class PufferfishSmallModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170845_;
   private final ModelPart f_170846_;
   private final ModelPart f_170847_;

   public PufferfishSmallModel(ModelPart p_170849_) {
      this.f_170845_ = p_170849_;
      this.f_170846_ = p_170849_.m_171324_("left_fin");
      this.f_170847_ = p_170849_.m_171324_("right_fin");
   }

   public static LayerDefinition m_170850_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 23;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 27).m_171481_(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      partdefinition.m_171599_("right_eye", CubeListBuilder.m_171558_().m_171514_(24, 6).m_171481_(-1.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F), PartPose.m_171419_(0.0F, 20.0F, 0.0F));
      partdefinition.m_171599_("left_eye", CubeListBuilder.m_171558_().m_171514_(28, 6).m_171481_(0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F), PartPose.m_171419_(0.0F, 20.0F, 0.0F));
      partdefinition.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(-3, 0).m_171481_(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F), PartPose.m_171419_(0.0F, 22.0F, 1.5F));
      partdefinition.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171481_(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F), PartPose.m_171419_(-1.5F, 22.0F, -1.5F));
      partdefinition.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(25, 0).m_171481_(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F), PartPose.m_171419_(1.5F, 22.0F, -1.5F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170845_;
   }

   public void m_6973_(T p_103486_, float p_103487_, float p_103488_, float p_103489_, float p_103490_, float p_103491_) {
      this.f_170847_.f_104205_ = -0.2F + 0.4F * Mth.m_14031_(p_103489_ * 0.2F);
      this.f_170846_.f_104205_ = 0.2F - 0.4F * Mth.m_14031_(p_103489_ * 0.2F);
   }
}