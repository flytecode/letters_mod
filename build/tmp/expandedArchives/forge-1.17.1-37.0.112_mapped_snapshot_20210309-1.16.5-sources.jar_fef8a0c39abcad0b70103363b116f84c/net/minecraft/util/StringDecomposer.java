package net.minecraft.util;

import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public class StringDecomposer {
   private static final char f_144984_ = '\ufffd';
   private static final Optional<Object> f_14298_ = Optional.of(Unit.INSTANCE);

   private static boolean m_14332_(Style p_14333_, FormattedCharSink p_14334_, int p_14335_, char p_14336_) {
      return Character.isSurrogate(p_14336_) ? p_14334_.m_6411_(p_14335_, p_14333_, 65533) : p_14334_.m_6411_(p_14335_, p_14333_, p_14336_);
   }

   public static boolean m_14317_(String p_14318_, Style p_14319_, FormattedCharSink p_14320_) {
      int i = p_14318_.length();

      for(int j = 0; j < i; ++j) {
         char c0 = p_14318_.charAt(j);
         if (Character.isHighSurrogate(c0)) {
            if (j + 1 >= i) {
               if (!p_14320_.m_6411_(j, p_14319_, 65533)) {
                  return false;
               }
               break;
            }

            char c1 = p_14318_.charAt(j + 1);
            if (Character.isLowSurrogate(c1)) {
               if (!p_14320_.m_6411_(j, p_14319_, Character.toCodePoint(c0, c1))) {
                  return false;
               }

               ++j;
            } else if (!p_14320_.m_6411_(j, p_14319_, 65533)) {
               return false;
            }
         } else if (!m_14332_(p_14319_, p_14320_, j, c0)) {
            return false;
         }
      }

      return true;
   }

   public static boolean m_14337_(String p_14338_, Style p_14339_, FormattedCharSink p_14340_) {
      int i = p_14338_.length();

      for(int j = i - 1; j >= 0; --j) {
         char c0 = p_14338_.charAt(j);
         if (Character.isLowSurrogate(c0)) {
            if (j - 1 < 0) {
               if (!p_14340_.m_6411_(0, p_14339_, 65533)) {
                  return false;
               }
               break;
            }

            char c1 = p_14338_.charAt(j - 1);
            if (Character.isHighSurrogate(c1)) {
               --j;
               if (!p_14340_.m_6411_(j, p_14339_, Character.toCodePoint(c1, c0))) {
                  return false;
               }
            } else if (!p_14340_.m_6411_(j, p_14339_, 65533)) {
               return false;
            }
         } else if (!m_14332_(p_14339_, p_14340_, j, c0)) {
            return false;
         }
      }

      return true;
   }

   public static boolean m_14346_(String p_14347_, Style p_14348_, FormattedCharSink p_14349_) {
      return m_14306_(p_14347_, 0, p_14348_, p_14349_);
   }

   public static boolean m_14306_(String p_14307_, int p_14308_, Style p_14309_, FormattedCharSink p_14310_) {
      return m_14311_(p_14307_, p_14308_, p_14309_, p_14309_, p_14310_);
   }

   public static boolean m_14311_(String p_14312_, int p_14313_, Style p_14314_, Style p_14315_, FormattedCharSink p_14316_) {
      int i = p_14312_.length();
      Style style = p_14314_;

      for(int j = p_14313_; j < i; ++j) {
         char c0 = p_14312_.charAt(j);
         if (c0 == 167) {
            if (j + 1 >= i) {
               break;
            }

            char c1 = p_14312_.charAt(j + 1);
            ChatFormatting chatformatting = ChatFormatting.m_126645_(c1);
            if (chatformatting != null) {
               style = chatformatting == ChatFormatting.RESET ? p_14315_ : style.m_131164_(chatformatting);
            }

            ++j;
         } else if (Character.isHighSurrogate(c0)) {
            if (j + 1 >= i) {
               if (!p_14316_.m_6411_(j, style, 65533)) {
                  return false;
               }
               break;
            }

            char c2 = p_14312_.charAt(j + 1);
            if (Character.isLowSurrogate(c2)) {
               if (!p_14316_.m_6411_(j, style, Character.toCodePoint(c0, c2))) {
                  return false;
               }

               ++j;
            } else if (!p_14316_.m_6411_(j, style, 65533)) {
               return false;
            }
         } else if (!m_14332_(style, p_14316_, j, c0)) {
            return false;
         }
      }

      return true;
   }

   public static boolean m_14328_(FormattedText p_14329_, Style p_14330_, FormattedCharSink p_14331_) {
      return !p_14329_.m_7451_((p_14302_, p_14303_) -> {
         return m_14306_(p_14303_, 0, p_14302_, p_14331_) ? Optional.empty() : f_14298_;
      }, p_14330_).isPresent();
   }

   public static String m_14304_(String p_14305_) {
      StringBuilder stringbuilder = new StringBuilder();
      m_14317_(p_14305_, Style.f_131099_, (p_14343_, p_14344_, p_14345_) -> {
         stringbuilder.appendCodePoint(p_14345_);
         return true;
      });
      return stringbuilder.toString();
   }

   public static String m_14326_(FormattedText p_14327_) {
      StringBuilder stringbuilder = new StringBuilder();
      m_14328_(p_14327_, Style.f_131099_, (p_14323_, p_14324_, p_14325_) -> {
         stringbuilder.appendCodePoint(p_14325_);
         return true;
      });
      return stringbuilder.toString();
   }
}