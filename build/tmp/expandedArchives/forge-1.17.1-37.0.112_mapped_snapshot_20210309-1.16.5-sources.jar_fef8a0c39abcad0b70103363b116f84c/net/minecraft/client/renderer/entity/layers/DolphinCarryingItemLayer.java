package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DolphinCarryingItemLayer extends RenderLayer<Dolphin, DolphinModel<Dolphin>> {
   public DolphinCarryingItemLayer(RenderLayerParent<Dolphin, DolphinModel<Dolphin>> p_116884_) {
      super(p_116884_);
   }

   public void m_6494_(PoseStack p_116897_, MultiBufferSource p_116898_, int p_116899_, Dolphin p_116900_, float p_116901_, float p_116902_, float p_116903_, float p_116904_, float p_116905_, float p_116906_) {
      boolean flag = p_116900_.m_5737_() == HumanoidArm.RIGHT;
      p_116897_.m_85836_();
      float f = 1.0F;
      float f1 = -1.0F;
      float f2 = Mth.m_14154_(p_116900_.m_146909_()) / 60.0F;
      if (p_116900_.m_146909_() < 0.0F) {
         p_116897_.m_85837_(0.0D, (double)(1.0F - f2 * 0.5F), (double)(-1.0F + f2 * 0.5F));
      } else {
         p_116897_.m_85837_(0.0D, (double)(1.0F + f2 * 0.8F), (double)(-1.0F + f2 * 0.2F));
      }

      ItemStack itemstack = flag ? p_116900_.m_21205_() : p_116900_.m_21206_();
      Minecraft.m_91087_().m_91292_().m_109322_(p_116900_, itemstack, ItemTransforms.TransformType.GROUND, false, p_116897_, p_116898_, p_116899_);
      p_116897_.m_85849_();
   }
}