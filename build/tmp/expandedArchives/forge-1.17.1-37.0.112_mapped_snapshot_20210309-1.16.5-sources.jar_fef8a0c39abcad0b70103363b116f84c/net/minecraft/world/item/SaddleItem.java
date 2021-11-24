package net.minecraft.world.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;

public class SaddleItem extends Item {
   public SaddleItem(Item.Properties p_43053_) {
      super(p_43053_);
   }

   public InteractionResult m_6880_(ItemStack p_43055_, Player p_43056_, LivingEntity p_43057_, InteractionHand p_43058_) {
      if (p_43057_ instanceof Saddleable && p_43057_.m_6084_()) {
         Saddleable saddleable = (Saddleable)p_43057_;
         if (!saddleable.m_6254_() && saddleable.m_6741_()) {
            if (!p_43056_.f_19853_.f_46443_) {
               saddleable.m_5853_(SoundSource.NEUTRAL);
               p_43055_.m_41774_(1);
            }

            return InteractionResult.m_19078_(p_43056_.f_19853_.f_46443_);
         }
      }

      return InteractionResult.PASS;
   }
}