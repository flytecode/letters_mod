package net.minecraft.world.level.levelgen;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public final class NoiseGeneratorSettings {
   public static final Codec<NoiseGeneratorSettings> f_64430_ = RecordCodecBuilder.create((p_64475_) -> {
      return p_64475_.group(StructureSettings.f_64579_.fieldOf("structures").forGetter(NoiseGeneratorSettings::m_64457_), NoiseSettings.f_64507_.fieldOf("noise").forGetter(NoiseGeneratorSettings::m_64481_), BlockState.f_61039_.fieldOf("default_block").forGetter(NoiseGeneratorSettings::m_64482_), BlockState.f_61039_.fieldOf("default_fluid").forGetter(NoiseGeneratorSettings::m_64483_), Codec.INT.fieldOf("bedrock_roof_position").forGetter(NoiseGeneratorSettings::m_64484_), Codec.INT.fieldOf("bedrock_floor_position").forGetter(NoiseGeneratorSettings::m_64485_), Codec.INT.fieldOf("sea_level").forGetter(NoiseGeneratorSettings::m_64486_), Codec.INT.fieldOf("min_surface_level").forGetter(NoiseGeneratorSettings::m_158566_), Codec.BOOL.fieldOf("disable_mob_generation").forGetter(NoiseGeneratorSettings::m_64487_), Codec.BOOL.fieldOf("aquifers_enabled").forGetter(NoiseGeneratorSettings::m_158567_), Codec.BOOL.fieldOf("noise_caves_enabled").forGetter(NoiseGeneratorSettings::m_158568_), Codec.BOOL.fieldOf("deepslate_enabled").forGetter(NoiseGeneratorSettings::m_158569_), Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(NoiseGeneratorSettings::m_158570_), Codec.BOOL.fieldOf("noodle_caves_enabled").forGetter(NoiseGeneratorSettings::m_158570_)).apply(p_64475_, NoiseGeneratorSettings::new);
   });
   public static final Codec<Supplier<NoiseGeneratorSettings>> f_64431_ = RegistryFileCodec.m_135589_(Registry.f_122878_, f_64430_);
   private final StructureSettings f_64438_;
   private final NoiseSettings f_64439_;
   private final BlockState f_64440_;
   private final BlockState f_64441_;
   private final int f_64442_;
   private final int f_64443_;
   private final int f_64444_;
   private final int f_158532_;
   private final boolean f_64445_;
   private final boolean f_158533_;
   private final boolean f_158534_;
   private final boolean f_158535_;
   private final boolean f_158536_;
   private final boolean f_158537_;
   public static final ResourceKey<NoiseGeneratorSettings> f_64432_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("overworld"));
   public static final ResourceKey<NoiseGeneratorSettings> f_64433_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("amplified"));
   public static final ResourceKey<NoiseGeneratorSettings> f_64434_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("nether"));
   public static final ResourceKey<NoiseGeneratorSettings> f_64435_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("end"));
   public static final ResourceKey<NoiseGeneratorSettings> f_64436_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("caves"));
   public static final ResourceKey<NoiseGeneratorSettings> f_64437_ = ResourceKey.m_135785_(Registry.f_122878_, new ResourceLocation("floating_islands"));
   private static final NoiseGeneratorSettings f_64446_ = m_64478_(f_64432_, m_158563_(new StructureSettings(true), false));

   public NoiseGeneratorSettings(StructureSettings p_158539_, NoiseSettings p_158540_, BlockState p_158541_, BlockState p_158542_, int p_158543_, int p_158544_, int p_158545_, int p_158546_, boolean p_158547_, boolean p_158548_, boolean p_158549_, boolean p_158550_, boolean p_158551_, boolean p_158552_) {
      this.f_64438_ = p_158539_;
      this.f_64439_ = p_158540_;
      this.f_64440_ = p_158541_;
      this.f_64441_ = p_158542_;
      this.f_64442_ = p_158543_;
      this.f_64443_ = p_158544_;
      this.f_64444_ = p_158545_;
      this.f_158532_ = p_158546_;
      this.f_64445_ = p_158547_;
      this.f_158533_ = p_158548_;
      this.f_158534_ = p_158549_;
      this.f_158535_ = p_158550_;
      this.f_158536_ = p_158551_;
      this.f_158537_ = p_158552_;
   }

   public StructureSettings m_64457_() {
      return this.f_64438_;
   }

   public NoiseSettings m_64481_() {
      return this.f_64439_;
   }

   public BlockState m_64482_() {
      return this.f_64440_;
   }

   public BlockState m_64483_() {
      return this.f_64441_;
   }

   public int m_64484_() {
      return this.f_64442_;
   }

   public int m_64485_() {
      return this.f_64443_;
   }

   public int m_64486_() {
      return this.f_64444_;
   }

   public int m_158566_() {
      return this.f_158532_;
   }

   @Deprecated
   public boolean m_64487_() {
      return this.f_64445_;
   }

   public boolean m_158567_() {
      return this.f_158533_;
   }

   public boolean m_158568_() {
      return this.f_158534_;
   }

   public boolean m_158569_() {
      return this.f_158535_;
   }

   public boolean m_158570_() {
      return this.f_158536_;
   }

   public boolean m_158571_() {
      return this.f_158537_;
   }

   public boolean m_64476_(ResourceKey<NoiseGeneratorSettings> p_64477_) {
      return Objects.equals(this, BuiltinRegistries.f_123866_.m_6246_(p_64477_));
   }

   public static NoiseGeneratorSettings m_64478_(ResourceKey<NoiseGeneratorSettings> p_64479_, NoiseGeneratorSettings p_64480_) {
      BuiltinRegistries.m_123880_(BuiltinRegistries.f_123866_, p_64479_.m_135782_(), p_64480_);
      return p_64480_;
   }

   public static NoiseGeneratorSettings m_64488_() {
      return f_64446_;
   }

   public static NoiseGeneratorSettings m_158557_(StructureSettings p_158558_, BlockState p_158559_, BlockState p_158560_, boolean p_158561_, boolean p_158562_) {
      return new NoiseGeneratorSettings(p_158558_, NoiseSettings.m_158704_(0, 128, new NoiseSamplingSettings(2.0D, 1.0D, 80.0D, 160.0D), new NoiseSlideSettings(-3000, 64, -46), new NoiseSlideSettings(-30, 7, 1), 2, 1, 0.0D, 0.0D, true, false, p_158562_, false), p_158559_, p_158560_, Integer.MIN_VALUE, Integer.MIN_VALUE, 0, 0, p_158561_, false, false, false, false, false);
   }

   public static NoiseGeneratorSettings m_158553_(StructureSettings p_158554_, BlockState p_158555_, BlockState p_158556_) {
      Map<StructureFeature<?>, StructureFeatureConfiguration> map = Maps.newHashMap(StructureSettings.f_64580_);
      map.put(StructureFeature.f_67019_, new StructureFeatureConfiguration(25, 10, 34222645));
      return new NoiseGeneratorSettings(new StructureSettings(Optional.ofNullable(p_158554_.m_64597_()), map), NoiseSettings.m_158704_(0, 128, new NoiseSamplingSettings(1.0D, 3.0D, 80.0D, 60.0D), new NoiseSlideSettings(120, 3, 0), new NoiseSlideSettings(320, 4, -1), 1, 2, 0.0D, 0.019921875D, false, false, false, false), p_158555_, p_158556_, 0, 0, 32, 0, false, false, false, false, false, false);
   }

   public static NoiseGeneratorSettings m_158563_(StructureSettings p_158564_, boolean p_158565_) {
      double d0 = 0.9999999814507745D;
      return new NoiseGeneratorSettings(p_158564_, NoiseSettings.m_158704_(0, 256, new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new NoiseSlideSettings(-10, 3, 0), new NoiseSlideSettings(15, 3, 0), 1, 2, 1.0D, -0.46875D, true, true, false, p_158565_), Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_(), Integer.MIN_VALUE, 0, 63, 0, false, false, false, false, false, false);
   }

   static {
      m_64478_(f_64433_, m_158563_(new StructureSettings(true), true));
      m_64478_(f_64434_, m_158553_(new StructureSettings(false), Blocks.f_50134_.m_49966_(), Blocks.f_49991_.m_49966_()));
      m_64478_(f_64435_, m_158557_(new StructureSettings(false), Blocks.f_50259_.m_49966_(), Blocks.f_50016_.m_49966_(), true, true));
      m_64478_(f_64436_, m_158553_(new StructureSettings(true), Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_()));
      m_64478_(f_64437_, m_158557_(new StructureSettings(true), Blocks.f_50069_.m_49966_(), Blocks.f_49990_.m_49966_(), false, false));
   }
}