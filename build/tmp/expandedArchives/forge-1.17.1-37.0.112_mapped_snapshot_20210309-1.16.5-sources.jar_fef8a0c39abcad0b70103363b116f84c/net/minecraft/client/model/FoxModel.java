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
import net.minecraft.world.entity.animal.Fox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxModel<T extends Fox> extends AgeableListModel<T> {
   public final ModelPart f_102638_;
   private final ModelPart f_102642_;
   private final ModelPart f_170558_;
   private final ModelPart f_170559_;
   private final ModelPart f_170560_;
   private final ModelPart f_170561_;
   private final ModelPart f_102647_;
   private static final int f_170562_ = 6;
   private static final float f_170563_ = 16.5F;
   private static final float f_170564_ = 17.5F;
   private float f_102648_;

   public FoxModel(ModelPart p_170566_) {
      super(true, 8.0F, 3.35F);
      this.f_102638_ = p_170566_.m_171324_("head");
      this.f_102642_ = p_170566_.m_171324_("body");
      this.f_170558_ = p_170566_.m_171324_("right_hind_leg");
      this.f_170559_ = p_170566_.m_171324_("left_hind_leg");
      this.f_170560_ = p_170566_.m_171324_("right_front_leg");
      this.f_170561_ = p_170566_.m_171324_("left_front_leg");
      this.f_102647_ = this.f_102642_.m_171324_("tail");
   }

   public static LayerDefinition m_170567_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(1, 5).m_171481_(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F), PartPose.m_171419_(-1.0F, 16.5F, -3.0F));
      partdefinition1.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(8, 1).m_171481_(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(15, 1).m_171481_(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("nose", CubeListBuilder.m_171558_().m_171514_(6, 18).m_171481_(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F), PartPose.f_171404_);
      PartDefinition partdefinition2 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(24, 15).m_171481_(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F, 6.0F), PartPose.m_171423_(0.0F, 16.0F, -6.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeDeformation cubedeformation = new CubeDeformation(0.001F);
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(4, 24).m_171488_(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, cubedeformation);
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(13, 24).m_171488_(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, cubedeformation);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder1, PartPose.m_171419_(-5.0F, 17.5F, 7.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(-1.0F, 17.5F, 7.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder1, PartPose.m_171419_(-5.0F, 17.5F, 0.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(-1.0F, 17.5F, 0.0F));
      partdefinition2.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(30, 0).m_171481_(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F), PartPose.m_171423_(-4.0F, 15.0F, -1.0F, -0.05235988F, 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 48, 32);
   }

   public void m_6839_(T p_102664_, float p_102665_, float p_102666_, float p_102667_) {
      this.f_102642_.f_104203_ = ((float)Math.PI / 2F);
      this.f_102647_.f_104203_ = -0.05235988F;
      this.f_170558_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662F) * 1.4F * p_102666_;
      this.f_170559_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662F + (float)Math.PI) * 1.4F * p_102666_;
      this.f_170560_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662F + (float)Math.PI) * 1.4F * p_102666_;
      this.f_170561_.f_104203_ = Mth.m_14089_(p_102665_ * 0.6662F) * 1.4F * p_102666_;
      this.f_102638_.m_104227_(-1.0F, 16.5F, -3.0F);
      this.f_102638_.f_104204_ = 0.0F;
      this.f_102638_.f_104205_ = p_102664_.m_28620_(p_102667_);
      this.f_170558_.f_104207_ = true;
      this.f_170559_.f_104207_ = true;
      this.f_170560_.f_104207_ = true;
      this.f_170561_.f_104207_ = true;
      this.f_102642_.m_104227_(0.0F, 16.0F, -6.0F);
      this.f_102642_.f_104205_ = 0.0F;
      this.f_170558_.m_104227_(-5.0F, 17.5F, 7.0F);
      this.f_170559_.m_104227_(-1.0F, 17.5F, 7.0F);
      if (p_102664_.m_6047_()) {
         this.f_102642_.f_104203_ = 1.6755161F;
         float f = p_102664_.m_28624_(p_102667_);
         this.f_102642_.m_104227_(0.0F, 16.0F + p_102664_.m_28624_(p_102667_), -6.0F);
         this.f_102638_.m_104227_(-1.0F, 16.5F + f, -3.0F);
         this.f_102638_.f_104204_ = 0.0F;
      } else if (p_102664_.m_5803_()) {
         this.f_102642_.f_104205_ = (-(float)Math.PI / 2F);
         this.f_102642_.m_104227_(0.0F, 21.0F, -6.0F);
         this.f_102647_.f_104203_ = -2.6179938F;
         if (this.f_102610_) {
            this.f_102647_.f_104203_ = -2.1816616F;
            this.f_102642_.m_104227_(0.0F, 21.0F, -2.0F);
         }

         this.f_102638_.m_104227_(1.0F, 19.49F, -3.0F);
         this.f_102638_.f_104203_ = 0.0F;
         this.f_102638_.f_104204_ = -2.0943952F;
         this.f_102638_.f_104205_ = 0.0F;
         this.f_170558_.f_104207_ = false;
         this.f_170559_.f_104207_ = false;
         this.f_170560_.f_104207_ = false;
         this.f_170561_.f_104207_ = false;
      } else if (p_102664_.m_28555_()) {
         this.f_102642_.f_104203_ = ((float)Math.PI / 6F);
         this.f_102642_.m_104227_(0.0F, 9.0F, -3.0F);
         this.f_102647_.f_104203_ = ((float)Math.PI / 4F);
         this.f_102647_.m_104227_(-4.0F, 15.0F, -2.0F);
         this.f_102638_.m_104227_(-1.0F, 10.0F, -0.25F);
         this.f_102638_.f_104203_ = 0.0F;
         this.f_102638_.f_104204_ = 0.0F;
         if (this.f_102610_) {
            this.f_102638_.m_104227_(-1.0F, 13.0F, -3.75F);
         }

         this.f_170558_.f_104203_ = -1.3089969F;
         this.f_170558_.m_104227_(-5.0F, 21.5F, 6.75F);
         this.f_170559_.f_104203_ = -1.3089969F;
         this.f_170559_.m_104227_(-1.0F, 21.5F, 6.75F);
         this.f_170560_.f_104203_ = -0.2617994F;
         this.f_170561_.f_104203_ = -0.2617994F;
      }

   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102638_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102642_, this.f_170558_, this.f_170559_, this.f_170560_, this.f_170561_);
   }

   public void m_6973_(T p_102669_, float p_102670_, float p_102671_, float p_102672_, float p_102673_, float p_102674_) {
      if (!p_102669_.m_5803_() && !p_102669_.m_28556_() && !p_102669_.m_6047_()) {
         this.f_102638_.f_104203_ = p_102674_ * ((float)Math.PI / 180F);
         this.f_102638_.f_104204_ = p_102673_ * ((float)Math.PI / 180F);
      }

      if (p_102669_.m_5803_()) {
         this.f_102638_.f_104203_ = 0.0F;
         this.f_102638_.f_104204_ = -2.0943952F;
         this.f_102638_.f_104205_ = Mth.m_14089_(p_102672_ * 0.027F) / 22.0F;
      }

      if (p_102669_.m_6047_()) {
         float f = Mth.m_14089_(p_102672_) * 0.01F;
         this.f_102642_.f_104204_ = f;
         this.f_170558_.f_104205_ = f;
         this.f_170559_.f_104205_ = f;
         this.f_170560_.f_104205_ = f / 2.0F;
         this.f_170561_.f_104205_ = f / 2.0F;
      }

      if (p_102669_.m_28556_()) {
         float f1 = 0.1F;
         this.f_102648_ += 0.67F;
         this.f_170558_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662F) * 0.1F;
         this.f_170559_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662F + (float)Math.PI) * 0.1F;
         this.f_170560_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662F + (float)Math.PI) * 0.1F;
         this.f_170561_.f_104203_ = Mth.m_14089_(this.f_102648_ * 0.4662F) * 0.1F;
      }

   }
}