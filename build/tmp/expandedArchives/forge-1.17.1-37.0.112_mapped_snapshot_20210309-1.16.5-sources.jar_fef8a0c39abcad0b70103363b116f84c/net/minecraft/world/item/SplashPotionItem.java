package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SplashPotionItem extends ThrowablePotionItem {
   public SplashPotionItem(Item.Properties p_43241_) {
      super(p_43241_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_43243_, Player p_43244_, InteractionHand p_43245_) {
      p_43243_.m_6263_((Player)null, p_43244_.m_20185_(), p_43244_.m_20186_(), p_43244_.m_20189_(), SoundEvents.f_12437_, SoundSource.PLAYERS, 0.5F, 0.4F / (p_43243_.m_5822_().nextFloat() * 0.4F + 0.8F));
      return super.m_7203_(p_43243_, p_43244_, p_43245_);
   }
}