package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.SwamplandHutPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class SwamplandHutFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_67169_ = WeightedRandomList.m_146330_(new MobSpawnSettings.SpawnerData(EntityType.f_20495_, 1, 1, 1));
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_67170_ = WeightedRandomList.m_146330_(new MobSpawnSettings.SpawnerData(EntityType.f_20553_, 1, 1, 1));

   public SwamplandHutFeature(Codec<NoneFeatureConfiguration> p_67173_) {
      super(p_67173_);
   }

   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> m_6258_() {
      return SwamplandHutFeature.FeatureStart::new;
   }

   @Override
   public java.util.List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(net.minecraft.world.entity.MobCategory category) {
      if (category == net.minecraft.world.entity.MobCategory.MONSTER)
         return f_67169_.m_146338_();
      else if (category == net.minecraft.world.entity.MobCategory.CREATURE)
         return f_67170_.m_146338_();
      return java.util.Collections.emptyList();
   }

   public static class FeatureStart extends StructureStart<NoneFeatureConfiguration> {
      public FeatureStart(StructureFeature<NoneFeatureConfiguration> p_160489_, ChunkPos p_160490_, int p_160491_, long p_160492_) {
         super(p_160489_, p_160490_, p_160491_, p_160492_);
      }

      public void m_142743_(RegistryAccess p_160502_, ChunkGenerator p_160503_, StructureManager p_160504_, ChunkPos p_160505_, Biome p_160506_, NoneFeatureConfiguration p_160507_, LevelHeightAccessor p_160508_) {
         SwamplandHutPiece swamplandhutpiece = new SwamplandHutPiece(this.f_73564_, p_160505_.m_45604_(), p_160505_.m_45605_());
         this.m_142679_(swamplandhutpiece);
      }
   }
}
