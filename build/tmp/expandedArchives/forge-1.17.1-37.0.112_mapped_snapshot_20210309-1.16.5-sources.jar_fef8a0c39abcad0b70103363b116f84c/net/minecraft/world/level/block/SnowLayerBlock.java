package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SnowLayerBlock extends Block {
   public static final int f_154646_ = 8;
   public static final IntegerProperty f_56581_ = BlockStateProperties.f_61417_;
   protected static final VoxelShape[] f_56582_ = new VoxelShape[]{Shapes.m_83040_(), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
   public static final int f_154647_ = 5;

   public SnowLayerBlock(BlockBehaviour.Properties p_56585_) {
      super(p_56585_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56581_, Integer.valueOf(1)));
   }

   public boolean m_7357_(BlockState p_56592_, BlockGetter p_56593_, BlockPos p_56594_, PathComputationType p_56595_) {
      switch(p_56595_) {
      case LAND:
         return p_56592_.m_61143_(f_56581_) < 5;
      case WATER:
         return false;
      case AIR:
         return false;
      default:
         return false;
      }
   }

   public VoxelShape m_5940_(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
      return f_56582_[p_56620_.m_61143_(f_56581_)];
   }

   public VoxelShape m_5939_(BlockState p_56625_, BlockGetter p_56626_, BlockPos p_56627_, CollisionContext p_56628_) {
      return f_56582_[p_56625_.m_61143_(f_56581_) - 1];
   }

   public VoxelShape m_7947_(BlockState p_56632_, BlockGetter p_56633_, BlockPos p_56634_) {
      return f_56582_[p_56632_.m_61143_(f_56581_)];
   }

   public VoxelShape m_5909_(BlockState p_56597_, BlockGetter p_56598_, BlockPos p_56599_, CollisionContext p_56600_) {
      return f_56582_[p_56597_.m_61143_(f_56581_)];
   }

   public boolean m_7923_(BlockState p_56630_) {
      return true;
   }

   public boolean m_7898_(BlockState p_56602_, LevelReader p_56603_, BlockPos p_56604_) {
      BlockState blockstate = p_56603_.m_8055_(p_56604_.m_7495_());
      if (!blockstate.m_60713_(Blocks.f_50126_) && !blockstate.m_60713_(Blocks.f_50354_) && !blockstate.m_60713_(Blocks.f_50375_)) {
         if (!blockstate.m_60713_(Blocks.f_50719_) && !blockstate.m_60713_(Blocks.f_50135_)) {
            return Block.m_49918_(blockstate.m_60812_(p_56603_, p_56604_.m_7495_()), Direction.UP) || blockstate.m_60713_(this) && blockstate.m_61143_(f_56581_) == 8;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public BlockState m_7417_(BlockState p_56606_, Direction p_56607_, BlockState p_56608_, LevelAccessor p_56609_, BlockPos p_56610_, BlockPos p_56611_) {
      return !p_56606_.m_60710_(p_56609_, p_56610_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_56606_, p_56607_, p_56608_, p_56609_, p_56610_, p_56611_);
   }

   public void m_7455_(BlockState p_56615_, ServerLevel p_56616_, BlockPos p_56617_, Random p_56618_) {
      if (p_56616_.m_45517_(LightLayer.BLOCK, p_56617_) > 11) {
         m_49950_(p_56615_, p_56616_, p_56617_);
         p_56616_.m_7471_(p_56617_, false);
      }

   }

   public boolean m_6864_(BlockState p_56589_, BlockPlaceContext p_56590_) {
      int i = p_56589_.m_61143_(f_56581_);
      if (p_56590_.m_43722_().m_150930_(this.m_5456_()) && i < 8) {
         if (p_56590_.m_7058_()) {
            return p_56590_.m_43719_() == Direction.UP;
         } else {
            return true;
         }
      } else {
         return i == 1;
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_56587_) {
      BlockState blockstate = p_56587_.m_43725_().m_8055_(p_56587_.m_8083_());
      if (blockstate.m_60713_(this)) {
         int i = blockstate.m_61143_(f_56581_);
         return blockstate.m_61124_(f_56581_, Integer.valueOf(Math.min(8, i + 1)));
      } else {
         return super.m_5573_(p_56587_);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56613_) {
      p_56613_.m_61104_(f_56581_);
   }
}