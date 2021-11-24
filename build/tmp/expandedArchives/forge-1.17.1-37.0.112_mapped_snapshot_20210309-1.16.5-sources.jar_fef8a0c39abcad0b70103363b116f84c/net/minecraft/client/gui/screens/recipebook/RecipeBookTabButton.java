package net.minecraft.client.gui.screens.recipebook;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeBookTabButton extends StateSwitchingButton {
   private final RecipeBookCategories f_100445_;
   private static final float f_170055_ = 15.0F;
   private float f_100446_;

   public RecipeBookTabButton(RecipeBookCategories p_100448_) {
      super(0, 0, 35, 27, false);
      this.f_100445_ = p_100448_;
      this.m_94624_(153, 2, 35, 0, RecipeBookComponent.f_100268_);
   }

   public void m_100451_(Minecraft p_100452_) {
      ClientRecipeBook clientrecipebook = p_100452_.f_91074_.m_108631_();
      List<RecipeCollection> list = clientrecipebook.m_90623_(this.f_100445_);
      if (p_100452_.f_91074_.f_36096_ instanceof RecipeBookMenu) {
         for(RecipeCollection recipecollection : list) {
            for(Recipe<?> recipe : recipecollection.m_100510_(clientrecipebook.m_12689_((RecipeBookMenu)p_100452_.f_91074_.f_36096_))) {
               if (clientrecipebook.m_12717_(recipe)) {
                  this.f_100446_ = 15.0F;
                  return;
               }
            }
         }

      }
   }

   public void m_6303_(PoseStack p_100457_, int p_100458_, int p_100459_, float p_100460_) {
      if (this.f_100446_ > 0.0F) {
         float f = 1.0F + 0.1F * (float)Math.sin((double)(this.f_100446_ / 15.0F * (float)Math.PI));
         p_100457_.m_85836_();
         p_100457_.m_85837_((double)(this.f_93620_ + 8), (double)(this.f_93621_ + 12), 0.0D);
         p_100457_.m_85841_(1.0F, f, 1.0F);
         p_100457_.m_85837_((double)(-(this.f_93620_ + 8)), (double)(-(this.f_93621_ + 12)), 0.0D);
      }

      Minecraft minecraft = Minecraft.m_91087_();
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

      int k = this.f_93620_;
      if (this.f_94609_) {
         k -= 2;
      }

      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      this.m_93228_(p_100457_, k, this.f_93621_, i, j, this.f_93618_, this.f_93619_);
      RenderSystem.m_69482_();
      this.m_100453_(minecraft.m_91291_());
      if (this.f_100446_ > 0.0F) {
         p_100457_.m_85849_();
         this.f_100446_ -= p_100460_;
      }

   }

   private void m_100453_(ItemRenderer p_100454_) {
      List<ItemStack> list = this.f_100445_.m_92268_();
      int i = this.f_94609_ ? -2 : 0;
      if (list.size() == 1) {
         p_100454_.m_115218_(list.get(0), this.f_93620_ + 9 + i, this.f_93621_ + 5);
      } else if (list.size() == 2) {
         p_100454_.m_115218_(list.get(0), this.f_93620_ + 3 + i, this.f_93621_ + 5);
         p_100454_.m_115218_(list.get(1), this.f_93620_ + 14 + i, this.f_93621_ + 5);
      }

   }

   public RecipeBookCategories m_100455_() {
      return this.f_100445_;
   }

   public boolean m_100449_(ClientRecipeBook p_100450_) {
      List<RecipeCollection> list = p_100450_.m_90623_(this.f_100445_);
      this.f_93624_ = false;
      if (list != null) {
         for(RecipeCollection recipecollection : list) {
            if (recipecollection.m_100498_() && recipecollection.m_100515_()) {
               this.f_93624_ = true;
               break;
            }
         }
      }

      return this.f_93624_;
   }
}