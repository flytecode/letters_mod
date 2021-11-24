package net.minecraft.network.chat;

public class TextComponent extends BaseComponent {
   public static final Component f_131282_ = new TextComponent("");
   private final String f_131283_;

   public TextComponent(String p_131286_) {
      this.f_131283_ = p_131286_;
   }

   public String m_131292_() {
      return this.f_131283_;
   }

   public String m_6111_() {
      return this.f_131283_;
   }

   public TextComponent m_6879_() {
      return new TextComponent(this.f_131283_);
   }

   public boolean equals(Object p_131290_) {
      if (this == p_131290_) {
         return true;
      } else if (!(p_131290_ instanceof TextComponent)) {
         return false;
      } else {
         TextComponent textcomponent = (TextComponent)p_131290_;
         return this.f_131283_.equals(textcomponent.m_131292_()) && super.equals(p_131290_);
      }
   }

   public String toString() {
      return "TextComponent{text='" + this.f_131283_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
   }
}