package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeBookPage {
   public static final int f_170052_ = 20;
   private final List<RecipeButton> f_100394_ = Lists.newArrayListWithCapacity(20);
   @Nullable
   private RecipeButton f_100395_;
   private final OverlayRecipeComponent f_100396_ = new OverlayRecipeComponent();
   private Minecraft f_100397_;
   private final List<RecipeShownListener> f_100398_ = Lists.newArrayList();
   private List<RecipeCollection> f_100399_ = ImmutableList.of();
   private StateSwitchingButton f_100400_;
   private StateSwitchingButton f_100401_;
   private int f_100402_;
   private int f_100403_;
   private RecipeBook f_100404_;
   @Nullable
   private Recipe<?> f_100405_;
   @Nullable
   private RecipeCollection f_100406_;

   public RecipeBookPage() {
      for(int i = 0; i < 20; ++i) {
         this.f_100394_.add(new RecipeButton());
      }

   }

   public void m_100428_(Minecraft p_100429_, int p_100430_, int p_100431_) {
      this.f_100397_ = p_100429_;
      this.f_100404_ = p_100429_.f_91074_.m_108631_();

      for(int i = 0; i < this.f_100394_.size(); ++i) {
         this.f_100394_.get(i).m_100474_(p_100430_ + 11 + 25 * (i % 5), p_100431_ + 31 + 25 * (i / 5));
      }

      this.f_100400_ = new StateSwitchingButton(p_100430_ + 93, p_100431_ + 137, 12, 17, false);
      this.f_100400_.m_94624_(1, 208, 13, 18, RecipeBookComponent.f_100268_);
      this.f_100401_ = new StateSwitchingButton(p_100430_ + 38, p_100431_ + 137, 12, 17, true);
      this.f_100401_.m_94624_(1, 208, 13, 18, RecipeBookComponent.f_100268_);
   }

   public void m_100432_(RecipeBookComponent p_100433_) {
      this.f_100398_.remove(p_100433_);
      this.f_100398_.add(p_100433_);
   }

   public void m_100436_(List<RecipeCollection> p_100437_, boolean p_100438_) {
      this.f_100399_ = p_100437_;
      this.f_100402_ = (int)Math.ceil((double)p_100437_.size() / 20.0D);
      if (this.f_100402_ <= this.f_100403_ || p_100438_) {
         this.f_100403_ = 0;
      }

      this.m_100443_();
   }

   private void m_100443_() {
      int i = 20 * this.f_100403_;

      for(int j = 0; j < this.f_100394_.size(); ++j) {
         RecipeButton recipebutton = this.f_100394_.get(j);
         if (i + j < this.f_100399_.size()) {
            RecipeCollection recipecollection = this.f_100399_.get(i + j);
            recipebutton.m_100479_(recipecollection, this);
            recipebutton.f_93624_ = true;
         } else {
            recipebutton.f_93624_ = false;
         }
      }

      this.m_100444_();
   }

   private void m_100444_() {
      this.f_100400_.f_93624_ = this.f_100402_ > 1 && this.f_100403_ < this.f_100402_ - 1;
      this.f_100401_.f_93624_ = this.f_100402_ > 1 && this.f_100403_ > 0;
   }

   public void m_100421_(PoseStack p_100422_, int p_100423_, int p_100424_, int p_100425_, int p_100426_, float p_100427_) {
      if (this.f_100402_ > 1) {
         String s = this.f_100403_ + 1 + "/" + this.f_100402_;
         int i = this.f_100397_.f_91062_.m_92895_(s);
         this.f_100397_.f_91062_.m_92883_(p_100422_, s, (float)(p_100423_ - i / 2 + 73), (float)(p_100424_ + 141), -1);
      }

      this.f_100395_ = null;

      for(RecipeButton recipebutton : this.f_100394_) {
         recipebutton.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
         if (recipebutton.f_93624_ && recipebutton.m_5702_()) {
            this.f_100395_ = recipebutton;
         }
      }

      this.f_100401_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
      this.f_100400_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
      this.f_100396_.m_6305_(p_100422_, p_100425_, p_100426_, p_100427_);
   }

   public void m_100417_(PoseStack p_100418_, int p_100419_, int p_100420_) {
      if (this.f_100397_.f_91080_ != null && this.f_100395_ != null && !this.f_100396_.m_100212_()) {
         this.f_100397_.f_91080_.renderComponentTooltip(p_100418_, this.f_100395_.m_100477_(this.f_100397_.f_91080_), p_100419_, p_100420_, this.f_100395_.m_100488_().m_8043_());
      }

   }

   @Nullable
   public Recipe<?> m_100408_() {
      return this.f_100405_;
   }

   @Nullable
   public RecipeCollection m_100439_() {
      return this.f_100406_;
   }

   public void m_100440_() {
      this.f_100396_.m_100204_(false);
   }

   public boolean m_100409_(double p_100410_, double p_100411_, int p_100412_, int p_100413_, int p_100414_, int p_100415_, int p_100416_) {
      this.f_100405_ = null;
      this.f_100406_ = null;
      if (this.f_100396_.m_100212_()) {
         if (this.f_100396_.m_6375_(p_100410_, p_100411_, p_100412_)) {
            this.f_100405_ = this.f_100396_.m_100206_();
            this.f_100406_ = this.f_100396_.m_100184_();
         } else {
            this.f_100396_.m_100204_(false);
         }

         return true;
      } else if (this.f_100400_.m_6375_(p_100410_, p_100411_, p_100412_)) {
         ++this.f_100403_;
         this.m_100443_();
         return true;
      } else if (this.f_100401_.m_6375_(p_100410_, p_100411_, p_100412_)) {
         --this.f_100403_;
         this.m_100443_();
         return true;
      } else {
         for(RecipeButton recipebutton : this.f_100394_) {
            if (recipebutton.m_6375_(p_100410_, p_100411_, p_100412_)) {
               if (p_100412_ == 0) {
                  this.f_100405_ = recipebutton.m_100488_();
                  this.f_100406_ = recipebutton.m_100471_();
               } else if (p_100412_ == 1 && !this.f_100396_.m_100212_() && !recipebutton.m_100482_()) {
                  this.f_100396_.m_100194_(this.f_100397_, recipebutton.m_100471_(), recipebutton.f_93620_, recipebutton.f_93621_, p_100413_ + p_100415_ / 2, p_100414_ + 13 + p_100416_ / 2, (float)recipebutton.m_5711_());
               }

               return true;
            }
         }

         return false;
      }
   }

   public void m_100434_(List<Recipe<?>> p_100435_) {
      for(RecipeShownListener recipeshownlistener : this.f_100398_) {
         recipeshownlistener.m_7262_(p_100435_);
      }

   }

   public Minecraft m_100441_() {
      return this.f_100397_;
   }

   public RecipeBook m_100442_() {
      return this.f_100404_;
   }

   protected void m_170053_(Consumer<AbstractWidget> p_170054_) {
      p_170054_.accept(this.f_100400_);
      p_170054_.accept(this.f_100401_);
      this.f_100394_.forEach(p_170054_);
   }
}
