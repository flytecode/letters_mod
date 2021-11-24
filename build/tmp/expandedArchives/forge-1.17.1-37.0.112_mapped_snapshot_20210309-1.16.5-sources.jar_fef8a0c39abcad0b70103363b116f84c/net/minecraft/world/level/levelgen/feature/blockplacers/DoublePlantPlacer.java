package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DoublePlantPlacer extends BlockPlacer {
   public static final Codec<DoublePlantPlacer> f_67517_;
   public static final DoublePlantPlacer f_67518_ = new DoublePlantPlacer();

   protected BlockPlacerType<?> m_7070_() {
      return BlockPlacerType.f_67488_;
   }

   public void m_7275_(LevelAccessor p_67523_, BlockPos p_67524_, BlockState p_67525_, Random p_67526_) {
      DoublePlantBlock.m_153173_(p_67523_, p_67525_, p_67524_, 2);
   }

   static {
      f_67517_ = Codec.unit(() -> {
         return f_67518_;
      });
   }
}