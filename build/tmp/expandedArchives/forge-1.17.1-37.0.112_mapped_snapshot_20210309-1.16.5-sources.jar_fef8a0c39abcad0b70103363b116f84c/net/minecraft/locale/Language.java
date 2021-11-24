package net.minecraft.locale;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringDecomposer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Language {
   private static final Logger f_128101_ = LogManager.getLogger();
   private static final Gson f_128102_ = new Gson();
   private static final Pattern f_128103_ = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
   public static final String f_177832_ = "en_us";
   private static volatile Language f_128104_ = m_128118_();

   private static Language m_128118_() {
      Builder<String, String> builder = ImmutableMap.builder();
      BiConsumer<String, String> biconsumer = builder::put;
      String s = "/assets/minecraft/lang/en_us.json";

      try {
         InputStream inputstream = Language.class.getResourceAsStream("/assets/minecraft/lang/en_us.json");

         try {
            m_128108_(inputstream, biconsumer);
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
      } catch (JsonParseException | IOException ioexception) {
         f_128101_.error("Couldn't read strings from {}", "/assets/minecraft/lang/en_us.json", ioexception);
      }

      final Map<String, String> map = new java.util.HashMap<>(builder.build());
      net.minecraftforge.fmllegacy.server.LanguageHook.captureLanguageMap(map);
      return new Language() {
         public String m_6834_(String p_128127_) {
            return map.getOrDefault(p_128127_, p_128127_);
         }

         public boolean m_6722_(String p_128135_) {
            return map.containsKey(p_128135_);
         }

         public boolean m_6627_() {
            return false;
         }

         public FormattedCharSequence m_5536_(FormattedText p_128129_) {
            return (p_128132_) -> {
               return p_128129_.m_7451_((p_177835_, p_177836_) -> {
                  return StringDecomposer.m_14346_(p_177836_, p_177835_, p_128132_) ? Optional.empty() : FormattedText.f_130759_;
               }, Style.f_131099_).isPresent();
            };
         }
         
         @Override
         public Map<String, String> getLanguageData() {
            return map;
         }
      };
   }

   public static void m_128108_(InputStream p_128109_, BiConsumer<String, String> p_128110_) {
      JsonObject jsonobject = f_128102_.fromJson(new InputStreamReader(p_128109_, StandardCharsets.UTF_8), JsonObject.class);

      for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
         String s = f_128103_.matcher(GsonHelper.m_13805_(entry.getValue(), entry.getKey())).replaceAll("%$1s");
         p_128110_.accept(entry.getKey(), s);
      }

   }

   public static Language m_128107_() {
      return f_128104_;
   }

   public static void m_128114_(Language p_128115_) {
      f_128104_ = p_128115_;
   }
   
   // FORGE START
   public Map<String, String> getLanguageData() { return ImmutableMap.of(); }

   public abstract String m_6834_(String p_128111_);

   public abstract boolean m_6722_(String p_128117_);

   public abstract boolean m_6627_();

   public abstract FormattedCharSequence m_5536_(FormattedText p_128116_);

   public List<FormattedCharSequence> m_128112_(List<FormattedText> p_128113_) {
      return p_128113_.stream().map(this::m_5536_).collect(ImmutableList.toImmutableList());
   }
}
