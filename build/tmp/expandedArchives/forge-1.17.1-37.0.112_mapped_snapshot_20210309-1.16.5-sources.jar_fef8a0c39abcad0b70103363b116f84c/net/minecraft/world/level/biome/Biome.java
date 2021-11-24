package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome extends net.minecraftforge.registries.ForgeRegistryEntry.UncheckedRegistryEntry<Biome> {
   public static final Logger f_47428_ = LogManager.getLogger();
   public static final Codec<Biome> f_47429_ = RecordCodecBuilder.create((p_47527_) -> {
      return p_47527_.group(Biome.ClimateSettings.f_47679_.forGetter((p_151717_) -> {
         return p_151717_.f_47437_;
      }), Biome.BiomeCategory.f_47631_.fieldOf("category").forGetter((p_151715_) -> {
         return p_151715_.f_47442_;
      }), Codec.FLOAT.fieldOf("depth").forGetter((p_151713_) -> {
         return p_151713_.f_47440_;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_151711_) -> {
         return p_151711_.f_47441_;
      }), BiomeSpecialEffects.f_47926_.fieldOf("effects").forGetter((p_151709_) -> {
         return p_151709_.f_47443_;
      }), BiomeGenerationSettings.f_47778_.forGetter((p_151707_) -> {
         return p_151707_.f_47438_;
      }), MobSpawnSettings.f_48327_.forGetter((p_151705_) -> {
         return p_151705_.f_47439_;
      }), ResourceLocation.f_135803_.optionalFieldOf("forge:registry_name").forGetter(b -> Optional.ofNullable(b.getRegistryName())))
      .apply(p_47527_, (climate, category, depth, scale, effects, gen, spawns, name) ->
          net.minecraftforge.common.ForgeHooks.enhanceBiome(name.orElse(null), climate, category, depth, scale, effects, gen, spawns, p_47527_, Biome::new));
   });
   public static final Codec<Biome> f_47430_ = RecordCodecBuilder.create((p_47504_) -> {
      return p_47504_.group(Biome.ClimateSettings.f_47679_.forGetter((p_151703_) -> {
         return p_151703_.f_47437_;
      }), Biome.BiomeCategory.f_47631_.fieldOf("category").forGetter((p_151701_) -> {
         return p_151701_.f_47442_;
      }), Codec.FLOAT.fieldOf("depth").forGetter((p_151699_) -> {
         return p_151699_.f_47440_;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_151695_) -> {
         return p_151695_.f_47441_;
      }), BiomeSpecialEffects.f_47926_.fieldOf("effects").forGetter((p_151675_) -> {
         return p_151675_.f_47443_;
      })).apply(p_47504_, (p_151669_, p_151670_, p_151671_, p_151672_, p_151673_) -> {
         return new Biome(p_151669_, p_151670_, p_151671_, p_151672_, p_151673_, BiomeGenerationSettings.f_47777_, MobSpawnSettings.f_48326_);
      });
   });
   public static final Codec<Supplier<Biome>> f_47431_ = RegistryFileCodec.m_135589_(Registry.f_122885_, f_47429_);
   public static final Codec<List<Supplier<Biome>>> f_47432_ = RegistryFileCodec.m_135600_(Registry.f_122885_, f_47429_);
   private final Map<Integer, List<StructureFeature<?>>> f_47434_ = Registry.f_122841_.m_123024_().collect(Collectors.groupingBy((p_47525_) -> {
      return p_47525_.m_67095_().ordinal();
   }));
   private static final PerlinSimplexNoise f_47435_ = new PerlinSimplexNoise(new WorldgenRandom(1234L), ImmutableList.of(0));
   static final PerlinSimplexNoise f_47436_ = new PerlinSimplexNoise(new WorldgenRandom(3456L), ImmutableList.of(-2, -1, 0));
   public static final PerlinSimplexNoise f_47433_ = new PerlinSimplexNoise(new WorldgenRandom(2345L), ImmutableList.of(0));
   private static final int f_151655_ = 1024;
   private final Biome.ClimateSettings f_47437_;
   private final BiomeGenerationSettings f_47438_;
   private final MobSpawnSettings f_47439_;
   private final float f_47440_;
   private final float f_47441_;
   private final Biome.BiomeCategory f_47442_;
   private final BiomeSpecialEffects f_47443_;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> f_47444_ = ThreadLocal.withInitial(() -> {
      return Util.m_137537_(() -> {
         Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int p_47580_) {
            }
         };
         long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);
         return long2floatlinkedopenhashmap;
      });
   });

   Biome(Biome.ClimateSettings p_47447_, Biome.BiomeCategory p_47448_, float p_47449_, float p_47450_, BiomeSpecialEffects p_47451_, BiomeGenerationSettings p_47452_, MobSpawnSettings p_47453_) {
      this.f_47437_ = p_47447_;
      this.f_47438_ = p_47452_;
      this.f_47439_ = p_47453_;
      this.f_47442_ = p_47448_;
      this.f_47440_ = p_47449_;
      this.f_47441_ = p_47450_;
      this.f_47443_ = p_47451_;
   }

   public int m_47463_() {
      return this.f_47443_.m_47978_();
   }

   public MobSpawnSettings m_47518_() {
      return this.f_47439_;
   }

   public Biome.Precipitation m_47530_() {
      return this.f_47437_.f_47680_;
   }

   public boolean m_47533_() {
      return this.m_47548_() > 0.85F;
   }

   private float m_47528_(BlockPos p_47529_) {
      float f = this.f_47437_.f_47682_.m_8117_(p_47529_, this.m_47554_());
      if (p_47529_.m_123342_() > 64) {
         float f1 = (float)(f_47435_.m_75449_((double)((float)p_47529_.m_123341_() / 8.0F), (double)((float)p_47529_.m_123343_() / 8.0F), false) * 4.0D);
         return f - (f1 + (float)p_47529_.m_123342_() - 64.0F) * 0.05F / 30.0F;
      } else {
         return f;
      }
   }

   public final float m_47505_(BlockPos p_47506_) {
      long i = p_47506_.m_121878_();
      Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = this.f_47444_.get();
      float f = long2floatlinkedopenhashmap.get(i);
      if (!Float.isNaN(f)) {
         return f;
      } else {
         float f1 = this.m_47528_(p_47506_);
         if (long2floatlinkedopenhashmap.size() == 1024) {
            long2floatlinkedopenhashmap.removeFirstFloat();
         }

         long2floatlinkedopenhashmap.put(i, f1);
         return f1;
      }
   }

   public boolean m_47477_(LevelReader p_47478_, BlockPos p_47479_) {
      return this.m_47480_(p_47478_, p_47479_, true);
   }

   public boolean m_47480_(LevelReader p_47481_, BlockPos p_47482_, boolean p_47483_) {
      if (this.m_47505_(p_47482_) >= 0.15F) {
         return false;
      } else {
         if (p_47482_.m_123342_() >= p_47481_.m_141937_() && p_47482_.m_123342_() < p_47481_.m_151558_() && p_47481_.m_45517_(LightLayer.BLOCK, p_47482_) < 10) {
            BlockState blockstate = p_47481_.m_8055_(p_47482_);
            FluidState fluidstate = p_47481_.m_6425_(p_47482_);
            if (fluidstate.m_76152_() == Fluids.f_76193_ && blockstate.m_60734_() instanceof LiquidBlock) {
               if (!p_47483_) {
                  return true;
               }

               boolean flag = p_47481_.m_46801_(p_47482_.m_142125_()) && p_47481_.m_46801_(p_47482_.m_142126_()) && p_47481_.m_46801_(p_47482_.m_142127_()) && p_47481_.m_46801_(p_47482_.m_142128_());
               if (!flag) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean m_151696_(BlockPos p_151697_) {
      return this.m_47505_(p_151697_) < 0.15F;
   }

   public boolean m_47519_(LevelReader p_47520_, BlockPos p_47521_) {
      if (!this.m_151696_(p_47521_)) {
         return false;
      } else {
         if (p_47521_.m_123342_() >= p_47520_.m_141937_() && p_47521_.m_123342_() < p_47520_.m_151558_() && p_47520_.m_45517_(LightLayer.BLOCK, p_47521_) < 10) {
            BlockState blockstate = p_47520_.m_8055_(p_47521_);
            if (blockstate.m_60795_() && Blocks.f_50125_.m_49966_().m_60710_(p_47520_, p_47521_)) {
               return true;
            }
         }

         return false;
      }
   }

   public BiomeGenerationSettings m_47536_() {
      return this.f_47438_;
   }

   public void m_47484_(StructureFeatureManager p_47485_, ChunkGenerator p_47486_, WorldGenRegion p_47487_, long p_47488_, WorldgenRandom p_47489_, BlockPos p_47490_) {
      List<List<Supplier<ConfiguredFeature<?, ?>>>> list = this.f_47438_.m_47818_();
      Registry<ConfiguredFeature<?, ?>> registry = p_47487_.m_5962_().m_175515_(Registry.f_122881_);
      Registry<StructureFeature<?>> registry1 = p_47487_.m_5962_().m_175515_(Registry.f_122840_);
      int i = GenerationStep.Decoration.values().length;

      for(int j = 0; j < i; ++j) {
         int k = 0;
         if (p_47485_.m_47271_()) {
            for(StructureFeature<?> structurefeature : this.f_47434_.getOrDefault(j, Collections.emptyList())) {
               p_47489_.m_64699_(p_47488_, k, j);
               int l = SectionPos.m_123171_(p_47490_.m_123341_());
               int i1 = SectionPos.m_123171_(p_47490_.m_123343_());
               int j1 = SectionPos.m_123223_(l);
               int k1 = SectionPos.m_123223_(i1);
               Supplier<String> supplier = () -> {
                  return registry1.m_7854_(structurefeature).map(Object::toString).orElseGet(structurefeature::toString);
               };

               try {
                  int l1 = p_47487_.m_141937_() + 1;
                  int i2 = p_47487_.m_151558_() - 1;
                  p_47487_.m_143497_(supplier);
                  p_47485_.m_47289_(SectionPos.m_123199_(p_47490_), structurefeature).forEach((p_151667_) -> {
                     p_151667_.m_7129_(p_47487_, p_47485_, p_47486_, p_47489_, new BoundingBox(j1, l1, k1, j1 + 15, i2, k1 + 15), new ChunkPos(l, i1));
                  });
               } catch (Exception exception) {
                  CrashReport crashreport = CrashReport.m_127521_(exception, "Feature placement");
                  crashreport.m_127514_("Feature").m_128165_("Description", supplier::get);
                  throw new ReportedException(crashreport);
               }

               ++k;
            }
         }

         if (list.size() > j) {
            for(Supplier<ConfiguredFeature<?, ?>> supplier1 : list.get(j)) {
               ConfiguredFeature<?, ?> configuredfeature = supplier1.get();
               Supplier<String> supplier2 = () -> {
                  return registry.m_7854_(configuredfeature).map(Object::toString).orElseGet(configuredfeature::toString);
               };
               p_47489_.m_64699_(p_47488_, k, j);

               try {
                  p_47487_.m_143497_(supplier2);
                  configuredfeature.m_65385_(p_47487_, p_47486_, p_47489_, p_47490_);
               } catch (Exception exception1) {
                  CrashReport crashreport1 = CrashReport.m_127521_(exception1, "Feature placement");
                  crashreport1.m_127514_("Feature").m_128165_("Description", supplier2::get);
                  throw new ReportedException(crashreport1);
               }

               ++k;
            }
         }
      }

      p_47487_.m_143497_((Supplier<String>)null);
   }

   public int m_47539_() {
      return this.f_47443_.m_47967_();
   }

   public int m_47464_(double p_47465_, double p_47466_) {
      int i = this.f_47443_.m_47984_().orElseGet(this::m_47570_);
      return this.f_47443_.m_47987_().m_6583_(p_47465_, p_47466_, i);
   }

   private int m_47570_() {
      double d0 = (double)Mth.m_14036_(this.f_47437_.f_47681_, 0.0F, 1.0F);
      double d1 = (double)Mth.m_14036_(this.f_47437_.f_47683_, 0.0F, 1.0F);
      return GrassColor.m_46415_(d0, d1);
   }

   public int m_47542_() {
      return this.f_47443_.m_47981_().orElseGet(this::m_47571_);
   }

   private int m_47571_() {
      double d0 = (double)Mth.m_14036_(this.f_47437_.f_47681_, 0.0F, 1.0F);
      double d1 = (double)Mth.m_14036_(this.f_47437_.f_47683_, 0.0F, 1.0F);
      return FoliageColor.m_46107_(d0, d1);
   }

   public void m_151682_(Random p_151683_, ChunkAccess p_151684_, int p_151685_, int p_151686_, int p_151687_, double p_151688_, BlockState p_151689_, BlockState p_151690_, int p_151691_, int p_151692_, long p_151693_) {
      ConfiguredSurfaceBuilder<?> configuredsurfacebuilder = this.f_47438_.m_47821_().get();
      configuredsurfacebuilder.m_74771_(p_151693_);
      configuredsurfacebuilder.m_163848_(p_151683_, p_151684_, this, p_151685_, p_151686_, p_151687_, p_151688_, p_151689_, p_151690_, p_151691_, p_151692_, p_151693_);
   }

   public final float m_47545_() {
      return this.f_47440_;
   }

   public final float m_47548_() {
      return this.f_47437_.f_47683_;
   }

   public final float m_47551_() {
      return this.f_47441_;
   }

   public final float m_47554_() {
      return this.f_47437_.f_47681_;
   }

   public BiomeSpecialEffects m_47557_() {
      return this.f_47443_;
   }

   public final int m_47560_() {
      return this.f_47443_.m_47972_();
   }

   public final int m_47561_() {
      return this.f_47443_.m_47975_();
   }

   public Optional<AmbientParticleSettings> m_47562_() {
      return this.f_47443_.m_47990_();
   }

   public Optional<SoundEvent> m_47563_() {
      return this.f_47443_.m_47993_();
   }

   public Optional<AmbientMoodSettings> m_47564_() {
      return this.f_47443_.m_47996_();
   }

   public Optional<AmbientAdditionsSettings> m_47565_() {
      return this.f_47443_.m_47999_();
   }

   public Optional<Music> m_47566_() {
      return this.f_47443_.m_48002_();
   }

   public final Biome.BiomeCategory m_47567_() {
      return this.f_47442_;
   }

   public String toString() {
      ResourceLocation resourcelocation = BuiltinRegistries.f_123865_.m_7981_(this);
      return resourcelocation == null ? super.toString() : resourcelocation.toString();
   }

   public static class BiomeBuilder {
      @Nullable
      private Biome.Precipitation f_47581_;
      @Nullable
      private Biome.BiomeCategory f_47582_;
      @Nullable
      private Float f_47583_;
      @Nullable
      private Float f_47584_;
      @Nullable
      private Float f_47585_;
      private Biome.TemperatureModifier f_47586_ = Biome.TemperatureModifier.NONE;
      @Nullable
      private Float f_47587_;
      @Nullable
      private BiomeSpecialEffects f_47588_;
      @Nullable
      private MobSpawnSettings f_47589_;
      @Nullable
      private BiomeGenerationSettings f_47590_;

      public Biome.BiomeBuilder m_47597_(Biome.Precipitation p_47598_) {
         this.f_47581_ = p_47598_;
         return this;
      }

      public Biome.BiomeBuilder m_47595_(Biome.BiomeCategory p_47596_) {
         this.f_47582_ = p_47596_;
         return this;
      }

      public Biome.BiomeBuilder m_47593_(float p_47594_) {
         this.f_47583_ = p_47594_;
         return this;
      }

      public Biome.BiomeBuilder m_47607_(float p_47608_) {
         this.f_47584_ = p_47608_;
         return this;
      }

      public Biome.BiomeBuilder m_47609_(float p_47610_) {
         this.f_47585_ = p_47610_;
         return this;
      }

      public Biome.BiomeBuilder m_47611_(float p_47612_) {
         this.f_47587_ = p_47612_;
         return this;
      }

      public Biome.BiomeBuilder m_47603_(BiomeSpecialEffects p_47604_) {
         this.f_47588_ = p_47604_;
         return this;
      }

      public Biome.BiomeBuilder m_47605_(MobSpawnSettings p_47606_) {
         this.f_47589_ = p_47606_;
         return this;
      }

      public Biome.BiomeBuilder m_47601_(BiomeGenerationSettings p_47602_) {
         this.f_47590_ = p_47602_;
         return this;
      }

      public Biome.BiomeBuilder m_47599_(Biome.TemperatureModifier p_47600_) {
         this.f_47586_ = p_47600_;
         return this;
      }

      public Biome m_47592_() {
         if (this.f_47581_ != null && this.f_47582_ != null && this.f_47583_ != null && this.f_47584_ != null && this.f_47585_ != null && this.f_47587_ != null && this.f_47588_ != null && this.f_47589_ != null && this.f_47590_ != null) {
            return new Biome(new Biome.ClimateSettings(this.f_47581_, this.f_47585_, this.f_47586_, this.f_47587_), this.f_47582_, this.f_47583_, this.f_47584_, this.f_47588_, this.f_47590_, this.f_47589_);
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      public String toString() {
         return "BiomeBuilder{\nprecipitation=" + this.f_47581_ + ",\nbiomeCategory=" + this.f_47582_ + ",\ndepth=" + this.f_47583_ + ",\nscale=" + this.f_47584_ + ",\ntemperature=" + this.f_47585_ + ",\ntemperatureModifier=" + this.f_47586_ + ",\ndownfall=" + this.f_47587_ + ",\nspecialEffects=" + this.f_47588_ + ",\nmobSpawnSettings=" + this.f_47589_ + ",\ngenerationSettings=" + this.f_47590_ + ",\n}";
      }
   }

   public static enum BiomeCategory implements StringRepresentable {
      NONE("none"),
      TAIGA("taiga"),
      EXTREME_HILLS("extreme_hills"),
      JUNGLE("jungle"),
      MESA("mesa"),
      PLAINS("plains"),
      SAVANNA("savanna"),
      ICY("icy"),
      THEEND("the_end"),
      BEACH("beach"),
      FOREST("forest"),
      OCEAN("ocean"),
      DESERT("desert"),
      RIVER("river"),
      SWAMP("swamp"),
      MUSHROOM("mushroom"),
      NETHER("nether"),
      UNDERGROUND("underground");

      public static final Codec<Biome.BiomeCategory> f_47631_ = StringRepresentable.m_14350_(Biome.BiomeCategory::values, Biome.BiomeCategory::m_47643_);
      private static final Map<String, Biome.BiomeCategory> f_47632_ = Arrays.stream(values()).collect(Collectors.toMap(Biome.BiomeCategory::m_47645_, (p_47642_) -> {
         return p_47642_;
      }));
      private final String f_47633_;

      private BiomeCategory(String p_47639_) {
         this.f_47633_ = p_47639_;
      }

      public String m_47645_() {
         return this.f_47633_;
      }

      public static Biome.BiomeCategory m_47643_(String p_47644_) {
         return f_47632_.get(p_47644_);
      }

      public String m_7912_() {
         return this.f_47633_;
      }
   }

   public static class ClimateParameters {
      public static final Codec<Biome.ClimateParameters> f_47649_ = RecordCodecBuilder.create((p_47665_) -> {
         return p_47665_.group(Codec.floatRange(-2.0F, 2.0F).fieldOf("temperature").forGetter((p_151730_) -> {
            return p_151730_.f_47650_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("humidity").forGetter((p_151728_) -> {
            return p_151728_.f_47651_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("altitude").forGetter((p_151726_) -> {
            return p_151726_.f_47652_;
         }), Codec.floatRange(-2.0F, 2.0F).fieldOf("weirdness").forGetter((p_151724_) -> {
            return p_151724_.f_47653_;
         }), Codec.floatRange(0.0F, 1.0F).fieldOf("offset").forGetter((p_151722_) -> {
            return p_151722_.f_47654_;
         })).apply(p_47665_, Biome.ClimateParameters::new);
      });
      private final float f_47650_;
      private final float f_47651_;
      private final float f_47652_;
      private final float f_47653_;
      private final float f_47654_;

      public ClimateParameters(float p_47657_, float p_47658_, float p_47659_, float p_47660_, float p_47661_) {
         this.f_47650_ = p_47657_;
         this.f_47651_ = p_47658_;
         this.f_47652_ = p_47659_;
         this.f_47653_ = p_47660_;
         this.f_47654_ = p_47661_;
      }

      public String toString() {
         return "temp: " + this.f_47650_ + ", hum: " + this.f_47651_ + ", alt: " + this.f_47652_ + ", weird: " + this.f_47653_ + ", offset: " + this.f_47654_;
      }

      public boolean equals(Object p_47675_) {
         if (this == p_47675_) {
            return true;
         } else if (p_47675_ != null && this.getClass() == p_47675_.getClass()) {
            Biome.ClimateParameters biome$climateparameters = (Biome.ClimateParameters)p_47675_;
            if (Float.compare(biome$climateparameters.f_47650_, this.f_47650_) != 0) {
               return false;
            } else if (Float.compare(biome$climateparameters.f_47651_, this.f_47651_) != 0) {
               return false;
            } else if (Float.compare(biome$climateparameters.f_47652_, this.f_47652_) != 0) {
               return false;
            } else {
               return Float.compare(biome$climateparameters.f_47653_, this.f_47653_) == 0;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.f_47650_ != 0.0F ? Float.floatToIntBits(this.f_47650_) : 0;
         i = 31 * i + (this.f_47651_ != 0.0F ? Float.floatToIntBits(this.f_47651_) : 0);
         i = 31 * i + (this.f_47652_ != 0.0F ? Float.floatToIntBits(this.f_47652_) : 0);
         return 31 * i + (this.f_47653_ != 0.0F ? Float.floatToIntBits(this.f_47653_) : 0);
      }

      public float m_47662_(Biome.ClimateParameters p_47663_) {
         return (this.f_47650_ - p_47663_.f_47650_) * (this.f_47650_ - p_47663_.f_47650_) + (this.f_47651_ - p_47663_.f_47651_) * (this.f_47651_ - p_47663_.f_47651_) + (this.f_47652_ - p_47663_.f_47652_) * (this.f_47652_ - p_47663_.f_47652_) + (this.f_47653_ - p_47663_.f_47653_) * (this.f_47653_ - p_47663_.f_47653_) + (this.f_47654_ - p_47663_.f_47654_) * (this.f_47654_ - p_47663_.f_47654_);
      }
   }

   public static class ClimateSettings {
      public static final MapCodec<Biome.ClimateSettings> f_47679_ = RecordCodecBuilder.mapCodec((p_47699_) -> {
         return p_47699_.group(Biome.Precipitation.f_47717_.fieldOf("precipitation").forGetter((p_151739_) -> {
            return p_151739_.f_47680_;
         }), Codec.FLOAT.fieldOf("temperature").forGetter((p_151737_) -> {
            return p_151737_.f_47681_;
         }), Biome.TemperatureModifier.f_47737_.optionalFieldOf("temperature_modifier", Biome.TemperatureModifier.NONE).forGetter((p_151735_) -> {
            return p_151735_.f_47682_;
         }), Codec.FLOAT.fieldOf("downfall").forGetter((p_151733_) -> {
            return p_151733_.f_47683_;
         })).apply(p_47699_, Biome.ClimateSettings::new);
      });
      public final Biome.Precipitation f_47680_;
      public final float f_47681_;
      public final Biome.TemperatureModifier f_47682_;
      public final float f_47683_;

      public ClimateSettings(Biome.Precipitation p_47686_, float p_47687_, Biome.TemperatureModifier p_47688_, float p_47689_) {
         this.f_47680_ = p_47686_;
         this.f_47681_ = p_47687_;
         this.f_47682_ = p_47688_;
         this.f_47683_ = p_47689_;
      }
   }

   public static enum Precipitation implements StringRepresentable {
      NONE("none"),
      RAIN("rain"),
      SNOW("snow");

      public static final Codec<Biome.Precipitation> f_47717_ = StringRepresentable.m_14350_(Biome.Precipitation::values, Biome.Precipitation::m_47729_);
      private static final Map<String, Biome.Precipitation> f_47718_ = Arrays.stream(values()).collect(Collectors.toMap(Biome.Precipitation::m_47731_, (p_47728_) -> {
         return p_47728_;
      }));
      private final String f_47719_;

      private Precipitation(String p_47725_) {
         this.f_47719_ = p_47725_;
      }

      public String m_47731_() {
         return this.f_47719_;
      }

      public static Biome.Precipitation m_47729_(String p_47730_) {
         return f_47718_.get(p_47730_);
      }

      public String m_7912_() {
         return this.f_47719_;
      }
   }

   public static enum TemperatureModifier implements StringRepresentable {
      NONE("none") {
         public float m_8117_(BlockPos p_47767_, float p_47768_) {
            return p_47768_;
         }
      },
      FROZEN("frozen") {
         public float m_8117_(BlockPos p_47774_, float p_47775_) {
            double d0 = Biome.f_47436_.m_75449_((double)p_47774_.m_123341_() * 0.05D, (double)p_47774_.m_123343_() * 0.05D, false) * 7.0D;
            double d1 = Biome.f_47433_.m_75449_((double)p_47774_.m_123341_() * 0.2D, (double)p_47774_.m_123343_() * 0.2D, false);
            double d2 = d0 + d1;
            if (d2 < 0.3D) {
               double d3 = Biome.f_47433_.m_75449_((double)p_47774_.m_123341_() * 0.09D, (double)p_47774_.m_123343_() * 0.09D, false);
               if (d3 < 0.8D) {
                  return 0.2F;
               }
            }

            return p_47775_;
         }
      };

      private final String f_47738_;
      public static final Codec<Biome.TemperatureModifier> f_47737_ = StringRepresentable.m_14350_(Biome.TemperatureModifier::values, Biome.TemperatureModifier::m_47756_);
      private static final Map<String, Biome.TemperatureModifier> f_47739_ = Arrays.stream(values()).collect(Collectors.toMap(Biome.TemperatureModifier::m_47758_, (p_47753_) -> {
         return p_47753_;
      }));

      public abstract float m_8117_(BlockPos p_47754_, float p_47755_);

      TemperatureModifier(String p_47745_) {
         this.f_47738_ = p_47745_;
      }

      public String m_47758_() {
         return this.f_47738_;
      }

      public String m_7912_() {
         return this.f_47738_;
      }

      public static Biome.TemperatureModifier m_47756_(String p_47757_) {
         return f_47739_.get(p_47757_);
      }
   }
}
