package net.minecraft.world.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.level.Level;

public class ExperienceBottleItem extends Item {
   public ExperienceBottleItem(Item.Properties p_41194_) {
      super(p_41194_);
   }

   public boolean m_5812_(ItemStack p_41200_) {
      return true;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41196_, Player p_41197_, InteractionHand p_41198_) {
      ItemStack itemstack = p_41197_.m_21120_(p_41198_);
      p_41196_.m_6263_((Player)null, p_41197_.m_20185_(), p_41197_.m_20186_(), p_41197_.m_20189_(), SoundEvents.f_11870_, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41196_.m_5822_().nextFloat() * 0.4F + 0.8F));
      if (!p_41196_.f_46443_) {
         ThrownExperienceBottle thrownexperiencebottle = new ThrownExperienceBottle(p_41196_, p_41197_);
         thrownexperiencebottle.m_37446_(itemstack);
         thrownexperiencebottle.m_37251_(p_41197_, p_41197_.m_146909_(), p_41197_.m_146908_(), -20.0F, 0.7F, 1.0F);
         p_41196_.m_7967_(thrownexperiencebottle);
      }

      p_41197_.m_36246_(Stats.f_12982_.m_12902_(this));
      if (!p_41197_.m_150110_().f_35937_) {
         itemstack.m_41774_(1);
      }

      return InteractionResultHolder.m_19092_(itemstack, p_41196_.m_5776_());
   }
}