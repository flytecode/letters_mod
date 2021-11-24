package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IllagerModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
   private final ModelPart f_170686_;
   private final ModelPart f_102901_;
   private final ModelPart f_102902_;
   private final ModelPart f_102904_;
   private final ModelPart f_102905_;
   private final ModelPart f_102906_;
   private final ModelPart f_102907_;
   private final ModelPart f_102908_;

   public IllagerModel(ModelPart p_170688_) {
      this.f_170686_ = p_170688_;
      this.f_102901_ = p_170688_.m_171324_("head");
      this.f_102902_ = this.f_102901_.m_171324_("hat");
      this.f_102902_.f_104207_ = false;
      this.f_102904_ = p_170688_.m_171324_("arms");
      this.f_102905_ = p_170688_.m_171324_("left_leg");
      this.f_102906_ = p_170688_.m_171324_("right_leg");
      this.f_102908_ = p_170688_.m_171324_("left_arm");
      this.f_102907_ = p_170688_.m_171324_("right_arm");
   }

   public static LayerDefinition m_170689_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition1.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.f_171404_);
      partdefinition1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.m_171419_(0.0F, -2.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).m_171514_(0, 38).m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("arms", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).m_171514_(40, 38).m_171481_(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.m_171423_(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
      partdefinition2.m_171599_("left_shoulder", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171480_().m_171481_(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.f_171404_);
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 46).m_171481_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 46).m_171480_().m_171481_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_170686_;
   }

   public void m_6973_(T p_102928_, float p_102929_, float p_102930_, float p_102931_, float p_102932_, float p_102933_) {
      this.f_102901_.f_104204_ = p_102932_ * ((float)Math.PI / 180F);
      this.f_102901_.f_104203_ = p_102933_ * ((float)Math.PI / 180F);
      if (this.f_102609_) {
         this.f_102907_.f_104203_ = (-(float)Math.PI / 5F);
         this.f_102907_.f_104204_ = 0.0F;
         this.f_102907_.f_104205_ = 0.0F;
         this.f_102908_.f_104203_ = (-(float)Math.PI / 5F);
         this.f_102908_.f_104204_ = 0.0F;
         this.f_102908_.f_104205_ = 0.0F;
         this.f_102906_.f_104203_ = -1.4137167F;
         this.f_102906_.f_104204_ = ((float)Math.PI / 10F);
         this.f_102906_.f_104205_ = 0.07853982F;
         this.f_102905_.f_104203_ = -1.4137167F;
         this.f_102905_.f_104204_ = (-(float)Math.PI / 10F);
         this.f_102905_.f_104205_ = -0.07853982F;
      } else {
         this.f_102907_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662F + (float)Math.PI) * 2.0F * p_102930_ * 0.5F;
         this.f_102907_.f_104204_ = 0.0F;
         this.f_102907_.f_104205_ = 0.0F;
         this.f_102908_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662F) * 2.0F * p_102930_ * 0.5F;
         this.f_102908_.f_104204_ = 0.0F;
         this.f_102908_.f_104205_ = 0.0F;
         this.f_102906_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662F) * 1.4F * p_102930_ * 0.5F;
         this.f_102906_.f_104204_ = 0.0F;
         this.f_102906_.f_104205_ = 0.0F;
         this.f_102905_.f_104203_ = Mth.m_14089_(p_102929_ * 0.6662F + (float)Math.PI) * 1.4F * p_102930_ * 0.5F;
         this.f_102905_.f_104204_ = 0.0F;
         this.f_102905_.f_104205_ = 0.0F;
      }

      AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = p_102928_.m_6768_();
      if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.ATTACKING) {
         if (p_102928_.m_21205_().m_41619_()) {
            AnimationUtils.m_102102_(this.f_102908_, this.f_102907_, true, this.f_102608_, p_102931_);
         } else {
            AnimationUtils.m_102091_(this.f_102907_, this.f_102908_, p_102928_, this.f_102608_, p_102931_);
         }
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
         this.f_102907_.f_104202_ = 0.0F;
         this.f_102907_.f_104200_ = -5.0F;
         this.f_102908_.f_104202_ = 0.0F;
         this.f_102908_.f_104200_ = 5.0F;
         this.f_102907_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662F) * 0.25F;
         this.f_102908_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662F) * 0.25F;
         this.f_102907_.f_104205_ = 2.3561945F;
         this.f_102908_.f_104205_ = -2.3561945F;
         this.f_102907_.f_104204_ = 0.0F;
         this.f_102908_.f_104204_ = 0.0F;
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
         this.f_102907_.f_104204_ = -0.1F + this.f_102901_.f_104204_;
         this.f_102907_.f_104203_ = (-(float)Math.PI / 2F) + this.f_102901_.f_104203_;
         this.f_102908_.f_104203_ = -0.9424779F + this.f_102901_.f_104203_;
         this.f_102908_.f_104204_ = this.f_102901_.f_104204_ - 0.4F;
         this.f_102908_.f_104205_ = ((float)Math.PI / 2F);
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
         AnimationUtils.m_102097_(this.f_102907_, this.f_102908_, this.f_102901_, true);
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
         AnimationUtils.m_102086_(this.f_102907_, this.f_102908_, p_102928_, true);
      } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CELEBRATING) {
         this.f_102907_.f_104202_ = 0.0F;
         this.f_102907_.f_104200_ = -5.0F;
         this.f_102907_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662F) * 0.05F;
         this.f_102907_.f_104205_ = 2.670354F;
         this.f_102907_.f_104204_ = 0.0F;
         this.f_102908_.f_104202_ = 0.0F;
         this.f_102908_.f_104200_ = 5.0F;
         this.f_102908_.f_104203_ = Mth.m_14089_(p_102931_ * 0.6662F) * 0.05F;
         this.f_102908_.f_104205_ = -2.3561945F;
         this.f_102908_.f_104204_ = 0.0F;
      }

      boolean flag = abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSED;
      this.f_102904_.f_104207_ = flag;
      this.f_102908_.f_104207_ = !flag;
      this.f_102907_.f_104207_ = !flag;
   }

   private ModelPart m_102922_(HumanoidArm p_102923_) {
      return p_102923_ == HumanoidArm.LEFT ? this.f_102908_ : this.f_102907_;
   }

   public ModelPart m_102934_() {
      return this.f_102902_;
   }

   public ModelPart m_5585_() {
      return this.f_102901_;
   }

   public void m_6002_(HumanoidArm p_102925_, PoseStack p_102926_) {
      this.m_102922_(p_102925_).m_104299_(p_102926_);
   }
}