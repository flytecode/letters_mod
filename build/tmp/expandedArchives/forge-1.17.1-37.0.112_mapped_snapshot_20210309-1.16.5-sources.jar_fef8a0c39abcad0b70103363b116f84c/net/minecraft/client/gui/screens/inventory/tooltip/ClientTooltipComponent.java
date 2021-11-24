package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ClientTooltipComponent {
   static ClientTooltipComponent m_169948_(FormattedCharSequence p_169949_) {
      return new ClientTextTooltip(p_169949_);
   }

   static ClientTooltipComponent m_169950_(TooltipComponent p_169951_) {
      if (p_169951_ instanceof BundleTooltip) {
         return new ClientBundleTooltip((BundleTooltip)p_169951_);
      } else {
         ClientTooltipComponent result = net.minecraftforge.client.MinecraftForgeClient.getClientTooltipComponent(p_169951_);
         if (result != null) return result;
         throw new IllegalArgumentException("Unknown TooltipComponent");
      }
   }

   int m_142103_();

   int m_142069_(Font p_169952_);

   default void m_142440_(Font p_169953_, int p_169954_, int p_169955_, Matrix4f p_169956_, MultiBufferSource.BufferSource p_169957_) {
   }

   default void m_142162_(Font p_169958_, int p_169959_, int p_169960_, PoseStack p_169961_, ItemRenderer p_169962_, int p_169963_, TextureManager p_169964_) {
   }
}
