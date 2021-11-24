package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
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
public class ChickenModel<T extends Entity> extends AgeableListModel<T> {
   public static final String f_170484_ = "red_thing";
   private final ModelPart f_102381_;
   private final ModelPart f_102382_;
   private final ModelPart f_170485_;
   private final ModelPart f_170486_;
   private final ModelPart f_170487_;
   private final ModelPart f_170488_;
   private final ModelPart f_102387_;
   private final ModelPart f_102388_;

   public ChickenModel(ModelPart p_170490_) {
      this.f_102381_ = p_170490_.m_171324_("head");
      this.f_102387_ = p_170490_.m_171324_("beak");
      this.f_102388_ = p_170490_.m_171324_("red_thing");
      this.f_102382_ = p_170490_.m_171324_("body");
      this.f_170485_ = p_170490_.m_171324_("right_leg");
      this.f_170486_ = p_170490_.m_171324_("left_leg");
      this.f_170487_ = p_170490_.m_171324_("right_wing");
      this.f_170488_ = p_170490_.m_171324_("left_wing");
   }

   public static LayerDefinition m_170491_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 16;
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("beak", CubeListBuilder.m_171558_().m_171514_(14, 0).m_171481_(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("red_thing", CubeListBuilder.m_171558_().m_171514_(14, 4).m_171481_(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.m_171419_(0.0F, 15.0F, -4.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 9).m_171481_(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.m_171423_(0.0F, 16.0F, 0.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(26, 0).m_171481_(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      partdefinition.m_171599_("right_leg", cubelistbuilder, PartPose.m_171419_(-2.0F, 19.0F, 1.0F));
      partdefinition.m_171599_("left_leg", cubelistbuilder, PartPose.m_171419_(1.0F, 19.0F, 1.0F));
      partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171419_(-4.0F, 13.0F, 0.0F));
      partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(24, 13).m_171481_(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171419_(4.0F, 13.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102381_, this.f_102387_, this.f_102388_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102382_, this.f_170485_, this.f_170486_, this.f_170487_, this.f_170488_);
   }

   public void m_6973_(T p_102392_, float p_102393_, float p_102394_, float p_102395_, float p_102396_, float p_102397_) {
      this.f_102381_.f_104203_ = p_102397_ * ((float)Math.PI / 180F);
      this.f_102381_.f_104204_ = p_102396_ * ((float)Math.PI / 180F);
      this.f_102387_.f_104203_ = this.f_102381_.f_104203_;
      this.f_102387_.f_104204_ = this.f_102381_.f_104204_;
      this.f_102388_.f_104203_ = this.f_102381_.f_104203_;
      this.f_102388_.f_104204_ = this.f_102381_.f_104204_;
      this.f_170485_.f_104203_ = Mth.m_14089_(p_102393_ * 0.6662F) * 1.4F * p_102394_;
      this.f_170486_.f_104203_ = Mth.m_14089_(p_102393_ * 0.6662F + (float)Math.PI) * 1.4F * p_102394_;
      this.f_170487_.f_104205_ = p_102395_;
      this.f_170488_.f_104205_ = -p_102395_;
   }
}