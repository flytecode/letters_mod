package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldModel extends Model {
   private static final String f_170905_ = "plate";
   private static final String f_170906_ = "handle";
   private static final int f_170907_ = 10;
   private static final int f_170908_ = 20;
   private final ModelPart f_170909_;
   private final ModelPart f_103698_;
   private final ModelPart f_103699_;

   public ShieldModel(ModelPart p_170911_) {
      super(RenderType::m_110446_);
      this.f_170909_ = p_170911_;
      this.f_103698_ = p_170911_.m_171324_("plate");
      this.f_103699_ = p_170911_.m_171324_("handle");
   }

   public static LayerDefinition m_170912_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("plate", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F), PartPose.f_171404_);
      partdefinition.m_171599_("handle", CubeListBuilder.m_171558_().m_171514_(26, 0).m_171481_(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_103701_() {
      return this.f_103698_;
   }

   public ModelPart m_103711_() {
      return this.f_103699_;
   }

   public void m_7695_(PoseStack p_103703_, VertexConsumer p_103704_, int p_103705_, int p_103706_, float p_103707_, float p_103708_, float p_103709_, float p_103710_) {
      this.f_170909_.m_104306_(p_103703_, p_103704_, p_103705_, p_103706_, p_103707_, p_103708_, p_103709_, p_103710_);
   }
}