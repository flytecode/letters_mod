package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

@OnlyIn(Dist.CLIENT)
public class StringSplitter {
   final StringSplitter.WidthProvider f_92333_;

   public StringSplitter(StringSplitter.WidthProvider p_92335_) {
      this.f_92333_ = p_92335_;
   }

   public float m_92353_(@Nullable String p_92354_) {
      if (p_92354_ == null) {
         return 0.0F;
      } else {
         MutableFloat mutablefloat = new MutableFloat();
         StringDecomposer.m_14346_(p_92354_, Style.f_131099_, (p_92429_, p_92430_, p_92431_) -> {
            mutablefloat.add(this.f_92333_.m_92515_(p_92431_, p_92430_));
            return true;
         });
         return mutablefloat.floatValue();
      }
   }

   public float m_92384_(FormattedText p_92385_) {
      MutableFloat mutablefloat = new MutableFloat();
      StringDecomposer.m_14328_(p_92385_, Style.f_131099_, (p_92420_, p_92421_, p_92422_) -> {
         mutablefloat.add(this.f_92333_.m_92515_(p_92422_, p_92421_));
         return true;
      });
      return mutablefloat.floatValue();
   }

   public float m_92336_(FormattedCharSequence p_92337_) {
      MutableFloat mutablefloat = new MutableFloat();
      p_92337_.m_13731_((p_92400_, p_92401_, p_92402_) -> {
         mutablefloat.add(this.f_92333_.m_92515_(p_92402_, p_92401_));
         return true;
      });
      return mutablefloat.floatValue();
   }

   public int m_92360_(String p_92361_, int p_92362_, Style p_92363_) {
      StringSplitter.WidthLimitedCharSink stringsplitter$widthlimitedcharsink = new StringSplitter.WidthLimitedCharSink((float)p_92362_);
      StringDecomposer.m_14317_(p_92361_, p_92363_, stringsplitter$widthlimitedcharsink);
      return stringsplitter$widthlimitedcharsink.m_92509_();
   }

   public String m_92410_(String p_92411_, int p_92412_, Style p_92413_) {
      return p_92411_.substring(0, this.m_92360_(p_92411_, p_92412_, p_92413_));
   }

   public String m_92423_(String p_92424_, int p_92425_, Style p_92426_) {
      MutableFloat mutablefloat = new MutableFloat();
      MutableInt mutableint = new MutableInt(p_92424_.length());
      StringDecomposer.m_14337_(p_92424_, p_92426_, (p_92407_, p_92408_, p_92409_) -> {
         float f = mutablefloat.addAndGet(this.f_92333_.m_92515_(p_92409_, p_92408_));
         if (f > (float)p_92425_) {
            return false;
         } else {
            mutableint.setValue(p_92407_);
            return true;
         }
      });
      return p_92424_.substring(mutableint.intValue());
   }

   public int m_168626_(String p_168627_, int p_168628_, Style p_168629_) {
      StringSplitter.WidthLimitedCharSink stringsplitter$widthlimitedcharsink = new StringSplitter.WidthLimitedCharSink((float)p_168628_);
      StringDecomposer.m_14346_(p_168627_, p_168629_, stringsplitter$widthlimitedcharsink);
      return stringsplitter$widthlimitedcharsink.m_92509_();
   }

   @Nullable
   public Style m_92386_(FormattedText p_92387_, int p_92388_) {
      StringSplitter.WidthLimitedCharSink stringsplitter$widthlimitedcharsink = new StringSplitter.WidthLimitedCharSink((float)p_92388_);
      return p_92387_.m_7451_((p_92343_, p_92344_) -> {
         return StringDecomposer.m_14346_(p_92344_, p_92343_, stringsplitter$widthlimitedcharsink) ? Optional.empty() : Optional.of(p_92343_);
      }, Style.f_131099_).orElse((Style)null);
   }

   @Nullable
   public Style m_92338_(FormattedCharSequence p_92339_, int p_92340_) {
      StringSplitter.WidthLimitedCharSink stringsplitter$widthlimitedcharsink = new StringSplitter.WidthLimitedCharSink((float)p_92340_);
      MutableObject<Style> mutableobject = new MutableObject<>();
      p_92339_.m_13731_((p_92348_, p_92349_, p_92350_) -> {
         if (!stringsplitter$widthlimitedcharsink.m_6411_(p_92348_, p_92349_, p_92350_)) {
            mutableobject.setValue(p_92349_);
            return false;
         } else {
            return true;
         }
      });
      return mutableobject.getValue();
   }

