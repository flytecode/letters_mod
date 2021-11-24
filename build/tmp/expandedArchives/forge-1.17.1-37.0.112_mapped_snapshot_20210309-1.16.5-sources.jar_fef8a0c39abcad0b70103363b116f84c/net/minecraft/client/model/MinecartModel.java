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
public class MinecartModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart f_170733_;
   private static final String f_170734_ = "contents";
   private final ModelPart f_170735_;

   public MinecartModel(ModelPart p_170737_) {
      this.f_170733_ = p_170737_;
      this.f_170735_ = p_170737_.m_171324_("contents");
   }

   public static LayerDefinition m_170738_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      int i = 20;
      int j = 8;
      int k = 16;
      int l = 4;
      partdefinition.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 10).m_171481_(-10.0F, -8.0F, -1.0F, 20.0F, 16.0F, 2.0F), PartPose.m_171423_(0.0F, 4.0F, 0.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("front", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), PartPose.m_171423_(-9.0F, 4.0F, 0.0F, 0.0F, ((float)Math.PI * 1.5F), 0.0F));
      partdefinition.m_171599_("back", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), PartPose.m_171423_(9.0F, 4.0F, 0.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
      partdefinition.m_171599_("left", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), PartPose.m_171423_(0.0F, 4.0F, -7.0F, 0.0F, (float)Math.PI, 0.0F));
      partdefinition.m_171599_("right", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), PartPose.m_171419_(0.0F, 4.0F, 7.0F));
      partdefinition.m_171599_("contents", CubeListBuilder.m_171558_().m_171514_(44, 10).m_171481_(-9.0F, -7.0F, -1.0F, 18.0F, 14.0F, 1.0F), PartPose.m_171423_(0.0F, 4.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(T p_103100_, float p_103101_, float p_103102_, float p_103103_, float p_103104_, float p_103105_) {
      this.f_170735_.f_104201_ = 4.0F - p_103103_;
   }

   public ModelPart m_142109_() {
      return this.f_170733_;
   }
}