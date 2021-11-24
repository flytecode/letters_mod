package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CampfireRenderer implements BlockEntityRenderer<CampfireBlockEntity> {
   private static final float f_173600_ = 0.375F;

   public CampfireRenderer(BlockEntityRendererProvider.Context p_173602_) {
   }

   public void m_6922_(CampfireBlockEntity p_112344_, float p_112345_, PoseStack p_112346_, MultiBufferSource p_112347_, int p_112348_, int p_112349_) {
      Direction direction = p_112344_.m_58900_().m_61143_(CampfireBlock.f_51230_);
      NonNullList<ItemStack> nonnulllist = p_112344_.m_59065_();
      int i = (int)p_112344_.m_58899_().m_121878_();

      for(int j = 0; j < nonnulllist.size(); ++j) {
         ItemStack itemstack = nonnulllist.get(j);
         if (itemstack != ItemStack.f_41583_) {
            p_112346_.m_85836_();
            p_112346_.m_85837_(0.5D, 0.44921875D, 0.5D);
            Direction direction1 = Direction.m_122407_((j + direction.m_122416_()) % 4);
            float f = -direction1.m_122435_();
            p_112346_.m_85845_(Vector3f.f_122225_.m_122240_(f));
            p_112346_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
            p_112346_.m_85837_(-0.3125D, -0.3125D, 0.0D);
            p_112346_.m_85841_(0.375F, 0.375F, 0.375F);
            Minecraft.m_91087_().m_91291_().m_174269_(itemstack, ItemTransforms.TransformType.FIXED, p_112348_, p_112349_, p_112346_, p_112347_, i + j);
            p_112346_.m_85849_();
         }
      }

   }
}