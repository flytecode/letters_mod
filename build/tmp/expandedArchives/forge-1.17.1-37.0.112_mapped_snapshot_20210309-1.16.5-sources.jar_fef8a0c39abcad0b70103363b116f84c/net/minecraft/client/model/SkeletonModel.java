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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonModel<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {
   public SkeletonModel(ModelPart p_170941_) {
      super(p_170941_);
   }

   public static LayerDefinition m_170942_() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(40, 16).m_171480_().m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6839_(T p_103793_, float p_103794_, float p_103795_, float p_103796_) {
      this.f_102816_ = HumanoidModel.ArmPose.EMPTY;
      this.f_102815_ = HumanoidModel.ArmPose.EMPTY;
      ItemStack itemstack = p_103793_.m_21120_(InteractionHand.MAIN_HAND);
      if (itemstack.m_150930_(Items.f_42411_) && p_103793_.m_5912_()) {
         if (p_103793_.m_5737_() == HumanoidArm.RIGHT) {
            this.f_102816_ = HumanoidModel.ArmPose.BOW_AND_ARROW;
         } else {
            this.f_102815_ = HumanoidModel.ArmPose.BOW_AND_ARROW;
         }
      }

      super.m_6839_(p_103793_, p_103794_, p_103795_, p_103796_);
   }

   public void m_6973_(T p_103798_, float p_103799_, float p_103800_, float p_103801_, float p_103802_, float p_103803_) {
      super.m_6973_(p_103798_, p_103799_, p_103800_, p_103801_, p_103802_, p_103803_);
      ItemStack itemstack = p_103798_.m_21205_();
      if (p_103798_.m_5912_() && (itemstack.m_41619_() || !itemstack.m_150930_(Items.f_42411_))) {
         float f = Mth.m_14031_(this.f_102608_ * (float)Math.PI);
         float f1 = Mth.m_14031_((1.0F - (1.0F - this.f_102608_) * (1.0F - this.f_102608_)) * (float)Math.PI);
         this.f_102811_.f_104205_ = 0.0F;
         this.f_102812_.f_104205_ = 0.0F;
         this.f_102811_.f_104204_ = -(0.1F - f * 0.6F);
         this.f_102812_.f_104204_ = 0.1F - f * 0.6F;
         this.f_102811_.f_104203_ = (-(float)Math.PI / 2F);
         this.f_102812_.f_104203_ = (-(float)Math.PI / 2F);
         this.f_102811_.f_104203_ -= f * 1.2F - f1 * 0.4F;
         this.f_102812_.f_104203_ -= f * 1.2F - f1 * 0.4F;
         AnimationUtils.m_102082_(this.f_102811_, this.f_102812_, p_103801_);
      }

   }

   public void m_6002_(HumanoidArm p_103778_, PoseStack p_103779_) {
      float f = p_103778_ == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      ModelPart modelpart = this.m_102851_(p_103778_);
      modelpart.f_104200_ += f;
      modelpart.m_104299_(p_103779_);
      modelpart.f_104200_ -= f;
   }
}