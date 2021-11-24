package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoatModel<T extends Goat> extends QuadrupedModel<T> {
   public GoatModel(ModelPart p_170578_) {
      super(p_170578_, true, 19.0F, 1.0F, 2.5F, 2.0F, 24);
   }

   public static LayerDefinition m_170593_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(2, 61).m_171517_("right ear", -6.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F).m_171514_(2, 61).m_171480_().m_171517_("left ear", 2.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F).m_171514_(23, 52).m_171517_("goatee", -0.5F, -3.0F, -14.0F, 0.0F, 7.0F, 5.0F), PartPose.m_171419_(1.0F, 14.0F, 0.0F));
      partdefinition1.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(12, 55).m_171481_(-0.01F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition1.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(12, 55).m_171481_(-2.99F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(34, 46).m_171481_(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F), PartPose.m_171423_(0.0F, -8.0F, -8.0F, 0.9599F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-4.0F, -17.0F, -7.0F, 9.0F, 11.0F, 16.0F).m_171514_(0, 28).m_171481_(-5.0F, -18.0F, -8.0F, 11.0F, 14.0F, 11.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(36, 29).m_171481_(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.m_171419_(1.0F, 14.0F, 4.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(49, 29).m_171481_(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.m_171419_(-3.0F, 14.0F, 4.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(49, 2).m_171481_(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.m_171419_(1.0F, 14.0F, -6.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(35, 2).m_171481_(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.m_171419_(-3.0F, 14.0F, -6.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T p_170587_, float p_170588_, float p_170589_, float p_170590_, float p_170591_, float p_170592_) {
      this.f_103492_.m_171324_("left_horn").f_104207_ = !p_170587_.m_6162_();
      this.f_103492_.m_171324_("right_horn").f_104207_ = !p_170587_.m_6162_();
      super.m_6973_(p_170587_, p_170588_, p_170589_, p_170590_, p_170591_, p_170592_);
      float f = p_170587_.m_149398_();
      if (f != 0.0F) {
         this.f_103492_.f_104203_ = f;
      }

   }
}