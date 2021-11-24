package net.minecraft.network.chat;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class KeybindComponent extends BaseComponent {
   private static Function<String, Supplier<Component>> f_130913_ = (p_130928_) -> {
      return () -> {
         return new TextComponent(p_130928_);
      };
   };
   private final String f_130914_;
   private Supplier<Component> f_130915_;

   public KeybindComponent(String p_130918_) {
      this.f_130914_ = p_130918_;
   }

   public static void m_130919_(Function<String, Supplier<Component>> p_130920_) {
      f_130913_ = p_130920_;
   }

   private Component m_130936_() {
      if (this.f_130915_ == null) {
         this.f_130915_ = f_130913_.apply(this.f_130914_);
      }

      return this.f_130915_.get();
   }

   public <T> Optional<T> m_5655_(FormattedText.ContentConsumer<T> p_130922_) {
      return this.m_130936_().m_5651_(p_130922_);
   }

   public <T> Optional<T> m_7452_(FormattedText.StyledContentConsumer<T> p_130924_, Style p_130925_) {
      return this.m_130936_().m_7451_(p_130924_, p_130925_);
   }

   public KeybindComponent m_6879_() {
      return new KeybindComponent(this.f_130914_);
   }

   public boolean equals(Object p_130932_) {
      if (this == p_130932_) {
         return true;
      } else if (!(p_130932_ instanceof KeybindComponent)) {
         return false;
      } else {
         KeybindComponent keybindcomponent = (KeybindComponent)p_130932_;
         return this.f_130914_.equals(keybindcomponent.f_130914_) && super.equals(p_130932_);
      }
   }

   public String toString() {
      return "KeybindComponent{keybind='" + this.f_130914_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
   }

   public String m_130935_() {
      return this.f_130914_;
   }
}