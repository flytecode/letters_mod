package net.minecraft.world.level.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeZoomer;
import net.minecraft.world.level.biome.FuzzyOffsetBiomeZoomer;
import net.minecraft.world.level.biome.FuzzyOffsetConstantColumnBiomeZoomer;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class DimensionType {
   public static final int f_156649_ = BlockPos.f_121857_;
   public static final int f_156650_ = 16;
   public static final int f_156651_ = (1 << f_156649_) - 32;
   public static final int f_156652_ = (f_156651_ >> 1) - 1;
   public static final int f_156653_ = f_156652_ - f_156651_ + 1;
   public static final ResourceLocation f_63840_ = new ResourceLocation("overworld");
   public static final ResourceLocation f_63841_ = new ResourceLocation("the_nether");
   public static final ResourceLocation f_63842_ = new ResourceLocation("the_end");
   public static final Codec<DimensionType> f_63843_ = RecordCodecBuilder.<DimensionType>create((p_63914_) -> {
      return p_63914_.group(Codec.LONG.optionalFieldOf("fixed_time").xmap((p_156696_) -> {
         return p_156696_.map(OptionalLong::of).orElseGet(OptionalLong::empty);
      }, (p_156698_) -> {
         return p_156698_.isPresent() ? Optional.of(p_156698_.getAsLong()) : Optional.empty();
      }).forGetter((p_156731_) -> {
         return p_156731_.f_63854_;
      }), Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::m_63935_), Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::m_63946_), Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::m_63951_), Codec.BOOL.fieldOf("natural").forGetter(DimensionType::m_63956_), Codec.doubleRange((double)1.0E-5F, 3.0E7D).fieldOf("coordinate_scale").forGetter(DimensionType::m_63959_), Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionType::m_63960_), Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::m_63961_), Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::m_63962_), Codec.BOOL.fieldOf("has_raids").forGetter(DimensionType::m_63963_), Codec.intRange(f_156653_, f_156652_).fieldOf("min_y").forGetter(DimensionType::m_156732_), Codec.intRange(16, f_156651_).fieldOf("height").forGetter(DimensionType::m_156733_), Codec.intRange(0, f_156651_).fieldOf("logical_height").forGetter(DimensionType::m_63964_), ResourceLocation.f_135803_.fieldOf("infiniburn").forGetter((p_156729_) -> {
         return p_156729_.f_63836_;
      }), ResourceLocation.f_135803_.fieldOf("effects").orElse(f_63840_).forGetter((p_156725_) -> {
         return p_156725_.f_63837_;
      }), Codec.FLOAT.fieldOf("ambient_light").forGetter((p_156721_) -> {
         return p_156721_.f_63838_;
      })).apply(p_63914_, DimensionType::new);
   }).comapFlatMap(DimensionType::m_156718_, Function.identity());
   private static final int f_156654_ = 8;
   public static final float[] f_63844_ = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   public static final ResourceKey<DimensionType> f_63845_ = ResourceKey.m_135785_(Registry.f_122818_, new ResourceLocation("overworld"));
   public static final ResourceKey<DimensionType> f_63846_ = ResourceKey.m_135785_(Registry.f_122818_, new ResourceLocation("the_nether"));
   public static final ResourceKey<DimensionType> f_63847_ = ResourceKey.m_135785_(Registry.f_122818_, new ResourceLocation("the_end"));
   protected static final DimensionType f_63848_ = m_156699_(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, 0, 256, 256, FuzzyOffsetConstantColumnBiomeZoomer.INSTANCE, BlockTags.f_13058_.m_6979_(), f_63840_, 0.0F);
   protected static final DimensionType f_63849_ = m_156699_(OptionalLong.of(18000L), false, true, true, false, 8.0D, false, true, false, true, false, 0, 256, 128, FuzzyOffsetBiomeZoomer.INSTANCE, BlockTags.f_13059_.m_6979_(), f_63841_, 0.1F);
   protected static final DimensionType f_63850_ = m_156699_(OptionalLong.of(6000L), false, false, false, false, 1.0D, true, false, false, false, true, 0, 256, 256, FuzzyOffsetBiomeZoomer.INSTANCE, BlockTags.f_13060_.m_6979_(), f_63842_, 0.0F);
   public static final ResourceKey<DimensionType> f_63851_ = ResourceKey.m_135785_(Registry.f_122818_, new ResourceLocation("overworld_caves"));
   protected static final DimensionType f_63852_ = m_156699_(OptionalLong.empty(), true, true, false, true, 1.0D, false, false, true, false, true, 0, 256, 256, FuzzyOffsetConstantColumnBiomeZoomer.INSTANCE, BlockTags.f_13058_.m_6979_(), f_63840_, 0.0F);
   public static final Codec<Supplier<DimensionType>> f_63853_ = RegistryFileCodec.m_135589_(Registry.f_122818_, f_63843_);
   private final OptionalLong f_63854_;
   private final boolean f_63855_;
   private final boolean f_63856_;
   private final boolean f_63857_;
   private final boolean f_63858_;
   private final double f_63859_;
   private final boolean f_63860_;
   private final boolean f_63861_;
   private final boolean f_63862_;
   private final boolean f_63863_;
   private final boolean f_63864_;
   private final int f_156647_;
   private final int f_156648_;
   private final int f_63865_;
   private final BiomeZoomer f_63835_;
   private final ResourceLocation f_63836_;
   private final ResourceLocation f_63837_;
   private final float f_63838_;
   private final transient float[] f_63839_;

   private static DataResult<DimensionType> m_156718_(DimensionType p_156719_) {
      if (p_156719_.m_156733_() < 16) {
         return DataResult.error("height has to be at least 16");
      } else if (p_156719_.m_156732_() + p_156719_.m_156733_() > f_156652_ + 1) {
         return DataResult.error("min_y + height cannot be higher than: " + (f_156652_ + 1));
      } else if (p_156719_.m_63964_() > p_156719_.m_156733_()) {
         return DataResult.error("logical_height cannot be higher than height");
      } else if (p_156719_.m_156733_() % 16 != 0) {
         return DataResult.error("height has to be multiple of 16");
      } else {
         return p_156719_.m_156732_() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(p_156719_);
      }
   }

   private DimensionType(OptionalLong p_156656_, boolean p_156657_, boolean p_156658_, boolean p_156659_, boolean p_156660_, double p_156661_, boolean p_156662_, boolean p_156663_, boolean p_156664_, boolean p_156665_, int p_156666_, int p_156667_, int p_156668_, ResourceLocation p_156669_, ResourceLocation p_156670_, float p_156671_) {
      this(p_156656_, p_156657_, p_156658_, p_156659_, p_156660_, p_156661_, false, p_156662_, p_156663_, p_156664_, p_156665_, p_156666_, p_156667_, p_156668_, FuzzyOffsetBiomeZoomer.INSTANCE, p_156669_, p_156670_, p_156671_);
   }

   public static DimensionType m_156699_(OptionalLong p_156700_, boolean p_156701_, boolean p_156702_, boolean p_156703_, boolean p_156704_, double p_156705_, boolean p_156706_, boolean p_156707_, boolean p_156708_, boolean p_156709_, boolean p_156710_, int p_156711_, int p_156712_, int p_156713_, BiomeZoomer p_156714_, ResourceLocation p_156715_, ResourceLocation p_156716_, float p_156717_) {
      DimensionType dimensiontype = new DimensionType(p_156700_, p_156701_, p_156702_, p_156703_, p_156704_, p_156705_, p_156706_, p_156707_, p_156708_, p_156709_, p_156710_, p_156711_, p_156712_, p_156713_, p_156714_, p_156715_, p_156716_, p_156717_);
      m_156718_(dimensiontype).error().ifPresent((p_156692_) -> {
         throw new IllegalStateException(p_156692_.message());
      });
      return dimensiontype;
   }

   @Deprecated
   private DimensionType(OptionalLong p_156673_, boolean p_156674_, boolean p_156675_, boolean p_156676_, boolean p_156677_, double p_156678_, boolean p_156679_, boolean p_156680_, boolean p_156681_, boolean p_156682_, boolean p_156683_, int p_156684_, int p_156685_, int p_156686_, BiomeZoomer p_156687_, ResourceLocation p_156688_, ResourceLocation p_156689_, float p_156690_) {
      this.f_63854_ = p_156673_;
      this.f_63855_ = p_156674_;
      this.f_63856_ = p_156675_;
      this.f_63857_ = p_156676_;
      this.f_63858_ = p_156677_;
      this.f_63859_ = p_156678_;
      this.f_63860_ = p_156679_;
      this.f_63861_ = p_156680_;
      this.f_63862_ = p_156681_;
      this.f_63863_ = p_156682_;
      this.f_63864_ = p_156683_;
      this.f_156647_ = p_156684_;
      this.f_156648_ = p_156685_;
      this.f_63865_ = p_156686_;
      this.f_63835_ = p_156687_;
      this.f_63836_ = p_156688_;
      this.f_63837_ = p_156689_;
      this.f_63838_ = p_156690_;
      this.f_63839_ = m_63900_(p_156690_);
   }

   private static float[] m_63900_(float p_63901_) {
      float[] afloat = new float[16];

      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f / (4.0F - 3.0F * f);
         afloat[i] = Mth.m_14179_(p_63901_, f1, 1.0F);
      }

      return afloat;
   }

   @Deprecated
   public static DataResult<ResourceKey<Level>> m_63911_(Dynamic<?> p_63912_) {
      Optional<Number> optional = p_63912_.asNumber().result();
      if (optional.isPresent()) {
         int i = optional.get().intValue();
         if (i == -1) {
            return DataResult.success(Level.f_46429_);
         }

         if (i == 0) {
            return DataResult.success(Level.f_46428_);
         }

         if (i == 1) {
            return DataResult.success(Level.f_46430_);
         }
      }

      return Level.f_46427_.parse(p_63912_);
   }

   public static RegistryAccess.RegistryHolder m_63926_(RegistryAccess.RegistryHolder p_63927_) {
      WritableRegistry<DimensionType> writableregistry = p_63927_.m_175512_(Registry.f_122818_);
      writableregistry.m_7135_(f_63845_, f_63848_, Lifecycle.stable());
      writableregistry.m_7135_(f_63851_, f_63852_, Lifecycle.stable());
      writableregistry.m_7135_(f_63846_, f_63849_, Lifecycle.stable());
      writableregistry.m_7135_(f_63847_, f_63850_, Lifecycle.stable());
      return p_63927_;
   }

   private static ChunkGenerator m_63917_(Registry<Biome> p_63918_, Registry<NoiseGeneratorSettings> p_63919_, long p_63920_) {
      return new NoiseBasedChunkGenerator(new TheEndBiomeSource(p_63918_, p_63920_), p_63920_, () -> {
         return p_63919_.m_123013_(NoiseGeneratorSettings.f_64435_);
      });
   }

   private static ChunkGenerator m_63942_(Registry<Biome> p_63943_, Registry<NoiseGeneratorSettings> p_63944_, long p_63945_) {
      return new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.Preset.f_48512_.m_48529_(p_63943_, p_63945_), p_63945_, () -> {
         return p_63944_.m_123013_(NoiseGeneratorSettings.f_64434_);
      });
   }

   public static MappedRegistry<LevelStem> m_63921_(Registry<DimensionType> p_63922_, Registry<Biome> p_63923_, Registry<NoiseGeneratorSettings> p_63924_, long p_63925_) {
      MappedRegistry<LevelStem> mappedregistry = new MappedRegistry<>(Registry.f_122820_, Lifecycle.experimental());
      mappedregistry.m_7135_(LevelStem.f_63972_, new LevelStem(() -> {
         return p_63922_.m_123013_(f_63846_);
      }, m_63942_(p_63923_, p_63924_, p_63925_)), Lifecycle.stable());
      mappedregistry.m_7135_(LevelStem.f_63973_, new LevelStem(() -> {
         return p_63922_.m_123013_(f_63847_);
      }, m_63917_(p_63923_, p_63924_, p_63925_)), Lifecycle.stable());
      return mappedregistry;
   }

   public static double m_63908_(DimensionType p_63909_, DimensionType p_63910_) {
      double d0 = p_63909_.m_63959_();
      double d1 = p_63910_.m_63959_();
      return d0 / d1;
   }

   @Deprecated
   public String m_63899_() {
      return this.m_63906_(f_63850_) ? "_end" : "";
   }

   public static File m_63932_(ResourceKey<Level> p_63933_, File p_63934_) {
      if (p_63933_ == Level.f_46428_) {
         return p_63934_;
      } else if (p_63933_ == Level.f_46430_) {
         return new File(p_63934_, "DIM1");
      } else {
         return p_63933_ == Level.f_46429_ ? new File(p_63934_, "DIM-1") : new File(p_63934_, "dimensions/" + p_63933_.m_135782_().m_135827_() + "/" + p_63933_.m_135782_().m_135815_());
      }
   }

   public boolean m_63935_() {
      return this.f_63855_;
   }

   public boolean m_63946_() {
      return this.f_63856_;
   }

   public boolean m_63951_() {
      return this.f_63857_;
   }

   public boolean m_63956_() {
      return this.f_63858_;
   }

   public double m_63959_() {
      return this.f_63859_;
   }

   public boolean m_63960_() {
      return this.f_63861_;
   }

   public boolean m_63961_() {
      return this.f_63862_;
   }

   public boolean m_63962_() {
      return this.f_63863_;
   }

   public boolean m_63963_() {
      return this.f_63864_;
   }

   public int m_156732_() {
      return this.f_156647_;
   }

   public int m_156733_() {
      return this.f_156648_;
   }

   public int m_63964_() {
      return this.f_63865_;
   }

   public boolean m_63965_() {
      return this.f_63860_;
   }

   public BiomeZoomer m_63966_() {
      return this.f_63835_;
   }

   public boolean m_63967_() {
      return this.f_63854_.isPresent();
   }

   public float m_63904_(long p_63905_) {
      double d0 = Mth.m_14185_((double)this.f_63854_.orElse(p_63905_) / 24000.0D - 0.25D);
      double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
      return (float)(d0 * 2.0D + d1) / 3.0F;
   }

   public int m_63936_(long p_63937_) {
      return (int)(p_63937_ / 24000L % 8L + 8L) % 8;
   }

   public float m_63902_(int p_63903_) {
      return this.f_63839_[p_63903_];
   }

   public Tag<Block> m_63968_() {
      Tag<Block> tag = BlockTags.m_13115_().m_13404_(this.f_63836_);
      return (Tag<Block>)(tag != null ? tag : BlockTags.f_13058_);
   }

   public ResourceLocation m_63969_() {
      return this.f_63837_;
   }

   public boolean m_63906_(DimensionType p_63907_) {
      if (this == p_63907_) {
         return true;
      } else {
         return this.f_63855_ == p_63907_.f_63855_ && this.f_63856_ == p_63907_.f_63856_ && this.f_63857_ == p_63907_.f_63857_ && this.f_63858_ == p_63907_.f_63858_ && this.f_63859_ == p_63907_.f_63859_ && this.f_63860_ == p_63907_.f_63860_ && this.f_63861_ == p_63907_.f_63861_ && this.f_63862_ == p_63907_.f_63862_ && this.f_63863_ == p_63907_.f_63863_ && this.f_63864_ == p_63907_.f_63864_ && this.f_156647_ == p_63907_.f_156647_ && this.f_156648_ == p_63907_.f_156648_ && this.f_63865_ == p_63907_.f_63865_ && Float.compare(p_63907_.f_63838_, this.f_63838_) == 0 && this.f_63854_.equals(p_63907_.f_63854_) && this.f_63835_.equals(p_63907_.f_63835_) && this.f_63836_.equals(p_63907_.f_63836_) && this.f_63837_.equals(p_63907_.f_63837_);
      }
   }
}