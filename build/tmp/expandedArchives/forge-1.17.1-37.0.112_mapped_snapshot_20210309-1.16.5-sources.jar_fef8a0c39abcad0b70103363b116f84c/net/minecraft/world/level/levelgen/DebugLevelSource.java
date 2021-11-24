package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class DebugLevelSource extends ChunkGenerator {
   public static final Codec<DebugLevelSource> f_64111_ = RegistryLookupCodec.m_135622_(Registry.f_122885_).xmap(DebugLevelSource::new, DebugLevelSource::m_64151_).stable().codec();
   private static final int f_158227_ = 2;
   private static List<BlockState> f_64114_ = StreamSupport.stream(Registry.f_122824_.spliterator(), false).flatMap((p_64147_) -> {
      return p_64147_.m_49965_().m_61056_().stream();
   }).collect(Collectors.toList());
   private static int f_64115_ = Mth.m_14167_(Mth.m_14116_((float)f_64114_.size()));
   private static int f_64116_ = Mth.m_14167_((float)f_64114_.size() / (float)f_64115_);
   protected static final BlockState f_64112_ = Blocks.f_50016_.m_49966_();
   protected static final BlockState f_64113_ = Blocks.f_50375_.m_49966_();
   public static final int f_158225_ = 70;
   public static final int f_158226_ = 60;
   private final Registry<Biome> f_64117_;

   public DebugLevelSource(Registry<Biome> p_64120_) {
      super(new FixedBiomeSource(p_64120_.m_123013_(Biomes.f_48202_)), new StructureSettings(false));
      this.f_64117_ = p_64120_;
   }

   public Registry<Biome> m_64151_() {
      return this.f_64117_;
   }

   protected Codec<? extends ChunkGenerator> m_6909_() {
      return f_64111_;
   }

   public ChunkGenerator m_6819_(long p_64130_) {
      return this;
   }

   public void m_7338_(WorldGenRegion p_64140_, ChunkAccess p_64141_) {
   }

   public void m_6013_(long p_64132_, BiomeManager p_64133_, ChunkAccess p_64134_, GenerationStep.Carving p_64135_) {
   }

   public void m_7399_(WorldGenRegion p_64137_, StructureFeatureManager p_64138_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      ChunkPos chunkpos = p_64137_.m_143488_();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = SectionPos.m_175554_(chunkpos.f_45578_, i);
            int l = SectionPos.m_175554_(chunkpos.f_45579_, j);
            p_64137_.m_7731_(blockpos$mutableblockpos.m_122178_(k, 60, l), f_64113_, 2);
            BlockState blockstate = m_64148_(k, l);
            if (blockstate != null) {
               p_64137_.m_7731_(blockpos$mutableblockpos.m_122178_(k, 70, l), blockstate, 2);
            }
         }
      }

   }

   public CompletableFuture<ChunkAccess> m_142189_(Executor p_158238_, StructureFeatureManager p_158239_, ChunkAccess p_158240_) {
      return CompletableFuture.completedFuture(p_158240_);
   }

   public int m_142647_(int p_158233_, int p_158234_, Heightmap.Types p_158235_, LevelHeightAccessor p_158236_) {
      return 0;
   }

   public NoiseColumn m_141914_(int p_158229_, int p_158230_, LevelHeightAccessor p_158231_) {
      return new NoiseColumn(0, new BlockState[0]);
   }

   public static BlockState m_64148_(int p_64149_, int p_64150_) {
      BlockState blockstate = f_64112_;
      if (p_64149_ > 0 && p_64150_ > 0 && p_64149_ % 2 != 0 && p_64150_ % 2 != 0) {
         p_64149_ = p_64149_ / 2;
         p_64150_ = p_64150_ / 2;
         if (p_64149_ <= f_64115_ && p_64150_ <= f_64116_) {
            int i = Mth.m_14040_(p_64149_ * f_64115_ + p_64150_);
            if (i < f_64114_.size()) {
               blockstate = f_64114_.get(i);
            }
         }
      }

      return blockstate;
   }
   
   public static void initValidStates() {
      f_64114_ = StreamSupport.stream(Registry.f_122824_.spliterator(), false).flatMap(block -> block.m_49965_().m_61056_().stream()).collect(Collectors.toList());
      f_64115_ = Mth.m_14167_(Mth.m_14116_(f_64114_.size()));
      f_64116_ = Mth.m_14167_((float) (f_64114_.size() / f_64115_));
   }
}
