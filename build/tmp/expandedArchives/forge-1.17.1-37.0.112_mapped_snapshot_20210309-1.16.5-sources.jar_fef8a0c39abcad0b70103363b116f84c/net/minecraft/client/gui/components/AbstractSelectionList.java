package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractSelectionList<E extends AbstractSelectionList.Entry<E>> extends AbstractContainerEventHandler implements Widget, NarratableEntry {
   protected final Minecraft f_93386_;
   protected final int f_93387_;
   private final List<E> f_93385_ = new AbstractSelectionList.TrackedList();
   protected int f_93388_;
   protected int f_93389_;
   protected int f_93390_;
   protected int f_93391_;
   protected int f_93392_;
   protected int f_93393_;
   protected boolean f_93394_ = true;
   private double f_93396_;
   private boolean f_93397_ = true;
   private boolean f_93398_;
   protected int f_93395_;
   private boolean f_93399_;
   @Nullable
   private E f_93400_;
   private boolean f_93401_ = true;
   private boolean f_93402_ = true;
   @Nullable
   private E f_168789_;

   public AbstractSelectionList(Minecraft p_93404_, int p_93405_, int p_93406_, int p_93407_, int p_93408_, int p_93409_) {
      this.f_93386_ = p_93404_;
      this.f_93388_ = p_93405_;
      this.f_93389_ = p_93406_;
      this.f_93390_ = p_93407_;
      this.f_93391_ = p_93408_;
      this.f_93387_ = p_93409_;
      this.f_93393_ = 0;
      this.f_93392_ = p_93405_;
   }

   public void m_93471_(boolean p_93472_) {
      this.f_93397_ = p_93472_;
   }

   protected void m_93473_(boolean p_93474_, int p_93475_) {
      this.f_93398_ = p_93474_;
      this.f_93395_ = p_93475_;
      if (!p_93474_) {
         this.f_93395_ = 0;
      }

   }

   public int m_5759_() {
      return 220;
   }

   @Nullable
   public E m_93511_() {
      return this.f_93400_;
   }

   public void m_6987_(@Nullable E p_93462_) {
      this.f_93400_ = p_93462_;
   }

   public void m_93488_(boolean p_93489_) {
      this.f_93401_ = p_93489_;
   }

   public void m_93496_(boolean p_93497_) {
      this.f_93402_ = p_93497_;
   }

   @Nullable
   public E m_7222_() {
      return (E)(super.m_7222_());
   }

   public final List<E> m_6702_() {
      return this.f_93385_;
   }

   protected final void m_93516_() {
      this.f_93385_.clear();
   }

   protected void m_5988_(Collection<E> p_93470_) {
      this.f_93385_.clear();
      this.f_93385_.addAll(p_93470_);
   }

   protected E m_93500_(int p_93501_) {
      return this.m_6702_().get(p_93501_);
   }

   protected int m_7085_(E p_93487_) {
      this.f_93385_.add(p_93487_);
      return this.f_93385_.size() - 1;
   }

   protected int m_5773_() {
      return this.m_6702_().size();
   }

   protected boolean m_7987_(int p_93504_) {
      return Objects.equals(this.m_93511_(), this.m_6702_().get(p_93504_));
   }

   @Nullable
   protected final E m_93412_(double p_93413_, double p_93414_) {
      int i = this.m_5759_() / 2;
      int j = this.f_93393_ + this.f_93388_ / 2;
      int k = j - i;
      int l = j + i;
      int i1 = Mth.m_14107_(p_93414_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
      int j1 = i1 / this.f_93387_;
      return (E)(p_93413_ < (double)this.m_5756_() && p_93413_ >= (double)k && p_93413_ <= (double)l && j1 >= 0 && i1 >= 0 && j1 < this.m_5773_() ? this.m_6702_().get(j1) : null);
   }

   public void m_93437_(int p_93438_, int p_93439_, int p_93440_, int p_93441_) {
      this.f_93388_ = p_93438_;
      this.f_93389_ = p_93439_;
      this.f_93390_ = p_93440_;
      this.f_93391_ = p_93441_;
      this.f_93393_ = 0;
      this.f_93392_ = p_93438_;
   }

   public void m_93507_(int p_93508_) {
      this.f_93393_ = p_93508_;
      this.f_93392_ = p_93508_ + this.f_93388_;
   }

   protected int m_5775_() {
      return this.m_5773_() * this.f_93387_ + this.f_93395_;
   }

   protected void m_6205_(int p_93431_, int p_93432_) {
   }

   protected void m_7154_(PoseStack p_93458_, int p_93459_, int p_93460_, Tesselator p_93461_) {
   }

   protected void m_7733_(PoseStack p_93442_) {
   }

   protected void m_7415_(PoseStack p_93443_, int p_93444_, int p_93445_) {
   }

   public void m_6305_(PoseStack p_93447_, int p_93448_, int p_93449_, float p_93450_) {
      this.m_7733_(p_93447_);
      int i = this.m_5756_();
      int j = i + 6;
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      this.f_168789_ = this.m_5953_((double)p_93448_, (double)p_93449_) ? this.m_93412_((double)p_93448_, (double)p_93449_) : null;
      if (this.f_93401_) {
         RenderSystem.m_157456_(0, GuiComponent.f_93096_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         float f = 32.0F;
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93391_, 0.0D).m_7421_((float)this.f_93393_ / 32.0F, (float)(this.f_93391_ + (int)this.m_93517_()) / 32.0F).m_6122_(32, 32, 32, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)this.f_93391_, 0.0D).m_7421_((float)this.f_93392_ / 32.0F, (float)(this.f_93391_ + (int)this.m_93517_()) / 32.0F).m_6122_(32, 32, 32, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)this.f_93390_, 0.0D).m_7421_((float)this.f_93392_ / 32.0F, (float)(this.f_93390_ + (int)this.m_93517_()) / 32.0F).m_6122_(32, 32, 32, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93390_, 0.0D).m_7421_((float)this.f_93393_ / 32.0F, (float)(this.f_93390_ + (int)this.m_93517_()) / 32.0F).m_6122_(32, 32, 32, 255).m_5752_();
         tesselator.m_85914_();
      }

      int j1 = this.m_5747_();
      int k = this.f_93390_ + 4 - (int)this.m_93517_();
      if (this.f_93398_) {
         this.m_7154_(p_93447_, j1, k, tesselator);
      }

      this.m_93451_(p_93447_, j1, k, p_93448_, p_93449_, p_93450_);
      if (this.f_93402_) {
         RenderSystem.m_157427_(GameRenderer::m_172820_);
         RenderSystem.m_157456_(0, GuiComponent.f_93096_);
         RenderSystem.m_69482_();
         RenderSystem.m_69456_(519);
         float f1 = 32.0F;
         int l = -100;
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93390_, -100.0D).m_7421_(0.0F, (float)this.f_93390_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)(this.f_93393_ + this.f_93388_), (double)this.f_93390_, -100.0D).m_7421_((float)this.f_93388_ / 32.0F, (float)this.f_93390_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)(this.f_93393_ + this.f_93388_), 0.0D, -100.0D).m_7421_((float)this.f_93388_ / 32.0F, 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, 0.0D, -100.0D).m_7421_(0.0F, 0.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93389_, -100.0D).m_7421_(0.0F, (float)this.f_93389_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)(this.f_93393_ + this.f_93388_), (double)this.f_93389_, -100.0D).m_7421_((float)this.f_93388_ / 32.0F, (float)this.f_93389_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)(this.f_93393_ + this.f_93388_), (double)this.f_93391_, -100.0D).m_7421_((float)this.f_93388_ / 32.0F, (float)this.f_93391_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93391_, -100.0D).m_7421_(0.0F, (float)this.f_93391_ / 32.0F).m_6122_(64, 64, 64, 255).m_5752_();
         tesselator.m_85914_();
         RenderSystem.m_69456_(515);
         RenderSystem.m_69465_();
         RenderSystem.m_69478_();
         RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
         RenderSystem.m_69472_();
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         int i1 = 4;
         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
         bufferbuilder.m_5483_((double)this.f_93393_, (double)(this.f_93390_ + 4), 0.0D).m_6122_(0, 0, 0, 0).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)(this.f_93390_ + 4), 0.0D).m_6122_(0, 0, 0, 0).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)this.f_93390_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93390_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)this.f_93391_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)this.f_93391_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93392_, (double)(this.f_93391_ - 4), 0.0D).m_6122_(0, 0, 0, 0).m_5752_();
         bufferbuilder.m_5483_((double)this.f_93393_, (double)(this.f_93391_ - 4), 0.0D).m_6122_(0, 0, 0, 0).m_5752_();
         tesselator.m_85914_();
      }

      int k1 = this.m_93518_();
      if (k1 > 0) {
         RenderSystem.m_69472_();
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         int l1 = (int)((float)((this.f_93391_ - this.f_93390_) * (this.f_93391_ - this.f_93390_)) / (float)this.m_5775_());
         l1 = Mth.m_14045_(l1, 32, this.f_93391_ - this.f_93390_ - 8);
         int i2 = (int)this.m_93517_() * (this.f_93391_ - this.f_93390_ - l1) / k1 + this.f_93390_;
         if (i2 < this.f_93390_) {
            i2 = this.f_93390_;
         }

         bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
         bufferbuilder.m_5483_((double)i, (double)this.f_93391_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)j, (double)this.f_93391_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)j, (double)this.f_93390_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)this.f_93390_, 0.0D).m_6122_(0, 0, 0, 255).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)(i2 + l1), 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
         bufferbuilder.m_5483_((double)j, (double)(i2 + l1), 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
         bufferbuilder.m_5483_((double)j, (double)i2, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)i2, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)(i2 + l1 - 1), 0.0D).m_6122_(192, 192, 192, 255).m_5752_();
         bufferbuilder.m_5483_((double)(j - 1), (double)(i2 + l1 - 1), 0.0D).m_6122_(192, 192, 192, 255).m_5752_();
         bufferbuilder.m_5483_((double)(j - 1), (double)i2, 0.0D).m_6122_(192, 192, 192, 255).m_5752_();
         bufferbuilder.m_5483_((double)i, (double)i2, 0.0D).m_6122_(192, 192, 192, 255).m_5752_();
         tesselator.m_85914_();
      }

      this.m_7415_(p_93447_, p_93448_, p_93449_);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   protected void m_93494_(E p_93495_) {
      this.m_93410_((double)(this.m_6702_().indexOf(p_93495_) * this.f_93387_ + this.f_93387_ / 2 - (this.f_93391_ - this.f_93390_) / 2));
   }

   protected void m_93498_(E p_93499_) {
      int i = this.m_7610_(this.m_6702_().indexOf(p_93499_));
      int j = i - this.f_93390_ - 4 - this.f_93387_;
      if (j < 0) {
         this.m_93429_(j);
      }

      int k = this.f_93391_ - i - this.f_93387_ - this.f_93387_;
      if (k < 0) {
         this.m_93429_(-k);
      }

   }

   private void m_93429_(int p_93430_) {
      this.m_93410_(this.m_93517_() + (double)p_93430_);
   }

   public double m_93517_() {
      return this.f_93396_;
   }

   public void m_93410_(double p_93411_) {
      this.f_93396_ = Mth.m_14008_(p_93411_, 0.0D, (double)this.m_93518_());
   }

   public int m_93518_() {
      return Math.max(0, this.m_5775_() - (this.f_93391_ - this.f_93390_ - 4));
   }

   public int m_168793_() {
      return (int)this.m_93517_() - this.f_93389_ - this.f_93395_;
   }

   protected void m_93481_(double p_93482_, double p_93483_, int p_93484_) {
      this.f_93399_ = p_93484_ == 0 && p_93482_ >= (double)this.m_5756_() && p_93482_ < (double)(this.m_5756_() + 6);
   }

   protected int m_5756_() {
      return this.f_93388_ / 2 + 124;
   }

   public boolean m_6375_(double p_93420_, double p_93421_, int p_93422_) {
      this.m_93481_(p_93420_, p_93421_, p_93422_);
      if (!this.m_5953_(p_93420_, p_93421_)) {
         return false;
      } else {
         E e = this.m_93412_(p_93420_, p_93421_);
         if (e != null) {
            if (e.m_6375_(p_93420_, p_93421_, p_93422_)) {
               this.m_7522_(e);
               this.m_7897_(true);
               return true;
            }
         } else if (p_93422_ == 0) {
            this.m_6205_((int)(p_93420_ - (double)(this.f_93393_ + this.f_93388_ / 2 - this.m_5759_() / 2)), (int)(p_93421_ - (double)this.f_93390_) + (int)this.m_93517_() - 4);
            return true;
         }

         return this.f_93399_;
      }
   }

   public boolean m_6348_(double p_93491_, double p_93492_, int p_93493_) {
      if (this.m_7222_() != null) {
         this.m_7222_().m_6348_(p_93491_, p_93492_, p_93493_);
      }

      return false;
   }

   public boolean m_7979_(double p_93424_, double p_93425_, int p_93426_, double p_93427_, double p_93428_) {
      if (super.m_7979_(p_93424_, p_93425_, p_93426_, p_93427_, p_93428_)) {
         return true;
      } else if (p_93426_ == 0 && this.f_93399_) {
         if (p_93425_ < (double)this.f_93390_) {
            this.m_93410_(0.0D);
         } else if (p_93425_ > (double)this.f_93391_) {
            this.m_93410_((double)this.m_93518_());
         } else {
            double d0 = (double)Math.max(1, this.m_93518_());
            int i = this.f_93391_ - this.f_93390_;
            int j = Mth.m_14045_((int)((float)(i * i) / (float)this.m_5775_()), 32, i - 8);
            double d1 = Math.max(1.0D, d0 / (double)(i - j));
            this.m_93410_(this.m_93517_() + p_93428_ * d1);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_6050_(double p_93416_, double p_93417_, double p_93418_) {
      this.m_93410_(this.m_93517_() - p_93418_ * (double)this.f_93387_ / 2.0D);
      return true;
   }

   public boolean m_7933_(int p_93434_, int p_93435_, int p_93436_) {
      if (super.m_7933_(p_93434_, p_93435_, p_93436_)) {
         return true;
      } else if (p_93434_ == 264) {
         this.m_6778_(AbstractSelectionList.SelectionDirection.DOWN);
         return true;
      } else if (p_93434_ == 265) {
         this.m_6778_(AbstractSelectionList.SelectionDirection.UP);
         return true;
      } else {
         return false;
      }
   }

   protected void m_6778_(AbstractSelectionList.SelectionDirection p_93463_) {
      this.m_93464_(p_93463_, (p_93510_) -> {
         return true;
      });
   }

   protected void m_93519_() {
      E e = this.m_93511_();
      if (e != null) {
         this.m_6987_(e);
         this.m_93498_(e);
      }

   }

   protected void m_93464_(AbstractSelectionList.SelectionDirection p_93465_, Predicate<E> p_93466_) {
      int i = p_93465_ == AbstractSelectionList.SelectionDirection.UP ? -1 : 1;
      if (!this.m_6702_().isEmpty()) {
         int j = this.m_6702_().indexOf(this.m_93511_());

         while(true) {
            int k = Mth.m_14045_(j + i, 0, this.m_5773_() - 1);
            if (j == k) {
               break;
            }

            E e = this.m_6702_().get(k);
            if (p_93466_.test(e)) {
               this.m_6987_(e);
               this.m_93498_(e);
               break;
            }

            j = k;
         }
      }

   }

   public boolean m_5953_(double p_93479_, double p_93480_) {
      return p_93480_ >= (double)this.f_93390_ && p_93480_ <= (double)this.f_93391_ && p_93479_ >= (double)this.f_93393_ && p_93479_ <= (double)this.f_93392_;
   }

   protected void m_93451_(PoseStack p_93452_, int p_93453_, int p_93454_, int p_93455_, int p_93456_, float p_93457_) {
      int i = this.m_5773_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();

      for(int j = 0; j < i; ++j) {
         int k = this.m_7610_(j);
         int l = this.m_93485_(j);
         if (l >= this.f_93390_ && k <= this.f_93391_) {
            int i1 = p_93454_ + j * this.f_93387_ + this.f_93395_;
            int j1 = this.f_93387_ - 4;
            E e = this.m_93500_(j);
            int k1 = this.m_5759_();
            if (this.f_93397_ && this.m_7987_(j)) {
               int l1 = this.f_93393_ + this.f_93388_ / 2 - k1 / 2;
               int i2 = this.f_93393_ + this.f_93388_ / 2 + k1 / 2;
               RenderSystem.m_69472_();
               RenderSystem.m_157427_(GameRenderer::m_172808_);
               float f = this.m_5694_() ? 1.0F : 0.5F;
               RenderSystem.m_157429_(f, f, f, 1.0F);
               bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
               bufferbuilder.m_5483_((double)l1, (double)(i1 + j1 + 2), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)i2, (double)(i1 + j1 + 2), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)i2, (double)(i1 - 2), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)l1, (double)(i1 - 2), 0.0D).m_5752_();
               tesselator.m_85914_();
               RenderSystem.m_157429_(0.0F, 0.0F, 0.0F, 1.0F);
               bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
               bufferbuilder.m_5483_((double)(l1 + 1), (double)(i1 + j1 + 1), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)(i2 - 1), (double)(i1 + j1 + 1), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)(i2 - 1), (double)(i1 - 1), 0.0D).m_5752_();
               bufferbuilder.m_5483_((double)(l1 + 1), (double)(i1 - 1), 0.0D).m_5752_();
               tesselator.m_85914_();
               RenderSystem.m_69493_();
            }

            int j2 = this.m_5747_();
            e.m_6311_(p_93452_, j, k, j2, k1, j1, p_93455_, p_93456_, Objects.equals(this.f_168789_, e), p_93457_);
         }
      }

   }

   public int m_5747_() {
      return this.f_93393_ + this.f_93388_ / 2 - this.m_5759_() / 2 + 2;
   }

   public int m_93520_() {
      return this.m_5747_() + this.m_5759_();
   }

   protected int m_7610_(int p_93512_) {
      return this.f_93390_ + 4 - (int)this.m_93517_() + p_93512_ * this.f_93387_ + this.f_93395_;
   }

   private int m_93485_(int p_93486_) {
      return this.m_7610_(p_93486_) + this.f_93387_;
   }

   protected boolean m_5694_() {
      return false;
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      if (this.m_5694_()) {
         return NarratableEntry.NarrationPriority.FOCUSED;
      } else {
         return this.f_168789_ != null ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
      }
   }

   @Nullable
   protected E m_93514_(int p_93515_) {
      E e = this.f_93385_.get(p_93515_);
      return (E)(this.m_93502_(this.f_93385_.get(p_93515_)) ? e : null);
   }

   protected boolean m_93502_(E p_93503_) {
      boolean flag = this.f_93385_.remove(p_93503_);
      if (flag && p_93503_ == this.m_93511_()) {
         this.m_6987_((E)null);
      }

      return flag;
   }

   @Nullable
   protected E m_168795_() {
      return this.f_168789_;
   }

   void m_93505_(AbstractSelectionList.Entry<E> p_93506_) {
      p_93506_.f_93521_ = this;
   }

   protected void m_168790_(NarrationElementOutput p_168791_, E p_168792_) {
      List<E> list = this.m_6702_();
      if (list.size() > 1) {
         int i = list.indexOf(p_168792_);
         if (i != -1) {
            p_168791_.m_169146_(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.list", i + 1, list.size()));
         }
      }

   }

   public int getWidth() { return this.f_93388_; }
   public int getHeight() { return this.f_93389_; }
   public int getTop() { return this.f_93390_; }
   public int getBottom() { return this.f_93391_; }
   public int getLeft() { return this.f_93393_; }
   public int getRight() { return this.f_93392_; }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry<E extends AbstractSelectionList.Entry<E>> implements GuiEventListener {
      @Deprecated
      protected AbstractSelectionList<E> f_93521_;

      public abstract void m_6311_(PoseStack p_93523_, int p_93524_, int p_93525_, int p_93526_, int p_93527_, int p_93528_, int p_93529_, int p_93530_, boolean p_93531_, float p_93532_);

      public boolean m_5953_(double p_93537_, double p_93538_) {
         return Objects.equals(this.f_93521_.m_93412_(p_93537_, p_93538_), this);
      }
   }

   @OnlyIn(Dist.CLIENT)
   protected static enum SelectionDirection {
      UP,
      DOWN;
   }

   @OnlyIn(Dist.CLIENT)
   class TrackedList extends AbstractList<E> {
      private final List<E> f_93550_ = Lists.newArrayList();

      public E get(int p_93557_) {
         return this.f_93550_.get(p_93557_);
      }

      public int size() {
         return this.f_93550_.size();
      }

      public E set(int p_93559_, E p_93560_) {
         E e = this.f_93550_.set(p_93559_, p_93560_);
         AbstractSelectionList.this.m_93505_(p_93560_);
         return e;
      }

      public void add(int p_93567_, E p_93568_) {
         this.f_93550_.add(p_93567_, p_93568_);
         AbstractSelectionList.this.m_93505_(p_93568_);
      }

      public E remove(int p_93565_) {
         return this.f_93550_.remove(p_93565_);
      }
   }
}
