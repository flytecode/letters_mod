package com.mojang.blaze3d.font;

import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class TrueTypeGlyphProvider implements GlyphProvider {
   private final ByteBuffer f_83837_;
   final STBTTFontinfo f_83838_;
   final float f_83839_;
   private final IntSet f_83840_ = new IntArraySet();
   final float f_83841_;
   final float f_83842_;
   final float f_83843_;
   final float f_83844_;

   public TrueTypeGlyphProvider(ByteBuffer p_83846_, STBTTFontinfo p_83847_, float p_83848_, float p_83849_, float p_83850_, float p_83851_, String p_83852_) {
      this.f_83837_ = p_83846_;
      this.f_83838_ = p_83847_;
      this.f_83839_ = p_83849_;
      p_83852_.codePoints().forEach(this.f_83840_::add);
      this.f_83841_ = p_83850_ * p_83849_;
      this.f_83842_ = p_83851_ * p_83849_;
      this.f_83843_ = STBTruetype.stbtt_ScaleForPixelHeight(p_83847_, p_83848_ * p_83849_);
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         IntBuffer intbuffer = memorystack.mallocInt(1);
         IntBuffer intbuffer1 = memorystack.mallocInt(1);
         IntBuffer intbuffer2 = memorystack.mallocInt(1);
         STBTruetype.stbtt_GetFontVMetrics(p_83847_, intbuffer, intbuffer1, intbuffer2);
         this.f_83844_ = (float)intbuffer.get(0) * this.f_83843_;
      } catch (Throwable throwable1) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (memorystack != null) {
         memorystack.close();
      }

   }

   @Nullable
   public TrueTypeGlyphProvider.Glyph m_7823_(int p_83859_) {
      if (this.f_83840_.contains(p_83859_)) {
         return null;
      } else {
         MemoryStack memorystack = MemoryStack.stackPush();

         Object object1;
         label61: {
            TrueTypeGlyphProvider.Glyph truetypeglyphprovider$glyph;
            label62: {
               Object object;
               try {
                  IntBuffer intbuffer = memorystack.mallocInt(1);
                  IntBuffer intbuffer1 = memorystack.mallocInt(1);
                  IntBuffer intbuffer2 = memorystack.mallocInt(1);
                  IntBuffer intbuffer3 = memorystack.mallocInt(1);
                  int i = STBTruetype.stbtt_FindGlyphIndex(this.f_83838_, p_83859_);
                  if (i == 0) {
                     object1 = null;
                     break label61;
                  }

                  STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(this.f_83838_, i, this.f_83843_, this.f_83843_, this.f_83841_, this.f_83842_, intbuffer, intbuffer1, intbuffer2, intbuffer3);
                  int j = intbuffer2.get(0) - intbuffer.get(0);
                  int k = intbuffer3.get(0) - intbuffer1.get(0);
                  if (j > 0 && k > 0) {
                     IntBuffer intbuffer5 = memorystack.mallocInt(1);
                     IntBuffer intbuffer4 = memorystack.mallocInt(1);
                     STBTruetype.stbtt_GetGlyphHMetrics(this.f_83838_, i, intbuffer5, intbuffer4);
                     truetypeglyphprovider$glyph = new TrueTypeGlyphProvider.Glyph(intbuffer.get(0), intbuffer2.get(0), -intbuffer1.get(0), -intbuffer3.get(0), (float)intbuffer5.get(0) * this.f_83843_, (float)intbuffer4.get(0) * this.f_83843_, i);
                     break label62;
                  }

                  object = null;
               } catch (Throwable throwable1) {
                  if (memorystack != null) {
                     try {
                        memorystack.close();
                     } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                     }
                  }

                  throw throwable1;
               }

               if (memorystack != null) {
                  memorystack.close();
               }

               return (TrueTypeGlyphProvider.Glyph)object;
            }

            if (memorystack != null) {
               memorystack.close();
            }

            return truetypeglyphprovider$glyph;
         }

         if (memorystack != null) {
            memorystack.close();
         }

         return (TrueTypeGlyphProvider.Glyph)object1;
      }
   }

   public void close() {
      this.f_83838_.free();
      MemoryUtil.memFree(this.f_83837_);
   }

   public IntSet m_6990_() {
      return IntStream.range(0, 65535).filter((p_83863_) -> {
         return !this.f_83840_.contains(p_83863_);
      }).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
   }

   @OnlyIn(Dist.CLIENT)
   class Glyph implements RawGlyph {
      private final int f_83874_;
      private final int f_83875_;
      private final float f_83876_;
      private final float f_83877_;
      private final float f_83878_;
      private final int f_83879_;

      Glyph(int p_83882_, int p_83883_, int p_83884_, int p_83885_, float p_83886_, float p_83887_, int p_83888_) {
         this.f_83874_ = p_83883_ - p_83882_;
         this.f_83875_ = p_83884_ - p_83885_;
         this.f_83878_ = p_83886_ / TrueTypeGlyphProvider.this.f_83839_;
         this.f_83876_ = (p_83887_ + (float)p_83882_ + TrueTypeGlyphProvider.this.f_83841_) / TrueTypeGlyphProvider.this.f_83839_;
         this.f_83877_ = (TrueTypeGlyphProvider.this.f_83844_ - (float)p_83884_ + TrueTypeGlyphProvider.this.f_83842_) / TrueTypeGlyphProvider.this.f_83839_;
         this.f_83879_ = p_83888_;
      }

      public int m_5631_() {
         return this.f_83874_;
      }

      public int m_5629_() {
         return this.f_83875_;
      }

      public float m_5621_() {
         return TrueTypeGlyphProvider.this.f_83839_;
      }

      public float m_7403_() {
         return this.f_83878_;
      }

      public float m_5620_() {
         return this.f_83876_;
      }

      public float m_142672_() {
         return this.f_83877_;
      }

      public void m_6238_(int p_83901_, int p_83902_) {
         NativeImage nativeimage = new NativeImage(NativeImage.Format.LUMINANCE, this.f_83874_, this.f_83875_, false);
         nativeimage.m_85068_(TrueTypeGlyphProvider.this.f_83838_, this.f_83879_, this.f_83874_, this.f_83875_, TrueTypeGlyphProvider.this.f_83843_, TrueTypeGlyphProvider.this.f_83843_, TrueTypeGlyphProvider.this.f_83841_, TrueTypeGlyphProvider.this.f_83842_, 0, 0);
         nativeimage.m_85003_(0, p_83901_, p_83902_, 0, 0, this.f_83874_, this.f_83875_, false, true);
      }

      public boolean m_5633_() {
         return false;
      }
   }
}