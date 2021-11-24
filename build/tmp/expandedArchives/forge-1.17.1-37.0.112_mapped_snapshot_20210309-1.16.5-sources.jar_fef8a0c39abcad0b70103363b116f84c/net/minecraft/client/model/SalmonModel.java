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
public class SalmonModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170892_ = "body_front";
   private static final String f_170893_ = "body_back";
   private final ModelPart f_170894_;
   private final ModelPart f_103633_;

   public SalmonModel(ModelPart p_170896_) {
      this.f_170894_ = p_170896_;
      this.f_103633_ = p_170896_.m_171324_("body_back");
   }

   public static LayerDefinition m_170897_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 20;
      PartDefinition partdefinition1 = partdefinition.m_171599_("body_front", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), PartPose.m_171419_(0.0F, 20.0F, 0.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("body_back", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), PartPose.m_171419_(0.0F, 20.0F, 8.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171481_(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), PartPose.m_171419_(0.0F, 20.0F, 0.0F));
      partdefinition2.m_171599_("back_fin", CubeListBuilder.m_171558_().m_171514_(20, 10).m_171481_(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F), PartPose.m_171419_(0.0F, 0.0F, 8.0F));
      partdefinition1.m_171599_("top_front_fin", CubeListBuilder.m_171558_().m_171514_(2, 1).m_171481_(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F), PartPose.m_171419_(0.0F, -4.5F, 5.0F));
      partdefinition2.m_171599_("top_back_fin", CubeListBuilder.m_171558_().m_171514_(0, 2).m_171481_(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F), PartPose.m_171419_(0.0F, -4.5F, -1.0F));
      partdefinition.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(-4, 0).m_171481_(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171423_(-1.5F, 21.5F, 0.0F, 0.0F, 0.0F, (-(float)Math.PI / 4F)));
      partdefinition.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171423_(1.5F, 21.5F, 0.0F, 0.0F, 0.0F, ((float)Math.PI / 4F)));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170894_;
   }

   public void m_6973_(T p_103640_, float p_103641_, float p_103642_, float p_103643_, float p_103644_, float p_103645_) {
      float f = 1.0F;
      float f1 = 1.0F;
      if (!p_103640_.m_20069_()) {
         f = 1.3F;
         f1 = 1.7F;
      }

      this.f_103633_.f_104204_ = -f * 0.25F * Mth.m_14031_(f1 * 0.6F * p_103643_);
   }
}