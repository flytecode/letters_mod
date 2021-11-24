package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoorBlock extends Block {
   public static final DirectionProperty f_52726_ = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty f_52727_ = BlockStateProperties.f_61446_;
   public static final EnumProperty<DoorHingeSide> f_52728_ = BlockStateProperties.f_61394_;
   public static final BooleanProperty f_52729_ = BlockStateProperties.f_61448_;
   public static final EnumProperty<DoubleBlockHalf> f_52730_ = BlockStateProperties.f_61401_;
   protected static final float f_153164_ = 3.0F;
   protected static final VoxelShape f_52731_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   protected static final VoxelShape f_52732_ = Block.m_49796_(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_52733_ = Block.m_49796_(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_52734_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

   public DoorBlock(BlockBehaviour.Properties p_52737_) {
      super(p_52737_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52726_, Direction.NORTH).m_61124_(f_52727_, Boolean.valueOf(false)).m_61124_(f_52728_, DoorHingeSide.LEFT).m_61124_(f_52729_, Boolean.valueOf(false)).m_61124_(f_52730_, DoubleBlockHalf.LOWER));
   }

   public VoxelShape m_5940_(BlockState p_52807_, BlockGetter p_52808_, BlockPos p_52809_, CollisionContext p_52810_) {
      Direction direction = p_52807_.m_61143_(f_52726_);
      boolean flag = !p_52807_.m_61143_(f_52727_);
      boolean flag1 = p_52807_.m_61143_(f_52728_) == DoorHingeSide.RIGHT;
      switch(direction) {
      case EAST:
      default:
         return flag ? f_52734_ : (flag1 ? f_52732_ : f_52731_);
      case SOUTH:
         return flag ? f_52731_ : (flag1 ? f_52734_ : f_52733_);
      case WEST:
         return flag ? f_52733_ : (flag1 ? f_52731_ : f_52732_);
      case NORTH:
         return flag ? f_52732_ : (flag1 ? f_52733_ : f_52734_);
      }
   }

   public BlockState m_7417_(BlockState p_52796_, Direction p_52797_, BlockState p_52798_, LevelAccessor p_52799_, BlockPos p_52800_, BlockPos p_52801_) {
      DoubleBlockHalf doubleblockhalf = p_52796_.m_61143_(f_52730_);
      if (p_52797_.m_122434_() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (p_52797_ == Direction.UP)) {
         return p_52798_.m_60713_(this) && p_52798_.m_61143_(f_52730_) != doubleblockhalf ? p_52796_.m_61124_(f_52726_, p_52798_.m_61143_(f_52726_)).m_61124_(f_52727_, p_52798_.m_61143_(f_52727_)).m_61124_(f_52728_, p_52798_.m_61143_(f_52728_)).m_61124_(f_52729_, p_52798_.m_61143_(f_52729_)) : Blocks.f_50016_.m_49966_();
      } else {
         return doubleblockhalf == DoubleBlockHalf.LOWER && p_52797_ == Direction.DOWN && !p_52796_.m_60710_(p_52799_, p_52800_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_52796_, p_52797_, p_52798_, p_52799_, p_52800_, p_52801_);
      }
   }

   public void m_5707_(Level p_52755_, BlockPos p_52756_, BlockState p_52757_, Player p_52758_) {
      if (!p_52755_.f_46443_ && p_52758_.m_7500_()) {
         DoublePlantBlock.m_52903_(p_52755_, p_52756_, p_52757_, p_52758_);
      }

      super.m_5707_(p_52755_, p_52756_, p_52757_, p_52758_);
   }

   public boolean m_7357_(BlockState p_52764_, BlockGetter p_52765_, BlockPos p_52766_, PathComputationType p_52767_) {
      switch(p_52767_) {
      case LAND:
         return p_52764_.m_61143_(f_52727_);
      case WATER:
         return false;
      case AIR:
         return p_52764_.m_61143_(f_52727_);
      default:
         return false;
      }
   }

   private int m_52811_() {
      return this.f_60442_ == Material.f_76279_ ? 1011 : 1012;
   }

   private int m_52812_() {
      return this.f_60442_ == Material.f_76279_ ? 1005 : 1006;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_52739_) {
      BlockPos blockpos = p_52739_.m_8083_();
      Level level = p_52739_.m_43725_();
      if (blockpos.m_123342_() < level.m_151558_() - 1 && level.m_8055_(blockpos.m_7494_()).m_60629_(p_52739_)) {
         boolean flag = level.m_46753_(blockpos) || level.m_46753_(blockpos.m_7494_());
         return this.m_49966_().m_61124_(f_52726_, p_52739_.m_8125_()).m_61124_(f_52728_, this.m_52804_(p_52739_)).m_61124_(f_52729_, Boolean.valueOf(flag)).m_61124_(f_52727_, Boolean.valueOf(flag)).m_61124_(f_52730_, DoubleBlockHalf.LOWER);
      } else {
         return null;
      }
   }

   public void m_6402_(Level p_52749_, BlockPos p_52750_, BlockState p_52751_, LivingEntity p_52752_, ItemStack p_52753_) {
      p_52749_.m_7731_(p_52750_.m_7494_(), p_52751_.m_61124_(f_52730_, DoubleBlockHalf.UPPER), 3);
   }

   private DoorHingeSide m_52804_(BlockPlaceContext p_52805_) {
      BlockGetter blockgetter = p_52805_.m_43725_();
      BlockPos blockpos = p_52805_.m_8083_();
      Direction direction = p_52805_.m_8125_();
      BlockPos blockpos1 = blockpos.m_7494_();
      Direction direction1 = direction.m_122428_();
      BlockPos blockpos2 = blockpos.m_142300_(direction1);
      BlockState blockstate = blockgetter.m_8055_(blockpos2);
      BlockPos blockpos3 = blockpos1.m_142300_(direction1);
      BlockState blockstate1 = blockgetter.m_8055_(blockpos3);
      Direction direction2 = direction.m_122427_();
      BlockPos blockpos4 = blockpos.m_142300_(direction2);
      BlockState blockstate2 = blockgetter.m_8055_(blockpos4);
      BlockPos blockpos5 = blockpos1.m_142300_(direction2);
      BlockState blockstate3 = blockgetter.m_8055_(blockpos5);
      int i = (blockstate.m_60838_(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.m_60838_(blockgetter, blockpos3) ? -1 : 0) + (blockstate2.m_60838_(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.m_60838_(blockgetter, blockpos5) ? 1 : 0);
      boolean flag = blockstate.m_60713_(this) && blockstate.m_61143_(f_52730_) == DoubleBlockHalf.LOWER;
      boolean flag1 = blockstate2.m_60713_(this) && blockstate2.m_61143_(f_52730_) == DoubleBlockHalf.LOWER;
      if ((!flag || flag1) && i <= 0) {
         if ((!flag1 || flag) && i >= 0) {
            int j = direction.m_122429_();
            int k = direction.m_122431_();
            Vec3 vec3 = p_52805_.m_43720_();
            double d0 = vec3.f_82479_ - (double)blockpos.m_123341_();
            double d1 = vec3.f_82481_ - (double)blockpos.m_123343_();
            return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
         } else {
            return DoorHingeSide.LEFT;
         }
      } else {
         return DoorHingeSide.RIGHT;
      }
   }

   public InteractionResult m_6227_(BlockState p_52769_, Level p_52770_, BlockPos p_52771_, Player p_52772_, InteractionHand p_52773_, BlockHitResult p_52774_) {
      if (this.f_60442_ == Material.f_76279_) {
         return InteractionResult.PASS;
      } else {
         p_52769_ = p_52769_.m_61122_(f_52727_);
         p_52770_.m_7731_(p_52771_, p_52769_, 10);
         p_52770_.m_5898_(p_52772_, p_52769_.m_61143_(f_52727_) ? this.m_52812_() : this.m_52811_(), p_52771_, 0);
         p_52770_.m_142346_(p_52772_, this.m_52815_(p_52769_) ? GameEvent.f_157796_ : GameEvent.f_157793_, p_52771_);
         return InteractionResult.m_19078_(p_52770_.f_46443_);
      }
   }

   public boolean m_52815_(BlockState p_52816_) {
      return p_52816_.m_61143_(f_52727_);
   }

   public void m_153165_(@Nullable Entity p_153166_, Level p_153167_, BlockState p_153168_, BlockPos p_153169_, boolean p_153170_) {
      if (p_153168_.m_60713_(this) && p_153168_.m_61143_(f_52727_) != p_153170_) {
         p_153167_.m_7731_(p_153169_, p_153168_.m_61124_(f_52727_, Boolean.valueOf(p_153170_)), 10);
         this.m_52759_(p_153167_, p_153169_, p_153170_);
         p_153167_.m_142346_(p_153166_, p_153170_ ? GameEvent.f_157796_ : GameEvent.f_157793_, p_153169_);
      }
   }

   public void m_6861_(BlockState p_52776_, Level p_52777_, BlockPos p_52778_, Block p_52779_, BlockPos p_52780_, boolean p_52781_) {
      boolean flag = p_52777_.m_46753_(p_52778_) || p_52777_.m_46753_(p_52778_.m_142300_(p_52776_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
      if (!this.m_49966_().m_60713_(p_52779_) && flag != p_52776_.m_61143_(f_52729_)) {
         if (flag != p_52776_.m_61143_(f_52727_)) {
            this.m_52759_(p_52777_, p_52778_, flag);
            p_52777_.m_151555_(flag ? GameEvent.f_157796_ : GameEvent.f_157793_, p_52778_);
         }

         p_52777_.m_7731_(p_52778_, p_52776_.m_61124_(f_52729_, Boolean.valueOf(flag)).m_61124_(f_52727_, Boolean.valueOf(flag)), 2);
      }

   }

   public boolean m_7898_(BlockState p_52783_, LevelReader p_52784_, BlockPos p_52785_) {
      BlockPos blockpos = p_52785_.m_7495_();
      BlockState blockstate = p_52784_.m_8055_(blockpos);
      return p_52783_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER ? blockstate.m_60783_(p_52784_, blockpos, Direction.UP) : blockstate.m_60713_(this);
   }

   private void m_52759_(Level p_52760_, BlockPos p_52761_, boolean p_52762_) {
      p_52760_.m_5898_((Player)null, p_52762_ ? this.m_52812_() : this.m_52811_(), p_52761_, 0);
   }

   public PushReaction m_5537_(BlockState p_52814_) {
      return PushReaction.DESTROY;
   }

   public BlockState m_6843_(BlockState p_52790_, Rotation p_52791_) {
      return p_52790_.m_61124_(f_52726_, p_52791_.m_55954_(p_52790_.m_61143_(f_52726_)));
   }

   public BlockState m_6943_(BlockState p_52787_, Mirror p_52788_) {
      return p_52788_ == Mirror.NONE ? p_52787_ : p_52787_.m_60717_(p_52788_.m_54846_(p_52787_.m_61143_(f_52726_))).m_61122_(f_52728_);
   }

   public long m_7799_(BlockState p_52793_, BlockPos p_52794_) {
      return Mth.m_14130_(p_52794_.m_123341_(), p_52794_.m_6625_(p_52793_.m_61143_(f_52730_) == DoubleBlockHalf.LOWER ? 0 : 1).m_123342_(), p_52794_.m_123343_());
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52803_) {
      p_52803_.m_61104_(f_52730_, f_52726_, f_52727_, f_52728_, f_52729_);
   }

   public static boolean m_52745_(Level p_52746_, BlockPos p_52747_) {
      return m_52817_(p_52746_.m_8055_(p_52747_));
   }

   public static boolean m_52817_(BlockState p_52818_) {
      return p_52818_.m_60734_() instanceof DoorBlock && (p_52818_.m_60767_() == Material.f_76320_ || p_52818_.m_60767_() == Material.f_76321_);
   }
}