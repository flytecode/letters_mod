package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GrowingPlantConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SmallDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public abstract class Feature<FC extends FeatureConfiguration> extends net.minecraftforge.registries.ForgeRegistryEntry<Feature<?>> {
   public static final Feature<NoneFeatureConfiguration> f_65759_ = m_65807_("no_op", new NoOpFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<TreeConfiguration> f_65760_ = m_65807_("tree", new TreeFeature(TreeConfiguration.f_68184_));
   public static final AbstractFlowerFeature<RandomPatchConfiguration> f_65761_ = m_65807_("flower", new DefaultFlowerFeature(RandomPatchConfiguration.f_67902_));
   public static final AbstractFlowerFeature<RandomPatchConfiguration> f_65762_ = m_65807_("no_bonemeal_flower", new DefaultFlowerFeature(RandomPatchConfiguration.f_67902_));
   public static final Feature<RandomPatchConfiguration> f_65763_ = m_65807_("random_patch", new RandomPatchFeature(RandomPatchConfiguration.f_67902_));
   public static final Feature<BlockPileConfiguration> f_65764_ = m_65807_("block_pile", new BlockPileFeature(BlockPileConfiguration.f_67539_));
   public static final Feature<SpringConfiguration> f_65765_ = m_65807_("spring_feature", new SpringFeature(SpringConfiguration.f_68123_));
   public static final Feature<NoneFeatureConfiguration> f_65766_ = m_65807_("chorus_plant", new ChorusPlantFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<ReplaceBlockConfiguration> f_159732_ = m_65807_("replace_single_block", new ReplaceBlockFeature(ReplaceBlockConfiguration.f_68023_));
   public static final Feature<NoneFeatureConfiguration> f_65768_ = m_65807_("void_start_platform", new VoidStartPlatformFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65769_ = m_65807_("desert_well", new DesertWellFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<FossilFeatureConfiguration> f_65770_ = m_65807_("fossil", new FossilFeature(FossilFeatureConfiguration.f_159796_));
   public static final Feature<HugeMushroomFeatureConfiguration> f_65771_ = m_65807_("huge_red_mushroom", new HugeRedMushroomFeature(HugeMushroomFeatureConfiguration.f_67739_));
   public static final Feature<HugeMushroomFeatureConfiguration> f_65772_ = m_65807_("huge_brown_mushroom", new HugeBrownMushroomFeature(HugeMushroomFeatureConfiguration.f_67739_));
   public static final Feature<NoneFeatureConfiguration> f_65773_ = m_65807_("ice_spike", new IceSpikeFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65774_ = m_65807_("glowstone_blob", new GlowstoneFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65775_ = m_65807_("freeze_top_layer", new SnowAndFreezeFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65776_ = m_65807_("vines", new VinesFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<GrowingPlantConfiguration> f_159733_ = m_65807_("growing_plant", new GrowingPlantFeature(GrowingPlantConfiguration.f_160904_));
   public static final Feature<VegetationPatchConfiguration> f_159734_ = m_65807_("vegetation_patch", new VegetationPatchFeature(VegetationPatchConfiguration.f_161280_));
   public static final Feature<VegetationPatchConfiguration> f_159735_ = m_65807_("waterlogged_vegetation_patch", new WaterloggedVegetationPatchFeature(VegetationPatchConfiguration.f_161280_));
   public static final Feature<RootSystemConfiguration> f_159724_ = m_65807_("root_system", new RootSystemFeature(RootSystemConfiguration.f_161101_));
   public static final Feature<GlowLichenConfiguration> f_159725_ = m_65807_("glow_lichen", new GlowLichenFeature(GlowLichenConfiguration.f_160869_));
   public static final Feature<UnderwaterMagmaConfiguration> f_159726_ = m_65807_("underwater_magma", new UnderwaterMagmaFeature(UnderwaterMagmaConfiguration.f_161263_));
   public static final Feature<NoneFeatureConfiguration> f_65777_ = m_65807_("monster_room", new MonsterRoomFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65778_ = m_65807_("blue_ice", new BlueIceFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<BlockStateConfiguration> f_65779_ = m_65807_("iceberg", new IcebergFeature(BlockStateConfiguration.f_67546_));
   public static final Feature<BlockStateConfiguration> f_65780_ = m_65807_("forest_rock", new BlockBlobFeature(BlockStateConfiguration.f_67546_));
   public static final Feature<DiskConfiguration> f_65781_ = m_65807_("disk", new DiskReplaceFeature(DiskConfiguration.f_67618_));
   public static final Feature<DiskConfiguration> f_65782_ = m_65807_("ice_patch", new IcePatchFeature(DiskConfiguration.f_67618_));
   public static final Feature<BlockStateConfiguration> f_65783_ = m_65807_("lake", new LakeFeature(BlockStateConfiguration.f_67546_));
   public static final Feature<OreConfiguration> f_65731_ = m_65807_("ore", new OreFeature(OreConfiguration.f_67837_));
   public static final Feature<SpikeConfiguration> f_65732_ = m_65807_("end_spike", new SpikeFeature(SpikeConfiguration.f_68099_));
   public static final Feature<NoneFeatureConfiguration> f_65733_ = m_65807_("end_island", new EndIslandFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<EndGatewayConfiguration> f_65734_ = m_65807_("end_gateway", new EndGatewayFeature(EndGatewayConfiguration.f_67639_));
   public static final SeagrassFeature f_65735_ = m_65807_("seagrass", new SeagrassFeature(ProbabilityFeatureConfiguration.f_67858_));
   public static final Feature<NoneFeatureConfiguration> f_65736_ = m_65807_("kelp", new KelpFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65737_ = m_65807_("coral_tree", new CoralTreeFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65738_ = m_65807_("coral_mushroom", new CoralMushroomFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65739_ = m_65807_("coral_claw", new CoralClawFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<CountConfiguration> f_65740_ = m_65807_("sea_pickle", new SeaPickleFeature(CountConfiguration.f_67568_));
   public static final Feature<SimpleBlockConfiguration> f_65741_ = m_65807_("simple_block", new SimpleBlockFeature(SimpleBlockConfiguration.f_68068_));
   public static final Feature<ProbabilityFeatureConfiguration> f_65742_ = m_65807_("bamboo", new BambooFeature(ProbabilityFeatureConfiguration.f_67858_));
   public static final Feature<HugeFungusConfiguration> f_65743_ = m_65807_("huge_fungus", new HugeFungusFeature(HugeFungusConfiguration.f_65892_));
   public static final Feature<BlockPileConfiguration> f_65744_ = m_65807_("nether_forest_vegetation", new NetherForestVegetationFeature(BlockPileConfiguration.f_67539_));
   public static final Feature<NoneFeatureConfiguration> f_65745_ = m_65807_("weeping_vines", new WeepingVinesFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65746_ = m_65807_("twisting_vines", new TwistingVinesFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<ColumnFeatureConfiguration> f_65747_ = m_65807_("basalt_columns", new BasaltColumnsFeature(ColumnFeatureConfiguration.f_67553_));
   public static final Feature<DeltaFeatureConfiguration> f_65748_ = m_65807_("delta_feature", new DeltaFeature(DeltaFeatureConfiguration.f_67593_));
   public static final Feature<ReplaceSphereConfiguration> f_65749_ = m_65807_("netherrack_replace_blobs", new ReplaceBlobsFeature(ReplaceSphereConfiguration.f_68036_));
   public static final Feature<LayerConfiguration> f_65750_ = m_65807_("fill_layer", new FillLayerFeature(LayerConfiguration.f_67767_));
   public static final BonusChestFeature f_65751_ = m_65807_("bonus_chest", new BonusChestFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<NoneFeatureConfiguration> f_65752_ = m_65807_("basalt_pillar", new BasaltPillarFeature(NoneFeatureConfiguration.f_67815_));
   public static final Feature<OreConfiguration> f_159727_ = m_65807_("scattered_ore", new ScatteredOreFeature(OreConfiguration.f_67837_));
   public static final Feature<RandomFeatureConfiguration> f_65754_ = m_65807_("random_selector", new RandomSelectorFeature(RandomFeatureConfiguration.f_67881_));
   public static final Feature<SimpleRandomFeatureConfiguration> f_65755_ = m_65807_("simple_random_selector", new SimpleRandomSelectorFeature(SimpleRandomFeatureConfiguration.f_68089_));
   public static final Feature<RandomBooleanFeatureConfiguration> f_65756_ = m_65807_("random_boolean_selector", new RandomBooleanSelectorFeature(RandomBooleanFeatureConfiguration.f_67867_));
   public static final Feature<DecoratedFeatureConfiguration> f_65758_ = m_65807_("decorated", new DecoratedFeature(DecoratedFeatureConfiguration.f_67576_));
   public static final Feature<GeodeConfiguration> f_159728_ = m_65807_("geode", new GeodeFeature(GeodeConfiguration.f_160812_));
   public static final Feature<DripstoneClusterConfiguration> f_159729_ = m_65807_("dripstone_cluster", new DripstoneClusterFeature(DripstoneClusterConfiguration.f_160758_));
   public static final Feature<LargeDripstoneConfiguration> f_159730_ = m_65807_("large_dripstone", new LargeDripstoneFeature(LargeDripstoneConfiguration.f_160944_));
   public static final Feature<SmallDripstoneConfiguration> f_159731_ = m_65807_("small_dripstone", new SmallDripstoneFeature(SmallDripstoneConfiguration.f_161169_));
   private final Codec<ConfiguredFeature<FC, Feature<FC>>> f_65757_;

   private static <C extends FeatureConfiguration, F extends Feature<C>> F m_65807_(String p_65808_, F p_65809_) {
      return Registry.m_122961_(Registry.f_122839_, p_65808_, p_65809_);
   }

   public Feature(Codec<FC> p_65786_) {
      this.f_65757_ = p_65786_.fieldOf("config").xmap((p_65806_) -> {
         return new ConfiguredFeature<>(this, p_65806_);
      }, (p_65804_) -> {
         return p_65804_.f_65378_;
      }).codec();
   }

   public Codec<ConfiguredFeature<FC, Feature<FC>>> m_65787_() {
      return this.f_65757_;
   }

   public ConfiguredFeature<FC, ?> m_65815_(FC p_65816_) {
      return new ConfiguredFeature<>(this, p_65816_);
   }

   protected void m_5974_(LevelWriter p_65791_, BlockPos p_65792_, BlockState p_65793_) {
      p_65791_.m_7731_(p_65792_, p_65793_, 3);
   }

   public static Predicate<BlockState> m_159757_(ResourceLocation p_159758_) {
      Tag<Block> tag = BlockTags.m_13115_().m_13404_(p_159758_);
      return tag == null ? (p_159762_) -> {
         return true;
      } : (p_159738_) -> {
         return !p_159738_.m_60620_(tag);
      };
   }

   protected void m_159742_(WorldGenLevel p_159743_, BlockPos p_159744_, BlockState p_159745_, Predicate<BlockState> p_159746_) {
      if (p_159746_.test(p_159743_.m_8055_(p_159744_))) {
         p_159743_.m_7731_(p_159744_, p_159745_, 2);
      }

   }

   public abstract boolean m_142674_(FeaturePlaceContext<FC> p_159749_);

   protected static boolean m_159747_(BlockState p_159748_) {
      return p_159748_.m_60620_(net.minecraftforge.common.Tags.Blocks.STONE);
   }

   public static boolean m_159759_(BlockState p_159760_) {
      return p_159760_.m_60620_(net.minecraftforge.common.Tags.Blocks.DIRT);
   }

   public static boolean m_65788_(LevelSimulatedReader p_65789_, BlockPos p_65790_) {
      return p_65789_.m_7433_(p_65790_, Feature::m_159759_);
   }

   public static boolean m_65810_(LevelSimulatedReader p_65811_, BlockPos p_65812_) {
      return p_65811_.m_7433_(p_65812_, BlockBehaviour.BlockStateBase::m_60795_);
   }

   public static boolean m_159753_(Function<BlockPos, BlockState> p_159754_, BlockPos p_159755_, Predicate<BlockState> p_159756_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : Direction.values()) {
         blockpos$mutableblockpos.m_122159_(p_159755_, direction);
         if (p_159756_.test(p_159754_.apply(blockpos$mutableblockpos))) {
            return true;
         }
      }

      return false;
   }

   public static boolean m_159750_(Function<BlockPos, BlockState> p_159751_, BlockPos p_159752_) {
      return m_159753_(p_159751_, p_159752_, BlockBehaviour.BlockStateBase::m_60795_);
   }

   protected void m_159739_(WorldGenLevel p_159740_, BlockPos p_159741_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159741_.m_122032_();

      for(int i = 0; i < 2; ++i) {
         blockpos$mutableblockpos.m_122173_(Direction.UP);
         if (p_159740_.m_8055_(blockpos$mutableblockpos).m_60795_()) {
            return;
         }

         p_159740_.m_46865_(blockpos$mutableblockpos).m_8113_(blockpos$mutableblockpos);
      }

   }
}
