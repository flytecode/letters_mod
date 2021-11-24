package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PolarBearModel<T extends PolarBear> extends QuadrupedModel<T> {
   public PolarBearModel(ModelPart p_170829_) {
      super(p_170829_, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
   }

   public static LayerDefinition m_170830_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F).m_171514_(0, 44).m_171517_("mouth", -2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F).m_171514_(26, 0).m_171517_("right_ear", -4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F).m_171514_(26, 0).m_171480_().m_171517_("left_ear", 2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F), PartPose.m_171419_(0.0F, 10.0F, -16.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F).m_171514_(39, 0).m_171481_(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F), PartPose.m_171423_(-2.0F, 9.0F, 12.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      int i = 10;
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(50, 22).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-4.5F, 14.0F, 6.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(4.5F, 14.0F, 6.0F));
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(50, 40).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F);
      partdefinition.m_171599_("right_front_leg", cubelistbuilder1, PartPose.m_171419_(-3.5F, 14.0F, -8.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder1, PartPose.m_171419_(3.5F, 14.0F, -8.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void m_6973_(T p_103429_, float p_103430_, float p_103431_, float p_103432_, float p_103433_, float p_103434_) {
      super.m_6973_(p_103429_, p_103430_, p_103431_, p_103432_, p_103433_, p_103434_);
      float f = p_103432_ - (float)p_103429_.f_19797_;
      float f1 = p_103429_.m_29569_(f);
      f1 = f1 * f1;
      float f2 = 1.0F - f1;
      this.f_103493_.f_104203_ = ((float)Math.PI / 2F) - f1 * (float)Math.PI * 0.35F;
      this.f_103493_.f_104201_ = 9.0F * f2 + 11.0F * f1;
      this.f_170854_.f_104201_ = 14.0F * f2 - 6.0F * f1;
      this.f_170854_.f_104202_ = -8.0F * f2 - 4.0F * f1;
      this.f_170854_.f_104203_ -= f1 * (float)Math.PI * 0.45F;
      this.f_170855_.f_104201_ = this.f_170854_.f_104201_;
      this.f_170855_.f_104202_ = this.f_170854_.f_104202_;
      this.f_170855_.f_104203_ -= f1 * (float)Math.PI * 0.45F;
      if (this.f_102610_) {
         this.f_103492_.f_104201_ = 10.0F * f2 - 9.0F * f1;
         this.f_103492_.f_104202_ = -16.0F * f2 - 7.0F * f1;
      } else {
         this.f_103492_.f_104201_ = 10.0F * f2 - 14.0F * f1;
         this.f_103492_.f_104202_ = -16.0F * f2 - 3.0F * f1;
      }

      this.f_103492_.f_104203_ += f1 * (float)Math.PI * 0.15F;
   }
}