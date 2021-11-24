package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class GpuWarnlistManager extends SimplePreparableReloadListener<GpuWarnlistManager.Preparations> {
   private static final Logger f_109210_ = LogManager.getLogger();
   private static final ResourceLocation f_109211_ = new ResourceLocation("gpu_warnlist.json");
   private ImmutableMap<String, String> f_109212_ = ImmutableMap.of();
   private boolean f_109213_;
   private boolean f_109214_;
   private boolean f_109215_;

   public boolean m_109218_() {
      return !this.f_109212_.isEmpty();
   }

   public boolean m_109240_() {
      return this.m_109218_() && !this.f_109214_;
   }

   public void m_109247_() {
      this.f_109213_ = true;
   }

   public void m_109248_() {
      this.f_109214_ = true;
   }

   public void m_109249_() {
      this.f_109214_ = true;
      this.f_109215_ = true;
   }

   public boolean m_109250_() {
      return this.f_109213_ && !this.f_109214_;
   }

   public boolean m_109251_() {
      return this.f_109215_;
   }

   public void m_109252_() {
      this.f_109213_ = false;
      this.f_109214_ = false;
      this.f_109215_ = false;
   }

   @Nullable
   public String m_109253_() {
      return this.f_109212_.get("renderer");
   }

   @Nullable
   public String m_109254_() {
      return this.f_109212_.get("version");
   }

   @Nullable
   public String m_109255_() {
      return this.f_109212_.get("vendor");
   }

   @Nullable
   public String m_109256_() {
      StringBuilder stringbuilder = new StringBuilder();
      this.f_109212_.forEach((p_109235_, p_109236_) -> {
         stringbuilder.append(p_109235_).append(": ").append(p_109236_);
      });
      return stringbuilder.length() == 0 ? null : stringbuilder.toString();
   }

   protected GpuWarnlistManager.Preparations m_5944_(ResourceManager p_109220_, ProfilerFiller p_109221_) {
      List<Pattern> list = Lists.newArrayList();
      List<Pattern> list1 = Lists.newArrayList();
      List<Pattern> list2 = Lists.newArrayList();
      p_109221_.m_7242_();
      JsonObject jsonobject = m_109244_(p_109220_, p_109221_);
      if (jsonobject != null) {
         p_109221_.m_6180_("compile_regex");
         m_109222_(jsonobject.getAsJsonArray("renderer"), list);
         m_109222_(jsonobject.getAsJsonArray("version"), list1);
         m_109222_(jsonobject.getAsJsonArray("vendor"), list2);
         p_109221_.m_7238_();
      }

      p_109221_.m_7241_();
      return new GpuWarnlistManager.Preparations(list, list1, list2);
   }

   protected void m_5787_(GpuWarnlistManager.Preparations p_109226_, ResourceManager p_109227_, ProfilerFiller p_109228_) {
      this.f_109212_ = p_109226_.m_109269_();
   }

   private static void m_109222_(JsonArray p_109223_, List<Pattern> p_109224_) {
      p_109223_.forEach((p_109239_) -> {
         p_109224_.add(Pattern.compile(p_109239_.getAsString(), 2));
      });
   }

   @Nullable
   private static JsonObject m_109244_(ResourceManager p_109245_, ProfilerFiller p_109246_) {
      p_109246_.m_6180_("parse_json");
      JsonObject jsonobject = null;

      try {
         Resource resource = p_109245_.m_142591_(f_109211_);

         try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8));

            try {
               jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
            } catch (Throwable throwable2) {
               try {
                  bufferedreader.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }

            bufferedreader.close();
         } catch (Throwable throwable3) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable3.addSuppressed(throwable);
               }
            }

            throw throwable3;
         }

         if (resource != null) {
            resource.close();
         }
      } catch (JsonSyntaxException | IOException ioexception) {
         f_109210_.warn("Failed to load GPU warnlist");
      }

      p_109246_.m_7238_();
      return jsonobject;
   }

   @OnlyIn(Dist.CLIENT)
   protected static final class Preparations {
      private final List<Pattern> f_109257_;
      private final List<Pattern> f_109258_;
      private final List<Pattern> f_109259_;

      Preparations(List<Pattern> p_109261_, List<Pattern> p_109262_, List<Pattern> p_109263_) {
         this.f_109257_ = p_109261_;
         this.f_109258_ = p_109262_;
         this.f_109259_ = p_109263_;
      }

      private static String m_109272_(List<Pattern> p_109273_, String p_109274_) {
         List<String> list = Lists.newArrayList();

         for(Pattern pattern : p_109273_) {
            Matcher matcher = pattern.matcher(p_109274_);

            while(matcher.find()) {
               list.add(matcher.group());
            }
         }

         return String.join(", ", list);
      }

      ImmutableMap<String, String> m_109269_() {
         Builder<String, String> builder = new Builder<>();
         String s = m_109272_(this.f_109257_, GlUtil.m_84820_());
         if (!s.isEmpty()) {
            builder.put("renderer", s);
         }

         String s1 = m_109272_(this.f_109258_, GlUtil.m_84821_());
         if (!s1.isEmpty()) {
            builder.put("version", s1);
         }

         String s2 = m_109272_(this.f_109259_, GlUtil.m_84818_());
         if (!s2.isEmpty()) {
            builder.put("vendor", s2);
         }

         return builder.build();
      }
   }
}