package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PandaModel<T extends Panda> extends QuadrupedModel<T> {
   private float f_103154_;
   private float f_103155_;
   private float f_103156_;

   public PandaModel(ModelPart p_170771_) {
      super(p_170771_, true, 23.0F, 4.8F, 2.7F, 3.0F, 49);
   }

   public static LayerDefinition m_170772_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171481_(-6.5F, -5.0F, -4.0F, 13.0F, 10.0F, 9.0F).m_171514_(45, 16).m_171517_("nose", -3.5F, 0.0F, -6.0F, 7.0F, 5.0F, 2.0F).m_171514_(52, 25).m_171517_("left_ear", 3.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F).m_171514_(52, 25).m_171517_("right_ear", -8.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F), PartPose.m_171419_(0.0F, 11.5F, -17.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171481_(-9.5F, -13.0F, -6.5F, 19.0F, 26.0F, 13.0F), PartPose.m_171423_(0.0F, 10.0F, 0.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      int i = 9;
      int j = 6;
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-5.5F, 15.0F, 9.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(5.5F, 15.0F, 9.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-5.5F, 15.0F, -9.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(5.5F, 15.0F, -9.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6839_(T p_103173_, float p_103174_, float p_103175_, float p_103176_) {
      super.m_6839_(p_103173_, p_103174_, p_103175_, p_103176_);
      this.f_103154_ = p_103173_.m_29224_(p_103176_);
      this.f_103155_ = p_103173_.m_29226_(p_103176_);
      this.f_103156_ = p_103173_.m_6162_() ? 0.0F : p_103173_.m_29088_(p_103176_);
   }

   public void m_6973_(T p_103178_, float p_103179_, float p_103180_, float p_103181_, float p_103182_, float p_103183_) {
      super.m_6973_(p_103178_, p_103179_, p_103180_, p_103181_, p_103182_, p_103183_);
      boolean flag = p_103178_.m_29148_() > 0;
      boolean flag1 = p_103178_.m_29149_();
      int i = p_103178_.m_29153_();
      boolean flag2 = p_103178_.m_29152_();
      boolean flag3 = p_103178_.m_29165_();
      if (flag) {
         this.f_103492_.f_104204_ = 0.35F * Mth.m_14031_(0.6F * p_103181_);
         this.f_103492_.f_104205_ = 0.35F * Mth.m_14031_(0.6F * p_103181_);
         this.f_170854_.f_104203_ = -0.75F * Mth.m_14031_(0.3F * p_103181_);
         this.f_170855_.f_104203_ = 0.75F * Mth.m_14031_(0.3F * p_103181_);
      } else {
         this.f_103492_.f_104205_ = 0.0F;
      }

      if (flag1) {
         if (i < 15) {
            this.f_103492_.f_104203_ = (-(float)Math.PI / 4F) * (float)i / 14.0F;
         } else if (i < 20) {
            float f = (float)((i - 15) / 5);
            this.f_103492_.f_104203_ = (-(float)Math.PI / 4F) + ((float)Math.PI / 4F) * f;
         }
      }

      if (this.f_103154_ > 0.0F) {
         this.f_103493_.f_104203_ = ModelUtils.m_103125_(this.f_103493_.f_104203_, 1.7407963F, this.f_103154_);
         this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, ((float)Math.PI / 2F), this.f_103154_);
         this.f_170854_.f_104205_ = -0.27079642F;
         this.f_170855_.f_104205_ = 0.27079642F;
         this.f_170852_.f_104205_ = 0.5707964F;
         this.f_170853_.f_104205_ = -0.5707964F;
         if (flag2) {
            this.f_103492_.f_104203_ = ((float)Math.PI / 2F) + 0.2F * Mth.m_14031_(p_103181_ * 0.6F);
            this.f_170854_.f_104203_ = -0.4F - 0.2F * Mth.m_14031_(p_103181_ * 0.6F);
            this.f_170855_.f_104203_ = -0.4F - 0.2F * Mth.m_14031_(p_103181_ * 0.6F);
         }

         if (flag3) {
            this.f_103492_.f_104203_ = 2.1707964F;
            this.f_170854_.f_104203_ = -0.9F;
            this.f_170855_.f_104203_ = -0.9F;
         }
      } else {
         this.f_170852_.f_104205_ = 0.0F;
         this.f_170853_.f_104205_ = 0.0F;
         this.f_170854_.f_104205_ = 0.0F;
         this.f_170855_.f_104205_ = 0.0F;
      }

      if (this.f_103155_ > 0.0F) {
         this.f_170852_.f_104203_ = -0.6F * Mth.m_14031_(p_103181_ * 0.15F);
         this.f_170853_.f_104203_ = 0.6F * Mth.m_14031_(p_103181_ * 0.15F);
         this.f_170854_.f_104203_ = 0.3F * Mth.m_14031_(p_103181_ * 0.25F);
         this.f_170855_.f_104203_ = -0.3F * Mth.m_14031_(p_103181_ * 0.25F);
         this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, ((float)Math.PI / 2F), this.f_103155_);
      }

      if (this.f_103156_ > 0.0F) {
         this.f_103492_.f_104203_ = ModelUtils.m_103125_(this.f_103492_.f_104203_, 2.0561945F, this.f_103156_);
         this.f_170852_.f_104203_ = -0.5F * Mth.m_14031_(p_103181_ * 0.5F);
         this.f_170853_.f_104203_ = 0.5F * Mth.m_14031_(p_103181_ * 0.5F);
         this.f_170854_.f_104203_ = 0.5F * Mth.m_14031_(p_103181_ * 0.5F);
         this.f_170855_.f_104203_ = -0.5F * Mth.m_14031_(p_103181_ * 0.5F);
      }

   }
}