package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GameMasterBlockItem extends BlockItem {
   public GameMasterBlockItem(Block p_41318_, Item.Properties p_41319_) {
      super(p_41318_, p_41319_);
   }

   @Nullable
   protected BlockState m_5965_(BlockPlaceContext p_41321_) {
      Player player = p_41321_.m_43723_();
      return player != null && !player.m_36337_() ? null : super.m_5965_(p_41321_);
   }
}