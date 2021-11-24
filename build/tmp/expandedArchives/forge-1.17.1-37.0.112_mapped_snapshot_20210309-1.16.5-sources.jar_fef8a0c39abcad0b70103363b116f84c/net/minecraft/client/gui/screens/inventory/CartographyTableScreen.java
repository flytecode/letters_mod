package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CartographyTableScreen extends AbstractContainerScreen<CartographyTableMenu> {
   private static final ResourceLocation f_98346_ = new ResourceLocation("textures/gui/container/cartography_table.png");

   public CartographyTableScreen(CartographyTableMenu p_98349_, Inventory p_98350_, Component p_98351_) {
      super(p_98349_, p_98350_, p_98351_);
      this.f_97729_ -= 2;
   }

   public void m_6305_(PoseStack p_98363_, int p_98364_, int p_98365_, float p_98366_) {
      super.m_6305_(p_98363_, p_98364_, p_98365_, p_98366_);
      this.m_7025_(p_98363_, p_98364_, p_98365_);
   }

   protected void m_7286_(PoseStack p_98358_, float p_98359_, int p_98360_, int p_98361_) {
      this.m_7333_(p_98358_);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98346_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(p_98358_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      ItemStack itemstack = this.f_97732_.m_38853_(1).m_7993_();
      boolean flag = itemstack.m_150930_(Items.f_42676_);
      boolean flag1 = itemstack.m_150930_(Items.f_42516_);
      boolean flag2 = itemstack.m_150930_(Items.f_42027_);
      ItemStack itemstack1 = this.f_97732_.m_38853_(0).m_7993_();
      boolean flag3 = false;
      Integer integer;
      MapItemSavedData mapitemsaveddata;
      if (itemstack1.m_150930_(Items.f_42573_)) {
         integer = MapItem.m_151131_(itemstack1);
         mapitemsaveddata = MapItem.m_151128_(integer, this.f_96541_.f_91073_);
         if (mapitemsaveddata != null) {
            if (mapitemsaveddata.f_77892_) {
               flag3 = true;
               if (flag1 || flag2) {
                  this.m_93228_(p_98358_, i + 35, j + 31, this.f_97726_ + 50, 132, 28, 21);
               }
            }

            if (flag1 && mapitemsaveddata.f_77890_ >= 4) {
               flag3 = true;
               this.m_93228_(p_98358_, i + 35, j + 31, this.f_97726_ + 50, 132, 28, 21);
            }
         }
      } else {
         integer = null;
         mapitemsaveddata = null;
      }

      this.m_169710_(p_98358_, integer, mapitemsaveddata, flag, flag1, flag2, flag3);
   }

   private void m_169710_(PoseStack p_169711_, @Nullable Integer p_169712_, @Nullable MapItemSavedData p_169713_, boolean p_169714_, boolean p_169715_, boolean p_169716_, boolean p_169717_) {
      int i = this.f_97735_;
      int j = this.f_97736_;
      if (p_169715_ && !p_169717_) {
         this.m_93228_(p_169711_, i + 67, j + 13, this.f_97726_, 66, 66, 66);
         this.m_169703_(p_169711_, p_169712_, p_169713_, i + 85, j + 31, 0.226F);
      } else if (p_169714_) {
         this.m_93228_(p_169711_, i + 67 + 16, j + 13, this.f_97726_, 132, 50, 66);
         this.m_169703_(p_169711_, p_169712_, p_169713_, i + 86, j + 16, 0.34F);
         RenderSystem.m_157456_(0, f_98346_);
         p_169711_.m_85836_();
         p_169711_.m_85837_(0.0D, 0.0D, 1.0D);
         this.m_93228_(p_169711_, i + 67, j + 13 + 16, this.f_97726_, 132, 50, 66);
         this.m_169703_(p_169711_, p_169712_, p_169713_, i + 70, j + 32, 0.34F);
         p_169711_.m_85849_();
      } else if (p_169716_) {
         this.m_93228_(p_169711_, i + 67, j + 13, this.f_97726_, 0, 66, 66);
         this.m_169703_(p_169711_, p_169712_, p_169713_, i + 71, j + 17, 0.45F);
         RenderSystem.m_157456_(0, f_98346_);
         p_169711_.m_85836_();
         p_169711_.m_85837_(0.0D, 0.0D, 1.0D);
         this.m_93228_(p_169711_, i + 66, j + 12, 0, this.f_97727_, 66, 66);
         p_169711_.m_85849_();
      } else {
         this.m_93228_(p_169711_, i + 67, j + 13, this.f_97726_, 0, 66, 66);
         this.m_169703_(p_169711_, p_169712_, p_169713_, i + 71, j + 17, 0.45F);
      }

   }

   private void m_169703_(PoseStack p_169704_, @Nullable Integer p_169705_, @Nullable MapItemSavedData p_169706_, int p_169707_, int p_169708_, float p_169709_) {
      if (p_169705_ != null && p_169706_ != null) {
         p_169704_.m_85836_();
         p_169704_.m_85837_((double)p_169707_, (double)p_169708_, 1.0D);
         p_169704_.m_85841_(p_169709_, p_169709_, 1.0F);
         MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
         this.f_96541_.f_91063_.m_109151_().m_168771_(p_169704_, multibuffersource$buffersource, p_169705_, p_169706_, true, 15728880);
         multibuffersource$buffersource.m_109911_();
         p_169704_.m_85849_();
      }

   }
}