package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherBossModel<T extends WitherBoss> extends HierarchicalModel<T> {
   private static final String f_171057_ = "ribcage";
   private static final String f_171058_ = "center_head";
   private static final String f_171059_ = "right_head";
   private static final String f_171060_ = "left_head";
   private static final float f_171061_ = 0.065F;
   private static final float f_171062_ = 0.265F;
   private final ModelPart f_171063_;
   private final ModelPart f_171064_;
   private final ModelPart f_171065_;
   private final ModelPart f_171066_;
   private final ModelPart f_171067_;
   private final ModelPart f_171068_;

   public WitherBossModel(ModelPart p_171070_) {
      this.f_171063_ = p_171070_;
      this.f_171067_ = p_171070_.m_171324_("ribcage");
      this.f_171068_ = p_171070_.m_171324_("tail");
      this.f_171064_ = p_171070_.m_171324_("center_head");
      this.f_171065_ = p_171070_.m_171324_("right_head");
      this.f_171066_ = p_171070_.m_171324_("left_head");
   }

   public static LayerDefinition m_171075_(CubeDeformation p_171076_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("shoulders", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, p_171076_), PartPose.f_171404_);
      float f = 0.20420352F;
      partdefinition.m_171599_("ribcage", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, p_171076_).m_171514_(24, 22).m_171488_(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, p_171076_).m_171514_(24, 22).m_171488_(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, p_171076_).m_171514_(24, 22).m_171488_(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, p_171076_), PartPose.m_171423_(-2.0F, 6.9F, -0.5F, 0.20420352F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(12, 22).m_171488_(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, p_171076_), PartPose.m_171423_(-2.0F, 6.9F + Mth.m_14089_(0.20420352F) * 10.0F, -0.5F + Mth.m_14031_(0.20420352F) * 10.0F, 0.83252203F, 0.0F, 0.0F));
      partdefinition.m_171599_("center_head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_171076_), PartPose.f_171404_);
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, p_171076_);
      partdefinition.m_171599_("right_head", cubelistbuilder, PartPose.m_171419_(-8.0F, 4.0F, 0.0F));
      partdefinition.m_171599_("left_head", cubelistbuilder, PartPose.m_171419_(10.0F, 4.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_171063_;
   }

   public void m_6973_(T p_104100_, float p_104101_, float p_104102_, float p_104103_, float p_104104_, float p_104105_) {
      float f = Mth.m_14089_(p_104103_ * 0.1F);
      this.f_171067_.f_104203_ = (0.065F + 0.05F * f) * (float)Math.PI;
      this.f_171068_.m_104227_(-2.0F, 6.9F + Mth.m_14089_(this.f_171067_.f_104203_) * 10.0F, -0.5F + Mth.m_14031_(this.f_171067_.f_104203_) * 10.0F);
      this.f_171068_.f_104203_ = (0.265F + 0.1F * f) * (float)Math.PI;
      this.f_171064_.f_104204_ = p_104104_ * ((float)Math.PI / 180F);
      this.f_171064_.f_104203_ = p_104105_ * ((float)Math.PI / 180F);
   }

   public void m_6839_(T p_104095_, float p_104096_, float p_104097_, float p_104098_) {
      m_171071_(p_104095_, this.f_171065_, 0);
      m_171071_(p_104095_, this.f_171066_, 1);
   }

   private static <T extends WitherBoss> void m_171071_(T p_171072_, ModelPart p_171073_, int p_171074_) {
      p_171073_.f_104204_ = (p_171072_.m_31446_(p_171074_) - p_171072_.f_20883_) * ((float)Math.PI / 180F);
      p_171073_.f_104203_ = p_171072_.m_31480_(p_171074_) * ((float)Math.PI / 180F);
   }
}