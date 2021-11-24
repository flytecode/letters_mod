package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NetherBridgePieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class NetherFortressFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_66381_ = WeightedRandomList.m_146330_(new MobSpawnSettings.SpawnerData(EntityType.f_20551_, 10, 2, 3), new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 5, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.f_20497_, 8, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.f_20524_, 2, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 3, 4, 4));

   public NetherFortressFeature(Codec<NoneFeatureConfiguration> p_66384_) {
      super(p_66384_);
   }

   protected boolean m_142290_(ChunkGenerator p_160080_, BiomeSource p_160081_, long p_160082_, WorldgenRandom p_160083_, ChunkPos p_160084_, Biome p_160085_, ChunkPos p_160086_, NoneFeatureConfiguration p_160087_, LevelHeightAccessor p_160088_) {
      return p_160083_.nextInt(5) < 2;
   }

   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> m_6258_() {
      return NetherFortressFeature.NetherBridgeStart::new;
   }

   @Override
   public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(net.minecraft.world.entity.MobCategory category) {
      if (category == net.minecraft.world.entity.MobCategory.MONSTER)
         return f_66381_.m_146338_();
      return java.util.Collections.emptyList();
   }

   public static class NetherBridgeStart extends StructureStart<NoneFeatureConfiguration> {
      public NetherBridgeStart(StructureFeature<NoneFeatureConfiguration> p_160091_, ChunkPos p_160092_, int p_160093_, long p_160094_) {
         super(p_160091_, p_160092_, p_160093_, p_160094_);
      }

      public void m_142743_(RegistryAccess p_160104_, ChunkGenerator p_160105_, StructureManager p_160106_, ChunkPos p_160107_, Biome p_160108_, NoneFeatureConfiguration p_160109_, LevelHeightAccessor p_160110_) {
         NetherBridgePieces.StartPiece netherbridgepieces$startpiece = new NetherBridgePieces.StartPiece(this.f_73564_, p_160107_.m_151382_(2), p_160107_.m_151391_(2));
         this.m_142679_(netherbridgepieces$startpiece);
         netherbridgepieces$startpiece.m_142537_(netherbridgepieces$startpiece, this, this.f_73564_);
         List<StructurePiece> list = netherbridgepieces$startpiece.f_72022_;

         while(!list.isEmpty()) {
            int i = this.f_73564_.nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.m_142537_(netherbridgepieces$startpiece, this, this.f_73564_);
         }

         this.m_73597_(this.f_73564_, 48, 70);
      }
   }
}
