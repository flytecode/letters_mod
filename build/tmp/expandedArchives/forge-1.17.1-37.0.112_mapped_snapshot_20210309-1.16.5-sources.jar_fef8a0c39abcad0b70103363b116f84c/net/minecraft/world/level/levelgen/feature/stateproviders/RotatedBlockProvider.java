package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RotatedBlockProvider extends BlockStateProvider {
   public static final Codec<RotatedBlockProvider> f_68786_ = BlockState.f_61039_.fieldOf("state").xmap(BlockBehaviour.BlockStateBase::m_60734_, Block::m_49966_).xmap(RotatedBlockProvider::new, (p_68793_) -> {
      return p_68793_.f_68787_;
   }).codec();
   private final Block f_68787_;

   public RotatedBlockProvider(Block p_68790_) {
      this.f_68787_ = p_68790_;
   }

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_68756_;
   }

   public BlockState m_7112_(Random p_68795_, BlockPos p_68796_) {
      Direction.Axis direction$axis = Direction.Axis.m_122475_(p_68795_);
      return this.f_68787_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, direction$axis);
   }
}