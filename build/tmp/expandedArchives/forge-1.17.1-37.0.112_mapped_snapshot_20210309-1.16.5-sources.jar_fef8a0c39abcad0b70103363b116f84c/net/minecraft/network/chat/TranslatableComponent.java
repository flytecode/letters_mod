package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.locale.Language;
import net.minecraft.world.entity.Entity;

public class TranslatableComponent extends BaseComponent implements ContextAwareComponent {
   private static final Object[] f_131295_ = new Object[0];
   private static final FormattedText f_131296_ = FormattedText.m_130775_("%");
   private static final FormattedText f_131297_ = FormattedText.m_130775_("null");
   private final String f_131298_;
   private final Object[] f_131299_;
   @Nullable
   private Language f_131300_;
   private final List<FormattedText> f_131301_ = Lists.newArrayList();
   private static final Pattern f_131302_ = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TranslatableComponent(String p_131305_) {
      this.f_131298_ = p_131305_;
      this.f_131299_ = f_131295_;
   }

   public TranslatableComponent(String p_131307_, Object... p_131308_) {
      this.f_131298_ = p_131307_;
      this.f_131299_ = p_131308_;
   }

   private void m_131330_() {
      Language language = Language.m_128107_();
      if (language != this.f_131300_) {
         this.f_131300_ = language;
         this.f_131301_.clear();
         String s = language.m_6834_(this.f_131298_);

         try {
            this.m_131321_(s);
         } catch (TranslatableFormatException translatableformatexception) {
            this.f_131301_.clear();
            this.f_131301_.add(FormattedText.m_130775_(s));
         }

      }
   }

   private void m_131321_(String p_131322_) {
      Matcher matcher = f_131302_.matcher(p_131322_);

      try {
         int i = 0;

         int j;
         int l;
         for(j = 0; matcher.find(j); j = l) {
            int k = matcher.start();
            l = matcher.end();
            if (k > j) {
               String s = p_131322_.substring(j, k);
               if (s.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               this.f_131301_.add(FormattedText.m_130775_(s));
            }

            String s4 = matcher.group(2);
            String s1 = p_131322_.substring(k, l);
            if ("%".equals(s4) && "%%".equals(s1)) {
               this.f_131301_.add(f_131296_);
            } else {
               if (!"s".equals(s4)) {
                  throw new TranslatableFormatException(this, "Unsupported format: '" + s1 + "'");
               }

               String s2 = matcher.group(1);
               int i1 = s2 != null ? Integer.parseInt(s2) - 1 : i++;
               if (i1 < this.f_131299_.length) {
                  this.f_131301_.add(this.m_131313_(i1));
               }
            }
         }

         if (j == 0) {
            // if we failed to match above, lets try the messageformat handler instead.
            j = net.minecraftforge.fmllegacy.TextComponentMessageFormatHandler.handle(this, this.f_131301_, this.f_131299_, p_131322_);
         }
         if (j < p_131322_.length()) {
            String s3 = p_131322_.substring(j);
            if (s3.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            this.f_131301_.add(FormattedText.m_130775_(s3));
         }

      } catch (IllegalArgumentException illegalargumentexception) {
         throw new TranslatableFormatException(this, illegalargumentexception);
      }
   }

   private FormattedText m_131313_(int p_131314_) {
      if (p_131314_ >= this.f_131299_.length) {
         throw new TranslatableFormatException(this, p_131314_);
      } else {
         Object object = this.f_131299_[p_131314_];
         if (object instanceof Component) {
            return (Component)object;
         } else {
            return object == null ? f_131297_ : FormattedText.m_130775_(object.toString());
         }
      }
   }

   public TranslatableComponent m_6879_() {
      return new TranslatableComponent(this.f_131298_, this.f_131299_);
   }

   public <T> Optional<T> m_7452_(FormattedText.StyledContentConsumer<T> p_131318_, Style p_131319_) {
      this.m_131330_();

      for(FormattedText formattedtext : this.f_131301_) {
         Optional<T> optional = formattedtext.m_7451_(p_131318_, p_131319_);
         if (optional.isPresent()) {
            return optional;
         }
      }

      return Optional.empty();
   }

   public <T> Optional<T> m_5655_(FormattedText.ContentConsumer<T> p_131316_) {
      this.m_131330_();

      for(FormattedText formattedtext : this.f_131301_) {
         Optional<T> optional = formattedtext.m_5651_(p_131316_);
         if (optional.isPresent()) {
            return optional;
         }
      }

      return Optional.empty();
   }

   public MutableComponent m_5638_(@Nullable CommandSourceStack p_131310_, @Nullable Entity p_131311_, int p_131312_) throws CommandSyntaxException {
      Object[] aobject = new Object[this.f_131299_.length];

      for(int i = 0; i < aobject.length; ++i) {
         Object object = this.f_131299_[i];
         if (object instanceof Component) {
            aobject[i] = ComponentUtils.m_130731_(p_131310_, (Component)object, p_131311_, p_131312_);
         } else {
            aobject[i] = object;
         }
      }

      return new TranslatableComponent(this.f_131298_, aobject);
   }

   public boolean equals(Object p_131324_) {
      if (this == p_131324_) {
         return true;
      } else if (!(p_131324_ instanceof TranslatableComponent)) {
         return false;
      } else {
         TranslatableComponent translatablecomponent = (TranslatableComponent)p_131324_;
         return Arrays.equals(this.f_131299_, translatablecomponent.f_131299_) && this.f_131298_.equals(translatablecomponent.f_131298_) && super.equals(p_131324_);
      }
   }

   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.f_131298_.hashCode();
      return 31 * i + Arrays.hashCode(this.f_131299_);
   }

   public String toString() {
      return "TranslatableComponent{key='" + this.f_131298_ + "', args=" + Arrays.toString(this.f_131299_) + ", siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
   }

   public String m_131328_() {
      return this.f_131298_;
   }

   public Object[] m_131329_() {
      return this.f_131299_;
   }
}