   public String m_168630_(String p_168631_, int p_168632_, Style p_168633_) {
      return p_168631_.substring(0, this.m_168626_(p_168631_, p_168632_, p_168633_));
   }

   public FormattedText m_92389_(FormattedText p_92390_, int p_92391_, Style p_92392_) {
      final StringSplitter.WidthLimitedCharSink stringsplitter$widthlimitedcharsink = new StringSplitter.WidthLimitedCharSink((float)p_92391_);
      return p_92390_.m_7451_(new FormattedText.StyledContentConsumer<FormattedText>() {
         private final ComponentCollector f_92438_ = new ComponentCollector();

         public Optional<FormattedText> m_7164_(Style p_92443_, String p_92444_) {
            stringsplitter$widthlimitedcharsink.m_92514_();
            if (!StringDecomposer.m_14346_(p_92444_, p_92443_, stringsplitter$widthlimitedcharsink)) {
               String s = p_92444_.substring(0, stringsplitter$widthlimitedcharsink.m_92509_());
               if (!s.isEmpty()) {
                  this.f_92438_.m_90675_(FormattedText.m_130762_(s, p_92443_));
               }

               return Optional.of(this.f_92438_.m_90677_());
            } else {
               if (!p_92444_.isEmpty()) {
                  this.f_92438_.m_90675_(FormattedText.m_130762_(p_92444_, p_92443_));
               }

               return Optional.empty();
            }
         }
      }, p_92392_).orElse(p_92390_);
   }

   public int m_168634_(String p_168635_, int p_168636_, Style p_168637_) {
      StringSplitter.LineBreakFinder stringsplitter$linebreakfinder = new StringSplitter.LineBreakFinder((float)p_168636_);
      StringDecomposer.m_14346_(p_168635_, p_168637_, stringsplitter$linebreakfinder);
      return stringsplitter$linebreakfinder.m_92473_();
   }

   public static int m_92355_(String p_92356_, int p_92357_, int p_92358_, boolean p_92359_) {
      int i = p_92358_;
      boolean flag = p_92357_ < 0;
      int j = Math.abs(p_92357_);

      for(int k = 0; k < j; ++k) {
         if (flag) {
            while(p_92359_ && i > 0 && (p_92356_.charAt(i - 1) == ' ' || p_92356_.charAt(i - 1) == '\n')) {
               --i;
            }

            while(i > 0 && p_92356_.charAt(i - 1) != ' ' && p_92356_.charAt(i - 1) != '\n') {
               --i;
            }
         } else {
            int l = p_92356_.length();
            int i1 = p_92356_.indexOf(32, i);
            int j1 = p_92356_.indexOf(10, i);
            if (i1 == -1 && j1 == -1) {
               i = -1;
            } else if (i1 != -1 && j1 != -1) {
               i = Math.min(i1, j1);
            } else if (i1 != -1) {
               i = i1;
            } else {
               i = j1;
            }

            if (i == -1) {
               i = l;
            } else {
               while(p_92359_ && i < l && (p_92356_.charAt(i) == ' ' || p_92356_.charAt(i) == '\n')) {
                  ++i;
               }
            }
         }
      }

      return i;
   }

   public void m_92364_(String p_92365_, int p_92366_, Style p_92367_, boolean p_92368_, StringSplitter.LinePosConsumer p_92369_) {
      int i = 0;
      int j = p_92365_.length();

      StringSplitter.LineBreakFinder stringsplitter$linebreakfinder;
      for(Style style = p_92367_; i < j; style = stringsplitter$linebreakfinder.m_92483_()) {
         stringsplitter$linebreakfinder = new StringSplitter.LineBreakFinder((float)p_92366_);
         boolean flag = StringDecomposer.m_14311_(p_92365_, i, style, p_92367_, stringsplitter$linebreakfinder);
         if (flag) {
            p_92369_.m_92499_(style, i, j);
            break;
         }

         int k = stringsplitter$linebreakfinder.m_92473_();
         char c0 = p_92365_.charAt(k);
         int l = c0 != '\n' && c0 != ' ' ? k : k + 1;
         p_92369_.m_92499_(style, i, p_92368_ ? l : k);
         i = l;
      }

   }

   public List<FormattedText> m_92432_(String p_92433_, int p_92434_, Style p_92435_) {
      List<FormattedText> list = Lists.newArrayList();
      this.m_92364_(p_92433_, p_92434_, p_92435_, false, (p_92373_, p_92374_, p_92375_) -> {
         list.add(FormattedText.m_130762_(p_92433_.substring(p_92374_, p_92375_), p_92373_));
      });
      return list;
   }

