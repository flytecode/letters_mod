package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderDragonRenderer extends EntityRenderer<EnderDragon> {
   public static final ResourceLocation f_114174_ = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation f_114175_ = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation f_114176_ = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation f_114177_ = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderType f_114178_ = RenderType.m_110458_(f_114176_);
   private static final RenderType f_114179_ = RenderType.m_110479_(f_114176_);
   private static final RenderType f_114180_ = RenderType.m_110488_(f_114177_);
   private static final RenderType f_114181_ = RenderType.m_110476_(f_114174_);
   private static final float f_114182_ = (float)(Math.sqrt(3.0D) / 2.0D);
   private final EnderDragonRenderer.DragonModel f_114183_;

   public EnderDragonRenderer(EntityRendererProvider.Context p_173973_) {
      super(p_173973_);
      this.f_114477_ = 0.5F;
      this.f_114183_ = new EnderDragonRenderer.DragonModel(p_173973_.m_174023_(ModelLayers.f_171144_));
   }

   public void m_7392_(EnderDragon p_114208_, float p_114209_, float p_114210_, PoseStack p_114211_, MultiBufferSource p_114212_, int p_114213_) {
      p_114211_.m_85836_();
      float f = (float)p_114208_.m_31101_(7, p_114210_)[0];
      float f1 = (float)(p_114208_.m_31101_(5, p_114210_)[1] - p_114208_.m_31101_(10, p_114210_)[1]);
      p_114211_.m_85845_(Vector3f.f_122225_.m_122240_(-f));
      p_114211_.m_85845_(Vector3f.f_122223_.m_122240_(f1 * 10.0F));
      p_114211_.m_85837_(0.0D, 0.0D, 1.0D);
      p_114211_.m_85841_(-1.0F, -1.0F, 1.0F);
      p_114211_.m_85837_(0.0D, (double)-1.501F, 0.0D);
      boolean flag = p_114208_.f_20916_ > 0;
      this.f_114183_.m_6839_(p_114208_, 0.0F, 0.0F, p_114210_);
      if (p_114208_.f_31084_ > 0) {
         float f2 = (float)p_114208_.f_31084_ / 200.0F;
         VertexConsumer vertexconsumer = p_114212_.m_6299_(RenderType.m_173235_(f_114175_));
         this.f_114183_.m_7695_(p_114211_, vertexconsumer, p_114213_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, f2);
         VertexConsumer vertexconsumer1 = p_114212_.m_6299_(f_114179_);
         this.f_114183_.m_7695_(p_114211_, vertexconsumer1, p_114213_, OverlayTexture.m_118090_(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         VertexConsumer vertexconsumer3 = p_114212_.m_6299_(f_114178_);
         this.f_114183_.m_7695_(p_114211_, vertexconsumer3, p_114213_, OverlayTexture.m_118090_(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      VertexConsumer vertexconsumer4 = p_114212_.m_6299_(f_114180_);
      this.f_114183_.m_7695_(p_114211_, vertexconsumer4, p_114213_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      if (p_114208_.f_31084_ > 0) {
         float f5 = ((float)p_114208_.f_31084_ + p_114210_) / 200.0F;
         float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
         Random random = new Random(432L);
         VertexConsumer vertexconsumer2 = p_114212_.m_6299_(RenderType.m_110502_());
         p_114211_.m_85836_();
         p_114211_.m_85837_(0.0D, -1.0D, -2.0D);

         for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            p_114211_.m_85845_(Vector3f.f_122223_.m_122240_(random.nextFloat() * 360.0F));
            p_114211_.m_85845_(Vector3f.f_122225_.m_122240_(random.nextFloat() * 360.0F));
            p_114211_.m_85845_(Vector3f.f_122227_.m_122240_(random.nextFloat() * 360.0F));
            p_114211_.m_85845_(Vector3f.f_122223_.m_122240_(random.nextFloat() * 360.0F));
            p_114211_.m_85845_(Vector3f.f_122225_.m_122240_(random.nextFloat() * 360.0F));
            p_114211_.m_85845_(Vector3f.f_122227_.m_122240_(random.nextFloat() * 360.0F + f5 * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            Matrix4f matrix4f = p_114211_.m_85850_().m_85861_();
            int j = (int)(255.0F * (1.0F - f7));
            m_114219_(vertexconsumer2, matrix4f, j);
            m_114214_(vertexconsumer2, matrix4f, f3, f4);
            m_114223_(vertexconsumer2, matrix4f, f3, f4);
            m_114219_(vertexconsumer2, matrix4f, j);
            m_114223_(vertexconsumer2, matrix4f, f3, f4);
            m_114228_(vertexconsumer2, matrix4f, f3, f4);
            m_114219_(vertexconsumer2, matrix4f, j);
            m_114228_(vertexconsumer2, matrix4f, f3, f4);
            m_114214_(vertexconsumer2, matrix4f, f3, f4);
         }

         p_114211_.m_85849_();
      }

      p_114211_.m_85849_();
      if (p_114208_.f_31086_ != null) {
         p_114211_.m_85836_();
         float f6 = (float)(p_114208_.f_31086_.m_20185_() - Mth.m_14139_((double)p_114210_, p_114208_.f_19854_, p_114208_.m_20185_()));
         float f8 = (float)(p_114208_.f_31086_.m_20186_() - Mth.m_14139_((double)p_114210_, p_114208_.f_19855_, p_114208_.m_20186_()));
         float f9 = (float)(p_114208_.f_31086_.m_20189_() - Mth.m_14139_((double)p_114210_, p_114208_.f_19856_, p_114208_.m_20189_()));
         m_114187_(f6, f8 + EndCrystalRenderer.m_114158_(p_114208_.f_31086_, p_114210_), f9, p_114210_, p_114208_.f_19797_, p_114211_, p_114212_, p_114213_);
         p_114211_.m_85849_();
      }

      super.m_7392_(p_114208_, p_114209_, p_114210_, p_114211_, p_114212_, p_114213_);
   }

   private static void m_114219_(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
      p_114220_.m_85982_(p_114221_, 0.0F, 0.0F, 0.0F).m_6122_(255, 255, 255, p_114222_).m_5752_();
   }

   private static void m_114214_(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
      p_114215_.m_85982_(p_114216_, -f_114182_ * p_114218_, p_114217_, -0.5F * p_114218_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   private static void m_114223_(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
      p_114224_.m_85982_(p_114225_, f_114182_ * p_114227_, p_114226_, -0.5F * p_114227_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   private static void m_114228_(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
      p_114229_.m_85982_(p_114230_, 0.0F, p_114231_, 1.0F * p_114232_).m_6122_(255, 0, 255, 0).m_5752_();
   }

   public static void m_114187_(float p_114188_, float p_114189_, float p_114190_, float p_114191_, int p_114192_, PoseStack p_114193_, MultiBufferSource p_114194_, int p_114195_) {
      float f = Mth.m_14116_(p_114188_ * p_114188_ + p_114190_ * p_114190_);
      float f1 = Mth.m_14116_(p_114188_ * p_114188_ + p_114189_ * p_114189_ + p_114190_ * p_114190_);
      p_114193_.m_85836_();
      p_114193_.m_85837_(0.0D, 2.0D, 0.0D);
      p_114193_.m_85845_(Vector3f.f_122225_.m_122270_((float)(-Math.atan2((double)p_114190_, (double)p_114188_)) - ((float)Math.PI / 2F)));
      p_114193_.m_85845_(Vector3f.f_122223_.m_122270_((float)(-Math.atan2((double)f, (double)p_114189_)) - ((float)Math.PI / 2F)));
      VertexConsumer vertexconsumer = p_114194_.m_6299_(f_114181_);
      float f2 = 0.0F - ((float)p_114192_ + p_114191_) * 0.01F;
      float f3 = Mth.m_14116_(p_114188_ * p_114188_ + p_114189_ * p_114189_ + p_114190_ * p_114190_) / 32.0F - ((float)p_114192_ + p_114191_) * 0.01F;
      int i = 8;
      float f4 = 0.0F;
      float f5 = 0.75F;
      float f6 = 0.0F;
      PoseStack.Pose posestack$pose = p_114193_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();

      for(int j = 1; j <= 8; ++j) {
         float f7 = Mth.m_14031_((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
         float f8 = Mth.m_14089_((float)j * ((float)Math.PI * 2F) / 8.0F) * 0.75F;
         float f9 = (float)j / 8.0F;
         vertexconsumer.m_85982_(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).m_6122_(0, 0, 0, 255).m_7421_(f6, f2).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
         vertexconsumer.m_85982_(matrix4f, f4, f5, f1).m_6122_(255, 255, 255, 255).m_7421_(f6, f3).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
         vertexconsumer.m_85982_(matrix4f, f7, f8, f1).m_6122_(255, 255, 255, 255).m_7421_(f9, f3).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
         vertexconsumer.m_85982_(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).m_6122_(0, 0, 0, 255).m_7421_(f9, f2).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114195_).m_85977_(matrix3f, 0.0F, -1.0F, 0.0F).m_5752_();
         f4 = f7;
         f5 = f8;
         f6 = f9;
      }

      p_114193_.m_85849_();
   }

   public ResourceLocation m_5478_(EnderDragon p_114206_) {
      return f_114176_;
   }

   public static LayerDefinition m_173974_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      float f = -16.0F;
      PartDefinition partdefinition1 = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171534_("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44).m_171534_("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30).m_171480_().m_171534_("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).m_171534_("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0).m_171480_().m_171534_("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).m_171534_("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0), PartPose.f_171404_);
      partdefinition1.m_171599_("jaw", CubeListBuilder.m_171558_().m_171534_("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), PartPose.m_171419_(0.0F, 4.0F, -8.0F));
      partdefinition.m_171599_("neck", CubeListBuilder.m_171558_().m_171534_("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104).m_171534_("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0), PartPose.f_171404_);
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171534_("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0).m_171534_("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53).m_171534_("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53).m_171534_("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53), PartPose.m_171419_(0.0F, 4.0F, 8.0F));
      PartDefinition partdefinition2 = partdefinition.m_171599_("left_wing", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), PartPose.m_171419_(12.0F, 5.0F, 2.0F));
      partdefinition2.m_171599_("left_wing_tip", CubeListBuilder.m_171558_().m_171480_().m_171534_("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), PartPose.m_171419_(56.0F, 0.0F, 0.0F));
      PartDefinition partdefinition3 = partdefinition.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.m_171419_(12.0F, 20.0F, 2.0F));
      PartDefinition partdefinition4 = partdefinition3.m_171599_("left_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0F, 20.0F, -1.0F));
      partdefinition4.m_171599_("left_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      PartDefinition partdefinition5 = partdefinition.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.m_171419_(16.0F, 16.0F, 42.0F));
      PartDefinition partdefinition6 = partdefinition5.m_171599_("left_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0F, 32.0F, -4.0F));
      partdefinition6.m_171599_("left_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0F, 31.0F, 4.0F));
      PartDefinition partdefinition7 = partdefinition.m_171599_("right_wing", CubeListBuilder.m_171558_().m_171534_("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), PartPose.m_171419_(-12.0F, 5.0F, 2.0F));
      partdefinition7.m_171599_("right_wing_tip", CubeListBuilder.m_171558_().m_171534_("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).m_171534_("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), PartPose.m_171419_(-56.0F, 0.0F, 0.0F));
      PartDefinition partdefinition8 = partdefinition.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.m_171419_(-12.0F, 20.0F, 2.0F));
      PartDefinition partdefinition9 = partdefinition8.m_171599_("right_front_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.m_171419_(0.0F, 20.0F, -1.0F));
      partdefinition9.m_171599_("right_front_foot", CubeListBuilder.m_171558_().m_171534_("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      PartDefinition partdefinition10 = partdefinition.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171534_("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.m_171419_(-16.0F, 16.0F, 42.0F));
      PartDefinition partdefinition11 = partdefinition10.m_171599_("right_hind_leg_tip", CubeListBuilder.m_171558_().m_171534_("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.m_171419_(0.0F, 32.0F, -4.0F));
      partdefinition11.m_171599_("right_hind_foot", CubeListBuilder.m_171558_().m_171534_("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.m_171419_(0.0F, 31.0F, 4.0F));
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   @OnlyIn(Dist.CLIENT)
   public static class DragonModel extends EntityModel<EnderDragon> {
      private final ModelPart f_114235_;
      private final ModelPart f_114236_;
      private final ModelPart f_114237_;
      private final ModelPart f_114238_;
      private final ModelPart f_114239_;
      private final ModelPart f_114240_;
      private final ModelPart f_114241_;
      private final ModelPart f_114242_;
      private final ModelPart f_114243_;
      private final ModelPart f_114244_;
      private final ModelPart f_114245_;
      private final ModelPart f_114246_;
      private final ModelPart f_114247_;
      private final ModelPart f_114248_;
      private final ModelPart f_114249_;
      private final ModelPart f_114250_;
      private final ModelPart f_114251_;
      private final ModelPart f_114252_;
      private final ModelPart f_114253_;
      private final ModelPart f_114254_;
      @Nullable
      private EnderDragon f_114233_;
      private float f_114234_;

      public DragonModel(ModelPart p_173976_) {
         this.f_114235_ = p_173976_.m_171324_("head");
         this.f_114237_ = this.f_114235_.m_171324_("jaw");
         this.f_114236_ = p_173976_.m_171324_("neck");
         this.f_114238_ = p_173976_.m_171324_("body");
         this.f_114239_ = p_173976_.m_171324_("left_wing");
         this.f_114240_ = this.f_114239_.m_171324_("left_wing_tip");
         this.f_114241_ = p_173976_.m_171324_("left_front_leg");
         this.f_114242_ = this.f_114241_.m_171324_("left_front_leg_tip");
         this.f_114243_ = this.f_114242_.m_171324_("left_front_foot");
         this.f_114244_ = p_173976_.m_171324_("left_hind_leg");
         this.f_114245_ = this.f_114244_.m_171324_("left_hind_leg_tip");
         this.f_114246_ = this.f_114245_.m_171324_("left_hind_foot");
         this.f_114247_ = p_173976_.m_171324_("right_wing");
         this.f_114248_ = this.f_114247_.m_171324_("right_wing_tip");
         this.f_114249_ = p_173976_.m_171324_("right_front_leg");
         this.f_114250_ = this.f_114249_.m_171324_("right_front_leg_tip");
         this.f_114251_ = this.f_114250_.m_171324_("right_front_foot");
         this.f_114252_ = p_173976_.m_171324_("right_hind_leg");
         this.f_114253_ = this.f_114252_.m_171324_("right_hind_leg_tip");
         this.f_114254_ = this.f_114253_.m_171324_("right_hind_foot");
      }

      public void m_6839_(EnderDragon p_114269_, float p_114270_, float p_114271_, float p_114272_) {
         this.f_114233_ = p_114269_;
         this.f_114234_ = p_114272_;
      }

      public void m_6973_(EnderDragon p_114274_, float p_114275_, float p_114276_, float p_114277_, float p_114278_, float p_114279_) {
      }

      public void m_7695_(PoseStack p_114281_, VertexConsumer p_114282_, int p_114283_, int p_114284_, float p_114285_, float p_114286_, float p_114287_, float p_114288_) {
         p_114281_.m_85836_();
         float f = Mth.m_14179_(this.f_114234_, this.f_114233_.f_31081_, this.f_114233_.f_31082_);
         this.f_114237_.f_104203_ = (float)(Math.sin((double)(f * ((float)Math.PI * 2F))) + 1.0D) * 0.2F;
         float f1 = (float)(Math.sin((double)(f * ((float)Math.PI * 2F) - 1.0F)) + 1.0D);
         f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
         p_114281_.m_85837_(0.0D, (double)(f1 - 2.0F), -3.0D);
         p_114281_.m_85845_(Vector3f.f_122223_.m_122240_(f1 * 2.0F));
         float f2 = 0.0F;
         float f3 = 20.0F;
         float f4 = -12.0F;
         float f5 = 1.5F;
         double[] adouble = this.f_114233_.m_31101_(6, this.f_114234_);
         float f6 = Mth.m_14209_(this.f_114233_.m_31101_(5, this.f_114234_)[0] - this.f_114233_.m_31101_(10, this.f_114234_)[0]);
         float f7 = Mth.m_14209_(this.f_114233_.m_31101_(5, this.f_114234_)[0] + (double)(f6 / 2.0F));
         float f8 = f * ((float)Math.PI * 2F);

         for(int i = 0; i < 5; ++i) {
            double[] adouble1 = this.f_114233_.m_31101_(5 - i, this.f_114234_);
            float f9 = (float)Math.cos((double)((float)i * 0.45F + f8)) * 0.15F;
            this.f_114236_.f_104204_ = Mth.m_14209_(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F) * 1.5F;
            this.f_114236_.f_104203_ = f9 + this.f_114233_.m_31108_(i, adouble, adouble1) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
            this.f_114236_.f_104205_ = -Mth.m_14209_(adouble1[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
            this.f_114236_.f_104201_ = f3;
            this.f_114236_.f_104202_ = f4;
            this.f_114236_.f_104200_ = f2;
            f3 = (float)((double)f3 + Math.sin((double)this.f_114236_.f_104203_) * 10.0D);
            f4 = (float)((double)f4 - Math.cos((double)this.f_114236_.f_104204_) * Math.cos((double)this.f_114236_.f_104203_) * 10.0D);
            f2 = (float)((double)f2 - Math.sin((double)this.f_114236_.f_104204_) * Math.cos((double)this.f_114236_.f_104203_) * 10.0D);
            this.f_114236_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0F, 1.0F, 1.0F, p_114288_);
         }

         this.f_114235_.f_104201_ = f3;
         this.f_114235_.f_104202_ = f4;
         this.f_114235_.f_104200_ = f2;
         double[] adouble2 = this.f_114233_.m_31101_(0, this.f_114234_);
         this.f_114235_.f_104204_ = Mth.m_14209_(adouble2[0] - adouble[0]) * ((float)Math.PI / 180F);
         this.f_114235_.f_104203_ = Mth.m_14209_((double)this.f_114233_.m_31108_(6, adouble, adouble2)) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
         this.f_114235_.f_104205_ = -Mth.m_14209_(adouble2[0] - (double)f7) * ((float)Math.PI / 180F);
         this.f_114235_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0F, 1.0F, 1.0F, p_114288_);
         p_114281_.m_85836_();
         p_114281_.m_85837_(0.0D, 1.0D, 0.0D);
         p_114281_.m_85845_(Vector3f.f_122227_.m_122240_(-f6 * 1.5F));
         p_114281_.m_85837_(0.0D, -1.0D, 0.0D);
         this.f_114238_.f_104205_ = 0.0F;
         this.f_114238_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0F, 1.0F, 1.0F, p_114288_);
         float f10 = f * ((float)Math.PI * 2F);
         this.f_114239_.f_104203_ = 0.125F - (float)Math.cos((double)f10) * 0.2F;
         this.f_114239_.f_104204_ = -0.25F;
         this.f_114239_.f_104205_ = -((float)(Math.sin((double)f10) + 0.125D)) * 0.8F;
         this.f_114240_.f_104205_ = (float)(Math.sin((double)(f10 + 2.0F)) + 0.5D) * 0.75F;
         this.f_114247_.f_104203_ = this.f_114239_.f_104203_;
         this.f_114247_.f_104204_ = -this.f_114239_.f_104204_;
         this.f_114247_.f_104205_ = -this.f_114239_.f_104205_;
         this.f_114248_.f_104205_ = -this.f_114240_.f_104205_;
         this.m_173977_(p_114281_, p_114282_, p_114283_, p_114284_, f1, this.f_114239_, this.f_114241_, this.f_114242_, this.f_114243_, this.f_114244_, this.f_114245_, this.f_114246_, p_114288_);
         this.m_173977_(p_114281_, p_114282_, p_114283_, p_114284_, f1, this.f_114247_, this.f_114249_, this.f_114250_, this.f_114251_, this.f_114252_, this.f_114253_, this.f_114254_, p_114288_);
         p_114281_.m_85849_();
         float f11 = -((float)Math.sin((double)(f * ((float)Math.PI * 2F)))) * 0.0F;
         f8 = f * ((float)Math.PI * 2F);
         f3 = 10.0F;
         f4 = 60.0F;
         f2 = 0.0F;
         adouble = this.f_114233_.m_31101_(11, this.f_114234_);

         for(int j = 0; j < 12; ++j) {
            adouble2 = this.f_114233_.m_31101_(12 + j, this.f_114234_);
            f11 = (float)((double)f11 + Math.sin((double)((float)j * 0.45F + f8)) * (double)0.05F);
            this.f_114236_.f_104204_ = (Mth.m_14209_(adouble2[0] - adouble[0]) * 1.5F + 180.0F) * ((float)Math.PI / 180F);
            this.f_114236_.f_104203_ = f11 + (float)(adouble2[1] - adouble[1]) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
            this.f_114236_.f_104205_ = Mth.m_14209_(adouble2[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
            this.f_114236_.f_104201_ = f3;
            this.f_114236_.f_104202_ = f4;
            this.f_114236_.f_104200_ = f2;
            f3 = (float)((double)f3 + Math.sin((double)this.f_114236_.f_104203_) * 10.0D);
            f4 = (float)((double)f4 - Math.cos((double)this.f_114236_.f_104204_) * Math.cos((double)this.f_114236_.f_104203_) * 10.0D);
            f2 = (float)((double)f2 - Math.sin((double)this.f_114236_.f_104204_) * Math.cos((double)this.f_114236_.f_104203_) * 10.0D);
            this.f_114236_.m_104306_(p_114281_, p_114282_, p_114283_, p_114284_, 1.0F, 1.0F, 1.0F, p_114288_);
         }

         p_114281_.m_85849_();
      }

      private void m_173977_(PoseStack p_173978_, VertexConsumer p_173979_, int p_173980_, int p_173981_, float p_173982_, ModelPart p_173983_, ModelPart p_173984_, ModelPart p_173985_, ModelPart p_173986_, ModelPart p_173987_, ModelPart p_173988_, ModelPart p_173989_, float p_173990_) {
         p_173987_.f_104203_ = 1.0F + p_173982_ * 0.1F;
         p_173988_.f_104203_ = 0.5F + p_173982_ * 0.1F;
         p_173989_.f_104203_ = 0.75F + p_173982_ * 0.1F;
         p_173984_.f_104203_ = 1.3F + p_173982_ * 0.1F;
         p_173985_.f_104203_ = -0.5F - p_173982_ * 0.1F;
         p_173986_.f_104203_ = 0.75F + p_173982_ * 0.1F;
         p_173983_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
         p_173984_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
         p_173987_.m_104306_(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
      }
   }
}