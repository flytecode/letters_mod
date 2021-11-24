package net.minecraft.client.model;

import java.util.Random;
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
public class GhastModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170568_;
   private final ModelPart[] f_102676_ = new ModelPart[9];

   public GhastModel(ModelPart p_170570_) {
      this.f_170568_ = p_170570_;

      for(int i = 0; i < this.f_102676_.length; ++i) {
         this.f_102676_[i] = p_170570_.m_171324_(m_170572_(i));
      }

   }

   private static String m_170572_(int p_170573_) {
      return "tentacle" + p_170573_;
   }

   public static LayerDefinition m_170571_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.m_171419_(0.0F, 17.6F, 0.0F));
      Random random = new Random(1660L);

      for(int i = 0; i < 9; ++i) {
         float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float f1 = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int j = random.nextInt(7) + 8;
         partdefinition.m_171599_(m_170572_(i), CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, 0.0F, -1.0F, 2.0F, (float)j, 2.0F), PartPose.m_171419_(f, 24.6F, f1));
      }

      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_102681_, float p_102682_, float p_102683_, float p_102684_, float p_102685_, float p_102686_) {
      for(int i = 0; i < this.f_102676_.length; ++i) {
         this.f_102676_[i].f_104203_ = 0.2F * Mth.m_14031_(p_102684_ * 0.3F + (float)i) + 0.4F;
      }

   }

   public ModelPart m_142109_() {
      return this.f_170568_;
   }
}