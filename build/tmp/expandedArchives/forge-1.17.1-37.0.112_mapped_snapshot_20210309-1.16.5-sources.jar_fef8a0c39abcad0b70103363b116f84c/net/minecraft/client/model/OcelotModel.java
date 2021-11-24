package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OcelotModel<T extends Entity> extends AgeableListModel<T> {
   private static final int f_170757_ = 0;
   private static final int f_170758_ = 1;
   private static final int f_170759_ = 2;
   protected static final int f_170749_ = 3;
   private static final float f_170760_ = 0.0F;
   private static final float f_170761_ = 16.0F;
   private static final float f_170762_ = -9.0F;
   private static final float f_170763_ = 15.0F;
   private static final float f_170764_ = -9.0F;
   private static final float f_170765_ = 12.0F;
   private static final float f_170741_ = -10.0F;
   private static final float f_170742_ = 15.0F;
   private static final float f_170743_ = 8.0F;
   private static final float f_170744_ = 20.0F;
   private static final float f_170745_ = 14.0F;
   protected static final float f_170750_ = 18.0F;
   protected static final float f_170751_ = 5.0F;
   protected static final float f_170752_ = 14.1F;
   private static final float f_170746_ = -5.0F;
   private static final String f_170747_ = "tail1";
   private static final String f_170748_ = "tail2";
   protected final ModelPart f_170753_;
   protected final ModelPart f_170754_;
   protected final ModelPart f_170755_;
   protected final ModelPart f_170756_;
   protected final ModelPart f_103133_;
   protected final ModelPart f_103134_;
   protected final ModelPart f_103135_;
   protected final ModelPart f_103136_;
   protected int f_103137_ = 1;

   public OcelotModel(ModelPart p_170767_) {
      super(true, 10.0F, 4.0F);
      this.f_103135_ = p_170767_.m_171324_("head");
      this.f_103136_ = p_170767_.m_171324_("body");
      this.f_103133_ = p_170767_.m_171324_("tail1");
      this.f_103134_ = p_170767_.m_171324_("tail2");
      this.f_170753_ = p_170767_.m_171324_("left_hind_leg");
      this.f_170754_ = p_170767_.m_171324_("right_hind_leg");
      this.f_170755_ = p_170767_.m_171324_("left_front_leg");
      this.f_170756_ = p_170767_.m_171324_("right_front_leg");
   }

   public static MeshDefinition m_170768_(CubeDeformation p_170769_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171525_("main", -2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, p_170769_).m_171544_("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2, p_170769_, 0, 24).m_171544_("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, p_170769_, 0, 10).m_171544_("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, p_170769_, 6, 10), PartPose.m_171419_(0.0F, 15.0F, -9.0F));
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(20, 0).m_171488_(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, p_170769_), PartPose.m_171423_(0.0F, 12.0F, -10.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("tail1", CubeListBuilder.m_171558_().m_171514_(0, 15).m_171488_(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, p_170769_), PartPose.m_171423_(0.0F, 15.0F, 8.0F, 0.9F, 0.0F, 0.0F));
      partdefinition.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(4, 15).m_171488_(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, p_170769_), PartPose.m_171419_(0.0F, 20.0F, 14.0F));
      CubeListBuilder cubelistbuilder = CubeListBuilder.m_171558_().m_171514_(8, 13).m_171488_(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, p_170769_);
      partdefinition.m_171599_("left_hind_leg", cubelistbuilder, PartPose.m_171419_(1.1F, 18.0F, 5.0F));
      partdefinition.m_171599_("right_hind_leg", cubelistbuilder, PartPose.m_171419_(-1.1F, 18.0F, 5.0F));
      CubeListBuilder cubelistbuilder1 = CubeListBuilder.m_171558_().m_171514_(40, 0).m_171488_(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, p_170769_);
      partdefinition.m_171599_("left_front_leg", cubelistbuilder1, PartPose.m_171419_(1.2F, 14.1F, -5.0F));
      partdefinition.m_171599_("right_front_leg", cubelistbuilder1, PartPose.m_171419_(-1.2F, 14.1F, -5.0F));
      return meshdefinition;
   }

   protected Iterable<ModelPart> m_5607_() {
      return ImmutableList.of(this.f_103135_);
   }

   protected Iterable<ModelPart> m_5608_() {
      return ImmutableList.of(this.f_103136_, this.f_170753_, this.f_170754_, this.f_170755_, this.f_170756_, this.f_103133_, this.f_103134_);
   }

   public void m_6973_(T p_103147_, float p_103148_, float p_103149_, float p_103150_, float p_103151_, float p_103152_) {
      this.f_103135_.f_104203_ = p_103152_ * ((float)Math.PI / 180F);
      this.f_103135_.f_104204_ = p_103151_ * ((float)Math.PI / 180F);
      if (this.f_103137_ != 3) {
         this.f_103136_.f_104203_ = ((float)Math.PI / 2F);
         if (this.f_103137_ == 2) {
            this.f_170753_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F) * p_103149_;
            this.f_170754_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F + 0.3F) * p_103149_;
            this.f_170755_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F + (float)Math.PI + 0.3F) * p_103149_;
            this.f_170756_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F + (float)Math.PI) * p_103149_;
            this.f_103134_.f_104203_ = 1.7278761F + ((float)Math.PI / 10F) * Mth.m_14089_(p_103148_) * p_103149_;
         } else {
            this.f_170753_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F) * p_103149_;
            this.f_170754_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F + (float)Math.PI) * p_103149_;
            this.f_170755_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F + (float)Math.PI) * p_103149_;
            this.f_170756_.f_104203_ = Mth.m_14089_(p_103148_ * 0.6662F) * p_103149_;
            if (this.f_103137_ == 1) {
               this.f_103134_.f_104203_ = 1.7278761F + ((float)Math.PI / 4F) * Mth.m_14089_(p_103148_) * p_103149_;
            } else {
               this.f_103134_.f_104203_ = 1.7278761F + 0.47123894F * Mth.m_14089_(p_103148_) * p_103149_;
            }
         }
      }

   }

   public void m_6839_(T p_103142_, float p_103143_, float p_103144_, float p_103145_) {
      this.f_103136_.f_104201_ = 12.0F;
      this.f_103136_.f_104202_ = -10.0F;
      this.f_103135_.f_104201_ = 15.0F;
      this.f_103135_.f_104202_ = -9.0F;
      this.f_103133_.f_104201_ = 15.0F;
      this.f_103133_.f_104202_ = 8.0F;
      this.f_103134_.f_104201_ = 20.0F;
      this.f_103134_.f_104202_ = 14.0F;
      this.f_170755_.f_104201_ = 14.1F;
      this.f_170755_.f_104202_ = -5.0F;
      this.f_170756_.f_104201_ = 14.1F;
      this.f_170756_.f_104202_ = -5.0F;
      this.f_170753_.f_104201_ = 18.0F;
      this.f_170753_.f_104202_ = 5.0F;
      this.f_170754_.f_104201_ = 18.0F;
      this.f_170754_.f_104202_ = 5.0F;
      this.f_103133_.f_104203_ = 0.9F;
      if (p_103142_.m_6047_()) {
         ++this.f_103136_.f_104201_;
         this.f_103135_.f_104201_ += 2.0F;
         ++this.f_103133_.f_104201_;
         this.f_103134_.f_104201_ += -4.0F;
         this.f_103134_.f_104202_ += 2.0F;
         this.f_103133_.f_104203_ = ((float)Math.PI / 2F);
         this.f_103134_.f_104203_ = ((float)Math.PI / 2F);
         this.f_103137_ = 0;
      } else if (p_103142_.m_20142_()) {
         this.f_103134_.f_104201_ = this.f_103133_.f_104201_;
         this.f_103134_.f_104202_ += 2.0F;
         this.f_103133_.f_104203_ = ((float)Math.PI / 2F);
         this.f_103134_.f_104203_ = ((float)Math.PI / 2F);
         this.f_103137_ = 2;
      } else {
         this.f_103137_ = 1;
      }

   }
}