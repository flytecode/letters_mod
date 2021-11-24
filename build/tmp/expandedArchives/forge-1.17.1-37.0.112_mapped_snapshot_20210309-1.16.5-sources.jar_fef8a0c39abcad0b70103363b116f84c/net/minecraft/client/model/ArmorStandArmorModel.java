package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandArmorModel extends HumanoidModel<ArmorStand> {
   public ArmorStandArmorModel(ModelPart p_170346_) {
      super(p_170346_);
   }

   public static LayerDefinition m_170347_(CubeDeformation p_170348_) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(p_170348_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170348_), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      partdefinition.m_171599_("hat", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_170348_.m_171469_(0.5F)), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170348_), PartPose.m_171419_(-1.9F, 11.0F, 0.0F));
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170348_), PartPose.m_171419_(1.9F, 11.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_6973_(ArmorStand p_102131_, float p_102132_, float p_102133_, float p_102134_, float p_102135_, float p_102136_) {
      this.f_102808_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31680_().m_123156_();
      this.f_102808_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31680_().m_123157_();
      this.f_102808_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31680_().m_123158_();
      this.f_102810_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31685_().m_123156_();
      this.f_102810_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31685_().m_123157_();
      this.f_102810_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31685_().m_123158_();
      this.f_102812_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31688_().m_123156_();
      this.f_102812_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31688_().m_123157_();
      this.f_102812_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31688_().m_123158_();
      this.f_102811_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31689_().m_123156_();
      this.f_102811_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31689_().m_123157_();
      this.f_102811_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31689_().m_123158_();
      this.f_102814_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31691_().m_123156_();
      this.f_102814_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31691_().m_123157_();
      this.f_102814_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31691_().m_123158_();
      this.f_102813_.f_104203_ = ((float)Math.PI / 180F) * p_102131_.m_31694_().m_123156_();
      this.f_102813_.f_104204_ = ((float)Math.PI / 180F) * p_102131_.m_31694_().m_123157_();
      this.f_102813_.f_104205_ = ((float)Math.PI / 180F) * p_102131_.m_31694_().m_123158_();
      this.f_102809_.m_104315_(this.f_102808_);
   }
}