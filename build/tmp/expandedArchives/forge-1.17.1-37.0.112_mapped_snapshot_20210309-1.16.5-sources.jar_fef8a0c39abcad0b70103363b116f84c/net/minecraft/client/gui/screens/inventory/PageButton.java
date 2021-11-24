package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PageButton extends Button {
   private final boolean f_99222_;
   private final boolean f_99223_;

   public PageButton(int p_99225_, int p_99226_, boolean p_99227_, Button.OnPress p_99228_, boolean p_99229_) {
      super(p_99225_, p_99226_, 23, 13, TextComponent.f_131282_, p_99228_);
      this.f_99222_ = p_99227_;
      this.f_99223_ = p_99229_;
   }

   public void m_6303_(PoseStack p_99233_, int p_99234_, int p_99235_, float p_99236_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, BookViewScreen.f_98252_);
      int i = 0;
      int j = 192;
      if (this.m_5702_()) {
         i += 23;
      }

      if (!this.f_99222_) {
         j += 13;
      }

      this.m_93228_(p_99233_, this.f_93620_, this.f_93621_, i, j, 23, 13);
   }

   public void m_7435_(SoundManager p_99231_) {
      if (this.f_99223_) {
         p_99231_.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_11713_, 1.0F));
      }

   }
}