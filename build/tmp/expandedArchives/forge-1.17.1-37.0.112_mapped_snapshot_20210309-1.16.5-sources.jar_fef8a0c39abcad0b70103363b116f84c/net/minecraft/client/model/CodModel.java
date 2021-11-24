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
public class CodModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170492_;
   private final ModelPart f_102405_;

   public CodModel(ModelPart p_170494_) {
      this.f_170492_ = p_170494_;
      this.f_102405_ = p_170494_.m_171324_("tail_fin");
   }

   public static LayerDefinition m_170495_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 22;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 7.0F), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(11, 0).m_171481_(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F), PartPose.m_171419_(0.0F, 22.0F, -3.0F));
      partdefinition.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171481_(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171423_(-1.0F, 23.0F, 0.0F, 0.0F, 0.0F, (-(float)Math.PI / 4F)));
      partdefinition.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(22, 4).m_171481_(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F), PartPose.m_171423_(1.0F, 23.0F, 0.0F, 0.0F, 0.0F, ((float)Math.PI / 4F)));
      partdefinition.m_171599_("tail_fin", CubeListBuilder.m_171558_().m_171514_(22, 3).m_171481_(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F), PartPose.m_171419_(0.0F, 22.0F, 7.0F));
      partdefinition.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(20, -6).m_171481_(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 6.0F), PartPose.m_171419_(0.0F, 20.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170492_;
   }

   public void m_6973_(T p_102409_, float p_102410_, float p_102411_, float p_102412_, float p_102413_, float p_102414_) {
      float f = 1.0F;
      if (!p_102409_.m_20069_()) {
         f = 1.5F;
      }

      this.f_102405_.f_104204_ = -f * 0.45F * Mth.m_14031_(0.6F * p_102412_);
   }
}