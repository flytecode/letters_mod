package net.minecraft.world.level.levelgen.feature;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FeaturePlaceContext<FC extends FeatureConfiguration> {
   private final WorldGenLevel f_159763_;
   private final ChunkGenerator f_159764_;
   private final Random f_159765_;
   private final BlockPos f_159766_;
   private final FC f_159767_;

   public FeaturePlaceContext(WorldGenLevel p_159769_, ChunkGenerator p_159770_, Random p_159771_, BlockPos p_159772_, FC p_159773_) {
      this.f_159763_ = p_159769_;
      this.f_159764_ = p_159770_;
      this.f_159765_ = p_159771_;
      this.f_159766_ = p_159772_;
      this.f_159767_ = p_159773_;
   }

   public WorldGenLevel m_159774_() {
      return this.f_159763_;
   }

   public ChunkGenerator m_159775_() {
      return this.f_159764_;
   }

   public Random m_159776_() {
      return this.f_159765_;
   }

   public BlockPos m_159777_() {
      return this.f_159766_;
   }

   public FC m_159778_() {
      return this.f_159767_;
   }
}