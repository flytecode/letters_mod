package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralWallFanBlock extends BaseCoralFanBlock {
   public static final DirectionProperty f_49192_ = HorizontalDirectionalBlock.f_54117_;
   private static final Map<Direction, VoxelShape> f_49193_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), Direction.SOUTH, Block.m_49796_(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), Direction.WEST, Block.m_49796_(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), Direction.EAST, Block.m_49796_(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));

   public BaseCoralWallFanBlock(BlockBehaviour.Properties p_49196_) {
      super(p_49196_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49192_, Direction.NORTH).m_61124_(f_49158_, Boolean.valueOf(true)));
   }

   public VoxelShape m_5940_(BlockState p_49219_, BlockGetter p_49220_, BlockPos p_49221_, CollisionContext p_49222_) {
      return f_49193_.get(p_49219_.m_61143_(f_49192_));
   }

   public BlockState m_6843_(BlockState p_49207_, Rotation p_49208_) {
      return p_49207_.m_61124_(f_49192_, p_49208_.m_55954_(p_49207_.m_61143_(f_49192_)));
   }

   public BlockState m_6943_(BlockState p_49204_, Mirror p_49205_) {
      return p_49204_.m_60717_(p_49205_.m_54846_(p_49204_.m_61143_(f_49192_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49217_) {
      p_49217_.m_61104_(f_49192_, f_49158_);
   }

   public BlockState m_7417_(BlockState p_49210_, Direction p_49211_, BlockState p_49212_, LevelAccessor p_49213_, BlockPos p_49214_, BlockPos p_49215_) {
      if (p_49210_.m_61143_(f_49158_)) {
         p_49213_.m_6217_().m_5945_(p_49214_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_49213_));
      }

      return p_49211_.m_122424_() == p_49210_.m_61143_(f_49192_) && !p_49210_.m_60710_(p_49213_, p_49214_) ? Blocks.f_50016_.m_49966_() : p_49210_;
   }

   public boolean m_7898_(BlockState p_49200_, LevelReader p_49201_, BlockPos p_49202_) {
      Direction direction = p_49200_.m_61143_(f_49192_);
      BlockPos blockpos = p_49202_.m_142300_(direction.m_122424_());
      BlockState blockstate = p_49201_.m_8055_(blockpos);
      return blockstate.m_60783_(p_49201_, blockpos, direction);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_49198_) {
      BlockState blockstate = super.m_5573_(p_49198_);
      LevelReader levelreader = p_49198_.m_43725_();
      BlockPos blockpos = p_49198_.m_8083_();
      Direction[] adirection = p_49198_.m_6232_();

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            blockstate = blockstate.m_61124_(f_49192_, direction.m_122424_());
            if (blockstate.m_60710_(levelreader, blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }
}