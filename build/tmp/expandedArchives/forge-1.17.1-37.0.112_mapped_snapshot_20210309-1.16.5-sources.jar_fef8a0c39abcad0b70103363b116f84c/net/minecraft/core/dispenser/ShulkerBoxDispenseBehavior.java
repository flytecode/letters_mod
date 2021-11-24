package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShulkerBoxDispenseBehavior extends OptionalDispenseItemBehavior {
   private static final Logger f_175749_ = LogManager.getLogger();

   protected ItemStack m_7498_(BlockSource p_123587_, ItemStack p_123588_) {
      this.m_123573_(false);
      Item item = p_123588_.m_41720_();
      if (item instanceof BlockItem) {
         Direction direction = p_123587_.m_6414_().m_61143_(DispenserBlock.f_52659_);
         BlockPos blockpos = p_123587_.m_7961_().m_142300_(direction);
         Direction direction1 = p_123587_.m_7727_().m_46859_(blockpos.m_7495_()) ? direction : Direction.UP;

         try {
            this.m_123573_(((BlockItem)item).m_40576_(new DirectionalPlaceContext(p_123587_.m_7727_(), blockpos, direction, p_123588_, direction1)).m_19077_());
         } catch (Exception exception) {
            f_175749_.error("Error trying to place shulker box at {}", blockpos, exception);
         }
      }

      return p_123588_;
   }
}