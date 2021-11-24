package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientLanguage extends Language {
   private static final Logger f_118909_ = LogManager.getLogger();
   private final Map<String, String> f_118910_;
   private final boolean f_118911_;

   private ClientLanguage(Map<String, String> p_118914_, boolean p_118915_) {
      this.f_118910_ = p_118914_;
      this.f_118911_ = p_118915_;
   }

   public static ClientLanguage m_118916_(ResourceManager p_118917_, List<LanguageInfo> p_118918_) {
      Map<String, String> map = Maps.newHashMap();
      boolean flag = false;

      for(LanguageInfo languageinfo : p_118918_) {
         flag |= languageinfo.m_118952_();
         String s = String.format("lang/%s.json", languageinfo.getCode());

         for(String s1 : p_118917_.m_7187_()) {
            try {
               ResourceLocation resourcelocation = new ResourceLocation(s1, s);
               m_118921_(p_118917_.m_7396_(resourcelocation), map);
            } catch (FileNotFoundException filenotfoundexception) {
            } catch (Exception exception) {
               f_118909_.warn("Skipped language file: {}:{} ({})", s1, s, exception.toString());
            }
         }
      }

      return new ClientLanguage(ImmutableMap.copyOf(map), flag);
   }

   private static void m_118921_(List<Resource> p_118922_, Map<String, String> p_118923_) {
      for(Resource resource : p_118922_) {
         try {
            InputStream inputstream = resource.m_6679_();

            try {
               Language.m_128108_(inputstream, p_118923_::put);
            } catch (Throwable throwable1) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (inputstream != null) {
               inputstream.close();
            }
         } catch (IOException ioexception) {
            f_118909_.warn("Failed to load translations from {}", resource, ioexception);
         }
      }

   }

   public String m_6834_(String p_118920_) {
      return this.f_118910_.getOrDefault(p_118920_, p_118920_);
   }

   public boolean m_6722_(String p_118928_) {
      return this.f_118910_.containsKey(p_118928_);
   }

   public boolean m_6627_() {
      return this.f_118911_;
   }

   public FormattedCharSequence m_5536_(FormattedText p_118925_) {
      return FormattedBidiReorder.m_118931_(p_118925_, this.f_118911_);
   }

   @Override
   public Map<String, String> getLanguageData() {
      return f_118910_;
   }
}
