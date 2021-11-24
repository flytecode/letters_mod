package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PiglinModel<T extends Mob> extends PlayerModel<T> {
   public final ModelPart f_170807_ = this.f_102808_.m_171324_("right_ear");
   private final ModelPart f_170808_ = this.f_102808_.m_171324_("left_ear");
   private final PartPose f_103337_ = this.f_102810_.m_171308_();
   private final PartPose f_103338_ = this.f_102808_.m_171308_();
   private final PartPose f_103333_ = this.f_102812_.m_171308_();
   private final PartPose f_103334_ = this.f_102811_.m_171308_();

   public PiglinModel(ModelPart p_170810_) {
      super(p_170810_, false);
   }

   public static MeshDefinition m_170811_(CubeDeformation p_170812_) {
      MeshDefinition meshdefinition = PlayerModel.m_170825_(p_170812_, false);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170812_), PartPose.f_171404_);
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, p_170812_).m_171514_(31, 1).m_171488_(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, p_170812_).m_171514_(2, 4).m_171488_(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_170812_).m_171514_(2, 0).m_171488_(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, p_170812_), PartPose.f_171404_);
      partdefinition1.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(51, 6).m_171488_(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_170812_), PartPose.m_171423_(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, (-(float)Math.PI / 6F)));
      partdefinition1.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(39, 6).m_171488_(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, p_170812_), PartPose.m_171423_(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, ((float)Math.PI / 6F)));
      partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.f_171404_);
      return meshdefinition;
   }

   public void m_6973_(T p_103366_, float p_103367_, float p_103368_, float p_103369_, float p_103370_, float p_103371_) {
      this.f_102810_.m_171322_(this.f_103337_);
      this.f_102808_.m_171322_(this.f_103338_);
      this.f_102812_.m_171322_(this.f_103333_);
      this.f_102811_.m_171322_(this.f_103334_);
      super.m_6973_(p_103366_, p_103367_, p_103368_, p_103369_, p_103370_, p_103371_);
      float f = ((float)Math.PI / 6F);
      float f1 = p_103369_ * 0.1F + p_103367_ * 0.5F;
      float f2 = 0.08F + p_103368_ * 0.4F;
      this.f_170808_.f_104205_ = (-(float)Math.PI / 6F) - Mth.m_14089_(f1 * 1.2F) * f2;
      this.f_170807_.f_104205_ = ((float)Math.PI / 6F) + Mth.m_14089_(f1) * f2;
      if (p_103366_ instanceof AbstractPiglin) {
         AbstractPiglin abstractpiglin = (AbstractPiglin)p_103366_;
         PiglinArmPose piglinarmpose = abstractpiglin.m_6389_();
         if (piglinarmpose == PiglinArmPose.DANCING) {
            float f3 = p_103369_ / 60.0F;
            this.f_170807_.f_104205_ = ((float)Math.PI / 6F) + ((float)Math.PI / 180F) * Mth.m_14031_(f3 * 30.0F) * 10.0F;
            this.f_170808_.f_104205_ = (-(float)Math.PI / 6F) - ((float)Math.PI / 180F) * Mth.m_14089_(f3 * 30.0F) * 10.0F;
            this.f_102808_.f_104200_ = Mth.m_14031_(f3 * 10.0F);
            this.f_102808_.f_104201_ = Mth.m_14031_(f3 * 40.0F) + 0.4F;
            this.f_102811_.f_104205_ = ((float)Math.PI / 180F) * (70.0F + Mth.m_14089_(f3 * 40.0F) * 10.0F);
            this.f_102812_.f_104205_ = this.f_102811_.f_104205_ * -1.0F;
            this.f_102811_.f_104201_ = Mth.m_14031_(f3 * 40.0F) * 0.5F + 1.5F;
            this.f_102812_.f_104201_ = Mth.m_14031_(f3 * 40.0F) * 0.5F + 1.5F;
            this.f_102810_.f_104201_ = Mth.m_14031_(f3 * 40.0F) * 0.35F;
         } else if (piglinarmpose == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON && this.f_102608_ == 0.0F) {
            this.m_103360_(p_103366_);
         } else if (piglinarmpose == PiglinArmPose.CROSSBOW_HOLD) {
            AnimationUtils.m_102097_(this.f_102811_, this.f_102812_, this.f_102808_, !p_103366_.m_21526_());
         } else if (piglinarmpose == PiglinArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.m_102086_(this.f_102811_, this.f_102812_, p_103366_, !p_103366_.m_21526_());
         } else if (piglinarmpose == PiglinArmPose.ADMIRING_ITEM) {
            this.f_102808_.f_104203_ = 0.5F;
            this.f_102808_.f_104204_ = 0.0F;
            if (p_103366_.m_21526_()) {
               this.f_102811_.f_104204_ = -0.5F;
               this.f_102811_.f_104203_ = -0.9F;
            } else {
               this.f_102812_.f_104204_ = 0.5F;
               this.f_102812_.f_104203_ = -0.9F;
            }
         }
      } else if (p_103366_.m_6095_() == EntityType.f_20531_) {
         AnimationUtils.m_102102_(this.f_102812_, this.f_102811_, p_103366_.m_5912_(), this.f_102608_, p_103369_);
      }

      this.f_103376_.m_104315_(this.f_102814_);
      this.f_103377_.m_104315_(this.f_102813_);
      this.f_103374_.m_104315_(this.f_102812_);
      this.f_103375_.m_104315_(this.f_102811_);
      this.f_103378_.m_104315_(this.f_102810_);
      this.f_102809_.m_104315_(this.f_102808_);
   }

   protected void m_7884_(T p_103363_, float p_103364_) {
      if (this.f_102608_ > 0.0F && p_103363_ instanceof Piglin && ((Piglin)p_103363_).m_6389_() == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON) {
         AnimationUtils.m_102091_(this.f_102811_, this.f_102812_, p_103363_, this.f_102608_, p_103364_);
      } else {
         super.m_7884_(p_103363_, p_103364_);
      }
   }

   private void m_103360_(T p_103361_) {
      if (p_103361_.m_21526_()) {
         this.f_102812_.f_104203_ = -1.8F;
      } else {
         this.f_102811_.f_104203_ = -1.8F;
      }

   }
}