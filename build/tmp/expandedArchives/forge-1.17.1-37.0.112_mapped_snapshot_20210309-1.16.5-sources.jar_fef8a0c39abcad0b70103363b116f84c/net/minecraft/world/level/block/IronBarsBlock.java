package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IronBarsBlock extends CrossCollisionBlock {
   public IronBarsBlock(BlockBehaviour.Properties p_54198_) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, p_54198_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52309_, Boolean.valueOf(false)).m_61124_(f_52310_, Boolean.valueOf(false)).m_61124_(f_52311_, Boolean.valueOf(false)).m_61124_(f_52312_, Boolean.valueOf(false)).m_61124_(f_52313_, Boolean.valueOf(false)));
   }

   public BlockState m_5573_(BlockPlaceContext p_54200_) {
      BlockGetter blockgetter = p_54200_.m_43725_();
      BlockPos blockpos = p_54200_.m_8083_();
      FluidState fluidstate = p_54200_.m_43725_().m_6425_(p_54200_.m_8083_());
      BlockPos blockpos1 = blockpos.m_142127_();
      BlockPos blockpos2 = blockpos.m_142128_();
      BlockPos blockpos3 = blockpos.m_142125_();
      BlockPos blockpos4 = blockpos.m_142126_();
      BlockState blockstate = blockgetter.m_8055_(blockpos1);
      BlockState blockstate1 = blockgetter.m_8055_(blockpos2);
      BlockState blockstate2 = blockgetter.m_8055_(blockpos3);
      BlockState blockstate3 = blockgetter.m_8055_(blockpos4);
      return this.m_49966_().m_61124_(f_52309_, Boolean.valueOf(this.m_54217_(blockstate, blockstate.m_60783_(blockgetter, blockpos1, Direction.SOUTH)))).m_61124_(f_52311_, Boolean.valueOf(this.m_54217_(blockstate1, blockstate1.m_60783_(blockgetter, blockpos2, Direction.NORTH)))).m_61124_(f_52312_, Boolean.valueOf(this.m_54217_(blockstate2, blockstate2.m_60783_(blockgetter, blockpos3, Direction.EAST)))).m_61124_(f_52310_, Boolean.valueOf(this.m_54217_(blockstate3, blockstate3.m_60783_(blockgetter, blockpos4, Direction.WEST)))).m_61124_(f_52313_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public BlockState m_7417_(BlockState p_54211_, Direction p_54212_, BlockState p_54213_, LevelAccessor p_54214_, BlockPos p_54215_, BlockPos p_54216_) {
      if (p_54211_.m_61143_(f_52313_)) {
         p_54214_.m_6217_().m_5945_(p_54215_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_54214_));
      }

      return p_54212_.m_122434_().m_122479_() ? p_54211_.m_61124_(f_52314_.get(p_54212_), Boolean.valueOf(this.m_54217_(p_54213_, p_54213_.m_60783_(p_54214_, p_54216_, p_54212_.m_122424_())))) : super.m_7417_(p_54211_, p_54212_, p_54213_, p_54214_, p_54215_, p_54216_);
   }

   public VoxelShape m_5909_(BlockState p_54202_, BlockGetter p_54203_, BlockPos p_54204_, CollisionContext p_54205_) {
      return Shapes.m_83040_();
   }

   public boolean m_6104_(BlockState p_54207_, BlockState p_54208_, Direction p_54209_) {
      if (p_54208_.m_60713_(this)) {
         if (!p_54209_.m_122434_().m_122479_()) {
            return true;
         }

         if (p_54207_.m_61143_(f_52314_.get(p_54209_)) && p_54208_.m_61143_(f_52314_.get(p_54209_.m_122424_()))) {
            return true;
         }
      }

      return super.m_6104_(p_54207_, p_54208_, p_54209_);
   }

   public final boolean m_54217_(BlockState p_54218_, boolean p_54219_) {
      return !m_152463_(p_54218_) && p_54219_ || p_54218_.m_60734_() instanceof IronBarsBlock || p_54218_.m_60620_(BlockTags.f_13032_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54221_) {
      p_54221_.m_61104_(f_52309_, f_52310_, f_52312_, f_52311_, f_52313_);
   }
}