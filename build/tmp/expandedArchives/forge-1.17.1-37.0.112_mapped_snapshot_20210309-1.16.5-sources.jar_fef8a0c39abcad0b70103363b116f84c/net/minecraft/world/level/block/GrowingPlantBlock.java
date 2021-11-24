package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantBlock extends Block {
   protected final Direction f_53859_;
   protected final boolean f_53860_;
   protected final VoxelShape f_53861_;

   protected GrowingPlantBlock(BlockBehaviour.Properties p_53863_, Direction p_53864_, VoxelShape p_53865_, boolean p_53866_) {
      super(p_53863_);
      this.f_53859_ = p_53864_;
      this.f_53861_ = p_53865_;
      this.f_53860_ = p_53866_;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_53868_) {
      BlockState blockstate = p_53868_.m_43725_().m_8055_(p_53868_.m_8083_().m_142300_(this.f_53859_));
      return !blockstate.m_60713_(this.m_7272_()) && !blockstate.m_60713_(this.m_7777_()) ? this.m_7722_(p_53868_.m_43725_()) : this.m_7777_().m_49966_();
   }

   public BlockState m_7722_(LevelAccessor p_53869_) {
      return this.m_49966_();
   }

   public boolean m_7898_(BlockState p_53876_, LevelReader p_53877_, BlockPos p_53878_) {
      BlockPos blockpos = p_53878_.m_142300_(this.f_53859_.m_122424_());
      BlockState blockstate = p_53877_.m_8055_(blockpos);
      if (!this.m_142209_(blockstate)) {
         return false;
      } else {
         return blockstate.m_60713_(this.m_7272_()) || blockstate.m_60713_(this.m_7777_()) || blockstate.m_60783_(p_53877_, blockpos, this.f_53859_);
      }
   }

   public void m_7458_(BlockState p_53871_, ServerLevel p_53872_, BlockPos p_53873_, Random p_53874_) {
      if (!p_53871_.m_60710_(p_53872_, p_53873_)) {
         p_53872_.m_46961_(p_53873_, true);
      }

   }

   protected boolean m_142209_(BlockState p_153321_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_53880_, BlockGetter p_53881_, BlockPos p_53882_, CollisionContext p_53883_) {
      return this.f_53861_;
   }

   protected abstract GrowingPlantHeadBlock m_7272_();

   protected abstract Block m_7777_();
}