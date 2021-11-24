package net.minecraft.data.worldgen;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;

public class SurfaceBuilders {
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127276_ = m_127300_("badlands", SurfaceBuilder.f_75168_.m_75223_(SurfaceBuilder.f_75206_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127277_ = m_127300_("basalt_deltas", SurfaceBuilder.f_75175_.m_75223_(SurfaceBuilder.f_75213_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127278_ = m_127300_("crimson_forest", SurfaceBuilder.f_75173_.m_75223_(SurfaceBuilder.f_75211_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127279_ = m_127300_("desert", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75203_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127280_ = m_127300_("end", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75210_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127281_ = m_127300_("eroded_badlands", SurfaceBuilder.f_75170_.m_75223_(SurfaceBuilder.f_75206_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127282_ = m_127300_("frozen_ocean", SurfaceBuilder.f_75171_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127283_ = m_127300_("full_sand", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75205_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127284_ = m_127300_("giant_tree_taiga", SurfaceBuilder.f_75218_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127285_ = m_127300_("grass", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127286_ = m_127300_("gravelly_mountain", SurfaceBuilder.f_75217_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127287_ = m_127300_("ice_spikes", SurfaceBuilder.f_75214_.m_75223_(new SurfaceBuilderBaseConfiguration(Blocks.f_50127_.m_49966_(), Blocks.f_50493_.m_49966_(), Blocks.f_49994_.m_49966_())));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127288_ = m_127300_("mountain", SurfaceBuilder.f_75215_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127289_ = m_127300_("mycelium", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75207_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127290_ = m_127300_("nether", SurfaceBuilder.f_75172_.m_75223_(SurfaceBuilder.f_75208_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127291_ = m_127300_("nope", SurfaceBuilder.f_75176_.m_75223_(SurfaceBuilder.f_75201_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127292_ = m_127300_("ocean_sand", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75204_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127293_ = m_127300_("shattered_savanna", SurfaceBuilder.f_75216_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127294_ = m_127300_("soul_sand_valley", SurfaceBuilder.f_75174_.m_75223_(SurfaceBuilder.f_75209_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127295_ = m_127300_("stone", SurfaceBuilder.f_75214_.m_75223_(SurfaceBuilder.f_75201_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127296_ = m_127300_("swamp", SurfaceBuilder.f_75167_.m_75223_(SurfaceBuilder.f_75200_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127297_ = m_127300_("warped_forest", SurfaceBuilder.f_75173_.m_75223_(SurfaceBuilder.f_75212_));
   public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> f_127298_ = m_127300_("wooded_badlands", SurfaceBuilder.f_75169_.m_75223_(SurfaceBuilder.f_75206_));

   private static <SC extends SurfaceBuilderConfiguration> ConfiguredSurfaceBuilder<SC> m_127300_(String p_127301_, ConfiguredSurfaceBuilder<SC> p_127302_) {
      return BuiltinRegistries.m_123876_(BuiltinRegistries.f_123859_, p_127301_, p_127302_);
   }
}