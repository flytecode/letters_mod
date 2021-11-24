package net.minecraft.world.level.levelgen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.level.levelgen.synth.SurfaceNoise;

public class NoiseBasedChunkGenerator extends ChunkGenerator {
   public static final Codec<NoiseBasedChunkGenerator> f_64314_ = RecordCodecBuilder.create((p_64405_) -> {
      return p_64405_.group(BiomeSource.f_47888_.fieldOf("biome_source").forGetter((p_158489_) -> {
         return p_158489_.f_62137_;
      }), Codec.LONG.fieldOf("seed").stable().forGetter((p_158487_) -> {
         return p_158487_.f_64333_;
      }), NoiseGeneratorSettings.f_64431_.fieldOf("settings").forGetter((p_158458_) -> {
         return p_158458_.f_64318_;
      })).apply(p_64405_, p_64405_.stable(NoiseBasedChunkGenerator::new));
   });
   private static final BlockState f_64321_ = Blocks.f_50016_.m_49966_();
   private static final BlockState[] f_158373_ = new BlockState[0];
   protected final int f_158374_;
   protected final int f_158375_;
   protected final int f_158376_;
   protected final int f_158377_;
   protected final int f_158378_;
   protected final SurfaceNoise f_64330_;
   protected final NormalNoise f_158379_;
   protected final NormalNoise f_158380_;
   protected final NormalNoise f_158381_;
   protected final BlockState f_64316_;
   protected final BlockState f_64317_;
   protected final long f_64333_;
   protected final Supplier<NoiseGeneratorSettings> f_64318_;
   protected final int f_64334_;
   protected final NoiseSampler f_158382_;
   protected final BaseStoneSource f_158383_;
   protected final OreVeinifier f_158384_;
   protected final NoodleCavifier f_158385_;

   public NoiseBasedChunkGenerator(BiomeSource p_64337_, long p_64338_, Supplier<NoiseGeneratorSettings> p_64339_) {
      this(p_64337_, p_64337_, p_64338_, p_64339_);
   }

   private NoiseBasedChunkGenerator(BiomeSource p_64341_, BiomeSource p_64342_, long p_64343_, Supplier<NoiseGeneratorSettings> p_64344_) {
      super(p_64341_, p_64342_, p_64344_.get().m_64457_(), p_64343_);
      this.f_64333_ = p_64343_;
      NoiseGeneratorSettings noisegeneratorsettings = p_64344_.get();
      this.f_64318_ = p_64344_;
      NoiseSettings noisesettings = noisegeneratorsettings.m_64481_();
      this.f_64334_ = noisesettings.m_64534_();
      this.f_158374_ = QuartPos.m_175402_(noisesettings.m_64541_());
      this.f_158375_ = QuartPos.m_175402_(noisesettings.m_64540_());
      this.f_64316_ = noisegeneratorsettings.m_64482_();
      this.f_64317_ = noisegeneratorsettings.m_64483_();
      this.f_158376_ = 16 / this.f_158375_;
      this.f_158377_ = noisesettings.m_64534_() / this.f_158374_;
      this.f_158378_ = 16 / this.f_158375_;
      WorldgenRandom worldgenrandom = new WorldgenRandom(p_64343_);
      BlendedNoise blendednoise = new BlendedNoise(worldgenrandom);
      this.f_64330_ = (SurfaceNoise)(noisesettings.m_64544_() ? new PerlinSimplexNoise(worldgenrandom, IntStream.rangeClosed(-3, 0)) : new PerlinNoise(worldgenrandom, IntStream.rangeClosed(-3, 0)));
      worldgenrandom.m_158876_(2620);
      PerlinNoise perlinnoise = new PerlinNoise(worldgenrandom, IntStream.rangeClosed(-15, 0));
      SimplexNoise simplexnoise;
      if (noisesettings.m_64546_()) {
         WorldgenRandom worldgenrandom1 = new WorldgenRandom(p_64343_);
         worldgenrandom1.m_158876_(17292);
         simplexnoise = new SimplexNoise(worldgenrandom1);
      } else {
         simplexnoise = null;
      }

      this.f_158379_ = NormalNoise.m_164354_(new SimpleRandomSource(worldgenrandom.nextLong()), -3, 1.0D);
      this.f_158380_ = NormalNoise.m_164354_(new SimpleRandomSource(worldgenrandom.nextLong()), -3, 1.0D, 0.0D, 2.0D);
      this.f_158381_ = NormalNoise.m_164354_(new SimpleRandomSource(worldgenrandom.nextLong()), -1, 1.0D, 0.0D);
      NoiseModifier noisemodifier;
      if (noisegeneratorsettings.m_158568_()) {
         noisemodifier = new Cavifier(worldgenrandom, noisesettings.m_158703_() / this.f_158374_);
      } else {
         noisemodifier = NoiseModifier.f_158626_;
      }

      this.f_158382_ = new NoiseSampler(p_64341_, this.f_158375_, this.f_158374_, this.f_158377_, noisesettings, blendednoise, simplexnoise, perlinnoise, noisemodifier);
      this.f_158383_ = new DepthBasedReplacingBaseStoneSource(p_64343_, this.f_64316_, Blocks.f_152550_.m_49966_(), noisegeneratorsettings);
      this.f_158384_ = new OreVeinifier(p_64343_, this.f_64316_, this.f_158375_, this.f_158374_, noisegeneratorsettings.m_64481_().m_158703_());
      this.f_158385_ = new NoodleCavifier(p_64343_);
   }

