package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BasePressurePlateBlock extends Block {
   protected static final VoxelShape f_49285_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 0.5D, 15.0D);
   protected static final VoxelShape f_49286_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
   protected static final AABB f_49287_ = new AABB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);

   protected BasePressurePlateBlock(BlockBehaviour.Properties p_49290_) {
      super(p_49290_);
   }

   public VoxelShape m_5940_(BlockState p_49341_, BlockGetter p_49342_, BlockPos p_49343_, CollisionContext p_49344_) {
      return this.m_6016_(p_49341_) > 0 ? f_49285_ : f_49286_;
   }

   protected int m_7342_() {
      return 20;
   }

   public boolean m_5568_() {
      return true;
   }

   public BlockState m_7417_(BlockState p_49329_, Direction p_49330_, BlockState p_49331_, LevelAccessor p_49332_, BlockPos p_49333_, BlockPos p_49334_) {
      return p_49330_ == Direction.DOWN && !p_49329_.m_60710_(p_49332_, p_49333_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_49329_, p_49330_, p_49331_, p_49332_, p_49333_, p_49334_);
   }

   public boolean m_7898_(BlockState p_49325_, LevelReader p_49326_, BlockPos p_49327_) {
      BlockPos blockpos = p_49327_.m_7495_();
      return m_49936_(p_49326_, blockpos) || m_49863_(p_49326_, blockpos, Direction.UP);
   }

   public void m_7458_(BlockState p_49304_, ServerLevel p_49305_, BlockPos p_49306_, Random p_49307_) {
      int i = this.m_6016_(p_49304_);
      if (i > 0) {
         this.m_152143_((Entity)null, p_49305_, p_49306_, p_49304_, i);
      }

   }

   public void m_7892_(BlockState p_49314_, Level p_49315_, BlockPos p_49316_, Entity p_49317_) {
      if (!p_49315_.f_46443_) {
         int i = this.m_6016_(p_49314_);
         if (i == 0) {
            this.m_152143_(p_49317_, p_49315_, p_49316_, p_49314_, i);
         }

      }
   }

   protected void m_152143_(@Nullable Entity p_152144_, Level p_152145_, BlockPos p_152146_, BlockState p_152147_, int p_152148_) {
      int i = this.m_6693_(p_152145_, p_152146_);
      boolean flag = p_152148_ > 0;
      boolean flag1 = i > 0;
      if (p_152148_ != i) {
         BlockState blockstate = this.m_7422_(p_152147_, i);
         p_152145_.m_7731_(p_152146_, blockstate, 2);
         this.m_49291_(p_152145_, p_152146_);
         p_152145_.m_6550_(p_152146_, p_152147_, blockstate);
      }

      if (!flag1 && flag) {
         this.m_5493_(p_152145_, p_152146_);
         p_152145_.m_142346_(p_152144_, GameEvent.f_157800_, p_152146_);
      } else if (flag1 && !flag) {
         this.m_5494_(p_152145_, p_152146_);
         p_152145_.m_142346_(p_152144_, GameEvent.f_157798_, p_152146_);
      }

      if (flag1) {
         p_152145_.m_6219_().m_5945_(new BlockPos(p_152146_), this, this.m_7342_());
      }

   }

   protected abstract void m_5494_(LevelAccessor p_49299_, BlockPos p_49300_);

   protected abstract void m_5493_(LevelAccessor p_49338_, BlockPos p_49339_);

   public void m_6810_(BlockState p_49319_, Level p_49320_, BlockPos p_49321_, BlockState p_49322_, boolean p_49323_) {
      if (!p_49323_ && !p_49319_.m_60713_(p_49322_.m_60734_())) {
         if (this.m_6016_(p_49319_) > 0) {
            this.m_49291_(p_49320_, p_49321_);
         }

         super.m_6810_(p_49319_, p_49320_, p_49321_, p_49322_, p_49323_);
      }
   }

   protected void m_49291_(Level p_49292_, BlockPos p_49293_) {
      p_49292_.m_46672_(p_49293_, this);
      p_49292_.m_46672_(p_49293_.m_7495_(), this);
   }

   public int m_6378_(BlockState p_49309_, BlockGetter p_49310_, BlockPos p_49311_, Direction p_49312_) {
      return this.m_6016_(p_49309_);
   }

   public int m_6376_(BlockState p_49346_, BlockGetter p_49347_, BlockPos p_49348_, Direction p_49349_) {
      return p_49349_ == Direction.UP ? this.m_6016_(p_49346_) : 0;
   }

   public boolean m_7899_(BlockState p_49351_) {
      return true;
   }

   public PushReaction m_5537_(BlockState p_49353_) {
      return PushReaction.DESTROY;
   }

   protected abstract int m_6693_(Level p_49336_, BlockPos p_49337_);

   protected abstract int m_6016_(BlockState p_49354_);

   protected abstract BlockState m_7422_(BlockState p_49301_, int p_49302_);
}