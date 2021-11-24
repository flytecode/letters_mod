package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class ComparatorBlockEntity extends BlockEntity {
   private int f_59173_;

   public ComparatorBlockEntity(BlockPos p_155386_, BlockState p_155387_) {
      super(BlockEntityType.f_58934_, p_155386_, p_155387_);
   }

   public CompoundTag m_6945_(CompoundTag p_59181_) {
      super.m_6945_(p_59181_);
      p_59181_.m_128405_("OutputSignal", this.f_59173_);
      return p_59181_;
   }

   public void m_142466_(CompoundTag p_155389_) {
      super.m_142466_(p_155389_);
      this.f_59173_ = p_155389_.m_128451_("OutputSignal");
   }

   public int m_59182_() {
      return this.f_59173_;
   }

   public void m_59175_(int p_59176_) {
      this.f_59173_ = p_59176_;
   }
}