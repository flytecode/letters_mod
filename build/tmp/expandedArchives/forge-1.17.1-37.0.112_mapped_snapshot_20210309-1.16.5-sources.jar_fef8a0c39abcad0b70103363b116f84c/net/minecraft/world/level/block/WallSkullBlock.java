package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallSkullBlock extends AbstractSkullBlock {
   public static final DirectionProperty f_58097_ = HorizontalDirectionalBlock.f_54117_;
   private static final Map<Direction, VoxelShape> f_58098_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D), Direction.SOUTH, Block.m_49796_(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D), Direction.EAST, Block.m_49796_(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D), Direction.WEST, Block.m_49796_(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));

   public WallSkullBlock(SkullBlock.Type p_58101_, BlockBehaviour.Properties p_58102_) {
      super(p_58101_, p_58102_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_58097_, Direction.NORTH));
   }

   public String m_7705_() {
      return this.m_5456_().m_5524_();
   }

   public VoxelShape m_5940_(BlockState p_58114_, BlockGetter p_58115_, BlockPos p_58116_, CollisionContext p_58117_) {
      return f_58098_.get(p_58114_.m_61143_(f_58097_));
   }

   public BlockState m_5573_(BlockPlaceContext p_58104_) {
      BlockState blockstate = this.m_49966_();
      BlockGetter blockgetter = p_58104_.m_43725_();
      BlockPos blockpos = p_58104_.m_8083_();
      Direction[] adirection = p_58104_.m_6232_();

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = blockstate.m_61124_(f_58097_, direction1);
            if (!blockgetter.m_8055_(blockpos.m_142300_(direction)).m_60629_(p_58104_)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_6843_(BlockState p_58109_, Rotation p_58110_) {
      return p_58109_.m_61124_(f_58097_, p_58110_.m_55954_(p_58109_.m_61143_(f_58097_)));
   }

   public BlockState m_6943_(BlockState p_58106_, Mirror p_58107_) {
      return p_58106_.m_60717_(p_58107_.m_54846_(p_58106_.m_61143_(f_58097_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58112_) {
      p_58112_.m_61104_(f_58097_);
   }
}