package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerModel<T extends Entity> extends HierarchicalModel<T> implements HeadedModel, VillagerHeadModel {
   private final ModelPart f_171047_;
   private final ModelPart f_104036_;
   private final ModelPart f_104037_;
   private final ModelPart f_104038_;
   private final ModelPart f_171048_;
   private final ModelPart f_171049_;
   protected final ModelPart f_104044_;

   public VillagerModel(ModelPart p_171051_) {
      this.f_171047_ = p_171051_;
      this.f_104036_ = p_171051_.m_171324_("head");
      this.f_104037_ = this.f_104036_.m_171324_("hat");
      this.f_104038_ = this.f_104037_.m_171324_("hat_rim");
      this.f_104044_ = this.f_104036_.m_171324_("nose");
      this.f_171048_ = p_171051_.m_171324_("right_leg");
      this.f_171049_ = p_171051_.m_171324_("left_leg");
   }

   public static MeshDefinition m_171052_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = 0.5F;
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.f_171404_);
      PartDefinition partdefinition2 = partdefinition1.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.f_171404_);
      partdefinition2.m_171599_("hat_rim", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171481_(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), PartPose.m_171430_((-(float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(24, 0).m_171481_(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.m_171419_(0.0F, -2.0F, 0.0F));
      PartDefinition partdefinition3 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 20).m_171481_(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.f_171404_);
      partdefinition3.m_171599_("jacket", CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.f_171404_);
      partdefinition.m_171599_("arms", CubeListBuilder.m_171558_().m_171514_(44, 22).m_171481_(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).m_171514_(44, 22).m_171506_(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true).m_171514_(40, 38).m_171481_(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.m_171423_(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(-2.0F, 12.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171480_().m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.m_171419_(2.0F, 12.0F, 0.0F));
      return meshdefinition;
   }

   public ModelPart m_142109_() {
      return this.f_171047_;
   }

   public void m_6973_(T p_104053_, float p_104054_, float p_104055_, float p_104056_, float p_104057_, float p_104058_) {
      boolean flag = false;
      if (p_104053_ instanceof AbstractVillager) {
         flag = ((AbstractVillager)p_104053_).m_35303_() > 0;
      }

      this.f_104036_.f_104204_ = p_104057_ * ((float)Math.PI / 180F);
      this.f_104036_.f_104203_ = p_104058_ * ((float)Math.PI / 180F);
      if (flag) {
         this.f_104036_.f_104205_ = 0.3F * Mth.m_14031_(0.45F * p_104056_);
         this.f_104036_.f_104203_ = 0.4F;
      } else {
         this.f_104036_.f_104205_ = 0.0F;
      }

      this.f_171048_.f_104203_ = Mth.m_14089_(p_104054_ * 0.6662F) * 1.4F * p_104055_ * 0.5F;
      this.f_171049_.f_104203_ = Mth.m_14089_(p_104054_ * 0.6662F + (float)Math.PI) * 1.4F * p_104055_ * 0.5F;
      this.f_171048_.f_104204_ = 0.0F;
      this.f_171049_.f_104204_ = 0.0F;
   }

   public ModelPart m_5585_() {
      return this.f_104036_;
   }

   public void m_7491_(boolean p_104060_) {
      this.f_104036_.f_104207_ = p_104060_;
      this.f_104037_.f_104207_ = p_104060_;
      this.f_104038_.f_104207_ = p_104060_;
   }
}