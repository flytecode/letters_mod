package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrapDoorBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_57514_ = BlockStateProperties.f_61446_;
   public static final EnumProperty<Half> f_57515_ = BlockStateProperties.f_61402_;
   public static final BooleanProperty f_57516_ = BlockStateProperties.f_61448_;
   public static final BooleanProperty f_57517_ = BlockStateProperties.f_61362_;
   protected static final int f_154832_ = 3;
   protected static final VoxelShape f_57518_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_57519_ = Block.m_49796_(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_57520_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   protected static final VoxelShape f_57521_ = Block.m_49796_(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_57522_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
   protected static final VoxelShape f_57523_ = Block.m_49796_(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);

   public TrapDoorBlock(BlockBehaviour.Properties p_57526_) {
      super(p_57526_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_57514_, Boolean.valueOf(false)).m_61124_(f_57515_, Half.BOTTOM).m_61124_(f_57516_, Boolean.valueOf(false)).m_61124_(f_57517_, Boolean.valueOf(false)));
   }

   public VoxelShape m_5940_(BlockState p_57563_, BlockGetter p_57564_, BlockPos p_57565_, CollisionContext p_57566_) {
      if (!p_57563_.m_61143_(f_57514_)) {
         return p_57563_.m_61143_(f_57515_) == Half.TOP ? f_57523_ : f_57522_;
      } else {
         switch((Direction)p_57563_.m_61143_(f_54117_)) {
         case NORTH:
         default:
            return f_57521_;
         case SOUTH:
            return f_57520_;
         case WEST:
            return f_57519_;
         case EAST:
            return f_57518_;
         }
      }
   }

   public boolean m_7357_(BlockState p_57535_, BlockGetter p_57536_, BlockPos p_57537_, PathComputationType p_57538_) {
      switch(p_57538_) {
      case LAND:
         return p_57535_.m_61143_(f_57514_);
      case WATER:
         return p_57535_.m_61143_(f_57517_);
      case AIR:
         return p_57535_.m_61143_(f_57514_);
      default:
         return false;
      }
   }

   public InteractionResult m_6227_(BlockState p_57540_, Level p_57541_, BlockPos p_57542_, Player p_57543_, InteractionHand p_57544_, BlockHitResult p_57545_) {
      if (this.f_60442_ == Material.f_76279_) {
         return InteractionResult.PASS;
      } else {
         p_57540_ = p_57540_.m_61122_(f_57514_);
         p_57541_.m_7731_(p_57542_, p_57540_, 2);
         if (p_57540_.m_61143_(f_57517_)) {
            p_57541_.m_6217_().m_5945_(p_57542_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_57541_));
         }

         this.m_57527_(p_57543_, p_57541_, p_57542_, p_57540_.m_61143_(f_57514_));
         return InteractionResult.m_19078_(p_57541_.f_46443_);
      }
   }

   protected void m_57527_(@Nullable Player p_57528_, Level p_57529_, BlockPos p_57530_, boolean p_57531_) {
      if (p_57531_) {
         int i = this.f_60442_ == Material.f_76279_ ? 1037 : 1007;
         p_57529_.m_5898_(p_57528_, i, p_57530_, 0);
      } else {
         int j = this.f_60442_ == Material.f_76279_ ? 1036 : 1013;
         p_57529_.m_5898_(p_57528_, j, p_57530_, 0);
      }

      p_57529_.m_142346_(p_57528_, p_57531_ ? GameEvent.f_157796_ : GameEvent.f_157793_, p_57530_);
   }

   public void m_6861_(BlockState p_57547_, Level p_57548_, BlockPos p_57549_, Block p_57550_, BlockPos p_57551_, boolean p_57552_) {
      if (!p_57548_.f_46443_) {
         boolean flag = p_57548_.m_46753_(p_57549_);
         if (flag != p_57547_.m_61143_(f_57516_)) {
            if (p_57547_.m_61143_(f_57514_) != flag) {
               p_57547_ = p_57547_.m_61124_(f_57514_, Boolean.valueOf(flag));
               this.m_57527_((Player)null, p_57548_, p_57549_, flag);
            }

            p_57548_.m_7731_(p_57549_, p_57547_.m_61124_(f_57516_, Boolean.valueOf(flag)), 2);
            if (p_57547_.m_61143_(f_57517_)) {
               p_57548_.m_6217_().m_5945_(p_57549_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_57548_));
            }
         }

      }
   }

   public BlockState m_5573_(BlockPlaceContext p_57533_) {
      BlockState blockstate = this.m_49966_();
      FluidState fluidstate = p_57533_.m_43725_().m_6425_(p_57533_.m_8083_());
      Direction direction = p_57533_.m_43719_();
      if (!p_57533_.m_7058_() && direction.m_122434_().m_122479_()) {
         blockstate = blockstate.m_61124_(f_54117_, direction).m_61124_(f_57515_, p_57533_.m_43720_().f_82480_ - (double)p_57533_.m_8083_().m_123342_() > 0.5D ? Half.TOP : Half.BOTTOM);
      } else {
         blockstate = blockstate.m_61124_(f_54117_, p_57533_.m_8125_().m_122424_()).m_61124_(f_57515_, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
      }

      if (p_57533_.m_43725_().m_46753_(p_57533_.m_8083_())) {
         blockstate = blockstate.m_61124_(f_57514_, Boolean.valueOf(true)).m_61124_(f_57516_, Boolean.valueOf(true));
      }

      return blockstate.m_61124_(f_57517_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57561_) {
      p_57561_.m_61104_(f_54117_, f_57514_, f_57515_, f_57516_, f_57517_);
   }

   public FluidState m_5888_(BlockState p_57568_) {
      return p_57568_.m_61143_(f_57517_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_57568_);
   }

   public BlockState m_7417_(BlockState p_57554_, Direction p_57555_, BlockState p_57556_, LevelAccessor p_57557_, BlockPos p_57558_, BlockPos p_57559_) {
      if (p_57554_.m_61143_(f_57517_)) {
         p_57557_.m_6217_().m_5945_(p_57558_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_57557_));
      }

      return super.m_7417_(p_57554_, p_57555_, p_57556_, p_57557_, p_57558_, p_57559_);
   }

   //Forge Start
   @Override
   public boolean isLadder(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, net.minecraft.world.entity.LivingEntity entity) {
      if (state.m_61143_(f_57514_)) {
         BlockPos downPos = pos.m_7495_();
         BlockState down = world.m_8055_(downPos);
         return down.m_60734_().makesOpenTrapdoorAboveClimbable(down, world, downPos, state);
      }
      return false;
   }
   //Forge End

}
