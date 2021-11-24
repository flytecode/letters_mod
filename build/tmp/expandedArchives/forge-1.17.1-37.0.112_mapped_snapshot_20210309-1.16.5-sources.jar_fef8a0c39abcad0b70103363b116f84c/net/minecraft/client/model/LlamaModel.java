package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaModel<T extends AbstractChestedHorse> extends EntityModel<T> {
   private final ModelPart f_103031_;
   private final ModelPart f_103032_;
   private final ModelPart f_170717_;
   private final ModelPart f_170718_;
   private final ModelPart f_170719_;
   private final ModelPart f_170720_;
   private final ModelPart f_170721_;
   private final ModelPart f_170722_;

   public LlamaModel(ModelPart p_170724_) {
      this.f_103031_ = p_170724_.m_171324_("head");
      this.f_103032_ = p_170724_.m_171324_("body");
      this.f_170721_ = p_170724_.m_171324_("right_chest");
      this.f_170722_ = p_170724_.m_171324_("left_chest");
      this.f_170717_ = p_170724_.m_171324_("right_hind_leg");
      this.f_170718_ = p_170724_.m_171324_("left_hind_leg");
      this.f_170719_ = p_170724_.m_171324_("right_front_leg");
      this.f_170720_ = p_170724_.m_171324_("left_front_leg");
   }

   public static LayerDefinition m_170725_(CubeDeformation p_170726_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, p_170726_).m_171514_(0, 14).m_171525_("neck", -4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, p_170726_).m_171514_(17, 0).m_171525_("ear", -4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_170726_).m_171514_(17, 0).m_171525_("ear", 1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_170726_), PartPose.m_171419_(0.0F, 7.0F, -6.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(29, 0).m_171488_(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, p_170726_), PartPose.m_171423_(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_chest", CubeListBuilder.m_171558_().m_171514_(45, 28).m_171488_(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_170726_), PartPose.m_171423_(-8.5F, 3.0F, 3.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
      partdefinition.m_171599_("left_chest", CubeListBuilder.m_171558_().m_171514_(45, 41).m_171488_(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_170726_), PartPose.m_171423_(5.5F, 3.0F, 3.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
      int i = 4;
      int j = 14;
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(29, 29).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_170726_);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-3.5F, 10.0F, 6.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(3.5F, 10.0F, 6.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-3.5F, 10.0F, -5.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(3.5F, 10.0F, -5.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void m_6973_(T p_103049_, float p_103050_, float p_103051_, float p_103052_, float p_103053_, float p_103054_) {
      this.f_103031_.f_104203_ = p_103054_ * ((float)Math.PI / 180F);
      this.f_103031_.f_104204_ = p_103053_ * ((float)Math.PI / 180F);
      this.f_170717_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662F) * 1.4F * p_103051_;
      this.f_170718_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662F + (float)Math.PI) * 1.4F * p_103051_;
      this.f_170719_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662F + (float)Math.PI) * 1.4F * p_103051_;
      this.f_170720_.f_104203_ = Mth.m_14089_(p_103050_ * 0.6662F) * 1.4F * p_103051_;
      boolean flag = !p_103049_.m_6162_() && p_103049_.m_30502_();
      this.f_170721_.f_104207_ = flag;
      this.f_170722_.f_104207_ = flag;
   }

   public void m_7695_(PoseStack p_103056_, VertexConsumer p_103057_, int p_103058_, int p_103059_, float p_103060_, float p_103061_, float p_103062_, float p_103063_) {
      if (this.f_102610_) {
         float f = 2.0F;
         p_103056_.m_85836_();
         float f1 = 0.7F;
         p_103056_.m_85841_(0.71428573F, 0.64935064F, 0.7936508F);
         p_103056_.m_85837_(0.0D, 1.3125D, (double)0.22F);
         this.f_103031_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
         p_103056_.m_85849_();
         p_103056_.m_85836_();
         float f2 = 1.1F;
         p_103056_.m_85841_(0.625F, 0.45454544F, 0.45454544F);
         p_103056_.m_85837_(0.0D, 2.0625D, 0.0D);
         this.f_103032_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
         p_103056_.m_85849_();
         p_103056_.m_85836_();
         p_103056_.m_85841_(0.45454544F, 0.41322312F, 0.45454544F);
         p_103056_.m_85837_(0.0D, 2.0625D, 0.0D);
         ImmutableList.of(this.f_170717_, this.f_170718_, this.f_170719_, this.f_170720_, this.f_170721_, this.f_170722_).forEach((p_103083_) -> {
            p_103083_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
         });
         p_103056_.m_85849_();
      } else {
         ImmutableList.of(this.f_103031_, this.f_103032_, this.f_170717_, this.f_170718_, this.f_170719_, this.f_170720_, this.f_170721_, this.f_170722_).forEach((p_103073_) -> {
            p_103073_.m_104306_(p_103056_, p_103057_, p_103058_, p_103059_, p_103060_, p_103061_, p_103062_, p_103063_);
         });
      }

   }
}