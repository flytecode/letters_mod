package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurtleModel<T extends Turtle> extends QuadrupedModel<T> {
   private static final String f_171040_ = "egg_belly";
   private final ModelPart f_103983_;

   public TurtleModel(ModelPart p_171042_) {
      super(p_171042_, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
      this.f_103983_ = p_171042_.m_171324_("egg_belly");
   }

   public static LayerDefinition m_171043_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(3, 0).m_171481_(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F), PartPose.m_171419_(0.0F, 19.0F, -10.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(7, 37).m_171517_("shell", -9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F).m_171514_(31, 1).m_171517_("belly", -5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F), PartPose.m_171423_(0.0F, 11.0F, -10.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("egg_belly", CubeListBuilder.m_171558_().m_171514_(70, 33).m_171481_(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F), PartPose.m_171423_(0.0F, 11.0F, -10.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      int i = 1;
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(1, 23).m_171481_(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), PartPose.m_171419_(-3.5F, 22.0F, 11.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(1, 12).m_171481_(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), PartPose.m_171419_(3.5F, 22.0F, 11.0F));
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(27, 30).m_171481_(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), PartPose.m_171419_(-5.0F, 21.0F, -4.0F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(27, 24).m_171481_(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), PartPose.m_171419_(5.0F, 21.0F, -4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   protected Iterable<ModelPart> m_5608_() {
      return Iterables.concat(super.m_5608_(), ImmutableList.of(this.f_103983_));
   }

   public void m_6973_(T p_103994_, float p_103995_, float p_103996_, float p_103997_, float p_103998_, float p_103999_) {
      super.m_6973_(p_103994_, p_103995_, p_103996_, p_103997_, p_103998_, p_103999_);
      this.f_170852_.f_104203_ = Mth.m_14089_(p_103995_ * 0.6662F * 0.6F) * 0.5F * p_103996_;
      this.f_170853_.f_104203_ = Mth.m_14089_(p_103995_ * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * p_103996_;
      this.f_170854_.f_104205_ = Mth.m_14089_(p_103995_ * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * p_103996_;
      this.f_170855_.f_104205_ = Mth.m_14089_(p_103995_ * 0.6662F * 0.6F) * 0.5F * p_103996_;
      this.f_170854_.f_104203_ = 0.0F;
      this.f_170855_.f_104203_ = 0.0F;
      this.f_170854_.f_104204_ = 0.0F;
      this.f_170855_.f_104204_ = 0.0F;
      this.f_170852_.f_104204_ = 0.0F;
      this.f_170853_.f_104204_ = 0.0F;
      if (!p_103994_.m_20069_() && p_103994_.m_20096_()) {
         float f = p_103994_.m_30206_() ? 4.0F : 1.0F;
         float f1 = p_103994_.m_30206_() ? 2.0F : 1.0F;
         float f2 = 5.0F;
         this.f_170854_.f_104204_ = Mth.m_14089_(f * p_103995_ * 5.0F + (float)Math.PI) * 8.0F * p_103996_ * f1;
         this.f_170854_.f_104205_ = 0.0F;
         this.f_170855_.f_104204_ = Mth.m_14089_(f * p_103995_ * 5.0F) * 8.0F * p_103996_ * f1;
         this.f_170855_.f_104205_ = 0.0F;
         this.f_170852_.f_104204_ = Mth.m_14089_(p_103995_ * 5.0F + (float)Math.PI) * 3.0F * p_103996_;
         this.f_170852_.f_104203_ = 0.0F;
         this.f_170853_.f_104204_ = Mth.m_14089_(p_103995_ * 5.0F) * 3.0F * p_103996_;
         this.f_170853_.f_104203_ = 0.0F;
      }

      this.f_103983_.f_104207_ = !this.f_102610_ && p_103994_.m_30205_();
   }

   public void m_7695_(PoseStack p_104001_, VertexConsumer p_104002_, int p_104003_, int p_104004_, float p_104005_, float p_104006_, float p_104007_, float p_104008_) {
      boolean flag = this.f_103983_.f_104207_;
      if (flag) {
         p_104001_.m_85836_();
         p_104001_.m_85837_(0.0D, (double)-0.08F, 0.0D);
      }

      super.m_7695_(p_104001_, p_104002_, p_104003_, p_104004_, p_104005_, p_104006_, p_104007_, p_104008_);
      if (flag) {
         p_104001_.m_85849_();
      }

   }
}