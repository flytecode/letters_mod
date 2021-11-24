package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SnowyDirtBlock extends Block {
   public static final BooleanProperty f_56637_ = BlockStateProperties.f_61451_;

   public SnowyDirtBlock(BlockBehaviour.Properties p_56640_) {
      super(p_56640_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56637_, Boolean.valueOf(false)));
   }

   public BlockState m_7417_(BlockState p_56644_, Direction p_56645_, BlockState p_56646_, LevelAccessor p_56647_, BlockPos p_56648_, BlockPos p_56649_) {
      return p_56645_ == Direction.UP ? p_56644_.m_61124_(f_56637_, Boolean.valueOf(m_154648_(p_56646_))) : super.m_7417_(p_56644_, p_56645_, p_56646_, p_56647_, p_56648_, p_56649_);
   }

   public BlockState m_5573_(BlockPlaceContext p_56642_) {
      BlockState blockstate = p_56642_.m_43725_().m_8055_(p_56642_.m_8083_().m_7494_());
      return this.m_49966_().m_61124_(f_56637_, Boolean.valueOf(m_154648_(blockstate)));
   }

   private static boolean m_154648_(BlockState p_154649_) {
      return p_154649_.m_60620_(BlockTags.f_144279_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56651_) {
      p_56651_.m_61104_(f_56637_);
   }
}