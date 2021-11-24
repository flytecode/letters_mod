package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

public class BigDripleafStemBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty f_152325_ = BlockStateProperties.f_61362_;
   private static final int f_152326_ = 6;
   protected static final VoxelShape f_152321_ = Block.m_49796_(5.0D, 0.0D, 9.0D, 11.0D, 16.0D, 15.0D);
   protected static final VoxelShape f_152322_ = Block.m_49796_(5.0D, 0.0D, 1.0D, 11.0D, 16.0D, 7.0D);
   protected static final VoxelShape f_152323_ = Block.m_49796_(1.0D, 0.0D, 5.0D, 7.0D, 16.0D, 11.0D);
   protected static final VoxelShape f_152324_ = Block.m_49796_(9.0D, 0.0D, 5.0D, 15.0D, 16.0D, 11.0D);

   public BigDripleafStemBlock(BlockBehaviour.Properties p_152329_) {
      super(p_152329_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_152325_, Boolean.valueOf(false)).m_61124_(f_54117_, Direction.NORTH));
   }

   public VoxelShape m_5940_(BlockState p_152360_, BlockGetter p_152361_, BlockPos p_152362_, CollisionContext p_152363_) {
      switch((Direction)p_152360_.m_61143_(f_54117_)) {
      case SOUTH:
         return f_152322_;
      case NORTH:
      default:
         return f_152321_;
      case WEST:
         return f_152324_;
      case EAST:
         return f_152323_;
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152376_) {
      p_152376_.m_61104_(f_152325_, f_54117_);
   }

   public FluidState m_5888_(BlockState p_152378_) {
      return p_152378_.m_61143_(f_152325_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_152378_);
   }

   public boolean m_7898_(BlockState p_152365_, LevelReader p_152366_, BlockPos p_152367_) {
      BlockPos blockpos = p_152367_.m_7495_();
      BlockState blockstate = p_152366_.m_8055_(blockpos);
      BlockState blockstate1 = p_152366_.m_8055_(p_152367_.m_7494_());
      return (blockstate.m_60713_(this) || blockstate.m_60783_(p_152366_, blockpos, Direction.UP)) && (blockstate1.m_60713_(this) || blockstate1.m_60713_(Blocks.f_152545_));
   }

   protected static boolean m_152349_(LevelAccessor p_152350_, BlockPos p_152351_, FluidState p_152352_, Direction p_152353_) {
      BlockState blockstate = Blocks.f_152546_.m_49966_().m_61124_(f_152325_, Boolean.valueOf(p_152352_.m_164512_(Fluids.f_76193_))).m_61124_(f_54117_, p_152353_);
      return p_152350_.m_7731_(p_152351_, blockstate, 3);
   }

   public BlockState m_7417_(BlockState p_152369_, Direction p_152370_, BlockState p_152371_, LevelAccessor p_152372_, BlockPos p_152373_, BlockPos p_152374_) {
      if ((p_152370_ == Direction.DOWN || p_152370_ == Direction.UP) && !p_152369_.m_60710_(p_152372_, p_152373_)) {
         p_152372_.m_6219_().m_5945_(p_152373_, this, 1);
      }

      if (p_152369_.m_61143_(f_152325_)) {
         p_152372_.m_6217_().m_5945_(p_152373_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_152372_));
      }

      return super.m_7417_(p_152369_, p_152370_, p_152371_, p_152372_, p_152373_, p_152374_);
   }

   public void m_7458_(BlockState p_152355_, ServerLevel p_152356_, BlockPos p_152357_, Random p_152358_) {
      if (!p_152355_.m_60710_(p_152356_, p_152357_)) {
         p_152356_.m_46961_(p_152357_, true);
      }

   }

   public boolean m_7370_(BlockGetter p_152340_, BlockPos p_152341_, BlockState p_152342_, boolean p_152343_) {
      Optional<BlockPos> optional = BlockUtil.m_177845_(p_152340_, p_152341_, p_152342_.m_60734_(), Direction.UP, Blocks.f_152545_);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos blockpos = optional.get().m_7494_();
         BlockState blockstate = p_152340_.m_8055_(blockpos);
         return BigDripleafBlock.m_152251_(p_152340_, blockpos, blockstate);
      }
   }

   public boolean m_5491_(Level p_152345_, Random p_152346_, BlockPos p_152347_, BlockState p_152348_) {
      return true;
   }

   public void m_7719_(ServerLevel p_152331_, Random p_152332_, BlockPos p_152333_, BlockState p_152334_) {
      Optional<BlockPos> optional = BlockUtil.m_177845_(p_152331_, p_152333_, p_152334_.m_60734_(), Direction.UP, Blocks.f_152545_);
      if (optional.isPresent()) {
         BlockPos blockpos = optional.get();
         BlockPos blockpos1 = blockpos.m_7494_();
         Direction direction = p_152334_.m_61143_(f_54117_);
         m_152349_(p_152331_, blockpos, p_152331_.m_6425_(blockpos), direction);
         BigDripleafBlock.m_152241_(p_152331_, blockpos1, p_152331_.m_6425_(blockpos1), direction);
      }
   }

   public ItemStack m_7397_(BlockGetter p_152336_, BlockPos p_152337_, BlockState p_152338_) {
      return new ItemStack(Blocks.f_152545_);
   }
}