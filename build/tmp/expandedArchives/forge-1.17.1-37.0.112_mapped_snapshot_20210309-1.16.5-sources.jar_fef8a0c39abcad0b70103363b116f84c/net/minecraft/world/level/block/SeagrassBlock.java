package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeagrassBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer, net.minecraftforge.common.IForgeShearable {
   protected static final float f_154492_ = 6.0F;
   protected static final VoxelShape f_154493_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

   public SeagrassBlock(BlockBehaviour.Properties p_154496_) {
      super(p_154496_);
   }

   public VoxelShape m_5940_(BlockState p_154525_, BlockGetter p_154526_, BlockPos p_154527_, CollisionContext p_154528_) {
      return f_154493_;
   }

   protected boolean m_6266_(BlockState p_154539_, BlockGetter p_154540_, BlockPos p_154541_) {
      return p_154539_.m_60783_(p_154540_, p_154541_, Direction.UP) && !p_154539_.m_60713_(Blocks.f_50450_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154503_) {
      FluidState fluidstate = p_154503_.m_43725_().m_6425_(p_154503_.m_8083_());
      return fluidstate.m_76153_(FluidTags.f_13131_) && fluidstate.m_76186_() == 8 ? super.m_5573_(p_154503_) : null;
   }

   public BlockState m_7417_(BlockState p_154530_, Direction p_154531_, BlockState p_154532_, LevelAccessor p_154533_, BlockPos p_154534_, BlockPos p_154535_) {
      BlockState blockstate = super.m_7417_(p_154530_, p_154531_, p_154532_, p_154533_, p_154534_, p_154535_);
      if (!blockstate.m_60795_()) {
         p_154533_.m_6217_().m_5945_(p_154534_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154533_));
      }

      return blockstate;
   }

   public boolean m_7370_(BlockGetter p_154510_, BlockPos p_154511_, BlockState p_154512_, boolean p_154513_) {
      return true;
   }

   public boolean m_5491_(Level p_154515_, Random p_154516_, BlockPos p_154517_, BlockState p_154518_) {
      return true;
   }

   public FluidState m_5888_(BlockState p_154537_) {
      return Fluids.f_76193_.m_76068_(false);
   }

   public void m_7719_(ServerLevel p_154498_, Random p_154499_, BlockPos p_154500_, BlockState p_154501_) {
      BlockState blockstate = Blocks.f_50038_.m_49966_();
      BlockState blockstate1 = blockstate.m_61124_(TallSeagrassBlock.f_154740_, DoubleBlockHalf.UPPER);
      BlockPos blockpos = p_154500_.m_7494_();
      if (p_154498_.m_8055_(blockpos).m_60713_(Blocks.f_49990_)) {
         p_154498_.m_7731_(p_154500_, blockstate, 2);
         p_154498_.m_7731_(blockpos, blockstate1, 2);
      }

   }

   public boolean m_6044_(BlockGetter p_154505_, BlockPos p_154506_, BlockState p_154507_, Fluid p_154508_) {
      return false;
   }

   public boolean m_7361_(LevelAccessor p_154520_, BlockPos p_154521_, BlockState p_154522_, FluidState p_154523_) {
      return false;
   }
}
