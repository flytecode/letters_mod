package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlabBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<SlabType> f_56353_ = BlockStateProperties.f_61397_;
   public static final BooleanProperty f_56354_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_56355_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   protected static final VoxelShape f_56356_ = Block.m_49796_(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

   public SlabBlock(BlockBehaviour.Properties p_56359_) {
      super(p_56359_);
      this.m_49959_(this.m_49966_().m_61124_(f_56353_, SlabType.BOTTOM).m_61124_(f_56354_, Boolean.valueOf(false)));
   }

   public boolean m_7923_(BlockState p_56395_) {
      return p_56395_.m_61143_(f_56353_) != SlabType.DOUBLE;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56388_) {
      p_56388_.m_61104_(f_56353_, f_56354_);
   }

   public VoxelShape m_5940_(BlockState p_56390_, BlockGetter p_56391_, BlockPos p_56392_, CollisionContext p_56393_) {
      SlabType slabtype = p_56390_.m_61143_(f_56353_);
      switch(slabtype) {
      case DOUBLE:
         return Shapes.m_83144_();
      case TOP:
         return f_56356_;
      default:
         return f_56355_;
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_56361_) {
      BlockPos blockpos = p_56361_.m_8083_();
      BlockState blockstate = p_56361_.m_43725_().m_8055_(blockpos);
      if (blockstate.m_60713_(this)) {
         return blockstate.m_61124_(f_56353_, SlabType.DOUBLE).m_61124_(f_56354_, Boolean.valueOf(false));
      } else {
         FluidState fluidstate = p_56361_.m_43725_().m_6425_(blockpos);
         BlockState blockstate1 = this.m_49966_().m_61124_(f_56353_, SlabType.BOTTOM).m_61124_(f_56354_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
         Direction direction = p_56361_.m_43719_();
         return direction != Direction.DOWN && (direction == Direction.UP || !(p_56361_.m_43720_().f_82480_ - (double)blockpos.m_123342_() > 0.5D)) ? blockstate1 : blockstate1.m_61124_(f_56353_, SlabType.TOP);
      }
   }

   public boolean m_6864_(BlockState p_56373_, BlockPlaceContext p_56374_) {
      ItemStack itemstack = p_56374_.m_43722_();
      SlabType slabtype = p_56373_.m_61143_(f_56353_);
      if (slabtype != SlabType.DOUBLE && itemstack.m_150930_(this.m_5456_())) {
         if (p_56374_.m_7058_()) {
            boolean flag = p_56374_.m_43720_().f_82480_ - (double)p_56374_.m_8083_().m_123342_() > 0.5D;
            Direction direction = p_56374_.m_43719_();
            if (slabtype == SlabType.BOTTOM) {
               return direction == Direction.UP || flag && direction.m_122434_().m_122479_();
            } else {
               return direction == Direction.DOWN || !flag && direction.m_122434_().m_122479_();
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public FluidState m_5888_(BlockState p_56397_) {
      return p_56397_.m_61143_(f_56354_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_56397_);
   }

   public boolean m_7361_(LevelAccessor p_56368_, BlockPos p_56369_, BlockState p_56370_, FluidState p_56371_) {
      return p_56370_.m_61143_(f_56353_) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.m_7361_(p_56368_, p_56369_, p_56370_, p_56371_) : false;
   }

   public boolean m_6044_(BlockGetter p_56363_, BlockPos p_56364_, BlockState p_56365_, Fluid p_56366_) {
      return p_56365_.m_61143_(f_56353_) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.m_6044_(p_56363_, p_56364_, p_56365_, p_56366_) : false;
   }

   public BlockState m_7417_(BlockState p_56381_, Direction p_56382_, BlockState p_56383_, LevelAccessor p_56384_, BlockPos p_56385_, BlockPos p_56386_) {
      if (p_56381_.m_61143_(f_56354_)) {
         p_56384_.m_6217_().m_5945_(p_56385_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56384_));
      }

      return super.m_7417_(p_56381_, p_56382_, p_56383_, p_56384_, p_56385_, p_56386_);
   }

   public boolean m_7357_(BlockState p_56376_, BlockGetter p_56377_, BlockPos p_56378_, PathComputationType p_56379_) {
      switch(p_56379_) {
      case LAND:
         return false;
      case WATER:
         return p_56377_.m_6425_(p_56378_).m_76153_(FluidTags.f_13131_);
      case AIR:
         return false;
      default:
         return false;
      }
   }
}