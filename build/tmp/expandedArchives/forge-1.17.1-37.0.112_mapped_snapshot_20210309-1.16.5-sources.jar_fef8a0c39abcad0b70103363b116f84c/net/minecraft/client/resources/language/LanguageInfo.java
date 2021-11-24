package net.minecraft.client.resources.language;

import com.mojang.bridge.game.Language;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LanguageInfo implements Language, Comparable<LanguageInfo> {
   private final String f_118943_;
   private final String f_118944_;
   private final String f_118945_;
   private final boolean f_118946_;

   public LanguageInfo(String p_118948_, String p_118949_, String p_118950_, boolean p_118951_) {
      this.f_118943_ = p_118948_;
      this.f_118944_ = p_118949_;
      this.f_118945_ = p_118950_;
      this.f_118946_ = p_118951_;
      String[] splitLangCode = f_118943_.split("_", 2);
      if (splitLangCode.length == 1) { // Vanilla has some languages without underscores
         this.javaLocale = new java.util.Locale(f_118943_);
      } else {
         this.javaLocale = new java.util.Locale(splitLangCode[0], splitLangCode[1]);
      }
   }

   public String getCode() {
      return this.f_118943_;
   }

   public String getName() {
      return this.f_118945_;
   }

   public String getRegion() {
      return this.f_118944_;
   }

   public boolean m_118952_() {
      return this.f_118946_;
   }

   public String toString() {
      return String.format("%s (%s)", this.f_118945_, this.f_118944_);
   }

   public boolean equals(Object p_118958_) {
      if (this == p_118958_) {
         return true;
      } else {
         return !(p_118958_ instanceof LanguageInfo) ? false : this.f_118943_.equals(((LanguageInfo)p_118958_).f_118943_);
      }
   }

   public int hashCode() {
      return this.f_118943_.hashCode();
   }

   public int compareTo(LanguageInfo p_118954_) {
      return this.f_118943_.compareTo(p_118954_.f_118943_);
   }

   // Forge: add access to Locale so modders can create correct string and number formatters
   private final java.util.Locale javaLocale;
   public java.util.Locale getJavaLocale() { return javaLocale; }
}
