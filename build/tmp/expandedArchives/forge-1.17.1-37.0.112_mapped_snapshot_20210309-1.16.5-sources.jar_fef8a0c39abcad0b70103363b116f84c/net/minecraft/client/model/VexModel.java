package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Vex;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VexModel extends HumanoidModel<Vex> {
   private final ModelPart f_104010_;
   private final ModelPart f_104011_;

   public VexModel(ModelPart p_171045_) {
      super(p_171045_);
      this.f_102814_.f_104207_ = false;
      this.f_102809_.f_104207_ = false;
      this.f_104011_ = p_171045_.m_171324_("right_wing");
      this.f_104010_ = p_171045_.m_171324_("left_wing");
   }

   public static LayerDefinition m_171046_() {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(CubeDeformation.f_171458_, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-1.0F, -1.0F, -2.0F, 6.0F, 10.0F, 4.0F), PartPose.m_171419_(-1.9F, 12.0F, 0.0F));
      partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-20.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F), PartPose.f_171404_);
      partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171480_().m_171481_(0.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   protected Iterable<ModelPart> m_5608_() {
      return Iterables.concat(super.m_5608_(), ImmutableList.of(this.f_104011_, this.f_104010_));
   }

   public void m_6973_(Vex p_104028_, float p_104029_, float p_104030_, float p_104031_, float p_104032_, float p_104033_) {
      super.m_6973_(p_104028_, p_104029_, p_104030_, p_104031_, p_104032_, p_104033_);
      if (p_104028_.m_34028_()) {
         if (p_104028_.m_21205_().m_41619_()) {
            this.f_102811_.f_104203_ = ((float)Math.PI * 1.5F);
            this.f_102812_.f_104203_ = ((float)Math.PI * 1.5F);
         } else if (p_104028_.m_5737_() == HumanoidArm.RIGHT) {
            this.f_102811_.f_104203_ = 3.7699115F;
         } else {
            this.f_102812_.f_104203_ = 3.7699115F;
         }
      }

      this.f_102813_.f_104203_ += ((float)Math.PI / 5F);
      this.f_104011_.f_104202_ = 2.0F;
      this.f_104010_.f_104202_ = 2.0F;
      this.f_104011_.f_104201_ = 1.0F;
      this.f_104010_.f_104201_ = 1.0F;
      this.f_104011_.f_104204_ = 0.47123894F + Mth.m_14089_(p_104031_ * 45.836624F * ((float)Math.PI / 180F)) * (float)Math.PI * 0.05F;
      this.f_104010_.f_104204_ = -this.f_104011_.f_104204_;
      this.f_104010_.f_104205_ = -0.47123894F;
      this.f_104010_.f_104203_ = 0.47123894F;
      this.f_104011_.f_104203_ = 0.47123894F;
      this.f_104011_.f_104205_ = 0.47123894F;
   }
}