package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.locale.Language;
import net.minecraft.util.FormattedCharSequence;

public abstract class BaseComponent implements MutableComponent {
   protected final List<Component> f_130578_ = Lists.newArrayList();
   private FormattedCharSequence f_130579_ = FormattedCharSequence.f_13691_;
   @Nullable
   private Language f_130580_;
   private Style f_130581_ = Style.f_131099_;

   public MutableComponent m_7220_(Component p_130585_) {
      this.f_130578_.add(p_130585_);
      return this;
   }

   public String m_6111_() {
      return "";
   }

   public List<Component> m_7360_() {
      return this.f_130578_;
   }

   public MutableComponent m_6270_(Style p_130587_) {
      this.f_130581_ = p_130587_;
      return this;
   }

   public Style m_7383_() {
      return this.f_130581_;
   }

   public abstract BaseComponent m_6879_();

   public final MutableComponent m_6881_() {
      BaseComponent basecomponent = this.m_6879_();
      basecomponent.f_130578_.addAll(this.f_130578_);
      basecomponent.m_6270_(this.f_130581_);
      return basecomponent;
   }

   public FormattedCharSequence m_7532_() {
      Language language = Language.m_128107_();
      if (this.f_130580_ != language) {
         this.f_130579_ = language.m_5536_(this);
         this.f_130580_ = language;
      }

      return this.f_130579_;
   }

   public boolean equals(Object p_130593_) {
      if (this == p_130593_) {
         return true;
      } else if (!(p_130593_ instanceof BaseComponent)) {
         return false;
      } else {
         BaseComponent basecomponent = (BaseComponent)p_130593_;
         return this.f_130578_.equals(basecomponent.f_130578_) && Objects.equals(this.m_7383_(), basecomponent.m_7383_());
      }
   }

   public int hashCode() {
      return Objects.hash(this.m_7383_(), this.f_130578_);
   }

   public String toString() {
      return "BaseComponent{style=" + this.f_130581_ + ", siblings=" + this.f_130578_ + "}";
   }
}