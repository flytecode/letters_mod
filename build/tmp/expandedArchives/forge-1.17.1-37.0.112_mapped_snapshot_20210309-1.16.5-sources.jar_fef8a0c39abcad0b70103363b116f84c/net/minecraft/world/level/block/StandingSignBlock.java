package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class StandingSignBlock extends SignBlock {
   public static final IntegerProperty f_56987_ = BlockStateProperties.f_61390_;

   public StandingSignBlock(BlockBehaviour.Properties p_56990_, WoodType p_56991_) {
      super(p_56990_, p_56991_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56987_, Integer.valueOf(0)).m_61124_(f_56268_, Boolean.valueOf(false)));
   }

   public boolean m_7898_(BlockState p_56995_, LevelReader p_56996_, BlockPos p_56997_) {
      return p_56996_.m_8055_(p_56997_.m_7495_()).m_60767_().m_76333_();
   }

   public BlockState m_5573_(BlockPlaceContext p_56993_) {
      FluidState fluidstate = p_56993_.m_43725_().m_6425_(p_56993_.m_8083_());
      return this.m_49966_().m_61124_(f_56987_, Integer.valueOf(Mth.m_14107_((double)((180.0F + p_56993_.m_7074_()) * 16.0F / 360.0F) + 0.5D) & 15)).m_61124_(f_56268_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public BlockState m_7417_(BlockState p_57005_, Direction p_57006_, BlockState p_57007_, LevelAccessor p_57008_, BlockPos p_57009_, BlockPos p_57010_) {
      return p_57006_ == Direction.DOWN && !this.m_7898_(p_57005_, p_57008_, p_57009_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_57005_, p_57006_, p_57007_, p_57008_, p_57009_, p_57010_);
   }

   public BlockState m_6843_(BlockState p_57002_, Rotation p_57003_) {
      return p_57002_.m_61124_(f_56987_, Integer.valueOf(p_57003_.m_55949_(p_57002_.m_61143_(f_56987_), 16)));
   }

   public BlockState m_6943_(BlockState p_56999_, Mirror p_57000_) {
      return p_56999_.m_61124_(f_56987_, Integer.valueOf(p_57000_.m_54843_(p_56999_.m_61143_(f_56987_), 16)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57012_) {
      p_57012_.m_61104_(f_56987_, f_56268_);
   }
}