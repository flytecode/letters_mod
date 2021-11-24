package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ChorusPlantBlock extends PipeBlock {
   public ChorusPlantBlock(BlockBehaviour.Properties p_51707_) {
      super(0.3125F, p_51707_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55148_, Boolean.valueOf(false)).m_61124_(f_55149_, Boolean.valueOf(false)).m_61124_(f_55150_, Boolean.valueOf(false)).m_61124_(f_55151_, Boolean.valueOf(false)).m_61124_(f_55152_, Boolean.valueOf(false)).m_61124_(f_55153_, Boolean.valueOf(false)));
   }

   public BlockState m_5573_(BlockPlaceContext p_51709_) {
      return this.m_51710_(p_51709_.m_43725_(), p_51709_.m_8083_());
   }

   public BlockState m_51710_(BlockGetter p_51711_, BlockPos p_51712_) {
      BlockState blockstate = p_51711_.m_8055_(p_51712_.m_7495_());
      BlockState blockstate1 = p_51711_.m_8055_(p_51712_.m_7494_());
      BlockState blockstate2 = p_51711_.m_8055_(p_51712_.m_142127_());
      BlockState blockstate3 = p_51711_.m_8055_(p_51712_.m_142126_());
      BlockState blockstate4 = p_51711_.m_8055_(p_51712_.m_142128_());
      BlockState blockstate5 = p_51711_.m_8055_(p_51712_.m_142125_());
      return this.m_49966_().m_61124_(f_55153_, Boolean.valueOf(blockstate.m_60713_(this) || blockstate.m_60713_(Blocks.f_50491_) || blockstate.m_60713_(Blocks.f_50259_))).m_61124_(f_55152_, Boolean.valueOf(blockstate1.m_60713_(this) || blockstate1.m_60713_(Blocks.f_50491_))).m_61124_(f_55148_, Boolean.valueOf(blockstate2.m_60713_(this) || blockstate2.m_60713_(Blocks.f_50491_))).m_61124_(f_55149_, Boolean.valueOf(blockstate3.m_60713_(this) || blockstate3.m_60713_(Blocks.f_50491_))).m_61124_(f_55150_, Boolean.valueOf(blockstate4.m_60713_(this) || blockstate4.m_60713_(Blocks.f_50491_))).m_61124_(f_55151_, Boolean.valueOf(blockstate5.m_60713_(this) || blockstate5.m_60713_(Blocks.f_50491_)));
   }

   public BlockState m_7417_(BlockState p_51728_, Direction p_51729_, BlockState p_51730_, LevelAccessor p_51731_, BlockPos p_51732_, BlockPos p_51733_) {
      if (!p_51728_.m_60710_(p_51731_, p_51732_)) {
         p_51731_.m_6219_().m_5945_(p_51732_, this, 1);
         return super.m_7417_(p_51728_, p_51729_, p_51730_, p_51731_, p_51732_, p_51733_);
      } else {
         boolean flag = p_51730_.m_60713_(this) || p_51730_.m_60713_(Blocks.f_50491_) || p_51729_ == Direction.DOWN && p_51730_.m_60713_(Blocks.f_50259_);
         return p_51728_.m_61124_(f_55154_.get(p_51729_), Boolean.valueOf(flag));
      }
   }

   public void m_7458_(BlockState p_51714_, ServerLevel p_51715_, BlockPos p_51716_, Random p_51717_) {
      if (!p_51714_.m_60710_(p_51715_, p_51716_)) {
         p_51715_.m_46961_(p_51716_, true);
      }

   }

   public boolean m_7898_(BlockState p_51724_, LevelReader p_51725_, BlockPos p_51726_) {
      BlockState blockstate = p_51725_.m_8055_(p_51726_.m_7495_());
      boolean flag = !p_51725_.m_8055_(p_51726_.m_7494_()).m_60795_() && !blockstate.m_60795_();

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_51726_.m_142300_(direction);
         BlockState blockstate1 = p_51725_.m_8055_(blockpos);
         if (blockstate1.m_60713_(this)) {
            if (flag) {
               return false;
            }

            BlockState blockstate2 = p_51725_.m_8055_(blockpos.m_7495_());
            if (blockstate2.m_60713_(this) || blockstate2.m_60713_(Blocks.f_50259_)) {
               return true;
            }
         }
      }

      return blockstate.m_60713_(this) || blockstate.m_60713_(Blocks.f_50259_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51735_) {
      p_51735_.m_61104_(f_55148_, f_55149_, f_55150_, f_55151_, f_55152_, f_55153_);
   }

   public boolean m_7357_(BlockState p_51719_, BlockGetter p_51720_, BlockPos p_51721_, PathComputationType p_51722_) {
      return false;
   }
}