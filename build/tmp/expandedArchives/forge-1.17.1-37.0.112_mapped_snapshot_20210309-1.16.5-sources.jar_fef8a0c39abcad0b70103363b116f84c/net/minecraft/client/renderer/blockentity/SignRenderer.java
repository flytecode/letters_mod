package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SignRenderer implements BlockEntityRenderer<SignBlockEntity> {
   public static final int f_173627_ = 90;
   private static final int f_173628_ = 10;
   private static final String f_173629_ = "stick";
   private static final int f_173630_ = -988212;
   private static final int f_173631_ = Mth.m_144944_(16);
   private final Map<WoodType, SignRenderer.SignModel> f_173632_;
   private final Font f_173633_;

   public SignRenderer(BlockEntityRendererProvider.Context p_173636_) {
      this.f_173632_ = WoodType.m_61843_().collect(ImmutableMap.toImmutableMap((p_173645_) -> {
         return p_173645_;
      }, (p_173651_) -> {
         return new SignRenderer.SignModel(p_173636_.m_173582_(ModelLayers.m_171291_(p_173651_)));
      }));
      this.f_173633_ = p_173636_.m_173586_();
   }

   public void m_6922_(SignBlockEntity p_112497_, float p_112498_, PoseStack p_112499_, MultiBufferSource p_112500_, int p_112501_, int p_112502_) {
      BlockState blockstate = p_112497_.m_58900_();
      p_112499_.m_85836_();
      float f = 0.6666667F;
      WoodType woodtype = m_173637_(blockstate.m_60734_());
      SignRenderer.SignModel signrenderer$signmodel = this.f_173632_.get(woodtype);
      if (blockstate.m_60734_() instanceof StandingSignBlock) {
         p_112499_.m_85837_(0.5D, 0.5D, 0.5D);
         float f1 = -((float)(blockstate.m_61143_(StandingSignBlock.f_56987_) * 360) / 16.0F);
         p_112499_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
         signrenderer$signmodel.f_112507_.f_104207_ = true;
      } else {
         p_112499_.m_85837_(0.5D, 0.5D, 0.5D);
         float f4 = -blockstate.m_61143_(WallSignBlock.f_58064_).m_122435_();
         p_112499_.m_85845_(Vector3f.f_122225_.m_122240_(f4));
         p_112499_.m_85837_(0.0D, -0.3125D, -0.4375D);
         signrenderer$signmodel.f_112507_.f_104207_ = false;
      }

      p_112499_.m_85836_();
      p_112499_.m_85841_(0.6666667F, -0.6666667F, -0.6666667F);
      Material material = Sheets.m_173381_(woodtype);
      VertexConsumer vertexconsumer = material.m_119194_(p_112500_, signrenderer$signmodel::m_103119_);
      signrenderer$signmodel.f_173655_.m_104301_(p_112499_, vertexconsumer, p_112501_, p_112502_);
      p_112499_.m_85849_();
      float f2 = 0.010416667F;
      p_112499_.m_85837_(0.0D, (double)0.33333334F, (double)0.046666667F);
      p_112499_.m_85841_(0.010416667F, -0.010416667F, 0.010416667F);
      int i = m_173639_(p_112497_);
      int j = 20;
      FormattedCharSequence[] aformattedcharsequence = p_112497_.m_155717_(Minecraft.m_91087_().m_167974_(), (p_173653_) -> {
         List<FormattedCharSequence> list = this.f_173633_.m_92923_(p_173653_, 90);
         return list.isEmpty() ? FormattedCharSequence.f_13691_ : list.get(0);
      });
      int k;
      boolean flag;
      int l;
      if (p_112497_.m_155727_()) {
         k = p_112497_.m_59753_().m_41071_();
         flag = m_173641_(p_112497_, k);
         l = 15728880;
      } else {
         k = i;
         flag = false;
         l = p_112501_;
      }

      for(int i1 = 0; i1 < 4; ++i1) {
         FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
         float f3 = (float)(-this.f_173633_.m_92724_(formattedcharsequence) / 2);
         if (flag) {
            this.f_173633_.m_168645_(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, i, p_112499_.m_85850_().m_85861_(), p_112500_, l);
         } else {
            this.f_173633_.m_92733_(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, false, p_112499_.m_85850_().m_85861_(), p_112500_, false, 0, l);
         }
      }

      p_112499_.m_85849_();
   }

   private static boolean m_173641_(SignBlockEntity p_173642_, int p_173643_) {
      if (p_173643_ == DyeColor.BLACK.m_41071_()) {
         return true;
      } else {
         Minecraft minecraft = Minecraft.m_91087_();
         LocalPlayer localplayer = minecraft.f_91074_;
         if (localplayer != null && minecraft.f_91066_.m_92176_().m_90612_() && localplayer.m_150108_()) {
            return true;
         } else {
            Entity entity = minecraft.m_91288_();
            return entity != null && entity.m_20238_(Vec3.m_82512_(p_173642_.m_58899_())) < (double)f_173631_;
         }
      }
   }

   private static int m_173639_(SignBlockEntity p_173640_) {
      int i = p_173640_.m_59753_().m_41071_();
      double d0 = 0.4D;
      int j = (int)((double)NativeImage.m_85085_(i) * 0.4D);
      int k = (int)((double)NativeImage.m_85103_(i) * 0.4D);
      int l = (int)((double)NativeImage.m_85119_(i) * 0.4D);
      return i == DyeColor.BLACK.m_41071_() && p_173640_.m_155727_() ? -988212 : NativeImage.m_84992_(0, l, k, j);
   }

   public static WoodType m_173637_(Block p_173638_) {
      WoodType woodtype;
      if (p_173638_ instanceof SignBlock) {
         woodtype = ((SignBlock)p_173638_).m_56297_();
      } else {
         woodtype = WoodType.f_61830_;
      }

      return woodtype;
   }

   public static SignRenderer.SignModel m_173646_(EntityModelSet p_173647_, WoodType p_173648_) {
      return new SignRenderer.SignModel(p_173647_.m_171103_(ModelLayers.m_171291_(p_173648_)));
   }

   public static LayerDefinition m_173654_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("sign", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.f_171404_);
      partdefinition.m_171599_("stick", CubeListBuilder.m_171558_().m_171514_(0, 14).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   @OnlyIn(Dist.CLIENT)
   public static final class SignModel extends Model {
      public final ModelPart f_173655_;
      public final ModelPart f_112507_;

      public SignModel(ModelPart p_173657_) {
         super(RenderType::m_110458_);
         this.f_173655_ = p_173657_;
         this.f_112507_ = p_173657_.m_171324_("stick");
      }

      public void m_7695_(PoseStack p_112510_, VertexConsumer p_112511_, int p_112512_, int p_112513_, float p_112514_, float p_112515_, float p_112516_, float p_112517_) {
         this.f_173655_.m_104306_(p_112510_, p_112511_, p_112512_, p_112513_, p_112514_, p_112515_, p_112516_, p_112517_);
      }
   }
}