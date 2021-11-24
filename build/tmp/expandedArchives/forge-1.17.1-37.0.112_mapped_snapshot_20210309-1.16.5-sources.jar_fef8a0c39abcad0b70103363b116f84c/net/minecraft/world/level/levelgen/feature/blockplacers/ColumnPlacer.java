package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class ColumnPlacer extends BlockPlacer {
   public static final Codec<ColumnPlacer> f_67498_ = RecordCodecBuilder.create((p_160711_) -> {
      return p_160711_.group(IntProvider.f_146532_.fieldOf("size").forGetter((p_160713_) -> {
         return p_160713_.f_160707_;
      })).apply(p_160711_, ColumnPlacer::new);
   });
   private final IntProvider f_160707_;

   public ColumnPlacer(IntProvider p_160709_) {
      this.f_160707_ = p_160709_;
   }

   protected BlockPlacerType<?> m_7070_() {
      return BlockPlacerType.f_67489_;
   }

   public void m_7275_(LevelAccessor p_67507_, BlockPos p_67508_, BlockState p_67509_, Random p_67510_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_67508_.m_122032_();
      int i = this.f_160707_.m_142270_(p_67510_);

      for(int j = 0; j < i; ++j) {
         p_67507_.m_7731_(blockpos$mutableblockpos, p_67509_, 2);
         blockpos$mutableblockpos.m_122173_(Direction.UP);
      }

   }
}