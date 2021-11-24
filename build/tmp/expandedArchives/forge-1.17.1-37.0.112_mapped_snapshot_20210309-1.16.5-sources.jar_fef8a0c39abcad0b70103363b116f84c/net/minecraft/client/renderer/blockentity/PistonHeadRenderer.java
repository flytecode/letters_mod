package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PistonHeadRenderer implements BlockEntityRenderer<PistonMovingBlockEntity> {
   private BlockRenderDispatcher f_112441_;

   public PistonHeadRenderer(BlockEntityRendererProvider.Context p_173623_) {
      this.f_112441_ = p_173623_.m_173584_();
   }

   public void m_6922_(PistonMovingBlockEntity p_112452_, float p_112453_, PoseStack p_112454_, MultiBufferSource p_112455_, int p_112456_, int p_112457_) {
      Level level = p_112452_.m_58904_();
      if (level != null) {
         BlockPos blockpos = p_112452_.m_58899_().m_142300_(p_112452_.m_60399_().m_122424_());
         BlockState blockstate = p_112452_.m_60400_();
         if (!blockstate.m_60795_()) {
            ModelBlockRenderer.m_111000_();
            p_112454_.m_85836_();
            p_112454_.m_85837_((double)p_112452_.m_60380_(p_112453_), (double)p_112452_.m_60385_(p_112453_), (double)p_112452_.m_60388_(p_112453_));
            if (blockstate.m_60713_(Blocks.f_50040_) && p_112452_.m_60350_(p_112453_) <= 4.0F) {
               blockstate = blockstate.m_61124_(PistonHeadBlock.f_60236_, Boolean.valueOf(p_112452_.m_60350_(p_112453_) <= 0.5F));
               this.m_112458_(blockpos, blockstate, p_112454_, p_112455_, level, false, p_112457_);
            } else if (p_112452_.m_60397_() && !p_112452_.m_60387_()) {
               PistonType pistontype = blockstate.m_60713_(Blocks.f_50032_) ? PistonType.STICKY : PistonType.DEFAULT;
               BlockState blockstate1 = Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_60235_, pistontype).m_61124_(PistonHeadBlock.f_52588_, blockstate.m_61143_(PistonBaseBlock.f_52588_));
               blockstate1 = blockstate1.m_61124_(PistonHeadBlock.f_60236_, Boolean.valueOf(p_112452_.m_60350_(p_112453_) >= 0.5F));
               this.m_112458_(blockpos, blockstate1, p_112454_, p_112455_, level, false, p_112457_);
               BlockPos blockpos1 = blockpos.m_142300_(p_112452_.m_60399_());
               p_112454_.m_85849_();
               p_112454_.m_85836_();
               blockstate = blockstate.m_61124_(PistonBaseBlock.f_60153_, Boolean.valueOf(true));
               this.m_112458_(blockpos1, blockstate, p_112454_, p_112455_, level, true, p_112457_);
            } else {
               this.m_112458_(blockpos, blockstate, p_112454_, p_112455_, level, false, p_112457_);
            }

            p_112454_.m_85849_();
            ModelBlockRenderer.m_111077_();
         }
      }
   }

   private void m_112458_(BlockPos p_112459_, BlockState p_112460_, PoseStack p_112461_, MultiBufferSource p_112462_, Level p_112463_, boolean p_112464_, int p_112465_) {
      net.minecraftforge.client.ForgeHooksClient.renderPistonMovedBlocks(p_112459_, p_112460_, p_112461_, p_112462_, p_112463_, p_112464_, p_112465_, f_112441_ == null ? f_112441_ = net.minecraft.client.Minecraft.m_91087_().m_91289_() : f_112441_);
      if(false) {
      RenderType rendertype = ItemBlockRenderTypes.m_109293_(p_112460_);
      VertexConsumer vertexconsumer = p_112462_.m_6299_(rendertype);
      this.f_112441_.m_110937_().m_111047_(p_112463_, this.f_112441_.m_110910_(p_112460_), p_112460_, p_112459_, p_112461_, vertexconsumer, p_112464_, new Random(), p_112460_.m_60726_(p_112459_), p_112465_);
      }
   }

   public int m_142163_() {
      return 68;
   }
}
