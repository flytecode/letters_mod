package net.minecraft.data.worldgen;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.MineshaftFeature;
import net.minecraft.world.level.levelgen.feature.RuinedPortalFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.OceanRuinFeature;

public class StructureFeatures {
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127239_ = m_127267_("pillager_outpost", StructureFeature.f_67013_.m_67065_(new JigsawConfiguration(() -> {
      return PillagerOutpostPools.f_127180_;
   }, 7)));
   public static final ConfiguredStructureFeature<MineshaftConfiguration, ? extends StructureFeature<MineshaftConfiguration>> f_127240_ = m_127267_("mineshaft", StructureFeature.f_67014_.m_67065_(new MineshaftConfiguration(0.004F, MineshaftFeature.Type.NORMAL)));
   public static final ConfiguredStructureFeature<MineshaftConfiguration, ? extends StructureFeature<MineshaftConfiguration>> f_127241_ = m_127267_("mineshaft_mesa", StructureFeature.f_67014_.m_67065_(new MineshaftConfiguration(0.004F, MineshaftFeature.Type.MESA)));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127242_ = m_127267_("mansion", StructureFeature.f_67015_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127243_ = m_127267_("jungle_pyramid", StructureFeature.f_67016_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127244_ = m_127267_("desert_pyramid", StructureFeature.f_67017_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127245_ = m_127267_("igloo", StructureFeature.f_67018_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<ShipwreckConfiguration, ? extends StructureFeature<ShipwreckConfiguration>> f_127246_ = m_127267_("shipwreck", StructureFeature.f_67020_.m_67065_(new ShipwreckConfiguration(false)));
   public static final ConfiguredStructureFeature<ShipwreckConfiguration, ? extends StructureFeature<ShipwreckConfiguration>> f_127247_ = m_127267_("shipwreck_beached", StructureFeature.f_67020_.m_67065_(new ShipwreckConfiguration(true)));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127248_ = m_127267_("swamp_hut", StructureFeature.f_67021_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127249_ = m_127267_("stronghold", StructureFeature.f_67022_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127250_ = m_127267_("monument", StructureFeature.f_67023_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<OceanRuinConfiguration, ? extends StructureFeature<OceanRuinConfiguration>> f_127251_ = m_127267_("ocean_ruin_cold", StructureFeature.f_67024_.m_67065_(new OceanRuinConfiguration(OceanRuinFeature.Type.COLD, 0.3F, 0.9F)));
   public static final ConfiguredStructureFeature<OceanRuinConfiguration, ? extends StructureFeature<OceanRuinConfiguration>> f_127252_ = m_127267_("ocean_ruin_warm", StructureFeature.f_67024_.m_67065_(new OceanRuinConfiguration(OceanRuinFeature.Type.WARM, 0.3F, 0.9F)));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127253_ = m_127267_("fortress", StructureFeature.f_67025_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<RangeDecoratorConfiguration, ? extends StructureFeature<RangeDecoratorConfiguration>> f_127254_ = m_127267_("nether_fossil", StructureFeature.f_67029_.m_67065_(new RangeDecoratorConfiguration(UniformHeight.m_162034_(VerticalAnchor.m_158922_(32), VerticalAnchor.m_158935_(2)))));
   public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> f_127255_ = m_127267_("end_city", StructureFeature.f_67026_.m_67065_(NoneFeatureConfiguration.f_67816_));
   public static final ConfiguredStructureFeature<ProbabilityFeatureConfiguration, ? extends StructureFeature<ProbabilityFeatureConfiguration>> f_127256_ = m_127267_("buried_treasure", StructureFeature.f_67027_.m_67065_(new ProbabilityFeatureConfiguration(0.01F)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127257_ = m_127267_("bastion_remnant", StructureFeature.f_67030_.m_67065_(new JigsawConfiguration(() -> {
      return BastionPieces.f_126673_;
   }, 6)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127258_ = m_127267_("village_plains", StructureFeature.f_67028_.m_67065_(new JigsawConfiguration(() -> {
      return PlainVillagePools.f_127183_;
   }, 6)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127259_ = m_127267_("village_desert", StructureFeature.f_67028_.m_67065_(new JigsawConfiguration(() -> {
      return DesertVillagePools.f_126858_;
   }, 6)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127260_ = m_127267_("village_savanna", StructureFeature.f_67028_.m_67065_(new JigsawConfiguration(() -> {
      return SavannaVillagePools.f_127228_;
   }, 6)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127261_ = m_127267_("village_snowy", StructureFeature.f_67028_.m_67065_(new JigsawConfiguration(() -> {
      return SnowyVillagePools.f_127231_;
   }, 6)));
   public static final ConfiguredStructureFeature<JigsawConfiguration, ? extends StructureFeature<JigsawConfiguration>> f_127262_ = m_127267_("village_taiga", StructureFeature.f_67028_.m_67065_(new JigsawConfiguration(() -> {
      return TaigaVillagePools.f_127303_;
   }, 6)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127263_ = m_127267_("ruined_portal", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.STANDARD)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127264_ = m_127267_("ruined_portal_desert", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.DESERT)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127234_ = m_127267_("ruined_portal_jungle", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.JUNGLE)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127235_ = m_127267_("ruined_portal_swamp", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.SWAMP)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127236_ = m_127267_("ruined_portal_mountain", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.MOUNTAIN)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127237_ = m_127267_("ruined_portal_ocean", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.OCEAN)));
   public static final ConfiguredStructureFeature<RuinedPortalConfiguration, ? extends StructureFeature<RuinedPortalConfiguration>> f_127238_ = m_127267_("ruined_portal_nether", StructureFeature.f_67019_.m_67065_(new RuinedPortalConfiguration(RuinedPortalFeature.Type.NETHER)));

   private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> m_127267_(String p_127268_, ConfiguredStructureFeature<FC, F> p_127269_) {
      return BuiltinRegistries.m_123876_(BuiltinRegistries.f_123862_, p_127268_, p_127269_);
   }
}