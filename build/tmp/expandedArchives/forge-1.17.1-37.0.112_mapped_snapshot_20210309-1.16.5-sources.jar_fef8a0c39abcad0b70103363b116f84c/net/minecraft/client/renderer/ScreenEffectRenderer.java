package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenEffectRenderer {
   private static final ResourceLocation f_110714_ = new ResourceLocation("textures/misc/underwater.png");

   public static void m_110718_(Minecraft p_110719_, PoseStack p_110720_) {
      Player player = p_110719_.f_91074_;
      if (!player.f_19794_) {
         org.apache.commons.lang3.tuple.Pair<BlockState, BlockPos> overlay = getOverlayBlock(player);
         if (overlay != null) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderBlockOverlay(player, p_110720_, net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType.BLOCK, overlay.getLeft(), overlay.getRight()))
                m_173296_(p_110719_.m_91289_().m_110907_().getTexture(overlay.getLeft(), p_110719_.f_91073_, overlay.getRight()), p_110720_);
         }
      }

      if (!p_110719_.f_91074_.m_5833_()) {
         if (p_110719_.f_91074_.m_19941_(FluidTags.f_13131_)) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderWaterOverlay(player, p_110720_))
            m_110725_(p_110719_, p_110720_);
         }

         if (p_110719_.f_91074_.m_6060_()) {
            if (!net.minecraftforge.event.ForgeEventFactory.renderFireOverlay(player, p_110720_))
            m_110728_(p_110719_, p_110720_);
         }
      }

   }

   @Nullable
   private static BlockState m_110716_(Player p_110717_) {
      return getOverlayBlock(p_110717_).getLeft();
   }

   @Nullable
   private static org.apache.commons.lang3.tuple.Pair<BlockState, BlockPos> getOverlayBlock(Player p_110717_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 8; ++i) {
         double d0 = p_110717_.m_20185_() + (double)(((float)((i >> 0) % 2) - 0.5F) * p_110717_.m_20205_() * 0.8F);
         double d1 = p_110717_.m_20188_() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F);
         double d2 = p_110717_.m_20189_() + (double)(((float)((i >> 2) % 2) - 0.5F) * p_110717_.m_20205_() * 0.8F);
         blockpos$mutableblockpos.m_122169_(d0, d1, d2);
         BlockState blockstate = p_110717_.f_19853_.m_8055_(blockpos$mutableblockpos);
         if (blockstate.m_60799_() != RenderShape.INVISIBLE && blockstate.m_60831_(p_110717_.f_19853_, blockpos$mutableblockpos)) {
            return org.apache.commons.lang3.tuple.Pair.of(blockstate, blockpos$mutableblockpos.m_7949_());
         }
      }

      return null;
   }

   private static void m_173296_(TextureAtlasSprite p_173297_, PoseStack p_173298_) {
      RenderSystem.m_157456_(0, p_173297_.m_118414_().m_118330_());
      RenderSystem.m_157427_(GameRenderer::m_172814_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      float f = 0.1F;
      float f1 = -1.0F;
      float f2 = 1.0F;
      float f3 = -1.0F;
      float f4 = 1.0F;
      float f5 = -0.5F;
      float f6 = p_173297_.m_118409_();
      float f7 = p_173297_.m_118410_();
      float f8 = p_173297_.m_118411_();
      float f9 = p_173297_.m_118412_();
      Matrix4f matrix4f = p_173298_.m_85850_().m_85861_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
      bufferbuilder.m_85982_(matrix4f, -1.0F, -1.0F, -0.5F).m_85950_(0.1F, 0.1F, 0.1F, 1.0F).m_7421_(f7, f9).m_5752_();
      bufferbuilder.m_85982_(matrix4f, 1.0F, -1.0F, -0.5F).m_85950_(0.1F, 0.1F, 0.1F, 1.0F).m_7421_(f6, f9).m_5752_();
      bufferbuilder.m_85982_(matrix4f, 1.0F, 1.0F, -0.5F).m_85950_(0.1F, 0.1F, 0.1F, 1.0F).m_7421_(f6, f8).m_5752_();
      bufferbuilder.m_85982_(matrix4f, -1.0F, 1.0F, -0.5F).m_85950_(0.1F, 0.1F, 0.1F, 1.0F).m_7421_(f7, f8).m_5752_();
      bufferbuilder.m_85721_();
      BufferUploader.m_85761_(bufferbuilder);
   }

   private static void m_110725_(Minecraft p_110726_, PoseStack p_110727_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_69493_();
      RenderSystem.m_157456_(0, f_110714_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      float f = p_110726_.f_91074_.m_6073_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157429_(f, f, f, 0.1F);
      float f1 = 4.0F;
      float f2 = -1.0F;
      float f3 = 1.0F;
      float f4 = -1.0F;
      float f5 = 1.0F;
      float f6 = -0.5F;
      float f7 = -p_110726_.f_91074_.m_146908_() / 64.0F;
      float f8 = p_110726_.f_91074_.m_146909_() / 64.0F;
      Matrix4f matrix4f = p_110727_.m_85850_().m_85861_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_85982_(matrix4f, -1.0F, -1.0F, -0.5F).m_7421_(4.0F + f7, 4.0F + f8).m_5752_();
      bufferbuilder.m_85982_(matrix4f, 1.0F, -1.0F, -0.5F).m_7421_(0.0F + f7, 4.0F + f8).m_5752_();
      bufferbuilder.m_85982_(matrix4f, 1.0F, 1.0F, -0.5F).m_7421_(0.0F + f7, 0.0F + f8).m_5752_();
      bufferbuilder.m_85982_(matrix4f, -1.0F, 1.0F, -0.5F).m_7421_(4.0F + f7, 0.0F + f8).m_5752_();
      bufferbuilder.m_85721_();
      BufferUploader.m_85761_(bufferbuilder);
      RenderSystem.m_69461_();
   }

   private static void m_110728_(Minecraft p_110729_, PoseStack p_110730_) {
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172814_);
      RenderSystem.m_69456_(519);
      RenderSystem.m_69458_(false);
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69493_();
      TextureAtlasSprite textureatlassprite = ModelBakery.f_119220_.m_119204_();
      RenderSystem.m_157456_(0, textureatlassprite.m_118414_().m_118330_());
      float f = textureatlassprite.m_118409_();
      float f1 = textureatlassprite.m_118410_();
      float f2 = (f + f1) / 2.0F;
      float f3 = textureatlassprite.m_118411_();
      float f4 = textureatlassprite.m_118412_();
      float f5 = (f3 + f4) / 2.0F;
      float f6 = textureatlassprite.m_118417_();
      float f7 = Mth.m_14179_(f6, f, f2);
      float f8 = Mth.m_14179_(f6, f1, f2);
      float f9 = Mth.m_14179_(f6, f3, f5);
      float f10 = Mth.m_14179_(f6, f4, f5);
      float f11 = 1.0F;

      for(int i = 0; i < 2; ++i) {
         p_110730_.m_85836_();
         float f12 = -0.5F;
         float f13 = 0.5F;
         float f14 = -0.5F;
         float f15 = 0.5F;
         float f16 = -0.5F;
         p_110730_.m_85837_((double)((float)(-(i * 2 - 1)) * 0.24F), (double)-0.3F, 0.0D);
         p_110730_.m_85845_(Vector3f.f_122225_.m_122240_((float)(i * 2 - 1) * 10.0F));
         Matrix4f matrix4f = p_110730_.m_85850_().m_85861_();
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
         bufferbuilder.m_85982_(matrix4f, -0.5F, -0.5F, -0.5F).m_85950_(1.0F, 1.0F, 1.0F, 0.9F).m_7421_(f8, f10).m_5752_();
         bufferbuilder.m_85982_(matrix4f, 0.5F, -0.5F, -0.5F).m_85950_(1.0F, 1.0F, 1.0F, 0.9F).m_7421_(f7, f10).m_5752_();
         bufferbuilder.m_85982_(matrix4f, 0.5F, 0.5F, -0.5F).m_85950_(1.0F, 1.0F, 1.0F, 0.9F).m_7421_(f7, f9).m_5752_();
         bufferbuilder.m_85982_(matrix4f, -0.5F, 0.5F, -0.5F).m_85950_(1.0F, 1.0F, 1.0F, 0.9F).m_7421_(f8, f9).m_5752_();
         bufferbuilder.m_85721_();
         BufferUploader.m_85761_(bufferbuilder);
         p_110730_.m_85849_();
      }

      RenderSystem.m_69461_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69456_(515);
   }
}
