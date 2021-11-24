package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AxolotlModel<T extends Axolotl & LerpingModel> extends AgeableListModel<T> {
   public static final float f_170358_ = 1.8849558F;
   private final ModelPart f_170359_;
   private final ModelPart f_170360_;
   private final ModelPart f_170361_;
   private final ModelPart f_170362_;
   private final ModelPart f_170363_;
   private final ModelPart f_170364_;
   private final ModelPart f_170365_;
   private final ModelPart f_170366_;
   private final ModelPart f_170367_;
   private final ModelPart f_170368_;

   public AxolotlModel(ModelPart p_170370_) {
      super(true, 8.0F, 3.35F);
      this.f_170364_ = p_170370_.m_171324_("body");
      this.f_170365_ = this.f_170364_.m_171324_("head");
      this.f_170361_ = this.f_170364_.m_171324_("right_hind_leg");
      this.f_170360_ = this.f_170364_.m_171324_("left_hind_leg");
      this.f_170363_ = this.f_170364_.m_171324_("right_front_leg");
      this.f_170362_ = this.f_170364_.m_171324_("left_front_leg");
      this.f_170359_ = this.f_170364_.m_171324_("tail");
      this.f_170366_ = this.f_170365_.m_171324_("top_gills");
      this.f_170367_ = this.f_170365_.m_171324_("left_gills");
      this.f_170368_ = this.f_170365_.m_171324_("right_gills");
   }

   public static LayerDefinition m_170417_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 11).m_171481_(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 10.0F).m_171514_(2, 17).m_171481_(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 9.0F), PartPose.m_171419_(0.0F, 20.0F, 5.0F));
      CubeDeformation cubedeformation = new CubeDeformation(0.001F);
      PartDefinition partdefinition2 = partdefinition1.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 1).m_171488_(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 5.0F, cubedeformation), PartPose.m_171419_(0.0F, 0.0F, -9.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(3, 37).m_171488_(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, cubedeformation);
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(0, 40).m_171488_(-3.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, cubedeformation);
      CubeListBuilder cubelistbuilder2 = CubeListBuilder.m_171558_().m_171514_(11, 40).m_171488_(0.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, cubedeformation);
      partdefinition2.m_171599_("top_gills", cubelistbuilder, PartPose.m_171419_(0.0F, -3.0F, -1.0F));
      partdefinition2.m_171599_("left_gills", cubelistbuilder1, PartPose.m_171419_(-4.0F, 0.0F, -1.0F));
      partdefinition2.m_171599_("right_gills", cubelistbuilder2, PartPose.m_171419_(4.0F, 0.0F, -1.0F));
      CubeListBuilder cubelistbuilder3 = CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-1.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, cubedeformation);
      CubeListBuilder cubelistbuilder4 = CubeListBuilder.m_171558_().m_171514_(2, 13).m_171488_(-2.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, cubedeformation);
      partdefinition1.m_171599_("right_hind_leg", cubelistbuilder4, PartPose.m_171419_(-3.5F, 1.0F, -1.0F));
      partdefinition1.m_171599_("left_hind_leg", cubelistbuilder3, PartPose.m_171419_(3.5F, 1.0F, -1.0F));
      partdefinition1.m_171599_("right_front_leg", cubelistbuilder4, PartPose.m_171419_(-3.5F, 1.0F, -8.0F));
      partdefinition1.m_171599_("left_front_leg", cubelistbuilder3, PartPose.m_171419_(3.5F, 1.0F, -8.0F));
      partdefinition1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(2, 19).m_171481_(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 12.0F), PartPose.m_171419_(0.0F, 0.0F, 1.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_170364_);
   }

   public void m_6973_(T p_170395_, float p_170396_, float p_170397_, float p_170398_, float p_170399_, float p_170400_) {
      this.m_170390_(p_170395_, p_170399_, p_170400_);
      if (p_170395_.m_149175_()) {
         this.m_170412_(p_170399_);
         this.m_170388_(p_170395_);
      } else {
         boolean flag = p_170395_.m_20184_().m_165925_() > 1.0E-7D || p_170395_.m_146909_() != p_170395_.f_19860_ || p_170395_.m_146908_() != p_170395_.f_19859_ || p_170395_.f_19790_ != p_170395_.m_20185_() || p_170395_.f_19792_ != p_170395_.m_20189_();
         if (p_170395_.m_20072_()) {
            if (flag) {
               this.m_170422_(p_170398_, p_170400_);
            } else {
               this.m_170372_(p_170398_);
            }

            this.m_170388_(p_170395_);
         } else {
            if (p_170395_.m_20096_()) {
               if (flag) {
                  this.m_170418_(p_170398_, p_170399_);
               } else {
                  this.m_170414_(p_170398_, p_170399_);
               }
            }

            this.m_170388_(p_170395_);
         }
      }
   }

   private void m_170388_(T p_170389_) {
      Map<String, Vector3f> map = p_170389_.m_142115_();
      map.put("body", this.m_170401_(this.f_170364_));
      map.put("head", this.m_170401_(this.f_170365_));
      map.put("right_hind_leg", this.m_170401_(this.f_170361_));
      map.put("left_hind_leg", this.m_170401_(this.f_170360_));
      map.put("right_front_leg", this.m_170401_(this.f_170363_));
      map.put("left_front_leg", this.m_170401_(this.f_170362_));
      map.put("tail", this.m_170401_(this.f_170359_));
      map.put("top_gills", this.m_170401_(this.f_170366_));
      map.put("left_gills", this.m_170401_(this.f_170367_));
      map.put("right_gills", this.m_170401_(this.f_170368_));
   }

   private Vector3f m_170401_(ModelPart p_170402_) {
      return new Vector3f(p_170402_.f_104203_, p_170402_.f_104204_, p_170402_.f_104205_);
   }

   private void m_170408_(ModelPart p_170409_, Vector3f p_170410_) {
      p_170409_.m_171327_(p_170410_.m_122239_(), p_170410_.m_122260_(), p_170410_.m_122269_());
   }

   private void m_170390_(T p_170391_, float p_170392_, float p_170393_) {
      this.f_170364_.f_104200_ = 0.0F;
      this.f_170365_.f_104201_ = 0.0F;
      this.f_170364_.f_104201_ = 20.0F;
      Map<String, Vector3f> map = p_170391_.m_142115_();
      if (map.isEmpty()) {
         this.f_170364_.m_171327_(p_170393_ * ((float)Math.PI / 180F), p_170392_ * ((float)Math.PI / 180F), 0.0F);
         this.f_170365_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170360_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170361_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170362_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170363_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170367_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170368_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170366_.m_171327_(0.0F, 0.0F, 0.0F);
         this.f_170359_.m_171327_(0.0F, 0.0F, 0.0F);
      } else {
         this.m_170408_(this.f_170364_, map.get("body"));
         this.m_170408_(this.f_170365_, map.get("head"));
         this.m_170408_(this.f_170360_, map.get("left_hind_leg"));
         this.m_170408_(this.f_170361_, map.get("right_hind_leg"));
         this.m_170408_(this.f_170362_, map.get("left_front_leg"));
         this.m_170408_(this.f_170363_, map.get("right_front_leg"));
         this.m_170408_(this.f_170367_, map.get("left_gills"));
         this.m_170408_(this.f_170368_, map.get("right_gills"));
         this.m_170408_(this.f_170366_, map.get("top_gills"));
         this.m_170408_(this.f_170359_, map.get("tail"));
      }

   }

   private float m_170374_(float p_170375_, float p_170376_) {
      return this.m_170377_(0.05F, p_170375_, p_170376_);
   }

   private float m_170377_(float p_170378_, float p_170379_, float p_170380_) {
      return Mth.m_14189_(p_170378_, p_170379_, p_170380_);
   }

   private void m_170403_(ModelPart p_170404_, float p_170405_, float p_170406_, float p_170407_) {
      p_170404_.m_171327_(this.m_170374_(p_170404_.f_104203_, p_170405_), this.m_170374_(p_170404_.f_104204_, p_170406_), this.m_170374_(p_170404_.f_104205_, p_170407_));
   }

   private void m_170414_(float p_170415_, float p_170416_) {
      float f = p_170415_ * 0.09F;
      float f1 = Mth.m_14031_(f);
      float f2 = Mth.m_14089_(f);
      float f3 = f1 * f1 - 2.0F * f1;
      float f4 = f2 * f2 - 3.0F * f1;
      this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, -0.09F * f3);
      this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0F);
      this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, -0.2F);
      this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, -0.1F + 0.1F * f3);
      this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.6F + 0.05F * f4);
      this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -this.f_170366_.f_104203_);
      this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
      this.m_170403_(this.f_170360_, 1.1F, 1.0F, 0.0F);
      this.m_170403_(this.f_170362_, 0.8F, 2.3F, -0.5F);
      this.m_170421_();
      this.f_170364_.f_104203_ = this.m_170377_(0.2F, this.f_170364_.f_104203_, 0.0F);
      this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170416_ * ((float)Math.PI / 180F));
      this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.0F);
   }

   private void m_170418_(float p_170419_, float p_170420_) {
      float f = p_170419_ * 0.11F;
      float f1 = Mth.m_14089_(f);
      float f2 = (f1 * f1 - 2.0F * f1) / 5.0F;
      float f3 = 0.7F * f1;
      this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, 0.0F);
      this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.09F * f1);
      this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0F);
      this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, this.f_170365_.f_104204_);
      this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.6F - 0.08F * (f1 * f1 + 2.0F * Mth.m_14031_(f)));
      this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -this.f_170366_.f_104203_);
      this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
      this.m_170403_(this.f_170360_, 0.9424779F, 1.5F - f2, -0.1F);
      this.m_170403_(this.f_170362_, 1.0995574F, ((float)Math.PI / 2F) - f3, 0.0F);
      this.m_170403_(this.f_170361_, this.f_170360_.f_104203_, -1.0F - f2, 0.0F);
      this.m_170403_(this.f_170363_, this.f_170362_.f_104203_, (-(float)Math.PI / 2F) - f3, 0.0F);
      this.f_170364_.f_104203_ = this.m_170377_(0.2F, this.f_170364_.f_104203_, 0.0F);
      this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170420_ * ((float)Math.PI / 180F));
      this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.0F);
   }

   private void m_170372_(float p_170373_) {
      float f = p_170373_ * 0.075F;
      float f1 = Mth.m_14089_(f);
      float f2 = Mth.m_14031_(f) * 0.15F;
      this.f_170364_.f_104203_ = this.m_170374_(this.f_170364_.f_104203_, -0.15F + 0.075F * f1);
      this.f_170364_.f_104201_ -= f2;
      this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, -this.f_170364_.f_104203_);
      this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, 0.2F * f1);
      this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, -0.3F * f1 - 0.19F);
      this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
      this.m_170403_(this.f_170360_, 2.3561945F - f1 * 0.11F, 0.47123894F, 1.7278761F);
      this.m_170403_(this.f_170362_, ((float)Math.PI / 4F) - f1 * 0.2F, 2.042035F, 0.0F);
      this.m_170421_();
      this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.5F * f1);
      this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0F);
      this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0F);
   }

   private void m_170422_(float p_170423_, float p_170424_) {
      float f = p_170423_ * 0.33F;
      float f1 = Mth.m_14031_(f);
      float f2 = Mth.m_14089_(f);
      float f3 = 0.13F * f1;
      this.f_170364_.f_104203_ = this.m_170377_(0.1F, this.f_170364_.f_104203_, p_170424_ * ((float)Math.PI / 180F) + f3);
      this.f_170365_.f_104203_ = -f3 * 1.8F;
      this.f_170364_.f_104201_ -= 0.45F * f2;
      this.f_170366_.f_104203_ = this.m_170374_(this.f_170366_.f_104203_, -0.5F * f1 - 0.8F);
      this.f_170367_.f_104204_ = this.m_170374_(this.f_170367_.f_104204_, 0.3F * f1 + 0.9F);
      this.f_170368_.f_104204_ = this.m_170374_(this.f_170368_.f_104204_, -this.f_170367_.f_104204_);
      this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.3F * Mth.m_14089_(f * 0.9F));
      this.m_170403_(this.f_170360_, 1.8849558F, -0.4F * f1, ((float)Math.PI / 2F));
      this.m_170403_(this.f_170362_, 1.8849558F, -0.2F * f2 - 0.1F, ((float)Math.PI / 2F));
      this.m_170421_();
      this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0F);
      this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0F);
   }

   private void m_170412_(float p_170413_) {
      this.m_170403_(this.f_170360_, 1.4137167F, 1.0995574F, ((float)Math.PI / 4F));
      this.m_170403_(this.f_170362_, ((float)Math.PI / 4F), 2.042035F, 0.0F);
      this.f_170364_.f_104203_ = this.m_170374_(this.f_170364_.f_104203_, -0.15F);
      this.f_170364_.f_104205_ = this.m_170374_(this.f_170364_.f_104205_, 0.35F);
      this.m_170421_();
      this.f_170364_.f_104204_ = this.m_170374_(this.f_170364_.f_104204_, p_170413_ * ((float)Math.PI / 180F));
      this.f_170365_.f_104203_ = this.m_170374_(this.f_170365_.f_104203_, 0.0F);
      this.f_170365_.f_104204_ = this.m_170374_(this.f_170365_.f_104204_, 0.0F);
      this.f_170365_.f_104205_ = this.m_170374_(this.f_170365_.f_104205_, 0.0F);
      this.f_170359_.f_104204_ = this.m_170374_(this.f_170359_.f_104204_, 0.0F);
      this.m_170403_(this.f_170366_, 0.0F, 0.0F, 0.0F);
      this.m_170403_(this.f_170367_, 0.0F, 0.0F, 0.0F);
      this.m_170403_(this.f_170368_, 0.0F, 0.0F, 0.0F);
   }

   private void m_170421_() {
      this.m_170403_(this.f_170361_, this.f_170360_.f_104203_, -this.f_170360_.f_104204_, -this.f_170360_.f_104205_);
      this.m_170403_(this.f_170363_, this.f_170362_.f_104203_, -this.f_170362_.f_104204_, -this.f_170362_.f_104205_);
   }
}