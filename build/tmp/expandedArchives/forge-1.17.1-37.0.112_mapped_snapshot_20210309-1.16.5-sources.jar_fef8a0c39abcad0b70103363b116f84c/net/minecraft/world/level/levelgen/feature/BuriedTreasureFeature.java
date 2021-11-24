package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BuriedTreasurePieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class BuriedTreasureFeature extends StructureFeature<ProbabilityFeatureConfiguration> {
   private static final int f_159478_ = 10387320;

   public BuriedTreasureFeature(Codec<ProbabilityFeatureConfiguration> p_65313_) {
      super(p_65313_);
   }

   protected boolean m_142290_(ChunkGenerator p_159490_, BiomeSource p_159491_, long p_159492_, WorldgenRandom p_159493_, ChunkPos p_159494_, Biome p_159495_, ChunkPos p_159496_, ProbabilityFeatureConfiguration p_159497_, LevelHeightAccessor p_159498_) {
      p_159493_.m_64694_(p_159492_, p_159494_.f_45578_, p_159494_.f_45579_, 10387320);
      return p_159493_.nextFloat() < p_159497_.f_67859_;
   }

   public StructureFeature.StructureStartFactory<ProbabilityFeatureConfiguration> m_6258_() {
      return BuriedTreasureFeature.BuriedTreasureStart::new;
   }

   public static class BuriedTreasureStart extends StructureStart<ProbabilityFeatureConfiguration> {
      public BuriedTreasureStart(StructureFeature<ProbabilityFeatureConfiguration> p_159500_, ChunkPos p_159501_, int p_159502_, long p_159503_) {
         super(p_159500_, p_159501_, p_159502_, p_159503_);
      }

      public void m_142743_(RegistryAccess p_159513_, ChunkGenerator p_159514_, StructureManager p_159515_, ChunkPos p_159516_, Biome p_159517_, ProbabilityFeatureConfiguration p_159518_, LevelHeightAccessor p_159519_) {
         BlockPos blockpos = new BlockPos(p_159516_.m_151382_(9), 90, p_159516_.m_151391_(9));
         this.m_142679_(new BuriedTreasurePieces.BuriedTreasurePiece(blockpos));
      }

      public BlockPos m_7148_() {
         ChunkPos chunkpos = this.m_163625_();
         return new BlockPos(chunkpos.m_151382_(9), 0, chunkpos.m_151391_(9));
      }
   }
}