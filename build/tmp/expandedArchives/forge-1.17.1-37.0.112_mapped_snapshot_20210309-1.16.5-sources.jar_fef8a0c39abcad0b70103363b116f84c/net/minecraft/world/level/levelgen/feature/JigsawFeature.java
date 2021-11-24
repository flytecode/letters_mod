package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureStart;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class JigsawFeature extends StructureFeature<JigsawConfiguration> {
   final int f_66146_;
   final boolean f_66147_;
   final boolean f_66148_;

   public JigsawFeature(Codec<JigsawConfiguration> p_66150_, int p_66151_, boolean p_66152_, boolean p_66153_) {
      super(p_66150_);
      this.f_66146_ = p_66151_;
      this.f_66147_ = p_66152_;
      this.f_66148_ = p_66153_;
   }

   public StructureFeature.StructureStartFactory<JigsawConfiguration> m_6258_() {
      return (p_159909_, p_159910_, p_159911_, p_159912_) -> {
         return new JigsawFeature.FeatureStart(this, p_159910_, p_159911_, p_159912_);
      };
   }

   public static class FeatureStart extends NoiseAffectingStructureStart<JigsawConfiguration> {
      private final JigsawFeature f_66168_;

      public FeatureStart(JigsawFeature p_159914_, ChunkPos p_159915_, int p_159916_, long p_159917_) {
         super(p_159914_, p_159915_, p_159916_, p_159917_);
         this.f_66168_ = p_159914_;
      }

      public void m_142743_(RegistryAccess p_159927_, ChunkGenerator p_159928_, StructureManager p_159929_, ChunkPos p_159930_, Biome p_159931_, JigsawConfiguration p_159932_, LevelHeightAccessor p_159933_) {
         BlockPos blockpos = new BlockPos(p_159930_.m_45604_(), this.f_66168_.f_66146_, p_159930_.m_45605_());
         Pools.m_127189_();
         JigsawPlacement.m_161612_(p_159927_, p_159932_, PoolElementStructurePiece::new, p_159928_, p_159929_, blockpos, this, this.f_73564_, this.f_66168_.f_66147_, this.f_66168_.f_66148_, p_159933_);
      }
   }
}