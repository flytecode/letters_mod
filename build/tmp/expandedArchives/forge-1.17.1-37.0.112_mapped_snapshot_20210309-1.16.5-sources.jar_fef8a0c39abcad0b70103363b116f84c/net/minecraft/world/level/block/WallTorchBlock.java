package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallTorchBlock extends TorchBlock {
   public static final DirectionProperty f_58119_ = HorizontalDirectionalBlock.f_54117_;
   protected static final float f_154885_ = 2.5F;
   private static final Map<Direction, VoxelShape> f_58120_ = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.m_49796_(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.m_49796_(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.m_49796_(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.m_49796_(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

   public WallTorchBlock(BlockBehaviour.Properties p_58123_, ParticleOptions p_58124_) {
      super(p_58123_, p_58124_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_58119_, Direction.NORTH));
   }

   public String m_7705_() {
      return this.m_5456_().m_5524_();
   }

   public VoxelShape m_5940_(BlockState p_58152_, BlockGetter p_58153_, BlockPos p_58154_, CollisionContext p_58155_) {
      return m_58156_(p_58152_);
   }

   public static VoxelShape m_58156_(BlockState p_58157_) {
      return f_58120_.get(p_58157_.m_61143_(f_58119_));
   }

   public boolean m_7898_(BlockState p_58133_, LevelReader p_58134_, BlockPos p_58135_) {
      Direction direction = p_58133_.m_61143_(f_58119_);
      BlockPos blockpos = p_58135_.m_142300_(direction.m_122424_());
      BlockState blockstate = p_58134_.m_8055_(blockpos);
      return blockstate.m_60783_(p_58134_, blockpos, direction);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_58126_) {
      BlockState blockstate = this.m_49966_();
      LevelReader levelreader = p_58126_.m_43725_();
      BlockPos blockpos = p_58126_.m_8083_();
      Direction[] adirection = p_58126_.m_6232_();

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = blockstate.m_61124_(f_58119_, direction1);
            if (blockstate.m_60710_(levelreader, blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_7417_(BlockState p_58143_, Direction p_58144_, BlockState p_58145_, LevelAccessor p_58146_, BlockPos p_58147_, BlockPos p_58148_) {
      return p_58144_.m_122424_() == p_58143_.m_61143_(f_58119_) && !p_58143_.m_60710_(p_58146_, p_58147_) ? Blocks.f_50016_.m_49966_() : p_58143_;
   }

   public void m_7100_(BlockState p_58128_, Level p_58129_, BlockPos p_58130_, Random p_58131_) {
      Direction direction = p_58128_.m_61143_(f_58119_);
      double d0 = (double)p_58130_.m_123341_() + 0.5D;
      double d1 = (double)p_58130_.m_123342_() + 0.7D;
      double d2 = (double)p_58130_.m_123343_() + 0.5D;
      double d3 = 0.22D;
      double d4 = 0.27D;
      Direction direction1 = direction.m_122424_();
      p_58129_.m_7106_(ParticleTypes.f_123762_, d0 + 0.27D * (double)direction1.m_122429_(), d1 + 0.22D, d2 + 0.27D * (double)direction1.m_122431_(), 0.0D, 0.0D, 0.0D);
      p_58129_.m_7106_(this.f_57488_, d0 + 0.27D * (double)direction1.m_122429_(), d1 + 0.22D, d2 + 0.27D * (double)direction1.m_122431_(), 0.0D, 0.0D, 0.0D);
   }

   public BlockState m_6843_(BlockState p_58140_, Rotation p_58141_) {
      return p_58140_.m_61124_(f_58119_, p_58141_.m_55954_(p_58140_.m_61143_(f_58119_)));
   }

   public BlockState m_6943_(BlockState p_58137_, Mirror p_58138_) {
      return p_58137_.m_60717_(p_58138_.m_54846_(p_58137_.m_61143_(f_58119_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_58150_) {
      p_58150_.m_61104_(f_58119_);
   }
}