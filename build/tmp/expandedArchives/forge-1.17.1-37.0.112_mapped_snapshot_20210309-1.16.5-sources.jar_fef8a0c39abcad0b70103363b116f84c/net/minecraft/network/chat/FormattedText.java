package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Unit;

public interface FormattedText {
   Optional<Unit> f_130759_ = Optional.of(Unit.INSTANCE);
   FormattedText f_130760_ = new FormattedText() {
      public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130779_) {
         return Optional.empty();
      }

      public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130781_, Style p_130782_) {
         return Optional.empty();
      }
   };

   <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130770_);

   <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130771_, Style p_130772_);

   static FormattedText m_130775_(final String p_130776_) {
      return new FormattedText() {
         public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130787_) {
            return p_130787_.m_130809_(p_130776_);
         }

         public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130789_, Style p_130790_) {
            return p_130789_.m_7164_(p_130790_, p_130776_);
         }
      };
   }

   static FormattedText m_130762_(final String p_130763_, final Style p_130764_) {
      return new FormattedText() {
         public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130797_) {
            return p_130797_.m_130809_(p_130763_);
         }

         public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130799_, Style p_130800_) {
            return p_130799_.m_7164_(p_130764_.m_131146_(p_130800_), p_130763_);
         }
      };
   }

   static FormattedText m_130773_(FormattedText... p_130774_) {
      return m_130768_(ImmutableList.copyOf(p_130774_));
   }

   static FormattedText m_130768_(final List<? extends FormattedText> p_130769_) {
      return new FormattedText() {
         public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_130805_) {
            for(FormattedText formattedtext : p_130769_) {
               Optional<T> optional = formattedtext.m_5651_(p_130805_);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }

         public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_130807_, Style p_130808_) {
            for(FormattedText formattedtext : p_130769_) {
               Optional<T> optional = formattedtext.m_7451_(p_130807_, p_130808_);
               if (optional.isPresent()) {
                  return optional;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder stringbuilder = new StringBuilder();
      this.m_5651_((p_130767_) -> {
         stringbuilder.append(p_130767_);
         return Optional.empty();
      });
      return stringbuilder.toString();
   }

   public interface ContentConsumer<T> {
      Optional<T> m_130809_(String p_130810_);
   }

   public interface StyledContentConsumer<T> {
      Optional<T> m_7164_(Style p_130811_, String p_130812_);
   }
}