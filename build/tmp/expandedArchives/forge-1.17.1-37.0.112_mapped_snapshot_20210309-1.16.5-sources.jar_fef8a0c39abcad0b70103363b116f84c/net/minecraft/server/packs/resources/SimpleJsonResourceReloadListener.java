package net.minecraft.server.packs.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SimpleJsonResourceReloadListener extends SimplePreparableReloadListener<Map<ResourceLocation, JsonElement>> {
   private static final Logger f_10762_ = LogManager.getLogger();
   private static final String f_143936_ = ".json";
   private static final int f_10763_ = ".json".length();
   private final Gson f_10764_;
   private final String f_10765_;

   public SimpleJsonResourceReloadListener(Gson p_10768_, String p_10769_) {
      this.f_10764_ = p_10768_;
      this.f_10765_ = p_10769_;
   }

   protected Map<ResourceLocation, JsonElement> m_5944_(ResourceManager p_10771_, ProfilerFiller p_10772_) {
      Map<ResourceLocation, JsonElement> map = Maps.newHashMap();
      int i = this.f_10765_.length() + 1;

      for(ResourceLocation resourcelocation : p_10771_.m_6540_(this.f_10765_, (p_10774_) -> {
         return p_10774_.endsWith(".json");
      })) {
         String s = resourcelocation.m_135815_();
         ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.m_135827_(), s.substring(i, s.length() - f_10763_));

         try {
            Resource resource = p_10771_.m_142591_(resourcelocation);

            try {
               InputStream inputstream = resource.m_6679_();

               try {
                  Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));

                  try {
                     JsonElement jsonelement = GsonHelper.m_13776_(this.f_10764_, reader, JsonElement.class);
                     if (jsonelement != null) {
                        JsonElement jsonelement1 = map.put(resourcelocation1, jsonelement);
                        if (jsonelement1 != null) {
                           throw new IllegalStateException("Duplicate data file ignored with ID " + resourcelocation1);
                        }
                     } else {
                        f_10762_.error("Couldn't load data file {} from {} as it's null or empty", resourcelocation1, resourcelocation);
                     }
                  } catch (Throwable throwable3) {
                     try {
                        reader.close();
                     } catch (Throwable throwable2) {
                        throwable3.addSuppressed(throwable2);
                     }

                     throw throwable3;
                  }

                  reader.close();
               } catch (Throwable throwable4) {
                  if (inputstream != null) {
                     try {
                        inputstream.close();
                     } catch (Throwable throwable1) {
                        throwable4.addSuppressed(throwable1);
                     }
                  }

                  throw throwable4;
               }

               if (inputstream != null) {
                  inputstream.close();
               }
            } catch (Throwable throwable5) {
               if (resource != null) {
                  try {
                     resource.close();
                  } catch (Throwable throwable) {
                     throwable5.addSuppressed(throwable);
                  }
               }

               throw throwable5;
            }

            if (resource != null) {
               resource.close();
            }
         } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
            f_10762_.error("Couldn't parse data file {} from {}", resourcelocation1, resourcelocation, jsonparseexception);
         }
      }

      return map;
   }

   protected ResourceLocation getPreparedPath(ResourceLocation rl) {
      return new ResourceLocation(rl.m_135827_(), this.f_10765_ + "/" + rl.m_135815_() + ".json");
   }
}
