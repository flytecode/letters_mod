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
public class CreeperModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170517_;
   private final ModelPart f_102451_;
   private final ModelPart f_170518_;
   private final ModelPart f_170519_;
   private final ModelPart f_170520_;
   private final ModelPart f_170521_;
   private static final int f_170522_ = 6;

   public CreeperModel(ModelPart p_170524_) {
      this.f_170517_ = p_170524_;
      this.f_102451_ = p_170524_.m_171324_("head");
      this.f_170519_ = p_170524_.m_171324_("right_hind_leg");
      this.f_170518_ = p_170524_.m_171324_("left_hind_leg");
      this.f_170521_ = p_170524_.m_171324_("right_front_leg");
      this.f_170520_ = p_170524_.m_171324_("left_front_leg");
   }

   public static LayerDefinition m_170525_(CubeDeformation p_170526_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170526_), PartPose.m_171419_(0.0F, 6.0F, 0.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(16, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170526_), PartPose.m_171419_(0.0F, 6.0F, 0.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_170526_);
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-2.0F, 18.0F, 4.0F));
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(2.0F, 18.0F, 4.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder, PartPose.m_171419_(-2.0F, 18.0F, -4.0F));
      partdefinition.m_171599_("left_front_leg", cubelistbuilder, PartPose.m_171419_(2.0F, 18.0F, -4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170517_;
   }

   public void m_6973_(T p_102463_, float p_102464_, float p_102465_, float p_102466_, float p_102467_, float p_102468_) {
      this.f_102451_.f_104204_ = p_102467_ * ((float)Math.PI / 180F);
      this.f_102451_.f_104203_ = p_102468_ * ((float)Math.PI / 180F);
      this.f_170518_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662F) * 1.4F * p_102465_;
      this.f_170519_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662F + (float)Math.PI) * 1.4F * p_102465_;
      this.f_170520_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662F + (float)Math.PI) * 1.4F * p_102465_;
      this.f_170521_.f_104203_ = Mth.m_14089_(p_102464_ * 0.6662F) * 1.4F * p_102465_;
   }
}