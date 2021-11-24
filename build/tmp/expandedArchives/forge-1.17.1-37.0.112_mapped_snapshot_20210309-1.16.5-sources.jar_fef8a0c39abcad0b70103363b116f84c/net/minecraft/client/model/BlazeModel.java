package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlazeModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170441_;
   private final ModelPart[] f_102244_;
   private final ModelPart f_102245_;

   public BlazeModel(ModelPart p_170443_) {
      this.f_170441_ = p_170443_;
      this.f_102245_ = p_170443_.m_171324_("head");
      this.f_102244_ = new ModelPart[12];
      Arrays.setAll(this.f_102244_, (p_170449_) -> {
         return p_170443_.m_171324_(m_170445_(p_170449_));
      });
   }

   private static String m_170445_(int p_170446_) {
      return "part" + p_170446_;
   }

   public static LayerDefinition m_170444_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      float f = 0.0F;
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);

      for(int i = 0; i < 4; ++i) {
         float f1 = Mth.m_14089_(f) * 9.0F;
         float f2 = -2.0F + Mth.m_14089_((float)(i * 2) * 0.25F);
         float f3 = Mth.m_14031_(f) * 9.0F;
         partdefinition.m_171599_(m_170445_(i), cubelistbuilder, PartPose.m_171419_(f1, f2, f3));
         ++f;
      }

      f = ((float)Math.PI / 4F);

      for(int j = 4; j < 8; ++j) {
         float f4 = Mth.m_14089_(f) * 7.0F;
         float f6 = 2.0F + Mth.m_14089_((float)(j * 2) * 0.25F);
         float f8 = Mth.m_14031_(f) * 7.0F;
         partdefinition.m_171599_(m_170445_(j), cubelistbuilder, PartPose.m_171419_(f4, f6, f8));
         ++f;
      }

      f = 0.47123894F;

      for(int k = 8; k < 12; ++k) {
         float f5 = Mth.m_14089_(f) * 5.0F;
         float f7 = 11.0F + Mth.m_14089_((float)k * 1.5F * 0.5F);
         float f9 = Mth.m_14031_(f) * 5.0F;
         partdefinition.m_171599_(m_170445_(k), cubelistbuilder, PartPose.m_171419_(f5, f7, f9));
         ++f;
      }

      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170441_;
   }

   public void m_6973_(T p_102250_, float p_102251_, float p_102252_, float p_102253_, float p_102254_, float p_102255_) {
      float f = p_102253_ * (float)Math.PI * -0.1F;

      for(int i = 0; i < 4; ++i) {
         this.f_102244_[i].f_104201_ = -2.0F + Mth.m_14089_(((float)(i * 2) + p_102253_) * 0.25F);
         this.f_102244_[i].f_104200_ = Mth.m_14089_(f) * 9.0F;
         this.f_102244_[i].f_104202_ = Mth.m_14031_(f) * 9.0F;
         ++f;
      }

      f = ((float)Math.PI / 4F) + p_102253_ * (float)Math.PI * 0.03F;

      for(int j = 4; j < 8; ++j) {
         this.f_102244_[j].f_104201_ = 2.0F + Mth.m_14089_(((float)(j * 2) + p_102253_) * 0.25F);
         this.f_102244_[j].f_104200_ = Mth.m_14089_(f) * 7.0F;
         this.f_102244_[j].f_104202_ = Mth.m_14031_(f) * 7.0F;
         ++f;
      }

      f = 0.47123894F + p_102253_ * (float)Math.PI * -0.05F;

      for(int k = 8; k < 12; ++k) {
         this.f_102244_[k].f_104201_ = 11.0F + Mth.m_14089_(((float)k * 1.5F + p_102253_) * 0.5F);
         this.f_102244_[k].f_104200_ = Mth.m_14089_(f) * 5.0F;
         this.f_102244_[k].f_104202_ = Mth.m_14031_(f) * 5.0F;
         ++f;
      }

      this.f_102245_.f_104204_ = p_102254_ * ((float)Math.PI / 180F);
      this.f_102245_.f_104203_ = p_102255_ * ((float)Math.PI / 180F);
   }
}