package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SquidModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart[] f_103873_ = new ModelPart[8];
   private final ModelPart f_170987_;

   public SquidModel(ModelPart p_170989_) {
      this.f_170987_ = p_170989_;
      Arrays.setAll(this.f_103873_, (p_170995_) -> {
         return p_170989_.m_171324_(m_170991_(p_170995_));
      });
   }

   private static String m_170991_(int p_170992_) {
      return "tentacle" + p_170992_;
   }

   public static LayerDefinition m_170990_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = -16;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      int j = 8;
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(48, 0).m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);

      for(int k = 0; k < 8; ++k) {
         double d0 = (double)k * Math.PI * 2.0D / 8.0D;
         float f = (float)Math.cos(d0) * 5.0F;
         float f1 = 15.0F;
         float f2 = (float)Math.sin(d0) * 5.0F;
         d0 = (double)k * Math.PI * -2.0D / 8.0D + (Math.PI / 2D);
         float f3 = (float)d0;
         partdefinition.m_171599_(m_170991_(k), cubelistbuilder, PartPose.m_171423_(f, 15.0F, f2, 0.0F, f3, 0.0F));
      }

      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_103878_, float p_103879_, float p_103880_, float p_103881_, float p_103882_, float p_103883_) {
      for(ModelPart modelpart : this.f_103873_) {
         modelpart.f_104203_ = p_103881_;
      }

   }

   public ModelPart m_142109_() {
      return this.f_170987_;
   }
}