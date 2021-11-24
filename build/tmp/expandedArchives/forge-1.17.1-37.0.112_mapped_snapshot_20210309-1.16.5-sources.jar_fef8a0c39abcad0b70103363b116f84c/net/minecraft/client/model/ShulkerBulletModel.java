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
public class ShulkerBulletModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170913_ = "main";
   private final ModelPart f_170914_;
   private final ModelPart f_103712_;

   public ShulkerBulletModel(ModelPart p_170916_) {
      this.f_170914_ = p_170916_;
      this.f_103712_ = p_170916_.m_171324_("main");
   }

   public static LayerDefinition m_170917_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("main", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F).m_171514_(0, 10).m_171481_(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F).m_171514_(20, 0).m_171481_(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170914_;
   }

   public void m_6973_(T p_103716_, float p_103717_, float p_103718_, float p_103719_, float p_103720_, float p_103721_) {
      this.f_103712_.f_104204_ = p_103720_ * ((float)Math.PI / 180F);
      this.f_103712_.f_104203_ = p_103721_ * ((float)Math.PI / 180F);
   }
}