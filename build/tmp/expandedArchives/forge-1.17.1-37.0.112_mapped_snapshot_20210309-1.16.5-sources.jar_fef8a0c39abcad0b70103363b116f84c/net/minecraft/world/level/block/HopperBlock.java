package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HopperBlock extends BaseEntityBlock {
   public static final DirectionProperty f_54021_ = BlockStateProperties.f_61373_;
   public static final BooleanProperty f_54022_ = BlockStateProperties.f_61431_;
   private static final VoxelShape f_54023_ = Block.m_49796_(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private static final VoxelShape f_54024_ = Block.m_49796_(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
   private static final VoxelShape f_54025_ = Shapes.m_83110_(f_54024_, f_54023_);
   private static final VoxelShape f_54026_ = Shapes.m_83113_(f_54025_, Hopper.f_59296_, BooleanOp.f_82685_);
   private static final VoxelShape f_54027_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
   private static final VoxelShape f_54028_ = Shapes.m_83110_(f_54026_, Block.m_49796_(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
   private static final VoxelShape f_54029_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
   private static final VoxelShape f_54030_ = Shapes.m_83110_(f_54026_, Block.m_49796_(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
   private static final VoxelShape f_54031_ = Shapes.m_83110_(f_54026_, Block.m_49796_(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
   private static final VoxelShape f_54032_ = Hopper.f_59296_;
   private static final VoxelShape f_54033_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
   private static final VoxelShape f_54034_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
   private static final VoxelShape f_54035_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
   private static final VoxelShape f_54036_ = Shapes.m_83110_(Hopper.f_59296_, Block.m_49796_(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));

   public HopperBlock(BlockBehaviour.Properties p_54039_) {
      super(p_54039_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54021_, Direction.DOWN).m_61124_(f_54022_, Boolean.valueOf(true)));
   }

   public VoxelShape m_5940_(BlockState p_54105_, BlockGetter p_54106_, BlockPos p_54107_, CollisionContext p_54108_) {
      switch((Direction)p_54105_.m_61143_(f_54021_)) {
      case DOWN:
         return f_54027_;
      case NORTH:
         return f_54029_;
      case SOUTH:
         return f_54030_;
      case WEST:
         return f_54031_;
      case EAST:
         return f_54028_;
      default:
         return f_54026_;
      }
   }

   public VoxelShape m_6079_(BlockState p_54099_, BlockGetter p_54100_, BlockPos p_54101_) {
      switch((Direction)p_54099_.m_61143_(f_54021_)) {
      case DOWN:
         return f_54032_;
      case NORTH:
         return f_54034_;
      case SOUTH:
         return f_54035_;
      case WEST:
         return f_54036_;
      case EAST:
         return f_54033_;
      default:
         return Hopper.f_59296_;
      }
   }

   public BlockState m_5573_(BlockPlaceContext p_54041_) {
      Direction direction = p_54041_.m_43719_().m_122424_();
      return this.m_49966_().m_61124_(f_54021_, direction.m_122434_() == Direction.Axis.Y ? Direction.DOWN : direction).m_61124_(f_54022_, Boolean.valueOf(true));
   }

   public BlockEntity m_142194_(BlockPos p_153382_, BlockState p_153383_) {
      return new HopperBlockEntity(p_153382_, p_153383_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153378_, BlockState p_153379_, BlockEntityType<T> p_153380_) {
      return p_153378_.f_46443_ ? null : m_152132_(p_153380_, BlockEntityType.f_58933_, HopperBlockEntity::m_155573_);
   }

   public void m_6402_(Level p_54049_, BlockPos p_54050_, BlockState p_54051_, LivingEntity p_54052_, ItemStack p_54053_) {
      if (p_54053_.m_41788_()) {
         BlockEntity blockentity = p_54049_.m_7702_(p_54050_);
         if (blockentity instanceof HopperBlockEntity) {
            ((HopperBlockEntity)blockentity).m_58638_(p_54053_.m_41786_());
         }
      }

   }

   public void m_6807_(BlockState p_54110_, Level p_54111_, BlockPos p_54112_, BlockState p_54113_, boolean p_54114_) {
      if (!p_54113_.m_60713_(p_54110_.m_60734_())) {
         this.m_54044_(p_54111_, p_54112_, p_54110_);
      }
   }

   public InteractionResult m_6227_(BlockState p_54071_, Level p_54072_, BlockPos p_54073_, Player p_54074_, InteractionHand p_54075_, BlockHitResult p_54076_) {
      if (p_54072_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = p_54072_.m_7702_(p_54073_);
         if (blockentity instanceof HopperBlockEntity) {
            p_54074_.m_5893_((HopperBlockEntity)blockentity);
            p_54074_.m_36220_(Stats.f_12957_);
         }

         return InteractionResult.CONSUME;
      }
   }

   public void m_6861_(BlockState p_54078_, Level p_54079_, BlockPos p_54080_, Block p_54081_, BlockPos p_54082_, boolean p_54083_) {
      this.m_54044_(p_54079_, p_54080_, p_54078_);
   }

   private void m_54044_(Level p_54045_, BlockPos p_54046_, BlockState p_54047_) {
      boolean flag = !p_54045_.m_46753_(p_54046_);
      if (flag != p_54047_.m_61143_(f_54022_)) {
         p_54045_.m_7731_(p_54046_, p_54047_.m_61124_(f_54022_, Boolean.valueOf(flag)), 4);
      }

   }

   public void m_6810_(BlockState p_54085_, Level p_54086_, BlockPos p_54087_, BlockState p_54088_, boolean p_54089_) {
      if (!p_54085_.m_60713_(p_54088_.m_60734_())) {
         BlockEntity blockentity = p_54086_.m_7702_(p_54087_);
         if (blockentity instanceof HopperBlockEntity) {
            Containers.m_19002_(p_54086_, p_54087_, (HopperBlockEntity)blockentity);
            p_54086_.m_46717_(p_54087_, this);
         }

         super.m_6810_(p_54085_, p_54086_, p_54087_, p_54088_, p_54089_);
      }
   }

   public RenderShape m_7514_(BlockState p_54103_) {
      return RenderShape.MODEL;
   }

   public boolean m_7278_(BlockState p_54055_) {
      return true;
   }

   public int m_6782_(BlockState p_54062_, Level p_54063_, BlockPos p_54064_) {
      return AbstractContainerMenu.m_38918_(p_54063_.m_7702_(p_54064_));
   }

   public BlockState m_6843_(BlockState p_54094_, Rotation p_54095_) {
      return p_54094_.m_61124_(f_54021_, p_54095_.m_55954_(p_54094_.m_61143_(f_54021_)));
   }

   public BlockState m_6943_(BlockState p_54091_, Mirror p_54092_) {
      return p_54091_.m_60717_(p_54092_.m_54846_(p_54091_.m_61143_(f_54021_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54097_) {
      p_54097_.m_61104_(f_54021_, f_54022_);
   }

   public void m_7892_(BlockState p_54066_, Level p_54067_, BlockPos p_54068_, Entity p_54069_) {
      BlockEntity blockentity = p_54067_.m_7702_(p_54068_);
      if (blockentity instanceof HopperBlockEntity) {
         HopperBlockEntity.m_155567_(p_54067_, p_54068_, p_54066_, p_54069_, (HopperBlockEntity)blockentity);
      }

   }

   public boolean m_7357_(BlockState p_54057_, BlockGetter p_54058_, BlockPos p_54059_, PathComputationType p_54060_) {
      return false;
   }
}