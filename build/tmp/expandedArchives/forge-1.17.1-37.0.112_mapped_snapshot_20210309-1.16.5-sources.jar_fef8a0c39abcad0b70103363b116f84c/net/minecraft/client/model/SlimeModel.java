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
public class SlimeModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170953_;

   public SlimeModel(ModelPart p_170955_) {
      this.f_170953_ = p_170955_;
   }

   public static LayerDefinition m_170956_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public static LayerDefinition m_170958_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.f_171404_);
      partdefinition.m_171599_("right_eye", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.f_171404_);
      partdefinition.m_171599_("left_eye", CubeListBuilder.m_171558_().m_171514_(32, 4).m_171481_(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.f_171404_);
      partdefinition.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(32, 8).m_171481_(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_103831_, float p_103832_, float p_103833_, float p_103834_, float p_103835_, float p_103836_) {
   }

   public ModelPart m_142109_() {
      return this.f_170953_;
   }
}