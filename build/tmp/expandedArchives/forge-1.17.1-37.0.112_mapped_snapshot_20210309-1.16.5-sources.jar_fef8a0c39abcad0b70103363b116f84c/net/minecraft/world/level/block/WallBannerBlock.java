package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBannerBlock extends AbstractBannerBlock {
   public static final DirectionProperty f_57916_ = HorizontalDirectionalBlock.f_54117_;
   private static final Map<Direction, VoxelShape> f_57917_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_(0.0D, 0.0D, 14.0D, 16.0D, 12.5D, 16.0D), Direction.SOUTH, Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.5D, 2.0D), Direction.WEST, Block.m_49796_(14.0D, 0.0D, 0.0D, 16.0D, 12.5D, 16.0D), Direction.EAST, Block.m_49796_(0.0D, 0.0D, 0.0D, 2.0D, 12.5D, 16.0D)));

   public WallBannerBlock(DyeColor p_57920_, BlockBehaviour.Properties p_57921_) {
      super(p_57920_, p_57921_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57916_, Direction.NORTH));
   }

   public String m_7705_() {
      return this.m_5456_().m_5524_();
   }

   public boolean m_7898_(BlockState p_57925_, LevelReader p_57926_, BlockPos p_57927_) {
      return p_57926_.m_8055_(p_57927_.m_142300_(p_57925_.m_61143_(f_57916_).m_122424_())).m_60767_().m_76333_();
   }

   public BlockState m_7417_(BlockState p_57935_, Direction p_57936_, BlockState p_57937_, LevelAccessor p_57938_, BlockPos p_57939_, BlockPos p_57940_) {
      return p_57936_ == p_57935_.m_61143_(f_57916_).m_122424_() && !p_57935_.m_60710_(p_57938_, p_57939_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_57935_, p_57936_, p_57937_, p_57938_, p_57939_, p_57940_);
   }

   public VoxelShape m_5940_(BlockState p_57944_, BlockGetter p_57945_, BlockPos p_57946_, CollisionContext p_57947_) {
      return f_57917_.get(p_57944_.m_61143_(f_57916_));
   }

   public BlockState m_5573_(BlockPlaceContext p_57923_) {
      BlockState blockstate = this.m_49966_();
      LevelReader levelreader = p_57923_.m_43725_();
      BlockPos blockpos = p_57923_.m_8083_();
      Direction[] adirection = p_57923_.m_6232_();

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = blockstate.m_61124_(f_57916_, direction1);
            if (blockstate.m_60710_(levelreader, blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_6843_(BlockState p_57932_, Rotation p_57933_) {
      return p_57932_.m_61124_(f_57916_, p_57933_.m_55954_(p_57932_.m_61143_(f_57916_)));
   }

   public BlockState m_6943_(BlockState p_57929_, Mirror p_57930_) {
      return p_57929_.m_60717_(p_57930_.m_54846_(p_57929_.m_61143_(f_57916_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57942_) {
      p_57942_.m_61104_(f_57916_);
   }
}