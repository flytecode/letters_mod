package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.SingleBaseStoneSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public abstract class ChunkGenerator {
   public static final Codec<ChunkGenerator> f_62136_ = Registry.f_122890_.dispatchStable(ChunkGenerator::m_6909_, Function.identity());
   protected final BiomeSource f_62137_;
   protected final BiomeSource f_62138_;
   private final StructureSettings f_62139_;
   private final long f_62140_;
   private final List<ChunkPos> f_62141_ = Lists.newArrayList();
   private final BaseStoneSource f_156149_;

   public ChunkGenerator(BiomeSource p_62149_, StructureSettings p_62150_) {
      this(p_62149_, p_62149_, p_62150_, 0L);
   }

   public ChunkGenerator(BiomeSource p_62144_, BiomeSource p_62145_, StructureSettings p_62146_, long p_62147_) {
      this.f_62137_ = p_62144_;
      this.f_62138_ = p_62145_;
      this.f_62139_ = p_62146_;
      this.f_62140_ = p_62147_;
      this.f_156149_ = new SingleBaseStoneSource(Blocks.f_50069_.m_49966_());
   }

   private void m_62219_() {
      if (this.f_62141_.isEmpty()) {
         StrongholdConfiguration strongholdconfiguration = this.f_62139_.m_64597_();
         if (strongholdconfiguration != null && strongholdconfiguration.m_68161_() != 0) {
            List<Biome> list = Lists.newArrayList();

            for(Biome biome : this.f_62137_.m_47922_()) {
               if (biome.m_47536_().m_47808_(StructureFeature.f_67022_)) {
                  list.add(biome);
               }
            }

            int k1 = strongholdconfiguration.m_68157_();
            int l1 = strongholdconfiguration.m_68161_();
            int i = strongholdconfiguration.m_68160_();
            Random random = new Random();
            random.setSeed(this.f_62140_);
            double d0 = random.nextDouble() * Math.PI * 2.0D;
            int j = 0;
            int k = 0;

            for(int l = 0; l < l1; ++l) {
               double d1 = (double)(4 * k1 + k1 * k * 6) + (random.nextDouble() - 0.5D) * (double)k1 * 2.5D;
               int i1 = (int)Math.round(Math.cos(d0) * d1);
               int j1 = (int)Math.round(Math.sin(d0) * d1);
               BlockPos blockpos = this.f_62137_.m_47909_(SectionPos.m_175554_(i1, 8), 0, SectionPos.m_175554_(j1, 8), 112, list::contains, random);
               if (blockpos != null) {
                  i1 = SectionPos.m_123171_(blockpos.m_123341_());
                  j1 = SectionPos.m_123171_(blockpos.m_123343_());
               }

               this.f_62141_.add(new ChunkPos(i1, j1));
               d0 += (Math.PI * 2D) / (double)i;
               ++j;
               if (j == i) {
                  ++k;
                  j = 0;
                  i = i + 2 * i / (k + 1);
                  i = Math.min(i, l1 - l);
                  d0 += random.nextDouble() * Math.PI * 2.0D;
               }
            }

         }
      }
   }

   protected abstract Codec<? extends ChunkGenerator> m_6909_();

   public abstract ChunkGenerator m_6819_(long p_62156_);

   public void m_62196_(Registry<Biome> p_62197_, ChunkAccess p_62198_) {
      ChunkPos chunkpos = p_62198_.m_7697_();
      ((ProtoChunk)p_62198_).m_7329_(new ChunkBiomeContainer(p_62197_, p_62198_, chunkpos, this.f_62138_));
   }

   public void m_6013_(long p_62157_, BiomeManager p_62158_, ChunkAccess p_62159_, GenerationStep.Carving p_62160_) {
      BiomeManager biomemanager = p_62158_.m_47879_(this.f_62137_);
      WorldgenRandom worldgenrandom = new WorldgenRandom();
      int i = 8;
      ChunkPos chunkpos = p_62159_.m_7697_();
      CarvingContext carvingcontext = new CarvingContext(this, p_62159_);
      Aquifer aquifer = this.m_142439_(p_62159_);
      BitSet bitset = ((ProtoChunk)p_62159_).m_6547_(p_62160_);

      for(int j = -8; j <= 8; ++j) {
         for(int k = -8; k <= 8; ++k) {
            ChunkPos chunkpos1 = new ChunkPos(chunkpos.f_45578_ + j, chunkpos.f_45579_ + k);
            BiomeGenerationSettings biomegenerationsettings = this.f_62137_.m_7158_(QuartPos.m_175400_(chunkpos1.m_45604_()), 0, QuartPos.m_175400_(chunkpos1.m_45605_())).m_47536_();
            List<Supplier<ConfiguredWorldCarver<?>>> list = biomegenerationsettings.m_47799_(p_62160_);
            ListIterator<Supplier<ConfiguredWorldCarver<?>>> listiterator = list.listIterator();

            while(listiterator.hasNext()) {
               int l = listiterator.nextIndex();
               ConfiguredWorldCarver<?> configuredworldcarver = listiterator.next().get();
               worldgenrandom.m_64703_(p_62157_ + (long)l, chunkpos1.f_45578_, chunkpos1.f_45579_);
               if (configuredworldcarver.m_159273_(worldgenrandom)) {
                  configuredworldcarver.m_159265_(carvingcontext, p_62159_, biomemanager::m_47881_, worldgenrandom, aquifer, chunkpos1, bitset);
               }
            }
         }
      }

   }

   protected Aquifer m_142439_(ChunkAccess p_156162_) {
      return Aquifer.m_157956_(this.m_6337_(), Blocks.f_49990_.m_49966_());
   }

   @Nullable
   public BlockPos m_62161_(ServerLevel p_62162_, StructureFeature<?> p_62163_, BlockPos p_62164_, int p_62165_, boolean p_62166_) {
      if (!this.f_62137_.m_47917_(p_62163_)) {
         return null;
      } else if (p_62163_ == StructureFeature.f_67022_) {
         this.m_62219_();
         BlockPos blockpos = null;
         double d0 = Double.MAX_VALUE;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(ChunkPos chunkpos : this.f_62141_) {
            blockpos$mutableblockpos.m_122178_(SectionPos.m_175554_(chunkpos.f_45578_, 8), 32, SectionPos.m_175554_(chunkpos.f_45579_, 8));
            double d1 = blockpos$mutableblockpos.m_123331_(p_62164_);
            if (blockpos == null) {
               blockpos = new BlockPos(blockpos$mutableblockpos);
               d0 = d1;
            } else if (d1 < d0) {
               blockpos = new BlockPos(blockpos$mutableblockpos);
               d0 = d1;
            }
         }

         return blockpos;
      } else {
         StructureFeatureConfiguration structurefeatureconfiguration = this.f_62139_.m_64593_(p_62163_);
         return structurefeatureconfiguration == null ? null : p_62163_.m_67046_(p_62162_, p_62162_.m_8595_(), p_62164_, p_62165_, p_62166_, p_62162_.m_7328_(), structurefeatureconfiguration);
      }
   }

   public void m_7399_(WorldGenRegion p_62168_, StructureFeatureManager p_62169_) {
      ChunkPos chunkpos = p_62168_.m_143488_();
      int i = chunkpos.m_45604_();
      int j = chunkpos.m_45605_();
      BlockPos blockpos = new BlockPos(i, p_62168_.m_141937_(), j);
      Biome biome = this.f_62137_.m_151754_(chunkpos);
      WorldgenRandom worldgenrandom = new WorldgenRandom();
      long k = worldgenrandom.m_64690_(p_62168_.m_7328_(), i, j);

      try {
         biome.m_47484_(p_62169_, this, p_62168_, k, worldgenrandom, blockpos);
      } catch (Exception exception) {
         CrashReport crashreport = CrashReport.m_127521_(exception, "Biome decoration");
         crashreport.m_127514_("Generation").m_128159_("CenterX", chunkpos.f_45578_).m_128159_("CenterZ", chunkpos.f_45579_).m_128159_("Seed", k).m_128159_("Biome", biome);
         throw new ReportedException(crashreport);
      }
   }

   public abstract void m_7338_(WorldGenRegion p_62170_, ChunkAccess p_62171_);

   public void m_6929_(WorldGenRegion p_62167_) {
   }

   public StructureSettings m_62205_() {
      return this.f_62139_;
   }

   public int m_142051_(LevelHeightAccessor p_156157_) {
      return 64;
   }

   public BiomeSource m_62218_() {
      return this.f_62138_;
   }

   public int m_6331_() {
      return 256;
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_142184_(Biome p_156158_, StructureFeatureManager p_156159_, MobCategory p_156160_, BlockPos p_156161_) {
      return p_156158_.m_47518_().m_151798_(p_156160_);
   }

   public void m_62199_(RegistryAccess p_62200_, StructureFeatureManager p_62201_, ChunkAccess p_62202_, StructureManager p_62203_, long p_62204_) {
      Biome biome = this.f_62137_.m_151754_(p_62202_.m_7697_());
      this.m_156163_(StructureFeatures.f_127249_, p_62200_, p_62201_, p_62202_, p_62203_, p_62204_, biome);

      for(Supplier<ConfiguredStructureFeature<?, ?>> supplier : biome.m_47536_().m_47796_()) {
         this.m_156163_(supplier.get(), p_62200_, p_62201_, p_62202_, p_62203_, p_62204_, biome);
      }

   }

   private void m_156163_(ConfiguredStructureFeature<?, ?> p_156164_, RegistryAccess p_156165_, StructureFeatureManager p_156166_, ChunkAccess p_156167_, StructureManager p_156168_, long p_156169_, Biome p_156170_) {
      ChunkPos chunkpos = p_156167_.m_7697_();
      SectionPos sectionpos = SectionPos.m_175562_(p_156167_);
      StructureStart<?> structurestart = p_156166_.m_47297_(sectionpos, p_156164_.f_65403_, p_156167_);
      int i = structurestart != null ? structurestart.m_73608_() : 0;
      StructureFeatureConfiguration structurefeatureconfiguration = this.f_62139_.m_64593_(p_156164_.f_65403_);
      if (structurefeatureconfiguration != null) {
         StructureStart<?> structurestart1 = p_156164_.m_159524_(p_156165_, this, this.f_62137_, p_156168_, p_156169_, chunkpos, p_156170_, i, structurefeatureconfiguration, p_156167_);
         p_156166_.m_47301_(sectionpos, p_156164_.f_65403_, structurestart1, p_156167_);
      }

   }

   public void m_62177_(WorldGenLevel p_62178_, StructureFeatureManager p_62179_, ChunkAccess p_62180_) {
      int i = 8;
      ChunkPos chunkpos = p_62180_.m_7697_();
      int j = chunkpos.f_45578_;
      int k = chunkpos.f_45579_;
      int l = chunkpos.m_45604_();
      int i1 = chunkpos.m_45605_();
      SectionPos sectionpos = SectionPos.m_175562_(p_62180_);

      for(int j1 = j - 8; j1 <= j + 8; ++j1) {
         for(int k1 = k - 8; k1 <= k + 8; ++k1) {
            long l1 = ChunkPos.m_45589_(j1, k1);

            for(StructureStart<?> structurestart : p_62178_.m_6325_(j1, k1).m_6633_().values()) {
               try {
                  if (structurestart.m_73603_() && structurestart.m_73601_().m_71019_(l, i1, l + 15, i1 + 15)) {
                     p_62179_.m_47292_(sectionpos, structurestart.m_73610_(), l1, p_62180_);
                     DebugPackets.m_133711_(p_62178_, structurestart);
                  }
               } catch (Exception exception) {
                  CrashReport crashreport = CrashReport.m_127521_(exception, "Generating structure reference");
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Structure");
                  crashreportcategory.m_128165_("Id", () -> {
                     return Registry.f_122841_.m_7981_(structurestart.m_73610_()).toString();
                  });
                  crashreportcategory.m_128165_("Name", () -> {
                     return structurestart.m_73610_().m_67098_();
                  });
                  crashreportcategory.m_128165_("Class", () -> {
                     return structurestart.m_73610_().getClass().getCanonicalName();
                  });
                  throw new ReportedException(crashreport);
               }
            }
         }
      }

   }

   public abstract CompletableFuture<ChunkAccess> m_142189_(Executor p_156171_, StructureFeatureManager p_156172_, ChunkAccess p_156173_);

   public int m_6337_() {
      return 63;
   }

   public int m_142062_() {
      return 0;
   }

   public abstract int m_142647_(int p_156153_, int p_156154_, Heightmap.Types p_156155_, LevelHeightAccessor p_156156_);

   public abstract NoiseColumn m_141914_(int p_156150_, int p_156151_, LevelHeightAccessor p_156152_);

   public int m_156174_(int p_156175_, int p_156176_, Heightmap.Types p_156177_, LevelHeightAccessor p_156178_) {
      return this.m_142647_(p_156175_, p_156176_, p_156177_, p_156178_);
   }

   public int m_156179_(int p_156180_, int p_156181_, Heightmap.Types p_156182_, LevelHeightAccessor p_156183_) {
      return this.m_142647_(p_156180_, p_156181_, p_156182_, p_156183_) - 1;
   }

   public boolean m_62172_(ChunkPos p_62173_) {
      this.m_62219_();
      return this.f_62141_.contains(p_62173_);
   }

   public BaseStoneSource m_142168_() {
      return this.f_156149_;
   }

   static {
      Registry.m_122961_(Registry.f_122890_, "noise", NoiseBasedChunkGenerator.f_64314_);
      Registry.m_122961_(Registry.f_122890_, "flat", FlatLevelSource.f_64164_);
      Registry.m_122961_(Registry.f_122890_, "debug", DebugLevelSource.f_64111_);
   }
}