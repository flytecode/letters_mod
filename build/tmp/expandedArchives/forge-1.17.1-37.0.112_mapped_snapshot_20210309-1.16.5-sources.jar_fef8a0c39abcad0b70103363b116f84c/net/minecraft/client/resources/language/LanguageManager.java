package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import net.minecraft.client.resources.metadata.language.LanguageMetadataSection;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LanguageManager implements ResourceManagerReloadListener {
   private static final Logger f_118964_ = LogManager.getLogger();
   public static final String f_174854_ = "en_us";
   private static final LanguageInfo f_118965_ = new LanguageInfo("en_us", "US", "English", false);
   private Map<String, LanguageInfo> f_118966_ = ImmutableMap.of("en_us", f_118965_);
   private String f_118967_;
   private LanguageInfo f_118968_ = f_118965_;

   public LanguageManager(String p_118971_) {
      this.f_118967_ = p_118971_;
   }

   private static Map<String, LanguageInfo> m_118981_(Stream<PackResources> p_118982_) {
      Map<String, LanguageInfo> map = Maps.newHashMap();
      p_118982_.forEach((p_118980_) -> {
         try {
            LanguageMetadataSection languagemetadatasection = p_118980_.m_5550_(LanguageMetadataSection.f_119096_);
            if (languagemetadatasection != null) {
               for(LanguageInfo languageinfo : languagemetadatasection.m_119101_()) {
                  map.putIfAbsent(languageinfo.getCode(), languageinfo);
               }
            }
         } catch (IOException | RuntimeException runtimeexception) {
            f_118964_.warn("Unable to parse language metadata section of resourcepack: {}", p_118980_.m_8017_(), runtimeexception);
         }

      });
      return ImmutableMap.copyOf(map);
   }

   public void m_6213_(ResourceManager p_118973_) {
      this.f_118966_ = m_118981_(p_118973_.m_7536_());
      LanguageInfo languageinfo = this.f_118966_.getOrDefault("en_us", f_118965_);
      this.f_118968_ = this.f_118966_.getOrDefault(this.f_118967_, languageinfo);
      List<LanguageInfo> list = Lists.newArrayList(languageinfo);
      if (this.f_118968_ != languageinfo) {
         list.add(this.f_118968_);
      }

      ClientLanguage clientlanguage = ClientLanguage.m_118916_(p_118973_, list);
      I18n.m_118941_(clientlanguage);
      Language.m_128114_(clientlanguage);
   }

   public void m_118974_(LanguageInfo p_118975_) {
      this.f_118967_ = p_118975_.getCode();
      this.f_118968_ = p_118975_;
   }

   public LanguageInfo m_118983_() {
      return this.f_118968_;
   }

   public SortedSet<LanguageInfo> m_118984_() {
      return Sets.newTreeSet(this.f_118966_.values());
   }

   public LanguageInfo m_118976_(String p_118977_) {
      return this.f_118966_.get(p_118977_);
   }
}