package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LecternRenderer implements BlockEntityRenderer<LecternBlockEntity> {
   private final BookModel f_112424_;

   public LecternRenderer(BlockEntityRendererProvider.Context p_173621_) {
      this.f_112424_ = new BookModel(p_173621_.m_173582_(ModelLayers.f_171271_));
   }

   public void m_6922_(LecternBlockEntity p_112435_, float p_112436_, PoseStack p_112437_, MultiBufferSource p_112438_, int p_112439_, int p_112440_) {
      BlockState blockstate = p_112435_.m_58900_();
      if (blockstate.m_61143_(LecternBlock.f_54467_)) {
         p_112437_.m_85836_();
         p_112437_.m_85837_(0.5D, 1.0625D, 0.5D);
         float f = blockstate.m_61143_(LecternBlock.f_54465_).m_122427_().m_122435_();
         p_112437_.m_85845_(Vector3f.f_122225_.m_122240_(-f));
         p_112437_.m_85845_(Vector3f.f_122227_.m_122240_(67.5F));
         p_112437_.m_85837_(0.0D, -0.125D, 0.0D);
         this.f_112424_.m_102292_(0.0F, 0.1F, 0.9F, 1.2F);
         VertexConsumer vertexconsumer = EnchantTableRenderer.f_112405_.m_119194_(p_112438_, RenderType::m_110446_);
         this.f_112424_.m_102316_(p_112437_, vertexconsumer, p_112439_, p_112440_, 1.0F, 1.0F, 1.0F, 1.0F);
         p_112437_.m_85849_();
      }
   }
}