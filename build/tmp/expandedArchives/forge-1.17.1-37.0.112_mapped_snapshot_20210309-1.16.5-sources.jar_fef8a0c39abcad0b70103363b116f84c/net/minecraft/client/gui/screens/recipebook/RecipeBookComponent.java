package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeBookComponent extends GuiComponent implements Widget, GuiEventListener, NarratableEntry, RecipeShownListener, PlaceRecipe<Ingredient> {
   protected static final ResourceLocation f_100268_ = new ResourceLocation("textures/gui/recipe_book.png");
   private static final Component f_100273_ = (new TranslatableComponent("gui.recipebook.search_hint")).m_130940_(ChatFormatting.ITALIC).m_130940_(ChatFormatting.GRAY);
   public static final int f_170042_ = 147;
   public static final int f_170043_ = 166;
   private static final int f_170044_ = 86;
   private static final Component f_100274_ = new TranslatableComponent("gui.recipebook.toggleRecipes.craftable");
   private static final Component f_100275_ = new TranslatableComponent("gui.recipebook.toggleRecipes.all");
   private int f_100276_;
   private int f_100277_;
   private int f_100278_;
   protected final GhostRecipe f_100269_ = new GhostRecipe();
   private final List<RecipeBookTabButton> f_100279_ = Lists.newArrayList();
   @Nullable
   private RecipeBookTabButton f_100280_;
   protected StateSwitchingButton f_100270_;
   protected RecipeBookMenu<?> f_100271_;
   protected Minecraft f_100272_;
   @Nullable
   private EditBox f_100281_;
   private String f_100282_ = "";
   private ClientRecipeBook f_100283_;
   private final RecipeBookPage f_100284_ = new RecipeBookPage();
   private final StackedContents f_100285_ = new StackedContents();
   private int f_100286_;
   private boolean f_100287_;
   private boolean f_170041_;
   private boolean f_181400_;

   public void m_100309_(int p_100310_, int p_100311_, Minecraft p_100312_, boolean p_100313_, RecipeBookMenu<?> p_100314_) {
      this.f_100272_ = p_100312_;
      this.f_100277_ = p_100310_;
      this.f_100278_ = p_100311_;
      this.f_100271_ = p_100314_;
      this.f_181400_ = p_100313_;
      p_100312_.f_91074_.f_36096_ = p_100314_;
      this.f_100283_ = p_100312_.f_91074_.m_108631_();
      this.f_100286_ = p_100312_.f_91074_.m_150109_().m_36072_();
      this.f_170041_ = this.m_170050_();
      if (this.f_170041_) {
         this.m_181404_();
      }

      p_100312_.f_91068_.m_90926_(true);
   }

   public void m_181404_() {
      this.f_100276_ = this.f_181400_ ? 0 : 86;
      int i = (this.f_100277_ - 147) / 2 - this.f_100276_;
      int j = (this.f_100278_ - 166) / 2;
      this.f_100285_.m_36453_();
      this.f_100272_.f_91074_.m_150109_().m_36010_(this.f_100285_);
      this.f_100271_.m_5816_(this.f_100285_);
      String s = this.f_100281_ != null ? this.f_100281_.m_94155_() : "";
      this.f_100281_ = new EditBox(this.f_100272_.f_91062_, i + 25, j + 14, 80, 9 + 5, new TranslatableComponent("itemGroup.search"));
      this.f_100281_.m_94199_(50);
      this.f_100281_.m_94182_(false);
      this.f_100281_.m_94194_(true);
      this.f_100281_.m_94202_(16777215);
      this.f_100281_.m_94144_(s);
      this.f_100284_.m_100428_(this.f_100272_, i, j);
      this.f_100284_.m_100432_(this);
      this.f_100270_ = new StateSwitchingButton(i + 110, j + 12, 26, 16, this.f_100283_.m_12689_(this.f_100271_));
      this.m_5674_();
      this.f_100279_.clear();

      for(RecipeBookCategories recipebookcategories : this.f_100271_.getRecipeBookCategories()) {
         this.f_100279_.add(new RecipeBookTabButton(recipebookcategories));
      }

      if (this.f_100280_ != null) {
         this.f_100280_ = this.f_100279_.stream().filter((p_100329_) -> {
            return p_100329_.m_100455_().equals(this.f_100280_.m_100455_());
         }).findFirst().orElse((RecipeBookTabButton)null);
      }

      if (this.f_100280_ == null) {
         this.f_100280_ = this.f_100279_.get(0);
      }

      this.f_100280_.m_94635_(true);
      this.m_100382_(false);
      this.m_100351_();
   }

   public boolean m_5755_(boolean p_100372_) {
      return false;
   }

   protected void m_5674_() {
      this.f_100270_.m_94624_(152, 41, 28, 18, f_100268_);
   }

   public void m_100373_() {
      this.f_100272_.f_91068_.m_90926_(false);
   }

   public int m_181401_(int p_181402_, int p_181403_) {
      int i;
      if (this.m_100385_() && !this.f_181400_) {
         i = 177 + (p_181402_ - p_181403_ - 200) / 2;
      } else {
         i = (p_181402_ - p_181403_) / 2;
      }

      return i;
   }

   public void m_100384_() {
      this.m_100369_(!this.m_100385_());
   }

   public boolean m_100385_() {
      return this.f_170041_;
   }

   private boolean m_170050_() {
      return this.f_100283_.m_12691_(this.f_100271_.m_5867_());
   }

   protected void m_100369_(boolean p_100370_) {
      if (p_100370_) {
         this.m_181404_();
      }

      this.f_170041_ = p_100370_;
      this.f_100283_.m_12693_(this.f_100271_.m_5867_(), p_100370_);
      if (!p_100370_) {
         this.f_100284_.m_100440_();
      }

      this.m_100388_();
   }

   public void m_6904_(@Nullable Slot p_100315_) {
      if (p_100315_ != null && p_100315_.f_40219_ < this.f_100271_.m_6653_()) {
         this.f_100269_.m_100140_();
         if (this.m_100385_()) {
            this.m_100389_();
         }
      }

   }

   private void m_100382_(boolean p_100383_) {
      List<RecipeCollection> list = this.f_100283_.m_90623_(this.f_100280_.m_100455_());
      list.forEach((p_100381_) -> {
         p_100381_.m_100501_(this.f_100285_, this.f_100271_.m_6635_(), this.f_100271_.m_6656_(), this.f_100283_);
      });
      List<RecipeCollection> list1 = Lists.newArrayList(list);
      list1.removeIf((p_100368_) -> {
         return !p_100368_.m_100498_();
      });
      list1.removeIf((p_100360_) -> {
         return !p_100360_.m_100515_();
      });
      String s = this.f_100281_.m_94155_();
      if (!s.isEmpty()) {
         ObjectSet<RecipeCollection> objectset = new ObjectLinkedOpenHashSet<>(this.f_100272_.m_91171_(SearchRegistry.f_119943_).m_6293_(s.toLowerCase(Locale.ROOT)));
         list1.removeIf((p_100334_) -> {
            return !objectset.contains(p_100334_);
         });
      }

      if (this.f_100283_.m_12689_(this.f_100271_)) {
         list1.removeIf((p_100331_) -> {
            return !p_100331_.m_100512_();
         });
      }

      this.f_100284_.m_100436_(list1, p_100383_);
   }

   private void m_100351_() {
      int i = (this.f_100277_ - 147) / 2 - this.f_100276_ - 30;
      int j = (this.f_100278_ - 166) / 2 + 3;
      int k = 27;
      int l = 0;

      for(RecipeBookTabButton recipebooktabbutton : this.f_100279_) {
         RecipeBookCategories recipebookcategories = recipebooktabbutton.m_100455_();
         if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
            if (recipebooktabbutton.m_100449_(this.f_100283_)) {
               recipebooktabbutton.m_94621_(i, j + 27 * l++);
               recipebooktabbutton.m_100451_(this.f_100272_);
            }
         } else {
            recipebooktabbutton.f_93624_ = true;
            recipebooktabbutton.m_94621_(i, j + 27 * l++);
         }
      }

   }

   public void m_100386_() {
      boolean flag = this.m_170050_();
      if (this.m_100385_() != flag) {
         this.m_100369_(flag);
      }

      if (this.m_100385_()) {
         if (this.f_100286_ != this.f_100272_.f_91074_.m_150109_().m_36072_()) {
            this.m_100389_();
            this.f_100286_ = this.f_100272_.f_91074_.m_150109_().m_36072_();
         }

         this.f_100281_.m_94120_();
      }
   }

   private void m_100389_() {
      this.f_100285_.m_36453_();
      this.f_100272_.f_91074_.m_150109_().m_36010_(this.f_100285_);
      this.f_100271_.m_5816_(this.f_100285_);
      this.m_100382_(false);
   }

   public void m_6305_(PoseStack p_100319_, int p_100320_, int p_100321_, float p_100322_) {
      if (this.m_100385_()) {
         p_100319_.m_85836_();
         p_100319_.m_85837_(0.0D, 0.0D, 100.0D);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_100268_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         int i = (this.f_100277_ - 147) / 2 - this.f_100276_;
         int j = (this.f_100278_ - 166) / 2;
         this.m_93228_(p_100319_, i, j, 1, 1, 147, 166);
         if (!this.f_100281_.m_93696_() && this.f_100281_.m_94155_().isEmpty()) {
            m_93243_(p_100319_, this.f_100272_.f_91062_, f_100273_, i + 25, j + 14, -1);
         } else {
            this.f_100281_.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
         }

         for(RecipeBookTabButton recipebooktabbutton : this.f_100279_) {
            recipebooktabbutton.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
         }

         this.f_100270_.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
         this.f_100284_.m_100421_(p_100319_, i, j, p_100320_, p_100321_, p_100322_);
         p_100319_.m_85849_();
      }
   }

   public void m_100361_(PoseStack p_100362_, int p_100363_, int p_100364_, int p_100365_, int p_100366_) {
      if (this.m_100385_()) {
         this.f_100284_.m_100417_(p_100362_, p_100365_, p_100366_);
         if (this.f_100270_.m_5702_()) {
            Component component = this.m_100390_();
            if (this.f_100272_.f_91080_ != null) {
               this.f_100272_.f_91080_.m_96602_(p_100362_, component, p_100365_, p_100366_);
            }
         }

         this.m_100374_(p_100362_, p_100363_, p_100364_, p_100365_, p_100366_);
      }
   }

   private Component m_100390_() {
      return this.f_100270_.m_94620_() ? this.m_5815_() : f_100275_;
   }

   protected Component m_5815_() {
      return f_100274_;
   }

   private void m_100374_(PoseStack p_100375_, int p_100376_, int p_100377_, int p_100378_, int p_100379_) {
      ItemStack itemstack = null;

      for(int i = 0; i < this.f_100269_.m_100158_(); ++i) {
         GhostRecipe.GhostIngredient ghostrecipe$ghostingredient = this.f_100269_.m_100141_(i);
         int j = ghostrecipe$ghostingredient.m_100169_() + p_100376_;
         int k = ghostrecipe$ghostingredient.m_100170_() + p_100377_;
         if (p_100378_ >= j && p_100379_ >= k && p_100378_ < j + 16 && p_100379_ < k + 16) {
            itemstack = ghostrecipe$ghostingredient.m_100171_();
         }
      }

      if (itemstack != null && this.f_100272_.f_91080_ != null) {
         this.f_100272_.f_91080_.renderComponentTooltip(p_100375_, this.f_100272_.f_91080_.m_96555_(itemstack), p_100378_, p_100379_, itemstack);
      }

   }

   public void m_6545_(PoseStack p_100323_, int p_100324_, int p_100325_, boolean p_100326_, float p_100327_) {
      this.f_100269_.m_100149_(p_100323_, this.f_100272_, p_100324_, p_100325_, p_100326_, p_100327_);
   }

   public boolean m_6375_(double p_100294_, double p_100295_, int p_100296_) {
      if (this.m_100385_() && !this.f_100272_.f_91074_.m_5833_()) {
         if (this.f_100284_.m_100409_(p_100294_, p_100295_, p_100296_, (this.f_100277_ - 147) / 2 - this.f_100276_, (this.f_100278_ - 166) / 2, 147, 166)) {
            Recipe<?> recipe = this.f_100284_.m_100408_();
            RecipeCollection recipecollection = this.f_100284_.m_100439_();
            if (recipe != null && recipecollection != null) {
               if (!recipecollection.m_100506_(recipe) && this.f_100269_.m_100159_() == recipe) {
                  return false;
               }

               this.f_100269_.m_100140_();
               this.f_100272_.f_91072_.m_105217_(this.f_100272_.f_91074_.f_36096_.f_38840_, recipe, Screen.m_96638_());
               if (!this.m_100393_()) {
                  this.m_100369_(false);
               }
            }

            return true;
         } else if (this.f_100281_.m_6375_(p_100294_, p_100295_, p_100296_)) {
            return true;
         } else if (this.f_100270_.m_6375_(p_100294_, p_100295_, p_100296_)) {
            boolean flag = this.m_100391_();
            this.f_100270_.m_94635_(flag);
            this.m_100388_();
            this.m_100382_(false);
            return true;
         } else {
            for(RecipeBookTabButton recipebooktabbutton : this.f_100279_) {
               if (recipebooktabbutton.m_6375_(p_100294_, p_100295_, p_100296_)) {
                  if (this.f_100280_ != recipebooktabbutton) {
                     if (this.f_100280_ != null) {
                        this.f_100280_.m_94635_(false);
                     }

                     this.f_100280_ = recipebooktabbutton;
                     this.f_100280_.m_94635_(true);
                     this.m_100382_(true);
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean m_100391_() {
      RecipeBookType recipebooktype = this.f_100271_.m_5867_();
      boolean flag = !this.f_100283_.m_12704_(recipebooktype);
      this.f_100283_.m_12706_(recipebooktype, flag);
      return flag;
   }

   public boolean m_100297_(double p_100298_, double p_100299_, int p_100300_, int p_100301_, int p_100302_, int p_100303_, int p_100304_) {
      if (!this.m_100385_()) {
         return true;
      } else {
         boolean flag = p_100298_ < (double)p_100300_ || p_100299_ < (double)p_100301_ || p_100298_ >= (double)(p_100300_ + p_100302_) || p_100299_ >= (double)(p_100301_ + p_100303_);
         boolean flag1 = (double)(p_100300_ - 147) < p_100298_ && p_100298_ < (double)p_100300_ && (double)p_100301_ < p_100299_ && p_100299_ < (double)(p_100301_ + p_100303_);
         return flag && !flag1 && !this.f_100280_.m_5702_();
      }
   }

   public boolean m_7933_(int p_100306_, int p_100307_, int p_100308_) {
      this.f_100287_ = false;
      if (this.m_100385_() && !this.f_100272_.f_91074_.m_5833_()) {
         if (p_100306_ == 256 && !this.m_100393_()) {
            this.m_100369_(false);
            return true;
         } else if (this.f_100281_.m_7933_(p_100306_, p_100307_, p_100308_)) {
            this.m_100392_();
            return true;
         } else if (this.f_100281_.m_93696_() && this.f_100281_.m_94213_() && p_100306_ != 256) {
            return true;
         } else if (this.f_100272_.f_91066_.f_92098_.m_90832_(p_100306_, p_100307_) && !this.f_100281_.m_93696_()) {
            this.f_100287_ = true;
            this.f_100281_.m_94178_(true);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_7920_(int p_100356_, int p_100357_, int p_100358_) {
      this.f_100287_ = false;
      return GuiEventListener.super.m_7920_(p_100356_, p_100357_, p_100358_);
   }

   public boolean m_5534_(char p_100291_, int p_100292_) {
      if (this.f_100287_) {
         return false;
      } else if (this.m_100385_() && !this.f_100272_.f_91074_.m_5833_()) {
         if (this.f_100281_.m_5534_(p_100291_, p_100292_)) {
            this.m_100392_();
            return true;
         } else {
            return GuiEventListener.super.m_5534_(p_100291_, p_100292_);
         }
      } else {
         return false;
      }
   }

   public boolean m_5953_(double p_100353_, double p_100354_) {
      return false;
   }

   private void m_100392_() {
      String s = this.f_100281_.m_94155_().toLowerCase(Locale.ROOT);
      this.m_100335_(s);
      if (!s.equals(this.f_100282_)) {
         this.m_100382_(false);
         this.f_100282_ = s;
      }

   }

   private void m_100335_(String p_100336_) {
      if ("excitedze".equals(p_100336_)) {
         LanguageManager languagemanager = this.f_100272_.m_91102_();
         LanguageInfo languageinfo = languagemanager.m_118976_("en_pt");
         if (languagemanager.m_118983_().compareTo(languageinfo) == 0) {
            return;
         }

         languagemanager.m_118974_(languageinfo);
         this.f_100272_.f_91066_.f_92075_ = languageinfo.getCode();
         this.f_100272_.m_91391_();
         this.f_100272_.f_91066_.m_92169_();
      }

   }

   private boolean m_100393_() {
      return this.f_100276_ == 86;
   }

   public void m_100387_() {
      this.m_100351_();
      if (this.m_100385_()) {
         this.m_100382_(false);
      }

   }

   public void m_7262_(List<Recipe<?>> p_100344_) {
      for(Recipe<?> recipe : p_100344_) {
         this.f_100272_.f_91074_.m_108675_(recipe);
      }

   }

   public void m_7173_(Recipe<?> p_100316_, List<Slot> p_100317_) {
      ItemStack itemstack = p_100316_.m_8043_();
      this.f_100269_.m_100147_(p_100316_);
      this.f_100269_.m_100143_(Ingredient.m_43927_(itemstack), (p_100317_.get(0)).f_40220_, (p_100317_.get(0)).f_40221_);
      this.m_135408_(this.f_100271_.m_6635_(), this.f_100271_.m_6656_(), this.f_100271_.m_6636_(), p_100316_, p_100316_.m_7527_().iterator(), 0);
   }

   public void m_5817_(Iterator<Ingredient> p_100338_, int p_100339_, int p_100340_, int p_100341_, int p_100342_) {
      Ingredient ingredient = p_100338_.next();
      if (!ingredient.m_43947_()) {
         Slot slot = this.f_100271_.f_38839_.get(p_100339_);
         this.f_100269_.m_100143_(ingredient, slot.f_40220_, slot.f_40221_);
      }

   }

   protected void m_100388_() {
      if (this.f_100272_.m_91403_() != null) {
         RecipeBookType recipebooktype = this.f_100271_.m_5867_();
         boolean flag = this.f_100283_.m_12684_().m_12734_(recipebooktype);
         boolean flag1 = this.f_100283_.m_12684_().m_12754_(recipebooktype);
         this.f_100272_.m_91403_().m_104955_(new ServerboundRecipeBookChangeSettingsPacket(recipebooktype, flag, flag1));
      }

   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return this.f_170041_ ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput p_170046_) {
      List<NarratableEntry> list = Lists.newArrayList();
      this.f_100284_.m_170053_((p_170049_) -> {
         if (p_170049_.m_142518_()) {
            list.add(p_170049_);
         }

      });
      list.add(this.f_100281_);
      list.add(this.f_100270_);
      list.addAll(this.f_100279_);
      Screen.NarratableSearchResult screen$narratablesearchresult = Screen.m_169400_(list, (NarratableEntry)null);
      if (screen$narratablesearchresult != null) {
         screen$narratablesearchresult.f_169420_.m_142291_(p_170046_.m_142047_());
      }

   }
}
