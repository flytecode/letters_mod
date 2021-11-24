package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class StraightTrunkPlacer extends TrunkPlacer {
   public static final Codec<StraightTrunkPlacer> f_70245_ = RecordCodecBuilder.create((p_70261_) -> {
      return m_70305_(p_70261_).apply(p_70261_, StraightTrunkPlacer::new);
   });

   public StraightTrunkPlacer(int p_70248_, int p_70249_, int p_70250_) {
      super(p_70248_, p_70249_, p_70250_);
   }

   protected TrunkPlacerType<?> m_7362_() {
      return TrunkPlacerType.f_70315_;
   }

   public List<FoliagePlacer.FoliageAttachment> m_142625_(LevelSimulatedReader p_161859_, BiConsumer<BlockPos, BlockState> p_161860_, Random p_161861_, int p_161862_, BlockPos p_161863_, TreeConfiguration p_161864_) {
      m_161880_(p_161859_, p_161860_, p_161861_, p_161863_.m_7495_(), p_161864_);

      for(int i = 0; i < p_161862_; ++i) {
         m_161893_(p_161859_, p_161860_, p_161861_, p_161863_.m_6630_(i), p_161864_);
      }

      return ImmutableList.of(new FoliagePlacer.FoliageAttachment(p_161863_.m_6630_(p_161862_), 0, false));
   }
}