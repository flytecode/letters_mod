package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class LingeringPotionItem extends ThrowablePotionItem {
   public LingeringPotionItem(Item.Properties p_42836_) {
      super(p_42836_);
   }

   public void m_7373_(ItemStack p_42838_, @Nullable Level p_42839_, List<Component> p_42840_, TooltipFlag p_42841_) {
      PotionUtils.m_43555_(p_42838_, p_42840_, 0.25F);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_42843_, Player p_42844_, InteractionHand p_42845_) {
      p_42843_.m_6263_((Player)null, p_42844_.m_20185_(), p_42844_.m_20186_(), p_42844_.m_20189_(), SoundEvents.f_12091_, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_42843_.m_5822_().nextFloat() * 0.4F + 0.8F));
      return super.m_7203_(p_42843_, p_42844_, p_42845_);
   }
}