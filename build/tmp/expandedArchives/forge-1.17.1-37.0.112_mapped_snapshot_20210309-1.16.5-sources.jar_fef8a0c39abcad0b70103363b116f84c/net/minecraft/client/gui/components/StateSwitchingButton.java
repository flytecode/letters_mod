package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StateSwitchingButton extends AbstractWidget {
   protected ResourceLocation f_94608_;
   protected boolean f_94609_;
   protected int f_94610_;
   protected int f_94611_;
   protected int f_94612_;
   protected int f_94613_;

   public StateSwitchingButton(int p_94615_, int p_94616_, int p_94617_, int p_94618_, boolean p_94619_) {
      super(p_94615_, p_94616_, p_94617_, p_94618_, TextComponent.f_131282_);
      this.f_94609_ = p_94619_;
   }

   public void m_94624_(int p_94625_, int p_94626_, int p_94627_, int p_94628_, ResourceLocation p_94629_) {
      this.f_94610_ = p_94625_;
      this.f_94611_ = p_94626_;
      this.f_94612_ = p_94627_;
      this.f_94613_ = p_94628_;
      this.f_94608_ = p_94629_;
   }

   public void m_94635_(boolean p_94636_) {
      this.f_94609_ = p_94636_;
   }

   public boolean m_94620_() {
      return this.f_94609_;
   }

   public void m_94621_(int p_94622_, int p_94623_) {
      this.f_93620_ = p_94622_;
      this.f_93621_ = p_94623_;
   }

   public void m_142291_(NarrationElementOutput p_169069_) {
      this.m_168802_(p_169069_);
   }

   public void m_6303_(PoseStack p_94631_, int p_94632_, int p_94633_, float p_94634_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, this.f_94608_);
      RenderSystem.m_69465_();
      int i = this.f_94610_;
      int j = this.f_94611_;
      if (this.f_94609_) {
         i += this.f_94612_;
      }

      if (this.m_5702_()) {
         j += this.f_94613_;
      }

      this.m_93228_(p_94631_, this.f_93620_, this.f_93621_, i, j, this.f_93618_, this.f_93619_);
      RenderSystem.m_69482_();
   }
}