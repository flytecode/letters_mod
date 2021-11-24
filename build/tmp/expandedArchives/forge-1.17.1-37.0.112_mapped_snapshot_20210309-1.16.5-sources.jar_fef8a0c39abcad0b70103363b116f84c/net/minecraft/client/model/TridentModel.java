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
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TridentModel extends Model {
   public static final ResourceLocation f_103914_ = new ResourceLocation("textures/entity/trident.png");
   private final ModelPart f_171014_;

   public TridentModel(ModelPart p_171016_) {
      super(RenderType::m_110446_);
      this.f_171014_ = p_171016_;
   }

   public static LayerDefinition m_171017_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("pole", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171481_(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171481_(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("left_spike", CubeListBuilder.m_171558_().m_171514_(4, 3).m_171481_(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("middle_spike", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.f_171404_);
      partdefinition1.m_171599_("right_spike", CubeListBuilder.m_171558_().m_171514_(4, 3).m_171480_().m_171481_(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack p_103919_, VertexConsumer p_103920_, int p_103921_, int p_103922_, float p_103923_, float p_103924_, float p_103925_, float p_103926_) {
      this.f_171014_.m_104306_(p_103919_, p_103920_, p_103921_, p_103922_, p_103923_, p_103924_, p_103925_, p_103926_);
   }
}