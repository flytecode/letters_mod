package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

public class SculkSensorBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
   private final VibrationListener f_155632_;
   private int f_155633_;

   public SculkSensorBlockEntity(BlockPos p_155635_, BlockState p_155636_) {
      super(BlockEntityType.f_155257_, p_155635_, p_155636_);
      this.f_155632_ = new VibrationListener(new BlockPositionSource(this.f_58858_), ((SculkSensorBlock)p_155636_.m_60734_()).m_154482_(), this);
   }

   public void m_142466_(CompoundTag p_155649_) {
      super.m_142466_(p_155649_);
      this.f_155633_ = p_155649_.m_128451_("last_vibration_frequency");
   }

   public CompoundTag m_6945_(CompoundTag p_155654_) {
      super.m_6945_(p_155654_);
      p_155654_.m_128405_("last_vibration_frequency", this.f_155633_);
      return p_155654_;
   }

   public VibrationListener m_155655_() {
      return this.f_155632_;
   }

   public int m_155656_() {
      return this.f_155633_;
   }

   public boolean m_142008_(Level p_155643_, GameEventListener p_155644_, BlockPos p_155645_, GameEvent p_155646_, @Nullable Entity p_155647_) {
      boolean flag = p_155646_ == GameEvent.f_157794_ && p_155645_.equals(this.m_58899_());
      boolean flag1 = p_155646_ == GameEvent.f_157797_ && p_155645_.equals(this.m_58899_());
      return !flag && !flag1 && SculkSensorBlock.m_154489_(this.m_58900_());
   }

   public void m_142190_(Level p_155638_, GameEventListener p_155639_, GameEvent p_155640_, int p_155641_) {
      BlockState blockstate = this.m_58900_();
      if (!p_155638_.m_5776_() && SculkSensorBlock.m_154489_(blockstate)) {
         this.f_155633_ = SculkSensorBlock.f_154385_.getInt(p_155640_);
         SculkSensorBlock.m_154411_(p_155638_, this.f_58858_, blockstate, m_155650_(p_155641_, p_155639_.m_142078_()));
      }

   }

   public static int m_155650_(int p_155651_, int p_155652_) {
      double d0 = (double)p_155651_ / (double)p_155652_;
      return Math.max(1, 15 - Mth.m_14107_(d0 * 15.0D));
   }
}