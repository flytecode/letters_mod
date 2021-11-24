package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancementWidget extends GuiComponent {
   private static final ResourceLocation f_97239_ = new ResourceLocation("textures/gui/advancements/widgets.png");
   private static final int f_169542_ = 26;
   private static final int f_169543_ = 0;
   private static final int f_169544_ = 200;
   private static final int f_169545_ = 26;
   private static final int f_169546_ = 8;
   private static final int f_169547_ = 5;
   private static final int f_169548_ = 26;
   private static final int f_169549_ = 3;
   private static final int f_169550_ = 5;
   private static final int f_169551_ = 32;
   private static final int f_169552_ = 9;
   private static final int f_169553_ = 163;
   private static final int[] f_97240_ = new int[]{0, 10, -10, 25, -25};
   private final AdvancementTab f_97241_;
   private final Advancement f_97242_;
   private final DisplayInfo f_97243_;
   private final FormattedCharSequence f_97244_;
   private final int f_97245_;
   private final List<FormattedCharSequence> f_97246_;
   private final Minecraft f_97247_;
   private AdvancementWidget f_97248_;
   private final List<AdvancementWidget> f_97249_ = Lists.newArrayList();
   private AdvancementProgress f_97250_;
   private final int f_97251_;
   private final int f_97252_;

   public AdvancementWidget(AdvancementTab p_97255_, Minecraft p_97256_, Advancement p_97257_, DisplayInfo p_97258_) {
      this.f_97241_ = p_97255_;
      this.f_97242_ = p_97257_;
      this.f_97243_ = p_97258_;
      this.f_97247_ = p_97256_;
      this.f_97244_ = Language.m_128107_().m_5536_(p_97256_.f_91062_.m_92854_(p_97258_.m_14977_(), 163));
      this.f_97251_ = Mth.m_14143_(p_97258_.m_14993_() * 28.0F);
      this.f_97252_ = Mth.m_14143_(p_97258_.m_14994_() * 27.0F);
      int i = p_97257_.m_138326_();
      int j = String.valueOf(i).length();
      int k = i > 1 ? p_97256_.f_91062_.m_92895_("  ") + p_97256_.f_91062_.m_92895_("0") * j * 2 + p_97256_.f_91062_.m_92895_("/") : 0;
      int l = 29 + p_97256_.f_91062_.m_92724_(this.f_97244_) + k;
      this.f_97246_ = Language.m_128107_().m_128112_(this.m_97308_(ComponentUtils.m_130750_(p_97258_.m_14985_().m_6881_(), Style.f_131099_.m_131140_(p_97258_.m_14992_().m_15552_())), l));

      for(FormattedCharSequence formattedcharsequence : this.f_97246_) {
         l = Math.max(l, p_97256_.f_91062_.m_92724_(formattedcharsequence));
      }

      this.f_97245_ = l + 3 + 5;
   }

   private static float m_97303_(StringSplitter p_97304_, List<FormattedText> p_97305_) {
      return (float)p_97305_.stream().mapToDouble(p_97304_::m_92384_).max().orElse(0.0D);
   }

   private List<FormattedText> m_97308_(Component p_97309_, int p_97310_) {
      StringSplitter stringsplitter = this.f_97247_.f_91062_.m_92865_();
      List<FormattedText> list = null;
      float f = Float.MAX_VALUE;

      for(int i : f_97240_) {
         List<FormattedText> list1 = stringsplitter.m_92414_(p_97309_, p_97310_ - i, Style.f_131099_);
         float f1 = Math.abs(m_97303_(stringsplitter, list1) - (float)p_97310_);
         if (f1 <= 10.0F) {
            return list1;
         }

         if (f1 < f) {
            f = f1;
            list = list1;
         }
      }

      return list;
   }

   @Nullable
   private AdvancementWidget m_97311_(Advancement p_97312_) {
      do {
         p_97312_ = p_97312_.m_138319_();
      } while(p_97312_ != null && p_97312_.m_138320_() == null);

      return p_97312_ != null && p_97312_.m_138320_() != null ? this.f_97241_.m_97180_(p_97312_) : null;
   }

   public void m_97298_(PoseStack p_97299_, int p_97300_, int p_97301_, boolean p_97302_) {
      if (this.f_97248_ != null) {
         int i = p_97300_ + this.f_97248_.f_97251_ + 13;
         int j = p_97300_ + this.f_97248_.f_97251_ + 26 + 4;
         int k = p_97301_ + this.f_97248_.f_97252_ + 13;
         int l = p_97300_ + this.f_97251_ + 13;
         int i1 = p_97301_ + this.f_97252_ + 13;
         int j1 = p_97302_ ? -16777216 : -1;
         if (p_97302_) {
            this.m_93154_(p_97299_, j, i, k - 1, j1);
            this.m_93154_(p_97299_, j + 1, i, k, j1);
            this.m_93154_(p_97299_, j, i, k + 1, j1);
            this.m_93154_(p_97299_, l, j - 1, i1 - 1, j1);
            this.m_93154_(p_97299_, l, j - 1, i1, j1);
            this.m_93154_(p_97299_, l, j - 1, i1 + 1, j1);
            this.m_93222_(p_97299_, j - 1, i1, k, j1);
            this.m_93222_(p_97299_, j + 1, i1, k, j1);
         } else {
            this.m_93154_(p_97299_, j, i, k, j1);
            this.m_93154_(p_97299_, l, j, i1, j1);
            this.m_93222_(p_97299_, j, i1, k, j1);
         }
      }

      for(AdvancementWidget advancementwidget : this.f_97249_) {
         advancementwidget.m_97298_(p_97299_, p_97300_, p_97301_, p_97302_);
      }

   }

   public void m_97266_(PoseStack p_97267_, int p_97268_, int p_97269_) {
      if (!this.f_97243_.m_14997_() || this.f_97250_ != null && this.f_97250_.m_8193_()) {
         float f = this.f_97250_ == null ? 0.0F : this.f_97250_.m_8213_();
         AdvancementWidgetType advancementwidgettype;
         if (f >= 1.0F) {
            advancementwidgettype = AdvancementWidgetType.OBTAINED;
         } else {
            advancementwidgettype = AdvancementWidgetType.UNOBTAINED;
         }

         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_97239_);
         this.m_93228_(p_97267_, p_97268_ + this.f_97251_ + 3, p_97269_ + this.f_97252_, this.f_97243_.m_14992_().m_15551_(), 128 + advancementwidgettype.m_97325_() * 26, 26, 26);
         this.f_97247_.m_91291_().m_115218_(this.f_97243_.m_14990_(), p_97268_ + this.f_97251_ + 8, p_97269_ + this.f_97252_ + 5);
      }

      for(AdvancementWidget advancementwidget : this.f_97249_) {
         advancementwidget.m_97266_(p_97267_, p_97268_, p_97269_);
      }

   }

   public int m_169554_() {
      return this.f_97245_;
   }

   public void m_97264_(AdvancementProgress p_97265_) {
      this.f_97250_ = p_97265_;
   }

   public void m_97306_(AdvancementWidget p_97307_) {
      this.f_97249_.add(p_97307_);
   }

   public void m_97270_(PoseStack p_97271_, int p_97272_, int p_97273_, float p_97274_, int p_97275_, int p_97276_) {
      boolean flag = p_97275_ + p_97272_ + this.f_97251_ + this.f_97245_ + 26 >= this.f_97241_.m_97190_().f_96543_;
      String s = this.f_97250_ == null ? null : this.f_97250_.m_8218_();
      int i = s == null ? 0 : this.f_97247_.f_91062_.m_92895_(s);
      boolean flag1 = 113 - p_97273_ - this.f_97252_ - 26 <= 6 + this.f_97246_.size() * 9;
      float f = this.f_97250_ == null ? 0.0F : this.f_97250_.m_8213_();
      int j = Mth.m_14143_(f * (float)this.f_97245_);
      AdvancementWidgetType advancementwidgettype;
      AdvancementWidgetType advancementwidgettype1;
      AdvancementWidgetType advancementwidgettype2;
      if (f >= 1.0F) {
         j = this.f_97245_ / 2;
         advancementwidgettype = AdvancementWidgetType.OBTAINED;
         advancementwidgettype1 = AdvancementWidgetType.OBTAINED;
         advancementwidgettype2 = AdvancementWidgetType.OBTAINED;
      } else if (j < 2) {
         j = this.f_97245_ / 2;
         advancementwidgettype = AdvancementWidgetType.UNOBTAINED;
         advancementwidgettype1 = AdvancementWidgetType.UNOBTAINED;
         advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
      } else if (j > this.f_97245_ - 2) {
         j = this.f_97245_ / 2;
         advancementwidgettype = AdvancementWidgetType.OBTAINED;
         advancementwidgettype1 = AdvancementWidgetType.OBTAINED;
         advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
      } else {
         advancementwidgettype = AdvancementWidgetType.OBTAINED;
         advancementwidgettype1 = AdvancementWidgetType.UNOBTAINED;
         advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
      }

      int k = this.f_97245_ - j;
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_97239_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      int l = p_97273_ + this.f_97252_;
      int i1;
      if (flag) {
         i1 = p_97272_ + this.f_97251_ - this.f_97245_ + 26 + 6;
      } else {
         i1 = p_97272_ + this.f_97251_;
      }

      int j1 = 32 + this.f_97246_.size() * 9;
      if (!this.f_97246_.isEmpty()) {
         if (flag1) {
            this.m_97287_(p_97271_, i1, l + 26 - j1, this.f_97245_, j1, 10, 200, 26, 0, 52);
         } else {
            this.m_97287_(p_97271_, i1, l, this.f_97245_, j1, 10, 200, 26, 0, 52);
         }
      }

      this.m_93228_(p_97271_, i1, l, 0, advancementwidgettype.m_97325_() * 26, j, 26);
      this.m_93228_(p_97271_, i1 + j, l, 200 - k, advancementwidgettype1.m_97325_() * 26, k, 26);
      this.m_93228_(p_97271_, p_97272_ + this.f_97251_ + 3, p_97273_ + this.f_97252_, this.f_97243_.m_14992_().m_15551_(), 128 + advancementwidgettype2.m_97325_() * 26, 26, 26);
      if (flag) {
         this.f_97247_.f_91062_.m_92744_(p_97271_, this.f_97244_, (float)(i1 + 5), (float)(p_97273_ + this.f_97252_ + 9), -1);
         if (s != null) {
            this.f_97247_.f_91062_.m_92750_(p_97271_, s, (float)(p_97272_ + this.f_97251_ - i), (float)(p_97273_ + this.f_97252_ + 9), -1);
         }
      } else {
         this.f_97247_.f_91062_.m_92744_(p_97271_, this.f_97244_, (float)(p_97272_ + this.f_97251_ + 32), (float)(p_97273_ + this.f_97252_ + 9), -1);
         if (s != null) {
            this.f_97247_.f_91062_.m_92750_(p_97271_, s, (float)(p_97272_ + this.f_97251_ + this.f_97245_ - i - 5), (float)(p_97273_ + this.f_97252_ + 9), -1);
         }
      }

      if (flag1) {
         for(int k1 = 0; k1 < this.f_97246_.size(); ++k1) {
            this.f_97247_.f_91062_.m_92877_(p_97271_, this.f_97246_.get(k1), (float)(i1 + 5), (float)(l + 26 - j1 + 7 + k1 * 9), -5592406);
         }
      } else {
         for(int l1 = 0; l1 < this.f_97246_.size(); ++l1) {
            this.f_97247_.f_91062_.m_92877_(p_97271_, this.f_97246_.get(l1), (float)(i1 + 5), (float)(p_97273_ + this.f_97252_ + 9 + 17 + l1 * 9), -5592406);
         }
      }

      this.f_97247_.m_91291_().m_115218_(this.f_97243_.m_14990_(), p_97272_ + this.f_97251_ + 8, p_97273_ + this.f_97252_ + 5);
   }

   protected void m_97287_(PoseStack p_97288_, int p_97289_, int p_97290_, int p_97291_, int p_97292_, int p_97293_, int p_97294_, int p_97295_, int p_97296_, int p_97297_) {
      this.m_93228_(p_97288_, p_97289_, p_97290_, p_97296_, p_97297_, p_97293_, p_97293_);
      this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_, p_97291_ - p_97293_ - p_97293_, p_97293_, p_97296_ + p_97293_, p_97297_, p_97294_ - p_97293_ - p_97293_, p_97295_);
      this.m_93228_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_, p_97296_ + p_97294_ - p_97293_, p_97297_, p_97293_, p_97293_);
      this.m_93228_(p_97288_, p_97289_, p_97290_ + p_97292_ - p_97293_, p_97296_, p_97297_ + p_97295_ - p_97293_, p_97293_, p_97293_);
      this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_ + p_97292_ - p_97293_, p_97291_ - p_97293_ - p_97293_, p_97293_, p_97296_ + p_97293_, p_97297_ + p_97295_ - p_97293_, p_97294_ - p_97293_ - p_97293_, p_97295_);
      this.m_93228_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_ + p_97292_ - p_97293_, p_97296_ + p_97294_ - p_97293_, p_97297_ + p_97295_ - p_97293_, p_97293_, p_97293_);
      this.m_97277_(p_97288_, p_97289_, p_97290_ + p_97293_, p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_, p_97297_ + p_97293_, p_97294_, p_97295_ - p_97293_ - p_97293_);
      this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_ + p_97293_, p_97291_ - p_97293_ - p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_ + p_97293_, p_97297_ + p_97293_, p_97294_ - p_97293_ - p_97293_, p_97295_ - p_97293_ - p_97293_);
      this.m_97277_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_ + p_97293_, p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_ + p_97294_ - p_97293_, p_97297_ + p_97293_, p_97294_, p_97295_ - p_97293_ - p_97293_);
   }

   protected void m_97277_(PoseStack p_97278_, int p_97279_, int p_97280_, int p_97281_, int p_97282_, int p_97283_, int p_97284_, int p_97285_, int p_97286_) {
      for(int i = 0; i < p_97281_; i += p_97285_) {
         int j = p_97279_ + i;
         int k = Math.min(p_97285_, p_97281_ - i);

         for(int l = 0; l < p_97282_; l += p_97286_) {
            int i1 = p_97280_ + l;
            int j1 = Math.min(p_97286_, p_97282_ - l);
            this.m_93228_(p_97278_, j, i1, p_97283_, p_97284_, k, j1);
         }
      }

   }

   public boolean m_97259_(int p_97260_, int p_97261_, int p_97262_, int p_97263_) {
      if (!this.f_97243_.m_14997_() || this.f_97250_ != null && this.f_97250_.m_8193_()) {
         int i = p_97260_ + this.f_97251_;
         int j = i + 26;
         int k = p_97261_ + this.f_97252_;
         int l = k + 26;
         return p_97262_ >= i && p_97262_ <= j && p_97263_ >= k && p_97263_ <= l;
      } else {
         return false;
      }
   }

   public void m_97313_() {
      if (this.f_97248_ == null && this.f_97242_.m_138319_() != null) {
         this.f_97248_ = this.m_97311_(this.f_97242_);
         if (this.f_97248_ != null) {
            this.f_97248_.m_97306_(this);
         }
      }

   }

   public int m_97314_() {
      return this.f_97252_;
   }

   public int m_97315_() {
      return this.f_97251_;
   }
}