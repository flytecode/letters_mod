package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

@OnlyIn(Dist.CLIENT)
public class BookEditScreen extends Screen {
   private static final int f_169682_ = 114;
   private static final int f_169683_ = 128;
   private static final int f_169684_ = 250;
   private static final int f_169685_ = 192;
   private static final int f_169686_ = 192;
   private static final Component f_98060_ = new TranslatableComponent("book.editTitle");
   private static final Component f_98061_ = new TranslatableComponent("book.finalizeWarning");
   private static final FormattedCharSequence f_98062_ = FormattedCharSequence.m_13714_("_", Style.f_131099_.m_131140_(ChatFormatting.BLACK));
   private static final FormattedCharSequence f_98063_ = FormattedCharSequence.m_13714_("_", Style.f_131099_.m_131140_(ChatFormatting.GRAY));
   private final Player f_98064_;
   private final ItemStack f_98065_;
   private boolean f_98066_;
   private boolean f_98067_;
   private int f_98068_;
   private int f_98069_;
   private final List<String> f_98070_ = Lists.newArrayList();
   private String f_98071_ = "";
   private final TextFieldHelper f_98072_ = new TextFieldHelper(this::m_98191_, this::m_98158_, this::m_98180_, this::m_98147_, (p_98179_) -> {
      return p_98179_.length() < 1024 && this.f_96547_.m_92920_(p_98179_, 114) <= 128;
   });
   private final TextFieldHelper f_98073_ = new TextFieldHelper(() -> {
      return this.f_98071_;
   }, (p_98175_) -> {
      this.f_98071_ = p_98175_;
   }, this::m_98180_, this::m_98147_, (p_98170_) -> {
      return p_98170_.length() < 16;
   });
   private long f_98048_;
   private int f_98049_ = -1;
   private PageButton f_98050_;
   private PageButton f_98051_;
   private Button f_98052_;
   private Button f_98053_;
   private Button f_98054_;
   private Button f_98055_;
   private final InteractionHand f_98056_;
   @Nullable
   private BookEditScreen.DisplayCache f_98057_ = BookEditScreen.DisplayCache.f_98192_;
   private Component f_98058_ = TextComponent.f_131282_;
   private final Component f_98059_;

   public BookEditScreen(Player p_98076_, ItemStack p_98077_, InteractionHand p_98078_) {
      super(NarratorChatListener.f_93310_);
      this.f_98064_ = p_98076_;
      this.f_98065_ = p_98077_;
      this.f_98056_ = p_98078_;
      CompoundTag compoundtag = p_98077_.m_41783_();
      if (compoundtag != null) {
         BookViewScreen.m_169696_(compoundtag, this.f_98070_::add);
      }

      if (this.f_98070_.isEmpty()) {
         this.f_98070_.add("");
      }

      this.f_98059_ = (new TranslatableComponent("book.byAuthor", p_98076_.m_7755_())).m_130940_(ChatFormatting.DARK_GRAY);
   }

   private void m_98147_(String p_98148_) {
      if (this.f_96541_ != null) {
         TextFieldHelper.m_95155_(this.f_96541_, p_98148_);
      }

   }

   private String m_98180_() {
      return this.f_96541_ != null ? TextFieldHelper.m_95169_(this.f_96541_) : "";
   }

   private int m_98181_() {
      return this.f_98070_.size();
   }

   public void m_96624_() {
      super.m_96624_();
      ++this.f_98068_;
   }

