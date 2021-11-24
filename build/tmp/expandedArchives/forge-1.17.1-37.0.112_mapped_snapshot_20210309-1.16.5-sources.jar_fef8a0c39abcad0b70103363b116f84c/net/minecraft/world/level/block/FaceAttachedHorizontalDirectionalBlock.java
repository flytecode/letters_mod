package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class FaceAttachedHorizontalDirectionalBlock extends HorizontalDirectionalBlock {
   public static final EnumProperty<AttachFace> f_53179_ = BlockStateProperties.f_61376_;

   public FaceAttachedHorizontalDirectionalBlock(BlockBehaviour.Properties p_53182_) {
      super(p_53182_);
   }

   public boolean m_7898_(BlockState p_53186_, LevelReader p_53187_, BlockPos p_53188_) {
      return m_53196_(p_53187_, p_53188_, m_53200_(p_53186_).m_122424_());
   }

   public static boolean m_53196_(LevelReader p_53197_, BlockPos p_53198_, Direction p_53199_) {
      BlockPos blockpos = p_53198_.m_142300_(p_53199_);
      return p_53197_.m_8055_(blockpos).m_60783_(p_53197_, blockpos, p_53199_.m_122424_());
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_53184_) {
      for(Direction direction : p_53184_.m_6232_()) {
         BlockState blockstate;
         if (direction.m_122434_() == Direction.Axis.Y) {
            blockstate = this.m_49966_().m_61124_(f_53179_, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).m_61124_(f_54117_, p_53184_.m_8125_());
         } else {
            blockstate = this.m_49966_().m_61124_(f_53179_, AttachFace.WALL).m_61124_(f_54117_, direction.m_122424_());
         }

         if (blockstate.m_60710_(p_53184_.m_43725_(), p_53184_.m_8083_())) {
            return blockstate;
         }
      }

      return null;
   }

   public BlockState m_7417_(BlockState p_53190_, Direction p_53191_, BlockState p_53192_, LevelAccessor p_53193_, BlockPos p_53194_, BlockPos p_53195_) {
      return m_53200_(p_53190_).m_122424_() == p_53191_ && !p_53190_.m_60710_(p_53193_, p_53194_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_53190_, p_53191_, p_53192_, p_53193_, p_53194_, p_53195_);
   }

   protected static Direction m_53200_(BlockState p_53201_) {
      switch((AttachFace)p_53201_.m_61143_(f_53179_)) {
      case CEILING:
         return Direction.DOWN;
      case FLOOR:
         return Direction.UP;
      default:
         return p_53201_.m_61143_(f_54117_);
      }
   }
}