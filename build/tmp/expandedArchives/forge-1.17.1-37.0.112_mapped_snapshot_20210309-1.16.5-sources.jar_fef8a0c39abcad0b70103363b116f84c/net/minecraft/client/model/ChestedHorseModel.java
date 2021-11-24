package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestedHorseModel<T extends AbstractChestedHorse> extends HorseModel<T> {
   private final ModelPart f_170479_ = this.f_102751_.m_171324_("left_chest");
   private final ModelPart f_170480_ = this.f_102751_.m_171324_("right_chest");

   public ChestedHorseModel(ModelPart p_170482_) {
      super(p_170482_);
   }

   public static LayerDefinition m_170483_() {
      MeshDefinition meshdefinition = HorseModel.m_170669_(CubeDeformation.f_171458_);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171597_("body");
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(26, 21).m_171481_(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      partdefinition1.m_171599_("left_chest", cubelistbuilder, PartPose.m_171423_(6.0F, -8.0F, 0.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F));
      partdefinition1.m_171599_("right_chest", cubelistbuilder, PartPose.m_171423_(-6.0F, -8.0F, 0.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
      PartDefinition partdefinition2 = partdefinition.m_171597_("head_parts").m_171597_("head");
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(0, 12).m_171481_(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      partdefinition2.m_171599_("left_ear", cubelistbuilder1, PartPose.m_171423_(1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, 0.2617994F));
      partdefinition2.m_171599_("right_ear", cubelistbuilder1, PartPose.m_171423_(-1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, -0.2617994F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T p_102366_, float p_102367_, float p_102368_, float p_102369_, float p_102370_, float p_102371_) {
      super.m_6973_(p_102366_, p_102367_, p_102368_, p_102369_, p_102370_, p_102371_);
      if (p_102366_.m_30502_()) {
         this.f_170479_.f_104207_ = true;
         this.f_170480_.f_104207_ = true;
      } else {
         this.f_170479_.f_104207_ = false;
         this.f_170480_.f_104207_ = false;
      }

   }
}