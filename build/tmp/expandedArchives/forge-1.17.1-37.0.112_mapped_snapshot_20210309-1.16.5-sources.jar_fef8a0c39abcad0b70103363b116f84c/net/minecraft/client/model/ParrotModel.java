package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotModel extends HierarchicalModel<Parrot> {
   private static final String f_170773_ = "feather";
   private final ModelPart f_170774_;
   private final ModelPart f_103184_;
   private final ModelPart f_103185_;
   private final ModelPart f_170775_;
   private final ModelPart f_170776_;
   private final ModelPart f_103188_;
   private final ModelPart f_103192_;
   private final ModelPart f_170777_;
   private final ModelPart f_170778_;

   public ParrotModel(ModelPart p_170780_) {
      this.f_170774_ = p_170780_;
      this.f_103184_ = p_170780_.m_171324_("body");
      this.f_103185_ = p_170780_.m_171324_("tail");
      this.f_170775_ = p_170780_.m_171324_("left_wing");
      this.f_170776_ = p_170780_.m_171324_("right_wing");
      this.f_103188_ = p_170780_.m_171324_("head");
      this.f_103192_ = this.f_103188_.m_171324_("feather");
      this.f_170777_ = p_170780_.m_171324_("left_leg");
      this.f_170778_ = p_170780_.m_171324_("right_leg");
   }

   public static LayerDefinition m_170781_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(2, 8).m_171481_(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.m_171419_(0.0F, 16.5F, -3.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(22, 1).m_171481_(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F), PartPose.m_171419_(0.0F, 21.07F, 1.16F));
      partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(19, 8).m_171481_(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), PartPose.m_171419_(1.5F, 16.94F, -2.76F));
      partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(19, 8).m_171481_(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), PartPose.m_171419_(-1.5F, 16.94F, -2.76F));
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171481_(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.m_171419_(0.0F, 15.69F, -2.76F));
      partdefinition1.m_171599_("head2", CubeListBuilder.m_171558_().m_171514_(10, 0).m_171481_(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F), PartPose.m_171419_(0.0F, -2.0F, -1.0F));
      partdefinition1.m_171599_("beak1", CubeListBuilder.m_171558_().m_171514_(11, 7).m_171481_(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.m_171419_(0.0F, -0.5F, -1.5F));
      partdefinition1.m_171599_("beak2", CubeListBuilder.m_171558_().m_171514_(16, 7).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.m_171419_(0.0F, -1.75F, -2.45F));
      partdefinition1.m_171599_("feather", CubeListBuilder.m_171558_().m_171514_(2, 18).m_171481_(0.0F, -4.0F, -2.0F, 0.0F, 5.0F, 4.0F), PartPose.m_171419_(0.0F, -2.15F, 0.15F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(14, 18).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      partdefinition.m_171599_("left_leg", cubelistbuilder, PartPose.m_171419_(1.0F, 22.0F, -1.05F));
      partdefinition.m_171599_("right_leg", cubelistbuilder, PartPose.m_171419_(-1.0F, 22.0F, -1.05F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170774_;
   }

   public void m_6973_(Parrot p_103217_, float p_103218_, float p_103219_, float p_103220_, float p_103221_, float p_103222_) {
      this.m_103241_(m_103209_(p_103217_), p_103217_.f_19797_, p_103218_, p_103219_, p_103220_, p_103221_, p_103222_);
   }

   public void m_6839_(Parrot p_103212_, float p_103213_, float p_103214_, float p_103215_) {
      this.m_103239_(m_103209_(p_103212_));
   }

   public void m_103223_(PoseStack p_103224_, VertexConsumer p_103225_, int p_103226_, int p_103227_, float p_103228_, float p_103229_, float p_103230_, float p_103231_, int p_103232_) {
      this.m_103239_(ParrotModel.State.ON_SHOULDER);
      this.m_103241_(ParrotModel.State.ON_SHOULDER, p_103232_, p_103228_, p_103229_, 0.0F, p_103230_, p_103231_);
      this.f_170774_.m_104301_(p_103224_, p_103225_, p_103226_, p_103227_);
   }

   private void m_103241_(ParrotModel.State p_103242_, int p_103243_, float p_103244_, float p_103245_, float p_103246_, float p_103247_, float p_103248_) {
      this.f_103188_.f_104203_ = p_103248_ * ((float)Math.PI / 180F);
      this.f_103188_.f_104204_ = p_103247_ * ((float)Math.PI / 180F);
      this.f_103188_.f_104205_ = 0.0F;
      this.f_103188_.f_104200_ = 0.0F;
      this.f_103184_.f_104200_ = 0.0F;
      this.f_103185_.f_104200_ = 0.0F;
      this.f_170776_.f_104200_ = -1.5F;
      this.f_170775_.f_104200_ = 1.5F;
      switch(p_103242_) {
      case SITTING:
         break;
      case PARTY:
         float f = Mth.m_14089_((float)p_103243_);
         float f1 = Mth.m_14031_((float)p_103243_);
         this.f_103188_.f_104200_ = f;
         this.f_103188_.f_104201_ = 15.69F + f1;
         this.f_103188_.f_104203_ = 0.0F;
         this.f_103188_.f_104204_ = 0.0F;
         this.f_103188_.f_104205_ = Mth.m_14031_((float)p_103243_) * 0.4F;
         this.f_103184_.f_104200_ = f;
         this.f_103184_.f_104201_ = 16.5F + f1;
         this.f_170775_.f_104205_ = -0.0873F - p_103246_;
         this.f_170775_.f_104200_ = 1.5F + f;
         this.f_170775_.f_104201_ = 16.94F + f1;
         this.f_170776_.f_104205_ = 0.0873F + p_103246_;
         this.f_170776_.f_104200_ = -1.5F + f;
         this.f_170776_.f_104201_ = 16.94F + f1;
         this.f_103185_.f_104200_ = f;
         this.f_103185_.f_104201_ = 21.07F + f1;
         break;
      case STANDING:
         this.f_170777_.f_104203_ += Mth.m_14089_(p_103244_ * 0.6662F) * 1.4F * p_103245_;
         this.f_170778_.f_104203_ += Mth.m_14089_(p_103244_ * 0.6662F + (float)Math.PI) * 1.4F * p_103245_;
      case FLYING:
      case ON_SHOULDER:
      default:
         float f2 = p_103246_ * 0.3F;
         this.f_103188_.f_104201_ = 15.69F + f2;
         this.f_103185_.f_104203_ = 1.015F + Mth.m_14089_(p_103244_ * 0.6662F) * 0.3F * p_103245_;
         this.f_103185_.f_104201_ = 21.07F + f2;
         this.f_103184_.f_104201_ = 16.5F + f2;
         this.f_170775_.f_104205_ = -0.0873F - p_103246_;
         this.f_170775_.f_104201_ = 16.94F + f2;
         this.f_170776_.f_104205_ = 0.0873F + p_103246_;
         this.f_170776_.f_104201_ = 16.94F + f2;
         this.f_170777_.f_104201_ = 22.0F + f2;
         this.f_170778_.f_104201_ = 22.0F + f2;
      }

   }

   private void m_103239_(ParrotModel.State p_103240_) {
      this.f_103192_.f_104203_ = -0.2214F;
      this.f_103184_.f_104203_ = 0.4937F;
      this.f_170775_.f_104203_ = -0.6981F;
      this.f_170775_.f_104204_ = -(float)Math.PI;
      this.f_170776_.f_104203_ = -0.6981F;
      this.f_170776_.f_104204_ = -(float)Math.PI;
      this.f_170777_.f_104203_ = -0.0299F;
      this.f_170778_.f_104203_ = -0.0299F;
      this.f_170777_.f_104201_ = 22.0F;
      this.f_170778_.f_104201_ = 22.0F;
      this.f_170777_.f_104205_ = 0.0F;
      this.f_170778_.f_104205_ = 0.0F;
      switch(p_103240_) {
      case SITTING:
         float f = 1.9F;
         this.f_103188_.f_104201_ = 17.59F;
         this.f_103185_.f_104203_ = 1.5388988F;
         this.f_103185_.f_104201_ = 22.97F;
         this.f_103184_.f_104201_ = 18.4F;
         this.f_170775_.f_104205_ = -0.0873F;
         this.f_170775_.f_104201_ = 18.84F;
         this.f_170776_.f_104205_ = 0.0873F;
         this.f_170776_.f_104201_ = 18.84F;
         ++this.f_170777_.f_104201_;
         ++this.f_170778_.f_104201_;
         ++this.f_170777_.f_104203_;
         ++this.f_170778_.f_104203_;
         break;
      case PARTY:
         this.f_170777_.f_104205_ = -0.34906584F;
         this.f_170778_.f_104205_ = 0.34906584F;
      case STANDING:
      case ON_SHOULDER:
      default:
         break;
      case FLYING:
         this.f_170777_.f_104203_ += 0.6981317F;
         this.f_170778_.f_104203_ += 0.6981317F;
      }

   }

   private static ParrotModel.State m_103209_(Parrot p_103210_) {
      if (p_103210_.m_29439_()) {
         return ParrotModel.State.PARTY;
      } else if (p_103210_.m_21825_()) {
         return ParrotModel.State.SITTING;
      } else {
         return p_103210_.m_142592_() ? ParrotModel.State.FLYING : ParrotModel.State.STANDING;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum State {
      FLYING,
      STANDING,
      SITTING,
      PARTY,
      ON_SHOULDER;
   }
}