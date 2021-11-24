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

public class GiantTrunkPlacer extends TrunkPlacer {
   public static final Codec<GiantTrunkPlacer> f_70162_ = RecordCodecBuilder.create((p_70189_) -> {
      return m_70305_(p_70189_).apply(p_70189_, GiantTrunkPlacer::new);
   });

   public GiantTrunkPlacer(int p_70165_, int p_70166_, int p_70167_) {
      super(p_70165_, p_70166_, p_70167_);
   }

   protected TrunkPlacerType<?> m_7362_() {
      return TrunkPlacerType.f_70317_;
   }

   public List<FoliagePlacer.FoliageAttachment> m_142625_(LevelSimulatedReader p_161835_, BiConsumer<BlockPos, BlockState> p_161836_, Random p_161837_, int p_161838_, BlockPos p_161839_, TreeConfiguration p_161840_) {
      BlockPos blockpos = p_161839_.m_7495_();
      m_161880_(p_161835_, p_161836_, p_161837_, blockpos, p_161840_);
      m_161880_(p_161835_, p_161836_, p_161837_, blockpos.m_142126_(), p_161840_);
      m_161880_(p_161835_, p_161836_, p_161837_, blockpos.m_142128_(), p_161840_);
      m_161880_(p_161835_, p_161836_, p_161837_, blockpos.m_142128_().m_142126_(), p_161840_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < p_161838_; ++i) {
         m_161841_(p_161835_, p_161836_, p_161837_, blockpos$mutableblockpos, p_161840_, p_161839_, 0, i, 0);
         if (i < p_161838_ - 1) {
            m_161841_(p_161835_, p_161836_, p_161837_, blockpos$mutableblockpos, p_161840_, p_161839_, 1, i, 0);
            m_161841_(p_161835_, p_161836_, p_161837_, blockpos$mutableblockpos, p_161840_, p_161839_, 1, i, 1);
            m_161841_(p_161835_, p_161836_, p_161837_, blockpos$mutableblockpos, p_161840_, p_161839_, 0, i, 1);
         }
      }

      return ImmutableList.of(new FoliagePlacer.FoliageAttachment(p_161839_.m_6630_(p_161838_), 0, true));
   }

   private static void m_161841_(LevelSimulatedReader p_161842_, BiConsumer<BlockPos, BlockState> p_161843_, Random p_161844_, BlockPos.MutableBlockPos p_161845_, TreeConfiguration p_161846_, BlockPos p_161847_, int p_161848_, int p_161849_, int p_161850_) {
      p_161845_.m_122154_(p_161847_, p_161848_, p_161849_, p_161850_);
      m_161874_(p_161842_, p_161843_, p_161844_, p_161845_, p_161846_);
   }
}