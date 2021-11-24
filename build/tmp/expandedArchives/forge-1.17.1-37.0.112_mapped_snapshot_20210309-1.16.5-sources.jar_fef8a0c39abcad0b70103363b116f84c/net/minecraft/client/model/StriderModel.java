package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Strider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StriderModel<T extends Strider> extends HierarchicalModel<T> {
   private static final String f_170997_ = "right_bottom_bristle";
   private static final String f_170998_ = "right_middle_bristle";
   private static final String f_170999_ = "right_top_bristle";
   private static final String f_171000_ = "left_top_bristle";
   private static final String f_171001_ = "left_middle_bristle";
   private static final String f_171002_ = "left_bottom_bristle";
   private final ModelPart f_171003_;
   private final ModelPart f_103884_;
   private final ModelPart f_103885_;
   private final ModelPart f_103886_;
   private final ModelPart f_171004_;
   private final ModelPart f_171005_;
   private final ModelPart f_171006_;
   private final ModelPart f_171007_;
   private final ModelPart f_171008_;
   private final ModelPart f_171009_;

   public StriderModel(ModelPart p_171011_) {
      this.f_171003_ = p_171011_;
      this.f_103884_ = p_171011_.m_171324_("right_leg");
      this.f_103885_ = p_171011_.m_171324_("left_leg");
      this.f_103886_ = p_171011_.m_171324_("body");
      this.f_171004_ = this.f_103886_.m_171324_("right_bottom_bristle");
      this.f_171005_ = this.f_103886_.m_171324_("right_middle_bristle");
      this.f_171006_ = this.f_103886_.m_171324_("right_top_bristle");
      this.f_171007_ = this.f_103886_.m_171324_("left_top_bristle");
      this.f_171008_ = this.f_103886_.m_171324_("left_middle_bristle");
      this.f_171009_ = this.f_103886_.m_171324_("left_bottom_bristle");
   }

   public static LayerDefinition m_171012_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.m_171419_(-4.0F, 8.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171481_(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.m_171419_(4.0F, 8.0F, 0.0F));
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      partdefinition1.m_171599_("right_bottom_bristle", CubeListBuilder.m_171558_().m_171514_(16, 65).m_171506_(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), PartPose.m_171423_(-8.0F, 4.0F, -8.0F, 0.0F, 0.0F, -1.2217305F));
      partdefinition1.m_171599_("right_middle_bristle", CubeListBuilder.m_171558_().m_171514_(16, 49).m_171506_(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), PartPose.m_171423_(-8.0F, -1.0F, -8.0F, 0.0F, 0.0F, -1.134464F));
      partdefinition1.m_171599_("right_top_bristle", CubeListBuilder.m_171558_().m_171514_(16, 33).m_171506_(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), PartPose.m_171423_(-8.0F, -5.0F, -8.0F, 0.0F, 0.0F, -0.87266463F));
      partdefinition1.m_171599_("left_top_bristle", CubeListBuilder.m_171558_().m_171514_(16, 33).m_171481_(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), PartPose.m_171423_(8.0F, -6.0F, -8.0F, 0.0F, 0.0F, 0.87266463F));
      partdefinition1.m_171599_("left_middle_bristle", CubeListBuilder.m_171558_().m_171514_(16, 49).m_171481_(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), PartPose.m_171423_(8.0F, -2.0F, -8.0F, 0.0F, 0.0F, 1.134464F));
      partdefinition1.m_171599_("left_bottom_bristle", CubeListBuilder.m_171558_().m_171514_(16, 65).m_171481_(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), PartPose.m_171423_(8.0F, 3.0F, -8.0F, 0.0F, 0.0F, 1.2217305F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 128);
   }

   public void m_6973_(Strider p_103903_, float p_103904_, float p_103905_, float p_103906_, float p_103907_, float p_103908_) {
      p_103905_ = Math.min(0.25F, p_103905_);
      if (!p_103903_.m_20160_()) {
         this.f_103886_.f_104203_ = p_103908_ * ((float)Math.PI / 180F);
         this.f_103886_.f_104204_ = p_103907_ * ((float)Math.PI / 180F);
      } else {
         this.f_103886_.f_104203_ = 0.0F;
         this.f_103886_.f_104204_ = 0.0F;
      }

      float f = 1.5F;
      this.f_103886_.f_104205_ = 0.1F * Mth.m_14031_(p_103904_ * 1.5F) * 4.0F * p_103905_;
      this.f_103886_.f_104201_ = 2.0F;
      this.f_103886_.f_104201_ -= 2.0F * Mth.m_14089_(p_103904_ * 1.5F) * 2.0F * p_103905_;
      this.f_103885_.f_104203_ = Mth.m_14031_(p_103904_ * 1.5F * 0.5F) * 2.0F * p_103905_;
      this.f_103884_.f_104203_ = Mth.m_14031_(p_103904_ * 1.5F * 0.5F + (float)Math.PI) * 2.0F * p_103905_;
      this.f_103885_.f_104205_ = 0.17453292F * Mth.m_14089_(p_103904_ * 1.5F * 0.5F) * p_103905_;
      this.f_103884_.f_104205_ = 0.17453292F * Mth.m_14089_(p_103904_ * 1.5F * 0.5F + (float)Math.PI) * p_103905_;
      this.f_103885_.f_104201_ = 8.0F + 2.0F * Mth.m_14031_(p_103904_ * 1.5F * 0.5F + (float)Math.PI) * 2.0F * p_103905_;
      this.f_103884_.f_104201_ = 8.0F + 2.0F * Mth.m_14031_(p_103904_ * 1.5F * 0.5F) * 2.0F * p_103905_;
      this.f_171004_.f_104205_ = -1.2217305F;
      this.f_171005_.f_104205_ = -1.134464F;
      this.f_171006_.f_104205_ = -0.87266463F;
      this.f_171007_.f_104205_ = 0.87266463F;
      this.f_171008_.f_104205_ = 1.134464F;
      this.f_171009_.f_104205_ = 1.2217305F;
      float f1 = Mth.m_14089_(p_103904_ * 1.5F + (float)Math.PI) * p_103905_;
      this.f_171004_.f_104205_ += f1 * 1.3F;
      this.f_171005_.f_104205_ += f1 * 1.2F;
      this.f_171006_.f_104205_ += f1 * 0.6F;
      this.f_171007_.f_104205_ += f1 * 0.6F;
      this.f_171008_.f_104205_ += f1 * 1.2F;
      this.f_171009_.f_104205_ += f1 * 1.3F;
      float f2 = 1.0F;
      float f3 = 1.0F;
      this.f_171004_.f_104205_ += 0.05F * Mth.m_14031_(p_103906_ * 1.0F * -0.4F);
      this.f_171005_.f_104205_ += 0.1F * Mth.m_14031_(p_103906_ * 1.0F * 0.2F);
      this.f_171006_.f_104205_ += 0.1F * Mth.m_14031_(p_103906_ * 1.0F * 0.4F);
      this.f_171007_.f_104205_ += 0.1F * Mth.m_14031_(p_103906_ * 1.0F * 0.4F);
      this.f_171008_.f_104205_ += 0.1F * Mth.m_14031_(p_103906_ * 1.0F * 0.2F);
      this.f_171009_.f_104205_ += 0.05F * Mth.m_14031_(p_103906_ * 1.0F * -0.4F);
   }

   public ModelPart m_142109_() {
      return this.f_171003_;
   }
}