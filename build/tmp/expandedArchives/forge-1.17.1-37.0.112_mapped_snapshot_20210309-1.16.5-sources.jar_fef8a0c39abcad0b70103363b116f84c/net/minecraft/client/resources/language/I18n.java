package net.minecraft.client.resources.language;

import java.util.IllegalFormatException;
import net.minecraft.locale.Language;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class I18n {
   private static volatile Language f_118934_ = Language.m_128107_();

   private I18n() {
   }

   static void m_118941_(Language p_118942_) {
      f_118934_ = p_118942_;
      net.minecraftforge.fmllegacy.ForgeI18n.loadLanguageData(p_118942_.getLanguageData());
   }

   public static String m_118938_(String p_118939_, Object... p_118940_) {
      String s = f_118934_.m_6834_(p_118939_);

      try {
         return String.format(s, p_118940_);
      } catch (IllegalFormatException illegalformatexception) {
         return "Format error: " + s;
      }
   }

   public static boolean m_118936_(String p_118937_) {
      return f_118934_.m_6722_(p_118937_);
   }
}
