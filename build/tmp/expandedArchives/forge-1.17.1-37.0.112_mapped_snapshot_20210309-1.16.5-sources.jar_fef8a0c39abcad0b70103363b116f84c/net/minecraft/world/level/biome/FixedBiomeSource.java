package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;

public class FixedBiomeSource extends BiomeSource {
   public static final Codec<FixedBiomeSource> f_48251_ = Biome.f_47431_.fieldOf("biome").xmap(FixedBiomeSource::new, (p_48278_) -> {
      return p_48278_.f_48252_;
   }).stable().codec();
   private final Supplier<Biome> f_48252_;

   public FixedBiomeSource(Biome p_48255_) {
      this(() -> {
         return p_48255_;
      });
   }

   public FixedBiomeSource(Supplier<Biome> p_48257_) {
      super(ImmutableList.of(p_48257_.get()));
      this.f_48252_ = p_48257_;
   }

   protected Codec<? extends BiomeSource> m_5820_() {
      return f_48251_;
   }

   public BiomeSource m_7206_(long p_48274_) {
      return this;
   }

   public Biome m_7158_(int p_48280_, int p_48281_, int p_48282_) {
      return this.f_48252_.get();
   }

   @Nullable
   public BlockPos m_7754_(int p_48265_, int p_48266_, int p_48267_, int p_48268_, int p_48269_, Predicate<Biome> p_48270_, Random p_48271_, boolean p_48272_) {
      if (p_48270_.test(this.f_48252_.get())) {
         return p_48272_ ? new BlockPos(p_48265_, p_48266_, p_48267_) : new BlockPos(p_48265_ - p_48268_ + p_48271_.nextInt(p_48268_ * 2 + 1), p_48266_, p_48267_ - p_48268_ + p_48271_.nextInt(p_48268_ * 2 + 1));
      } else {
         return null;
      }
   }

   public Set<Biome> m_7901_(int p_48260_, int p_48261_, int p_48262_, int p_48263_) {
      return Sets.newHashSet(this.f_48252_.get());
   }
}