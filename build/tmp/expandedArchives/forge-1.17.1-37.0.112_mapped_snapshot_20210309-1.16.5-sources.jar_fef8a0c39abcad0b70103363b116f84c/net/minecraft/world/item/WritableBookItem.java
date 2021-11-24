package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WritableBookItem extends Item {
   public WritableBookItem(Item.Properties p_43445_) {
      super(p_43445_);
   }

   public InteractionResult m_6225_(UseOnContext p_43447_) {
      Level level = p_43447_.m_43725_();
      BlockPos blockpos = p_43447_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (blockstate.m_60713_(Blocks.f_50624_)) {
         return LecternBlock.m_153566_(p_43447_.m_43723_(), level, blockpos, blockstate, p_43447_.m_43722_()) ? InteractionResult.m_19078_(level.f_46443_) : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43449_, Player p_43450_, InteractionHand p_43451_) {
      ItemStack itemstack = p_43450_.m_21120_(p_43451_);
      p_43450_.m_6986_(itemstack, p_43451_);
      p_43450_.m_36246_(Stats.f_12982_.m_12902_(this));
      return InteractionResultHolder.m_19092_(itemstack, p_43449_.m_5776_());
   }

   public static boolean m_43452_(@Nullable CompoundTag p_43453_) {
      if (p_43453_ == null) {
         return false;
      } else if (!p_43453_.m_128425_("pages", 9)) {
         return false;
      } else {
         ListTag listtag = p_43453_.m_128437_("pages", 8);

         for(int i = 0; i < listtag.size(); ++i) {
            String s = listtag.m_128778_(i);
            if (s.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}