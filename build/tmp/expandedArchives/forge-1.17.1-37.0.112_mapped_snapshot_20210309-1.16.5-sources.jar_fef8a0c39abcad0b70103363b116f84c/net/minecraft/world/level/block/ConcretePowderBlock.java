package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ConcretePowderBlock extends FallingBlock {
   private final BlockState f_52058_;

   public ConcretePowderBlock(Block p_52060_, BlockBehaviour.Properties p_52061_) {
      super(p_52061_);
      this.f_52058_ = p_52060_.m_49966_();
   }

   public void m_142216_(Level p_52068_, BlockPos p_52069_, BlockState p_52070_, BlockState p_52071_, FallingBlockEntity p_52072_) {
      if (m_52080_(p_52068_, p_52069_, p_52071_)) {
         p_52068_.m_7731_(p_52069_, this.f_52058_, 3);
      }

   }

   public BlockState m_5573_(BlockPlaceContext p_52063_) {
      BlockGetter blockgetter = p_52063_.m_43725_();
      BlockPos blockpos = p_52063_.m_8083_();
      BlockState blockstate = blockgetter.m_8055_(blockpos);
      return m_52080_(blockgetter, blockpos, blockstate) ? this.f_52058_ : super.m_5573_(p_52063_);
   }

   private static boolean m_52080_(BlockGetter p_52081_, BlockPos p_52082_, BlockState p_52083_) {
      return m_52088_(p_52083_) || m_52064_(p_52081_, p_52082_);
   }

   private static boolean m_52064_(BlockGetter p_52065_, BlockPos p_52066_) {
      boolean flag = false;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_52066_.m_122032_();

      for(Direction direction : Direction.values()) {
         BlockState blockstate = p_52065_.m_8055_(blockpos$mutableblockpos);
         if (direction != Direction.DOWN || m_52088_(blockstate)) {
            blockpos$mutableblockpos.m_122159_(p_52066_, direction);
            blockstate = p_52065_.m_8055_(blockpos$mutableblockpos);
            if (m_52088_(blockstate) && !blockstate.m_60783_(p_52065_, p_52066_, direction.m_122424_())) {
               flag = true;
               break;
            }
         }
      }

      return flag;
   }

   private static boolean m_52088_(BlockState p_52089_) {
      return p_52089_.m_60819_().m_76153_(FluidTags.f_13131_);
   }

   public BlockState m_7417_(BlockState p_52074_, Direction p_52075_, BlockState p_52076_, LevelAccessor p_52077_, BlockPos p_52078_, BlockPos p_52079_) {
      return m_52064_(p_52077_, p_52078_) ? this.f_52058_ : super.m_7417_(p_52074_, p_52075_, p_52076_, p_52077_, p_52078_, p_52079_);
   }

   public int m_6248_(BlockState p_52085_, BlockGetter p_52086_, BlockPos p_52087_) {
      return p_52085_.m_60780_(p_52086_, p_52087_).f_76396_;
   }
}