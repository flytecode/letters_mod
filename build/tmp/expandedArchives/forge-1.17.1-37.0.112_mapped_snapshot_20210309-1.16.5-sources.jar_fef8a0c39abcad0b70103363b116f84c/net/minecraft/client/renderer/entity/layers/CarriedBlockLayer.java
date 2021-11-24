package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarriedBlockLayer extends RenderLayer<EnderMan, EndermanModel<EnderMan>> {
   public CarriedBlockLayer(RenderLayerParent<EnderMan, EndermanModel<EnderMan>> p_116626_) {
      super(p_116626_);
   }

   public void m_6494_(PoseStack p_116639_, MultiBufferSource p_116640_, int p_116641_, EnderMan p_116642_, float p_116643_, float p_116644_, float p_116645_, float p_116646_, float p_116647_, float p_116648_) {
      BlockState blockstate = p_116642_.m_32530_();
      if (blockstate != null) {
         p_116639_.m_85836_();
         p_116639_.m_85837_(0.0D, 0.6875D, -0.75D);
         p_116639_.m_85845_(Vector3f.f_122223_.m_122240_(20.0F));
         p_116639_.m_85845_(Vector3f.f_122225_.m_122240_(45.0F));
         p_116639_.m_85837_(0.25D, 0.1875D, 0.25D);
         float f = 0.5F;
         p_116639_.m_85841_(-0.5F, -0.5F, 0.5F);
         p_116639_.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
         Minecraft.m_91087_().m_91289_().m_110912_(blockstate, p_116639_, p_116640_, p_116641_, OverlayTexture.f_118083_);
         p_116639_.m_85849_();
      }
   }
}