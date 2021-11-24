package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemCombinerScreen<T extends ItemCombinerMenu> extends AbstractContainerScreen<T> implements ContainerListener {
   private final ResourceLocation f_98899_;

   public ItemCombinerScreen(T p_98901_, Inventory p_98902_, Component p_98903_, ResourceLocation p_98904_) {
      super(p_98901_, p_98902_, p_98903_);
      this.f_98899_ = p_98904_;
   }

   protected void m_5653_() {
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_5653_();
      this.f_97732_.m_38893_(this);
   }

   public void m_7861_() {
      super.m_7861_();
      this.f_97732_.m_38943_(this);
   }

   public void m_6305_(PoseStack p_98922_, int p_98923_, int p_98924_, float p_98925_) {
      this.m_7333_(p_98922_);
      super.m_6305_(p_98922_, p_98923_, p_98924_, p_98925_);
      RenderSystem.m_69461_();
      this.m_6691_(p_98922_, p_98923_, p_98924_, p_98925_);
      this.m_7025_(p_98922_, p_98923_, p_98924_);
   }

   protected void m_6691_(PoseStack p_98927_, int p_98928_, int p_98929_, float p_98930_) {
   }

   protected void m_7286_(PoseStack p_98917_, float p_98918_, int p_98919_, int p_98920_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, this.f_98899_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98917_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      this.m_93228_(p_98917_, i + 59, j + 20, 0, this.f_97727_ + (this.f_97732_.m_38853_(0).m_6657_() ? 0 : 16), 110, 16);
      if ((this.f_97732_.m_38853_(0).m_6657_() || this.f_97732_.m_38853_(1).m_6657_()) && !this.f_97732_.m_38853_(2).m_6657_()) {
         this.m_93228_(p_98917_, i + 99, j + 45, this.f_97726_, 0, 28, 21);
      }

   }

   public void m_142153_(AbstractContainerMenu p_169759_, int p_169760_, int p_169761_) {
   }

   public void m_7934_(AbstractContainerMenu p_98910_, int p_98911_, ItemStack p_98912_) {
   }
}