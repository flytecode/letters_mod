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
public class SilverfishModel<T extends Entity> extends HierarchicalModel<T> {
   private static final int f_170924_ = 7;
   private final ModelPart f_170925_;
   private final ModelPart[] f_103744_ = new ModelPart[7];
   private final ModelPart[] f_103745_ = new ModelPart[3];
   private static final int[][] f_103748_ = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] f_103749_ = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public SilverfishModel(ModelPart p_170927_) {
      this.f_170925_ = p_170927_;
      Arrays.setAll(this.f_103744_, (p_170939_) -> {
         return p_170927_.m_171324_(m_170935_(p_170939_));
      });
      Arrays.setAll(this.f_103745_, (p_170933_) -> {
         return p_170927_.m_171324_(m_170929_(p_170933_));
      });
   }

   private static String m_170929_(int p_170930_) {
      return "layer" + p_170930_;
   }

   private static String m_170935_(int p_170936_) {
      return "segment" + p_170936_;
   }

   public static LayerDefinition m_170928_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float[] afloat = new float[7];
      float f = -3.5F;

      for(int i = 0; i < 7; ++i) {
         partdefinition.m_171599_(m_170935_(i), CubeListBuilder.m_171558_().m_171514_(f_103749_[i][0], f_103749_[i][1]).m_171481_((float)f_103748_[i][0] * -0.5F, 0.0F, (float)f_103748_[i][2] * -0.5F, (float)f_103748_[i][0], (float)f_103748_[i][1], (float)f_103748_[i][2]), PartPose.m_171419_(0.0F, (float)(24 - f_103748_[i][1]), f));
         afloat[i] = f;
         if (i < 6) {
            f += (float)(f_103748_[i][2] + f_103748_[i + 1][2]) * 0.5F;
         }
      }

      partdefinition.m_171599_(m_170929_(0), CubeListBuilder.m_171558_().m_171514_(20, 0).m_171481_(-5.0F, 0.0F, (float)f_103748_[2][2] * -0.5F, 10.0F, 8.0F, (float)f_103748_[2][2]), PartPose.m_171419_(0.0F, 16.0F, afloat[2]));
      partdefinition.m_171599_(m_170929_(1), CubeListBuilder.m_171558_().m_171514_(20, 11).m_171481_(-3.0F, 0.0F, (float)f_103748_[4][2] * -0.5F, 6.0F, 4.0F, (float)f_103748_[4][2]), PartPose.m_171419_(0.0F, 20.0F, afloat[4]));
      partdefinition.m_171599_(m_170929_(2), CubeListBuilder.m_171558_().m_171514_(20, 18).m_171481_(-3.0F, 0.0F, (float)f_103748_[4][2] * -0.5F, 6.0F, 5.0F, (float)f_103748_[1][2]), PartPose.m_171419_(0.0F, 19.0F, afloat[1]));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170925_;
   }

   public void m_6973_(T p_103754_, float p_103755_, float p_103756_, float p_103757_, float p_103758_, float p_103759_) {
      for(int i = 0; i < this.f_103744_.length; ++i) {
         this.f_103744_[i].f_104204_ = Mth.m_14089_(p_103757_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(i - 2));
         this.f_103744_[i].f_104200_ = Mth.m_14031_(p_103757_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
      }

      this.f_103745_[0].f_104204_ = this.f_103744_[2].f_104204_;
      this.f_103745_[1].f_104204_ = this.f_103744_[4].f_104204_;
      this.f_103745_[1].f_104200_ = this.f_103744_[4].f_104200_;
      this.f_103745_[2].f_104204_ = this.f_103744_[1].f_104204_;
      this.f_103745_[2].f_104200_ = this.f_103744_[1].f_104200_;
   }
}