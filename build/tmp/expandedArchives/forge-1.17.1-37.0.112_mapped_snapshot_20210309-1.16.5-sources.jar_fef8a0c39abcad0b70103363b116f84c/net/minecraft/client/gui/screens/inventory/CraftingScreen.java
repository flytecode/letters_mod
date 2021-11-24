package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CraftingScreen extends AbstractContainerScreen<CraftingMenu> implements RecipeUpdateListener {
   private static final ResourceLocation f_98442_ = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation f_98443_ = new ResourceLocation("textures/gui/recipe_button.png");
   private final RecipeBookComponent f_98444_ = new RecipeBookComponent();
   private boolean f_98445_;

   public CraftingScreen(CraftingMenu p_98448_, Inventory p_98449_, Component p_98450_) {
      super(p_98448_, p_98449_, p_98450_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_98445_ = this.f_96543_ < 379;
      this.f_98444_.m_100309_(this.f_96543_, this.f_96544_, this.f_96541_, this.f_98445_, this.f_97732_);
      this.f_97735_ = this.f_98444_.m_181401_(this.f_96543_, this.f_97726_);
      this.m_142416_(new ImageButton(this.f_97735_ + 5, this.f_96544_ / 2 - 49, 20, 18, 0, 0, 19, f_98443_, (p_98484_) -> {
         this.f_98444_.m_100384_();
         this.f_97735_ = this.f_98444_.m_181401_(this.f_96543_, this.f_97726_);
         ((ImageButton)p_98484_).m_94278_(this.f_97735_ + 5, this.f_96544_ / 2 - 49);
      }));
      this.m_7787_(this.f_98444_);
      this.m_94718_(this.f_98444_);
      this.f_97728_ = 29;
   }

   public void m_181908_() {
      super.m_181908_();
      this.f_98444_.m_100386_();
   }

   public void m_6305_(PoseStack p_98479_, int p_98480_, int p_98481_, float p_98482_) {
      this.m_7333_(p_98479_);
      if (this.f_98444_.m_100385_() && this.f_98445_) {
         this.m_7286_(p_98479_, p_98482_, p_98480_, p_98481_);
         this.f_98444_.m_6305_(p_98479_, p_98480_, p_98481_, p_98482_);
      } else {
         this.f_98444_.m_6305_(p_98479_, p_98480_, p_98481_, p_98482_);
         super.m_6305_(p_98479_, p_98480_, p_98481_, p_98482_);
         this.f_98444_.m_6545_(p_98479_, this.f_97735_, this.f_97736_, true, p_98482_);
      }

      this.m_7025_(p_98479_, p_98480_, p_98481_);
      this.f_98444_.m_100361_(p_98479_, this.f_97735_, this.f_97736_, p_98480_, p_98481_);
   }

   protected void m_7286_(PoseStack p_98474_, float p_98475_, int p_98476_, int p_98477_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98442_);
      int i = this.f_97735_;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98474_, i, j, 0, 0, this.f_97726_, this.f_97727_);
   }

   protected boolean m_6774_(int p_98462_, int p_98463_, int p_98464_, int p_98465_, double p_98466_, double p_98467_) {
      return (!this.f_98445_ || !this.f_98444_.m_100385_()) && super.m_6774_(p_98462_, p_98463_, p_98464_, p_98465_, p_98466_, p_98467_);
   }

   public boolean m_6375_(double p_98452_, double p_98453_, int p_98454_) {
      if (this.f_98444_.m_6375_(p_98452_, p_98453_, p_98454_)) {
         this.m_7522_(this.f_98444_);
         return true;
      } else {
         return this.f_98445_ && this.f_98444_.m_100385_() ? true : super.m_6375_(p_98452_, p_98453_, p_98454_);
      }
   }

   protected boolean m_7467_(double p_98456_, double p_98457_, int p_98458_, int p_98459_, int p_98460_) {
      boolean flag = p_98456_ < (double)p_98458_ || p_98457_ < (double)p_98459_ || p_98456_ >= (double)(p_98458_ + this.f_97726_) || p_98457_ >= (double)(p_98459_ + this.f_97727_);
      return this.f_98444_.m_100297_(p_98456_, p_98457_, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, p_98460_) && flag;
   }

   protected void m_6597_(Slot p_98469_, int p_98470_, int p_98471_, ClickType p_98472_) {
      super.m_6597_(p_98469_, p_98470_, p_98471_, p_98472_);
      this.f_98444_.m_6904_(p_98469_);
   }

   public void m_6916_() {
      this.f_98444_.m_100387_();
   }

   public void m_7861_() {
      this.f_98444_.m_100373_();
      super.m_7861_();
   }

   public RecipeBookComponent m_5564_() {
      return this.f_98444_;
   }
}