package net.minecraft.client.model;

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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrownedModel<T extends Zombie> extends ZombieModel<T> {
   public DrownedModel(ModelPart p_170534_) {
      super(p_170534_);
   }

   public static LayerDefinition m_170535_(CubeDeformation p_170536_) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(p_170536_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170536_), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(16, 48).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170536_), PartPose.m_171419_(1.9F, 12.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6839_(T p_102521_, float p_102522_, float p_102523_, float p_102524_) {
      this.f_102816_ = HumanoidModel.ArmPose.EMPTY;
      this.f_102815_ = HumanoidModel.ArmPose.EMPTY;
      ItemStack itemstack = p_102521_.m_21120_(InteractionHand.MAIN_HAND);
      if (itemstack.m_150930_(Items.f_42713_) && p_102521_.m_5912_()) {
         if (p_102521_.m_5737_() == HumanoidArm.RIGHT) {
            this.f_102816_ = HumanoidModel.ArmPose.THROW_SPEAR;
         } else {
            this.f_102815_ = HumanoidModel.ArmPose.THROW_SPEAR;
         }
      }

      super.m_6839_(p_102521_, p_102522_, p_102523_, p_102524_);
   }

   public void m_6973_(T p_102526_, float p_102527_, float p_102528_, float p_102529_, float p_102530_, float p_102531_) {
      super.m_6973_(p_102526_, p_102527_, p_102528_, p_102529_, p_102530_, p_102531_);
      if (this.f_102815_ == HumanoidModel.ArmPose.THROW_SPEAR) {
         this.f_102812_.f_104203_ = this.f_102812_.f_104203_ * 0.5F - (float)Math.PI;
         this.f_102812_.f_104204_ = 0.0F;
      }

      if (this.f_102816_ == HumanoidModel.ArmPose.THROW_SPEAR) {
         this.f_102811_.f_104203_ = this.f_102811_.f_104203_ * 0.5F - (float)Math.PI;
         this.f_102811_.f_104204_ = 0.0F;
      }

      if (this.f_102818_ > 0.0F) {
         this.f_102811_.f_104203_ = this.m_102835_(this.f_102818_, this.f_102811_.f_104203_, -2.5132742F) + this.f_102818_ * 0.35F * Mth.m_14031_(0.1F * p_102529_);
         this.f_102812_.f_104203_ = this.m_102835_(this.f_102818_, this.f_102812_.f_104203_, -2.5132742F) - this.f_102818_ * 0.35F * Mth.m_14031_(0.1F * p_102529_);
         this.f_102811_.f_104205_ = this.m_102835_(this.f_102818_, this.f_102811_.f_104205_, -0.15F);
         this.f_102812_.f_104205_ = this.m_102835_(this.f_102818_, this.f_102812_.f_104205_, 0.15F);
         this.f_102814_.f_104203_ -= this.f_102818_ * 0.55F * Mth.m_14031_(0.1F * p_102529_);
         this.f_102813_.f_104203_ += this.f_102818_ * 0.55F * Mth.m_14031_(0.1F * p_102529_);
         this.f_102808_.f_104203_ = 0.0F;
      }

   }
}