package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhostRecipe {
   @Nullable
   private Recipe<?> f_100136_;
   private final List<GhostRecipe.GhostIngredient> f_100137_ = Lists.newArrayList();
   float f_100138_;

   public void m_100140_() {
      this.f_100136_ = null;
      this.f_100137_.clear();
      this.f_100138_ = 0.0F;
   }

   public void m_100143_(Ingredient p_100144_, int p_100145_, int p_100146_) {
      this.f_100137_.add(new GhostRecipe.GhostIngredient(p_100144_, p_100145_, p_100146_));
   }

   public GhostRecipe.GhostIngredient m_100141_(int p_100142_) {
      return this.f_100137_.get(p_100142_);
   }

   public int m_100158_() {
      return this.f_100137_.size();
   }

   @Nullable
   public Recipe<?> m_100159_() {
      return this.f_100136_;
   }

   public void m_100147_(Recipe<?> p_100148_) {
      this.f_100136_ = p_100148_;
   }

   public void m_100149_(PoseStack p_100150_, Minecraft p_100151_, int p_100152_, int p_100153_, boolean p_100154_, float p_100155_) {
      if (!Screen.m_96637_()) {
         this.f_100138_ += p_100155_;
      }

      for(int i = 0; i < this.f_100137_.size(); ++i) {
         GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.f_100137_.get(i);
         int j = ghostrecipe$ghostingredient.m_100169_() + p_100152_;
         int k = ghostrecipe$ghostingredient.m_100170_() + p_100153_;
         if (i == 0 && p_100154_) {
            GuiComponent.m_93172_(p_100150_, j - 4, k - 4, j + 20, k + 20, 822018048);
         } else {
            GuiComponent.m_93172_(p_100150_, j, k, j + 16, k + 16, 822018048);
         }

         ItemStack itemstack = ghostrecipe$ghostingredient.m_100171_();
         ItemRenderer itemrenderer = p_100151_.m_91291_();
         itemrenderer.m_115218_(itemstack, j, k);
         RenderSystem.m_69456_(516);
         GuiComponent.m_93172_(p_100150_, j, k, j + 16, k + 16, 822083583);
         RenderSystem.m_69456_(515);
         if (i == 0) {
            itemrenderer.m_115169_(p_100151_.f_91062_, itemstack, j, k);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public class GhostIngredient {
      private final Ingredient f_100161_;
      private final int f_100162_;
      private final int f_100163_;

      public GhostIngredient(Ingredient p_100166_, int p_100167_, int p_100168_) {
         this.f_100161_ = p_100166_;
         this.f_100162_ = p_100167_;
         this.f_100163_ = p_100168_;
      }

      public int m_100169_() {
         return this.f_100162_;
      }

      public int m_100170_() {
         return this.f_100163_;
      }

      public ItemStack m_100171_() {
         ItemStack[] aitemstack = this.f_100161_.m_43908_();
         return aitemstack.length == 0 ? ItemStack.f_41583_ : aitemstack[Mth.m_14143_(GhostRecipe.this.f_100138_ / 30.0F) % aitemstack.length];
      }
   }
}