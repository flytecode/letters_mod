package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatScreen extends Screen {
   public static final int f_169234_ = 7;
   private static final Component f_169235_ = new TranslatableComponent("chat_screen.usage");
   private String f_95574_ = "";
   private int f_95575_ = -1;
   protected EditBox f_95573_;
   private final String f_95576_;
   CommandSuggestions f_95577_;

   public ChatScreen(String p_95579_) {
      super(new TranslatableComponent("chat_screen.title"));
      this.f_95576_ = p_95579_;
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_95575_ = this.f_96541_.f_91065_.m_93076_().m_93797_().size();
      this.f_95573_ = new EditBox(this.f_96547_, 4, this.f_96544_ - 12, this.f_96543_ - 4, 12, new TranslatableComponent("chat.editBox")) {
         protected MutableComponent m_5646_() {
            return super.m_5646_().m_130946_(ChatScreen.this.f_95577_.m_93924_());
         }
      };
      this.f_95573_.m_94199_(256);
      this.f_95573_.m_94182_(false);
      this.f_95573_.m_94144_(this.f_95576_);
      this.f_95573_.m_94151_(this::m_95610_);
      this.m_7787_(this.f_95573_);
      this.f_95577_ = new CommandSuggestions(this.f_96541_, this, this.f_95573_, this.f_96547_, false, false, 1, 10, true, -805306368);
      this.f_95577_.m_93881_();
      this.m_94718_(this.f_95573_);
   }

   public void m_6574_(Minecraft p_95600_, int p_95601_, int p_95602_) {
      String s = this.f_95573_.m_94155_();
      this.m_6575_(p_95600_, p_95601_, p_95602_);
      this.m_95612_(s);
      this.f_95577_.m_93881_();
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
      this.f_96541_.f_91065_.m_93076_().m_93810_();
   }

   public void m_96624_() {
      this.f_95573_.m_94120_();
   }

   private void m_95610_(String p_95611_) {
      String s = this.f_95573_.m_94155_();
      this.f_95577_.m_93922_(!s.equals(this.f_95576_));
      this.f_95577_.m_93881_();
   }

   public boolean m_7933_(int p_95591_, int p_95592_, int p_95593_) {
      if (this.f_95577_.m_93888_(p_95591_, p_95592_, p_95593_)) {
         return true;
      } else if (super.m_7933_(p_95591_, p_95592_, p_95593_)) {
         return true;
      } else if (p_95591_ == 256) {
         this.f_96541_.m_91152_((Screen)null);
         return true;
      } else if (p_95591_ != 257 && p_95591_ != 335) {
         if (p_95591_ == 265) {
            this.m_95588_(-1);
            return true;
         } else if (p_95591_ == 264) {
            this.m_95588_(1);
            return true;
         } else if (p_95591_ == 266) {
            this.f_96541_.f_91065_.m_93076_().m_93770_((double)(this.f_96541_.f_91065_.m_93076_().m_93816_() - 1));
            return true;
         } else if (p_95591_ == 267) {
            this.f_96541_.f_91065_.m_93076_().m_93770_((double)(-this.f_96541_.f_91065_.m_93076_().m_93816_() + 1));
            return true;
         } else {
            return false;
         }
      } else {
         String s = this.f_95573_.m_94155_().trim();
         if (!s.isEmpty()) {
            this.m_96615_(s);
         }

         this.f_96541_.m_91152_((Screen)null);
         return true;
      }
   }

   public boolean m_6050_(double p_95581_, double p_95582_, double p_95583_) {
      if (p_95583_ > 1.0D) {
         p_95583_ = 1.0D;
      }

      if (p_95583_ < -1.0D) {
         p_95583_ = -1.0D;
      }

      if (this.f_95577_.m_93882_(p_95583_)) {
         return true;
      } else {
         if (!m_96638_()) {
            p_95583_ *= 7.0D;
         }

         this.f_96541_.f_91065_.m_93076_().m_93770_(p_95583_);
         return true;
      }
   }

   public boolean m_6375_(double p_95585_, double p_95586_, int p_95587_) {
      if (this.f_95577_.m_93884_((double)((int)p_95585_), (double)((int)p_95586_), p_95587_)) {
         return true;
      } else {
         if (p_95587_ == 0) {
            ChatComponent chatcomponent = this.f_96541_.f_91065_.m_93076_();
            if (chatcomponent.m_93772_(p_95585_, p_95586_)) {
               return true;
            }

            Style style = chatcomponent.m_93800_(p_95585_, p_95586_);
            if (style != null && this.m_5561_(style)) {
               return true;
            }
         }

         return this.f_95573_.m_6375_(p_95585_, p_95586_, p_95587_) ? true : super.m_6375_(p_95585_, p_95586_, p_95587_);
      }
   }

   protected void m_6697_(String p_95606_, boolean p_95607_) {
      if (p_95607_) {
         this.f_95573_.m_94144_(p_95606_);
      } else {
         this.f_95573_.m_94164_(p_95606_);
      }

   }

   public void m_95588_(int p_95589_) {
      int i = this.f_95575_ + p_95589_;
      int j = this.f_96541_.f_91065_.m_93076_().m_93797_().size();
      i = Mth.m_14045_(i, 0, j);
      if (i != this.f_95575_) {
         if (i == j) {
            this.f_95575_ = j;
            this.f_95573_.m_94144_(this.f_95574_);
         } else {
            if (this.f_95575_ == j) {
               this.f_95574_ = this.f_95573_.m_94155_();
            }

            this.f_95573_.m_94144_(this.f_96541_.f_91065_.m_93076_().m_93797_().get(i));
            this.f_95577_.m_93922_(false);
            this.f_95575_ = i;
         }
      }
   }

   public void m_6305_(PoseStack p_95595_, int p_95596_, int p_95597_, float p_95598_) {
      this.m_7522_(this.f_95573_);
      this.f_95573_.m_94178_(true);
      m_93172_(p_95595_, 2, this.f_96544_ - 14, this.f_96543_ - 2, this.f_96544_ - 2, this.f_96541_.f_91066_.m_92143_(Integer.MIN_VALUE));
      this.f_95573_.m_6305_(p_95595_, p_95596_, p_95597_, p_95598_);
      this.f_95577_.m_93900_(p_95595_, p_95596_, p_95597_);
      Style style = this.f_96541_.f_91065_.m_93076_().m_93800_((double)p_95596_, (double)p_95597_);
      if (style != null && style.m_131186_() != null) {
         this.m_96570_(p_95595_, style, p_95596_, p_95597_);
      }

      super.m_6305_(p_95595_, p_95596_, p_95597_, p_95598_);
   }

   public boolean m_7043_() {
      return false;
   }

   private void m_95612_(String p_95613_) {
      this.f_95573_.m_94144_(p_95613_);
   }

   protected void m_142228_(NarrationElementOutput p_169238_) {
      p_169238_.m_169146_(NarratedElementType.TITLE, this.m_96636_());
      p_169238_.m_169146_(NarratedElementType.USAGE, f_169235_);
      String s = this.f_95573_.m_94155_();
      if (!s.isEmpty()) {
         p_169238_.m_142047_().m_169146_(NarratedElementType.TITLE, new TranslatableComponent("chat_screen.message", s));
      }

   }
}