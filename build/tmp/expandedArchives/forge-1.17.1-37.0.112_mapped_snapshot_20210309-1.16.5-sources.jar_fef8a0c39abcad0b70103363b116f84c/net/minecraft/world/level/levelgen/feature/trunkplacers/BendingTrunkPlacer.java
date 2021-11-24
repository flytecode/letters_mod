package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class BendingTrunkPlacer extends TrunkPlacer {
   public static final Codec<BendingTrunkPlacer> f_161765_ = RecordCodecBuilder.create((p_161786_) -> {
      return m_70305_(p_161786_).and(p_161786_.group(ExtraCodecs.f_144629_.optionalFieldOf("min_height_for_leaves", 1).forGetter((p_161788_) -> {
         return p_161788_.f_161766_;
      }), IntProvider.m_146545_(1, 64).fieldOf("bend_length").forGetter((p_161784_) -> {
         return p_161784_.f_161767_;
      }))).apply(p_161786_, BendingTrunkPlacer::new);
   });
   private final int f_161766_;
   private final IntProvider f_161767_;

   public BendingTrunkPlacer(int p_161770_, int p_161771_, int p_161772_, int p_161773_, IntProvider p_161774_) {
      super(p_161770_, p_161771_, p_161772_);
      this.f_161766_ = p_161773_;
      this.f_161767_ = p_161774_;
   }

   protected TrunkPlacerType<?> m_7362_() {
      return TrunkPlacerType.f_161899_;
   }

   public List<FoliagePlacer.FoliageAttachment> m_142625_(LevelSimulatedReader p_161777_, BiConsumer<BlockPos, BlockState> p_161778_, Random p_161779_, int p_161780_, BlockPos p_161781_, TreeConfiguration p_161782_) {
      Direction direction = Direction.Plane.HORIZONTAL.m_122560_(p_161779_);
      int i = p_161780_ - 1;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_161781_.m_122032_();
      BlockPos blockpos = blockpos$mutableblockpos.m_7495_();
      m_161880_(p_161777_, p_161778_, p_161779_, blockpos, p_161782_);
      List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();

      for(int j = 0; j <= i; ++j) {
         if (j + 1 >= i + p_161779_.nextInt(2)) {
            blockpos$mutableblockpos.m_122173_(direction);
         }

         if (TreeFeature.m_67272_(p_161777_, blockpos$mutableblockpos)) {
            m_161893_(p_161777_, p_161778_, p_161779_, blockpos$mutableblockpos, p_161782_);
         }

         if (j >= this.f_161766_) {
            list.add(new FoliagePlacer.FoliageAttachment(blockpos$mutableblockpos.m_7949_(), 0, false));
         }

         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

      int l = this.f_161767_.m_142270_(p_161779_);

      for(int k = 0; k <= l; ++k) {
         if (TreeFeature.m_67272_(p_161777_, blockpos$mutableblockpos)) {
            m_161893_(p_161777_, p_161778_, p_161779_, blockpos$mutableblockpos, p_161782_);
         }

         list.add(new FoliagePlacer.FoliageAttachment(blockpos$mutableblockpos.m_7949_(), 0, false));
         blockpos$mutableblockpos.m_122173_(direction);
      }

      return list;
   }
}