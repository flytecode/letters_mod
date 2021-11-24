package net.minecraft.client.renderer.blockentity;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface BlockEntityRendererProvider<T extends BlockEntity> {
   BlockEntityRenderer<T> m_173570_(BlockEntityRendererProvider.Context p_173571_);

   @OnlyIn(Dist.CLIENT)
   public static class Context {
      private final BlockEntityRenderDispatcher f_173572_;
      private final BlockRenderDispatcher f_173573_;
      private final EntityModelSet f_173574_;
      private final Font f_173575_;

      public Context(BlockEntityRenderDispatcher p_173577_, BlockRenderDispatcher p_173578_, EntityModelSet p_173579_, Font p_173580_) {
         this.f_173572_ = p_173577_;
         this.f_173573_ = p_173578_;
         this.f_173574_ = p_173579_;
         this.f_173575_ = p_173580_;
      }

      public BlockEntityRenderDispatcher m_173581_() {
         return this.f_173572_;
      }

      public BlockRenderDispatcher m_173584_() {
         return this.f_173573_;
      }

      public EntityModelSet m_173585_() {
         return this.f_173574_;
      }

      public ModelPart m_173582_(ModelLayerLocation p_173583_) {
         return this.f_173574_.m_171103_(p_173583_);
      }

      public Font m_173586_() {
         return this.f_173575_;
      }
   }
}