package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookViewScreen extends Screen {
   public static final int f_169687_ = 16;
   public static final int f_169688_ = 36;
   public static final int f_169689_ = 30;
   public static final BookViewScreen.BookAccess f_98251_ = new BookViewScreen.BookAccess() {
      public int m_5732_() {
         return 0;
      }

      public FormattedText m_7303_(int p_98306_) {
         return FormattedText.f_130760_;
      }
   };
   public static final ResourceLocation f_98252_ = new ResourceLocation("textures/gui/book.png");
   protected static final int f_169690_ = 114;
   protected static final int f_169691_ = 128;
   protected static final int f_169692_ = 192;
   protected static final int f_169693_ = 192;
   private BookViewScreen.BookAccess f_98253_;
   private int f_98254_;
   private List<FormattedCharSequence> f_98255_ = Collections.emptyList();
   private int f_98256_ = -1;
   private Component f_98257_ = TextComponent.f_131282_;
   private PageButton f_98258_;
   private PageButton f_98259_;
   private final boolean f_98260_;

   public BookViewScreen(BookViewScreen.BookAccess p_98264_) {
      this(p_98264_, true);
   }

   public BookViewScreen() {
      this(f_98251_, false);
   }

   private BookViewScreen(BookViewScreen.BookAccess p_98266_, boolean p_98267_) {
      super(NarratorChatListener.f_93310_);
      this.f_98253_ = p_98266_;
      this.f_98260_ = p_98267_;
   }

   public void m_98288_(BookViewScreen.BookAccess p_98289_) {
      this.f_98253_ = p_98289_;
      this.f_98254_ = Mth.m_14045_(this.f_98254_, 0, p_98289_.m_5732_());
      this.m_98302_();
      this.f_98256_ = -1;
   }

   public boolean m_98275_(int p_98276_) {
      int i = Mth.m_14045_(p_98276_, 0, this.f_98253_.m_5732_() - 1);
      if (i != this.f_98254_) {
         this.f_98254_ = i;
         this.m_98302_();
         this.f_98256_ = -1;
         return true;
      } else {
         return false;
      }
   }

   protected boolean m_7735_(int p_98295_) {
      return this.m_98275_(p_98295_);
   }

   protected void m_7856_() {
      this.m_7829_();
      this.m_98301_();
   }

   protected void m_7829_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 200, 20, CommonComponents.f_130655_, (p_98299_) -> {
         this.f_96541_.m_91152_((Screen)null);
      }));
   }

   protected void m_98301_() {
      int i = (this.f_96543_ - 192) / 2;
      int j = 2;
      this.f_98258_ = this.m_142416_(new PageButton(i + 116, 159, true, (p_98297_) -> {
         this.m_7815_();
      }, this.f_98260_));
      this.f_98259_ = this.m_142416_(new PageButton(i + 43, 159, false, (p_98287_) -> {
         this.m_7811_();
      }, this.f_98260_));
      this.m_98302_();
   }

   private int m_98300_() {
      return this.f_98253_.m_5732_();
   }

   protected void m_7811_() {
      if (this.f_98254_ > 0) {
         --this.f_98254_;
      }

      this.m_98302_();
   }

   protected void m_7815_() {
      if (this.f_98254_ < this.m_98300_() - 1) {
         ++this.f_98254_;
      }

      this.m_98302_();
   }

   private void m_98302_() {
      this.f_98258_.f_93624_ = this.f_98254_ < this.m_98300_() - 1;
      this.f_98259_.f_93624_ = this.f_98254_ > 0;
   }

   public boolean m_7933_(int p_98278_, int p_98279_, int p_98280_) {
      if (super.m_7933_(p_98278_, p_98279_, p_98280_)) {
         return true;
      } else {
         switch(p_98278_) {
         case 266:
            this.f_98259_.m_5691_();
            return true;
         case 267:
            this.f_98258_.m_5691_();
            return true;
         default:
            return false;
         }
      }
   }

   public void m_6305_(PoseStack p_98282_, int p_98283_, int p_98284_, float p_98285_) {
      this.m_7333_(p_98282_);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98252_);
      int i = (this.f_96543_ - 192) / 2;
      int j = 2;
      this.m_93228_(p_98282_, i, 2, 0, 0, 192, 192);
      if (this.f_98256_ != this.f_98254_) {
         FormattedText formattedtext = this.f_98253_.m_98310_(this.f_98254_);
         this.f_98255_ = this.f_96547_.m_92923_(formattedtext, 114);
         this.f_98257_ = new TranslatableComponent("book.pageIndicator", this.f_98254_ + 1, Math.max(this.m_98300_(), 1));
      }

      this.f_98256_ = this.f_98254_;
      int i1 = this.f_96547_.m_92852_(this.f_98257_);
      this.f_96547_.m_92889_(p_98282_, this.f_98257_, (float)(i - i1 + 192 - 44), 18.0F, 0);
      int k = Math.min(128 / 9, this.f_98255_.size());

      for(int l = 0; l < k; ++l) {
         FormattedCharSequence formattedcharsequence = this.f_98255_.get(l);
         this.f_96547_.m_92877_(p_98282_, formattedcharsequence, (float)(i + 36), (float)(32 + l * 9), 0);
      }

      Style style = this.m_98268_((double)p_98283_, (double)p_98284_);
      if (style != null) {
         this.m_96570_(p_98282_, style, p_98283_, p_98284_);
      }

      super.m_6305_(p_98282_, p_98283_, p_98284_, p_98285_);
   }

   public boolean m_6375_(double p_98272_, double p_98273_, int p_98274_) {
      if (p_98274_ == 0) {
         Style style = this.m_98268_(p_98272_, p_98273_);
         if (style != null && this.m_5561_(style)) {
            return true;
         }
      }

      return super.m_6375_(p_98272_, p_98273_, p_98274_);
   }

   public boolean m_5561_(Style p_98293_) {
      ClickEvent clickevent = p_98293_.m_131182_();
      if (clickevent == null) {
         return false;
      } else if (clickevent.m_130622_() == ClickEvent.Action.CHANGE_PAGE) {
         String s = clickevent.m_130623_();

         try {
            int i = Integer.parseInt(s) - 1;
            return this.m_7735_(i);
         } catch (Exception exception) {
            return false;
         }
      } else {
         boolean flag = super.m_5561_(p_98293_);
         if (flag && clickevent.m_130622_() == ClickEvent.Action.RUN_COMMAND) {
            this.m_141919_();
         }

         return flag;
      }
   }

   protected void m_141919_() {
      this.f_96541_.m_91152_((Screen)null);
   }

   @Nullable
   public Style m_98268_(double p_98269_, double p_98270_) {
      if (this.f_98255_.isEmpty()) {
         return null;
      } else {
         int i = Mth.m_14107_(p_98269_ - (double)((this.f_96543_ - 192) / 2) - 36.0D);
         int j = Mth.m_14107_(p_98270_ - 2.0D - 30.0D);
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / 9, this.f_98255_.size());
            if (i <= 114 && j < 9 * k + k) {
               int l = j / 9;
               if (l >= 0 && l < this.f_98255_.size()) {
                  FormattedCharSequence formattedcharsequence = this.f_98255_.get(l);
                  return this.f_96541_.f_91062_.m_92865_().m_92338_(formattedcharsequence, i);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   static List<String> m_169694_(CompoundTag p_169695_) {
      Builder<String> builder = ImmutableList.builder();
      m_169696_(p_169695_, builder::add);
      return builder.build();
   }

   public static void m_169696_(CompoundTag p_169697_, Consumer<String> p_169698_) {
      ListTag listtag = p_169697_.m_128437_("pages", 8).m_6426_();
      IntFunction<String> intfunction;
      if (Minecraft.m_91087_().m_167974_() && p_169697_.m_128425_("filtered_pages", 10)) {
         CompoundTag compoundtag = p_169697_.m_128469_("filtered_pages");
         intfunction = (p_169702_) -> {
            String s = String.valueOf(p_169702_);
            return compoundtag.m_128441_(s) ? compoundtag.m_128461_(s) : listtag.m_128778_(p_169702_);
         };
      } else {
         intfunction = listtag::m_128778_;
      }

      for(int i = 0; i < listtag.size(); ++i) {
         p_169698_.accept(intfunction.apply(i));
      }

   }

   @OnlyIn(Dist.CLIENT)
   public interface BookAccess {
      int m_5732_();

      FormattedText m_7303_(int p_98307_);

      default FormattedText m_98310_(int p_98311_) {
         return p_98311_ >= 0 && p_98311_ < this.m_5732_() ? this.m_7303_(p_98311_) : FormattedText.f_130760_;
      }

      static BookViewScreen.BookAccess m_98308_(ItemStack p_98309_) {
         if (p_98309_.m_150930_(Items.f_42615_)) {
            return new BookViewScreen.WrittenBookAccess(p_98309_);
         } else {
            return (BookViewScreen.BookAccess)(p_98309_.m_150930_(Items.f_42614_) ? new BookViewScreen.WritableBookAccess(p_98309_) : BookViewScreen.f_98251_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WritableBookAccess implements BookViewScreen.BookAccess {
      private final List<String> f_98312_;

      public WritableBookAccess(ItemStack p_98314_) {
         this.f_98312_ = m_98318_(p_98314_);
      }

      private static List<String> m_98318_(ItemStack p_98319_) {
         CompoundTag compoundtag = p_98319_.m_41783_();
         return (List<String>)(compoundtag != null ? BookViewScreen.m_169694_(compoundtag) : ImmutableList.of());
      }

      public int m_5732_() {
         return this.f_98312_.size();
      }

      public FormattedText m_7303_(int p_98317_) {
         return FormattedText.m_130775_(this.f_98312_.get(p_98317_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WrittenBookAccess implements BookViewScreen.BookAccess {
      private final List<String> f_98320_;

      public WrittenBookAccess(ItemStack p_98322_) {
         this.f_98320_ = m_98326_(p_98322_);
      }

      private static List<String> m_98326_(ItemStack p_98327_) {
         CompoundTag compoundtag = p_98327_.m_41783_();
         return (List<String>)(compoundtag != null && WrittenBookItem.m_43471_(compoundtag) ? BookViewScreen.m_169694_(compoundtag) : ImmutableList.of(Component.Serializer.m_130703_((new TranslatableComponent("book.invalid.tag")).m_130940_(ChatFormatting.DARK_RED))));
      }

      public int m_5732_() {
         return this.f_98320_.size();
      }

      public FormattedText m_7303_(int p_98325_) {
         String s = this.f_98320_.get(p_98325_);

         try {
            FormattedText formattedtext = Component.Serializer.m_130701_(s);
            if (formattedtext != null) {
               return formattedtext;
            }
         } catch (Exception exception) {
         }

         return FormattedText.m_130775_(s);
      }
   }
}