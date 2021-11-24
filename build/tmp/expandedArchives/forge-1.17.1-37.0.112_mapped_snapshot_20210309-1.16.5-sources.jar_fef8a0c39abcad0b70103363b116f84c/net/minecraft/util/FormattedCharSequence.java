package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.network.chat.Style;

@FunctionalInterface
public interface FormattedCharSequence {
   FormattedCharSequence f_13691_ = (p_13704_) -> {
      return true;
   };

   boolean m_13731_(FormattedCharSink p_13732_);

   static FormattedCharSequence m_13693_(int p_13694_, Style p_13695_) {
      return (p_13730_) -> {
         return p_13730_.m_6411_(0, p_13695_, p_13694_);
      };
   }

   static FormattedCharSequence m_13714_(String p_13715_, Style p_13716_) {
      return p_13715_.isEmpty() ? f_13691_ : (p_13739_) -> {
         return StringDecomposer.m_14317_(p_13715_, p_13716_, p_13739_);
      };
   }

   static FormattedCharSequence m_144717_(String p_144718_, Style p_144719_, Int2IntFunction p_144720_) {
      return p_144718_.isEmpty() ? f_13691_ : (p_144730_) -> {
         return StringDecomposer.m_14317_(p_144718_, p_144719_, m_13705_(p_144730_, p_144720_));
      };
   }

   static FormattedCharSequence m_144723_(String p_144724_, Style p_144725_) {
      return p_144724_.isEmpty() ? f_13691_ : (p_144716_) -> {
         return StringDecomposer.m_14337_(p_144724_, p_144725_, p_144716_);
      };
   }

   static FormattedCharSequence m_13740_(String p_13741_, Style p_13742_, Int2IntFunction p_13743_) {
      return p_13741_.isEmpty() ? f_13691_ : (p_13721_) -> {
         return StringDecomposer.m_14337_(p_13741_, p_13742_, m_13705_(p_13721_, p_13743_));
      };
   }

   static FormattedCharSink m_13705_(FormattedCharSink p_13706_, Int2IntFunction p_13707_) {
      return (p_13711_, p_13712_, p_13713_) -> {
         return p_13706_.m_6411_(p_13711_, p_13712_, p_13707_.apply(Integer.valueOf(p_13713_)));
      };
   }

   static FormattedCharSequence m_144710_() {
      return f_13691_;
   }

   static FormattedCharSequence m_144711_(FormattedCharSequence p_144712_) {
      return p_144712_;
   }

   static FormattedCharSequence m_13696_(FormattedCharSequence p_13697_, FormattedCharSequence p_13698_) {
      return m_13733_(p_13697_, p_13698_);
   }

   static FormattedCharSequence m_144721_(FormattedCharSequence... p_144722_) {
      return m_13744_(ImmutableList.copyOf(p_144722_));
   }

   static FormattedCharSequence m_13722_(List<FormattedCharSequence> p_13723_) {
      int i = p_13723_.size();
      switch(i) {
      case 0:
         return f_13691_;
      case 1:
         return p_13723_.get(0);
      case 2:
         return m_13733_(p_13723_.get(0), p_13723_.get(1));
      default:
         return m_13744_(ImmutableList.copyOf(p_13723_));
      }
   }

   static FormattedCharSequence m_13733_(FormattedCharSequence p_13734_, FormattedCharSequence p_13735_) {
      return (p_13702_) -> {
         return p_13734_.m_13731_(p_13702_) && p_13735_.m_13731_(p_13702_);
      };
   }

   static FormattedCharSequence m_13744_(List<FormattedCharSequence> p_13745_) {
      return (p_13726_) -> {
         for(FormattedCharSequence formattedcharsequence : p_13745_) {
            if (!formattedcharsequence.m_13731_(p_13726_)) {
               return false;
            }
         }

         return true;
      };
   }
}