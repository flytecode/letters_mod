package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StructureBlockRenderer implements BlockEntityRenderer<StructureBlockEntity> {
   public StructureBlockRenderer(BlockEntityRendererProvider.Context p_173675_) {
   }

   public void m_6922_(StructureBlockEntity p_112583_, float p_112584_, PoseStack p_112585_, MultiBufferSource p_112586_, int p_112587_, int p_112588_) {
      if (Minecraft.m_91087_().f_91074_.m_36337_() || Minecraft.m_91087_().f_91074_.m_5833_()) {
         BlockPos blockpos = p_112583_.m_59902_();
         Vec3i vec3i = p_112583_.m_155805_();
         if (vec3i.m_123341_() >= 1 && vec3i.m_123342_() >= 1 && vec3i.m_123343_() >= 1) {
            if (p_112583_.m_59908_() == StructureMode.SAVE || p_112583_.m_59908_() == StructureMode.LOAD) {
               double d0 = (double)blockpos.m_123341_();
               double d1 = (double)blockpos.m_123343_();
               double d5 = (double)blockpos.m_123342_();
               double d8 = d5 + (double)vec3i.m_123342_();
               double d2;
               double d3;
               switch(p_112583_.m_59905_()) {
               case LEFT_RIGHT:
                  d2 = (double)vec3i.m_123341_();
                  d3 = (double)(-vec3i.m_123343_());
                  break;
               case FRONT_BACK:
                  d2 = (double)(-vec3i.m_123341_());
                  d3 = (double)vec3i.m_123343_();
                  break;
               default:
                  d2 = (double)vec3i.m_123341_();
                  d3 = (double)vec3i.m_123343_();
               }

               double d4;
               double d6;
               double d7;
               double d9;
               switch(p_112583_.m_59906_()) {
               case CLOCKWISE_90:
                  d4 = d3 < 0.0D ? d0 : d0 + 1.0D;
                  d6 = d2 < 0.0D ? d1 + 1.0D : d1;
                  d7 = d4 - d3;
                  d9 = d6 + d2;
                  break;
               case CLOCKWISE_180:
                  d4 = d2 < 0.0D ? d0 : d0 + 1.0D;
                  d6 = d3 < 0.0D ? d1 : d1 + 1.0D;
                  d7 = d4 - d2;
                  d9 = d6 - d3;
                  break;
               case COUNTERCLOCKWISE_90:
                  d4 = d3 < 0.0D ? d0 + 1.0D : d0;
                  d6 = d2 < 0.0D ? d1 : d1 + 1.0D;
                  d7 = d4 + d3;
                  d9 = d6 - d2;
                  break;
               default:
                  d4 = d2 < 0.0D ? d0 + 1.0D : d0;
                  d6 = d3 < 0.0D ? d1 + 1.0D : d1;
                  d7 = d4 + d2;
                  d9 = d6 + d3;
               }

               float f = 1.0F;
               float f1 = 0.9F;
               float f2 = 0.5F;
               VertexConsumer vertexconsumer = p_112586_.m_6299_(RenderType.m_110504_());
               if (p_112583_.m_59908_() == StructureMode.SAVE || p_112583_.m_59835_()) {
                  LevelRenderer.m_109621_(p_112585_, vertexconsumer, d4, d5, d6, d7, d8, d9, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
               }

               if (p_112583_.m_59908_() == StructureMode.SAVE && p_112583_.m_59834_()) {
                  this.m_173676_(p_112583_, vertexconsumer, blockpos, p_112585_);
               }

            }
         }
      }
   }

   private void m_173676_(StructureBlockEntity p_173677_, VertexConsumer p_173678_, BlockPos p_173679_, PoseStack p_173680_) {
      BlockGetter blockgetter = p_173677_.m_58904_();
      BlockPos blockpos = p_173677_.m_58899_();
      BlockPos blockpos1 = blockpos.m_141952_(p_173679_);

      for(BlockPos blockpos2 : BlockPos.m_121940_(blockpos1, blockpos1.m_141952_(p_173677_.m_155805_()).m_142082_(-1, -1, -1))) {
         BlockState blockstate = blockgetter.m_8055_(blockpos2);
         boolean flag = blockstate.m_60795_();
         boolean flag1 = blockstate.m_60713_(Blocks.f_50454_);
         boolean flag2 = blockstate.m_60713_(Blocks.f_50375_);
         boolean flag3 = blockstate.m_60713_(Blocks.f_152480_);
         boolean flag4 = flag1 || flag2 || flag3;
         if (flag || flag4) {
            float f = flag ? 0.05F : 0.0F;
            double d0 = (double)((float)(blockpos2.m_123341_() - blockpos.m_123341_()) + 0.45F - f);
            double d1 = (double)((float)(blockpos2.m_123342_() - blockpos.m_123342_()) + 0.45F - f);
            double d2 = (double)((float)(blockpos2.m_123343_() - blockpos.m_123343_()) + 0.45F - f);
            double d3 = (double)((float)(blockpos2.m_123341_() - blockpos.m_123341_()) + 0.55F + f);
            double d4 = (double)((float)(blockpos2.m_123342_() - blockpos.m_123342_()) + 0.55F + f);
            double d5 = (double)((float)(blockpos2.m_123343_() - blockpos.m_123343_()) + 0.55F + f);
            if (flag) {
               LevelRenderer.m_109621_(p_173680_, p_173678_, d0, d1, d2, d3, d4, d5, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);
            } else if (flag1) {
               LevelRenderer.m_109621_(p_173680_, p_173678_, d0, d1, d2, d3, d4, d5, 1.0F, 0.75F, 0.75F, 1.0F, 1.0F, 0.75F, 0.75F);
            } else if (flag2) {
               LevelRenderer.m_109621_(p_173680_, p_173678_, d0, d1, d2, d3, d4, d5, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F);
            } else if (flag3) {
               LevelRenderer.m_109621_(p_173680_, p_173678_, d0, d1, d2, d3, d4, d5, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
            }
         }
      }

   }

   public boolean m_5932_(StructureBlockEntity p_112581_) {
      return true;
   }

   public int m_142163_() {
      return 96;
   }
}