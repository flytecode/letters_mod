package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BedBlockEntity extends BlockEntity {
   private DyeColor f_58724_;

   public BedBlockEntity(BlockPos p_155115_, BlockState p_155116_) {
      super(BlockEntityType.f_58940_, p_155115_, p_155116_);
      this.f_58724_ = ((BedBlock)p_155116_.m_60734_()).m_49554_();
   }

   public BedBlockEntity(BlockPos p_155118_, BlockState p_155119_, DyeColor p_155120_) {
      super(BlockEntityType.f_58940_, p_155118_, p_155119_);
      this.f_58724_ = p_155120_;
   }

   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 11, this.m_5995_());
   }

   public DyeColor m_58731_() {
      return this.f_58724_;
   }

   public void m_58729_(DyeColor p_58730_) {
      this.f_58724_ = p_58730_;
   }
}