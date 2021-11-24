package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringDecomposer;

public class SubStringSource {
   private final String f_131228_;
   private final List<Style> f_131229_;
   private final Int2IntFunction f_131230_;

   private SubStringSource(String p_131232_, List<Style> p_131233_, Int2IntFunction p_131234_) {
      this.f_131228_ = p_131232_;
      this.f_131229_ = ImmutableList.copyOf(p_131233_);
      this.f_131230_ = p_131234_;
   }

   public String m_131235_() {
      return this.f_131228_;
   }

   public List<FormattedCharSequence> m_131236_(int p_131237_, int p_131238_, boolean p_131239_) {
      if (p_131238_ == 0) {
         return ImmutableList.of();
      } else {
         List<FormattedCharSequence> list = Lists.newArrayList();
         Style style = this.f_131229_.get(p_131237_);
         int i = p_131237_;

         for(int j = 1; j < p_131238_; ++j) {
            int k = p_131237_ + j;
            Style style1 = this.f_131229_.get(k);
            if (!style1.equals(style)) {
               String s = this.f_131228_.substring(i, k);
               list.add(p_131239_ ? FormattedCharSequence.m_13740_(s, style, this.f_131230_) : FormattedCharSequence.m_13714_(s, style));
               style = style1;
               i = k;
            }
         }

         if (i < p_131237_ + p_131238_) {
            String s1 = this.f_131228_.substring(i, p_131237_ + p_131238_);
            list.add(p_131239_ ? FormattedCharSequence.m_13740_(s1, style, this.f_131230_) : FormattedCharSequence.m_13714_(s1, style));
         }

         return p_131239_ ? Lists.reverse(list) : list;
      }
   }

   public static SubStringSource m_178536_(FormattedText p_178537_) {
      return m_131251_(p_178537_, (p_178527_) -> {
         return p_178527_;
      }, (p_178529_) -> {
         return p_178529_;
      });
   }

   public static SubStringSource m_131251_(FormattedText p_131252_, Int2IntFunction p_131253_, UnaryOperator<String> p_131254_) {
      StringBuilder stringbuilder = new StringBuilder();
      List<Style> list = Lists.newArrayList();
      p_131252_.m_7451_((p_131249_, p_131250_) -> {
         StringDecomposer.m_14346_(p_131250_, p_131249_, (p_178533_, p_178534_, p_178535_) -> {
            stringbuilder.appendCodePoint(p_178535_);
            int i = Character.charCount(p_178535_);

            for(int j = 0; j < i; ++j) {
               list.add(p_178534_);
            }

            return true;
         });
         return Optional.empty();
      }, Style.f_131099_);
      return new SubStringSource(p_131254_.apply(stringbuilder.toString()), list, p_131253_);
   }
}