package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BellBlock extends BaseEntityBlock {
   public static final DirectionProperty f_49679_ = HorizontalDirectionalBlock.f_54117_;
   public static final EnumProperty<BellAttachType> f_49680_ = BlockStateProperties.f_61377_;
   public static final BooleanProperty f_49681_ = BlockStateProperties.f_61448_;
   private static final VoxelShape f_49682_ = Block.m_49796_(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
   private static final VoxelShape f_49683_ = Block.m_49796_(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
   private static final VoxelShape f_49684_ = Block.m_49796_(5.0D, 6.0D, 5.0D, 11.0D, 13.0D, 11.0D);
   private static final VoxelShape f_49685_ = Block.m_49796_(4.0D, 4.0D, 4.0D, 12.0D, 6.0D, 12.0D);
   private static final VoxelShape f_49686_ = Shapes.m_83110_(f_49685_, f_49684_);
   private static final VoxelShape f_49687_ = Shapes.m_83110_(f_49686_, Block.m_49796_(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 16.0D));
   private static final VoxelShape f_49688_ = Shapes.m_83110_(f_49686_, Block.m_49796_(0.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
   private static final VoxelShape f_49689_ = Shapes.m_83110_(f_49686_, Block.m_49796_(0.0D, 13.0D, 7.0D, 13.0D, 15.0D, 9.0D));
   private static final VoxelShape f_49690_ = Shapes.m_83110_(f_49686_, Block.m_49796_(3.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
   private static final VoxelShape f_49691_ = Shapes.m_83110_(f_49686_, Block.m_49796_(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 13.0D));
   private static final VoxelShape f_49692_ = Shapes.m_83110_(f_49686_, Block.m_49796_(7.0D, 13.0D, 3.0D, 9.0D, 15.0D, 16.0D));
   private static final VoxelShape f_49693_ = Shapes.m_83110_(f_49686_, Block.m_49796_(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D));
   public static final int f_152187_ = 1;

   public BellBlock(BlockBehaviour.Properties p_49696_) {
      super(p_49696_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_49679_, Direction.NORTH).m_61124_(f_49680_, BellAttachType.FLOOR).m_61124_(f_49681_, Boolean.valueOf(false)));
   }

   public void m_6861_(BlockState p_49729_, Level p_49730_, BlockPos p_49731_, Block p_49732_, BlockPos p_49733_, boolean p_49734_) {
      boolean flag = p_49730_.m_46753_(p_49731_);
      if (flag != p_49729_.m_61143_(f_49681_)) {
         if (flag) {
            this.m_49712_(p_49730_, p_49731_, (Direction)null);
         }

         p_49730_.m_7731_(p_49731_, p_49729_.m_61124_(f_49681_, Boolean.valueOf(flag)), 3);
      }

   }

   public void m_5581_(Level p_49708_, BlockState p_49709_, BlockHitResult p_49710_, Projectile p_49711_) {
      Entity entity = p_49711_.m_37282_();
      Player player = entity instanceof Player ? (Player)entity : null;
      this.m_49701_(p_49708_, p_49709_, p_49710_, player, true);
   }

   public InteractionResult m_6227_(BlockState p_49722_, Level p_49723_, BlockPos p_49724_, Player p_49725_, InteractionHand p_49726_, BlockHitResult p_49727_) {
      return this.m_49701_(p_49723_, p_49722_, p_49727_, p_49725_, true) ? InteractionResult.m_19078_(p_49723_.f_46443_) : InteractionResult.PASS;
   }

   public boolean m_49701_(Level p_49702_, BlockState p_49703_, BlockHitResult p_49704_, @Nullable Player p_49705_, boolean p_49706_) {
      Direction direction = p_49704_.m_82434_();
      BlockPos blockpos = p_49704_.m_82425_();
      boolean flag = !p_49706_ || this.m_49739_(p_49703_, direction, p_49704_.m_82450_().f_82480_ - (double)blockpos.m_123342_());
      if (flag) {
         boolean flag1 = this.m_152188_(p_49705_, p_49702_, blockpos, direction);
         if (flag1 && p_49705_ != null) {
            p_49705_.m_36220_(Stats.f_12979_);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean m_49739_(BlockState p_49740_, Direction p_49741_, double p_49742_) {
      if (p_49741_.m_122434_() != Direction.Axis.Y && !(p_49742_ > (double)0.8124F)) {
         Direction direction = p_49740_.m_61143_(f_49679_);
         BellAttachType bellattachtype = p_49740_.m_61143_(f_49680_);
         switch(bellattachtype) {
         case FLOOR:
            return direction.m_122434_() == p_49741_.m_122434_();
         case SINGLE_WALL:
         case DOUBLE_WALL:
            return direction.m_122434_() != p_49741_.m_122434_();
         case CEILING:
            return true;
         default:
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_49712_(Level p_49713_, BlockPos p_49714_, @Nullable Direction p_49715_) {
      return this.m_152188_((Entity)null, p_49713_, p_49714_, p_49715_);
   }

   public boolean m_152188_(@Nullable Entity p_152189_, Level p_152190_, BlockPos p_152191_, @Nullable Direction p_152192_) {
      BlockEntity blockentity = p_152190_.m_7702_(p_152191_);
      if (!p_152190_.f_46443_ && blockentity instanceof BellBlockEntity) {
         if (p_152192_ == null) {
            p_152192_ = p_152190_.m_8055_(p_152191_).m_61143_(f_49679_);
         }

         ((BellBlockEntity)blockentity).m_58834_(p_152192_);
         p_152190_.m_5594_((Player)null, p_152191_, SoundEvents.f_11699_, SoundSource.BLOCKS, 2.0F, 1.0F);
         p_152190_.m_142346_(p_152189_, GameEvent.f_157780_, p_152191_);
         return true;
      } else {
         return false;
      }
   }

   private VoxelShape m_49766_(BlockState p_49767_) {
      Direction direction = p_49767_.m_61143_(f_49679_);
      BellAttachType bellattachtype = p_49767_.m_61143_(f_49680_);
      if (bellattachtype == BellAttachType.FLOOR) {
         return direction != Direction.NORTH && direction != Direction.SOUTH ? f_49683_ : f_49682_;
      } else if (bellattachtype == BellAttachType.CEILING) {
         return f_49693_;
      } else if (bellattachtype == BellAttachType.DOUBLE_WALL) {
         return direction != Direction.NORTH && direction != Direction.SOUTH ? f_49688_ : f_49687_;
      } else if (direction == Direction.NORTH) {
         return f_49691_;
      } else if (direction == Direction.SOUTH) {
         return f_49692_;
      } else {
         return direction == Direction.EAST ? f_49690_ : f_49689_;
      }
   }

   public VoxelShape m_5939_(BlockState p_49760_, BlockGetter p_49761_, BlockPos p_49762_, CollisionContext p_49763_) {
      return this.m_49766_(p_49760_);
   }

   public VoxelShape m_5940_(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
      return this.m_49766_(p_49755_);
   }

   public RenderShape m_7514_(BlockState p_49753_) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_49698_) {
      Direction direction = p_49698_.m_43719_();
      BlockPos blockpos = p_49698_.m_8083_();
      Level level = p_49698_.m_43725_();
      Direction.Axis direction$axis = direction.m_122434_();
      if (direction$axis == Direction.Axis.Y) {
         BlockState blockstate = this.m_49966_().m_61124_(f_49680_, direction == Direction.DOWN ? BellAttachType.CEILING : BellAttachType.FLOOR).m_61124_(f_49679_, p_49698_.m_8125_());
         if (blockstate.m_60710_(p_49698_.m_43725_(), blockpos)) {
            return blockstate;
         }
      } else {
         boolean flag = direction$axis == Direction.Axis.X && level.m_8055_(blockpos.m_142125_()).m_60783_(level, blockpos.m_142125_(), Direction.EAST) && level.m_8055_(blockpos.m_142126_()).m_60783_(level, blockpos.m_142126_(), Direction.WEST) || direction$axis == Direction.Axis.Z && level.m_8055_(blockpos.m_142127_()).m_60783_(level, blockpos.m_142127_(), Direction.SOUTH) && level.m_8055_(blockpos.m_142128_()).m_60783_(level, blockpos.m_142128_(), Direction.NORTH);
         BlockState blockstate1 = this.m_49966_().m_61124_(f_49679_, direction.m_122424_()).m_61124_(f_49680_, flag ? BellAttachType.DOUBLE_WALL : BellAttachType.SINGLE_WALL);
         if (blockstate1.m_60710_(p_49698_.m_43725_(), p_49698_.m_8083_())) {
            return blockstate1;
         }

         boolean flag1 = level.m_8055_(blockpos.m_7495_()).m_60783_(level, blockpos.m_7495_(), Direction.UP);
         blockstate1 = blockstate1.m_61124_(f_49680_, flag1 ? BellAttachType.FLOOR : BellAttachType.CEILING);
         if (blockstate1.m_60710_(p_49698_.m_43725_(), p_49698_.m_8083_())) {
            return blockstate1;
         }
      }

      return null;
   }

   public BlockState m_7417_(BlockState p_49744_, Direction p_49745_, BlockState p_49746_, LevelAccessor p_49747_, BlockPos p_49748_, BlockPos p_49749_) {
      BellAttachType bellattachtype = p_49744_.m_61143_(f_49680_);
      Direction direction = m_49768_(p_49744_).m_122424_();
      if (direction == p_49745_ && !p_49744_.m_60710_(p_49747_, p_49748_) && bellattachtype != BellAttachType.DOUBLE_WALL) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_49745_.m_122434_() == p_49744_.m_61143_(f_49679_).m_122434_()) {
            if (bellattachtype == BellAttachType.DOUBLE_WALL && !p_49746_.m_60783_(p_49747_, p_49749_, p_49745_)) {
               return p_49744_.m_61124_(f_49680_, BellAttachType.SINGLE_WALL).m_61124_(f_49679_, p_49745_.m_122424_());
            }

            if (bellattachtype == BellAttachType.SINGLE_WALL && direction.m_122424_() == p_49745_ && p_49746_.m_60783_(p_49747_, p_49749_, p_49744_.m_61143_(f_49679_))) {
               return p_49744_.m_61124_(f_49680_, BellAttachType.DOUBLE_WALL);
            }
         }

         return super.m_7417_(p_49744_, p_49745_, p_49746_, p_49747_, p_49748_, p_49749_);
      }
   }

   public boolean m_7898_(BlockState p_49736_, LevelReader p_49737_, BlockPos p_49738_) {
      Direction direction = m_49768_(p_49736_).m_122424_();
      return direction == Direction.UP ? Block.m_49863_(p_49737_, p_49738_.m_7494_(), Direction.DOWN) : FaceAttachedHorizontalDirectionalBlock.m_53196_(p_49737_, p_49738_, direction);
   }

   private static Direction m_49768_(BlockState p_49769_) {
      switch((BellAttachType)p_49769_.m_61143_(f_49680_)) {
      case FLOOR:
         return Direction.UP;
      case CEILING:
         return Direction.DOWN;
      default:
         return p_49769_.m_61143_(f_49679_).m_122424_();
      }
   }

   public PushReaction m_5537_(BlockState p_49765_) {
      return PushReaction.DESTROY;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49751_) {
      p_49751_.m_61104_(f_49679_, f_49680_, f_49681_);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos p_152198_, BlockState p_152199_) {
      return new BellBlockEntity(p_152198_, p_152199_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152194_, BlockState p_152195_, BlockEntityType<T> p_152196_) {
      return m_152132_(p_152196_, BlockEntityType.f_58909_, p_152194_.f_46443_ ? BellBlockEntity::m_155175_ : BellBlockEntity::m_155202_);
   }

   public boolean m_7357_(BlockState p_49717_, BlockGetter p_49718_, BlockPos p_49719_, PathComputationType p_49720_) {
      return false;
   }
}