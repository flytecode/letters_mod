package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieVillagerModel<T extends Zombie> extends HumanoidModel<T> implements VillagerHeadModel {
   private final ModelPart f_104156_ = this.f_102809_.m_171324_("hat_rim");

   public ZombieVillagerModel(ModelPart p_171092_) {
      super(p_171092_);
   }

   public static LayerDefinition m_171095_() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", (new CubeListBuilder()).m_171514_(0, 0).m_171481_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F).m_171514_(24, 0).m_171481_(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.f_171404_);
      PartDefinition partdefinition1 = partdefinition.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.f_171404_);
      partdefinition1.m_171599_("hat_rim", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171481_(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), PartPose.m_171430_((-(float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).m_171514_(0, 38).m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.f_171404_);
      partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(-5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171480_().m_171481_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(5.0F, 2.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition m_171093_(CubeDeformation p_171094_) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(p_171094_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_171094_), PartPose.f_171404_);
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_171094_.m_171469_(0.1F)), PartPose.f_171404_);
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_171094_.m_171469_(0.1F)), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_171094_.m_171469_(0.1F)), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("hat_rim", CubeListBuilder.m_171558_(), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_104175_, float p_104176_, float p_104177_, float p_104178_, float p_104179_, float p_104180_) {
      super.m_6973_(p_104175_, p_104176_, p_104177_, p_104178_, p_104179_, p_104180_);
      AnimationUtils.m_102102_(this.f_102812_, this.f_102811_, p_104175_.m_5912_(), this.f_102608_, p_104178_);
   }

   public void m_7491_(boolean p_104182_) {
      this.f_102808_.f_104207_ = p_104182_;
      this.f_102809_.f_104207_ = p_104182_;
      this.f_104156_.f_104207_ = p_104182_;
   }
}