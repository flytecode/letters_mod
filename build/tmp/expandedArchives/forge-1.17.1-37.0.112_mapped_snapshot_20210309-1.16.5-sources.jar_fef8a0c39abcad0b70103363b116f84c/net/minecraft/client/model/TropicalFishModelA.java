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
public class TropicalFishModelA<T extends Entity> extends ColorableHierarchicalModel<T> {
   private final ModelPart f_171018_;
   private final ModelPart f_103953_;

   public TropicalFishModelA(ModelPart p_171020_) {
      this.f_171018_ = p_171020_;
      this.f_103953_ = p_171020_.m_171324_("tail");
   }

   public static LayerDefinition m_171021_(CubeDeformation p_171022_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 22;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, p_171022_), PartPose.m_171419_(0.0F, 22.0F, 0.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(22, -6).m_171488_(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 6.0F, p_171022_), PartPose.m_171419_(0.0F, 22.0F, 3.0F));
      partdefinition.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(2, 16).m_171488_(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, p_171022_), PartPose.m_171423_(-1.0F, 22.5F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(2, 12).m_171488_(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, p_171022_), PartPose.m_171423_(1.0F, 22.5F, 0.0F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(10, -5).m_171488_(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 6.0F, p_171022_), PartPose.m_171419_(0.0F, 20.5F, -3.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_171018_;
   }

   public void m_6973_(T p_103961_, float p_103962_, float p_103963_, float p_103964_, float p_103965_, float p_103966_) {
      float f = 1.0F;
      if (!p_103961_.m_20069_()) {
         f = 1.5F;
      }

      this.f_103953_.f_104204_ = -f * 0.45F * Mth.m_14031_(0.6F * p_103964_);
   }
}