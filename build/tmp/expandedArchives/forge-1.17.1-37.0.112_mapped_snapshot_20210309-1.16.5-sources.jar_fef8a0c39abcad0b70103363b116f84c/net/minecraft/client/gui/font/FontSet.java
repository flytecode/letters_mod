package net.minecraft.client.gui.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.RawGlyph;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.gui.font.glyphs.MissingGlyph;
import net.minecraft.client.gui.font.glyphs.WhiteGlyph;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FontSet implements AutoCloseable {
   private static final EmptyGlyph f_95048_ = new EmptyGlyph();
   private static final GlyphInfo f_95049_ = () -> {
      return 4.0F;
   };
   private static final Random f_95050_ = new Random();
   private final TextureManager f_95051_;
   private final ResourceLocation f_95052_;
   private BakedGlyph f_95053_;
   private BakedGlyph f_95054_;
   private final List<GlyphProvider> f_95055_ = Lists.newArrayList();
   private final Int2ObjectMap<BakedGlyph> f_95056_ = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<GlyphInfo> f_95057_ = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<IntList> f_95058_ = new Int2ObjectOpenHashMap<>();
   private final List<FontTexture> f_95059_ = Lists.newArrayList();

   public FontSet(TextureManager p_95062_, ResourceLocation p_95063_) {
      this.f_95051_ = p_95062_;
      this.f_95052_ = p_95063_;
   }

   public void m_95071_(List<GlyphProvider> p_95072_) {
      this.m_95077_();
      this.m_95080_();
      this.f_95056_.clear();
      this.f_95057_.clear();
      this.f_95058_.clear();
      this.f_95053_ = this.m_95069_(MissingGlyph.INSTANCE);
      this.f_95054_ = this.m_95069_(WhiteGlyph.INSTANCE);
      IntSet intset = new IntOpenHashSet();

      for(GlyphProvider glyphprovider : p_95072_) {
         intset.addAll(glyphprovider.m_6990_());
      }

      Set<GlyphProvider> set = Sets.newHashSet();
      intset.forEach((int p_95076_) -> {
         for(GlyphProvider glyphprovider1 : p_95072_) {
            GlyphInfo glyphinfo = (GlyphInfo)(p_95076_ == 32 ? f_95049_ : glyphprovider1.m_7823_(p_95076_));
            if (glyphinfo != null) {
               set.add(glyphprovider1);
               if (glyphinfo != MissingGlyph.INSTANCE) {
                  this.f_95058_.computeIfAbsent(Mth.m_14167_(glyphinfo.m_83827_(false)), (p_169091_) -> {
                     return new IntArrayList();
                  }).add(p_95076_);
               }
               break;
            }
         }

      });
      p_95072_.stream().filter(set::contains).forEach(this.f_95055_::add);
   }

   public void close() {
      this.m_95077_();
      this.m_95080_();
   }

   private void m_95077_() {
      for(GlyphProvider glyphprovider : this.f_95055_) {
         glyphprovider.close();
      }

      this.f_95055_.clear();
   }

   private void m_95080_() {
      for(FontTexture fonttexture : this.f_95059_) {
         fonttexture.close();
      }

      this.f_95059_.clear();
   }

   public GlyphInfo m_95065_(int p_95066_) {
      return this.f_95057_.computeIfAbsent(p_95066_, (p_95088_) -> {
         return (GlyphInfo)(p_95088_ == 32 ? f_95049_ : this.m_95081_(p_95088_));
      });
   }

   private RawGlyph m_95081_(int p_95082_) {
      for(GlyphProvider glyphprovider : this.f_95055_) {
         RawGlyph rawglyph = glyphprovider.m_7823_(p_95082_);
         if (rawglyph != null) {
            return rawglyph;
         }
      }

      return MissingGlyph.INSTANCE;
   }

   public BakedGlyph m_95078_(int p_95079_) {
      return this.f_95056_.computeIfAbsent(p_95079_, (p_95086_) -> {
         return (BakedGlyph)(p_95086_ == 32 ? f_95048_ : this.m_95069_(this.m_95081_(p_95086_)));
      });
   }

   private BakedGlyph m_95069_(RawGlyph p_95070_) {
      for(FontTexture fonttexture : this.f_95059_) {
         BakedGlyph bakedglyph = fonttexture.m_95102_(p_95070_);
         if (bakedglyph != null) {
            return bakedglyph;
         }
      }

      FontTexture fonttexture1 = new FontTexture(new ResourceLocation(this.f_95052_.m_135827_(), this.f_95052_.m_135815_() + "/" + this.f_95059_.size()), p_95070_.m_5633_());
      this.f_95059_.add(fonttexture1);
      this.f_95051_.m_118495_(fonttexture1.m_95099_(), fonttexture1);
      BakedGlyph bakedglyph1 = fonttexture1.m_95102_(p_95070_);
      return bakedglyph1 == null ? this.f_95053_ : bakedglyph1;
   }

   public BakedGlyph m_95067_(GlyphInfo p_95068_) {
      IntList intlist = this.f_95058_.get(Mth.m_14167_(p_95068_.m_83827_(false)));
      return intlist != null && !intlist.isEmpty() ? this.m_95078_(intlist.getInt(f_95050_.nextInt(intlist.size()))) : this.f_95053_;
   }

   public BakedGlyph m_95064_() {
      return this.f_95054_;
   }
}