   private boolean m_158492_() {
      return this.f_64318_.get().m_158567_();
   }

   protected Codec<? extends ChunkGenerator> m_6909_() {
      return f_64314_;
   }

   public ChunkGenerator m_6819_(long p_64374_) {
      return new NoiseBasedChunkGenerator(this.f_62137_.m_7206_(p_64374_), p_64374_, this.f_64318_);
   }

   public boolean m_64375_(long p_64376_, ResourceKey<NoiseGeneratorSettings> p_64377_) {
      return this.f_64333_ == p_64376_ && this.f_64318_.get().m_64476_(p_64377_);
   }

   private double[] m_158391_(int p_158392_, int p_158393_, int p_158394_, int p_158395_) {
      double[] adouble = new double[p_158395_ + 1];
      this.m_158466_(adouble, p_158392_, p_158393_, p_158394_, p_158395_);
      return adouble;
   }

   protected void m_158466_(double[] p_158467_, int p_158468_, int p_158469_, int p_158470_, int p_158471_) {
      NoiseSettings noisesettings = this.f_64318_.get().m_64481_();
      this.f_158382_.m_158678_(p_158467_, p_158468_, p_158469_, noisesettings, this.m_6337_(), p_158470_, p_158471_);
   }

   public int m_142647_(int p_158405_, int p_158406_, Heightmap.Types p_158407_, LevelHeightAccessor p_158408_) {
      int i = Math.max(this.f_64318_.get().m_64481_().m_158703_(), p_158408_.m_141937_());
      int j = Math.min(this.f_64318_.get().m_64481_().m_158703_() + this.f_64318_.get().m_64481_().m_64534_(), p_158408_.m_151558_());
      int k = Mth.m_14042_(i, this.f_158374_);
      int l = Mth.m_14042_(j - i, this.f_158374_);
      return l <= 0 ? p_158408_.m_141937_() : this.m_158413_(p_158405_, p_158406_, (BlockState[])null, p_158407_.m_64299_(), k, l).orElse(p_158408_.m_141937_());
   }

   public NoiseColumn m_141914_(int p_158401_, int p_158402_, LevelHeightAccessor p_158403_) {
      int i = Math.max(this.f_64318_.get().m_64481_().m_158703_(), p_158403_.m_141937_());
      int j = Math.min(this.f_64318_.get().m_64481_().m_158703_() + this.f_64318_.get().m_64481_().m_64534_(), p_158403_.m_151558_());
      int k = Mth.m_14042_(i, this.f_158374_);
      int l = Mth.m_14042_(j - i, this.f_158374_);
      if (l <= 0) {
         return new NoiseColumn(i, f_158373_);
      } else {
         BlockState[] ablockstate = new BlockState[l * this.f_158374_];
         this.m_158413_(p_158401_, p_158402_, ablockstate, (Predicate<BlockState>)null, k, l);
         return new NoiseColumn(i, ablockstate);
      }
   }

   public BaseStoneSource m_142168_() {
      return this.f_158383_;
   }

