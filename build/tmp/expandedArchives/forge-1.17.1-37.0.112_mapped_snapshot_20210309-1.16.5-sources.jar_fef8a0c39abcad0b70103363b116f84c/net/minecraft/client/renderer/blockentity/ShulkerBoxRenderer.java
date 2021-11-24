package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerBoxRenderer implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
   private final ShulkerModel<?> f_112466_;

   public ShulkerBoxRenderer(BlockEntityRendererProvider.Context p_173626_) {
      this.f_112466_ = new ShulkerModel(p_173626_.m_173582_(ModelLayers.f_171180_));
   }

   public void m_6922_(ShulkerBoxBlockEntity p_112478_, float p_112479_, PoseStack p_112480_, MultiBufferSource p_112481_, int p_112482_, int p_112483_) {
      Direction direction = Direction.UP;
      if (p_112478_.m_58898_()) {
         BlockState blockstate = p_112478_.m_58904_().m_8055_(p_112478_.m_58899_());
         if (blockstate.m_60734_() instanceof ShulkerBoxBlock) {
            direction = blockstate.m_61143_(ShulkerBoxBlock.f_56183_);
         }
      }

      DyeColor dyecolor = p_112478_.m_59701_();
      Material material;
      if (dyecolor == null) {
         material = Sheets.f_110741_;
      } else {
         material = Sheets.f_110742_.get(dyecolor.m_41060_());
      }

      p_112480_.m_85836_();
      p_112480_.m_85837_(0.5D, 0.5D, 0.5D);
      float f = 0.9995F;
      p_112480_.m_85841_(0.9995F, 0.9995F, 0.9995F);
      p_112480_.m_85845_(direction.m_122406_());
      p_112480_.m_85841_(1.0F, -1.0F, -1.0F);
      p_112480_.m_85837_(0.0D, -1.0D, 0.0D);
      ModelPart modelpart = this.f_112466_.m_103742_();
      modelpart.m_104227_(0.0F, 24.0F - p_112478_.m_59657_(p_112479_) * 0.5F * 16.0F, 0.0F);
      modelpart.f_104204_ = 270.0F * p_112478_.m_59657_(p_112479_) * ((float)Math.PI / 180F);
      VertexConsumer vertexconsumer = material.m_119194_(p_112481_, RenderType::m_110458_);
      this.f_112466_.m_7695_(p_112480_, vertexconsumer, p_112482_, p_112483_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_112480_.m_85849_();
   }
}