package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HoglinModel<T extends Mob & HoglinBase> extends AgeableListModel<T> {
   private static final float f_170633_ = 0.87266463F;
   private static final float f_170634_ = -0.34906584F;
   private final ModelPart f_102725_;
   private final ModelPart f_102726_;
   private final ModelPart f_102727_;
   private final ModelPart f_102728_;
   private final ModelPart f_170635_;
   private final ModelPart f_170636_;
   private final ModelPart f_170637_;
   private final ModelPart f_170638_;
   private final ModelPart f_102733_;

   public HoglinModel(ModelPart p_170640_) {
      super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
      this.f_102728_ = p_170640_.m_171324_("body");
      this.f_102733_ = this.f_102728_.m_171324_("mane");
      this.f_102725_ = p_170640_.m_171324_("head");
      this.f_102726_ = this.f_102725_.m_171324_("right_ear");
      this.f_102727_ = this.f_102725_.m_171324_("left_ear");
      this.f_170635_ = p_170640_.m_171324_("right_front_leg");
      this.f_170636_ = p_170640_.m_171324_("left_front_leg");
      this.f_170637_ = p_170640_.m_171324_("right_hind_leg");
      this.f_170638_ = p_170640_.m_171324_("left_hind_leg");
   }

   public static LayerDefinition m_170641_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F), PartPose.m_171419_(0.0F, 7.0F, 0.0F));
      partdefinition1.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(90, 33).m_171488_(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.001F)), PartPose.m_171419_(0.0F, -14.0F, -5.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(61, 1).m_171481_(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F), PartPose.m_171423_(0.0F, 2.0F, -12.0F, 0.87266463F, 0.0F, 0.0F));
      partdefinition2.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171481_(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F), PartPose.m_171423_(-6.0F, -2.0F, -3.0F, 0.0F, 0.0F, -0.6981317F));
      partdefinition2.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(1, 6).m_171481_(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F), PartPose.m_171423_(6.0F, -2.0F, -3.0F, 0.0F, 0.0F, 0.6981317F));
      partdefinition2.m_171599_("right_horn", CubeListBuilder.m_171558_().m_171514_(10, 13).m_171481_(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.m_171419_(-7.0F, 2.0F, -12.0F));
      partdefinition2.m_171599_("left_horn", CubeListBuilder.m_171558_().m_171514_(1, 13).m_171481_(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.m_171419_(7.0F, 2.0F, -12.0F));
      int i = 14;
      int j = 11;
      partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(66, 42).m_171481_(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), PartPose.m_171419_(-4.0F, 10.0F, -8.5F));
      partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(41, 42).m_171481_(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), PartPose.m_171419_(4.0F, 10.0F, -8.5F));
      partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(21, 45).m_171481_(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.m_171419_(-5.0F, 13.0F, 10.0F));
      partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(0, 45).m_171481_(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.m_171419_(5.0F, 13.0F, 10.0F));
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_102725_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_102728_, this.f_170635_, this.f_170636_, this.f_170637_, this.f_170638_);
   }

   public void m_6973_(T p_102744_, float p_102745_, float p_102746_, float p_102747_, float p_102748_, float p_102749_) {
      this.f_102726_.f_104205_ = -0.6981317F - p_102746_ * Mth.m_14031_(p_102745_);
      this.f_102727_.f_104205_ = 0.6981317F + p_102746_ * Mth.m_14031_(p_102745_);
      this.f_102725_.f_104204_ = p_102748_ * ((float)Math.PI / 180F);
      int i = p_102744_.m_7575_();
      float f = 1.0F - (float)Mth.m_14040_(10 - 2 * i) / 10.0F;
      this.f_102725_.f_104203_ = Mth.m_14179_(f, 0.87266463F, -0.34906584F);
      if (p_102744_.m_6162_()) {
         this.f_102725_.f_104201_ = Mth.m_14179_(f, 2.0F, 5.0F);
         this.f_102733_.f_104202_ = -3.0F;
      } else {
         this.f_102725_.f_104201_ = 2.0F;
         this.f_102733_.f_104202_ = -7.0F;
      }

      float f1 = 1.2F;
      this.f_170635_.f_104203_ = Mth.m_14089_(p_102745_) * 1.2F * p_102746_;
      this.f_170636_.f_104203_ = Mth.m_14089_(p_102745_ + (float)Math.PI) * 1.2F * p_102746_;
      this.f_170637_.f_104203_ = this.f_170636_.f_104203_;
      this.f_170638_.f_104203_ = this.f_170635_.f_104203_;
   }
}