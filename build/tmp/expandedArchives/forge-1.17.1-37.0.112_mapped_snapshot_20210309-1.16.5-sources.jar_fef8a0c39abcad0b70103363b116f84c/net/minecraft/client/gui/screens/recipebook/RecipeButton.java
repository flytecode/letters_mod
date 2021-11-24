package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeButton extends AbstractWidget {
   private static final ResourceLocation f_100461_ = new ResourceLocation("textures/gui/recipe_book.png");
   private static final float f_170057_ = 15.0F;
   private static final int f_170058_ = 25;
   public static final int f_170056_ = 30;
   private static final Component f_100462_ = new TranslatableComponent("gui.recipebook.moreRecipes");
   private RecipeBookMenu<?> f_100463_;
   private RecipeBook f_100464_;
   private RecipeCollection f_100465_;
   private float f_100466_;
   private float f_100467_;
   private int f_100468_;

   public RecipeButton() {
      super(0, 0, 25, 25, TextComponent.f_131282_);
   }

   public void m_100479_(RecipeCollection p_100480_, RecipeBookPage p_100481_) {
      this.f_100465_ = p_100480_;
      this.f_100463_ = (RecipeBookMenu)p_100481_.m_100441_().f_91074_.f_36096_;
      this.f_100464_ = p_100481_.m_100442_();
      List<Recipe<?>> list = p_100480_.m_100510_(this.f_100464_.m_12689_(this.f_100463_));

      for(Recipe<?> recipe : list) {
         if (this.f_100464_.m_12717_(recipe)) {
            p_100481_.m_100434_(list);
            this.f_100467_ = 15.0F;
            break;
         }
      }

   }

   public RecipeCollection m_100471_() {
      return this.f_100465_;
   }

   public void m_100474_(int p_100475_, int p_100476_) {
      this.f_93620_ = p_100475_;
      this.f_93621_ = p_100476_;
   }

   public void m_6303_(PoseStack p_100484_, int p_100485_, int p_100486_, float p_100487_) {
      if (!Screen.m_96637_()) {
         this.f_100466_ += p_100487_;
      }

      Minecraft minecraft = Minecraft.m_91087_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_100461_);
      int i = 29;
      if (!this.f_100465_.m_100512_()) {
         i += 25;
      }

      int j = 206;
      if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
         j += 25;
      }

      boolean flag = this.f_100467_ > 0.0F;
      PoseStack posestack = RenderSystem.m_157191_();
      if (flag) {
         float f = 1.0F + 0.1F * (float)Math.sin((double)(this.f_100467_ / 15.0F * (float)Math.PI));
         posestack.m_85836_();
         posestack.m_85837_((double)(this.f_93620_ + 8), (double)(this.f_93621_ + 12), 0.0D);
         posestack.m_85841_(f, f, 1.0F);
         posestack.m_85837_((double)(-(this.f_93620_ + 8)), (double)(-(this.f_93621_ + 12)), 0.0D);
         RenderSystem.m_157182_();
         this.f_100467_ -= p_100487_;
      }

      this.m_93228_(p_100484_, this.f_93620_, this.f_93621_, i, j, this.f_93618_, this.f_93619_);
      List<Recipe<?>> list = this.m_100490_();
      this.f_100468_ = Mth.m_14143_(this.f_100466_ / 30.0F) % list.size();
      ItemStack itemstack = list.get(this.f_100468_).m_8043_();
      int k = 4;
      if (this.f_100465_.m_100517_() && this.m_100490_().size() > 1) {
         minecraft.m_91291_().m_174258_(itemstack, this.f_93620_ + k + 1, this.f_93621_ + k + 1, 0, 10);
         --k;
      }

      minecraft.m_91291_().m_115218_(itemstack, this.f_93620_ + k, this.f_93621_ + k);
      if (flag) {
         posestack.m_85849_();
         RenderSystem.m_157182_();
      }

   }

   private List<Recipe<?>> m_100490_() {
      List<Recipe<?>> list = this.f_100465_.m_100513_(true);
      if (!this.f_100464_.m_12689_(this.f_100463_)) {
         list.addAll(this.f_100465_.m_100513_(false));
      }

      return list;
   }

   public boolean m_100482_() {
      return this.m_100490_().size() == 1;
   }

   public Recipe<?> m_100488_() {
      List<Recipe<?>> list = this.m_100490_();
      return list.get(this.f_100468_);
   }

   public List<Component> m_100477_(Screen p_100478_) {
      ItemStack itemstack = this.m_100490_().get(this.f_100468_).m_8043_();
      List<Component> list = Lists.newArrayList(p_100478_.m_96555_(itemstack));
      if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
         list.add(f_100462_);
      }

      return list;
   }

   public void m_142291_(NarrationElementOutput p_170060_) {
      ItemStack itemstack = this.m_100490_().get(this.f_100468_).m_8043_();
      p_170060_.m_169146_(NarratedElementType.TITLE, new TranslatableComponent("narration.recipe", itemstack.m_41786_()));
      if (this.f_100465_.m_100510_(this.f_100464_.m_12689_(this.f_100463_)).size() > 1) {
         p_170060_.m_169149_(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"), new TranslatableComponent("narration.recipe.usage.more"));
      } else {
         p_170060_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.button.usage.hovered"));
      }

   }

   public int m_5711_() {
      return 25;
   }

   protected boolean m_7972_(int p_100473_) {
      return p_100473_ == 0 || p_100473_ == 1;
   }
}