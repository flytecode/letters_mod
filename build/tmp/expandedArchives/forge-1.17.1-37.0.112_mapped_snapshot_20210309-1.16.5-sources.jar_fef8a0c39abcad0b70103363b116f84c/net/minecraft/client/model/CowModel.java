package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowModel<T extends Entity> extends QuadrupedModel<T> {
   public CowModel(ModelPart p_170515_) {
      super(p_170515_, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition m_170516_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 12;
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F).m_171514_(22, 0).m_171517_("right_horn", -5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F).m_171514_(22, 0).m_171517_("left_horn", 4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171419_(0.0F, 4.0F, -8.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(18, 4).m_171481_(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F).m_171514_(52, 0).m_171481_(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F), PartPose.m_171423_(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 12.0F, 7.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(4.0F, 12.0F, 7.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-4.0F, 12.0F, -6.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(4.0F, 12.0F, -6.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_102450_() {
      return this.f_103492_;
   }
}