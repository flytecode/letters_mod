package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ConfiguredStructureFeature<FC extends FeatureConfiguration, F extends StructureFeature<FC>> {
   public static final Codec<ConfiguredStructureFeature<?, ?>> f_65400_ = Registry.f_122841_.dispatch((p_65410_) -> {
      return p_65410_.f_65403_;
   }, StructureFeature::m_67097_);
   public static final Codec<Supplier<ConfiguredStructureFeature<?, ?>>> f_65401_ = RegistryFileCodec.m_135589_(Registry.f_122882_, f_65400_);
   public static final Codec<List<Supplier<ConfiguredStructureFeature<?, ?>>>> f_65402_ = RegistryFileCodec.m_135600_(Registry.f_122882_, f_65400_);
   public final F f_65403_;
   public final FC f_65404_;

   public ConfiguredStructureFeature(F p_65407_, FC p_65408_) {
      this.f_65403_ = p_65407_;
      this.f_65404_ = p_65408_;
   }

   public StructureStart<?> m_159524_(RegistryAccess p_159525_, ChunkGenerator p_159526_, BiomeSource p_159527_, StructureManager p_159528_, long p_159529_, ChunkPos p_159530_, Biome p_159531_, int p_159532_, StructureFeatureConfiguration p_159533_, LevelHeightAccessor p_159534_) {
      return this.f_65403_.m_160464_(p_159525_, p_159526_, p_159527_, p_159528_, p_159529_, p_159530_, p_159531_, p_159532_, new WorldgenRandom(), p_159533_, this.f_65404_, p_159534_);
   }
}