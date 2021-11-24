package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkullModel extends SkullModelBase {
   private final ModelPart f_170943_;
   protected final ModelPart f_103804_;

   public SkullModel(ModelPart p_170945_) {
      this.f_170943_ = p_170945_;
      this.f_103804_ = p_170945_.m_171324_("head");
   }

   public static MeshDefinition m_170946_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      return meshdefinition;
   }

   public static LayerDefinition m_170947_() {
      MeshDefinition meshdefinition = m_170946_();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition m_170948_() {
      MeshDefinition meshdefinition = m_170946_();
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_142698_(float p_103811_, float p_103812_, float p_103813_) {
      this.f_103804_.f_104204_ = p_103812_ * ((float)Math.PI / 180F);
      this.f_103804_.f_104203_ = p_103813_ * ((float)Math.PI / 180F);
   }

   public void m_7695_(PoseStack p_103815_, VertexConsumer p_103816_, int p_103817_, int p_103818_, float p_103819_, float p_103820_, float p_103821_, float p_103822_) {
      this.f_170943_.m_104306_(p_103815_, p_103816_, p_103817_, p_103818_, p_103819_, p_103820_, p_103821_, p_103822_);
   }
}