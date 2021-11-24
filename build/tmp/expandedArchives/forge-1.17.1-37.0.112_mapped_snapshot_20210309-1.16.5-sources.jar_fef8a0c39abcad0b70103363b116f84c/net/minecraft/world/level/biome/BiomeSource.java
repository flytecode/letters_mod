package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public abstract class BiomeSource implements BiomeManager.NoiseBiomeSource {
   public static final Codec<BiomeSource> f_47888_ = Registry.f_122889_.dispatchStable(BiomeSource::m_5820_, Function.identity());
   protected final Map<StructureFeature<?>, Boolean> f_47889_ = Maps.newHashMap();
   protected final Set<BlockState> f_47890_ = Sets.newHashSet();
   protected final List<Biome> f_47891_;

   protected BiomeSource(Stream<Supplier<Biome>> p_47896_) {
      this(p_47896_.map(Supplier::get).collect(ImmutableList.toImmutableList()));
   }

   protected BiomeSource(List<Biome> p_47894_) {
      this.f_47891_ = p_47894_;
   }

   protected abstract Codec<? extends BiomeSource> m_5820_();

   public abstract BiomeSource m_7206_(long p_47916_);

   public List<Biome> m_47922_() {
      return this.f_47891_;
   }

   public Set<Biome> m_7901_(int p_47897_, int p_47898_, int p_47899_, int p_47900_) {
      int i = QuartPos.m_175400_(p_47897_ - p_47900_);
      int j = QuartPos.m_175400_(p_47898_ - p_47900_);
      int k = QuartPos.m_175400_(p_47899_ - p_47900_);
      int l = QuartPos.m_175400_(p_47897_ + p_47900_);
      int i1 = QuartPos.m_175400_(p_47898_ + p_47900_);
      int j1 = QuartPos.m_175400_(p_47899_ + p_47900_);
      int k1 = l - i + 1;
      int l1 = i1 - j + 1;
      int i2 = j1 - k + 1;
      Set<Biome> set = Sets.newHashSet();

      for(int j2 = 0; j2 < i2; ++j2) {
         for(int k2 = 0; k2 < k1; ++k2) {
            for(int l2 = 0; l2 < l1; ++l2) {
               int i3 = i + k2;
               int j3 = j + l2;
               int k3 = k + j2;
               set.add(this.m_7158_(i3, j3, k3));
            }
         }
      }

      return set;
   }

   @Nullable
   public BlockPos m_47909_(int p_47910_, int p_47911_, int p_47912_, int p_47913_, Predicate<Biome> p_47914_, Random p_47915_) {
      return this.m_7754_(p_47910_, p_47911_, p_47912_, p_47913_, 1, p_47914_, p_47915_, false);
   }

   @Nullable
   public BlockPos m_7754_(int p_47901_, int p_47902_, int p_47903_, int p_47904_, int p_47905_, Predicate<Biome> p_47906_, Random p_47907_, boolean p_47908_) {
      int i = QuartPos.m_175400_(p_47901_);
      int j = QuartPos.m_175400_(p_47903_);
      int k = QuartPos.m_175400_(p_47904_);
      int l = QuartPos.m_175400_(p_47902_);
      BlockPos blockpos = null;
      int i1 = 0;
      int j1 = p_47908_ ? 0 : k;

      for(int k1 = j1; k1 <= k; k1 += p_47905_) {
         for(int l1 = -k1; l1 <= k1; l1 += p_47905_) {
            boolean flag = Math.abs(l1) == k1;

            for(int i2 = -k1; i2 <= k1; i2 += p_47905_) {
               if (p_47908_) {
                  boolean flag1 = Math.abs(i2) == k1;
                  if (!flag1 && !flag) {
                     continue;
                  }
               }

               int k2 = i + i2;
               int j2 = j + l1;
               if (p_47906_.test(this.m_7158_(k2, l, j2))) {
                  if (blockpos == null || p_47907_.nextInt(i1 + 1) == 0) {
                     blockpos = new BlockPos(QuartPos.m_175402_(k2), p_47902_, QuartPos.m_175402_(j2));
                     if (p_47908_) {
                        return blockpos;
                     }
                  }

                  ++i1;
               }
            }
         }
      }

      return blockpos;
   }

   public boolean m_47917_(StructureFeature<?> p_47918_) {
      return this.f_47889_.computeIfAbsent(p_47918_, (p_47924_) -> {
         return this.f_47891_.stream().anyMatch((p_151758_) -> {
            return p_151758_.m_47536_().m_47808_(p_47924_);
         });
      });
   }

   public Set<BlockState> m_47925_() {
      if (this.f_47890_.isEmpty()) {
         for(Biome biome : this.f_47891_) {
            this.f_47890_.add(biome.m_47536_().m_47824_().m_6743_());
         }
      }

      return this.f_47890_;
   }

   static {
      Registry.m_122961_(Registry.f_122889_, "fixed", FixedBiomeSource.f_48251_);
      Registry.m_122961_(Registry.f_122889_, "multi_noise", MultiNoiseBiomeSource.f_48425_);
      Registry.m_122961_(Registry.f_122889_, "checkerboard", CheckerboardColumnBiomeSource.f_48230_);
      Registry.m_122961_(Registry.f_122889_, "vanilla_layered", OverworldBiomeSource.f_48581_);
      Registry.m_122961_(Registry.f_122889_, "the_end", TheEndBiomeSource.f_48617_);
   }
}