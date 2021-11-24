package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallDripleafBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty f_154580_ = BlockStateProperties.f_61362_;
   public static final DirectionProperty f_154577_ = BlockStateProperties.f_61374_;
   protected static final float f_154578_ = 6.0F;
   protected static final VoxelShape f_154579_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public SmallDripleafBlock(BlockBehaviour.Properties p_154583_) {
      super(p_154583_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52858_, DoubleBlockHalf.LOWER).m_61124_(f_154580_, Boolean.valueOf(false)).m_61124_(f_154577_, Direction.NORTH));
   }

   public VoxelShape m_5940_(BlockState p_154610_, BlockGetter p_154611_, BlockPos p_154612_, CollisionContext p_154613_) {
      return f_154579_;
   }

   protected boolean m_6266_(BlockState p_154636_, BlockGetter p_154637_, BlockPos p_154638_) {
      return p_154636_.m_60620_(BlockTags.f_144278_) || p_154637_.m_6425_(p_154638_.m_7494_()).m_164512_(Fluids.f_76193_) && super.m_6266_(p_154636_, p_154637_, p_154638_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154592_) {
      BlockState blockstate = super.m_5573_(p_154592_);
      return blockstate != null ? m_182453_(p_154592_.m_43725_(), p_154592_.m_8083_(), blockstate.m_61124_(f_154577_, p_154592_.m_8125_().m_122424_())) : null;
   }

   public void m_6402_(Level p_154599_, BlockPos p_154600_, BlockState p_154601_, LivingEntity p_154602_, ItemStack p_154603_) {
      if (!p_154599_.m_5776_()) {
         BlockPos blockpos = p_154600_.m_7494_();
         BlockState blockstate = DoublePlantBlock.m_182453_(p_154599_, blockpos, this.m_49966_().m_61124_(f_52858_, DoubleBlockHalf.UPPER).m_61124_(f_154577_, p_154601_.m_61143_(f_154577_)));
         p_154599_.m_7731_(blockpos, blockstate, 3);
      }

   }

   public FluidState m_5888_(BlockState p_154634_) {
      return p_154634_.m_61143_(f_154580_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_154634_);
   }

   public boolean m_7898_(BlockState p_154615_, LevelReader p_154616_, BlockPos p_154617_) {
      if (p_154615_.m_61143_(f_52858_) == DoubleBlockHalf.UPPER) {
         return super.m_7898_(p_154615_, p_154616_, p_154617_);
      } else {
         BlockPos blockpos = p_154617_.m_7495_();
         BlockState blockstate = p_154616_.m_8055_(blockpos);
         return this.m_6266_(blockstate, p_154616_, blockpos);
      }
   }

   public BlockState m_7417_(BlockState p_154625_, Direction p_154626_, BlockState p_154627_, LevelAccessor p_154628_, BlockPos p_154629_, BlockPos p_154630_) {
      if (p_154625_.m_61143_(f_154580_)) {
         p_154628_.m_6217_().m_5945_(p_154629_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154628_));
      }

      return super.m_7417_(p_154625_, p_154626_, p_154627_, p_154628_, p_154629_, p_154630_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154632_) {
      p_154632_.m_61104_(f_52858_, f_154580_, f_154577_);
   }

   public boolean m_7370_(BlockGetter p_154594_, BlockPos p_154595_, BlockState p_154596_, boolean p_154597_) {
      return true;
   }

   public boolean m_5491_(Level p_154605_, Random p_154606_, BlockPos p_154607_, BlockState p_154608_) {
      return true;
   }

   public void m_7719_(ServerLevel p_154587_, Random p_154588_, BlockPos p_154589_, BlockState p_154590_) {
      if (p_154590_.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.LOWER) {
         BlockPos blockpos = p_154589_.m_7494_();
         p_154587_.m_7731_(blockpos, p_154587_.m_6425_(blockpos).m_76188_(), 18);
         BigDripleafBlock.m_152246_(p_154587_, p_154588_, p_154589_, p_154590_.m_61143_(f_154577_));
      } else {
         BlockPos blockpos1 = p_154589_.m_7495_();
         this.m_7719_(p_154587_, p_154588_, blockpos1, p_154587_.m_8055_(blockpos1));
      }

   }

   public BlockState m_6843_(BlockState p_154622_, Rotation p_154623_) {
      return p_154622_.m_61124_(f_154577_, p_154623_.m_55954_(p_154622_.m_61143_(f_154577_)));
   }

   public BlockState m_6943_(BlockState p_154619_, Mirror p_154620_) {
      return p_154619_.m_60717_(p_154620_.m_54846_(p_154619_.m_61143_(f_154577_)));
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XYZ;
   }

   public float m_142627_() {
      return 0.1F;
   }
}