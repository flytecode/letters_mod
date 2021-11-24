package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfModel<T extends Wolf> extends ColorableAgeableListModel<T> {
   private static final String f_171078_ = "real_head";
   private static final String f_171079_ = "upper_body";
   private static final String f_171080_ = "real_tail";
   private final ModelPart f_104107_;
   private final ModelPart f_104108_;
   private final ModelPart f_104109_;
   private final ModelPart f_171081_;
   private final ModelPart f_171082_;
   private final ModelPart f_171083_;
   private final ModelPart f_171084_;
   private final ModelPart f_104114_;
   private final ModelPart f_104115_;
   private final ModelPart f_104116_;
   private static final int f_171085_ = 8;

   public WolfModel(ModelPart p_171087_) {
      this.f_104107_ = p_171087_.m_171324_("head");
      this.f_104108_ = this.f_104107_.m_171324_("real_head");
      this.f_104109_ = p_171087_.m_171324_("body");
      this.f_104116_ = p_171087_.m_171324_("upper_body");
      this.f_171081_ = p_171087_.m_171324_("right_hind_leg");
      this.f_171082_ = p_171087_.m_171324_("left_hind_leg");
      this.f_171083_ = p_171087_.m_171324_("right_front_leg");
      this.f_171084_ = p_171087_.m_171324_("left_front_leg");
      this.f_104114_ = p_171087_.m_171324_("tail");
      this.f_104115_ = this.f_104114_.m_171324_("real_tail");
   }

   public static LayerDefinition m_171088_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = 13.5F;
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(-1.0F, 13.5F, -7.0F));
      partdefinition1.m_171599_("real_head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).m_171514_(16, 14).m_171481_(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).m_171514_(16, 14).m_171481_(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).m_171514_(0, 10).m_171481_(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.f_171404_);
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(18, 14).m_171481_(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), PartPose.m_171423_(0.0F, 14.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("upper_body", CubeListBuilder.m_171558_().m_171514_(21, 0).m_171481_(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), PartPose.m_171423_(-1.0F, 14.0F, -3.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 18).m_171481_(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-2.5F, 16.0F, 7.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(0.5F, 16.0F, 7.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-2.5F, 16.0F, -4.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(0.5F, 16.0F, -4.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("tail", CubeListBuilder.m_171558_(), PartPose.m_171423_(-1.0F, 12.0F, 8.0F, ((float)Math.PI / 5F), 0.0F, 0.0F));
      partdefinition2.m_171599_("real_tail", CubeListBuilder.m_171558_().m_171514_(9, 18).m_171481_(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_104107_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_104109_, this.f_171081_, this.f_171082_, this.f_171083_, this.f_171084_, this.f_104114_, this.f_104116_);
   }

   public void m_6839_(T p_104132_, float p_104133_, float p_104134_, float p_104135_) {
      if (p_104132_.m_21660_()) {
         this.f_104114_.f_104204_ = 0.0F;
      } else {
         this.f_104114_.f_104204_ = Mth.m_14089_(p_104133_ * 0.6662F) * 1.4F * p_104134_;
      }

      if (p_104132_.m_21825_()) {
         this.f_104116_.m_104227_(-1.0F, 16.0F, -3.0F);
         this.f_104116_.f_104203_ = 1.2566371F;
         this.f_104116_.f_104204_ = 0.0F;
         this.f_104109_.m_104227_(0.0F, 18.0F, 0.0F);
         this.f_104109_.f_104203_ = ((float)Math.PI / 4F);
         this.f_104114_.m_104227_(-1.0F, 21.0F, 6.0F);
         this.f_171081_.m_104227_(-2.5F, 22.7F, 2.0F);
         this.f_171081_.f_104203_ = ((float)Math.PI * 1.5F);
         this.f_171082_.m_104227_(0.5F, 22.7F, 2.0F);
         this.f_171082_.f_104203_ = ((float)Math.PI * 1.5F);
         this.f_171083_.f_104203_ = 5.811947F;
         this.f_171083_.m_104227_(-2.49F, 17.0F, -4.0F);
         this.f_171084_.f_104203_ = 5.811947F;
         this.f_171084_.m_104227_(0.51F, 17.0F, -4.0F);
      } else {
         this.f_104109_.m_104227_(0.0F, 14.0F, 2.0F);
         this.f_104109_.f_104203_ = ((float)Math.PI / 2F);
         this.f_104116_.m_104227_(-1.0F, 14.0F, -3.0F);
         this.f_104116_.f_104203_ = this.f_104109_.f_104203_;
         this.f_104114_.m_104227_(-1.0F, 12.0F, 8.0F);
         this.f_171081_.m_104227_(-2.5F, 16.0F, 7.0F);
         this.f_171082_.m_104227_(0.5F, 16.0F, 7.0F);
         this.f_171083_.m_104227_(-2.5F, 16.0F, -4.0F);
         this.f_171084_.m_104227_(0.5F, 16.0F, -4.0F);
         this.f_171081_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662F) * 1.4F * p_104134_;
         this.f_171082_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662F + (float)Math.PI) * 1.4F * p_104134_;
         this.f_171083_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662F + (float)Math.PI) * 1.4F * p_104134_;
         this.f_171084_.f_104203_ = Mth.m_14089_(p_104133_ * 0.6662F) * 1.4F * p_104134_;
      }

      this.f_104108_.f_104205_ = p_104132_.m_30448_(p_104135_) + p_104132_.m_30432_(p_104135_, 0.0F);
      this.f_104116_.f_104205_ = p_104132_.m_30432_(p_104135_, -0.08F);
      this.f_104109_.f_104205_ = p_104132_.m_30432_(p_104135_, -0.16F);
      this.f_104115_.f_104205_ = p_104132_.m_30432_(p_104135_, -0.2F);
   }

   public void m_6973_(T p_104137_, float p_104138_, float p_104139_, float p_104140_, float p_104141_, float p_104142_) {
      this.f_104107_.f_104203_ = p_104142_ * ((float)Math.PI / 180F);
      this.f_104107_.f_104204_ = p_104141_ * ((float)Math.PI / 180F);
      this.f_104114_.f_104203_ = p_104140_;
   }
}