package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingBlockRenderer extends EntityRenderer<FallingBlockEntity> {
   public FallingBlockRenderer(EntityRendererProvider.Context p_174112_) {
      super(p_174112_);
      this.f_114477_ = 0.5F;
   }

   public void m_7392_(FallingBlockEntity p_114634_, float p_114635_, float p_114636_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
      BlockState blockstate = p_114634_.m_31980_();
      if (blockstate.m_60799_() == RenderShape.MODEL) {
         Level level = p_114634_.m_31979_();
         if (blockstate != level.m_8055_(p_114634_.m_142538_()) && blockstate.m_60799_() != RenderShape.INVISIBLE) {
            p_114637_.m_85836_();
            BlockPos blockpos = new BlockPos(p_114634_.m_20185_(), p_114634_.m_142469_().f_82292_, p_114634_.m_20189_());
            p_114637_.m_85837_(-0.5D, 0.0D, -0.5D);
            BlockRenderDispatcher blockrenderdispatcher = Minecraft.m_91087_().m_91289_();
            for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.m_110506_()) {
               if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
                  net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
                  blockrenderdispatcher.m_110937_().m_111047_(level, blockrenderdispatcher.m_110910_(blockstate), blockstate, blockpos, p_114637_, p_114638_.m_6299_(type), false, new Random(), blockstate.m_60726_(p_114634_.m_31978_()), OverlayTexture.f_118083_);
               }
            }
            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
            p_114637_.m_85849_();
            super.m_7392_(p_114634_, p_114635_, p_114636_, p_114637_, p_114638_, p_114639_);
         }
      }
   }

   public ResourceLocation m_5478_(FallingBlockEntity p_114632_) {
      return TextureAtlas.f_118259_;
   }
}
