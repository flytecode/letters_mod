package net.minecraft.client.resources.language;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.List;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.SubStringSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FormattedBidiReorder {
   public static FormattedCharSequence m_118931_(FormattedText p_118932_, boolean p_118933_) {
      SubStringSource substringsource = SubStringSource.m_131251_(p_118932_, UCharacter::getMirror, FormattedBidiReorder::m_118929_);
      Bidi bidi = new Bidi(substringsource.m_131235_(), p_118933_ ? 127 : 126);
      bidi.setReorderingMode(0);
      List<FormattedCharSequence> list = Lists.newArrayList();
      int i = bidi.countRuns();

      for(int j = 0; j < i; ++j) {
         BidiRun bidirun = bidi.getVisualRun(j);
         list.addAll(substringsource.m_131236_(bidirun.getStart(), bidirun.getLength(), bidirun.isOddRun()));
      }

      return FormattedCharSequence.m_13722_(list);
   }

   private static String m_118929_(String p_118930_) {
      try {
         return (new ArabicShaping(8)).shape(p_118930_);
      } catch (Exception exception) {
         return p_118930_;
      }
   }
}