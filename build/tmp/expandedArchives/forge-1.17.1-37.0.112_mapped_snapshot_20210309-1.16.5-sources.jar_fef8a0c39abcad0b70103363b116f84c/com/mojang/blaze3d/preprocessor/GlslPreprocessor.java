package com.mojang.blaze3d.preprocessor;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.util.StringUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

@OnlyIn(Dist.CLIENT)
public abstract class GlslPreprocessor {
   private static final String f_166454_ = "/\\*(?:[^*]|\\*+[^*/])*\\*+/";
   private static final String f_166455_ = "//[^\\v]*";
   private static final Pattern f_166456_ = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*moj_import(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(?:\"(.*)\"|<(.*)>))");
   private static final Pattern f_166457_ = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*version(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(\\d+))\\b");
   private static final Pattern f_166458_ = Pattern.compile("(?:^|\\v)(?:\\s|/\\*(?:[^*]|\\*+[^*/])*\\*+/|(//[^\\v]*))*\\z");

   public List<String> m_166461_(String p_166462_) {
      GlslPreprocessor.Context glslpreprocessor$context = new GlslPreprocessor.Context();
      List<String> list = this.m_166469_(p_166462_, glslpreprocessor$context, "");
      list.set(0, this.m_166463_(list.get(0), glslpreprocessor$context.f_166482_));
      return list;
   }

   private List<String> m_166469_(String p_166470_, GlslPreprocessor.Context p_166471_, String p_166472_) {
      int i = p_166471_.f_166483_;
      int j = 0;
      String s = "";
      List<String> list = Lists.newArrayList();
      Matcher matcher = f_166456_.matcher(p_166470_);

      while(matcher.find()) {
         if (!m_166476_(p_166470_, matcher, j)) {
            String s1 = matcher.group(2);
            boolean flag = s1 != null;
            if (!flag) {
               s1 = matcher.group(3);
            }

            if (s1 != null) {
               String s2 = p_166470_.substring(j, matcher.start(1));
               String s3 = p_166472_ + s1;
               String s4 = this.m_142138_(flag, s3);
               if (!Strings.isEmpty(s4)) {
                  if (!StringUtil.m_145004_(s4)) {
                     s4 = s4 + System.lineSeparator();
                  }

                  ++p_166471_.f_166483_;
                  int k = p_166471_.f_166483_;
                  List<String> list1 = this.m_166469_(s4, p_166471_, flag ? FileUtil.m_179922_(s3) : "");
                  list1.set(0, String.format(Locale.ROOT, "#line %d %d\n%s", 0, k, this.m_166466_(list1.get(0), p_166471_)));
                  if (!StringUtils.isBlank(s2)) {
                     list.add(s2);
                  }

                  list.addAll(list1);
               } else {
                  String s6 = flag ? String.format("/*#moj_import \"%s\"*/", s1) : String.format("/*#moj_import <%s>*/", s1);
                  list.add(s + s2 + s6);
               }

               int l = StringUtil.m_145002_(p_166470_.substring(0, matcher.end(1)));
               s = String.format(Locale.ROOT, "#line %d %d", l, i);
               j = matcher.end(1);
            }
         }
      }

      String s5 = p_166470_.substring(j);
      if (!StringUtils.isBlank(s5)) {
         list.add(s + s5);
      }

      return list;
   }

   private String m_166466_(String p_166467_, GlslPreprocessor.Context p_166468_) {
      Matcher matcher = f_166457_.matcher(p_166467_);
      if (matcher.find() && m_166473_(p_166467_, matcher)) {
         p_166468_.f_166482_ = Math.max(p_166468_.f_166482_, Integer.parseInt(matcher.group(2)));
         return p_166467_.substring(0, matcher.start(1)) + "/*" + p_166467_.substring(matcher.start(1), matcher.end(1)) + "*/" + p_166467_.substring(matcher.end(1));
      } else {
         return p_166467_;
      }
   }

   private String m_166463_(String p_166464_, int p_166465_) {
      Matcher matcher = f_166457_.matcher(p_166464_);
      return matcher.find() && m_166473_(p_166464_, matcher) ? p_166464_.substring(0, matcher.start(2)) + Math.max(p_166465_, Integer.parseInt(matcher.group(2))) + p_166464_.substring(matcher.end(2)) : p_166464_;
   }

   private static boolean m_166473_(String p_166474_, Matcher p_166475_) {
      return !m_166476_(p_166474_, p_166475_, 0);
   }

   private static boolean m_166476_(String p_166477_, Matcher p_166478_, int p_166479_) {
      int i = p_166478_.start() - p_166479_;
      if (i == 0) {
         return false;
      } else {
         Matcher matcher = f_166458_.matcher(p_166477_.substring(p_166479_, p_166478_.start()));
         if (!matcher.find()) {
            return true;
         } else {
            int j = matcher.end(1);
            return j == p_166478_.start();
         }
      }
   }

   @Nullable
   public abstract String m_142138_(boolean p_166480_, String p_166481_);

   @OnlyIn(Dist.CLIENT)
   static final class Context {
      int f_166482_;
      int f_166483_;
   }
}