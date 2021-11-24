package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowGolemModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170959_ = "upper_body";
   private final ModelPart f_170960_;
   private final ModelPart f_170961_;
   private final ModelPart f_103839_;
   private final ModelPart f_170962_;
   private final ModelPart f_170963_;

   public SnowGolemModel(ModelPart p_170965_) {
      this.f_170960_ = p_170965_;
      this.f_103839_ = p_170965_.m_171324_("head");
      this.f_170962_ = p_170965_.m_171324_("left_arm");
      this.f_170963_ = p_170965_.m_171324_("right_arm");
      this.f_170961_ = p_170965_.m_171324_("upper_body");
   }

   public static LayerDefinition m_170966_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = 4.0F;
      CubeDeformation cubedeformation = new CubeDeformation(-0.5F);
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubedeformation), PartPose.m_171419_(0.0F, 4.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, cubedeformation);
      partdefinition.m_171599_("left_arm", cubelistbuilder, PartPose.m_171423_(5.0F, 6.0F, 1.0F, 0.0F, 0.0F, 1.0F));
      partdefinition.m_171599_("right_arm", cubelistbuilder, PartPose.m_171423_(-5.0F, 6.0F, -1.0F, 0.0F, (float)Math.PI, -1.0F));
      partdefinition.m_171599_("upper_body", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, cubedeformation), PartPose.m_171419_(0.0F, 13.0F, 0.0F));
      partdefinition.m_171599_("lower_body", CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, cubedeformation), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T p_103845_, float p_103846_, float p_103847_, float p_103848_, float p_103849_, float p_103850_) {
      this.f_103839_.f_104204_ = p_103849_ * ((float)Math.PI / 180F);
      this.f_103839_.f_104203_ = p_103850_ * ((float)Math.PI / 180F);
      this.f_170961_.f_104204_ = p_103849_ * ((float)Math.PI / 180F) * 0.25F;
      float f = Mth.m_14031_(this.f_170961_.f_104204_);
      float f1 = Mth.m_14089_(this.f_170961_.f_104204_);
      this.f_170962_.f_104204_ = this.f_170961_.f_104204_;
      this.f_170963_.f_104204_ = this.f_170961_.f_104204_ + (float)Math.PI;
      this.f_170962_.f_104200_ = f1 * 5.0F;
      this.f_170962_.f_104202_ = -f * 5.0F;
      this.f_170963_.f_104200_ = -f1 * 5.0F;
      this.f_170963_.f_104202_ = f * 5.0F;
   }

   public ModelPart m_142109_() {
      return this.f_170960_;
   }

   public ModelPart m_103851_() {
      return this.f_103839_;
   }
}