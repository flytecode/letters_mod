package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;

public class ItemFrameItem extends HangingEntityItem {
   public ItemFrameItem(EntityType<? extends HangingEntity> p_150904_, Item.Properties p_150905_) {
      super(p_150904_, p_150905_);
   }

   protected boolean m_5595_(Player p_41551_, Direction p_41552_, ItemStack p_41553_, BlockPos p_41554_) {
      return !p_41551_.f_19853_.m_151570_(p_41554_) && p_41551_.m_36204_(p_41554_, p_41552_, p_41553_);
   }
}