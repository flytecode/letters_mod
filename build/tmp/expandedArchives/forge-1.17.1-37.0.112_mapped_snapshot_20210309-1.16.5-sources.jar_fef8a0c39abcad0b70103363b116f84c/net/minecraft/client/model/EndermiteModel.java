package net.minecraft.client.model;

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
public class EndermiteModel<T extends Entity> extends HierarchicalModel<T> {
   private static final int f_102596_ = 4;
   private static final int[][] f_102594_ = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] f_102595_ = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private final ModelPart f_170543_;
   private final ModelPart[] f_102597_;

   public EndermiteModel(ModelPart p_170545_) {
      this.f_170543_ = p_170545_;
      this.f_102597_ = new ModelPart[4];

      for(int i = 0; i < 4; ++i) {
         this.f_102597_[i] = p_170545_.m_171324_(m_170547_(i));
      }

   }

   private static String m_170547_(int p_170548_) {
      return "segment" + p_170548_;
   }

   public static LayerDefinition m_170546_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = -3.5F;

      for(int i = 0; i < 4; ++i) {
         partdefinition.m_171599_(m_170547_(i), CubeListBuilder.m_171558_().m_171514_(f_102595_[i][0], f_102595_[i][1]).m_171481_((float)f_102594_[i][0] * -0.5F, 0.0F, (float)f_102594_[i][2] * -0.5F, (float)f_102594_[i][0], (float)f_102594_[i][1], (float)f_102594_[i][2]), PartPose.m_171419_(0.0F, (float)(24 - f_102594_[i][1]), f));
         if (i < 3) {
            f += (float)(f_102594_[i][2] + f_102594_[i + 1][2]) * 0.5F;
         }
      }

      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public ModelPart m_142109_() {
      return this.f_170543_;
   }

   public void m_6973_(T p_102602_, float p_102603_, float p_102604_, float p_102605_, float p_102606_, float p_102607_) {
      for(int i = 0; i < this.f_102597_.length; ++i) {
         this.f_102597_[i].f_104204_ = Mth.m_14089_(p_102605_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.01F * (float)(1 + Math.abs(i - 2));
         this.f_102597_[i].f_104200_ = Mth.m_14031_(p_102605_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.1F * (float)Math.abs(i - 2);
      }

   }
}