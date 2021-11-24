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
public class TropicalFishModelB<T extends Entity> extends ColorableHierarchicalModel<T> {
   private final ModelPart f_171034_;
   private final ModelPart f_103968_;

   public TropicalFishModelB(ModelPart p_171036_) {
      this.f_171034_ = p_171036_;
      this.f_103968_ = p_171036_.m_171324_("tail");
   }

   public static LayerDefinition m_171037_(CubeDeformation p_171038_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 19;
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171488_(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, p_171038_), PartPose.m_171419_(0.0F, 19.0F, 0.0F));
      partdefinition.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(21, 16).m_171488_(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 5.0F, p_171038_), PartPose.m_171419_(0.0F, 19.0F, 3.0F));
      partdefinition.m_171599_("right_fin", CubeListBuilder.m_171558_().m_171514_(2, 16).m_171488_(-2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, p_171038_), PartPose.m_171423_(-1.0F, 20.0F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("left_fin", CubeListBuilder.m_171558_().m_171514_(2, 12).m_171488_(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 0.0F, p_171038_), PartPose.m_171423_(1.0F, 20.0F, 0.0F, 0.0F, (-(float)Math.PI / 4F), 0.0F));
      partdefinition.m_171599_("top_fin", CubeListBuilder.m_171558_().m_171514_(20, 11).m_171488_(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 6.0F, p_171038_), PartPose.m_171419_(0.0F, 16.0F, -3.0F));
      partdefinition.m_171599_("bottom_fin", CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 6.0F, p_171038_), PartPose.m_171419_(0.0F, 22.0F, -3.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public ModelPart m_142109_() {
      return this.f_171034_;
   }

   public void m_6973_(T p_103977_, float p_103978_, float p_103979_, float p_103980_, float p_103981_, float p_103982_) {
      float f = 1.0F;
      if (!p_103977_.m_20069_()) {
         f = 1.5F;
      }

      this.f_103968_.f_104204_ = -f * 0.45F * Mth.m_14031_(0.6F * p_103980_);
   }
}