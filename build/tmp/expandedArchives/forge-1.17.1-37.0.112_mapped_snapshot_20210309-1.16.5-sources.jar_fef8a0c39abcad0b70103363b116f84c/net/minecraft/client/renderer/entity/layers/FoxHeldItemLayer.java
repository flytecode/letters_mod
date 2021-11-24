package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FoxHeldItemLayer extends RenderLayer<Fox, FoxModel<Fox>> {
   public FoxHeldItemLayer(RenderLayerParent<Fox, FoxModel<Fox>> p_116994_) {
      super(p_116994_);
   }

   public void m_6494_(PoseStack p_117007_, MultiBufferSource p_117008_, int p_117009_, Fox p_117010_, float p_117011_, float p_117012_, float p_117013_, float p_117014_, float p_117015_, float p_117016_) {
      boolean flag = p_117010_.m_5803_();
      boolean flag1 = p_117010_.m_6162_();
      p_117007_.m_85836_();
      if (flag1) {
         float f = 0.75F;
         p_117007_.m_85841_(0.75F, 0.75F, 0.75F);
         p_117007_.m_85837_(0.0D, 0.5D, (double)0.209375F);
      }

      p_117007_.m_85837_((double)((this.m_117386_()).f_102638_.f_104200_ / 16.0F), (double)((this.m_117386_()).f_102638_.f_104201_ / 16.0F), (double)((this.m_117386_()).f_102638_.f_104202_ / 16.0F));
      float f1 = p_117010_.m_28620_(p_117013_);
      p_117007_.m_85845_(Vector3f.f_122227_.m_122270_(f1));
      p_117007_.m_85845_(Vector3f.f_122225_.m_122240_(p_117015_));
      p_117007_.m_85845_(Vector3f.f_122223_.m_122240_(p_117016_));
      if (p_117010_.m_6162_()) {
         if (flag) {
            p_117007_.m_85837_((double)0.4F, (double)0.26F, (double)0.15F);
         } else {
            p_117007_.m_85837_((double)0.06F, (double)0.26F, -0.5D);
         }
      } else if (flag) {
         p_117007_.m_85837_((double)0.46F, (double)0.26F, (double)0.22F);
      } else {
         p_117007_.m_85837_((double)0.06F, (double)0.27F, -0.5D);
      }

      p_117007_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      if (flag) {
         p_117007_.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
      }

      ItemStack itemstack = p_117010_.m_6844_(EquipmentSlot.MAINHAND);
      Minecraft.m_91087_().m_91292_().m_109322_(p_117010_, itemstack, ItemTransforms.TransformType.GROUND, false, p_117007_, p_117008_, p_117009_);
      p_117007_.m_85849_();
   }
}