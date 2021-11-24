package net.minecraft.client.model;

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
public class LlamaSpitModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String f_170727_ = "main";
   private final ModelPart f_170728_;

   public LlamaSpitModel(ModelPart p_170730_) {
      this.f_170728_ = p_170730_;
   }

   public static LayerDefinition m_170731_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 2;
      partdefinition.m_171599_("main", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F).m_171481_(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F).m_171481_(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F).m_171481_(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F).m_171481_(2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F).m_171481_(0.0F, 2.0F, 0.0F, 2.0F, 2.0F, 2.0F).m_171481_(0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 2.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_103090_, float p_103091_, float p_103092_, float p_103093_, float p_103094_, float p_103095_) {
   }

   public ModelPart m_142109_() {
      return this.f_170728_;
   }
}