   protected OptionalInt m_158413_(int p_158414_, int p_158415_, @Nullable BlockState[] p_158416_, @Nullable Predicate<BlockState> p_158417_, int p_158418_, int p_158419_) {
      int i = SectionPos.m_123171_(p_158414_);
      int j = SectionPos.m_123171_(p_158415_);
      int k = Math.floorDiv(p_158414_, this.f_158375_);
      int l = Math.floorDiv(p_158415_, this.f_158375_);
      int i1 = Math.floorMod(p_158414_, this.f_158375_);
      int j1 = Math.floorMod(p_158415_, this.f_158375_);
      double d0 = (double)i1 / (double)this.f_158375_;
      double d1 = (double)j1 / (double)this.f_158375_;
      double[][] adouble = new double[][]{this.m_158391_(k, l, p_158418_, p_158419_), this.m_158391_(k, l + 1, p_158418_, p_158419_), this.m_158391_(k + 1, l, p_158418_, p_158419_), this.m_158391_(k + 1, l + 1, p_158418_, p_158419_)};
      Aquifer aquifer = this.m_158396_(p_158418_, p_158419_, new ChunkPos(i, j));

      for(int k1 = p_158419_ - 1; k1 >= 0; --k1) {
         double d2 = adouble[0][k1];
         double d3 = adouble[1][k1];
         double d4 = adouble[2][k1];
         double d5 = adouble[3][k1];
         double d6 = adouble[0][k1 + 1];
         double d7 = adouble[1][k1 + 1];
         double d8 = adouble[2][k1 + 1];
         double d9 = adouble[3][k1 + 1];

         for(int l1 = this.f_158374_ - 1; l1 >= 0; --l1) {
            double d10 = (double)l1 / (double)this.f_158374_;
            double d11 = Mth.m_14019_(d10, d0, d1, d2, d6, d4, d8, d3, d7, d5, d9);
            int i2 = k1 * this.f_158374_ + l1;
            int j2 = i2 + p_158418_ * this.f_158374_;
            BlockState blockstate = this.m_158439_(Beardifier.f_158059_, aquifer, this.f_158383_, NoiseModifier.f_158626_, p_158414_, j2, p_158415_, d11);
            if (p_158416_ != null) {
               p_158416_[i2] = blockstate;
            }

            if (p_158417_ != null && p_158417_.test(blockstate)) {
               return OptionalInt.of(j2 + 1);
            }
         }
      }

      return OptionalInt.empty();
   }

   protected Aquifer m_158396_(int p_158397_, int p_158398_, ChunkPos p_158399_) {
      return !this.m_158492_() ? Aquifer.m_157956_(this.m_6337_(), this.f_64317_) : Aquifer.m_157959_(p_158399_, this.f_158379_, this.f_158380_, this.f_158381_, this.f_64318_.get(), this.f_158382_, p_158397_ * this.f_158374_, p_158398_ * this.f_158374_);
   }

   protected BlockState m_158439_(Beardifier p_158440_, Aquifer p_158441_, BaseStoneSource p_158442_, NoiseModifier p_158443_, int p_158444_, int p_158445_, int p_158446_, double p_158447_) {
      double d0 = Mth.m_14008_(p_158447_ / 200.0D, -1.0D, 1.0D);
      d0 = d0 / 2.0D - d0 * d0 * d0 / 24.0D;
      d0 = p_158443_.m_142124_(d0, p_158444_, p_158445_, p_158446_);
      d0 = d0 + p_158440_.m_158072_(p_158444_, p_158445_, p_158446_);
      return p_158441_.m_142419_(p_158442_, p_158444_, p_158445_, p_158446_, d0);
   }

