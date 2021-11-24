package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TntMinecartRenderer extends MinecartRenderer<MinecartTNT> {
   public TntMinecartRenderer(EntityRendererProvider.Context p_174424_) {
      super(p_174424_, ModelLayers.f_171253_);
   }

   protected void m_7002_(MinecartTNT p_116151_, float p_116152_, BlockState p_116153_, PoseStack p_116154_, MultiBufferSource p_116155_, int p_116156_) {
      int i = p_116151_.m_38694_();
      if (i > -1 && (float)i - p_116152_ + 1.0F < 10.0F) {
         float f = 1.0F - ((float)i - p_116152_ + 1.0F) / 10.0F;
         f = Mth.m_14036_(f, 0.0F, 1.0F);
         f = f * f;
         f = f * f;
         float f1 = 1.0F + f * 0.3F;
         p_116154_.m_85841_(f1, f1, f1);
      }

      m_116157_(p_116153_, p_116154_, p_116155_, p_116156_, i > -1 && i / 5 % 2 == 0);
   }

   public static void m_116157_(BlockState p_116158_, PoseStack p_116159_, MultiBufferSource p_116160_, int p_116161_, boolean p_116162_) {
      int i;
      if (p_116162_) {
         i = OverlayTexture.m_118093_(OverlayTexture.m_118088_(1.0F), 10);
      } else {
         i = OverlayTexture.f_118083_;
      }

      Minecraft.m_91087_().m_91289_().m_110912_(p_116158_, p_116159_, p_116160_, p_116161_, i);
   }
}