   public List<FormattedText> m_92414_(FormattedText p_92415_, int p_92416_, Style p_92417_) {
      List<FormattedText> list = Lists.newArrayList();
      this.m_92393_(p_92415_, p_92416_, p_92417_, (p_92378_, p_92379_) -> {
         list.add(p_92378_);
      });
      return list;
   }

   public List<FormattedText> m_168621_(FormattedText p_168622_, int p_168623_, Style p_168624_, FormattedText p_168625_) {
      List<FormattedText> list = Lists.newArrayList();
      this.m_92393_(p_168622_, p_168623_, p_168624_, (p_168619_, p_168620_) -> {
         list.add(p_168620_ ? FormattedText.m_130773_(p_168625_, p_168619_) : p_168619_);
      });
      return list;
   }

   public void m_92393_(FormattedText p_92394_, int p_92395_, Style p_92396_, BiConsumer<FormattedText, Boolean> p_92397_) {
      List<StringSplitter.LineComponent> list = Lists.newArrayList();
      p_92394_.m_7451_((p_92382_, p_92383_) -> {
         if (!p_92383_.isEmpty()) {
            list.add(new StringSplitter.LineComponent(p_92383_, p_92382_));
         }

         return Optional.empty();
      }, p_92396_);
      StringSplitter.FlatComponents stringsplitter$flatcomponents = new StringSplitter.FlatComponents(list);
      boolean flag = true;
      boolean flag1 = false;
      boolean flag2 = false;

      while(flag) {
         flag = false;
         StringSplitter.LineBreakFinder stringsplitter$linebreakfinder = new StringSplitter.LineBreakFinder((float)p_92395_);

         for(StringSplitter.LineComponent stringsplitter$linecomponent : stringsplitter$flatcomponents.f_92445_) {
            boolean flag3 = StringDecomposer.m_14311_(stringsplitter$linecomponent.f_92485_, 0, stringsplitter$linecomponent.f_92486_, p_92396_, stringsplitter$linebreakfinder);
            if (!flag3) {
               int i = stringsplitter$linebreakfinder.m_92473_();
               Style style = stringsplitter$linebreakfinder.m_92483_();
               char c0 = stringsplitter$flatcomponents.m_92450_(i);
               boolean flag4 = c0 == '\n';
               boolean flag5 = flag4 || c0 == ' ';
               flag1 = flag4;
               FormattedText formattedtext = stringsplitter$flatcomponents.m_92452_(i, flag5 ? 1 : 0, style);
               p_92397_.accept(formattedtext, flag2);
               flag2 = !flag4;
               flag = true;
               break;
            }

            stringsplitter$linebreakfinder.m_92474_(stringsplitter$linecomponent.f_92485_.length());
         }
      }

      FormattedText formattedtext1 = stringsplitter$flatcomponents.m_92449_();
      if (formattedtext1 != null) {
         p_92397_.accept(formattedtext1, flag2);
      } else if (flag1) {
         p_92397_.accept(FormattedText.f_130760_, false);
      }

   }

   @OnlyIn(Dist.CLIENT)
   static class FlatComponents {
      final List<StringSplitter.LineComponent> f_92445_;
      private String f_92446_;

      public FlatComponents(List<StringSplitter.LineComponent> p_92448_) {
         this.f_92445_ = p_92448_;
         this.f_92446_ = p_92448_.stream().map((p_92459_) -> {
            return p_92459_.f_92485_;
         }).collect(Collectors.joining());
      }

      public char m_92450_(int p_92451_) {
         return this.f_92446_.charAt(p_92451_);
      }

