package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceBlock extends CrossCollisionBlock {
   private final VoxelShape[] f_53300_;

   public FenceBlock(BlockBehaviour.Properties p_53302_) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, p_53302_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52309_, Boolean.valueOf(false)).m_61124_(f_52310_, Boolean.valueOf(false)).m_61124_(f_52311_, Boolean.valueOf(false)).m_61124_(f_52312_, Boolean.valueOf(false)).m_61124_(f_52313_, Boolean.valueOf(false)));
      this.f_53300_ = this.m_52326_(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   public VoxelShape m_7952_(BlockState p_53338_, BlockGetter p_53339_, BlockPos p_53340_) {
      return this.f_53300_[this.m_52363_(p_53338_)];
   }

   public VoxelShape m_5909_(BlockState p_53311_, BlockGetter p_53312_, BlockPos p_53313_, CollisionContext p_53314_) {
      return this.m_5940_(p_53311_, p_53312_, p_53313_, p_53314_);
   }

   public boolean m_7357_(BlockState p_53306_, BlockGetter p_53307_, BlockPos p_53308_, PathComputationType p_53309_) {
      return false;
   }

   public boolean m_53329_(BlockState p_53330_, boolean p_53331_, Direction p_53332_) {
      Block block = p_53330_.m_60734_();
      boolean flag = this.m_153254_(p_53330_);
      boolean flag1 = block instanceof FenceGateBlock && FenceGateBlock.m_53378_(p_53330_, p_53332_);
      return !m_152463_(p_53330_) && p_53331_ || flag || flag1;
   }

   private boolean m_153254_(BlockState p_153255_) {
      return p_153255_.m_60620_(BlockTags.f_13039_) && p_153255_.m_60620_(BlockTags.f_13098_) == this.m_49966_().m_60620_(BlockTags.f_13098_);
   }

   public InteractionResult m_6227_(BlockState p_53316_, Level p_53317_, BlockPos p_53318_, Player p_53319_, InteractionHand p_53320_, BlockHitResult p_53321_) {
      if (p_53317_.f_46443_) {
         ItemStack itemstack = p_53319_.m_21120_(p_53320_);
         return itemstack.m_150930_(Items.f_42655_) ? InteractionResult.SUCCESS : InteractionResult.PASS;
      } else {
         return LeadItem.m_42829_(p_53319_, p_53317_, p_53318_);
      }
   }

   public BlockState m_5573_(BlockPlaceContext p_53304_) {
      BlockGetter blockgetter = p_53304_.m_43725_();
      BlockPos blockpos = p_53304_.m_8083_();
      FluidState fluidstate = p_53304_.m_43725_().m_6425_(p_53304_.m_8083_());
      BlockPos blockpos1 = blockpos.m_142127_();
      BlockPos blockpos2 = blockpos.m_142126_();
      BlockPos blockpos3 = blockpos.m_142128_();
      BlockPos blockpos4 = blockpos.m_142125_();
      BlockState blockstate = blockgetter.m_8055_(blockpos1);
      BlockState blockstate1 = blockgetter.m_8055_(blockpos2);
      BlockState blockstate2 = blockgetter.m_8055_(blockpos3);
      BlockState blockstate3 = blockgetter.m_8055_(blockpos4);
      return super.m_5573_(p_53304_).m_61124_(f_52309_, Boolean.valueOf(this.m_53329_(blockstate, blockstate.m_60783_(blockgetter, blockpos1, Direction.SOUTH), Direction.SOUTH))).m_61124_(f_52310_, Boolean.valueOf(this.m_53329_(blockstate1, blockstate1.m_60783_(blockgetter, blockpos2, Direction.WEST), Direction.WEST))).m_61124_(f_52311_, Boolean.valueOf(this.m_53329_(blockstate2, blockstate2.m_60783_(blockgetter, blockpos3, Direction.NORTH), Direction.NORTH))).m_61124_(f_52312_, Boolean.valueOf(this.m_53329_(blockstate3, blockstate3.m_60783_(blockgetter, blockpos4, Direction.EAST), Direction.EAST))).m_61124_(f_52313_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public BlockState m_7417_(BlockState p_53323_, Direction p_53324_, BlockState p_53325_, LevelAccessor p_53326_, BlockPos p_53327_, BlockPos p_53328_) {
      if (p_53323_.m_61143_(f_52313_)) {
         p_53326_.m_6217_().m_5945_(p_53327_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53326_));
      }

      return p_53324_.m_122434_().m_122480_() == Direction.Plane.HORIZONTAL ? p_53323_.m_61124_(f_52314_.get(p_53324_), Boolean.valueOf(this.m_53329_(p_53325_, p_53325_.m_60783_(p_53326_, p_53328_, p_53324_.m_122424_()), p_53324_.m_122424_()))) : super.m_7417_(p_53323_, p_53324_, p_53325_, p_53326_, p_53327_, p_53328_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53334_) {
      p_53334_.m_61104_(f_52309_, f_52310_, f_52312_, f_52311_, f_52313_);
   }
}