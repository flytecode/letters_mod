package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaSlimeModel<T extends Slime> extends HierarchicalModel<T> {
   private static final int f_170700_ = 8;
   private final ModelPart f_170701_;
   private final ModelPart[] f_102969_ = new ModelPart[8];

   public LavaSlimeModel(ModelPart p_170703_) {
      this.f_170701_ = p_170703_;
      Arrays.setAll(this.f_102969_, (p_170709_) -> {
         return p_170703_.m_171324_(m_170705_(p_170709_));
      });
   }

   private static String m_170705_(int p_170706_) {
      return "cube" + p_170706_;
   }

   public static LayerDefinition m_170704_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();

      for(int i = 0; i < 8; ++i) {
         int j = 0;
         int k = i;
         if (i == 2) {
            j = 24;
            k = 10;
         } else if (i == 3) {
            j = 24;
            k = 19;
         }

         partdefinition.m_171599_(m_170705_(i), CubeListBuilder.m_171558_().m_171514_(j, k).m_171481_(-4.0F, (float)(16 + i), -4.0F, 8.0F, 1.0F, 8.0F), PartPose.f_171404_);
      }

      partdefinition.m_171599_("inside_cube", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_102992_, float p_102993_, float p_102994_, float p_102995_, float p_102996_, float p_102997_) {
   }

   public void m_6839_(T p_102987_, float p_102988_, float p_102989_, float p_102990_) {
      float f = Mth.m_14179_(p_102990_, p_102987_.f_33585_, p_102987_.f_33584_);
      if (f < 0.0F) {
         f = 0.0F;
      }

      for(int i = 0; i < this.f_102969_.length; ++i) {
         this.f_102969_[i].f_104201_ = (float)(-(4 - i)) * f * 1.7F;
      }

   }

   public ModelPart m_142109_() {
      return this.f_170701_;
   }
}