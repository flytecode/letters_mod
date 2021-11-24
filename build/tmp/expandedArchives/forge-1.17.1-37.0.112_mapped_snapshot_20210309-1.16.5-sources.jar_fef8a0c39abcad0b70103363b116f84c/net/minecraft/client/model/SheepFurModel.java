package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepFurModel<T extends Sheep> extends QuadrupedModel<T> {
   private float f_103646_;

   public SheepFurModel(ModelPart p_170900_) {
      super(p_170900_, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition m_170901_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.m_171419_(0.0F, 6.0F, -8.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(28, 8).m_171488_(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.75F)), PartPose.m_171423_(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F));
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-3.0F, 12.0F, 7.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(3.0F, 12.0F, 7.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-3.0F, 12.0F, -5.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(3.0F, 12.0F, -5.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6839_(T p_103661_, float p_103662_, float p_103663_, float p_103664_) {
      super.m_6839_(p_103661_, p_103662_, p_103663_, p_103664_);
      this.f_103492_.f_104201_ = 6.0F + p_103661_.m_29880_(p_103664_) * 9.0F;
      this.f_103646_ = p_103661_.m_29882_(p_103664_);
   }

   public void m_6973_(T p_103666_, float p_103667_, float p_103668_, float p_103669_, float p_103670_, float p_103671_) {
      super.m_6973_(p_103666_, p_103667_, p_103668_, p_103669_, p_103670_, p_103671_);
      this.f_103492_.f_104203_ = this.f_103646_;
   }
}