package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralPlantTypeBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_49158_ = BlockStateProperties.f_61362_;
   private static final VoxelShape f_49157_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

   public BaseCoralPlantTypeBlock(BlockBehaviour.Properties p_49161_) {
      super(p_49161_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49158_, Boolean.valueOf(true)));
   }

   protected void m_49164_(BlockState p_49165_, LevelAccessor p_49166_, BlockPos p_49167_) {
      if (!m_49186_(p_49165_, p_49166_, p_49167_)) {
         p_49166_.m_6219_().m_5945_(p_49167_, this, 60 + p_49166_.m_5822_().nextInt(40));
      }

   }

   protected static boolean m_49186_(BlockState p_49187_, BlockGetter p_49188_, BlockPos p_49189_) {
      if (p_49187_.m_61143_(f_49158_)) {
         return true;
      } else {
         for(Direction direction : Direction.values()) {
            if (p_49188_.m_6425_(p_49189_.m_142300_(direction)).m_76153_(FluidTags.f_13131_)) {
               return true;
            }
         }

         return false;
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_49163_) {
      FluidState fluidstate = p_49163_.m_43725_().m_6425_(p_49163_.m_8083_());
      return this.m_49966_().m_61124_(f_49158_, Boolean.valueOf(fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8));
   }

   public VoxelShape m_5940_(BlockState p_49182_, BlockGetter p_49183_, BlockPos p_49184_, CollisionContext p_49185_) {
      return f_49157_;
   }

   public BlockState m_7417_(BlockState p_49173_, Direction p_49174_, BlockState p_49175_, LevelAccessor p_49176_, BlockPos p_49177_, BlockPos p_49178_) {
      if (p_49173_.m_61143_(f_49158_)) {
         p_49176_.m_6217_().m_5945_(p_49177_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_49176_));
      }

      return p_49174_ == Direction.DOWN && !this.m_7898_(p_49173_, p_49176_, p_49177_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_49173_, p_49174_, p_49175_, p_49176_, p_49177_, p_49178_);
   }

   public boolean m_7898_(BlockState p_49169_, LevelReader p_49170_, BlockPos p_49171_) {
      BlockPos blockpos = p_49171_.m_7495_();
      return p_49170_.m_8055_(blockpos).m_60783_(p_49170_, blockpos, Direction.UP);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49180_) {
      p_49180_.m_61104_(f_49158_);
   }

   public FluidState m_5888_(BlockState p_49191_) {
      return p_49191_.m_61143_(f_49158_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_49191_);
   }
}