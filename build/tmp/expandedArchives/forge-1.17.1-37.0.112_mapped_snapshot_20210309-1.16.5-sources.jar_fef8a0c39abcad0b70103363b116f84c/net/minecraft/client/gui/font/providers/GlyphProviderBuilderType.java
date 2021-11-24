package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum GlyphProviderBuilderType {
   BITMAP("bitmap", BitmapProvider.Builder::m_95355_),
   TTF("ttf", TrueTypeGlyphProviderBuilder::m_95499_),
   LEGACY_UNICODE("legacy_unicode", LegacyUnicodeBitmapsProvider.Builder::m_95452_);

   private static final Map<String, GlyphProviderBuilderType> f_95403_ = Util.m_137469_(Maps.newHashMap(), (p_95418_) -> {
      for(GlyphProviderBuilderType glyphproviderbuildertype : values()) {
         p_95418_.put(glyphproviderbuildertype.f_95404_, glyphproviderbuildertype);
      }

   });
   private final String f_95404_;
   private final Function<JsonObject, GlyphProviderBuilder> f_95405_;

   private GlyphProviderBuilderType(String p_95411_, Function<JsonObject, GlyphProviderBuilder> p_95412_) {
      this.f_95404_ = p_95411_;
      this.f_95405_ = p_95412_;
   }

   public static GlyphProviderBuilderType m_95415_(String p_95416_) {
      GlyphProviderBuilderType glyphproviderbuildertype = f_95403_.get(p_95416_);
      if (glyphproviderbuildertype == null) {
         throw new IllegalArgumentException("Invalid type: " + p_95416_);
      } else {
         return glyphproviderbuildertype;
      }
   }

   public GlyphProviderBuilder m_95413_(JsonObject p_95414_) {
      return this.f_95405_.apply(p_95414_);
   }
}