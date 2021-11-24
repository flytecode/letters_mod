package net.minecraft.client.gui.font.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LegacyUnicodeBitmapsProvider implements GlyphProvider {
   static final Logger f_95422_ = LogManager.getLogger();
   private static final int f_169109_ = 256;
   private static final int f_169110_ = 256;
   private static final int f_169111_ = 256;
   private final ResourceManager f_95423_;
   private final byte[] f_95424_;
   private final String f_95425_;
   private final Map<ResourceLocation, NativeImage> f_95426_ = Maps.newHashMap();

   public LegacyUnicodeBitmapsProvider(ResourceManager p_95429_, byte[] p_95430_, String p_95431_) {
      this.f_95423_ = p_95429_;
      this.f_95424_ = p_95430_;
      this.f_95425_ = p_95431_;

      for(int i = 0; i < 256; ++i) {
         int j = i * 256;
         ResourceLocation resourcelocation = this.m_95442_(j);

         try {
            Resource resource = this.f_95423_.m_142591_(resourcelocation);

            label90: {
               try {
                  NativeImage nativeimage;
                  label104: {
                     nativeimage = NativeImage.m_85048_(NativeImage.Format.RGBA, resource.m_6679_());

                     try {
                        if (nativeimage.m_84982_() == 256 && nativeimage.m_85084_() == 256) {
                           int k = 0;

                           while(true) {
                              if (k >= 256) {
                                 break label104;
                              }

                              byte b0 = p_95430_[j + k];
                              if (b0 != 0 && m_95433_(b0) > m_95440_(b0)) {
                                 p_95430_[j + k] = 0;
                              }

                              ++k;
                           }
                        }
                     } catch (Throwable throwable2) {
                        if (nativeimage != null) {
                           try {
                              nativeimage.close();
                           } catch (Throwable throwable1) {
                              throwable2.addSuppressed(throwable1);
                           }
                        }

                        throw throwable2;
                     }

                     if (nativeimage != null) {
                        nativeimage.close();
                     }
                     break label90;
                  }

                  if (nativeimage != null) {
                     nativeimage.close();
                  }
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
               continue;
            }

            if (resource != null) {
               resource.close();
            }
         } catch (IOException ioexception) {
         }

         Arrays.fill(p_95430_, j, j + 256, (byte)0);
      }

   }

   public void close() {
      this.f_95426_.values().forEach(NativeImage::close);
   }

   private ResourceLocation m_95442_(int p_95443_) {
      ResourceLocation resourcelocation = new ResourceLocation(String.format(this.f_95425_, String.format("%02x", p_95443_ / 256)));
      return new ResourceLocation(resourcelocation.m_135827_(), "textures/" + resourcelocation.m_135815_());
   }

   @Nullable
   public RawGlyph m_7823_(int p_95436_) {
      if (p_95436_ >= 0 && p_95436_ <= 65535) {
         byte b0 = this.f_95424_[p_95436_];
         if (b0 != 0) {
            NativeImage nativeimage = this.f_95426_.computeIfAbsent(this.m_95442_(p_95436_), this::m_95437_);
            if (nativeimage != null) {
               int i = m_95433_(b0);
               return new LegacyUnicodeBitmapsProvider.Glyph(p_95436_ % 16 * 16 + i, (p_95436_ & 255) / 16 * 16, m_95440_(b0) - i, 16, nativeimage);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public IntSet m_6990_() {
      IntSet intset = new IntOpenHashSet();

      for(int i = 0; i < 65535; ++i) {
         if (this.f_95424_[i] != 0) {
            intset.add(i);
         }
      }

      return intset;
   }

   @Nullable
   private NativeImage m_95437_(ResourceLocation p_95438_) {
      try {
         Resource resource = this.f_95423_.m_142591_(p_95438_);

         NativeImage nativeimage;
         try {
            nativeimage = NativeImage.m_85048_(NativeImage.Format.RGBA, resource.m_6679_());
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

         return nativeimage;
      } catch (IOException ioexception) {
         f_95422_.error("Couldn't load texture {}", p_95438_, ioexception);
         return null;
      }
   }

   private static int m_95433_(byte p_95434_) {
      return p_95434_ >> 4 & 15;
   }

   private static int m_95440_(byte p_95441_) {
      return (p_95441_ & 15) + 1;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder implements GlyphProviderBuilder {
      private final ResourceLocation f_95445_;
      private final String f_95446_;

      public Builder(ResourceLocation p_95448_, String p_95449_) {
         this.f_95445_ = p_95448_;
         this.f_95446_ = p_95449_;
      }

      public static GlyphProviderBuilder m_95452_(JsonObject p_95453_) {
         return new LegacyUnicodeBitmapsProvider.Builder(new ResourceLocation(GsonHelper.m_13906_(p_95453_, "sizes")), m_182569_(p_95453_));
      }

      private static String m_182569_(JsonObject p_182570_) {
         String s = GsonHelper.m_13906_(p_182570_, "template");

         try {
            String.format(s, "");
            return s;
         } catch (IllegalFormatException illegalformatexception) {
            throw new JsonParseException("Invalid legacy unicode template supplied, expected single '%s': " + s);
         }
      }

      @Nullable
      public GlyphProvider m_6762_(ResourceManager p_95451_) {
         try {
            Resource resource = Minecraft.m_91087_().m_91098_().m_142591_(this.f_95445_);

            LegacyUnicodeBitmapsProvider legacyunicodebitmapsprovider;
            try {
               byte[] abyte = new byte[65536];
               resource.m_6679_().read(abyte);
               legacyunicodebitmapsprovider = new LegacyUnicodeBitmapsProvider(p_95451_, abyte, this.f_95446_);
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

            return legacyunicodebitmapsprovider;
         } catch (IOException ioexception) {
            LegacyUnicodeBitmapsProvider.f_95422_.error("Cannot load {}, unicode glyphs will not render correctly", (Object)this.f_95445_);
            return null;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Glyph implements RawGlyph {
      private final int f_95454_;
      private final int f_95455_;
      private final int f_95456_;
      private final int f_95457_;
      private final NativeImage f_95458_;

      Glyph(int p_95460_, int p_95461_, int p_95462_, int p_95463_, NativeImage p_95464_) {
         this.f_95454_ = p_95462_;
         this.f_95455_ = p_95463_;
         this.f_95456_ = p_95460_;
         this.f_95457_ = p_95461_;
         this.f_95458_ = p_95464_;
      }

      public float m_5621_() {
         return 2.0F;
      }

      public int m_5631_() {
         return this.f_95454_;
      }

      public int m_5629_() {
         return this.f_95455_;
      }

      public float m_7403_() {
         return (float)(this.f_95454_ / 2 + 1);
      }

      public void m_6238_(int p_95473_, int p_95474_) {
         this.f_95458_.m_85003_(0, p_95473_, p_95474_, this.f_95456_, this.f_95457_, this.f_95454_, this.f_95455_, false, false);
      }

      public boolean m_5633_() {
         return this.f_95458_.m_85102_().m_85161_() > 1;
      }

      public float m_5645_() {
         return 0.5F;
      }

      public float m_5619_() {
         return 0.5F;
      }
   }
}