   protected void m_7856_() {
      this.m_98080_();
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_98053_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 98, 20, new TranslatableComponent("book.signButton"), (p_98177_) -> {
         this.f_98067_ = true;
         this.m_98184_();
      }));
      this.f_98052_ = this.m_142416_(new Button(this.f_96543_ / 2 + 2, 196, 98, 20, CommonComponents.f_130655_, (p_98173_) -> {
         this.f_96541_.m_91152_((Screen)null);
         this.m_98160_(false);
      }));
      this.f_98054_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 98, 20, new TranslatableComponent("book.finalizeButton"), (p_98168_) -> {
         if (this.f_98067_) {
            this.m_98160_(true);
            this.f_96541_.m_91152_((Screen)null);
         }

      }));
      this.f_98055_ = this.m_142416_(new Button(this.f_96543_ / 2 + 2, 196, 98, 20, CommonComponents.f_130656_, (p_98157_) -> {
         if (this.f_98067_) {
            this.f_98067_ = false;
         }

         this.m_98184_();
      }));
      int i = (this.f_96543_ - 192) / 2;
      int j = 2;
      this.f_98050_ = this.m_142416_(new PageButton(i + 116, 159, true, (p_98144_) -> {
         this.m_98183_();
      }, true));
      this.f_98051_ = this.m_142416_(new PageButton(i + 43, 159, false, (p_98113_) -> {
         this.m_98182_();
      }, true));
      this.m_98184_();
   }

   private void m_98182_() {
      if (this.f_98069_ > 0) {
         --this.f_98069_;
      }

      this.m_98184_();
      this.m_98081_();
   }

   private void m_98183_() {
      if (this.f_98069_ < this.m_98181_() - 1) {
         ++this.f_98069_;
      } else {
         this.m_98186_();
         if (this.f_98069_ < this.m_98181_() - 1) {
            ++this.f_98069_;
         }
      }

      this.m_98184_();
      this.m_98081_();
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_98184_() {
      this.f_98051_.f_93624_ = !this.f_98067_ && this.f_98069_ > 0;
      this.f_98050_.f_93624_ = !this.f_98067_;
      this.f_98052_.f_93624_ = !this.f_98067_;
      this.f_98053_.f_93624_ = !this.f_98067_;
      this.f_98055_.f_93624_ = this.f_98067_;
      this.f_98054_.f_93624_ = this.f_98067_;
      this.f_98054_.f_93623_ = !this.f_98071_.trim().isEmpty();
   }

   private void m_98185_() {
      ListIterator<String> listiterator = this.f_98070_.listIterator(this.f_98070_.size());

      while(listiterator.hasPrevious() && listiterator.previous().isEmpty()) {
         listiterator.remove();
      }

   }

   private void m_98160_(boolean p_98161_) {
      if (this.f_98066_) {
         this.m_98185_();
         this.m_182574_(p_98161_);
         int i = this.f_98056_ == InteractionHand.MAIN_HAND ? this.f_98064_.m_150109_().f_35977_ : 40;
         this.f_96541_.m_91403_().m_104955_(new ServerboundEditBookPacket(i, this.f_98070_, p_98161_ ? Optional.of(this.f_98071_.trim()) : Optional.empty()));
      }
   }

   private void m_182574_(boolean p_182575_) {
      ListTag listtag = new ListTag();
      this.f_98070_.stream().map(StringTag::m_129297_).forEach(listtag::add);
      if (!this.f_98070_.isEmpty()) {
         this.f_98065_.m_41700_("pages", listtag);
      }

      if (p_182575_) {
         this.f_98065_.m_41700_("author", StringTag.m_129297_(this.f_98064_.m_36316_().getName()));
         this.f_98065_.m_41700_("title", StringTag.m_129297_(this.f_98071_.trim()));
      }

   }

   private void m_98186_() {
      if (this.m_98181_() < 100) {
         this.f_98070_.add("");
         this.f_98066_ = true;
      }
   }

   public boolean m_7933_(int p_98100_, int p_98101_, int p_98102_) {
      if (super.m_7933_(p_98100_, p_98101_, p_98102_)) {
         return true;
      } else if (this.f_98067_) {
         return this.m_98163_(p_98100_, p_98101_, p_98102_);
      } else {
         boolean flag = this.m_98152_(p_98100_, p_98101_, p_98102_);
         if (flag) {
            this.m_98080_();
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean m_5534_(char p_98085_, int p_98086_) {
      if (super.m_5534_(p_98085_, p_98086_)) {
         return true;
      } else if (this.f_98067_) {
         boolean flag = this.f_98073_.m_95143_(p_98085_);
         if (flag) {
            this.m_98184_();
            this.f_98066_ = true;
            return true;
         } else {
            return false;
         }
      } else if (SharedConstants.m_136188_(p_98085_)) {
         this.f_98072_.m_95158_(Character.toString(p_98085_));
         this.m_98080_();
         return true;
      } else {
         return false;
      }
   }

   private boolean m_98152_(int p_98153_, int p_98154_, int p_98155_) {
      if (Screen.m_96634_(p_98153_)) {
         this.f_98072_.m_95188_();
         return true;
      } else if (Screen.m_96632_(p_98153_)) {
         this.f_98072_.m_95178_();
         return true;
      } else if (Screen.m_96630_(p_98153_)) {
         this.f_98072_.m_95165_();
         return true;
      } else if (Screen.m_96628_(p_98153_)) {
         this.f_98072_.m_95142_();
         return true;
      } else {
         switch(p_98153_) {
         case 257:
         case 335:
            this.f_98072_.m_95158_("\n");
            return true;
         case 259:
            this.f_98072_.m_95189_(-1);
            return true;
         case 261:
            this.f_98072_.m_95189_(1);
            return true;
         case 262:
            this.f_98072_.m_95150_(1, Screen.m_96638_());
            return true;
         case 263:
            this.f_98072_.m_95150_(-1, Screen.m_96638_());
            return true;
         case 264:
            this.m_98188_();
            return true;
         case 265:
            this.m_98187_();
            return true;
         case 266:
            this.f_98051_.m_5691_();
            return true;
         case 267:
            this.f_98050_.m_5691_();
            return true;
         case 268:
            this.m_98189_();
            return true;
         case 269:
            this.m_98190_();
            return true;
         default:
            return false;
         }
      }
   }

   private void m_98187_() {
      this.m_98097_(-1);
   }

   private void m_98188_() {
      this.m_98097_(1);
   }

   private void m_98097_(int p_98098_) {
      int i = this.f_98072_.m_95194_();
      int j = this.m_98079_().m_98210_(i, p_98098_);
      this.f_98072_.m_95179_(j, Screen.m_96638_());
   }

   private void m_98189_() {
      int i = this.f_98072_.m_95194_();
      int j = this.m_98079_().m_98208_(i);
      this.f_98072_.m_95179_(j, Screen.m_96638_());
   }

   private void m_98190_() {
      BookEditScreen.DisplayCache bookeditscreen$displaycache = this.m_98079_();
      int i = this.f_98072_.m_95194_();
      int j = bookeditscreen$displaycache.m_98218_(i);
      this.f_98072_.m_95179_(j, Screen.m_96638_());
   }

   private boolean m_98163_(int p_98164_, int p_98165_, int p_98166_) {
      switch(p_98164_) {
      case 257:
      case 335:
         if (!this.f_98071_.isEmpty()) {
            this.m_98160_(true);
            this.f_96541_.m_91152_((Screen)null);
         }

         return true;
      case 259:
         this.f_98073_.m_95189_(-1);
         this.m_98184_();
         this.f_98066_ = true;
         return true;
      default:
         return false;
      }
   }

   private String m_98191_() {
      return this.f_98069_ >= 0 && this.f_98069_ < this.f_98070_.size() ? this.f_98070_.get(this.f_98069_) : "";
   }

   private void m_98158_(String p_98159_) {
      if (this.f_98069_ >= 0 && this.f_98069_ < this.f_98070_.size()) {
         this.f_98070_.set(this.f_98069_, p_98159_);
         this.f_98066_ = true;
         this.m_98080_();
      }

   }

   public void m_6305_(PoseStack p_98104_, int p_98105_, int p_98106_, float p_98107_) {
      this.m_7333_(p_98104_);
      this.m_7522_((GuiEventListener)null);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, BookViewScreen.f_98252_);
      int i = (this.f_96543_ - 192) / 2;
      int j = 2;
      this.m_93228_(p_98104_, i, 2, 0, 0, 192, 192);
      if (this.f_98067_) {
         boolean flag = this.f_98068_ / 6 % 2 == 0;
         FormattedCharSequence formattedcharsequence = FormattedCharSequence.m_13696_(FormattedCharSequence.m_13714_(this.f_98071_, Style.f_131099_), flag ? f_98062_ : f_98063_);
         int k = this.f_96547_.m_92852_(f_98060_);
         this.f_96547_.m_92889_(p_98104_, f_98060_, (float)(i + 36 + (114 - k) / 2), 34.0F, 0);
         int l = this.f_96547_.m_92724_(formattedcharsequence);
         this.f_96547_.m_92877_(p_98104_, formattedcharsequence, (float)(i + 36 + (114 - l) / 2), 50.0F, 0);
         int i1 = this.f_96547_.m_92852_(this.f_98059_);
         this.f_96547_.m_92889_(p_98104_, this.f_98059_, (float)(i + 36 + (114 - i1) / 2), 60.0F, 0);
         this.f_96547_.m_92857_(f_98061_, i + 36, 82, 114, 0);
      } else {
         int j1 = this.f_96547_.m_92852_(this.f_98058_);
         this.f_96547_.m_92889_(p_98104_, this.f_98058_, (float)(i - j1 + 192 - 44), 18.0F, 0);
         BookEditScreen.DisplayCache bookeditscreen$displaycache = this.m_98079_();

         for(BookEditScreen.LineInfo bookeditscreen$lineinfo : bookeditscreen$displaycache.f_98197_) {
            this.f_96547_.m_92889_(p_98104_, bookeditscreen$lineinfo.f_98228_, (float)bookeditscreen$lineinfo.f_98229_, (float)bookeditscreen$lineinfo.f_98230_, -16777216);
         }

         this.m_98138_(bookeditscreen$displaycache.f_98198_);
         this.m_98108_(p_98104_, bookeditscreen$displaycache.f_98194_, bookeditscreen$displaycache.f_98195_);
      }

      super.m_6305_(p_98104_, p_98105_, p_98106_, p_98107_);
   }

   private void m_98108_(PoseStack p_98109_, BookEditScreen.Pos2i p_98110_, boolean p_98111_) {
      if (this.f_98068_ / 6 % 2 == 0) {
         p_98110_ = this.m_98145_(p_98110_);
         if (!p_98111_) {
            GuiComponent.m_93172_(p_98109_, p_98110_.f_98246_, p_98110_.f_98247_ - 1, p_98110_.f_98246_ + 1, p_98110_.f_98247_ + 9, -16777216);
         } else {
            this.f_96547_.m_92883_(p_98109_, "_", (float)p_98110_.f_98246_, (float)p_98110_.f_98247_, 0);
         }
      }

   }

   private void m_98138_(Rect2i[] p_98139_) {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172808_);
      RenderSystem.m_157429_(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.m_69472_();
      RenderSystem.m_69479_();
      RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);

      for(Rect2i rect2i : p_98139_) {
         int i = rect2i.m_110085_();
         int j = rect2i.m_110086_();
         int k = i + rect2i.m_110090_();
         int l = j + rect2i.m_110091_();
         bufferbuilder.m_5483_((double)i, (double)l, 0.0D).m_5752_();
         bufferbuilder.m_5483_((double)k, (double)l, 0.0D).m_5752_();
         bufferbuilder.m_5483_((double)k, (double)j, 0.0D).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)j, 0.0D).m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69462_();
      RenderSystem.m_69493_();
   }

   private BookEditScreen.Pos2i m_98114_(BookEditScreen.Pos2i p_98115_) {
      return new BookEditScreen.Pos2i(p_98115_.f_98246_ - (this.f_96543_ - 192) / 2 - 36, p_98115_.f_98247_ - 32);
   }

   private BookEditScreen.Pos2i m_98145_(BookEditScreen.Pos2i p_98146_) {
      return new BookEditScreen.Pos2i(p_98146_.f_98246_ + (this.f_96543_ - 192) / 2 + 36, p_98146_.f_98247_ + 32);
   }

   public boolean m_6375_(double p_98088_, double p_98089_, int p_98090_) {
      if (super.m_6375_(p_98088_, p_98089_, p_98090_)) {
         return true;
      } else {
         if (p_98090_ == 0) {
            long i = Util.m_137550_();
            BookEditScreen.DisplayCache bookeditscreen$displaycache = this.m_98079_();
            int j = bookeditscreen$displaycache.m_98213_(this.f_96547_, this.m_98114_(new BookEditScreen.Pos2i((int)p_98088_, (int)p_98089_)));
            if (j >= 0) {
               if (j == this.f_98049_ && i - this.f_98048_ < 250L) {
                  if (!this.f_98072_.m_95198_()) {
                     this.m_98141_(j);
                  } else {
                     this.f_98072_.m_95188_();
                  }
               } else {
                  this.f_98072_.m_95179_(j, Screen.m_96638_());
               }

               this.m_98080_();
            }

            this.f_98049_ = j;
            this.f_98048_ = i;
         }

         return true;
      }
   }

   private void m_98141_(int p_98142_) {
      String s = this.m_98191_();
      this.f_98072_.m_95147_(StringSplitter.m_92355_(s, -1, p_98142_, false), StringSplitter.m_92355_(s, 1, p_98142_, false));
   }

   public boolean m_7979_(double p_98092_, double p_98093_, int p_98094_, double p_98095_, double p_98096_) {
      if (super.m_7979_(p_98092_, p_98093_, p_98094_, p_98095_, p_98096_)) {
         return true;
      } else {
         if (p_98094_ == 0) {
            BookEditScreen.DisplayCache bookeditscreen$displaycache = this.m_98079_();
            int i = bookeditscreen$displaycache.m_98213_(this.f_96547_, this.m_98114_(new BookEditScreen.Pos2i((int)p_98092_, (int)p_98093_)));
            this.f_98072_.m_95179_(i, true);
            this.m_98080_();
         }

         return true;
      }
   }

   private BookEditScreen.DisplayCache m_98079_() {
      if (this.f_98057_ == null) {
         this.f_98057_ = this.m_98082_();
         this.f_98058_ = new TranslatableComponent("book.pageIndicator", this.f_98069_ + 1, this.m_98181_());
      }

      return this.f_98057_;
   }

   private void m_98080_() {
      this.f_98057_ = null;
   }

   private void m_98081_() {
      this.f_98072_.m_95193_();
      this.m_98080_();
   }

   private BookEditScreen.DisplayCache m_98082_() {
      String s = this.m_98191_();
      if (s.isEmpty()) {
         return BookEditScreen.DisplayCache.f_98192_;
      } else {
         int i = this.f_98072_.m_95194_();
         int j = this.f_98072_.m_95197_();
         IntList intlist = new IntArrayList();
         List<BookEditScreen.LineInfo> list = Lists.newArrayList();
         MutableInt mutableint = new MutableInt();
         MutableBoolean mutableboolean = new MutableBoolean();
         StringSplitter stringsplitter = this.f_96547_.m_92865_();
         stringsplitter.m_92364_(s, 114, Style.f_131099_, true, (p_98132_, p_98133_, p_98134_) -> {
            int k3 = mutableint.getAndIncrement();
            String s2 = s.substring(p_98133_, p_98134_);
            mutableboolean.setValue(s2.endsWith("\n"));
            String s3 = StringUtils.stripEnd(s2, " \n");
            int l3 = k3 * 9;
            BookEditScreen.Pos2i bookeditscreen$pos2i1 = this.m_98145_(new BookEditScreen.Pos2i(0, l3));
            intlist.add(p_98133_);
            list.add(new BookEditScreen.LineInfo(p_98132_, s3, bookeditscreen$pos2i1.f_98246_, bookeditscreen$pos2i1.f_98247_));
         });
         int[] aint = intlist.toIntArray();
         boolean flag = i == s.length();
         BookEditScreen.Pos2i bookeditscreen$pos2i;
         if (flag && mutableboolean.isTrue()) {
            bookeditscreen$pos2i = new BookEditScreen.Pos2i(0, list.size() * 9);
         } else {
            int k = m_98149_(aint, i);
            int l = this.f_96547_.m_92895_(s.substring(aint[k], i));
            bookeditscreen$pos2i = new BookEditScreen.Pos2i(l, k * 9);
         }

         List<Rect2i> list1 = Lists.newArrayList();
         if (i != j) {
            int l2 = Math.min(i, j);
            int i1 = Math.max(i, j);
            int j1 = m_98149_(aint, l2);
            int k1 = m_98149_(aint, i1);
            if (j1 == k1) {
               int l1 = j1 * 9;
               int i2 = aint[j1];
               list1.add(this.m_98119_(s, stringsplitter, l2, i1, l1, i2));
            } else {
               int i3 = j1 + 1 > aint.length ? s.length() : aint[j1 + 1];
               list1.add(this.m_98119_(s, stringsplitter, l2, i3, j1 * 9, aint[j1]));

               for(int j3 = j1 + 1; j3 < k1; ++j3) {
                  int j2 = j3 * 9;
                  String s1 = s.substring(aint[j3], aint[j3 + 1]);
                  int k2 = (int)stringsplitter.m_92353_(s1);
                  list1.add(this.m_98116_(new BookEditScreen.Pos2i(0, j2), new BookEditScreen.Pos2i(k2, j2 + 9)));
               }

               list1.add(this.m_98119_(s, stringsplitter, aint[k1], i1, k1 * 9, aint[k1]));
            }
         }

         return new BookEditScreen.DisplayCache(s, bookeditscreen$pos2i, flag, aint, list.toArray(new BookEditScreen.LineInfo[0]), list1.toArray(new Rect2i[0]));
      }
   }

   static int m_98149_(int[] p_98150_, int p_98151_) {
      int i = Arrays.binarySearch(p_98150_, p_98151_);
      return i < 0 ? -(i + 2) : i;
   }

   private Rect2i m_98119_(String p_98120_, StringSplitter p_98121_, int p_98122_, int p_98123_, int p_98124_, int p_98125_) {
      String s = p_98120_.substring(p_98125_, p_98122_);
      String s1 = p_98120_.substring(p_98125_, p_98123_);
      BookEditScreen.Pos2i bookeditscreen$pos2i = new BookEditScreen.Pos2i((int)p_98121_.m_92353_(s), p_98124_);
      BookEditScreen.Pos2i bookeditscreen$pos2i1 = new BookEditScreen.Pos2i((int)p_98121_.m_92353_(s1), p_98124_ + 9);
      return this.m_98116_(bookeditscreen$pos2i, bookeditscreen$pos2i1);
   }

   private Rect2i m_98116_(BookEditScreen.Pos2i p_98117_, BookEditScreen.Pos2i p_98118_) {
      BookEditScreen.Pos2i bookeditscreen$pos2i = this.m_98145_(p_98117_);
      BookEditScreen.Pos2i bookeditscreen$pos2i1 = this.m_98145_(p_98118_);
      int i = Math.min(bookeditscreen$pos2i.f_98246_, bookeditscreen$pos2i1.f_98246_);
      int j = Math.max(bookeditscreen$pos2i.f_98246_, bookeditscreen$pos2i1.f_98246_);
      int k = Math.min(bookeditscreen$pos2i.f_98247_, bookeditscreen$pos2i1.f_98247_);
      int l = Math.max(bookeditscreen$pos2i.f_98247_, bookeditscreen$pos2i1.f_98247_);
      return new Rect2i(i, k, j - i, l - k);
   }

   @OnlyIn(Dist.CLIENT)
   static class DisplayCache {
      static final BookEditScreen.DisplayCache f_98192_ = new BookEditScreen.DisplayCache("", new BookEditScreen.Pos2i(0, 0), true, new int[]{0}, new BookEditScreen.LineInfo[]{new BookEditScreen.LineInfo(Style.f_131099_, "", 0, 0)}, new Rect2i[0]);
      private final String f_98193_;
      final BookEditScreen.Pos2i f_98194_;
      final boolean f_98195_;
      private final int[] f_98196_;
      final BookEditScreen.LineInfo[] f_98197_;
      final Rect2i[] f_98198_;

      public DisplayCache(String p_98201_, BookEditScreen.Pos2i p_98202_, boolean p_98203_, int[] p_98204_, BookEditScreen.LineInfo[] p_98205_, Rect2i[] p_98206_) {
         this.f_98193_ = p_98201_;
         this.f_98194_ = p_98202_;
         this.f_98195_ = p_98203_;
         this.f_98196_ = p_98204_;
         this.f_98197_ = p_98205_;
         this.f_98198_ = p_98206_;
      }

      public int m_98213_(Font p_98214_, BookEditScreen.Pos2i p_98215_) {
         int i = p_98215_.f_98247_ / 9;
         if (i < 0) {
            return 0;
         } else if (i >= this.f_98197_.length) {
            return this.f_98193_.length();
         } else {
            BookEditScreen.LineInfo bookeditscreen$lineinfo = this.f_98197_[i];
            return this.f_98196_[i] + p_98214_.m_92865_().m_92360_(bookeditscreen$lineinfo.f_98227_, p_98215_.f_98246_, bookeditscreen$lineinfo.f_98226_);
         }
      }

      public int m_98210_(int p_98211_, int p_98212_) {
         int i = BookEditScreen.m_98149_(this.f_98196_, p_98211_);
         int j = i + p_98212_;
         int k;
         if (0 <= j && j < this.f_98196_.length) {
            int l = p_98211_ - this.f_98196_[i];
            int i1 = this.f_98197_[j].f_98227_.length();
            k = this.f_98196_[j] + Math.min(l, i1);
         } else {
            k = p_98211_;
         }

         return k;
      }

      public int m_98208_(int p_98209_) {
         int i = BookEditScreen.m_98149_(this.f_98196_, p_98209_);
         return this.f_98196_[i];
      }

      public int m_98218_(int p_98219_) {
         int i = BookEditScreen.m_98149_(this.f_98196_, p_98219_);
         return this.f_98196_[i] + this.f_98197_[i].f_98227_.length();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class LineInfo {
      final Style f_98226_;
      final String f_98227_;
      final Component f_98228_;
      final int f_98229_;
      final int f_98230_;

      public LineInfo(Style p_98232_, String p_98233_, int p_98234_, int p_98235_) {
         this.f_98226_ = p_98232_;
         this.f_98227_ = p_98233_;
         this.f_98229_ = p_98234_;
         this.f_98230_ = p_98235_;
         this.f_98228_ = (new TextComponent(p_98233_)).m_6270_(p_98232_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Pos2i {
      public final int f_98246_;
      public final int f_98247_;

      Pos2i(int p_98249_, int p_98250_) {
         this.f_98246_ = p_98249_;
         this.f_98247_ = p_98250_;
      }
   }
}