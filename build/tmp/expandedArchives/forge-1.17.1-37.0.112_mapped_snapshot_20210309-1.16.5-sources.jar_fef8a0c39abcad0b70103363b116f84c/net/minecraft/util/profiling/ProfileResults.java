package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.List;

public interface ProfileResults {
   char f_145956_ = '\u001e';

   List<ResultField> m_6412_(String p_18574_);

   boolean m_142444_(Path p_145957_);

   long m_7229_();

   int m_7230_();

   long m_7236_();

   int m_7317_();

   default long m_18577_() {
      return this.m_7236_() - this.m_7229_();
   }

   default int m_7315_() {
      return this.m_7317_() - this.m_7230_();
   }

   String m_142368_();

   static String m_18575_(String p_18576_) {
      return p_18576_.replace('\u001e', '.');
   }
}