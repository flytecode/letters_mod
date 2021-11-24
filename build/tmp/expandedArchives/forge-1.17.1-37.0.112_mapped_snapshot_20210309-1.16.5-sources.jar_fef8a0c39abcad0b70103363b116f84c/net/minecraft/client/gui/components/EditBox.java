package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditBox extends AbstractWidget implements Widget, GuiEventListener {
   public static final int f_168999_ = -1;
   public static final int f_169000_ = 1;
   private static final int f_169002_ = 1;
   private static final int f_169003_ = -3092272;
   private static final String f_169004_ = "_";
   public static final int f_169001_ = 14737632;
   private static final int f_169005_ = -1;
   private static final int f_169006_ = -6250336;
   private static final int f_169007_ = -16777216;
   private final Font f_94092_;
   private String f_94093_ = "";
   private int f_94094_ = 32;
   private int f_94095_;
   private boolean f_94096_ = true;
   private boolean f_94097_ = true;
   private boolean f_94098_ = true;
   private boolean f_94099_;
   private int f_94100_;
   private int f_94101_;
   private int f_94102_;
   private int f_94103_ = 14737632;
   private int f_94104_ = 7368816;
   @Nullable
   private String f_94088_;
   @Nullable
   private Consumer<String> f_94089_;
   private Predicate<String> f_94090_ = Objects::nonNull;
   private BiFunction<String, Integer, FormattedCharSequence> f_94091_ = (p_94147_, p_94148_) -> {
      return FormattedCharSequence.m_13714_(p_94147_, Style.f_131099_);
   };

   public EditBox(Font p_94114_, int p_94115_, int p_94116_, int p_94117_, int p_94118_, Component p_94119_) {
      this(p_94114_, p_94115_, p_94116_, p_94117_, p_94118_, (EditBox)null, p_94119_);
   }

   public EditBox(Font p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, @Nullable EditBox p_94111_, Component p_94112_) {
      super(p_94107_, p_94108_, p_94109_, p_94110_, p_94112_);
      this.f_94092_ = p_94106_;
      if (p_94111_ != null) {
         this.m_94144_(p_94111_.m_94155_());
      }

   }

   public void m_94151_(Consumer<String> p_94152_) {
      this.f_94089_ = p_94152_;
   }

   public void m_94149_(BiFunction<String, Integer, FormattedCharSequence> p_94150_) {
      this.f_94091_ = p_94150_;
   }

   public void m_94120_() {
      ++this.f_94095_;
   }

   protected MutableComponent m_5646_() {
      Component component = this.m_6035_();
      return new TranslatableComponent("gui.narrate.editBox", component, this.f_94093_);
   }

   public void m_94144_(String p_94145_) {
      if (this.f_94090_.test(p_94145_)) {
         if (p_94145_.length() > this.f_94094_) {
            this.f_94093_ = p_94145_.substring(0, this.f_94094_);
         } else {
            this.f_94093_ = p_94145_;
         }

         this.m_94201_();
         this.m_94208_(this.f_94101_);
         this.m_94174_(p_94145_);
      }
   }

   public String m_94155_() {
      return this.f_94093_;
   }

   public String m_94173_() {
      int i = Math.min(this.f_94101_, this.f_94102_);
      int j = Math.max(this.f_94101_, this.f_94102_);
      return this.f_94093_.substring(i, j);
   }

   public void m_94153_(Predicate<String> p_94154_) {
      this.f_94090_ = p_94154_;
   }

   public void m_94164_(String p_94165_) {
      int i = Math.min(this.f_94101_, this.f_94102_);
      int j = Math.max(this.f_94101_, this.f_94102_);
      int k = this.f_94094_ - this.f_94093_.length() - (i - j);
      String s = SharedConstants.m_136190_(p_94165_);
      int l = s.length();
      if (k < l) {
         s = s.substring(0, k);
         l = k;
      }

      String s1 = (new StringBuilder(this.f_94093_)).replace(i, j, s).toString();
      if (this.f_94090_.test(s1)) {
         this.f_94093_ = s1;
         this.m_94196_(i + l);
         this.m_94208_(this.f_94101_);
         this.m_94174_(this.f_94093_);
      }
   }

   private void m_94174_(String p_94175_) {
      if (this.f_94089_ != null) {
         this.f_94089_.accept(p_94175_);
      }

   }

   private void m_94217_(int p_94218_) {
      if (Screen.m_96637_()) {
         this.m_94176_(p_94218_);
      } else {
         this.m_94180_(p_94218_);
      }

   }

   public void m_94176_(int p_94177_) {
      if (!this.f_94093_.isEmpty()) {
         if (this.f_94102_ != this.f_94101_) {
            this.m_94164_("");
         } else {
            this.m_94180_(this.m_94184_(p_94177_) - this.f_94101_);
         }
      }
   }

   public void m_94180_(int p_94181_) {
      if (!this.f_94093_.isEmpty()) {
         if (this.f_94102_ != this.f_94101_) {
            this.m_94164_("");
         } else {
            int i = this.m_94220_(p_94181_);
            int j = Math.min(i, this.f_94101_);
            int k = Math.max(i, this.f_94101_);
            if (j != k) {
               String s = (new StringBuilder(this.f_94093_)).delete(j, k).toString();
               if (this.f_94090_.test(s)) {
                  this.f_94093_ = s;
                  this.m_94192_(j);
               }
            }
         }
      }
   }

   public int m_94184_(int p_94185_) {
      return this.m_94128_(p_94185_, this.m_94207_());
   }

   private int m_94128_(int p_94129_, int p_94130_) {
      return this.m_94140_(p_94129_, p_94130_, true);
   }

   private int m_94140_(int p_94141_, int p_94142_, boolean p_94143_) {
      int i = p_94142_;
      boolean flag = p_94141_ < 0;
      int j = Math.abs(p_94141_);

      for(int k = 0; k < j; ++k) {
         if (!flag) {
            int l = this.f_94093_.length();
            i = this.f_94093_.indexOf(32, i);
            if (i == -1) {
               i = l;
            } else {
               while(p_94143_ && i < l && this.f_94093_.charAt(i) == ' ') {
                  ++i;
               }
            }
         } else {
            while(p_94143_ && i > 0 && this.f_94093_.charAt(i - 1) == ' ') {
               --i;
            }

            while(i > 0 && this.f_94093_.charAt(i - 1) != ' ') {
               --i;
            }
         }
      }

      return i;
   }

   public void m_94188_(int p_94189_) {
      this.m_94192_(this.m_94220_(p_94189_));
   }

   private int m_94220_(int p_94221_) {
      return Util.m_137479_(this.f_94093_, this.f_94101_, p_94221_);
   }

   public void m_94192_(int p_94193_) {
      this.m_94196_(p_94193_);
      if (!this.f_94099_) {
         this.m_94208_(this.f_94101_);
      }

      this.m_94174_(this.f_94093_);
   }

   public void m_94196_(int p_94197_) {
      this.f_94101_ = Mth.m_14045_(p_94197_, 0, this.f_94093_.length());
   }

   public void m_94198_() {
      this.m_94192_(0);
   }

   public void m_94201_() {
      this.m_94192_(this.f_94093_.length());
   }

   public boolean m_7933_(int p_94132_, int p_94133_, int p_94134_) {
      if (!this.m_94204_()) {
         return false;
      } else {
         this.f_94099_ = Screen.m_96638_();
         if (Screen.m_96634_(p_94132_)) {
            this.m_94201_();
            this.m_94208_(0);
            return true;
         } else if (Screen.m_96632_(p_94132_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_94173_());
            return true;
         } else if (Screen.m_96630_(p_94132_)) {
            if (this.f_94098_) {
               this.m_94164_(Minecraft.m_91087_().f_91068_.m_90876_());
            }

            return true;
         } else if (Screen.m_96628_(p_94132_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_94173_());
            if (this.f_94098_) {
               this.m_94164_("");
            }

            return true;
         } else {
            switch(p_94132_) {
            case 259:
               if (this.f_94098_) {
                  this.f_94099_ = false;
                  this.m_94217_(-1);
                  this.f_94099_ = Screen.m_96638_();
               }

               return true;
            case 260:
            case 264:
            case 265:
            case 266:
            case 267:
            default:
               return false;
            case 261:
               if (this.f_94098_) {
                  this.f_94099_ = false;
                  this.m_94217_(1);
                  this.f_94099_ = Screen.m_96638_();
               }

               return true;
            case 262:
               if (Screen.m_96637_()) {
                  this.m_94192_(this.m_94184_(1));
               } else {
                  this.m_94188_(1);
               }

               return true;
            case 263:
               if (Screen.m_96637_()) {
                  this.m_94192_(this.m_94184_(-1));
               } else {
                  this.m_94188_(-1);
               }

               return true;
            case 268:
               this.m_94198_();
               return true;
            case 269:
               this.m_94201_();
               return true;
            }
         }
      }
   }

   public boolean m_94204_() {
      return this.m_94213_() && this.m_93696_() && this.m_94222_();
   }

   public boolean m_5534_(char p_94122_, int p_94123_) {
      if (!this.m_94204_()) {
         return false;
      } else if (SharedConstants.m_136188_(p_94122_)) {
         if (this.f_94098_) {
            this.m_94164_(Character.toString(p_94122_));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_6375_(double p_94125_, double p_94126_, int p_94127_) {
      if (!this.m_94213_()) {
         return false;
      } else {
         boolean flag = p_94125_ >= (double)this.f_93620_ && p_94125_ < (double)(this.f_93620_ + this.f_93618_) && p_94126_ >= (double)this.f_93621_ && p_94126_ < (double)(this.f_93621_ + this.f_93619_);
         if (this.f_94097_) {
            this.m_94178_(flag);
         }

         if (this.m_93696_() && flag && p_94127_ == 0) {
            int i = Mth.m_14107_(p_94125_) - this.f_93620_;
            if (this.f_94096_) {
               i -= 4;
            }

            String s = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
            this.m_94192_(this.f_94092_.m_92834_(s, i).length() + this.f_94100_);
            return true;
         } else {
            return false;
         }
      }
   }

   public void m_94178_(boolean p_94179_) {
      this.m_93692_(p_94179_);
   }

   public void m_6303_(PoseStack p_94160_, int p_94161_, int p_94162_, float p_94163_) {
      if (this.m_94213_()) {
         if (this.m_94219_()) {
            int i = this.m_93696_() ? -1 : -6250336;
            m_93172_(p_94160_, this.f_93620_ - 1, this.f_93621_ - 1, this.f_93620_ + this.f_93618_ + 1, this.f_93621_ + this.f_93619_ + 1, i);
            m_93172_(p_94160_, this.f_93620_, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, -16777216);
         }

         int i2 = this.f_94098_ ? this.f_94103_ : this.f_94104_;
         int j = this.f_94101_ - this.f_94100_;
         int k = this.f_94102_ - this.f_94100_;
         String s = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), this.m_94210_());
         boolean flag = j >= 0 && j <= s.length();
         boolean flag1 = this.m_93696_() && this.f_94095_ / 6 % 2 == 0 && flag;
         int l = this.f_94096_ ? this.f_93620_ + 4 : this.f_93620_;
         int i1 = this.f_94096_ ? this.f_93621_ + (this.f_93619_ - 8) / 2 : this.f_93621_;
         int j1 = l;
         if (k > s.length()) {
            k = s.length();
         }

         if (!s.isEmpty()) {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = this.f_94092_.m_92744_(p_94160_, this.f_94091_.apply(s1, this.f_94100_), (float)l, (float)i1, i2);
         }

         boolean flag2 = this.f_94101_ < this.f_94093_.length() || this.f_94093_.length() >= this.m_94216_();
         int k1 = j1;
         if (!flag) {
            k1 = j > 0 ? l + this.f_93618_ : l;
         } else if (flag2) {
            k1 = j1 - 1;
            --j1;
         }

         if (!s.isEmpty() && flag && j < s.length()) {
            this.f_94092_.m_92744_(p_94160_, this.f_94091_.apply(s.substring(j), this.f_94101_), (float)j1, (float)i1, i2);
         }

         if (!flag2 && this.f_94088_ != null) {
            this.f_94092_.m_92750_(p_94160_, this.f_94088_, (float)(k1 - 1), (float)i1, -8355712);
         }

         if (flag1) {
            if (flag2) {
               GuiComponent.m_93172_(p_94160_, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
            } else {
               this.f_94092_.m_92750_(p_94160_, "_", (float)k1, (float)i1, i2);
            }
         }

         if (k != j) {
            int l1 = l + this.f_94092_.m_92895_(s.substring(0, k));
            this.m_94135_(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
         }

      }
   }

   private void m_94135_(int p_94136_, int p_94137_, int p_94138_, int p_94139_) {
      if (p_94136_ < p_94138_) {
         int i = p_94136_;
         p_94136_ = p_94138_;
         p_94138_ = i;
      }

      if (p_94137_ < p_94139_) {
         int j = p_94137_;
         p_94137_ = p_94139_;
         p_94139_ = j;
      }

      if (p_94138_ > this.f_93620_ + this.f_93618_) {
         p_94138_ = this.f_93620_ + this.f_93618_;
      }

      if (p_94136_ > this.f_93620_ + this.f_93618_) {
         p_94136_ = this.f_93620_ + this.f_93618_;
      }

      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172808_);
      RenderSystem.m_157429_(0.0F, 0.0F, 1.0F, 1.0F);
      RenderSystem.m_69472_();
      RenderSystem.m_69479_();
      RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
      bufferbuilder.m_5483_((double)p_94136_, (double)p_94139_, 0.0D).m_5752_();
      bufferbuilder.m_5483_((double)p_94138_, (double)p_94139_, 0.0D).m_5752_();
      bufferbuilder.m_5483_((double)p_94138_, (double)p_94137_, 0.0D).m_5752_();
      bufferbuilder.m_5483_((double)p_94136_, (double)p_94137_, 0.0D).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69462_();
      RenderSystem.m_69493_();
   }

   public void m_94199_(int p_94200_) {
      this.f_94094_ = p_94200_;
      if (this.f_94093_.length() > p_94200_) {
         this.f_94093_ = this.f_94093_.substring(0, p_94200_);
         this.m_94174_(this.f_94093_);
      }

   }

   private int m_94216_() {
      return this.f_94094_;
   }

   public int m_94207_() {
      return this.f_94101_;
   }

   private boolean m_94219_() {
      return this.f_94096_;
   }

   public void m_94182_(boolean p_94183_) {
      this.f_94096_ = p_94183_;
   }

   public void m_94202_(int p_94203_) {
      this.f_94103_ = p_94203_;
   }

   public void m_94205_(int p_94206_) {
      this.f_94104_ = p_94206_;
   }

   public boolean m_5755_(boolean p_94172_) {
      return this.f_93624_ && this.f_94098_ ? super.m_5755_(p_94172_) : false;
   }

   public boolean m_5953_(double p_94157_, double p_94158_) {
      return this.f_93624_ && p_94157_ >= (double)this.f_93620_ && p_94157_ < (double)(this.f_93620_ + this.f_93618_) && p_94158_ >= (double)this.f_93621_ && p_94158_ < (double)(this.f_93621_ + this.f_93619_);
   }

   protected void m_7207_(boolean p_94170_) {
      if (p_94170_) {
         this.f_94095_ = 0;
      }

   }

   private boolean m_94222_() {
      return this.f_94098_;
   }

   public void m_94186_(boolean p_94187_) {
      this.f_94098_ = p_94187_;
   }

   public int m_94210_() {
      return this.m_94219_() ? this.f_93618_ - 8 : this.f_93618_;
   }

   public void m_94208_(int p_94209_) {
      int i = this.f_94093_.length();
      this.f_94102_ = Mth.m_14045_(p_94209_, 0, i);
      if (this.f_94092_ != null) {
         if (this.f_94100_ > i) {
            this.f_94100_ = i;
         }

         int j = this.m_94210_();
         String s = this.f_94092_.m_92834_(this.f_94093_.substring(this.f_94100_), j);
         int k = s.length() + this.f_94100_;
         if (this.f_94102_ == this.f_94100_) {
            this.f_94100_ -= this.f_94092_.m_92837_(this.f_94093_, j, true).length();
         }

         if (this.f_94102_ > k) {
            this.f_94100_ += this.f_94102_ - k;
         } else if (this.f_94102_ <= this.f_94100_) {
            this.f_94100_ -= this.f_94100_ - this.f_94102_;
         }

         this.f_94100_ = Mth.m_14045_(this.f_94100_, 0, i);
      }

   }

   public void m_94190_(boolean p_94191_) {
      this.f_94097_ = p_94191_;
   }

   public boolean m_94213_() {
      return this.f_93624_;
   }

   public void m_94194_(boolean p_94195_) {
      this.f_93624_ = p_94195_;
   }

   public void m_94167_(@Nullable String p_94168_) {
      this.f_94088_ = p_94168_;
   }

   public int m_94211_(int p_94212_) {
      return p_94212_ > this.f_94093_.length() ? this.f_93620_ : this.f_93620_ + this.f_94092_.m_92895_(this.f_94093_.substring(0, p_94212_));
   }

   public void m_94214_(int p_94215_) {
      this.f_93620_ = p_94215_;
   }

   public void m_142291_(NarrationElementOutput p_169009_) {
      p_169009_.m_169146_(NarratedElementType.TITLE, new TranslatableComponent("narration.edit_box", this.m_94155_()));
   }
}