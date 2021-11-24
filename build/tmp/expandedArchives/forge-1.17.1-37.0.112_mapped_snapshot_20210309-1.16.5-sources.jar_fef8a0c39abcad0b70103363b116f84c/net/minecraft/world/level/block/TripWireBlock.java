package net.minecraft.world.level.block;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TripWireBlock extends Block {
   public static final BooleanProperty f_57590_ = BlockStateProperties.f_61448_;
   public static final BooleanProperty f_57591_ = BlockStateProperties.f_61386_;
   public static final BooleanProperty f_57592_ = BlockStateProperties.f_61429_;
   public static final BooleanProperty f_57593_ = PipeBlock.f_55148_;
   public static final BooleanProperty f_57594_ = PipeBlock.f_55149_;
   public static final BooleanProperty f_57595_ = PipeBlock.f_55150_;
   public static final BooleanProperty f_57596_ = PipeBlock.f_55151_;
   private static final Map<Direction, BooleanProperty> f_57599_ = CrossCollisionBlock.f_52314_;
   protected static final VoxelShape f_57597_ = Block.m_49796_(0.0D, 1.0D, 0.0D, 16.0D, 2.5D, 16.0D);
   protected static final VoxelShape f_57598_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   private static final int f_154836_ = 10;
   private final TripWireHookBlock f_57600_;

   public TripWireBlock(TripWireHookBlock p_57603_, BlockBehaviour.Properties p_57604_) {
      super(p_57604_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57590_, Boolean.valueOf(false)).m_61124_(f_57591_, Boolean.valueOf(false)).m_61124_(f_57592_, Boolean.valueOf(false)).m_61124_(f_57593_, Boolean.valueOf(false)).m_61124_(f_57594_, Boolean.valueOf(false)).m_61124_(f_57595_, Boolean.valueOf(false)).m_61124_(f_57596_, Boolean.valueOf(false)));
      this.f_57600_ = p_57603_;
   }

   public VoxelShape m_5940_(BlockState p_57654_, BlockGetter p_57655_, BlockPos p_57656_, CollisionContext p_57657_) {
      return p_57654_.m_61143_(f_57591_) ? f_57597_ : f_57598_;
   }

   public BlockState m_5573_(BlockPlaceContext p_57606_) {
      BlockGetter blockgetter = p_57606_.m_43725_();
      BlockPos blockpos = p_57606_.m_8083_();
      return this.m_49966_().m_61124_(f_57593_, Boolean.valueOf(this.m_57641_(blockgetter.m_8055_(blockpos.m_142127_()), Direction.NORTH))).m_61124_(f_57594_, Boolean.valueOf(this.m_57641_(blockgetter.m_8055_(blockpos.m_142126_()), Direction.EAST))).m_61124_(f_57595_, Boolean.valueOf(this.m_57641_(blockgetter.m_8055_(blockpos.m_142128_()), Direction.SOUTH))).m_61124_(f_57596_, Boolean.valueOf(this.m_57641_(blockgetter.m_8055_(blockpos.m_142125_()), Direction.WEST)));
   }

   public BlockState m_7417_(BlockState p_57645_, Direction p_57646_, BlockState p_57647_, LevelAccessor p_57648_, BlockPos p_57649_, BlockPos p_57650_) {
      return p_57646_.m_122434_().m_122479_() ? p_57645_.m_61124_(f_57599_.get(p_57646_), Boolean.valueOf(this.m_57641_(p_57647_, p_57646_))) : super.m_7417_(p_57645_, p_57646_, p_57647_, p_57648_, p_57649_, p_57650_);
   }

   public void m_6807_(BlockState p_57659_, Level p_57660_, BlockPos p_57661_, BlockState p_57662_, boolean p_57663_) {
      if (!p_57662_.m_60713_(p_57659_.m_60734_())) {
         this.m_57610_(p_57660_, p_57661_, p_57659_);
      }
   }

   public void m_6810_(BlockState p_57630_, Level p_57631_, BlockPos p_57632_, BlockState p_57633_, boolean p_57634_) {
      if (!p_57634_ && !p_57630_.m_60713_(p_57633_.m_60734_())) {
         this.m_57610_(p_57631_, p_57632_, p_57630_.m_61124_(f_57590_, Boolean.valueOf(true)));
      }
   }

   public void m_5707_(Level p_57615_, BlockPos p_57616_, BlockState p_57617_, Player p_57618_) {
      if (!p_57615_.f_46443_ && !p_57618_.m_21205_().m_41619_() && p_57618_.m_21205_().canPerformAction(net.minecraftforge.common.ToolActions.SHEARS_DISARM)) {
         p_57615_.m_7731_(p_57616_, p_57617_.m_61124_(f_57592_, Boolean.valueOf(true)), 4);
         p_57615_.m_142346_(p_57618_, GameEvent.f_157781_, p_57616_);
      }

      super.m_5707_(p_57615_, p_57616_, p_57617_, p_57618_);
   }

   private void m_57610_(Level p_57611_, BlockPos p_57612_, BlockState p_57613_) {
      for(Direction direction : new Direction[]{Direction.SOUTH, Direction.WEST}) {
         for(int i = 1; i < 42; ++i) {
            BlockPos blockpos = p_57612_.m_5484_(direction, i);
            BlockState blockstate = p_57611_.m_8055_(blockpos);
            if (blockstate.m_60713_(this.f_57600_)) {
               if (blockstate.m_61143_(TripWireHookBlock.f_57667_) == direction.m_122424_()) {
                  this.f_57600_.m_57685_(p_57611_, blockpos, blockstate, false, true, i, p_57613_);
               }
               break;
            }

            if (!blockstate.m_60713_(this)) {
               break;
            }
         }
      }

   }

   public void m_7892_(BlockState p_57625_, Level p_57626_, BlockPos p_57627_, Entity p_57628_) {
      if (!p_57626_.f_46443_) {
         if (!p_57625_.m_61143_(f_57590_)) {
            this.m_57607_(p_57626_, p_57627_);
         }
      }
   }

   public void m_7458_(BlockState p_57620_, ServerLevel p_57621_, BlockPos p_57622_, Random p_57623_) {
      if (p_57621_.m_8055_(p_57622_).m_61143_(f_57590_)) {
         this.m_57607_(p_57621_, p_57622_);
      }
   }

   private void m_57607_(Level p_57608_, BlockPos p_57609_) {
      BlockState blockstate = p_57608_.m_8055_(p_57609_);
      boolean flag = blockstate.m_61143_(f_57590_);
      boolean flag1 = false;
      List<? extends Entity> list = p_57608_.m_45933_((Entity)null, blockstate.m_60808_(p_57608_, p_57609_).m_83215_().m_82338_(p_57609_));
      if (!list.isEmpty()) {
         for(Entity entity : list) {
            if (!entity.m_6090_()) {
               flag1 = true;
               break;
            }
         }
      }

      if (flag1 != flag) {
         blockstate = blockstate.m_61124_(f_57590_, Boolean.valueOf(flag1));
         p_57608_.m_7731_(p_57609_, blockstate, 3);
         this.m_57610_(p_57608_, p_57609_, blockstate);
      }

      if (flag1) {
         p_57608_.m_6219_().m_5945_(new BlockPos(p_57609_), this, 10);
      }

   }

   public boolean m_57641_(BlockState p_57642_, Direction p_57643_) {
      if (p_57642_.m_60713_(this.f_57600_)) {
         return p_57642_.m_61143_(TripWireHookBlock.f_57667_) == p_57643_.m_122424_();
      } else {
         return p_57642_.m_60713_(this);
      }
   }

   public BlockState m_6843_(BlockState p_57639_, Rotation p_57640_) {
      switch(p_57640_) {
      case CLOCKWISE_180:
         return p_57639_.m_61124_(f_57593_, p_57639_.m_61143_(f_57595_)).m_61124_(f_57594_, p_57639_.m_61143_(f_57596_)).m_61124_(f_57595_, p_57639_.m_61143_(f_57593_)).m_61124_(f_57596_, p_57639_.m_61143_(f_57594_));
      case COUNTERCLOCKWISE_90:
         return p_57639_.m_61124_(f_57593_, p_57639_.m_61143_(f_57594_)).m_61124_(f_57594_, p_57639_.m_61143_(f_57595_)).m_61124_(f_57595_, p_57639_.m_61143_(f_57596_)).m_61124_(f_57596_, p_57639_.m_61143_(f_57593_));
      case CLOCKWISE_90:
         return p_57639_.m_61124_(f_57593_, p_57639_.m_61143_(f_57596_)).m_61124_(f_57594_, p_57639_.m_61143_(f_57593_)).m_61124_(f_57595_, p_57639_.m_61143_(f_57594_)).m_61124_(f_57596_, p_57639_.m_61143_(f_57595_));
      default:
         return p_57639_;
      }
   }

   public BlockState m_6943_(BlockState p_57636_, Mirror p_57637_) {
      switch(p_57637_) {
      case LEFT_RIGHT:
         return p_57636_.m_61124_(f_57593_, p_57636_.m_61143_(f_57595_)).m_61124_(f_57595_, p_57636_.m_61143_(f_57593_));
      case FRONT_BACK:
         return p_57636_.m_61124_(f_57594_, p_57636_.m_61143_(f_57596_)).m_61124_(f_57596_, p_57636_.m_61143_(f_57594_));
      default:
         return super.m_6943_(p_57636_, p_57637_);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57652_) {
      p_57652_.m_61104_(f_57590_, f_57591_, f_57592_, f_57593_, f_57594_, f_57596_, f_57595_);
   }
}
