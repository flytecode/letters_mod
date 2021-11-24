package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallSeagrassBlock extends DoublePlantBlock implements LiquidBlockContainer {
   public static final EnumProperty<DoubleBlockHalf> f_154740_ = DoublePlantBlock.f_52858_;
   protected static final float f_154741_ = 6.0F;
   protected static final VoxelShape f_154742_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

   public TallSeagrassBlock(BlockBehaviour.Properties p_154745_) {
      super(p_154745_);
   }

   public VoxelShape m_5940_(BlockState p_154763_, BlockGetter p_154764_, BlockPos p_154765_, CollisionContext p_154766_) {
      return f_154742_;
   }

   protected boolean m_6266_(BlockState p_154774_, BlockGetter p_154775_, BlockPos p_154776_) {
      return p_154774_.m_60783_(p_154775_, p_154776_, Direction.UP) && !p_154774_.m_60713_(Blocks.f_50450_);
   }

   public ItemStack m_7397_(BlockGetter p_154749_, BlockPos p_154750_, BlockState p_154751_) {
      return new ItemStack(Blocks.f_50037_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154747_) {
      BlockState blockstate = super.m_5573_(p_154747_);
      if (blockstate != null) {
         FluidState fluidstate = p_154747_.m_43725_().m_6425_(p_154747_.m_8083_().m_7494_());
         if (fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8) {
            return blockstate;
         }
      }

      return null;
   }

   public boolean m_7898_(BlockState p_154768_, LevelReader p_154769_, BlockPos p_154770_) {
      if (p_154768_.m_61143_(f_154740_) == DoubleBlockHalf.UPPER) {
         BlockState blockstate = p_154769_.m_8055_(p_154770_.m_7495_());
         return blockstate.m_60713_(this) && blockstate.m_61143_(f_154740_) == DoubleBlockHalf.LOWER;
      } else {
         FluidState fluidstate = p_154769_.m_6425_(p_154770_);
         return super.m_7898_(p_154768_, p_154769_, p_154770_) && fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8;
      }
   }

   public FluidState m_5888_(BlockState p_154772_) {
      return Fluids.f_76193_.m_76068_(false);
   }

   public boolean m_6044_(BlockGetter p_154753_, BlockPos p_154754_, BlockState p_154755_, Fluid p_154756_) {
      return false;
   }

   public boolean m_7361_(LevelAccessor p_154758_, BlockPos p_154759_, BlockState p_154760_, FluidState p_154761_) {
      return false;
   }
}