      public FormattedText m_92452_(int p_92453_, int p_92454_, Style p_92455_) {
         ComponentCollector componentcollector = new ComponentCollector();
         ListIterator<StringSplitter.LineComponent> listiterator = this.f_92445_.listIterator();
         int i = p_92453_;
         boolean flag = false;

         while(listiterator.hasNext()) {
            StringSplitter.LineComponent stringsplitter$linecomponent = listiterator.next();
            String s = stringsplitter$linecomponent.f_92485_;
            int j = s.length();
            if (!flag) {
               if (i > j) {
                  componentcollector.m_90675_(stringsplitter$linecomponent);
                  listiterator.remove();
                  i -= j;
               } else {
                  String s1 = s.substring(0, i);
                  if (!s1.isEmpty()) {
                     componentcollector.m_90675_(FormattedText.m_130762_(s1, stringsplitter$linecomponent.f_92486_));
                  }

                  i += p_92454_;
                  flag = true;
               }
            }

            if (flag) {
               if (i <= j) {
                  String s2 = s.substring(i);
                  if (s2.isEmpty()) {
                     listiterator.remove();
                  } else {
                     listiterator.set(new StringSplitter.LineComponent(s2, p_92455_));
                  }
                  break;
               }

               listiterator.remove();
               i -= j;
            }
         }

         this.f_92446_ = this.f_92446_.substring(p_92453_ + p_92454_);
         return componentcollector.m_90677_();
      }

      @Nullable
      public FormattedText m_92449_() {
         ComponentCollector componentcollector = new ComponentCollector();
         this.f_92445_.forEach(componentcollector::m_90675_);
         this.f_92445_.clear();
         return componentcollector.m_90674_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class LineBreakFinder implements FormattedCharSink {
      private final float f_92461_;
      private int f_92462_ = -1;
      private Style f_92463_ = Style.f_131099_;
      private boolean f_92464_;
      private float f_92465_;
      private int f_92466_ = -1;
      private Style f_92467_ = Style.f_131099_;
      private int f_92468_;
      private int f_92469_;

      public LineBreakFinder(float p_92472_) {
         this.f_92461_ = Math.max(p_92472_, 1.0F);
      }

      public boolean m_6411_(int p_92480_, Style p_92481_, int p_92482_) {
         int i = p_92480_ + this.f_92469_;
         switch(p_92482_) {
         case 10:
            return this.m_92476_(i, p_92481_);
         case 32:
            this.f_92466_ = i;
            this.f_92467_ = p_92481_;
         default:
            float f = StringSplitter.this.f_92333_.m_92515_(p_92482_, p_92481_);
            this.f_92465_ += f;
            if (this.f_92464_ && this.f_92465_ > this.f_92461_) {
               return this.f_92466_ != -1 ? this.m_92476_(this.f_92466_, this.f_92467_) : this.m_92476_(i, p_92481_);
            } else {
               this.f_92464_ |= f != 0.0F;
               this.f_92468_ = i + Character.charCount(p_92482_);
               return true;
            }
         }
      }

      private boolean m_92476_(int p_92477_, Style p_92478_) {
         this.f_92462_ = p_92477_;
         this.f_92463_ = p_92478_;
         return false;
      }

      private boolean m_92484_() {
         return this.f_92462_ != -1;
      }

      public int m_92473_() {
         return this.m_92484_() ? this.f_92462_ : this.f_92468_;
      }

      public Style m_92483_() {
         return this.f_92463_;
      }

      public void m_92474_(int p_92475_) {
         this.f_92469_ += p_92475_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class LineComponent implements FormattedText {
      final String f_92485_;
      final Style f_92486_;

      public LineComponent(String p_92488_, Style p_92489_) {
         this.f_92485_ = p_92488_;
         this.f_92486_ = p_92489_;
      }

      public <T> Optional<T> m_5651_(FormattedText.ContentConsumer<T> p_92493_) {
         return p_92493_.m_130809_(this.f_92485_);
      }

      public <T> Optional<T> m_7451_(FormattedText.StyledContentConsumer<T> p_92495_, Style p_92496_) {
         return p_92495_.m_7164_(this.f_92486_.m_131146_(p_92496_), this.f_92485_);
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface LinePosConsumer {
      void m_92499_(Style p_92500_, int p_92501_, int p_92502_);
   }

   @OnlyIn(Dist.CLIENT)
   class WidthLimitedCharSink implements FormattedCharSink {
      private float f_92504_;
      private int f_92505_;

      public WidthLimitedCharSink(float p_92508_) {
         this.f_92504_ = p_92508_;
      }

      public boolean m_6411_(int p_92511_, Style p_92512_, int p_92513_) {
         this.f_92504_ -= StringSplitter.this.f_92333_.m_92515_(p_92513_, p_92512_);
         if (this.f_92504_ >= 0.0F) {
            this.f_92505_ = p_92511_ + Character.charCount(p_92513_);
            return true;
         } else {
            return false;
         }
      }

      public int m_92509_() {
         return this.f_92505_;
      }

      public void m_92514_() {
         this.f_92505_ = 0;
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface WidthProvider {
      float m_92515_(int p_92516_, Style p_92517_);
   }
}