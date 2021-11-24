package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class TheEndBiomeSource extends BiomeSource {
   public static final Codec<TheEndBiomeSource> f_48617_ = RecordCodecBuilder.create((p_48644_) -> {
      return p_48644_.group(RegistryLookupCodec.m_135622_(Registry.f_122885_).forGetter((p_151890_) -> {
         return p_151890_.f_48619_;
      }), Codec.LONG.fieldOf("seed").stable().forGetter((p_151888_) -> {
         return p_151888_.f_48620_;
      })).apply(p_48644_, p_48644_.stable(TheEndBiomeSource::new));
   });
   private static final float f_151885_ = -0.9F;
   public static final int f_151884_ = 64;
   private static final long f_151886_ = 4096L;
   private final SimplexNoise f_48618_;
   private final Registry<Biome> f_48619_;
   private final long f_48620_;
   private final Biome f_48621_;
   private final Biome f_48622_;
   private final Biome f_48623_;
   private final Biome f_48624_;
   private final Biome f_48625_;

   public TheEndBiomeSource(Registry<Biome> p_48628_, long p_48629_) {
      this(p_48628_, p_48629_, p_48628_.m_123013_(Biomes.f_48210_), p_48628_.m_123013_(Biomes.f_48164_), p_48628_.m_123013_(Biomes.f_48163_), p_48628_.m_123013_(Biomes.f_48162_), p_48628_.m_123013_(Biomes.f_48165_));
   }

   private TheEndBiomeSource(Registry<Biome> p_48631_, long p_48632_, Biome p_48633_, Biome p_48634_, Biome p_48635_, Biome p_48636_, Biome p_48637_) {
      super(ImmutableList.of(p_48633_, p_48634_, p_48635_, p_48636_, p_48637_));
      this.f_48619_ = p_48631_;
      this.f_48620_ = p_48632_;
      this.f_48621_ = p_48633_;
      this.f_48622_ = p_48634_;
      this.f_48623_ = p_48635_;
      this.f_48624_ = p_48636_;
      this.f_48625_ = p_48637_;
      WorldgenRandom worldgenrandom = new WorldgenRandom(p_48632_);
      worldgenrandom.m_158876_(17292);
      this.f_48618_ = new SimplexNoise(worldgenrandom);
   }

   protected Codec<? extends BiomeSource> m_5820_() {
      return f_48617_;
   }

   public BiomeSource m_7206_(long p_48640_) {
      return new TheEndBiomeSource(this.f_48619_, p_48640_, this.f_48621_, this.f_48622_, this.f_48623_, this.f_48624_, this.f_48625_);
   }

   public Biome m_7158_(int p_48650_, int p_48651_, int p_48652_) {
      int i = p_48650_ >> 2;
      int j = p_48652_ >> 2;
      if ((long)i * (long)i + (long)j * (long)j <= 4096L) {
         return this.f_48621_;
      } else {
         float f = m_48645_(this.f_48618_, i * 2 + 1, j * 2 + 1);
         if (f > 40.0F) {
            return this.f_48622_;
         } else if (f >= 0.0F) {
            return this.f_48623_;
         } else {
            return f < -20.0F ? this.f_48624_ : this.f_48625_;
         }
      }
   }

   public boolean m_48653_(long p_48654_) {
      return this.f_48620_ == p_48654_;
   }

   public static float m_48645_(SimplexNoise p_48646_, int p_48647_, int p_48648_) {
      int i = p_48647_ / 2;
      int j = p_48648_ / 2;
      int k = p_48647_ % 2;
      int l = p_48648_ % 2;
      float f = 100.0F - Mth.m_14116_((float)(p_48647_ * p_48647_ + p_48648_ * p_48648_)) * 8.0F;
      f = Mth.m_14036_(f, -100.0F, 80.0F);

      for(int i1 = -12; i1 <= 12; ++i1) {
         for(int j1 = -12; j1 <= 12; ++j1) {
            long k1 = (long)(i + i1);
            long l1 = (long)(j + j1);
            if (k1 * k1 + l1 * l1 > 4096L && p_48646_.m_75464_((double)k1, (double)l1) < (double)-0.9F) {
               float f1 = (Mth.m_14154_((float)k1) * 3439.0F + Mth.m_14154_((float)l1) * 147.0F) % 13.0F + 9.0F;
               float f2 = (float)(k - i1 * 2);
               float f3 = (float)(l - j1 * 2);
               float f4 = 100.0F - Mth.m_14116_(f2 * f2 + f3 * f3) * f1;
               f4 = Mth.m_14036_(f4, -100.0F, 80.0F);
               f = Math.max(f, f4);
            }
         }
      }

      return f;
   }
}