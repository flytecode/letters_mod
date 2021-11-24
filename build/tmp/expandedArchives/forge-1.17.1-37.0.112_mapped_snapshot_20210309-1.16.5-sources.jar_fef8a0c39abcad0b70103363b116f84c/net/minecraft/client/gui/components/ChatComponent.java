package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ChatComponent extends GuiComponent {
   private static final Logger f_93757_ = LogManager.getLogger();
   private static final int f_168843_ = 100;
   private final Minecraft f_93758_;
   private final List<String> f_93759_ = Lists.newArrayList();
   private final List<GuiMessage<Component>> f_93760_ = Lists.newArrayList();
   private final List<GuiMessage<FormattedCharSequence>> f_93761_ = Lists.newArrayList();
   private final Deque<Component> f_93762_ = Queues.newArrayDeque();
   private int f_93763_;
   private boolean f_93764_;
   private long f_93765_;

   public ChatComponent(Minecraft p_93768_) {
      this.f_93758_ = p_93768_;
   }

   public void m_93780_(PoseStack p_93781_, int p_93782_) {
      if (!this.m_93817_()) {
         this.m_93820_();
         int i = this.m_93816_();
         int j = this.f_93761_.size();
         if (j > 0) {
            boolean flag = false;
            if (this.m_93818_()) {
               flag = true;
            }

            float f = (float)this.m_93815_();
            int k = Mth.m_14167_((float)this.m_93813_() / f);
            p_93781_.m_85836_();
            p_93781_.m_85837_(4.0D, 8.0D, 0.0D);
            p_93781_.m_85841_(f, f, 1.0F);
            double d0 = this.f_93758_.f_91066_.f_92120_ * (double)0.9F + (double)0.1F;
            double d1 = this.f_93758_.f_91066_.f_92122_;
            double d2 = 9.0D * (this.f_93758_.f_91066_.f_92121_ + 1.0D);
            double d3 = -8.0D * (this.f_93758_.f_91066_.f_92121_ + 1.0D) + 4.0D * this.f_93758_.f_91066_.f_92121_;
            int l = 0;

            for(int i1 = 0; i1 + this.f_93763_ < this.f_93761_.size() && i1 < i; ++i1) {
               GuiMessage<FormattedCharSequence> guimessage = this.f_93761_.get(i1 + this.f_93763_);
               if (guimessage != null) {
                  int j1 = p_93782_ - guimessage.m_90794_();
                  if (j1 < 200 || flag) {
                     double d4 = flag ? 1.0D : m_93775_(j1);
                     int l1 = (int)(255.0D * d4 * d0);
                     int i2 = (int)(255.0D * d4 * d1);
                     ++l;
                     if (l1 > 3) {
                        int j2 = 0;
                        double d5 = (double)(-i1) * d2;
                        p_93781_.m_85836_();
                        p_93781_.m_85837_(0.0D, 0.0D, 50.0D);
                        m_93172_(p_93781_, -4, (int)(d5 - d2), 0 + k + 4, (int)d5, i2 << 24);
                        RenderSystem.m_69478_();
                        p_93781_.m_85837_(0.0D, 0.0D, 50.0D);
                        this.f_93758_.f_91062_.m_92744_(p_93781_, guimessage.m_90793_(), 0.0F, (float)((int)(d5 + d3)), 16777215 + (l1 << 24));
                        RenderSystem.m_69461_();
                        p_93781_.m_85849_();
                     }
                  }
               }
            }

            if (!this.f_93762_.isEmpty()) {
               int k2 = (int)(128.0D * d0);
               int i3 = (int)(255.0D * d1);
               p_93781_.m_85836_();
               p_93781_.m_85837_(0.0D, 0.0D, 50.0D);
               m_93172_(p_93781_, -2, 0, k + 4, 9, i3 << 24);
               RenderSystem.m_69478_();
               p_93781_.m_85837_(0.0D, 0.0D, 50.0D);
               this.f_93758_.f_91062_.m_92763_(p_93781_, new TranslatableComponent("chat.queue", this.f_93762_.size()), 0.0F, 1.0F, 16777215 + (k2 << 24));
               p_93781_.m_85849_();
               RenderSystem.m_69461_();
            }

            if (flag) {
               int l2 = 9;
               int j3 = j * l2;
               int k3 = l * l2;
               int l3 = this.f_93763_ * k3 / j;
               int k1 = k3 * k3 / j3;
               if (j3 != k3) {
                  int i4 = l3 > 0 ? 170 : 96;
                  int j4 = this.f_93764_ ? 13382451 : 3355562;
                  p_93781_.m_85837_(-4.0D, 0.0D, 0.0D);
                  m_93172_(p_93781_, 0, -l3, 2, -l3 - k1, j4 + (i4 << 24));
                  m_93172_(p_93781_, 2, -l3, 1, -l3 - k1, 13421772 + (i4 << 24));
               }
            }

            p_93781_.m_85849_();
         }
      }
   }

   private boolean m_93817_() {
      return this.f_93758_.f_91066_.f_92119_ == ChatVisiblity.HIDDEN;
   }

   private static double m_93775_(int p_93776_) {
      double d0 = (double)p_93776_ / 200.0D;
      d0 = 1.0D - d0;
      d0 = d0 * 10.0D;
      d0 = Mth.m_14008_(d0, 0.0D, 1.0D);
      return d0 * d0;
   }

   public void m_93795_(boolean p_93796_) {
      this.f_93762_.clear();
      this.f_93761_.clear();
      this.f_93760_.clear();
      if (p_93796_) {
         this.f_93759_.clear();
      }

   }

   public void m_93785_(Component p_93786_) {
      this.m_93787_(p_93786_, 0);
   }

   private void m_93787_(Component p_93788_, int p_93789_) {
      this.m_93790_(p_93788_, p_93789_, this.f_93758_.f_91065_.m_93079_(), false);
      f_93757_.info("[CHAT] {}", (Object)p_93788_.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void m_93790_(Component p_93791_, int p_93792_, int p_93793_, boolean p_93794_) {
      if (p_93792_ != 0) {
         this.m_93803_(p_93792_);
      }

      int i = Mth.m_14107_((double)this.m_93813_() / this.m_93815_());
      List<FormattedCharSequence> list = ComponentRenderUtils.m_94005_(p_93791_, i, this.f_93758_.f_91062_);
      boolean flag = this.m_93818_();

      for(FormattedCharSequence formattedcharsequence : list) {
         if (flag && this.f_93763_ > 0) {
            this.f_93764_ = true;
            this.m_93770_(1.0D);
         }

         this.f_93761_.add(0, new GuiMessage<>(p_93793_, formattedcharsequence, p_93792_));
      }

      while(this.f_93761_.size() > 100) {
         this.f_93761_.remove(this.f_93761_.size() - 1);
      }

      if (!p_93794_) {
         this.f_93760_.add(0, new GuiMessage<>(p_93793_, p_93791_, p_93792_));

         while(this.f_93760_.size() > 100) {
            this.f_93760_.remove(this.f_93760_.size() - 1);
         }
      }

   }

   public void m_93769_() {
      this.f_93761_.clear();
      this.m_93810_();

      for(int i = this.f_93760_.size() - 1; i >= 0; --i) {
         GuiMessage<Component> guimessage = this.f_93760_.get(i);
         this.m_93790_(guimessage.m_90793_(), guimessage.m_90795_(), guimessage.m_90794_(), true);
      }

   }

   public List<String> m_93797_() {
      return this.f_93759_;
   }

   public void m_93783_(String p_93784_) {
      if (this.f_93759_.isEmpty() || !this.f_93759_.get(this.f_93759_.size() - 1).equals(p_93784_)) {
         this.f_93759_.add(p_93784_);
      }

   }

   public void m_93810_() {
      this.f_93763_ = 0;
      this.f_93764_ = false;
   }

   public void m_93770_(double p_93771_) {
      this.f_93763_ = (int)((double)this.f_93763_ + p_93771_);
      int i = this.f_93761_.size();
      if (this.f_93763_ > i - this.m_93816_()) {
         this.f_93763_ = i - this.m_93816_();
      }

      if (this.f_93763_ <= 0) {
         this.f_93763_ = 0;
         this.f_93764_ = false;
      }

   }

   public boolean m_93772_(double p_93773_, double p_93774_) {
      if (this.m_93818_() && !this.f_93758_.f_91066_.f_92062_ && !this.m_93817_() && !this.f_93762_.isEmpty()) {
         double d0 = p_93773_ - 2.0D;
         double d1 = (double)this.f_93758_.m_91268_().m_85446_() - p_93774_ - 40.0D;
         if (d0 <= (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_()) && d1 < 0.0D && d1 > (double)Mth.m_14107_(-9.0D * this.m_93815_())) {
            this.m_93785_(this.f_93762_.remove());
            this.f_93765_ = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public Style m_93800_(double p_93801_, double p_93802_) {
      if (this.m_93818_() && !this.f_93758_.f_91066_.f_92062_ && !this.m_93817_()) {
         double d0 = p_93801_ - 2.0D;
         double d1 = (double)this.f_93758_.m_91268_().m_85446_() - p_93802_ - 40.0D;
         d0 = (double)Mth.m_14107_(d0 / this.m_93815_());
         d1 = (double)Mth.m_14107_(d1 / (this.m_93815_() * (this.f_93758_.f_91066_.f_92121_ + 1.0D)));
         if (!(d0 < 0.0D) && !(d1 < 0.0D)) {
            int i = Math.min(this.m_93816_(), this.f_93761_.size());
            if (d0 <= (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_()) && d1 < (double)(9 * i + i)) {
               int j = (int)(d1 / 9.0D + (double)this.f_93763_);
               if (j >= 0 && j < this.f_93761_.size()) {
                  GuiMessage<FormattedCharSequence> guimessage = this.f_93761_.get(j);
                  return this.f_93758_.f_91062_.m_92865_().m_92338_(guimessage.m_90793_(), (int)d0);
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean m_93818_() {
      return this.f_93758_.f_91080_ instanceof ChatScreen;
   }

   private void m_93803_(int p_93804_) {
      this.f_93761_.removeIf((p_93807_) -> {
         return p_93807_.m_90795_() == p_93804_;
      });
      this.f_93760_.removeIf((p_93779_) -> {
         return p_93779_.m_90795_() == p_93804_;
      });
   }

   public int m_93813_() {
      return m_93798_(this.f_93758_.f_91066_.f_92132_);
   }

   public int m_93814_() {
      return m_93811_((this.m_93818_() ? this.f_93758_.f_91066_.f_92134_ : this.f_93758_.f_91066_.f_92133_) / (this.f_93758_.f_91066_.f_92121_ + 1.0D));
   }

   public double m_93815_() {
      return this.f_93758_.f_91066_.f_92131_;
   }

   public static int m_93798_(double p_93799_) {
      int i = 320;
      int j = 40;
      return Mth.m_14107_(p_93799_ * 280.0D + 40.0D);
   }

   public static int m_93811_(double p_93812_) {
      int i = 180;
      int j = 20;
      return Mth.m_14107_(p_93812_ * 160.0D + 20.0D);
   }

   public int m_93816_() {
      return this.m_93814_() / 9;
   }

   private long m_93819_() {
      return (long)(this.f_93758_.f_91066_.f_92135_ * 1000.0D);
   }

   private void m_93820_() {
      if (!this.f_93762_.isEmpty()) {
         long i = System.currentTimeMillis();
         if (i - this.f_93765_ >= this.m_93819_()) {
            this.m_93785_(this.f_93762_.remove());
            this.f_93765_ = i;
         }

      }
   }

   public void m_93808_(Component p_93809_) {
      if (this.f_93758_.f_91066_.f_92135_ <= 0.0D) {
         this.m_93785_(p_93809_);
      } else {
         long i = System.currentTimeMillis();
         if (i - this.f_93765_ >= this.m_93819_()) {
            this.m_93785_(p_93809_);
            this.f_93765_ = i;
         } else {
            this.f_93762_.add(p_93809_);
         }
      }

   }
}