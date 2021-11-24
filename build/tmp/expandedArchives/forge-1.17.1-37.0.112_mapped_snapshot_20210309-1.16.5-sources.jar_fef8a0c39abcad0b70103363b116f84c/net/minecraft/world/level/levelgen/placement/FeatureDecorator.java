package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoiseDependantDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.nether.CountMultiLayerDecorator;

public abstract class FeatureDecorator<DC extends DecoratorConfiguration> extends net.minecraftforge.registries.ForgeRegistryEntry<FeatureDecorator<?>> {
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70681_ = m_70717_("nope", new NopePlacementDecorator(NoneDecoratorConfiguration.f_67810_));
   public static final FeatureDecorator<DecoratedDecoratorConfiguration> f_70678_ = m_70717_("decorated", new DecoratedDecorator(DecoratedDecoratorConfiguration.f_70570_));
   public static final FeatureDecorator<CarvingMaskDecoratorConfiguration> f_70697_ = m_70717_("carving_mask", new CarvingMaskDecorator(CarvingMaskDecoratorConfiguration.f_70441_));
   public static final FeatureDecorator<CountConfiguration> f_70679_ = m_70717_("count_multilayer", new CountMultiLayerDecorator(CountConfiguration.f_67568_));
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70687_ = m_70717_("square", new SquareDecorator(NoneDecoratorConfiguration.f_67810_));
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70705_ = m_70717_("dark_oak_tree", new DarkOakTreePlacementDecorator(NoneDecoratorConfiguration.f_67810_));
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70706_ = m_70717_("iceberg", new IcebergPlacementDecorator(NoneDecoratorConfiguration.f_67810_));
   public static final FeatureDecorator<ChanceDecoratorConfiguration> f_70682_ = m_70717_("chance", new ChanceDecorator(ChanceDecoratorConfiguration.f_70464_));
   public static final FeatureDecorator<CountConfiguration> f_70683_ = m_70717_("count", new CountDecorator(CountConfiguration.f_67568_));
   public static final FeatureDecorator<NoiseDependantDecoratorConfiguration> f_70684_ = m_70717_("count_noise", new CountNoiseDecorator(NoiseDependantDecoratorConfiguration.f_67793_));
   public static final FeatureDecorator<NoiseCountFactorDecoratorConfiguration> f_70685_ = m_70717_("count_noise_biased", new NoiseBasedDecorator(NoiseCountFactorDecoratorConfiguration.f_70806_));
   public static final FeatureDecorator<FrequencyWithExtraChanceDecoratorConfiguration> f_70686_ = m_70717_("count_extra", new CountWithExtraChanceDecorator(FrequencyWithExtraChanceDecoratorConfiguration.f_70723_));
   public static final FeatureDecorator<ChanceDecoratorConfiguration> f_70701_ = m_70717_("lava_lake", new LakeLavaPlacementDecorator(ChanceDecoratorConfiguration.f_70464_));
   public static final FeatureDecorator<HeightmapConfiguration> f_70688_ = m_70717_("heightmap", new HeightmapDecorator(HeightmapConfiguration.f_160929_));
   public static final FeatureDecorator<HeightmapConfiguration> f_70689_ = m_70717_("heightmap_spread_double", new HeightmapDoubleDecorator(HeightmapConfiguration.f_160929_));
   public static final FeatureDecorator<WaterDepthThresholdConfiguration> f_162179_ = m_70717_("water_depth_threshold", new WaterDepthThresholdDecorator(WaterDepthThresholdConfiguration.f_162333_));
   public static final FeatureDecorator<CaveDecoratorConfiguration> f_162180_ = m_70717_("cave_surface", new CaveSurfaceDecorator(CaveDecoratorConfiguration.f_162079_));
   public static final FeatureDecorator<RangeDecoratorConfiguration> f_70692_ = m_70717_("range", new RangeDecorator(RangeDecoratorConfiguration.f_68006_));
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70696_ = m_70717_("spread_32_above", new Spread32Decorator(NoneDecoratorConfiguration.f_67810_));
   public static final FeatureDecorator<NoneDecoratorConfiguration> f_70704_ = m_70717_("end_gateway", new EndGatewayPlacementDecorator(NoneDecoratorConfiguration.f_67810_));
   private final Codec<ConfiguredDecorator<DC>> f_70680_;

   private static <T extends DecoratorConfiguration, G extends FeatureDecorator<T>> G m_70717_(String p_70718_, G p_70719_) {
      return Registry.m_122961_(Registry.f_122845_, p_70718_, p_70719_);
   }

   public FeatureDecorator(Codec<DC> p_70709_) {
      this.f_70680_ = p_70709_.fieldOf("config").xmap((p_70712_) -> {
         return new ConfiguredDecorator<DC>(this, p_70712_);
      }, ConfiguredDecorator::m_70484_).codec();
   }

   public ConfiguredDecorator<DC> m_70720_(DC p_70721_) {
      return new ConfiguredDecorator<>(this, p_70721_);
   }

   public Codec<ConfiguredDecorator<DC>> m_70710_() {
      return this.f_70680_;
   }

   public abstract Stream<BlockPos> m_7887_(DecorationContext p_70713_, Random p_70714_, DC p_70715_, BlockPos p_70716_);

   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}
