package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseModel<T extends AbstractHorse> extends AgeableListModel<T> {
   private static final float f_170648_ = 2.1816616F;
   private static final float f_170649_ = ((float)Math.PI / 3F);
   private static final float f_170650_ = ((float)Math.PI / 4F);
   private static final float f_170651_ = ((float)Math.PI / 6F);
   private static final float f_170652_ = 0.2617994F;
   protected static final String f_170647_ = "head_parts";
   private static final String f_170653_ = "left_hind_baby_leg";
   private static final String f_170654_ = "right_hind_baby_leg";
   private static final String f_170655_ = "left_front_baby_leg";
   private static final String f_170656_ = "right_front_baby_leg";
   private static final String f_170657_ = "saddle";
   private static final String f_170658_ = "left_saddle_mouth";
   private static final String f_170659_ = "left_saddle_line";
   private static final String f_170660_ = "right_saddle_mouth";
   private static final String f_170661_ = "right_saddle_line";
   private static final String f_170662_ = "head_saddle";
   private static final String f_170663_ = "mouth_saddle_wrap";
   protected final ModelPart f_102751_;
   protected final ModelPart f_102752_;
   private final ModelPart f_170664_;
   private final ModelPart f_170665_;
   private final ModelPart f_170666_;
   private final ModelPart f_170642_;
   private final ModelPart f_170643_;
   private final ModelPart f_170644_;
   private final ModelPart f_170645_;
   private final ModelPart f_170646_;
   private final ModelPart f_102761_;
   private final ModelPart[] f_102762_;
   private final ModelPart[] f_102763_;

   public HorseModel(ModelPart p_170668_) {
      super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
      this.f_102751_ = p_170668_.m_171324_("body");
      this.f_102752_ = p_170668_.m_171324_("head_parts");
      this.f_170664_ = p_170668_.m_171324_("right_hind_leg");
      this.f_170665_ = p_170668_.m_171324_("left_hind_leg");
      this.f_170666_ = p_170668_.m_171324_("right_front_leg");
      this.f_170642_ = p_170668_.m_171324_("left_front_leg");
      this.f_170643_ = p_170668_.m_171324_("right_hind_baby_leg");
      this.f_170644_ = p_170668_.m_171324_("left_hind_baby_leg");
      this.f_170645_ = p_170668_.m_171324_("right_front_baby_leg");
      this.f_170646_ = p_170668_.m_171324_("left_front_baby_leg");
      this.f_102761_ = this.f_102751_.m_171324_("tail");
      ModelPart modelpart = this.f_102751_.m_171324_("saddle");
      ModelPart modelpart1 = this.f_102752_.m_171324_("left_saddle_mouth");
      ModelPart modelpart2 = this.f_102752_.m_171324_("right_saddle_mouth");
      ModelPart modelpart3 = this.f_102752_.m_171324_("left_saddle_line");
      ModelPart modelpart4 = this.f_102752_.m_171324_("right_saddle_line");
      ModelPart modelpart5 = this.f_102752_.m_171324_("head_saddle");
      ModelPart modelpart6 = this.f_102752_.m_171324_("mouth_saddle_wrap");
      this.f_102762_ = new ModelPart[]{modelpart, modelpart1, modelpart2, modelpart5, modelpart6};
      this.f_102763_ = new ModelPart[]{modelpart3, modelpart4};
   }

   public static MeshDefinition m_170669_(CubeDeformation p_170670_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.m_171419_(0.0F, 11.0F, 5.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("head_parts", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171481_(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F), PartPose.m_171423_(0.0F, 4.0F, -12.0F, ((float)Math.PI / 6F), 0.0F, 0.0F));
      PartDefinition partdefinition3 = partdefinition2.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, p_170670_), PartPose.f_171404_);
      partdefinition2.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(56, 36).m_171488_(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, p_170670_), PartPose.f_171404_);
      partdefinition2.m_171599_("upper_mouth", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, p_170670_), PartPose.f_171404_);
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_170670_), PartPose.m_171419_(4.0F, 14.0F, 7.0F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, p_170670_), PartPose.m_171419_(-4.0F, 14.0F, 7.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_170670_), PartPose.m_171419_(4.0F, 14.0F, -12.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, p_170670_), PartPose.m_171419_(-4.0F, 14.0F, -12.0F));
      CubeDeformation cubedeformation = p_170670_.m_171471_(0.0F, 5.5F, 0.0F);
      partdefinition.m_171599_("left_hind_baby_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.m_171419_(4.0F, 14.0F, 7.0F));
      partdefinition.m_171599_("right_hind_baby_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.m_171419_(-4.0F, 14.0F, 7.0F));
      partdefinition.m_171599_("left_front_baby_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.m_171419_(4.0F, 14.0F, -12.0F));
      partdefinition.m_171599_("right_front_baby_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubedeformation), PartPose.m_171419_(-4.0F, 14.0F, -12.0F));
      partdefinition1.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(42, 36).m_171488_(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, p_170670_), PartPose.m_171423_(0.0F, -5.0F, 2.0F, ((float)Math.PI / 6F), 0.0F, 0.0F));
      partdefinition1.m_171599_("saddle", CubeListBuilder.m_171558_().m_171514_(26, 0).m_171488_(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.f_171404_);
      partdefinition2.m_171599_("left_saddle_mouth", CubeListBuilder.m_171558_().m_171514_(29, 5).m_171488_(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_170670_), PartPose.f_171404_);
      partdefinition2.m_171599_("right_saddle_mouth", CubeListBuilder.m_171558_().m_171514_(29, 5).m_171488_(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, p_170670_), PartPose.f_171404_);
      partdefinition2.m_171599_("left_saddle_line", CubeListBuilder.m_171558_().m_171514_(32, 2).m_171488_(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_170670_), PartPose.m_171430_((-(float)Math.PI / 6F), 0.0F, 0.0F));
      partdefinition2.m_171599_("right_saddle_line", CubeListBuilder.m_171558_().m_171514_(32, 2).m_171488_(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, p_170670_), PartPose.m_171430_((-(float)Math.PI / 6F), 0.0F, 0.0F));
      partdefinition2.m_171599_("head_saddle", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.f_171404_);
      partdefinition2.m_171599_("mouth_saddle_wrap", CubeListBuilder.m_171558_().m_171514_(19, 0).m_171488_(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.f_171404_);
      partdefinition3.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(19, 16).m_171488_(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.f_171404_);
      partdefinition3.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(19, 16).m_171488_(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.f_171404_);
      return meshdefinition;
   }

   public void m_6973_(T p_102785_, float p_102786_, float p_102787_, float p_102788_, float p_102789_, float p_102790_) {
      boolean flag = p_102785_.m_6254_();
      boolean flag1 = p_102785_.m_20160_();

      for(ModelPart modelpart : this.f_102762_) {
         modelpart.f_104207_ = flag;
      }

      for(ModelPart modelpart1 : this.f_102763_) {
         modelpart1.f_104207_ = flag1 && flag;
      }

      this.f_102751_.f_104201_ = 11.0F;
   }

   public Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102752_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102751_, this.f_170664_, this.f_170665_, this.f_170666_, this.f_170642_, this.f_170643_, this.f_170644_, this.f_170645_, this.f_170646_);
   }

   public void m_6839_(T p_102780_, float p_102781_, float p_102782_, float p_102783_) {
      super.m_6839_(p_102780_, p_102781_, p_102782_, p_102783_);
      float f = Mth.m_14201_(p_102780_.f_20884_, p_102780_.f_20883_, p_102783_);
      float f1 = Mth.m_14201_(p_102780_.f_20886_, p_102780_.f_20885_, p_102783_);
      float f2 = Mth.m_14179_(p_102783_, p_102780_.f_19860_, p_102780_.m_146909_());
      float f3 = f1 - f;
      float f4 = f2 * ((float)Math.PI / 180F);
      if (f3 > 20.0F) {
         f3 = 20.0F;
      }

      if (f3 < -20.0F) {
         f3 = -20.0F;
      }

      if (p_102782_ > 0.2F) {
         f4 += Mth.m_14089_(p_102781_ * 0.4F) * 0.15F * p_102782_;
      }

      float f5 = p_102780_.m_30663_(p_102783_);
      float f6 = p_102780_.m_30667_(p_102783_);
      float f7 = 1.0F - f6;
      float f8 = p_102780_.m_30533_(p_102783_);
      boolean flag = p_102780_.f_30517_ != 0;
      float f9 = (float)p_102780_.f_19797_ + p_102783_;
      this.f_102752_.f_104201_ = 4.0F;
      this.f_102752_.f_104202_ = -12.0F;
      this.f_102751_.f_104203_ = 0.0F;
      this.f_102752_.f_104203_ = ((float)Math.PI / 6F) + f4;
      this.f_102752_.f_104204_ = f3 * ((float)Math.PI / 180F);
      float f10 = p_102780_.m_20069_() ? 0.2F : 1.0F;
      float f11 = Mth.m_14089_(f10 * p_102781_ * 0.6662F + (float)Math.PI);
      float f12 = f11 * 0.8F * p_102782_;
      float f13 = (1.0F - Math.max(f6, f5)) * (((float)Math.PI / 6F) + f4 + f8 * Mth.m_14031_(f9) * 0.05F);
      this.f_102752_.f_104203_ = f6 * (0.2617994F + f4) + f5 * (2.1816616F + Mth.m_14031_(f9) * 0.05F) + f13;
      this.f_102752_.f_104204_ = f6 * f3 * ((float)Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.f_102752_.f_104204_;
      this.f_102752_.f_104201_ = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.f_102752_.f_104201_;
      this.f_102752_.f_104202_ = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.f_102752_.f_104202_;
      this.f_102751_.f_104203_ = f6 * (-(float)Math.PI / 4F) + f7 * this.f_102751_.f_104203_;
      float f14 = 0.2617994F * f6;
      float f15 = Mth.m_14089_(f9 * 0.6F + (float)Math.PI);
      this.f_170642_.f_104201_ = 2.0F * f6 + 14.0F * f7;
      this.f_170642_.f_104202_ = -6.0F * f6 - 10.0F * f7;
      this.f_170666_.f_104201_ = this.f_170642_.f_104201_;
      this.f_170666_.f_104202_ = this.f_170642_.f_104202_;
      float f16 = ((-(float)Math.PI / 3F) + f15) * f6 + f12 * f7;
      float f17 = ((-(float)Math.PI / 3F) - f15) * f6 - f12 * f7;
      this.f_170665_.f_104203_ = f14 - f11 * 0.5F * p_102782_ * f7;
      this.f_170664_.f_104203_ = f14 + f11 * 0.5F * p_102782_ * f7;
      this.f_170642_.f_104203_ = f16;
      this.f_170666_.f_104203_ = f17;
      this.f_102761_.f_104203_ = ((float)Math.PI / 6F) + p_102782_ * 0.75F;
      this.f_102761_.f_104201_ = -5.0F + p_102782_;
      this.f_102761_.f_104202_ = 2.0F + p_102782_ * 2.0F;
      if (flag) {
         this.f_102761_.f_104204_ = Mth.m_14089_(f9 * 0.7F);
      } else {
         this.f_102761_.f_104204_ = 0.0F;
      }

      this.f_170643_.f_104201_ = this.f_170664_.f_104201_;
      this.f_170643_.f_104202_ = this.f_170664_.f_104202_;
      this.f_170643_.f_104203_ = this.f_170664_.f_104203_;
      this.f_170644_.f_104201_ = this.f_170665_.f_104201_;
      this.f_170644_.f_104202_ = this.f_170665_.f_104202_;
      this.f_170644_.f_104203_ = this.f_170665_.f_104203_;
      this.f_170645_.f_104201_ = this.f_170666_.f_104201_;
      this.f_170645_.f_104202_ = this.f_170666_.f_104202_;
      this.f_170645_.f_104203_ = this.f_170666_.f_104203_;
      this.f_170646_.f_104201_ = this.f_170642_.f_104201_;
      this.f_170646_.f_104202_ = this.f_170642_.f_104202_;
      this.f_170646_.f_104203_ = this.f_170642_.f_104203_;
      boolean flag1 = p_102780_.m_6162_();
      this.f_170664_.f_104207_ = !flag1;
      this.f_170665_.f_104207_ = !flag1;
      this.f_170666_.f_104207_ = !flag1;
      this.f_170642_.f_104207_ = !flag1;
      this.f_170643_.f_104207_ = flag1;
      this.f_170644_.f_104207_ = flag1;
      this.f_170645_.f_104207_ = flag1;
      this.f_170646_.f_104207_ = flag1;
      this.f_102751_.f_104201_ = flag1 ? 10.8F : 0.0F;
   }
}