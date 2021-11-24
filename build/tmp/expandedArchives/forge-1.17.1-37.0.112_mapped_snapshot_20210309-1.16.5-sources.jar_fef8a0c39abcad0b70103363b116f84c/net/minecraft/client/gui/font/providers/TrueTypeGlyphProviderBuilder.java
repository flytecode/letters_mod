package net.minecraft.client.gui.font.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class TrueTypeGlyphProviderBuilder implements GlyphProviderBuilder {
   private static final Logger f_95482_ = LogManager.getLogger();
   private final ResourceLocation f_95483_;
   private final float f_95484_;
   private final float f_95485_;
   private final float f_95486_;
   private final float f_95487_;
   private final String f_95488_;

   public TrueTypeGlyphProviderBuilder(ResourceLocation p_95491_, float p_95492_, float p_95493_, float p_95494_, float p_95495_, String p_95496_) {
      this.f_95483_ = p_95491_;
      this.f_95484_ = p_95492_;
      this.f_95485_ = p_95493_;
      this.f_95486_ = p_95494_;
      this.f_95487_ = p_95495_;
      this.f_95488_ = p_95496_;
   }

   public static GlyphProviderBuilder m_95499_(JsonObject p_95500_) {
      float f = 0.0F;
      float f1 = 0.0F;
      if (p_95500_.has("shift")) {
         JsonArray jsonarray = p_95500_.getAsJsonArray("shift");
         if (jsonarray.size() != 2) {
            throw new JsonParseException("Expected 2 elements in 'shift', found " + jsonarray.size());
         }

         f = GsonHelper.m_13888_(jsonarray.get(0), "shift[0]");
         f1 = GsonHelper.m_13888_(jsonarray.get(1), "shift[1]");
      }

      StringBuilder stringbuilder = new StringBuilder();
      if (p_95500_.has("skip")) {
         JsonElement jsonelement = p_95500_.get("skip");
         if (jsonelement.isJsonArray()) {
            JsonArray jsonarray1 = GsonHelper.m_13924_(jsonelement, "skip");

            for(int i = 0; i < jsonarray1.size(); ++i) {
               stringbuilder.append(GsonHelper.m_13805_(jsonarray1.get(i), "skip[" + i + "]"));
            }
         } else {
            stringbuilder.append(GsonHelper.m_13805_(jsonelement, "skip"));
         }
      }

      return new TrueTypeGlyphProviderBuilder(new ResourceLocation(GsonHelper.m_13906_(p_95500_, "file")), GsonHelper.m_13820_(p_95500_, "size", 11.0F), GsonHelper.m_13820_(p_95500_, "oversample", 1.0F), f, f1, stringbuilder.toString());
   }

   @Nullable
   public GlyphProvider m_6762_(ResourceManager p_95498_) {
      STBTTFontinfo stbttfontinfo = null;
      ByteBuffer bytebuffer = null;

      try {
         Resource resource = p_95498_.m_142591_(new ResourceLocation(this.f_95483_.m_135827_(), "font/" + this.f_95483_.m_135815_()));

         TrueTypeGlyphProvider truetypeglyphprovider;
         try {
            f_95482_.debug("Loading font {}", (Object)this.f_95483_);
            stbttfontinfo = STBTTFontinfo.malloc();
            bytebuffer = TextureUtil.m_85303_(resource.m_6679_());
            bytebuffer.flip();
            f_95482_.debug("Reading font {}", (Object)this.f_95483_);
            if (!STBTruetype.stbtt_InitFont(stbttfontinfo, bytebuffer)) {
               throw new IOException("Invalid ttf");
            }

            truetypeglyphprovider = new TrueTypeGlyphProvider(bytebuffer, stbttfontinfo, this.f_95484_, this.f_95485_, this.f_95486_, this.f_95487_, this.f_95488_);
         } catch (Throwable throwable1) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (resource != null) {
            resource.close();
         }

         return truetypeglyphprovider;
      } catch (Exception exception) {
         f_95482_.error("Couldn't load truetype font {}", this.f_95483_, exception);
         if (stbttfontinfo != null) {
            stbttfontinfo.free();
         }

         MemoryUtil.memFree(bytebuffer);
         return null;
      }
   }
}