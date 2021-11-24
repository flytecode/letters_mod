package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayRecipeComponent extends GuiComponent implements Widget, GuiEventListener {
   static final ResourceLocation f_100172_ = new ResourceLocation("textures/gui/recipe_book.png");
   private static final int f_170036_ = 4;
   private static final int f_170037_ = 5;
   private static final float f_170038_ = 0.375F;
   private final List<OverlayRecipeComponent.OverlayRecipeButton> f_100173_ = Lists.newArrayList();
   private boolean f_100174_;
   private int f_100175_;
   private int f_100176_;
   Minecraft f_100177_;
   private RecipeCollection f_100178_;
   @Nullable
   private Recipe<?> f_100179_;
   float f_100180_;
   boolean f_100181_;

   public void m_100194_(Minecraft p_100195_, RecipeCollection p_100196_, int p_100197_, int p_100198_, int p_100199_, int p_100200_, float p_100201_) {
      this.f_100177_ = p_100195_;
      this.f_100178_ = p_100196_;
      if (p_100195_.f_91074_.f_36096_ instanceof AbstractFurnaceMenu) {
         this.f_100181_ = true;
      }

      boolean flag = p_100195_.f_91074_.m_108631_().m_12689_((RecipeBookMenu)p_100195_.f_91074_.f_36096_);
      List<Recipe<?>> list = p_100196_.m_100513_(true);
      List<Recipe<?>> list1 = flag ? Collections.emptyList() : p_100196_.m_100513_(false);
      int i = list.size();
      int j = i + list1.size();
      int k = j <= 16 ? 4 : 5;
      int l = (int)Math.ceil((double)((float)j / (float)k));
      this.f_100175_ = p_100197_;
      this.f_100176_ = p_100198_;
      int i1 = 25;
      float f = (float)(this.f_100175_ + Math.min(j, k) * 25);
      float f1 = (float)(p_100199_ + 50);
      if (f > f1) {
         this.f_100175_ = (int)((float)this.f_100175_ - p_100201_ * (float)((int)((f - f1) / p_100201_)));
      }

      float f2 = (float)(this.f_100176_ + l * 25);
      float f3 = (float)(p_100200_ + 50);
      if (f2 > f3) {
         this.f_100176_ = (int)((float)this.f_100176_ - p_100201_ * (float)Mth.m_14167_((f2 - f3) / p_100201_));
      }

      float f4 = (float)this.f_100176_;
      float f5 = (float)(p_100200_ - 100);
      if (f4 < f5) {
         this.f_100176_ = (int)((float)this.f_100176_ - p_100201_ * (float)Mth.m_14167_((f4 - f5) / p_100201_));
      }

      this.f_100174_ = true;
      this.f_100173_.clear();

      for(int j1 = 0; j1 < j; ++j1) {
         boolean flag1 = j1 < i;
         Recipe<?> recipe = flag1 ? list.get(j1) : list1.get(j1 - i);
         int k1 = this.f_100175_ + 4 + 25 * (j1 % k);
         int l1 = this.f_100176_ + 5 + 25 * (j1 / k);
         if (this.f_100181_) {
            this.f_100173_.add(new OverlayRecipeComponent.OverlaySmeltingRecipeButton(k1, l1, recipe, flag1));
         } else {
            this.f_100173_.add(new OverlayRecipeComponent.OverlayRecipeButton(k1, l1, recipe, flag1));
         }
      }

      this.f_100179_ = null;
   }

   public boolean m_5755_(boolean p_100224_) {
      return false;
   }

   public RecipeCollection m_100184_() {
      return this.f_100178_;
   }

   @Nullable
   public Recipe<?> m_100206_() {
      return this.f_100179_;
   }

   public boolean m_6375_(double p_100186_, double p_100187_, int p_100188_) {
      if (p_100188_ != 0) {
         return false;
      } else {
         for(OverlayRecipeComponent.OverlayRecipeButton overlayrecipecomponent$overlayrecipebutton : this.f_100173_) {
            if (overlayrecipecomponent$overlayrecipebutton.m_6375_(p_100186_, p_100187_, p_100188_)) {
               this.f_100179_ = overlayrecipecomponent$overlayrecipebutton.f_100228_;
               return true;
            }
         }

         return false;
      }
   }

   public boolean m_5953_(double p_100208_, double p_100209_) {
      return false;
   }

   public void m_6305_(PoseStack p_100190_, int p_100191_, int p_100192_, float p_100193_) {
      if (this.f_100174_) {
         this.f_100180_ += p_100193_;
         RenderSystem.m_69478_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, f_100172_);
         p_100190_.m_85836_();
         p_100190_.m_85837_(0.0D, 0.0D, 170.0D);
         int i = this.f_100173_.size() <= 16 ? 4 : 5;
         int j = Math.min(this.f_100173_.size(), i);
         int k = Mth.m_14167_((float)this.f_100173_.size() / (float)i);
         int l = 24;
         int i1 = 4;
         int j1 = 82;
         int k1 = 208;
         this.m_100213_(p_100190_, j, k, 24, 4, 82, 208);
         RenderSystem.m_69461_();

         for(OverlayRecipeComponent.OverlayRecipeButton overlayrecipecomponent$overlayrecipebutton : this.f_100173_) {
            overlayrecipecomponent$overlayrecipebutton.m_6305_(p_100190_, p_100191_, p_100192_, p_100193_);
         }

         p_100190_.m_85849_();
      }
   }

   private void m_100213_(PoseStack p_100214_, int p_100215_, int p_100216_, int p_100217_, int p_100218_, int p_100219_, int p_100220_) {
      this.m_93228_(p_100214_, this.f_100175_, this.f_100176_, p_100219_, p_100220_, p_100218_, p_100218_);
      this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_, p_100219_ + p_100217_ + p_100218_, p_100220_, p_100218_, p_100218_);
      this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
      this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);

      for(int i = 0; i < p_100215_; ++i) {
         this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + i * p_100217_, this.f_100176_, p_100219_ + p_100218_, p_100220_, p_100217_, p_100218_);
         this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + (i + 1) * p_100217_, this.f_100176_, p_100219_ + p_100218_, p_100220_, p_100218_, p_100218_);

         for(int j = 0; j < p_100216_; ++j) {
            if (i == 0) {
               this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ + j * p_100217_, p_100219_, p_100220_ + p_100218_, p_100218_, p_100217_);
               this.m_93228_(p_100214_, this.f_100175_, this.f_100176_ + p_100218_ + (j + 1) * p_100217_, p_100219_, p_100220_ + p_100218_, p_100218_, p_100218_);
            }

            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + i * p_100217_, this.f_100176_ + p_100218_ + j * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100217_, p_100217_);
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + (i + 1) * p_100217_, this.f_100176_ + p_100218_ + j * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100217_);
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + i * p_100217_, this.f_100176_ + p_100218_ + (j + 1) * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100217_, p_100218_);
            this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + (i + 1) * p_100217_ - 1, this.f_100176_ + p_100218_ + (j + 1) * p_100217_ - 1, p_100219_ + p_100218_, p_100220_ + p_100218_, p_100218_ + 1, p_100218_ + 1);
            if (i == p_100215_ - 1) {
               this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ + j * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100217_);
               this.m_93228_(p_100214_, this.f_100175_ + p_100218_ * 2 + p_100215_ * p_100217_, this.f_100176_ + p_100218_ + (j + 1) * p_100217_, p_100219_ + p_100217_ + p_100218_, p_100220_ + p_100218_, p_100218_, p_100218_);
            }
         }

         this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + i * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100217_, p_100218_);
         this.m_93228_(p_100214_, this.f_100175_ + p_100218_ + (i + 1) * p_100217_, this.f_100176_ + p_100218_ * 2 + p_100216_ * p_100217_, p_100219_ + p_100218_, p_100220_ + p_100217_ + p_100218_, p_100218_, p_100218_);
      }

   }

   public void m_100204_(boolean p_100205_) {
      this.f_100174_ = p_100205_;
   }

   public boolean m_100212_() {
      return this.f_100174_;
   }

   @OnlyIn(Dist.CLIENT)
   class OverlayRecipeButton extends AbstractWidget implements PlaceRecipe<Ingredient> {
      final Recipe<?> f_100228_;
      private final boolean f_100229_;
      protected final List<OverlayRecipeComponent.OverlayRecipeButton.Pos> f_100226_ = Lists.newArrayList();

      public OverlayRecipeButton(int p_100232_, int p_100233_, Recipe<?> p_100234_, boolean p_100235_) {
         super(p_100232_, p_100233_, 200, 20, TextComponent.f_131282_);
         this.f_93618_ = 24;
         this.f_93619_ = 24;
         this.f_100228_ = p_100234_;
         this.f_100229_ = p_100235_;
         this.m_6222_(p_100234_);
      }

      protected void m_6222_(Recipe<?> p_100236_) {
         this.m_135408_(3, 3, -1, p_100236_, p_100236_.m_7527_().iterator(), 0);
      }

      public void m_142291_(NarrationElementOutput p_170040_) {
         this.m_168802_(p_170040_);
      }

      public void m_5817_(Iterator<Ingredient> p_100240_, int p_100241_, int p_100242_, int p_100243_, int p_100244_) {
         ItemStack[] aitemstack = p_100240_.next().m_43908_();
         if (aitemstack.length != 0) {
            this.f_100226_.add(new OverlayRecipeComponent.OverlayRecipeButton.Pos(3 + p_100244_ * 7, 3 + p_100243_ * 7, aitemstack));
         }

      }

      public void m_6303_(PoseStack p_100246_, int p_100247_, int p_100248_, float p_100249_) {
         RenderSystem.m_157456_(0, OverlayRecipeComponent.f_100172_);
         int i = 152;
         if (!this.f_100229_) {
            i += 26;
         }

         int j = OverlayRecipeComponent.this.f_100181_ ? 130 : 78;
         if (this.m_5702_()) {
            j += 26;
         }

         this.m_93228_(p_100246_, this.f_93620_, this.f_93621_, i, j, this.f_93618_, this.f_93619_);
         PoseStack posestack = RenderSystem.m_157191_();
         posestack.m_85836_();
         posestack.m_85837_((double)(this.f_93620_ + 2), (double)(this.f_93621_ + 2), 125.0D);

         for(OverlayRecipeComponent.OverlayRecipeButton.Pos overlayrecipecomponent$overlayrecipebutton$pos : this.f_100226_) {
            posestack.m_85836_();
            posestack.m_85837_((double)overlayrecipecomponent$overlayrecipebutton$pos.f_100251_, (double)overlayrecipecomponent$overlayrecipebutton$pos.f_100252_, 0.0D);
            posestack.m_85841_(0.375F, 0.375F, 1.0F);
            posestack.m_85837_(-8.0D, -8.0D, 0.0D);
            RenderSystem.m_157182_();
            OverlayRecipeComponent.this.f_100177_.m_91291_().m_115203_(overlayrecipecomponent$overlayrecipebutton$pos.f_100250_[Mth.m_14143_(OverlayRecipeComponent.this.f_100180_ / 30.0F) % overlayrecipecomponent$overlayrecipebutton$pos.f_100250_.length], 0, 0);
            posestack.m_85849_();
         }

         posestack.m_85849_();
         RenderSystem.m_157182_();
      }

      @OnlyIn(Dist.CLIENT)
      protected class Pos {
         public final ItemStack[] f_100250_;
         public final int f_100251_;
         public final int f_100252_;

         public Pos(int p_100256_, int p_100257_, ItemStack[] p_100258_) {
            this.f_100251_ = p_100256_;
            this.f_100252_ = p_100257_;
            this.f_100250_ = p_100258_;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   class OverlaySmeltingRecipeButton extends OverlayRecipeComponent.OverlayRecipeButton {
      public OverlaySmeltingRecipeButton(int p_100262_, int p_100263_, Recipe<?> p_100264_, boolean p_100265_) {
         super(p_100262_, p_100263_, p_100264_, p_100265_);
      }

      protected void m_6222_(Recipe<?> p_100267_) {
         ItemStack[] aitemstack = p_100267_.m_7527_().get(0).m_43908_();
         this.f_100226_.add(new OverlayRecipeComponent.OverlayRecipeButton.Pos(10, 10, aitemstack));
      }
   }
}