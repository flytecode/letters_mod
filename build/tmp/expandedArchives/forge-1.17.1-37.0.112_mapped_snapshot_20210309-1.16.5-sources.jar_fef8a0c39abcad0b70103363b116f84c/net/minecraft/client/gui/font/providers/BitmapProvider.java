package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BitmapProvider implements GlyphProvider {
   static final Logger f_95328_ = LogManager.getLogger();
   private final NativeImage f_95329_;
   private final Int2ObjectMap<BitmapProvider.Glyph> f_95330_;

   BitmapProvider(NativeImage p_95333_, Int2ObjectMap<BitmapProvider.Glyph> p_95334_) {
      this.f_95329_ = p_95333_;
      this.f_95330_ = p_95334_;
   }

   public void close() {
      this.f_95329_.close();
   }

   @Nullable
   public RawGlyph m_7823_(int p_95341_) {
      return this.f_95330_.get(p_95341_);
   }

   public IntSet m_6990_() {
      return IntSets.unmodifiable(this.f_95330_.keySet());
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder implements GlyphProviderBuilder {
      private final ResourceLocation f_95344_;
      private final List<int[]> f_95345_;
      private final int f_95346_;
      private final int f_95347_;

      public Builder(ResourceLocation p_95349_, int p_95350_, int p_95351_, List<int[]> p_95352_) {
         this.f_95344_ = new ResourceLocation(p_95349_.m_135827_(), "textures/" + p_95349_.m_135815_());
         this.f_95345_ = p_95352_;
         this.f_95346_ = p_95350_;
         this.f_95347_ = p_95351_;
      }

      public static BitmapProvider.Builder m_95355_(JsonObject p_95356_) {
         int i = GsonHelper.m_13824_(p_95356_, "height", 8);
         int j = GsonHelper.m_13927_(p_95356_, "ascent");
         if (j > i) {
            throw new JsonParseException("Ascent " + j + " higher than height " + i);
         } else {
            List<int[]> list = Lists.newArrayList();
            JsonArray jsonarray = GsonHelper.m_13933_(p_95356_, "chars");

            for(int k = 0; k < jsonarray.size(); ++k) {
               String s = GsonHelper.m_13805_(jsonarray.get(k), "chars[" + k + "]");
               int[] aint = s.codePoints().toArray();
               if (k > 0) {
                  int l = ((int[])list.get(0)).length;
                  if (aint.length != l) {
                     throw new JsonParseException("Elements of chars have to be the same length (found: " + aint.length + ", expected: " + l + "), pad with space or \\u0000");
                  }
               }

               list.add(aint);
            }

            if (!list.isEmpty() && ((int[])list.get(0)).length != 0) {
               return new BitmapProvider.Builder(new ResourceLocation(GsonHelper.m_13906_(p_95356_, "file")), i, j, list);
            } else {
               throw new JsonParseException("Expected to find data in chars, found none.");
            }
         }
      }

      @Nullable
      public GlyphProvider m_6762_(ResourceManager p_95354_) {
         try {
            Resource resource = p_95354_.m_142591_(this.f_95344_);

            BitmapProvider bitmapprovider;
            try {
               NativeImage nativeimage = NativeImage.m_85048_(NativeImage.Format.RGBA, resource.m_6679_());
               int i = nativeimage.m_84982_();
               int j = nativeimage.m_85084_();
               int k = i / ((int[])this.f_95345_.get(0)).length;
               int l = j / this.f_95345_.size();
               float f = (float)this.f_95346_ / (float)l;
               Int2ObjectMap<BitmapProvider.Glyph> int2objectmap = new Int2ObjectOpenHashMap<>();

               for(int i1 = 0; i1 < this.f_95345_.size(); ++i1) {
                  int j1 = 0;

                  for(int k1 : this.f_95345_.get(i1)) {
                     int l1 = j1++;
                     if (k1 != 0 && k1 != 32) {
                        int i2 = this.m_95357_(nativeimage, k, l, l1, i1);
                        BitmapProvider.Glyph bitmapprovider$glyph = int2objectmap.put(k1, new BitmapProvider.Glyph(f, nativeimage, l1 * k, i1 * l, k, l, (int)(0.5D + (double)((float)i2 * f)) + 1, this.f_95347_));
                        if (bitmapprovider$glyph != null) {
                           BitmapProvider.f_95328_.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(k1), this.f_95344_);
                        }
                     }
                  }
               }

               bitmapprovider = new BitmapProvider(nativeimage, int2objectmap);
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

            return bitmapprovider;
         } catch (IOException ioexception) {
            throw new RuntimeException(ioexception.getMessage());
         }
      }

      private int m_95357_(NativeImage p_95358_, int p_95359_, int p_95360_, int p_95361_, int p_95362_) {
         int i;
         for(i = p_95359_ - 1; i >= 0; --i) {
            int j = p_95361_ * p_95359_ + i;

            for(int k = 0; k < p_95360_; ++k) {
               int l = p_95362_ * p_95360_ + k;
               if (p_95358_.m_85087_(j, l) != 0) {
                  return i + 1;
               }
            }
         }

         return i + 1;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static final class Glyph implements RawGlyph {
      private final float f_95363_;
      private final NativeImage f_95364_;
      private final int f_95365_;
      private final int f_95366_;
      private final int f_95367_;
      private final int f_95368_;
      private final int f_95369_;
      private final int f_95370_;

      Glyph(float p_95372_, NativeImage p_95373_, int p_95374_, int p_95375_, int p_95376_, int p_95377_, int p_95378_, int p_95379_) {
         this.f_95363_ = p_95372_;
         this.f_95364_ = p_95373_;
         this.f_95365_ = p_95374_;
         this.f_95366_ = p_95375_;
         this.f_95367_ = p_95376_;
         this.f_95368_ = p_95377_;
         this.f_95369_ = p_95378_;
         this.f_95370_ = p_95379_;
      }

      public float m_5621_() {
         return 1.0F / this.f_95363_;
      }

      public int m_5631_() {
         return this.f_95367_;
      }

      public int m_5629_() {
         return this.f_95368_;
      }

      public float m_7403_() {
         return (float)this.f_95369_;
      }

      public float m_142672_() {
         return RawGlyph.super.m_142672_() + 7.0F - (float)this.f_95370_;
      }

      public void m_6238_(int p_95391_, int p_95392_) {
         this.f_95364_.m_85003_(0, p_95391_, p_95392_, this.f_95365_, this.f_95366_, this.f_95367_, this.f_95368_, false, false);
      }

      public boolean m_5633_() {
         return this.f_95364_.m_85102_().m_85161_() > 1;
      }
   }
}