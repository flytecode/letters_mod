package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ButtonBlock extends FaceAttachedHorizontalDirectionalBlock {
   public static final BooleanProperty f_51045_ = BlockStateProperties.f_61448_;
   private static final int f_152736_ = 1;
   private static final int f_152737_ = 2;
   protected static final int f_152738_ = 2;
   protected static final int f_152739_ = 3;
   protected static final VoxelShape f_51046_ = Block.m_49796_(6.0D, 14.0D, 5.0D, 10.0D, 16.0D, 11.0D);
   protected static final VoxelShape f_51047_ = Block.m_49796_(5.0D, 14.0D, 6.0D, 11.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_51048_ = Block.m_49796_(6.0D, 0.0D, 5.0D, 10.0D, 2.0D, 11.0D);
   protected static final VoxelShape f_51049_ = Block.m_49796_(5.0D, 0.0D, 6.0D, 11.0D, 2.0D, 10.0D);
   protected static final VoxelShape f_51050_ = Block.m_49796_(5.0D, 6.0D, 14.0D, 11.0D, 10.0D, 16.0D);
   protected static final VoxelShape f_51051_ = Block.m_49796_(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 2.0D);
   protected static final VoxelShape f_51052_ = Block.m_49796_(14.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
   protected static final VoxelShape f_51053_ = Block.m_49796_(0.0D, 6.0D, 5.0D, 2.0D, 10.0D, 11.0D);
   protected static final VoxelShape f_51054_ = Block.m_49796_(6.0D, 15.0D, 5.0D, 10.0D, 16.0D, 11.0D);
   protected static final VoxelShape f_51055_ = Block.m_49796_(5.0D, 15.0D, 6.0D, 11.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_51056_ = Block.m_49796_(6.0D, 0.0D, 5.0D, 10.0D, 1.0D, 11.0D);
   protected static final VoxelShape f_51057_ = Block.m_49796_(5.0D, 0.0D, 6.0D, 11.0D, 1.0D, 10.0D);
   protected static final VoxelShape f_51058_ = Block.m_49796_(5.0D, 6.0D, 15.0D, 11.0D, 10.0D, 16.0D);
   protected static final VoxelShape f_51059_ = Block.m_49796_(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 1.0D);
   protected static final VoxelShape f_51060_ = Block.m_49796_(15.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
   protected static final VoxelShape f_51061_ = Block.m_49796_(0.0D, 6.0D, 5.0D, 1.0D, 10.0D, 11.0D);
   private final boolean f_51062_;

   protected ButtonBlock(boolean p_51065_, BlockBehaviour.Properties p_51066_) {
      super(p_51066_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_51045_, Boolean.valueOf(false)).m_61124_(f_53179_, AttachFace.WALL));
      this.f_51062_ = p_51065_;
   }

   private int m_51115_() {
      return this.f_51062_ ? 30 : 20;
   }

   public VoxelShape m_5940_(BlockState p_51104_, BlockGetter p_51105_, BlockPos p_51106_, CollisionContext p_51107_) {
      Direction direction = p_51104_.m_61143_(f_54117_);
      boolean flag = p_51104_.m_61143_(f_51045_);
      switch((AttachFace)p_51104_.m_61143_(f_53179_)) {
      case FLOOR:
         if (direction.m_122434_() == Direction.Axis.X) {
            return flag ? f_51056_ : f_51048_;
         }

         return flag ? f_51057_ : f_51049_;
      case WALL:
         switch(direction) {
         case EAST:
            return flag ? f_51061_ : f_51053_;
         case WEST:
            return flag ? f_51060_ : f_51052_;
         case SOUTH:
            return flag ? f_51059_ : f_51051_;
         case NORTH:
         default:
            return flag ? f_51058_ : f_51050_;
         }
      case CEILING:
      default:
         if (direction.m_122434_() == Direction.Axis.X) {
            return flag ? f_51054_ : f_51046_;
         } else {
            return flag ? f_51055_ : f_51047_;
         }
      }
   }

   public InteractionResult m_6227_(BlockState p_51088_, Level p_51089_, BlockPos p_51090_, Player p_51091_, InteractionHand p_51092_, BlockHitResult p_51093_) {
      if (p_51088_.m_61143_(f_51045_)) {
         return InteractionResult.CONSUME;
      } else {
         this.m_51116_(p_51088_, p_51089_, p_51090_);
         this.m_51067_(p_51091_, p_51089_, p_51090_, true);
         p_51089_.m_142346_(p_51091_, GameEvent.f_157798_, p_51090_);
         return InteractionResult.m_19078_(p_51089_.f_46443_);
      }
   }

   public void m_51116_(BlockState p_51117_, Level p_51118_, BlockPos p_51119_) {
      p_51118_.m_7731_(p_51119_, p_51117_.m_61124_(f_51045_, Boolean.valueOf(true)), 3);
      this.m_51124_(p_51117_, p_51118_, p_51119_);
      p_51118_.m_6219_().m_5945_(p_51119_, this, this.m_51115_());
   }

   protected void m_51067_(@Nullable Player p_51068_, LevelAccessor p_51069_, BlockPos p_51070_, boolean p_51071_) {
      p_51069_.m_5594_(p_51071_ ? p_51068_ : null, p_51070_, this.m_5722_(p_51071_), SoundSource.BLOCKS, 0.3F, p_51071_ ? 0.6F : 0.5F);
   }

   protected abstract SoundEvent m_5722_(boolean p_51102_);

   public void m_6810_(BlockState p_51095_, Level p_51096_, BlockPos p_51097_, BlockState p_51098_, boolean p_51099_) {
      if (!p_51099_ && !p_51095_.m_60713_(p_51098_.m_60734_())) {
         if (p_51095_.m_61143_(f_51045_)) {
            this.m_51124_(p_51095_, p_51096_, p_51097_);
         }

         super.m_6810_(p_51095_, p_51096_, p_51097_, p_51098_, p_51099_);
      }
   }

   public int m_6378_(BlockState p_51078_, BlockGetter p_51079_, BlockPos p_51080_, Direction p_51081_) {
      return p_51078_.m_61143_(f_51045_) ? 15 : 0;
   }

   public int m_6376_(BlockState p_51109_, BlockGetter p_51110_, BlockPos p_51111_, Direction p_51112_) {
      return p_51109_.m_61143_(f_51045_) && m_53200_(p_51109_) == p_51112_ ? 15 : 0;
   }

   public boolean m_7899_(BlockState p_51114_) {
      return true;
   }

   public void m_7458_(BlockState p_51073_, ServerLevel p_51074_, BlockPos p_51075_, Random p_51076_) {
      if (p_51073_.m_61143_(f_51045_)) {
         if (this.f_51062_) {
            this.m_51120_(p_51073_, p_51074_, p_51075_);
         } else {
            p_51074_.m_7731_(p_51075_, p_51073_.m_61124_(f_51045_, Boolean.valueOf(false)), 3);
            this.m_51124_(p_51073_, p_51074_, p_51075_);
            this.m_51067_((Player)null, p_51074_, p_51075_, false);
            p_51074_.m_151555_(GameEvent.f_157800_, p_51075_);
         }

      }
   }

   public void m_7892_(BlockState p_51083_, Level p_51084_, BlockPos p_51085_, Entity p_51086_) {
      if (!p_51084_.f_46443_ && this.f_51062_ && !p_51083_.m_61143_(f_51045_)) {
         this.m_51120_(p_51083_, p_51084_, p_51085_);
      }
   }

   private void m_51120_(BlockState p_51121_, Level p_51122_, BlockPos p_51123_) {
      List<? extends Entity> list = p_51122_.m_45976_(AbstractArrow.class, p_51121_.m_60808_(p_51122_, p_51123_).m_83215_().m_82338_(p_51123_));
      boolean flag = !list.isEmpty();
      boolean flag1 = p_51121_.m_61143_(f_51045_);
      if (flag != flag1) {
         p_51122_.m_7731_(p_51123_, p_51121_.m_61124_(f_51045_, Boolean.valueOf(flag)), 3);
         this.m_51124_(p_51121_, p_51122_, p_51123_);
         this.m_51067_((Player)null, p_51122_, p_51123_, flag);
         p_51122_.m_142346_(list.stream().findFirst().orElse(null), flag ? GameEvent.f_157798_ : GameEvent.f_157800_, p_51123_);
      }

      if (flag) {
         p_51122_.m_6219_().m_5945_(new BlockPos(p_51123_), this, this.m_51115_());
      }

   }

   private void m_51124_(BlockState p_51125_, Level p_51126_, BlockPos p_51127_) {
      p_51126_.m_46672_(p_51127_, this);
      p_51126_.m_46672_(p_51127_.m_142300_(m_53200_(p_51125_).m_122424_()), this);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51101_) {
      p_51101_.m_61104_(f_54117_, f_51045_, f_53179_);
   }
}