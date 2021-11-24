package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigModel<T extends Entity> extends QuadrupedModel<T> {
   public PigModel(ModelPart p_170799_) {
      super(p_170799_, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition m_170800_(CubeDeformation p_170801_) {
      MeshDefinition meshdefinition = QuadrupedModel.m_170864_(6, p_170801_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, p_170801_).m_171514_(16, 16).m_171488_(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, p_170801_), PartPose.m_171419_(0.0F, 12.0F, -6.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }
}