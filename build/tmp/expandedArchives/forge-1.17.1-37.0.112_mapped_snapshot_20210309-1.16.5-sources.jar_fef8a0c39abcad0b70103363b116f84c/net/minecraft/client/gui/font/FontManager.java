package net.minecraft.client.gui.font;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.font.GlyphProvider;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.providers.GlyphProviderBuilderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class FontManager implements AutoCloseable {
   static final Logger f_94997_ = LogManager.getLogger();
   private static final String f_169089_ = "fonts.json";
   public static final ResourceLocation f_94996_ = new ResourceLocation("minecraft", "missing");
   private final FontSet f_94998_;
   final Map<ResourceLocation, FontSet> f_94999_ = Maps.newHashMap();
   final TextureManager f_95000_;
   private Map<ResourceLocation, ResourceLocation> f_95001_ = ImmutableMap.of();
   private final PreparableReloadListener f_95002_ = new SimplePreparableReloadListener<Map<ResourceLocation, List<GlyphProvider>>>() {
      protected Map<ResourceLocation, List<GlyphProvider>> m_5944_(ResourceManager p_95024_, ProfilerFiller p_95025_) {
         p_95025_.m_7242_();
         Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
         Map<ResourceLocation, List<GlyphProvider>> map = Maps.newHashMap();

         for(ResourceLocation resourcelocation : p_95024_.m_6540_("font", (p_95031_) -> {
            return p_95031_.endsWith(".json");
         })) {
            String s = resourcelocation.m_135815_();
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.m_135827_(), s.substring("font/".length(), s.length() - ".json".length()));
            List<GlyphProvider> list = map.computeIfAbsent(resourcelocation1, (p_95040_) -> {
               return Lists.newArrayList(new AllMissingGlyphProvider());
            });
            p_95025_.m_6521_(resourcelocation1::toString);

            try {
               for(Resource resource : p_95024_.m_7396_(resourcelocation)) {
                  p_95025_.m_6521_(resource::m_7816_);

                  try {
                     InputStream inputstream = resource.m_6679_();

                     try {
                        Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));

                        try {
                           p_95025_.m_6180_("reading");
                           JsonArray jsonarray = GsonHelper.m_13933_(GsonHelper.m_13776_(gson, reader, JsonObject.class), "providers");
                           p_95025_.m_6182_("parsing");

                           for(int i = jsonarray.size() - 1; i >= 0; --i) {
                              JsonObject jsonobject = GsonHelper.m_13918_(jsonarray.get(i), "providers[" + i + "]");

                              try {
                                 String s1 = GsonHelper.m_13906_(jsonobject, "type");
                                 GlyphProviderBuilderType glyphproviderbuildertype = GlyphProviderBuilderType.m_95415_(s1);
                                 p_95025_.m_6180_(s1);
                                 GlyphProvider glyphprovider = glyphproviderbuildertype.m_95413_(jsonobject).m_6762_(p_95024_);
                                 if (glyphprovider != null) {
                                    list.add(glyphprovider);
                                 }

                                 p_95025_.m_7238_();
                              } catch (RuntimeException runtimeexception) {
                                 FontManager.f_94997_.warn("Unable to read definition '{}' in {} in resourcepack: '{}': {}", resourcelocation1, "fonts.json", resource.m_7816_(), runtimeexception.getMessage());
                              }
                           }

                           p_95025_.m_7238_();
                        } catch (Throwable throwable2) {
                           try {
                              reader.close();
                           } catch (Throwable throwable1) {
                              throwable2.addSuppressed(throwable1);
                           }

                           throw throwable2;
                        }

                        reader.close();
                     } catch (Throwable throwable3) {
                        if (inputstream != null) {
                           try {
                              inputstream.close();
                           } catch (Throwable throwable) {
                              throwable3.addSuppressed(throwable);
                           }
                        }

                        throw throwable3;
                     }

                     if (inputstream != null) {
                        inputstream.close();
                     }
                  } catch (RuntimeException runtimeexception1) {
                     FontManager.f_94997_.warn("Unable to load font '{}' in {} in resourcepack: '{}': {}", resourcelocation1, "fonts.json", resource.m_7816_(), runtimeexception1.getMessage());
                  }

                  p_95025_.m_7238_();
               }
            } catch (IOException ioexception) {
               FontManager.f_94997_.warn("Unable to load font '{}' in {}: {}", resourcelocation1, "fonts.json", ioexception.getMessage());
            }

            p_95025_.m_6180_("caching");
            IntSet intset = new IntOpenHashSet();

            for(GlyphProvider glyphprovider1 : list) {
               intset.addAll(glyphprovider1.m_6990_());
            }

            intset.forEach((int p_95034_) -> {
               if (p_95034_ != 32) {
                  for(GlyphProvider glyphprovider2 : Lists.reverse(list)) {
                     if (glyphprovider2.m_7823_(p_95034_) != null) {
                        break;
                     }
                  }

               }
            });
            p_95025_.m_7238_();
            p_95025_.m_7238_();
         }

         p_95025_.m_7241_();
         return map;
      }

      protected void m_5787_(Map<ResourceLocation, List<GlyphProvider>> p_95036_, ResourceManager p_95037_, ProfilerFiller p_95038_) {
         p_95038_.m_7242_();
         p_95038_.m_6180_("closing");
         FontManager.this.f_94999_.values().forEach(FontSet::close);
         FontManager.this.f_94999_.clear();
         p_95038_.m_6182_("reloading");
         p_95036_.forEach((p_95042_, p_95043_) -> {
            FontSet fontset = new FontSet(FontManager.this.f_95000_, p_95042_);
            fontset.m_95071_(Lists.reverse(p_95043_));
            FontManager.this.f_94999_.put(p_95042_, fontset);
         });
         p_95038_.m_7238_();
         p_95038_.m_7241_();
      }

      public String m_7812_() {
         return "FontManager";
      }
   };

   public FontManager(TextureManager p_95005_) {
      this.f_95000_ = p_95005_;
      this.f_94998_ = Util.m_137469_(new FontSet(p_95005_, f_94996_), (p_95010_) -> {
         p_95010_.m_95071_(Lists.newArrayList(new AllMissingGlyphProvider()));
      });
   }

   public void m_95011_(Map<ResourceLocation, ResourceLocation> p_95012_) {
      this.f_95001_ = p_95012_;
   }

   public Font m_95006_() {
      return new Font((p_95014_) -> {
         return this.f_94999_.getOrDefault(this.f_95001_.getOrDefault(p_95014_, p_95014_), this.f_94998_);
      });
   }

   public PreparableReloadListener m_95015_() {
      return this.f_95002_;
   }

   public void close() {
      this.f_94999_.values().forEach(FontSet::close);
      this.f_94998_.close();
   }
}