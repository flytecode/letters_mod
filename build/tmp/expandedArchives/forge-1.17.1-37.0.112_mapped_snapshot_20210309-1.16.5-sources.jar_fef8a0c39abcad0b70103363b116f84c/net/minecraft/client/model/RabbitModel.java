package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RabbitModel<T extends Rabbit> extends EntityModel<T> {
   private static final float f_170867_ = 50.0F;
   private static final float f_170868_ = -40.0F;
   private static final String f_170869_ = "left_haunch";
   private static final String f_170870_ = "right_haunch";
   private final ModelPart f_170871_;
   private final ModelPart f_170872_;
   private final ModelPart f_170873_;
   private final ModelPart f_170874_;
   private final ModelPart f_103520_;
   private final ModelPart f_170875_;
   private final ModelPart f_170876_;
   private final ModelPart f_103523_;
   private final ModelPart f_170877_;
   private final ModelPart f_170878_;
   private final ModelPart f_103526_;
   private final ModelPart f_103527_;
   private float f_103528_;
   private static final float f_170879_ = 0.6F;

   public RabbitModel(ModelPart p_170881_) {
      this.f_170871_ = p_170881_.m_171324_("left_hind_foot");
      this.f_170872_ = p_170881_.m_171324_("right_hind_foot");
      this.f_170873_ = p_170881_.m_171324_("left_haunch");
      this.f_170874_ = p_170881_.m_171324_("right_haunch");
      this.f_103520_ = p_170881_.m_171324_("body");
      this.f_170875_ = p_170881_.m_171324_("left_front_leg");
      this.f_170876_ = p_170881_.m_171324_("right_front_leg");
      this.f_103523_ = p_170881_.m_171324_("head");
      this.f_170877_ = p_170881_.m_171324_("right_ear");
      this.f_170878_ = p_170881_.m_171324_("left_ear");
      this.f_103526_ = p_170881_.m_171324_("tail");
      this.f_103527_ = p_170881_.m_171324_("nose");
   }

   public static LayerDefinition m_170882_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("left_hind_foot", CubeListBuilder.m_171558_().m_171514_(26, 24).m_171481_(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), PartPose.m_171419_(3.0F, 17.5F, 3.7F));
      partdefinition.m_171599_("right_hind_foot", CubeListBuilder.m_171558_().m_171514_(8, 24).m_171481_(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), PartPose.m_171419_(-3.0F, 17.5F, 3.7F));
      partdefinition.m_171599_("left_haunch", CubeListBuilder.m_171558_().m_171514_(30, 15).m_171481_(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), PartPose.m_171423_(3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
      partdefinition.m_171599_("right_haunch", CubeListBuilder.m_171558_().m_171514_(16, 15).m_171481_(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), PartPose.m_171423_(-3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F), PartPose.m_171423_(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(8, 15).m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.m_171423_(3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.m_171423_(-3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F), PartPose.m_171419_(0.0F, 16.0F, -1.0F));
      partdefinition.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(52, 0).m_171481_(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), PartPose.m_171423_(0.0F, 16.0F, -1.0F, 0.0F, -0.2617994F, 0.0F));
      partdefinition.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(58, 0).m_171481_(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), PartPose.m_171423_(0.0F, 16.0F, -1.0F, 0.0F, 0.2617994F, 0.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(52, 6).m_171481_(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F), PartPose.m_171423_(0.0F, 20.0F, 7.0F, -0.3490659F, 0.0F, 0.0F));
      partdefinition.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(32, 9).m_171481_(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F), PartPose.m_171419_(0.0F, 16.0F, -1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_7695_(PoseStack p_103555_, VertexConsumer p_103556_, int p_103557_, int p_103558_, float p_103559_, float p_103560_, float p_103561_, float p_103562_) {
      if (this.f_102610_) {
         float f = 1.5F;
         p_103555_.m_85836_();
         p_103555_.m_85841_(0.56666666F, 0.56666666F, 0.56666666F);
         p_103555_.m_85837_(0.0D, 1.375D, 0.125D);
         ImmutableList.of(this.f_103523_, this.f_170878_, this.f_170877_, this.f_103527_).forEach((p_103597_) -> {
            p_103597_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_);
         });
         p_103555_.m_85849_();
         p_103555_.m_85836_();
         p_103555_.m_85841_(0.4F, 0.4F, 0.4F);
         p_103555_.m_85837_(0.0D, 2.25D, 0.0D);
         ImmutableList.of(this.f_170871_, this.f_170872_, this.f_170873_, this.f_170874_, this.f_103520_, this.f_170875_, this.f_170876_, this.f_103526_).forEach((p_103587_) -> {
            p_103587_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_);
         });
         p_103555_.m_85849_();
      } else {
         p_103555_.m_85836_();
         p_103555_.m_85841_(0.6F, 0.6F, 0.6F);
         p_103555_.m_85837_(0.0D, 1.0D, 0.0D);
         ImmutableList.of(this.f_170871_, this.f_170872_, this.f_170873_, this.f_170874_, this.f_103520_, this.f_170875_, this.f_170876_, this.f_103523_, this.f_170877_, this.f_170878_, this.f_103526_, this.f_103527_).forEach((p_103572_) -> {
            p_103572_.m_104306_(p_103555_, p_103556_, p_103557_, p_103558_, p_103559_, p_103560_, p_103561_, p_103562_);
         });
         p_103555_.m_85849_();
      }

   }

   public void m_6973_(T p_103548_, float p_103549_, float p_103550_, float p_103551_, float p_103552_, float p_103553_) {
      float f = p_103551_ - (float)p_103548_.f_19797_;
      this.f_103527_.f_104203_ = p_103553_ * ((float)Math.PI / 180F);
      this.f_103523_.f_104203_ = p_103553_ * ((float)Math.PI / 180F);
      this.f_170877_.f_104203_ = p_103553_ * ((float)Math.PI / 180F);
      this.f_170878_.f_104203_ = p_103553_ * ((float)Math.PI / 180F);
      this.f_103527_.f_104204_ = p_103552_ * ((float)Math.PI / 180F);
      this.f_103523_.f_104204_ = p_103552_ * ((float)Math.PI / 180F);
      this.f_170877_.f_104204_ = this.f_103527_.f_104204_ - 0.2617994F;
      this.f_170878_.f_104204_ = this.f_103527_.f_104204_ + 0.2617994F;
      this.f_103528_ = Mth.m_14031_(p_103548_.m_29735_(f) * (float)Math.PI);
      this.f_170873_.f_104203_ = (this.f_103528_ * 50.0F - 21.0F) * ((float)Math.PI / 180F);
      this.f_170874_.f_104203_ = (this.f_103528_ * 50.0F - 21.0F) * ((float)Math.PI / 180F);
      this.f_170871_.f_104203_ = this.f_103528_ * 50.0F * ((float)Math.PI / 180F);
      this.f_170872_.f_104203_ = this.f_103528_ * 50.0F * ((float)Math.PI / 180F);
      this.f_170875_.f_104203_ = (this.f_103528_ * -40.0F - 11.0F) * ((float)Math.PI / 180F);
      this.f_170876_.f_104203_ = (this.f_103528_ * -40.0F - 11.0F) * ((float)Math.PI / 180F);
   }

   public void m_6839_(T p_103543_, float p_103544_, float p_103545_, float p_103546_) {
      super.m_6839_(p_103543_, p_103544_, p_103545_, p_103546_);
      this.f_103528_ = Mth.m_14031_(p_103543_.m_29735_(p_103546_) * (float)Math.PI);
   }
}