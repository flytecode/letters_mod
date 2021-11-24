package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class DropperBlockEntity extends DispenserBlockEntity {
   public DropperBlockEntity(BlockPos p_155498_, BlockState p_155499_) {
      super(BlockEntityType.f_58923_, p_155498_, p_155499_);
   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.dropper");
   }
}