package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeModel<T extends Bee> extends AgeableListModel<T> {
   private static final float f_170430_ = 19.0F;
   private static final String f_170431_ = "bone";
   private static final String f_170432_ = "stinger";
   private static final String f_170433_ = "left_antenna";
   private static final String f_170434_ = "right_antenna";
   private static final String f_170435_ = "front_legs";
   private static final String f_170436_ = "middle_legs";
   private static final String f_170437_ = "back_legs";
   private final ModelPart f_102206_;
   private final ModelPart f_102208_;
   private final ModelPart f_102209_;
   private final ModelPart f_102210_;
   private final ModelPart f_102211_;
   private final ModelPart f_102212_;
   private final ModelPart f_102213_;
   private final ModelPart f_102214_;
   private final ModelPart f_102215_;
   private float f_102216_;

   public BeeModel(ModelPart p_170439_) {
      super(false, 24.0F, 0.0F);
      this.f_102206_ = p_170439_.m_171324_("bone");
      ModelPart modelpart = this.f_102206_.m_171324_("body");
      this.f_102213_ = modelpart.m_171324_("stinger");
      this.f_102214_ = modelpart.m_171324_("left_antenna");
      this.f_102215_ = modelpart.m_171324_("right_antenna");
      this.f_102208_ = this.f_102206_.m_171324_("right_wing");
      this.f_102209_ = this.f_102206_.m_171324_("left_wing");
      this.f_102210_ = this.f_102206_.m_171324_("front_legs");
      this.f_102211_ = this.f_102206_.m_171324_("middle_legs");
      this.f_102212_ = this.f_102206_.m_171324_("back_legs");
   }

   public static LayerDefinition m_170440_() {
      float f = 19.0F;
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("bone", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 19.0F, 0.0F));
      PartDefinition partdefinition2 = partdefinition1.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F), PartPose.f_171404_);
      partdefinition2.m_171599_("stinger", CubeListBuilder.m_171558_().m_171514_(26, 7).m_171481_(0.0F, -1.0F, 5.0F, 0.0F, 1.0F, 2.0F), PartPose.f_171404_);
      partdefinition2.m_171599_("left_antenna", CubeListBuilder.m_171558_().m_171514_(2, 0).m_171481_(1.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), PartPose.m_171419_(0.0F, -2.0F, -5.0F));
      partdefinition2.m_171599_("right_antenna", CubeListBuilder.m_171558_().m_171514_(2, 3).m_171481_(-2.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), PartPose.m_171419_(0.0F, -2.0F, -5.0F));
      CubeDeformation cubedeformation = new CubeDeformation(0.001F);
      partdefinition1.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, cubedeformation), PartPose.m_171423_(-1.5F, -4.0F, -3.0F, 0.0F, -0.2618F, 0.0F));
      partdefinition1.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(0, 18).m_171480_().m_171488_(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, cubedeformation), PartPose.m_171423_(1.5F, -4.0F, -3.0F, 0.0F, 0.2618F, 0.0F));
      partdefinition1.m_171599_("front_legs", CubeListBuilder.m_171558_().m_171534_("front_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 1), PartPose.m_171419_(1.5F, 3.0F, -2.0F));
      partdefinition1.m_171599_("middle_legs", CubeListBuilder.m_171558_().m_171534_("middle_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 3), PartPose.m_171419_(1.5F, 3.0F, 0.0F));
      partdefinition1.m_171599_("back_legs", CubeListBuilder.m_171558_().m_171534_("back_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 5), PartPose.m_171419_(1.5F, 3.0F, 2.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6839_(T p_102232_, float p_102233_, float p_102234_, float p_102235_) {
      super.m_6839_(p_102232_, p_102233_, p_102234_, p_102235_);
      this.f_102216_ = p_102232_.m_27935_(p_102235_);
      this.f_102213_.f_104207_ = !p_102232_.m_27857_();
   }

   public void m_6973_(T p_102237_, float p_102238_, float p_102239_, float p_102240_, float p_102241_, float p_102242_) {
      this.f_102208_.f_104203_ = 0.0F;
      this.f_102214_.f_104203_ = 0.0F;
      this.f_102215_.f_104203_ = 0.0F;
      this.f_102206_.f_104203_ = 0.0F;
      boolean flag = p_102237_.m_20096_() && p_102237_.m_20184_().m_82556_() < 1.0E-7D;
      if (flag) {
         this.f_102208_.f_104204_ = -0.2618F;
         this.f_102208_.f_104205_ = 0.0F;
         this.f_102209_.f_104203_ = 0.0F;
         this.f_102209_.f_104204_ = 0.2618F;
         this.f_102209_.f_104205_ = 0.0F;
         this.f_102210_.f_104203_ = 0.0F;
         this.f_102211_.f_104203_ = 0.0F;
         this.f_102212_.f_104203_ = 0.0F;
      } else {
         float f = p_102240_ * 120.32113F * ((float)Math.PI / 180F);
         this.f_102208_.f_104204_ = 0.0F;
         this.f_102208_.f_104205_ = Mth.m_14089_(f) * (float)Math.PI * 0.15F;
         this.f_102209_.f_104203_ = this.f_102208_.f_104203_;
         this.f_102209_.f_104204_ = this.f_102208_.f_104204_;
         this.f_102209_.f_104205_ = -this.f_102208_.f_104205_;
         this.f_102210_.f_104203_ = ((float)Math.PI / 4F);
         this.f_102211_.f_104203_ = ((float)Math.PI / 4F);
         this.f_102212_.f_104203_ = ((float)Math.PI / 4F);
         this.f_102206_.f_104203_ = 0.0F;
         this.f_102206_.f_104204_ = 0.0F;
         this.f_102206_.f_104205_ = 0.0F;
      }

      if (!p_102237_.m_21660_()) {
         this.f_102206_.f_104203_ = 0.0F;
         this.f_102206_.f_104204_ = 0.0F;
         this.f_102206_.f_104205_ = 0.0F;
         if (!flag) {
            float f1 = Mth.m_14089_(p_102240_ * 0.18F);
            this.f_102206_.f_104203_ = 0.1F + f1 * (float)Math.PI * 0.025F;
            this.f_102214_.f_104203_ = f1 * (float)Math.PI * 0.03F;
            this.f_102215_.f_104203_ = f1 * (float)Math.PI * 0.03F;
            this.f_102210_.f_104203_ = -f1 * (float)Math.PI * 0.1F + ((float)Math.PI / 8F);
            this.f_102212_.f_104203_ = -f1 * (float)Math.PI * 0.05F + ((float)Math.PI / 4F);
            this.f_102206_.f_104201_ = 19.0F - Mth.m_14089_(p_102240_ * 0.18F) * 0.9F;
         }
      }

      if (this.f_102216_ > 0.0F) {
         this.f_102206_.f_104203_ = ModelUtils.m_103125_(this.f_102206_.f_104203_, 3.0915928F, this.f_102216_);
      }

   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102206_);
   }
}