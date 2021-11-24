package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BannerRenderer implements BlockEntityRenderer<BannerBlockEntity> {
   private static final int f_173515_ = 20;
   private static final int f_173516_ = 40;
   private static final int f_173517_ = 16;
   public static final String f_173514_ = "flag";
   private static final String f_173518_ = "pole";
   private static final String f_173519_ = "bar";
   private final ModelPart f_112045_;
   private final ModelPart f_112046_;
   private final ModelPart f_112047_;

   public BannerRenderer(BlockEntityRendererProvider.Context p_173521_) {
      ModelPart modelpart = p_173521_.m_173582_(ModelLayers.f_171264_);
      this.f_112045_ = modelpart.m_171324_("flag");
      this.f_112046_ = modelpart.m_171324_("pole");
      this.f_112047_ = modelpart.m_171324_("bar");
   }

   public static LayerDefinition m_173522_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("flag", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F), PartPose.f_171404_);
      partdefinition.m_171599_("pole", CubeListBuilder.m_171558_().m_171514_(44, 0).m_171481_(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F), PartPose.f_171404_);
      partdefinition.m_171599_("bar", CubeListBuilder.m_171558_().m_171514_(0, 42).m_171481_(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6922_(BannerBlockEntity p_112052_, float p_112053_, PoseStack p_112054_, MultiBufferSource p_112055_, int p_112056_, int p_112057_) {
      List<Pair<BannerPattern, DyeColor>> list = p_112052_.m_58508_();
      if (list != null) {
         float f = 0.6666667F;
         boolean flag = p_112052_.m_58904_() == null;
         p_112054_.m_85836_();
         long i;
         if (flag) {
            i = 0L;
            p_112054_.m_85837_(0.5D, 0.5D, 0.5D);
            this.f_112046_.f_104207_ = true;
         } else {
            i = p_112052_.m_58904_().m_46467_();
            BlockState blockstate = p_112052_.m_58900_();
            if (blockstate.m_60734_() instanceof BannerBlock) {
               p_112054_.m_85837_(0.5D, 0.5D, 0.5D);
               float f1 = (float)(-blockstate.m_61143_(BannerBlock.f_49007_) * 360) / 16.0F;
               p_112054_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
               this.f_112046_.f_104207_ = true;
            } else {
               p_112054_.m_85837_(0.5D, (double)-0.16666667F, 0.5D);
               float f3 = -blockstate.m_61143_(WallBannerBlock.f_57916_).m_122435_();
               p_112054_.m_85845_(Vector3f.f_122225_.m_122240_(f3));
               p_112054_.m_85837_(0.0D, -0.3125D, -0.4375D);
               this.f_112046_.f_104207_ = false;
            }
         }

         p_112054_.m_85836_();
         p_112054_.m_85841_(0.6666667F, -0.6666667F, -0.6666667F);
         VertexConsumer vertexconsumer = ModelBakery.f_119224_.m_119194_(p_112055_, RenderType::m_110446_);
         this.f_112046_.m_104301_(p_112054_, vertexconsumer, p_112056_, p_112057_);
         this.f_112047_.m_104301_(p_112054_, vertexconsumer, p_112056_, p_112057_);
         BlockPos blockpos = p_112052_.m_58899_();
         float f2 = ((float)Math.floorMod((long)(blockpos.m_123341_() * 7 + blockpos.m_123342_() * 9 + blockpos.m_123343_() * 13) + i, 100L) + p_112053_) / 100.0F;
         this.f_112045_.f_104203_ = (-0.0125F + 0.01F * Mth.m_14089_(((float)Math.PI * 2F) * f2)) * (float)Math.PI;
         this.f_112045_.f_104201_ = -32.0F;
         m_112065_(p_112054_, p_112055_, p_112056_, p_112057_, this.f_112045_, ModelBakery.f_119224_, true, list);
         p_112054_.m_85849_();
         p_112054_.m_85849_();
      }
   }

   public static void m_112065_(PoseStack p_112066_, MultiBufferSource p_112067_, int p_112068_, int p_112069_, ModelPart p_112070_, Material p_112071_, boolean p_112072_, List<Pair<BannerPattern, DyeColor>> p_112073_) {
      m_112074_(p_112066_, p_112067_, p_112068_, p_112069_, p_112070_, p_112071_, p_112072_, p_112073_, false);
   }

   public static void m_112074_(PoseStack p_112075_, MultiBufferSource p_112076_, int p_112077_, int p_112078_, ModelPart p_112079_, Material p_112080_, boolean p_112081_, List<Pair<BannerPattern, DyeColor>> p_112082_, boolean p_112083_) {
      p_112079_.m_104301_(p_112075_, p_112080_.m_119197_(p_112076_, RenderType::m_110446_, p_112083_), p_112077_, p_112078_);

      for(int i = 0; i < 17 && i < p_112082_.size(); ++i) {
         Pair<BannerPattern, DyeColor> pair = p_112082_.get(i);
         float[] afloat = pair.getSecond().m_41068_();
         BannerPattern bannerpattern = pair.getFirst();
         Material material = p_112081_ ? Sheets.m_173379_(bannerpattern) : Sheets.m_173383_(bannerpattern);
         p_112079_.m_104306_(p_112075_, material.m_119194_(p_112076_, RenderType::m_110482_), p_112077_, p_112078_, afloat[0], afloat[1], afloat[2], 1.0F);
      }

   }
}