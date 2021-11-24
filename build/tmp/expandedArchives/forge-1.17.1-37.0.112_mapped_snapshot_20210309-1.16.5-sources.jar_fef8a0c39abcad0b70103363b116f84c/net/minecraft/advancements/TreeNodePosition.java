package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class TreeNodePosition {
   private final Advancement f_16554_;
   private final TreeNodePosition f_16555_;
   private final TreeNodePosition f_16556_;
   private final int f_16557_;
   private final List<TreeNodePosition> f_16558_ = Lists.newArrayList();
   private TreeNodePosition f_16559_;
   private TreeNodePosition f_16560_;
   private int f_16561_;
   private float f_16562_;
   private float f_16563_;
   private float f_16564_;
   private float f_16565_;

   public TreeNodePosition(Advancement p_16567_, @Nullable TreeNodePosition p_16568_, @Nullable TreeNodePosition p_16569_, int p_16570_, int p_16571_) {
      if (p_16567_.m_138320_() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.f_16554_ = p_16567_;
         this.f_16555_ = p_16568_;
         this.f_16556_ = p_16569_;
         this.f_16557_ = p_16570_;
         this.f_16559_ = this;
         this.f_16561_ = p_16571_;
         this.f_16562_ = -1.0F;
         TreeNodePosition treenodeposition = null;

         for(Advancement advancement : p_16567_.m_138322_()) {
            treenodeposition = this.m_16589_(advancement, treenodeposition);
         }

      }
   }

   @Nullable
   private TreeNodePosition m_16589_(Advancement p_16590_, @Nullable TreeNodePosition p_16591_) {
      if (p_16590_.m_138320_() != null) {
         p_16591_ = new TreeNodePosition(p_16590_, this, p_16591_, this.f_16558_.size() + 1, this.f_16561_ + 1);
         this.f_16558_.add(p_16591_);
      } else {
         for(Advancement advancement : p_16590_.m_138322_()) {
            p_16591_ = this.m_16589_(advancement, p_16591_);
         }
      }

      return p_16591_;
   }

   private void m_16572_() {
      if (this.f_16558_.isEmpty()) {
         if (this.f_16556_ != null) {
            this.f_16562_ = this.f_16556_.f_16562_ + 1.0F;
         } else {
            this.f_16562_ = 0.0F;
         }

      } else {
         TreeNodePosition treenodeposition = null;

         for(TreeNodePosition treenodeposition1 : this.f_16558_) {
            treenodeposition1.m_16572_();
            treenodeposition = treenodeposition1.m_16579_(treenodeposition == null ? treenodeposition1 : treenodeposition);
         }

         this.m_16592_();
         float f = ((this.f_16558_.get(0)).f_16562_ + (this.f_16558_.get(this.f_16558_.size() - 1)).f_16562_) / 2.0F;
         if (this.f_16556_ != null) {
            this.f_16562_ = this.f_16556_.f_16562_ + 1.0F;
            this.f_16563_ = this.f_16562_ - f;
         } else {
            this.f_16562_ = f;
         }

      }
   }

   private float m_16575_(float p_16576_, int p_16577_, float p_16578_) {
      this.f_16562_ += p_16576_;
      this.f_16561_ = p_16577_;
      if (this.f_16562_ < p_16578_) {
         p_16578_ = this.f_16562_;
      }

      for(TreeNodePosition treenodeposition : this.f_16558_) {
         p_16578_ = treenodeposition.m_16575_(p_16576_ + this.f_16563_, p_16577_ + 1, p_16578_);
      }

      return p_16578_;
   }

   private void m_16573_(float p_16574_) {
      this.f_16562_ += p_16574_;

      for(TreeNodePosition treenodeposition : this.f_16558_) {
         treenodeposition.m_16573_(p_16574_);
      }

   }

   private void m_16592_() {
      float f = 0.0F;
      float f1 = 0.0F;

      for(int i = this.f_16558_.size() - 1; i >= 0; --i) {
         TreeNodePosition treenodeposition = this.f_16558_.get(i);
         treenodeposition.f_16562_ += f;
         treenodeposition.f_16563_ += f;
         f1 += treenodeposition.f_16564_;
         f += treenodeposition.f_16565_ + f1;
      }

   }

   @Nullable
   private TreeNodePosition m_16593_() {
      if (this.f_16560_ != null) {
         return this.f_16560_;
      } else {
         return !this.f_16558_.isEmpty() ? this.f_16558_.get(0) : null;
      }
   }

   @Nullable
   private TreeNodePosition m_16594_() {
      if (this.f_16560_ != null) {
         return this.f_16560_;
      } else {
         return !this.f_16558_.isEmpty() ? this.f_16558_.get(this.f_16558_.size() - 1) : null;
      }
   }

   private TreeNodePosition m_16579_(TreeNodePosition p_16580_) {
      if (this.f_16556_ == null) {
         return p_16580_;
      } else {
         TreeNodePosition treenodeposition = this;
         TreeNodePosition treenodeposition1 = this;
         TreeNodePosition treenodeposition2 = this.f_16556_;
         TreeNodePosition treenodeposition3 = this.f_16555_.f_16558_.get(0);
         float f = this.f_16563_;
         float f1 = this.f_16563_;
         float f2 = treenodeposition2.f_16563_;

         float f3;
         for(f3 = treenodeposition3.f_16563_; treenodeposition2.m_16594_() != null && treenodeposition.m_16593_() != null; f1 += treenodeposition1.f_16563_) {
            treenodeposition2 = treenodeposition2.m_16594_();
            treenodeposition = treenodeposition.m_16593_();
            treenodeposition3 = treenodeposition3.m_16593_();
            treenodeposition1 = treenodeposition1.m_16594_();
            treenodeposition1.f_16559_ = this;
            float f4 = treenodeposition2.f_16562_ + f2 - (treenodeposition.f_16562_ + f) + 1.0F;
            if (f4 > 0.0F) {
               treenodeposition2.m_16584_(this, p_16580_).m_16581_(this, f4);
               f += f4;
               f1 += f4;
            }

            f2 += treenodeposition2.f_16563_;
            f += treenodeposition.f_16563_;
            f3 += treenodeposition3.f_16563_;
         }

         if (treenodeposition2.m_16594_() != null && treenodeposition1.m_16594_() == null) {
            treenodeposition1.f_16560_ = treenodeposition2.m_16594_();
            treenodeposition1.f_16563_ += f2 - f1;
         } else {
            if (treenodeposition.m_16593_() != null && treenodeposition3.m_16593_() == null) {
               treenodeposition3.f_16560_ = treenodeposition.m_16593_();
               treenodeposition3.f_16563_ += f - f3;
            }

            p_16580_ = this;
         }

         return p_16580_;
      }
   }

   private void m_16581_(TreeNodePosition p_16582_, float p_16583_) {
      float f = (float)(p_16582_.f_16557_ - this.f_16557_);
      if (f != 0.0F) {
         p_16582_.f_16564_ -= p_16583_ / f;
         this.f_16564_ += p_16583_ / f;
      }

      p_16582_.f_16565_ += p_16583_;
      p_16582_.f_16562_ += p_16583_;
      p_16582_.f_16563_ += p_16583_;
   }

   private TreeNodePosition m_16584_(TreeNodePosition p_16585_, TreeNodePosition p_16586_) {
      return this.f_16559_ != null && p_16585_.f_16555_.f_16558_.contains(this.f_16559_) ? this.f_16559_ : p_16586_;
   }

   private void m_16595_() {
      if (this.f_16554_.m_138320_() != null) {
         this.f_16554_.m_138320_().m_14978_((float)this.f_16561_, this.f_16562_);
      }

      if (!this.f_16558_.isEmpty()) {
         for(TreeNodePosition treenodeposition : this.f_16558_) {
            treenodeposition.m_16595_();
         }
      }

   }

   public static void m_16587_(Advancement p_16588_) {
      if (p_16588_.m_138320_() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         TreeNodePosition treenodeposition = new TreeNodePosition(p_16588_, (TreeNodePosition)null, (TreeNodePosition)null, 1, 0);
         treenodeposition.m_16572_();
         float f = treenodeposition.m_16575_(0.0F, 0, treenodeposition.f_16562_);
         if (f < 0.0F) {
            treenodeposition.m_16573_(-f);
         }

         treenodeposition.m_16595_();
      }
   }
}