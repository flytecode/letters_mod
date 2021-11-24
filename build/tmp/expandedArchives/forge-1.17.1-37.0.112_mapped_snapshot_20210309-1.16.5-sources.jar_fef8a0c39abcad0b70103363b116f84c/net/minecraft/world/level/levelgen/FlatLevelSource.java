package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class FlatLevelSource extends ChunkGenerator {
   public static final Codec<FlatLevelSource> f_64164_ = FlatLevelGeneratorSettings.f_70347_.fieldOf("settings").xmap(FlatLevelSource::new, FlatLevelSource::m_64191_).codec();
   private final FlatLevelGeneratorSettings f_64165_;

   public FlatLevelSource(FlatLevelGeneratorSettings p_64168_) {
      super(new FixedBiomeSource(p_64168_.m_70390_()), new FixedBiomeSource(p_64168_.m_70400_()), p_64168_.m_70395_(), 0L);
      this.f_64165_ = p_64168_;
   }

   protected Codec<? extends ChunkGenerator> m_6909_() {
      return f_64164_;
   }

   public ChunkGenerator m_6819_(long p_64180_) {
      return this;
   }

   public FlatLevelGeneratorSettings m_64191_() {
      return this.f_64165_;
   }

   public void m_7338_(WorldGenRegion p_64182_, ChunkAccess p_64183_) {
   }

   public int m_142051_(LevelHeightAccessor p_158279_) {
      return p_158279_.m_141937_() + Math.min(p_158279_.m_141928_(), this.f_64165_.m_161917_().size());
   }

   public CompletableFuture<ChunkAccess> m_142189_(Executor p_158281_, StructureFeatureManager p_158282_, ChunkAccess p_158283_) {
      List<BlockState> list = this.f_64165_.m_161917_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      Heightmap heightmap = p_158283_.m_6005_(Heightmap.Types.OCEAN_FLOOR_WG);
      Heightmap heightmap1 = p_158283_.m_6005_(Heightmap.Types.WORLD_SURFACE_WG);

      for(int i = 0; i < Math.min(p_158283_.m_141928_(), list.size()); ++i) {
         BlockState blockstate = list.get(i);
         if (blockstate != null) {
            int j = p_158283_.m_141937_() + i;

            for(int k = 0; k < 16; ++k) {
               for(int l = 0; l < 16; ++l) {
                  p_158283_.m_6978_(blockpos$mutableblockpos.m_122178_(k, j, l), blockstate, false);
                  heightmap.m_64249_(k, j, l, blockstate);
                  heightmap1.m_64249_(k, j, l, blockstate);
               }
            }
         }
      }

      return CompletableFuture.completedFuture(p_158283_);
   }

   public int m_142647_(int p_158274_, int p_158275_, Heightmap.Types p_158276_, LevelHeightAccessor p_158277_) {
      List<BlockState> list = this.f_64165_.m_161917_();

      for(int i = Math.min(list.size(), p_158277_.m_151558_()) - 1; i >= 0; --i) {
         BlockState blockstate = list.get(i);
         if (blockstate != null && p_158276_.m_64299_().test(blockstate)) {
            return p_158277_.m_141937_() + i + 1;
         }
      }

      return p_158277_.m_141937_();
   }

   public NoiseColumn m_141914_(int p_158270_, int p_158271_, LevelHeightAccessor p_158272_) {
      return new NoiseColumn(p_158272_.m_141937_(), this.f_64165_.m_161917_().stream().limit((long)p_158272_.m_141928_()).map((p_64189_) -> {
         return p_64189_ == null ? Blocks.f_50016_.m_49966_() : p_64189_;
      }).toArray((p_64171_) -> {
         return new BlockState[p_64171_];
      }));
   }
}