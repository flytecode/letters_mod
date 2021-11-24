package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SolidFaceRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113666_;

   public SolidFaceRenderer(Minecraft p_113668_) {
      this.f_113666_ = p_113668_;
   }

   public void m_7790_(PoseStack p_113670_, MultiBufferSource p_113671_, double p_113672_, double p_113673_, double p_113674_) {
      BlockGetter blockgetter = this.f_113666_.f_91074_.f_19853_;
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69832_(2.0F);
      RenderSystem.m_69472_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      BlockPos blockpos = new BlockPos(p_113672_, p_113673_, p_113674_);

      for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-6, -6, -6), blockpos.m_142082_(6, 6, 6))) {
         BlockState blockstate = blockgetter.m_8055_(blockpos1);
         if (!blockstate.m_60713_(Blocks.f_50016_)) {
            VoxelShape voxelshape = blockstate.m_60808_(blockgetter, blockpos1);

            for(AABB aabb : voxelshape.m_83299_()) {
               AABB aabb1 = aabb.m_82338_(blockpos1).m_82400_(0.002D).m_82386_(-p_113672_, -p_113673_, -p_113674_);
               double d0 = aabb1.f_82288_;
               double d1 = aabb1.f_82289_;
               double d2 = aabb1.f_82290_;
               double d3 = aabb1.f_82291_;
               double d4 = aabb1.f_82292_;
               double d5 = aabb1.f_82293_;
               float f = 1.0F;
               float f1 = 0.0F;
               float f2 = 0.0F;
               float f3 = 0.5F;
               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.WEST)) {
                  Tesselator tesselator = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder = tesselator.m_85915_();
                  bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder.m_5483_(d0, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder.m_5483_(d0, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder.m_5483_(d0, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder.m_5483_(d0, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator.m_85914_();
               }

               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.SOUTH)) {
                  Tesselator tesselator1 = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder1 = tesselator1.m_85915_();
                  bufferbuilder1.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder1.m_5483_(d0, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder1.m_5483_(d0, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder1.m_5483_(d3, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder1.m_5483_(d3, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator1.m_85914_();
               }

               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.EAST)) {
                  Tesselator tesselator2 = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder2 = tesselator2.m_85915_();
                  bufferbuilder2.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder2.m_5483_(d3, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder2.m_5483_(d3, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder2.m_5483_(d3, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder2.m_5483_(d3, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator2.m_85914_();
               }

               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.NORTH)) {
                  Tesselator tesselator3 = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder3 = tesselator3.m_85915_();
                  bufferbuilder3.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder3.m_5483_(d3, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder3.m_5483_(d3, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder3.m_5483_(d0, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder3.m_5483_(d0, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator3.m_85914_();
               }

               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.DOWN)) {
                  Tesselator tesselator4 = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder4 = tesselator4.m_85915_();
                  bufferbuilder4.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder4.m_5483_(d0, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder4.m_5483_(d3, d1, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder4.m_5483_(d0, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder4.m_5483_(d3, d1, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator4.m_85914_();
               }

               if (blockstate.m_60783_(blockgetter, blockpos1, Direction.UP)) {
                  Tesselator tesselator5 = Tesselator.m_85913_();
                  BufferBuilder bufferbuilder5 = tesselator5.m_85915_();
                  bufferbuilder5.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);
                  bufferbuilder5.m_5483_(d0, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder5.m_5483_(d0, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder5.m_5483_(d3, d4, d2).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  bufferbuilder5.m_5483_(d3, d4, d5).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
                  tesselator5.m_85914_();
               }
            }
         }
      }

      RenderSystem.m_69458_(true);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }
}