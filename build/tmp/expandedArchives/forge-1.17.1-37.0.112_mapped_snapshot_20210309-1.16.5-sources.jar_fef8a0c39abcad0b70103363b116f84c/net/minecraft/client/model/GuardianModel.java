package net.minecraft.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianModel extends HierarchicalModel<Guardian> {
   private static final float[] f_102695_ = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
   private static final float[] f_102696_ = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
   private static final float[] f_102697_ = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
   private static final float[] f_102698_ = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
   private static final float[] f_102699_ = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
   private static final float[] f_102700_ = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
   private static final String f_170594_ = "eye";
   private static final String f_170595_ = "tail0";
   private static final String f_170596_ = "tail1";
   private static final String f_170597_ = "tail2";
   private final ModelPart f_170598_;
   private final ModelPart f_102701_;
   private final ModelPart f_102702_;
   private final ModelPart[] f_102703_;
   private final ModelPart[] f_102704_;

   public GuardianModel(ModelPart p_170600_) {
      this.f_170598_ = p_170600_;
      this.f_102703_ = new ModelPart[12];
      this.f_102701_ = p_170600_.m_171324_("head");

      for(int i = 0; i < this.f_102703_.length; ++i) {
         this.f_102703_[i] = this.f_102701_.m_171324_(m_170602_(i));
      }

      this.f_102702_ = this.f_102701_.m_171324_("eye");
      this.f_102704_ = new ModelPart[3];
      this.f_102704_[0] = this.f_102701_.m_171324_("tail0");
      this.f_102704_[1] = this.f_102704_[0].m_171324_("tail1");
      this.f_102704_[2] = this.f_102704_[1].m_171324_("tail2");
   }

   private static String m_170602_(int p_170603_) {
      return "spike" + p_170603_;
   }

   public static LayerDefinition m_170601_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F).m_171514_(0, 28).m_171481_(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F).m_171514_(0, 28).m_171506_(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true).m_171514_(16, 40).m_171481_(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F).m_171514_(16, 40).m_171481_(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F), PartPose.f_171404_);
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);

      for(int i = 0; i < 12; ++i) {
         float f = m_170609_(i, 0.0F, 0.0F);
         float f1 = m_170613_(i, 0.0F, 0.0F);
         float f2 = m_170617_(i, 0.0F, 0.0F);
         float f3 = (float)Math.PI * f_102695_[i];
         float f4 = (float)Math.PI * f_102696_[i];
         float f5 = (float)Math.PI * f_102697_[i];
         partdefinition1.m_171599_(m_170602_(i), cubelistbuilder, PartPose.m_171423_(f, f1, f2, f3, f4, f5));
      }

      partdefinition1.m_171599_("eye", CubeListBuilder.m_171558_().m_171514_(8, 0).m_171481_(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F), PartPose.m_171419_(0.0F, 0.0F, -8.25F));
      PartDefinition partdefinition2 = partdefinition1.m_171599_("tail0", CubeListBuilder.m_171558_().m_171514_(40, 0).m_171481_(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F), PartPose.f_171404_);
      PartDefinition partdefinition3 = partdefinition2.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(0, 54).m_171481_(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F), PartPose.m_171419_(-1.5F, 0.5F, 14.0F));
      partdefinition3.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(41, 32).m_171481_(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F).m_171514_(25, 19).m_171481_(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F), PartPose.m_171419_(0.5F, 0.5F, 6.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public ModelPart m_142109_() {
      return this.f_170598_;
   }

   public void m_6973_(Guardian p_102719_, float p_102720_, float p_102721_, float p_102722_, float p_102723_, float p_102724_) {
      float f = p_102722_ - (float)p_102719_.f_19797_;
      this.f_102701_.f_104204_ = p_102723_ * ((float)Math.PI / 180F);
      this.f_102701_.f_104203_ = p_102724_ * ((float)Math.PI / 180F);
      float f1 = (1.0F - p_102719_.m_32865_(f)) * 0.55F;
      this.m_102708_(p_102722_, f1);
      Entity entity = Minecraft.m_91087_().m_91288_();
      if (p_102719_.m_32855_()) {
         entity = p_102719_.m_32856_();
      }

      if (entity != null) {
         Vec3 vec3 = entity.m_20299_(0.0F);
         Vec3 vec31 = p_102719_.m_20299_(0.0F);
         double d0 = vec3.f_82480_ - vec31.f_82480_;
         if (d0 > 0.0D) {
            this.f_102702_.f_104201_ = 0.0F;
         } else {
            this.f_102702_.f_104201_ = 1.0F;
         }

         Vec3 vec32 = p_102719_.m_20252_(0.0F);
         vec32 = new Vec3(vec32.f_82479_, 0.0D, vec32.f_82481_);
         Vec3 vec33 = (new Vec3(vec31.f_82479_ - vec3.f_82479_, 0.0D, vec31.f_82481_ - vec3.f_82481_)).m_82541_().m_82524_(((float)Math.PI / 2F));
         double d1 = vec32.m_82526_(vec33);
         this.f_102702_.f_104200_ = Mth.m_14116_((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
      }

      this.f_102702_.f_104207_ = true;
      float f2 = p_102719_.m_32863_(f);
      this.f_102704_[0].f_104204_ = Mth.m_14031_(f2) * (float)Math.PI * 0.05F;
      this.f_102704_[1].f_104204_ = Mth.m_14031_(f2) * (float)Math.PI * 0.1F;
      this.f_102704_[2].f_104204_ = Mth.m_14031_(f2) * (float)Math.PI * 0.15F;
   }

   private void m_102708_(float p_102709_, float p_102710_) {
      for(int i = 0; i < 12; ++i) {
         this.f_102703_[i].f_104200_ = m_170609_(i, p_102709_, p_102710_);
         this.f_102703_[i].f_104201_ = m_170613_(i, p_102709_, p_102710_);
         this.f_102703_[i].f_104202_ = m_170617_(i, p_102709_, p_102710_);
      }

   }

   private static float m_170604_(int p_170605_, float p_170606_, float p_170607_) {
      return 1.0F + Mth.m_14089_(p_170606_ * 1.5F + (float)p_170605_) * 0.01F - p_170607_;
   }

   private static float m_170609_(int p_170610_, float p_170611_, float p_170612_) {
      return f_102698_[p_170610_] * m_170604_(p_170610_, p_170611_, p_170612_);
   }

   private static float m_170613_(int p_170614_, float p_170615_, float p_170616_) {
      return 16.0F + f_102699_[p_170614_] * m_170604_(p_170614_, p_170615_, p_170616_);
   }

   private static float m_170617_(int p_170618_, float p_170619_, float p_170620_) {
      return f_102700_[p_170618_] * m_170604_(p_170618_, p_170619_, p_170620_);
   }
}