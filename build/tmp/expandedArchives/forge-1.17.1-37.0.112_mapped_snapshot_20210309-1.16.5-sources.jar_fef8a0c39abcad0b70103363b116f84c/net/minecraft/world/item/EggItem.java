package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;

public class EggItem extends Item {
   public EggItem(Item.Properties p_41126_) {
      super(p_41126_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41128_, Player p_41129_, InteractionHand p_41130_) {
      ItemStack itemstack = p_41129_.m_21120_(p_41130_);
      p_41128_.m_6263_((Player)null, p_41129_.m_20185_(), p_41129_.m_20186_(), p_41129_.m_20189_(), SoundEvents.f_11877_, SoundSource.PLAYERS, 0.5F, 0.4F / (p_41128_.m_5822_().nextFloat() * 0.4F + 0.8F));
      if (!p_41128_.f_46443_) {
         ThrownEgg thrownegg = new ThrownEgg(p_41128_, p_41129_);
         thrownegg.m_37446_(itemstack);
         thrownegg.m_37251_(p_41129_, p_41129_.m_146909_(), p_41129_.m_146908_(), 0.0F, 1.5F, 1.0F);
         p_41128_.m_7967_(thrownegg);
      }

      p_41129_.m_36246_(Stats.f_12982_.m_12902_(this));
      if (!p_41129_.m_150110_().f_35937_) {
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19092_(itemstack, p_41128_.m_5776_());
   }
}