package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class MultiNoiseBiomeSource extends BiomeSource {
   private static final MultiNoiseBiomeSource.NoiseParameters f_48426_ = new MultiNoiseBiomeSource.NoiseParameters(-7, ImmutableList.of(1.0D, 1.0D));
   public static final MapCodec<MultiNoiseBiomeSource> f_48424_ = RecordCodecBuilder.mapCodec((p_48477_) -> {
      return p_48477_.group(Codec.LONG.fieldOf("seed").forGetter((p_151852_) -> {
         return p_151852_.f_48437_;
      }), RecordCodecBuilder.<Pair<Biome.ClimateParameters, Supplier<Biome>>>create((p_151838_) -> {
         return p_151838_.group(Biome.ClimateParameters.f_47649_.fieldOf("parameters").forGetter(Pair::getFirst), Biome.f_47431_.fieldOf("biome").forGetter(Pair::getSecond)).apply(p_151838_, Pair::of);
      }).listOf().fieldOf("biomes").forGetter((p_151850_) -> {
         return p_151850_.f_48435_;
      }), MultiNoiseBiomeSource.NoiseParameters.f_48501_.fieldOf("temperature_noise").forGetter((p_151848_) -> {
         return p_151848_.f_48427_;
      }), MultiNoiseBiomeSource.NoiseParameters.f_48501_.fieldOf("humidity_noise").forGetter((p_151846_) -> {
         return p_151846_.f_48428_;
      }), MultiNoiseBiomeSource.NoiseParameters.f_48501_.fieldOf("altitude_noise").forGetter((p_151844_) -> {
         return p_151844_.f_48429_;
      }), MultiNoiseBiomeSource.NoiseParameters.f_48501_.fieldOf("weirdness_noise").forGetter((p_151842_) -> {
         return p_151842_.f_48430_;
      })).apply(p_48477_, MultiNoiseBiomeSource::new);
   });
   public static final Codec<MultiNoiseBiomeSource> f_48425_ = Codec.mapEither(MultiNoiseBiomeSource.PresetInstance.f_48540_, f_48424_).xmap((p_48473_) -> {
      return p_48473_.map(MultiNoiseBiomeSource.PresetInstance::m_48565_, Function.identity());
   }, (p_48471_) -> {
      return p_48471_.m_48490_().map(Either::<MultiNoiseBiomeSource.PresetInstance, MultiNoiseBiomeSource>left).orElseGet(() -> {
         return Either.right(p_48471_);
      });
   }).codec();
   private final MultiNoiseBiomeSource.NoiseParameters f_48427_;
   private final MultiNoiseBiomeSource.NoiseParameters f_48428_;
   private final MultiNoiseBiomeSource.NoiseParameters f_48429_;
   private final MultiNoiseBiomeSource.NoiseParameters f_48430_;
   private final NormalNoise f_48431_;
   private final NormalNoise f_48432_;
   private final NormalNoise f_48433_;
   private final NormalNoise f_48434_;
   private final List<Pair<Biome.ClimateParameters, Supplier<Biome>>> f_48435_;
   private final boolean f_48436_;
   private final long f_48437_;
   private final Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> f_48438_;

   public MultiNoiseBiomeSource(long p_151828_, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> p_151829_) {
      this(p_151828_, p_151829_, Optional.empty());
   }

   MultiNoiseBiomeSource(long p_48456_, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> p_48457_, Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> p_48458_) {
      this(p_48456_, p_48457_, f_48426_, f_48426_, f_48426_, f_48426_, p_48458_);
   }

   private MultiNoiseBiomeSource(long p_48441_, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> p_48442_, MultiNoiseBiomeSource.NoiseParameters p_48443_, MultiNoiseBiomeSource.NoiseParameters p_48444_, MultiNoiseBiomeSource.NoiseParameters p_48445_, MultiNoiseBiomeSource.NoiseParameters p_48446_) {
      this(p_48441_, p_48442_, p_48443_, p_48444_, p_48445_, p_48446_, Optional.empty());
   }

   private MultiNoiseBiomeSource(long p_48448_, List<Pair<Biome.ClimateParameters, Supplier<Biome>>> p_48449_, MultiNoiseBiomeSource.NoiseParameters p_48450_, MultiNoiseBiomeSource.NoiseParameters p_48451_, MultiNoiseBiomeSource.NoiseParameters p_48452_, MultiNoiseBiomeSource.NoiseParameters p_48453_, Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> p_48454_) {
      super(p_48449_.stream().map(Pair::getSecond));
      this.f_48437_ = p_48448_;
      this.f_48438_ = p_48454_;
      this.f_48427_ = p_48450_;
      this.f_48428_ = p_48451_;
      this.f_48429_ = p_48452_;
      this.f_48430_ = p_48453_;
      this.f_48431_ = NormalNoise.m_164350_(new WorldgenRandom(p_48448_), p_48450_.m_48508_(), p_48450_.m_48511_());
      this.f_48432_ = NormalNoise.m_164350_(new WorldgenRandom(p_48448_ + 1L), p_48451_.m_48508_(), p_48451_.m_48511_());
      this.f_48433_ = NormalNoise.m_164350_(new WorldgenRandom(p_48448_ + 2L), p_48452_.m_48508_(), p_48452_.m_48511_());
      this.f_48434_ = NormalNoise.m_164350_(new WorldgenRandom(p_48448_ + 3L), p_48453_.m_48508_(), p_48453_.m_48511_());
      this.f_48435_ = p_48449_;
      this.f_48436_ = false;
   }

   public static MultiNoiseBiomeSource m_151832_(Registry<Biome> p_151833_, long p_151834_) {
      ImmutableList<Pair<Biome.ClimateParameters, Supplier<Biome>>> immutablelist = m_151830_(p_151833_);
      MultiNoiseBiomeSource.NoiseParameters multinoisebiomesource$noiseparameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 0.0D, 3.0D, 3.0D, 3.0D, 3.0D);
      MultiNoiseBiomeSource.NoiseParameters multinoisebiomesource$noiseparameters1 = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 4.0D, 4.0D);
      MultiNoiseBiomeSource.NoiseParameters multinoisebiomesource$noiseparameters2 = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D);
      MultiNoiseBiomeSource.NoiseParameters multinoisebiomesource$noiseparameters3 = new MultiNoiseBiomeSource.NoiseParameters(-8, 1.2D, 0.6D, 0.0D, 0.0D, 1.0D, 0.0D);
      return new MultiNoiseBiomeSource(p_151834_, immutablelist, multinoisebiomesource$noiseparameters, multinoisebiomesource$noiseparameters1, multinoisebiomesource$noiseparameters2, multinoisebiomesource$noiseparameters3, Optional.empty());
   }

   protected Codec<? extends BiomeSource> m_5820_() {
      return f_48425_;
   }

   public BiomeSource m_7206_(long p_48466_) {
      return new MultiNoiseBiomeSource(p_48466_, this.f_48435_, this.f_48427_, this.f_48428_, this.f_48429_, this.f_48430_, this.f_48438_);
   }

   private Optional<MultiNoiseBiomeSource.PresetInstance> m_48490_() {
      return this.f_48438_.map((p_48475_) -> {
         return new MultiNoiseBiomeSource.PresetInstance(p_48475_.getSecond(), p_48475_.getFirst(), this.f_48437_);
      });
   }

   public Biome m_7158_(int p_48479_, int p_48480_, int p_48481_) {
      int i = this.f_48436_ ? p_48480_ : 0;
      Biome.ClimateParameters biome$climateparameters = new Biome.ClimateParameters((float)this.f_48431_.m_75380_((double)p_48479_, (double)i, (double)p_48481_), (float)this.f_48432_.m_75380_((double)p_48479_, (double)i, (double)p_48481_), (float)this.f_48433_.m_75380_((double)p_48479_, (double)i, (double)p_48481_), (float)this.f_48434_.m_75380_((double)p_48479_, (double)i, (double)p_48481_), 0.0F);
      return this.f_48435_.stream().min(Comparator.comparing((p_48469_) -> {
         return p_48469_.getFirst().m_47662_(biome$climateparameters);
      })).map(Pair::getSecond).map(Supplier::get).orElse(net.minecraft.data.worldgen.biome.Biomes.f_127322_);
   }

   public static ImmutableList<Pair<Biome.ClimateParameters, Supplier<Biome>>> m_151830_(Registry<Biome> p_151831_) {
      return ImmutableList.of(Pair.of(new Biome.ClimateParameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
         return p_151831_.m_123013_(Biomes.f_48202_);
      }));
   }

   public boolean m_48482_(long p_48483_) {
      return this.f_48437_ == p_48483_ && this.f_48438_.isPresent() && Objects.equals(this.f_48438_.get().getSecond(), MultiNoiseBiomeSource.Preset.f_48512_);
   }

   static class NoiseParameters {
      private final int f_48502_;
      private final DoubleList f_48503_;
      public static final Codec<MultiNoiseBiomeSource.NoiseParameters> f_48501_ = RecordCodecBuilder.create((p_48510_) -> {
         return p_48510_.group(Codec.INT.fieldOf("firstOctave").forGetter(MultiNoiseBiomeSource.NoiseParameters::m_48508_), Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(MultiNoiseBiomeSource.NoiseParameters::m_48511_)).apply(p_48510_, MultiNoiseBiomeSource.NoiseParameters::new);
      });

      public NoiseParameters(int p_48506_, List<Double> p_48507_) {
         this.f_48502_ = p_48506_;
         this.f_48503_ = new DoubleArrayList(p_48507_);
      }

      public NoiseParameters(int p_151854_, double... p_151855_) {
         this.f_48502_ = p_151854_;
         this.f_48503_ = new DoubleArrayList(p_151855_);
      }

      public int m_48508_() {
         return this.f_48502_;
      }

      public DoubleList m_48511_() {
         return this.f_48503_;
      }
   }

   public static class Preset {
      static final Map<ResourceLocation, MultiNoiseBiomeSource.Preset> f_48513_ = Maps.newHashMap();
      public static final MultiNoiseBiomeSource.Preset f_48512_ = new MultiNoiseBiomeSource.Preset(new ResourceLocation("nether"), (p_48524_, p_48525_, p_48526_) -> {
         return new MultiNoiseBiomeSource(p_48526_, ImmutableList.of(Pair.of(new Biome.ClimateParameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
            return p_48525_.m_123013_(Biomes.f_48209_);
         }), Pair.of(new Biome.ClimateParameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F), () -> {
            return p_48525_.m_123013_(Biomes.f_48199_);
         }), Pair.of(new Biome.ClimateParameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F), () -> {
            return p_48525_.m_123013_(Biomes.f_48200_);
         }), Pair.of(new Biome.ClimateParameters(0.0F, 0.5F, 0.0F, 0.0F, 0.375F), () -> {
            return p_48525_.m_123013_(Biomes.f_48201_);
         }), Pair.of(new Biome.ClimateParameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F), () -> {
            return p_48525_.m_123013_(Biomes.f_48175_);
         })), Optional.of(Pair.of(p_48525_, p_48524_)));
      });
      final ResourceLocation f_48514_;
      private final Function3<MultiNoiseBiomeSource.Preset, Registry<Biome>, Long, MultiNoiseBiomeSource> f_48515_;

      public Preset(ResourceLocation p_48518_, Function3<MultiNoiseBiomeSource.Preset, Registry<Biome>, Long, MultiNoiseBiomeSource> p_48519_) {
         this.f_48514_ = p_48518_;
         this.f_48515_ = p_48519_;
         f_48513_.put(p_48518_, this);
      }

      public MultiNoiseBiomeSource m_48529_(Registry<Biome> p_48530_, long p_48531_) {
         return this.f_48515_.apply(this, p_48530_, p_48531_);
      }
   }

   static final class PresetInstance {
      public static final MapCodec<MultiNoiseBiomeSource.PresetInstance> f_48540_ = RecordCodecBuilder.mapCodec((p_48558_) -> {
         return p_48558_.group(ResourceLocation.f_135803_.flatXmap((p_151869_) -> {
            return Optional.ofNullable(MultiNoiseBiomeSource.Preset.f_48513_.get(p_151869_)).map(DataResult::success).orElseGet(() -> {
               return DataResult.error("Unknown preset: " + p_151869_);
            });
         }, (p_151867_) -> {
            return DataResult.success(p_151867_.f_48514_);
         }).fieldOf("preset").stable().forGetter(MultiNoiseBiomeSource.PresetInstance::m_48554_), RegistryLookupCodec.m_135622_(Registry.f_122885_).forGetter(MultiNoiseBiomeSource.PresetInstance::m_48561_), Codec.LONG.fieldOf("seed").stable().forGetter(MultiNoiseBiomeSource.PresetInstance::m_48564_)).apply(p_48558_, p_48558_.stable(MultiNoiseBiomeSource.PresetInstance::new));
      });
      private final MultiNoiseBiomeSource.Preset f_48541_;
      private final Registry<Biome> f_48542_;
      private final long f_48543_;

      PresetInstance(MultiNoiseBiomeSource.Preset p_48546_, Registry<Biome> p_48547_, long p_48548_) {
         this.f_48541_ = p_48546_;
         this.f_48542_ = p_48547_;
         this.f_48543_ = p_48548_;
      }

      public MultiNoiseBiomeSource.Preset m_48554_() {
         return this.f_48541_;
      }

      public Registry<Biome> m_48561_() {
         return this.f_48542_;
      }

      public long m_48564_() {
         return this.f_48543_;
      }

      public MultiNoiseBiomeSource m_48565_() {
         return this.f_48541_.m_48529_(this.f_48542_, this.f_48543_);
      }
   }
}