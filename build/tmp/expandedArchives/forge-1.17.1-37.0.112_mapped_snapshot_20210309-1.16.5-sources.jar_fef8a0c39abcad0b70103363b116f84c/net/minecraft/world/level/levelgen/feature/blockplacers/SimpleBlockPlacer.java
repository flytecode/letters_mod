package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBlockPlacer extends BlockPlacer {
   public static final Codec<SimpleBlockPlacer> f_67528_;
   public static final SimpleBlockPlacer f_67529_ = new SimpleBlockPlacer();

   protected BlockPlacerType<?> m_7070_() {
      return BlockPlacerType.f_67487_;
   }

   public void m_7275_(LevelAccessor p_67534_, BlockPos p_67535_, BlockState p_67536_, Random p_67537_) {
      p_67534_.m_7731_(p_67535_, p_67536_, 2);
   }

   static {
      f_67528_ = Codec.unit(() -> {
         return f_67529_;
      });
   }
}