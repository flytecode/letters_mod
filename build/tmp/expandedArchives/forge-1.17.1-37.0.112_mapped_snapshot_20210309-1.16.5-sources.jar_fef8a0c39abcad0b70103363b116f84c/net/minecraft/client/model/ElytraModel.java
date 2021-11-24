package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraModel<T extends LivingEntity> extends AgeableListModel<T> {
   private final ModelPart f_102532_;
   private final ModelPart f_102533_;

   public ElytraModel(ModelPart p_170538_) {
      this.f_102533_ = p_170538_.m_171324_("left_wing");
      this.f_102532_ = p_170538_.m_171324_("right_wing");
   }

   public static LayerDefinition m_170539_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      CubeDeformation cubedeformation = new CubeDeformation(1.0F);
      partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171488_(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, cubedeformation), PartPose.m_171423_(5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, -0.2617994F));
      partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171514_(22, 0).m_171480_().m_171488_(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, cubedeformation), PartPose.m_171423_(-5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, 0.2617994F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of();
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102533_, this.f_102532_);
   }

   public void m_6973_(T p_102544_, float p_102545_, float p_102546_, float p_102547_, float p_102548_, float p_102549_) {
      float f = 0.2617994F;
      float f1 = -0.2617994F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      if (p_102544_.m_21255_()) {
         float f4 = 1.0F;
         Vec3 vec3 = p_102544_.m_20184_();
         if (vec3.f_82480_ < 0.0D) {
            Vec3 vec31 = vec3.m_82541_();
            f4 = 1.0F - (float)Math.pow(-vec31.f_82480_, 1.5D);
         }

         f = f4 * 0.34906584F + (1.0F - f4) * f;
         f1 = f4 * (-(float)Math.PI / 2F) + (1.0F - f4) * f1;
      } else if (p_102544_.m_6047_()) {
         f = 0.6981317F;
         f1 = (-(float)Math.PI / 4F);
         f2 = 3.0F;
         f3 = 0.08726646F;
      }

      this.f_102533_.f_104201_ = f2;
      if (p_102544_ instanceof AbstractClientPlayer) {
         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_102544_;
         abstractclientplayer.f_108542_ = (float)((double)abstractclientplayer.f_108542_ + (double)(f - abstractclientplayer.f_108542_) * 0.1D);
         abstractclientplayer.f_108543_ = (float)((double)abstractclientplayer.f_108543_ + (double)(f3 - abstractclientplayer.f_108543_) * 0.1D);
         abstractclientplayer.f_108544_ = (float)((double)abstractclientplayer.f_108544_ + (double)(f1 - abstractclientplayer.f_108544_) * 0.1D);
         this.f_102533_.f_104203_ = abstractclientplayer.f_108542_;
         this.f_102533_.f_104204_ = abstractclientplayer.f_108543_;
         this.f_102533_.f_104205_ = abstractclientplayer.f_108544_;
      } else {
         this.f_102533_.f_104203_ = f;
         this.f_102533_.f_104205_ = f1;
         this.f_102533_.f_104204_ = f3;
      }

      this.f_102532_.f_104204_ = -this.f_102533_.f_104204_;
      this.f_102532_.f_104201_ = this.f_102533_.f_104201_;
      this.f_102532_.f_104203_ = this.f_102533_.f_104203_;
      this.f_102532_.f_104205_ = -this.f_102533_.f_104205_;
   }
}