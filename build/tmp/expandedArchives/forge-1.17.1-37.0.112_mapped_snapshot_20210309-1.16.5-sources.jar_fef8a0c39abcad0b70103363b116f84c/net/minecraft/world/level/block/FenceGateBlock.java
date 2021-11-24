package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceGateBlock extends HorizontalDirectionalBlock {
   public static final BooleanProperty f_53341_ = BlockStateProperties.f_61446_;
   public static final BooleanProperty f_53342_ = BlockStateProperties.f_61448_;
   public static final BooleanProperty f_53343_ = BlockStateProperties.f_61442_;
   protected static final VoxelShape f_53344_ = Block.m_49796_(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_53345_ = Block.m_49796_(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_53346_ = Block.m_49796_(0.0D, 0.0D, 6.0D, 16.0D, 13.0D, 10.0D);
   protected static final VoxelShape f_53347_ = Block.m_49796_(6.0D, 0.0D, 0.0D, 10.0D, 13.0D, 16.0D);
   protected static final VoxelShape f_53348_ = Block.m_49796_(0.0D, 0.0D, 6.0D, 16.0D, 24.0D, 10.0D);
   protected static final VoxelShape f_53349_ = Block.m_49796_(6.0D, 0.0D, 0.0D, 10.0D, 24.0D, 16.0D);
   protected static final VoxelShape f_53350_ = Shapes.m_83110_(Block.m_49796_(0.0D, 5.0D, 7.0D, 2.0D, 16.0D, 9.0D), Block.m_49796_(14.0D, 5.0D, 7.0D, 16.0D, 16.0D, 9.0D));
   protected static final VoxelShape f_53351_ = Shapes.m_83110_(Block.m_49796_(7.0D, 5.0D, 0.0D, 9.0D, 16.0D, 2.0D), Block.m_49796_(7.0D, 5.0D, 14.0D, 9.0D, 16.0D, 16.0D));
   protected static final VoxelShape f_53352_ = Shapes.m_83110_(Block.m_49796_(0.0D, 2.0D, 7.0D, 2.0D, 13.0D, 9.0D), Block.m_49796_(14.0D, 2.0D, 7.0D, 16.0D, 13.0D, 9.0D));
   protected static final VoxelShape f_53353_ = Shapes.m_83110_(Block.m_49796_(7.0D, 2.0D, 0.0D, 9.0D, 13.0D, 2.0D), Block.m_49796_(7.0D, 2.0D, 14.0D, 9.0D, 13.0D, 16.0D));

   public FenceGateBlock(BlockBehaviour.Properties p_53356_) {
      super(p_53356_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53341_, Boolean.valueOf(false)).m_61124_(f_53342_, Boolean.valueOf(false)).m_61124_(f_53343_, Boolean.valueOf(false)));
   }

   public VoxelShape m_5940_(BlockState p_53391_, BlockGetter p_53392_, BlockPos p_53393_, CollisionContext p_53394_) {
      if (p_53391_.m_61143_(f_53343_)) {
         return p_53391_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53347_ : f_53346_;
      } else {
         return p_53391_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53345_ : f_53344_;
      }
   }

   public BlockState m_7417_(BlockState p_53382_, Direction p_53383_, BlockState p_53384_, LevelAccessor p_53385_, BlockPos p_53386_, BlockPos p_53387_) {
      Direction.Axis direction$axis = p_53383_.m_122434_();
      if (p_53382_.m_61143_(f_54117_).m_122427_().m_122434_() != direction$axis) {
         return super.m_7417_(p_53382_, p_53383_, p_53384_, p_53385_, p_53386_, p_53387_);
      } else {
         boolean flag = this.m_53404_(p_53384_) || this.m_53404_(p_53385_.m_8055_(p_53386_.m_142300_(p_53383_.m_122424_())));
         return p_53382_.m_61124_(f_53343_, Boolean.valueOf(flag));
      }
   }

   public VoxelShape m_5939_(BlockState p_53396_, BlockGetter p_53397_, BlockPos p_53398_, CollisionContext p_53399_) {
      if (p_53396_.m_61143_(f_53341_)) {
         return Shapes.m_83040_();
      } else {
         return p_53396_.m_61143_(f_54117_).m_122434_() == Direction.Axis.Z ? f_53348_ : f_53349_;
      }
   }

   public VoxelShape m_7952_(BlockState p_53401_, BlockGetter p_53402_, BlockPos p_53403_) {
      if (p_53401_.m_61143_(f_53343_)) {
         return p_53401_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53353_ : f_53352_;
      } else {
         return p_53401_.m_61143_(f_54117_).m_122434_() == Direction.Axis.X ? f_53351_ : f_53350_;
      }
   }

   public boolean m_7357_(BlockState p_53360_, BlockGetter p_53361_, BlockPos p_53362_, PathComputationType p_53363_) {
      switch(p_53363_) {
      case LAND:
         return p_53360_.m_61143_(f_53341_);
      case WATER:
         return false;
      case AIR:
         return p_53360_.m_61143_(f_53341_);
      default:
         return false;
      }
   }

   public BlockState m_5573_(BlockPlaceContext p_53358_) {
      Level level = p_53358_.m_43725_();
      BlockPos blockpos = p_53358_.m_8083_();
      boolean flag = level.m_46753_(blockpos);
      Direction direction = p_53358_.m_8125_();
      Direction.Axis direction$axis = direction.m_122434_();
      boolean flag1 = direction$axis == Direction.Axis.Z && (this.m_53404_(level.m_8055_(blockpos.m_142125_())) || this.m_53404_(level.m_8055_(blockpos.m_142126_()))) || direction$axis == Direction.Axis.X && (this.m_53404_(level.m_8055_(blockpos.m_142127_())) || this.m_53404_(level.m_8055_(blockpos.m_142128_())));
      return this.m_49966_().m_61124_(f_54117_, direction).m_61124_(f_53341_, Boolean.valueOf(flag)).m_61124_(f_53342_, Boolean.valueOf(flag)).m_61124_(f_53343_, Boolean.valueOf(flag1));
   }

   private boolean m_53404_(BlockState p_53405_) {
      return p_53405_.m_60620_(BlockTags.f_13032_);
   }

   public InteractionResult m_6227_(BlockState p_53365_, Level p_53366_, BlockPos p_53367_, Player p_53368_, InteractionHand p_53369_, BlockHitResult p_53370_) {
      if (p_53365_.m_61143_(f_53341_)) {
         p_53365_ = p_53365_.m_61124_(f_53341_, Boolean.valueOf(false));
         p_53366_.m_7731_(p_53367_, p_53365_, 10);
      } else {
         Direction direction = p_53368_.m_6350_();
         if (p_53365_.m_61143_(f_54117_) == direction.m_122424_()) {
            p_53365_ = p_53365_.m_61124_(f_54117_, direction);
         }

         p_53365_ = p_53365_.m_61124_(f_53341_, Boolean.valueOf(true));
         p_53366_.m_7731_(p_53367_, p_53365_, 10);
      }

      boolean flag = p_53365_.m_61143_(f_53341_);
      p_53366_.m_5898_(p_53368_, flag ? 1008 : 1014, p_53367_, 0);
      p_53366_.m_142346_(p_53368_, flag ? GameEvent.f_157796_ : GameEvent.f_157793_, p_53367_);
      return InteractionResult.m_19078_(p_53366_.f_46443_);
   }

   public void m_6861_(BlockState p_53372_, Level p_53373_, BlockPos p_53374_, Block p_53375_, BlockPos p_53376_, boolean p_53377_) {
      if (!p_53373_.f_46443_) {
         boolean flag = p_53373_.m_46753_(p_53374_);
         if (p_53372_.m_61143_(f_53342_) != flag) {
            p_53373_.m_7731_(p_53374_, p_53372_.m_61124_(f_53342_, Boolean.valueOf(flag)).m_61124_(f_53341_, Boolean.valueOf(flag)), 2);
            if (p_53372_.m_61143_(f_53341_) != flag) {
               p_53373_.m_5898_((Player)null, flag ? 1008 : 1014, p_53374_, 0);
               p_53373_.m_151555_(flag ? GameEvent.f_157796_ : GameEvent.f_157793_, p_53374_);
            }
         }

      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53389_) {
      p_53389_.m_61104_(f_54117_, f_53341_, f_53342_, f_53343_);
   }

   public static boolean m_53378_(BlockState p_53379_, Direction p_53380_) {
      return p_53379_.m_61143_(f_54117_).m_122434_() == p_53380_.m_122427_().m_122434_();
   }
}