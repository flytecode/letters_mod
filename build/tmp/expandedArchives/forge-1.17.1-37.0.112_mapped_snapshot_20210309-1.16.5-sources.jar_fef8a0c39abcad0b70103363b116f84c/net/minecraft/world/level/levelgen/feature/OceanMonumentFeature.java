package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class OceanMonumentFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_66469_ = WeightedRandomList.m_146330_(new MobSpawnSettings.SpawnerData(EntityType.f_20455_, 1, 2, 4));

   public OceanMonumentFeature(Codec<NoneFeatureConfiguration> p_66472_) {
      super(p_66472_);
   }

   protected boolean m_5910_() {
      return false;
   }

   protected boolean m_142290_(ChunkGenerator p_160136_, BiomeSource p_160137_, long p_160138_, WorldgenRandom p_160139_, ChunkPos p_160140_, Biome p_160141_, ChunkPos p_160142_, NoneFeatureConfiguration p_160143_, LevelHeightAccessor p_160144_) {
      int i = p_160140_.m_151382_(9);
      int j = p_160140_.m_151391_(9);

      for(Biome biome : p_160137_.m_7901_(i, p_160136_.m_6337_(), j, 16)) {
         if (!biome.m_47536_().m_47808_(this)) {
            return false;
         }
      }

      for(Biome biome1 : p_160137_.m_7901_(i, p_160136_.m_6337_(), j, 29)) {
         if (biome1.m_47567_() != Biome.BiomeCategory.OCEAN && biome1.m_47567_() != Biome.BiomeCategory.RIVER) {
            return false;
         }
      }

      return true;
   }

   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> m_6258_() {
      return OceanMonumentFeature.OceanMonumentStart::new;
   }

   @Override
   public java.util.List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(net.minecraft.world.entity.MobCategory category) {
      if (category == net.minecraft.world.entity.MobCategory.MONSTER)
         return f_66469_.m_146338_();
      return java.util.Collections.emptyList();
   }

   public static class OceanMonumentStart extends StructureStart<NoneFeatureConfiguration> {
      private boolean f_66496_;

      public OceanMonumentStart(StructureFeature<NoneFeatureConfiguration> p_160147_, ChunkPos p_160148_, int p_160149_, long p_160150_) {
         super(p_160147_, p_160148_, p_160149_, p_160150_);
      }

      public void m_142743_(RegistryAccess p_160162_, ChunkGenerator p_160163_, StructureManager p_160164_, ChunkPos p_160165_, Biome p_160166_, NoneFeatureConfiguration p_160167_, LevelHeightAccessor p_160168_) {
         this.m_160151_(p_160165_);
      }

      private void m_160151_(ChunkPos p_160152_) {
         int i = p_160152_.m_45604_() - 29;
         int j = p_160152_.m_45605_() - 29;
         Direction direction = Direction.Plane.HORIZONTAL.m_122560_(this.f_73564_);
         this.m_142679_(new OceanMonumentPieces.MonumentBuilding(this.f_73564_, i, j, direction));
         this.f_66496_ = true;
      }

      public void m_7129_(WorldGenLevel p_66505_, StructureFeatureManager p_66506_, ChunkGenerator p_66507_, Random p_66508_, BoundingBox p_66509_, ChunkPos p_66510_) {
         if (!this.f_66496_) {
            this.f_73562_.clear();
            this.m_160151_(this.m_163625_());
         }

         super.m_7129_(p_66505_, p_66506_, p_66507_, p_66508_, p_66509_, p_66510_);
      }
   }
}
