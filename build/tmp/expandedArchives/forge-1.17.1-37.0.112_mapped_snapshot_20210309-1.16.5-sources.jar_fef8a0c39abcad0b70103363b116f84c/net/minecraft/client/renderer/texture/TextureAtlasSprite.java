package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.renderer.SpriteCoordinateExpander;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class TextureAtlasSprite implements AutoCloseable, net.minecraftforge.client.extensions.IForgeTextureAtlasSprite {
   private static final Logger f_174721_ = LogManager.getLogger();
   private final TextureAtlas f_118343_;
   private final ResourceLocation f_174722_;
   final int f_174723_;
   final int f_174724_;
   protected final NativeImage[] f_118342_;
   @Nullable
   private final TextureAtlasSprite.AnimatedTexture f_174725_;
   private final int f_118349_;
   private final int f_118350_;
   private final float f_118351_;
   private final float f_118352_;
   private final float f_118353_;
   private final float f_118354_;

   protected TextureAtlasSprite(TextureAtlas p_118358_, TextureAtlasSprite.Info p_118359_, int p_118360_, int p_118361_, int p_118362_, int p_118363_, int p_118364_, NativeImage p_118365_) {
      this.f_118343_ = p_118358_;
      this.f_174723_ = p_118359_.f_118423_;
      this.f_174724_ = p_118359_.f_118424_;
      this.f_174722_ = p_118359_.f_118422_;
      this.f_118349_ = p_118363_;
      this.f_118350_ = p_118364_;
      this.f_118351_ = (float)p_118363_ / (float)p_118361_;
      this.f_118352_ = (float)(p_118363_ + this.f_174723_) / (float)p_118361_;
      this.f_118353_ = (float)p_118364_ / (float)p_118362_;
      this.f_118354_ = (float)(p_118364_ + this.f_174724_) / (float)p_118362_;
      this.f_174725_ = this.m_174729_(p_118359_, p_118365_.m_84982_(), p_118365_.m_85084_(), p_118360_);

      try {
         try {
            this.f_118342_ = MipmapGenerator.m_118054_(p_118365_, p_118360_);
         } catch (Throwable throwable) {
            CrashReport crashreport1 = CrashReport.m_127521_(throwable, "Generating mipmaps for frame");
            CrashReportCategory crashreportcategory1 = crashreport1.m_127514_("Frame being iterated");
            crashreportcategory1.m_128165_("First frame", () -> {
               StringBuilder stringbuilder = new StringBuilder();
               if (stringbuilder.length() > 0) {
                  stringbuilder.append(", ");
               }

               stringbuilder.append(p_118365_.m_84982_()).append("x").append(p_118365_.m_85084_());
               return stringbuilder.toString();
            });
            throw new ReportedException(crashreport1);
         }
      } catch (Throwable throwable1) {
         CrashReport crashreport = CrashReport.m_127521_(throwable1, "Applying mipmap");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Sprite being mipmapped");
         crashreportcategory.m_128165_("Sprite name", this.f_174722_::toString);
         crashreportcategory.m_128165_("Sprite size", () -> {
            return this.f_174723_ + " x " + this.f_174724_;
         });
         crashreportcategory.m_128165_("Sprite frames", () -> {
            return this.m_118415_() + " frames";
         });
         crashreportcategory.m_128159_("Mipmap levels", p_118360_);
         throw new ReportedException(crashreport);
      }
   }

   public int m_118415_() {
      return this.f_174725_ != null ? this.f_174725_.f_174750_.size() : 1;
   }

   @Nullable
   private TextureAtlasSprite.AnimatedTexture m_174729_(TextureAtlasSprite.Info p_174730_, int p_174731_, int p_174732_, int p_174733_) {
      AnimationMetadataSection animationmetadatasection = p_174730_.f_118425_;
      int i = p_174731_ / animationmetadatasection.m_119031_(p_174730_.f_118423_);
      int j = p_174732_ / animationmetadatasection.m_119026_(p_174730_.f_118424_);
      int k = i * j;
      List<TextureAtlasSprite.FrameInfo> list = Lists.newArrayList();
      animationmetadatasection.m_174861_((p_174739_, p_174740_) -> {
         list.add(new TextureAtlasSprite.FrameInfo(p_174739_, p_174740_));
      });
      if (list.isEmpty()) {
         for(int l = 0; l < k; ++l) {
            list.add(new TextureAtlasSprite.FrameInfo(l, animationmetadatasection.m_119030_()));
         }
      } else {
         int i1 = 0;
         IntSet intset = new IntOpenHashSet();

         for(Iterator<TextureAtlasSprite.FrameInfo> iterator = list.iterator(); iterator.hasNext(); ++i1) {
            TextureAtlasSprite.FrameInfo textureatlassprite$frameinfo = iterator.next();
            boolean flag = true;
            if (textureatlassprite$frameinfo.f_174772_ <= 0) {
               f_174721_.warn("Invalid frame duration on sprite {} frame {}: {}", this.f_174722_, i1, textureatlassprite$frameinfo.f_174772_);
               flag = false;
            }

            if (textureatlassprite$frameinfo.f_174771_ < 0 || textureatlassprite$frameinfo.f_174771_ >= k) {
               f_174721_.warn("Invalid frame index on sprite {} frame {}: {}", this.f_174722_, i1, textureatlassprite$frameinfo.f_174771_);
               flag = false;
            }

            if (flag) {
               intset.add(textureatlassprite$frameinfo.f_174771_);
            } else {
               iterator.remove();
            }
         }

         int[] aint = IntStream.range(0, k).filter((p_174736_) -> {
            return !intset.contains(p_174736_);
         }).toArray();
         if (aint.length > 0) {
            f_174721_.warn("Unused frames in sprite {}: {}", this.f_174722_, Arrays.toString(aint));
         }
      }

      if (list.size() <= 1) {
         return null;
      } else {
         TextureAtlasSprite.InterpolationData textureatlassprite$interpolationdata = animationmetadatasection.m_119036_() ? new TextureAtlasSprite.InterpolationData(p_174730_, p_174733_) : null;
         return new TextureAtlasSprite.AnimatedTexture(ImmutableList.copyOf(list), i, textureatlassprite$interpolationdata);
      }
   }

   void m_118375_(int p_118376_, int p_118377_, NativeImage[] p_118378_) {
      for(int i = 0; i < this.f_118342_.length; ++i) {
         if ((this.f_174723_ >> i <= 0) || (this.f_174724_ >> i <= 0)) break;
         p_118378_[i].m_85003_(i, this.f_118349_ >> i, this.f_118350_ >> i, p_118376_ >> i, p_118377_ >> i, this.f_174723_ >> i, this.f_174724_ >> i, this.f_118342_.length > 1, false);
      }

   }

   public int m_174743_() {
      return this.f_118349_;
   }

   public int m_174744_() {
      return this.f_118350_;
   }

   public int m_118405_() {
      return this.f_174723_;
   }

   public int m_118408_() {
      return this.f_174724_;
   }

   public float m_118409_() {
      return this.f_118351_;
   }

   public float m_118410_() {
      return this.f_118352_;
   }

   public float m_118367_(double p_118368_) {
      float f = this.f_118352_ - this.f_118351_;
      return this.f_118351_ + f * (float)p_118368_ / 16.0F;
   }

   public float m_174727_(float p_174728_) {
      float f = this.f_118352_ - this.f_118351_;
      return (p_174728_ - this.f_118351_) / f * 16.0F;
   }

   public float m_118411_() {
      return this.f_118353_;
   }

   public float m_118412_() {
      return this.f_118354_;
   }

   public float m_118393_(double p_118394_) {
      float f = this.f_118354_ - this.f_118353_;
      return this.f_118353_ + f * (float)p_118394_ / 16.0F;
   }

   public float m_174741_(float p_174742_) {
      float f = this.f_118354_ - this.f_118353_;
      return (p_174742_ - this.f_118353_) / f * 16.0F;
   }

   public ResourceLocation m_118413_() {
      return this.f_174722_;
   }

   public TextureAtlas m_118414_() {
      return this.f_118343_;
   }

   public IntStream m_174745_() {
      return this.f_174725_ != null ? this.f_174725_.m_174763_() : IntStream.of(1);
   }

   public void close() {
      for(NativeImage nativeimage : this.f_118342_) {
         if (nativeimage != null) {
            nativeimage.close();
         }
      }

      if (this.f_174725_ != null) {
         this.f_174725_.close();
      }

   }

   public String toString() {
      return "TextureAtlasSprite{name='" + this.f_174722_ + "', frameCount=" + this.m_118415_() + ", x=" + this.f_118349_ + ", y=" + this.f_118350_ + ", height=" + this.f_174724_ + ", width=" + this.f_174723_ + ", u0=" + this.f_118351_ + ", u1=" + this.f_118352_ + ", v0=" + this.f_118353_ + ", v1=" + this.f_118354_ + "}";
   }

   public boolean m_118371_(int p_118372_, int p_118373_, int p_118374_) {
      int i = p_118373_;
      int j = p_118374_;
      if (this.f_174725_ != null) {
         i = p_118373_ + this.f_174725_.m_174759_(p_118372_) * this.f_174723_;
         j = p_118374_ + this.f_174725_.m_174764_(p_118372_) * this.f_174724_;
      }

      return (this.f_118342_[0].m_84985_(i, j) >> 24 & 255) == 0;
   }

   public void m_118416_() {
      if (this.f_174725_ != null) {
         this.f_174725_.m_174758_();
      } else {
         this.m_118375_(0, 0, this.f_118342_);
      }

   }

   private float m_118366_() {
      float f = (float)this.f_174723_ / (this.f_118352_ - this.f_118351_);
      float f1 = (float)this.f_174724_ / (this.f_118354_ - this.f_118353_);
      return Math.max(f1, f);
   }

   public float m_118417_() {
      return 4.0F / this.m_118366_();
   }

   @Nullable
   public Tickable m_174746_() {
      return this.f_174725_;
   }

   public VertexConsumer m_118381_(VertexConsumer p_118382_) {
      return new SpriteCoordinateExpander(p_118382_, this);
   }

   @OnlyIn(Dist.CLIENT)
   class AnimatedTexture implements Tickable, AutoCloseable {
      int f_174748_;
      int f_174749_;
      final List<TextureAtlasSprite.FrameInfo> f_174750_;
      private final int f_174751_;
      @Nullable
      private final TextureAtlasSprite.InterpolationData f_174752_;

      AnimatedTexture(List<TextureAtlasSprite.FrameInfo> p_174755_, @Nullable int p_174756_, TextureAtlasSprite.InterpolationData p_174757_) {
         this.f_174750_ = p_174755_;
         this.f_174751_ = p_174756_;
         this.f_174752_ = p_174757_;
      }

      int m_174759_(int p_174760_) {
         return p_174760_ % this.f_174751_;
      }

      int m_174764_(int p_174765_) {
         return p_174765_ / this.f_174751_;
      }

      private void m_174767_(int p_174768_) {
         int i = this.m_174759_(p_174768_) * TextureAtlasSprite.this.f_174723_;
         int j = this.m_174764_(p_174768_) * TextureAtlasSprite.this.f_174724_;
         TextureAtlasSprite.this.m_118375_(i, j, TextureAtlasSprite.this.f_118342_);
      }

      public void close() {
         if (this.f_174752_ != null) {
            this.f_174752_.close();
         }

      }

      public void m_7673_() {
         ++this.f_174749_;
         TextureAtlasSprite.FrameInfo textureatlassprite$frameinfo = this.f_174750_.get(this.f_174748_);
         if (this.f_174749_ >= textureatlassprite$frameinfo.f_174772_) {
            int i = textureatlassprite$frameinfo.f_174771_;
            this.f_174748_ = (this.f_174748_ + 1) % this.f_174750_.size();
            this.f_174749_ = 0;
            int j = (this.f_174750_.get(this.f_174748_)).f_174771_;
            if (i != j) {
               this.m_174767_(j);
            }
         } else if (this.f_174752_ != null) {
            if (!RenderSystem.m_69586_()) {
               RenderSystem.m_69879_(() -> {
                  this.f_174752_.m_174776_(this);
               });
            } else {
               this.f_174752_.m_174776_(this);
            }
         }

      }

      public void m_174758_() {
         this.m_174767_((this.f_174750_.get(0)).f_174771_);
      }

      public IntStream m_174763_() {
         return this.f_174750_.stream().mapToInt((p_174762_) -> {
            return p_174762_.f_174771_;
         }).distinct();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FrameInfo {
      final int f_174771_;
      final int f_174772_;

      FrameInfo(int p_174774_, int p_174775_) {
         this.f_174771_ = p_174774_;
         this.f_174772_ = p_174775_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Info {
      final ResourceLocation f_118422_;
      final int f_118423_;
      final int f_118424_;
      final AnimationMetadataSection f_118425_;

      public Info(ResourceLocation p_118427_, int p_118428_, int p_118429_, AnimationMetadataSection p_118430_) {
         this.f_118422_ = p_118427_;
         this.f_118423_ = p_118428_;
         this.f_118424_ = p_118429_;
         this.f_118425_ = p_118430_;
      }

      public ResourceLocation m_118431_() {
         return this.f_118422_;
      }

      public int m_118434_() {
         return this.f_118423_;
      }

      public int m_118437_() {
         return this.f_118424_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   final class InterpolationData implements AutoCloseable {
      private final NativeImage[] f_118443_;

      InterpolationData(TextureAtlasSprite.Info p_118446_, int p_118447_) {
         this.f_118443_ = new NativeImage[p_118447_ + 1];

         for(int i = 0; i < this.f_118443_.length; ++i) {
            int j = p_118446_.f_118423_ >> i;
            int k = p_118446_.f_118424_ >> i;
            if (this.f_118443_[i] == null) {
               this.f_118443_[i] = new NativeImage(j, k, false);
            }
         }

      }

      void m_174776_(TextureAtlasSprite.AnimatedTexture p_174777_) {
         TextureAtlasSprite.FrameInfo textureatlassprite$frameinfo = p_174777_.f_174750_.get(p_174777_.f_174748_);
         double d0 = 1.0D - (double)p_174777_.f_174749_ / (double)textureatlassprite$frameinfo.f_174772_;
         int i = textureatlassprite$frameinfo.f_174771_;
         int j = (p_174777_.f_174750_.get((p_174777_.f_174748_ + 1) % p_174777_.f_174750_.size())).f_174771_;
         if (i != j) {
            for(int k = 0; k < this.f_118443_.length; ++k) {
               int l = TextureAtlasSprite.this.f_174723_ >> k;
               int i1 = TextureAtlasSprite.this.f_174724_ >> k;

               for(int j1 = 0; j1 < i1; ++j1) {
                  for(int k1 = 0; k1 < l; ++k1) {
                     int l1 = this.m_174778_(p_174777_, i, k, k1, j1);
                     int i2 = this.m_174778_(p_174777_, j, k, k1, j1);
                     int j2 = this.m_118454_(d0, l1 >> 16 & 255, i2 >> 16 & 255);
                     int k2 = this.m_118454_(d0, l1 >> 8 & 255, i2 >> 8 & 255);
                     int l2 = this.m_118454_(d0, l1 & 255, i2 & 255);
                     this.f_118443_[k].m_84988_(k1, j1, l1 & -16777216 | j2 << 16 | k2 << 8 | l2);
                  }
               }
            }

            TextureAtlasSprite.this.m_118375_(0, 0, this.f_118443_);
         }

      }

      private int m_174778_(TextureAtlasSprite.AnimatedTexture p_174779_, int p_174780_, int p_174781_, int p_174782_, int p_174783_) {
         return TextureAtlasSprite.this.f_118342_[p_174781_].m_84985_(p_174782_ + (p_174779_.m_174759_(p_174780_) * TextureAtlasSprite.this.f_174723_ >> p_174781_), p_174783_ + (p_174779_.m_174764_(p_174780_) * TextureAtlasSprite.this.f_174724_ >> p_174781_));
      }

      private int m_118454_(double p_118455_, int p_118456_, int p_118457_) {
         return (int)(p_118455_ * (double)p_118456_ + (1.0D - p_118455_) * (double)p_118457_);
      }

      public void close() {
         for(NativeImage nativeimage : this.f_118443_) {
            if (nativeimage != null) {
               nativeimage.close();
            }
         }

      }
   }

   // Forge Start
   public int getPixelRGBA(int frameIndex, int x, int y) {
       if (this.f_174725_ != null) {
           x += this.f_174725_.m_174759_(frameIndex) * this.f_174723_;
           y += this.f_174725_.m_174764_(frameIndex) * this.f_174724_;
       }

       return this.f_118342_[0].m_84985_(x, y);
   }
}
