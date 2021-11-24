package net.minecraft.world.level.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TripWireHookBlock extends Block {
   public static final DirectionProperty f_57667_ = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty f_57668_ = BlockStateProperties.f_61448_;
   public static final BooleanProperty f_57669_ = BlockStateProperties.f_61386_;
   protected static final int f_154837_ = 1;
   protected static final int f_154838_ = 42;
   private static final int f_154840_ = 10;
   protected static final int f_154839_ = 3;
   protected static final VoxelShape f_57670_ = Block.m_49796_(5.0D, 0.0D, 10.0D, 11.0D, 10.0D, 16.0D);
   protected static final VoxelShape f_57671_ = Block.m_49796_(5.0D, 0.0D, 0.0D, 11.0D, 10.0D, 6.0D);
   protected static final VoxelShape f_57672_ = Block.m_49796_(10.0D, 0.0D, 5.0D, 16.0D, 10.0D, 11.0D);
   protected static final VoxelShape f_57673_ = Block.m_49796_(0.0D, 0.0D, 5.0D, 6.0D, 10.0D, 11.0D);

   public TripWireHookBlock(BlockBehaviour.Properties p_57676_) {
      super(p_57676_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57667_, Direction.NORTH).m_61124_(f_57668_, Boolean.valueOf(false)).m_61124_(f_57669_, Boolean.valueOf(false)));
   }

   public VoxelShape m_5940_(BlockState p_57740_, BlockGetter p_57741_, BlockPos p_57742_, CollisionContext p_57743_) {
      switch((Direction)p_57740_.m_61143_(f_57667_)) {
      case EAST:
      default:
         return f_57673_;
      case WEST:
         return f_57672_;
      case SOUTH:
         return f_57671_;
      case NORTH:
         return f_57670_;
      }
   }

   public boolean m_7898_(BlockState p_57721_, LevelReader p_57722_, BlockPos p_57723_) {
      Direction direction = p_57721_.m_61143_(f_57667_);
      BlockPos blockpos = p_57723_.m_142300_(direction.m_122424_());
      BlockState blockstate = p_57722_.m_8055_(blockpos);
      return direction.m_122434_().m_122479_() && blockstate.m_60783_(p_57722_, blockpos, direction);
   }

   public BlockState m_7417_(BlockState p_57731_, Direction p_57732_, BlockState p_57733_, LevelAccessor p_57734_, BlockPos p_57735_, BlockPos p_57736_) {
      return p_57732_.m_122424_() == p_57731_.m_61143_(f_57667_) && !p_57731_.m_60710_(p_57734_, p_57735_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_57731_, p_57732_, p_57733_, p_57734_, p_57735_, p_57736_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_57678_) {
      BlockState blockstate = this.m_49966_().m_61124_(f_57668_, Boolean.valueOf(false)).m_61124_(f_57669_, Boolean.valueOf(false));
      LevelReader levelreader = p_57678_.m_43725_();
      BlockPos blockpos = p_57678_.m_8083_();
      Direction[] adirection = p_57678_.m_6232_();

      for(Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = blockstate.m_61124_(f_57667_, direction1);
            if (blockstate.m_60710_(levelreader, blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public void m_6402_(Level p_57680_, BlockPos p_57681_, BlockState p_57682_, LivingEntity p_57683_, ItemStack p_57684_) {
      this.m_57685_(p_57680_, p_57681_, p_57682_, false, false, -1, (BlockState)null);
   }

   public void m_57685_(Level p_57686_, BlockPos p_57687_, BlockState p_57688_, boolean p_57689_, boolean p_57690_, int p_57691_, @Nullable BlockState p_57692_) {
      Direction direction = p_57688_.m_61143_(f_57667_);
      boolean flag = p_57688_.m_61143_(f_57669_);
      boolean flag1 = p_57688_.m_61143_(f_57668_);
      boolean flag2 = !p_57689_;
      boolean flag3 = false;
      int i = 0;
      BlockState[] ablockstate = new BlockState[42];

      for(int j = 1; j < 42; ++j) {
         BlockPos blockpos = p_57687_.m_5484_(direction, j);
         BlockState blockstate = p_57686_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50266_)) {
            if (blockstate.m_61143_(f_57667_) == direction.m_122424_()) {
               i = j;
            }
            break;
         }

         if (!blockstate.m_60713_(Blocks.f_50267_) && j != p_57691_) {
            ablockstate[j] = null;
            flag2 = false;
         } else {
            if (j == p_57691_) {
               blockstate = MoreObjects.firstNonNull(p_57692_, blockstate);
            }

            boolean flag4 = !blockstate.m_61143_(TripWireBlock.f_57592_);
            boolean flag5 = blockstate.m_61143_(TripWireBlock.f_57590_);
            flag3 |= flag4 && flag5;
            ablockstate[j] = blockstate;
            if (j == p_57691_) {
               p_57686_.m_6219_().m_5945_(p_57687_, this, 10);
               flag2 &= flag4;
            }
         }
      }

      flag2 = flag2 & i > 1;
      flag3 = flag3 & flag2;
      BlockState blockstate1 = this.m_49966_().m_61124_(f_57669_, Boolean.valueOf(flag2)).m_61124_(f_57668_, Boolean.valueOf(flag3));
      if (i > 0) {
         BlockPos blockpos1 = p_57687_.m_5484_(direction, i);
         Direction direction1 = direction.m_122424_();
         p_57686_.m_7731_(blockpos1, blockstate1.m_61124_(f_57667_, direction1), 3);
         this.m_57693_(p_57686_, blockpos1, direction1);
         this.m_57697_(p_57686_, blockpos1, flag2, flag3, flag, flag1);
      }

      this.m_57697_(p_57686_, p_57687_, flag2, flag3, flag, flag1);
      if (!p_57689_) {
         p_57686_.m_7731_(p_57687_, blockstate1.m_61124_(f_57667_, direction), 3);
         if (p_57690_) {
            this.m_57693_(p_57686_, p_57687_, direction);
         }
      }

      if (flag != flag2) {
         for(int k = 1; k < i; ++k) {
            BlockPos blockpos2 = p_57687_.m_5484_(direction, k);
            BlockState blockstate2 = ablockstate[k];
            if (blockstate2 != null) {
               if (!p_57686_.m_8055_(blockpos2).m_60795_()) { // FORGE: fix MC-129055
               p_57686_.m_7731_(blockpos2, blockstate2.m_61124_(f_57669_, Boolean.valueOf(flag2)), 3);
               }
            }
         }
      }

   }

   public void m_7458_(BlockState p_57705_, ServerLevel p_57706_, BlockPos p_57707_, Random p_57708_) {
      this.m_57685_(p_57706_, p_57707_, p_57705_, false, true, -1, (BlockState)null);
   }

   private void m_57697_(Level p_57698_, BlockPos p_57699_, boolean p_57700_, boolean p_57701_, boolean p_57702_, boolean p_57703_) {
      if (p_57701_ && !p_57703_) {
         p_57698_.m_5594_((Player)null, p_57699_, SoundEvents.f_12524_, SoundSource.BLOCKS, 0.4F, 0.6F);
         p_57698_.m_151555_(GameEvent.f_157798_, p_57699_);
      } else if (!p_57701_ && p_57703_) {
         p_57698_.m_5594_((Player)null, p_57699_, SoundEvents.f_12523_, SoundSource.BLOCKS, 0.4F, 0.5F);
         p_57698_.m_151555_(GameEvent.f_157800_, p_57699_);
      } else if (p_57700_ && !p_57702_) {
         p_57698_.m_5594_((Player)null, p_57699_, SoundEvents.f_12522_, SoundSource.BLOCKS, 0.4F, 0.7F);
         p_57698_.m_151555_(GameEvent.f_157791_, p_57699_);
      } else if (!p_57700_ && p_57702_) {
         p_57698_.m_5594_((Player)null, p_57699_, SoundEvents.f_12525_, SoundSource.BLOCKS, 0.4F, 1.2F / (p_57698_.f_46441_.nextFloat() * 0.2F + 0.9F));
         p_57698_.m_151555_(GameEvent.f_157795_, p_57699_);
      }

   }

   private void m_57693_(Level p_57694_, BlockPos p_57695_, Direction p_57696_) {
      p_57694_.m_46672_(p_57695_, this);
      p_57694_.m_46672_(p_57695_.m_142300_(p_57696_.m_122424_()), this);
   }

   public void m_6810_(BlockState p_57715_, Level p_57716_, BlockPos p_57717_, BlockState p_57718_, boolean p_57719_) {
      if (!p_57719_ && !p_57715_.m_60713_(p_57718_.m_60734_())) {
         boolean flag = p_57715_.m_61143_(f_57669_);
         boolean flag1 = p_57715_.m_61143_(f_57668_);
         if (flag || flag1) {
            this.m_57685_(p_57716_, p_57717_, p_57715_, true, false, -1, (BlockState)null);
         }

         if (flag1) {
            p_57716_.m_46672_(p_57717_, this);
            p_57716_.m_46672_(p_57717_.m_142300_(p_57715_.m_61143_(f_57667_).m_122424_()), this);
         }

         super.m_6810_(p_57715_, p_57716_, p_57717_, p_57718_, p_57719_);
      }
   }

   public int m_6378_(BlockState p_57710_, BlockGetter p_57711_, BlockPos p_57712_, Direction p_57713_) {
      return p_57710_.m_61143_(f_57668_) ? 15 : 0;
   }

   public int m_6376_(BlockState p_57745_, BlockGetter p_57746_, BlockPos p_57747_, Direction p_57748_) {
      if (!p_57745_.m_61143_(f_57668_)) {
         return 0;
      } else {
         return p_57745_.m_61143_(f_57667_) == p_57748_ ? 15 : 0;
      }
   }

   public boolean m_7899_(BlockState p_57750_) {
      return true;
   }

   public BlockState m_6843_(BlockState p_57728_, Rotation p_57729_) {
      return p_57728_.m_61124_(f_57667_, p_57729_.m_55954_(p_57728_.m_61143_(f_57667_)));
   }

   public BlockState m_6943_(BlockState p_57725_, Mirror p_57726_) {
      return p_57725_.m_60717_(p_57726_.m_54846_(p_57725_.m_61143_(f_57667_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57738_) {
      p_57738_.m_61104_(f_57667_, f_57668_, f_57669_);
   }
}
