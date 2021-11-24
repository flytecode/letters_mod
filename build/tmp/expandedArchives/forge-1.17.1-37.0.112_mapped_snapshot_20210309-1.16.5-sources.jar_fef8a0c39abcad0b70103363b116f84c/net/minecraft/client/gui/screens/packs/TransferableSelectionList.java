package net.minecraft.client.gui.screens.packs;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TransferableSelectionList extends ObjectSelectionList<TransferableSelectionList.PackEntry> {
   static final ResourceLocation f_100052_ = new ResourceLocation("textures/gui/resource_packs.png");
   static final Component f_100053_ = new TranslatableComponent("pack.incompatible");
   static final Component f_100054_ = new TranslatableComponent("pack.incompatible.confirm.title");
   private final Component f_100055_;

   public TransferableSelectionList(Minecraft p_100058_, int p_100059_, int p_100060_, Component p_100061_) {
      super(p_100058_, p_100059_, p_100060_, 32, p_100060_ - 55 + 4, 36);
      this.f_100055_ = p_100061_;
      this.f_93394_ = false;
      this.m_93473_(true, (int)(9.0F * 1.5F));
   }

   protected void m_7154_(PoseStack p_100063_, int p_100064_, int p_100065_, Tesselator p_100066_) {
      Component component = (new TextComponent("")).m_7220_(this.f_100055_).m_130944_(ChatFormatting.UNDERLINE, ChatFormatting.BOLD);
      this.f_93386_.f_91062_.m_92889_(p_100063_, component, (float)(p_100064_ + this.f_93388_ / 2 - this.f_93386_.f_91062_.m_92852_(component) / 2), (float)Math.min(this.f_93390_ + 3, p_100065_), 16777215);
   }

   public int m_5759_() {
      return this.f_93388_;
   }

   protected int m_5756_() {
      return this.f_93392_ - 6;
   }

   @OnlyIn(Dist.CLIENT)
   public static class PackEntry extends ObjectSelectionList.Entry<TransferableSelectionList.PackEntry> {
      private static final int f_170026_ = 0;
      private static final int f_170027_ = 32;
      private static final int f_170028_ = 64;
      private static final int f_170029_ = 96;
      private static final int f_170030_ = 0;
      private static final int f_170031_ = 32;
      private static final int f_170032_ = 157;
      private static final int f_170033_ = 157;
      private static final String f_170034_ = "...";
      private final TransferableSelectionList f_100077_;
      protected final Minecraft f_100075_;
      protected final Screen f_100076_;
      private final PackSelectionModel.Entry f_100078_;
      private final FormattedCharSequence f_100079_;
      private final MultiLineLabel f_100080_;
      private final FormattedCharSequence f_100081_;
      private final MultiLineLabel f_100082_;

      public PackEntry(Minecraft p_100084_, TransferableSelectionList p_100085_, Screen p_100086_, PackSelectionModel.Entry p_100087_) {
         this.f_100075_ = p_100084_;
         this.f_100076_ = p_100086_;
         this.f_100078_ = p_100087_;
         this.f_100077_ = p_100085_;
         this.f_100079_ = m_100104_(p_100084_, p_100087_.m_7356_());
         this.f_100080_ = m_100109_(p_100084_, p_100087_.m_99929_());
         this.f_100081_ = m_100104_(p_100084_, TransferableSelectionList.f_100053_);
         this.f_100082_ = m_100109_(p_100084_, p_100087_.m_7709_().m_10492_());
      }

      private static FormattedCharSequence m_100104_(Minecraft p_100105_, Component p_100106_) {
         int i = p_100105_.f_91062_.m_92852_(p_100106_);
         if (i > 157) {
            FormattedText formattedtext = FormattedText.m_130773_(p_100105_.f_91062_.m_92854_(p_100106_, 157 - p_100105_.f_91062_.m_92895_("...")), FormattedText.m_130775_("..."));
            return Language.m_128107_().m_5536_(formattedtext);
         } else {
            return p_100106_.m_7532_();
         }
      }

      private static MultiLineLabel m_100109_(Minecraft p_100110_, Component p_100111_) {
         return MultiLineLabel.m_94345_(p_100110_.f_91062_, p_100111_, 157, 2);
      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", this.f_100078_.m_7356_());
      }

      public void m_6311_(PoseStack p_100094_, int p_100095_, int p_100096_, int p_100097_, int p_100098_, int p_100099_, int p_100100_, int p_100101_, boolean p_100102_, float p_100103_) {
         PackCompatibility packcompatibility = this.f_100078_.m_7709_();
         if (!packcompatibility.m_10489_()) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.m_93172_(p_100094_, p_100097_ - 1, p_100096_ - 1, p_100097_ + p_100098_ - 9, p_100096_ + p_100099_ + 1, -8978432);
         }

         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, this.f_100078_.m_6876_());
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0F, 0.0F, 32, 32, 32, 32);
         FormattedCharSequence formattedcharsequence = this.f_100079_;
         MultiLineLabel multilinelabel = this.f_100080_;
         if (this.m_100088_() && (this.f_100075_.f_91066_.f_92051_ || p_100102_)) {
            RenderSystem.m_157456_(0, TransferableSelectionList.f_100052_);
            GuiComponent.m_93172_(p_100094_, p_100097_, p_100096_, p_100097_ + 32, p_100096_ + 32, -1601138544);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            int i = p_100100_ - p_100097_;
            int j = p_100101_ - p_100096_;
            if (!this.f_100078_.m_7709_().m_10489_()) {
               formattedcharsequence = this.f_100081_;
               multilinelabel = this.f_100082_;
            }

            if (this.f_100078_.m_99930_()) {
               if (i < 32) {
                  GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else {
               if (this.f_100078_.m_99931_()) {
                  if (i < 16) {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 32.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 32.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.f_100078_.m_7802_()) {
                  if (i < 32 && i > 16 && j < 16) {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 96.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 96.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.f_100078_.m_7803_()) {
                  if (i < 32 && i > 16 && j > 16) {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 64.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     GuiComponent.m_93133_(p_100094_, p_100097_, p_100096_, 64.0F, 0.0F, 32, 32, 256, 256);
                  }
               }
            }
         }

         this.f_100075_.f_91062_.m_92744_(p_100094_, formattedcharsequence, (float)(p_100097_ + 32 + 2), (float)(p_100096_ + 1), 16777215);
         multilinelabel.m_6516_(p_100094_, p_100097_ + 32 + 2, p_100096_ + 12, 10, 8421504);
      }

      private boolean m_100088_() {
         return !this.f_100078_.m_7867_() || !this.f_100078_.m_7844_();
      }

      public boolean m_6375_(double p_100090_, double p_100091_, int p_100092_) {
         double d0 = p_100090_ - (double)this.f_100077_.m_5747_();
         double d1 = p_100091_ - (double)this.f_100077_.m_7610_(this.f_100077_.m_6702_().indexOf(this));
         if (this.m_100088_() && d0 <= 32.0D) {
            if (this.f_100078_.m_99930_()) {
               PackCompatibility packcompatibility = this.f_100078_.m_7709_();
               if (packcompatibility.m_10489_()) {
                  this.f_100078_.m_7849_();
               } else {
                  Component component = packcompatibility.m_10493_();
                  this.f_100075_.m_91152_(new ConfirmScreen((p_100108_) -> {
                     this.f_100075_.m_91152_(this.f_100076_);
                     if (p_100108_) {
                        this.f_100078_.m_7849_();
                     }

                  }, TransferableSelectionList.f_100054_, component));
               }

               return true;
            }

            if (d0 < 16.0D && this.f_100078_.m_99931_()) {
               this.f_100078_.m_7850_();
               return true;
            }

            if (d0 > 16.0D && d1 < 16.0D && this.f_100078_.m_7802_()) {
               this.f_100078_.m_7852_();
               return true;
            }

            if (d0 > 16.0D && d1 > 16.0D && this.f_100078_.m_7803_()) {
               this.f_100078_.m_7845_();
               return true;
            }
         }

         return false;
      }
   }
}