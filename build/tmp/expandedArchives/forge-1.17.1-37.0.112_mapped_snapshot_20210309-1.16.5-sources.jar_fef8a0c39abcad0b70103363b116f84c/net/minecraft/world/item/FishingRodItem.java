package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FishingRodItem extends Item implements Vanishable {
   public FishingRodItem(Item.Properties p_41285_) {
      super(p_41285_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41290_, Player p_41291_, InteractionHand p_41292_) {
      ItemStack itemstack = p_41291_.m_21120_(p_41292_);
      if (p_41291_.f_36083_ != null) {
         if (!p_41290_.f_46443_) {
            int i = p_41291_.f_36083_.m_37156_(itemstack);
            itemstack.m_41622_(i, p_41291_, (p_41288_) -> {
               p_41288_.m_21190_(p_41292_);
            });
         }

         p_41290_.m_6263_((Player)null, p_41291_.m_20185_(), p_41291_.m_20186_(), p_41291_.m_20189_(), SoundEvents.f_11939_, SoundSource.NEUTRAL, 1.0F, 0.4F / (p_41290_.m_5822_().nextFloat() * 0.4F + 0.8F));
         p_41290_.m_151545_(p_41291_, GameEvent.f_157814_, p_41291_);
      } else {
         p_41290_.m_6263_((Player)null, p_41291_.m_20185_(), p_41291_.m_20186_(), p_41291_.m_20189_(), SoundEvents.f_11941_, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41290_.m_5822_().nextFloat() * 0.4F + 0.8F));
         if (!p_41290_.f_46443_) {
            int k = EnchantmentHelper.m_44916_(itemstack);
            int j = EnchantmentHelper.m_44904_(itemstack);
            p_41290_.m_7967_(new FishingHook(p_41291_, p_41290_, j, k));
         }

         p_41291_.m_36246_(Stats.f_12982_.m_12902_(this));
         p_41290_.m_151545_(p_41291_, GameEvent.f_157813_, p_41291_);
      }

      return InteractionResultHolder.m_19092_(itemstack, p_41290_.m_5776_());
   }

   public int m_6473_() {
      return 1;
   }
}