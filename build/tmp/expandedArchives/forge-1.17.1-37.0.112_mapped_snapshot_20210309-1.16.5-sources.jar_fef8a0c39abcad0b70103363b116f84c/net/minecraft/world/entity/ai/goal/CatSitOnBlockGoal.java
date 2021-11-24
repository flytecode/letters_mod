package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class CatSitOnBlockGoal extends MoveToBlockGoal {
   private final Cat f_25147_;

   public CatSitOnBlockGoal(Cat p_25149_, double p_25150_) {
      super(p_25149_, p_25150_, 8);
      this.f_25147_ = p_25149_;
   }

   public boolean m_8036_() {
      return this.f_25147_.m_21824_() && !this.f_25147_.m_21827_() && super.m_8036_();
   }

   public void m_8056_() {
      super.m_8056_();
      this.f_25147_.m_21837_(false);
   }

   public void m_8041_() {
      super.m_8041_();
      this.f_25147_.m_21837_(false);
   }

   public void m_8037_() {
      super.m_8037_();
      this.f_25147_.m_21837_(this.m_25625_());
   }

   protected boolean m_6465_(LevelReader p_25153_, BlockPos p_25154_) {
      if (!p_25153_.m_46859_(p_25154_.m_7494_())) {
         return false;
      } else {
         BlockState blockstate = p_25153_.m_8055_(p_25154_);
         if (blockstate.m_60713_(Blocks.f_50087_)) {
            return ChestBlockEntity.m_59086_(p_25153_, p_25154_) < 1;
         } else {
            return blockstate.m_60713_(Blocks.f_50094_) && blockstate.m_61143_(FurnaceBlock.f_48684_) ? true : blockstate.m_60622_(BlockTags.f_13038_, (p_25156_) -> {
               return p_25156_.<BedPart>m_61145_(BedBlock.f_49440_).map((p_148084_) -> {
                  return p_148084_ != BedPart.HEAD;
               }).orElse(true);
            });
         }
      }
   }
}