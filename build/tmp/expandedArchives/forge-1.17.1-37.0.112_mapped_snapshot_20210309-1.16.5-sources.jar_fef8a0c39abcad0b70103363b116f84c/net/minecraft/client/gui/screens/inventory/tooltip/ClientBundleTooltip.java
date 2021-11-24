package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientBundleTooltip implements ClientTooltipComponent {
   public static final ResourceLocation f_169863_ = new ResourceLocation("textures/gui/container/bundle.png");
   private static final int f_169864_ = 4;
   private static final int f_169865_ = 1;
   private static final int f_169866_ = 128;
   private static final int f_169867_ = 18;
   private static final int f_169868_ = 20;
   private final NonNullList<ItemStack> f_169869_;
   private final int f_169870_;

   public ClientBundleTooltip(BundleTooltip p_169873_) {
      this.f_169869_ = p_169873_.m_150679_();
      this.f_169870_ = p_169873_.m_150680_();
   }

   public int m_142103_() {
      return this.m_169911_() * 20 + 2 + 4;
   }

   public int m_142069_(Font p_169901_) {
      return this.m_169910_() * 18 + 2;
   }

   public void m_142162_(Font p_169903_, int p_169904_, int p_169905_, PoseStack p_169906_, ItemRenderer p_169907_, int p_169908_, TextureManager p_169909_) {
      int i = this.m_169910_();
      int j = this.m_169911_();
      boolean flag = this.f_169870_ >= 64;
      int k = 0;

      for(int l = 0; l < j; ++l) {
         for(int i1 = 0; i1 < i; ++i1) {
            int j1 = p_169904_ + i1 * 18 + 1;
            int k1 = p_169905_ + l * 20 + 1;
            this.m_169883_(j1, k1, k++, flag, p_169903_, p_169906_, p_169907_, p_169908_, p_169909_);
         }
      }

      this.m_169875_(p_169904_, p_169905_, i, j, p_169906_, p_169908_, p_169909_);
   }

   private void m_169883_(int p_169884_, int p_169885_, int p_169886_, boolean p_169887_, Font p_169888_, PoseStack p_169889_, ItemRenderer p_169890_, int p_169891_, TextureManager p_169892_) {
      if (p_169886_ >= this.f_169869_.size()) {
         this.m_169893_(p_169889_, p_169884_, p_169885_, p_169891_, p_169892_, p_169887_ ? ClientBundleTooltip.Texture.BLOCKED_SLOT : ClientBundleTooltip.Texture.SLOT);
      } else {
         ItemStack itemstack = this.f_169869_.get(p_169886_);
         this.m_169893_(p_169889_, p_169884_, p_169885_, p_169891_, p_169892_, ClientBundleTooltip.Texture.SLOT);
         p_169890_.m_174253_(itemstack, p_169884_ + 1, p_169885_ + 1, p_169886_);
         p_169890_.m_115169_(p_169888_, itemstack, p_169884_ + 1, p_169885_ + 1);
         if (p_169886_ == 0) {
            AbstractContainerScreen.m_169606_(p_169889_, p_169884_ + 1, p_169885_ + 1, p_169891_);
         }

      }
   }

   private void m_169875_(int p_169876_, int p_169877_, int p_169878_, int p_169879_, PoseStack p_169880_, int p_169881_, TextureManager p_169882_) {
      this.m_169893_(p_169880_, p_169876_, p_169877_, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_CORNER_TOP);
      this.m_169893_(p_169880_, p_169876_ + p_169878_ * 18 + 1, p_169877_, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_CORNER_TOP);

      for(int i = 0; i < p_169878_; ++i) {
         this.m_169893_(p_169880_, p_169876_ + 1 + i * 18, p_169877_, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_HORIZONTAL_TOP);
         this.m_169893_(p_169880_, p_169876_ + 1 + i * 18, p_169877_ + p_169879_ * 20, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_HORIZONTAL_BOTTOM);
      }

      for(int j = 0; j < p_169879_; ++j) {
         this.m_169893_(p_169880_, p_169876_, p_169877_ + j * 20 + 1, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_VERTICAL);
         this.m_169893_(p_169880_, p_169876_ + p_169878_ * 18 + 1, p_169877_ + j * 20 + 1, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_VERTICAL);
      }

      this.m_169893_(p_169880_, p_169876_, p_169877_ + p_169879_ * 20, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_CORNER_BOTTOM);
      this.m_169893_(p_169880_, p_169876_ + p_169878_ * 18 + 1, p_169877_ + p_169879_ * 20, p_169881_, p_169882_, ClientBundleTooltip.Texture.BORDER_CORNER_BOTTOM);
   }

   private void m_169893_(PoseStack p_169894_, int p_169895_, int p_169896_, int p_169897_, TextureManager p_169898_, ClientBundleTooltip.Texture p_169899_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_169863_);
      GuiComponent.m_93143_(p_169894_, p_169895_, p_169896_, p_169897_, (float)p_169899_.f_169919_, (float)p_169899_.f_169920_, p_169899_.f_169921_, p_169899_.f_169922_, 128, 128);
   }

   private int m_169910_() {
      return Math.max(2, (int)Math.ceil(Math.sqrt((double)this.f_169869_.size() + 1.0D)));
   }

   private int m_169911_() {
      return (int)Math.ceil(((double)this.f_169869_.size() + 1.0D) / (double)this.m_169910_());
   }

   @OnlyIn(Dist.CLIENT)
   static enum Texture {
      SLOT(0, 0, 18, 20),
      BLOCKED_SLOT(0, 40, 18, 20),
      BORDER_VERTICAL(0, 18, 1, 20),
      BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
      BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
      BORDER_CORNER_TOP(0, 20, 1, 1),
      BORDER_CORNER_BOTTOM(0, 60, 1, 1);

      public final int f_169919_;
      public final int f_169920_;
      public final int f_169921_;
      public final int f_169922_;

      private Texture(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
         this.f_169919_ = p_169928_;
         this.f_169920_ = p_169929_;
         this.f_169921_ = p_169930_;
         this.f_169922_ = p_169931_;
      }
   }
}