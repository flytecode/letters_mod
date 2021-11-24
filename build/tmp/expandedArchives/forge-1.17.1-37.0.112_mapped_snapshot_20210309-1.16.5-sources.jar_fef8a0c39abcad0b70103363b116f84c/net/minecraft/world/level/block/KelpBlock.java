package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KelpBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
   protected static final VoxelShape f_54297_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
   private static final double f_153453_ = 0.14D;

   public KelpBlock(BlockBehaviour.Properties p_54300_) {
      super(p_54300_, Direction.UP, f_54297_, true, 0.14D);
   }

   protected boolean m_5971_(BlockState p_54321_) {
      return p_54321_.m_60713_(Blocks.f_49990_);
   }

   protected Block m_7777_() {
      return Blocks.f_50576_;
   }

   protected boolean m_142209_(BlockState p_153455_) {
      return !p_153455_.m_60713_(Blocks.f_50450_);
   }

   public boolean m_6044_(BlockGetter p_54304_, BlockPos p_54305_, BlockState p_54306_, Fluid p_54307_) {
      return false;
   }

   public boolean m_7361_(LevelAccessor p_54309_, BlockPos p_54310_, BlockState p_54311_, FluidState p_54312_) {
      return false;
   }

   protected int m_7363_(Random p_54314_) {
      return 1;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_54302_) {
      FluidState fluidstate = p_54302_.m_43725_().m_6425_(p_54302_.m_8083_());
      return fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8 ? super.m_5573_(p_54302_) : null;
   }

   public FluidState m_5888_(BlockState p_54319_) {
      return Fluids.f_76193_.m_76068_(false);
   }
}