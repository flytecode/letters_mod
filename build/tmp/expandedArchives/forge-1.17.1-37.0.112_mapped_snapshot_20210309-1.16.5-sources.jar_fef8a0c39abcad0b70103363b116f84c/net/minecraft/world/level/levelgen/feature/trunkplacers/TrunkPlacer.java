package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public abstract class TrunkPlacer {
   public static final Codec<TrunkPlacer> f_70262_ = Registry.f_122859_.dispatch(TrunkPlacer::m_7362_, TrunkPlacerType::m_70325_);
   private static final int f_161865_ = 32;
   private static final int f_161866_ = 24;
   public static final int f_161867_ = 80;
   protected final int f_70263_;
   protected final int f_70264_;
   protected final int f_70265_;

   protected static <P extends TrunkPlacer> P3<Mu<P>, Integer, Integer, Integer> m_70305_(Instance<P> p_70306_) {
      return p_70306_.group(Codec.intRange(0, 32).fieldOf("base_height").forGetter((p_70314_) -> {
         return p_70314_.f_70263_;
      }), Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter((p_70312_) -> {
         return p_70312_.f_70264_;
      }), Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter((p_70308_) -> {
         return p_70308_.f_70265_;
      }));
   }

   public TrunkPlacer(int p_70268_, int p_70269_, int p_70270_) {
      this.f_70263_ = p_70268_;
      this.f_70264_ = p_70269_;
      this.f_70265_ = p_70270_;
   }

   protected abstract TrunkPlacerType<?> m_7362_();

   public abstract List<FoliagePlacer.FoliageAttachment> m_142625_(LevelSimulatedReader p_161868_, BiConsumer<BlockPos, BlockState> p_161869_, Random p_161870_, int p_161871_, BlockPos p_161872_, TreeConfiguration p_161873_);

   public int m_70309_(Random p_70310_) {
      return this.f_70263_ + p_70310_.nextInt(this.f_70264_ + 1) + p_70310_.nextInt(this.f_70265_ + 1);
   }

   private static boolean m_70295_(LevelSimulatedReader p_70296_, BlockPos p_70297_) {
      return p_70296_.m_7433_(p_70297_, (p_70304_) -> {
         return Feature.m_159759_(p_70304_) && !p_70304_.m_60713_(Blocks.f_50440_) && !p_70304_.m_60713_(Blocks.f_50195_);
      });
   }

   protected static void m_161880_(LevelSimulatedReader p_161881_, BiConsumer<BlockPos, BlockState> p_161882_, Random p_161883_, BlockPos p_161884_, TreeConfiguration p_161885_) {
      if (p_161885_.f_161215_ || !m_70295_(p_161881_, p_161884_)) {
         p_161882_.accept(p_161884_, p_161885_.f_161212_.m_7112_(p_161883_, p_161884_));
      }

   }

   protected static boolean m_161893_(LevelSimulatedReader p_161894_, BiConsumer<BlockPos, BlockState> p_161895_, Random p_161896_, BlockPos p_161897_, TreeConfiguration p_161898_) {
      return m_161886_(p_161894_, p_161895_, p_161896_, p_161897_, p_161898_, Function.identity());
   }

   protected static boolean m_161886_(LevelSimulatedReader p_161887_, BiConsumer<BlockPos, BlockState> p_161888_, Random p_161889_, BlockPos p_161890_, TreeConfiguration p_161891_, Function<BlockState, BlockState> p_161892_) {
      if (TreeFeature.m_67272_(p_161887_, p_161890_)) {
         p_161888_.accept(p_161890_, p_161892_.apply(p_161891_.f_68185_.m_7112_(p_161889_, p_161890_)));
         return true;
      } else {
         return false;
      }
   }

   protected static void m_161874_(LevelSimulatedReader p_161875_, BiConsumer<BlockPos, BlockState> p_161876_, Random p_161877_, BlockPos.MutableBlockPos p_161878_, TreeConfiguration p_161879_) {
      if (TreeFeature.m_67262_(p_161875_, p_161878_)) {
         m_161893_(p_161875_, p_161876_, p_161877_, p_161878_, p_161879_);
      }

   }
}