package net.minecraft.world.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class StandingAndWallBlockItem extends BlockItem {
   protected final Block f_43246_;

   public StandingAndWallBlockItem(Block p_43248_, Block p_43249_, Item.Properties p_43250_) {
      super(p_43248_, p_43250_);
      this.f_43246_ = p_43249_;
   }

   @Nullable
   protected BlockState m_5965_(BlockPlaceContext p_43255_) {
      BlockState blockstate = this.f_43246_.m_5573_(p_43255_);
      BlockState blockstate1 = null;
      LevelReader levelreader = p_43255_.m_43725_();
      BlockPos blockpos = p_43255_.m_8083_();

      for(Direction direction : p_43255_.m_6232_()) {
         if (direction != Direction.UP) {
            BlockState blockstate2 = direction == Direction.DOWN ? this.m_40614_().m_5573_(p_43255_) : blockstate;
            if (blockstate2 != null && blockstate2.m_60710_(levelreader, blockpos)) {
               blockstate1 = blockstate2;
               break;
            }
         }
      }

      return blockstate1 != null && levelreader.m_45752_(blockstate1, blockpos, CollisionContext.m_82749_()) ? blockstate1 : null;
   }

   public void m_6192_(Map<Block, Item> p_43252_, Item p_43253_) {
      super.m_6192_(p_43252_, p_43253_);
      p_43252_.put(this.f_43246_, p_43253_);
   }

   public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
      super.removeFromBlockToItemMap(blockToItemMap, itemIn);
      blockToItemMap.remove(this.f_43246_);
   }
}
