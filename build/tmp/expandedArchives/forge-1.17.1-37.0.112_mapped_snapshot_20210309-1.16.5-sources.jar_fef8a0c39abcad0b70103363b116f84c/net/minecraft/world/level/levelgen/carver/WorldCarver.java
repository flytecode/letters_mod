package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.SingleBaseStoneSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class WorldCarver<C extends CarverConfiguration> extends net.minecraftforge.registries.ForgeRegistryEntry<WorldCarver<?>> {
   public static final WorldCarver<CaveCarverConfiguration> f_64974_ = m_65065_("cave", new CaveWorldCarver(CaveCarverConfiguration.f_159154_));
   public static final WorldCarver<CaveCarverConfiguration> f_64975_ = m_65065_("nether_cave", new NetherWorldCarver(CaveCarverConfiguration.f_159154_));
   public static final WorldCarver<CanyonCarverConfiguration> f_64976_ = m_65065_("canyon", new CanyonWorldCarver(CanyonCarverConfiguration.f_158966_));
   public static final WorldCarver<CanyonCarverConfiguration> f_64977_ = m_65065_("underwater_canyon", new UnderwaterCanyonWorldCarver(CanyonCarverConfiguration.f_158966_));
   public static final WorldCarver<CaveCarverConfiguration> f_64978_ = m_65065_("underwater_cave", new UnderwaterCaveWorldCarver(CaveCarverConfiguration.f_159154_));
   protected static final BaseStoneSource f_159364_ = new SingleBaseStoneSource(Blocks.f_50069_.m_49966_());
   protected static final BlockState f_64979_ = Blocks.f_50016_.m_49966_();
   protected static final BlockState f_64980_ = Blocks.f_50627_.m_49966_();
   protected static final FluidState f_64981_ = Fluids.f_76193_.m_76145_();
   protected static final FluidState f_64982_ = Fluids.f_76195_.m_76145_();
   protected Set<Block> f_64983_ = ImmutableSet.of(Blocks.f_50069_, Blocks.f_50122_, Blocks.f_50228_, Blocks.f_50334_, Blocks.f_50493_, Blocks.f_50546_, Blocks.f_50599_, Blocks.f_50440_, Blocks.f_50352_, Blocks.f_50287_, Blocks.f_50288_, Blocks.f_50289_, Blocks.f_50290_, Blocks.f_50291_, Blocks.f_50292_, Blocks.f_50293_, Blocks.f_50294_, Blocks.f_50295_, Blocks.f_50296_, Blocks.f_50297_, Blocks.f_50298_, Blocks.f_50299_, Blocks.f_50300_, Blocks.f_50301_, Blocks.f_50302_, Blocks.f_50062_, Blocks.f_50394_, Blocks.f_50195_, Blocks.f_50125_, Blocks.f_50354_, Blocks.f_152550_, Blocks.f_152496_, Blocks.f_50122_, Blocks.f_49996_, Blocks.f_152468_, Blocks.f_152598_, Blocks.f_152505_, Blocks.f_152506_, Blocks.f_152599_);
   protected Set<Fluid> f_64984_ = ImmutableSet.of(Fluids.f_76193_);
   private final Codec<ConfiguredWorldCarver<C>> f_64986_;

   private static <C extends CarverConfiguration, F extends WorldCarver<C>> F m_65065_(String p_65066_, F p_65067_) {
      return Registry.m_122961_(Registry.f_122837_, p_65066_, p_65067_);
   }

   public WorldCarver(Codec<C> p_159366_) {
      this.f_64986_ = p_159366_.fieldOf("config").xmap(this::m_65063_, ConfiguredWorldCarver::m_64855_).codec();
   }

   public ConfiguredWorldCarver<C> m_65063_(C p_65064_) {
      return new ConfiguredWorldCarver<>(this, p_65064_);
   }

   public Codec<ConfiguredWorldCarver<C>> m_65072_() {
      return this.f_64986_;
   }

   public int m_65073_() {
      return 4;
   }

   protected boolean m_159386_(CarvingContext p_159387_, C p_159388_, ChunkAccess p_159389_, Function<BlockPos, Biome> p_159390_, long p_159391_, Aquifer p_159392_, double p_159393_, double p_159394_, double p_159395_, double p_159396_, double p_159397_, BitSet p_159398_, WorldCarver.CarveSkipChecker p_159399_) {
      ChunkPos chunkpos = p_159389_.m_7697_();
      int i = chunkpos.f_45578_;
      int j = chunkpos.f_45579_;
      Random random = new Random(p_159391_ + (long)i + (long)j);
      double d0 = (double)chunkpos.m_151390_();
      double d1 = (double)chunkpos.m_151393_();
      double d2 = 16.0D + p_159396_ * 2.0D;
      if (!(Math.abs(p_159393_ - d0) > d2) && !(Math.abs(p_159395_ - d1) > d2)) {
         int k = chunkpos.m_45604_();
         int l = chunkpos.m_45605_();
         int i1 = Math.max(Mth.m_14107_(p_159393_ - p_159396_) - k - 1, 0);
         int j1 = Math.min(Mth.m_14107_(p_159393_ + p_159396_) - k, 15);
         int k1 = Math.max(Mth.m_14107_(p_159394_ - p_159397_) - 1, p_159387_.m_142201_() + 1);
         int l1 = Math.min(Mth.m_14107_(p_159394_ + p_159397_) + 1, p_159387_.m_142201_() + p_159387_.m_142208_() - 8);
         int i2 = Math.max(Mth.m_14107_(p_159395_ - p_159396_) - l - 1, 0);
         int j2 = Math.min(Mth.m_14107_(p_159395_ + p_159396_) - l, 15);
         if (!p_159388_.f_159091_ && this.m_141931_(p_159389_, i1, j1, k1, l1, i2, j2)) {
            return false;
         } else {
            boolean flag = false;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            for(int k2 = i1; k2 <= j1; ++k2) {
               int l2 = chunkpos.m_151382_(k2);
               double d3 = ((double)l2 + 0.5D - p_159393_) / p_159396_;

               for(int i3 = i2; i3 <= j2; ++i3) {
                  int j3 = chunkpos.m_151391_(i3);
                  double d4 = ((double)j3 + 0.5D - p_159395_) / p_159396_;
                  if (!(d3 * d3 + d4 * d4 >= 1.0D)) {
                     MutableBoolean mutableboolean = new MutableBoolean(false);

                     for(int k3 = l1; k3 > k1; --k3) {
                        double d5 = ((double)k3 - 0.5D - p_159394_) / p_159397_;
                        if (!p_159399_.m_159425_(p_159387_, d3, d5, d4, k3)) {
                           int l3 = k3 - p_159387_.m_142201_();
                           int i4 = k2 | i3 << 4 | l3 << 8;
                           if (!p_159398_.get(i4) || m_159423_(p_159388_)) {
                              p_159398_.set(i4);
                              blockpos$mutableblockpos.m_122178_(l2, k3, j3);
                              flag |= this.m_141949_(p_159387_, p_159388_, p_159389_, p_159390_, p_159398_, random, blockpos$mutableblockpos, blockpos$mutableblockpos1, p_159392_, mutableboolean);
                           }
                        }
                     }
                  }
               }
            }

            return flag;
         }
      } else {
         return false;
      }
   }

   protected boolean m_141949_(CarvingContext p_159400_, C p_159401_, ChunkAccess p_159402_, Function<BlockPos, Biome> p_159403_, BitSet p_159404_, Random p_159405_, BlockPos.MutableBlockPos p_159406_, BlockPos.MutableBlockPos p_159407_, Aquifer p_159408_, MutableBoolean p_159409_) {
      BlockState blockstate = p_159402_.m_8055_(p_159406_);
      BlockState blockstate1 = p_159402_.m_8055_(p_159407_.m_122159_(p_159406_, Direction.UP));
      if (blockstate.m_60713_(Blocks.f_50440_) || blockstate.m_60713_(Blocks.f_50195_)) {
         p_159409_.setTrue();
      }

      if (!this.m_65012_(blockstate, blockstate1) && !m_159423_(p_159401_)) {
         return false;
      } else {
         BlockState blockstate2 = this.m_159418_(p_159400_, p_159401_, p_159406_, p_159408_);
         if (blockstate2 == null) {
            return false;
         } else {
            p_159402_.m_6978_(p_159406_, blockstate2, false);
            if (p_159409_.isTrue()) {
               p_159407_.m_122159_(p_159406_, Direction.DOWN);
               if (p_159402_.m_8055_(p_159407_).m_60713_(Blocks.f_50493_)) {
                  p_159402_.m_6978_(p_159407_, p_159403_.apply(p_159406_).m_47536_().m_47824_().m_6743_(), false);
               }
            }

            return true;
         }
      }
   }

   @Nullable
   private BlockState m_159418_(CarvingContext p_159419_, C p_159420_, BlockPos p_159421_, Aquifer p_159422_) {
      if (p_159421_.m_123342_() <= p_159420_.f_159090_.m_142322_(p_159419_)) {
         return f_64982_.m_76188_();
      } else if (!p_159420_.f_159091_) {
         return m_159423_(p_159420_) ? m_159381_(p_159420_, f_64979_) : f_64979_;
      } else {
         BlockState blockstate = p_159422_.m_142419_(f_159364_, p_159421_.m_123341_(), p_159421_.m_123342_(), p_159421_.m_123343_(), 0.0D);
         if (blockstate == Blocks.f_50069_.m_49966_()) {
            return m_159423_(p_159420_) ? p_159420_.f_159092_.m_159148_() : null;
         } else {
            return m_159423_(p_159420_) ? m_159381_(p_159420_, blockstate) : blockstate;
         }
      }
   }

   private static BlockState m_159381_(CarverConfiguration p_159382_, BlockState p_159383_) {
      if (p_159383_.m_60713_(Blocks.f_50016_)) {
         return p_159382_.f_159092_.m_159145_();
      } else if (p_159383_.m_60713_(Blocks.f_49990_)) {
         BlockState blockstate = p_159382_.f_159092_.m_159146_();
         return blockstate.m_61138_(BlockStateProperties.f_61362_) ? blockstate.m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(true)) : blockstate;
      } else {
         return p_159383_.m_60713_(Blocks.f_49991_) ? p_159382_.f_159092_.m_159147_() : p_159383_;
      }
   }

   public abstract boolean m_142404_(CarvingContext p_159410_, C p_159411_, ChunkAccess p_159412_, Function<BlockPos, Biome> p_159413_, Random p_159414_, Aquifer p_159415_, ChunkPos p_159416_, BitSet p_159417_);

   public abstract boolean m_142512_(C p_159384_, Random p_159385_);

   protected boolean m_65010_(BlockState p_65011_) {
      return this.f_64983_.contains(p_65011_.m_60734_());
   }

   protected boolean m_65012_(BlockState p_65013_, BlockState p_65014_) {
      return this.m_65010_(p_65013_) || (p_65013_.m_60713_(Blocks.f_49992_) || p_65013_.m_60713_(Blocks.f_49994_)) && !p_65014_.m_60819_().m_76153_(FluidTags.f_13131_);
   }

   protected boolean m_141931_(ChunkAccess p_159374_, int p_159375_, int p_159376_, int p_159377_, int p_159378_, int p_159379_, int p_159380_) {
      ChunkPos chunkpos = p_159374_.m_7697_();
      int i = chunkpos.m_45604_();
      int j = chunkpos.m_45605_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k = p_159375_; k <= p_159376_; ++k) {
         for(int l = p_159379_; l <= p_159380_; ++l) {
            for(int i1 = p_159377_ - 1; i1 <= p_159378_ + 1; ++i1) {
               blockpos$mutableblockpos.m_122178_(i + k, i1, j + l);
               if (this.f_64984_.contains(p_159374_.m_6425_(blockpos$mutableblockpos).m_76152_())) {
                  return true;
               }

               if (i1 != p_159378_ + 1 && !m_65003_(k, l, p_159375_, p_159376_, p_159379_, p_159380_)) {
                  i1 = p_159378_;
               }
            }
         }
      }

      return false;
   }

   private static boolean m_65003_(int p_65004_, int p_65005_, int p_65006_, int p_65007_, int p_65008_, int p_65009_) {
      return p_65004_ == p_65006_ || p_65004_ == p_65007_ || p_65005_ == p_65008_ || p_65005_ == p_65009_;
   }

   protected static boolean m_159367_(ChunkPos p_159368_, double p_159369_, double p_159370_, int p_159371_, int p_159372_, float p_159373_) {
      double d0 = (double)p_159368_.m_151390_();
      double d1 = (double)p_159368_.m_151393_();
      double d2 = p_159369_ - d0;
      double d3 = p_159370_ - d1;
      double d4 = (double)(p_159372_ - p_159371_);
      double d5 = (double)(p_159373_ + 2.0F + 16.0F);
      return d2 * d2 + d3 * d3 - d4 * d4 <= d5 * d5;
   }

   private static boolean m_159423_(CarverConfiguration p_159424_) {
      return p_159424_.f_159092_.m_159128_();
   }

   public interface CarveSkipChecker {
      boolean m_159425_(CarvingContext p_159426_, double p_159427_, double p_159428_, double p_159429_, int p_159430_);
   }
}
