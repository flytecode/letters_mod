package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class LeverBlock extends FaceAttachedHorizontalDirectionalBlock {
   public static final BooleanProperty f_54622_ = BlockStateProperties.f_61448_;
   protected static final int f_153653_ = 6;
   protected static final int f_153654_ = 6;
   protected static final int f_153655_ = 8;
   protected static final VoxelShape f_54623_ = Block.m_49796_(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
   protected static final VoxelShape f_54624_ = Block.m_49796_(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
   protected static final VoxelShape f_54625_ = Block.m_49796_(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
   protected static final VoxelShape f_54626_ = Block.m_49796_(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
   protected static final VoxelShape f_54627_ = Block.m_49796_(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
   protected static final VoxelShape f_54628_ = Block.m_49796_(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
   protected static final VoxelShape f_54629_ = Block.m_49796_(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
   protected static final VoxelShape f_54630_ = Block.m_49796_(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);

   public LeverBlock(BlockBehaviour.Properties p_54633_) {
      super(p_54633_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_54622_, Boolean.valueOf(false)).m_61124_(f_53179_, AttachFace.WALL));
   }

   public VoxelShape m_5940_(BlockState p_54665_, BlockGetter p_54666_, BlockPos p_54667_, CollisionContext p_54668_) {
      switch((AttachFace)p_54665_.m_61143_(f_53179_)) {
      case FLOOR:
         switch(p_54665_.m_61143_(f_54117_).m_122434_()) {
         case X:
            return f_54628_;
         case Z:
         default:
            return f_54627_;
         }
      case WALL:
         switch((Direction)p_54665_.m_61143_(f_54117_)) {
         case EAST:
            return f_54626_;
         case WEST:
            return f_54625_;
         case SOUTH:
            return f_54624_;
         case NORTH:
         default:
            return f_54623_;
         }
      case CEILING:
      default:
         switch(p_54665_.m_61143_(f_54117_).m_122434_()) {
         case X:
            return f_54630_;
         case Z:
         default:
            return f_54629_;
         }
      }
   }

   public InteractionResult m_6227_(BlockState p_54640_, Level p_54641_, BlockPos p_54642_, Player p_54643_, InteractionHand p_54644_, BlockHitResult p_54645_) {
      if (p_54641_.f_46443_) {
         BlockState blockstate1 = p_54640_.m_61122_(f_54622_);
         if (blockstate1.m_61143_(f_54622_)) {
            m_54657_(blockstate1, p_54641_, p_54642_, 1.0F);
         }

         return InteractionResult.SUCCESS;
      } else {
         BlockState blockstate = this.m_54676_(p_54640_, p_54641_, p_54642_);
         float f = blockstate.m_61143_(f_54622_) ? 0.6F : 0.5F;
         p_54641_.m_5594_((Player)null, p_54642_, SoundEvents.f_12088_, SoundSource.BLOCKS, 0.3F, f);
         p_54641_.m_142346_(p_54643_, blockstate.m_61143_(f_54622_) ? GameEvent.f_157799_ : GameEvent.f_157801_, p_54642_);
         return InteractionResult.CONSUME;
      }
   }

   public BlockState m_54676_(BlockState p_54677_, Level p_54678_, BlockPos p_54679_) {
      p_54677_ = p_54677_.m_61122_(f_54622_);
      p_54678_.m_7731_(p_54679_, p_54677_, 3);
      this.m_54680_(p_54677_, p_54678_, p_54679_);
      return p_54677_;
   }

   private static void m_54657_(BlockState p_54658_, LevelAccessor p_54659_, BlockPos p_54660_, float p_54661_) {
      Direction direction = p_54658_.m_61143_(f_54117_).m_122424_();
      Direction direction1 = m_53200_(p_54658_).m_122424_();
      double d0 = (double)p_54660_.m_123341_() + 0.5D + 0.1D * (double)direction.m_122429_() + 0.2D * (double)direction1.m_122429_();
      double d1 = (double)p_54660_.m_123342_() + 0.5D + 0.1D * (double)direction.m_122430_() + 0.2D * (double)direction1.m_122430_();
      double d2 = (double)p_54660_.m_123343_() + 0.5D + 0.1D * (double)direction.m_122431_() + 0.2D * (double)direction1.m_122431_();
      p_54659_.m_7106_(new DustParticleOptions(DustParticleOptions.f_175788_, p_54661_), d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }

   public void m_7100_(BlockState p_54653_, Level p_54654_, BlockPos p_54655_, Random p_54656_) {
      if (p_54653_.m_61143_(f_54622_) && p_54656_.nextFloat() < 0.25F) {
         m_54657_(p_54653_, p_54654_, p_54655_, 0.5F);
      }

   }

   public void m_6810_(BlockState p_54647_, Level p_54648_, BlockPos p_54649_, BlockState p_54650_, boolean p_54651_) {
      if (!p_54651_ && !p_54647_.m_60713_(p_54650_.m_60734_())) {
         if (p_54647_.m_61143_(f_54622_)) {
            this.m_54680_(p_54647_, p_54648_, p_54649_);
         }

         super.m_6810_(p_54647_, p_54648_, p_54649_, p_54650_, p_54651_);
      }
   }

   public int m_6378_(BlockState p_54635_, BlockGetter p_54636_, BlockPos p_54637_, Direction p_54638_) {
      return p_54635_.m_61143_(f_54622_) ? 15 : 0;
   }

   public int m_6376_(BlockState p_54670_, BlockGetter p_54671_, BlockPos p_54672_, Direction p_54673_) {
      return p_54670_.m_61143_(f_54622_) && m_53200_(p_54670_) == p_54673_ ? 15 : 0;
   }

   public boolean m_7899_(BlockState p_54675_) {
      return true;
   }

   private void m_54680_(BlockState p_54681_, Level p_54682_, BlockPos p_54683_) {
      p_54682_.m_46672_(p_54683_, this);
      p_54682_.m_46672_(p_54683_.m_142300_(m_53200_(p_54681_).m_122424_()), this);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54663_) {
      p_54663_.m_61104_(f_53179_, f_54117_, f_54622_);
   }
}