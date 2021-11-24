package net.minecraft.world.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ChorusFruitItem extends Item {
   public ChorusFruitItem(Item.Properties p_40710_) {
      super(p_40710_);
   }

   public ItemStack m_5922_(ItemStack p_40712_, Level p_40713_, LivingEntity p_40714_) {
      ItemStack itemstack = super.m_5922_(p_40712_, p_40713_, p_40714_);
      if (!p_40713_.f_46443_) {
         double d0 = p_40714_.m_20185_();
         double d1 = p_40714_.m_20186_();
         double d2 = p_40714_.m_20189_();

         for(int i = 0; i < 16; ++i) {
            double d3 = p_40714_.m_20185_() + (p_40714_.m_21187_().nextDouble() - 0.5D) * 16.0D;
            double d4 = Mth.m_14008_(p_40714_.m_20186_() + (double)(p_40714_.m_21187_().nextInt(16) - 8), (double)p_40713_.m_141937_(), (double)(p_40713_.m_141937_() + ((ServerLevel)p_40713_).m_142475_() - 1));
            double d5 = p_40714_.m_20189_() + (p_40714_.m_21187_().nextDouble() - 0.5D) * 16.0D;
            if (p_40714_.m_20159_()) {
               p_40714_.m_8127_();
            }

            net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(p_40714_, d3, d4, d5);
            if (event.isCanceled()) return itemstack;
            if (p_40714_.m_20984_(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
               SoundEvent soundevent = p_40714_ instanceof Fox ? SoundEvents.f_11953_ : SoundEvents.f_11757_;
               p_40713_.m_6263_((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
               p_40714_.m_5496_(soundevent, 1.0F, 1.0F);
               break;
            }
         }

         if (p_40714_ instanceof Player) {
            ((Player)p_40714_).m_36335_().m_41524_(this, 20);
         }
      }

      return itemstack;
   }
}
