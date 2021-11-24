package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RavagerModel extends HierarchicalModel<Ravager> {
   private final ModelPart f_170883_;
   private final ModelPart f_103598_;
   private final ModelPart f_103599_;
   private final ModelPart f_170884_;
   private final ModelPart f_170885_;
   private final ModelPart f_170886_;
   private final ModelPart f_170887_;
   private final ModelPart f_103605_;

   public RavagerModel(ModelPart p_170889_) {
      this.f_170883_ = p_170889_;
      this.f_103605_ = p_170889_.m_171324_("neck");
      this.f_103598_ = this.f_103605_.m_171324_("head");
      this.f_103599_ = this.f_103598_.m_171324_("mouth");
      this.f_170884_ = p_170889_.m_171324_("right_hind_leg");
      this.f_170885_ = p_170889_.m_171324_("left_hind_leg");
      this.f_170886_ = p_170889_.m_171324_("right_front_leg");
      this.f_170887_ = p_170889_.m_171324_("left_front_leg");
   }

   public static LayerDefinition m_170890_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 16;
      PartDefinition partdefinition1 = partdefinition.m_171599_("neck", CubeListBuilder.m_171558_().m_171514_(68, 73).m_171481_(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F), PartPose.m_171419_(0.0F, -7.0F, 5.5F));
      PartDefinition partdefinition2 = partdefinition1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F).m_171514_(0, 0).m_171481_(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F), PartPose.m_171419_(0.0F, 16.0F, -17.0F));
      partdefinition2.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171481_(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), PartPose.m_171423_(-10.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
      partdefinition2.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(74, 55).m_171480_().m_171481_(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), PartPose.m_171423_(8.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
      partdefinition2.m_171599_("mouth", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171481_(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F), PartPose.m_171419_(0.0F, -2.0F, 2.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 55).m_171481_(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F).m_171514_(0, 91).m_171481_(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F), PartPose.m_171423_(0.0F, 1.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(96, 0).m_171481_(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.m_171419_(-8.0F, -13.0F, 18.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(96, 0).m_171480_().m_171481_(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.m_171419_(8.0F, -13.0F, 18.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(64, 0).m_171481_(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.m_171419_(-8.0F, -13.0F, -5.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(64, 0).m_171480_().m_171481_(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.m_171419_(8.0F, -13.0F, -5.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public ModelPart m_142109_() {
      return this.f_170883_;
   }

   public void m_6973_(Ravager p_103626_, float p_103627_, float p_103628_, float p_103629_, float p_103630_, float p_103631_) {
      this.f_103598_.f_104203_ = p_103631_ * ((float)Math.PI / 180F);
      this.f_103598_.f_104204_ = p_103630_ * ((float)Math.PI / 180F);
      float f = 0.4F * p_103628_;
      this.f_170884_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662F) * f;
      this.f_170885_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662F + (float)Math.PI) * f;
      this.f_170886_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662F + (float)Math.PI) * f;
      this.f_170887_.f_104203_ = Mth.m_14089_(p_103627_ * 0.6662F) * f;
   }

   public void m_6839_(Ravager p_103621_, float p_103622_, float p_103623_, float p_103624_) {
      super.m_6839_(p_103621_, p_103622_, p_103623_, p_103624_);
      int i = p_103621_.m_33364_();
      int j = p_103621_.m_33366_();
      int k = 20;
      int l = p_103621_.m_33362_();
      int i1 = 10;
      if (l > 0) {
         float f = Mth.m_14156_((float)l - p_103624_, 10.0F);
         float f1 = (1.0F + f) * 0.5F;
         float f2 = f1 * f1 * f1 * 12.0F;
         float f3 = f2 * Mth.m_14031_(this.f_103605_.f_104203_);
         this.f_103605_.f_104202_ = -6.5F + f2;
         this.f_103605_.f_104201_ = -7.0F - f3;
         float f4 = Mth.m_14031_(((float)l - p_103624_) / 10.0F * (float)Math.PI * 0.25F);
         this.f_103599_.f_104203_ = ((float)Math.PI / 2F) * f4;
         if (l > 5) {
            this.f_103599_.f_104203_ = Mth.m_14031_(((float)(-4 + l) - p_103624_) / 4.0F) * (float)Math.PI * 0.4F;
         } else {
            this.f_103599_.f_104203_ = 0.15707964F * Mth.m_14031_((float)Math.PI * ((float)l - p_103624_) / 10.0F);
         }
      } else {
         float f5 = -1.0F;
         float f6 = -1.0F * Mth.m_14031_(this.f_103605_.f_104203_);
         this.f_103605_.f_104200_ = 0.0F;
         this.f_103605_.f_104201_ = -7.0F - f6;
         this.f_103605_.f_104202_ = 5.5F;
         boolean flag = i > 0;
         this.f_103605_.f_104203_ = flag ? 0.21991149F : 0.0F;
         this.f_103599_.f_104203_ = (float)Math.PI * (flag ? 0.05F : 0.01F);
         if (flag) {
            double d0 = (double)i / 40.0D;
            this.f_103605_.f_104200_ = (float)Math.sin(d0 * 10.0D) * 3.0F;
         } else if (j > 0) {
            float f7 = Mth.m_14031_(((float)(20 - j) - p_103624_) / 20.0F * (float)Math.PI * 0.25F);
            this.f_103599_.f_104203_ = ((float)Math.PI / 2F) * f7;
         }
      }

   }
}