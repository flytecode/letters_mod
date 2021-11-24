package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class BlockEventData {
   private final BlockPos f_45529_;
   private final Block f_45530_;
   private final int f_45531_;
   private final int f_45532_;

   public BlockEventData(BlockPos p_45534_, Block p_45535_, int p_45536_, int p_45537_) {
      this.f_45529_ = p_45534_;
      this.f_45530_ = p_45535_;
      this.f_45531_ = p_45536_;
      this.f_45532_ = p_45537_;
   }

   public BlockPos m_45538_() {
      return this.f_45529_;
   }

   public Block m_45539_() {
      return this.f_45530_;
   }

   public int m_45540_() {
      return this.f_45531_;
   }

   public int m_45541_() {
      return this.f_45532_;
   }

   public boolean equals(Object p_45543_) {
      if (!(p_45543_ instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData blockeventdata = (BlockEventData)p_45543_;
         return this.f_45529_.equals(blockeventdata.f_45529_) && this.f_45531_ == blockeventdata.f_45531_ && this.f_45532_ == blockeventdata.f_45532_ && this.f_45530_ == blockeventdata.f_45530_;
      }
   }

   public int hashCode() {
      int i = this.f_45529_.hashCode();
      i = 31 * i + this.f_45530_.hashCode();
      i = 31 * i + this.f_45531_;
      return 31 * i + this.f_45532_;
   }

   public String toString() {
      return "TE(" + this.f_45529_ + ")," + this.f_45531_ + "," + this.f_45532_ + "," + this.f_45530_;
   }
}