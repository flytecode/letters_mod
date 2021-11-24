package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScaffoldingBlockItem extends BlockItem {
   public ScaffoldingBlockItem(Block p_43060_, Item.Properties p_43061_) {
      super(p_43060_, p_43061_);
   }

   @Nullable
   public BlockPlaceContext m_7732_(BlockPlaceContext p_43063_) {
      BlockPos blockpos = p_43063_.m_8083_();
      Level level = p_43063_.m_43725_();
      BlockState blockstate = level.m_8055_(blockpos);
      Block block = this.m_40614_();
      if (!blockstate.m_60713_(block)) {
         return ScaffoldingBlock.m_56024_(level, blockpos) == 7 ? null : p_43063_;
      } else {
         Direction direction;
         if (p_43063_.m_7078_()) {
            direction = p_43063_.m_43721_() ? p_43063_.m_43719_().m_122424_() : p_43063_.m_43719_();
         } else {
            direction = p_43063_.m_43719_() == Direction.UP ? p_43063_.m_8125_() : Direction.UP;
         }

         int i = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_().m_122173_(direction);

         while(i < 7) {
            if (!level.f_46443_ && !level.m_46739_(blockpos$mutableblockpos)) {
               Player player = p_43063_.m_43723_();
               int j = level.m_151558_();
               if (player instanceof ServerPlayer && blockpos$mutableblockpos.m_123342_() >= j) {
                  ((ServerPlayer)player).m_9146_((new TranslatableComponent("build.tooHigh", j - 1)).m_130940_(ChatFormatting.RED), ChatType.GAME_INFO, Util.f_137441_);
               }
               break;
            }

            blockstate = level.m_8055_(blockpos$mutableblockpos);
            if (!blockstate.m_60713_(this.m_40614_())) {
               if (blockstate.m_60629_(p_43063_)) {
                  return BlockPlaceContext.m_43644_(p_43063_, blockpos$mutableblockpos, direction);
               }
               break;
            }

            blockpos$mutableblockpos.m_122173_(direction);
            if (direction.m_122434_().m_122479_()) {
               ++i;
            }
         }

         return null;
      }
   }

   protected boolean m_6652_() {
      return false;
   }
}