   public void m_7338_(WorldGenRegion p_64381_, ChunkAccess p_64382_) {
      ChunkPos chunkpos = p_64382_.m_7697_();
      int i = chunkpos.f_45578_;
      int j = chunkpos.f_45579_;
      WorldgenRandom worldgenrandom = new WorldgenRandom();
      worldgenrandom.m_64682_(i, j);
      ChunkPos chunkpos1 = p_64382_.m_7697_();
      int k = chunkpos1.m_45604_();
      int l = chunkpos1.m_45605_();
      double d0 = 0.0625D;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i1 = 0; i1 < 16; ++i1) {
         for(int j1 = 0; j1 < 16; ++j1) {
            int k1 = k + i1;
            int l1 = l + j1;
            int i2 = p_64382_.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, i1, j1) + 1;
            double d1 = this.f_64330_.m_5495_((double)k1 * 0.0625D, (double)l1 * 0.0625D, 0.0625D, (double)i1 * 0.0625D) * 15.0D;
            int j2 = this.f_64318_.get().m_158566_();
            p_64381_.m_46857_(blockpos$mutableblockpos.m_122178_(k + i1, i2, l + j1)).m_151682_(worldgenrandom, p_64382_, k1, l1, i2, d1, this.f_64316_, this.f_64317_, this.m_6337_(), j2, p_64381_.m_7328_());
         }
      }

      this.m_64399_(p_64382_, worldgenrandom);
   }

   protected void m_64399_(ChunkAccess p_64400_, Random p_64401_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int i = p_64400_.m_7697_().m_45604_();
      int j = p_64400_.m_7697_().m_45605_();
      NoiseGeneratorSettings noisegeneratorsettings = this.f_64318_.get();
      int k = noisegeneratorsettings.m_64481_().m_158703_();
      int l = k + noisegeneratorsettings.m_64485_();
      int i1 = this.f_64334_ - 1 + k - noisegeneratorsettings.m_64484_();
      int j1 = 5;
      int k1 = p_64400_.m_141937_();
      int l1 = p_64400_.m_151558_();
      boolean flag = i1 + 5 - 1 >= k1 && i1 < l1;
      boolean flag1 = l + 5 - 1 >= k1 && l < l1;
      if (flag || flag1) {
         for(BlockPos blockpos : BlockPos.m_121976_(i, 0, j, i + 15, 0, j + 15)) {
            if (flag) {
               for(int i2 = 0; i2 < 5; ++i2) {
                  if (i2 <= p_64401_.nextInt(5)) {
                     p_64400_.m_6978_(blockpos$mutableblockpos.m_122178_(blockpos.m_123341_(), i1 - i2, blockpos.m_123343_()), Blocks.f_50752_.m_49966_(), false);
                  }
               }
            }

            if (flag1) {
               for(int j2 = 4; j2 >= 0; --j2) {
                  if (j2 <= p_64401_.nextInt(5)) {
                     p_64400_.m_6978_(blockpos$mutableblockpos.m_122178_(blockpos.m_123341_(), l + j2, blockpos.m_123343_()), Blocks.f_50752_.m_49966_(), false);
                  }
               }
            }
         }

      }
   }

   public CompletableFuture<ChunkAccess> m_142189_(Executor p_158463_, StructureFeatureManager p_158464_, ChunkAccess p_158465_) {
      NoiseSettings noisesettings = this.f_64318_.get().m_64481_();
      int i = Math.max(noisesettings.m_158703_(), p_158465_.m_141937_());
      int j = Math.min(noisesettings.m_158703_() + noisesettings.m_64534_(), p_158465_.m_151558_());
      int k = Mth.m_14042_(i, this.f_158374_);
      int l = Mth.m_14042_(j - i, this.f_158374_);
      if (l <= 0) {
         return CompletableFuture.completedFuture(p_158465_);
      } else {
         int i1 = p_158465_.m_151564_(l * this.f_158374_ - 1 + i);
         int j1 = p_158465_.m_151564_(i);
         return CompletableFuture.supplyAsync(() -> {
            Set<LevelChunkSection> set = Sets.newHashSet();

            ChunkAccess chunkaccess;
            try {
               for(int k1 = i1; k1 >= j1; --k1) {
                  LevelChunkSection levelchunksection = p_158465_.m_156115_(k1);
                  levelchunksection.m_62981_();
                  set.add(levelchunksection);
               }

               chunkaccess = this.m_158427_(p_158464_, p_158465_, k, l);
            } finally {
               for(LevelChunkSection levelchunksection1 : set) {
                  levelchunksection1.m_63006_();
               }

            }

            return chunkaccess;
         }, Util.m_137578_());
      }
   }

   protected ChunkAccess m_158427_(StructureFeatureManager p_158428_, ChunkAccess p_158429_, int p_158430_, int p_158431_) {
      Heightmap heightmap = p_158429_.m_6005_(Heightmap.Types.OCEAN_FLOOR_WG);
      Heightmap heightmap1 = p_158429_.m_6005_(Heightmap.Types.WORLD_SURFACE_WG);
      ChunkPos chunkpos = p_158429_.m_7697_();
      int i = chunkpos.m_45604_();
      int j = chunkpos.m_45605_();
      Beardifier beardifier = new Beardifier(p_158428_, p_158429_);
      Aquifer aquifer = this.m_158396_(p_158430_, p_158431_, chunkpos);
      NoiseInterpolator noiseinterpolator = new NoiseInterpolator(this.f_158376_, p_158431_, this.f_158378_, chunkpos, p_158430_, this::m_158466_);
      List<NoiseInterpolator> list = Lists.newArrayList(noiseinterpolator);
      Consumer<NoiseInterpolator> consumer = list::add;
      DoubleFunction<BaseStoneSource> doublefunction = this.m_158477_(p_158430_, chunkpos, consumer);
      DoubleFunction<NoiseModifier> doublefunction1 = this.m_158420_(p_158430_, chunkpos, consumer);
      list.forEach(NoiseInterpolator::m_158601_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k = 0; k < this.f_158376_; ++k) {
         int l = k;
         list.forEach((p_158426_) -> {
            p_158426_.m_158604_(l);
         });

         for(int i1 = 0; i1 < this.f_158378_; ++i1) {
            LevelChunkSection levelchunksection = p_158429_.m_156115_(p_158429_.m_151559_() - 1);

            for(int j1 = p_158431_ - 1; j1 >= 0; --j1) {
               int k1 = i1;
               int l1 = j1;
               list.forEach((p_158412_) -> {
                  p_158412_.m_158606_(l1, k1);
               });

               for(int i2 = this.f_158374_ - 1; i2 >= 0; --i2) {
                  int j2 = (p_158430_ + j1) * this.f_158374_ + i2;
                  int k2 = j2 & 15;
                  int l2 = p_158429_.m_151564_(j2);
                  if (p_158429_.m_151564_(levelchunksection.m_63017_()) != l2) {
                     levelchunksection = p_158429_.m_156115_(l2);
                  }

                  double d0 = (double)i2 / (double)this.f_158374_;
                  list.forEach((p_158476_) -> {
                     p_158476_.m_158602_(d0);
                  });

                  for(int i3 = 0; i3 < this.f_158375_; ++i3) {
                     int j3 = i + k * this.f_158375_ + i3;
                     int k3 = j3 & 15;
                     double d1 = (double)i3 / (double)this.f_158375_;
                     list.forEach((p_158390_) -> {
                        p_158390_.m_158613_(d1);
                     });

                     for(int l3 = 0; l3 < this.f_158375_; ++l3) {
                        int i4 = j + i1 * this.f_158375_ + l3;
                        int j4 = i4 & 15;
                        double d2 = (double)l3 / (double)this.f_158375_;
                        double d3 = noiseinterpolator.m_158618_(d2);
                        BlockState blockstate = this.m_158439_(beardifier, aquifer, doublefunction.apply(d2), doublefunction1.apply(d2), j3, j2, i4, d3);
                        if (blockstate != f_64321_) {
                           if (blockstate.m_60791_() != 0 && p_158429_ instanceof ProtoChunk) {
                              blockpos$mutableblockpos.m_122178_(j3, j2, i4);
                              ((ProtoChunk)p_158429_).m_63277_(blockpos$mutableblockpos);
                           }

                           levelchunksection.m_62991_(k3, k2, j4, blockstate, false);
                           heightmap.m_64249_(k3, j2, j4, blockstate);
                           heightmap1.m_64249_(k3, j2, j4, blockstate);
                           if (aquifer.m_142203_() && !blockstate.m_60819_().m_76178_()) {
                              blockpos$mutableblockpos.m_122178_(j3, j2, i4);
                              p_158429_.m_5783_().m_5945_(blockpos$mutableblockpos, blockstate.m_60819_().m_76152_(), 0);
                           }
                        }
                     }
                  }
               }
            }
         }

         list.forEach(NoiseInterpolator::m_158612_);
      }

      return p_158429_;
   }

   protected DoubleFunction<NoiseModifier> m_158420_(int p_158421_, ChunkPos p_158422_, Consumer<NoiseInterpolator> p_158423_) {
      if (!this.f_64318_.get().m_158571_()) {
         return (p_158473_) -> {
            return NoiseModifier.f_158626_;
         };
      } else {
         NoiseBasedChunkGenerator.NoodleCaveNoiseModifier noisebasedchunkgenerator$noodlecavenoisemodifier = new NoiseBasedChunkGenerator.NoodleCaveNoiseModifier(p_158422_, p_158421_);
         noisebasedchunkgenerator$noodlecavenoisemodifier.m_158505_(p_158423_);
         return noisebasedchunkgenerator$noodlecavenoisemodifier::m_158503_;
      }
   }

   protected DoubleFunction<BaseStoneSource> m_158477_(int p_158478_, ChunkPos p_158479_, Consumer<NoiseInterpolator> p_158480_) {
      if (!this.f_64318_.get().m_158570_()) {
         return (p_158387_) -> {
            return this.f_158383_;
         };
      } else {
         NoiseBasedChunkGenerator.OreVeinNoiseSource noisebasedchunkgenerator$oreveinnoisesource = new NoiseBasedChunkGenerator.OreVeinNoiseSource(p_158479_, p_158478_, this.f_64333_ + 1L);
         noisebasedchunkgenerator$oreveinnoisesource.m_158526_(p_158480_);
         BaseStoneSource basestonesource = (p_158450_, p_158451_, p_158452_) -> {
            BlockState blockstate = noisebasedchunkgenerator$oreveinnoisesource.m_142722_(p_158450_, p_158451_, p_158452_);
            return blockstate != this.f_64316_ ? blockstate : this.f_158383_.m_142722_(p_158450_, p_158451_, p_158452_);
         };
         return (p_158456_) -> {
            noisebasedchunkgenerator$oreveinnoisesource.m_158524_(p_158456_);
            return basestonesource;
         };
      }
   }

   protected Aquifer m_142439_(ChunkAccess p_158438_) {
      ChunkPos chunkpos = p_158438_.m_7697_();
      int i = Math.max(this.f_64318_.get().m_64481_().m_158703_(), p_158438_.m_141937_());
      int j = Mth.m_14042_(i, this.f_158374_);
      return this.m_158396_(j, this.f_158377_, chunkpos);
   }

   public int m_6331_() {
      return this.f_64334_;
   }

   public int m_6337_() {
      return this.f_64318_.get().m_64486_();
   }

   public int m_142062_() {
      return this.f_64318_.get().m_64481_().m_158703_();
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_142184_(Biome p_158433_, StructureFeatureManager p_158434_, MobCategory p_158435_, BlockPos p_158436_) {
      WeightedRandomList<MobSpawnSettings.SpawnerData> spawns = net.minecraftforge.common.world.StructureSpawnManager.getStructureSpawns(p_158434_, p_158435_, p_158436_);
      if (spawns != null) return spawns;
      if (false) {//Forge: We handle these hardcoded cases above in StructureSpawnManager#getStructureSpawns, but allow for insideOnly to be changed and allow for creatures to be spawned in ones other than just the witch hut
      if (p_158434_.m_47285_(p_158436_, true, StructureFeature.f_67021_).m_73603_()) {
         if (p_158435_ == MobCategory.MONSTER) {
            return StructureFeature.f_67021_.m_142494_();
         }

         if (p_158435_ == MobCategory.CREATURE) {
            return StructureFeature.f_67021_.m_142498_();
         }
      }

      if (p_158435_ == MobCategory.MONSTER) {
         if (p_158434_.m_47285_(p_158436_, false, StructureFeature.f_67013_).m_73603_()) {
            return StructureFeature.f_67013_.m_142494_();
         }

         if (p_158434_.m_47285_(p_158436_, false, StructureFeature.f_67023_).m_73603_()) {
            return StructureFeature.f_67023_.m_142494_();
         }

         if (p_158434_.m_47285_(p_158436_, true, StructureFeature.f_67025_).m_73603_()) {
            return StructureFeature.f_67025_.m_142494_();
         }
      }
      }

      return p_158435_ == MobCategory.UNDERGROUND_WATER_CREATURE && p_158434_.m_47285_(p_158436_, false, StructureFeature.f_67023_).m_73603_() ? StructureFeature.f_67023_.m_160477_() : super.m_142184_(p_158433_, p_158434_, p_158435_, p_158436_);
   }

   public void m_6929_(WorldGenRegion p_64379_) {
      if (!this.f_64318_.get().m_64487_()) {
         ChunkPos chunkpos = p_64379_.m_143488_();
         Biome biome = p_64379_.m_46857_(chunkpos.m_45615_());
         WorldgenRandom worldgenrandom = new WorldgenRandom();
         worldgenrandom.m_64690_(p_64379_.m_7328_(), chunkpos.m_45604_(), chunkpos.m_45605_());
         NaturalSpawner.m_151616_(p_64379_, biome, chunkpos, worldgenrandom);
      }
   }

   public class NoodleCaveNoiseModifier implements NoiseModifier {
      private final NoiseInterpolator f_158494_;
      private final NoiseInterpolator f_158495_;
      private final NoiseInterpolator f_158496_;
      private final NoiseInterpolator f_158497_;
      private double f_158498_;

      public NoodleCaveNoiseModifier(ChunkPos p_158501_, int p_158502_) {
         this.f_158494_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158501_, p_158502_, NoiseBasedChunkGenerator.this.f_158385_::m_158742_);
         this.f_158495_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158501_, p_158502_, NoiseBasedChunkGenerator.this.f_158385_::m_158765_);
         this.f_158496_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158501_, p_158502_, NoiseBasedChunkGenerator.this.f_158385_::m_158771_);
         this.f_158497_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158501_, p_158502_, NoiseBasedChunkGenerator.this.f_158385_::m_158777_);
      }

      public NoiseModifier m_158503_(double p_158504_) {
         this.f_158498_ = p_158504_;
         return this;
      }

      public double m_142124_(double p_158508_, int p_158509_, int p_158510_, int p_158511_) {
         double d0 = this.f_158494_.m_158618_(this.f_158498_);
         double d1 = this.f_158495_.m_158618_(this.f_158498_);
         double d2 = this.f_158496_.m_158618_(this.f_158498_);
         double d3 = this.f_158497_.m_158618_(this.f_158498_);
         return NoiseBasedChunkGenerator.this.f_158385_.m_158732_(p_158508_, p_158509_, p_158510_, p_158511_, d0, d1, d2, d3, NoiseBasedChunkGenerator.this.m_142062_());
      }

      public void m_158505_(Consumer<NoiseInterpolator> p_158506_) {
         p_158506_.accept(this.f_158494_);
         p_158506_.accept(this.f_158495_);
         p_158506_.accept(this.f_158496_);
         p_158506_.accept(this.f_158497_);
      }
   }

   public class OreVeinNoiseSource implements BaseStoneSource {
      private final NoiseInterpolator f_158513_;
      private final NoiseInterpolator f_158514_;
      private final NoiseInterpolator f_158515_;
      private double f_158516_;
      private final long f_158517_;
      private final WorldgenRandom f_158518_ = new WorldgenRandom();

      public OreVeinNoiseSource(ChunkPos p_158521_, int p_158522_, long p_158523_) {
         this.f_158513_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158521_, p_158522_, NoiseBasedChunkGenerator.this.f_158384_::m_158827_);
         this.f_158514_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158521_, p_158522_, NoiseBasedChunkGenerator.this.f_158384_::m_158843_);
         this.f_158515_ = new NoiseInterpolator(NoiseBasedChunkGenerator.this.f_158376_, NoiseBasedChunkGenerator.this.f_158377_, NoiseBasedChunkGenerator.this.f_158378_, p_158521_, p_158522_, NoiseBasedChunkGenerator.this.f_158384_::m_158849_);
         this.f_158517_ = p_158523_;
      }

      public void m_158526_(Consumer<NoiseInterpolator> p_158527_) {
         p_158527_.accept(this.f_158513_);
         p_158527_.accept(this.f_158514_);
         p_158527_.accept(this.f_158515_);
      }

      public void m_158524_(double p_158525_) {
         this.f_158516_ = p_158525_;
      }

      public BlockState m_142722_(int p_158529_, int p_158530_, int p_158531_) {
         double d0 = this.f_158513_.m_158618_(this.f_158516_);
         double d1 = this.f_158514_.m_158618_(this.f_158516_);
         double d2 = this.f_158515_.m_158618_(this.f_158516_);
         this.f_158518_.m_158961_(this.f_158517_, p_158529_, p_158530_, p_158531_);
         return NoiseBasedChunkGenerator.this.f_158384_.m_158819_(this.f_158518_, p_158529_, p_158530_, p_158531_, d0, d1, d2);
      }
   }
}
